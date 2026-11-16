"use strict";

/**
 * Tracks lease and cancellation signals for one worker execution.
 * A heartbeat failure is treated as lease loss: the worker must not publish
 * a result because it can no longer prove that it owns the job.
 */
class JobControl {
  constructor() {
    this.leaseLost = false;
    this.cancellationRequested = false;
  }

  observeHeartbeat(result) {
    if (result?.cancellationRequested === true) {
      this.cancellationRequested = true;
    }
  }

  observeHeartbeatFailure() {
    this.leaseLost = true;
  }

  get canPublishResult() {
    return !this.leaseLost && !this.cancellationRequested;
  }

  get terminalState() {
    if (this.leaseLost) return "LEASE_LOST";
    if (this.cancellationRequested) return "CANCELLED";
    return null;
  }
}

module.exports = { JobControl };
