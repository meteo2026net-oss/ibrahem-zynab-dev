# 145 — مطابقة مرشحة بين الوظائف الأصلية وعمليات API للمراكز C01–C07

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC CANDIDATE CROSSWALK / NOT EXACT / OWNER REVIEW REQUIRED / NOT APPROVED  
**المصادر:** Export 07 B-01..B-07؛ سجل العمليات 97؛ سجل الوظائف المصدرية 144.

## 1. الغرض والحدود

تضع هذه الوثيقة مطابقة دلالية أولية بين 109 وظائف مصدرية و120 عملية مقترحة في سجل 97 للمراكز C01–C07. عمليات 97 مشتقة/مرشحة وليست endpoints منشورة، وعناوين الوظائف مصدرية. لذلك لا يمكن أن تصبح هذه المطابقات EXACT بمجرد تشابه الاسم أو الدور.

**حظر مهم:** كل ربط في هذا المستند هو `SEMANTIC-CANDIDATE` فقط، ويحتاج مراجعة مالك الوظيفة ومالك API. غياب عملية مرشحة لا يعني تلقائياً وجوب إنشاء API جديد؛ قد تكون الوظيفة داخلية أو ضمن عقد مركز آخر أو لم تُفكك بعد.

## 2. الملخص

| المقياس | النتيجة |
|---|---:|
| وظائف مصدرية | 109 |
| وظائف لها مرشح عملية واحد على الأقل | 100 |
| وظائف بلا عملية واضحة في سجل 97 | 9 |
| عمليات مختلفة مستخدمة كمرشحات | 113 |
| روابط EXACT معتمدة | 0 |
| روابط وافق عليها الملاك | 0 |
| اختبارات منفذة مثبتة | 0 |

## 3. جدول المطابقة المرشحة

| Function ID المصدر | عنوان الوظيفة | عمليات API المرشحة من سجل 97 | سطر المصدر | حالة الربط |
|---|---|---|---:|---|
| `C01-F01` | إنشاء مشروع جديد | `OP-C01-001` (createProject) | 5478 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F02` | إدخال فكرة المشروع | `OP-C01-002` (updateProjectProfile)<br>`OP-C01-003` (saveIdeaVersion) | 5519 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F03` | إدارة المراجع والمرفقات | `OP-C01-004` (attachProjectReference) | 5556 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F04` | تحليل الفكرة والمجال | `OP-C01-005` (startDomainAnalysis)<br>`OP-C01-006` (getAnalysisResult) | 5594 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F05` | إدارة المستخدمين والأدوار المستهدفة | `OP-C01-007` (upsertPersona) | 5623 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F06` | إدارة المتطلبات الوظيفية | `OP-C01-008` (createRequirement)<br>`OP-C01-009` (reviseRequirement) | 5640 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F07` | إدارة المتطلبات غير الوظيفية | `OP-C01-010` (recordNFR) | 5661 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F08` | اكتشاف النواقص والتعارضات | `OP-C01-011` (analyzeGaps) | 5670 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F09` | الحوار الذكي | `OP-C01-012` (answerClarification) | 5689 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F10` | تحديد المنصات والتقنيات الأولية | `OP-C01-013` (proposeTechnologyOptions) | 5708 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F11` | تقدير الموارد والتكلفة | `OP-C01-014` (createEstimate) | 5717 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C01-F12` | مراجعة واعتماد وثيقة المشروع | `OP-C01-015` (submitRequirementBaseline)<br>`OP-C01-016` (decideRequirementBaseline) | 5726 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F01` | إنشاء مساحة تصميم | `OP-C02-017` (createDesignWorkspace) | 6362 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F02` | إنشاء الصفحات وتحريرها | `OP-C02-018` (createDesignDocument) | 6371 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F03` | إدارة العناصر والطبقات | `OP-C02-020` (saveDesignChangeSet) | 6380 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F04` | إدارة المكونات | `OP-C02-022` (createComponentDefinition) | 6389 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F05` | تحرير الخصائص | `OP-C02-020` (saveDesignChangeSet) | 6397 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F06` | تحرير النصوص | `OP-C02-020` (saveDesignChangeSet) | 6418 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F07` | إدارة التخطيطات | `OP-C02-020` (saveDesignChangeSet) | 6435 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F08` | تعريف التفاعلات | `OP-C02-020` (saveDesignChangeSet) | 6452 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F09` | إدارة الحالات | `OP-C02-021` (validateDesignDocument) | 6461 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F10` | إدارة نظام التصميم | `OP-C02-020` (saveDesignChangeSet) | 6486 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F11` | معاينة الأجهزة والمنصات | `OP-C02-023` (createDesignPreview) | 6495 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F12` | التصميم بمساعدة AI | `OP-C02-024` (requestDesignAIProposal)<br>`OP-C02-025` (applyDesignProposal) | 6516 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F13` | التحرير المباشر من المعاينة | `OP-C02-020` (saveDesignChangeSet) | 6537 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F14` | إدارة الاختلافات بين المنصات | `OP-C02-021` (validateDesignDocument) | 6555 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C02-F15` | مراجعة واعتماد التصميم | `OP-C02-026` (submitDesignForApproval)<br>`OP-C02-027` (decideDesignApproval)<br>`OP-C02-028` (getApprovedDesignVersion) | 6568 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F01` | إدارة سجل الصفحات | `OP-C03-029` (createPage)<br>`OP-C03-030` (renamePage) | 7001 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F02` | إنشاء خريطة التنقل البصرية | `OP-C03-032` (updateNavigationGraph) | 7034 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F03` | محرر المسارات Routes | `OP-C03-031` (createRoute) | 7058 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F04` | إدارة الانتقالات | `OP-C03-032` (updateNavigationGraph) | 7089 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F05` | إنشاء القوائم وإدارتها | `OP-C03-032` (updateNavigationGraph) | 7114 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F06` | إدارة أشرطة التنقل | `OP-C03-032` (updateNavigationGraph) | 7131 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F07` | حماية الصفحات | لا توجد عملية واضحة في سجل 97 | 7150 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C03-F08` | الصفحات الديناميكية | لا توجد عملية واضحة في سجل 97 | 7177 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C03-F09` | إدارة Deep Links | `OP-C03-035` (createDeepLinkRule) | 7193 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F10` | إدارة حالات التنقل | `OP-C03-033` (validateNavigationGraph) | 7212 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F11` | رسم رحلة المستخدم | `OP-C03-036` (simulateUserJourney) | 7235 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F12` | التشخيص الآلي للتنقل | `OP-C03-033` (validateNavigationGraph)<br>`OP-C03-034` (analyzeReachability) | 7254 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C03-F13` | اقتراح تحسينات AI للتنقل | لا توجد عملية واضحة في سجل 97 | 7286 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C03-F14` | المراجعة والاعتماد | `OP-C03-037` (submitNavigationForApproval)<br>`OP-C03-038` (decideNavigationApproval)<br>`OP-C03-039` (getApprovedNavigation) | 7308 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F01` | إدارة مصادر البيانات | `OP-C04-040` (registerDataSource)<br>`OP-C04-041` (validateDataSource) | 7775 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F02` | تصميم مخطط قاعدة البيانات | `OP-C04-042` (createSchema) | 7806 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F03` | إدارة الجداول والحقول | `OP-C04-043` (addEntity)<br>`OP-C04-044` (addField) | 7827 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F04` | إدارة أنواع البيانات والقيود | `OP-C04-046` (defineConstraint) | 7848 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F05` | إدارة العلاقات | `OP-C04-045` (defineRelation) | 7867 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F06` | إدارة الفهارس | `OP-C04-047` (defineIndex) | 7884 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F07` | إدارة سياسات RLS | `OP-C04-048` (defineRowPolicy) | 7899 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F08` | إدارة الاستعلامات | `OP-C04-050` (previewQuery) | 7918 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F09` | ربط البيانات بالواجهات | `OP-C04-051` (createDataBinding) | 7937 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F10` | إدارة البيانات التجريبية | `OP-C04-052` (generateTestFixtures) | 7956 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F11` | إدارة Offline & Sync | `OP-C04-053` (configureSync) | 7971 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F12` | إدارة الملفات والتخزين | لا توجد عملية واضحة في سجل 97 | 7990 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C04-F13` | فحص سلامة البيانات | `OP-C04-049` (validateSchema) | 8006 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F14` | إدارة Migrations | `OP-C04-054` (createMigrationPlan)<br>`OP-C04-055` (dryRunMigration)<br>`OP-C04-056` (applyMigration)<br>`OP-C04-057` (createRollbackPlan) | 8023 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F15` | النسخ الاحتياطي والاستعادة | `OP-C04-058` (startBackup)<br>`OP-C04-059` (requestRestore)<br>`OP-C04-060` (verifyRestore) | 8040 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C04-F16` | مراجعة واعتماد مخطط البيانات | `OP-C04-061` (submitSchemaForApproval)<br>`OP-C04-062` (decideSchemaApproval) | 8057 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F01` | تهيئة بيئة البرمجة | `OP-C05-063` (initializeCodeWorkspace) | 8576 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F02` | إدارة الملفات والمجلدات | `OP-C05-064` (listProjectFiles)<br>`OP-C05-065` (readProjectFile)<br>`OP-C05-066` (proposeFileChange) | 8605 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F03` | تحرير الكود | `OP-C05-066` (proposeFileChange) | 8626 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F04` | إدارة اللغات والأطر والمنصات | لا توجد عملية واضحة في سجل 97 | 8650 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C05-F05` | توليد الكود من التصميم | `OP-C05-070` (generateCodeFromDesign) | 8671 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F06` | توليد الكود من المتطلبات | `OP-C05-071` (generateCodeFromRequirements) | 8702 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F07` | إدارة المعمارية البرمجية | `OP-C05-068` (generateIR)<br>`OP-C05-069` (validateIR) | 8723 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F08` | إدارة الاعتماديات | `OP-C05-073` (analyzeDependencies)<br>`OP-C05-074` (proposeDependencyUpdate) | 8742 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F09` | إنشاء APIs والخدمات | لا توجد عملية واضحة في سجل 97 | 8763 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C05-F10` | تحليل الأخطاء والتصحيح | `OP-C05-075` (runCodeDiagnostics) | 8782 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F11` | تعديل الكود باللغة الطبيعية | `OP-C05-076` (proposeNaturalLanguagePatch) | 8802 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F12` | مزامنة التصميم والكود | `OP-C05-077` (compareDesignAndCode) | 8821 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F13` | Sandbox وتشغيل الكود | `OP-C05-078` (createSandboxRun)<br>`OP-C05-079` (cancelSandboxRun) | 8841 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F14` | GitHub وإدارة الإصدارات | `OP-C05-082` (createGitHubChangeRequest) | 8860 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F15` | مراجعة الجودة والأمن والتراخيص | `OP-C05-073` (analyzeDependencies)<br>`OP-C05-075` (runCodeDiagnostics) | 8881 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C05-F16` | اعتماد الكود وتسليمه | `OP-C05-084` (approveCodeBaseline)<br>`OP-C05-085` (prepareCodeHandoff) | 8902 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F01` | إدارة الوكلاء | `OP-C06-086` (registerAgent)<br>`OP-C06-087` (createAgentVersion) | 9414 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F02` | إنشاء وكيل متخصص | `OP-C06-087` (createAgentVersion) | 9449 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F03` | إدارة المهارات | `OP-C06-088` (registerSkill) | 9480 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F04` | إدارة الأدوات والتفويض | `OP-C06-089` (registerToolManifest)<br>`OP-C06-090` (requestToolGrant) | 9501 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F05` | إدارة المهام | `OP-C06-091` (createAITask) | 9518 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F06` | مخطط التنفيذ | `OP-C06-092` (planAITask) | 9553 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F07` | تنسيق الوكلاء | `OP-C06-093` (assignAgents)<br>`OP-C06-094` (executeTaskStep) | 9572 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F08` | إدارة الذاكرة والسياق | لا توجد عملية واضحة في سجل 97 | 9590 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C06-F09` | إدارة مزودي الذكاء الاصطناعي | `OP-C06-102` (registerAIProvider)<br>`OP-C06-103` (validateAIProvider)<br>`OP-C06-106` (disableAIProvider) | 9612 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F10` | إدارة السياسات والتكاليف | `OP-C06-104` (selectModelForTask)<br>`OP-C06-105` (recordProviderUsage) | 9633 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F11` | مراقبة الوكلاء | `OP-C06-105` (recordProviderUsage) | 9654 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F12` | مراجعة النتائج | `OP-C06-098` (submitAgentOutput)<br>`OP-C06-099` (reviewAgentOutput) | 9677 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F13` | معالجة التعارضات | `OP-C06-100` (resolveAgentConflict) | 9696 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F14` | إدارة الصلاحيات والموافقات | `OP-C06-090` (requestToolGrant) | 9715 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F15` | الإيقاف والاستعادة | `OP-C06-095` (pauseTask)<br>`OP-C06-096` (resumeTask)<br>`OP-C06-097` (cancelTask) | 9734 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F16` | سجل التدقيق | لا توجد عملية واضحة في سجل 97 | 9755 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C06-F17` | تقييم أداء الوكلاء | `OP-C06-101` (evaluateAgent) | 9776 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C06-F18` | إعداد الفريق والاختبارات | لا توجد عملية واضحة في سجل 97 | 9797 | NOT-MAPPED / POSSIBLE MISSING OPERATION |
| `C07-F01` | إنشاء خطة اختبار | `OP-C07-107` (createTestPlan) | 10344 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F02` | إدارة حالات الاختبار | `OP-C07-108` (createTestCase) | 10377 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F03` | تتبع تغطية المتطلبات | `OP-C07-109` (linkTestToRequirement)<br>`OP-C07-116` (calculateCoverage) | 10405 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F04` | اختبارات الوحدات Unit Testing | `OP-C07-111` (executeTestCase) | 10420 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F05` | اختبارات التكامل Integration Testing | `OP-C07-112` (executeTestSuite) | 10437 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F06` | اختبارات واجهة المستخدم | `OP-C07-111` (executeTestCase)<br>`OP-C07-112` (executeTestSuite) | 10442 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F07` | اختبارات E2E | `OP-C07-111` (executeTestCase)<br>`OP-C07-112` (executeTestSuite) | 10463 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F08` | اختبارات APIs | `OP-C07-111` (executeTestCase)<br>`OP-C07-112` (executeTestSuite) | 10480 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F09` | اختبارات قواعد البيانات | `OP-C07-111` (executeTestCase)<br>`OP-C07-112` (executeTestSuite) | 10499 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F10` | اختبارات الأمن | `OP-C07-112` (executeTestSuite) | 10518 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F11` | اختبارات الأداء | `OP-C07-112` (executeTestSuite) | 10543 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F12` | اختبارات التوافق | `OP-C07-112` (executeTestSuite) | 10564 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F13` | اختبارات إمكانية الوصول | `OP-C07-112` (executeTestSuite) | 10583 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F14` | اختبارات Offline & Sync | `OP-C07-112` (executeTestSuite) | 10607 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F15` | اختبارات الانحدار | `OP-C07-112` (executeTestSuite) | 10626 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F16` | إدارة العيوب | `OP-C07-113` (recordDefect)<br>`OP-C07-114` (transitionDefect)<br>`OP-C07-115` (retestDefect) | 10641 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F17` | تقارير الجودة وبوابة الاعتماد | `OP-C07-117` (evaluateQualityGate)<br>`OP-C07-118` (requestQualityWaiver)<br>`OP-C07-120` (exportQualityReport) | 10664 | SEMANTIC-CANDIDATE — OWNER REVIEW |
| `C07-F18` | اعتماد نتائج الاختبار | `OP-C07-119` (approveTestResults) | 10689 | SEMANTIC-CANDIDATE — OWNER REVIEW |

## 4. قراءة الفجوات

### وظائف بلا عملية واضحة في سجل 97

- **C03-F07 حماية الصفحات:** لا توجد عملية مخصصة صريحة لإدارة/نشر حراس الوصول في قائمة C03؛ قد تكون سياسة التفويض مشتركة أو جزءاً من خدمة C10، ويجب إثبات العقد والمالك.
- **C03-F08 الصفحات الديناميكية:** لا توجد عملية صريحة لإدارة تعريفات الصفحات الديناميكية أو schema binding في عمليات C03 الحالية.
- **C03-F13 اقتراح تحسينات AI للتنقل:** لا توجد عملية AI محددة في C03؛ يلزم تحديد هل الطلب يمر عبر C06 مع بقاء ملكية القرار في C03.
- **C04-F12 إدارة الملفات والتخزين:** لا توجد عملية واضحة في C04؛ ملكية binary/assets قد تكون C08/خدمة التخزين، وعلى C04 الاحتفاظ بعقود الربط فقط.
- **C05-F04 إدارة اللغات والأطر والمنصات:** لا توجد عملية واضحة لإدارة التقنية/المحول؛ يلزم تحديد إن كانت هذه وظيفة C05 محلية أم خدمة كتالوج مشتركة.
- **C05-F09 إنشاء APIs والخدمات:** لا توجد عملية مستقلة واضحة في قائمة C05، رغم وجود توليد IR والكود. يجب تحديد ما إذا كانت عملية فرعية من التوليد أو وظيفة مستقلة.
- **C06-F08 إدارة الذاكرة والسياق:** لا توجد عملية صريحة في عمليات C06-086..106؛ يجب تعريف ملكية الذاكرة ونطاق tenant/project وسياسات الاحتفاظ.
- **C06-F16 سجل التدقيق:** لا توجد عملية C06 صريحة لكتابة/قراءة audit؛ المتوقع أن تكون الكتابة عبر خدمة تدقيق مركزية، ويجب توثيق عقد الأحداث والقراءة المصرح بها.
- **C06-F18 إعداد الفريق والاختبارات:** لا توجد عملية صريحة لتهيئة الفريق والتحقق منه.
  
هذه تسعة مواضع تستحق مراجعة مالك المجال، وليست تسعة APIs جديدة معتمدة.

### مواضع تداخل تستلزم تدقيقاً

- عمليات `saveDesignChangeSet` مرشحة لعدة وظائف تصميمية؛ يلزم توضيح command model وpayload discriminators أو تقسيم العمليات إذا ثبتت الحاجة.
- `executeTestCase` و`executeTestSuite` مرشحتان لعدة أنواع اختبار؛ يلزم تحديد كيف يحدد نوع suite/adapter/fixture والبيئة والأدلة.
- `recordProviderUsage` مرشح للمراقبة/التكلفة؛ لا يغطي وحده telemetry والصحة.
- `validateSchema` قد يغطي التحقق البنيوي، لكنه لا يغطي وحده سلامة البيانات أو فحص RLS/restore.

## 5. قرار المراجعة المطلوب لكل صف

على مالك المجال تحديد:
1. هل العملية تخص الوظيفة أم أنها جزء من وظيفة أخرى؟
2. هل العملية نفسها تخدم وظائف متعددة بعقد واحد واضح، أم يلزم فصلها؟
3. هل مالك API هو مركز المجال أم خدمة مشتركة؟ وما Domain SoR؟
4. هل العملية Command/Query/Job/Approval فعلاً؟ وهل لها idempotency/version binding؟
5. ما permission, input/output schema, failure states, audit event, evidence؟
6. ما اختبار القبول الأصلي/المشتق الذي يتحقق من العملية؟ لا يكفي اسم عائلة الاختبار.

## 6. الحكم

هذه المطابقة تقلل غموض البحث وتكشف مناطق نقص محتملة، لكنها **لا تغلق** فجوة 201 عملية و109 وظائف. لا تُغير عمود Exact mapping في سجل 97، ولا تحوّل أي مرشح إلى API معتمد. تظل جميع روابط Operation→Function بحاجة إلى مراجعة مالك، وجميع روابط Operation→Test exact غير مثبتة، والاختبارات NOT RUN.

لا تعديل على كود التطبيق أو قاعدة البيانات أو API، ولا إذن ببدء البرمجة.
