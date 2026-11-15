# 49 — تدقيق الدفعة E للمحاور AX-041 إلى AX-064

**التاريخ:** 2026-11-15  
**الحالة:** BATCH-E-AUDIT / SOURCE-BOUNDED / OWNER-REVIEW-PENDING  
**النطاق:** مراجعة المحاور AX-041..AX-064 مقابل الوثائق 03 و07–16 و18 و22–25 و27–38 و40–48 بحسب المجال.  
**نوع العمل:** تدقيق مواصفات فقط؛ لا تعديل كود ولا تشغيل اختبارات المنتج.

## 1. النتيجة التنفيذية

تمت مراجعة المراجع المتاحة للمحاور الأربعة والعشرين الأخيرة. توجد مبادئ ومتطلبات ومراكز وخدمات ذات صلة، كما توجد مواد مستعادة للفصول 141 و158–163، ونماذج أولية للبيانات والعقود والخدمات. لكن عدة مجالات لا تزال تحتوي قرارات مفتوحة أو عقوداً مشتقة، وبعضها يتطلب استعادة مصدر إضافي أو اعتماد مالك أو قياساً تنفيذياً لا يمكن استخلاصه من الوثائق وحدها.

**نتيجة الدفعة:** 24/24 محور تمت مراجعة مراجعها؛ 0 محاور أُغلقت تنفيذياً؛ 0 نتائج اختبار منفذة مثبتة في هذه الجولة.

## 2. سجل المحاور

### AX-041 — محرك قواعد العمل والمنطق البرمجي
- **المراجع:** 02/C02، 03/محركات التصميم والتنفيذ، 12/C05، 20/C05، وقرار محرر الإجراءات المرئي واللغة الطبيعية.
- **المتاح:** بناء سلوك العناصر بالطريقتين المرئية واللغوية؛ triggers/actions/targets وحالات المكونات؛ مراجعة منطق التنفيذ واختباره قبل الاعتماد؛ محرك البرمجة مستقل عن Angular.
- **الناقص:** workflow/behavior DSL أو IR schema، أنواع الإجراءات والأحداث، typing والتحقق، إدارة الحالة والآثار الجانبية، sandbox وقيود الموارد والشبكة، الصلاحيات، idempotency والتعويض، اختبار المنطق.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** تجربة بناء المنطق معتمدة من حيث المبدأ؛ محرك التنفيذ غير مكتمل المواصفات.

### AX-042 — إدارة البيانات والتحليلات
- **المراجع:** 13؛ 19/C04؛ 24/ملكية البيانات؛ 25/عقود المراكز؛ 21/C14.
- **المتاح:** مصمم البيانات والعلاقات والقيود؛ مصدر حقيقة ومالك لكل كيان؛ مؤشرات وتجميعات من المراكز المالكة؛ C14 للعرض لا للملكية.
- **الناقص:** قاموس بيانات نهائي، data lineage، تعريف metrics، طبقة تحليلات وصلاحياتها، سياسات إخفاء/تجميع، تصنيف واحتفاظ، عقود ingestion/aggregation، اختبارات عدم تسريب tenant.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA=RECOVERED-PROPOSED-MODEL؛ API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** الملكية المنطقية معروفة جزئياً؛ طبقة التحليلات وعقودها تحتاج اعتماداً.

### AX-043 — إدارة دورة حياة التطبيقات بعد الإطلاق
- **المراجع:** 01/رحلة المنتج؛ 19/C11؛ 25/C11؛ 27/حوكمة العقود؛ 03/بيئات التنفيذ.
- **المتاح:** إصدار ونشر ومراقبة؛ بيئات منفصلة؛ موافقات مستقلة للنشر؛ سجل إصدار وتراجع؛ مراقبة بعد الإطلاق.
- **الناقص:** دورة lifecycle لكل منصة، قنوات release، إدارة التحديثات والتوافق، سياسات الدعم والصيانة، rollback آمن مع migrations، incident-to-release flow، تقارير ما بعد النشر.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** القدرة العامة موجودة؛ lifecycle contracts لكل منصة غير مكتملة.

### AX-044 — الاختبارات الآلية الشاملة
- **المراجع:** 12/C07؛ 20/C07؛ 25؛ 27؛ 31/اختبارات الوثائق.
- **المتاح:** unit/integration/UI/E2E/API/database/security/performance/compatibility/accessibility/offline-sync/regression؛ تتبع المتطلبات؛ تقارير جودة وبوابات.
- **الناقص:** النص الأصلي الكامل لحالات الاختبار، مصفوفة المنصات والبيئات، test data strategy، flaky test policy، evidence schema، thresholds والاستثناءات، CI workflows، ونتائج تشغيل موثقة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-RECOVERED؛ SCR=SOURCE-REFERENCE-FOUND؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** نطاق الاختبارات معروف، لكن التغطية التنفيذية غير مثبتة.

### AX-045 — مركز التعاون والتواصل
- **المراجع:** 12/C06؛ 20/C06؛ 13/الأدوار والعضويات؛ 24/المهام والأحداث؛ 22/C12.
- **المتاح:** فرق ووكلاء ومهام وتعيينات ومراجعات وتعارضات؛ عضويات وأدوار ومساحات عمل؛ إشعارات للأحداث والموافقات.
- **الناقص:** محادثات وتعليقات ومراجعات تعاونية، presence، locking/merge conflicts، مشاركة ملفات، mention/notifications، retention، moderation/abuse controls، صلاحيات الفريق، مزامنة فورية.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND (جزئي)؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DETAIL-PENDING؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التعاون يظهر ضمن وظائف الفريق والمهام، لكن تجربة التواصل كقدرة مستقلة غير مكتملة.

### AX-046 — فهم الصور والمستندات والتصميمات
- **المراجع:** 07/تحليل AI للأصول؛ 19/C08؛ 22/C12؛ 37/مبادئ تحليل المراجع؛ 01/المرفقات.
- **المتاح:** تحليل صور واجهات التطبيقات؛ PDF/Word/Excel/PowerPoint؛ المخططات والرسومات ولقطات الشاشة والفيديو؛ استخراج النصوص والجداول والبيانات؛ مقارنة التصميمات؛ تحويل الرسومات اليدوية؛ إظهار المصدر والثقة.
- **الناقص:** ingestion pipeline، OCR/layout schemas، video frame extraction، confidence/provenance schema، formats/size limits، prompt-injection isolation، human review، retention/deletion، benchmarks واختبارات دقة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-REFERENCE-FOUND + DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** النطاق الوظيفي واضح ومعتمد؛ محرك الفهم ومقاييسه وعقوده لم تُعتمد.

### AX-047 — إدارة التكاليف والموارد التقنية
- **المراجع:** 16/الفصل 141؛ 01/PRD-FR-025؛ 10؛ 12/C06-F10؛ 21/SET-F13؛ 23/SH-11.
- **المتاح:** تقديرات موارد وتكلفة؛ حدود وحصص وميزانيات؛ استخدام مزودي AI؛ 20 شاشة معروفة للفصل 141 وفق سجل الاستعادة؛ قياس استهلاك عند توفر المصدر.
- **الناقص:** قرارات الفصل 141 المقترحة التي لم تعتمد؛ usage units؛ مصادر billing؛ قياس فعلي؛ العملة والضرائب؛ allocation؛ تنبيهات وتجاوزات؛ التسعير والفوترة؛ reconciliation بين التقديري والفعلي.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=RECOVERED-SUMMARY / DECISIONS-PENDING؛ SCR=RECOVERED-SUMMARY؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** الحاجة لإدارة التكلفة معتمدة؛ نموذج التسعير والقرارات المالية التفصيلية لم تعتمد بالكامل.

### AX-048 — البحث الذكي الموحد
- **المراجع:** 22/C12؛ 23/SH-07؛ 24؛ 32/SH-16؛ 36.
- **المتاح:** بحث موحد في معرفة المشروع ومراجعه؛ فهرسة نصية/متجهية كخيار؛ إرجاع مصادر وثقة؛ احترام صلاحيات مصدر الحقيقة قبل عرض النتائج.
- **الناقص:** search schema، مصادر الفهرسة، ACL filtering قبل retrieval، embedding/model selection، reindex/versioning، ranking/evaluation، deletion propagation، عزل المؤسسات، query audit، سياسة استخدام المحتوى في سياق AI.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** قدرة البحث مطلوبة؛ تصميم محرك البحث واختبارات عزل الصلاحيات غير مكتملة.

### AX-049 — تعدد الأجهزة والمزامنة
- **المراجع:** 13/Offline & Sync؛ 08/C13؛ 24/المهام والإصدارات؛ 21/SET؛ 19.
- **المتاح:** تفضيلات وأجهزة وجلسات؛ مزامنة عند عودة الاتصال؛ منع تكرار الكتابة؛ حل تعارضات؛ تحقق صلاحيات المستخدم عند المزامنة.
- **الناقص:** device registry، sync protocol، local encryption/retention، conflict resolution per entity، revocation propagation، offline capabilities، ordering/version vectors، limits، اختبار فقد الشبكة وتعدد الأجهزة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=SOURCE-REFERENCE-FOUND (C13)؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** متطلبات المزامنة موجودة، لكن البروتوكول ومخطط التعارض غير معتمدين.

### AX-050 — تحديث المصمم نفسه
- **المراجع:** 08/C13-SCR-19/20؛ 19/C11؛ 03/البيئات؛ 25/العقود؛ 16/الفصل 163.
- **المتاح:** إعدادات وإصدارات وتغييرات وتشخيص؛ مفهوم التحديث والتراجع؛ حماية استمرارية المنصة.
- **الناقص:** updater architecture، قنوات إصدار، manifest/signature verification، staged rollout، compatibility/migration، rollback، update permissions، سياسة انقطاع الخدمة، اختبارات update/restore.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND (محدود)؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=SOURCE-REFERENCE-FOUND؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** إدارة الإصدارات موجودة؛ نظام تحديث المصمم ذاته يحتاج مواصفات مستقلة.

### AX-051 — إدارة الأخطاء والمشكلات والحوادث
- **المراجع:** 14/المراقبة والحوادث؛ 27/نموذج الأخطاء؛ 32/الخدمات؛ 16/الفصل 158؛ 19/C10/C14.
- **المتاح:** تصنيف الأخطاء، correlation IDs، retryability، incident alerts/escalation، إدارة الحوادث والتصعيد؛ C14 للعرض وليس مصدر الحقيقة.
- **الناقص:** severity taxonomy، incident lifecycle، ownership/SLA، runbooks، postmortem، ارتباط الحادث بالإصدارات والتغييرات، status communication، deduplication، retention، اختبارات محاكاة الحوادث.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** متطلبات الرصد والحوادث موجودة؛ نظام إدارة الحوادث غير موثق بالكامل.

### AX-052 — إدارة التراخيص والملكية الفكرية
- **المراجع:** 07/تراخيص الأصول؛ 12/C05-F08/F15؛ 19/C08/C09/C10/C11؛ 20؛ 16/الفصل 158.
- **المتاح:** حفظ مصدر الأصول وحقوقها؛ فحص تراخيص الاعتماديات؛ مراجعة تراخيص الكود والموصلات؛ إظهار مصدر الأصل.
- **الناقص:** license catalog، SBOM، attribution policy، license compatibility rules، contributor/IP ownership، third-party notices، patent/trademark handling، takedown process، سجل موافقات واستثناءات، اختبار build license gate.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التراخيص ضمن نطاق الجودة والأصول؛ الحوكمة القانونية التفصيلية لم تعتمد.

### AX-053 — إمكانية الوصول والشمول الرقمي
- **المراجع:** 03/الواجهة؛ 08/إعدادات اللغة والإتاحة؛ 19/C02؛ 37/بطاقة عنصر UI/UX؛ 12/C07.
- **المتاح:** قابلية الوصول كجزء من الواجهة؛ إعدادات لغة ومظهر وإتاحة؛ تسميات ولوحة مفاتيح وقارئات شاشة وتباين وسلوك متجاوب كحقول بطاقة UI.
- **الناقص:** معيار إصدار أول معتمد؛ نطاق WCAG 2.2 AA كهدف افتراضي عند الملاءمة لا كاجتياز مثبت؛ اختبارات آلية ويدوية؛ مصفوفة المنصات؛ معايير قارئات الشاشة والحركة والتركيز؛ تقارير استثناء.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=SOURCE-REFERENCE-FOUND؛ DATA/API=NOT-APPLICABLE/SCHEMA-LIMITED؛ AUTH=OWNER-REVIEW؛ TEST=TEST-NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** الإتاحة مطلوبة؛ لا يوجد دليل على تدقيق WCAG أو اجتياز المنصات.

### AX-054 — الخصوصية والبيانات الشخصية
- **المراجع:** 13/سلامة البيانات؛ 03/الأمن والخصوصية؛ 16/الفصل 158؛ 36؛ 19/C10؛ 22/C12.
- **المتاح:** تقليل البيانات، أغراض المعالجة، تصنيف واحتفاظ وحذف؛ حماية أسرار؛ عزل مؤسسات؛ عدم مشاركة بيانات حساسة مع AI دون سياسة وموافقة مناسبة.
- **الناقص:** data inventory، lawful basis/consent عند اللزوم، data subject workflows، retention schedules، DPIA/PIA، DPA للمزودين، residency/transfers، breach response، anonymization، سجل المعالجة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA=SCHEMA-MISSING؛ API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** المبادئ موجودة؛ برنامج خصوصية وامتثال متكامل غير مكتمل.

### AX-055 — التوسع والأداء وتحمل الضغط
- **المراجع:** 01/NFR؛ 03/المعمارية؛ 14/المراقبة والسعة؛ 10/الخط التقني المبدئي؛ 16/الفصل 158.
- **المتاح:** قياس P50/P90/P95/P99؛ Modular Monolith أولاً؛ مراقبة الأداء والسعة؛ مهام طويلة وعزل أعطال؛ محولات قابلة للتوسع.
- **الناقص:** workload model، أهداف SLO/latency/throughput، capacity planning، load/stress/soak tests، autoscaling policy، database scaling، cost/performance budgets، degradation strategy.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=NOT-APPLICABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** الأداء والتوسع مطلبان غير وظيفيين؛ لا توجد أهداف كمية أو نتائج قياس معتمدة.

### AX-056 — التوثيق القانوني والتنظيمي
- **المراجع:** 22/C12؛ 07/تراخيص الأصول؛ 16/الفصل 158؛ 31/وثيقة المواصفات؛ 38.
- **المتاح:** مركز معرفة وتوثيق، تصنيف مصادر وقرارات، سجل وثائق ومراجعة واعتماد، توثيق حقوق الاستخدام والتكاملات.
- **الناقص:** سجل تشريعات ينطبق على نطاق الخدمة، مسؤول امتثال، سياسة تحديث قانوني، mapping بين المتطلبات والضوابط، مراجعة قانونية متخصصة، اتفاقيات المستخدم والخصوصية، معايير إقليمية/دولية بحسب الأسواق.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND (عام)؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التوثيق القانوني قدرة مطلوبة؛ تحديد الالتزامات القانونية الفعلية يحتاج تحديد نطاق السوق ومراجعة مختصة.

### AX-057 — التخصيص والعلامة التجارية
- **المراجع:** 08/C13؛ 02/C02؛ 19/C02/C08؛ 21/SET-F05.
- **المتاح:** تخصيص Design System واللغات والمظهر؛ إعدادات المؤسسة ومساحة العمل والمشروع؛ هوية بصرية وأصول.
- **الناقص:** brand token schema، themes وwhite-label، domains/email templates، asset licensing، tenant-specific overrides، preview/version/rollback، اختبارات التباين والتوافق.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=SOURCE-REFERENCE-FOUND؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التخصيص وارد ضمن الإعدادات والتصميم؛ حزمة العلامة التجارية المؤسسية لم تُفصل بالكامل.

### AX-058 — تحليلات تجربة المستخدم والسلوك
- **المراجع:** 21/C14؛ 24/ملكية البيانات؛ 03/NFR؛ 13/الخصوصية؛ 22/C12.
- **المتاح:** لوحات وتحليلات ومؤشرات استخدام؛ C14 يجمع من المراكز المالكة؛ خصوصية وعزل مطلوبان.
- **الناقص:** event taxonomy، consent/privacy model، anonymization/pseudonymization، retention، funnel/cohort metrics، telemetry SDK، opt-out، data minimization، فصل analytics عن audit/security logs، اختبارات منع إعادة التعرف.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND (جزئي)؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=SOURCE-REFERENCE-FOUND (C14)؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التحليلات مطلوبة على مستوى المؤشرات؛ تحليلات سلوك المستخدم تحتاج قرار خصوصية وتصميم مستقل.

### AX-059 — سوق المكونات البرمجية وSDK
- **المراجع:** 03/المحولات والإضافات؛ 19/C09؛ 23/SH-13؛ 32؛ 16/الفصل 125.
- **المتاح:** كتالوج موصلات وإصدارات إضافات ومحولات؛ توافق وتراخيص وثغرات وتحديث وتراجع؛ مكونات مشتركة.
- **الناقص:** marketplace governance، ناشرون ومراجعة، signing/provenance، تقييمات/اعتمادات، سياسة دفع/تقاسم إيرادات إن وجدت، SDK versioning، compatibility certification، vulnerability response، uninstall/rollback.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND (جزئي)؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** نظام إضافات ومحولات ضمن المعمارية؛ سوق عام للمكونات وSDK لم يعتمد نطاقه التجاري والتشغيلي.

### AX-060 — الاستيراد الذكي وتحليل التطبيقات الموجودة
- **المراجع:** 01/PRD-FR-002؛ 07؛ 19/C01/C08؛ 12/C05؛ 38/ADR-024؛ 37.
- **المتاح:** وضع تحليل فقط دون تعديل أي ملف؛ تقرير للبنية والتقنيات والاعتماديات والواجهات والاختبارات والمخاطر؛ وضع تطوير وتعديل بعد موافقة، مع نسخة احتياطية وفروقات وتراجع.
- **الناقص:** parsers للغات والأطر، import manifest، dependency graph، AST/IR mapping، تحليل migration، source fingerprint، sandbox، coverage matrix، ضمان read-only تقنياً، واختبارات byte-for-byte لعدم تعديل المصدر.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-REFERENCE-FOUND؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=SOURCE-REFERENCE-FOUND / DETAIL-PENDING؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** القرار معتمد بوضوح؛ عقود الاستيراد والمحولات والاختبارات لم تُستكمل.

### AX-061 — تعدد المستخدمين والمؤسسات
- **المراجع:** 13؛ 24؛ 38/ADR-025؛ 36؛ 19/C10.
- **المتاح:** Multi-Tenant مع مساحات عمل وأعضاء وأدوار وعزل بيانات ومشاريع وملفات وموارد وذاكرة ومهام؛ إدارة عدة مؤسسات وفق الصلاحيات؛ نقل المشاريع محكوم.
- **الناقص:** tenant context enforcement في كل endpoint/worker/search/cache/storage/export/backup؛ RLS/constraints؛ cross-tenant test suite؛ إدارة نقل الملكية؛ حسابات متعددة المؤسسات؛ قياس الاستهلاك؛ سياسات حذف واحتفاظ لكل مؤسسة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-REFERENCE-FOUND؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA=RECOVERED-PROPOSED-SCHEMA؛ API=SCHEMA-MISSING؛ AUTH=SOURCE-REFERENCE-FOUND / IMPLEMENTATION-PENDING؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** القرار معتمد؛ العزل الفعلي لم يثبت باختبارات.

### AX-062 — التوسع المستقبلي وإضافة التقنيات
- **المراجع:** 03/المعمارية المعيارية والمحولات؛ 10/الخط التقني المبدئي؛ 16/الفصل 158؛ 37؛ 38.
- **المتاح:** Modular Architecture؛ واجهات موحدة وإصدارات وتوافق؛ إضافة لغات ومنصات وقواعد بيانات ومزودي AI وخدمات دون إضرار بالمشاريع القائمة.
- **الناقص:** plugin/adapter SDK، compatibility matrix، semantic versioning، deprecation policy، conformance tests، migration guarantees، extension permissions، upgrade safety.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=DERIVED-DRAFT-AVAILABLE؛ SCR=NOT-APPLICABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** التوسع المعياري معتمد من حيث المبدأ؛ عقود الإضافات والتوافق غير مكتملة.

### AX-063 — حوكمة الذكاء الاصطناعي والرقابة عليه
- **المراجع:** 01/أدوار الوكلاء وPRD-FR-013..020؛ 12/C06؛ 16/الفصل 158؛ 20؛ 23/SH-02/03/04؛ 37/استقلالية التنفيذ؛ 43/Zero Trust.
- **المتاح:** صلاحيات للوكلاء؛ موافقات؛ سجل تدقيق لا يستطيع الوكيل تعديله؛ عزل الذاكرة؛ مراجعة المخرجات؛ إيقاف الوكلاء؛ حدود الميزانية؛ منع تجاوز السياسات؛ كشف التعليمات الخبيثة في الملفات.
- **الناقص:** AI policy registry؛ threat model للـprompt injection/tool abuse؛ قياس المخاطر؛ سياسات بيانات المزود؛ red-team/evaluation suites؛ kill switch موحد؛ immutable audit proof؛ فصل مسؤوليات المشغل والموافق والمدقق؛ حسم تعارض Zero Trust SEC-REV-01 من مصدر الموافقة.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-REFERENCE-FOUND + DERIVED-DRAFT-AVAILABLE؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=SOURCE-REFERENCE-FOUND / MATRIX-INCOMPLETE؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** مبادئ الحوكمة الأساسية معتمدة؛ نموذج الضوابط والاختبارات والتعارض المفتوح يحتاج حسمًا.

### AX-064 — الاعتمادية واستمرارية التشغيل والتعافي من الكوارث
- **المراجع:** 14/ADR-028؛ 16/الفصل 163؛ 23/SH-14/15؛ 32/SH-14/15؛ 36؛ 38.
- **المتاح:** مراقبة صحة الخدمات؛ اكتشاف الأعطال؛ بدائل وتشغيل متدهور آمن؛ حماية الجلسات والمسودات ونقاط التحقق؛ نسخ احتياطي واستعادة معزولة؛ اختبار دوري؛ RTO/RPO بعد اعتماد القيم؛ فصل صلاحيات النسخ والحذف والاستعادة.
- **الناقص:** تصنيف الخدمات حسب الحرجة؛ RTO/RPO معتمدة لكل نطاق؛ خرائط الاعتماد؛ runbooks؛ خطط DR؛ اختبارات failover/restore؛ قياس نتائج الاستعادة؛ اتفاقيات مزودي الخدمة؛ خطة استمرارية بشرية وتشغيلية؛ أدلة نجاح.
- **الحالة:** REQ=SOURCE-REFERENCE-FOUND؛ FUN=SOURCE-REFERENCE-FOUND؛ SCR=DERIVED-DRAFT-AVAILABLE؛ DATA/API=SCHEMA-MISSING؛ AUTH=OWNER-REVIEW؛ TEST=TEST-SPECIFIED-PARTIALLY / NOT-EXECUTED؛ EVIDENCE=MISSING.
- **الحكم:** مبدأ الاستمرارية معتمد؛ لا توجد قيم RTO/RPO أو نتائج استعادة مثبتة.

## 3. ملخص الدفعة E

| مجموعة | المحاور | عدد المحاور | نتيجة المراجعة |
|---|---|---:|---|
| المنطق والبيانات ودورة الحياة والاختبار والتعاون والفهم | AX-041..046 | 6 | متطلبات موجودة؛ schemas ومحرك التنفيذ والاختبارات ناقصة |
| التكلفة والبحث والمزامنة والتحديث والحوادث والتراخيص | AX-047..052 | 6 | مواد مصدرية جزئية؛ قرارات/عقود/أدلة مفتوحة |
| الإتاحة والخصوصية والأداء والقانون والتخصيص والتحليلات | AX-053..058 | 6 | مبادئ عامة؛ أهداف وقياسات وسياسات امتثال ناقصة |
| السوق والاستيراد وMulti-Tenant والتوسع والحوكمة والتعافي | AX-059..064 | 6 | قرارات مبدئية/معتمدة؛ التنفيذ والعزل والاختبارات غير مثبتة |

**حصيلة قابلة للقياس:** 24 محوراً راجعت؛ 24 لها مراجع أو مبادئ ذات صلة؛ 0 محاور أُغلقت تنفيذياً؛ 0 نتائج اختبار منفذة مثبتة في هذه الجولة.

## 4. قرارات ومصادر لا يجوز استنتاجها

1. لا تعتمد تسعيراً أو فوترة أو قيماً مالية من الفصل 141 دون اعتماد قراراته المفتوحة.
2. لا تعتبر WCAG 2.2 AA أو أي معيار قانوني اجتيازاً مثبتاً؛ لا يوجد تقرير اختبار.
3. لا تفترض أن كل مزود AI أو منصة أو تقنية متاحة في الإصدار الأول.
4. لا تعتبر السوق/Marketplace أو ميزات الدفع أو تقاسم الإيرادات معتمدة.
5. لا تحسم تعارض Zero Trust المسجل في التقرير 43 دون مرجع موافقة صريح.
6. لا تخترع RTO/RPO أو SLA أو SLO أو ميزانيات.
7. لا تعتبر قرار Multi-Tenant دليلاً على تطبيق عزل فعلي.
8. لا تعتبر قرار تحليل التطبيقات read-only دليلاً على سلامة التنفيذ قبل اختبار عدم تغيير المصدر.
9. لا تعتبر وجود وثائق سياسات أو معرفات اختبار دليلاً على تشغيلها.
10. لا تعتبر التقرير 33/34 اختبار اختراق أو تشغيل CI؛ هما فحصان ساكنان يحددان عيوباً تحتاج معالجة واختباراً.

## 5. المطلوب لإغلاق الدفعة E

- استكمال schemas لمحرك القواعد والبيانات والتحليلات والبحث والمزامنة والتحديث.
- اعتماد أهداف الأداء والسعة وRTO/RPO وSLO/SLA.
- اعتماد إطار الخصوصية والامتثال القانوني بحسب الأسواق المستهدفة.
- استكمال سياسة التراخيص والـSBOM وMarketplace إن تقرر إدراجه في الإصدار الأول.
- تنفيذ اختبارات عزل Multi-Tenant والـAI Governance والـRead-only Import.
- إبقاء الفصل 157 فجوة مصدرية، واستكمال مطابقة DOCX الرئيسي المستخرج مع المحاور والحقول ذات الصلة.
- حسم SEC-REV-01 وفق دليل موافقة المستخدم.
- مراجعة مالكي المراكز والخدمات واعتماد الحدود والعقود.

## 6. أثر التقرير على حالة المشروع

- اكتملت مراجعة المحاور AX-001..AX-064 على خمس دفعات من ناحية مراجعة المراجع وتصنيف الأدلة والفجوات.
- **GAP-044 لا تُغلق تلقائياً**؛ لا تزال تحتاج مراجعة المالك، واستكمال الحقول والعقود والمخططات والاختبارات والأدلة لكل محور.
- الفصل 157 وSH-17..SH-20 لا تزال فجوة مصدر؛ لا تُنشأ من التخمين.
- DOCX الرئيسي استُخرج نصياً وفُهرست وسائطه وتحققت بصمته؛ لا يثبت ذلك اكتمال المطابقة التفصيلية لكل محور أو حقل.
- لا توجد نتائج اختبارات منتج أو تشغيل أو نشر مثبتة في هذه الجولة.
- حزمة المواصفات لا تزال مرشح مراجعة، وليست اعتماداً نهائياً أو تصريح بدء برمجة.

## 7. سجل التغييرات

- إضافة مراجعة تفصيلية للمحاور AX-041..AX-064.
- تسجيل الأدلة المصدرية والمواد المشتقة والقرارات المفتوحة.
- منع تحويل المبادئ العامة إلى نتائج اختبار أو موافقات غير موجودة.
