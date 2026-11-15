# 154 — مصفوفة مطابقة الشاشات التاريخية C08–C14 مع العمليات الحالية

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC CANDIDATE CROSSWALK / NOT EXACT / OWNER REVIEW REQUIRED / NOT APPROVED.

## 1. الهدف والمنهج

تربط هذه الوثيقة عناوين الشاشات المستعادة في 153 بأسماء العمليات المنطقية في العقود 64–70، وبمعرفات العمليات في سجل 97. المطابقة مبنية على معنى الوظيفة الظاهر من عنوان الشاشة ووصف العملية، وليست نصاً حرفياً من المصدر. لذلك كل الروابط أدناه **SEMANTIC CANDIDATE**.

لا يوجد افتراض 1:1: الشاشة الواحدة قد تستدعي عدة عمليات، والعملية قد تخدم عدة شاشات. بعض الشاشات حاويات تنقل/عرض وليست مالكة لوظائف المجال.

## 2. خريطة العمليات الرسمية المرشحة في سجل 97

| المركز | عدد العمليات | مرجع الملكية المرشح المسجل | العقد | نطاق معرفات العمليات |
|---|---:|---|---:|---|
| C08 | 11 | OWN72-01 | 64 | OP-C08-121..131 |
| C09 | 12 | OWN72-02 | 65 | OP-C09-132..143 |
| C10 | 11 | OWN72-03 | 66 | OP-C10-144..154 |
| C11 | 12 | OWN72-06 / OWN36-06 | 67 | OP-C11-155..166 |
| C12 | 12 | OWN72-04 | 68 | OP-C12-167..178 |
| C13 | 10 | OWN72-05 | 69 | OP-C13-179..188 |
| C14 | 13 | OWN36-10 / OWN72-09 | 70 | OP-C14-189..201 |

كل صف في سجل 97 لا يزال يحمل EXACT TEST ID TBD / OWNER REVIEW. وجود OWN reference في هذا السجل لا يثبت أن المالك أو Domain SoR قد اعتُمد نهائياً لكل عملية؛ راجع حالات القرار في 72 و121.

## 3. C10 — وحدات الفصل 29 وربطها بالعمليات

| الوحدة الوظيفية التاريخية | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| إدارة الهوية والمصادقة | `evaluatePolicy` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الأدوار والصلاحيات | `evaluatePolicy` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مصفوفة الصلاحيات | `evaluatePolicy` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مدير الأسرار المركزي | `rotateSecretReference (C09) / secret service boundary review` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التشفير وحماية البيانات | `assessPrivacyImpact; createThreatModel` | SEMANTIC CANDIDATE — OWNER REVIEW |
| عزل المؤسسات والمشاريع | `evaluatePolicy; assessPrivacyImpact` | SEMANTIC CANDIDATE — OWNER REVIEW |
| حوكمة وكلاء AI | `suspendAgentCapability; reviewAgentRun` | SEMANTIC CANDIDATE — OWNER REVIEW |
| أمن الإضافات والمحولات | `createThreatModel; registerSecurityFinding` | SEMANTIC CANDIDATE — OWNER REVIEW |
| سجل التدقيق المركزي | `exportAuditEvidence; recordIncident` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة المخاطر والحوادث | `recordIncident; registerSecurityFinding; closeFinding` | SEMANTIC CANDIDATE — OWNER REVIEW |

ملاحظة: قسم «الشاشة الرئيسية» في الفصل 29 واجهة تجميع؛ لم أضفه كعملية مستقلة. «نموذج البيانات» و«اختبارات القبول» ليسا شاشتين. خزنة الأسرار تحتاج قراراً صريحاً لحد C09/SH-07/SH-13 ولا يجوز نقل ملكية الأسرار إلى C10 لمجرد ظهورها في الشاشة.

## 4. C11 — وحدات الفصل 30 وربطها بالعمليات

| الوحدة الوظيفية التاريخية | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| المنصات المستهدفة | `validateBuildProfile` | SEMANTIC CANDIDATE — OWNER REVIEW |
| دورة البناء | `requestBuild; getBuildResult` | SEMANTIC CANDIDATE — OWNER REVIEW |
| البيئات المنفصلة | `promoteEnvironment; getReleaseStatus` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مدير الإصدارات | `createReleaseCandidate; submitReleaseForApproval; approveRelease` | SEMANTIC CANDIDATE — OWNER REVIEW |
| النشر | `deployRelease; runPostDeployChecks` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التراجع عن الإصدار | `requestRollback; getReleaseStatus` | SEMANTIC CANDIDATE — OWNER REVIEW |

الشاشة الرئيسية وبيانات المركز واختباراته ليست عمليات مجال إضافية. يجب حسم اعتماديات C11 على SH-08/14/15 وملكية lifecycle قبل اعتماد الربط.

## 5. مطابقة عناوين الشاشات مع عمليات العقود 64–70

## C08 — الفصل 93

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| لوحة الملفات الرئيسية | `createAssetRecord; inspectAsset; requestExport` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مستكشف المجلدات والتصنيفات | `inspectAsset; linkAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| رفع الملفات واستيرادها | `requestUpload; finalizeUpload` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مكتبة الصور | `createAssetRecord; inspectAsset; createAssetVersion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مكتبة الفيديو والصوت | `createAssetRecord; inspectAsset; createAssetVersion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الأيقونات والخطوط | `createAssetRecord; inspectAsset; requestTransformation` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة ملفات التصميم | `createAssetRecord; createAssetVersion; linkAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة المستندات | `createAssetRecord; createAssetVersion; linkAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز تحليل الصور والمستندات بالذكاء الاصطناعي | `requestTransformation; inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| تحرير الصور والوسائط | `requestTransformation; createAssetVersion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الأصول المرتبطة بعناصر التصميم والكود | `linkAsset; revokeAssetLink; inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الإصدارات والتاريخ | `createAssetVersion; inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| كشف الملفات المكررة وغير المستخدمة | `inspectAsset; requestDeletion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة التخزين المحلي والسحابي | `requestUpload; finalizeUpload; inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| صلاحيات الوصول والمشاركة | `linkAsset; revokeAssetLink` | SEMANTIC CANDIDATE — OWNER REVIEW |
| ضغط وتحويل الملفات | `requestTransformation; createAssetVersion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| البحث والفهرسة الذكية | `inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التراخيص وحقوق استخدام الأصول | `inspectAsset; requestExport; requestDeletion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| النسخ الاحتياطي والاستعادة | `requestExport; inspectAsset` | SEMANTIC CANDIDATE — OWNER REVIEW |
| المراجعة والاعتماد | `requestDeletion; archiveAsset; requestExport` | SEMANTIC CANDIDATE — OWNER REVIEW |

## C09 — الفصل 102

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| لوحة التكاملات الرئيسية | `getIntegrationStatus; registerIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مكتبة مزودي الخدمات | `registerIntegration; validateConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إضافة تكامل جديد | `registerIntegration; configureIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز إدارة واجهات API | `configureIntegration; testMapping` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مدير المصادقة وطرق الاتصال | `beginAuthorization; completeAuthorization; validateConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| خزنة الأسرار ومفاتيح API | `rotateSecretReference; configureIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| ربط التكاملات بمشاريع التطبيقات | `registerIntegration; configureIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| محرر تدفق البيانات | `configureIntegration; testMapping; startSync` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز Webhooks والأحداث | `receiveWebhook` | SEMANTIC CANDIDATE — OWNER REVIEW |
| محرر التكاملات المرئي | `configureIntegration; testMapping` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الهوية وتسجيل الدخول الخارجي | `beginAuthorization; completeAuthorization; revokeConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الدفع والفوترة | `registerIntegration; configureIntegration; startSync` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الإشعارات والرسائل | `registerIntegration; configureIntegration; startSync` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التكامل مع قواعد البيانات والتخزين | `registerIntegration; configureIntegration; startSync` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التكامل مع خدمات الخرائط والمواقع | `registerIntegration; configureIntegration; validateConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| التكامل مع مزودي الذكاء الاصطناعي | `registerIntegration; configureIntegration; validateConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز اختبار التكاملات والمحاكاة | `testMapping; validateConnection` | SEMANTIC CANDIDATE — OWNER REVIEW |
| المراقبة والتشخيص وإدارة الأعطال | `getIntegrationStatus; pauseIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة التكلفة والحدود والبدائل | `getIntegrationStatus; pauseIntegration` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الإصدارات والتوافق والاعتماد | `configureIntegration; validateConnection; rotateSecretReference` | SEMANTIC CANDIDATE — OWNER REVIEW |

## C12 — الفصل 152 (الشاشات 11–20)

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| مركز توثيق الاختبارات والجودة | `createKnowledgeDocument; linkSourceEvidence; searchKnowledge` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز توثيق الأمن والحوكمة | `createKnowledgeDocument; linkSourceEvidence; publishKnowledgeDocument` | SEMANTIC CANDIDATE — OWNER REVIEW |
| سجل القرارات المعمارية والوظيفية | `createKnowledgeDocument; createDocumentVersion; compareDocumentVersions` | SEMANTIC CANDIDATE — OWNER REVIEW |
| قاعدة المعرفة والأسئلة والأجوبة | `createKnowledgeDocument; searchKnowledge` | SEMANTIC CANDIDATE — OWNER REVIEW |
| البحث المعرفي الذكي | `searchKnowledge` | SEMANTIC CANDIDATE — OWNER REVIEW |
| محلل الوثائق بالذكاء الاصطناعي | `generateDocumentationDraft; linkSourceEvidence` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الإصدارات والتغييرات | `createDocumentVersion; compareDocumentVersions; reconcileDocumentation` | SEMANTIC CANDIDATE — OWNER REVIEW |
| المراجعة والاعتماد | `submitDocumentReview; decideDocumentReview; publishKnowledgeDocument` | SEMANTIC CANDIDATE — OWNER REVIEW |
| الاستيراد والتصدير والمشاركة | `linkSourceEvidence; exportKnowledgeBundle` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات المعرفة والاحتفاظ | `archiveKnowledgeDocument; reconcileDocumentation` | SEMANTIC CANDIDATE — OWNER REVIEW |

## C13 — الفصل 150

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| لوحة الإعدادات الرئيسية | `getEffectiveSettings; diagnoseInheritance` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات الحساب الشخصي | `getEffectiveSettings; updateUserPreference` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات المؤسسة | `getEffectiveSettings; requestScopedSettingChange; decideSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات مساحات العمل | `getEffectiveSettings; requestScopedSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات المشاريع | `getEffectiveSettings; requestScopedSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة البيئات | `getEffectiveSettings; requestScopedSettingChange; compareSettingVersions` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات واجهة المصمم | `getEffectiveSettings; updateUserPreference` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات التصميم وDesign System | `getEffectiveSettings; requestScopedSettingChange; validateSettingValue` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات اللغات وإمكانية الوصول | `getEffectiveSettings; updateUserPreference; validateSettingValue` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات التقنيات والأطر | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات محركات التصميم والبرمجة | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات فريق الذكاء الاصطناعي | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات المهام والأتمتة | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات التكاملات والخدمات | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات الأمن والخصوصية | `getEffectiveSettings; requestScopedSettingChange; decideSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات الإشعارات | `getEffectiveSettings; updateUserPreference; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات الموارد والتكاليف | `getEffectiveSettings; requestScopedSettingChange; validateSettingValue` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إعدادات البناء والنشر | `getEffectiveSettings; requestScopedSettingChange; applyOwnerSettingChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| الإصدارات والتغييرات والتراجع | `compareSettingVersions; rollbackSettingVersion` | SEMANTIC CANDIDATE — OWNER REVIEW |
| الإعدادات المتقدمة والتشخيص | `diagnoseInheritance; validateSettingValue; exportNonSecretSettings` | SEMANTIC CANDIDATE — OWNER REVIEW |

## C14 — الفصل 142 (نسخة تاريخية 16 شاشة)

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| الصفحة الرئيسية | `getDashboardOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| ملخص المشاريع | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز المهام | `getMyTasksOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الموافقات | `getApprovalQueue` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الذكاء الاصطناعي | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز صحة المنصة | `getOperationalHealth` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الجودة والأمن | `getQualityOverview; getSecurityOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز البناء والنشر | `getReleaseOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الموارد والتكاليف | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز النشاط والتدقيق | `getCenterSummary; exportDashboardReport` | SEMANTIC CANDIDATE — OWNER REVIEW |
| البحث الشامل | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| المساعد الذكي للوحة التحكم | `getDashboardOverview; getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| تخصيص لوحة التحكم | `saveDashboardLayout; pinWidget` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التنبيهات | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التقارير | `exportDashboardReport` | SEMANTIC CANDIDATE — OWNER REVIEW |
| إدارة الاختصارات والإجراءات السريعة | `saveDashboardLayout; pinWidget` | SEMANTIC CANDIDATE — OWNER REVIEW |

## C14 — الفصل 153 (نسخة تاريخية 20 شاشة)

| عنوان الشاشة التاريخي | عمليات العقد المرشحة | التصنيف |
|---|---|---|
| لوحة التحكم الرئيسية | `getDashboardOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز المشاريع | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز المهام | `getMyTasksOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز فريق AI | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الموافقات | `getApprovalQueue` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الإشعارات | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الأنشطة | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز تقدم التطوير | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الجودة والاختبارات | `getQualityOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الأمن والمخاطر | `getSecurityOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التكاملات | `getIntegrationHealth` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز البناء والنشر | `getReleaseOverview` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الموارد والتكاليف | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز الأداء والاستمرارية | `getOperationalHealth` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز المعرفة والتوثيق | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التقارير | `exportDashboardReport` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التحليلات | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التنبيهات الحرجة | `getCenterSummary` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التخصيص | `saveDashboardLayout; pinWidget` | SEMANTIC CANDIDATE — OWNER REVIEW |
| مركز التقارير التنفيذية | `exportDashboardReport` | SEMANTIC CANDIDATE — OWNER REVIEW |

## 6. النتائج الكمية والحدود

- جرى إدراج 20 عنواناً تاريخياً لكل من C08 وC09 وC13.
- جرى إدراج 10 عناوين C12 للشاشات 11–20؛ الشاشات 1–10 لم تستعد عناوينها التفصيلية في المواد التي راجعتها هذه الجولة.
- سُجلت نسختا C14 منفصلتين: 16 عنواناً في الفصل 142 و20 عنواناً في الفصل 153. لا تجمعان في 36 شاشة ولا تعتبر النسخة الأحدث معتمدة تلقائياً.
- جرى ربط وحدات C10 وC11 وظيفياً دون اختراع screen IDs.
- عمليات C08–C14 في سجل 97 = 81 عملية. لا تغير هذه الوثيقة معرفاتها أو حالتها.
- هذه المصفوفة لا تدعي اكتمال كل وظيفة تاريخية: C10/C11 أقسام وظيفية، C12 شاشات 11–20 فقط، وبعض العناوين عامة أو مجمعة.

## 7. فجوات وتعارضات تستلزم قراراً

1. **C08:** «النسخ الاحتياطي والاستعادة» في شاشة تاريخية، لكن عقد 64 لا يملك عمليات backup/restore؛ requestExport ليس بديلاً مكافئاً. يلزم حسم حدود C04/SH-14/SH-15 أو إضافة عملية مجال بعد اعتمادها.
2. **C08:** «التراخيص وحقوق استخدام الأصول» لا تقابلها عملية license entitlement مستقلة في عقد 64؛ inspectAsset لا يثبت الحقوق وحده.
3. **C08:** «البحث والفهرسة الذكية» لا تملك عملية search مخصصة في عقد 64؛ inspectAsset ليس بحثاً واسع النطاق.
4. **C09:** شاشات الدفع والفوترة والإشعارات وقواعد البيانات والخرائط والذكاء الاصطناعي هي مجالات مزودين مختلفة؛ register/configure/sync لا تكفي وحدها لإثبات تفاصيل كل adapter أو اعتماد مزود.
5. **C09:** شاشة «خزنة الأسرار» تتقاطع مع SH-07 وC10؛ rotateSecretReference لا يجعل C09 مالك السر الخام.
6. **C10:** تقييم السياسة مسجل كـCommand candidate في سجل 97 بينما عقد 66 يصفه Query/Decision؛ يجب تسوية نوع العملية قبل التنفيذ.
7. **C10:** لا توجد عملية مستقلة صريحة في عقد 66 لإدارة المستخدمين/الأدوار أو لتشفير البيانات؛ لا تُسقط هذه المسؤوليات على evaluatePolicy أو assessPrivacyImpact دون قرار.
8. **C11:** النشر والتراجع يتطلبان قرارات واضحة حول release ownership وbackup/restore وmigrations وtelemetry.
9. **C12:** لا تزال عناوين الشاشات 1–10 غير مستعادة تفصيلياً، ولا ينبغي استنتاجها من الفصل 31 وحده.
10. **C13:** settings screens لا تمنح C13 حق كتابة إعدادات مجالات C01–C12؛ applyOwnerSettingChange يجب أن يمر عبر API المالك.
11. **C14:** هذه لوحة إسقاط/قراءة؛ لا تملك بيانات المراكز الأخرى. getCenterSummary لا يتجاوز ACL ولا ينقل SoR إلى C14.
12. **C14:** تكرار «مركز التقارير» و«مركز التقارير التنفيذية» في النسخة ذات 20 شاشة يحتاج قرار merge/variant.

## 8. التصنيف المطلوب لكل صف

بعد مراجعة مالك المركز، يصنف كل صف إلى واحد فقط:
- EXACT SOURCE MATCH: نص المصدر يحدد الوظيفة والعملية صراحةً.
- SAME INTENT: المعنى متوافق لكن ليس حرفياً.
- PARTIAL: العملية تغطي جزءاً من الشاشة فقط.
- CONFLICT: اختلاف ملكية أو سلوك أو نطاق.
- NOT MAPPED: لا توجد عملية مرشحة.
- OUT OF SCOPE / MERGED / SUPERSEDED: فقط بقرار موثق.

لا يجوز تحويل SEMANTIC CANDIDATE إلى EXACT أو APPROVED بمجرد التشابه اللغوي.

## 9. أثر الوثيقة على خط الأساس

- لا تتغير أعداد 14 مركزاً و64 محوراً و201 عملية و106 أحداث و26 موافقة.
- لا تتغير روابط Operation→Test exact: تظل 0/201.
- لا تتغير روابط Event→Test exact: تظل 0/106.
- لا تتغير حالة الموافقات الـ14 للمراكز C08–C14 عن OWNER REVIEW.
- لا تعدل OWN/SoR أو سجلات 97/98/100.
- لا تعديل على كود التطبيق أو قاعدة البيانات أو APIs، ولا تشغيل لاختبارات المنتج.

## 10. الحكم

**HISTORICAL SCREEN-TO-OPERATION CANDIDATES PREPARED; GAPS AND OWNERSHIP CONFLICTS EXPOSED; NO EXACT APPROVAL CLAIMED.**

هذه المصفوفة تجعل الفجوات قابلة للقرار وتحدد أين لا تكفي العمليات الحالية لتغطية الشاشة التاريخية. تظل المواصفات غير جاهزة للاعتماد النهائي حتى تراجع المراكز والملكية والاختبارات وتُغلق القرارات المفتوحة.

## 11. فحص تغطية أسماء عمليات سجل 97 — نتيجة تحقق آلي

قارنت أسماء العمليات الـ81 المسجلة للمراكز C08–C14 مع أسماء العمليات المذكورة في جداول هذه المصفوفة. النتيجة بعد إضافة الوحدات غير الممثلة في قوائم الشاشات:

| العملية | القرار |
|---|---|
| `requestPolicyChange` (OP-C10-148) | تُضاف كوحدة «إدارة تغيير السياسات» في C10؛ مرشح دلالي |
| `decideSecurityException` (OP-C10-149) | تُضاف كوحدة «إدارة الاستثناءات الأمنية» في C10؛ مرشح دلالي |
| `attestArtifact` (OP-C11-158) | تُضاف كوظيفة «اعتماد/إثبات سلامة مخرجات البناء» ضمن مدير الإصدارات C11؛ مرشح دلالي |
| `invalidateProjection` (OP-C14-201) | لا تُربط بشاشة تاريخية؛ عملية داخلية لإبطال/تحديث الإسقاط وليست واجهة مستخدم بحسب عقد 70. تسجل NOT A UI SCREEN / OWNER REVIEW، ولا تُسقط على شاشة التقارير أو التخصيص |

بعد هذا التحديث، 80/81 عملية لها مرشح شاشة/وحدة وظيفية في هذه المصفوفة، والعملية المتبقية موثقة كعملية داخلية لا تتطلب شاشة تاريخية مستقلة. هذا **لا يعني** اعتماد أي ربط أو اكتمال تغطية الاختبارات.

## 12. إضافات C10 وC11 إلى قائمة الوحدات

| المركز | الوحدة التاريخية/الوظيفية المرشحة | العملية | التصنيف |
|---|---|---|---|
| C10 | إدارة تغيير السياسات | `requestPolicyChange` | SEMANTIC CANDIDATE — OWNER REVIEW |
| C10 | إدارة الاستثناءات الأمنية | `decideSecurityException` | SEMANTIC CANDIDATE — OWNER REVIEW |
| C11 | اعتماد/إثبات سلامة مخرجات البناء | `attestArtifact` | SEMANTIC CANDIDATE — OWNER REVIEW |

