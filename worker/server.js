"use strict";

const express = require("express");
const { GoogleAuth } = require("google-auth-library");
const { VertexAI } = require("@google-cloud/vertexai");

const app = express();
app.disable("x-powered-by");
app.use(express.json({ limit: "256kb", strict: true }));

const port = Number(process.env.PORT || 8080);
const workerId = process.env.JOB_WORKER_ID || process.env.K_REVISION || "cloud-run-worker";
const functionsBaseUrl = (process.env.FUNCTIONS_BASE_URL || "").replace(/\/$/, "");
const projectId = process.env.GCP_PROJECT_ID || process.env.GOOGLE_CLOUD_PROJECT;
const modelName = process.env.VERTEX_AI_MODEL || "gemini-2.5-flash";
const location = process.env.VERTEX_AI_LOCATION || "us-central1";
const auth = new GoogleAuth();

function required(name, value) {
  if (!value) throw new Error("Missing required worker configuration: " + name);
  return value;
}

function functionUrl(name) {
  return required("FUNCTIONS_BASE_URL", functionsBaseUrl) + "/" + name;
}

async function callPrivateFunction(name, payload) {
  const url = functionUrl(name);
  const client = await auth.getIdTokenClient(url);
  const response = await client.request({
    url,
    method: "POST",
    data: payload,
    headers: { "Content-Type": "application/json" },
    timeout: 30000
  });
  return response.data;
}

function extractText(response) {
  const candidates = response && response.candidates;
  if (!Array.isArray(candidates) || !candidates.length) {
    throw new Error("The model returned no candidates.");
  }
  const parts = candidates[0].content && candidates[0].content.parts;
  if (!Array.isArray(parts)) throw new Error("The model response has no content parts.");
  return parts.map(part => typeof part.text === "string" ? part.text : "").join("").trim();
}

app.get("/health", (_req, res) => {
  res.status(200).json({ ok: true, service: "intelligent-app-designer-worker" });
});

app.post("/tasks", async (req, res) => {
  // Cloud Run IAM is the authentication boundary. Restrict Cloud Run Invoker
  // to the Cloud Tasks OIDC service account; do not make this service public.
  if (!req.get("X-CloudTasks-TaskName") || !req.get("X-CloudTasks-QueueName")) {
    res.status(403).json({ error: "cloud-tasks-headers-required" });
    return;
  }
  const jobId = typeof req.body?.jobId === "string" ? req.body.jobId : "";
  if (!jobId || jobId.length > 128) {
    res.status(400).json({ error: "invalid-job-id" });
    return;
  }

  let lease;
  let heartbeatTimer;
  let heartbeatBusy = false;
  let leaseLost = false;
  try {
    const claimed = await callPrivateFunction("claimPersistentJob", { jobId, workerId });
    if (claimed?.cancelled || claimed?.alreadyFinished) {
      res.status(200).json({ jobId, state: claimed.state || "CANCELLED" });
      return;
    }
    lease = claimed;
    const prompt = lease?.job?.prompt || lease?.job?.userPrompt;
    if (typeof prompt !== "string" || !prompt.trim()) {
      throw new Error("The queued job has no prompt.");
    }

    heartbeatTimer = setInterval(async () => {
      if (heartbeatBusy || leaseLost || !lease) return;
      heartbeatBusy = true;
      try {
        await callPrivateFunction("renewPersistentJobLease", {
          jobId, workerId, leaseToken: lease.leaseToken
        });
      } catch (error) {
        leaseLost = true;
        console.error("Worker lease renewal failed", { jobId, message: error.message });
      } finally {
        heartbeatBusy = false;
      }
    }, 20000);

    const update = async (progress, currentStepIndex, message) => {
      try {
        await callPrivateFunction("updatePersistentJobProgress", {
          jobId, workerId, leaseToken: lease.leaseToken,
          progress, currentStepIndex, message
        });
        return true;
      } catch (error) {
        if (error.response?.status === 409) return false;
        throw error;
      }
    };

    if (!await update(10, 0, "Preparing the AI execution environment")) {
      await callPrivateFunction("finishPersistentJob", {
        jobId, workerId, leaseToken: lease.leaseToken,
        state: "CANCELLED", summary: "Cancelled before model execution."
      });
      res.status(200).json({ jobId, state: "CANCELLED" });
      return;
    }

    const vertex = new VertexAI({
      project: required("GCP_PROJECT_ID", projectId),
      location
    });
    const model = vertex.getGenerativeModel({
      model: modelName,
      generationConfig: { temperature: 0.2, maxOutputTokens: 8192 }
    });

    if (!await update(25, 1, "Sending the task to the configured server-side AI model")) {
      await callPrivateFunction("finishPersistentJob", {
        jobId, workerId, leaseToken: lease.leaseToken,
        state: "CANCELLED", summary: "Cancelled before model execution."
      });
      res.status(200).json({ jobId, state: "CANCELLED" });
      return;
    }
    const generated = await model.generateContent({
      contents: [{ role: "user", parts: [{ text: prompt }] }]
    });
    if (leaseLost) {
      res.status(503).json({ error: "worker-lease-lost", jobId });
      return;
    }

    const output = extractText(generated.response);
    if (!output) throw new Error("The AI model returned an empty result.");
    if (!await update(90, 2, "Saving the generated result")) {
      await callPrivateFunction("finishPersistentJob", {
        jobId, workerId, leaseToken: lease.leaseToken,
        state: "CANCELLED", summary: "Cancelled after model execution."
      });
      res.status(200).json({ jobId, state: "CANCELLED" });
      return;
    }

    await callPrivateFunction("finishPersistentJob", {
      jobId,
      workerId,
      leaseToken: lease.leaseToken,
      state: "SUCCEEDED",
      summary: output.slice(0, 4000),
      resultText: output.slice(0, 80000),
      resultModel: modelName
    });
    res.status(200).json({ jobId, state: "SUCCEEDED" });
  } catch (error) {
    if (!lease && error.response?.status === 409 &&
        error.response?.data?.error === "job-already-claimed") {
      res.status(503).json({ error: "job-lease-currently-owned", jobId });
      return;
    }
    console.error("Persistent job execution failed", {
      jobId, message: error.message, status: error.response?.status
    });
    if (lease && !leaseLost) {
      try {
        await callPrivateFunction("finishPersistentJob", {
          jobId, workerId, leaseToken: lease.leaseToken,
          state: "FAILED",
          summary: "Execution failed. Check the server-side job logs."
        });
        res.status(200).json({ jobId, state: "FAILED" });
        return;
      } catch (finishError) {
        console.error("Could not finalize failed job", {
          jobId, message: finishError.message
        });
      }
    }
    // A failed claim due to a live lease is retried by Cloud Tasks. Configure
    // queue retry/backoff so a reclaimed expired lease can be processed.
    res.status(503).json({ error: "job-execution-unavailable", jobId });
  } finally {
    if (heartbeatTimer) clearInterval(heartbeatTimer);
  }
});

app.listen(port, "0.0.0.0", () => {
  console.log("Trusted job worker listening", { port, workerId, modelName, location });
});
