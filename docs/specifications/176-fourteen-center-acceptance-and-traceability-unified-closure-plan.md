# 176 — الخطة الموحدة لإغلاق اختبارات القبول والتتبع للمراكز الأربعة عشر

**الحالة:** CONSOLIDATED BASELINE / OWNER DECISIONS OPEN / NOT EXECUTED / NOT APPROVED  
**النطاق:** C01–C14، 201 عملية، 106 أحداث، 26 نوع موافقة، وسيناريوهات القبول المستعادة.

## 1. الملخص التنفيذي

| محور التتبع | خط الأساس | الحالة الدقيقة |
|---|---:|---|
| المراكز | 14 | النطاق المعماري ثابت في خط الأساس |
| المحاور الوظيفية | 64 | لا يثبت إغلاق كل محور بالاختبارات والمالك |
| عمليات API/Command/Query | 201 | 0/201 exact Operation→Test approved |
| أحداث المجال | 106 | 0/106 exact Event→Test approved |
| أنواع الموافقات | 26 | 0/26 approval-test exact approved |
| سيناريوهات workpaper | 254 | 0 تنفيذ موثق في خط الأساس |
| مرشحات أحداث دلالية | 106 | مرشحات وليست روابط exact |
| مرشحات عمليات C08–C14 | 81/81 | 34 صريحة + 47 دلالية؛ 0 معتمد |
| عمليات C01–C07 ذات مرشح وظيفة | 113/120 | Function→Operation وليست Operation→Test |

**تنبيه عددي:** 254 = 68 سيناريو C01–C04 + 62 سيناريو C05–C07 + 124 سيناريو C08–C14. هذه سيناريوهات workpaper وليست دليلاً على اكتمال تعريف كل اختبار أو اعتماد aliases.

## 2. مصفوفة المراكز الموحدة

| المركز | العمليات | الأحداث | الموافقات | سيناريوهات | الحالة |
|---|---:|---:|---:|---:|---|
| C01 | 16 | 6 | 1 | 15 | 15/15 لها expected result منفصل؛ غير منفذة |
| C02 | 12 | 5 | 1 | 15 | 0/15 لها expected result منفصل؛ غير منفذة |
| C03 | 11 | 5 | 1 | 18 | 0/18 لها expected result منفصل؛ غير منفذة |
| C04 | 23 | 10 | 3 | 20 | 0/20 لها expected result منفصل؛ غير منفذة |
| C05 | 23 | 10 | 2 | 20 | لا expected results منفصلة في B-05؛ غير منفذة |
| C06 | 21 | 9 | 2 | 20 | لا expected results منفصلة في B-06؛ غير منفذة |
| C07 | 14 | 6 | 2 | 22 | لا expected results منفصلة في B-07؛ غير منفذة |
| C08 | 11 | 11 | 2 | 10 | مرشحات عملية صريحة؛ تعريف الاختبار غير مكتمل |
| C09 | 12 | 10 | 2 | 10 | OAuth/webhook/sync fixtures غير معتمدة |
| C10 | 11 | 6 | 2 | 18 | سياسة الأمن وSoD/SH-02 تحتاج حسم |
| C11 | 12 | 14 | 3 | 22 | expected text 22/22؛ oracle والقرارات التشغيلية ناقصة |
| C12 | 12 | 9 | 2 | 22 | expected text 22/22؛ retention/legal hold مفتوحة |
| C13 | 10 | 4 | 2 | 20 | expected text 20/20؛ precedence والأسرار مفتوحة |
| C14 | 13 | 1 | 1 | 22 | expected text 22/22؛ KPI/freshness/cache مفتوحة |
| **الإجمالي** | **201** | **106** | **26** | **254** | **لا روابط exact معتمدة ولا تنفيذ مثبت** |

## 3. تفسير أعداد التغطية

1. Function→Operation candidate ليس Operation→Test link؛ لذلك 113/120 لا ترفع تغطية الاختبارات.
2. وجود مرشح لكل عملية في C08–C14 لا يثبت تغطية كل مدخلات العملية أو مخرجاتها أو أحداثها أو صلاحياتها.
3. مرشح Event→Test لا يثبت payload schema أو producer/consumer أو ordering أو retry/idempotency أو أثر التدقيق.
4. وجود Approval ID أو اسم فعل محمي لا يثبت workflow أو فصل الواجبات أو قرار مالك.
5. SPECIFIED / NOT RUN لا تعني PASS. كما أن عدم ظهور CI status أو workflow run لا يعني نجاح CI.

## 4. الحد الأدنى الموحد لاكتمال test case

| الحقل | معيار القبول |
|---|---|
| Canonical ID | معرف واحد معتمد مع حفظ معرف المصدر والaliases بقرار صريح |
| روابط التتبع | متطلب/عملية/حدث/موافقة معتمدة من مالك المجال |
| Owner/reviewer | مالك اختبار ومراجع مستقل وفق المخاطر |
| Preconditions/setup | actor/tenant/role/policy/version/environment ثابتة |
| Steps | خطوات مرقمة قابلة للتكرار |
| Test data/fixtures | بيانات اصطناعية منقحة ونسخ ثابتة |
| Expected oracle | assertions قابلة للقياس على response/state/event/audit/artifact |
| Security assertions | إثبات عدم التسريب والعزل ورفض التفويض غير المسموح |
| Cleanup/rollback | استعادة الحالة وإزالة fixtures والرموز المؤقتة |
| Evidence | run ID وcommit/build SHA وenvironment وlogs/artifact refs |
| Execution review | مراجعة مستقلة وdefect/retest عند الفشل |

## 5. قرارات الملكية والأمن والموافقات

### 5.1 الملكية وDomain SoR

- 62 عملية بلا Qualified OWN مفرد مؤهل في baseline 133؛ و139 عملية لها OWN مؤهل شكلياً فقط، لا اعتماد exact.
- لا تصبح الخدمات المشتركة مالك بيانات المجال بسبب تنفيذ المصادقة أو الموافقات أو المهام أو الفهرسة أو النسخ.
- الأحداث الـ55 في C08–C14 تحتاج owner/SoR/producer/consumer مراجعة.
- يجب الحفاظ على الفصل بين Domain SoR وShared Service SoR وعلى SH-03 كمصدر lifecycle للموافقات.

### 5.2 الأمن والخصوصية

- تعارض Zero Trust بين النص الإلزامي في 158.7 والتصنيف المقترح في 158.9 وDEC-SEC-010 ما زال يحتاج قراراً.
- SH-02 fail-closed وSoD وSH-03 lifecycle يجب أن تتحول إلى قواعد اختبار بعد اعتماد المالك.
- C08 retention/legal hold/deletion وC12 archive/legal hold وC13 secrets وC14 permission-aware cache لا يمكن إعلانها PASS قبل اعتماد السياسات.
- لا توجد قيم رقمية معتمدة لـRTO/RPO/SLO/freshness thresholds في baseline.

### 5.3 الموافقات

- C01–C07: 12 نوع موافقة؛ C08–C14: 14؛ الإجمالي 26.
- APR-C07-02 approveQualityWaiver ما زال فجوة؛ requestQualityWaiver ليس قرار اعتماد.
- يجب التمييز بين request/submit/review/decision/apply/publish/deploy؛ لا يعد الطلب موافقة.
- يلزم owner وapprover وscope وexpiry/revocation وreason وevidence وhash/version binding.

## 6. خطة الإغلاق ذات الأولوية

| المرحلة | النطاق | مخرج الإغلاق | الاعتماد |
|---|---|---|---|
| P0-A | تثبيت معرفات الاختبارات والسيناريوهات 254 | canonical registry + source aliases | مالكو المراكز وQA |
| P0-B | إغلاق OWN/Domain SoR/API Owner للعمليات والأحداث | owner matrix معتمدة | مالكو المجالات والخدمات |
| P0-C | إغلاق 26 موافقة وSH-03/SoD/SH-02 | approval decision matrix + tests | المالك المؤسسي وC10 وSH-03 |
| P0-D | حسم التعارضات المصدرية والتقنية | decision log مع source lineage | مالك القرار |
| P1-A | تحويل السيناريوهات إلى test cases مكتملة | setup/steps/fixtures/oracle/cleanup/evidence | test owners وQA |
| P1-B | إعداد بيئات اختبار معزولة وsandbox | environment manifest وtest data packs | الأمن والتشغيل |
| P1-C | تنفيذ الاختبارات تدريجياً | نتائج تشغيل وdefect/retest evidence | QA ومالكو المراكز |
| P1-D | تكامل شامل للمراكز الأربعة عشر | E2E + security/isolation/recovery evidence | اعتماد معماري وأمني |
| P2 | إصدار مرشح نشر | build provenance وrelease gates وrollback proof | موافقة النشر النهائية |

## 7. قرارات لا يجوز حسمها تحريرياً

1. Zero Trust hierarchy وقرار DEC-SEC-010.
2. OWN36/OWN72 collisions وتعيين المالك الرسمي.
3. Domain SoR لكل FLD-01..26 وSH-17..20 إن ثبتت من المصدر المعتمد.
4. canonical aliases لتصادمات COST وDASH ومعرفات اختبارات C08–C14.
5. RTO/RPO/SLO/freshness targets والاحتفاظ وlegal hold.
6. C05/C11 ownership conflict حول validateBuildConfiguration.
7. حدود SH-03 لكل مركز ومتى يلزم قرار ومن يملكه.
8. صلاحيات AI agents واعتماد نشر production.

## 8. قياس الحالة الموحد

| المؤشر | الحالة |
|---|---:|
| عمليات domain | 201 |
| Operation→Test exact approved | 0/201 |
| أحداث المجال | 106 |
| Event→Test exact approved | 0/106 |
| أنواع الموافقات | 26 |
| Approval→Test exact approved | 0/26 |
| سيناريوهات workpaper | 254 |
| سيناريوهات منفذة بأدلة مثبتة في baseline | 0/254 |
| عمليات C08–C14 ذات مرشح | 81/81 |
| أحداث ذات مرشح دلالي في crosswalks | 106/106 |
| موافقات C11–C14 ذات مرشح | 8/8 |
| تغييرات كود/API/schema/database بهذه الوثيقة | 0 |

## 9. الحكم

التوثيق والتدقيق أنتجا خريطة شاملة للمراكز الأربعة عشر، لكن الجاهزية التشغيلية لا تستنتج من عدد الوثائق أو المعرفات. العوائق الحاسمة هي قرارات المالك والأمن والبيانات والموافقات، ثم تحويل السيناريوهات إلى اختبارات قابلة للتكرار وتشغيلها مع أدلة. هذه الوثيقة خطة إغلاق موحدة وليست اعتماداً للمواصفات ولا إذناً بالبرمجة.

**حاجز بدء البرمجة:** يظل مستقلاً عن اعتماد المواصفات. لا يبدأ تعديل التطبيق أو إنشاء APIs/schemas/migrations قبل اكتمال حزمة المواصفات والحصول على موافقة المستخدم الصريحة المستقلة على بدء البرمجة.
