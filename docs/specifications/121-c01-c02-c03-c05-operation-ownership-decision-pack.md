# 121 — حزمة إغلاق ملكية عمليات C01 وC02 وC03 وC05

**التاريخ:** 2026-11-15  
**الحالة:** OPERATION OWNERSHIP DECISION PACK / DERIVED FROM INVENTORY 97 / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصدر المباشر:** سجل العمليات 97، وتدقيق الإحالات المؤهلة 111، وسجل namespace 82، وكتالوج الكيانات 77.  
**نسخة المصدر:** `de4a07576a3aa5e3b7cdff4ee0ca9105cc500cf6`.

## 1. النتيجة

تتركز العمليات الـ62 التي لا تحمل إحالة OWN مؤهلة في أربعة مراكز فقط:

| المركز | عدد العمليات | الإحالة الحالية |
|---|---:|---|
| C01 | 16 | TBD |
| C02 | 12 | TBD |
| C03 | 11 | TBD |
| C05 | 23 | TBD |
| **الإجمالي** | **62** | **لا توجد إحالات مفردة مؤهلة** |

هذه الحزمة تعزل العمليات المتأثرة وتقدمها لمراجعة المالكين. لا تسند OWN36/OWN72 آلياً؛ لأن namespace 82 يوضح أن وجود قرار قريب لا يثبت أن نطاقه يغطي العملية. كما لا تنقل `validateBuildConfiguration` من C05 إلى C11 أو العكس؛ تعارض موضعها موثق في التدقيق 108/114 ويحتاج قراراً مستقلاً.

## 2. قائمة العمليات التي تحتاج قرار ملكية

| Operation ID | المركز | اسم العملية | نوع العملية في 97 | مالك المجال المرشح في 97 | قرار OWN |
|---|---|---|---|---|---|
| OP-C01-001 | C01 | `createProject` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-002 | C01 | `updateProjectProfile` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-003 | C01 | `saveIdeaVersion` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-004 | C01 | `attachProjectReference` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-005 | C01 | `startDomainAnalysis` | Job/Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-006 | C01 | `getAnalysisResult` | Query candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-007 | C01 | `upsertPersona` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-008 | C01 | `createRequirement` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-009 | C01 | `reviseRequirement` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-010 | C01 | `recordNFR` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-011 | C01 | `analyzeGaps` | Query candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-012 | C01 | `answerClarification` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-013 | C01 | `proposeTechnologyOptions` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-014 | C01 | `createEstimate` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-015 | C01 | `submitRequirementBaseline` | Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C01-016 | C01 | `decideRequirementBaseline` | Approval/Command candidate | C01 (candidate) | TBD / OWNER DECISION |
| OP-C02-017 | C02 | `createDesignWorkspace` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-018 | C02 | `createDesignDocument` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-019 | C02 | `getDesignDocument` | Query candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-020 | C02 | `saveDesignChangeSet` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-021 | C02 | `validateDesignDocument` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-022 | C02 | `createComponentDefinition` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-023 | C02 | `createDesignPreview` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-024 | C02 | `requestDesignAIProposal` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-025 | C02 | `applyDesignProposal` | Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-026 | C02 | `submitDesignForApproval` | Approval/Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-027 | C02 | `decideDesignApproval` | Approval/Command candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C02-028 | C02 | `getApprovedDesignVersion` | Query candidate | C02 (candidate) | TBD / OWNER DECISION |
| OP-C03-029 | C03 | `createPage` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-030 | C03 | `renamePage` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-031 | C03 | `createRoute` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-032 | C03 | `updateNavigationGraph` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-033 | C03 | `validateNavigationGraph` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-034 | C03 | `analyzeReachability` | Query candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-035 | C03 | `createDeepLinkRule` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-036 | C03 | `simulateUserJourney` | Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-037 | C03 | `submitNavigationForApproval` | Approval/Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-038 | C03 | `decideNavigationApproval` | Approval/Command candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C03-039 | C03 | `getApprovedNavigation` | Query candidate | C03 (candidate) | TBD / OWNER DECISION |
| OP-C05-063 | C05 | `initializeCodeWorkspace` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-064 | C05 | `listProjectFiles` | Query candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-065 | C05 | `readProjectFile` | Query candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-066 | C05 | `proposeFileChange` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-067 | C05 | `applyApprovedChangeSet` | Approval/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-068 | C05 | `generateIR` | Job/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-069 | C05 | `validateIR` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-070 | C05 | `generateCodeFromDesign` | Job/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-071 | C05 | `generateCodeFromRequirements` | Job/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-072 | C05 | `getGenerationStatus` | Query candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-073 | C05 | `analyzeDependencies` | Query candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-074 | C05 | `proposeDependencyUpdate` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-075 | C05 | `runCodeDiagnostics` | Job/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-076 | C05 | `proposeNaturalLanguagePatch` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-077 | C05 | `compareDesignAndCode` | Query candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-078 | C05 | `createSandboxRun` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-079 | C05 | `cancelSandboxRun` | Job/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-080 | C05 | `createBuildPreparation` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-081 | C05 | `validateBuildConfiguration` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-082 | C05 | `createGitHubChangeRequest` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-083 | C05 | `requestCodeReview` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-084 | C05 | `approveCodeBaseline` | Approval/Command candidate | C05 (candidate) | TBD / OWNER DECISION |
| OP-C05-085 | C05 | `prepareCodeHandoff` | Command candidate | C05 (candidate) | TBD / OWNER DECISION |

## 3. ما يجب على مالك كل عملية تحديده

لكل صف أعلاه، يجب توثيق ما يلي قبل تغيير عمود OWN:

1. **Domain SoR:** الكيان/السجل الذي تكتب إليه العملية، وسبب كونه مصدر الحقيقة.
2. **Domain Owner:** المالك الدلالي للوظيفة والبيانات؛ لا يساوي منفذ العملية أو منشئ السجل.
3. **API Owner:** المالك المسؤول عن عقد الواجهة وإصداره وتوافقه.
4. **Shared Service Boundary:** هل العملية تستدعي SH-01..SH-16؟ وما الذي تملكه الخدمة المشتركة وما الذي يبقى لدى المركز؟
5. **Qualified OWN ID:** قرار موجود من OWN36/OWN72 مع دليل انطباقه، أو قرار جديد منفصل بعد موافقة المالك؛ أو N/A مع تبرير معتمد.
6. **Approval:** هل العملية نفسها فعل محمي؟ أم طلب موافقة؟ أم تنفيذ بعد قرار؟ يجب عدم الخلط بين هذه الأدوار.
7. **Test:** اختبار قبول مفرد أو تجميع مبرر، مع exact Test ID أو معرف مشتق موسوم PROPOSED، ومالك الاختبار وexpected result.
8. **Evidence:** locator مصدر، قرار مالك، نسخة العقد، run/build/environment/artifact عند التنفيذ.

## 4. نقاط تدقيق خاصة حسب المركز

### C01 — المشروع والمتطلبات
- لا تستنتج أن كل عمليات C01 تملك كل حقول المؤسسة أو مساحة العمل؛ يجب فصل project domain عن tenant/organization/workspace.
- يجب ربط عمليات المتطلبات والتحليل والمعايير بمصدر حقيقة واحد وإصدارات واضحة.
- قرارات قبول baseline أو اعتماد المتطلبات يجب أن تفصل request عن decision وعن apply.

### C02 — التصميم
- لا تفترض أن سجل الصفحة في C02 هو نفسه سجل الصفحة ومسارها في C03.
- يجب فصل التصميم المرئي ومكوناته ونسخه عن هوية الصفحة والتنقل.
- يجب توضيح من يملك design assets metadata ومن ينفذ تخزين الملفات في SH-06.

### C03 — الصفحات والتنقل
- C03 يملك page identity وroute/navigation lifecycle وفق الحدود المقترحة، بينما لا يصبح حارس المسار بديلاً عن تفويض الخادم في SH-02.
- يلزم ربط الصفحة بالتصميم عبر reference لا ازدواج قابل للكتابة.
- يجب تعريف عمليات deep links وroute guards وnavigation diagnostics ضمن tenant/workspace scope.

### C05 — البرمجة
- يجب فصل source snapshots/change sets/dependency manifests عن generic execution jobs في SH-08.
- لا تخلط artifact registry مع التخزين العام في SH-06 أو release lifecycle في C11.
- `validateBuildConfiguration` موضعها مختلف بين الفصل 135.9 وسجل العمليات 97؛ لا تحسم هذه الحزمة ذلك التعارض.

## 5. نموذج سجل قرار مطلوب لكل عملية

| الحقل | القيمة المطلوبة |
|---|---|
| Operation ID | من الجدول أعلاه |
| Source locator | chapter / paragraph / line أو قرار معتمد |
| Domain SoR | canonical entity + owner |
| Domain Owner | اسم الدور/الجهة المعتمدة |
| API Owner | اسم الدور/الجهة المعتمدة |
| Shared service dependencies | SH ID + boundary |
| Qualified OWN ID | OWN36/OWN72 أو قرار جديد معتمد أو N/A مبرر |
| Approval type | APR ID أو Not required مع السبب |
| Test ID | Exact source ID أو PROPOSED مع الاعتماد |
| Status | OPEN / APPROVED / REJECTED / N/A |
| Evidence | approval reference + version + audit reference |

لا يُستخدم هذا الجدول لإدخال قرارات افتراضية؛ تُملأ القيم بواسطة المالكين فقط.

## 6. شروط إغلاق حزمة العمليات الـ62

- [ ] مراجعة 16 عملية C01 وتحديد SoR والمالك والاختبارات.
- [ ] مراجعة 12 عملية C02 وحسم حد C02/C03 والأصول.
- [ ] مراجعة 11 عملية C03 وربطها بمصدر الصفحة والتنقل والتفويض.
- [ ] مراجعة 23 عملية C05 وحسم source/change-set/execution/artifact boundaries.
- [ ] حسم تعارض `validateBuildConfiguration` مع مالكي C05/C11.
- [ ] استكمال OWN المؤهل أو N/A المعتمد لكل عملية.
- [ ] ربط كل عملية بمتطلب واختبار قبول محدد أو استثناء معتمد.
- [ ] مراجعة SH dependencies وapproval gates وaudit.
- [ ] تحديث 97 و99 و111 و112 و113 بعد قرارات موثقة فقط.
- [ ] تنفيذ الاختبارات بعد اعتماد المواصفات والسماح المستقل بالبرمجة.

**النتيجة:** حزمة مراجعة جاهزة للمالكين، وليست قرار ملكية. لا تغير هذه الوثيقة أسماء العمليات أو مراكزها أو أدوارها، ولا تنشئ API أو schema أو اختباراً منفذاً.
