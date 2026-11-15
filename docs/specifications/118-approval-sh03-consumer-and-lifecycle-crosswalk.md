# 118 — مصفوفة ربط أنواع الموافقات بدورة SH-03 والمراكز المستهلكة

**الحالة:** DERIVED TRACEABILITY / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المصادر:** 23 (SH-03 وconsumer matrix)، 97 (201 عملية)، 98 (26 نوع موافقة)، 116، 117.  
**الغرض:** تحويل الاستنتاج الدلالي في 117 إلى crosswalk مفرد لكل نوع موافقة، مع كشف اختلاف نطاق مستهلكي SH-03. لا تنشئ هذه الوثيقة APIs أو جداول أو صلاحيات أو workflows فعلية.

## 1. قواعد قراءة المصفوفة

- أسماء SH-03 أدناه هي العمليات **المقترحة** في الوثيقة 23: createRequest، submitRequest، assignApprover، approve، reject، requestChanges، cancel، expire، getDecision.
- لكل نوع موافقة، SH-03 يدير الطلب والتعيين والقرار وحالة الطلب؛ أما الفعل المحمي فيبقى عملية مجال منفصلة، ولا ينفذ تلقائياً بمجرد إنشاء الطلب.
- يحدد مالك المجال ما إذا كانت الموافقة إلزامية أو مشروطة أو غير مطلوبة، ومن يقرر، وما إذا كان هناك أكثر من موافق.
- عند قرار approve، يجب أن يعاد فحص actor/policy/resource version/hash قبل استهلاك القرار في الفعل المحمي.
- الأفعال ذات البادئة decide* ملتبسة: قد تكون قرار مجال أو فعلاً محمياً باسم غير دقيق. لا نحسم ذلك دون قرار مالك المركز.
- كل mapping في هذه الوثيقة PROPOSED؛ لا يثبت وجود endpoint أو implementation.

## 2. المصفوفة المفردة — 26 نوعاً

| Approval ID | Center | Protected domain action/resource | SH-03 request/submit | SH-03 decision path | Post-approval gated action | Mapping issue / owner decision |
|---|---|---|---|---|---|---|
| APR-C01-01 | C01 | decideRequirementBaseline / requirement_baseline | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تثبيت خط أساس المتطلبات بإصدار/بصمة محددة؛ حسم هل decideRequirementBaseline هو قرار المجال أم الفعل بعد قرار SH-03 | C01 يحدد معنى decide* وتجنب ازدواج القرار |
| APR-C02-01 | C02 | decideDesignApproval / design_document_version | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد/نشر نسخة التصميم بعد تحقق version/hash؛ حسم هل العملية الحالية تمثل القرار نفسه | C02/C03 يحددان ملكية التصميم وحدود القرار |
| APR-C03-01 | C03 | decideNavigationApproval / navigation_graph | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد graph/version ثم إتاحته للتنقل | C03 يحدد هل C03 مستهلك مباشر لـSH-03 رغم غيابه من consumer list |
| APR-C04-01 | C04 | decideSchemaApproval / schema_version | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد schema/row policy؛ فصل قرار schema عن قرار السياسة الأمنية | C04 وC10 يحددان تعدد المراجعين عند أثر السياسة |
| APR-C04-02 | C04 | applyMigration / migration_plan | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | applyMigration بعد dry-run والتحقق من rollback والخطة والبصمة | تحديد ما إذا كانت موافقة SH-03 واحدة تكفي أم يلزم موافق بيئة/مالك بيانات |
| APR-C04-03 | C04 | requestRestore / restore_plan | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تنفيذ restore فقط بعد تحقق اتساق جميع المالكين المتأثرين | requestRestore قد يكون طلب restore نفسه؛ يلزم فصل طلب المجال عن طلب الموافقة |
| APR-C05-01 | C05 | applyApprovedChangeSet / code_change_set | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تطبيق change set بعد إعادة فحص base hash/diff والمراجعة المستقلة | تحديد هل applyApprovedChangeSet هو consumer أم request action |
| APR-C05-02 | C05 | approveCodeBaseline / code_baseline | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تثبيت baseline بعد قرار المراجع؛ حسم تكرار دلالة approve في اسم العملية | C05 يحدد العلاقة بين SH-03 approve وapproveCodeBaseline |
| APR-C06-01 | C06 | requestToolGrant / tool capability | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | منح capability محدود الزمن والنطاق بواسطة SH-02 بعد قرار SH-03 | requestToolGrant لا يساوي grant؛ تحديد من يستهلك القرار |
| APR-C06-02 | C06 | reviewAgentOutput / AI output | createRequest عند الحاجة → submitRequest → assignApprover | approve / reject / requestChanges؛ لا تعتمد review كقرار قبل تحديد outcome | تطبيق مخرج AI على المورد الحساس بعد موافقة مالك المورد والتحقق من diff | تحديد outcome للمراجعة وهل كل المخرجات الحساسة تدخل SH-03 |
| APR-C07-01 | C07 | approveTestResults / QA result | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد نتيجة الاختبار/بوابة الجودة مع الاحتفاظ بالأدلة | C07 غير موجود في consumer list؛ يجب تصحيح القائمة أو تحديد مسار آخر |
| APR-C07-02 | C07 | approveQualityWaiver / quality waiver | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تسجيل waiver محدد النطاق والمالك وexpiry، ثم تطبيقه على البوابة المعنية | approveQualityWaiver غير موجود حرفياً في سجل 97؛ requestQualityWaiver لا يعوض قرار الموافقة |
| APR-C08-01 | C08 | requestDeletion / asset deletion | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تنفيذ الحذف بعد retention/legal hold/ACL checks وتسجيل النتيجة | C08 غير موجود في consumer list؛ يلزم تحديد العلاقة مع SH-06 وC10 |
| APR-C08-02 | C08 | requestExport / asset export | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | إنشاء export artifact بعد إعادة فحص ACL/classification والنطاق | C08 غير موجود في consumer list؛ يلزم تحديد العلاقة مع مالك البيانات |
| APR-C09-01 | C09 | startSync / external write | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | بدء المزامنة الخارجية بعد التحقق من connector scope/idempotency/secret policy | C09 مدرج مستهلكاً؛ تحديد ما إذا كانت كل sync أم الكتابة الخارجية فقط تتطلب موافقة |
| APR-C09-02 | C09 | beginAuthorization / connector scope | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | إتمام تفويض الموصل بعد consent والسياسة والتحقق من redirect/scope | فصل موافقة المستخدم OAuth عن موافقة داخلية عالية الأثر |
| APR-C10-01 | C10 | decideSecurityException / security exception | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تسجيل استثناء محدود النطاق والمدة؛ حسم هل decideSecurityException هو قرار SH-03 أو إجراء المجال بعده | C10 يحدد استقلال الموافق وسياسة expiry |
| APR-C10-02 | C10 | requestPolicyChange / policy version | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد/نشر نسخة سياسة جديدة بعد المراجعة | الطلب لا يساوي اعتماد السياسة؛ تحديد مالك نشر policy version |
| APR-C11-01 | C11 | approveRelease / release_candidate | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | اعتماد release candidate المرتبط بـartifact/hash/environment | C11 يحدد reviewer المستقل وربط release evidence |
| APR-C11-02 | C11 | deployRelease / production deployment | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | deployRelease بعد فحص approvalRef والبوابات والبيئة | فصل قرار اعتماد الإصدار عن قرار السماح بالنشر عند الحاجة |
| APR-C11-03 | C11 | requestRollback / rollback | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | تنفيذ rollback بعد موافقات ملاك المجالات المتأثرة وفحص migration/data impact | requestRollback طلب مجال؛ تحديد هل يحتاج موافقات متعددة/تسلسل |
| APR-C12-01 | C12 | publishKnowledgeDocument / approved version | createRequest → submitRequest → assignApprover | approve / reject / requestChanges | نشر نسخة معرفة بعد اعتماد المصدر والمالك والنسخة | C12 غير موجود في consumer list؛ يلزم تأكيد SH-03 consumer أو مسار موافقة آخر |
| APR-C12-02 | C12 | exportKnowledgeBundle | createRequest عند اشتراط السياسة → submitRequest → assignApprover | approve / reject / requestChanges | تصدير الحزمة بعد ACL/classification/tenant scope revalidation | SH-03 consumer list لا يتضمن C12؛ تحديد متى تكون الموافقة مطلوبة |
| APR-C13-01 | C13 | applyOwnerSettingChange | createRequest عند التغيير الحساس → submitRequest → assignApprover | approve / reject / requestChanges | تطبيق تغيير الإعداد بعد تحقق owner/key/scope/precedence | C13 مدرج مستهلكاً؛ تعريف مفاتيح الإعداد الحساسة |
| APR-C13-02 | C13 | rollbackSettingVersion | createRequest عند التغيير الحساس → submitRequest → assignApprover | approve / reject / requestChanges | rollbackSettingVersion بعد قرار مالك المفتاح وإعادة فحص policy | C13 مدرج مستهلكاً؛ تحديد أثر الرجوع على الإعدادات الموروثة |
| APR-C14-01 | C14 | exportDashboardReport | createRequest عند حساسية/اتساع النطاق → submitRequest → assignApprover | approve / reject / requestChanges | إنشاء التقرير بعد جمع موافقات/ACL لكل مصدر بيانات بحسب policy | C14 غير موجود في consumer list؛ حسم SH-03 direct consumer أو تجميع قرارات المصادر |

## 3. فجوة نطاق المستهلكين في عقد 23

قائمة SH-03 في الوثيقة 23 هي: **C01 وC02 وC04 وC05 وC06 وC09 وC10 وC11 وC13**.

لكن سجل 98 يحتوي أنواع موافقات للمراكز التالية غير الموجودة في هذه القائمة:

| Center | Approval types | SH-03 consumer matrix | Required clarification |
|---|---|---|---|
| C03 | APR-C03-01 | غير مدرج | إضافة C03 إذا كان مستهلكاً مباشراً، أو توثيق آلية غير مباشرة |
| C07 | APR-C07-01/02 | غير مدرج | إضافة C07 أو تحديد consumer path آخر |
| C08 | APR-C08-01/02 | غير مدرج | تحديد SH-06/C08/SH-03 boundary |
| C12 | APR-C12-01/02 | غير مدرج | تحديد نشر المعرفة/التصدير ومتى يحتاج SH-03 |
| C14 | APR-C14-01 | غير مدرج | تحديد هل C14 يطلب موافقة مباشرة أم يجمع قرارات مالكي المصادر |

هذا اختلاف وثائقي مثبت بين قائمتين في المصدر الحالي، وليس دليلاً وحده على خطأ المعمارية. لا يتم تعديل قائمة المستهلكين حتى مراجعة مالكي SH-03 والمراكز المذكورة.

## 4. أنواع القرار في SH-03 مقابل الفعل المحمي

| المرحلة | العملية المقترحة | مالك الدلالة | ملاحظة الإغلاق |
|---|---|---|---|
| إنشاء الطلب | createRequest | طالب المجال/مالك المجال | يربط approvalType/resource/version/hash/scope/action |
| إرسال الطلب | submitRequest | SH-03 وفق شروط المجال | يتحقق من اكتمال الطلب قبل الانتقال إلى Pending |
| تعيين الموافق | assignApprover | سياسة المجال/C10؛ SH-03 ينفذ التعيين | يثبت الدور والنطاق والتفويض |
| القرار | approve / reject / requestChanges | الموافق المخول وفق سياسة المجال | لا يملك SH-03 اختراع القرار الدلالي |
| الإلغاء/الانتهاء | cancel / expire | الطالب/مالك السياسة/SH-03 وفق الحالة | يجب حفظ السبب والتوقيت |
| قراءة القرار | getDecision | SH-03 مع authorization من SH-02 | لا يكشف القرار لغير المخول |
| استهلاك القرار | لا يوجد اسم عام مثبت في 23 | مالك الفعل المحمي | يلزم تعريف التحقق/consume semantics أو ربطها بالفعل المحمي |
| التنفيذ | عملية المجال في 97 | Domain Owner | يعاد فحص approvalRef والنسخة والصلاحية والسياسة قبل الأثر |

**فجوة جديدة:** العقد 23 يسرد عمليات قرار عامة، لكنه لا يسمي عملية عامة صريحة لاستهلاك/تحقق الموافقة عند تنفيذ الفعل المحمي. قد تكون هذه مسؤولية داخلية لعملية المجال، أو تحتاج contract operation/guard موحداً؛ القرار مفتوح ولا نضيف اسم API افتراضياً.

## 5. اختبارات التغطية المقترحة

لا تكفي APR-98-T01..12 وحدها لإثبات كل نوع. الحد الأدنى المطلوب قبل اعتماد المصفوفة:

- كل APR-Cxx له happy path ورفض/طلب تعديل ومسار صلاحية غير مخولة.
- كل فعل محمي يفشل قبل قرار صالح إذا كانت الموافقة إلزامية.
- القرار لا يستهلك إذا تغير resource version/hash أو انتهت الصلاحية أو سُحبت.
- منع self-approval حيث تشترط السياسة الفصل.
- إعادة التنفيذ لا تكرر side effect؛ approvalRef يستخدم مرة واحدة أو وفق سياسة صريحة.
- الأفعال متعددة المجالات تتطلب قرارات جميع المالكين المطلوبين قبل التنفيذ.
- اختبار direct/indirect SH-03 consumer لكل C03/C07/C08/C12/C14 بعد حسم المالكين.
- اختبار تعطل SH-03 وSH-04 fail-closed للأفعال التي تتطلب ذلك.
- اختبارات tenant isolation وaudit evidence لكل قرار وفعل حساس.

جميع هذه الاختبارات **PROPOSED / NOT RUN**. لا ننشئ Test IDs جديدة بوصفها source IDs.

## 6. قرارات الإغلاق

| Decision ID | القرار المطلوب | المالك/الأطراف |
|---|---|---|
| APR-DEC-118-01 | اعتماد قائمة SH-03 operations العامة وحالات lifecycle | SH-03 owner |
| APR-DEC-118-02 | إضافة/استبعاد C03 من consumer matrix | SH-03 + C03 |
| APR-DEC-118-03 | إضافة/استبعاد C07 من consumer matrix | SH-03 + C07 |
| APR-DEC-118-04 | إضافة/استبعاد C08 من consumer matrix وتحديد SH-06 boundary | SH-03 + C08 + SH-06 |
| APR-DEC-118-05 | إضافة/استبعاد C12 من consumer matrix | SH-03 + C12 |
| APR-DEC-118-06 | إضافة/استبعاد C14 أو اعتماد تجميع موافقات المصادر | SH-03 + C14 + data owners |
| APR-DEC-118-07 | اعتماد عملية/آلية استهلاك القرار قبل الفعل المحمي | SH-03 + SH-02 + Domain Owners |
| APR-DEC-118-08 | حسم دلالة عمليات decide* الحالية وعدم ازدواج القرار | C01 + C02 + C10 |
| APR-DEC-118-09 | إضافة/اعتماد قرار quality waiver لـAPR-C07-02 | C07 + C10/C11 حسب الأثر |
| APR-DEC-118-10 | اعتماد اختبارات مفردة أو تجميع مبرر لكل نوع موافقة | C01–C14 + QA |

## 7. الحكم

أصبحت هناك مصفوفة مشتقة لكل الأنواع الـ26 تربط request/submit/assign/decision/action، وكُشف اختلاف محدد في قائمة مستهلكي SH-03 بين الوثيقتين 23 و98. لكن لا تزال المصفوفة غير معتمدة، ولا يوجد إثبات لواجهات SH-03 أو تنفيذها أو اختبارها. لا تغلق هذه الوثيقة بوابة المواصفات ولا تسمح ببدء البرمجة.


## تصحيح منشأ ونطاق المستهلكين (تدقيق 119)

تثبت الوثيقة 119 من المصدر الأصلي (الفصل 126.1) أن خدمة الموافقات المركزية تخدم جميع المراكز، وأن محرك الموافقات مركزي (126.2). لذلك فإن غياب C03/C07/C08/C12/C14 من جدول مستهلكي SH-03 في الوثيقة 23 هو فجوة في المصفوفة المشتقة، وليس قرار استبعاد. كما أن أسماء عمليات SH-03 في 23 مقترحة وليست أسماء APIs حرفية مستعادة من DOCX. تُقرأ مصفوفة 118 باعتبارها crosswalk مقترحاً لا اعتماداً نهائياً.
