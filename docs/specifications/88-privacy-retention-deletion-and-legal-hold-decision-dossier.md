# 88 — حزمة الخصوصية والتصنيف والاحتفاظ والحذف والحجز القانوني

**الحالة:** PRIVACY GOVERNANCE DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** 23 و36 و56 و77 و78 و79 و80 و84 و85 و86 و87؛ والمصادر التاريخية 158–160 حسب سجل المصدر.

## 1. الغرض والحدود

تحتاج المنصة إلى قواعد موحدة لإدارة تصنيف البيانات والغرض والاحتفاظ والحذف والحجز القانوني، لكن ملكية البيانات تبقى لدى مالك المجال، بينما C10 يملك ضوابط الأمن والحوكمة، وSH-04 سجل التدقيق، وSH-06 تخزين الأصول، وSH-07/SH-16 الفهرسة، وSH-15 النسخ والاستعادة.

هذه الوثيقة لا تحدد أساساً قانونياً أو مدة احتفاظ أو بلد إقامة أو سياسة نقل دولي من دون قرار ومراجعة قانونية مختصة. ولا تعني أن كل البيانات شخصية أو أن لكل فئة مدة واحدة.

## 2. المسؤوليات المقترحة

| القدرة/البيانات | المالك المقترح | حدود المسؤولية |
|---|---|---|
| Purpose and domain classification | مالك المجال | يحدد الغرض والسياق وتصنيف المورد |
| Privacy/security controls | C10 | يحدد الضوابط والمراجعات والاستثناءات وفق سياسة معتمدة |
| Identity subject references | SH-01 | هوية principal لا قرار الاحتفاظ بالمورد |
| Audit records | SH-04 | سلامة سجل التدقيق؛ لا يقرر حذف سجلاته منفرداً |
| Binary asset and lifecycle | C08 + SH-06 | C08 يملك سجل الأصل؛ SH-06 التخزين والتنفيذ |
| Knowledge source content | C12/domain owner | SH-07/16 الفهرسة لا تملك المصدر |
| Backups and restore copies | SH-15 | ينسق النسخ؛ يلزم تحديد أثر الحذف والـlegal hold على النسخ |
| Policy decisions/approvals | C10 + SH-03 + data owner | يثبت القرار ونطاقه وإصداره |
| Deletion execution | مالك المورد + الخدمة المنفذة | لا يكفي tombstone في فهرس لإثبات حذف الأصل |

## 3. سجل القرارات المطلوبة

| ID | القرار | الحالة |
|---|---|---|
| PRIV-88-01 | اعتماد taxonomy للتصنيف والحساسية والبيانات الشخصية والأسرار | OPEN |
| PRIV-88-02 | تعيين data owner لكل فئة وكيان ومصدر | OPEN |
| PRIV-88-03 | تحديد purpose/processing context لكل استخدام | OPEN |
| PRIV-88-04 | تحديد legal basis أو أساس المعالجة من جهة مختصة حيث يلزم | OPEN |
| PRIV-88-05 | اعتماد retention schedule حسب الفئة والولاية والسياق | OPEN |
| PRIV-88-06 | تحديد بدء احتساب مدة الاحتفاظ وأحداث reset/extension | OPEN |
| PRIV-88-07 | اعتماد subject access/export/correction workflows عند انطباقها | OPEN |
| PRIV-88-08 | اعتماد deletion semantics: logical, physical, crypto erase, tombstone | OPEN |
| PRIV-88-09 | اعتماد propagation إلى replicas/search/vector/cache/events/exports | OPEN |
| PRIV-88-10 | اعتماد backup deletion lag والتعامل مع restore بعد الحذف | OPEN |
| PRIV-88-11 | اعتماد legal hold authority, scope, reason, review and release | OPEN |
| PRIV-88-12 | اعتماد audit retention وعدم العبث بالأدلة | OPEN |
| PRIV-88-13 | اعتماد data residency/transfer/processor controls | OPEN |
| PRIV-88-14 | اعتماد redaction/anonymization/pseudonymization rules | OPEN |
| PRIV-88-15 | اعتماد breach/incident escalation and notification ownership | OPEN |
| PRIV-88-16 | اعتماد policy versioning وexception expiry/review | OPEN |

## 4. دورة الحذف المقترحة

1. تحديد المورد والمالك والنطاق والغرض والفئة.
2. التحقق من هوية مقدم الطلب وسلطته ومصدر الطلب.
3. فحص legal hold وretention schedule والالتزامات المتعارضة.
4. تحديد جميع النسخ والمشتقات والفهارس والـcaches والنسخ الاحتياطية والمشاركات.
5. عرض أثر الحذف والمستثنيات والنسخ التي لا يمكن حذفها فوراً.
6. طلب موافقة مستقلة إذا كانت السياسة تتطلبها.
7. تنفيذ الإجراء عبر مالك كل مورد/خدمة.
8. إبطال الوصول والروابط والرموز والفهارس وفق SLA معتمد.
9. توثيق ما حُذف وما عُلّق وما استُثني وسبب الاستثناء.
10. إصدار evidence report، مع عدم كشف بيانات أكثر مما يلزم.

هذه دورة مرشحة لا تمنح صلاحية حذف تلقائية.

## 5. قواعد الحجز القانوني

- يجب أن يكون الحجز محدوداً بمورد/فئة/نطاق وسبب ومالك وسلطة اعتماد وتاريخ مراجعة.
- لا يُستنتج legal hold من وجود incident أو طلب حذف.
- لا يزيل الحجز إلا actor مخول وبسجل قرار.
- يجب أن يحدد أثره على النسخ الاحتياطية والنسخ المكررة والفهارس والتصدير.
- لا يجوز لـAI Agent إنشاء أو إزالة حجز قانوني أو تفسير أساسه القانوني.
- القيم القانونية والولايات والأسس تبقى من اختصاص الجهة المختصة، ولا تُخمن من أسماء الحقول.

## 6. اختبارات القبول

| Test ID | الحالة | النتيجة | الوضع |
|---|---|---|---|
| PRIV-88-T01 | حذف مورد تحت legal hold | منع الحذف وفق policy | SPECIFIED / NOT RUN |
| PRIV-88-T02 | طلب بلا owner/purpose | حجب التنفيذ وطلب استكمال موثق | SPECIFIED / NOT RUN |
| PRIV-88-T03 | حذف أصل مع بقاء فهرس | منع ظهور المشتقات بعد SLA المعتمد | BLOCKED: SLA TBD |
| PRIV-88-T04 | restore لنسخة تحتوي بيانات محذوفة | إعادة تطبيق deletion tombstones/hold policy قبل الإتاحة | SPECIFIED / NOT RUN |
| PRIV-88-T05 | انتهاء retention period | لا حذف آلي قبل تحقق الاستثناءات والحجز | SPECIFIED / NOT RUN |
| PRIV-88-T06 | release legal hold | يتطلب authority وaudit event | SPECIFIED / NOT RUN |
| PRIV-88-T07 | طلب وصول cross-tenant | deny دون كشف بيانات | SPECIFIED / NOT RUN |
| PRIV-88-T08 | حذف سر/credential | إبطال reference والاعتماديات وفق key owner | SPECIFIED / NOT RUN |
| PRIV-88-T09 | audit evidence | integrity محفوظ ولا يتغير بسجل الحذف العادي | SPECIFIED / NOT RUN |
| PRIV-88-T10 | طلب AI Agent حذف/إزالة hold | لا تنفيذ دون تفويض بشري مستقل | SPECIFIED / NOT RUN |
| PRIV-88-T11 | تصدير بيانات دون purpose | منع أو تقليل البيانات وفق السياسة | SPECIFIED / NOT RUN |
| PRIV-88-T12 | حذف غير مكتمل في نسخة احتياطية | الإفصاح عن lag وعدم ادعاء physical purge | SPECIFIED / NOT RUN |

## 7. حقول الكتالوج المطلوبة

لكل كيان/حقل يلزم توثيق: owner، source of truth، classification، purpose، legal basis (إن انطبق)، retention class، retention trigger، deletion behavior، legal hold capability، replication/search/cache propagation، residency/transfer constraints، audit evidence. كل قيمة غير مستعادة تبقى TBD حتى اعتمادها.

**PRIVACY / RETENTION / DELETION / LEGAL HOLD / OPEN / NOT APPROVED / NO IMPLEMENTATION.**
