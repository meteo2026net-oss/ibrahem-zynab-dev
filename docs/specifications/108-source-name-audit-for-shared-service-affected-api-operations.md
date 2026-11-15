# 108 — تدقيق المطابقة المصدرية لأسماء عمليات API في المراكز المتقاطعة مع SH-08/13/14

**الحالة:** SOURCE-NAME AUDIT / SEMANTIC CROSSWALK CANDIDATE / OWNER REVIEW REQUIRED / NOT APPROVED  
**التاريخ:** 2026-11-15  
**المصدر:** النص الكامل المستخرج من DOCX الرئيسي داخل Artifact `14140098015`؛ locator الأسطر 1-based في `master-approved-specification.txt`.  
**النطاق:** عمليات C04 وC06 وC07 وC09 وC11 وC14 المدرجة في 97 (95 عملية).

## 1. نتيجة البحث الحرفي

تم فحص أسماء العمليات الـ95 المدرجة في الوثيقة 97 مقابل النص المستخرج كاملاً، مع البحث عن اسم العملية كما هو حرفياً وحساساً لحالة الأحرف. النتيجة:

| النتيجة | العدد |
|---|---:|
| أسماء مطابقة حرفياً | 5 |
| أسماء لم تظهر حرفياً | 90 |
| إجمالي العمليات المفحوصة | 95 |

عدم ظهور الاسم حرفياً لا يعني عدم وجود المتطلب الوظيفي؛ قد يكون موصوفاً بالعربية أو باسم مختلف في نموذج عقد الفصل 135. لكنه يعني أن الاسم المستخدم في 97 ليس اسماً حرفياً مستعاداً من DOCX، ويجب وسمه `DERIVED-NAME` حتى يثبت مالك العقد خلاف ذلك.

## 2. المطابقات الحرفية المثبتة

| Operation ID في 97 | الاسم | locator المصدر | القسم المصدر | الحكم |
|---|---|---:|---|---|
| OP-C04-049 | `validateSchema` | 11276–11283، وتحديداً 11279 | 135.6 نموذج عقد مركز البيانات | EXACT-NAME |
| OP-C07-107 | `createTestPlan` | 11310–11317، وتحديداً 11312 | 135.8 نموذج عقد مركز الاختبارات | EXACT-NAME |
| OP-C07-110 | `createTestRun` | 11310–11317، وتحديداً 11313 | 135.8 نموذج عقد مركز الاختبارات | EXACT-NAME |
| OP-C07-112 | `executeTestSuite` | 11310–11317، وتحديداً 11314 | 135.8 نموذج عقد مركز الاختبارات | EXACT-NAME |
| OP-C11-159 | `createReleaseCandidate` | 11328–11337، وتحديداً 11334 | 135.9 نموذج عقد البناء والنشر | EXACT-NAME |

هذه المطابقة تثبت الاسم في نموذج العقد المصدر، لكنها لا تثبت أن عملية API نُفذت أو أن schema أو صلاحياتها أو مالكها النهائي اعتمد.

## 3. أسماء مصدرية قريبة تحتاج قرار تطابق دلالي

| اسم العملية المرشح في 97 | الاسم الوارد في DOCX | locator | نوع العلاقة المرشحة | ما يلزم حسمه |
|---|---|---:|---|---|
| OP-C04-054 `createMigrationPlan` | `generateMigrationPlan` | 11276–11283، السطر 11281 | تشابه دلالي محتمل، لا تطابق حرفي | هل create وgenerate يمثلان الأمر نفسه أم أن أحدهما ينشئ مسودة والآخر يولد خطة؟ |
| OP-C07-117 `evaluateQualityGate` | `validateQualityGate` | 11310–11317، السطر 11316 | تشابه دلالي محتمل | هل evaluate ينتج قراراً وvalidate يفحص شروطاً فقط؟ |
| OP-C07-120 `exportQualityReport` | `submitQualityReport` | 11310–11317، السطر 11317 | ليس تطابقاً؛ عمليتان مختلفتان غالباً | لا تدمج التصدير والإرسال؛ يلزم إبقاءهما منفصلتين أو تصحيح المصدر الوسيط |
| OP-C11-156 `requestBuild` | `prepareBuild` / `createBuildJob` | 11328–11337، السطران 11330 و11332 | تشابه وظيفي جزئي | فصل طلب البناء عن تحضير ملف البناء وإنشاء job |
| OP-C11-162 `deployRelease` | `executeApprovedDeployment` | 11328–11337، السطر 11336 | تشابه دلالي محتمل | تضمين شرط الموافقة في اسم/عقد التنفيذ |
| OP-C11-165 `requestRollback` | `rollbackDeployment` | 11328–11337، السطر 11337 | تشابه جزئي | التمييز بين طلب التراجع وتنفيذه |
| OP-C11-164 `runPostDeployChecks` | وصف `التحقق بعد النشر` | 11338–11346، السطر 11345 | دعم مفهومي بلا اسم API | يلزم عقد مستقل أو توضيح إن كان جزءاً من execute deployment |

لا تعتبر أي علاقة في الجدول أعلاه مطابقة معتمدة. الاسم الأقرب قد يحمل lifecycle مختلفاً أو side effect مختلفاً.

## 4. تغطية نماذج العقود المصدرية

### C04 — الفصل 135.6
المصدر يذكر صراحةً:
- `getSchema`
- `validateSchema`
- `getDataBindings`
- `generateMigrationPlan`
- `validateMigrationPlan`
- `getApprovedDataContracts`

الموضع: الأسطر 11276–11289. من قائمة 23 عملية C04 في 97، لا يطابق حرفياً سوى `validateSchema`. بقية العمليات في 97 مشتقة من العقود الوظيفية 61/العقد الوسيط، ولا يجوز وصفها بأنها قائمة API أصلية كاملة من الفصل 135.

### C07 — الفصل 135.8
المصدر يذكر صراحةً:
- `createTestPlan`
- `createTestRun`
- `executeTestSuite`
- `getTestResults`
- `validateQualityGate`
- `submitQualityReport`

الموضع: الأسطر 11310–11327. من قائمة 14 عملية C07 في 97، ثلاث أسماء مطابقة حرفياً. أما `evaluateQualityGate` و`exportQualityReport` فتحتاجان قراراً يمنع دمج عمليتين مختلفتين.

### C11 — الفصل 135.9
المصدر يذكر صراحةً:
- `prepareBuild`
- `validateBuildConfiguration`
- `createBuildJob`
- `getBuildStatus`
- `createReleaseCandidate`
- `requestDeploymentApproval`
- `executeApprovedDeployment`
- `rollbackDeployment`

الموضع: الأسطر 11328–11346. من قائمة 12 عملية C11 في 97، `createReleaseCandidate` فقط مطابقة حرفياً؛ توجد أسماء قريبة لطلب البناء والنشر والتراجع، لكن يجب الحفاظ على فصل lifecycle الذي يصرح به المصدر: طلب البناء، تنفيذه، نجاحه، اعتماد الإصدار، الموافقة على النشر، تنفيذه، والتحقق بعد النشر.

### C06 وC09 وC14
لا يقدم الجزء المستعاد من الفصل 135 نموذج عقد API مستقلاً لهذه المراكز. الفصول 25 و28 و129 تقدم متطلبات ووظائف ومكونات بيانات ومعايير قبول، لكنها لا تثبت أسماء الـoperations الـ21 لـC06 أو الـ12 لـC09 أو الـ13 لـC14 الواردة في 97. يجب أن تبقى هذه الأسماء `DERIVED-NAME / OWNER REVIEW` إلى أن يثبتها مالك المجال أو مصدر معتمد آخر.

## 5. أثر النتيجة على سجل 97 و99

1. يجب إضافة حقل `source_name_status` لكل عملية: `EXACT-NAME`, `SEMANTIC-CANDIDATE`, `DERIVED-NAME`, أو `NOT-ASSESSED`.
2. يجب إضافة `source_locator` للفقرات المصدرية التي تذكر العملية نفسها، لا locator عام للفصل فقط.
3. لا يجوز تحويل تشابه الاسم إلى دمج عمليتين أو تغيير النوع Query/Command/Job/Approval دون قرار.
4. لا يزال `API Owner` و`Domain SoR` و`Qualified OWN` و`Exact Test ID` مستقلة؛ لا تستنتج من مطابقة الاسم.
5. لا يثبت وجود عملية في DOCX أنها منفذة؛ لا توجد أدلة تشغيل أو commit/build/test run في هذا التدقيق.
6. لا تغلق هذه الوثيقة RI-94-01 أو RI-94-08 أو GAP-020/027؛ لكنها تحدد بدقة أين تكون أسماء العمليات مشتقة من العقود الوسيطة.

## 6. الخطوة التالية

- مراجعة أسماء العمليات الـ95 مع مالكي C04/C06/C07/C09/C11/C14.
- فصل عمليات lifecycle التي يميزها الفصل 135.9 بدلاً من دمج request/execute/approve/verify.
- توثيق source locator دقيق لكل عملية ذات دعم دلالي.
- تثبيت عمليات C06/C09/C14 من مالكيها أو إبقاؤها مقترحة.
- إسناد exact Test ID أو N/A مبرر لكل عملية بعد اعتماد العقد واختبار oracle.

**بوابة التنفيذ:** تدقيق منشأ أسماء فقط؛ لا ينشئ API أو schema أو endpoint، ولا يعدل كود التطبيق ولا يمنح إذناً ببدء البرمجة.


## 7. قائمة العمليات الـ90 التي لم يظهر اسمها حرفياً

هذه القائمة هي نتيجة فحص حرفي للأسماء كما وردت في 97. لا تعني أن الوظائف غائبة عن المصدر؛ لكنها تمنع وصف الأسماء نفسها بأنها SOURCE-VERBATIM.

### C04 — 22 اسماً غير مطابق
- OP-C04-040 `registerDataSource`
- OP-C04-041 `validateDataSource`
- OP-C04-042 `createSchema`
- OP-C04-043 `addEntity`
- OP-C04-044 `addField`
- OP-C04-045 `defineRelation`
- OP-C04-046 `defineConstraint`
- OP-C04-047 `defineIndex`
- OP-C04-048 `defineRowPolicy`
- OP-C04-050 `previewQuery`
- OP-C04-051 `createDataBinding`
- OP-C04-052 `generateTestFixtures`
- OP-C04-053 `configureSync`
- OP-C04-054 `createMigrationPlan`
- OP-C04-055 `dryRunMigration`
- OP-C04-056 `applyMigration`
- OP-C04-057 `createRollbackPlan`
- OP-C04-058 `startBackup`
- OP-C04-059 `requestRestore`
- OP-C04-060 `verifyRestore`
- OP-C04-061 `submitSchemaForApproval`
- OP-C04-062 `decideSchemaApproval`

### C06 — 21 اسماً غير مطابق
`registerAgent`, `createAgentVersion`, `registerSkill`, `registerToolManifest`, `requestToolGrant`, `createAITask`, `planAITask`, `assignAgents`, `executeTaskStep`, `pauseTask`, `resumeTask`, `cancelTask`, `submitAgentOutput`, `reviewAgentOutput`, `resolveAgentConflict`, `evaluateAgent`, `registerAIProvider`, `validateAIProvider`, `selectModelForTask`, `recordProviderUsage`, `disableAIProvider`.

المعرفات: OP-C06-086..OP-C06-106.

### C07 — 11 اسماً غير مطابق
`createTestCase`, `linkTestToRequirement`, `executeTestCase`, `recordDefect`, `transitionDefect`, `retestDefect`, `calculateCoverage`, `evaluateQualityGate`, `requestQualityWaiver`, `approveTestResults`, `exportQualityReport`.

المعرفات: OP-C07-108، OP-C07-109، OP-C07-111، OP-C07-113..OP-C07-120.

### C09 — 12 اسماً غير مطابق
`registerIntegration`, `configureIntegration`, `beginAuthorization`, `completeAuthorization`, `validateConnection`, `testMapping`, `startSync`, `receiveWebhook`, `pauseIntegration`, `revokeConnection`, `rotateSecretReference`, `getIntegrationStatus`.

المعرفات: OP-C09-132..OP-C09-143.

### C11 — 11 اسماً غير مطابق
`validateBuildProfile`, `requestBuild`, `getBuildResult`, `attestArtifact`, `submitReleaseForApproval`, `approveRelease`, `deployRelease`, `promoteEnvironment`, `runPostDeployChecks`, `requestRollback`, `getReleaseStatus`.

المعرفات: OP-C11-155..OP-C11-158 وOP-C11-160..OP-C11-166.

### C14 — 13 اسماً غير مطابق
`getDashboardOverview`, `getCenterSummary`, `getMyTasksOverview`, `getApprovalQueue`, `getQualityOverview`, `getSecurityOverview`, `getIntegrationHealth`, `getReleaseOverview`, `getOperationalHealth`, `saveDashboardLayout`, `pinWidget`, `exportDashboardReport`, `invalidateProjection`.

المعرفات: OP-C14-189..OP-C14-201.

## 8. أسماء API الموجودة في DOCX ولكنها لا تظهر بالاسم نفسه في قائمة 97

هذه فجوة مقابلة مهمة: بعض أسماء العمليات الأصلية في نماذج الفصل 135 لم تُدرج بالاسم نفسه في 97.

| المركز | الاسم المصدر الأصلي غير الممثل حرفياً في 97 | locator |
|---|---|---:|
| C04 | `getSchema` | 11278 |
| C04 | `getDataBindings` | 11280 |
| C04 | `generateMigrationPlan` | 11281 |
| C04 | `validateMigrationPlan` | 11282 |
| C04 | `getApprovedDataContracts` | 11283 |
| C07 | `getTestResults` | 11315 |
| C07 | `validateQualityGate` | 11316 |
| C07 | `submitQualityReport` | 11317 |
| C11 | `prepareBuild` | 11330 |
| C11 | `validateBuildConfiguration` | 11331 |
| C11 | `createBuildJob` | 11332 |
| C11 | `getBuildStatus` | 11333 |
| C11 | `requestDeploymentApproval` | 11335 |
| C11 | `executeApprovedDeployment` | 11336 |
| C11 | `rollbackDeployment` | 11337 |

لا يعني غياب الاسم من 97 أن العملية غير موجودة في أي مكان؛ بل يعني أن سجل 97 لا يعكس قائمة الفصل 135 حرفياً. يجب على مالكي C04/C07/C11 تقرير: هل تضاف الأسماء المصدرية كما هي؟ أم يُحتفظ بأسماء مشتقة مع crosswalk واضح؟ أم تفصل العمليات التي دمجت في مسودة 97؟

## 9. الإجراء التصحيحي المطلوب

1. مراجعة قائمة 97 مقابل أسماء الفصل 135، لا الاكتفاء بإضافة ملاحظة provenance.
2. عدم حذف أي عملية مشتقة تلقائياً؛ يجب الحفاظ على تاريخها وقرار سبب الإبقاء/الدمج/التقسيم.
3. إنشاء crosswalk source_operation → candidate_operation → owner decision.
4. فصل عمليات C11 request/prepare/create/execute/verify، وفصل C07 validate/evaluate/export/submit.
5. تحديد API names الأصلية لمراكز C06/C09/C14 من مصدر معتمد أو قرار مالك موثق؛ لا يُخترع مصدر مفقود.
6. بعد اعتماد الأسماء، استكمال schema/version/authz/error/idempotency/test links.

**الحالة:** جميع الفروقات تتطلب مراجعة مالكي المراكز؛ لا يوجد rename أو merge أو حذف معتمد في هذه الجولة.
