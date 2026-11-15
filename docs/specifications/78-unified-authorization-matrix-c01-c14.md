# 78 — مصفوفة التفويض والصلاحيات الموحدة C01–C14

**الحالة:** DERIVED AUTHORIZATION MATRIX / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-15  
**المصادر:** 25، 59–70، 72، 76، 77.  
**الغرض:** توحيد صلاحيات عمليات المراكز وربطها بسياق actor/action/resource/scope، مع تحديد مواضع الموافقة والتدقيق. لا تنشئ هذه الوثيقة أدواراً أو صلاحيات فعلية في النظام ولا تعدل كود التطبيق.

## 1. مبدأ التفويض الإلزامي

كل طلب داخلي يحمل عند الانطباق requestId وcorrelationId وorganizationId وprojectId وactor.type وactor.id وaction وcontractVersion وpayload وفق الوثيقة 25. هذه الحقول سياق مطلوب للتحقق وليست إثباتاً للصلاحية. الخدمة المستقبلة مسؤولة عن:
1. التحقق من هوية الفاعل ومصدرها وحالة الجلسة/الخدمة.
2. تحديد المورد الحقيقي ومالك مصدر الحقيقة.
3. التحقق من تطابق organization/workspace/project/environment scope وعدم قبول scope يقدمه العميل دون تحقق.
4. تقييم action على resource بواسطة خدمة التفويض المركزية SH-02 وفق سياسة C10.
5. التحقق من الموافقة SH-03 إذا كانت العملية عالية الأثر أو تتطلب فصل المهام.
6. تسجيل العمليات والقرارات الحساسة في SH-04.
7. إعادة فحص التفويض عند تنفيذ job مؤجل أو استئناف checkpoint أو استهلاك event؛ لا تكفي صلاحية وقت إنشاء المهمة.
8. تقليل البيانات المعادة وفق أقل صلاحية، وإعادة فحص ACL عند البحث أو تنزيل الملفات أو التصدير.

**قاعدة الرفض الافتراضي:** غياب actor أو scope أو owner أو policy أو قرار التفويض أو الموافقة المطلوبة يؤدي إلى الرفض/التعليق الآمن، لا إلى السماح الضمني.

## 2. أنواع الفاعلين — تصنيف مرشح لا أدوار منشأة

| actor class | المقصود | حدود عامة |
|---|---|---|
| Human Owner | مالك مشروع/مورد أو مفوض رسمي | لا يملك تلقائياً كل موارد المؤسسة؛ التفويض حسب النطاق |
| Human Editor | محرر مفوض | كتابة ضمن الموارد والأفعال الممنوحة |
| Human Reviewer | مراجع مستقل | مراجعة/توصية أو قرار إذا كان مفوضاً صراحة |
| Human Viewer | قارئ | قراءة وفق ACL فقط |
| Security Operator | مشغل/مسؤول أمن | وظائف الأمن وفق فصل المهام؛ لا يملك بيانات المجال لمجرد دوره |
| Service Principal | خدمة نظام موثقة | service-to-service scopes محددة، بلا هوية بشرية منتحلة |
| AI Agent | وكيل AI بإصدار وهوية وسياسة | مهمة ومورد وأفعال ومدة وميزانية محددة؛ لا اعتماد ذاتي |
| External Provider | خدمة خارجية/موصل | صلاحيات تكامل محدودة ومقيدة بالـOAuth/connector policy |

تحتاج أسماء الأدوار النهائية وعضوياتها ووراثتها وتعارض المصالح إلى اعتماد C10 ومالكي المؤسسة.

## 3. أبعاد قرار ABAC/RBAC

| البعد | الحد الأدنى المطلوب | مصدر الحقيقة المقترح |
|---|---|---|
| actor | principal id/type/status/auth context | SH-01 |
| action | فعل مسمى وإصدار عقده | مالك API + سجل أفعال مركزي |
| resource | resource type/id + owner domain | مركز المجال |
| scope | organization/workspace/project/environment/user/session | سياق موحد + مالك النطاق |
| relationship | عضوية/ملكية/مشاركة/assignment | مالك العلاقة + SH-01/02 |
| policy | policy id/version/effective state | C10 |
| grant | allowed actions/resources/scope/expiry | SH-02 |
| approval | request/decision/approver/evidence/version | SH-03 |
| audit | decision/event/correlation/evidence refs | SH-04 |
| context | risk, sensitivity, environment, time, device/session as applicable | سياق موثوق؛ تفاصيله TBD |

لا تُقبل صلاحية مستمدة من إخفاء زر في الواجهة أو من route guard وحده. يجب فرضها على الخادم وعند حدود التخزين والبحث والمهام والتصدير.

## 4. مصفوفة صلاحيات عمليات C01–C14

الاختصارات: **R** قراءة؛ **W** إنشاء/تعديل ضمن النطاق؛ **P** اقتراح فقط؛ **REV** مراجعة؛ **APR** موافقة مستقلة؛ **DENY** ممنوع افتراضياً؛ **COND** مشروط بتفويض/سياسة. جميعها مقترحات تحتاج اعتماداً.

| المركز / العملية أو المجموعة | Human Owner | Editor | Reviewer | Viewer | Service | AI Agent | Approval / Audit |
|---|---|---|---|---|---|---|---|
| C01 createProject / updateProjectProfile | W | W مفوض | R/REV | R | تنفيذ نيابة عن API المالك فقط | P | create/update: audit؛ تغيير عالي الأثر حسب policy |
| C01 saveIdeaVersion / attachProjectReference | W | W مفوض | R | R وفق ACL | SH-06 upload/scan فقط | P | file ACL + audit للربط |
| C01 startDomainAnalysis / analyzeGaps | W/COND | W مفوض | R | R للنتائج المصرح بها | SH-08 job؛ SH-06 evidence | P ضمن task scope | recheck grant/budget؛ لا اعتماد AI |
| C01 create/reviseRequirement / recordNFR | W | W مفوض | REV | R | لا يملك سجل المتطلب | P | baseline المعتمد لا يعدل بصمت |
| C01 submit/decideRequirementBaseline | submit | submit مفوض | REV | R | SH-03 workflow | DENY | APR من مالك/مفوض مستقل؛ SH-04 |
| C02 create/save/validate design | W | W ضمن design scope | REV/R | R | preview job عبر SH-08 | P | concurrency + audit |
| C02 applyDesignProposal | W/COND | W بعد قبول diff | REV | DENY | تنفيذ versioned command | P فقط؛ لا apply ذاتي | موافقة حسب الأثر؛ expected_version |
| C02 submit/decideDesignApproval | submit | submit | REV/COND | R | SH-03 | DENY | APR بشري مخول؛ hash/version ثابتان |
| C03 createPage/createRoute/updateNavigationGraph | W | W مفوض | REV | R | لا يملك حقيقة المسارات | P | تغيير المسار المعتمد عبر change set وموافقة حسب السياسة |
| C03 validateNavigation/analyzeReachability/simulateJourney | W/COND | W مفوض | R | R محدود | SH-08 عند job | P ضمن role context | لا يكشف موارد غير مصرح بها |
| C03 submit/decideNavigationApproval | submit | submit | REV/COND | R | SH-03 | DENY | APR مستقل؛ خريطة/نسخة معتمدة |
| C04 registerDataSource/validateDataSource | W/COND | W مفوض | REV | R للحالة | SH-13 connector/SH-08 job | P؛ لا secrets | secret_ref فقط؛ audit؛ least privilege |
| C04 createSchema/addEntity/addField/defineRelation/Constraint/Index | W | W مفوض | REV | R | API C04 فقط | P | اعتماد schema version؛ لا كتابة عابرة للـtenant |
| C04 defineRowPolicy | submit | DENY افتراضياً | REV | DENY | SH-02 enforcement | DENY | C10 review/approval وفق الأثر |
| C04 previewQuery/createDataBinding | W | W مفوض | R | R منقح | query executor | P ضمن allowlist | safe limit + server ACL + redaction |
| C04 createMigrationPlan/dryRunMigration | W | W مفوض | REV | R | SH-08 execution | P | dry-run evidence؛ audit |
| C04 applyMigration/restore/backup | submit/COND | DENY افتراضياً | REV | R status | SH-08/SH-15/DB provider | DENY | APR للإنتاج/الاستعادة؛ فصل مقدم الطلب والمنفذ/المعتمد |
| C05 readProjectFile/listFiles | R | R | R | R ACL | storage service | R فقط لملفات المهمة | SH-06 ACL/redaction |
| C05 proposeFileChange/proposeNaturalLanguagePatch | W | W مفوض | REV | DENY | C05 command | P/patch ضمن allowlist | base hash + diff + audit |
| C05 applyApprovedChangeSet | submit/COND | W بعد approval | REV | DENY | C05 executor | DENY ذاتياً | approval_ref + expected_hash |
| C05 generateIR/generateCode/diagnostics | W/COND | W مفوض | R | R للنتائج | SH-08 job | تنفيذ داخل مهمة محددة فقط | source baseline approved؛ budget/sandbox policy |
| C05 sandbox/network/Dependency update | submit | COND | REV | DENY | isolated runner | DENY للشبكة افتراضياً | network exception / high impact APR؛ audit |
| C05 GitHub PR / code approval / merge | COND | COND حسب repo | REV | R | GitHub provider | لا merge ولا self-approval | branch rules + independent review |
| C06 registerAgent/Skill/ToolManifest | W/COND | W مفوض | REV | R | registry service | DENY | مراجعة أمنية قبل التفعيل |
| C06 requestToolGrant/assignAgents | submit | submit مفوض | REV | R | SH-02/03 | DENY grant لنفسه | APR least privilege/duration/scope |
| C06 create/plan/execute/pause/resume/cancel AI task | W/COND | COND | R/REV | R status | SH-08 execution | ضمن task assignment/grants | recheck policy, budget, deadline؛ SH-04 |
| C06 submit/review output / resolve conflict | submit | submit | REV/COND | R | SH-03 | submit only | لا self-approval؛ مالك المورد يحسم conflict |
| C06 register/disable provider / select model / usage | W/COND | DENY للسر | REV | R status | SH-13/SH-11 | لا اختيار خارج allowlist | secret manager؛ budget/policy/audit |
| C07 createTestPlan/TestCase/linkRequirement | W | W مفوض | REV | R | C07 API | P | كل test مرتبط بbaseline/requirement |
| C07 create/execute TestRun/Suite / recordDefect | W/COND | W مفوض | R/REV | R نتائج منقحة | SH-08 runner | تنفيذ محدود إن فوض | evidence hash + build/environment |
| C07 evaluateQualityGate/requestWaiver | submit | submit | REV | R | SH-03 | DENY | waiver محدود ومؤقت؛ APR مستقل |
| C07 approveTestResults/close defect after retest | submit | submit | REV/COND | R | SH-08 | DENY | independent reviewer؛ retest evidence |
| C08 createAssetRecord/createVersion/linkAsset | W | W مفوض | REV | R ACL | SH-06 upload/storage only | P؛ لا حذف | صلاحية على الأصل والهدف؛ audit |
| C08 inspect/download/export | W/COND | COND | R/COND | R metadata only | SH-06 signed access | DENY افتراضياً أو task-scoped | ACL at access time + export policy |
| C08 archive/delete/retention change | submit | DENY افتراضياً | REV | DENY | SH-06 executes approved storage action | DENY | APR مستقل عند retention/legal hold؛ SH-04 |
| C09 register/configure integration / mapping | W/COND | W مفوض | REV | R | SH-13 connector lifecycle | P | secret_ref only; audit |
| C09 begin/complete authorization / rotate secret | submit/COND | DENY افتراضياً | REV | DENY | SH-13/Secret Manager | DENY | verified OAuth flow; never expose secret |
| C09 startSync / webhook / revokeConnection | submit/COND | COND | REV | R status | SH-08/09/13 | DENY افتراضياً | external-write approval where needed; replay/idempotency |
| C10 policy/risk/threat/privacy authoring | W | COND | REV | R sanitized | policy service | P only | C10 governance; policy version |
| C10 decideSecurityException / requestPolicyChange | submit | submit | REV | R status | SH-03 | DENY | APR independent, expiry, reason, audit |
| C10 suspendAgentCapability / closeFinding | W/COND | DENY | REV | R sanitized | SH-02 enforcement | DENY | independent evidence for closure |
| C10 exportAuditEvidence / incident | submit/COND | DENY | REV | DENY | SH-04 | DENY | approval, scope, redaction; immutable source |
| C11 validateBuildProfile/requestBuild/getBuildResult | W | W مفوض | R/REV | R sanitized | SH-08 runner | P/execute within grant | source permission; redact logs |
| C11 attestArtifact/createReleaseCandidate | W/COND | COND | REV | R | registry TBD | P only | provenance + checks + version |
| C11 submit/approveRelease | submit | submit | REV/COND | R | SH-03 | DENY | independent human APR |
| C11 deploy/promote/rollback | submit/COND | DENY افتراضياً | REV | R status | SH-08/14/15 | DENY | valid approval + target env policy + postchecks |
| C12 create/version/linkSourceEvidence | W | W مفوض | REV | R ACL | SH-06 binary | P draft only | provenance; source ACL |
| C12 generateDraft/submitReview/decide/publish | submit | submit | REV/COND | R published | SH-03 | draft only | human approval; immutable approved version |
| C12 search/compare/export/archive | COND | COND | R/COND | R ACL | SH-07/16 query/index | task-scoped R | recheck ACL on retrieval/export |
| C13 readEffectiveSettings/diagnose | R | R | R | R allowed | SH-12 values | R only task-needed | show inheritance source; redact sensitive values |
| C13 updateUserPreference | W self | W self | R | R self | SH-12 | DENY | allowlist; audit where policy requires |
| C13 requestScopedSettingChange/applyOwnerSettingChange | submit | submit | REV | R status | owner API + SH-12 | P only | owner decides semantics; approval for sensitive keys |
| C13 decide/rollback/export settings | submit/COND | DENY | REV | R sanitized | SH-03/12 | DENY | independent APR; exclude secret values |
| C14 dashboard queries/projections | R | R | R | R scoped | query services | R only if granted | source ACL/freshness; C14 read-only |
| C14 saveLayout/pinWidget | W own layout | W own layout | W own layout | W own layout | C14 API | DENY | layout permission only; cannot change source ACL |
| C14 exportDashboardReport | submit/COND | COND | REV | R only if allowed | SH-06/08 | DENY default | export policy + source ACL + audit |

## 5. قرارات حساسة تستلزم موافقة مستقلة أو إعادة تحقق

| الفئة | شرط الموافقة/إعادة التحقق | صاحب القرار المقترح | خدمة التشغيل |
|---|---|---|---|
| اعتماد خط أساس متطلبات/تصميم/تنقل/كود/وثيقة | نسخة محددة + hash + evidence + reviewer مستقل | مالك المجال/المشروع المفوض | SH-03 |
| تغيير سياسة/استثناء أمني أو تفويض وكيل | سبب ونطاق وفترة انتهاء وتحليل أثر | C10/مسؤول مخول مستقل | SH-02/03/04 |
| تغيير schema أو migration إنتاج | dry-run + rollback/restore plan + اختبار + موافقة مالك البيانات | مالك البيانات + مراجعة أمن عند الحاجة | C04 + SH-08/15 |
| تشغيل كود أو Sandbox بشبكة | allowlist + موارد + صورة runtime + مصدر موثوق + موافقة استثناء | مالك المشروع/الأمن حسب السياسة | C05 + SH-08 |
| أدوات AI أو مزود/ميزانية عالية الأثر | tool grant محدد، budget، deadline، scope، model allowlist | مفوض بشري غير الوكيل | SH-02/03/11 |
| مزامنة/كتابة إلى خدمة خارجية | preview mapping، نطاق، direction، idempotency، موافقة عند الأثر الخارجي | مالك التكامل/البيانات | C09 + SH-08/13 |
| نشر/ترقية/تراجع | artifact provenance، QA/security gates، approval، target environment | مالك الإصدار/المفوض | C11 + SH-03/08 |
| حذف/تصدير/تغيير احتفاظ | retention/legal hold/classification/recipient verified | مالك البيانات + privacy/security حسب السياسة | C08/10 + SH-04/06 |

## 6. متطلبات سجل التدقيق

يسجل SH-04، بحسب السياسة، على الأقل:
- actor principal/type، delegated actor/agent version عند الانطباق.
- action، resource type/id، owner domain، organization/project scope.
- policy/version، decision allow/deny، reason code، grant reference.
- approval request/decision/approver، approved resource version/hash.
- requestId/correlationId، contractVersion، event/job references.
- occurred_at وrecorded_at مع semantics موحدة.
- before/after version/hash أو change-set reference دون نسخ أسرار/بيانات زائدة.
- source/provenance/evidence references وسبب الاستثناء ومدته.
- نتيجة العملية وفشلها وإعادة محاولتها، مع redaction للأسرار.

لا يملك أي مركز أو وكيل تعديل سجل التدقيق المركزي أو حذف آثاره. تفاصيل append-only والتخزين والتوقيع والاحتفاظ تحتاج تصميم واعتماداً مستقلاً.

## 7. اختبارات قبول المصفوفة — مواصفات غير منفذة

| ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| AUTH-78-01 | تزوير organizationId/projectId في الطلب | رفض خادمي حتى لو كانت الواجهة تسمح | SPECIFIED / NOT RUN |
| AUTH-78-02 | مستخدم يقرأ مورداً من tenant آخر | رفض دون كشف وجود المورد | SPECIFIED / NOT RUN |
| AUTH-78-03 | Viewer يستدعي Command مباشرة | رفض من API وليس UI فقط | SPECIFIED / NOT RUN |
| AUTH-78-04 | AI يستخدم أداة خارج grant/expiry/scope | رفض وتدقيق | SPECIFIED / NOT RUN |
| AUTH-78-05 | AI يحاول اعتماد مخرجاته أو تعديل audit | رفض | SPECIFIED / NOT RUN |
| AUTH-78-06 | مهمة مؤجلة بعد سحب التفويض | إعادة تقييم قبل التنفيذ ومنع الإجراء | SPECIFIED / NOT RUN |
| AUTH-78-07 | قرار approval لنسخة hash قديمة | رفض الموافقة أو طلب نسخة جديدة | SPECIFIED / NOT RUN |
| AUTH-78-08 | تصدير بحث/ملف بعد سحب ACL | منع التنزيل/التصدير عند نقطة الوصول | SPECIFIED / NOT RUN |
| AUTH-78-09 | Event replay أو webhook مكرر | idempotent ومنع تكرار الأثر | SPECIFIED / NOT RUN |
| AUTH-78-10 | Service principal يستخدم صلاحية أوسع من مهمته | رفض أقل صلاحية | SPECIFIED / NOT RUN |
| AUTH-78-11 | موافق هو منشئ التغيير الحساس | رفض عند اشتراط فصل المهام | SPECIFIED / NOT RUN |
| AUTH-78-12 | فشل SH-02 أو SH-03 أو SH-04 | fail closed للعمليات الحساسة وعدم تخطي الموافقة/التدقيق | SPECIFIED / NOT RUN |
| AUTH-78-13 | C14 يحاول كتابة حقيقة مجال | رفض/لا يوجد command surface | SPECIFIED / NOT RUN |
| AUTH-78-14 | route guard يسمح لكن API يرفض | لا تتسرب بيانات؛ API هو المرجع | SPECIFIED / NOT RUN |
| AUTH-78-15 | تغيير الدور/العضوية أثناء الجلسة | إعادة تقييم وفق السياسة والجلسة | SPECIFIED / NOT RUN |

لا توجد نتائج تنفيذ أو test run IDs أو artifacts لهذه الحالات.

## 8. فجوات تمنع الاعتماد النهائي

- لا توجد أسماء أدوار نهائية أو عضويات أو قواعد وراثة معتمدة.
- لا توجد مصفوفة موحدة مكتملة للأفعال والموارد والنطاقات في سجل C10.
- تفاصيل actor/session claims وservice identity وdelegation تحتاج تثبيتاً.
- حالات approval وquorum وseparation-of-duties وexpiry/cancellation تحتاج اعتماداً.
- سياسة fail-open/fail-closed لكل خدمة غير موثقة بالكامل؛ توصي هذه المسودة fail-closed للعمليات الحساسة.
- تعريفات classification/retention/legal basis غير مكتملة.
- تفاصيل التوقيع والتشفير والاحتفاظ لسجل SH-04 غير معتمدة.
- تعارض Zero Trust/SEC-REV-01 ما زال مفتوحاً.
- لا تُحدّث إحصاءات المحاور الـ64 قبل مراجعة تقاريرها وإعادة الحساب.

## 9. بوابة الاعتماد

يلزم توقيع مالك كل مركز متأثر ومالكي SH-01/02/03/04، ومراجعة C10، ثم توثيق القرار والإصدار والأثر. هذه الوثيقة لا تعدل 36 ولا تغلق OWN-01..10 أو FLD-01..26. لا يبدأ تنفيذ RBAC/ABAC أو APIs أو تغيير كود التطبيق قبل اعتماد المواصفات وموافقة مستقلة صريحة.

**AUTHORIZATION MATRIX CANDIDATE / OPEN FOR OWNER REVIEW / NOT APPROVED / NO IMPLEMENTATION.**


## ملحق 82 — إحالات الملكية المؤهلة

عند ربط عمليات التفويض بقرارات الملكية، استخدم معرفات OWN72-nn عند الإشارة إلى حزمة قرارات 72/76، ومعرفات OWN36-nn عند الإشارة إلى السجل التاريخي في 36. يمنع استخدام OWN-nn منفرداً. راجع الوثيقة 82 لسجل المطابقة. لا يغلق هذا الملحق أي قرار ولا ينشئ صلاحيات منفذة؛ تبقى المصفوفة مرشحاً غير معتمد والاختبارات NOT RUN.
