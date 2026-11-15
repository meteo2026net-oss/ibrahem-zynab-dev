# 117 — تدقيق دلالة عملية الموافقة: الطلب والقرار والتنفيذ

**الحالة:** DERIVED SEMANTIC REVIEW / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المراجع:** 97 (سجل العمليات الـ201)، 98 (سجل أنواع الموافقات الـ26)، 99، 113، 116.  
**الغرض:** منع اعتبار تطابق اسم العملية مع نوع موافقة دليلاً على وجود قرار اعتماد. هذا تدقيق دلالي أولي للأسماء والحقول الوصفية المتاحة، وليس اعتماداً لسلوك API أو workflow.

## 1. النتيجة التنفيذية

الوثيقتان 98 و116 تربطان كل نوع موافقة بعملية/مورد محمي. لكن عمود العملية في 98 يصف غالباً **الفعل المحمي الذي يتطلب موافقة**، وليس بالضرورة **عملية اتخاذ القرار**. لذلك فإن نتيجة 25/26 في 116 تعني وجود اسم الفعل المحمي في سجل العمليات، ولا تعني وجود 25 handler مستقلاً لقرار الموافقة.

- 26/26 أنواع ما زالت OWNER REVIEW.
- 25/26 أسماء الأفعال المحمية موجودة في سجل 97.
- APR-C07-02 وحده يشير إلى اسم غير موجود حرفياً: approveQualityWaiver؛ الموجود requestQualityWaiver لا يساويه.
- وجود أسماء صريحة مثل decide* وapprove* دليل تسمية على نية القرار، لكنه لا يثبت صلاحياتها أو lifecycle أو تنفيذها.
- لا يوجد في الأدلة الحالية ما يثبت عقداً عاماً مكتملاً لـSH-03 يحدد request/assign/decide/expire/revoke/consume.
- 0/26 ربط اختبار مفرد معتمد؛ APR-98-T01..12 اختبارات عامة لدورة الموافقة وليست تغطية مفردة مثبتة لكل نوع.

## 2. تصنيف العملية المشار إليها في كل نوع

| Approval ID | العملية المشار إليها في 98 | التصنيف الدلالي للاسم | ما الذي لا يثبته الاسم؟ | قرار الإغلاق المطلوب |
|---|---|---|---|---|
| APR-C01-01 | decideRequirementBaseline | DECISION-NAMED | لا يثبت فصل مقدم الطلب عن الموافق أو التحقق من النسخة | تأكيد أن C01 يملك القرار وأن SH-03 ينفذ دورة الموافقة |
| APR-C02-01 | decideDesignApproval | DECISION-NAMED | لا يثبت ربط القرار بـversion/hash | تأكيد حد C02/C03 ومالك القرار |
| APR-C03-01 | decideNavigationApproval | DECISION-NAMED | لا يثبت تحقق graph/hash أو أثر التنقل | تأكيد حد C02/C03 ومالك القرار |
| APR-C04-01 | decideSchemaApproval | DECISION-NAMED | لا يثبت أن الموافقة تشمل row policy أو قيوداً بعينها | تحديد مسؤولية C04 وC10 |
| APR-C04-02 | applyMigration | EXECUTION/GATED ACTION | لا يظهر اسم عملية قرار منفصل لهذه الموافقة؛ لا يجوز اعتبار applyMigration قراراً | تحديد هل تستخدم موافقة عامة في SH-03 أم يلزم قرار مجال منفصل |
| APR-C04-03 | requestRestore | REQUEST ACTION | الطلب ليس قراراً ولا تنفيذ restore نهائياً | تحديد قرار restore وعلاقته بملاك المجالات المتأثرة |
| APR-C05-01 | applyApprovedChangeSet | EXECUTION/GATED ACTION | الاسم يفترض موافقة سابقة لكنه لا يعرّف كيف اتخذ القرار | توثيق مرجع approval/hash والتحقق وقت التطبيق |
| APR-C05-02 | approveCodeBaseline | DECISION-NAMED | لا يثبت هوية المراجع المستقل أو version/hash | تأكيد C05 وشرط فصل الواجبات |
| APR-C06-01 | requestToolGrant | REQUEST ACTION | طلب المنح ليس قرار منح القدرة | تحديد من يقرر grant وكيف ينفذ SH-03/SH-02 القرار |
| APR-C06-02 | reviewAgentOutput | REVIEW ACTION / DECISION SEMANTICS UNCLEAR | المراجعة قد تكون ملاحظة أو قرار قبول/رفض؛ الاسم وحده لا يحسم | تحديد نتيجة المراجعة وأثرها ومالك المورد |
| APR-C07-01 | approveTestResults | DECISION-NAMED | لا يثبت استقلال المراجع أو معيار قبول النتائج | تحديد قرار C07 والاختبار المفرد |
| APR-C07-02 | approveQualityWaiver (MISSING) | MISSING DECISION/ACTION NAME | requestQualityWaiver في OP-C07-118 لا يثبت قرار الاستثناء | مالك C07 يحدد عملية القرار أو آلية SH-03 المعتمدة |
| APR-C08-01 | requestDeletion | REQUEST ACTION | لا يثبت قرار الحذف أو تنفيذ الحذف أو legal hold | تحديد القرار والتنفيذ ومالك البيانات |
| APR-C08-02 | requestExport | REQUEST ACTION | لا يثبت قرار التصدير أو إنشاء artifact | تحديد قرار التصدير ونطاق ACL |
| APR-C09-01 | startSync | EXECUTION/START ACTION | لا يثبت قرار السماح بالكتابة الخارجية أو الموافقة على connector scope | تحديد بوابة الموافقة قبل الأثر الخارجي |
| APR-C09-02 | beginAuthorization | AUTHORIZATION-START ACTION | بدء التفويض ليس بالضرورة قرار موافقة بشرياً | تحديد علاقة OAuth consent وسياسة C10 |
| APR-C10-01 | decideSecurityException | DECISION-NAMED | لا يثبت استقلال الموافق أو مدة الاستثناء | تحديد دور الموافق ومدة ونطاق الاستثناء |
| APR-C10-02 | requestPolicyChange | REQUEST ACTION | طلب تغيير السياسة ليس اعتماد إصدار السياسة | تحديد عملية قرار/نشر السياسة ومن يملكها |
| APR-C11-01 | approveRelease | DECISION-NAMED | لا يثبت artifact/hash/environment gate | تأكيد مالك C11 والمراجع المستقل |
| APR-C11-02 | deployRelease | EXECUTION/GATED ACTION | النشر فعل تنفيذي لا قرار اعتماد الإصدار | تحديد approval reference المطلوب قبل النشر |
| APR-C11-03 | requestRollback | REQUEST ACTION | الطلب ليس قرار rollback ولا تنفيذ rollback | تحديد قرار الملاك المتأثرين وآلية التنفيذ |
| APR-C12-01 | publishKnowledgeDocument | PUBLISH/EXECUTION ACTION | النشر لا يثبت اعتماد المحتوى أو المصدر | تحديد حالة الاعتماد السابقة ومالك المحتوى |
| APR-C12-02 | exportKnowledgeBundle | EXPORT ACTION | التصدير فعل؛ لا يثبت قرار السماح أو فحص ACL | تحديد قرار التصدير ومصدر الصلاحية |
| APR-C13-01 | applyOwnerSettingChange | EXECUTION/GATED ACTION | تطبيق الإعداد ليس قرار مالك المفتاح | تحديد قرار الاعتماد وتحقق النطاق/التوريث |
| APR-C13-02 | rollbackSettingVersion | EXECUTION/GATED ACTION | rollback ليس قرار اعتماد الرجوع | تحديد قرار مالك المفتاح وحالة السياسة |
| APR-C14-01 | exportDashboardReport | EXPORT ACTION | لا يثبت موافقة أصحاب المصادر أو C10 | تحديد تجميع قرارات المصادر وسياسة التصدير |

## 3. التمييز الإلزامي في النموذج

يجب أن يميز العقد النهائي بين الكيانات/الأفعال التالية، سواء نفذت كواجهات مستقلة أو كعمليات داخل SH-03:

1. **Approval Request:** إنشاء طلب مرتبط بنوع الموافقة والمورد ونسخته/بصمته والطالب والنطاق.
2. **Assignment:** تعيين الموافقين وفق الدور والسياسة، مع دعم التفويض المسموح.
3. **Decision:** approve/reject/request-changes/waive حيث يسمح النوع؛ قرار من صاحب الصلاحية وليس من منفذ orchestration.
4. **Execution/Consumption:** إعادة فحص القرار والصلاحية والسياسة والنسخة ثم تنفيذ الفعل المحمي مرة واحدة وفق idempotency.
5. **Expiry/Revocation/Supersession:** إبطال أو انتهاء الطلب/القرار وتسجيل السبب.
6. **Audit Evidence:** تسجيل غير قابل للتلاعب من الوكيل، يربط الطلب والقرار والتنفيذ والأدلة.

هذه مراحل مفاهيمية مطلوبة للتصميم؛ لم تعتمد بعد كـAPI names أو schemas أو state machine.

## 4. قواعد منع الخلط

- وجود اسم العملية في سجل 97 يثبت وجود الاسم في inventory فقط.
- الفعل المحمي ليس بالضرورة عملية القرار.
- request* لا تساوي decide* أو approve*.
- apply*/deploy*/publish*/export*/start* غالباً أفعال تنفيذ/بدء، ولا تعتبر قراراً لمجرد ورودها في سجل موافقات.
- review* يحتاج تعريف outcome؛ لا يفترض أنه قرار قبول.
- قد تكون عملية القرار عامة ضمن SH-03 بدلاً من API مجال منفصل، لكن لا توجد في الأدلة الحالية مواصفة كافية لإثبات ذلك.
- لا تضف عملية جديدة أو تعِد تسمية عملية موجودة قبل قرار مالك المجال وتحديث سجل المصدر/ADR والتتبّع.

## 5. اختبارات الإغلاق المطلوبة

لكل APR-Cxx يجب ربط سيناريو محدد يثبت، حسب انطباق النوع:

- إنشاء الطلب بالمرجع والنطاق والنسخة/البصمة الصحيحة.
- رفض actor غير المخول ورفض self-approval عند اشتراط الاستقلال.
- قرار approve/reject/request-changes موثق بالسبب والفاعل والتوقيت.
- منع تنفيذ الفعل المحمي قبل قرار صالح.
- رفض القرار المنتهي أو المسحوب أو المرتبط ببصمة مختلفة.
- منع إعادة التنفيذ أو تكرار الأثر الخارجي عند replay.
- إعادة فحص الصلاحيات والسياسة وقت التنفيذ المؤجل.
- إثبات أن SH-03 ينفذ سير العمل ولا ينتحل سلطة Domain Decision Owner.
- حفظ evidence/audit event مع tenant scope الصحيح.

APR-98-T01..12 تبقى عائلات/سيناريوهات عامة؛ لا تنسب إلى نوع مفرد دون crosswalk واعتماد.

## 6. قائمة القرارات التي ما زالت مفتوحة

| Decision ID | المسألة | الحالة |
|---|---|---|
| APR-DEC-117-01 | هل قرار الموافقة API مجال منفصل أم قرار عام عبر SH-03؟ | OWNER DECISION REQUIRED |
| APR-DEC-117-02 | تحديد قرار APR-C04-02 قبل applyMigration | OWNER DECISION REQUIRED |
| APR-DEC-117-03 | تحديد قرار restore النهائي لـAPR-C04-03 | OWNER DECISION REQUIRED |
| APR-DEC-117-04 | تحديد قرار منح الأداة لـAPR-C06-01 | OWNER DECISION REQUIRED |
| APR-DEC-117-05 | تحديد outcome لـreviewAgentOutput في APR-C06-02 | OWNER DECISION REQUIRED |
| APR-DEC-117-06 | تحديد قرار quality waiver لـAPR-C07-02 | OWNER DECISION REQUIRED |
| APR-DEC-117-07 | تحديد قرارات الحذف والتصدير لـAPR-C08-01/02 | OWNER DECISION REQUIRED |
| APR-DEC-117-08 | تحديد قرار مزامنة/تفويض connector لـAPR-C09-01/02 | OWNER DECISION REQUIRED |
| APR-DEC-117-09 | تحديد اعتماد إصدار السياسة في APR-C10-02 | OWNER DECISION REQUIRED |
| APR-DEC-117-10 | تحديد قرارات النشر والتراجع لـAPR-C11-02/03 | OWNER DECISION REQUIRED |
| APR-DEC-117-11 | تحديد اعتماد محتوى المعرفة/تصديرها لـAPR-C12-01/02 | OWNER DECISION REQUIRED |
| APR-DEC-117-12 | تحديد قرار تغيير/تراجع الإعدادات لـAPR-C13-01/02 | OWNER DECISION REQUIRED |
| APR-DEC-117-13 | تحديد موافقات مصادر البيانات قبل APR-C14-01 | OWNER DECISION REQUIRED |

## 7. الحكم

تم تحسين دقة تفسير سجل الموافقات، لكن لا يمكن إعلان اكتمال دورة الموافقات. نتيجة 25/26 في الوثيقة 116 هي **وجود اسم الفعل المحمي**، وليست 25 عملية قرار مثبتة. يلزم اعتماد قرار APR-DEC-117-01 أولاً، ثم حسم الحالات بحسب المراكز، وربط كل نوع باختبار مفرد أو تجميع مبرر.

لا تنشئ هذه الوثيقة كوداً أو API أو workflow، ولا تغلق بوابة موافقة بدء البرمجة.
