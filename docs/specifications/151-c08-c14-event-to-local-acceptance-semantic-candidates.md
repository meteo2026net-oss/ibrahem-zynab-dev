# 151 — مرشحات ربط أحداث C08–C14 بسيناريوهات القبول المحلية

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC EVENT-TEST CANDIDATES / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED.

## 1. الغرض

يستكمل هذا العمل فجوة سجل 100: جميع أحداث C08–C14 لديها Event IDs، لكن حقل Test IDs يشير إلى مجموعة عامة EVT-96-T01..12 ولا يحدد اختباراً خاصاً بكل حدث. أعددت مرشحات دلالية من أوصاف السيناريوهات المحلية في workpapers 126–131.

**هذه ليست روابط Event→Test exact ولا روابط معتمدة.** يجب على مالك الحدث والاختبار مراجعتها، وقد يحتاج الحدث أكثر من سيناريو أو سيناريو واحد إلى تغطية أحداث متعددة. لم أعدل سجل 100.

## 2. ملخص توزيع الأحداث

| المركز | أحداث في سجل 100 | مرشحات أُعدت | وضع المصدر |
|---|---:|---:|---|
| C08 | 11 | 11 | SOURCE-RECOVERED (ليس verbatim) |
| C09 | 10 | 10 | SOURCE-RECOVERED (ليس verbatim) |
| C10 | 6 | 6 | PROPOSED |
| C11 | 14 | 14 | SOURCE-RECOVERED دلالياً |
| C12 | 9 | 9 | SOURCE-RECOVERED دلالياً |
| C13 | 4 | 4 | PROPOSED |
| C14 | 1 | 1 | PROPOSED |
| **الإجمالي** | **55** | **55** | **0 exact approved links** |

## 3. جدول المرشحات

| Event ID | اسم الحدث في سجل 100 | سيناريوهات اختبار مرشحة | سبب الترشيح |
|---|---|---|
| EVT-C08-001 | `AssetRecordCreated` | C08-126-T01, C08-126-T04 | إنشاء سجل أصل مع رفض الأصول غير الصالحة أو غير المخولة |
| EVT-C08-002 | `AssetUploadRequested` | C08-126-T01, C08-126-T09, C08-126-T10 | طلب رفع مضبوط بالحجم وidempotency وتأكيد التخزين |
| EVT-C08-003 | `AssetVersionReady` | C08-126-T03, C08-126-T06, C08-126-T10 | نوع الملف والفحص وإصدار المشتق وإثبات التخزين |
| EVT-C08-004 | `AssetQuarantined` | C08-126-T02, C08-126-T03 | عزل الأصل ومنع الوصول عند الاشتباه |
| EVT-C08-005 | `AssetScanCompleted` | C08-126-T02, C08-126-T03, C08-126-T10 | نتيجة الفحص وحالة التخزين الآمنة |
| EVT-C08-006 | `AssetLinked` | C08-126-T04, C08-126-T05 | ربط الأصل ضمن النطاق والتحقق من ACL |
| EVT-C08-007 | `AssetLinkRevoked` | C08-126-T05 | سحب ACL وإبطال الوصول اللاحق |
| EVT-C08-008 | `AssetTransformationCompleted` | C08-126-T06, C08-126-T07 | إنشاء مشتق مع provenance ومنع تسرب metadata |
| EVT-C08-009 | `AssetArchived` | C08-126-T08 | الاحتفاظ وlegal hold؛ الاختبار محجوب حتى اعتماد السياسة |
| EVT-C08-010 | `AssetDeletionRequested` | C08-126-T08 | طلب الحذف مع تطبيق retention/legal hold؛ محجوب |
| EVT-C08-011 | `AssetDeleted` | C08-126-T08 | إثبات منع الحذف غير المسموح؛ محجوب |
| EVT-C09-001 | `IntegrationRegistered` | C09-126-T03, C09-126-T09 | حماية مراجع الأسرار وعزل النطاق عند التسجيل |
| EVT-C09-002 | `IntegrationAuthorizationStarted` | C09-126-T02, C09-126-T03 | OAuth state/redirect وسرية الرموز |
| EVT-C09-003 | `IntegrationConnected` | C09-126-T02, C09-126-T09 | إكمال التفويض ضمن tenant/scope الصحيح |
| EVT-C09-004 | `IntegrationConnectionFailed` | C09-126-T01, C09-126-T09 | رفض الوجهة غير المسموحة وعزل الاتصال |
| EVT-C09-005 | `IntegrationSyncStarted` | C09-126-T06, C09-126-T09 | منع الكتابة الخارجية دون الموافقة والعزل |
| EVT-C09-006 | `IntegrationSyncCompleted` | C09-126-T06, C09-126-T07 | مزامنة معتمدة مع الحفاظ على SoR |
| EVT-C09-007 | `IntegrationSyncFailed` | C09-126-T07, C09-126-T10 | إظهار التعارض والفشل دون تكرار أثر خارجي |
| EVT-C09-008 | `IntegrationPaused` | C09-126-T08 | إيقاف الأعمال المجدولة والجارية وفق دلالات معلنة |
| EVT-C09-009 | `IntegrationRevoked` | C09-126-T03, C09-126-T08 | إبطال التفويض وإيقاف الأعمال مع حماية الأسرار |
| EVT-C09-010 | `WebhookRejected` | C09-126-T04, C09-126-T05 | رفض التوقيع/التوقيت غير الصحيح ومنع replay |
| EVT-C10-001 | `PolicyVersionPublished` | C10-127-T05, C10-127-T10 | تثبيت نسخة السياسة وكشف threat model القديم |
| EVT-C10-002 | `SecurityFindingRegistered` | C10-127-T08, C10-127-T09, C10-127-T10 | دليل مستقل وحالة finding وحداثة التقييم |
| EVT-C10-003 | `ExceptionRequested` | C10-127-T01, C10-127-T06, C10-127-T11 | سياق actor/scope ومدة الاستثناء والغرض |
| EVT-C10-004 | `ExceptionDecided` | C10-127-T04, C10-127-T05, C10-127-T06, C10-127-T07 | فصل المهام وربط القرار بنسخة السياسة وسحب الاستثناء |
| EVT-C10-005 | `AgentCapabilitySuspended` | C10-127-T13, C10-127-T14 | إيقاف القدرات ومنع الوكيل من تعديل grants والسياسات |
| EVT-C10-006 | `IncidentRecorded` | C10-127-T15, C10-127-T16, C10-127-T17, C10-127-T18 | حفظ provenance وضبط الوصول وتصدير evidence وسلامة audit |
| EVT-C11-001 | `BuildRequested` | C11-AT-001, C11-AT-002, C11-AT-003, C11-AT-004, C11-AT-021 | تفويض المصدر وprofile وتثبيت commit ومنع التكرار |
| EVT-C11-002 | `BuildStarted` | C11-AT-003, C11-AT-004 | تثبيت مرجع المصدر وعدم تكرار المهمة |
| EVT-C11-003 | `BuildCompleted` | C11-AT-005, C11-AT-007, C11-AT-008 | عدم إعلان نجاح عند الفشل وربط artifact بالمصدر وprovenance |
| EVT-C11-004 | `BuildFailed` | C11-AT-005, C11-AT-018 | عدم إعلان النجاح وإظهار تعطل telemetry |
| EVT-C11-005 | `ArtifactAttested` | C11-AT-007, C11-AT-008 | مطابقة digest وprovenance والتوقيع |
| EVT-C11-006 | `ReleaseCandidateCreated` | C11-AT-009 | منع خلط artifact بين المستأجرين |
| EVT-C11-007 | `ReleaseApprovalRequested` | C11-AT-010, C11-AT-012 | اكتمال أدلة gate وربط الموافقة ببصمة المرشح |
| EVT-C11-008 | `ReleaseApproved` | C11-AT-012, C11-AT-013 | إبطال الموافقة عند تغير artifact وفصل الواجبات |
| EVT-C11-009 | `DeploymentStarted` | C11-AT-011, C11-AT-012, C11-AT-014 | منع النشر بلا موافقة والتحقق من promotion |
| EVT-C11-010 | `DeploymentCompleted` | C11-AT-015, C11-AT-018 | فحوص ما بعد النشر وصحة telemetry |
| EVT-C11-011 | `DeploymentFailed` | C11-AT-015, C11-AT-018 | فشل post-deploy لا يتحول إلى healthy |
| EVT-C11-012 | `RollbackRequested` | C11-AT-016, C11-AT-019 | خطة تراجع وشرط توفر backup/restore |
| EVT-C11-013 | `RollbackCompleted` | C11-AT-017, C11-AT-019 | لا إعلان إكمال دون evidence والتحقق من backup |
| EVT-C11-014 | `PostDeployCheckFailed` | C11-AT-015, C11-AT-018 | تسجيل فشل الصحة وعدم إخفاء telemetry |
| EVT-C12-001 | `KnowledgeDocumentCreated` | C12-AT-001, C12-AT-002, C12-AT-003 | owner/scope وprovenance ومصدر مخول |
| EVT-C12-002 | `DocumentVersionCreated` | C12-AT-005, C12-AT-006 | نسخة immutable وإبطال مراجعة عند التعديل |
| EVT-C12-003 | `DocumentReviewRequested` | C12-AT-006, C12-AT-007 | ربط المراجعة بالنسخة ومنع النشر دون اعتماد |
| EVT-C12-004 | `DocumentApproved` | C12-AT-007, C12-AT-008 | قرار صالح ومطابقة hash |
| EVT-C12-005 | `DocumentPublished` | C12-AT-007, C12-AT-008 | نشر بعد الموافقة وتثبيت النسخة |
| EVT-C12-006 | `KnowledgeIndexUpdated` | C12-AT-009, C12-AT-013, C12-AT-014, C12-AT-022 | إبطال ACL وتحديث الفهرس وحداثته وlineage |
| EVT-C12-007 | `KnowledgeAccessRevoked` | C12-AT-009, C12-AT-010, C12-AT-022 | منع retrieval عبر cache/chunks/embeddings |
| EVT-C12-008 | `DocumentationDriftDetected` | C12-AT-014, C12-AT-021 | كشف drift وحداثة المصدر ونسخه |
| EVT-C12-009 | `DocumentArchived` | C12-AT-012, C12-AT-019 | الحجز القانوني والاحتفاظ ومنع الأرشفة المخالفة |
| EVT-C13-001 | `SettingChangeRequested` | C13-AT-004, C13-AT-016 | سياق موافقة ومالك وimpact classification |
| EVT-C13-002 | `SettingChangeDecided` | C13-AT-004, C13-AT-005, C13-AT-016 | القرار مربوط بالقيمة والنسخة ومالك المفتاح |
| EVT-C13-003 | `SettingApplied` | C13-AT-009, C13-AT-017, C13-AT-018 | تطبيق عبر API المالك وعدم إصدار event عند الفشل ومعالجة التكرار |
| EVT-C13-004 | `SettingVersionRolledBack` | C13-AT-007, C13-AT-020 | منع rollback غير الآمن وحفظ التاريخ |
| EVT-C14-001 | `DashboardProjectionRefreshed` | C14-AT-002, C14-AT-003, C14-AT-004, C14-AT-005, C14-AT-006, C14-AT-010, C14-AT-013, C14-AT-014, C14-AT-018, C14-AT-019, C14-AT-022 | تحديث projection مع ACL وfreshness وsource lineage وidempotency |

## 4. تحذيرات خاصة

- **C08 AssetArchived / AssetDeletionRequested / AssetDeleted:** المرشح C08-126-T08 حالته BLOCKED لأن retention/legal-hold policy لم تعتمد. لا يستخدم لإثبات سلامة الحذف قبل اعتماد السياسة.
- **C10:** الأحداث الستة PROPOSED وليست مستعادة من مصدر حرفي؛ يجب أولاً اعتماد أسماء الأحداث وproducer وDomain SoR.
- **C11–C12:** السيناريوهات محلية مشتقة وليست Test IDs أصلية من المحادثة المصدرية؛ لا يثبت ذلك أن أسماء الأحداث verbatim.
- **C13–C14:** الأحداث نفسها PROPOSED. مرشحات الاختبار لا تحولها إلى أحداث معتمدة.
- كل سيناريوهات القبول في هذه الحزم SPECIFIED / NOT RUN؛ لا توجد أدلة تنفيذ أو نتائج PASS مستنتجة.

## 5. متطلبات اعتماد كل ربط

1. موافقة مالك المجال على اسم الحدث وسبب إصداره ومصدر الحقيقة.
2. تحديد producer واحد مسؤول عن نشر الحدث، مع توثيق دور SH-06/08/13/14/15/16 كخدمات مساعدة دون نقل Domain SoR إليها.
3. تثبيت schema/version وtenant scope وaggregate/resource reference وoccurred_at وcorrelation/causation IDs وidempotency key وclassification.
4. تحديد delivery semantics، ordering، retry/dead-letter، retention، replay، وACL.
5. ربط سيناريو القبول بنتيجة متوقعة تغطي publish/consume/duplicate/out-of-order/failure/security حيث ينطبق.
6. تشغيل الاختبار وحفظ evidence artifact؛ لا تتحول الحالة إلى PASS دون ذلك.

## 6. أثر الوثيقة على خط الأساس

- يبقى 0/55 Exact Event→Test في سجل 100.
- تضاف 55 مرشحاً دلالياً لمراجعة الملاك فقط.
- تبقى حقول Qualified OWN وDomain SoR للأحداث TBD وفق تدقيق 148.
- لا تتغير أعداد 201 عملية / 106 أحداث / 26 موافقة.
- لا تتغير موافقات C08–C14 الـ14 عن OWNER REVIEW.

## 7. الحكم

**55 EVENT-TEST SEMANTIC CANDIDATES PREPARED; 0 EXACT APPROVED LINKS; EXECUTION NOT VERIFIED.**  
هذه الوثيقة تحول فجوة عامة إلى قائمة مرشحات قابلة للمراجعة، ولا تعتمد أسماء أو عقود أحداث نيابة عن الملاك. لا تعديل على كود التطبيق أو قاعدة البيانات أو APIs، ولا موافقة بدء برمجة.
