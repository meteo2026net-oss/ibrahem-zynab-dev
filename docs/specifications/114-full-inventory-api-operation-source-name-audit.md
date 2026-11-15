# 114 — تدقيق المطابقة المصدرية الشامل لأسماء عمليات API الـ201

**الحالة:** FULL-INVENTORY SOURCE NAME AUDIT / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المصدر:** النص المستخرج كاملاً من DOCX الرئيسي في Artifact `14140098015`؛ 18,350 سطراً؛ locators 1-based.  
**سجل العمليات:** الوثيقة 97.

## 1. النتيجة التنفيذية

أُعيد فحص جميع أسماء العمليات الـ201 في سجل 97 بمطابقة حرفية حساسة لحالة الأحرف على النص المستخرج كاملاً. كما فُصلت المطابقة اللفظية عن تطابق المركز/المعنى، ولم تُعتبر الأسماء القريبة متطابقة.

| التصنيف | العدد |
|---|---:|
| EXACT-NAME lexical matches | 11 |
| منها تعارض مركز مصدر/سجل | 1 |
| SEMANTIC-CANDIDATE غير معتمد | 7 |
| لا يوجد اسم API حرفي مطابق | 183 |
| **الإجمالي** | **201** |

النتيجة: 11 اسماً مطابقاً حرفياً، لكن أحدها `validateBuildConfiguration` موجود في نموذج C11.9 بينما سجل 97 يضعه في C05؛ لذلك لا يُعد تطابقاً سليماً في حدود المركز حتى يحسم مالكا C05/C11 التعارض. توجد 7 مرشحات دلالية من تدقيق 108، و183 اسماً لم يظهر كاسم API حرفي في النص المصدر.

## 2. المطابقات الحرفية

| Operation ID | Center in 97 | Name | DOCX source name | Source line | Center in source |
|---|---|---|---|---:|---|
| OP-C02-017 | C02 | `createDesignWorkspace` | `createDesignWorkspace` | 11257 | C02 |
| OP-C02-019 | C02 | `getDesignDocument` | `getDesignDocument` | 11258 | C02 |
| OP-C02-021 | C02 | `validateDesignDocument` | `validateDesignDocument` | 11259 | C02 |
| OP-C02-026 | C02 | `submitDesignForApproval` | `submitDesignForApproval` | 11261 | C02 |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | `getApprovedDesignVersion` | 11262 | C02 |
| OP-C04-049 | C04 | `validateSchema` | `validateSchema` | 11279 | C04 |
| OP-C05-081 | C05 | `validateBuildConfiguration` | `validateBuildConfiguration` | 11331 | C11 |
| OP-C07-107 | C07 | `createTestPlan` | `createTestPlan` | 11312 | C07 |
| OP-C07-110 | C07 | `createTestRun` | `createTestRun` | 11313 | C07 |
| OP-C07-112 | C07 | `executeTestSuite` | `executeTestSuite` | 11314 | C07 |
| OP-C11-159 | C11 | `createReleaseCandidate` | `createReleaseCandidate` | 11334 | C11 |

**مطابقة المركز:** 10 من 11 متوافقة مع مركز النموذج المصدر. `OP-C05-081` تعارض مركز صريح: اسم العملية موجود في 135.9 (C11) بينما سجل 97 يسنده إلى C05. لا نقل ولا حذف ولا دمج قبل قرار موثق.

## 3. مرشحات المطابقة الدلالية — ليست مطابقة

| Operation ID | Name in 97 | Source name/description | Source line | Issue to resolve |
|---|---|---|---:|---|
| OP-C04-054 | `createMigrationPlan` | `generateMigrationPlan` | 11281 | Potentially similar; create vs generate semantics unresolved |
| OP-C07-117 | `evaluateQualityGate` | `validateQualityGate` | 11316 | Evaluation/decision vs validation may differ |
| OP-C07-120 | `exportQualityReport` | `submitQualityReport` | 11317 | Export vs submit are distinct lifecycle actions |
| OP-C11-156 | `requestBuild` | `prepareBuild / createBuildJob` | 11330 | Request vs preparation/job creation must remain distinct |
| OP-C11-162 | `deployRelease` | `executeApprovedDeployment` | 11336 | Deployment must retain approved-execution constraint |
| OP-C11-164 | `runPostDeployChecks` | `post-deployment verification description` | 11345 | Concept described; no source API name in this passage |
| OP-C11-165 | `requestRollback` | `rollbackDeployment` | 11337 | Rollback request vs execution must remain distinct |

تظل هذه العلاقات مرشحات فقط. لا يجوز تحويل request إلى execute، أو export إلى submit، أو validation إلى evaluation، أو دمج lifecycle steps بسبب تشابه لغوي.

## 4. التغطية حسب المركز

| Center | Total | Exact lexical | Semantic candidate | No exact name |
|---|---:|---:|---:|---:|
| C01 | 16 | 0 | 0 | 16 |
| C02 | 12 | 5 | 0 | 7 |
| C03 | 11 | 0 | 0 | 11 |
| C04 | 23 | 1 | 1 | 21 |
| C05 | 23 | 1 | 0 | 22 |
| C06 | 21 | 0 | 0 | 21 |
| C07 | 14 | 3 | 2 | 9 |
| C08 | 11 | 0 | 0 | 11 |
| C09 | 12 | 0 | 0 | 12 |
| C10 | 11 | 0 | 0 | 11 |
| C11 | 12 | 1 | 4 | 7 |
| C12 | 12 | 0 | 0 | 12 |
| C13 | 10 | 0 | 0 | 10 |
| C14 | 13 | 0 | 0 | 13 |

## 5. قائمة العمليات الـ201 وحالة كل اسم

| Operation ID | Center | Inventory name | Source-name status | Source name / description | DOCX line | Notes |
|---|---|---|---|---|---:|---|
| OP-C01-001 | C01 | `createProject` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-002 | C01 | `updateProjectProfile` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-003 | C01 | `saveIdeaVersion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-004 | C01 | `attachProjectReference` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-005 | C01 | `startDomainAnalysis` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-006 | C01 | `getAnalysisResult` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-007 | C01 | `upsertPersona` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-008 | C01 | `createRequirement` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-009 | C01 | `reviseRequirement` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-010 | C01 | `recordNFR` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-011 | C01 | `analyzeGaps` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-012 | C01 | `answerClarification` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-014 | C01 | `createEstimate` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-015 | C01 | `submitRequirementBaseline` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C01-016 | C01 | `decideRequirementBaseline` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-017 | C02 | `createDesignWorkspace` | EXACT-NAME | createDesignWorkspace | 11257 | — |
| OP-C02-018 | C02 | `createDesignDocument` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-019 | C02 | `getDesignDocument` | EXACT-NAME | getDesignDocument | 11258 | — |
| OP-C02-020 | C02 | `saveDesignChangeSet` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-021 | C02 | `validateDesignDocument` | EXACT-NAME | validateDesignDocument | 11259 | — |
| OP-C02-022 | C02 | `createComponentDefinition` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-023 | C02 | `createDesignPreview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-024 | C02 | `requestDesignAIProposal` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-025 | C02 | `applyDesignProposal` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-026 | C02 | `submitDesignForApproval` | EXACT-NAME | submitDesignForApproval | 11261 | — |
| OP-C02-027 | C02 | `decideDesignApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | EXACT-NAME | getApprovedDesignVersion | 11262 | — |
| OP-C03-029 | C03 | `createPage` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-030 | C03 | `renamePage` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-031 | C03 | `createRoute` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-032 | C03 | `updateNavigationGraph` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-033 | C03 | `validateNavigationGraph` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-034 | C03 | `analyzeReachability` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-035 | C03 | `createDeepLinkRule` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-036 | C03 | `simulateUserJourney` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-037 | C03 | `submitNavigationForApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-038 | C03 | `decideNavigationApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C03-039 | C03 | `getApprovedNavigation` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-040 | C04 | `registerDataSource` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-041 | C04 | `validateDataSource` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-042 | C04 | `createSchema` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-043 | C04 | `addEntity` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-044 | C04 | `addField` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-045 | C04 | `defineRelation` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-046 | C04 | `defineConstraint` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-047 | C04 | `defineIndex` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-048 | C04 | `defineRowPolicy` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-049 | C04 | `validateSchema` | EXACT-NAME | validateSchema | 11279 | — |
| OP-C04-050 | C04 | `previewQuery` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-051 | C04 | `createDataBinding` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-052 | C04 | `generateTestFixtures` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-053 | C04 | `configureSync` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-054 | C04 | `createMigrationPlan` | SEMANTIC-CANDIDATE | generateMigrationPlan | 11281 | Potentially similar; create vs generate semantics unresolved |
| OP-C04-055 | C04 | `dryRunMigration` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-056 | C04 | `applyMigration` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-057 | C04 | `createRollbackPlan` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-058 | C04 | `startBackup` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-059 | C04 | `requestRestore` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-060 | C04 | `verifyRestore` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-061 | C04 | `submitSchemaForApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C04-062 | C04 | `decideSchemaApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-064 | C05 | `listProjectFiles` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-065 | C05 | `readProjectFile` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-066 | C05 | `proposeFileChange` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-068 | C05 | `generateIR` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-069 | C05 | `validateIR` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-070 | C05 | `generateCodeFromDesign` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-072 | C05 | `getGenerationStatus` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-073 | C05 | `analyzeDependencies` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-075 | C05 | `runCodeDiagnostics` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-077 | C05 | `compareDesignAndCode` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-078 | C05 | `createSandboxRun` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-079 | C05 | `cancelSandboxRun` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-080 | C05 | `createBuildPreparation` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-081 | C05 | `validateBuildConfiguration` | EXACT-NAME / CENTER-CONFLICT | validateBuildConfiguration | 11331 | — |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-083 | C05 | `requestCodeReview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-084 | C05 | `approveCodeBaseline` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C05-085 | C05 | `prepareCodeHandoff` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-086 | C06 | `registerAgent` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-087 | C06 | `createAgentVersion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-088 | C06 | `registerSkill` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-089 | C06 | `registerToolManifest` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-090 | C06 | `requestToolGrant` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-091 | C06 | `createAITask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-092 | C06 | `planAITask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-093 | C06 | `assignAgents` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-094 | C06 | `executeTaskStep` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-095 | C06 | `pauseTask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-096 | C06 | `resumeTask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-097 | C06 | `cancelTask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-098 | C06 | `submitAgentOutput` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-099 | C06 | `reviewAgentOutput` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-100 | C06 | `resolveAgentConflict` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-101 | C06 | `evaluateAgent` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-102 | C06 | `registerAIProvider` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-103 | C06 | `validateAIProvider` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-104 | C06 | `selectModelForTask` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-105 | C06 | `recordProviderUsage` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C06-106 | C06 | `disableAIProvider` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-107 | C07 | `createTestPlan` | EXACT-NAME | createTestPlan | 11312 | — |
| OP-C07-108 | C07 | `createTestCase` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-109 | C07 | `linkTestToRequirement` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-110 | C07 | `createTestRun` | EXACT-NAME | createTestRun | 11313 | — |
| OP-C07-111 | C07 | `executeTestCase` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-112 | C07 | `executeTestSuite` | EXACT-NAME | executeTestSuite | 11314 | — |
| OP-C07-113 | C07 | `recordDefect` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-114 | C07 | `transitionDefect` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-115 | C07 | `retestDefect` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-116 | C07 | `calculateCoverage` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-117 | C07 | `evaluateQualityGate` | SEMANTIC-CANDIDATE | validateQualityGate | 11316 | Evaluation/decision vs validation may differ |
| OP-C07-118 | C07 | `requestQualityWaiver` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-119 | C07 | `approveTestResults` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C07-120 | C07 | `exportQualityReport` | SEMANTIC-CANDIDATE | submitQualityReport | 11317 | Export vs submit are distinct lifecycle actions |
| OP-C08-121 | C08 | `createAssetRecord` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-122 | C08 | `requestUpload` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-123 | C08 | `finalizeUpload` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-124 | C08 | `inspectAsset` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-125 | C08 | `createAssetVersion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-126 | C08 | `linkAsset` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-127 | C08 | `requestTransformation` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-128 | C08 | `revokeAssetLink` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-129 | C08 | `archiveAsset` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-130 | C08 | `requestExport` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C08-131 | C08 | `requestDeletion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-132 | C09 | `registerIntegration` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-133 | C09 | `configureIntegration` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-134 | C09 | `beginAuthorization` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-135 | C09 | `completeAuthorization` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-136 | C09 | `validateConnection` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-137 | C09 | `testMapping` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-138 | C09 | `startSync` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-139 | C09 | `receiveWebhook` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-140 | C09 | `pauseIntegration` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-141 | C09 | `revokeConnection` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-142 | C09 | `rotateSecretReference` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C09-143 | C09 | `getIntegrationStatus` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-144 | C10 | `evaluatePolicy` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-145 | C10 | `registerSecurityFinding` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-146 | C10 | `createThreatModel` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-147 | C10 | `assessPrivacyImpact` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-148 | C10 | `requestPolicyChange` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-149 | C10 | `decideSecurityException` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-150 | C10 | `suspendAgentCapability` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-151 | C10 | `reviewAgentRun` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-152 | C10 | `recordIncident` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-153 | C10 | `closeFinding` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C10-154 | C10 | `exportAuditEvidence` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-155 | C11 | `validateBuildProfile` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-156 | C11 | `requestBuild` | SEMANTIC-CANDIDATE | prepareBuild / createBuildJob | 11330 | Request vs preparation/job creation must remain distinct |
| OP-C11-157 | C11 | `getBuildResult` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-158 | C11 | `attestArtifact` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-159 | C11 | `createReleaseCandidate` | EXACT-NAME | createReleaseCandidate | 11334 | — |
| OP-C11-160 | C11 | `submitReleaseForApproval` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-161 | C11 | `approveRelease` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-162 | C11 | `deployRelease` | SEMANTIC-CANDIDATE | executeApprovedDeployment | 11336 | Deployment must retain approved-execution constraint |
| OP-C11-163 | C11 | `promoteEnvironment` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C11-164 | C11 | `runPostDeployChecks` | SEMANTIC-CANDIDATE | post-deployment verification description | 11345 | Concept described; no source API name in this passage |
| OP-C11-165 | C11 | `requestRollback` | SEMANTIC-CANDIDATE | rollbackDeployment | 11337 | Rollback request vs execution must remain distinct |
| OP-C11-166 | C11 | `getReleaseStatus` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-167 | C12 | `createKnowledgeDocument` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-168 | C12 | `createDocumentVersion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-169 | C12 | `linkSourceEvidence` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-170 | C12 | `generateDocumentationDraft` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-171 | C12 | `submitDocumentReview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-172 | C12 | `decideDocumentReview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-173 | C12 | `publishKnowledgeDocument` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-174 | C12 | `searchKnowledge` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-175 | C12 | `compareDocumentVersions` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-176 | C12 | `exportKnowledgeBundle` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-177 | C12 | `archiveKnowledgeDocument` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C12-178 | C12 | `reconcileDocumentation` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-179 | C13 | `getEffectiveSettings` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-180 | C13 | `validateSettingValue` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-181 | C13 | `updateUserPreference` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-182 | C13 | `requestScopedSettingChange` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-183 | C13 | `decideSettingChange` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-184 | C13 | `applyOwnerSettingChange` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-185 | C13 | `compareSettingVersions` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-186 | C13 | `rollbackSettingVersion` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-187 | C13 | `exportNonSecretSettings` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C13-188 | C13 | `diagnoseInheritance` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-189 | C14 | `getDashboardOverview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-190 | C14 | `getCenterSummary` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-191 | C14 | `getMyTasksOverview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-192 | C14 | `getApprovalQueue` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-193 | C14 | `getQualityOverview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-194 | C14 | `getSecurityOverview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-195 | C14 | `getIntegrationHealth` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-196 | C14 | `getReleaseOverview` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-197 | C14 | `getOperationalHealth` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-198 | C14 | `saveDashboardLayout` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-199 | C14 | `pinWidget` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-200 | C14 | `exportDashboardReport` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |
| OP-C14-201 | C14 | `invalidateProjection` | NO-EXACT-NAME-FOUND | — | — | No exact case-sensitive API-name occurrence identified in extracted DOCX |

## 6. تصحيح النطاق السابق

- الوثيقة 108 فحصت 95 عملية من C04/C06/C07/C09/C11/C14، ووجدت 5 مطابقات حرفية و7 مرشحات دلالية و83 اسماً غير حرفي.
- العمليات الـ106 الأخرى كانت خارج ذلك النطاق، لا يعني ذلك أنها لم تُفحص قط في DOCX.
- هذه الوثيقة 114 أكملت المقارنة لكل 201 عملية: ظهرت 6 مطابقات حرفية إضافية ضمن العمليات الـ106 السابقة خارج نطاق 108؛ خمس منها في C02 وواحدة في C05 مع تعارض المركز الموضح أعلاه.
- الحصيلة الشاملة بعد توحيد النطاق: 11 exact lexical matches، و7 semantic candidates، و183 no-exact-name results.
- هذه الأرقام تخص أسماء العمليات في سجل 97 مقابل نص DOCX المستخرج، ولا تثبت اكتمال المتطلبات أو صحة العقود أو اعتماد المالك.

## 7. قرارات المالك المطلوبة

1. اعتماد أو تصحيح أسماء C02 الخمسة بعد مراجعة سياق 135.5 وحدود المركز.
2. حسم تعارض `validateBuildConfiguration`: هل تبقى عملية C05، أم تنقل إلى C11، أم تفصل إلى عمليتين بعقدين مختلفين؟
3. مراجعة المرشحات الدلالية السبعة، خصوصاً الفصل بين request/prepare/create/execute/verify وexport/submit.
4. تحديد مصدر معتمد لأسماء عمليات C06 وC09 وC14؛ لا يُخترع نص مصدر مفقود.
5. توثيق أي اسم مشتق retained مع owner decision وrationale وsource locator، أو استبداله باسم مصدر بعد موافقة المالك مع الحفاظ على التاريخ.
6. استكمال schema/version/authz/error/idempotency/test links لكل عملية بعد اعتماد الاسم والحدود.

## 8. الحالة والحدود

- Source-name coverage: **201/201 checked**.
- Exact lexical: **11/201**.
- Exact lexical with center conflict: **1**.
- Exact lexical and center-aligned: **10**.
- Semantic candidates: **7**, not approved.
- No exact source API name: **183**.
- Owner sign-off: **NOT RECEIVED / NOT VERIFIED**.
- API/schema implementation: **NOT STARTED BY THIS DOCUMENTATION WORKSTREAM**.
- Tests/CI: **NOT RUN BY THIS AUDIT**.

**الحكم:** أُغلق فحص المطابقة الحرفية لنطاق أسماء العمليات الـ201 فقط. لم تُغلق ملكية العمليات أو دلالتها أو عقودها أو اختبارات قبولها، ولا تمثل هذه الوثيقة موافقة على بدء البرمجة.