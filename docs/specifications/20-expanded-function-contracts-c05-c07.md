# 20 — توسيع عقود وظائف C05–C07

**الحالة:** DRAFT / DERIVED FUNCTION CONTRACTS / PENDING SOURCE RECONCILIATION  
**الأساس:** معرفات الوظائف وأسماؤها ودورات العمل في الملف 12.  
**تنبيه:** التفاصيل التالية توسع تشغيلي مقترح للوظائف المعروفة؛ لا تدعي استعادة نصوص الفصول الأصلية ولا اعتماد API أو مخطط البيانات أو نتائج الاختبارات.

## 1. القواعد المشتركة

- الأدوار المذكورة مبدئية وتخضع لمصفوفة التفويض المركزية.
- كل كتابة أو تنفيذ أداة يتطلب نطاق مهمة وصلاحية خادمية وسجل تدقيق.
- لا يدمج الوكيل أو ينشر أو يوافق على عمله دون تفويض مستقل.
- معرفات الاختبارات الأصلية محفوظة في القسم 5؛ لم يتم افتراض توزيع غير موثق بينها وبين الوظائف.
- لا تغلق الوظيفة قبل استكمال schema والعقود والأخطاء والاختبارات والأدلة.

## 2. C05 — مركز البرمجة

| المعرف والوظيفة | المدخلات والشروط | المخرجات | الصلاحيات والضوابط | حالات الفشل الواجب اختبارها |
|---|---|---|---|---|
| C05-F01 تهيئة بيئة البرمجة | مشروع، منصة، toolchain، حدود موارد | بيئة معزولة وmanifest | مطور/مسؤول منصة؛ موافقة للموارد الحساسة | تعارض نسخ، أداة مفقودة، فشل عزل |
| C05-F02 إدارة الملفات والمجلدات | نطاق المشروع، مسار، عملية، إصدار أساس | شجرة ملفات وdiff | مطور؛ الوكيل ضمن task scope | مسار خارج الجذر، تعارض كتابة، ملف محمي |
| C05-F03 تحرير الكود | ملف، patch، encoding، base version | تعديل وdiagnostics وإصدار | مطور؛ الوكيل بصلاحية محددة | تعارض إصدار، فقد encoding، syntax error |
| C05-F04 إدارة اللغات والأطر والمنصات | target، language/framework، قيود توافق | technology profile | مسؤول تقني؛ موافقة للقرار عالي الأثر | نسخة غير مدعومة، ترخيص غير مقبول |
| C05-F05 توليد الكود من التصميم | تصميم معتمد، tokens، adapter، mapping | ملفات مولدة وروابط trace | محرك التوليد؛ مراجعة قبل الدمج | عنصر غير مدعوم، فقد سلوك، mapping ناقص |
| C05-F06 توليد الكود من المتطلبات | baseline معتمد، requirements، architecture | code tasks وscaffolding | محرك التوليد؛ اعتماد التغيير | متطلب ملتبس، اعتماد ناقص، كود غير قابل للتحقق |
| C05-F07 إدارة المعمارية البرمجية | modules، dependencies، constraints | architecture graph وتقارير | مهندس/مراجع | dependency cycle، boundary violation |
| C05-F08 إدارة الاعتماديات | manifests، lockfiles، policies | dependency graph وlicense/security report | مطور؛ مراجعة التحديثات المؤثرة | حزمة خطرة، تعارض نسخة، ترخيص مجهول |
| C05-F09 إنشاء APIs والخدمات | API schema، domain model، auth policy | contracts وخدمات | مطور/مهندس؛ مراجعة أمنية | schema invalid، authorization missing، breaking change |
| C05-F10 تحليل الأخطاء والتصحيح | logs، stack traces، source refs | تشخيص وpatch مقترح وخطة تحقق | الوكيل يقترح؛ المطور يعتمد | دليل ناقص، تشخيص خاطئ، تسريب سر |
| C05-F11 تعديل الكود باللغة الطبيعية | طلب، نطاق ملفات، baseline، constraints | patch proposal وdiff وتفسير | وكيل ضمن النطاق؛ موافقة للتغييرات المؤثرة | scope drift، طلب ملتبس، لمس ملف محمي |
| C05-F12 مزامنة التصميم والكود | design/code versions، mapping/IR | drift report وconflict proposal | مطور/مصمم؛ موافقة على حل التعارض | overwrite يدوي، mapping قديم |
| C05-F13 Sandbox وتشغيل الكود | source snapshot، runtime، resource/network limits | execution result وlogs وartifacts | مطور/QA؛ عزل إلزامي | escape، استنزاف موارد، timeout |
| C05-F14 GitHub وإدارة الإصدارات | repo ref، branch، diff، commit metadata | branch/commit/PR refs | صلاحية repo مستقلة؛ الدمج بموافقة | stale base، رفض كتابة، secret detected |
| C05-F15 مراجعة الجودة والأمن والتراخيص | diff، SBOM، scan configs، policies | findings وseverity وwaivers | QA/security reviewers | فحص غير متاح، finding حرج غير معالج |
| C05-F16 اعتماد الكود وتسليمه | approved diff، tests، reviews، trace matrix | code baseline جاهز للتسليم | مالك/مراجع مستقل | دليل ناقص، اختبار فاشل، موافقة قديمة |

## 3. C06 — مركز فريق الذكاء الاصطناعي

| المعرف والوظيفة | المدخلات والشروط | المخرجات | الصلاحيات والضوابط | حالات الفشل الواجب اختبارها |
|---|---|---|---|---|
| C06-F01 إدارة الوكلاء | agent registry، owner، scope، status | سجل الوكلاء وإصداراتهم | مدير فريق AI | هوية مكررة، مالك مفقود |
| C06-F02 إنشاء وكيل متخصص | role، skills، tools، boundaries | agent profile draft | مدير AI؛ مراجعة أمنية قبل التفعيل | صلاحيات زائدة، أداة غير آمنة |
| C06-F03 إدارة المهارات | skill schema، version، I/O | skill registry | مالك المهارة/مدير AI | schema غير متوافق، مصدر غير موثوق |
| C06-F04 إدارة الأدوات والتفويض | tool manifest، capabilities، scopes | grants محدودة ومؤقتة | مسؤول مفوض؛ موافقة للحساس | تصعيد صلاحية، grant منتهي |
| C06-F05 إدارة المهام | task request، tenant/project scope، policy | task record وحالة | مستخدم مخول/Orchestrator | مهمة مكررة، scope مفقود، انتقال غير صالح |
| C06-F06 مخطط التنفيذ | goal، dependencies، constraints، budget | plan وخطوات ومخاطر | AI يقترح؛ اعتماد بشري للأثر العالي | خطة غير قابلة للتنفيذ، تجاوز ميزانية |
| C06-F07 تنسيق الوكلاء | approved plan، capabilities، task graph | assignments/events | Orchestrator ضمن policy | deadlock، تعيين مكرر، وكيل غير متاح |
| C06-F08 إدارة الذاكرة والسياق | project scope، classification، retention | context package وmemory refs | حسب المشروع والخصوصية | تسرب بين المشاريع، سياق قديم |
| C06-F09 إدارة مزودي AI | provider/model metadata، secret refs | provider registry | مسؤول AI/منصة | مزود غير متاح، نموذج متقاعد، اعتماد غير صالح |
| C06-F10 السياسات والتكاليف | budgets، quotas، model policies | effective policy وusage limits | مالك المؤسسة/مسؤول AI | تجاوز الميزانية، تضارب سياسة |
| C06-F11 المراقبة | traces، metrics، task events | health/performance views | تشغيل/مسؤول AI | فجوة telemetry، تسرب tenant |
| C06-F12 مراجعة النتائج | outputs، evidence، task criteria | review decision | مراجع مستقل؛ لا مراجعة ذاتية منفردة | ادعاء بلا دليل، معيار غير مستوفى |
| C06-F13 معالجة التعارضات | competing outputs، resource versions | conflict record/resolution proposal | مالك المورد/مراجع | دمج هدّام، baseline قديم |
| C06-F14 الصلاحيات والموافقات | action، actor، resource، risk | authorization/approval decision | خدمة التفويض وموافق مستقل | self-approval، موافقة منتهية |
| C06-F15 الإيقاف والاستعادة | task/agent state، checkpoint، reason | suspended/resumed/cancelled | مستخدم مخول/مشغل | استئناف غير آمن، checkpoint تالف، أثر مكرر |
| C06-F16 سجل التدقيق | structured events، actor identity | append-only audit events | Audit Service؛ الوكيل لا يعدل | محاولة عبث، فاعل مفقود، فجوة تسلسل |
| C06-F17 تقييم الأداء | outcomes، quality scores، costs، feedback | evaluation metrics | مسؤول AI | عينة منحازة، دليل غير كاف |
| C06-F18 إعداد الفريق والاختبارات | team config، agent matrix، test suites | team profile/test report | مدير AI/مراجع | وكلاء غير متوافقين، اختبارات أمان ناقصة |

## 4. C07 — مركز الاختبارات وضمان الجودة

| المعرف والوظيفة | المدخلات والشروط | المخرجات | الصلاحيات والضوابط | حالات الفشل الواجب اختبارها |
|---|---|---|---|---|
| C07-F01 إنشاء خطة اختبار | requirements baseline، risks، platforms | test plan بإصدار | QA lead | متطلب بلا تغطية، نطاق مبهم |
| C07-F02 إدارة حالات الاختبار | definition، preconditions، steps، expected | test case version | QA author/reviewer | خطوة مبهمة، نتيجة متوقعة مفقودة |
| C07-F03 تتبع تغطية المتطلبات | requirement IDs، test IDs، evidence refs | coverage/gap report | QA/مالك المتطلب | متطلب يتيم، رابط غير صالح |
| C07-F04 اختبارات الوحدات | source snapshot، suite، runtime | pass/fail، coverage، logs | مطور/QA | flaky test، فشل أداة التغطية |
| C07-F05 اختبارات التكامل | contracts، test env، fixtures | integration results | QA/مهندس تكامل | خدمة تابعة غير متاحة، contract mismatch |
| C07-F06 اختبارات واجهة المستخدم | UI build، scenarios، devices | UI results/screenshots | QA | selector drift، عائق وصول |
| C07-F07 اختبارات E2E | journeys، environment، test accounts | end-to-end evidence | QA | state contamination، اعتماد خارجي متذبذب |
| C07-F08 اختبارات APIs | API schema، roles، payload fixtures | contract/security results | QA/API owner | auth bypass، schema drift |
| C07-F09 اختبارات قواعد البيانات | schema/migrations، policies، fixtures | integrity/query/RLS results | QA/data owner | cross-tenant read، migration loss |
| C07-F10 اختبارات الأمن | threat model، source/artifact، scan policy | findings/severity/evidence | Security reviewer | فحص ناقص، finding حرج غير معالج |
| C07-F11 اختبارات الأداء | workload، SLO targets، environment | latency/throughput/resource report | QA/performance owner | حمل غير ممثل، تشبع موارد |
| C07-F12 اختبارات التوافق | target matrix، OS/browser/device | compatibility matrix | QA/release owner | هدف غير مدعوم، بيئة مختلفة |
| C07-F13 اختبارات إمكانية الوصول | UI build، WCAG target، assistive scenarios | accessibility findings | QA/accessibility reviewer | الاعتماد على فحص آلي فقط، عائق لوحة مفاتيح |
| C07-F14 اختبارات Offline & Sync | sync policy، conflict fixtures، network profiles | sync/recovery results | QA/data owner | كتابة مكررة، صلاحية قديمة |
| C07-F15 اختبارات الانحدار | prior baseline، changed scope، suite | regression report | QA | suite ناقصة، baseline غير مطابق |
| C07-F16 إدارة العيوب | finding، severity، owner، reproduction | defect lifecycle record | QA triage team | عيب مكرر، غير قابل لإعادة الإنتاج |
| C07-F17 تقارير الجودة وبوابة الاعتماد | runs، findings، waivers، coverage | quality report/gate result | QA lead/reviewer | دليل ناقص، waiver غير صالح |
| C07-F18 اعتماد نتائج الاختبار | evidence، criteria، approvals | approval مربوط ببصمة build | مراجع مستقل/مالك جودة | artifact قديم، موافقة ذاتية |

## 5. الاختبارات المسجلة وما ينقصها

- C05: C05-AT-01 إلى C05-AT-20.
- C06: C06-AT-01 إلى C06-AT-20.
- C07: C07-AT-01 إلى C07-AT-22.

هذه المعرفات موجودة في سجل المواصفات، لكن النص الكامل لكل حالة وتوزيعها على الوظائف لم يُستعد في الحزمة الحالية. لم يتم اختراع mapping جديد ولم تُسجل أي نتيجة اجتياز.

## 6. شروط الإغلاق لكل وظيفة

قبل اعتماد أي من الوظائف الـ52 للتنفيذ، يلزم استكمال: schema تفصيلي للمدخلات والمخرجات، API/command/event contracts وإصداراتها، أكواد الأخطاء، مالك الكيانات ومصدر الحقيقة والاحتفاظ، مصفوفة actor/resource/action/tenant/environment، شروط الموافقات وانتهائها، retry/idempotency/cancellation/timeout، سجلات التدقيق والقياسات والتكلفة، اختبارات إيجابية وسلبية وتكامل وعقد وE2E، وأدلة مرتبطة ببصمة الإصدار والبيئة.

**الخلاصة:** يوسع هذا الملف عقود الوظائف الـ52 إلى مستوى تشغيلي أولي ويحدد حالات الفشل والحوكمة المطلوبة. يظل DRAFT ولا يغلق فجوة أي مركز حتى مطابقة المصدر واستكمال العقود والاختبارات والأدلة.
