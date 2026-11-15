# 170 — سجل المراسي المرشحة لربط فجوات الحقول بعقود العمليات والأحداث والموافقات

**التاريخ:** 2026-11-16  
**الحالة:** DERIVED CROSSWALK / SOURCE-ID ANCHORED / SEMANTIC CANDIDATES / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**المراجع:** 168، 169، 97، 98، 99، 100، 120، 157، 164، 165.
**الغرض:** إضافة مراجع مفردة من سجلات العمليات والأحداث والموافقات إلى فجوات الحقول FLD-01..26، لتسهيل مراجعة أثر كل قرار ملكية. هذا سجل مراسٍ مرشحة، وليس اعتماداً لعلاقة Field→Operation/Event/Approval أو Field→Test.

## 1. قواعد القراءة

- معرفات OP/EVT/APR أدناه مستعادة من السجلات 97/100/98؛ لا تُنشأ معرفات جديدة.
- الربط بين FLD وبين المعرفات DERIVED-CANDIDATE من معنى الحقل واسم العملية/الحدث/الموافقة. لا يوجد في المصدر رابط مباشر معتمد يثبت أن العملية تقرأ أو تكتب كل حقل مذكور.
- عائلة اختبار المركز أو EVT-96-T01..12 ليست اختباراً مفرداً دقيقاً. لا تُحوّل إلى Exact Test ID.
- غياب مرساة من القائمة لا يعني عدم وجود عملية؛ يعني أن هذا السجل لم يثبتها.
- قرارات OWN36 وOWN72 تبقى منفصلة بسبب تصادم فضاء المعرفات. لا يُعامل وجود المرجع في 97 كموافقة نهائية.
- لا تغيير في API أو schema أو database أو code.

## 2. مصفوفة المراسي المرشحة

| FLD | مرساة عملية API مفردة | مرساة حدث مفرد | مرساة موافقة مفردة | حد الاستدلال / الفجوة الباقية |
|---|---|---|---|---|
| FLD-01 | OP-C01-001 createProject؛ OP-C01-002 updateProjectProfile | EVT-C01-001 ProjectCreated | — | لا يثبت أي منها تعريف organization/tenant أو عضوية workspace؛ لا يوجد قرار SoR/ACL مفرد |
| FLD-02 | OP-C01-008 createRequirement؛ OP-C01-009 reviseRequirement؛ OP-C01-015 submitRequirementBaseline | EVT-C01-003 RequirementCreated؛ EVT-C01-004 RequirementVersionChanged؛ EVT-C01-005 BaselineSubmitted | APR-C01-01 | مرساة قوية للمتطلبات وخط الأساس؛ تفاصيل الحقول والإصدارات والأدلة والمالك لا تزال TBD/OPEN |
| FLD-03 | OP-C03-029 createPage؛ OP-C03-030 renamePage؛ OP-C03-031 createRoute | EVT-C03-001 PageRegistered؛ EVT-C03-002 RouteDefinitionChanged | — | لا يحسم حد page identity مقابل design document في C02 ولا يثبت 1:1 بين الشاشة والعملية |
| FLD-04 | OP-C02-022 createComponentDefinition؛ OP-C05-070 generateCodeFromDesign | EVT-C02-002 DesignDocumentVersionSaved؛ EVT-C05-003 CodeGenerationCompleted | APR-C02-01 (مرشح غير مباشر لاعتماد مستند التصميم) | لا يوجد عقد توافق component/version أو رابط مباشر يثبت أن الموافقة تغطي component definition |
| FLD-05 | OP-C04-042 createSchema؛ OP-C04-043 addEntity؛ OP-C04-044 addField؛ OP-C04-054 createMigrationPlan؛ OP-C04-055 dryRunMigration؛ OP-C04-056 applyMigration | EVT-C04-003 SchemaVersionCreated؛ EVT-C04-005 MigrationPlanCreated؛ EVT-C04-006 MigrationApplied | APR-C04-01؛ APR-C04-02 | مالك المخطط والترحيل مقابل Domain Owner غير معتمد؛ لا SQL types/constraints معتمدة |
| FLD-06 | OP-C06-094 executeTaskStep؛ OP-C06-095 pauseTask؛ OP-C06-096 resumeTask؛ OP-C06-097 cancelTask | EVT-C06-003 AITaskCreated؛ EVT-C06-005 AITaskStateChanged | APR-C06-01 عند منح الأداة، وليس موافقة على كل مهمة | يلزم استكمال فصل سجل نية المهمة عن SH-08 execution job/attempt؛ روابط task_ref/job_ref TBD |
| FLD-07 | لا توجد عملية دفتر تكلفة مفردة مثبتة في هذه المراسي | لا توجد مرساة حدث تكلفة exact مثبتة هنا | — | SH-11 owner/budget/price/FX/reconciliation مفتوحة؛ لا تستنتج من قياس استخدام AI أنه دفتر التكلفة |
| FLD-08 | OP-C07-110 createTestRun | EVT-C07-002 QATestRunCompleted؛ EVT-C07-006 QATestResultsApproved | APR-C07-01؛ APR-C07-02 | APR-C07-02 موجود في سجل 98 لكن العملية الموافقة المقابلة مفقودة في سجل العمليات؛ لا test run جديد أو PASS |
| FLD-09 | لا تُثبت هذه المراجعة مرساة OP-C08 مفردة كافية لسجل الأصل | EVT-C08-001 AssetRecordCreated؛ EVT-C08-003 AssetVersionReady | APR-C08-01 للحذف؛ APR-C08-02 للتصدير | يلزم فصل سجل الأصل الدلالي عن bytes/storage object وملكية ACL؛ استكمال مرجع العملية الدقيقة مطلوب |
| FLD-10 | لا توجد مرساة محلية معتمدة لمنح الوصول | EVT-C08-006 AssetLinked؛ EVT-C08-007 AssetLinkRevoked | — | SH-02 مرشح لقرار التفويض المركزي؛ أحداث الربط/الإلغاء لا تثبت مصدر قرار grant |
| FLD-11 | عملية C09 startSync كما وردت في سبب APR-C09-01؛ OP ID exact يحتاج reconciliation مع 97 | EVT-C09-005 IntegrationSyncStarted؛ EVT-C09-006 IntegrationSyncCompleted؛ EVT-C09-007 IntegrationSyncFailed | APR-C09-01 | لا تربط اسم startSync بمعرف OP تخميناً؛ run_ref/job_ref وقواعد retry/status مفتوحة |
| FLD-12 | عملية beginAuthorization كما وردت في سبب APR-C09-02؛ OP ID exact يحتاج reconciliation مع 97 | EVT-C09-002 IntegrationAuthorizationStarted؛ EVT-C09-009 IntegrationRevoked | APR-C09-02 | فصل connector metadata عن SH-13 وعن Secret Manager مطلوب؛ secret_ref مرجع فقط |
| FLD-13 | عملية requestPolicyChange/decision كما في سجل الموافقات؛ OP ID exact غير مثبت هنا | EVT-C10-001 PolicyVersionPublished؛ EVT-C10-004 ExceptionDecided | APR-C10-01؛ APR-C10-02 | C10 يؤلف السياسة وSH-02 يقيم قرار الوصول وقت التشغيل؛ تعارض Zero Trust لا يزال مفتوحاً |
| FLD-14 | لا توجد عملية audit write مفردة مثبتة هنا | سجل SH-04 مرشح لمصدر الحدث؛ أحداث المجال في 100 ليست بديلاً عن سجل التدقيق المركزي | — | يلزم تحديد audit authority وimmutability وactor attribution دون نسخ سجلات المجال |
| FLD-15 | عمليات release/deploy/rollback موجودة كعائلات C11؛ IDs الدقيقة تتطلب ربطاً من 97 قبل اعتمادها | EVT-C11-006 ReleaseCandidateCreated؛ EVT-C11-008 ReleaseApproved؛ EVT-C11-009 DeploymentStarted؛ EVT-C11-012 RollbackRequested | APR-C11-01؛ APR-C11-02؛ APR-C11-03 | فصل release lifecycle عن SH-08 job state؛ لا تفترض أن الموافقة تعني نجاح النشر |
| FLD-16 | لا توجد مرساة OP مفردة مثبتة هنا لسجل artifact والـbinary | EVT-C11-005 ArtifactAttested | APR-C11-01 | سجل artifact وbinary storage والتوقيع والاحتفاظ والنسخ تحتاج مالكاً وحدوداً معتمدة |
| FLD-17 | عملية publishKnowledgeDocument كما في سبب APR-C12-01؛ OP ID exact يحتاج reconciliation | حدث النشر/الإصدار الدقيق يحتاج مطابقة مع سجل 100 قبل ربطه | APR-C12-01؛ APR-C12-02 | لا تجعل SH-16 مالك المحتوى canonical؛ يلزم مصدر المحتوى وإصداره وprovenance |
| FLD-18 | لا توجد عملية بحث/فهرسة مفردة معتمدة في هذه المراسي | لا توجد مرساة حدث مفردة مؤكدة | — | مالك المحتوى يملك النص وACL؛ SH-07 للفهرس والاستعلام؛ SH-16 للاستيعاب |
| FLD-19 | عملية applyOwnerSettingChange كما في سبب APR-C13-01؛ OP ID exact يحتاج reconciliation | لا توجد مرساة حدث مفردة مؤكدة في هذا السجل | APR-C13-01؛ APR-C13-02 | مالك مفتاح الإعداد وأولوية الوراثة ومخزن القيم SH-12 مفتوحة |
| FLD-20 | exportDashboardReport كما في سبب APR-C14-01؛ OP ID exact يحتاج reconciliation | لا توجد مرساة حدث مفردة مؤكدة في هذا السجل | APR-C14-01 | الإسقاطات للقراءة فقط؛ يجب إثبات مصدر كل مؤشر ووقت as_of/freshness وACL |
| FLD-21 | يطبق على كل عملية/حدث وفق envelope مشترك؛ لا operation واحد | جميع أحداث EVT في 100 تحتاج actor/time/provenance contract | — | occurred_at وrecorded_at وactor وservice identity وtimezone تحتاج تعريفاً مشتركاً معتمداً |
| FLD-22 | OP-C08 deletion request مرشح، ومعرفه المفرد يحتاج reconciliation | EVT-C08-010 AssetDeletionRequested؛ EVT-C08-011 AssetDeleted | APR-C08-01؛ APR-C08-02 للتصدير | retention/legal hold/delete propagation يجب تحديدها لكل فئة ومجال |
| FLD-23 | كل عمليات تغيير الحالة في 97 مرشحة حسب الكيان؛ لا enum عام واحد | أحداث state changes في 100 مرشحة حسب المجال | حسب قرار المجال؛ لا موافقة عامة مفترضة | state machine/version policy لكل كيان؛ لا تعميم status واحد |
| FLD-24 | OP-C01-001 مرساة إنشاء مشروع فقط وليست عقد cross-tenant | EVT-C01-001 ProjectCreated | — | لا توجد مرساة مفردة تثبت مشاركة عابرة للمؤسسات؛ اختبارات negative isolation لازمة |
| FLD-25 | لا توجد عملية owner/steward assignment مفردة مثبتة هنا | لا توجد مرساة حدث مفردة مؤكدة | — | فصل Domain Owner وrecord owner وsteward وcreator/actor يتطلب RACI وتعييناً موثقاً |
| FLD-26 | لا توجد عملية تصنيف/احتفاظ مفردة مثبتة هنا | لا توجد مرساة حدث مفردة مؤكدة | — | classification/purpose/legal_basis/retention taxonomy تحتاج قرارات المالك ومراجعة C10 |

## 3. ملاحظات تسوية مهمة

1. **APR-C07-02:** سجل 98 يعرّف موافقة استثناء الجودة approveQualityWaiver، لكن تدقيق 155/163 يؤكد غياب عملية قرار مقابلة في سجل 97. يجب تسجيلها كفجوة contract؛ لا تُنشأ عملية أو ID تلقائياً.
2. **C09/C12/C13/C14:** بعض سجلات الموافقات 98 تصف العملية بالاسم دون ربط مؤكد بـOperation ID في 97. يجب مطابقة الاسم مع سجل 97 قبل اعتماد رابط OP→APR.
3. **C08/C11/C12/C13/C14:** لا تملأ المراسي الناقصة بعمليات مشابهة أو بمعرفات تخمينية. تبقى مرجعيات المصدر المطلوب استكمالها.
4. **الأحداث:** 100 يسجل 106 حدثاً، لكن Qualified OWN وDomain SoR وExact Test تبقى TBD في السجلات التي تمت مراجعتها؛ وجود EVT ID لا يغلق الملكية أو الاختبار.
5. **الاختبارات:** لا تضيف هذه الوثيقة أي Exact Test ID. خطوط الأساس في 99/100 و148/155–164 تبقى كما هي، وكل الاختبارات ذات الصلة غير منفذة ما لم يوجد run evidence مستقل.

## 4. قياس التقدم الناتج

- فجوات FLD المفهرسة: **26/26**.
- فجوات لها مرساة عملية/حدث/موافقة مرشحة أو تصريح صريح بعدم ثبوت المرساة: **26/26**.
- روابط Field→Operation/Event/Approval معتمدة نهائياً بهذه الوثيقة: **0**.
- روابط Operation→Test أو Event→Test exact معتمدة جديدة: **0**.
- فجوة APR-C07-02 موثقة كعدم تطابق بين سجل الموافقات وسجل العمليات: **1**.
- موافقات مالك نهائية جديدة: **0**.
- اختبارات منفذة أو نتائج PASS جديدة: **0**.
- تغييرات كود/API/schema/database: **0**.

## 5. شروط الإغلاق التالية

1. مراجعة المراسي المرشحة من ملاك المجالات وملاك الخدمات المشتركة.
2. إرفاق مصدر الفقرة/القسم الأصلي لكل رابط؛ لا يكفي تشابه الاسم.
3. تسوية أسماء العمليات في APR-C09/C12/C13/C14 مع IDs في 97، وتسوية فجوة APR-C07-02.
4. إضافة producer/consumer وDomain SoR وQualified OWN للأحداث المفردة بعد قرار المالك.
5. ربط كل عملية/حدث/موافقة باختبار قبول مفرد له expected result وtest owner، ثم تنفيذ الاختبارات في بيئة معروفة.
6. تحديث 168/169 وسجلات 97–100 فقط بعد وجود القرار أو الدليل، لا بمجرد تكرار المرشح.
7. لا يصدر ERD/migration/API contract نهائي ولا يبدأ تعديل التطبيق قبل اعتماد المواصفات والموافقة المستقلة على بدء البرمجة.

**النتيجة:** أُنشئ فهرس مراجعة يحدد أين توجد مراسٍ مفردة مفيدة وأين لا توجد، ويكشف فجوة APR-C07-02 ونقاط عدم تطابق الموافقات مع معرفات العمليات. لا يغير هذا السجل حالات الاعتماد أو التنفيذ.