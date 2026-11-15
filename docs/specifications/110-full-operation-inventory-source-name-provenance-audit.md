
> **تحديث النطاق:** أُنجز لاحقاً التدقيق الحرفي الشامل لجميع العمليات الـ201 في الوثيقة [114](114-full-inventory-api-operation-source-name-audit.md). أرقام هذه الوثيقة تخص تقسيم التدقيق السابق (95 عملية مدققة في 108 و106 خارج ذلك النطاق)، ولا تستخدم كحصيلة نهائية للمخزون كله. الحصيلة الكاملة: 11 EXACT-NAME، و7 SEMANTIC-CANDIDATE، و183 بلا اسم حرفي مطابق؛ مع تعارض مركز واحد موثق في 114.

# 110 — تدقيق منشأ أسماء سجل العمليات الكامل C01–C14

**الحالة:** FULL INVENTORY PROVENANCE STATUS / DERIVED AUDIT / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المراجع:** 00_MASTER_APPROVED_SPECIFICATIONS_SOURCE.docx (Artifact 14140098015)، 97، 103، 108.

## 1. النتيجة التنفيذية

تم استخراج **201 عملية** من سجل 97 والتحقق من عدد السجلات ومعرفاتها ومراكزها. تدقيق المطابقة الحرفية المتاح في 108 شمل 95 عملية فقط من C04/C06/C07/C09/C11/C14؛ ولم يشمل بقية 106 عمليات.

| الحالة | العدد | المعنى |
|---|---:|---|
| EXACT-NAME | 5 | الاسم مطابق حرفياً في locator المصدر المسجل في 108 |
| SEMANTIC-CANDIDATE | 7 | اسم/وصف مصدر قريب؛ لم يعتمد التطابق الدلالي |
| DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | 83 | ضمن نطاق 95 في 108 ولم يظهر الاسم حرفياً |
| NOT-ASSESSED | 106 | خارج نطاق تدقيق 108؛ لم يجر فحص حرفي له هنا |
| **الإجمالي** | **201** | جميع عمليات 97 |

عدم ظهور الاسم حرفياً لا يعني غياب الوظيفة؛ قد تكون موصوفة بالعربية أو باسم آخر. كما أن NOT-ASSESSED لا يعني غياب المتطلب.

## 2. المطابقات الحرفية الخمس

| Operation ID | الاسم | موضع النص المستخرج | القسم |
|---|---|---:|---|
| OP-C04-049 | `validateSchema` | 11279 | §135.6 |
| OP-C07-107 | `createTestPlan` | 11312 | §135.8 |
| OP-C07-110 | `createTestRun` | 11313 | §135.8 |
| OP-C07-112 | `executeTestSuite` | 11314 | §135.8 |
| OP-C11-159 | `createReleaseCandidate` | 11334 | §135.9 |

تثبت المطابقة وجود الاسم في نموذج العقد المصدر فقط؛ ولا تثبت التنفيذ أو اعتماد schema أو الصلاحيات أو المالك النهائي.

## 3. المرشحات الدلالية السبعة

| Operation ID | الاسم في 97 | الاسم/الوصف المصدر | الموضع | نقطة الحسم |
|---|---|---|---:|---|
| OP-C04-054 | `createMigrationPlan` | `generateMigrationPlan` | 11281 | هل create وgenerate مرحلتان مختلفتان؟ |
| OP-C07-117 | `evaluateQualityGate` | `validateQualityGate` | 11316 | هل التقييم يصدر قراراً والتحقق يفحص الشروط؟ |
| OP-C07-120 | `exportQualityReport` | `submitQualityReport` | 11317 | لا تدمج التصدير والإرسال دون قرار |
| OP-C11-156 | `requestBuild` | `prepareBuild` / `createBuildJob` | 11330، 11332 | فصل الطلب والتحضير وإنشاء المهمة |
| OP-C11-162 | `deployRelease` | `executeApprovedDeployment` | 11336 | إبراز شرط الموافقة |
| OP-C11-165 | `requestRollback` | `rollbackDeployment` | 11337 | فصل الطلب عن التنفيذ |
| OP-C11-164 | `runPostDeployChecks` | وصف التحقق بعد النشر | 11345 | دعم مفهومي دون اسم API صريح |

هذه المرشحات لا تمثل دمجاً أو إعادة تسمية أو قرار ملكية.

## 4. فجوات النطاق والإجراء التالي

1. استكمال فحص 106 عملية من C01/C02/C03/C05/C08/C10/C12/C13 مقابل النص الأصلي.
2. تدقيق 83 عملية متبقية ضمن نطاق 108 لم تثبت مطابقتها الحرفية ولم تسجل ضمن المرشحات السبعة، مع التمييز بين الوصف العربي والاسم البديل.
3. استعادة قائمة العمليات الأصلية كاملة من الفصل 135 لكل مركز؛ لا سيما المراكز التي لا يقدم لها الفصل نموذج API مستقلاً.
4. توثيق locator فقرة/سطر لكل دعم دلالي، وعدم الاكتفاء بموضع الفصل أو العقد الوسيط.
5. إعداد crosswalk: source operation → candidate operation(s) → lifecycle distinction → owner decision → API contract.
6. عدم حذف أو دمج أي عملية مشتقة تلقائياً؛ يلزم قرار مالك موثق مع حفظ تاريخ الاسم.

## 5. قواعد المراجعة

- لا تعد المطابقة الحرفية قراراً بأن العملية يجب أن تنفذ كما هي؛ يجب مراجعة معناها ونوعها وحدودها.
- لا تحول Query إلى Command أو Job أو Approval بسبب تشابه الاسم.
- لا تدمج request/prepare/create/execute/approve/verify؛ قد تكون مراحل lifecycle منفصلة.
- لا تستنتج Domain SoR أو API Owner أو Qualified OWN من اسم العملية أو بادئتها.
- لا تستخدم عائلة اختبار مركزية كدليل تغطية لعملية مفردة.
- لا تسجل PASS دون test run وcommit/build/environment وartifact evidence.
- لا تنشئ endpoint أو schema أو migration من هذه المصفوفة.

## 6. سجل العمليات الـ201

| Operation ID | Center | Operation name | Source-name status | Source line | Source section | Review note |
|---|---|---|---|---:|---|---|
| OP-C01-001 | C01 | `createProject` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-002 | C01 | `updateProjectProfile` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-003 | C01 | `saveIdeaVersion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-004 | C01 | `attachProjectReference` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-005 | C01 | `startDomainAnalysis` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-006 | C01 | `getAnalysisResult` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-007 | C01 | `upsertPersona` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-008 | C01 | `createRequirement` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-009 | C01 | `reviseRequirement` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-010 | C01 | `recordNFR` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-011 | C01 | `analyzeGaps` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-012 | C01 | `answerClarification` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-014 | C01 | `createEstimate` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-015 | C01 | `submitRequirementBaseline` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C01-016 | C01 | `decideRequirementBaseline` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-017 | C02 | `createDesignWorkspace` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-018 | C02 | `createDesignDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-019 | C02 | `getDesignDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-020 | C02 | `saveDesignChangeSet` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-021 | C02 | `validateDesignDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-022 | C02 | `createComponentDefinition` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-023 | C02 | `createDesignPreview` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-024 | C02 | `requestDesignAIProposal` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-025 | C02 | `applyDesignProposal` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-026 | C02 | `submitDesignForApproval` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-027 | C02 | `decideDesignApproval` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-029 | C03 | `createPage` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-030 | C03 | `renamePage` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-031 | C03 | `createRoute` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-032 | C03 | `updateNavigationGraph` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-033 | C03 | `validateNavigationGraph` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-034 | C03 | `analyzeReachability` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-035 | C03 | `createDeepLinkRule` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-036 | C03 | `simulateUserJourney` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-037 | C03 | `submitNavigationForApproval` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-038 | C03 | `decideNavigationApproval` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C03-039 | C03 | `getApprovedNavigation` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C04-040 | C04 | `registerDataSource` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-041 | C04 | `validateDataSource` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-042 | C04 | `createSchema` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-043 | C04 | `addEntity` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-044 | C04 | `addField` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-045 | C04 | `defineRelation` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-046 | C04 | `defineConstraint` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-047 | C04 | `defineIndex` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-048 | C04 | `defineRowPolicy` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-049 | C04 | `validateSchema` | EXACT-NAME | 11279 | §135.6 | الاسم مطابق حرفياً وفق تدقيق 108. |
| OP-C04-050 | C04 | `previewQuery` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-051 | C04 | `createDataBinding` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-052 | C04 | `generateTestFixtures` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-053 | C04 | `configureSync` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-054 | C04 | `createMigrationPlan` | SEMANTIC-CANDIDATE | 11281 | §135.6 | generateMigrationPlan: تقارب محتمل؛ create مقابل generate |
| OP-C04-055 | C04 | `dryRunMigration` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-056 | C04 | `applyMigration` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-057 | C04 | `createRollbackPlan` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-058 | C04 | `startBackup` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-059 | C04 | `requestRestore` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-060 | C04 | `verifyRestore` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-061 | C04 | `submitSchemaForApproval` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C04-062 | C04 | `decideSchemaApproval` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-064 | C05 | `listProjectFiles` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-065 | C05 | `readProjectFile` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-066 | C05 | `proposeFileChange` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-068 | C05 | `generateIR` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-069 | C05 | `validateIR` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-070 | C05 | `generateCodeFromDesign` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-072 | C05 | `getGenerationStatus` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-073 | C05 | `analyzeDependencies` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-075 | C05 | `runCodeDiagnostics` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-077 | C05 | `compareDesignAndCode` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-078 | C05 | `createSandboxRun` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-079 | C05 | `cancelSandboxRun` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-080 | C05 | `createBuildPreparation` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-081 | C05 | `validateBuildConfiguration` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-083 | C05 | `requestCodeReview` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-084 | C05 | `approveCodeBaseline` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C05-085 | C05 | `prepareCodeHandoff` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C06-086 | C06 | `registerAgent` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-087 | C06 | `createAgentVersion` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-088 | C06 | `registerSkill` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-089 | C06 | `registerToolManifest` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-090 | C06 | `requestToolGrant` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-091 | C06 | `createAITask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-092 | C06 | `planAITask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-093 | C06 | `assignAgents` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-094 | C06 | `executeTaskStep` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-095 | C06 | `pauseTask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-096 | C06 | `resumeTask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-097 | C06 | `cancelTask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-098 | C06 | `submitAgentOutput` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-099 | C06 | `reviewAgentOutput` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-100 | C06 | `resolveAgentConflict` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-101 | C06 | `evaluateAgent` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-102 | C06 | `registerAIProvider` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-103 | C06 | `validateAIProvider` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-104 | C06 | `selectModelForTask` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-105 | C06 | `recordProviderUsage` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C06-106 | C06 | `disableAIProvider` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-107 | C07 | `createTestPlan` | EXACT-NAME | 11312 | §135.8 | الاسم مطابق حرفياً وفق تدقيق 108. |
| OP-C07-108 | C07 | `createTestCase` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-109 | C07 | `linkTestToRequirement` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-110 | C07 | `createTestRun` | EXACT-NAME | 11313 | §135.8 | الاسم مطابق حرفياً وفق تدقيق 108. |
| OP-C07-111 | C07 | `executeTestCase` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-112 | C07 | `executeTestSuite` | EXACT-NAME | 11314 | §135.8 | الاسم مطابق حرفياً وفق تدقيق 108. |
| OP-C07-113 | C07 | `recordDefect` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-114 | C07 | `transitionDefect` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-115 | C07 | `retestDefect` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-116 | C07 | `calculateCoverage` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-117 | C07 | `evaluateQualityGate` | SEMANTIC-CANDIDATE | 11316 | §135.8 | validateQualityGate: تقارب محتمل؛ validate مقابل evaluate |
| OP-C07-118 | C07 | `requestQualityWaiver` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-119 | C07 | `approveTestResults` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C07-120 | C07 | `exportQualityReport` | SEMANTIC-CANDIDATE | 11317 | §135.8 | submitQualityReport: ليس تطابقاً مؤكداً؛ export مقابل submit |
| OP-C08-121 | C08 | `createAssetRecord` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-122 | C08 | `requestUpload` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-123 | C08 | `finalizeUpload` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-124 | C08 | `inspectAsset` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-125 | C08 | `createAssetVersion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-126 | C08 | `linkAsset` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-127 | C08 | `requestTransformation` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-128 | C08 | `revokeAssetLink` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-129 | C08 | `archiveAsset` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-130 | C08 | `requestExport` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C08-131 | C08 | `requestDeletion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C09-132 | C09 | `registerIntegration` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-133 | C09 | `configureIntegration` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-134 | C09 | `beginAuthorization` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-135 | C09 | `completeAuthorization` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-136 | C09 | `validateConnection` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-137 | C09 | `testMapping` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-138 | C09 | `startSync` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-139 | C09 | `receiveWebhook` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-140 | C09 | `pauseIntegration` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-141 | C09 | `revokeConnection` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-142 | C09 | `rotateSecretReference` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C09-143 | C09 | `getIntegrationStatus` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C10-144 | C10 | `evaluatePolicy` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-145 | C10 | `registerSecurityFinding` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-146 | C10 | `createThreatModel` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-147 | C10 | `assessPrivacyImpact` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-148 | C10 | `requestPolicyChange` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-149 | C10 | `decideSecurityException` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-150 | C10 | `suspendAgentCapability` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-151 | C10 | `reviewAgentRun` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-152 | C10 | `recordIncident` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-153 | C10 | `closeFinding` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C10-154 | C10 | `exportAuditEvidence` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C11-155 | C11 | `validateBuildProfile` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-156 | C11 | `requestBuild` | SEMANTIC-CANDIDATE | 11330/11332 | §135.9 | prepareBuild / createBuildJob: يلزم فصل الطلب والتحضير وإنشاء المهمة |
| OP-C11-157 | C11 | `getBuildResult` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-158 | C11 | `attestArtifact` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-159 | C11 | `createReleaseCandidate` | EXACT-NAME | 11334 | §135.9 | الاسم مطابق حرفياً وفق تدقيق 108. |
| OP-C11-160 | C11 | `submitReleaseForApproval` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-161 | C11 | `approveRelease` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-162 | C11 | `deployRelease` | SEMANTIC-CANDIDATE | 11336 | §135.9 | executeApprovedDeployment: تقارب محتمل؛ يلزم شرط الموافقة |
| OP-C11-163 | C11 | `promoteEnvironment` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C11-164 | C11 | `runPostDeployChecks` | SEMANTIC-CANDIDATE | 11345 | §135.9 | التحقق بعد النشر: دعم مفهومي دون اسم API صريح |
| OP-C11-165 | C11 | `requestRollback` | SEMANTIC-CANDIDATE | 11337 | §135.9 | rollbackDeployment: يلزم فصل الطلب عن التنفيذ |
| OP-C11-166 | C11 | `getReleaseStatus` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C12-167 | C12 | `createKnowledgeDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-168 | C12 | `createDocumentVersion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-169 | C12 | `linkSourceEvidence` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-170 | C12 | `generateDocumentationDraft` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-171 | C12 | `submitDocumentReview` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-172 | C12 | `decideDocumentReview` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-173 | C12 | `publishKnowledgeDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-174 | C12 | `searchKnowledge` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-175 | C12 | `compareDocumentVersions` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-176 | C12 | `exportKnowledgeBundle` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-177 | C12 | `archiveKnowledgeDocument` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C12-178 | C12 | `reconcileDocumentation` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-179 | C13 | `getEffectiveSettings` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-180 | C13 | `validateSettingValue` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-181 | C13 | `updateUserPreference` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-182 | C13 | `requestScopedSettingChange` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-183 | C13 | `decideSettingChange` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-184 | C13 | `applyOwnerSettingChange` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-185 | C13 | `compareSettingVersions` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-186 | C13 | `rollbackSettingVersion` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-187 | C13 | `exportNonSecretSettings` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C13-188 | C13 | `diagnoseInheritance` | NOT-ASSESSED | — | — | خارج نطاق المطابقة الحرفية في 108؛ يلزم تدقيق المصدر. |
| OP-C14-189 | C14 | `getDashboardOverview` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-190 | C14 | `getCenterSummary` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-191 | C14 | `getMyTasksOverview` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-192 | C14 | `getApprovalQueue` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-193 | C14 | `getQualityOverview` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-194 | C14 | `getSecurityOverview` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-195 | C14 | `getIntegrationHealth` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-196 | C14 | `getReleaseOverview` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-197 | C14 | `getOperationalHealth` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-198 | C14 | `saveDashboardLayout` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-199 | C14 | `pinWidget` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-200 | C14 | `exportDashboardReport` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |
| OP-C14-201 | C14 | `invalidateProjection` | DERIVED-NAME / NO EXACT MATCH IN AUDIT 108 | — | — | ضمن نطاق تدقيق 108 ولم يظهر الاسم حرفياً؛ قد تكون الوظيفة موصوفة باسم آخر. |

## 7. بوابة الإغلاق

لا يغلق تدقيق منشأ الأسماء إلا بعد مطابقة كل عملية مع نص مصدر أصلي أو توثيق كونها قراراً مشتقاً لاحقاً؛ واعتماد crosswalk للعمليات المتشابهة مع توضيح lifecycle؛ واعتماد مالك المجال وAPI Owner وDomain SoR وQualified OWN؛ وربط Exact Test ID مفرد أو N/A مبرر؛ واستكمال schemas/versioning/authz/errors/idempotency/retention؛ وتنفيذ الاختبارات بعد إذن البرمجة وتسجيل الأدلة.

**الحالة النهائية:** تدقيق وثائقي مشتق. لا يعتمد أسماء العمليات، ولا يغلق فجوات الاختبارات أو الملكية، ولا يمثل موافقة على بدء البرمجة أو تغيير كود التطبيق.
