# 94 — تدقيق التكامل المرجعي لعقود API والأحداث والموافقات والاختبارات

**الحالة:** DOCUMENTARY AUDIT / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع المباشرة:** 36، 72، 77–82، 91–93، عقود المراكز 59–70.  
**الحد:** مراجعة بنيوية لمحتوى الملفات الحالية على main. لا تمثل تشغيل فاحص آلي، ولا تثبت تنفيذ API أو اختبار.

## 1. النتيجة

سجل 79 يغطي أسماء عمليات لكل مركز C01–C14، ويعرض مجموعات أحداث ومعايير موافقات عامة. سجل 80 يغطي اختبارات مراكز واختبارات تكامل وأمن، لكن الربط الحالي ليس بعد traceability مكتملة على مستوى كل عملية/حدث/موافقة. لا يمكن اعتبار مجرد ظهور العملية والاختبار في وثيقتين منفصلتين دليلاً على وجود علاقة تغطية مباشرة.

**النتيجة:** API/EVENT/APPROVAL TRACEABILITY = PARTIAL / OWNER REVIEW REQUIRED.  
لا يُعلن أي عقد أو اختبار معتمداً أو منفذاً من هذا التدقيق.

## 2. قواعد الربط الإلزامية المقترحة

لكل عملية API أو حدث أو موافقة، يجب تسجيل:
1. Contract ID ثابت ونسخة العقد.
2. نوع العملية (Query/Command/Job/Event/Approval) واسمها الدلالي.
3. Domain Owner وDomain SoR، منفصلين عن API Owner وEvent Producer/Consumer Owner.
4. قرار ملكية مؤهل OWN36-nn أو OWN72-nn، أو OPEN مع سبب واضح.
5. محور متطلب AX-001..064 ومعرف requirement/source trace.
6. اختبار قبول واحد على الأقل بمعرف ثابت؛ وإذا لم ينطبق، يسجل N/A مع مبرر مالك.
7. Authorization action/resource/scope وApproval policy عند اللزوم.
8. schema/version, idempotency, concurrency, retry, timeout, error semantics بحسب النوع.
9. evidence fields المطلوبة عند الاختبار؛ ولا يسجل PASSED دون run/build/commit/environment/actor/evidence.
10. الحالة: SOURCE-RECOVERED / PROPOSED / OWNER-REVIEW / APPROVED / IMPLEMENTED / EXECUTED، مع منع القفز بين الحالات.

## 3. نتائج التدقيق على السجلات الحالية

| ID | النتيجة | الدليل في السجل الحالي | أثرها | الإجراء المطلوب | الحالة |
|---|---|---|---|---|---|
| RI-94-01 | قائمة عمليات API موجودة لكل C01–C14، لكنها مجموعات أسماء لا سجلات عملية مفردة | 79 §2 | لا يمكن تتبع payload/owner/test لكل عملية | تفكيك كل عملية إلى سجل عقد مستقل | OPEN |
| RI-94-02 | سجل الأحداث المركزي 79 لا يحتوي صفاً مستقلاً لـC01، رغم وجود أحداثه في العقد المصدر 59 | 79 §3 مقابل 59 | فجوة تجميع/تتبع في السجل المركزي، وليست غياباً من كل المصدر | إضافة أحداث C01 المستعادة إلى inventory وربطها بمحاور واختبارات | OPEN |
| RI-94-03 | أحداث C06 وC07 مدمجة في صف واحد | 79 §3 | غموض Domain Owner وProducer وSoR | فصل أحداث AI عن أحداث QA | OPEN |
| RI-94-04 | أحداث C09 تشير إلى C09/SH-08/09/13 كمنتجين | 79 §3 | خلط معنى حدث المجال مع التنفيذ والتسليم والموصل | C09 producer للأحداث الدلالية؛ SH-08 execution refs؛ SH-09 transport؛ SH-13 connector runtime، بعد اعتماد المالكين | OPEN |
| RI-94-05 | أحداث C11 تشير إلى C11/SH-08/14/15 كمنتجين | 79 §3 | خلط release/build domain مع execution/telemetry/backup | فصل event producer الدلالي C11 عن خدمات التنفيذ والمراقبة والاستعادة | OPEN |
| RI-94-06 | لا توجد mapping مفردة بين كل حدث واختبار EVT/INT/SEC | 79 §3/8 و80 §3–4 | اختبار عام قد لا يغطي event contract بعينه | event-to-test matrix مع test oracle | OPEN |
| RI-94-07 | دورة الموافقة عامة، ولا يوجد سجل approval request type مفصل لكل مركز | 79 §6 و§9 | لا يمكن تدقيق owner/reviewer/hash/expiry لكل موافقة | Approval registry مستقل لكل نوع حساس | OPEN |
| RI-94-08 | اختبارات 80 تسجل مجموعات للمراكز ولا تربط كل عملية باختبار محدد | 80 §2 | تغطية الاختبار لا يمكن إثباتها على مستوى الوظيفة | API-to-test mapping مع rationale عند N/A | OPEN |
| RI-94-09 | معرّفات OWN المؤهلة لا تظهر لكل عملية في 79 | 79 §2 وملحق 91–93 | بعض الملكيات ما زالت OPEN، لكن يجب إظهارها على مستوى العملية | إضافة owner decision field لكل عقد | OPEN |
| RI-94-10 | محور AX/source trace غير موجود في سجل كل عملية/حدث | 79 | صعوبة قياس اكتمال المتطلبات end-to-end | ربط operation/event إلى AX وsource section | OPEN |
| RI-94-11 | C14 يملك عمليات layout/projection، لكنه ليس SoR للمؤشرات | 77 و79 | خطر الخلط بين projection ومصدر المؤشر | تمييز projection API عن owner write APIs في registry | OPEN |
| RI-94-12 | عمليات C04 backup/restore تتقاطع مع SH-15 دون سجل تشغيل تفصيلي موحد | 79 و87 | ازدواجية lifecycle/data ownership محتملة | فصل طلب المجال ونتيجة التحقق عن backup execution/catalog | OPEN |
| RI-94-13 | عمليات C11 artifact/release لا تزال دون artifact registry owner | 77 FLD-16 و85 VER-85-09 | لا يمكن اعتماد provenance/signature/retention | إغلاق dossier 95 وموافقة C11/C08/C10 | OPEN |
| RI-94-14 | SH-13/08/14 إضافات 91–93 ما زالت proposed ولا ترتبط بعد بمصفوفة operation-level | 91–93 | لا تُعامل كعقود نهائية | تحويلها إلى records بعد قرار المالكين | OPEN |
| RI-94-15 | كتالوج الاختبارات لا يتضمن نتائج تنفيذ أو run IDs | 80 §5 | لا يمكن إعلان PASS | إبقاء الحالات SPECIFIED/NOT RUN أو BLOCKED | CONFIRMED GAP |
| RI-94-16 | API schema/version/error/retry details ما زالت TBD في كثير من العمليات | 79 §9 | لا يمكن إنشاء تنفيذ متوافق دون قرارات | استكمال schema contract لكل عملية | OPEN |

## 4. مصفوفة ملكية الأحداث — خط الأساس للمراجعة

| المجال | منتج معنى المجال المقترح | خدمة تنفيذ/نقل/عرض لا تملك المعنى | اختبارات مرجعية أولية | قرار الملكية |
|---|---|---|---|---|
| C01 | C01 | SH-08/09 للتنفيذ والنقل | API-79 + INT-80؛ يلزم C01-specific events | OWNER REVIEW |
| C02 | C02 | SH-08/09 | API-79 / INT-80 | OWN36/72 حسب المورد |
| C03 | C03 | SH-08/09 | API-79 / INT-80 | OWN36/72 حسب المورد |
| C04 | C04 | SH-08/09/15 | API-79 / INT-80-18 / INT-80-17 | OWN72-07 |
| C05 | C05 | SH-08/09 | API-79 / SEC-80-06/07 | OWNER REVIEW |
| C06 | C06 | SH-08/09/11 | API-79 / INT-80-14 / SEC-80-09/10 | OWN72-10 |
| C07 | C07 | SH-08 للتنفيذ؛ SH-14 telemetry | API-79 / INT-80-01 / MON-93 | OWN36-03 وOWN72-08 |
| C08 | C08 | SH-06 storage؛ SH-08 processing jobs | API-79 / INT-80-11/12 / SEC-80-03/04/14 | OWN72-01 |
| C09 | C09 | SH-13 connector؛ SH-08 execution؛ SH-09 transport | INT-91 / TASK-92 / INT-80-05/08/12 | OWN72-02 |
| C10 | C10 | SH-02 decision؛ SH-03 approval؛ SH-04 audit | AUTH-78 / APR-79 / SEC-80 | OWN72-03 |
| C11 | C11 | SH-08 execution؛ SH-10 version metadata؛ SH-14 health؛ SH-15 backup | VER-85 / BAK-87 / MON-93 / INT-80-17 | OWN72-06 |
| C12 | C12 | SH-07 search؛ SH-16 knowledge indexing؛ SH-06 storage | KNOW-90 / INT-80-11 / SEC-80-12 | OWN72-04 |
| C13 | C13 | SH-12 settings store؛ SH-03 approval | CFG-86 / APR-79 / INT-80-06 | OWN72-05 |
| C14 | C14 read projection/layout | SH-07/14 sources; domain centers remain metric owners | INT-80-13 / MON-93 | OWN36-10 وOWN72-09 |

الاختبارات أعلاه مرشحة للربط وليست حكماً بأن كل عملية مغطاة. يلزم تعيين test ID محدد لكل عقد بعد تفكيك القائمة.

## 5. بوابة إغلاق RI-94

- [ ] تفكيك قائمة API إلى سجل operation لكل عملية.
- [ ] فصل جميع event producers حسب Domain SoR.
- [ ] إنشاء سجل approval type مستقل لكل موافقة حساسة.
- [ ] ربط كل سجل بـAX/source/OWN qualified/test IDs.
- [ ] توثيق N/A مع سبب وموافقة المالك، لا ترك خانة فارغة.
- [ ] التحقق من API schemas والنسخ والأخطاء وidempotency.
- [ ] التحقق من authorization action/resource/scope وrecheck وقت التنفيذ.
- [ ] تشغيل فحوص referential integrity على النسخة المراجعة.
- [ ] تنفيذ اختبارات القبول بعد اعتماد المواصفات وبوابة بدء البرمجة.
- [ ] إرفاق أدلة تنفيذ حقيقية قبل ترقية الحالات.

## 6. حدود القرار

لا يغير هذا التدقيق الوثائق التاريخية أو يوافق على ملكية أو API أو event أو test. جميع RI-94-01..16 مفتوحة عدا RI-94-15 التي تؤكد غياب نتائج التنفيذ في السجل الحالي. لا يعدل كود التطبيق، ولا يبدأ البرمجة، ولا يحسم الفصل 157 أو SH-17..SH-20.

**API/EVENT/APPROVAL TRACEABILITY: PARTIAL / REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED.**



## ملحق 97 — تفكيك العمليات إلى سجلات مفردة

أضيفت الوثيقة 97 وفككت 201 عملية من جدول API في 79 إلى Operation IDs مفردة عبر C01–C14. لكل سجل نوع مرشح ومالك API مرشح وعائلة اختبار على مستوى المركز وإحالة OWN أو TBD. لا يزال الربط إلى exact test ID ومحور AX/schema/authorization يحتاج مراجعة؛ لم تتحول السجلات إلى عقود معتمدة. لذلك تظل RI-94 PARTIAL.



## ملحق 98 — سجل أنواع الموافقات

أضيفت الوثيقة 98 كجرد أولي لـ26 نوع موافقة مرشحاً عبر C01–C14. لا يزال تحديد ما إذا كانت الموافقة إلزامية أو مشروطة أو غير مطلوبة قراراً للمالك والسياسة. يظل الربط الدقيق بـOperation ID وAX/source وOWN وtest ID مفتوحاً.


## تحديث تدقيق الحدود المفردة — الوثيقتان 106 و107
أضيفت مصفوفتان مفردتان تغطيان 50 حدثاً و95 عملية في المراكز المتقاطعة مع SH-08/13/14/15. تقللان غموض الفصل بين Domain SoR والخدمات المشتركة، لكن لا تغلقان RI-94-06 أو RI-94-08 أو RI-94-09: Exact Test ID والمالك النهائي واعتماد القرار ما زالت مفتوحة.
