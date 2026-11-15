# 97 — سجل العمليات المفردة وربطها الأولي بالاختبارات والملكية

**الحالة:** DERIVED OPERATION INVENTORY / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المصادر:** 79 (سجل العمليات)، 80 (عائلات الاختبارات)، 82 (معرفات الملكية المؤهلة)، 94 (تدقيق التكامل المرجعي).  
**منهج الاستخراج:** فُككت أسماء العمليات المدرجة في جدول API حسب المركز في الوثيقة 79 إلى سجلات مفردة. لم تُضف أسماء عمليات جديدة. تصنيف النوع ومالك API ترشيح أولي بالاعتماد على بادئة الاسم ومركز المجال، وليس قراراً معتمداً.

## 1. ملخص الاستخراج

- عدد العمليات المفردة المستخرجة من جدول 79: **201**.
- المراكز المشمولة: C01–C14.
- عائلة الاختبار المذكورة هي مرجع مستوى المركز فقط؛ لم يثبت أنها تغطي العملية المفردة.
- عمود OWN يحدد قراراً مؤهلاً عند وجود مرجع واضح، وإلا يعلن TBD بدلاً من اختراع قرار.
- جميع السجلات في هذه الوثيقة PROPOSED / OWNER REVIEW؛ لا تمثل endpoints أو schemas منشورة.

## 2. سجل العمليات

| Operation ID | Center | Operation name | Type candidate | API Owner candidate | Ownership decision | Center test family | Exact mapping |
|---|---|---|---|---|---|---|---|
| OP-C01-001 | C01 | `createProject` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-002 | C01 | `updateProjectProfile` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-003 | C01 | `saveIdeaVersion` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-004 | C01 | `attachProjectReference` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-005 | C01 | `startDomainAnalysis` | Job/Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-006 | C01 | `getAnalysisResult` | Query candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-007 | C01 | `upsertPersona` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-008 | C01 | `createRequirement` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-009 | C01 | `reviseRequirement` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-010 | C01 | `recordNFR` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-011 | C01 | `analyzeGaps` | Query candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-012 | C01 | `answerClarification` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-014 | C01 | `createEstimate` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-015 | C01 | `submitRequirementBaseline` | Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C01-016 | C01 | `decideRequirementBaseline` | Approval/Command candidate | C01 (candidate) | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-017 | C02 | `createDesignWorkspace` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-018 | C02 | `createDesignDocument` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-019 | C02 | `getDesignDocument` | Query candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-020 | C02 | `saveDesignChangeSet` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-021 | C02 | `validateDesignDocument` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-022 | C02 | `createComponentDefinition` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-023 | C02 | `createDesignPreview` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-024 | C02 | `requestDesignAIProposal` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-025 | C02 | `applyDesignProposal` | Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-026 | C02 | `submitDesignForApproval` | Approval/Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-027 | C02 | `decideDesignApproval` | Approval/Command candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | Query candidate | C02 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-029 | C03 | `createPage` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-030 | C03 | `renamePage` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-031 | C03 | `createRoute` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-032 | C03 | `updateNavigationGraph` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-033 | C03 | `validateNavigationGraph` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-034 | C03 | `analyzeReachability` | Query candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-035 | C03 | `createDeepLinkRule` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-036 | C03 | `simulateUserJourney` | Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-037 | C03 | `submitNavigationForApproval` | Approval/Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-038 | C03 | `decideNavigationApproval` | Approval/Command candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C03-039 | C03 | `getApprovedNavigation` | Query candidate | C03 (candidate) | TBD — حدود C02/C03 ما زالت مفتوحة | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-040 | C04 | `registerDataSource` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-041 | C04 | `validateDataSource` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-042 | C04 | `createSchema` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-043 | C04 | `addEntity` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-044 | C04 | `addField` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-045 | C04 | `defineRelation` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-046 | C04 | `defineConstraint` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-047 | C04 | `defineIndex` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-048 | C04 | `defineRowPolicy` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-049 | C04 | `validateSchema` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-050 | C04 | `previewQuery` | Query candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-051 | C04 | `createDataBinding` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-052 | C04 | `generateTestFixtures` | Job/Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-053 | C04 | `configureSync` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-054 | C04 | `createMigrationPlan` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-055 | C04 | `dryRunMigration` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-056 | C04 | `applyMigration` | Job/Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-057 | C04 | `createRollbackPlan` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-058 | C04 | `startBackup` | Job/Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-059 | C04 | `requestRestore` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-060 | C04 | `verifyRestore` | Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-061 | C04 | `submitSchemaForApproval` | Approval/Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C04-062 | C04 | `decideSchemaApproval` | Approval/Command candidate | C04 (candidate) | OWN72-07 / OWN36-05 | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-064 | C05 | `listProjectFiles` | Query candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-065 | C05 | `readProjectFile` | Query candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-066 | C05 | `proposeFileChange` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | Approval/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-068 | C05 | `generateIR` | Job/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-069 | C05 | `validateIR` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-070 | C05 | `generateCodeFromDesign` | Job/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | Job/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-072 | C05 | `getGenerationStatus` | Query candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-073 | C05 | `analyzeDependencies` | Query candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-075 | C05 | `runCodeDiagnostics` | Job/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-077 | C05 | `compareDesignAndCode` | Query candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-078 | C05 | `createSandboxRun` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-079 | C05 | `cancelSandboxRun` | Job/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-080 | C05 | `createBuildPreparation` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-081 | C05 | `validateBuildConfiguration` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-083 | C05 | `requestCodeReview` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-084 | C05 | `approveCodeBaseline` | Approval/Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C05-085 | C05 | `prepareCodeHandoff` | Command candidate | C05 (candidate) | TBD — يلزم تعيين Domain/API Owner | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-086 | C06 | `registerAgent` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-087 | C06 | `createAgentVersion` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-088 | C06 | `registerSkill` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-089 | C06 | `registerToolManifest` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-090 | C06 | `requestToolGrant` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-091 | C06 | `createAITask` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-092 | C06 | `planAITask` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-093 | C06 | `assignAgents` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-094 | C06 | `executeTaskStep` | Job/Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-095 | C06 | `pauseTask` | Job/Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-096 | C06 | `resumeTask` | Job/Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-097 | C06 | `cancelTask` | Job/Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-098 | C06 | `submitAgentOutput` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-099 | C06 | `reviewAgentOutput` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-100 | C06 | `resolveAgentConflict` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-101 | C06 | `evaluateAgent` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-102 | C06 | `registerAIProvider` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-103 | C06 | `validateAIProvider` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-104 | C06 | `selectModelForTask` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-105 | C06 | `recordProviderUsage` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C06-106 | C06 | `disableAIProvider` | Command candidate | C06 (candidate) | OWN72-10 | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-107 | C07 | `createTestPlan` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-108 | C07 | `createTestCase` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-109 | C07 | `linkTestToRequirement` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-110 | C07 | `createTestRun` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-111 | C07 | `executeTestCase` | Job/Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-112 | C07 | `executeTestSuite` | Job/Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-113 | C07 | `recordDefect` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-114 | C07 | `transitionDefect` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-115 | C07 | `retestDefect` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-116 | C07 | `calculateCoverage` | Query candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-117 | C07 | `evaluateQualityGate` | Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-118 | C07 | `requestQualityWaiver` | Approval/Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-119 | C07 | `approveTestResults` | Approval/Command candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C07-120 | C07 | `exportQualityReport` | Query candidate | C07 (candidate) | OWN36-03 / OWN72-08 | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-121 | C08 | `createAssetRecord` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-122 | C08 | `requestUpload` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-123 | C08 | `finalizeUpload` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-124 | C08 | `inspectAsset` | Query candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-125 | C08 | `createAssetVersion` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-126 | C08 | `linkAsset` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-127 | C08 | `requestTransformation` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-128 | C08 | `revokeAssetLink` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-129 | C08 | `archiveAsset` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-130 | C08 | `requestExport` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C08-131 | C08 | `requestDeletion` | Command candidate | C08 (candidate) | OWN72-01 | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-132 | C09 | `registerIntegration` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-133 | C09 | `configureIntegration` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-134 | C09 | `beginAuthorization` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-135 | C09 | `completeAuthorization` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-136 | C09 | `validateConnection` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-137 | C09 | `testMapping` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-138 | C09 | `startSync` | Job/Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-139 | C09 | `receiveWebhook` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-140 | C09 | `pauseIntegration` | Job/Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-141 | C09 | `revokeConnection` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-142 | C09 | `rotateSecretReference` | Command candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C09-143 | C09 | `getIntegrationStatus` | Query candidate | C09 (candidate) | OWN72-02 | حالات C09 في 65؛ INTEGRATION + EVT-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-144 | C10 | `evaluatePolicy` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-145 | C10 | `registerSecurityFinding` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-146 | C10 | `createThreatModel` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-147 | C10 | `assessPrivacyImpact` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-148 | C10 | `requestPolicyChange` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-149 | C10 | `decideSecurityException` | Approval/Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-150 | C10 | `suspendAgentCapability` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-151 | C10 | `reviewAgentRun` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-152 | C10 | `recordIncident` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-153 | C10 | `closeFinding` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C10-154 | C10 | `exportAuditEvidence` | Command candidate | C10 (candidate) | OWN72-03 | حالات C10 في 66؛ AUTH-78 + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-155 | C11 | `validateBuildProfile` | Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-156 | C11 | `requestBuild` | Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-157 | C11 | `getBuildResult` | Query candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-158 | C11 | `attestArtifact` | Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-159 | C11 | `createReleaseCandidate` | Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-160 | C11 | `submitReleaseForApproval` | Approval/Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-161 | C11 | `approveRelease` | Approval/Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-162 | C11 | `deployRelease` | Job/Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-163 | C11 | `promoteEnvironment` | Job/Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-164 | C11 | `runPostDeployChecks` | Job/Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-165 | C11 | `requestRollback` | Command candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C11-166 | C11 | `getReleaseStatus` | Query candidate | C11 (candidate) | OWN72-06 / OWN36-06 | حالات C11 في 67؛ RELEASE + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-167 | C12 | `createKnowledgeDocument` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-168 | C12 | `createDocumentVersion` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-169 | C12 | `linkSourceEvidence` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-170 | C12 | `generateDocumentationDraft` | Job/Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-171 | C12 | `submitDocumentReview` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-172 | C12 | `decideDocumentReview` | Approval/Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-173 | C12 | `publishKnowledgeDocument` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-174 | C12 | `searchKnowledge` | Query candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-175 | C12 | `compareDocumentVersions` | Query candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-176 | C12 | `exportKnowledgeBundle` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-177 | C12 | `archiveKnowledgeDocument` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C12-178 | C12 | `reconcileDocumentation` | Command candidate | C12 (candidate) | OWN72-04 | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-179 | C13 | `getEffectiveSettings` | Query candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-180 | C13 | `validateSettingValue` | Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-181 | C13 | `updateUserPreference` | Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-182 | C13 | `requestScopedSettingChange` | Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-183 | C13 | `decideSettingChange` | Approval/Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-184 | C13 | `applyOwnerSettingChange` | Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-185 | C13 | `compareSettingVersions` | Query candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-186 | C13 | `rollbackSettingVersion` | Job/Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-187 | C13 | `exportNonSecretSettings` | Command candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C13-188 | C13 | `diagnoseInheritance` | Query candidate | C13 (candidate) | OWN72-05 | حالات C13 في 69؛ SETTINGS + APR-79 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-189 | C14 | `getDashboardOverview` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-190 | C14 | `getCenterSummary` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-191 | C14 | `getMyTasksOverview` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-192 | C14 | `getApprovalQueue` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-193 | C14 | `getQualityOverview` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-194 | C14 | `getSecurityOverview` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-195 | C14 | `getIntegrationHealth` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-196 | C14 | `getReleaseOverview` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-197 | C14 | `getOperationalHealth` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-198 | C14 | `saveDashboardLayout` | Command candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-199 | C14 | `pinWidget` | Command candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-200 | C14 | `exportDashboardReport` | Query candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |
| OP-C14-201 | C14 | `invalidateProjection` | Command candidate | C14 (candidate) | OWN36-10 / OWN72-09 | حالات C14 في 70؛ DASHBOARD + AUTH-78 | EXACT TEST ID TBD / OWNER REVIEW |

## 3. فجوات الربط المتبقية

1. **النوع:** راجع كل Query/Command/Job/Approval يدوياً؛ التصنيف الآلي الأولي لا يعتمد semantics.
2. **API Owner:** اعتماد المالك التشغيلي لكل عملية، مع فصل Domain Owner وDomain SoR.
3. **OWN:** تعيين قرار ملكية مؤهل أو تسجيل N/A مبرر؛ لا تستخدم OWN-nn غير المؤهل.
4. **AX/source:** ربط كل عملية بمحور AX-001..064 ومقطع المصدر/الوثيقة التي تستند إليها.
5. **Exact test ID:** تحويل عائلة الاختبار إلى معرف/معرفات محددة، أو N/A بموافقة المالك.
6. **Contract schema:** request/response/error schema، required/nullability/type/constraints، versioning، timeout، retry، idempotency، concurrency.
7. **Authorization:** action/resource/scope/actor وrecheck عند التنفيذ المؤجل.
8. **Approval:** تحديد ما إذا كانت العملية تتطلب موافقة مستقلة وربطها بـSH-03 وhash/version.
9. **Evidence:** لا يُسجل تنفيذ أو نجاح قبل run ID وcommit/build/environment/actor/evidence.

## 4. قواعد قبول السجل

- لا توجد عملية بلا Operation ID فريد.
- لا توجد عملية بلا Domain Owner/SoR وAPI Owner، أو حالة OPEN معللة.
- لا توجد عملية بلا محور مصدر أو استثناء N/A معتمد.
- لا توجد عملية بلا اختبار مفرد أو استثناء N/A معتمد.
- العمليات الحساسة لا تنفذ دون authorization/approval/audit حسب السياسة المعتمدة.
- لا تتحول الحالة إلى APPROVED أو IMPLEMENTED أو EXECUTED إلا بأدلة مستقلة.

## 5. اختبارات السجل

| Test ID | الفحص | معيار النجاح | الحالة |
|---|---|---|---|
| OP-97-T01 | uniqueness | جميع Operation IDs فريدة | SPECIFIED / NOT RUN |
| OP-97-T02 | API inventory completeness | عدد سجلات العمليات يساوي أسماء العمليات المستخرجة من 79 | SPECIFIED / NOT RUN |
| OP-97-T03 | owner completeness | Domain Owner/SoR وAPI Owner لكل عملية أو OPEN مبرر | SPECIFIED / NOT RUN |
| OP-97-T04 | ownership reference | OWN مؤهل موجود أو TBD مبرر | SPECIFIED / NOT RUN |
| OP-97-T05 | requirement trace | AX/source لكل عملية | SPECIFIED / NOT RUN |
| OP-97-T06 | exact test linkage | test ID محدد أو N/A معتمد | SPECIFIED / NOT RUN |
| OP-97-T07 | approval classification | كل عملية حساسة لها approval rule أو مبرر عدم الحاجة | SPECIFIED / NOT RUN |
| OP-97-T08 | schema readiness | request/response/error/version rules مكتملة | SPECIFIED / NOT RUN |

**لا تنشئ الوثيقة API endpoints أو schemas أو صلاحيات. جميع 201 سجلاً مرشحاً للمراجعة، ولا تغلق RI-94 أو GAP-044.**



## ملحق 99 — ربط كل عملية بمحاور AX

أضيفت الوثيقة 99 لتقديم ربط مرشح لكل Operation ID من السجلات الـ201 مع عناوين محاور AX-001..064، وعائلة اختبار المركز، ومرجع OWN. الربط مشتق آلياً من اسم العملية وعنوان المحور، لذلك لا يعد اعتماداً دلالياً أو إثبات تغطية. يظل exact test ID وsource locator وowner approval مفتوحاً.



## ملحق 99.1 — مرجع العقد الوسيط

أضيف في الوثيقة 99 عمود Contract source لكل عملية، باستخدام عقد المركز 59–70. هذا يحدد مصدر العقد الوسيط، وليس موضع المتطلب في DOCX؛ يبقى section/paragraph locator الأصلي مفتوحاً.


## تحديث مصدرّي 2026-11-15 — الوثيقتان 103 و104
تم تثبيت locators المصدر على مستوى المراكز في الوثيقة 103، واستعادة سيناريوهات القبول النصية في الوثيقة 104. يجب على كل Operation ID أن يحتفظ بعقد المركز الوسيط وأن يضيف locator فقرة وظيفية فقط بعد التحقق المباشر؛ معرفات SRC-AT محلية وليست Test IDs أصلية. لا تتغير حالة الملكية أو الاختبارات، ولا توجد APIs منفذة.


## تحديث منشأ الاختبارات — الوثيقة 105
توضح الوثيقة 105 أن عائلات الاختبار العامة في المصدر لا تعني اختباراً مفرداً لكل Operation ID. لا يجوز تصنيف DRAFT-AT أو Cxx-AT كمعرفات أصلية؛ ولا تُربط أي عملية بمعرف مصدر إلا بعد إثبات أن سيناريو الاختبار يغطي أثر العملية وشروطها ونتيجتها.
