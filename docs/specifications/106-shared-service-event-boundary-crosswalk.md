# 106 — مصفوفة حدود الخدمات المشتركة على مستوى الأحداث

**الحالة:** EVENT-LEVEL BOUNDARY CROSSWALK / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** 36، 72، 91، 92، 93، 96، 100، 101، 103، 105.  
**النطاق:** أحداث C04 وC06 وC07 وC09 وC11 وC14 المرتبطة بحدود SH-08/SH-13/SH-14، مع الإشارة إلى SH-15 حيث يظهر النسخ والاستعادة.

## 1. الهدف وحدود الدليل

تفكك هذه الوثيقة المجموعات الست ذات الصلة إلى **50 Event Records** مستخرجة من صفوف الوثيقة 100. لا تنشئ أحداثاً جديدة ولا تغير أسماءها أو حالة مصدرها. وتضيف لكل حدث فصلاً واضحاً بين:
- حقيقة المجال ومالكها المرشح.
- خدمة التنفيذ أو الموصل أو المراقبة التي قد ترتبط به.
- قرار الملكية المؤهل الذي يجب مراجعته.
- عائلة اختبارات الخدمة المشتركة المرشحة، دون الادعاء أنها اختبار حدث مفرد.

**تحذير مصدرّي:** معرفات وأسماء الأحداث في 100 مشتقة من عقود المراكز الوسيطة 59–70، وليست بالضرورة نصاً حرفياً في DOCX الرئيسي. الوثيقة 103 تثبت locators على مستوى المركز فقط. لذلك لا يرفع هذا الملف أي حدث إلى `VERBATIM-SOURCE` أو `APPROVED`.

## 2. التوزيع العددي

| المركز | عدد الأحداث في هذا النطاق |
|---|---:|
| C04 | 10 |
| C06 | 9 |
| C07 | 6 |
| C09 | 10 |
| C11 | 14 |
| C14 | 1 |
| **الإجمالي** | **50** |

## 3. مصفوفة الحدود على مستوى الحدث

| Event ID | Event type | Domain center | shared-service boundary candidate | qualified decision reference | test-family evidence |
|---|---|---|---|---|---|
| EVT-C04-001 | `DataSourceRegistered` | C04 | C04 domain event; no SH-08/13/14 ownership of domain semantics | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | No shared-service exact test mapping |
| EVT-C04-002 | `DataSourceHealthChanged` | C04 | SH-14 telemetry candidate | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | MON-93 family candidate; exact event test TBD |
| EVT-C04-003 | `SchemaVersionCreated` | C04 | C04 domain event; no SH-08/13/14 ownership of domain semantics | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | No shared-service exact test mapping |
| EVT-C04-004 | `SchemaValidationCompleted` | C04 | C04 domain event; no SH-08/13/14 ownership of domain semantics | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | No shared-service exact test mapping |
| EVT-C04-005 | `MigrationPlanCreated` | C04 | C04 domain event; no SH-08/13/14 ownership of domain semantics | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | No shared-service exact test mapping |
| EVT-C04-006 | `MigrationApplied` | C04 | SH-08 execution attempt candidate (not domain result) | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | TASK-92 family candidate; exact event test TBD |
| EVT-C04-007 | `RowPolicyChanged` | C04 | C04 domain event; no SH-08/13/14 ownership of domain semantics | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | No shared-service exact test mapping |
| EVT-C04-008 | `SyncConflictDetected` | C04 | SH-08 execution attempt candidate (not domain result) | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | TASK-92 family candidate; exact event test TBD |
| EVT-C04-009 | `BackupCompleted` | C04 | SH-15 backup/restore execution evidence (outside SH-08/13/14 scope) | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | BAK-87 evidence; exact event test TBD |
| EVT-C04-010 | `RestoreVerificationCompleted` | C04 | SH-15 backup/restore execution evidence (outside SH-08/13/14 scope) | OWN72-07 (C04 vs domain centers); SH-15 boundary separately OPEN | BAK-87 evidence; exact event test TBD |
| EVT-C06-001 | `AIAgentRegistered` | C06 | C06 domain lifecycle; SH-08 only for any linked execution attempt | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-002 | `AIAgentVersionActivated` | C06 | C06 domain lifecycle; SH-08 only for any linked execution attempt | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-003 | `AITaskCreated` | C06 | SH-08 execution attempt/assignment reference candidate | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-004 | `AITaskAssigned` | C06 | SH-08 execution attempt/assignment reference candidate | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-005 | `AITaskStateChanged` | C06 | SH-08 execution attempt/assignment reference candidate | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-006 | `AIToolGrantRequested` | C06 | SH-02/SH-03/C10 authorization and approval; SH-08 only after grant | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-007 | `AIAgentOutputSubmitted` | C06 | C06 domain lifecycle; SH-08 only for any linked execution attempt | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-008 | `AIAgentOutputReviewed` | C06 | C06 domain lifecycle; SH-08 only for any linked execution attempt | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C06-009 | `AIAgentConflictDetected` | C06 | C06 domain lifecycle; SH-08 only for any linked execution attempt | OWN72-10 (agent governance); SH-08 boundary decision TASK-92-01..15 OPEN | TASK-92 family candidate where execution is involved; exact event test TBD |
| EVT-C07-001 | `QAPlanCreated` | C07 | C07 QA domain result; SH-14 telemetry only if a distinct operational signal exists | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C07-002 | `QATestRunCompleted` | C07 | SH-08 test execution attempt; SH-14 runner/platform telemetry | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C07-003 | `QADefectOpened` | C07 | C07 QA domain result; SH-14 telemetry only if a distinct operational signal exists | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C07-004 | `QADefectVerified` | C07 | C07 QA domain result; SH-14 telemetry only if a distinct operational signal exists | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C07-005 | `QAQualityGateEvaluated` | C07 | C07 QA domain result; SH-14 telemetry only if a distinct operational signal exists | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C07-006 | `QATestResultsApproved` | C07 | C07 QA domain result; SH-14 telemetry only if a distinct operational signal exists | OWN36-03 + OWN72-08 (C07 vs SH-14); SH-08 boundary TASK-92 OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C09-001 | `IntegrationRegistered` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-002 | `IntegrationAuthorizationStarted` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-003 | `IntegrationConnected` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-004 | `IntegrationConnectionFailed` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-005 | `IntegrationSyncStarted` | C09 | SH-08 execution attempt; SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-006 | `IntegrationSyncCompleted` | C09 | SH-08 execution attempt; SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-007 | `IntegrationSyncFailed` | C09 | SH-08 execution attempt; SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-008 | `IntegrationPaused` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-009 | `IntegrationRevoked` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C09-010 | `WebhookRejected` | C09 | SH-13 connector runtime/connection refs; SH-09 transport | OWN72-02 (C09 vs SH-13) OPEN | INT-91 family candidate; TASK-92 for sync execution; exact event test TBD |
| EVT-C11-001 | `BuildRequested` | C11 | C11 domain lifecycle; SH-08 only when an execution attempt exists | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-002 | `BuildStarted` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-003 | `BuildCompleted` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-004 | `BuildFailed` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-005 | `ArtifactAttested` | C11 | SH-10 version metadata candidate; artifact registry decision ART-95 OPEN | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-006 | `ReleaseCandidateCreated` | C11 | C11 domain lifecycle; SH-08 only when an execution attempt exists | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-007 | `ReleaseApprovalRequested` | C11 | C11 domain lifecycle; SH-08 only when an execution attempt exists | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-008 | `ReleaseApproved` | C11 | C11 domain lifecycle; SH-08 only when an execution attempt exists | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-009 | `DeploymentStarted` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-010 | `DeploymentCompleted` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-011 | `DeploymentFailed` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-012 | `RollbackRequested` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-013 | `RollbackCompleted` | C11 | SH-08 execution attempt; C11 retains build/deployment/rollback outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C11-014 | `PostDeployCheckFailed` | C11 | SH-14 telemetry/alert evidence; C11 owns release/deployment check outcome | OWN72-06 (C11 vs SH-08/10/14/15) OPEN | TASK-92 and MON-93 families candidate; exact event test TBD |
| EVT-C14-001 | `DashboardProjectionRefreshed` | C14 | C14 read projection only; SH-14 may supply freshness/health evidence; metric SoR remains source center | OWN36-10 + OWN72-09 OPEN | MON-93 family candidate; exact projection test TBD |

## 4. قواعد القرار التي تحكم كل صف

### SH-08 — تنفيذ المهام
- Domain Job وتعريفه ومعنى نجاحه ونتيجته الدلالية تبقى لدى المركز المنتج.
- SH-08 يحتفظ بسجل execution job ومحاولاته والعامل والجدولة والتقدم والإلغاء وإعادة المحاولة.
- لا يحول نجاح attempt إلى نجاح المجال، ولا يصدر أحداث المجال نيابة عن C06/C07/C09/C11/C04.
- إعادة التحقق من actor/grant/scope مطلوبة عند التنفيذ لا عند الإدراج فقط.
- حالات CancelRequested/Cancelled وSucceeded/DomainAccepted لا تدمج.

### SH-13 — Runtime الموصلات
- C09 يملك integration intent/mapping/sync direction/conflict policy وقرار قبول أثر المزامنة.
- SH-13 يملك connector runtime/manifest/connection lifecycle/health refs، وفق قرار OWN72-02.
- Secret Manager المعتمد يملك قيمة السر؛ لا تحفظ قيمة خام في manifest أو log أو event.
- نجاح الاتصال لا يعني نجاح mapping أو قبول بيانات المجال.
- إبطال التكامل يجب أن يوقف الاستدعاءات والمهام المؤجلة بعد إعادة التحقق من الصلاحيات.

### SH-14 — المراقبة التشغيلية
- SH-14 يملك health checks/metrics/traces/alerts/incident telemetry.
- C07 يملك test plan/result/defect/quality gate؛ C11 يملك build/release/deployment outcomes؛ C04 يملك جودة البيانات والمخطط.
- صحة runner لا تعني نجاح الاختبار، وصحة الخدمة لا تعني صحة بيانات المجال.
- C14 يعرض freshness/source/ACL ولا يصبح SoR للمؤشر.

### SH-15 — النسخ والاستعادة (حد خارج نطاق الخدمات الثلاث لكنه يتقاطع معها)
- SH-15 يملك backup/restore job catalog ومحاولات التنفيذ والأدلة التشغيلية.
- C04/C11 يملكان صلاحية الاستعادة الدلالية والتحقق من البيانات أو الإصدار.
- BackupCompleted لا يساوي RestoreVerificationCompleted؛ نجاح job لا يثبت سلامة المجال.

## 5. عائلات الاختبار المرشحة وحدودها

| الحزمة | العائلة | ماذا تختبر | ما لا تثبته |
|---|---|---|---|
| 92 | TASK-92-T01..T14 | idempotency، التفويض وقت التنفيذ، worker loss، الإلغاء، dependency cycle، العزل، retry، الفصل بين التنفيذ والنتيجة | لا تغطي كل حدث من أحداث C06/C07/C09/C11/C04 تلقائياً |
| 91 | INT-91-T01..T12 | SSRF/OAuth/secret/webhook/replay/mapping approval/re-authorize/revoke/tenant/retry/health | لا تغطي كل حدث C09 أو كل API |
| 93 | MON-93-T01..T14 | صحة المراقبة، freshness، alerts، عزل tenant، عدم تسريب البيانات، فصل health عن QA/release | لا تغطي كل event أو metric مفرداً |
| 87 | اختبارات النسخ والاستعادة في حزمة SH-15 | backup catalog وrestore evidence وsemantic verification | لا تمنح SH-15 ملكية بيانات C04/C11 |

كل هذه الاختبارات SPECIFIED / NOT RUN أو OPEN/BLOCKED. عائلة الاختبار تعني مجموعة تحقق مرشحة، لا exact Test ID mapping.

## 6. سجل قرارات الملكية المفتوحة

| القرار المؤهل | النطاق | الأطراف التي يجب أن تعتمد |
|---|---|---|
| OWN72-02 | C09 مقابل SH-13 | مالك C09، مالك SH-13، C10 |
| OWN72-06 | C11 مقابل SH-08/10/14/15 | مالك C11 وملاك SH-08/10/14/15 وC05/C07/C10 بحسب الأثر |
| OWN72-08 | C07 مقابل SH-14 | مالك C07 ومالك SH-14 |
| OWN36-03 | C07 مقابل SH-14 | مالك C07 ومالك SH-14؛ لا يدمج مع OWN72-08 |
| OWN72-07 | C04 مقابل مراكز المجال | مالك C04 وملاك المراكز المتأثرة |
| OWN72-10 | C06 مقابل حوكمة الوكلاء | مالك C06 وC10 وملاك SH-02/03/04 |
| OWN36-10 + OWN72-09 | C14 projection مقابل مالكي المؤشرات | مالك C14 وملاك مصادر المقاييس |
| SH15-BOUNDARY-01 | backup/restore evidence مقابل semantic restore | ملاك C04/C11/C08 وSH-15 وC10 |

لا يحسم هذا الملف أي قرار. جميعها OPEN حتى تسجيل موافقة مالك ودليلها في سجل القرار المؤهل.

## 7. مخرجات الإغلاق التالية

1. تحويل كل صف إلى قرار Producer/Consumer نهائي مع actor/action/resource/scope.
2. تعيين exact test ID مفرد أو N/A مبرر من مالك الحدث بعد مراجعة oracle والسيناريو.
3. إضافة source paragraph locator لكل حدث إذا كان نص المصدر يذكر انتقال المجال فعلاً؛ وإلا يبقى DERIVED-NOT-VERBATIM.
4. تحديد schemas/versioning/idempotency/retry/timeout/error/retention لكل عقد بعد الاعتماد.
5. ربط كل event/API بقرار OWN مؤهل ومحور AX بعد مراجعة دلالية، لا بمطابقة كلمات آلية.
6. إبقاء الاختبارات NOT RUN حتى توفر run ID وcommit/build/environment/evidence.

**بوابة التنفيذ:** لا تنشئ هذه الوثيقة APIs أو event topics أو schemas أو workers أو connectors أو monitoring rules. لا تعدل كود التطبيق ولا تمنح إذناً ببدء البرمجة.


## تحديث مصدر أسماء العمليات — الوثيقة 108
أضيفت الوثيقة 108 لتدقيق أسماء عمليات API ذات الصلة. لا يثبت وجود حدث في هذه المصفوفة أن العملية المرتبطة به تحمل اسماً أصلياً في DOCX؛ تبقى أسماء أحداث 100 ومراجع 106 مشتقة من العقود الوسيطة ما لم يوجد locator دلالي مفرد.


## مرشحات الاختبار المصدرية — الوثيقة 109
تحدد الوثيقة 109 مرشحات اختبار/سيناريو مصدر لكل حدث في هذه المصفوفة، مع locators وتصنيف NOT EXACT. لا تعني هذه المرشحات وجود تغطية معتمدة أو اختبار منفذ.
