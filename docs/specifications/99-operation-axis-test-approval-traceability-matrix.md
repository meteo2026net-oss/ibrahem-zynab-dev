# 99 — ربط عمليات API بمحاور المتطلبات والاختبارات وسجل الموافقات

**الحالة:** DERIVED TRACEABILITY MATRIX / HEURISTIC CANDIDATE MAPPING / OWNER REVIEW REQUIRED / NOT APPROVED  
**التاريخ:** 2026-11-15  
**المصادر:** 57 (أسماء المحاور وحالات حقولها)، 74 (خريطة FLD→AX)، 79 (عمليات API)، 80 (عائلات الاختبارات)، 82 (قرارات الملكية المؤهلة)، 97 (Operation IDs)، 98 (Approval Types).

## 1. منهج الربط وحدوده

- استخرجت هذه المصفوفة 201 Operation ID من الوثيقة 97، وأبقت اسم العملية كما هو.
- اقتراح المحاور مبني على مطابقة كلمات اسم العملية مع عناوين AX-001..064 في الوثيقة 57، مع fallback إلى نطاق المركز إذا لم تنطبق قاعدة. هذا **اقتراح آلي للمراجعة** وليس إثباتاً أن كل محور متأثر.
- لم أرفع Primary/Secondary إلى قرار نهائي؛ جميع محاور هذا الجدول CANDIDATE. يجب أن يراجع مالك المتطلب كل ربط.
- حقل الاختبار يعيد عائلة الاختبار الموجودة في الوثيقة 80 على مستوى المركز فقط. لا يُدّعى أن لها Test ID مفرداً يغطي العملية.
- قرار OWN ينسخ حالة المرجع الواردة في 97؛ TBD يبقى TBD.
- لا يُستنتج من الربط وجود requirement ID أدق من AX أو مصدر فقرة أصلي غير موثق.

## 2. سجل العمليات الـ201

| Operation ID | Center | Operation | Candidate AX mapping | Contract source | Test family (center-level) | OWN reference | Exact test ID |
|---|---|---|---|---|---|---|---|
| OP-C01-001 | C01 | `createProject` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-002 | C01 | `updateProjectProfile` | AX-001, AX-004, AX-025, AX-030, AX-014, AX-046, AX-038 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-003 | C01 | `saveIdeaVersion` | AX-001, AX-004, AX-025 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-004 | C01 | `attachProjectReference` | AX-001, AX-004, AX-025, AX-030, AX-021, AX-048, AX-056 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-005 | C01 | `startDomainAnalysis` | AX-001, AX-004, AX-025, AX-030, AX-005, AX-017, AX-018, AX-036, AX-063 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-006 | C01 | `getAnalysisResult` | AX-001, AX-004, AX-025 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-007 | C01 | `upsertPersona` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-008 | C01 | `createRequirement` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-009 | C01 | `reviseRequirement` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-010 | C01 | `recordNFR` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-011 | C01 | `analyzeGaps` | AX-001, AX-004, AX-025 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-012 | C01 | `answerClarification` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | AX-001, AX-004, AX-025 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-014 | C01 | `createEstimate` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-015 | C01 | `submitRequirementBaseline` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C01-016 | C01 | `decideRequirementBaseline` | AX-001, AX-004, AX-025, AX-030 | 59 (derived center contract; section locator TBD) | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | TBD — لا يوجد قرار OWN مفرد مخصص في السجل الحالي | TBD / OWNER REVIEW |
| OP-C02-017 | C02 | `createDesignWorkspace` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-018 | C02 | `createDesignDocument` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064, AX-021, AX-048, AX-056 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-019 | C02 | `getDesignDocument` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064, AX-021, AX-048, AX-056 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-020 | C02 | `saveDesignChangeSet` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-021 | C02 | `validateDesignDocument` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064, AX-021, AX-048, AX-056 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-022 | C02 | `createComponentDefinition` | AX-002, AX-006, AX-007, AX-009, AX-035 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-023 | C02 | `createDesignPreview` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-024 | C02 | `requestDesignAIProposal` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064, AX-005, AX-017, AX-018, AX-036, AX-063 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-025 | C02 | `applyDesignProposal` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-026 | C02 | `submitDesignForApproval` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-027 | C02 | `decideDesignApproval` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-029 | C03 | `createPage` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-030 | C03 | `renamePage` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-031 | C03 | `createRoute` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-032 | C03 | `updateNavigationGraph` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-033 | C03 | `validateNavigationGraph` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-034 | C03 | `analyzeReachability` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-035 | C03 | `createDeepLinkRule` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-036 | C03 | `simulateUserJourney` | AX-012, AX-023, AX-061 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-037 | C03 | `submitNavigationForApproval` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-038 | C03 | `decideNavigationApproval` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C03-039 | C03 | `getApprovedNavigation` | AX-008, AX-025 | 60 (derived center contract; section locator TBD) | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | TBD — حدود C02/C03 ما زالت مفتوحة | TBD / OWNER REVIEW |
| OP-C04-040 | C04 | `registerDataSource` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-041 | C04 | `validateDataSource` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-042 | C04 | `createSchema` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-043 | C04 | `addEntity` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-044 | C04 | `addField` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-045 | C04 | `defineRelation` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-046 | C04 | `defineConstraint` | AX-005, AX-017, AX-018, AX-036, AX-063 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-047 | C04 | `defineIndex` | AX-021, AX-048, AX-056 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-048 | C04 | `defineRowPolicy` | AX-011, AX-042, AX-023, AX-054, AX-056, AX-063 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-049 | C04 | `validateSchema` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-050 | C04 | `previewQuery` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-051 | C04 | `createDataBinding` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-052 | C04 | `generateTestFixtures` | AX-015, AX-044, AX-039 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-053 | C04 | `configureSync` | AX-022, AX-040, AX-049 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-054 | C04 | `createMigrationPlan` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-055 | C04 | `dryRunMigration` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-056 | C04 | `applyMigration` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-057 | C04 | `createRollbackPlan` | AX-016, AX-043, AX-050, AX-064 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-058 | C04 | `startBackup` | AX-037, AX-064 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-059 | C04 | `requestRestore` | AX-037, AX-064 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-060 | C04 | `verifyRestore` | AX-037, AX-064 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-061 | C04 | `submitSchemaForApproval` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C04-062 | C04 | `decideSchemaApproval` | AX-011, AX-042 | 61 (derived center contract; section locator TBD) | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | OWN72-07 / OWN36-05 | TBD / OWNER REVIEW |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-064 | C05 | `listProjectFiles` | AX-001, AX-004, AX-025, AX-030, AX-014, AX-046, AX-038 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-065 | C05 | `readProjectFile` | AX-001, AX-004, AX-025, AX-030, AX-014, AX-046, AX-038 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-066 | C05 | `proposeFileChange` | AX-014, AX-046, AX-038 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-068 | C05 | `generateIR` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-069 | C05 | `validateIR` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-070 | C05 | `generateCodeFromDesign` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | AX-001, AX-004, AX-025, AX-030 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-072 | C05 | `getGenerationStatus` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-073 | C05 | `analyzeDependencies` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-075 | C05 | `runCodeDiagnostics` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | AX-034, AX-057, AX-049 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-077 | C05 | `compareDesignAndCode` | AX-002, AX-006, AX-007, AX-009, AX-035, AX-016, AX-043, AX-050, AX-064 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-078 | C05 | `createSandboxRun` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-079 | C05 | `cancelSandboxRun` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-080 | C05 | `createBuildPreparation` | AX-016, AX-043, AX-050, AX-064 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-081 | C05 | `validateBuildConfiguration` | AX-016, AX-043, AX-050, AX-064 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | AX-022, AX-040, AX-049 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-083 | C05 | `requestCodeReview` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-084 | C05 | `approveCodeBaseline` | AX-001, AX-004, AX-025, AX-030 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C05-085 | C05 | `prepareCodeHandoff` | AX-028, AX-039, AX-041 | 62 (derived center contract; section locator TBD) | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | TBD — يلزم تعيين Domain/API Owner | TBD / OWNER REVIEW |
| OP-C06-086 | C06 | `registerAgent` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-087 | C06 | `createAgentVersion` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-088 | C06 | `registerSkill` | AX-005, AX-017, AX-018, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-089 | C06 | `registerToolManifest` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-090 | C06 | `requestToolGrant` | AX-012, AX-023, AX-061, AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-091 | C06 | `createAITask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-092 | C06 | `planAITask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-093 | C06 | `assignAgents` | AX-016, AX-043, AX-050, AX-064, AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-094 | C06 | `executeTaskStep` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-095 | C06 | `pauseTask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-096 | C06 | `resumeTask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-097 | C06 | `cancelTask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-098 | C06 | `submitAgentOutput` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-099 | C06 | `reviewAgentOutput` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-100 | C06 | `resolveAgentConflict` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-101 | C06 | `evaluateAgent` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-102 | C06 | `registerAIProvider` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-103 | C06 | `validateAIProvider` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-104 | C06 | `selectModelForTask` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-105 | C06 | `recordProviderUsage` | AX-005, AX-017, AX-018, AX-036, AX-063, AX-033, AX-047 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C06-106 | C06 | `disableAIProvider` | AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | OWN72-10 | TBD / OWNER REVIEW |
| OP-C07-107 | C07 | `createTestPlan` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-108 | C07 | `createTestCase` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-109 | C07 | `linkTestToRequirement` | AX-001, AX-004, AX-025, AX-030, AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-110 | C07 | `createTestRun` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-111 | C07 | `executeTestCase` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-112 | C07 | `executeTestSuite` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-113 | C07 | `recordDefect` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-114 | C07 | `transitionDefect` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-115 | C07 | `retestDefect` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-116 | C07 | `calculateCoverage` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-117 | C07 | `evaluateQualityGate` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-118 | C07 | `requestQualityWaiver` | AX-015, AX-044, AX-039, AX-005, AX-017, AX-018, AX-036, AX-063 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-119 | C07 | `approveTestResults` | AX-015, AX-044, AX-039 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C07-120 | C07 | `exportQualityReport` | AX-015, AX-044, AX-039, AX-024, AX-031, AX-042, AX-058, AX-038, AX-060 | 63 (derived center contract; section locator TBD) | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| OP-C08-121 | C08 | `createAssetRecord` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-122 | C08 | `requestUpload` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-123 | C08 | `finalizeUpload` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-124 | C08 | `inspectAsset` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-125 | C08 | `createAssetVersion` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-126 | C08 | `linkAsset` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-127 | C08 | `requestTransformation` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-128 | C08 | `revokeAssetLink` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-129 | C08 | `archiveAsset` | AX-014, AX-046, AX-038 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-130 | C08 | `requestExport` | AX-038, AX-060 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C08-131 | C08 | `requestDeletion` | AX-014, AX-046 | 64 (derived center contract; section locator TBD) | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | OWN72-01 | TBD / OWNER REVIEW |
| OP-C09-132 | C09 | `registerIntegration` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-133 | C09 | `configureIntegration` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-134 | C09 | `beginAuthorization` | AX-012, AX-023, AX-061 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-135 | C09 | `completeAuthorization` | AX-012, AX-023, AX-061 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-136 | C09 | `validateConnection` | AX-022, AX-040 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-137 | C09 | `testMapping` | AX-015, AX-044, AX-039 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-138 | C09 | `startSync` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-139 | C09 | `receiveWebhook` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-140 | C09 | `pauseIntegration` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-141 | C09 | `revokeConnection` | AX-022, AX-040 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-142 | C09 | `rotateSecretReference` | AX-021, AX-048, AX-056 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C09-143 | C09 | `getIntegrationStatus` | AX-022, AX-040, AX-049 | 65 (derived center contract; section locator TBD) | حالات C09 في 65؛ INTEGRATION + EVT-79 | OWN72-02 | TBD / OWNER REVIEW |
| OP-C10-144 | C10 | `evaluatePolicy` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-145 | C10 | `registerSecurityFinding` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-146 | C10 | `createThreatModel` | AX-005, AX-017, AX-018, AX-036, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-147 | C10 | `assessPrivacyImpact` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-148 | C10 | `requestPolicyChange` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-149 | C10 | `decideSecurityException` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-150 | C10 | `suspendAgentCapability` | AX-005, AX-017, AX-018, AX-036, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-151 | C10 | `reviewAgentRun` | AX-005, AX-017, AX-018, AX-036, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-152 | C10 | `recordIncident` | AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-153 | C10 | `closeFinding` | AX-023, AX-054, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C10-154 | C10 | `exportAuditEvidence` | AX-038, AX-060, AX-023, AX-054, AX-056, AX-063 | 66 (derived center contract; section locator TBD) | حالات C10 في 66؛ AUTH-78 + APR-79 | OWN72-03 | TBD / OWNER REVIEW |
| OP-C11-155 | C11 | `validateBuildProfile` | AX-014, AX-046, AX-038, AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-156 | C11 | `requestBuild` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-157 | C11 | `getBuildResult` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-158 | C11 | `attestArtifact` | AX-015, AX-044, AX-039, AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-159 | C11 | `createReleaseCandidate` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-160 | C11 | `submitReleaseForApproval` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-161 | C11 | `approveRelease` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-162 | C11 | `deployRelease` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-163 | C11 | `promoteEnvironment` | AX-016, AX-043 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-164 | C11 | `runPostDeployChecks` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-165 | C11 | `requestRollback` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C11-166 | C11 | `getReleaseStatus` | AX-016, AX-043, AX-050, AX-064 | 67 (derived center contract; section locator TBD) | حالات C11 في 67؛ RELEASE + APR-79 | OWN72-06 / OWN36-06 | TBD / OWNER REVIEW |
| OP-C12-167 | C12 | `createKnowledgeDocument` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-168 | C12 | `createDocumentVersion` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-169 | C12 | `linkSourceEvidence` | AX-021, AX-048 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-170 | C12 | `generateDocumentationDraft` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-171 | C12 | `submitDocumentReview` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-172 | C12 | `decideDocumentReview` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-173 | C12 | `publishKnowledgeDocument` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-174 | C12 | `searchKnowledge` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-175 | C12 | `compareDocumentVersions` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-176 | C12 | `exportKnowledgeBundle` | AX-021, AX-048, AX-056, AX-038, AX-060 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-177 | C12 | `archiveKnowledgeDocument` | AX-014, AX-046, AX-038, AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C12-178 | C12 | `reconcileDocumentation` | AX-021, AX-048, AX-056 | 68 (derived center contract; section locator TBD) | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | OWN72-04 | TBD / OWNER REVIEW |
| OP-C13-179 | C13 | `getEffectiveSettings` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-180 | C13 | `validateSettingValue` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-181 | C13 | `updateUserPreference` | AX-012, AX-023, AX-061, AX-021, AX-048, AX-056 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-182 | C13 | `requestScopedSettingChange` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-183 | C13 | `decideSettingChange` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-184 | C13 | `applyOwnerSettingChange` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-185 | C13 | `compareSettingVersions` | AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-186 | C13 | `rollbackSettingVersion` | AX-016, AX-043, AX-050, AX-064, AX-034, AX-057, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-187 | C13 | `exportNonSecretSettings` | AX-034, AX-057, AX-049, AX-038, AX-060 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C13-188 | C13 | `diagnoseInheritance` | AX-034, AX-049 | 69 (derived center contract; section locator TBD) | حالات C13 في 69؛ SETTINGS + APR-79 | OWN72-05 | TBD / OWNER REVIEW |
| OP-C14-189 | C14 | `getDashboardOverview` | AX-024, AX-031, AX-042, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-190 | C14 | `getCenterSummary` | AX-024, AX-031, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-191 | C14 | `getMyTasksOverview` | AX-005, AX-017, AX-018, AX-036, AX-063 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-192 | C14 | `getApprovalQueue` | AX-024, AX-031, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-193 | C14 | `getQualityOverview` | AX-015, AX-044, AX-039 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-194 | C14 | `getSecurityOverview` | AX-023, AX-054, AX-056, AX-063 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-195 | C14 | `getIntegrationHealth` | AX-022, AX-040, AX-049 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-196 | C14 | `getReleaseOverview` | AX-016, AX-043, AX-050, AX-064 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-197 | C14 | `getOperationalHealth` | AX-024, AX-031, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-198 | C14 | `saveDashboardLayout` | AX-024, AX-031, AX-042, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-199 | C14 | `pinWidget` | AX-024, AX-031, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-200 | C14 | `exportDashboardReport` | AX-024, AX-031, AX-042, AX-058, AX-038, AX-060 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |
| OP-C14-201 | C14 | `invalidateProjection` | AX-001, AX-004, AX-025, AX-030, AX-024, AX-031, AX-042, AX-058 | 70 (derived center contract; section locator TBD) | حالات C14 في 70؛ DASHBOARD + AUTH-78 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |

## 3. أنواع الموافقات وربطها بالعمليات

تمت مطابقة أسماء العمليات الدقيقة عندما كان اسم العملية المستهدف في الوثيقة 98 موجوداً في قائمة 97. الحالات التي لا تطابق اسماً حرفياً تبقى بحاجة إلى ربط مالك؛ لا يُنشأ Operation ID بالتخمين.

| Approval Type ID | Center | Target in 98 | Exact Operation ID | OWN reference | Test ID |
|---|---|---|---|---|---|
| APR-C01-01 | C01 | decideRequirementBaseline / requirement_baseline | OP-C01-016 | OWNER TBD | TBD / OWNER REVIEW |
| APR-C02-01 | C02 | decideDesignApproval / design_document_version | OP-C02-027 | C02/C03 boundary TBD | TBD / OWNER REVIEW |
| APR-C03-01 | C03 | decideNavigationApproval / navigation_graph | OP-C03-038 | C02/C03 boundary TBD | TBD / OWNER REVIEW |
| APR-C04-01 | C04 | decideSchemaApproval / schema_version | OP-C04-062 | OWN72-07 | TBD / OWNER REVIEW |
| APR-C04-02 | C04 | applyMigration / migration_plan | OP-C04-056 | OWN72-07 | TBD / OWNER REVIEW |
| APR-C04-03 | C04 | requestRestore / restore_plan | OP-C04-059 | OWN72-07 / BAK-87 | TBD / OWNER REVIEW |
| APR-C05-01 | C05 | applyApprovedChangeSet / code_change_set | OP-C05-067 | OWNER TBD | TBD / OWNER REVIEW |
| APR-C05-02 | C05 | approveCodeBaseline / code_baseline | OP-C05-084 | OWNER TBD | TBD / OWNER REVIEW |
| APR-C06-01 | C06 | requestToolGrant / tool capability | OP-C06-090 | OWN72-10 | TBD / OWNER REVIEW |
| APR-C06-02 | C06 | reviewAgentOutput / AI output | OP-C06-099 | OWN72-10 + domain owner | TBD / OWNER REVIEW |
| APR-C07-01 | C07 | approveTestResults / QA result | OP-C07-119 | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| APR-C07-02 | C07 | approveQualityWaiver / quality waiver | OP-C07-118 requestQualityWaiver (related request; decision endpoint/SH-03 operation TBD) | OWN36-03 / OWN72-08 | TBD / OWNER REVIEW |
| APR-C08-01 | C08 | requestDeletion / asset deletion | OP-C08-131 | OWN72-01 | TBD / OWNER REVIEW |
| APR-C08-02 | C08 | requestExport / asset export | OP-C08-130 | OWN72-01 | TBD / OWNER REVIEW |
| APR-C09-01 | C09 | startSync / external write | OP-C09-138 | OWN72-02 | TBD / OWNER REVIEW |
| APR-C09-02 | C09 | beginAuthorization / connector scope | OP-C09-134 | OWN72-02 | TBD / OWNER REVIEW |
| APR-C10-01 | C10 | decideSecurityException / security exception | OP-C10-149 | OWN72-03 | TBD / OWNER REVIEW |
| APR-C10-02 | C10 | requestPolicyChange / policy version | OP-C10-148 | OWN72-03 | TBD / OWNER REVIEW |
| APR-C11-01 | C11 | approveRelease / release_candidate | OP-C11-161 | OWN72-06 | TBD / OWNER REVIEW |
| APR-C11-02 | C11 | deployRelease / production deployment | OP-C11-162 | OWN72-06 | TBD / OWNER REVIEW |
| APR-C11-03 | C11 | requestRollback / rollback | OP-C11-165 | OWN72-06 | TBD / OWNER REVIEW |
| APR-C12-01 | C12 | publishKnowledgeDocument / approved version | OP-C12-173 | OWN72-04 | TBD / OWNER REVIEW |
| APR-C12-02 | C12 | exportKnowledgeBundle | OP-C12-176 | OWN72-04 | TBD / OWNER REVIEW |
| APR-C13-01 | C13 | applyOwnerSettingChange | OP-C13-184 | OWN72-05 | TBD / OWNER REVIEW |
| APR-C13-02 | C13 | rollbackSettingVersion | OP-C13-186 | OWN72-05 | TBD / OWNER REVIEW |
| APR-C14-01 | C14 | exportDashboardReport | OP-C14-200 | OWN36-10 / OWN72-09 | TBD / OWNER REVIEW |

## 4. نتائج المطابقة الأولية

- عدد عمليات API: 201.
- عدد العمليات ذات mapping إلى AX مقترح: 201؛ ولا يعني ذلك اعتماد المحاور.
- عدد أنواع الموافقات المرشحة: 26.
- عمليات الموافقات المطابقة حرفياً: 25.
- عمليات الموافقات غير المطابقة حرفياً: 1؛ تتطلب مراجعة المالك.

## 5. ما يلزم لإغلاق كل سجل

1. اعتماد AX Primary وSecondary من مالك المتطلب بالاستناد إلى المصدر الكامل، وليس اسم العملية فقط.
2. إضافة Source Locator دقيق: الفصل/القسم/الفقرة أو Requirement ID، مع حفظ مرجع المصدر الأصلي.
3. استبدال Test family بـExact Test ID أو N/A مبرر ومعتمد، ثم إنشاء حالة اختبار مفصلة: preconditions, input, expected, negative cases, environment, evidence.
4. اعتماد Domain Owner وDomain SoR وAPI Owner وربطها بقرار OWN مؤهل.
5. إكمال request/response/error schemas، types/nullability/constraints، versioning، authorization action/resource/scope، idempotency، concurrency، timeout/retry.
6. ربط كل Approval Type بـOperation ID محدد أو فصل نوع الموافقة إلى عملية/مورد صحيح بعد مراجعة المالك.

## 6. اختبارات جودة المصفوفة

| Test ID | الفحص | معيار القبول | الحالة |
|---|---|---|---|
| TRACE-99-T01 | Operation inventory | 201 سجل، وكل ID فريد | SPECIFIED / NOT RUN |
| TRACE-99-T02 | AX reference validity | كل AX موجود في سجل 57 | SPECIFIED / NOT RUN |
| TRACE-99-T03 | Center/test-family reference | كل مركز مربوط بعائلة اختبار 80 أو TBD مبرر | SPECIFIED / NOT RUN |
| TRACE-99-T04 | OWN qualification | كل مرجع مؤهل أو TBD صريح؛ لا OWN مبهم | SPECIFIED / NOT RUN |
| TRACE-99-T05 | Exact test coverage | لكل عملية Test ID أو N/A معتمد | BLOCKED — exact IDs pending |
| TRACE-99-T06 | Source locator | كل عملية مرتبطة بمقطع مصدر دقيق | BLOCKED — locator pending |
| TRACE-99-T07 | Approval linkage | كل Approval Type مربوط بعملية/مورد صحيح | SPECIFIED / NOT RUN |
| TRACE-99-T08 | Owner approval | AX/owner/schema/auth mappings معتمدة | BLOCKED — owner review |

**الحالة النهائية:** مصفوفة تتبع مشتقة قابلة للمراجعة فقط. لا تغلق GAP-020 أو GAP-027 أو RI-94، ولا تعتمد APIs أو الاختبارات أو الملكيات. لا يوجد تغيير في كود التطبيق.


## 7. مصدر العقد لكل عملية وحدود الاستدلال

أضيف حقل Contract source لكل عملية وفق توزيع المراكز في العقود 59–70: C01→59، C02/C03→60، C04→61، C05→62، C06/C07→63، C08→64، C09→65، C10→66، C11→67، C12→68، C13→69، C14→70. هذا يثبت مرجع العقد الوسيط فقط، ولا يثبت الفقرة الأصلية في DOCX أو متطلباً تفصيلياً بعينه. يبقى section/paragraph/source locator الأصلي TBD إلى حين المطابقة مع فهرس المصدر الكامل ومواضع النص.

يجب عدم اعتبار مصدر العقد الوسيط بديلاً عن المصدر الأصلي؛ كل سجل يحتاج locator إلى موضع محدد في DOCX أو فصل مستعاد عند إغلاق التتبع.



## ملحق 100 — فصل تتبع الأحداث عن عمليات API

تغطي الوثيقة 100 أحداث المجال الـ106 كسجلات مستقلة. لا تخلط Event ID مع Operation ID، ولا تفترض تطابقاً واحداً-إلى-واحد بينهما. أي ربط command→event يحتاج مراجعة مصدر ومالك المجال؛ لم يعتمد هذا الربط تلقائياً.


## تحديث مصدرّي 2026-11-15 — ضبط دلالة AX والاختبارات
تحدد الوثيقتان 103 و104 مواضع المصدر على مستوى المركز وسيناريوهات القبول النصية. لا يكفي locator الفصل لاعتماد Candidate AX mapping لعملية مفردة، ولا يكفي التشابه مع SRC-AT لإنشاء exact test link. تبقى جميع روابط AX المرشحة والاختبارات المفردة بحاجة إلى مراجعة دلالية ومالك.


## تدقيق منشأ الاختبار — الوثيقة 105
تم توثيق 89 Test IDs فريدة في المصدر عبر ثماني عائلات، لكن لا يثبت ذلك أن أياً منها يغطي Operation ID بعينه. تبقى Exact test ID لكل عملية TBD حتى مراجعة نص الاختبار ونتيجته المتوقعة؛ المعرفات المحلية المشتقة لا تصنف SOURCE-ID-RECOVERED.
