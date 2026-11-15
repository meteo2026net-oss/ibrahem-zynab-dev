# 83 — حزمة حسم ملكية SH-11 وعقود التكلفة والميزانيات

**التاريخ:** 2026-11-15  
**الحالة:** SOURCE-BASED DECISION DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** الوثائق 23 و32 و36 و56 و73 و76 و77 و80 و82، والفصل 141 كما أحالت إليه الوثيقة 32.

## 1. سبب إعداد الحزمة

تثبت الوثيقة 32 وجود SH-11 بوصفها خدمة التكاليف والميزانيات، وتحدد مسؤوليتها وعملياتها ومدخلاتها ومخرجاتها وضوابطها واختباراتها المرشحة. كما تسند الوثيقة 36 التداخل المتعلق بـSH-11 إلى OWN36-07. لكن حزمة القرارات 72/76 لا تتضمن قراراً مستقلاً مطابقاً يغطي هذا الموضوع. لذلك تبقى ملكية SH-11 فجوة لا يجوز إغلاقها بإعادة استخدام OWN72-07، لأنه يخص C04 ومراكز المجال.

## 2. ما هو مثبت في المصدر وما هو غير مثبت

| الموضوع | ما يثبته المصدر | ما لا يزال مفتوحاً |
|---|---|---|
| المسؤولية | تجميع الاستهلاك والتكلفة ومقارنتها بميزانيات النطاق | تعريفات ledger النهائية وحدود الخدمة |
| ملكية SH-11 المقترحة | سجلات الاستهلاك المحاسبية المجمعة والميزانيات والتنبيهات والتقديرات | المالك التشغيلي النهائي ومراجعته من أصحاب الخدمات |
| مصدر القياس والسعر | يبقى لدى المزود أو المجال المسؤول مع حفظ lineage | سجل provider/meter/rate authoritative |
| المستهلكون | C06 وC13 وC14 وخدمات AI والبناء والتخزين والتكامل بحسب المصفوفة | القائمة النهائية وتأكيد كل مستهلك |
| العمليات | ingestUsage, recordCost, estimateCost, createBudget, evaluateBudget, getCostBreakdown, closeCostPeriod | API schemas وصلاحيات كل عملية |
| المدخلات | provider, meter, quantity, unit, rateRef, currency, scope, usagePeriod, sourceRef, confidence/estimate flag | الأنواع والقيود والإصدارات وقواعد التحقق |
| المخرجات | normalized usage record, cost item, budget status, forecast, source lineage | canonical schema وحقول التدقيق |
| العملة | يجب عدم جمع عملات مختلفة دون سعر تحويل ومصدر وتاريخ | العملة الأساسية ومصدر FX وتوقيت السعر |
| التقديري/الفعلي | يجب الفصل بينهما | سياسة التسوية والتعديل وإعادة الحساب |
| التكرار | يجب منع احتساب سجل الاستخدام المكرر مرتين | idempotency key ونطاقه ومدة الاحتفاظ |
| الفترات | closeCostPeriod موجود كعملية مرشحة | تعريف الفترة، الإغلاق، reopening، adjustment |
| الضرائب والفوترة | مذكورة ضمن القرارات المفتوحة | شمول الضرائب، الفوترة، التقريب |
| الميزانيات | createBudget/evaluateBudget | من يملك اعتماد الميزانية ومن يملك تعديلها |
| العتبات | 50/75/90/100 لا تعتمد تلقائياً | القيم والسياسات والتنبيه والإجراء |
| الخصوصية | scope وsourceRef مطلوبان | تصنيف بيانات التكلفة والاحتفاظ والحذف |

## 3. فصل الملكية المقترح — يحتاج اعتماداً

| المورد | المالك المقترح | الحد |
|---|---|---|
| provider meter/raw usage evidence | المزود أو المركز/الخدمة المنتجة | يحتفظ بمصدر القياس الأصلي ومرجعه |
| provider rate/rate card | المزود أو مالك العقد التجاري | مصدر السعر والتاريخ والوحدة والعملة |
| normalized usage ledger | SH-11 | تطبيع القياس مع المصدر والـidempotency |
| cost calculation record | SH-11 | حفظ كمية ووحدة وسعر ومصدر حساب ونسخة القاعدة |
| estimate/forecast | SH-11 | تمييز صريح عن actual |
| budget definition and scope | SH-11 تشغيلياً؛ مالك النطاق/الميزانية يعتمد | لا يملك SH-11 قرار الميزانية التجارية نيابة عن صاحبها |
| budget alert state | SH-11 | حالة تنبيه وتشغيل فقط، لا تغيير تلقائي لسياسة المنتج |
| source-domain usage attribution | C06/C09/C11 أو مصدر القياس | SH-11 لا يختلق نسب الاستخدام |
| dashboard projection | C14 | قراءة فقط من SH-11 مع source/version/as_of/freshness/ACL |
| settings for budget thresholds | مالك إعداد المجال + SH-12 runtime | C13 UX؛ SH-12 values؛ لا عتبات افتراضية معتمدة |

## 4. سجل قرارات مقترح دون اعتماد

المعرفات أدناه محلية لهذه الحزمة ولا تستبدل OWN36-07:

| Decision ID | القرار المطلوب | المالك الذي يجب تعيينه | حالة القرار |
|---|---|---|---|
| COST-83-01 | تعيين مالك SH-11 التشغيلي ومالك سجل التكلفة | مالك منصة/خدمة معتمد | OPEN |
| COST-83-02 | تحديد المصدر المرجعي لكل provider/meter/rate | مالكو المزودين/المجالات | OPEN |
| COST-83-03 | اعتماد currency وFX source/time/precision/rounding | مالك المالية/الميزانية | OPEN |
| COST-83-04 | اعتماد actual vs estimate vs forecast وreconciliation | مالك SH-11 ومالكو القياس | OPEN |
| COST-83-05 | اعتماد idempotency/replay/late-arrival/adjustment | مالك SH-11 وSH-09 | OPEN |
| COST-83-06 | اعتماد budget scope/owner/approval/amendment | مالك كل ميزانية وSH-03 | OPEN |
| COST-83-07 | اعتماد thresholds/notifications/actions | مالك الميزانية وSH-05 | OPEN |
| COST-83-08 | اعتماد retention/classification/access/export | مالك SH-11 وC10 | OPEN |
| COST-83-09 | اعتماد cost API/event schemas وإصداراتها | API Owner وEvent Owner | OPEN |
| COST-83-10 | اعتماد اختبارات المطابقة والتسوية والعزل | مالك C07 وSH-11 | OPEN |

## 5. الحد الأدنى لعقد الاستخدام والتكلفة — حقول مصدرية/مرشحة

هذه قائمة تحقق وليست schema تنفيذية:

- provider وmeter وsourceRef.
- quantity وunit.
- rateRef وcurrency مع مصدر السعر وتاريخ صلاحيته.
- scope: organization/workspace/project/environment عند انطباقها.
- usagePeriod ووقت القياس ووقت التسجيل عند الحاجة.
- confidence/estimate flag لتمييز التقديري.
- idempotency/reconciliation reference لمنع التكرار وربط التسوية.
- provenance للقياس والتحويل والحساب.
- actor/authorization/approval refs عند إنشاء أو تعديل الميزانيات.
- contractVersion وrecordVersion/expectedVersion عند الكتابة.

كل الأنواع وnullability والقيود والـindexes والاحتفاظ ما زالت TBD حتى يعتمدها المالك.

## 6. اختبارات القبول الواجب ربطها

| Test ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| COST-01 | تقديري مقابل فعلي | لا تختلط القيم أو التقارير | SPECIFIED / NOT RUN |
| COST-02 | ingestUsage مكرر | لا يحتسب الأثر مرتين | SPECIFIED / NOT RUN |
| COST-03 | مبلغ دون source/rate/unit/period | يرفض أو يعزل وفق سياسة معتمدة | SPECIFIED / NOT RUN |
| COST-04 | عتبة 50/75/90/100 غير معتمدة | لا تتحول إلى منع أو إجراء تلقائي | SPECIFIED / NOT RUN |
| COST-83-05 | عملات مختلفة | لا جمع دون FX source/time موثق | SPECIFIED / NOT RUN |
| COST-83-06 | وصول tenant/project خارج الصلاحية | رفض خادمي دون كشف بيانات | SPECIFIED / NOT RUN |
| COST-83-07 | late usage بعد إغلاق الفترة | مسار adjustment/reopen مع تدقيق | BLOCKED: policy TBD |
| COST-83-08 | تعديل ميزانية | موافقة المالك وversion check والتدقيق | SPECIFIED / NOT RUN |
| COST-83-09 | إعادة حساب cost | reproducible عبر rate/logic version/source | SPECIFIED / NOT RUN |
| COST-83-10 | projection إلى C14 | read-only مع source/version/freshness/ACL | SPECIFIED / NOT RUN |

لا توجد نتائج تنفيذ أو run IDs أو build/environment evidence لهذه الاختبارات.

## 7. أثر الحزمة على الوثائق القائمة

- OWN36-07 يبقى المرجع التاريخي لموضوع SH-11.
- لا ينشأ OWN72-11 تلقائياً ولا يعاد استخدام OWN72-07.
- يجب أن يقرر المالكون ما إذا كان القرار الجديد يضاف إلى 72/76 أو يسجل كقرار منفصل؛ لا تُعدل 36 قبل اعتمادهم.
- FLD-07 يظل مفتوحاً حتى اعتماد ledger/reconciliation ownership.
- SH-11 جزء من SH-01..SH-16 كما ورد في المصدر؛ لا تضف SH-17..SH-20.
- C06 يملك سياق مزود/نموذج وusage refs؛ SH-11 يملك normalized cost ledger المقترح؛ لا تنقل هذه الحزمة ملكية بيانات C06.

## 8. بوابة الإغلاق

- [ ] تعيين مالك SH-11 وملاك الميزانيات.
- [ ] اعتماد currency/rate/FX/rounding/tax/period policies.
- [ ] اعتماد actual/estimate/forecast/reconciliation.
- [ ] اعتماد idempotency/late-arrival/adjustment/closed-period behavior.
- [ ] اعتماد budget scope/thresholds/approval and notification rules.
- [ ] اعتماد classification/retention/access/export.
- [ ] اعتماد API/event schemas والصلاحيات.
- [ ] ربط جميع اختبارات COST بمتطلبات ومحاور 45–49 وC07.
- [ ] تنفيذ الاختبارات في بيئة معتمدة وإرفاق الأدلة.
- [ ] تسجيل قرار مالك موثق ثم تحديث 36 و76/سجل القرارات.

**لا تمنح هذه الوثيقة إذناً بإنشاء قاعدة بيانات أو خدمة أو واجهة أو كود. ولا تعني اعتماد سياسة مالية أو أسعار أو عملة. موافقة بدء البرمجة مستقلة وصريحة.**

**SH-11 DECISION DOSSIER / OPEN FOR OWNER REVIEW / NOT APPROVED / NO IMPLEMENTATION.**
