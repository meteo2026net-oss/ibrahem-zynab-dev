# 174 — مراجعة النتائج المتوقعة وجاهزية تنفيذ اختبارات القبول C11–C14

**التاريخ:** 2026-11-16  
**الحالة:** EXPECTED-RESULT TEXT RECOVERED / ORACLE QUALITY REVIEW / TESTS NOT RUN / NOT APPROVED
**المصادر:** 67–70، 128–131، 150، 164، 172؛ وسجلات 97–100 وقرارات 165–167.

## 1. النتيجة التنفيذية

تحتوي workpapers 128–131 على 86 سيناريو قبول محلياً، ولكل سيناريو سطر نتيجة متوقعة مختصر. هذا أفضل من حالة C05–C07 في 163 التي لا تحتوي expected results منفصلة في المصدر المستعاد، لكنه لا يعني أن 86 اختباراً مكتمل التعريف أو جاهز للتنفيذ.

| المركز | عدد السيناريوهات | نتائج متوقعة نصية | روابط عمليات مرشحة | أحداث | الحالة |
|---|---:|---|---:|---:|---|
| C11 | 22 | 22/22 | 12/12 semantic candidates | 14 | SPECIFIED / NOT RUN |
| C12 | 22 | 22/22 | 12/12 semantic candidates | 9 | SPECIFIED / NOT RUN |
| C13 | 20 | 20/20 | 10/10 semantic candidates | 4 | SPECIFIED / NOT RUN |
| C14 | 22 | 22/22 | 13/13 semantic candidates | 1 | SPECIFIED / NOT RUN |
| **الإجمالي** | **86** | **86/86 نصوص موجزة** | **47/47 مرشحاً** | **28** | **0 منفذ** |

النتيجة المتوقعة النصية لا تساوي oracle مكتمل. لم تُستعد في هذه الحزم مواصفات موحدة لكل اختبار تشمل test owner وsetup وsteps وdata/fixtures وassertions قابلة للقياس وcleanup وevidence schema وenvironment.

## 2. فحص قابلية النتائج المتوقعة للتحويل إلى assertions

| المركز | حالات ذات نتيجة نصية | طبيعة النتائج | اعتمادها على قرارات خارجية |
|---|---:|---|---|
| C11 | 22 | رفض/قبول build وartifact وrelease/deploy، وفصل موافقات، وtelemetry/backup | مرتفع في rollback وmigration وbackup وhealth policy |
| C12 | 22 | provenance وACL وversioning وreview/publish وsearch/index/OCR/translation/export | مرتفع في retention/legal hold وحقوق التصدير وSLA إبطال المشتقات |
| C13 | 20 | precedence وACL وversion/hash وsecret references وdomain-owner API | مرتفع في precedence وpolicy وrollback وapproval rules |
| C14 | 22 | عزل tenants وcache وfreshness وsource lineage وdashboard export | مرتفع في KPI definitions وfreshness thresholds وcache invalidation وexport snapshot |

**تصنيف هذه المراجعة:** 86/86 لها نتيجة متوقعة مكتوبة في workpapers؛ 0/86 لها ملف اختبار كامل موحد يثبت كل عناصر التنفيذ؛ 0/86 نُفذت بأدلة تشغيل في هذه الجولة.

## 3. C11 — البناء والإصدارات والنشر (22)

| Test ID | النتيجة المتوقعة المصدرية المختصرة | تقييم oracle / فجوة الإغلاق |
|---|---|---|
| C11-AT-001 | رفض طلب build غير المصرح وتسجيل سبب آمن | يحتاج actor/permission fixture ورفض محدد ومرجع audit |
| C11-AT-002 | diagnostics وعدم بدء job لملف غير صالح | يحتاج profile schema وإصدار target وdiagnostic codes |
| C11-AT-003 | تثبيت المرجع الأصلي أو رفض تغيير commit | يجب اختيار السلوك المعتمد وتحديد immutability semantics |
| C11-AT-004 | عدم إنشاء build مكرر عند idempotency key نفسه | يحتاج key scope ومدة الاحتفاظ وassertion عدد jobs |
| C11-AT-005 | عدم إعلان النجاح عند فشل runner | يحتاج حالات job terminal وevidence ref |
| C11-AT-006 | تنقيح/منع secret leakage في logs/metadata | يحتاج secret fixtures وقواعد الكشف ونتيجة scan |
| C11-AT-007 | رفض attestation عند اختلاف digest/source commit | يحتاج hash algorithm وsource/artifact fixtures |
| C11-AT-008 | عدم قبول artifact مع provenance ناقص/توقيع غير صالح | يحتاج trust roots وattestation schema وسياسة توقيع |
| C11-AT-009 | رفض artifact من tenant آخر | يحتاج tenant fixtures وإثبات عدم تسرب metadata |
| C11-AT-010 | رفض/تعليق طلب الموافقة دون gate evidence | يحتاج قائمة gates المطلوبة وحالة incomplete |
| C11-AT-011 | رفض deploy دون موافقة صالحة | يحتاج approval fixture مربوطاً بالمرشح والبيئة |
| C11-AT-012 | إبطال الموافقة بعد تغيير artifact | يحتاج digest binding وإثبات invalidation |
| C11-AT-013 | منع مقدم الطلب من اعتماد طلبه | يحتاج أدواراً مستقلة وSoD matrix معتمدة |
| C11-AT-014 | رفض promotion دون اجتياز بوابات البيئة | يحتاج environment gates وإصداراتها |
| C11-AT-015 | عدم إعلان release healthy عند فشل post-deploy check | سياسة التعامل مع الفشل/rollback ما زالت مطلوبة |
| C11-AT-016 | إيقاف rollback عند migration غير قابلة للعكس | BLOCKED حتى اعتماد migration risk policy وخطة استعادة |
| C11-AT-017 | منع إعلان rollback completed قبل evidence | يحتاج rollback lifecycle وevidence schema |
| C11-AT-018 | إظهار telemetry unavailable عند انقطاع SH-14 | يحتاج تعريف unavailable/stale وhealth fixture |
| C11-AT-019 | منع rollback المعتمد على backup غير متاح | BLOCKED حتى اعتماد backup readiness وRPO/RTO ذات الصلة |
| C11-AT-020 | معالجة event مكرر/خارج الترتيب بصورة idempotent | يحتاج event IDs وordering/version contract |
| C11-AT-021 | رفض AI agent الذي يحاول اعتماد/نشر production | يحتاج capability/grant fixture وسجل قرار التفويض |
| C11-AT-022 | رفض قراءة logs/release لغير المخول وتنقيح الحساس | يحتاج ACL matrix وبيانات حساسة اصطناعية |

ملاحظة تعارض الملكية: `validateBuildConfiguration` ورد في الفصل 135.9 تحت C11 بينما سجل العمليات 97 يضع `OP-C05-081` تحت C05. لا تحسم هذه الوثيقة التعارض ولا تعيد تصنيف العملية.

## 4. C12 — المعرفة والتوثيق (22)

| Test ID | النتيجة المتوقعة المصدرية المختصرة | تقييم oracle / فجوة الإغلاق |
|---|---|---|
| C12-AT-001 | رفض إنشاء وثيقة دون owner أو tenant scope | يحتاج owner/tenant fixtures وvalidation error |
| C12-AT-002 | provenance لكل قسم مولد مع source version | يحتاج corpus مرجعي وcitation/section mapping oracle |
| C12-AT-003 | عدم تضمين/تلخيص مصدر غير متاح أو غير مصرح | يحتاج ACL fixtures وassertion على النص والمقتطفات |
| C12-AT-004 | منع AI من اعتماد/نشر وثيقة والسماح بالمسودة | يحتاج role/capability tests |
| C12-AT-005 | إنشاء version immutable عند تعديل وثيقة معتمدة | يحتاج version/hash history assertions |
| C12-AT-006 | إبطال review binding عند تغيير المحتوى | يحتاج review hash/version fixtures |
| C12-AT-007 | رفض النشر دون موافقة صالحة | يحتاج SH-03 approval state fixture |
| C12-AT-008 | إبطال القرار عند اختلاف hash | يحتاج canonical hash and approval binding |
| C12-AT-009 | رفض retrieval وبدء invalidation بعد سحب ACL | يحتاج cache/index/chunk fixture وSLA غير معتمد |
| C12-AT-010 | عدم إظهار عنوان أو snippet أو نتيجة مشتقة لمستأجر آخر | يحتاج tenant isolation test corpus |
| C12-AT-011 | تطبيق التنقيح ومنع تسريب السر/الحساس | يحتاج classification/redaction policy وfixtures |
| C12-AT-012 | منع الحذف أو تطبيق legal hold وفق السياسة | BLOCKED حتى اعتماد retention/legal-hold owner/policy |
| C12-AT-013 | عدم تكرار الوثيقة/النسخ عند إعادة الفهرسة | يحتاج idempotency key وindex version oracle |
| C12-AT-014 | إظهار freshness state عند قدم index | يحتاج canonical/index version fixture وthreshold |
| C12-AT-015 | حفظ file ref والصفحة ومواضع OCR للنص والجداول | يحتاج ملفات متعددة الصفحات وground-truth annotations |
| C12-AT-016 | حفظ الأصل ومرجع الترجمة دون تغيير provenance | يحتاج RTL corpus وsource/translation linkage |
| C12-AT-017 | إظهار diff دون تعديل النسخ | يحتاج known-diff fixtures وimmutability assertion |
| C12-AT-018 | منع التصدير أو تطبيق قيود الحقوق/الترخيص | BLOCKED جزئياً حتى اعتماد license taxonomy وpolicy owner |
| C12-AT-019 | منع الأرشفة/الحذف المخالف للاحتفاظ | BLOCKED حتى اعتماد retention policy وlegal hold |
| C12-AT-020 | معاملة prompt injection في المستند كمحتوى غير موثوق | يحتاج adversarial corpus وassertion أن السياسات لا تتغير |
| C12-AT-021 | إظهار مصدر المقارنة والنسخ والفرق عند drift | يحتاج baseline/source versions وdrift fixtures |
| C12-AT-022 | إبطال cache/chunks/embeddings بعد سحب ACL ضمن SLA | BLOCKED حتى اعتماد SLA وقياس اكتمال invalidation |

## 5. C13 — الإعدادات والتوريث (20)

| Test ID | النتيجة المتوقعة المصدرية المختصرة | تقييم oracle / فجوة الإغلاق |
|---|---|---|
| C13-AT-001 | عرض القيمة الفعالة ومصدرها وسلسلة التوريث | يحتاج precedence matrix معتمدة وfixture لكل scope |
| C13-AT-002 | رفض تجاوز enforced setting من نطاق أدنى | يحتاج تعريف enforced/override لكل مفتاح |
| C13-AT-003 | رفض قراءة/تعديل إعداد tenant آخر | يحتاج tenant ACL fixtures |
| C13-AT-004 | إبقاء تغيير حساس pending أو رفضه دون تطبيق | يعتمد على policy غير محسومة؛ يجب اختيار outcome لكل فئة |
| C13-AT-005 | إبطال الموافقة عند تغير value/hash | يحتاج version/hash binding |
| C13-AT-006 | عدم تصدير secret values أو metadata الحاملة لها | يحتاج secret classification وexport fixtures |
| C13-AT-007 | إيقاف rollback ذي آثار غير قابلة للعكس وطلب خطة | يحتاج تعريف irreversibility وrollback approval policy |
| C13-AT-008 | إظهار تعارض دون overwrite صامت عند expected_version متزامن | يحتاج concurrency fixture وconflict response contract |
| C13-AT-009 | المرور عبر API المالك وعدم الكتابة المباشرة | يحتاج domain-owner stub وتأكيد عدم وجود side-write |
| C13-AT-010 | عدم تأثير تفضيل مستخدم على الآخرين | يحتاج مستخدمين اثنين وscoped setting fixtures |
| C13-AT-011 | رفض مفتاح غير مسموح في scope | يحتاج allowlist/schema version |
| C13-AT-012 | validation report دون تطبيق لقيمة تخالف schema | يحتاج schemas مملوكة ومثبتة |
| C13-AT-013 | رفض secret reference غير صالح/غير مخول دون كشف القيمة | يحتاج vault stub وnegative assertions |
| C13-AT-014 | عدم ظهور قيمة سرية في logs/events/audit | يحتاج synthetic secrets وفحص جميع sinks |
| C13-AT-015 | نتيجة حتمية حسب policy precedence المعتمدة | BLOCKED حتى اعتماد precedence لكل فئة/مفتاح |
| C13-AT-016 | رفض/إبقاء الطلب غير مكتمل عند غياب owner أو impact class | يحتاج owner registry وimpact taxonomy |
| C13-AT-017 | عدم إصدار SettingApplied/عدم عرض القيمة فعالة عند فشل API المالك | يحتاج domain API failure fixture وevent assertions |
| C13-AT-018 | idempotency عند إعادة تسليم SettingApplied | يحتاج event deduplication contract |
| C13-AT-019 | رفض الوكيل تعديل policy/secret والسماح بالاقتراح المفوض | يحتاج agent capability fixture |
| C13-AT-020 | حفظ التاريخ وإظهار الإصدار الفعال بعد rollback | يحتاج version history وrollback semantics المعتمدة |

## 6. C14 — لوحة التحكم والإسقاطات (22)

| Test ID | النتيجة المتوقعة المصدرية المختصرة | تقييم oracle / فجوة الإغلاق |
|---|---|---|
| C14-AT-001 | لا تظهر بيانات/metadata لمستأجر آخر | يحتاج tenant fixtures وnegative data-leak assertions |
| C14-AT-002 | عدم إعادة cache من permission context سابق | يحتاج cache-key design وrevocation test |
| C14-AT-003 | إظهار source/as_of/freshness/owner لكل metric | يحتاج KPI/source registry |
| C14-AT-004 | استمرار بقية اللوحة عند تعطل مركز وبيان unavailable | يحتاج failure injection وdegraded-state contract |
| C14-AT-005 | وسم البيانات القديمة stale لا live | BLOCKED جزئياً حتى اعتماد freshness thresholds لكل مصدر |
| C14-AT-006 | اكتشاف اختلاف اللوحة عن المصدر وعرض aggregation/version | يحتاج aggregation definition وgolden dataset |
| C14-AT-007 | رفض الكتابة على سجل مجال وتوجيه المستخدم للمالك | يحتاج operation authorization assertions |
| C14-AT-008 | تطبيق ACL/classification/approval على كل مصدر في export | يحتاج source classification fixtures وexport policy |
| C14-AT-009 | عزل layout بين مستخدمين | يحتاج actor-scoped preference fixtures |
| C14-AT-010 | منع كشف projection بعد سحب صلاحية المصدر | يحتاج invalidation/reauthorization SLA |
| C14-AT-011 | عدم عرض نتيجة اختبار كنجاح موثق دون artifact evidence | يحتاج evidence schema من C07/C11 |
| C14-AT-012 | فصل operational health عن QA quality | يحتاج metric taxonomy وsource ownership |
| C14-AT-013 | منع cache key دون permission context | يحتاج architecture-level assertion أو integration test |
| C14-AT-014 | عرض telemetry unavailable عند توقف SH-14 | يحتاج health/freshness state contract |
| C14-AT-015 | عرض approval refs من SH-03 دون قرار محلي | يحتاج SH-03 contract fixture |
| C14-AT-016 | ربط release overview بمرجع C11 لا باستنتاج build success | يحتاج release lifecycle fixture |
| C14-AT-017 | عدم عرض مهام مستخدم آخر دون تفويض | يحتاج actor/owner fixtures |
| C14-AT-018 | تثبيت snapshot أو رفض export غير المتسق عند تغير المصدر | يحتاج snapshot consistency semantics |
| C14-AT-019 | idempotency وعدم إنشاء refresh loop | يحتاج repeated invalidation fixture وbounded retry oracle |
| C14-AT-020 | منع server-side الوصول إلى widget غير مصرح | يحتاج widget/source ACL matrix |
| C14-AT-021 | تمييز estimated عن actual مع المصدر والفترة | يحتاج cost metric definitions وSH-11 ownership |
| C14-AT-022 | حفظ source refs/version lineage وإظهار نقص المصدر | يحتاج multi-source golden snapshot |

## 7. فجوات مشتركة تمنع تحويل السيناريوهات إلى حزمة تنفيذ

| عنصر | ما توفر | ما يزال مطلوباً |
|---|---|---|
| Test ID | 86 معرفاً محلياً في workpapers | قرار aliases/canonical namespace مقابل مسودات 67–70 |
| Expected result | 86 نتيجة مختصرة | تحويلها إلى assertions قابلة للقياس وتحديد outcomes الشرطية |
| Test owner | لا مالك اختبار معتمد لكل صف | تعيين مالك مركز ومراجع QA مستقل |
| Setup/preconditions | إشارات متفرقة إلى actor/tenant/policy | fixtures قابلة لإعادة الإنتاج وإصداراتها |
| Steps | وصف السيناريو والنتيجة | خطوات تنفيذ مرقمة ومدخلات/مخرجات محددة |
| Test data | لا حزمة fixtures معتمدة شاملة | بيانات اصطناعية منقحة وgolden datasets |
| Oracle | نتائج مختصرة وبعضها مشروط بسياسة | assertions للـstate/API/events/audit/artifacts |
| Cleanup | غير موحد | إرجاع الحالة وإبطال الرموز وإزالة fixtures |
| Evidence | مراجع/آثار متوقعة على مستوى عام | evidence schema وrun ID وartifact refs وcorrelation IDs |
| Environment | غير معتمد كبيئة اختبار موحدة | بيئات معزولة، مزودات sandbox، نسخ الخدمات |
| Execution | SPECIFIED / NOT RUN | تشغيل موثق مرتبط بـcommit/build/environment |

## 8. الربط بالعمليات والأحداث والموافقات

- الوثيقة 150 تربط 47 عملية C11–C14 بمرشحات دلالية فقط؛ 0/47 exact approved.
- الوثائق 128–131 لا تثبت روابط Event→Test exact: C11 0/14، C12 0/9، C13 0/4، C14 0/1؛ الإجمالي 0/28.
- موافقات C11–C14 عددها 8 (3+2+2+1) وما زالت مراجعة مالك؛ لا روابط اختبار موافقة نهائية معتمدة.
- لا تُعتمد أي aliases بين `Cxx-DRAFT-AT-nn` و`Cxx-AT-nnn` بهذه الوثيقة.

## 9. قرارات/مدخلات تمنع PASS النهائي

1. C11: حسم تعارض `validateBuildConfiguration`، واعتماد build/release gates، وسياسة migration rollback، ونسخ SH-15، وRTO/RPO ذات الصلة، وSoD.
2. C12: تعيين مالك retention/legal hold، واعتماد حقوق التصدير، وSLA إبطال search index/chunks/embeddings/caches، ومزود OCR/search عند اللزوم.
3. C13: اعتماد precedence لكل scope/key class، وschema ownership، وتصنيف الأسرار، وقواعد approval/rollback.
4. C14: اعتماد تعريفات KPI وaggregation، freshness thresholds، cache invalidation/permission context، snapshot/export semantics، ومالك SH-11 للتكلفة.
5. جميع المراكز: اعتماد مالكي الاختبارات والبيئات والبيانات المرجعية ونسق الأدلة وcanonical test IDs.

## 10. قياس الإغلاق

| القياس | الحالة بعد هذه المراجعة |
|---|---:|
| نصوص expected result المحصاة | 86/86 |
| ملفات اختبار موحدة مكتملة العناصر | 0/86 مثبتة |
| روابط Operation→Test exact approved | 0/47 في نطاق C11–C14 |
| روابط Event→Test exact approved | 0/28 |
| موافقات مالك نهائية C11–C14 | 0/8 مثبتة |
| اختبارات منفذة بهذه الجولة | 0 |
| تغييرات كود/API/schema/database | 0 |

**الحكم:** C11–C14 لديها 86 نتيجة متوقعة موجزة قابلة للتحويل إلى مواصفات اختبار، لكن لا يجوز وصفها بأنها حزمة اختبارات تنفيذية مكتملة أو معتمدة. الوثيقة تحدد تحويل كل سيناريو إلى test case قابل للتكرار وتوضح القرارات التي تمنع بعض الحالات من PASS. لا تصدر هذه الوثيقة API أو schema أو migration ولا تمنح إذناً بالبرمجة. يظل بدء البرمجة متوقفاً حتى إغلاق المواصفات والحصول على موافقة المستخدم الصريحة المستقلة.