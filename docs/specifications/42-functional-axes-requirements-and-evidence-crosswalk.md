# 42 — مصفوفة ربط المحاور الـ64 بالمتطلبات والأدلة

**الحالة:** TRACEABILITY-EXPANSION / SOURCE-BOUNDED / OWNER-REVIEW-PENDING  
**المدخل الأساسي:** الوثيقة 40 والقائمة الأصلية المستعادة من «فحص المستودع وخطة العمل (5).html»، القسم 4.  
**الغرض:** تحويل كل محور إلى سجل تتبع منضبط يوضح ما يمكن إسناده إلى كتالوجات المواصفات الحالية وما لا يتوفر له دليل كافٍ بعد.  
**قاعدة الإثبات:** الإشارة إلى ملف مصدر أو كتالوج مركز لا تعني أن كل متطلب أو شاشة أو عقد داخله يغطي المحور بالكامل. لا تُنشأ معرفات REQ/FUN/SCR/DATA/API/AUTH/TEST افتراضية، ولا تُستنتج مخططات أو صلاحيات أو نتائج اختبار من عنوان المحور.

## 1. النتيجة

- تمت قراءة 64 صفاً من المصفوفة 40 والتحقق من العدد.
- تمت مراجعة فهارس ومحتويات الوثائق 18 و23 و25 و27 و30 و31 و32 و36.
- كتالوجات المراكز 19–22 والعقود 23–27 و32 ومصفوفة الملكية 36 توفر نقاط دخول للمطابقة، لكنها لا تقدم وحدها دليلاً كاملاً لكل محور.
- لذلك تسجل هذه الوثيقة المراجع المحتملة حسب المالك المقترح، وتبقي مستويات التتبع التفصيلية `SOURCE_REQUIRED` إلى أن تتم المطابقة الفعلية مع نصوص الوظائف والشاشات والكيانات والعقود.
- لا تغلق هذه الوثيقة GAP-044، ولا تعتمد ملكية المراكز أو الخدمات.

## 2. قاموس حالات التتبع

| الحالة | المعنى |
|---|---|
| SOURCE-RECOVERED | اسم المحور مستعاد من المصدر المرجعي |
| CATALOG-REFERENCE | توجد وثيقة كتالوج/مركز مرجعية محتملة للمقارنة |
| SOURCE_REQUIRED | لم يثبت بعد عنصر تفصيلي محدد يحقق هذا المحور |
| OWNER-REVIEW | تعيين المالك أو حدود المسؤولية يحتاج اعتماداً |
| NOT-TESTED | لا يوجد دليل اختبار منفذ مرتبط بالمحور في هذه المراجعة |

## 3. سجل التتبع لكل محور

الأعمدة REQ/FUN/SCR/DATA/API/AUTH/TEST/EVIDENCE أدناه تمثل الحد الأدنى المطلوب استخراجه من المصادر. ظهور `SOURCE_REQUIRED` يعني أن المعرفات أو التفاصيل لم تثبت، وليس أنها غير موجودة حتماً في كامل المشروع.

| المعرف | المحور الأصلي | المالك المقترح في 40 | مراجع الكتالوج/الخدمات للمراجعة | REQ | FUN | SCR/UI | DATA | API/EVT | AUTH | TEST | EVIDENCE |
|---|---|---|---|---|---|---|---|---|---|---|---|
| AX-001 | الرؤية العامة للمصمم | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-002 | التصميم قبل البرمجة | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-003 | دعم جميع المنصات | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-004 | الحوار وتحليل المتطلبات | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-005 | استقلالية التنفيذ والموافقات | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-006 | تعديل التصميم مباشرة | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-007 | إدارة المكونات | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-008 | مدير الصفحات وخريطة التنقل | C03 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-009 | إنشاء الصفحات بالذكاء الاصطناعي والإنشاء اليدوي والدمج | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-010 | بناء وظائف العناصر | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-011 | إدارة البيانات وقواعد البيانات | C04 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [24](./24-data-ownership-and-unified-logical-model.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-012 | تسجيل الدخول والمستخدمون والصلاحيات | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-013 | الإشعارات | C08 | [07](./07-notifications-and-media.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-014 | الملفات والوسائط | C08 | [07](./07-notifications-and-media.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-015 | الاختبار وضمان الجودة | C07 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-016 | البناء والنشر | C11 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-017 | فريق الذكاء الاصطناعي متعدد التخصصات | C06 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-018 | تعدد مزودي ونماذج الذكاء الاصطناعي | C06 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-019 | العمل الفردي والجماعي | C06 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-020 | القوالب | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-021 | مركز المعرفة والتوثيق | C12 | [22](./22-operational-contracts-c12-knowledge.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-022 | تكامل GitHub | C09 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [25](./25-inter-center-api-contracts.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-023 | الأمان | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-024 | واجهة إدارة المصمم نفسه | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-025 | رحلة إنشاء المشروع | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-026 | حفظ العمل وإدارة الإصدارات | C05 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-027 | المعاينة وبيئات الاختبار | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-028 | توليد الكود والتحكم البرمجي | C05 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-029 | نظام الإضافات | C09 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [25](./25-inter-center-api-contracts.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-030 | إدارة المشاريع المتعددة | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-031 | المراقبة والتشخيص الذكي | SHARED-L9/L10 | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-032 | المساعدة والتعلم | C12 | [22](./22-operational-contracts-c12-knowledge.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-033 | التسعير وإدارة التكاليف | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-034 | اللغات والتعريب | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-035 | ذكاء التصميم UI/UX | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-036 | إدارة سير العمل والمهام | C06 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-037 | النسخ الاحتياطي والاستعادة | SHARED-L9/L10 | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-038 | الاستيراد والتصدير ونقل المشاريع | C08 | [07](./07-notifications-and-media.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-039 | جودة الكود والمعمارية | C05 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-040 | إدارة APIs والتكاملات الخارجية | C09 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [25](./25-inter-center-api-contracts.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-041 | محرك قواعد العمل والمنطق البرمجي | C05 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-042 | إدارة البيانات والتحليلات | C04 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [24](./24-data-ownership-and-unified-logical-model.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-043 | إدارة دورة حياة التطبيقات بعد الإطلاق | C11 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-044 | الاختبارات الآلية الشاملة | C07 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-045 | مركز التعاون والتواصل | C06 | [12](./12-centers-05-07-function-catalog.md)، [20](./20-expanded-function-contracts-c05-c07.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-046 | فهم الصور والمستندات والتصميمات | C08 | [07](./07-notifications-and-media.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-047 | إدارة التكاليف والموارد التقنية | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-048 | البحث الذكي الموحد | C12 | [22](./22-operational-contracts-c12-knowledge.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-049 | تعدد الأجهزة والمزامنة | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-050 | تحديث المصمم نفسه | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-051 | إدارة الأخطاء والمشكلات والحوادث | SHARED-L9/L10 | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-052 | إدارة التراخيص والملكية الفكرية | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-053 | إمكانية الوصول والشمول الرقمي | C02 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-054 | الخصوصية والبيانات الشخصية | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-055 | التوسع والأداء وتحمل الضغط | SHARED-L9/L10 | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-056 | التوثيق القانوني والتنظيمي | C12 | [22](./22-operational-contracts-c12-knowledge.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-057 | التخصيص والعلامة التجارية | C13 | [08](./08-center-13-settings.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-058 | تحليلات تجربة المستخدم والسلوك | C14 | [09](./09-center-14-dashboard.md)، [21](./21-operational-contracts-c13-c14.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-059 | سوق المكونات البرمجية وSDK | C09 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [25](./25-inter-center-api-contracts.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-060 | الاستيراد الذكي وتحليل التطبيقات الموجودة | C01 | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-061 | تعدد المستخدمين والمؤسسات | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-062 | التوسع المستقبلي وإضافة التقنيات | C09 | [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [25](./25-inter-center-api-contracts.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-063 | حوكمة الذكاء الاصطناعي والرقابة عليه | C10 | [13](./13-authentication-and-data-requirements.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |
| AX-064 | الاعتمادية واستمرارية التشغيل والتعافي من الكوارث | SHARED-L9/L10 | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | SOURCE_REQUIRED | NOT-TESTED | SOURCE_REQUIRED |

## 4. مراجع العرض العام للمطابقة

| المجال | المراجع المتاحة | ما تثبته | ما لا تثبته |
|---|---|---|---|
| تعريف المراكز والشاشات | [02](./02-centers-and-screens.md)، [19](./19-operational-function-catalogs-c01-c04-c08-c11.md)، [20](./20-expanded-function-contracts-c05-c07.md)، [21](./21-operational-contracts-c13-c14.md)، [22](./22-operational-contracts-c12-knowledge.md) | وجود خرائط مراكز وكتالوجات تشغيل أولية | تغطية نهائية لكل محور أو اعتماد كل تفاصيل الحقول والأزرار |
| ملكية البيانات | [13](./13-authentication-and-data-requirements.md)، [24](./24-data-ownership-and-unified-logical-model.md)، [36](./36-data-and-shared-service-ownership-matrix.md) | قواعد وكيانات وملكية مقترحة/مستعادة جزئياً | ERD نهائي معتمد وربط كل محور بكل حقل ومالك مصدر حقيقة |
| الخدمات المشتركة | [14](./14-shared-platform-services.md)، [18](./18-recovered-chapters-143-151-152-and-shared-services.md)، [23](./23-shared-services-contracts-sh01-sh16.md)، [32](./32-detailed-shared-service-contracts-sh09-sh16.md) | القائمة المثبتة جزئياً SH-01..SH-16 وبطاقات أولية | أن القائمة نهائية أو أن الفصل 157 استعيد أو أن العقود جاهزة للتنفيذ |
| عقود الاتصال والحوكمة | [25](./25-inter-center-api-contracts.md)، [27](./27-recovered-chapter-155-api-contract-governance.md) | مصفوفة تفاعلات ونموذج حوكمة للعقد | schemas فعلية شاملة وواجهات منشورة واختبارات توافق منفذة |
| مطابقة بنية المواصفات | [30](./30-crosswalk-chapters-138-154-139-155-and-149-162.md)، [31](./31-mprts-149-162-coverage-identifiers-and-document-tests.md) | الفروقات الهيكلية المعروفة بين 149 و162 وحالة الفصل 157 | إغلاق كل اختلاف أو اجتياز DOC-TEST |
| ملكية التشغيل المشتركة | [36](./36-data-and-shared-service-ownership-matrix.md) والوثيقة [40](./40-functional-axes-64-source-and-traceability-matrix.md) | الفصل المقترح بين مالك المجال ومالك الخدمة المشتركة | اعتماد OWN-01..OWN-10 أو أرقام SLA/RTO/RPO |

## 5. نتيجة فحص الخدمات المشتركة والفصل 157

1. الفصل 132.6 هو المصدر المثبت لقائمة SH-01..SH-16 في الوثيقة 23.
2. SH-01..SH-08 موصوفة في الوثيقة 23 بعقود أولية أكثر تفصيلاً.
3. SH-09..SH-16 موسعة في الوثيقة 32، وتظل بطاقات مقترحة تحتاج اعتماداً ومخططات وواجهات ومستهلكين وصلاحيات واختبارات.
4. لا يثبت المصدر المتاح أسماء SH-17..SH-20. لا يجوز اختراعها أو افتراض وجودها.
5. الفصل 157 مطلوب في سياق الفصل 155.13 لكنه لم يُستعد كنص تفصيلي في ملفات المصدر المفحوصة. يظل **SOURCE GAP / OPEN**.
6. الوثيقة 36 تجعل ملكية الخدمات والبيانات حدوداً مقترحة، وتطلب مراجعة OWN-01..OWN-10؛ لا تسجل ملكية نهائية.

## 6. نتيجة مطابقة 149 و162

تعتمد هذه الوثيقة نتيجة الوثيقتين 30 و31 بوصفها أساساً موثقاً للمراجعة، ولا تعيد اختراع جدول الأجزاء. النتيجة الحالية:
- المقارنة الهيكلية بين 24 جزءاً في 149 و22 جزءاً في 162 موثقة، مع عناصر 149 التي يجب ألا تسقط.
- تصحيح اسم/دور C14 موثق كاختلاف يجب تسويته في المرجع الموحد.
- قاموس المعرفات واختبارات الوثيقة DOC-01..14 وDOC-TEST-01..12 موثقة، لكن تشغيل الاختبارات غير مثبت.
- لا يصدر MPRTS 1.0 قبل إغلاق الفجوات الحرجة ومراجعة المالك.

## 7. بوابة الإغلاق التالية لـGAP-044

لكل صف في القسم 3، يجب أن يضاف سجل تفصيلي قابل للتدقيق يتضمن:
1. نص المتطلب الذري ومصدره ورقم الفصل/الفقرة.
2. وظيفة واحدة ومالكها، مع مدخلاتها ومخرجاتها وحالات فشلها.
3. الشاشة والعنصر المحدد وسلوك كل حقل وزر وحالات التحميل/الفراغ/الخطأ/النجاح.
4. الكيانات والحقول والعلاقات ومالك مصدر الحقيقة ونطاق المؤسسة.
5. عقد API أو الحدث مع request/response schema والأخطاء والمهل وإعادة المحاولة وidempotency والإصدار.
6. الأدوار والصلاحيات والقرارات المطلوبة وسجل التدقيق.
7. معايير قبول واختبارات إيجابية وسلبية، ثم نتيجة تشغيل ودليل عند التنفيذ.
8. مراجعة مالك المركز والمالكين المشتركين، وإزالة التكرار والتعارض.

**قاعدة عدم التخمين:** عند غياب النص أو المعرف أو القرار، تسجل القيمة `SOURCE_REQUIRED` أو `PENDING_OWNER`، مع تحديد المصدر المطلوب. لا تُملأ الفجوة بتصميم جديد إلا بوصفه اقتراحاً منفصلاً ينتظر اعتماد المستخدم.

## 8. الأعمال المفتوحة بعد هذه الإضافة

- GAP-044: لم يغلق؛ الوثيقة 42 تحدد نقطة تتبع موحدة لكل محور لكنها لا تستبدل المطابقة التفصيلية.
- GAP-046: عقود التكامل والموصلات تحتاج schemas ومستهلكين وصلاحيات واختبارات.
- GAP-047: ذاكرة المشروع تحتاج ربطاً تفصيلياً بالكيانات والسياسات وسلوك الاسترجاع.
- GAP-048: حدود التكلفة والميزانيات والعملات والمصادر تحتاج قرارات صريحة.
- GAP-049: المهام الطويلة والاستئناف وcheckpoints تحتاج عقوداً واختبارات.
- GAP-050: محولات المنصات والتقنيات تحتاج مصفوفة توافق واختبارات فعلية.
- الفصل 157: مصدر تفصيلي غير مستعاد.
- الفصلان 149 و162: المطابقة موثقة، لكن الاعتماد النهائي واختبارات الوثيقة غير مثبتين.
- لا يوجد دليل هنا على إنشاء حزمة PDF/DOCX/XLSX المطلوبة أو تشغيل اختبارات DOC.

## 9. حدود هذه الوثيقة

هذه إضافة توثيقية فقط. لا تعدّل التطبيق أو إعداداته أو قاعدة بياناته، ولا تنشئ APIs أو مخططات تنفيذية، ولا تدعي تنفيذ اختبارات. يجب أن يراجعها مالكو المراكز ويكملوا معرفات التتبع من النصوص الأصلية قبل اعتماد مواصفات الإصدار 1.0.

**الحالة النهائية:** TRACEABILITY-EXPANSION / NOT-APPROVED / NOT-READY-FOR-CODING.
