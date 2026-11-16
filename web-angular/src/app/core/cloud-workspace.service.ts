import { Injectable, inject } from '@angular/core';
import { FirebaseClientService } from './firebase-client.service';
import { Platform, ProjectStatus } from './models';

export interface CloudProject {
  id: string;
  name: string;
  description: string;
  platforms: Platform[];
  status: ProjectStatus;
  phase: number;
  progress: number;
  createdAt: number | null;
  updatedAt: number | null;
}

export interface CloudJob {
  id: string;
  projectId: string;
  jobType: string;
  title: string;
  status: 'QUEUED' | 'RUNNING' | 'SUCCEEDED' | 'FAILED' | 'CANCELLED' | string;
  progress: number;
  progressPercent: number;
  currentStepIndex: number;
  attempts: number;
  cancelRequested: boolean;
  dispatchStatus: string;
  progressMessage: string;
  createdAt: number | null;
  updatedAt: number | null;
}

export interface CloudJobResult {
  jobId: string;
  status: string;
  progressPercent: number;
  summary: string;
  resultText: string;
  resultModel: string;
  resultFiles: Array<{ path: string; content: string }>;
  createdAt: number | null;
  updatedAt: number | null;
  finishedAt: number | null;
}

@Injectable({ providedIn: 'root' })
export class CloudWorkspaceService {
  private readonly firebase = inject(FirebaseClientService);

  listProjects(): Promise<{ projects: CloudProject[] }> {
    return this.firebase.call<Record<string, never>, { projects: CloudProject[] }>('listProjects', {});
  }

  createProject(input: { name: string; description: string; platforms: Platform[] }): Promise<{ projectId: string }> {
    return this.firebase.call<typeof input, { projectId: string }>('createProject', input);
  }

  updateProject(input: { projectId: string; name?: string; description?: string; platforms?: Platform[] }): Promise<{ projectId: string; updated: boolean }> {
    return this.firebase.call<typeof input, { projectId: string; updated: boolean }>('updateProject', input);
  }

  createJob(input: {
    projectId: string;
    jobType: string;
    title: string;
    prompt: string;
    projectName?: string;
    idempotencyKey: string;
  }): Promise<{ jobId: string; reused: boolean; dispatchStatus: string }> {
    return this.firebase.call<typeof input, { jobId: string; reused: boolean; dispatchStatus: string }>(
      'createPersistentJob', input,
    );
  }

  listJobs(projectId: string): Promise<{ jobs: CloudJob[] }> {
    return this.firebase.call<{ projectId: string }, { jobs: CloudJob[] }>('listPersistentJobs', { projectId });
  }

  getJobResult(jobId: string): Promise<CloudJobResult> {
    return this.firebase.call<{ jobId: string }, CloudJobResult>('getPersistentJobResult', { jobId });
  }

  cancelJob(jobId: string): Promise<{ jobId: string; cancellationRequested: boolean }> {
    return this.firebase.call<{ jobId: string }, { jobId: string; cancellationRequested: boolean }>(
      'cancelPersistentJob', { jobId },
    );
  }

  retryJob(jobId: string): Promise<{ jobId: string; dispatchStatus: string; generation: number }> {
    return this.firebase.call<{ jobId: string }, { jobId: string; dispatchStatus: string; generation: number }>(
      'retryPersistentJob', { jobId },
    );
  }
}
