# 132 — حزمة الإغلاق التتبعي الموحدة للمراكز C01–C07

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / NOT READY FOR IMPLEMENTATION  
**النطاق:** استكمال المراجعة التفصيلية للمراكز C01–C07 وربطها بسجلات العمليات 97 والأحداث 100 والموافقات 98 والاختبارات والعقود 59–63.  
**قاعدة حاكمة:** هذه الحزمة لا تعدل التطبيق ولا تعتمد API أو schema أو صلاحيات أو سياسات ولا تحول أي اختبار موصوف إلى اختبار منفذ.

## 1. النتيجة التنفيذية

| محور التتبع | C01–C07 | الحالة |
|---|---:|---|
| عمليات API/Command/Query في سجل 97 | 120 | جميعها مرشحة؛ لا exact Test ID مفرد مثبت |
| أحداث المجال في سجل 100 | 51 | SOURCE-RECOVERED دلالياً، لا يساوي تطابق اسم حرفي في المصدر |
| أنواع الموافقات في سجل 98 | 12 | كلها OWNER REVIEW |
| سيناريوهات القبول المحلية/المسودات | 179 تقريباً | مواصفات مقترحة وليست نتائج تشغيل |
| exact Event→Test ID مثبت | 0/51 | غير مثبت |
| موافقة مالك نهائية للمراكز السبعة | 0 موثقة هنا | غير معتمدة |
| تنفيذ اختبارات أو build أو نشر | لا يوجد دليل هنا | NOT RUN / NOT VERIFIED |

الأعداد مشتقة من سجلات 97 و98 و100 ومسودات 59–63. عدد 179 هو مجموع مجموعات الاختبارات المدرجة في المسودات الحالية (C01=24، C02=20، C03=20، C04=28، C05=32، C06=27، C07=28)؛ وقد تتداخل بعض السيناريوهات أو تختلف معرفاتها عن المصدر، لذا لا يستخدم كعدد اختبارات فريدة منفذة.

## 2. جرد المراكز والعمليات والأحداث والموافقات

| المركز | مسودة العقد | عمليات سجل 97 | أحداث سجل 100 | موافقات سجل 98 | AX المرشحة | أهم فجوة |
|---|---:|---:|---:|---:|---|---|
| C01 إنشاء المشروع وتحليل المتطلبات | 59 | 16 (OP-C01-001..016) | 6 (EVT-C01-001..006) | 1 (APR-C01-01) | AX-001/005/025 وغيرها | OWN مفرد ومالك قرار baseline وحقول requirement |
| C02 مساحة التصميم | 60 | 12 (OP-C02-017..028) | 5 (EVT-C02-001..005) | 1 (APR-C02-01) | AX-006/007 | حدود C02/C03 وIR والتصميم والمكونات |
| C03 الصفحات والتنقل | 60 | 11 (OP-C03-029..039) | 5 (EVT-C03-001..005) | 1 (APR-C03-01) | AX-008/010 | حراس المسارات وتكامل C10 واعتماد graph/hash |
| C04 البيانات وقواعد البيانات | 61 | 23 (OP-C04-040..062) | 10 (EVT-C04-001..010) | 3 (APR-C04-01..03) | AX-011/037/042 | مزودو DB وRLS/migrations وRTO/RPO وملكية restore |
| C05 محرك البرمجة | 62 | 23 (OP-C05-063..085) | 10 (EVT-C05-001..010) | 2 (APR-C05-01..02) | AX-028/039/041 | Domain/API Owner وIR وsandbox وGitHub والتبعيات |
| C06 فريق الذكاء الاصطناعي | 63 | 21 (OP-C06-086..106) | 9 (EVT-C06-001..009) | 2 (APR-C06-01..02) | AX-005/017/018 | تفويض الأدوات والذاكرة والتقييم وتعارض المصالح |
| C07 الاختبارات وضمان الجودة | 63 | 14 (OP-C07-107..120) | 6 (EVT-C07-001..006) | 2 (APR-C07-01..02) | AX-015/044 | قرار استثناء الجودة مفقود كعملية مستقلة |

**مجموع العمليات = 120، مجموع الأحداث = 51، مجموع الموافقات = 12.** هذه أعداد سجلات تتبع وليست إثباتاً على وجود خدمات أو endpoints عاملة.

## 3. قواعد مشتركة واجبة على C01–C07

1. كل عملية تتطلب actor وtenant scope وresource scope وauthorization decision من جهة الخادم؛ لا تثق بمعرفات tenant/project التي يرسلها العميل وحدها.
2. كل تغيير مؤثر ينشئ version/change set وبصمة مرتبطة، ويحتفظ بالنسخة السابقة ولا يستبدلها بصمت.
3. فصل Query عن Command وعن Job وعن Approval decision؛ أسماء العمليات في سجل 97 مرشحة وليست تصميماً نهائياً لمسارات HTTP.
4. AI agent ينتج اقتراحاً أو draft أو task output. لا يملك صلاحية اعتماد ناتجه أو منح نفسه أدوات أو تنفيذ أثر محمي دون تفويض وموافقة مستقلة عند اللزوم.
5. لا تكون الجهة التي أنشأت التغيير هي المعتمد الوحيد له عندما تتطلب السياسة فصل الواجبات.
6. كل artifact أو test result أو approval يرتبط بإصدار المصدر/commit/build hash وبـactor ووقت وevidence refs.
7. Domain SoR يبقى عند المركز المالك؛ الخدمات المشتركة لا تنقل ملكية البيانات أو قرار المجال إليها.
8. idempotency صريح في عمليات الإنشاء والتطبيق والمهام والأحداث، مع معالجة التكرار والتزامن.
9. حالات failure/partial/unknown لا تعرض على أنها success؛ ويجب وجود reconciliation عندما لا يمكن تأكيد نتيجة العملية.
10. كل اختبارات هذه الحزمة تظل NOT RUN حتى يوجد run ID وcommit/build/environment ونتائج وأدلة قابلة للتحقق.

## 4. تفصيل حدود الملكية والتبعيات حسب المركز

### 4.1 C01 — إنشاء المشروع وتحليل المتطلبات

- C01 يملك project profile وidea/requirement versions وpersonas وNFRs وgaps وclarifications وestimates وrequirement baseline.
- المؤسسة ومساحة العمل لهما حدود ملكية ونطاق؛ يجب إثبات ارتباط المشروع بهما والتحقق من العضوية من جهة الخادم.
- الملفات المرجعية bytes تظل لدى C08/SH-06؛ C01 يحفظ asset refs وhash وscan status وsource provenance.
- AI domain analysis ينتج findings/assumptions/confidence/evidence refs؛ لا يغلق gap أو يعتمد requirement أو technology choice تلقائياً.
- acceptance criteria تملكها C01 كمتطلبات؛ test implementation/evidence تملكها C07.
- APR-C01-01 يربط snapshot ثابتاً من requirement versions وdecision refs وgap refs. لا يقبل تعديل بعد الموافقة على نفس النسخة.

**العمليات:** OP-C01-001..016. **الأحداث:** ProjectCreated، DomainAnalysisCompleted، RequirementCreated، RequirementVersionChanged، BaselineSubmitted، BaselineApproved. **الموافقة:** APR-C01-01 decideRequirementBaseline.

**الإغلاق يتطلب:** اعتماد الحقول الكاملة للمتطلبات والإصدارات والعلاقات، حالات lifecycle، مصدر NFR والقياس، قواعد تقدير التكلفة والثقة، سياسات الخصوصية والاحتفاظ، owner/approver للـbaseline، والربط مع C02/C07/C08.

### 4.2 C02 — مساحة التصميم

- C02 يملك design documents/versions/elements/layers/components/tokens/interactions/states/previews/reviews.
- كل إصدار تصميم ثابت version/hash؛ التعديل من preview ينتج change set قابلاً للمراجعة والتراجع.
- C02 لا يملك سجل الصفحات والـroutes النهائي (C03)، ولا بيانات المجال (C04)، ولا الكود التنفيذي (C05).
- تحديث component definition لا يغير استخداماته في مشاريع أخرى بصمت؛ يلزم version compatibility ومراجعة impact.
- AI design proposal يحتفظ بالمصدر والـdiff؛ لا يطبق على نسخة تغيرت بعد إنشاء الاقتراح.
- أصول التصميم تبقى لدى C08، وC02 يحفظ asset refs لا نسخاً غير محكومة.

**العمليات:** OP-C02-017..028. **الأحداث:** DesignWorkspaceCreated، DesignDocumentVersionSaved، DesignValidationCompleted، DesignApprovalSubmitted، DesignApproved. **الموافقة:** APR-C02-01 decideDesignApproval.

**الإغلاق يتطلب:** اعتماد design IR/serialization وresponsive coordinate system وcomponent schemas وundo/redo/collaboration/conflict semantics وpreview fidelity وcompatibility policy، وحسم حدود الصفحة/التنقل مع C03.

### 4.3 C03 — الصفحات والتنقل

- C03 يملك page registry وroutes وroute parameters وnavigation graph وmenus/deep links/journeys/diagnostics/approved graph.
- page identity ثابت ولا يعاد استخدامه بعد الحذف؛ rename لا ينشئ صفحة جديدة.
- وجود عنصر في القائمة ليس شرطاً لصحة الوصول؛ route guard يجب أن يفرض التفويض خادمياً عبر C10/SH-02.
- C03 يحتفظ policy refs ولا يملك تعريف السياسة أو هوية المستخدم.
- graph approval يثبت graph version/hash؛ أي تغيير جوهري بعد الموافقة يبطلها.
- deep links لا تتجاوز tenant/resource authorization.

**العمليات:** OP-C03-029..039. **الأحداث:** PageRegistered، RouteDefinitionChanged، NavigationDiagnosticsCompleted، NavigationApprovalSubmitted، NavigationApproved. **الموافقة:** APR-C03-01 decideNavigationApproval.

**الإغلاق يتطلب:** route precedence/wildcard/fallback/back-stack، universal/deep link rules، graph cycle semantics، guard contract مع C10، offline navigation، accessibility، platform routing، وقرار حدود C02/C03.

### 4.4 C04 — البيانات وقواعد البيانات

- C04 يملك data-source registry وschema/entity/field/relations/constraints/indexes/row policies/bindings/query definitions/migration plans.
- بيانات الأعمال التي تنشئها التطبيقات تملكها نطاقات المجالات ذات الصلة؛ C04 يملك تعريف المخطط والموصل لا كل محتوى المجال.
- RLS وtenant isolation يجب اختبارهما لكل عملية read/write/export وليس الاكتفاء بوجود policy.
- migration الإنتاجية تحتاج plan + dry-run + backup/rollback strategy + approval من مالك البيانات والبيئة.
- backup execution والتخزين قد يتقاطعان مع SH-15؛ لكن Domain SoR يظل لدى مالك المجال، ولا يكفي نجاح backup لإثبات restore صالح.
- restore يتطلب التحقق من integrity وtenant isolation واتساق النسخ والاعتماديات قبل إعلان success.
- offline sync conflict policy لا تحسم بآخر كتابة تلقائياً.

**العمليات:** OP-C04-040..062. **الأحداث:** DataSourceRegistered، DataSourceHealthChanged، SchemaVersionCreated، SchemaValidationCompleted، MigrationPlanCreated، MigrationApplied، RowPolicyChanged، SyncConflictDetected، BackupCompleted، RestoreVerificationCompleted. **الموافقات:** APR-C04-01 schema/row policy؛ APR-C04-02 applyMigration؛ APR-C04-03 requestRestore.

**الإغلاق يتطلب:** قائمة مزودي DB وإصداراتهم وقدراتهم، schema registry، data type mapping، SQL/migration compatibility، locking/transactions، RLS/ABAC، secrets، offline sync، retention/deletion، backup/restore architecture وRTO/RPO واختبارات استعادة فعلية.

### 4.5 C05 — محرك البرمجة

- C05 يملك workspace/file change sets وIR generation/validation وcode generation/adapters/dependency analysis/diagnostics/sandbox/code baseline.
- كل تغيير ينتج diff على base commit/hash ويعرض للمراجعة؛ لا overwrite لتعديل يدوي.
- IR وسيط مستقل عن Angular UI وعن framework المستهدف، ويحتاج schema/version/migration policy.
- sandbox لا يملك network egress أو وصولاً خارج workspace افتراضياً؛ أي استثناء يطلب ويعتمد وفق السياسة.
- dependencies تخضع لـSBOM/license/security policy؛ لا تحدث تلقائياً في baseline.
- GitHub branch protection وpermissions تبقى في GitHub/SH integration؛ C05 يطلب الإجراء ولا يتجاوز رفض المنصة.
- code approval لا تعني QA approval ولا release/deployment approval.

**العمليات:** OP-C05-063..085. **الأحداث:** CodeChangeSetCreated، CodeGenerationStarted، CodeGenerationCompleted، CodeDiagnosticsCompleted، DependencyScanCompleted، SandboxRunCompleted، BuildPreparationCompleted، GitHubPullRequestCreated، CodeApprovalSubmitted، CodeBaselineApproved. **الموافقات:** APR-C05-01 applyApprovedChangeSet؛ APR-C05-02 approveCodeBaseline.

**الإغلاق يتطلب:** تعيين Domain/API Owner (TBD في سجل 97)، اعتماد IR وadapter contracts، sandbox isolation/runtime/quotas، network/secret policy، merge/review strategy، dependency/SBOM/license gates، GitHub permissions، وsource-to-test traceability.

### 4.6 C06 — فريق الذكاء الاصطناعي

- C06 يملك agent definitions/versions/skills/task orchestration/checkpoints/output review/conflict records.
- Orchestrator ينسق ولا يملك صلاحيات مفتوحة؛ كل agent يعمل تحت actor delegation وresource scope وtool grant محدد ومدة صلاحية.
- APR-C06-01 يحدد الأداة والعمليات والموارد والحدود والوقت والميزانية وقيود الشبكة والبيانات.
- APR-C06-02 لا تنقل ملكية المورد من مركز المجال؛ صاحب المورد يقرر تطبيق المخرج.
- agent memory لا تصبح SoR لبيانات المجالات، ويجب تحديد retention/isolation/deletion and retrieval ACL.
- checkpoints/resume يعيدان فحص صلاحية التفويض والسياسات قبل استئناف أي عمل.
- مزود AI غير متاح أو نموذج متقاعد لا يبرر اختيار بديل يخالف الخصوصية أو الميزانية أو policy.

**العمليات:** OP-C06-086..106. **الأحداث:** AIAgentRegistered، AIAgentVersionActivated، AITaskCreated، AITaskAssigned، AITaskStateChanged، AIToolGrantRequested، AIAgentOutputSubmitted، AIAgentOutputReviewed، AIAgentConflictDetected. **الموافقات:** APR-C06-01 requestToolGrant؛ APR-C06-02 reviewAgentOutput.

**الإغلاق يتطلب:** agent manifest/schema، capabilities matrix، delegation expiry/revocation، memory scope and retention، model/provider registry، evaluation and safety thresholds، cost budgets، audit integrity، conflict resolution، kill switch، وموافقة مالك المورد على الأدوات عالية الأثر.

### 4.7 C07 — الاختبارات وضمان الجودة

- C07 يملك test plans/cases/runs/fixtures/defects/quality gates/results/evidence references.
- C07 يستهلك requirement/design/schema/code/build artifacts ولا يصبح مالكها.
- كل test result يرتبط بـcommit/build hash وenvironment/config/fixture versions وlogs/artifact refs.
- PASS لا يصح إذا كانت الأدلة ناقصة أو stale أو لا تطابق build hash.
- Fixed defect لا يصبح Verified دون retest.
- Quality waiver مؤقت ومحدد scope/reason/owner/expiry/risk acceptance، ولا يتجاوز finding أمنياً أو قانونياً دون صلاحية مالك السياسة.
- يجب فصل test executor عن approver عند الحاجة.

**العمليات:** OP-C07-107..120. **الأحداث:** QAPlanCreated، QATestRunCompleted، QADefectOpened، QADefectVerified، QAQualityGateEvaluated، QATestResultsApproved. **الموافقات:** APR-C07-01 approveTestResults؛ APR-C07-02 approveQualityWaiver.

**فجوة حرجة:** سجل 98/116 يشير إلى approveQualityWaiver لكن لا توجد عملية مطابقة في سجل 97. العملية الموجودة requestQualityWaiver هي OP-C07-118، وهي طلب وليست قرار موافقة. يجب على مالك C07 اعتماد اسم/عملية قرار مستقلة، وتحديد صاحب القرار ودور المراجع المستقل وارتباطها بـSH-03. لا يجوز اعتبار OP-C07-118 بديلاً.

**الإغلاق يتطلب:** test case granularity لكل عملية وحدث وموافقة، test oracle، evidence schema، environments/fixtures، coverage policy، quality thresholds، waiver lifecycle، expiry/revocation، وربط findings الأمنية والجودة والإصدار.

## 5. دورة الموافقة الموحدة: الطلب والقرار والتطبيق والتحقق

يجب أن تكون هذه مراحل منفصلة:
1. Request: إنشاء طلب يحدد الفعل المحمي والمورد والنطاق والنسخة/hash والأثر والمبررات والأدلة.
2. Validate: SH-02/SH-03 يتحقق من actor والpolicy والـtenant والـapprover eligibility وفصل الواجبات.
3. Decide: Domain Decision Owner المعتمد يصدر approve/reject/request-changes مع reason/evidence.
4. Apply: المركز المالك ينفذ الفعل بعد التحقق من أن القرار ما زال صالحاً ويطابق hash/scope.
5. Verify: المركز المالك وC07 يثبتان نتيجة التطبيق ويصدران evidence refs.
6. Revoke/expire: عند تغير المورد أو انتهاء المدة أو سحب التفويض، يمنع تنفيذ القرار القديم.

SH-03 هو محرك دورة الموافقة وليس صاحب قرار المجال. قرار C04 migration أو C05 code أو C07 waiver يبقى عند المالك المعتمد.

## 6. قائمة فجوات المالك والحدود

| Gap | المركز/العلاقة | المطلوب | الحالة |
|---|---|---|---|
| OWN-C01-01 | C01 | Domain/API Owner مفرد لكل كيان وعملية رئيسية | OPEN |
| OWN-C01-02 | C01/SH-03 | Baseline Decision Owner وApprover Role | OPEN |
| OWN-C02-03 | C02/C03 | الفصل بين design page/artboard وpage registry | OPEN |
| OWN-C02-04 | C02/C05 | اعتماد design IR/codegen contract | OPEN |
| OWN-C03-05 | C03/C10 | route guard policy ownership and enforcement | OPEN |
| OWN-C04-06 | C04/SH-15 | backup/restore orchestration مقابل Domain SoR | OPEN |
| OWN-C04-07 | C04/C10 | schema/row policy/security owner | OPEN |
| OWN-C05-08 | C05 | Domain/API Owner للعملية والـworkspace والـIR | OPEN |
| OWN-C05-09 | C05/C11 | حدود build preparation مقابل release/deployment | OPEN |
| OWN-C06-10 | C06/C10/SH-03 | tool grant owner, scope, revocation, independent approver | OPEN |
| OWN-C06-11 | C06/Domain Owners | owner approval for agent output applying to domain resources | OPEN |
| OWN-C07-12 | C07/SH-03 | قرار APR-C07-02 وعملية approveQualityWaiver المفقودة | OPEN |
| OWN-C07-13 | C07/C10/C11 | waiver rules and forbidden critical findings | OPEN |
| OWN-C07-14 | C07/C05/C11 | test evidence/build hash/release gate contract | OPEN |

هذه معرفات فجوات محلية في هذه الحزمة وليست بدائل عن سجلات OWN72/OWN36؛ يجب توحيدها مع السجل الأصلي بعد مراجعة المالك.

## 7. خطة ربط الاختبارات وإغلاقها

يجب توسيع السجل 80/97/99/100 بحيث يحمل لكل عملية/حدث/موافقة:
- Test ID فريداً أو N/A مبرراً من مالك المجال.
- مصدر الاختبار الأصلي وموضعه أو وسمه LOCAL-DERIVED.
- Preconditions/actor/tenant/resource scope.
- Positive, negative, authorization, concurrency, idempotency, failure/recovery paths.
- Oracle واضح ونتيجة متوقعة قابلة للقياس.
- Environment, build/commit hash, fixture/data version.
- Run ID, status, logs/artifact refs, reviewer and timestamp.

**الحالة الحالية:** لا يوجد في سجل 100 exact Event→Test ID مفرد مثبت لأي من أحداث C01–C07 (0/51). كما أن مراجع test groups في سجل 97 لا تثبت تغطية عملية واحدة بعينها. تبقى جميع سيناريوهات المسودات SPECIFIED / NOT RUN.

## 8. سيناريوهات تكامل إضافية ذات أولوية

| Local Test ID | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C01-INT-01 | مستخدم ينشئ مشروعاً في workspace خارج tenant | رفض خادمي دون تسريب |
| C01-INT-02 | AI يحلل ملفاً مرجعياً يحوي تعليمات غير موثوقة | يعامل كبيانات لا كتعليمات تنفيذ |
| C01-INT-03 | baseline يتغير بعد تقديمه للموافقة | إبطال الطلب وإعادة التقديم |
| C02-INT-01 | AI proposal مبني على design version قديم | رفض التطبيق أو طلب rebase |
| C02-INT-02 | تحديث shared component يؤثر على مشاريع أخرى | impact report وعدم التغيير الصامت |
| C02-INT-03 | تعديل preview متزامن مع تعديل يدوي | كشف conflict وحفظ التعديل اليدوي |
| C03-INT-01 | hidden route يستدعى مباشرة دون صلاحية | رفض خادمي |
| C03-INT-02 | deep link يوجه لمورد من tenant آخر | رفض وعدم تسريب بيانات |
| C03-INT-03 | graph approval ثم تغيير route | إبطال approval |
| C04-INT-01 | RLS يسمح read ويمنع write | نجاح read ورفض write مع اختبار مستقل |
| C04-INT-02 | migration يفشل بعد تطبيق جزئي | state=unknown/partial مع reconciliation؛ لا إعلان success |
| C04-INT-03 | restore يكتمل لكن integrity check يفشل | عدم إعلان restore verified |
| C05-INT-01 | codegen يكتب خارج allowlist | رفض وتسجيل |
| C05-INT-02 | sandbox يحاول network egress أو قراءة secrets | رفض افتراضي |
| C05-INT-03 | patch base hash تغير قبل apply | رفض stale patch وطلب rebase |
| C06-INT-01 | agent يستأنف checkpoint بعد انتهاء tool grant | إعادة تحقق ورفض التنفيذ |
| C06-INT-02 | agent يحاول self-approve output أو tool grant | رفض |
| C06-INT-03 | مزود AI بديل يخالف data residency/budget | عدم التحويل التلقائي |
| C07-INT-01 | test result بلا build hash أو artifact | لا تقبل كدليل |
| C07-INT-02 | quality waiver انتهى أو تغير scope | لا يتجاوز gate |
| C07-INT-03 | QA agent يعتمد نتيجة نفذها وحده | طلب reviewer مستقل |

هذه السيناريوهات محلية مشتقة، SPECIFIED / NOT RUN، وتحتاج مراجعة C01–C07 وC10.

## 9. التوافق مع تدقيقات التتبع السابقة

- سجل 97 يحصي 201 عملية على المراكز الأربعة عشر؛ هذه الحزمة تفصل أول 120 عملية C01–C07 ولا تغير بقية المراكز.
- سجل 98 يحصي 26 موافقة مرشحة؛ هذه الحزمة تفصل أول 12 منها.
- سجل 100 يحصي 106 أحداث؛ هذه الحزمة تفصل 51 حدثاً C01–C07.
- تدقيق 114 وجد 11 تطابقاً حرفياً فقط بين 201 اسم عملية وأسماء المصدر، و7 مرشحات دلالية، و183 دون تطابق حرفي. هذه نتيجة شاملة وليست نسبة خاصة بكل مركز؛ لا يجوز إسقاطها على C01–C07 دون تدقيق تفصيلي.
- تدقيق 115 لم يجد أسماء أحداث حرفية مطابقة لأسماء سجل 100؛ لذلك SOURCE-RECOVERED تعني استرداد المفهوم/الحدث دلالياً لا الاسم الحرفي.
- تدقيق 116 أثبت أن APR-C07-02 لا يقابل عملية قرار في سجل 97؛ requestQualityWaiver ليس approveQualityWaiver.
- تدقيق 125 أثبت أن مسودات 59–63 تحتوي AX references صريحة لكنها جزئية، ولا يعني ذلك اكتمال تغطية المحاور أو الحقول أو الاختبارات.
- تدقيق 121/122 يوضح أن مراجع OWN ليست اعتماداً تلقائياً: بعضها مفقود وبعضها صحيح شكلياً فقط ويحتاج مراجعة معنى النطاق.

## 10. شروط إغلاق C01–C07

لا تنتقل المراكز السبعة إلى APPROVED أو READY-FOR-IMPLEMENTATION حتى:
1. اعتماد Domain/API Owner لكل كيان وعملية وحدث.
2. حسم حدود C02/C03 وC04/SH-15 وC05/C11 وC06/C10/SH-03 وC07/C10/C11.
3. اعتماد field catalog وSoR وclassification وtenant isolation وretention.
4. اعتماد operation/event/approval schemas وversioning/errors/idempotency.
5. حسم APR-C07-02 وإضافة عملية القرار المعتمدة أو تسجيل قرار N/A مسبب.
6. ربط كل عملية/حدث/موافقة باختبار أصلي أو محلي معتمد، دون الادعاء أن المحلي مصدر أصلي.
7. تشغيل الاختبارات على commit/build/environment محدد وتخزين evidence قابلة للتحقق.
8. اعتماد المالكين وC10 وSH-03 والجهات المتأثرة.
9. إعادة تدقيق referential integrity على سجلات 57/78/79/80/97/98/99/100.
10. تحديث readiness report بعد ظهور أدلة حقيقية لا بمجرد إضافة وثائق.

## 11. الحكم النهائي

تم استكمال حزمة التتبع التفصيلية الأولية C01–C07 وربطها بمراجع السجلات المركزية مع إظهار فجوة APR-C07-02 وحدود الملكية والاختبار. لم يتم اعتماد العقود أو تشغيل الاختبارات أو تنفيذ البرمجة. تبقى C01–C07: DERIVED / OWNER REVIEW / NOT APPROVED / NOT IMPLEMENTED.

**بوابة بدء البرمجة مستقلة ولا تفتحها هذه الحزمة. لا تعديل لكود التطبيق ولا ادعاء باكتمال التطبيق.**
