# 109 — مصفوفة مرشحات الاختبار المصدرية للأحداث المرتبطة بالخدمات المشتركة

**الحالة:** SOURCE-TEST SEMANTIC CANDIDATE MAP / NOT EXACT / OWNER REVIEW REQUIRED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** DOCX المصدر، 80، 91–93، 100، 102، 104، 105، 106، 108.  
**النطاق:** 50 حدثاً من C04/C06/C07/C09/C11/C14.

## 1. الغرض والمنهج

هذه المصفوفة تنقل التتبع من عائلة اختبار عامة إلى **مرشح سيناريو مصدر** لكل حدث، عند وجود تقارب دلالي معقول. لا تدعي أن DOCX يذكر Event ID نفسه أو يربط اختباراً بعينه به. لذلك كل الروابط موسومة `SEMANTIC-CANDIDATE / NOT EXACT`.

تمييز مهم:
- `DATA-xx`, `API-xx`, `AT-xx`, `DASH-INT-xx` معرفات موجودة في المصدر الأصلي.
- `SRC-AT-Cxx-nn` locators محلية في الوثيقة 104 لسيناريوهات غير مرقمة في DOCX؛ ليست Test IDs أصلية.
- أسماء العمليات في الفصل 135 ليست معرفات اختبار.
- كل الحالات NOT RUN؛ لا يوجد run evidence.

## 2. المصفوفة المفردة

| Event ID | Event type | Center | candidate source test/scenario | exact source locator | mapping status |
|---|---|---|---|---|---|
| EVT-C04-001 | `DataSourceRegistered` | C04 | §23.13 unnumbered acceptance scenario; no direct Test ID | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-002 | `DataSourceHealthChanged` | C04 | §23.13 unnumbered acceptance scenario; no direct Test ID | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-003 | `SchemaVersionCreated` | C04 | DATA-13 (migration/schema change); §135.6 validateSchema (operation name, not test ID) | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-004 | `SchemaValidationCompleted` | C04 | DATA-13 (migration/schema change); §135.6 validateSchema (operation name, not test ID) | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-005 | `MigrationPlanCreated` | C04 | DATA-13; DATA-18 | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-006 | `MigrationApplied` | C04 | DATA-13; DATA-18 | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-007 | `RowPolicyChanged` | C04 | DATA-05 (cross-tenant relation); DATA-15 (protected resource access) | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-008 | `SyncConflictDetected` | C04 | DATA-09 (duplicate event side effects) | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-009 | `BackupCompleted` | C04 | DATA-17 (backup restore verification) | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C04-010 | `RestoreVerificationCompleted` | C04 | DATA-12; DATA-17; DATA-18 | DOCX §23.13 lines 3177–3190; DATA tests §134.22 lines 11142–11161 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-001 | `AIAgentRegistered` | C06 | §25.11 unnumbered acceptance scenarios; no direct Test ID | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-002 | `AIAgentVersionActivated` | C06 | SRC-AT-C06-07 (provider/model switch; local locator); AT-06 | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-003 | `AITaskCreated` | C06 | §25.11 unnumbered acceptance scenarios; no direct Test ID | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-004 | `AITaskAssigned` | C06 | SRC-AT-C06-01 (task distribution; local locator); AT-15 (long-running task, architecture-level) | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-005 | `AITaskStateChanged` | C06 | SRC-AT-C06-02 (parallel tasks; local locator); AT-15 | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-006 | `AIToolGrantRequested` | C06 | SRC-AT-C06-06 (permission bypass; local locator); AT-08 | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-007 | `AIAgentOutputSubmitted` | C06 | §25.11 unnumbered acceptance scenarios; no direct Test ID | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-008 | `AIAgentOutputReviewed` | C06 | SRC-AT-C06-08 (independent review; local locator) | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C06-009 | `AIAgentConflictDetected` | C06 | SRC-AT-C06-02 (parallel tasks; local locator) | DOCX §25.11 lines 3461–3473; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-001 | `QAPlanCreated` | C07 | §26.12 unnumbered acceptance scenario; no direct Test ID | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-002 | `QATestRunCompleted` | C07 | SRC-AT-C07-01 (unit test); SRC-AT-C07-02 (integration test); SRC-AT-C07-03 (end-to-end journey) | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-003 | `QADefectOpened` | C07 | SRC-AT-C07-06 (mandatory test failure blocks gate) | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-004 | `QADefectVerified` | C07 | SRC-AT-C07-07 (retest comparison) | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-005 | `QAQualityGateEvaluated` | C07 | SRC-AT-C07-06 (mandatory failure blocks gate) | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C07-006 | `QATestResultsApproved` | C07 | SRC-AT-C07-10 (export/report); §26.12 approval criteria; no numbered ID | DOCX §26.12 lines 3619–3632; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-001 | `IntegrationRegistered` | C09 | §28.8 unnumbered acceptance scenario; no direct Test ID | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-002 | `IntegrationAuthorizationStarted` | C09 | SRC-AT-C09-02 (expired authentication); AT-07 (expired secret) | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-003 | `IntegrationConnected` | C09 | SRC-AT-C09-01 (API connection) | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-004 | `IntegrationConnectionFailed` | C09 | SRC-AT-C09-06 (service failure/retry); API-05 (service unavailable) | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-005 | `IntegrationSyncStarted` | C09 | §28.8 integration sync semantics; unnumbered acceptance scenario | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-006 | `IntegrationSyncCompleted` | C09 | §28.8 integration sync semantics; unnumbered acceptance scenario | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-007 | `IntegrationSyncFailed` | C09 | SRC-AT-C09-06 (service failure/retry); API-05 | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-008 | `IntegrationPaused` | C09 | §28.8 unnumbered acceptance scenario; no direct Test ID | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-009 | `IntegrationRevoked` | C09 | SRC-AT-C09-08 (revoked permission blocks later calls); API-02 (unauthorized request) | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C09-010 | `WebhookRejected` | C09 | SRC-AT-C09-05 (webhook source verification); API-10 (duplicate event) | DOCX §28.8 lines 3811–3821; no source-native Test IDs in this section | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-001 | `BuildRequested` | C11 | §135.9 release/build stage separation; no direct event-specific Test ID | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-002 | `BuildStarted` | C11 | §135.9 build lifecycle; API-05/API-08 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-003 | `BuildCompleted` | C11 | §135.9 build lifecycle; API-05/API-08 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-004 | `BuildFailed` | C11 | §135.9 build lifecycle; API-05/API-08 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-005 | `ArtifactAttested` | C11 | §135.9 release/build stage separation; no direct event-specific Test ID | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-006 | `ReleaseCandidateCreated` | C11 | §135.9 createReleaseCandidate operation (not Test ID) | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-007 | `ReleaseApprovalRequested` | C11 | §135.9 requestDeploymentApproval operation (not Test ID); API-02 generic authorization | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-008 | `ReleaseApproved` | C11 | §135.9 requestDeploymentApproval operation (not Test ID); API-02 generic authorization | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-009 | `DeploymentStarted` | C11 | §135.9 executeApprovedDeployment operation (not Test ID); API-05/API-06 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-010 | `DeploymentCompleted` | C11 | §135.9 executeApprovedDeployment operation (not Test ID); API-05/API-06 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-011 | `DeploymentFailed` | C11 | §135.9 executeApprovedDeployment operation (not Test ID); API-05/API-06 generic contract tests | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-012 | `RollbackRequested` | C11 | §135.9 rollbackDeployment operation (not Test ID); AT-12 recovery architecture test | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-013 | `RollbackCompleted` | C11 | §135.9 rollbackDeployment operation (not Test ID); AT-12 recovery architecture test | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C11-014 | `PostDeployCheckFailed` | C11 | §135.9 post-deploy verification criterion (unnumbered); MON-93 family candidate | DOCX §135.9 lines 11328–11346 and §135.11 lines 11357–11368 | SEMANTIC-CANDIDATE / NOT EXACT |
| EVT-C14-001 | `DashboardProjectionRefreshed` | C14 | DASH-INT-01..10 (source-native integration test family; not exact event mapping); §153.8 freshness/source/ACL criteria | DOCX §153.8 lines 13906–13917; DASH-INT-01..10 | SEMANTIC-CANDIDATE / NOT EXACT |

## 3. قواعد منع الربط الزائف

1. لا يتحول `DATA-17` إلى اختبار exact لحدث BackupCompleted لمجرد وجود كلمة backup؛ يلزم أن يثبت test oracle أنه يغطي event payload/producer/consumer/idempotency والنتيجة المتوقعة.
2. لا تتحول اختبارات API العامة إلى exact test لحدث IntegrationConnected أو DeploymentCompleted دون عقد الحدث نفسه.
3. لا تتحول سيناريوهات القبول غير المرقمة في الفصول 25 و26 و28 إلى Test IDs أصلية؛ تبقى SRC-AT local locators.
4. لا تتحول اختبارات لوحة التحكم DASH-INT إلى اختبارات لكل حدث مصدر؛ هي عائلة تكاملية لعرض الحالة والحداثة والصلاحيات.
5. لا يعني تشابه أسماء الأحداث أو الاختبارات أن Domain producer أو SoR أو API owner قد اعتمد.
6. لا يُسجل PASS دون run ID وcommit/build/environment وartifact evidence ومراجع التنفيذ.

## 4. نتيجة التغطية

- أحداث مفردة في النطاق: 50.
- أحداث لها مرشح مصدر/سيناريو دلالي: 50 (مرشح فقط).
- أحداث لها Exact Event→Test ID mapping معتمد: **0**.
- اختبارات منفذة مثبتة بهذه المصفوفة: **0**.
- قرارات ملكية مغلقة بهذه المصفوفة: **0**.

النتيجة لا تغلق GAP-020 أو GAP-027 أو RI-94-06؛ لكنها تحدد مسار مراجعة مالك لكل حدث وتمنع إعادة استخدام عائلة عامة على أنها تغطية مفردة.

## 5. بوابة الإغلاق

على مالك كل حدث أن يراجع:
- معنى الحدث وDomain SoR والمنتج والمستهلك.
- هل المرشح يغطي شروط الحدث أم لا؟ وما الـtest oracle؟
- هل يلزم Test ID جديد `PROPOSED` أو N/A مبرر؟
- صلاحيات المنتج/المستهلك، عزل المؤسسة، idempotency/replay، schema/version، retention، evidence.
- نتيجة تشغيل فعلية بعد اعتماد المواصفات وإذن البرمجة.

**بوابة التنفيذ:** هذه مصفوفة مراجعة مشتقة فقط؛ لا تنشئ اختبارات منفذة أو أحداثاً أو APIs، ولا تعدل كود التطبيق أو تمنح إذناً ببدء البرمجة.
