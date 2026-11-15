# 156 — مرشحات ربط أحداث C01–C07 بسيناريوهات القبول المستعادة

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC EVENT-TEST CANDIDATES / SOURCE-LOCATOR RECONCILED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**المراجع:** 100، 102، 103، 104، 105، 106، 155.

## 1. النتيجة التنفيذية

أُعدت 51 مطابقة دلالية مرشحة للأحداث الـ51 في المراكز C01–C07، بالاستناد إلى سيناريوهات القبول المحلية المستعادة في الوثيقة 104.

| المركز | الأحداث | مرشحات الحدث/السيناريو |
|---|---:|---:|
| C01 | 6 | 6 |
| C02 | 5 | 5 |
| C03 | 5 | 5 |
| C04 | 10 | 10 |
| C05 | 10 | 10 |
| C06 | 9 | 9 |
| C07 | 6 | 6 |
| **الإجمالي** | **51** | **51** |

**النتيجة لا تغير خط الأساس:** Exact Event→Test approved = 0/51. هذه روابط دلالية إلى SRC-AT locators وليست Test IDs أصلية أو معتمدة؛ وجميع السيناريوهات في 104 SOURCE-TEXT-RECOVERED / NOT EXECUTED.

## 2. قواعد التفسير

- SRC-AT-Cxx-nn معرف locator محلي في 104، وليس معرف اختبار أصلياً في DOCX.
- المرشح يحدد سيناريو مراجعة مناسباً، ولا يثبت أن الحدث ورد حرفياً في فقرة المصدر أو أن السيناريو يغطي كل payload/transition.
- بعض أحداث الموافقة والقرار تحتاج سيناريوهات منفصلة تغطي request وdecision وexecution؛ لا تختزلها في حدث واحد.
- عناصر C02 في 104 معايير قبول موضوعية لا Test Cases أصلية مرقمة.
- لا تستنتج producer أو Qualified OWN أو Domain SoR من اسم المركز. تبقى هذه الحقول TBD كما في 100.
- لا ترفع أي حالة إلى PASS دون تشغيل واستخراج evidence.

## 3. مصفوفة المرشحات

| Event ID | Event type | Candidate source locator(s) | سبب الترشيح / حدود التغطية |
|---|---|---|---|
| EVT-C01-001 | ProjectCreated | SRC-AT-C01-01 | إنشاء مشروع كامل؛ لا يثبت وحده صحة كل الحقول أو idempotency |
| EVT-C01-002 | DomainAnalysisCompleted | SRC-AT-C01-02, SRC-AT-C01-04, SRC-AT-C01-06 | تحليل الوصف والمراجع وإعادة التحليل؛ يلزم فصل مخرجات AI عن قرار المجال |
| EVT-C01-003 | RequirementCreated | SRC-AT-C01-02, SRC-AT-C01-03 | استخراج المتطلبات وكشف النواقص؛ يلزم تعريف متى يصبح المتطلب محفوظاً |
| EVT-C01-004 | RequirementVersionChanged | SRC-AT-C01-05, SRC-AT-C01-06 | إنشاء نسخة قابلة للمقارنة وعدم فقدان القرارات السابقة |
| EVT-C01-005 | BaselineSubmitted | SRC-AT-C01-09 | اختبار منع الانتقال المبكر؛ يحتاج تأكيد حالة submit والنسخة المرجعية |
| EVT-C01-006 | BaselineApproved | SRC-AT-C01-07, SRC-AT-C01-08, SRC-AT-C01-09 | رفض اقتراح/تكلفة ومنع الانتقال قبل الموافقة؛ لا يوجد سيناريو مصدر مستقل صريح لقرار اعتماد baseline النهائي |
| EVT-C02-001 | DesignWorkspaceCreated | SRC-AT-C02-01 | تصميم صفحة دون كتابة كود؛ معيار قبول عام |
| EVT-C02-002 | DesignDocumentVersionSaved | SRC-AT-C02-06, SRC-AT-C02-07 | مقارنة الإصدارات وحفظ التعديلات واستعادتها |
| EVT-C02-003 | DesignValidationCompleted | SRC-AT-C02-02, SRC-AT-C02-03, SRC-AT-C02-05 | تعديل العناصر والتحكم بالخصائص والمعاينة؛ لا يوجد oracle تفصيلي للتحقق |
| EVT-C02-004 | DesignApprovalSubmitted | SRC-AT-C02-08 | ربط التصميم بالمتطلبات المعتمدة؛ لا يثبت دورة submit/decision كاملة |
| EVT-C02-005 | DesignApproved | SRC-AT-C02-08, SRC-AT-C02-09 | اتساق التصميم مع المتطلبات ومنع فقدانه عند الانتقال؛ يلزم اختبار قرار اعتماد مستقل |
| EVT-C03-001 | PageRegistered | SRC-AT-C03-01, SRC-AT-C03-02 | إضافة صفحة ونسخها بمعرف مستقل |
| EVT-C03-002 | RouteDefinitionChanged | SRC-AT-C03-03, SRC-AT-C03-04, SRC-AT-C03-09 | نقل الصفحة وإنشاء الرابط وتعديل الاسم مع حفظ الروابط |
| EVT-C03-003 | NavigationDiagnosticsCompleted | SRC-AT-C03-07, SRC-AT-C03-08 | كشف الصفحة اليتيمة والرابط المكسور |
| EVT-C03-004 | NavigationApprovalSubmitted | SRC-AT-C03-05, SRC-AT-C03-06 | حذف صفحة مرتبطة ومسار محمي؛ مرشح غير مباشر ويحتاج سيناريو submit/approval مستقل |
| EVT-C03-005 | NavigationApproved | SRC-AT-C03-08, SRC-AT-C03-10 | منع اعتماد بناء به رابط مكسور واستعادة إصدار؛ لا يثبت قرار اعتماد graph منفرداً |
| EVT-C04-001 | DataSourceRegistered | SRC-AT-C04-01, SRC-AT-C04-04 | إنشاء جدول واستيراد البيانات؛ يلزم تمييز تسجيل مصدر البيانات عن إنشاء schema |
| EVT-C04-002 | DataSourceHealthChanged | SRC-AT-C04-09 | انقطاع الاتصال وحفظ التغييرات المسموح بها؛ لا يغطي كل حالات health/telemetry |
| EVT-C04-003 | SchemaVersionCreated | SRC-AT-C04-01, SRC-AT-C04-02, SRC-AT-C04-03 | إنشاء جدول وعلاقة وفهرس؛ يلزم ربط version/hash صراحة |
| EVT-C04-004 | SchemaValidationCompleted | SRC-AT-C04-01, SRC-AT-C04-02, SRC-AT-C04-03, SRC-AT-C04-04 | تحقق المخطط والمفاتيح والأنواع والتكرار |
| EVT-C04-005 | MigrationPlanCreated | SRC-AT-C04-05, SRC-AT-C04-06 | توليد SQL ومراجعة الترحيل والاستعادة |
| EVT-C04-006 | MigrationApplied | SRC-AT-C04-06 | اختبار الترحيل والاستعادة؛ يلزم فصل الخطة عن التطبيق الفعلي |
| EVT-C04-007 | RowPolicyChanged | SRC-AT-C04-07, SRC-AT-C04-08 | صلاحيات الصفوف وعزل المؤسسات |
| EVT-C04-008 | SyncConflictDetected | SRC-AT-C04-09 | انقطاع الاتصال وحفظ التغييرات؛ لا يختبر conflict resolution صراحة |
| EVT-C04-009 | BackupCompleted | SRC-AT-C04-10 | النسخ الاحتياطي والاستعادة الفعلية؛ يجب فصل نجاح backup عن نجاح restore |
| EVT-C04-010 | RestoreVerificationCompleted | SRC-AT-C04-06, SRC-AT-C04-10 | استعادة الترحيل والبيانات والتحقق منها؛ يتطلب evidence مستقل وRTO/RPO معتمدين |
| EVT-C05-001 | CodeChangeSetCreated | SRC-AT-C05-03, SRC-AT-C05-08 | حفظ التعديل وكشف تعارض التغييرات؛ يلزم ربط change-set ID/base hash |
| EVT-C05-002 | CodeGenerationStarted | SRC-AT-C05-02 | توليد مكون من التصميم؛ لا يحدد حدث بدء منفصل في السيناريو |
| EVT-C05-003 | CodeGenerationCompleted | SRC-AT-C05-02 | مطابقة خصائص المكون المولد؛ يحتاج artifact/hash ودليل اكتمال |
| EVT-C05-004 | CodeDiagnosticsCompleted | SRC-AT-C05-05, SRC-AT-C05-10 | كشف أخطاء البناء ونتائج الاختبار؛ لا يثبت تغطية كل diagnostics |
| EVT-C05-005 | DependencyScanCompleted | SRC-AT-C05-06 | فحص توافق وأمان الحزمة بعد تحديثها |
| EVT-C05-006 | SandboxRunCompleted | SRC-AT-C05-07 | تشغيل المشروع داخل بيئة معزولة |
| EVT-C05-007 | BuildPreparationCompleted | SRC-AT-C05-05 | فشل البناء وتحديد الخطأ؛ لا يختبر preparation success مستقلاً |
| EVT-C05-008 | GitHubPullRequestCreated | SRC-AT-C05-04, SRC-AT-C05-08 | مراجعة فرق AI والتعامل مع التعارض؛ لا يثبت إنشاء PR أو صلاحيات GitHub |
| EVT-C05-009 | CodeApprovalSubmitted | SRC-AT-C05-04, SRC-AT-C05-10 | عرض فرق AI وحفظ نتائج الاختبار؛ يلزم فصل submit عن decision |
| EVT-C05-010 | CodeBaselineApproved | SRC-AT-C05-09, SRC-AT-C05-10 | التراجع وحفظ نتائج قابلة للمراجعة؛ لا يوجد سيناريو مصدر مستقل لاعتماد baseline |
| EVT-C06-001 | AIAgentRegistered | SRC-AT-C06-01, SRC-AT-C06-07 | توزيع المهمة واختيار الوكيل وتبديل المزود؛ التسجيل نفسه يحتاج تحققاً مستقلاً |
| EVT-C06-002 | AIAgentVersionActivated | SRC-AT-C06-07 | تبديل مزود AI مع احترام التوافق والميزانية؛ لا يثبت version activation وحده |
| EVT-C06-003 | AITaskCreated | SRC-AT-C06-01 | توزيع مهمة واختيار الوكيل المناسب |
| EVT-C06-004 | AITaskAssigned | SRC-AT-C06-01, SRC-AT-C06-02 | اختيار الوكيل وموازاة المهام دون تعارض |
| EVT-C06-005 | AITaskStateChanged | SRC-AT-C06-03, SRC-AT-C06-09, SRC-AT-C06-10 | فشل الوكيل وإيقافه واستعادة الجلسة |
| EVT-C06-006 | AIToolGrantRequested | SRC-AT-C06-05, SRC-AT-C06-06 | طلب الموافقة ورفض تجاوز الصلاحيات؛ لا يثبت قرار المنح النهائي |
| EVT-C06-007 | AIAgentOutputSubmitted | SRC-AT-C06-02, SRC-AT-C06-08 | منع تعارض التعديلات ومراجعة مستقلة للمخرجات |
| EVT-C06-008 | AIAgentOutputReviewed | SRC-AT-C06-08 | مراجعة مستقلة واكتشاف الأخطاء؛ يلزم تعريف نتيجة review وأثرها |
| EVT-C06-009 | AIAgentConflictDetected | SRC-AT-C06-02, SRC-AT-C06-03 | كشف تعارض التعديلات والتعامل مع فشل الوكيل |
| EVT-C07-001 | QAPlanCreated | SRC-AT-C07-01, SRC-AT-C07-02 | تشغيل اختبارات الوحدة والتكامل؛ إنشاء الخطة نفسها يحتاج تحققاً مستقلاً |
| EVT-C07-002 | QATestRunCompleted | SRC-AT-C07-01, SRC-AT-C07-02, SRC-AT-C07-03 | نتائج الوحدة والتكامل والرحلة الكاملة |
| EVT-C07-003 | QADefectOpened | SRC-AT-C07-06 | فشل اختبار إلزامي ومنع اجتياز البوابة؛ يلزم إثبات تسجيل defect |
| EVT-C07-004 | QADefectVerified | SRC-AT-C07-07 | إعادة الاختبار ومقارنة النتيجة السابقة |
| EVT-C07-005 | QAQualityGateEvaluated | SRC-AT-C07-06, SRC-AT-C07-10 | منع اجتياز البوابة عند فشل إلزامي وتصدير تقرير قابل للمراجعة |
| EVT-C07-006 | QATestResultsApproved | SRC-AT-C07-10, SRC-AT-C07-07 | التقرير وإعادة الاختبار؛ لا يوجد سيناريو مصدر صريح مستقل لقرار اعتماد النتائج |

## 4. فجوات تغطية يجب ألا تُخفى

- **قرارات baseline/design/navigation/code/QA:** بعض السيناريوهات تختبر عدم الانتقال أو حفظ الحالة، لكنها لا تصف دائماً قرار الموافقة النهائي وربطه بـversion/hash. يلزم اختبار قرار مستقل.
- **C02:** locators في 104 هي معايير قبول موضوعية وليست Test Cases مصدرية مرقمة؛ يجب ألا تحتسب كاختبارات منفذة.
- **C04:** سيناريو التعارض لا يحدد استراتيجية resolution؛ وسيناريو backup يجمع النسخ والاستعادة في معيار واحد، فيجب فصلهما.
- **C05:** السيناريوهات لا تثبت PR provider integration أو attestation أو provenance؛ يلزم سيناريوهات إضافية إذا كانت هذه وظائف مطلوبة في العقد المعتمد.
- **C06:** فشل الوكيل/الإيقاف/الاستعادة لا يغطي كامل task lifecycle أو tool grant lifecycle أو صلاحيات كل أداة.
- **C07:** defect lifecycle وقرار اعتماد النتائج يحتاجان معايير انتقال ومالكاً واختبارات مستقلة.
- بعض روابط الأحداث متعددة السيناريوهات، وبعض السيناريوهات مرشحة لأكثر من حدث؛ لا توجد equivalence واحد-إلى-واحد.

## 5. حقول الاختبار المفرد المطلوبة قبل الاعتماد

لكل Event ID يجب على مالك الحدث والاختبار تثبيت:
1. Test ID أصلي موثق أو معرف جديد موسوم PROPOSED ثم معتمد.
2. preconditions وactor/tenant/project scope.
3. input/fixture وschema/version وresource version/hash.
4. expected emitted event أو expected absence عند الرفض.
5. domain state change الذي يجب أن يقع مرة واحدة أو لا يقع.
6. duplicate/out-of-order/retry/replay behavior.
7. unauthorized/cross-tenant/stale-version/invalid-payload cases.
8. evidence: eventId, requestId, correlationId, actor, timestamp, commit/build, environment, result.
9. owner approval وتاريخ المراجعة.

## 6. أثر الوثيقة على خطوط الأساس

- يظل سجل 100 عند 106 أحداث؛ لم يضف أو يحذف أي Event ID.
- يظل عدد أحداث C01–C07 هو 51.
- يضاف 51 semantic candidates فقط.
- Exact Event→Test approved يبقى 0/51.
- Qualified OWN وDomain SoR تبقيان TBD.
- لا تتغير أعداد 201 عملية / 106 أحداث / 26 موافقة.
- جميع السيناريوهات في 104 تبقى NOT EXECUTED.

## 7. الحكم

**51 EVENT-TEST SEMANTIC CANDIDATES PREPARED; 0 EXACT APPROVED LINKS; TEST EXECUTION NOT VERIFIED.**

هذه الوثيقة تجعل فجوة C01–C07 قابلة للمراجعة حدثاً بحدث، وتحدد مواضع التغطية غير الكافية بدلاً من وصف جميع الأحداث بأنها مغطاة. لا تعتمد الأحداث أو المالكين أو الاختبارات، ولا تنشئ event schemas أو topics أو APIs أو code. لا تغيير على التطبيق ولا إذن ببدء البرمجة.
