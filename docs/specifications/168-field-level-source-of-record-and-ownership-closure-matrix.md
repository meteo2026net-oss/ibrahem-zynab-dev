# 168 — مصفوفة مصدر الحقيقة وملكية الحقول FLD-01..26

**الحالة:** DERIVED TRACEABILITY MATRIX / SOURCE-REFERENCED / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-16  
**النطاق:** تحويل نتائج الكتالوج 77 وتدقيق الإغلاق 120 ومطابقة الملكية 73 والتحقق المصدرّي 75 إلى سجل موحد قابل للمراجعة على مستوى فجوات الحقول الـ26.  
**لا يمثل هذا المستند:** اعتماداً لملكية أي حقل، أو schema نهائياً، أو ERD، أو API/event contract، أو migration، أو نتيجة اختبار.

## 1. قاعدة الإثبات

يعتمد هذا السجل على المعرفات والوصف الظاهر في 73 و77 و120 و75، مع الاستفادة من حدود المراكز والخدمات في 59–70 و72. ويستخدم التصنيفات التالية:

- **SOURCE-REFERENCED:** وجود المشكلة أو المفهوم مدعوم بإحالة مصدرية محددة؛ لا يعني أن قرار الملكية نفسه وارد في المصدر.
- **DERIVED-CANDIDATE:** فصل الملكية أو الربط المقترح استنتاج توثيقي يحتاج مراجعة المالك.
- **OPEN:** القرار أو حد المسؤولية لم يعتمد.
- **TBD:** التفاصيل البنيوية أو التشغيلية غير موجودة في الأدلة المتاحة.
- **NOT-IMPLEMENTED / NOT-TESTED:** لا يوجد تنفيذ أو دليل تشغيل/اختبار في هذه المراجعة.

لا يُرقّى أي مرشح إلى APPROVED بمجرد تكراره في أكثر من وثيقة مشتقة. لا تُستنتج أنواع SQL أو nullability أو defaults أو constraints أو retention periods من أسماء الحقول.

## 2. مصفوفة FLD-01..26

| الفجوة | المفهوم/الكيانات المتأثرة | مصدر الحقيقة المرشح وحدود الخدمات المشتركة | حالة الدليل والقرار |
|---|---|---|---|
| FLD-01 | organization_id / tenant_id / tenant scope | مالك نطاق المؤسسة/المستأجر لم يحسم؛ C10 يحكم العزل، وSH-01/02 يقدمان الهوية والتفويض. لا يُفترض الترادف بين organization وtenant. | SOURCE-REFERENCED؛ قاموس العلاقات والمالك OPEN؛ أنواع وقيود TBD |
| FLD-02 | project_requirements وإصداراتها وعلاقاتها وأدلتها | C01 مرشح لامتلاك سجل المتطلبات ومعاييرها؛ C08/SH-06 للأصل الثنائي، وC07 للاختبار. | SOURCE-REFERENCED؛ تفاصيل الحقول والقيود OPEN/TBD |
| FLD-03 | design_pages / page_registry | الفصل المرشح: C03 لهوية الصفحة ومسارها؛ C02 لمستند التصميم واللوحة والعناصر مع مرجع للصفحة. | SOURCE-REFERENCED؛ حد C02/C03 DERIVED-CANDIDATE / OPEN |
| FLD-04 | component definitions / generated components | C02 لتعريفات التصميم والمكونات؛ C05 لمخرجات الكود والتغييرات؛ عقد التوافق والإصدار بينهما مطلوب. | SOURCE-REFERENCED؛ mapping/version contract TBD |
| FLD-05 | schema/migration مقابل سجلات المجال | C04 مرشح لملكية سجل تعريف المخطط والترحيل؛ كل مركز مجال يملك سجلاته الدلالية. | SOURCE-REFERENCED؛ Schema Steward وMigration Approver وDomain Owner OPEN |
| FLD-06 | ai_tasks/runs مقابل SH-08 tasks/attempts | C06 لنية/خطة/سجل مهمة AI؛ SH-08 لمحاولات التنفيذ العامة، مع task_ref/job_ref. | PARTIAL في 75؛ الفصل DERIVED-CANDIDATE / OPEN |
| FLD-07 | AI usage مقابل SH-11 cost ledger | C06 لبيانات قياس المزود/النموذج؛ SH-11 لدفتر تكلفة موحد وتسوية القياس. | SOURCE-REFERENCED؛ حدود السجل ومالكو الميزانية والتسوية OPEN |
| FLD-08 | qa_test_runs مقابل SH-08 jobs وSH-14 health | C07 لنتيجة الاختبار ودليلها؛ SH-08 لتشغيل المهمة؛ SH-14 للقياس التشغيلي. | SOURCE-REFERENCED؛ فصل النتيجة عن التنفيذ والصحة DERIVED-CANDIDATE / OPEN |
| FLD-09 | asset_records/versions مقابل SH-06 storage records | C08 مرشح لسجل الأصل الدلالي وروابطه؛ SH-06 للرفع والتخزين والفحص والمعالجة الثنائية. | SOURCE-REFERENCED؛ OWN-01 ما زال غير معتمد؛ حد سجل الأصل/الكائن OPEN |
| FLD-10 | asset_access_grants مقابل SH-02 grants | SH-02 مرشح لمصدر قرار التفويض المركزي؛ C08 يحتفظ بمراجع الأصل ولا يكرر grant قابل للكتابة. | SOURCE-REFERENCED؛ قرار إلغاء السجل المحلي OPEN |
| FLD-11 | integration_runs مقابل SH-08 tasks | C09 لسجل تشغيل التكامل التجاري؛ SH-08 لمحاولات التنفيذ العامة، مع run_ref/job_ref. | SOURCE-REFERENCED؛ ربط المعرفات وقواعد الحالة OPEN |
| FLD-12 | connector metadata / secrets | C09 لغرض التكامل والـmapping؛ SH-13 لدورة حياة الموصل؛ Secret Manager للقيمة السرية. | PARTIAL في 75؛ OWN-02 غير معتمد؛ فصل البيانات والسر OPEN |
| FLD-13 | C10 policy decisions مقابل SH-02 access decisions | C10 لتأليف السياسات والحوكمة؛ SH-02 للتقييم وقت التشغيل. | SOURCE-REFERENCED؛ الفصل DERIVED-CANDIDATE / OPEN |
| FLD-14 | C10 audit_events مقابل SH-04 ledger | SH-04 مرشح لمصدر سجل التدقيق؛ C10 يحتفظ بمراجع الأحداث اللازمة للحوكمة. | SOURCE-REFERENCED؛ لا اعتماد نهائي لسجل المصدر في هذه المراجعة |
| FLD-15 | C11 release state مقابل SH-08 job state | C11 لدورة حياة الإصدار/النشر؛ SH-08 لمحاولات التنفيذ والتقدم والإلغاء. | SOURCE-REFERENCED؛ قواعد انتقال الحالة والربط OPEN |
| FLD-16 | artifact registry / binary storage / attestation | ملكية سجل القطع ومخزن binary والتوقيع والاحتفاظ والنسخ لم تحسم؛ يلزم فصل metadata عن bytes. | SOURCE-REFERENCED؛ registry/storage authority OPEN بالكامل |
| FLD-17 | C12 document content/versions مقابل SH-16 ingestion | C12 أو مالك المجال للمحتوى القانوني/المعرفي canonical؛ SH-16 للاستيعاب والفهرسة الوصفية. | SOURCE-REFERENCED؛ OWN-04 توصية غير معتمدة |
| FLD-18 | C12 search documents مقابل SH-07 index | مالك المحتوى يملك النص وACL؛ SH-07 للاستعلام والفهرس؛ SH-16 للاستيعاب. | SOURCE-REFERENCED؛ قواعد إعادة الفهرسة والحذف وACL OPEN |
| FLD-19 | C13 setting values مقابل SH-12 | C13 لتجربة الإعداد وطلبات التغيير؛ SH-12 لتخزين القيم والإصدارات؛ مالك المفتاح لدلالته ومخططه. | PARTIAL في 75؛ أولوية الوراثة ومالك المفتاح OPEN |
| FLD-20 | C14 dashboard projections | المصدر الأصلي لكل مؤشر يملك الحقيقة؛ C14 يملك تفضيلات العرض وإسقاطات قراءة فقط مع source/version/as_of/freshness/scope/ACL. | SOURCE-REFERENCED؛ schema وfreshness وpermission filtering TBD |
| FLD-21 | actor / timestamps / provenance | سياق الطلب/SH-01 للهوية، المنتج لوقت الحدث، المستقبِل لوقت التسجيل، وSH-04 للتدقيق؛ قواعد كل مجال لازمة. | NARROWED في 75؛ envelope موحد مرشح لا قرار معتمد |
| FLD-22 | archive/delete/retention/legal hold | مالك كل مجال يحدد دورة حياة سجلاته؛ C10 يراجع الضوابط؛ الخدمات المشتركة تنفذ عملياتها ضمن حدودها. | SOURCE-REFERENCED؛ فئات ومدد وأساس قانوني TBD |
| FLD-23 | status/enums/state machine/versioning | مالك الكيان يحدد آلة حالاته؛ يلزم سجل توافق وإصدار للقيم دون تعميم enum واحد على المجالات. | NARROWED في 75؛ registry/version policy OPEN |
| FLD-24 | cross-project/global references | مالك السجل المصدر يثبت النطاق؛ C10 وSH-02 يطبقان العزل والمنح الصريحة؛ لا يجيز المرجع وحده مشاركة البيانات. | SOURCE-REFERENCED؛ قواعد cross-tenant OPEN |
| FLD-25 | domain_owner_ref / owner_domain / created_by / steward_ref | مالك المجال لحقيقة السجل؛ SH-01 لهوية الفاعل؛ steward لدور الإشراف وفق تعيين معتمد. | PARTIAL في 75؛ فصل المصطلحات DERIVED-CANDIDATE / OPEN |
| FLD-26 | classification / purpose / legal_basis / retention | مالك المجال يعلن تصنيف بياناته والغرض؛ C10 يحكم المراجعة والضوابط؛ لا تُخترع قيمة قانونية. | SOURCE-REFERENCED؛ taxonomy وlegal basis والاحتفاظ TBD |

## 3. قاموس الحقول المشتركة المستعاد من الكتالوج

هذه الأسماء واردة في 77 بوصفها مفاهيم أو حقولاً مرجعية، ولا يعني جمعها هنا أنها جميعاً حقول إلزامية لكل كيان:

| المجموعة | الأسماء الظاهرة في المصدر | ملاحظة الحسم |
|---|---|---|
| النطاق | organization_id, tenant_id, workspace_id, project_id, environment_id, default_environment_id, scope | لا ترادف مثبت بين organization_id وtenant_id؛ المرجع لا يمنح صلاحية |
| الملكية والفاعل | domain_owner_ref, record_owner_scope, steward_ref, created_by, created_by_actor, updated_by_actor, actor | افصل مالك الحقيقة عن المنشئ والمشرف والفاعل |
| الزمن والإصدار | occurred_at, recorded_at, timestamps, record_version, expected_version, version_id, parent_version | افصل وقت وقوع الحدث عن تسجيله، وإصدار السجل عن metadata الإصدار SH-10 |
| المصدر والمنشأ | source_ref, source_version, provenance_ref, content_ref, content_hash | تفاصيل provenance والبصمة/الترميز TBD |
| الأصول والمهام | asset_ref, storage_object_ref, task_ref, job_ref | افصل سجل الأصل عن binary، ومهمة المجال عن محاولة التنفيذ |
| السياسة والموافقة والتدقيق | policy_ref, decision_ref, approval_ref, audit_event_ref | مراجع إلى الخدمات؛ لا تنشئ نسخاً محلية قابلة للكتابة من سجل القرار |
| الأسرار والخصوصية | secret_ref, classification, dataClassification, retention_class, legal_basis_ref | secret_ref مرجع فقط؛ لا تخزن قيمة السر في قاعدة البيانات أو السجلات أو prompt |
| الحالة والتتبع | lifecycle_status, status, requestId, correlationId, contractVersion, idempotencyKey | لا توجد enums أو formats أو مدد idempotency معتمدة في هذه المراجعة |

## 4. تغطية المراكز والخدمات — خريطة مراجعة لا قرار ملكية

| المراكز | المجال الذي يظهر في العقود/الكتالوج | خدمات مشتركة ذات صلة كما وردت في 77/73 | نقاط الإغلاق المطلوبة |
|---|---|---|---|
| C01 | المشروع والمتطلبات | SH-03/06/08/10/16 | حقول المتطلبات، مالك المؤسسة/مساحة العمل |
| C02–C03 | التصميم والصفحات والتنقل | SH-02/04/06/07/08/10 | هوية الصفحة، مراجع التصميم، guards |
| C04 | المخططات والبيانات والترحيلات | SH-08/10/13 | Schema Steward وMigration Owner |
| C05 | الكود والاعتماديات | SH-02/03/04/06/08/10/13 | snapshot/patch/artifact provenance |
| C06 | الوكلاء ومهام AI | SH-02/03/04/08/09/11 | AI task مقابل job، usage مقابل ledger |
| C07 | الاختبارات والجودة | SH-03/04/06/08/14 | test result مقابل job وhealth |
| C08 | سجل الأصول | SH-02/04/06/08 | asset registry مقابل storage/binary/grants |
| C09 | التكاملات | SH-04/08/09/13 | business run مقابل job، connector مقابل secret |
| C10 | الأمن والحوكمة | SH-01/02/03/04 | policy authoring/evaluation، audit authority |
| C11 | البناء والنشر | SH-08/10/14/15 | release lifecycle مقابل job، artifact registry |
| C12 | المعرفة والتوثيق | SH-06/07/16 | canonical content مقابل ingestion/index |
| C13 | الإعدادات | SH-03/04/12 | key owner، value store، precedence |
| C14 | لوحة التحكم | SH-03/07/08/14 | projections للقراءة فقط، freshness وACL |

**تنبيه:** وجود خدمة مشتركة في هذا الجدول لا يمنحها ملكية بيانات المجال. كما أن نطاق SH-03 المركزي عبر C01–C14 مثبت في المراجعة المصدرية 157، لكن تفاصيل استهلاك كل مركز وآلية موافقاته لا تزال بحاجة إلى اعتماد.

## 5. قواعد التتبع المطلوبة قبل اعتماد أي حقل

لكل حقل/كيان يجب أن تتوفر السلسلة التالية، مع مصدر صريح لكل رابط:

1. Field/Entity ID أو اسم مصدر محفوظ، مع موضعه في المصدر.
2. Domain Owner وSchema Steward وAPI Owner، كلٌ بدوره المنفصل.
3. Domain SoR محدد، وأي Shared Service SoR مشارك مع حدود القراءة/الكتابة.
4. scope: tenant/organization/workspace/project/environment عند انطباقه.
5. API operation/event/approval references الدقيقة، مع producer/consumer.
6. actor/action/resource/scope وقاعدة الموافقة والتدقيق.
7. type/format/nullability/default/unique/index/FK/delete behavior، أو TBD صريح.
8. classification/purpose/retention/legal basis/legal hold.
9. version/concurrency/idempotency/compatibility semantics.
10. Acceptance Test ID exact وexpected result وtest owner.
11. test run/build/environment/evidence قبل أي حالة PASS.
12. توقيع المالكين ومراجعة الأمن والخصوصية.

## 6. ما لم يُغلق بعد — قياس صريح

- فجوات الحقول المسجلة: **26** (FLD-01..FLD-26).
- الفجوات التي لها تحقق مصدرّي في 75: **26/26**، موزعة SUPPORTED/PARTIAL/NARROWED؛ وهذا تحقق من اتصال المتطلبات بالمشكلة لا اعتماد للملكية.
- قرارات OWN-01..10: **غير معتمدة** في سجل 72؛ لا تُعامل توصياتها كقرارات.
- FLD-01..26: **لا يوجد إغلاق مالك نهائي مثبت** في الأدلة التي تمت مراجعتها.
- حقول وأنواع وقيود schema النهائية: **TBD** حيث لا يورد المصدر تعريفاً صريحاً.
- روابط API/Event/Approval/Tests الدقيقة: لا تنشأ من هذه المصفوفة؛ تبقى أرقام خط الأساس في 97/100/98/99 كما هي.
- الاختبارات المقترحة في 77/120: **SPECIFIED / NOT RUN**؛ لا يوجد run/build/environment/evidence جديد.
- لا ERD أو migration أو API أو قاعدة بيانات أو كود تطبيق تم تغييره بهذه الوثيقة.

## 7. بوابة الإغلاق التالية

1. استعادة مواضع المصدر الأصلي لكل حقل/كيان، ومقارنة النسخ التاريخية المتداخلة.
2. اعتماد مالك لكل قرار FLD، مع تسجيل القرار وتاريخه ونطاقه وأثره.
3. معالجة تعارضات OWN namespace بين الوثيقتين 36 و72 دون إعادة ترقيم أو إلغاء تاريخي.
4. إغلاق تعريفات النطاق والهوية والخصوصية والاحتفاظ أولاً، ثم ملكية الكيانات المشتركة.
5. بناء crosswalk دقيق Field→Entity→Operation/Event/Approval→Test، مع إبقاء المرشحات الدلالية مميزة عن الروابط المصدرية.
6. إصدار نسخة schema review مستقلة بعد اكتمال القرارات، لا اشتقاق migrations مباشرة من هذا السجل.
7. استكمال بقية بوابات G-01..G-15، ثم إصدار MPRTS 1.0 واعتماد المواصفات.
8. لا يبدأ أي تعديل برمجي قبل موافقة اعتماد المواصفات وموافقة مستقلة صريحة على بدء البرمجة.

**النتيجة:** أُنشئت مصفوفة تتبع موحدة للحقول الـ26 من الأدلة المتاحة. هذا تقدم توثيقي قابل للتدقيق، وليس إغلاقاً لقرارات الملكية أو تصريحاً بالبرمجة.
