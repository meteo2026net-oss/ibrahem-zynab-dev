# 100 — سجل الأحداث المفرد وربطها بمحاور المتطلبات والاختبارات والملكية

**الحالة:** DERIVED EVENT TRACEABILITY / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المراجع:** 57، 74، 79، 80، 82، 96، 99.

## 1. الغرض والمنهج

تفكك هذه الوثيقة صفوف event family في 96 إلى event records منفردة حتى يمكن مراجعة المنتج والمالك والمحور والاختبار لكل حدث على حدة. الأسماء وحالة المصدر من 96؛ لم تُخترع أسماء أحداث جديدة.

محاور AX المدرجة هنا **مرشحة على مستوى المركز** وليست ربطاً دلالياً معتمداً لكل حدث. مراجع OWN وDomain SoR وExact Test ID تبقى TBD ما لم يوجد قرار/دليل مفرد واضح. عائلات EVT-96-T01..12 اختبارات عامة للسجل، وليست اختبارات قبول منفردة للأحداث.

## 2. سجل الأحداث المفردة

| Event ID | Center | Event type | Intermediate source | Candidate AX | Source status | Proposed domain producer | Qualified OWN | Domain SoR | Test IDs |
|---|---|---|---|---|---|---|---|---|---|
| EVT-C01-001 | C01 | `ProjectCreated` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C01-002 | C01 | `DomainAnalysisCompleted` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C01-003 | C01 | `RequirementCreated` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C01-004 | C01 | `RequirementVersionChanged` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C01-005 | C01 | `BaselineSubmitted` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C01-006 | C01 | `BaselineApproved` | 59 | AX-001, AX-004, AX-025 (candidate) | SOURCE-RECOVERED | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C02-001 | C02 | `DesignWorkspaceCreated` | 60/79 | AX-002, AX-006, AX-007 (candidate) | SOURCE-RECOVERED | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C02-002 | C02 | `DesignDocumentVersionSaved` | 60/79 | AX-002, AX-006, AX-007 (candidate) | SOURCE-RECOVERED | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C02-003 | C02 | `DesignValidationCompleted` | 60/79 | AX-002, AX-006, AX-007 (candidate) | SOURCE-RECOVERED | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C02-004 | C02 | `DesignApprovalSubmitted` | 60/79 | AX-002, AX-006, AX-007 (candidate) | SOURCE-RECOVERED | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C02-005 | C02 | `DesignApproved` | 60/79 | AX-002, AX-006, AX-007 (candidate) | SOURCE-RECOVERED | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C03-001 | C03 | `PageRegistered` | 60/79 | AX-008, AX-025 (candidate) | SOURCE-RECOVERED | C03؛ قرار الاعتماد SH-03 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C03-002 | C03 | `RouteDefinitionChanged` | 60/79 | AX-008, AX-025 (candidate) | SOURCE-RECOVERED | C03؛ قرار الاعتماد SH-03 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C03-003 | C03 | `NavigationDiagnosticsCompleted` | 60/79 | AX-008, AX-025 (candidate) | SOURCE-RECOVERED | C03؛ قرار الاعتماد SH-03 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C03-004 | C03 | `NavigationApprovalSubmitted` | 60/79 | AX-008, AX-025 (candidate) | SOURCE-RECOVERED | C03؛ قرار الاعتماد SH-03 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C03-005 | C03 | `NavigationApproved` | 60/79 | AX-008, AX-025 (candidate) | SOURCE-RECOVERED | C03؛ قرار الاعتماد SH-03 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-001 | C04 | `DataSourceRegistered` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-002 | C04 | `DataSourceHealthChanged` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-003 | C04 | `SchemaVersionCreated` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-004 | C04 | `SchemaValidationCompleted` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-005 | C04 | `MigrationPlanCreated` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-006 | C04 | `MigrationApplied` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-007 | C04 | `RowPolicyChanged` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-008 | C04 | `SyncConflictDetected` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-009 | C04 | `BackupCompleted` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C04-010 | C04 | `RestoreVerificationCompleted` | 61/79 | AX-011, AX-042 (candidate) | SOURCE-RECOVERED | C04؛ backup execution قد يعتمد SH-15 | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-001 | C05 | `CodeChangeSetCreated` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-002 | C05 | `CodeGenerationStarted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-003 | C05 | `CodeGenerationCompleted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-004 | C05 | `CodeDiagnosticsCompleted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-005 | C05 | `DependencyScanCompleted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-006 | C05 | `SandboxRunCompleted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-007 | C05 | `BuildPreparationCompleted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-008 | C05 | `GitHubPullRequestCreated` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-009 | C05 | `CodeApprovalSubmitted` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C05-010 | C05 | `CodeBaselineApproved` | 62/79 | AX-028, AX-039, AX-041 (candidate) | SOURCE-RECOVERED | C05؛ SH-03 لقرار الموافقة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-001 | C06 | `AIAgentRegistered` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-002 | C06 | `AIAgentVersionActivated` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-003 | C06 | `AITaskCreated` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-004 | C06 | `AITaskAssigned` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-005 | C06 | `AITaskStateChanged` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-006 | C06 | `AIToolGrantRequested` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-007 | C06 | `AIAgentOutputSubmitted` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-008 | C06 | `AIAgentOutputReviewed` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C06-009 | C06 | `AIAgentConflictDetected` | 63/79 | AX-005, AX-017, AX-018 (candidate) | SOURCE-RECOVERED | C06؛ grant decision من SH-03/C10 بحسب السياسة | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-001 | C07 | `QAPlanCreated` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-002 | C07 | `QATestRunCompleted` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-003 | C07 | `QADefectOpened` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-004 | C07 | `QADefectVerified` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-005 | C07 | `QAQualityGateEvaluated` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C07-006 | C07 | `QATestResultsApproved` | 63/79 | AX-015, AX-044 (candidate) | SOURCE-RECOVERED | C07؛ approval service عند اعتماد النتائج | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-001 | C08 | `AssetRecordCreated` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-002 | C08 | `AssetUploadRequested` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-003 | C08 | `AssetVersionReady` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-004 | C08 | `AssetQuarantined` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-005 | C08 | `AssetScanCompleted` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-006 | C08 | `AssetLinked` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-007 | C08 | `AssetLinkRevoked` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-008 | C08 | `AssetTransformationCompleted` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-009 | C08 | `AssetArchived` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-010 | C08 | `AssetDeletionRequested` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C08-011 | C08 | `AssetDeleted` | 64 | AX-014, AX-046 (candidate) | SOURCE-RECOVERED | C08؛ SH-06 يرسل storage/scan operational evidence | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-001 | C09 | `IntegrationRegistered` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-002 | C09 | `IntegrationAuthorizationStarted` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-003 | C09 | `IntegrationConnected` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-004 | C09 | `IntegrationConnectionFailed` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-005 | C09 | `IntegrationSyncStarted` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-006 | C09 | `IntegrationSyncCompleted` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-007 | C09 | `IntegrationSyncFailed` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-008 | C09 | `IntegrationPaused` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-009 | C09 | `IntegrationRevoked` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C09-010 | C09 | `WebhookRejected` | 65 | AX-022, AX-040 (candidate) | SOURCE-RECOVERED | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-001 | C10 | `PolicyVersionPublished` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-002 | C10 | `SecurityFindingRegistered` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-003 | C10 | `ExceptionRequested` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-004 | C10 | `ExceptionDecided` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-005 | C10 | `AgentCapabilitySuspended` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C10-006 | C10 | `IncidentRecorded` | 79 | AX-023, AX-054, AX-063 (candidate) | PROPOSED | C10؛ SH-02 قرار runtime وSH-04 audit source | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-001 | C11 | `BuildRequested` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-002 | C11 | `BuildStarted` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-003 | C11 | `BuildCompleted` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-004 | C11 | `BuildFailed` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-005 | C11 | `ArtifactAttested` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-006 | C11 | `ReleaseCandidateCreated` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-007 | C11 | `ReleaseApprovalRequested` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-008 | C11 | `ReleaseApproved` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-009 | C11 | `DeploymentStarted` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-010 | C11 | `DeploymentCompleted` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-011 | C11 | `DeploymentFailed` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-012 | C11 | `RollbackRequested` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-013 | C11 | `RollbackCompleted` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C11-014 | C11 | `PostDeployCheckFailed` | 67 | AX-016, AX-043 (candidate) | SOURCE-RECOVERED | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-001 | C12 | `KnowledgeDocumentCreated` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-002 | C12 | `DocumentVersionCreated` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-003 | C12 | `DocumentReviewRequested` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-004 | C12 | `DocumentApproved` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-005 | C12 | `DocumentPublished` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-006 | C12 | `KnowledgeIndexUpdated` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-007 | C12 | `KnowledgeAccessRevoked` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-008 | C12 | `DocumentationDriftDetected` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C12-009 | C12 | `DocumentArchived` | 68 | AX-021, AX-048 (candidate) | SOURCE-RECOVERED | C12؛ SH-16 indexing refs وSH-07 search | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C13-001 | C13 | `SettingChangeRequested` | 79 | AX-034, AX-049 (candidate) | PROPOSED | C13؛ SH-03 approval؛ SH-12 value store | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C13-002 | C13 | `SettingChangeDecided` | 79 | AX-034, AX-049 (candidate) | PROPOSED | C13؛ SH-03 approval؛ SH-12 value store | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C13-003 | C13 | `SettingApplied` | 79 | AX-034, AX-049 (candidate) | PROPOSED | C13؛ SH-03 approval؛ SH-12 value store | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C13-004 | C13 | `SettingVersionRolledBack` | 79 | AX-034, AX-049 (candidate) | PROPOSED | C13؛ SH-03 approval؛ SH-12 value store | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |
| EVT-C14-001 | C14 | `DashboardProjectionRefreshed` | 79 | AX-024, AX-031 (candidate) | PROPOSED | C14 للـprojection فقط | TBD | TBD | EVT-96-T01..12 (generic); exact test TBD |

## 3. نتائج التفكيك

- إجمالي الأحداث المفردة المستخرجة: **106**.
- التوزيع حسب المركز: C01=6، C02=5، C03=5، C04=10، C05=10، C06=9، C07=6، C08=11، C09=10، C10=6، C11=14، C12=9، C13=4، C14=1.
- الأسماء SOURCE-RECOVERED مستعادة من مسودات العقود الوسيطة 59–70، وليست بالضرورة اقتباساً حرفياً من DOCX الأصلي.
- أحداث C10 وC13 وC14 التي صنفتها 96 على أنها PROPOSED تبقى مقترحة ولا ترفع إلى SOURCE-RECOVERED.
- بعد مطابقة حقل الحالة مع عمود مصدر الاسم في 96: SOURCE-RECOVERED=${recovered}، PROPOSED=${proposed}، TBD=${106-recovered-proposed}. لا يُستنتج مصدر مستعاد من حالة OWNER REVIEW.
- مراجع المصادر في الجدول هي مراجع عقود وسيطة من 59–70/79؛ لا تمثل section/paragraph locator في المصدر الأصلي.

## 4. فجوات يجب إغلاقها لكل حدث

1. اعتماد producer domain وDomain SoR وتحديد المنتج الفعلي إذا كان SH-08/09/13/14/15 ينقل الحدث أو يشغّل المهمة فقط.
2. تعيين OWN مؤهل من OWN36-nn أو OWN72-nn بعد اعتماد حدود الملكية؛ لا يكفي تعيين مالك المركز.
3. ربط AX دلالياً بمطلب مفرد ومصدر أصلي (chapter/section/paragraph) بدلاً من fallback المركز.
4. اعتماد event schema/version وpayload classification وresourceRef/resourceVersion وactorRef وscope.
5. تحديد consumers وACL revalidation وidempotency/deduplication وordering وretry/DLQ/replay.
6. تحديد retention وprivacy rules ومراجع provenance.
7. إنشاء exact event-specific test IDs مع preconditions/inputs/expected results/negative cases/evidence.
8. اعتماد events المقترحة في C10/C13/C14 قبل أي نشر.

## 5. اختبارات التتبع والإغلاق

| Test ID | الفحص | معيار القبول | الحالة |
|---|---|---|---|
| EVT-100-T01 | event inventory reconciliation | كل event في 96 ممثل مرة واحدة في 100 | SPECIFIED / NOT RUN |
| EVT-100-T02 | unique event IDs | كل Event ID فريد ولا يعاد استخدامه | SPECIFIED / NOT RUN |
| EVT-100-T03 | source status preservation | SOURCE-RECOVERED/PROPOSED لا تتغير بلا دليل | SPECIFIED / NOT RUN |
| EVT-100-T04 | AX mapping | كل محور موجود في 57؛ mapping reviewed by requirement owner | PARTIAL — candidate only |
| EVT-100-T05 | OWN/SoR | قرار ملكية مؤهل وDomain SoR معتمدان لكل حدث | BLOCKED — owner decisions |
| EVT-100-T06 | exact test coverage | لكل حدث test ID منفرد أو N/A مبرر ومعتمد | BLOCKED — exact IDs |
| EVT-100-T07 | schema and version | schema/version وclassification معتمدة قبل النشر | BLOCKED — schemas absent |
| EVT-100-T08 | runtime delivery safety | idempotency/order/retry/ACL/replay verified | SPECIFIED / NOT RUN |
| EVT-100-T09 | source locator | chapter/section/paragraph لكل حدث | BLOCKED — original locators absent |

## 6. العلاقة مع الوثائق السابقة

- 96 تبقى سجل event families وحدود المنتج/المستهلك.
- 79 تبقى سجل API/event/approval contract العام.
- 99 تغطي عمليات API، بينما 100 تغطي أحداث المجال؛ لا يفترض وجود تطابق واحد-إلى-واحد بين API وevent.
- 80 يظل كتالوج قبول عالي المستوى، ولا تُعتبر عائلاته تغطية تنفيذية للأحداث.
- 36 و72 و82 هي مراجع الملكية؛ لا يجوز حسمها من هذه الوثيقة.

**الخلاصة:** تفكيك inventory إلى 106 سجلاً منفرداً اكتمل على مستوى التسمية والمصدر الوسيط والحالة؛ أما الملكية النهائية والمخططات والاختبارات التفصيلية والاعتماد والتنفيذ فما زالت مفتوحة. لا تنشئ هذه الوثيقة event topics أو consumers أو APIs ولا تغلق RI-94.


## 7. تصحيح تدقيقي لحقل حالة المصدر

في النسخة الأولية من هذا السجل، نُقلت حالة OWNER REVIEW من عمود حالة الصف في 96 إلى حقل Source status، وهو تصنيف غير صحيح. تم تصحيح الحقل هنا بالرجوع إلى عمود مصدر الاسم في 96 لكل حدث على حدة، مع إبقاء حالة مراجعة المالك مستقلة ضمن النص والحقول المفتوحة. هذا التصحيح لا يغير أسماء الأحداث أو مصدرها.
