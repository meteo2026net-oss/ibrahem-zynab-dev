"use strict";

const test = require("node:test");
const assert = require("node:assert/strict");
const { JobControl } = require("../job-control");

test("allows result publication while lease is valid and no cancellation exists", () => {
  const control = new JobControl();
  assert.equal(control.canPublishResult, true);
  assert.equal(control.terminalState, null);
});

test("records cancellation returned by a lease heartbeat", () => {
  const control = new JobControl();
  control.observeHeartbeat({ cancellationRequested: true });
  assert.equal(control.canPublishResult, false);
  assert.equal(control.terminalState, "CANCELLED");
});

test("does not clear cancellation on a later heartbeat", () => {
  const control = new JobControl();
  control.observeHeartbeat({ cancellationRequested: true });
  control.observeHeartbeat({ cancellationRequested: false });
  assert.equal(control.terminalState, "CANCELLED");
});

test("treats heartbeat failure as lease loss and blocks result publication", () => {
  const control = new JobControl();
  control.observeHeartbeatFailure();
  assert.equal(control.canPublishResult, false);
  assert.equal(control.terminalState, "LEASE_LOST");
});

test("lease loss takes precedence if cancellation and lease loss both occur", () => {
  const control = new JobControl();
  control.observeHeartbeat({ cancellationRequested: true });
  control.observeHeartbeatFailure();
  assert.equal(control.terminalState, "LEASE_LOST");
});
