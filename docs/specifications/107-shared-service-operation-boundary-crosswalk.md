# 107 — مصفوفة حدود الخدمات المشتركة على مستوى عمليات API

**الحالة:** OPERATION-LEVEL BOUNDARY CROSSWALK / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** 32، 36، 72، 82، 85–93، 95، 97، 101، 103، 105، 106.

## 1. الغرض والنطاق

تفكك هذه الوثيقة **95 عملية مرشحة** من سجل 97 للمراكز C04 وC06 وC07 وC09 وC11 وC14، وهي المراكز التي تتقاطع عملياتها مباشرة مع SH-08/SH-13/SH-14 أو SH-15. لا تغير أسماء العمليات أو أنواعها المرشحة، ولا تنشئ API paths أو schemas.

| المركز | عدد العمليات |
|---|---:|
| C04 | 23 |
| C06 | 21 |
| C07 | 14 |
| C09 | 12 |
| C11 | 12 |
| C14 | 13 |
| **الإجمالي** | **95** |

**قاعدة القراءة:** حقل الخدمة المشتركة يصف حد ملكية مرشحاً، وليس تفويضاً نهائياً. العمليات التي لا يظهر لها تقاطع مباشر لا تُسند قسراً إلى SH. كل Exact Test ID ما زال TBD.

## 2. المصفوفة المفردة

| Operation ID | Operation | Center | Candidate kind | Shared-service boundary candidate | qualified OWN reference | test evidence |
|---|---|---|---|---|---|---|
| OP-C04-040 | `registerDataSource` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-041 | `validateDataSource` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-042 | `createSchema` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-043 | `addEntity` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-044 | `addField` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-045 | `defineRelation` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-046 | `defineConstraint` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-047 | `defineIndex` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-048 | `defineRowPolicy` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-049 | `validateSchema` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-050 | `previewQuery` | C04 | Query candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-051 | `createDataBinding` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-052 | `generateTestFixtures` | C04 | Job/Command candidate | SH-08 execution attempt؛ C04 يملك نتيجة الاختبار/الترحيل الدلالية | OWN72-07 / OWN36-05 | TASK-92 family candidate |
| OP-C04-053 | `configureSync` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-054 | `createMigrationPlan` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-055 | `dryRunMigration` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-056 | `applyMigration` | C04 | Job/Command candidate | SH-08 execution attempt؛ C04 يملك نتيجة الاختبار/الترحيل الدلالية | OWN72-07 / OWN36-05 | TASK-92 family candidate |
| OP-C04-057 | `createRollbackPlan` | C04 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-058 | `startBackup` | C04 | Job/Command candidate | SH-15 backup/restore job evidence؛ C04 يملك طلب المجال والتحقق الدلالي | OWN72-07 / OWN36-05 | BAK-87 family candidate؛ exact TBD |
| OP-C04-059 | `requestRestore` | C04 | Command candidate | SH-15 backup/restore job evidence؛ C04 يملك طلب المجال والتحقق الدلالي | OWN72-07 / OWN36-05 | BAK-87 family candidate؛ exact TBD |
| OP-C04-060 | `verifyRestore` | C04 | Command candidate | C04 semantic restore verification؛ SH-15 لا يملك قرار صحة البيانات | OWN72-07 / OWN36-05 | DATA-01..18 family candidate؛ exact TBD |
| OP-C04-061 | `submitSchemaForApproval` | C04 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C04-062 | `decideSchemaApproval` | C04 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-07 / OWN36-05 | Exact Test ID TBD |
| OP-C06-086 | `registerAgent` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-087 | `createAgentVersion` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-088 | `registerSkill` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-089 | `registerToolManifest` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-090 | `requestToolGrant` | C06 | Command candidate | SH-02 authorization + SH-03 approval؛ SH-08 لا ينشئ grant | OWN72-10 | AUTH/approval family candidate; exact TBD |
| OP-C06-091 | `createAITask` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-092 | `planAITask` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-093 | `assignAgents` | C06 | Command candidate | SH-08 assignment/execution/cancel attempts؛ C06 يملك AITask state/result | OWN72-10 | TASK-92 family candidate |
| OP-C06-094 | `executeTaskStep` | C06 | Job/Command candidate | SH-08 assignment/execution/cancel attempts؛ C06 يملك AITask state/result | OWN72-10 | TASK-92 family candidate |
| OP-C06-095 | `pauseTask` | C06 | Job/Command candidate | SH-08 assignment/execution/cancel attempts؛ C06 يملك AITask state/result | OWN72-10 | TASK-92 family candidate |
| OP-C06-096 | `resumeTask` | C06 | Job/Command candidate | SH-08 assignment/execution/cancel attempts؛ C06 يملك AITask state/result | OWN72-10 | TASK-92 family candidate |
| OP-C06-097 | `cancelTask` | C06 | Job/Command candidate | SH-08 assignment/execution/cancel attempts؛ C06 يملك AITask state/result | OWN72-10 | TASK-92 family candidate |
| OP-C06-098 | `submitAgentOutput` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-099 | `reviewAgentOutput` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-100 | `resolveAgentConflict` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-101 | `evaluateAgent` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-102 | `registerAIProvider` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-103 | `validateAIProvider` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-104 | `selectModelForTask` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-105 | `recordProviderUsage` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C06-106 | `disableAIProvider` | C06 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-10 | Exact Test ID TBD |
| OP-C07-107 | `createTestPlan` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-108 | `createTestCase` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-109 | `linkTestToRequirement` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-110 | `createTestRun` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-111 | `executeTestCase` | C07 | Job/Command candidate | SH-08 test execution attempt؛ C07 يملك test run/result/quality gate | OWN36-03 / OWN72-08 | TASK-92 family candidate; exact QA test TBD |
| OP-C07-112 | `executeTestSuite` | C07 | Job/Command candidate | SH-08 test execution attempt؛ C07 يملك test run/result/quality gate | OWN36-03 / OWN72-08 | TASK-92 family candidate; exact QA test TBD |
| OP-C07-113 | `recordDefect` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-114 | `transitionDefect` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-115 | `retestDefect` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-116 | `calculateCoverage` | C07 | Query candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-117 | `evaluateQualityGate` | C07 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-118 | `requestQualityWaiver` | C07 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-119 | `approveTestResults` | C07 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C07-120 | `exportQualityReport` | C07 | Query candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN36-03 / OWN72-08 | Exact Test ID TBD |
| OP-C09-132 | `registerIntegration` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-133 | `configureIntegration` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-134 | `beginAuthorization` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-135 | `completeAuthorization` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-136 | `validateConnection` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-137 | `testMapping` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-138 | `startSync` | C09 | Job/Command candidate | SH-08 sync execution attempt + SH-13 connector runtime; C09 owns sync domain outcome | OWN72-02 | INT-91 + TASK-92 family candidates |
| OP-C09-139 | `receiveWebhook` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-140 | `pauseIntegration` | C09 | Job/Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-141 | `revokeConnection` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-142 | `rotateSecretReference` | C09 | Command candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C09-143 | `getIntegrationStatus` | C09 | Query candidate | SH-13 connector runtime/connection refs; C09 owns integration definition/mapping/acceptance | OWN72-02 | INT-91 family candidate |
| OP-C11-155 | `validateBuildProfile` | C11 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-06 / OWN36-06 | Exact Test ID TBD |
| OP-C11-156 | `requestBuild` | C11 | Command candidate | SH-08 execution attempt; C11 owns build/release/deployment/rollback result | OWN72-06 / OWN36-06 | TASK-92 family candidate |
| OP-C11-157 | `getBuildResult` | C11 | Query candidate | C11 domain result; SH-14 supplies operational telemetry only | OWN72-06 / OWN36-06 | MON-93 candidate for health projection only |
| OP-C11-158 | `attestArtifact` | C11 | Command candidate | C11 artifact attestation; SH-10 version metadata; artifact registry/signing boundary ART-95 OPEN | OWN72-06 / OWN36-06 | VER-85/ART-95 candidates; exact TBD |
| OP-C11-159 | `createReleaseCandidate` | C11 | Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-06 / OWN36-06 | Exact Test ID TBD |
| OP-C11-160 | `submitReleaseForApproval` | C11 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-06 / OWN36-06 | Exact Test ID TBD |
| OP-C11-161 | `approveRelease` | C11 | Approval/Command candidate | لا توجد ملكية SH-08/13/14 مثبتة لهذه العملية؛ المركز يحتفظ بالمعنى | OWN72-06 / OWN36-06 | Exact Test ID TBD |
| OP-C11-162 | `deployRelease` | C11 | Job/Command candidate | SH-08 execution attempt; C11 owns build/release/deployment/rollback result | OWN72-06 / OWN36-06 | TASK-92 family candidate |
| OP-C11-163 | `promoteEnvironment` | C11 | Job/Command candidate | SH-08 execution attempt; C11 owns build/release/deployment/rollback result | OWN72-06 / OWN36-06 | TASK-92 family candidate |
| OP-C11-164 | `runPostDeployChecks` | C11 | Job/Command candidate | SH-08 execution attempt; C11 owns build/release/deployment/rollback result | OWN72-06 / OWN36-06 | TASK-92 family candidate |
| OP-C11-165 | `requestRollback` | C11 | Command candidate | SH-08 execution attempt; C11 owns build/release/deployment/rollback result | OWN72-06 / OWN36-06 | TASK-92 family candidate |
| OP-C11-166 | `getReleaseStatus` | C11 | Query candidate | C11 domain result; SH-14 supplies operational telemetry only | OWN72-06 / OWN36-06 | MON-93 candidate for health projection only |
| OP-C14-189 | `getDashboardOverview` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-190 | `getCenterSummary` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-191 | `getMyTasksOverview` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-192 | `getApprovalQueue` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-193 | `getQualityOverview` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-194 | `getSecurityOverview` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-195 | `getIntegrationHealth` | C14 | Query candidate | C14 read projection from SH-14/SH-13 and source owners; revalidate ACL/freshness | OWN36-10 / OWN72-09 | MON-93 family candidate |
| OP-C14-196 | `getReleaseOverview` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-197 | `getOperationalHealth` | C14 | Query candidate | C14 read projection from SH-14/SH-13 and source owners; revalidate ACL/freshness | OWN36-10 / OWN72-09 | MON-93 family candidate |
| OP-C14-198 | `saveDashboardLayout` | C14 | Command candidate | C14 owns layout/preferences only; no source metric writes | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-199 | `pinWidget` | C14 | Command candidate | C14 owns layout/preferences only; no source metric writes | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-200 | `exportDashboardReport` | C14 | Query candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |
| OP-C14-201 | `invalidateProjection` | C14 | Command candidate | C14 read projection; underlying data/metrics remain with domain SoR | OWN36-10 / OWN72-09 | DASH-INT-01..10 candidate; exact TBD |

## 3. قواعد الحدود

### SH-08
يملك execution job/attempt/queue/worker/schedule/progress/cancel/retry metadata فقط. المركز الطالب يملك Domain Job ونتيجته الدلالية. لا يُعلن نجاح المجال بمجرد نجاح محاولة التنفيذ.

### SH-13
يملك connector manifest/runtime/connection lifecycle/health references. C09 يملك غرض التكامل والخرائط واتجاه المزامنة وقواعد التعارض وقبول أثر البيانات. قيمة السر لدى Secret Manager المعتمد فقط.

### SH-14
يملك health/metrics/traces/alerts/incident telemetry. لا يملك نتائج الاختبارات أو جودة البيانات أو حالة إصدار المجال. C14 مستهلك إسقاط قراءة، لا مالك مؤشر.

### SH-15
يملك سجل تشغيل النسخ والاستعادة والأدلة التقنية. C04/C11 يملكان قرار صلاحية الاستعادة والتحقق الدلالي من بياناتهما/إصداراتهما.

## 4. قواعد الاختبارات والتتبع

- TASK-92 وINT-91 وMON-93 عائلات اختبار مرشحة لحدود الخدمات؛ لا تمثل تغطية Exact لعملية مفردة.
- DATA-01..18 وDASH-INT-01..10 وعائلات أخرى في 105 لا تُربط بعملية إلا بعد مراجعة السيناريو وtest oracle.
- لا تستخدم DRAFT-AT/Cxx-AT كمعرفات مصدرية؛ انظر 105.
- لا ترفع حالة عملية إلى APPROVED أو IMPLEMENTED أو EXECUTED دون قرار مالك ودليل اختبار.
- إذا كانت العملية Query/Projection، يجب تسجيل المصدر ووقت اللقطة وحداثتها وإعادة التحقق من ACL.
- لكل Command/Job يلزم actor/scope/policy/approval refs، وexpectedVersion أو idempotency عند الاقتضاء، مع timeout/retry/error semantics.

## 5. قرارات الملكية المفتوحة

| القرار | النطاق | الحالة |
|---|---|---|
| OWN72-02 | C09/SH-13 | OPEN |
| OWN72-06 | C11/SH-08/10/14/15 | OPEN |
| OWN72-07 وOWN36-05 | C04 ومراكز المجال | OPEN |
| OWN72-08 وOWN36-03 | C07/SH-14 | OPEN |
| OWN72-10 | C06 وحوكمة الوكلاء | OPEN |
| OWN36-10 وOWN72-09 | C14 ومصادر المؤشرات | OPEN |
| OWN36-06 / ART-95 | إصدار metadata وسجل artifact والتوقيع/التخزين | OPEN |
| SH15-BOUNDARY-01 | كتالوج النسخ مقابل تحقق المجال | OPEN |

لا يغلق هذا الملف أياً من هذه القرارات، ولا يحل محل توقيع المالكين.

## 6. نتائج قابلة للقياس وحدودها

- عمليات مراكز النطاق المفردة التي أُعيد تصنيف حدودها: 95.
- عمليات أصبحت ذات Exact Test ID: 0 (لا دليل جديد يثبت الربط المفرد).
- عمليات أصبح لها مالك نهائي معتمد: 0 (قرارات الملكية ما زالت OPEN).
- عمليات تحولت إلى APIs منفذة: 0.
- اختبارات تشغيلية نُفذت بهذه الجولة: 0.

**بوابة التنفيذ:** هذا crosswalk مشتق فقط؛ لا ينشئ APIs أو workers أو connectors أو monitoring rules أو جداول، ولا يعدل كود التطبيق ولا يمنح إذناً ببدء البرمجة.
