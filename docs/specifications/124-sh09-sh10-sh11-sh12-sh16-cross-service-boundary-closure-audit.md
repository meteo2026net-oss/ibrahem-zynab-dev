# 124 — تدقيق إغلاق حدود SH-09 وSH-10 وSH-11 وSH-12 وSH-16

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED CROSS-SERVICE CLOSURE AUDIT / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**النطاق:** الأحداث، الإصدارات، التكاليف والميزانيات، الإعدادات، المعرفة والوثائق.  
**المراجع:** 23 و32 و36 و67 و68 و72 و77–90 و94–100 و106–123.

## 1. الغرض وحدود المصدر

تسرد قائمة 132.6 الخدمات SH-09..SH-16 بالاسم والمسؤولية المختصرة. لا يحتوي المصدر المتاح على عقود تفصيلية مستقلة لهذه الخدمات، والفصل 157 الذي كان مطلوباً لتوحيد الخدمات لم يُستعد نصه الكامل. لذلك فإن هذا التدقيق يوحد حدود الحزم المقترحة في 32 و83 و85 و86 و90، ولا يدعي أنها قرارات مصدرية نهائية.

يركز هذا التدقيق على SH-09 (الأحداث)، SH-10 (الإصدارات)، SH-11 (التكاليف)، SH-12 (الإعدادات)، SH-16 (المعرفة)، ويأخذ في الاعتبار تقاطعات C11/SH-15 وC12/SH-07 وC10/SH-02/04.

## 2. مصفوفة ملكية الحقيقة

| المجال | مالك الحقيقة المقترح | دور الخدمة المشتركة | الحد الواجب حمايته |
|---|---|---|---|
| حدث المجال ومحتواه الدلالي | المركز/الخدمة المنتجة | SH-09 يملك سجل النشر والتسليم والاشتراك وإعادة المعالجة | سجل النقل لا يصبح Domain SoR |
| نتيجة استهلاك الحدث | المستهلك/مالك العملية | SH-09 يحفظ delivery reference/status | Delivered لا يعني نجاح أثر المجال |
| محتوى المورد وإصداره | مالك المورد في مركزه | SH-10 يملك version/change-set/snapshot metadata | لا يملك محتوى المجال ولا يمحو التاريخ |
| دورة الإصدار والنشر | C11 | SH-10 يوفر metadata عامة للإصدارات | لا يحل SH-10 محل release/deployment lifecycle |
| الاستهلاك والسعر الأصلي | المنتج/مزود القياس | SH-11 يجمع usage/cost records ويحسب التجميعات | لا يخترع سعر أو عملة أو مصدر فوترة |
| ميزانية النطاق | مالك الميزانية/المؤسسة وفق قرار معتمد | SH-11 يدير سجل الميزانية والتنبيه | لا يعتمد حدوداً أو تجاوزات نيابة عن المالك |
| إعدادات المجال | المركز المالك للإعداد | SH-12 يخزن/يوفر آلية القيم والوراثة | لا ينقل ملكية معنى الإعداد للخدمة |
| سياسة الإعدادات الحساسة | C10 والمالك المختص | SH-12 يطبق version/precedence وفق السياسة | لا يغير إعداداً محمياً دون موافقة |
| المستند الأصلي ومحتواه | C12 أو مالك المجال | SH-16 يدير ingestion/index/provenance metadata | index/embedding/snippet ليس مصدراً أصلياً |
| البحث العام | مالك البحث وفق العقد | SH-07 ينفذ الاسترجاع العام؛ SH-16 المعرفة الدلالية | لا تجاوز ACL أو عزل المستأجر |
| سجل Artifact وإصدار Build/Release | C11 | SH-10 يحتفظ بمرجع إصدار عام عند الحاجة | binary authority والتوقيع منفصلان ومفتوحان |

## 3. قرارات الحدود المفتوحة

| ID | الحد | القرار المطلوب | الحالة |
|---|---|---|---|
| XSH-124-01 | Domain Event / Transport Event | اعتماد event ownership وproducer contract وpayload minimization | OPEN |
| XSH-124-02 | Event delivery / business completion | اعتماد acknowledgement semantics وconsumer commit reference | OPEN |
| XSH-124-03 | Replay / side effects | اعتماد deduplication window وidempotency لكل مستهلك | OPEN |
| XSH-124-04 | Event schema evolution | اعتماد compatibility policy وversion support window | OPEN |
| XSH-124-05 | Dead-letter / retention | اعتماد من يراجع ويعيد ويغلق dead-letter ومدد الاحتفاظ | OPEN |
| XSH-124-06 | Resource version / content storage | اعتماد canonical contentRef/hash ومالك bytes لكل resource type | OPEN |
| XSH-124-07 | Version restore / domain rollback | اعتماد الفرق بين restore reference وrollback دلالي في المجال | OPEN |
| XSH-124-08 | C11 release / SH-10 version | اعتماد حدود release candidate مقابل generic version metadata | OPEN |
| XSH-124-09 | Usage source / cost record | اعتماد المصدر الأصلي ووحدة القياس والفترة وتصحيح السجلات | OPEN |
| XSH-124-10 | Budget owner / cost evaluator | تعيين مالك الميزانية وصلاحية تعديلها ومراجعة التوقعات | OPEN |
| XSH-124-11 | Thresholds / alerts | اعتماد عتبات 50/75/90/100 أو رفضها صراحة؛ تبقى مقترحات حالياً | OPEN |
| XSH-124-12 | Currency / rate / estimate | اعتماد العملة ومصدر سعر الصرف والتمييز بين actual/estimated | OPEN |
| XSH-124-13 | Configuration precedence | اعتماد أولوية system/org/workspace/project/environment/user/agent إن انطبقت | OPEN |
| XSH-124-14 | Secret-bearing configuration | اعتماد مراجع الأسرار ومالك KMS ومنع إرجاع القيم السرية | OPEN |
| XSH-124-15 | Configuration change approval | اعتماد مستويات الأثر وربط الموافقة بإصدار القيمة والبصمة | OPEN |
| XSH-124-16 | Canonical knowledge / index | تعيين مالك المصدر وSH-16 مقابل SH-07 وحدود المحتوى | OPEN |
| XSH-124-17 | ACL changes / derived indexes | اعتماد إعادة التحقق وإبطال chunks/embeddings/cache بعد سحب الصلاحية | OPEN |
| XSH-124-18 | Document deletion / legal hold | اعتماد انتشار retention/legal hold إلى المشتقات والنسخ | OPEN |
| XSH-124-19 | AI generated knowledge | اعتماد متى تصبح المخرجات وثيقة رسمية ومن يراجعها | OPEN |
| XSH-124-20 | Cross-service audit | اعتماد event lineage وcorrelation/audit references عبر الخدمات الخمس | OPEN |

## 4. قواعد التكامل المطلوبة

1. كل حدث يحمل معرفاً فريداً وإصداراً ومصدراً ونطاقاً ووقت وقوع ومرجع ارتباط؛ لا يضمن النقل ترتيباً عالمياً.
2. إعادة المعالجة لا تعيد أثر المجال؛ المستهلك يطبق idempotency ويعيد التحقق من التفويض.
3. تغيير إصدار المورد ينشئ تاريخاً قابلاً للتتبع؛ لا يمحو إصداراً منشوراً أو يستبدل محتواه بصمت.
4. تظل موافقة التغيير مرتبطة ببصمة المورد والإعداد/الإصدار المحدد؛ التغيير الجوهري يبطل الموافقة السابقة.
5. سجلات التكلفة تميز actual عن estimate، وتحفظ مصدر القياس والسعر والفترة والعملة؛ لا تحول القيم المقترحة إلى فواتير معتمدة.
6. الإعدادات الحساسة لا تظهر قيمها في logs/events/audit؛ تستخدم secret references مع صلاحيات منفصلة.
7. يعاد فحص ACL عند retrieval/export/download، وليس عند ingestion فقط.
8. حذف المصدر أو سحب صلاحيته يطلق إبطال المشتقات وفق SLA معتمد؛ لا يبقى embedding أو snippet متاحاً لمجرد بقائه في الفهرس.
9. لا تتحول إجابة AI أو ملخص آلي إلى canonical knowledge دون اعتماد مالك المحتوى.
10. SH-04 يملك سجل التدقيق المحمي؛ بقية الخدمات تصدر مراجع أحداث التدقيق ولا تنشئ سجلاً موازياً مستقلاً.

## 5. سيناريوهات التكامل المشتقة

هذه معرفات محلية مشتقة للتخطيط، وليست Test IDs مصدرية ولا اختبارات منفذة.

| Local Scenario ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| XSH-124-T01 | نشر الحدث نفسه مرتين | لا يكرر المستهلك أثر المجال | SPECIFIED / NOT RUN |
| XSH-124-T02 | مستهلك متوقف ثم replay | لا فقد صامت ولا أثر مكرر؛ lineage محفوظ | SPECIFIED / NOT RUN |
| XSH-124-T03 | event schema غير مدعوم | عزل/رفض واضح دون إسقاط غير مسجل | SPECIFIED / NOT RUN |
| XSH-124-T04 | delivery acknowledged دون domain commit | لا تعرض العملية على أنها مكتملة | SPECIFIED / NOT RUN |
| XSH-124-T05 | تعديل نسخة قديمة | لا يستبدل الإصدار الأحدث دون conflict resolution | SPECIFIED / NOT RUN |
| XSH-124-T06 | restore version reference | يحفظ التاريخ ويطبق domain rollback فقط بعقد المجال | SPECIFIED / NOT RUN |
| XSH-124-T07 | cost record بلا source/rateRef | لا يصنف كقيمة actual موثوقة | SPECIFIED / NOT RUN |
| XSH-124-T08 | estimate مقابل actual | يعرض النوع ومصدره وفترته بوضوح | SPECIFIED / NOT RUN |
| XSH-124-T09 | تجاوز budget threshold غير معتمد | لا يعرض الحد كمؤسسة-approved policy | SPECIFIED / NOT RUN |
| XSH-124-T10 | تحديث إعداد حساس دون approval | يرفض أو يبقي التغيير pending وفق السياسة | SPECIFIED / NOT RUN |
| XSH-124-T11 | تعارض قيم إعدادات متعددة المستويات | precedence deterministic وموثق مع provenance | SPECIFIED / NOT RUN |
| XSH-124-T12 | secret config في log/event | لا تظهر القيمة السرية | SPECIFIED / NOT RUN |
| XSH-124-T13 | سحب ACL بعد ingestion | retrieval يرفض ويبدأ invalidation وفق SLA | SPECIFIED / NOT RUN |
| XSH-124-T14 | مصدر knowledge محذوف أو legal hold | تتبع سياسة الإبطال/الحجز عبر كل المشتقات | BLOCKED: policy TBD |
| XSH-124-T15 | prompt injection في مستند | يعامل المحتوى كمصدر غير موثوق ولا يغير سياسات النظام | SPECIFIED / NOT RUN |
| XSH-124-T16 | AI-generated knowledge | لا يصبح canonical قبل اعتماد المالك | SPECIFIED / NOT RUN |
| XSH-124-T17 | cross-tenant search/index | لا يظهر عنوان أو snippet أو embedding-derived result خارج النطاق | SPECIFIED / NOT RUN |
| XSH-124-T18 | audit lineage بين الخدمات | يمكن تتبع actor/action/resource/approval/operation عبر correlation refs | SPECIFIED / NOT RUN |

## 6. أثر التدقيق على السجلات الحالية

- تبقى 32 baseline مقترحاً لا عقداً معتمداً؛ هذا التدقيق لا يغير حالة SH-09..SH-16.
- تبقى حزم 83–90 قرارات مفتوحة؛ لا يغلق هذا التدقيق أي OWN36/OWN72.
- لا تُعتمد عتبات 50%/75%/90%/100% أو أي أسعار أو عملات أو حدود تكلفة.
- لا تُعتمد أولوية إعدادات جديدة أو سياسة أسرار أو retention افتراضية.
- لا تنشأ Exact API/Event/Test mappings في 97/100/105/109؛ معرفات XSH-124 محلية مشتقة.
- لا تحسم هذه الوثيقة ملكية binary artifacts في 95 أو موضع validateBuildConfiguration بين C05/C11.
- لا تنشئ APIs أو schemas أو migrations أو queues أو vector stores أو cost meters أو configuration engines.

## 7. بوابة الإغلاق

1. اعتماد مالكي الخدمات SH-09/10/11/12/16 وملاك المجالات المقابلة.
2. اعتماد Domain SoR وscope وclassification لكل مورد.
3. اعتماد event semantics/replay/idempotency/schema evolution.
4. اعتماد version lineage وC11 release boundary وartifact binary authority.
5. اعتماد budget owner وsource/rate/currency/thresholds بعد مراجعة مالية.
6. اعتماد config precedence وsecret ownership وapproval classes.
7. اعتماد canonical knowledge, ACL, provenance, deletion/legal hold propagation.
8. ربط كل API/Event/Approval باختبار قبول مفرد أو استثناء معتمد.
9. تنفيذ الاختبارات وإرفاق run IDs والأدلة.
10. إدخال النتائج في تقرير الجاهزية ثم طلب اعتماد المواصفات النهائي.

## 8. الخلاصة

حدود الملكية المقترحة متماسكة على المستوى المفاهيمي: SH-09 ينقل الأحداث ولا يملك حقيقة المجال؛ SH-10 يدير metadata للإصدارات ولا يملك المحتوى؛ SH-11 يجمع ويقيم التكلفة دون اختراع أسعار أو ميزانيات؛ SH-12 يدير آلية الإعدادات دون امتلاك معنى كل إعداد؛ SH-16 يدير المعرفة المشتقة مع بقاء المصدر لدى مالكه. لكن القرارات العشرين مفتوحة والسيناريوهات الثمانية عشر لم تنفذ.

**NO IMPLEMENTATION / NO APPROVED API OR SCHEMA / NO TEST EXECUTION / SPECIFICATION APPROVAL GATE REMAINS CLOSED.**
