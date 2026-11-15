# 86 — حزمة ملكية الإعدادات وأولوية الوراثة SH-12

**الحالة:** DERIVED CONTRACT / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** 23 و32 و36 و72 و77 و78 و79 و80.

## 1. الحدود

تثبت المصادر أن SH-12 خدمة إعدادات مشتركة لإدارة تعريفات الإعدادات ومخططاتها وقيمها وإصداراتها وطبقات الوراثة. كما تفصل الوثيقة 36 بين:
- SH-12: تعريفات وآلية القيم المشتركة وتاريخها.
- مالك المجال: دلالة إعدادات المجال وقواعدها.
- C13: تجربة المستخدم وإدارة الطلبات والتفضيلات، لا امتلاك دلالة مفاتيح المجال.
- Secret Manager: القيمة السرية الفعلية، لا SH-12 كسجل إعدادات نصي.

الترتيب النهائي للوراثة غير معتمد. لا يجوز افتراض ترتيب ثابت للمنصة والمؤسسة ومساحة العمل والمشروع والبيئة والمستخدم.

## 2. نموذج الملكية المرشح

| المورد | مالك الحقيقة المقترح | حدود الخدمة |
|---|---|---|
| Setting definition/schema العام | SH-12 ضمن حوكمة المنصة | الاسم والوصف والنوع والتحقق والنطاق المسموح |
| Setting value المشترك | SH-12 | القيمة غير السرية وإصدارها ومصدرها |
| معنى setting خاص بالمجال | مالك المجال | الدلالة والآثار الجانبية وقواعد التغيير |
| إعداد المستخدم الشخصي | C13 للطلب/التفضيل؛ SH-12 للتخزين إن فُوض | لا يغير إعداداً أعلى نطاقاً |
| Secret value | Secret Manager/مزود الأسرار المعتمد | SH-12 يخزن secretRef فقط |
| Effective configuration | إسقاط محسوب بواسطة SH-12 | لا يصبح system of record مستقل |
| Change request/approval | C13 واجهة الطلب؛ SH-03 الموافقة؛ مالك المفتاح يقرر | لا تطبيق دون تفويض وموافقة مطلوبة |

## 3. قرارات أولوية الوراثة

| ID | القرار | الحالة |
|---|---|---|
| CFG-86-01 | اعتماد النطاقات التي يمكن أن تملك إعدادات: platform/tenant/organization/workspace/project/environment/user | OPEN |
| CFG-86-02 | اعتماد precedence وترتيب حل التعارضات | OPEN |
| CFG-86-03 | تحديد هل بعض الإعدادات immutable أو platform-enforced | OPEN |
| CFG-86-04 | تحديد قواعد null/unset/inherit/explicit reset | OPEN |
| CFG-86-05 | تحديد الاستثناءات والـoverride authority لكل key | OPEN |
| CFG-86-06 | تعيين مالك الدلالة لكل domain setting | OPEN |
| CFG-86-07 | اعتماد schema registry/version compatibility | OPEN |
| CFG-86-08 | اعتماد تخزين الأسرار ومراجعها والتدوير والإبطال | OPEN |
| CFG-86-09 | اعتماد الموافقات وفصل المهام للإعدادات عالية الأثر | OPEN |
| CFG-86-10 | اعتماد rollback semantics وإبطال cache وإعادة تحميل الخدمات | OPEN |
| CFG-86-11 | اعتماد audit/retention/classification لكل فئة إعدادات | OPEN |
| CFG-86-12 | اعتماد حالات الطوارئ وbreak-glass دون تسريب أسرار | OPEN |

## 4. قواعد أمان وتشغيل

1. لا تُعاد الأسرار الخام في getEffectiveConfig أو logs أو events؛ يعاد masked value أو secretRef بحسب السياسة.
2. لا يتجاوز override إعداداً أعلى نطاقاً إلا إذا سمحت policy صريحة بذلك.
3. يرفض update عند stale expectedVersion أو schema mismatch.
4. كل تغيير يحمل actor وscope وreason وversion وcorrelation reference.
5. إعادة التراجع تنشئ version/change event جديداً ولا تمحو التاريخ.
6. لا يستطيع AI Agent توسيع صلاحياته أو تغيير سياسات المنصة أو secrets دون موافقة مستقلة.
7. يجب بيان مصدر كل قيمة فعالة وسلسلة الوراثة، مع حجب القيم الحساسة.
8. يجب على المستهلك إعادة التحقق من التهيئة عند تغيّر النسخة أو إبطال cache.

## 5. اختبارات القبول

| Test ID | الحالة | النتيجة | الوضع |
|---|---|---|---|
| CFG-86-T01 | إعداد غير مطابق للمخطط | رفض دون حفظ جزئي | SPECIFIED / NOT RUN |
| CFG-86-T02 | قيمة موروثة | إظهار المصدر وسلسلة الوراثة | SPECIFIED / NOT RUN |
| CFG-86-T03 | سر في إعداد | لا يظهر النص الخام في API أو log | SPECIFIED / NOT RUN |
| CFG-86-T04 | override غير مخول | deny مع audit ref | SPECIFIED / NOT RUN |
| CFG-86-T05 | expectedVersion قديم | conflict دون lost update | SPECIFIED / NOT RUN |
| CFG-86-T06 | rollback | إصدار جديد مع provenance | SPECIFIED / NOT RUN |
| CFG-86-T07 | إبطال cache | لا تستمر قيمة قديمة بعد SLA المعتمد | BLOCKED: cache invalidation SLA TBD |
| CFG-86-T08 | إعداد platform-enforced | لا يمكن خفضه من نطاق أدنى | BLOCKED: key policy TBD |
| CFG-86-T09 | agent self-change | لا self-grant ولا تجاوز موافقة | SPECIFIED / NOT RUN |
| CFG-86-T10 | Secret Manager غير متاح | fail closed للإجراء الذي يحتاج السر | SPECIFIED / NOT RUN |

## 6. بوابة الإغلاق

تعيين مالك SH-12 ومالك الدلالة لكل إعداد؛ اعتماد precedence والاستثناءات؛ تحديد secret provider؛ اعتماد schema/versioning/cache/audit/retention؛ ثم تحديث 36 و73 و77–80 بعد الموافقة وتنفيذ الاختبارات.

لا تنشئ هذه الوثيقة جداول أو APIs أو خدمة أو ترتيب وراثة معتمداً، ولا تغلق OWN36-05 أو FLD-19.

**SH-12 CONFIGURATION OWNERSHIP / OPEN / NOT APPROVED / NO IMPLEMENTATION.**
