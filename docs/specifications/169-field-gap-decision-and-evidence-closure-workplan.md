# 169 — سجل قرارات وأدلة إغلاق فجوات الحقول FLD-01..26

**التاريخ:** 2026-11-16  
**الحالة:** DERIVED CLOSURE WORKPLAN / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المراجع الأساسية:** 73، 75، 77، 120، 165، 168، مع سجلات العمليات 97، الأحداث 100، الموافقات 98، ومصفوفة التتبع 99.  
**الغرض:** تحويل مصفوفة 168 من وصف موحد للفجوات إلى حزمة عمل قابلة للتوقيع والتحقق، بتحديد القرار المطلوب والأدلة الدنيا وأدوار المراجعة لكل فجوة. لا ينشئ هذا السجل قرارات ملكية أو أسماء ملاك أشخاص أو مخططات تنفيذية.

## 1. قواعد الإغلاق

1. لا يُعدّ اقتراح SoR قراراً معتمداً قبل تسجيل موافقة مالك المجال والجهات المشتركة المتأثرة، مع النطاق والتاريخ والإصدار.
2. Domain Owner وSchema Steward وAPI Owner أدوار منفصلة؛ يجوز أن يشغلها الشخص نفسه فقط بقرار موثق، ولا يُفترض ذلك.
3. لكل حقيقة مجال Domain SoR واحد. قواعد البيانات المشتقة والفهارس وذاكرات التخزين المؤقت ولوحات القراءة لا تصبح مصادر حقيقة لمجرد تخزين نسخة منها.
4. أي حقل غير مثبت النوع أو الصيغة أو القيود يبقى TBD؛ لا يُستنتج من الاسم.
5. المرشح الدلالي لعملية أو حدث أو اختبار ليس رابطاً معتمداً. لا يتغير خط الأساس 0 exact links دون قرار ومرجع اختبار محدد.
6. لا تُسجل حالة PASS دون test run/build/environment/evidence قابل للتتبع.
7. لا تنفذ هذه الوثيقة أي تعديل على التطبيق أو API أو قاعدة البيانات.

## 2. مصفوفة قرارات الإغلاق والأدلة المطلوبة

| الفجوة | القرار المحدد المطلوب | أدوار المراجعة المطلوبة (دون تعيين أشخاص) | الحد الأدنى للأدلة قبل الإغلاق |
|---|---|---|---|
| FLD-01 | تعريف العلاقة بين organization وtenant وworkspace، وتحديد مصدر العضوية والنطاق | مالك المؤسسة/المستأجر، SH-01، SH-02، C10، ملاك المجالات | قاموس مصطلحات؛ ERD نطاق؛ حالات إنشاء/نقل/تعطيل العضوية؛ اختبارات عزل |
| FLD-02 | اعتماد نموذج المتطلبات وإصداراتها وعلاقاتها وأدلة المصدر | C01، C08/SH-06، C07 | قاموس project_requirements؛ lifecycle/version rules؛ روابط artifacts والاختبارات |
| FLD-03 | فصل هوية الصفحة ومسارها عن لوحة التصميم ومحتوى الصفحة | C02، C03 | تعريف page identity؛ مرجع design_ref؛ قواعد التغيير والحذف؛ أمثلة صفحات متعددة |
| FLD-04 | عقد توافق تعريفات المكونات بين التصميم والكود وإصداراتها | C02، C05، C08 | component contract؛ version mapping؛ hash/provenance؛ حالات عدم التوافق |
| FLD-05 | تحديد مالك المخطط والترحيل مقابل مالك سجلات المجال | C04، كل Domain Owner متأثر | RACI لكل كيان؛ migration approval/rollback؛ schema diff؛ اختبار ترحيل واستعادة |
| FLD-06 | فصل مهمة AI الدلالية عن job ومحاولات التنفيذ العامة | C06، SH-08، C10 | task/job state model؛ task_ref/job_ref؛ قواعد الإلغاء وإعادة المحاولة؛ اختبار عدم ازدواج الحالة |
| FLD-07 | فصل قياس استخدام النموذج عن دفتر التكلفة والتسوية والميزانية | C06، SH-11، مالك الميزانية | تعريف units/source/price/FX؛ ledger reconciliation؛ مالك ميزانية؛ سياسة الحدود والتنبيهات |
| FLD-08 | فصل نتيجة الاختبار ودليله عن job التنفيذ وhealth telemetry | C07، SH-08، SH-14، C08/SH-06 | test-run/result schema؛ job_ref؛ evidence refs؛ حالات تشغيل وفشل وإعادة اختبار |
| FLD-09 | اعتماد سجل الأصل الدلالي مقابل binary/storage object | C08، SH-06، C10 | asset registry authority؛ storage receipt؛ version/checksum ownership؛ lifecycle وrestore tests |
| FLD-10 | حسم ما إذا كانت منح الوصول مركزية حصراً في SH-02 | C08، SH-02، C10 | grant decision contract؛ resource/scope model؛ إلغاء/منع النسخ القابلة للكتابة؛ اختبارات revoke |
| FLD-11 | فصل integration business run عن SH-08 execution attempts | C09، SH-08، SH-13 | run/job relationship؛ correlation؛ retry semantics؛ حالات نجاح جزئي ومصالحة |
| FLD-12 | فصل metadata والmapping عن دورة حياة الموصل وعن قيمة السر | C09، SH-13، Secret Manager، C10 | connector lifecycle contract؛ secret_ref only؛ OAuth/webhook scope؛ rotation/revocation tests |
| FLD-13 | فصل تأليف السياسات والحوكمة عن تقييم التفويض وقت التشغيل | C10، SH-02 | policy lifecycle/version؛ evaluation contract؛ decision reference؛ اختبارات deny-by-default |
| FLD-14 | اعتماد SH-04 مصدراً لسجل التدقيق وتحديد مراجع المجال | C10، SH-04، SH-01 | audit event ownership؛ actor/time semantics؛ immutability/access rules؛ اختبارات completeness |
| FLD-15 | فصل release/deployment lifecycle عن حالة job التنفيذية | C11، SH-08، SH-10، SH-14 | release state machine؛ job refs؛ promotion/rollback rules؛ evidence/attestation |
| FLD-16 | حسم سلطة سجل artifact والـbinary والتوقيع والاحتفاظ | C05، C11، C10، SH-06/SH-15 | registry owner؛ storage owner؛ SBOM/attestation format؛ signature verification؛ retention/replication tests |
| FLD-17 | اعتماد مالك المحتوى القانوني/المعرفي مقابل ingestion/index | C12، مالك المجال المصدر، SH-16، SH-06 | canonical content authority؛ version/provenance؛ ingestion metadata boundary؛ deletion propagation |
| FLD-18 | فصل محتوى المصدر وACL عن فهرس البحث والاستعلام | C12، SH-07، SH-16، C10 | ACL source؛ query-time authorization؛ reindex/delete contract؛ stale-index tests |
| FLD-19 | تحديد مالك معنى مفتاح الإعداد مقابل تخزين القيم وأولوية الوراثة | C13، SH-12، SH-03، مالك المفتاح | key schema owner؛ precedence table؛ effective-value trace؛ approval/write API؛ version conflict tests |
| FLD-20 | اعتماد إسقاطات C14 ومصدر كل مؤشر وحداثته وترشيح صلاحياته | C14، ملاك المؤشرات، SH-07، SH-14، SH-03 | metric source map؛ source_version/as_of/freshness؛ scope/ACL filter؛ stale/unauthorized projection tests |
| FLD-21 | اعتماد دلالة actor وoccurred_at وrecorded_at وprovenance | SH-01، SH-04، API Owners، C10 | field definitions؛ trusted clock/timezone rules؛ actor/service identity؛ provenance envelope tests |
| FLD-22 | اعتماد تصنيف الاحتفاظ والحذف والحجز القانوني لكل فئة | Domain Owners، C10، SH-06/07/15/16 | purpose/legal basis؛ retention trigger؛ deletion propagation؛ legal hold؛ backup expiry tests |
| FLD-23 | اعتماد state machine وenum versioning لكل نوع كيان | Domain Owners، C04، API Owners، SH-10 | per-entity states/transitions؛ compatibility policy؛ unknown-value behavior؛ concurrency tests |
| FLD-24 | حسم المراجع العابرة للمشروع/المؤسسة والمشاركة الصريحة | مالك السجل المصدر، SH-01، SH-02، C10 | scope inheritance؛ explicit grant؛ cross-tenant negative tests؛ revocation and audit evidence |
| FLD-25 | فصل Domain Owner وrecord owner وsteward وcreator/actor | Domain Owners، SH-01، SH-04، C10 | glossary؛ RACI؛ actor attribution؛ stewardship assignment؛ audit examples |
| FLD-26 | اعتماد classification وpurpose وlegal_basis وretention taxonomy | Domain Owners، C10، مستشار الخصوصية/القانون عند اللزوم | data inventory؛ taxonomy؛ purpose/legal basis mapping؛ retention schedule؛ enforcement tests |

## 3. ترتيب التنفيذ الوثائقي والاعتماد

| المرحلة | النطاق | سبب الترتيب | شرط الانتقال |
|---|---|---|---|
| A — هوية ونطاق | FLD-01، 02، 24، 25 | هذه القرارات تحدد حدود كل سجل ومن يحق له الوصول إليه | اعتماد قاموس المؤسسة/المستأجر/مساحة العمل والفاعل والملكية |
| B — ملكية السجلات والخدمات | FLD-03 إلى 20، مع FLD-05/09/10/13/14/16 أولوية | تمنع ازدواج مصادر الحقيقة بين المراكز والخدمات | RACI وSoR وعقود read/write لكل مجال |
| C — الخصوصية ودورة الحياة | FLD-21، 22، 26 | تؤثر على كل البيانات والتخزين والفهرسة والنسخ | اعتماد التصنيف والغرض والاحتفاظ والحذف والحجز |
| D — التوافق والتتبع | FLD-04، 06–08، 11، 15، 23 | تثبت الاتساق بين الإصدارات والمهام والنتائج | state/version contracts وروابط API/Event/Approval/Test الدقيقة |
| E — إصدار قاموس البيانات | FLD-01..26 | لا يصدر ERD نهائي قبل حسم المالكين والقيود | توقيعات المالكين ومراجعة الأمن والخصوصية واختبارات الأدلة |

هذا ترتيب عمل مقترح لا يغير أولويات مصدرية معتمدة ولا ينشئ موافقات.

## 4. نموذج سجل قرار يجب تعبئته لكل FLD

يجب أن يسجل كل قرار لاحقاً في سجل قابل للتدقيق بالحقول التالية:

- decision_id مؤهل وفريد، مع عدم إعادة استخدام معرفات OWN المتصادمة بين المصدرين 36 و72.
- FLD_ID واحد أو أكثر، والمركز/الخدمة المتأثرة.
- القرار: APPROVE / REJECT / DEFER / EXCEPTION، مع rationale.
- Domain SoR وShared Service SoR وحدود القراءة والكتابة.
- Domain Owner وSchema Steward وAPI Owner وأدوار المراجعة.
- مصدر القرار: الوثيقة/الفصل/القسم/السطر أو مرجع المصدر الدقيق.
- تاريخ القرار وإصدار العقد والنطاق الذي ينطبق عليه.
- API/Event/Approval IDs الدقيقة، producer/consumer، وtest ID exact.
- test run/build/environment/commit/evidence URI والنتيجة.
- أثر التغيير والتوافق والترحيل والتراجع.
- توقيعات الموافقة وسجل التدقيق.

لا تُملأ الحقول الفارغة بتخمين؛ تسجل TBD مع مسؤول إغلاق وخطوة تالية.

## 5. القياس بعد إعداد هذه الحزمة

- فجوات الحقول التي جرى تحويلها إلى بنود قرار وأدلة: **26/26**.
- قرارات الملكية المعتمدة بهذه الوثيقة: **0**.
- كيانات/حقول تحولت إلى schema معتمدة: **0 مثبتة**.
- روابط API/Event/Approval/Test exact جديدة: **0**.
- اختبارات منتج منفذة أو PASS جديدة: **0**.
- تغييرات التطبيق/API/قاعدة البيانات: **0**.
- موافقة اعتماد المواصفات أو بدء البرمجة: **غير موجودة**.

هذه الأرقام تصف ناتج الوثيقة 169 فقط، ولا تغير خطوط الأساس في الوثائق 97–100 أو 133 أو 167.

## 6. بوابة الإغلاق التالية

بعد تعبئة سجل القرار وتوقيعه، تُحدّث الوثيقة 168 وسجلات الملكية والتتبع بمراجع القرار، ثم تُراجع 97/98/99/100 لإضافة الروابط الدقيقة المعتمدة فقط. بعدها يمكن إعداد نسخة قاموس بيانات وERD للمراجعة، ثم استكمال بقية G-01..G-15 وإصدار MPRTS 1.0 واعتماد المواصفات.

**اعتماد المواصفات وبدء البرمجة موافقتان منفصلتان. لا يمنح هذا العمل إذناً بالبرمجة.**
