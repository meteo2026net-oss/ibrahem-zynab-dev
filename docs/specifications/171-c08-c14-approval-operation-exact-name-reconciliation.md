# 171 — تسوية أسماء عمليات الموافقات ومعرفات العمليات C08–C14

**التاريخ:** 2026-11-16  
**الحالة:** SOURCE-REGISTER RECONCILIATION / EXACT NAME MATCH / SEMANTIC APPROVAL LINK NOT APPROVED / OWNER REVIEW REQUIRED / NOT IMPLEMENTED  
**المراجع:** 97 (سجل العمليات)، 98 (سجل الموافقات)، 100 (سجل الأحداث)، 148، 157، 164، 170.  
**الغرض:** حسم سؤال محدد من workpaper 170: هل أسماء العمليات المذكورة في سجل الموافقات 98 لها Operation IDs مطابقة حرفياً في سجل العمليات 97؟ النتيجة أدناه مبنية على صفوف السجلين، لا على التخمين.

## 1. نتيجة التسوية

توجد مطابقة اسمية حرفية بين اسم العملية المذكور في كل نوع موافقة من C08–C14 وبين اسم عملية واحد في سجل 97، بمجموع **14/14**. هذه نتيجة reconciliation بين سجلين مشتقين، ولا تعني أن مالك المجال اعتمد أن العملية هي منفذ قرار الموافقة أو أن workflow SH-03 متكامل أو أن الفصل بين مقدم الطلب والمعتمد والمنفذ مختبر.

| Approval ID | Center | اسم العملية في سجل 98 | Operation ID المطابق في سجل 97 | نوع المطابقة | الحالة |
|---|---|---|---|---|---|
| APR-C08-01 | C08 | requestDeletion | OP-C08-131 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C08-02 | C08 | requestExport | OP-C08-130 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C09-01 | C09 | startSync | OP-C09-138 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C09-02 | C09 | beginAuthorization | OP-C09-134 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C10-01 | C10 | decideSecurityException | OP-C10-149 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C10-02 | C10 | requestPolicyChange | OP-C10-148 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C11-01 | C11 | approveRelease | OP-C11-161 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C11-02 | C11 | deployRelease | OP-C11-162 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C11-03 | C11 | requestRollback | OP-C11-165 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C12-01 | C12 | publishKnowledgeDocument | OP-C12-173 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C12-02 | C12 | exportKnowledgeBundle | OP-C12-176 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C13-01 | C13 | applyOwnerSettingChange | OP-C13-184 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C13-02 | C13 | rollbackSettingVersion | OP-C13-186 | EXACT NAME MATCH | OWNER REVIEW |
| APR-C14-01 | C14 | exportDashboardReport | OP-C14-200 | EXACT NAME MATCH | OWNER REVIEW |

## 2. ما الذي تم إثباته وما الذي لم يتم إثباته؟

### مثبت من السجلين
- كل اسم عملية وارد في عمود العملية لسجلات الموافقات الـ14 في 98 له اسم مطابق حرفياً في صف عملية محدد في 97.
- التوزيع: C08=2، C09=2، C10=2، C11=3، C12=2، C13=2، C14=1.
- بذلك تُغلق فجوة **اسم العملية غير الموجود في جرد 97** التي وردت بصياغة احترازية في 170، ويُستبدل بها crosswalk اسمي دقيق.

### غير مثبت ولا يُغلق بهذه المطابقة
- أن العملية المذكورة هي API تنفيذ قرار SH-03؛ بعض الأسماء مثل requestDeletion أو requestExport أو requestPolicyChange أو deployRelease قد تكون طلباً أو فعلاً محمياً، لا قرار اعتماد بحد ذاته.
- أن لكل نوع موافقة Decision Owner وApprover Role مفصولين عن مقدم الطلب والمنفذ.
- أن القرار مرتبط بمورد/نسخة/hash/policy version ونطاق tenant/project صحيح.
- أن الموافقة إلزامية في كل الحالات أو أن السياسة تحدد متى تلزم.
- وجود اختبار قبول exact أو expected result أو تنفيذ ناجح؛ سجل 97 لا يزال يضع EXACT TEST ID TBD / OWNER REVIEW لكل العمليات الـ81 في C08–C14.
- اعتماد أي من OWN72 أو Domain SoR أو API Owner.

## 3. ملاحظات بحسب المركز

| المركز | نتيجة الربط الاسمي | قيد المراجعة |
|---|---|---|
| C08 | requestDeletion→OP-C08-131؛ requestExport→OP-C08-130 | الطلب لا يساوي قرار الحذف/التصدير؛ يلزم التحقق من retention/legal hold وACL وقرار مالك البيانات |
| C09 | startSync→OP-C09-138؛ beginAuthorization→OP-C09-134 | يلزم تحديد external write scope وOAuth scope وقرار مالك الحساب الخارجي؛ لا تُخزن قيمة السر في سجلات التكامل |
| C10 | decideSecurityException→OP-C10-149؛ requestPolicyChange→OP-C10-148 | يلزم فصل request عن decision، وتحديد approver مستقل ومدة/نطاق الاستثناء؛ تعارض Zero Trust ما زال مفتوحاً |
| C11 | approveRelease→OP-C11-161؛ deployRelease→OP-C11-162؛ requestRollback→OP-C11-165 | يلزم ربط الموافقة ببصمة artifact والبيئة، ومنع اعتبار deploy أو rollback request قراراً بذاته |
| C12 | publishKnowledgeDocument→OP-C12-173؛ exportKnowledgeBundle→OP-C12-176 | يلزم إثبات مصدر المحتوى ونسخته وACL وتصنيف الحزمة قبل النشر/التصدير |
| C13 | applyOwnerSettingChange→OP-C13-184؛ rollbackSettingVersion→OP-C13-186 | يلزم توثيق مالك المفتاح ونطاق الإعداد والوراثة، وهل rollback يحتاج موافقة وفق السياسة |
| C14 | exportDashboardReport→OP-C14-200 | يلزم توثيق أصحاب مصادر البيانات وACL وscope التقرير؛ الإسقاطات تبقى read-only |

## 4. إعادة بيان فجوة APR-C07-02

هذه التسوية لا تشمل C07. وتبقى النتيجة السابقة كما هي: سجل الموافقات 98 يتضمن APR-C07-02 (approveQualityWaiver)، لكن لا يوجد في سجل العمليات 97 اسم عملية قرار مطابق. لا تنشأ عملية أو معرف جديد ضمن هذا العمل.

## 5. أثر التسوية على workpaper 170

يجب قراءة العبارات الاحترازية في صفوف FLD-11 وFLD-12 وFLD-13 وFLD-15 وFLD-17 وFLD-19 وFLD-20 في الوثيقة 170 على ضوء هذا العمل:
- حيث ذُكر أن OP ID يحتاج reconciliation، أصبحت المطابقة الاسمية متاحة في 171.
- لا تتغير حالة أي علاقة إلى APPROVED؛ 171 يثبت EXACT NAME MATCH فقط.
- لا تتغير خطوط أساس الاختبار والملكية والموافقات في 97–100 و148 و157 و164.
- لا ينبغي اعتبار أسماء العمليات المرشحة في 170 عقود تنفيذ موافقة.

## 6. قياس التقدم

| القياس | النتيجة |
|---|---:|
| أنواع الموافقات C08–C14 | 14 |
| أسماء عمليات الموافقات ذات المطابقة الحرفية في سجل 97 | 14/14 |
| روابط الموافقة إلى عملية تنفيذ قرار معتمدة من المالك | 0/14 |
| روابط Operation→Test exact approved | 0/81 |
| روابط Event→Test exact approved | 0/55 |
| موافقات مالك نهائية | 0/14 |
| اختبارات قبول منفذة وفق خط الأساس 164 | 0/124 |
| تغييرات كود/API/schema/database | 0 |

## 7. شروط الإغلاق المتبقية

1. يراجع مالك كل مجال مطابقة الاسم ودور العملية في lifecycle الموافقة.
2. يحدد SH-03 عقد الطلب والقرار والتنفيذ والتحقق، دون أن يصبح Decision Owner.
3. يحدد C10 سياسة الفصل بين الواجبات وإعادة التحقق وقت التنفيذ.
4. يربط كل Approval ID بمورد ونطاق ونسخة وسياسة ودليل قرار.
5. يحدد لكل عملية اختبار قبول مستقل ونتيجة متوقعة، ثم تنفذ الاختبارات في بيئة معروفة.
6. تبقى سجلات 97–100 دون تعديل حالة الاعتماد حتى توفر قرار المالك والدليل.

**الحكم:** أُغلقت المطابقة الاسمية بين 14/14 عملية موصوفة في الموافقات ومعرفات سجل العمليات. لم تُغلق حوكمة الموافقة أو الملكية أو الاختبارات أو الجاهزية، ولم يُعدل التطبيق أو API أو قاعدة البيانات. لا تمنح هذه الوثيقة إذناً بالبرمجة.
