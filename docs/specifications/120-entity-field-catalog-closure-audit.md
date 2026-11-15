# 120 — تدقيق إغلاق كتالوج الكيانات والحقول ومصادر الحقيقة

**التاريخ:** 2026-11-15  
**الحالة:** ENTITY / FIELD CLOSURE AUDIT / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**النطاق:** الوثائق 73 و77 و82 و84–90 و97 و99 و111–116.  
**مرجع النسخة:** أُجري التدقيق على فرع main عند commit `82800177a685b8d540757337746fbce15d419fe7`.

## 1. النتيجة التنفيذية

الكتالوج الحالي مفيد كخريطة أولية للكيانات والحقول وحدود الخدمات، لكنه **ليس قاموس بيانات قابلاً للتحويل مباشرة إلى ERD أو migrations أو APIs**. التدقيق يثبت أن فجوات الإغلاق الأساسية ما زالت قائمة:

- سجل مطابقة الحقول 73 يحوي 26 محور فجوة FLD-01..FLD-26؛ لا يوجد في الوثائق التي تمت مراجعتها دليل على اعتماد جميع هذه المحاور أو إغلاقها.
- الكتالوج 77 يصرّح بأن معظم الأنواع وقيود nullability والعلاقات والاحتفاظ TBD، ويمنع استنتاجها من أسماء الحقول.
- سجل الملكية 111 يراجع 201 عملية API: 139 لها إحالة OWN مؤهلة، و62 بلا إحالة OWN مفردة. هذه أرقام عمليات، وليست نسبة اعتماد للملكية.
- تدقيق 112 يثبت 0/201 ربطاً مفرداً Exact Operation→Test في سجل العمليات.
- تدقيق 113 يثبت أن تتبع الاختبارات عبر 201 عملية و106 أحداث و26 نوع موافقة لم يصل إلى إغلاق مفرد معتمد.
- حزم 84–90 توسع مسائل النطاق والإصدارات والإعدادات والنسخ والخصوصية والمعرفة، لكنها جميعاً تتضمن قرارات OPEN واختبارات SPECIFIED / NOT RUN أو BLOCKED.

**الاستنتاج:** لا توجد حالياً قاعدة أدلة كافية لاعتماد قاموس البيانات أو تثبيت ERD نهائي. ولا يعني ذلك أن العمل السابق غير صالح؛ بل إن مستوى الوصف والحدود سبق مستوى القرار والحقول والاختبار القابل للتحقق.

## 2. مصفوفة إغلاق FLD-01..26

| المحور | موضوع الإغلاق | دليل/حزمة مرتبطة | حالة الإغلاق في هذا التدقيق |
|---|---|---|---|
| FLD-01 | tenant / organization / workspace والعضوية | 84، OWN36-08 | OPEN؛ لا canonical identity أو علاقات معتمدة |
| FLD-02 | project/environment scope | 25، 77 | OPEN؛ قواعد الوراثة والتحقق تحتاج اعتماداً |
| FLD-03 | تصميم/صفحات ومصدر الحقيقة | 73، C02/C03 | OPEN؛ الحد بين تصميم الصفحة وسجل الصفحة يحتاج قراراً |
| FLD-04 | هوية الإصدار والتزامن | 85 | OPEN؛ يجب فصل record_version عن resource/contract/artifact/release versions |
| FLD-05 | schema/migration مقابل domain rows | 73، OWN36-05، OWN72-07 | OPEN؛ C04 يملك المخطط، ومراكز المجال تملك سجلاتها، لكن قواعد المراجعة والترحيل والرجوع تحتاج اعتماداً |
| FLD-06 | AI domain task مقابل SH-08 execution | 92 | OPEN؛ لا ازدواج لحالة المهمة أو محاولات التنفيذ |
| FLD-07 | AI usage مقابل SH-11 cost ledger | 83، OWN36-07 | OPEN؛ تعريف القياس والعملة والحدود والميزانية غير معتمد |
| FLD-08 | QA test run مقابل jobs/operational health | 93، OWN36-03، OWN72-08 | OPEN؛ فصل نتيجة الاختبار عن حالة المهمة والصحة التشغيلية |
| FLD-09 | asset registry مقابل binary/storage | 91، 95، OWN72-01 | OPEN؛ سجل الأصل ليس كائن التخزين |
| FLD-10 | asset grants مقابل SH-02 authorization | 84، 78 | OPEN؛ مصدر grants مركزي مقترح لكن تعريف الأدوار والعضوية غير محسوم |
| FLD-11 | integration business run مقابل SH-08 jobs | 92 | OPEN؛ يلزم run_ref/job_ref ومنع ازدواج الحالة |
| FLD-12 | connector metadata/secrets | 91، OWN72-02 | OPEN؛ C09 يملك الغرض والـmapping، وSH-13 lifecycle؛ السر في Secret Manager |
| FLD-13 | policy authoring مقابل access decision runtime | 84، OWN72-03 | OPEN؛ C10 يملك السياسة وSH-02 التقييم المقترح |
| FLD-14 | audit domain references مقابل SH-04 ledger | 23، OWN72-03 | OPEN؛ SH-04 مصدر سجل التدقيق، مع تحديد مرجع المجال فقط |
| FLD-15 | release lifecycle مقابل SH-08 execution | 92، OWN72-06 | OPEN؛ فصل حالة الإصدار عن محاولات التنفيذ |
| FLD-16 | artifact registry/storage/signing | 95، OWN36-02/06، OWN72-01/06 | OPEN؛ لا مصدر حقيقة نهائي للـbinary أو التوقيع والاحتفاظ |
| FLD-17 | canonical documents مقابل knowledge ingestion | 90، OWN72-04 | OPEN؛ الأصل لدى C12/مالك المجال والفهرسة لا تملك المحتوى |
| FLD-18 | general search مقابل semantic index | 90، OWN72-04 | OPEN؛ حد SH-07/SH-16 وACL/freshness/deletion لم يعتمد |
| FLD-19 | settings UX مقابل values/schema | 86، OWN72-05 | OPEN؛ مالك المفتاح وprecedence ومصدر القيمة الفعالة غير محسوم |
| FLD-20 | dashboard projections | 72، OWN72-09 | OPEN؛ freshness/source/version/scope/ACL تحتاج موافقة |
| FLD-21 | actor/time/provenance envelope | 73، 77 | OPEN؛ لا تعريف موحد معتمد للدلالة والتوقيت والمصدر |
| FLD-22 | retention/delete/legal hold | 88 | OPEN؛ taxonomy والمدة والمحفزات والحذف والنسخ غير معتمدة |
| FLD-23 | status/enums/state machines | 73، 85–90 | OPEN؛ لا enum registry أو سياسة unknown-value معتمدة |
| FLD-24 | cross-project/global references | 84، 88 | OPEN؛ مشاركة صريحة واتساق tenant غير معتمدين |
| FLD-25 | owner/steward/creator separation | 73، 84 | OPEN؛ تعيين ملاك المجال والمشرفين غير مكتمل |
| FLD-26 | classification/purpose/legal basis | 88 | OPEN؛ لا تصنيف أو غرض أو أساس معالجة أو سياسة احتفاظ معتمدة |

**ملاحظة:** حالة OPEN هنا تعني أن هذا التدقيق لم يجد قرار إغلاق موثقاً ومعتمداً في السجل الحالي؛ لا تعني أن كل فكرة أو حد مقترح مفقود من الوثائق.

## 3. تدقيق اكتمال الحقول

لكل كيان، لا يكفي وجود اسم ومالك مقترح. الحد الأدنى المطلوب قبل اعتماد القاموس:

1. الاسم القانوني/المعياري للكيان، تعريفه، وحدوده، وعلاقته بالكيانات الأخرى.
2. Domain Owner وSchema Steward وAPI Owner منفصلون عند اختلاف المسؤوليات.
3. Domain SoR واحد لكل حقيقة؛ وتحديد read models وcaches وindexes بوصفها مشتقات غير مالكة.
4. اسم كل حقل، تعريفه، نوعه وصيغته ودقته، nullable/required، default، constraints، uniqueness، indexes، وعلاقات FK.
5. tenant/workspace/project/environment scope وسلوك المراجع العابرة للنطاق.
6. classification وpurpose وlegal basis عند انطباقه وretention trigger/deletion/legal hold.
7. read/write actions والفاعل والمورد والنطاق وقرار SH-02/SH-03 وتسجيل SH-04.
8. lifecycle/state machine وإصدارات السجل والعقد والمورد وسلوك concurrency/idempotency.
9. API/Event/Approval references مع producer/consumer وschema compatibility.
10. اختبار قبول مفرد وبيانات الاختبار ونتيجة تشغيل ودليل قابل للتتبع.

لا تُملأ أي خانة غير مثبتة بالتخمين. القيمة الصحيحة مؤقتاً هي TBD مع مالك وخطوة إغلاق.

## 4. ملاحظات اتساق يجب عدم تسويتها آلياً

- `tenant_id` و`organization_id` ليسا مترادفين تلقائياً؛ يجب الحفاظ على هذا الفصل حتى قرار مالك النطاق.
- `workspace_id` مرجع نطاق، وليس grant أو إثبات عضوية.
- `created_by_actor` ليس Domain Owner أو Record Owner أو Steward.
- `record_version` ليس `version_id` ولا `contractVersion` ولا `artifactVersion` ولا `releaseVersion`.
- `task_ref` أو `run_ref` في المجال لا يحل محل سجل التنفيذ العام في SH-08.
- `asset_ref` لا يحل محل `storage_object_ref`، ولا يجعل SH-06 مالك سجل الأصل.
- `secret_ref` مرجع آمن فقط؛ لا تُنسخ قيمة السر إلى قاعدة البيانات أو السجلات أو prompt.
- مستند C12 الأصلي ليس هو index أو embedding أو snippet في SH-07/SH-16.
- نتيجة C07 ليست telemetry تشغيلية من SH-14.
- اقتراحات الحزم 84–90 لا ترفع الحالة إلى APPROVED؛ تظل قراراتها واختباراتها مفتوحة حتى وجود اعتماد ودليل.

## 5. فجوات التتبع والاختبار

| محور التتبع | الحجم الحالي | ما ثبت | ما لم يثبت |
|---|---:|---|---|
| عمليات API | 201 | جرد العمليات ومراجعة إحالات الملكية | 62 بلا إحالة OWN مفردة؛ 0/201 exact operation-to-test |
| Domain Events | 106 | جرد الأحداث ومراجعة حالة المصدر | 0/106 exact event-to-test mapping؛ أسماء الأحداث 0/106 تطابق حرفي |
| Approval Types | 26 | جرد الأنواع وربط مقترح مع SH-03 | لا اعتماد/اختبار مفرد نهائي لكل نوع |
| Field gaps | 26 | محاور FLD محددة | لا إغلاق مالك/حقل/اختبار موثق لجميع المحاور |
| Acceptance tests | عدة عائلات مصدرية | 89 معرف اختبار فريد جرى التحقق من منشئها في 105 | لا يثبت ذلك تغطية كل API/Event/Approval أو تنفيذها |

يجب ألا تستخدم عائلات الاختبار العامة أو أسماء سيناريوهات مشتقة كأنها معرفات أصلية دقيقة. وأي اختبار مستقبلي يجب أن يسجل: requirement ID، entity/field، API/Event/Approval ID، qualified OWN ID، خطوات التنفيذ، البيئة، actor، commit/build/run ID، النتيجة، artifact، والمراجع.

## 6. ترتيب الإغلاق المقترح

1. **النطاق والهوية:** إغلاق FLD-01/02/24/25 مع قرارات 84، ثم تحديث سياق الطلب ومصفوفة التفويض دون افتراضات.
2. **الملكية المزدوجة بين المراكز والخدمات:** إغلاق FLD-03/05/06/08/09/10/11/12/13/14/15/17/18/19/20 وفق حزم 85–95 وحدود 36/72.
3. **الإصدار والتشغيل:** اعتماد قواعد concurrency وstate machines وartifact registry وSH-08/14/15.
4. **الخصوصية:** إغلاق FLD-21/22/26 مع مراجعة قانونية مناسبة وسياسات النسخ والفهرسة والحذف.
5. **التتبع:** استكمال OWN المؤهل للـ62 عملية، ثم ربط كل API/Event/Approval باختبار قبول مفرد أو استثناء مبرر.
6. **المصادقة:** مراجعة المالكين لكل مركز C01–C14، ثم اعتماد القاموس وERD والعقود.
7. **التحقق:** تنفيذ الاختبارات في بيئة معروفة وإرفاق الأدلة؛ لا تتحول SPECIFIED / NOT RUN إلى PASS دون run evidence.

## 7. بوابة الإغلاق

لا يُعلن اكتمال كتالوج البيانات إلا إذا تحققت جميع الشروط التالية:

- [ ] اعتماد Domain Owner وSchema Steward وSoR لكل كيان.
- [ ] حسم FLD-01..26 أو اعتماد استثناء موثق لكل محور.
- [ ] اكتمال قاموس الحقول بالأنواع والقيود والنطاق والتصنيف والاحتفاظ أو TBD مبرر ومؤقت.
- [ ] اعتماد العلاقات وERD ومصادر الحقيقة ومنع الازدواج القابل للكتابة.
- [ ] إغلاق تعارضات الملكية بين C01–C14 وSH-01..SH-16.
- [ ] ربط العمليات والأحداث والموافقات بالمتطلبات والاختبارات والقرارات المؤهلة.
- [ ] تنفيذ اختبارات tenant isolation وauthorization وretention/deletion وconcurrency وrestore وindex ACL.
- [ ] تسجيل موافقات المالكين وأدلة التشغيل ونسخة المصدر/commit.
- [ ] موافقة مستقلة صريحة على بدء البرمجة بعد اكتمال اعتماد المواصفات.

**لا ينشئ هذا التدقيق schema أو ERD أو migrations أو API أو كوداً، ولا يغير حالة أي قرار إلى APPROVED.**
