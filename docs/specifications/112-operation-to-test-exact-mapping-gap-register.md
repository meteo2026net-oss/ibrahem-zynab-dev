# 112 — فجوة الربط المفرد بين عمليات API ومعرفات الاختبارات

**الحالة:** OPERATION-TO-TEST TRACEABILITY GAP REGISTER / SOURCE-AWARE / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** DOCX الرئيسي (Artifact 14140098015)، 80، 97، 104، 105، 109، 110، 111.

## 1. النتيجة التنفيذية

تم فحص حقل Exact mapping في كل صف من سجل العمليات 97، وعددها 201 عملية. جميع الصفوف الـ201 تحمل القيمة EXACT TEST ID TBD / OWNER REVIEW.

| المقياس | النتيجة |
|---|---:|
| عمليات API في سجل 97 | 201 |
| عمليات لها Exact Test ID محدد في 97 | 0 |
| عمليات بقيت Exact Test ID TBD | 201 |
| عائلات Test ID أصلية مستعادة في 105 | 8 |
| معرفات Test ID أصلية فريدة في 105 | 89 |
| روابط مفردة Operation → Test مثبتة في السجل 97 | 0 |
| عمليات ذات اختبار منفذ موثق في هذه المراجعة | 0 |

وجود 89 معرف اختبار أصلي في المصدر لا يعني أنها تغطي 201 عملية. لا يجوز إسناد AT/API/DATA/DOC/DASH-INT/KNOW-QA/KNOW-SEC/SET-INT إلى عملية بعينها دون إثبات دلالي وقرار مالك. كما أن SRC-AT معرف locator محلي لسيناريو مصدر غير مرقم، وليس Test ID أصلياً.

## 2. فجوة الربط حسب المركز

| المركز | عدد العمليات | معرفات العمليات | حالة الربط |
|---|---:|---|---|
| C01 | 16 | OP-C01-001, OP-C01-002, OP-C01-003, OP-C01-004, OP-C01-005, OP-C01-006, OP-C01-007, OP-C01-008, OP-C01-009, OP-C01-010, OP-C01-011, OP-C01-012, OP-C01-013, OP-C01-014, OP-C01-015, OP-C01-016 |
| C02 | 12 | OP-C02-017, OP-C02-018, OP-C02-019, OP-C02-020, OP-C02-021, OP-C02-022, OP-C02-023, OP-C02-024, OP-C02-025, OP-C02-026, OP-C02-027, OP-C02-028 |
| C03 | 11 | OP-C03-029, OP-C03-030, OP-C03-031, OP-C03-032, OP-C03-033, OP-C03-034, OP-C03-035, OP-C03-036, OP-C03-037, OP-C03-038, OP-C03-039 |
| C04 | 23 | OP-C04-040, OP-C04-041, OP-C04-042, OP-C04-043, OP-C04-044, OP-C04-045, OP-C04-046, OP-C04-047, OP-C04-048, OP-C04-049, OP-C04-050, OP-C04-051, OP-C04-052, OP-C04-053, OP-C04-054, OP-C04-055, OP-C04-056, OP-C04-057, OP-C04-058, OP-C04-059, OP-C04-060, OP-C04-061, OP-C04-062 |
| C05 | 23 | OP-C05-063, OP-C05-064, OP-C05-065, OP-C05-066, OP-C05-067, OP-C05-068, OP-C05-069, OP-C05-070, OP-C05-071, OP-C05-072, OP-C05-073, OP-C05-074, OP-C05-075, OP-C05-076, OP-C05-077, OP-C05-078, OP-C05-079, OP-C05-080, OP-C05-081, OP-C05-082, OP-C05-083, OP-C05-084, OP-C05-085 |
| C06 | 21 | OP-C06-086, OP-C06-087, OP-C06-088, OP-C06-089, OP-C06-090, OP-C06-091, OP-C06-092, OP-C06-093, OP-C06-094, OP-C06-095, OP-C06-096, OP-C06-097, OP-C06-098, OP-C06-099, OP-C06-100, OP-C06-101, OP-C06-102, OP-C06-103, OP-C06-104, OP-C06-105, OP-C06-106 |
| C07 | 14 | OP-C07-107, OP-C07-108, OP-C07-109, OP-C07-110, OP-C07-111, OP-C07-112, OP-C07-113, OP-C07-114, OP-C07-115, OP-C07-116, OP-C07-117, OP-C07-118, OP-C07-119, OP-C07-120 |
| C08 | 11 | OP-C08-121, OP-C08-122, OP-C08-123, OP-C08-124, OP-C08-125, OP-C08-126, OP-C08-127, OP-C08-128, OP-C08-129, OP-C08-130, OP-C08-131 |
| C09 | 12 | OP-C09-132, OP-C09-133, OP-C09-134, OP-C09-135, OP-C09-136, OP-C09-137, OP-C09-138, OP-C09-139, OP-C09-140, OP-C09-141, OP-C09-142, OP-C09-143 |
| C10 | 11 | OP-C10-144, OP-C10-145, OP-C10-146, OP-C10-147, OP-C10-148, OP-C10-149, OP-C10-150, OP-C10-151, OP-C10-152, OP-C10-153, OP-C10-154 |
| C11 | 12 | OP-C11-155, OP-C11-156, OP-C11-157, OP-C11-158, OP-C11-159, OP-C11-160, OP-C11-161, OP-C11-162, OP-C11-163, OP-C11-164, OP-C11-165, OP-C11-166 |
| C12 | 12 | OP-C12-167, OP-C12-168, OP-C12-169, OP-C12-170, OP-C12-171, OP-C12-172, OP-C12-173, OP-C12-174, OP-C12-175, OP-C12-176, OP-C12-177, OP-C12-178 |
| C13 | 10 | OP-C13-179, OP-C13-180, OP-C13-181, OP-C13-182, OP-C13-183, OP-C13-184, OP-C13-185, OP-C13-186, OP-C13-187, OP-C13-188 |
| C14 | 13 | OP-C14-189, OP-C14-190, OP-C14-191, OP-C14-192, OP-C14-193, OP-C14-194, OP-C14-195, OP-C14-196, OP-C14-197, OP-C14-198, OP-C14-199, OP-C14-200, OP-C14-201 |

**النتيجة:** لم يظهر أي ربط مفرد معتمد في 97 لأي مركز. هذه نتيجة تدقيق على سجل 97، ولا تعني عدم وجود سيناريوهات قبول نصية في فصول المصدر.

## 3. قواعد قبول الربط المستقبلي

لا يقبل ربط Operation → Test إلا إذا احتوى سجل الربط على:
1. Operation ID واحد أو مجموعة مبررة مع بيان حدود التجميع.
2. Test ID أصلي من DOCX مع locator دقيق، أو معرف اختبار جديد موسوم PROPOSED وبقرار اعتماد صريح.
3. Test intent وpreconditions وinput/fixture وexpected result/test oracle.
4. إثبات أن الاختبار يغطي السلوك المحدد للعملية، لا مجرد مركزها.
5. اختبار إيجابي واختبارات سلبية للأذونات، tenant isolation، invalid state، idempotency/replay، version conflicts، وfailure paths عندما تنطبق.
6. Qualified OWN reference ومالك يراجع الدلالة وحدود المجال والخدمة المشتركة.
7. حالة تنفيذ منفصلة تماماً عن حالة تصميم الاختبار. لا تستخدم EXECUTED/PASSED دون run ID وcommit/build وenvironment وartifact/evidence.

## 4. ضوابط معرفات المصدر

- AT-01..AT-15: اختبارات قبول معمارية عامة؛ لا تسند تلقائياً إلى كل عملية.
- API-01..API-10: اختبارات قبول عقود اتصال؛ يلزم مطابقة موضوعية لكل عملية.
- DATA-01..DATA-18: اختبارات نموذج بيانات؛ لا تعادل اختبار API.
- DOC-01..DOC-14: اختبارات قبول الوثيقة؛ ليست اختبارات تشغيل المنتج.
- DASH-INT-01..10: تكامل لوحة التحكم؛ لا تعمم على خدمات أخرى.
- KNOW-QA-01..06 وKNOW-SEC-01..06: اختبارات توثيق المعرفة والجودة/الأمن؛ لا تساوي اختباراً وظيفياً للمنتج.
- SET-INT-01..10: تكامل الإعدادات؛ يلزم ربط دلالي مع عمليات C13.
- SRC-AT-Cxx-nn: local locator فقط لسيناريو مصدر غير مرقم.
- DRAFT-AT-* وCxx-AT-* وEVT-96-T*: معرفات مشتقة/مقترحة وليست IDs أصلية مثبتة في DOCX وفق 105.

## 5. سجل فجوة الربط المفرد — العمليات الـ201

| Operation ID | Center | Operation name | Exact Test ID | Owner review | Mapping state |
|---|---|---|---|---|---|
| OP-C01-001 | C01 | `createProject` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-002 | C01 | `updateProjectProfile` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-003 | C01 | `saveIdeaVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-004 | C01 | `attachProjectReference` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-005 | C01 | `startDomainAnalysis` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-006 | C01 | `getAnalysisResult` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-007 | C01 | `upsertPersona` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-008 | C01 | `createRequirement` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-009 | C01 | `reviseRequirement` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-010 | C01 | `recordNFR` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-011 | C01 | `analyzeGaps` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-012 | C01 | `answerClarification` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-014 | C01 | `createEstimate` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-015 | C01 | `submitRequirementBaseline` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C01-016 | C01 | `decideRequirementBaseline` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-017 | C02 | `createDesignWorkspace` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-018 | C02 | `createDesignDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-019 | C02 | `getDesignDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-020 | C02 | `saveDesignChangeSet` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-021 | C02 | `validateDesignDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-022 | C02 | `createComponentDefinition` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-023 | C02 | `createDesignPreview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-024 | C02 | `requestDesignAIProposal` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-025 | C02 | `applyDesignProposal` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-026 | C02 | `submitDesignForApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-027 | C02 | `decideDesignApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-029 | C03 | `createPage` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-030 | C03 | `renamePage` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-031 | C03 | `createRoute` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-032 | C03 | `updateNavigationGraph` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-033 | C03 | `validateNavigationGraph` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-034 | C03 | `analyzeReachability` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-035 | C03 | `createDeepLinkRule` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-036 | C03 | `simulateUserJourney` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-037 | C03 | `submitNavigationForApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-038 | C03 | `decideNavigationApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C03-039 | C03 | `getApprovedNavigation` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-040 | C04 | `registerDataSource` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-041 | C04 | `validateDataSource` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-042 | C04 | `createSchema` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-043 | C04 | `addEntity` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-044 | C04 | `addField` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-045 | C04 | `defineRelation` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-046 | C04 | `defineConstraint` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-047 | C04 | `defineIndex` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-048 | C04 | `defineRowPolicy` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-049 | C04 | `validateSchema` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-050 | C04 | `previewQuery` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-051 | C04 | `createDataBinding` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-052 | C04 | `generateTestFixtures` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-053 | C04 | `configureSync` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-054 | C04 | `createMigrationPlan` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-055 | C04 | `dryRunMigration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-056 | C04 | `applyMigration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-057 | C04 | `createRollbackPlan` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-058 | C04 | `startBackup` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-059 | C04 | `requestRestore` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-060 | C04 | `verifyRestore` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-061 | C04 | `submitSchemaForApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C04-062 | C04 | `decideSchemaApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-064 | C05 | `listProjectFiles` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-065 | C05 | `readProjectFile` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-066 | C05 | `proposeFileChange` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-068 | C05 | `generateIR` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-069 | C05 | `validateIR` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-070 | C05 | `generateCodeFromDesign` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-072 | C05 | `getGenerationStatus` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-073 | C05 | `analyzeDependencies` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-075 | C05 | `runCodeDiagnostics` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-077 | C05 | `compareDesignAndCode` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-078 | C05 | `createSandboxRun` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-079 | C05 | `cancelSandboxRun` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-080 | C05 | `createBuildPreparation` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-081 | C05 | `validateBuildConfiguration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-083 | C05 | `requestCodeReview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-084 | C05 | `approveCodeBaseline` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C05-085 | C05 | `prepareCodeHandoff` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-086 | C06 | `registerAgent` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-087 | C06 | `createAgentVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-088 | C06 | `registerSkill` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-089 | C06 | `registerToolManifest` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-090 | C06 | `requestToolGrant` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-091 | C06 | `createAITask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-092 | C06 | `planAITask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-093 | C06 | `assignAgents` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-094 | C06 | `executeTaskStep` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-095 | C06 | `pauseTask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-096 | C06 | `resumeTask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-097 | C06 | `cancelTask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-098 | C06 | `submitAgentOutput` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-099 | C06 | `reviewAgentOutput` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-100 | C06 | `resolveAgentConflict` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-101 | C06 | `evaluateAgent` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-102 | C06 | `registerAIProvider` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-103 | C06 | `validateAIProvider` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-104 | C06 | `selectModelForTask` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-105 | C06 | `recordProviderUsage` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C06-106 | C06 | `disableAIProvider` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-107 | C07 | `createTestPlan` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-108 | C07 | `createTestCase` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-109 | C07 | `linkTestToRequirement` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-110 | C07 | `createTestRun` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-111 | C07 | `executeTestCase` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-112 | C07 | `executeTestSuite` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-113 | C07 | `recordDefect` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-114 | C07 | `transitionDefect` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-115 | C07 | `retestDefect` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-116 | C07 | `calculateCoverage` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-117 | C07 | `evaluateQualityGate` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-118 | C07 | `requestQualityWaiver` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-119 | C07 | `approveTestResults` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C07-120 | C07 | `exportQualityReport` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-121 | C08 | `createAssetRecord` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-122 | C08 | `requestUpload` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-123 | C08 | `finalizeUpload` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-124 | C08 | `inspectAsset` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-125 | C08 | `createAssetVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-126 | C08 | `linkAsset` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-127 | C08 | `requestTransformation` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-128 | C08 | `revokeAssetLink` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-129 | C08 | `archiveAsset` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-130 | C08 | `requestExport` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C08-131 | C08 | `requestDeletion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-132 | C09 | `registerIntegration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-133 | C09 | `configureIntegration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-134 | C09 | `beginAuthorization` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-135 | C09 | `completeAuthorization` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-136 | C09 | `validateConnection` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-137 | C09 | `testMapping` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-138 | C09 | `startSync` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-139 | C09 | `receiveWebhook` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-140 | C09 | `pauseIntegration` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-141 | C09 | `revokeConnection` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-142 | C09 | `rotateSecretReference` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C09-143 | C09 | `getIntegrationStatus` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-144 | C10 | `evaluatePolicy` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-145 | C10 | `registerSecurityFinding` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-146 | C10 | `createThreatModel` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-147 | C10 | `assessPrivacyImpact` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-148 | C10 | `requestPolicyChange` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-149 | C10 | `decideSecurityException` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-150 | C10 | `suspendAgentCapability` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-151 | C10 | `reviewAgentRun` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-152 | C10 | `recordIncident` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-153 | C10 | `closeFinding` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C10-154 | C10 | `exportAuditEvidence` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-155 | C11 | `validateBuildProfile` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-156 | C11 | `requestBuild` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-157 | C11 | `getBuildResult` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-158 | C11 | `attestArtifact` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-159 | C11 | `createReleaseCandidate` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-160 | C11 | `submitReleaseForApproval` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-161 | C11 | `approveRelease` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-162 | C11 | `deployRelease` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-163 | C11 | `promoteEnvironment` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-164 | C11 | `runPostDeployChecks` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-165 | C11 | `requestRollback` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C11-166 | C11 | `getReleaseStatus` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-167 | C12 | `createKnowledgeDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-168 | C12 | `createDocumentVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-169 | C12 | `linkSourceEvidence` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-170 | C12 | `generateDocumentationDraft` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-171 | C12 | `submitDocumentReview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-172 | C12 | `decideDocumentReview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-173 | C12 | `publishKnowledgeDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-174 | C12 | `searchKnowledge` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-175 | C12 | `compareDocumentVersions` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-176 | C12 | `exportKnowledgeBundle` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-177 | C12 | `archiveKnowledgeDocument` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C12-178 | C12 | `reconcileDocumentation` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-179 | C13 | `getEffectiveSettings` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-180 | C13 | `validateSettingValue` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-181 | C13 | `updateUserPreference` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-182 | C13 | `requestScopedSettingChange` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-183 | C13 | `decideSettingChange` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-184 | C13 | `applyOwnerSettingChange` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-185 | C13 | `compareSettingVersions` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-186 | C13 | `rollbackSettingVersion` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-187 | C13 | `exportNonSecretSettings` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C13-188 | C13 | `diagnoseInheritance` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-189 | C14 | `getDashboardOverview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-190 | C14 | `getCenterSummary` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-191 | C14 | `getMyTasksOverview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-192 | C14 | `getApprovalQueue` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-193 | C14 | `getQualityOverview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-194 | C14 | `getSecurityOverview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-195 | C14 | `getIntegrationHealth` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-196 | C14 | `getReleaseOverview` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-197 | C14 | `getOperationalHealth` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-198 | C14 | `saveDashboardLayout` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-199 | C14 | `pinWidget` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-200 | C14 | `exportDashboardReport` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |
| OP-C14-201 | C14 | `invalidateProjection` | EXACT TEST ID TBD | OWNER REVIEW | NOT MAPPED |

## 6. قرارات مطلوبة لإغلاق الفجوة

1. مالك كل عملية يراجع عائلات الاختبار الأصلية ذات الصلة ويحدد إن كان هناك اختبار يغطيها فعلاً.
2. عند عدم وجود تغطية مصدرية، يرفع مالك المجال Test Case جديداً بحالة PROPOSED مع rationale وacceptance oracle، ولا يضعه في خانة SOURCE-ID-RECOVERED.
3. العمليات الـ62 بلا OWN مؤهل في 111 تحتاج أولاً إلى حسم المالك وحدود C01/C02/C03/C05 قبل اعتماد أي ربط اختبار.
4. لا تدمج عمليات مختلفة في اختبار واحد إلا إذا كان ذلك اختبار contract/flow واضحاً وله assertions منفصلة لكل عملية.
5. توحيد Event→Test في 100 و109 مع Operation→Test هنا، دون افتراض أن اختبار الحدث يغطي العملية أو العكس.
6. بعد الموافقة على المواصفات وبدء البرمجة، تنفذ الاختبارات وتسجل أدلة التشغيل بشكل منفصل.

## 7. حالة الإغلاق

- Exact Operation → Test ID: 0/201.
- Source Test IDs الموجودة: 89، لكن دون ربط مفرد مثبت في 97.
- Operation owners: لا تزال إحالات الملكية بحاجة إلى sign-off، و62 عملية بلا OWN مؤهل.
- Event-specific exact mapping: 0/106 وفق 100/102.
- Test execution: NOT RUN.
- Application code changes: NONE.
- Implementation authorization: NOT GRANTED.

**الحكم:** فجوة توثيقية محددة بالكامل على مستوى عدد العمليات، وليست تغطية اختبار مكتملة. لا تنشئ هذه الوثيقة APIs أو اختبارات منفذة، ولا تمثل موافقة على بدء البرمجة.