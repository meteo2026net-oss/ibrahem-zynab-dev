# 21 — العقود التشغيلية الأولية للمركزين C13 وC14

**الحالة:** DRAFT / DERIVED FROM RECOVERED SCREEN AND FUNCTION LISTS  
**المصادر:** الملفان 08 و09، والفصلان 150 و153 كما استُعيدا في سجل المواصفات.  
**تنبيه:** تفاصيل العقود أدناه تفصيل هندسي أولي مستنتج. معرفات SET وDASH الأصلية محفوظة دون إعادة توزيع أو ادعاء اجتياز.

## 1. C13 — مركز الإعدادات

القاعدة الحاكمة: مركز الإعدادات يدير القيم والنطاقات والتوريث، لكنه لا يمتلك منطق الخدمة التي يضبط إعداداتها. يجب تنفيذ التغيير عبر عقد الخدمة المالكة، مع تطبيق سياسات النطاق الأعلى.

| الوظيفة | المدخلات والشروط | المخرجات | الصلاحيات والضوابط | حالات الفشل |
|---|---|---|---|---|
| SET-F01 عرض الإعدادات حسب النطاق والدور | actor، scope، resource، policy | قائمة إعدادات مرئية والقيمة الفعالة ومصدرها | القراءة وفق التفويض | نطاق غير صالح، تسريب إعداد مؤسسة |
| SET-F02 تعديل تفضيلات الحساب | user scope، key، value، current version | قيمة شخصية بإصدار | المستخدم لنفسه ضمن allowlist | محاولة تعديل قيمة مفروضة |
| SET-F03 إدارة إعدادات المؤسسة ومساحة العمل | org/workspace scope، policy values | إعدادات نطاق مؤسسي | مدير مؤسسة/مساحة عمل | تجاوز policy أعلى، عضوية منتهية |
| SET-F04 ضبط إعدادات المشروع والبيئة | project/environment، key/value، baseline | إعدادات مشروع/بيئة | مالك المشروع/مسؤول البيئة | خلط أسرار بيئات، تعارض توريث |
| SET-F05 ضبط الواجهة واللغة وإمكانية الوصول | locale، theme، density، a11y prefs | profile فعّال | المستخدم/مسؤول تجربة حسب النطاق | قيمة غير مدعومة، إعداد يخرق سياسة إلزامية |
| SET-F06 اختيار التقنيات والأطر | technology profile، compatibility، policy | اختيار مقترح/معتمد | مسؤول تقني؛ موافقة للأثر العالي | تقنية خارج allowlist، تعارض توافق |
| SET-F07 ضبط محركات التصميم والبرمجة | engine/adapter version، config refs | engine profile | مسؤول منصة/تقني | محرك غير متوافق، إعداد غير صالح |
| SET-F08 ضبط مزودي AI وسياساتهم | provider/model، secret ref، privacy/cost policy | AI profile | مسؤول AI؛ موافقة على إرسال بيانات حساسة | secret missing، provider policy mismatch |
| SET-F09 ضبط قواعد المهام والأتمتة | triggers، conditions، actions، schedule | automation policy/version | مالك مساحة/مشروع؛ موافقة للإجراءات المؤثرة | loop، trigger غير صالح، action غير مخول |
| SET-F10 ضبط التكاملات ومراجع الأسرار | connector، scope، secret reference | integration config | مسؤول تكامل؛ الأسرار في vault | secret ref مكشوف/غير موجود، connector غير موثوق |
| SET-F11 ضبط الأمن والخصوصية عبر المركز المالك | policy request، scope، risk | طلب تغيير موجه للمالك | مسؤول أمن/خصوصية؛ لا تعديل مباشر خارج العقد | تعارض سياسة أعلى، موافقة ناقصة |
| SET-F12 ضبط قنوات الإشعارات | channel، template، preferences، consent | notification preferences | المستخدم/مسؤول إشعارات | قناة غير متاحة، تفضيل يخالف سياسة |
| SET-F13 ضبط حدود الموارد والتكاليف | quota، budget، period، scope | budget policy/version | مالك مؤسسة/مسؤول مالي | حد غير صالح، عملة غير محددة، تجاوز موافقة |
| SET-F14 ضبط بيئات البناء والنشر | environment، pipeline refs، deploy policy | release environment profile | مسؤول نشر | إعداد إنتاج من نطاق أدنى، secret mismatch |
| SET-F15 مقارنة إصدارات الإعدادات | version refs، scope | diff/report | قراءة وفق الدور | إصدار مفقود، اختلاف نطاق |
| SET-F16 طلب تغيير حساس وموافقة | change diff، reason، approvers | approval request/status | طالب وموافق مستقل | self-approval، تغير diff، موافقة منتهية |
| SET-F17 التراجع عن إعداد | target version، impact analysis، rollback plan | rollback result أو رفض آمن | مسؤول مخول وموافقة عند الحاجة | side effects غير قابلة للعكس، target stale |
| SET-F18 تشخيص التعارضات والقيم غير الصالحة | effective config، schemas، inheritance | diagnostics مع owner | مسؤول إعدادات/مالك | schema غير متاح، تعارض غير قابل للحسم آلياً |
| SET-F19 استيراد/تصدير إعدادات غير سرية | config bundle، version، scope mapping | import/export artifact | مسؤول مخول | secret leakage، unsupported schema، cross-tenant |
| SET-F20 عرض سجل تدقيق الإعدادات | audit filters، scope، time range | audit events | قراءة للمدقق؛ لا تعديل | event gap، محاولة قراءة خارج النطاق |

### شروط إضافية لـC13

- حل التوريث يجب أن يعيد القيمة الفعالة ومصدرها وسلسلة التوريث، لا القيمة وحدها.
- يجب تعريف مفاتيح الإعدادات بنوع وschema وقيمة افتراضية ونطاقات مسموحة وقابلية توريث.
- لا يجوز تصدير secret values؛ تصدر مراجعها فقط إذا سمحت السياسة.
- لا يملك C13 حق تغيير سياسة مركز آخر مباشرة؛ يرسل طلباً إلى API الخدمة المالكة.
- التراجع لا يعيد تلقائياً آثاراً خارجية مثل رسائل مرسلة أو نشر تم بالفعل.

## 2. C14 — لوحة التحكم الرئيسية

القاعدة الحاكمة: C14 طبقة قراءة وتجميع. لا تصبح مصدراً للحقيقة ولا تنفذ عمليات الكتابة المملوكة للمراكز الأخرى. كل مؤشر يعرض المصدر ووقت التحديث وحدود الثقة.

| الشاشة | المدخلات | المخرجات | صلاحيات/ضوابط | حالات الفشل |
|---|---|---|---|---|
| 01 لوحة التحكم الرئيسية | user scope، time filters، center summaries | overview، KPI، shortcuts | استعلامات scoped | مصدر متأخر، مؤشر غير متاح |
| 02 مركز المشاريع | tenant/workspace/project filters | project list/status/progress | عضوية المشروع | مشروع خارج النطاق، بيانات قديمة |
| 03 مركز المهام | task filters، state、owner | tasks، dependencies، blocked/late | صلاحية قراءة المهمة | مهمة مخفية، حالة غير متزامنة |
| 04 مركز فريق AI | agent/task/model scope | agent health، tasks، usage/cost | صلاحيات AI | مزود غير متاح، بيانات تكلفة ناقصة |
| 05 مركز الموافقات | approver/status/time filters | pending/decided approvals | الموافق يرى ما فوض به | موافقة منتهية، قرار مكرر |
| 06 مركز الإشعارات | channel/status/time filters | delivery status/failures | بيانات المستلم وفق الدور | provider unavailable، حالة تسليم متأخرة |
| 07 مركز الأنشطة | actor/resource/time filters | activity feed وروابط المصدر | لا يعرض أحداثاً ممنوعة | audit source unavailable، ترتيب غير مؤكد |
| 08 مركز تقدم التطوير | project/release/milestone filters | progress/gates/blockers | صلاحية المشروع | تقدم محسوب من مصادر غير متزامنة |
| 09 مركز الجودة والاختبارات | build/test suite filters | pass/fail/coverage/defects | صلاحية QA | نتيجة بلا artifact evidence |
| 10 مركز الأمن والمخاطر | risk/severity/tenant filters | findings/incidents/risk posture | أدوار أمنية | كشف finding محظور، بيانات ناقصة |
| 11 مركز التكاملات | connector/environment filters | health/latency/errors | مسؤول تكامل/قراءة | health check قديم |
| 12 مركز البناء والنشر | release/environment/platform | build/deploy states | صلاحية قراءة الإصدار | build status غير متاح |
| 13 مركز الموارد والتكاليف | tenant/project/period/currency | usage/budget/forecast | صلاحية مالية | سعر صرف أو بيانات تكلفة غير متوفرة |
| 14 مركز الأداء والاستمرارية | service/time/region | availability/incident/recovery | تشغيل/إدارة | telemetry gap، قيمة RTO/RPO غير معتمدة |
| 15 مركز المعرفة والتوثيق | query/category/version | recent docs/decisions/knowledge refs | ACL لكل وثيقة | نتيجة بحث لا يحق للمستخدم رؤيتها |
| 16 مركز التقارير | report type/filters/format | report preview/export | تصدير حسب الصلاحية | data snapshot inconsistent |
| 17 مركز التحليلات | metric definitions/time series | trends/comparisons | إخفاء المجموعات الصغيرة الحساسة حسب السياسة | metric definition drift |
| 18 مركز التنبيهات الحرجة | severity/state/owner | prioritized alerts | التصعيد حسب الدور | duplicate alert، missed escalation |
| 19 مركز التخصيص | layout/widget prefs | saved dashboard layout | المستخدم/المؤسسة حسب النطاق | widget غير متاح أو ممنوع |
| 20 مركز التقارير التنفيذية | reporting period، audience، approved metrics | executive summary | دور تنفيذي/موافقة على التصدير | metric stale، audience scope violation |

### ضوابط C14 الإلزامية

- جميع الاستعلامات تطبق صلاحيات الخادم قبل التجميع؛ إخفاء widget ليس ضابط وصول.
- لا يجوز أن يخلط cache بين المستخدمين أو المؤسسات أو المشاريع؛ مفاتيح cache تشمل نطاق التفويض عند اللزوم.
- كل بطاقة تعرض مصدر الحقيقة ووقت التحديث، وتوضح stale/partial/unavailable.
- لا تعرض أرقاماً مشتقة من بيانات ناقصة على أنها نهائية.
- لا تنفذ اللوحة موافقة أو نشرًا أو تغييراً؛ تنقل المستخدم إلى المركز المالك مع إعادة تحقق الصلاحية.
- تقارير التصدير تعيد تطبيق التفويض وقت الإنشاء ووقت التنزيل.
- يجب تسجيل عمليات التصدير والتقارير الحساسة في Audit Service.

## 3. الاختبارات المسجلة وما ينقصها

- C13: SET-001 إلى SET-084.
- C14: DASH-001 إلى DASH-084، إضافة إلى DASH-INT-01 إلى DASH-INT-10.

لا تتضمن الحزمة الحالية نص كل حالة اختبار أصلية ولا خطواتها وبياناتها ونتائجها. لا يعيد هذا الملف توزيع المعرفات على الوظائف، ولا يدعي تنفيذ الاختبارات. يجب استعادة النص الأصلي أو اعتماد mapping جديد بمراجعة مالكي المركزين.

## 4. متطلبات الإغلاق

لكل وظيفة/شاشة يلزم استكمال: API contracts، event schemas، قاموس الكيانات، مصادر البيانات، المالك، مصفوفة الصلاحيات، حالات الخطأ، مؤشرات الأداء، retention، audit events، الاختبارات السلبية والإيجابية، وأدلة التنفيذ. تظل أي عتبات تكلفة أو سياسات مالية أو أهداف RTO/RPO غير معتمدة ما لم يسجل لها قرار مستقل.

**الخلاصة:** هذا الملف يوسع عقود C13 وC14 تشغيلياً مع احترام ملكية المراكز الأخرى، لكنه يبقى DRAFT حتى المطابقة والاعتماد والاختبار.
