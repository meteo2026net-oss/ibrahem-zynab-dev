# 79 — سجل عقود API والأحداث والموافقات C01–C14

**الحالة:** CONTRACT REGISTRY DRAFT / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-15  
**المصادر:** 25، 59–70، 72، 76، 78.  
**الحدود:** أسماء العمليات والأحداث هنا منطقية مقترحة أو مستعادة من مسودات العقود، وليست endpoint paths منشورة أو schemas نهائية. لا توجد خدمات أو topics أو queues منشأة بهذه الوثيقة.

## 1. قواعد العقد الموحد

تعتمد كل واجهة على نوع واضح: Query أو Command أو Event أو Approval أو File أو Workflow أو Streaming. لا تستخدم الواجهة الأمامية أو أي مركز قاعدة بيانات مركز آخر مباشرة؛ يتم التفاعل عبر عقد المالك.

### 1.1 سياق الطلب المستعاد من الوثيقة 25

| الحقل | المعنى | الحالة |
|---|---|---|
| requestId | معرف الطلب | مستعاد؛ format/uniqueness TBD |
| correlationId | ربط سلسلة التفاعل | مستعاد؛ propagation TBD |
| organizationId | سياق المؤسسة | مستعاد؛ يتحقق منه الخادم |
| projectId | سياق المشروع عند الانطباق | مستعاد؛ يتحقق منه الخادم |
| actor.type / actor.id | نوع الفاعل ومعرفه | مستعاد؛ principal schema TBD |
| action | اسم العملية | مستعاد؛ registry/version TBD |
| contractVersion | إصدار العقد | مستعاد؛ compatibility policy TBD |
| payload | بيانات العملية | schema لكل عملية TBD |

الحقول الإضافية المقترحة: resourceRef, scope, idempotencyKey, expectedVersion, sourceRef, provenanceRef, approvalRef, deadline, budgetRef, traceContext. هذه إضافات مشتقة تحتاج اعتماداً.

### 1.2 نتيجة Query/Command

مقترح envelope موحد:
- success: result أو resourceRef/versionRef.
- failure: code, message, retryable, correlationId, fieldErrors?, policyDecisionRef?.
- metadata: contractVersion, resourceVersion?, sourceRefs?, freshness/asOf? عند الانطباق.

لا تُرسل stack traces أو الأسرار أو محتوى غير مصرح به للعميل. أسماء error codes والحقول النهائية TBD.

### 1.3 ضوابط الكتابة

- Command يحدد المورد والمالك والنطاق والفاعل والفعل.
- expectedVersion/baseHash مطلوب عند التعديل المتزامن أو patch.
- idempotencyKey مطلوب للعمليات القابلة لإعادة المحاولة أو ذات الأثر الخارجي.
- أي تغيير حساس ينشئ change set/version جديداً؛ لا تعديل صامت لإصدار معتمد.
- لا تعاد المحاولة تلقائياً لعملية غير idempotent.
- التفويض يعاد تقييمه عند execution time، لا عند submit فقط.

## 2. سجل عمليات API المقترحة حسب المركز

| المركز | Queries / Commands / Jobs / Approvals المذكورة في العقد | ضوابط حاكمة |
|---|---|---|
| C01 | createProject; updateProjectProfile; saveIdeaVersion; attachProjectReference; startDomainAnalysis; getAnalysisResult; upsertPersona; createRequirement; reviseRequirement; recordNFR; analyzeGaps; answerClarification; proposeTechnologyOptions; createEstimate; submitRequirementBaseline; decideRequirementBaseline | project scope؛ AI draft only؛ version checks؛ baseline approval مستقل |
| C02 | createDesignWorkspace; createDesignDocument; getDesignDocument; saveDesignChangeSet; validateDesignDocument; createComponentDefinition; createDesignPreview; requestDesignAIProposal; applyDesignProposal; submitDesignForApproval; decideDesignApproval; getApprovedDesignVersion | base_version/client_change_id؛ proposal لا يطبق تلقائياً؛ approval على version/hash |
| C03 | createPage; renamePage; createRoute; updateNavigationGraph; validateNavigationGraph; analyzeReachability; createDeepLinkRule; simulateUserJourney; submitNavigationForApproval; decideNavigationApproval; getApprovedNavigation | لا تعديل لمسار معتمد دون diff/approval؛ الخادم يفرض حراس الوصول |
| C04 | registerDataSource; validateDataSource; createSchema; addEntity; addField; defineRelation; defineConstraint; defineIndex; defineRowPolicy; validateSchema; previewQuery; createDataBinding; generateTestFixtures; configureSync; createMigrationPlan; dryRunMigration; applyMigration; createRollbackPlan; startBackup; requestRestore; verifyRestore; submitSchemaForApproval; decideSchemaApproval | no raw secrets؛ tenant-safe relations؛ production migration/restore approval؛ dry-run/evidence |
| C05 | initializeCodeWorkspace; listProjectFiles; readProjectFile; proposeFileChange; applyApprovedChangeSet; generateIR; validateIR; generateCodeFromDesign; generateCodeFromRequirements; getGenerationStatus; analyzeDependencies; proposeDependencyUpdate; runCodeDiagnostics; proposeNaturalLanguagePatch; compareDesignAndCode; createSandboxRun; cancelSandboxRun; createBuildPreparation; validateBuildConfiguration; createGitHubChangeRequest; requestCodeReview; approveCodeBaseline; prepareCodeHandoff | approved baselines؛ allowlisted paths؛ isolated sandbox؛ no network by default؛ no self-approval |
| C06 | registerAgent; createAgentVersion; registerSkill; registerToolManifest; requestToolGrant; createAITask; planAITask; assignAgents; executeTaskStep; pauseTask; resumeTask; cancelTask; submitAgentOutput; reviewAgentOutput; resolveAgentConflict; evaluateAgent; registerAIProvider; validateAIProvider; selectModelForTask; recordProviderUsage; disableAIProvider | scope/budget/deadline؛ least privilege؛ grant expiry؛ project memory isolation؛ human review |
| C07 | createTestPlan; createTestCase; linkTestToRequirement; createTestRun; executeTestCase; executeTestSuite; recordDefect; transitionDefect; retestDefect; calculateCoverage; evaluateQualityGate; requestQualityWaiver; approveTestResults; exportQualityReport | build/environment/test version evidence؛ independent reviewer؛ waiver expiry؛ QA != release approval |
| C08 | createAssetRecord; requestUpload; finalizeUpload; inspectAsset; createAssetVersion; linkAsset; requestTransformation; revokeAssetLink; archiveAsset; requestExport; requestDeletion | C08 domain registry vs SH-06 storage; ACL at access time; deletion/retention approval |
| C09 | registerIntegration; configureIntegration; beginAuthorization; completeAuthorization; validateConnection; testMapping; startSync; receiveWebhook; pauseIntegration; revokeConnection; rotateSecretReference; getIntegrationStatus | no secret value; OAuth/redirect verification; signed webhook/replay protection; external write approval |
| C10 | evaluatePolicy; registerSecurityFinding; createThreatModel; assessPrivacyImpact; requestPolicyChange; decideSecurityException; suspendAgentCapability; reviewAgentRun; recordIncident; closeFinding; exportAuditEvidence | SH-02 decision runtime; SH-04 audit source; exception bounded/expiring; independent closure evidence |
| C11 | validateBuildProfile; requestBuild; getBuildResult; attestArtifact; createReleaseCandidate; submitReleaseForApproval; approveRelease; deployRelease; promoteEnvironment; runPostDeployChecks; requestRollback; getReleaseStatus | artifact provenance; quality/security gates; approval; environment policy; rollback impact review |
| C12 | createKnowledgeDocument; createDocumentVersion; linkSourceEvidence; generateDocumentationDraft; submitDocumentReview; decideDocumentReview; publishKnowledgeDocument; searchKnowledge; compareDocumentVersions; exportKnowledgeBundle; archiveKnowledgeDocument; reconcileDocumentation | source/version provenance; approved versions immutable; ACL recheck at search/export |
| C13 | getEffectiveSettings; validateSettingValue; updateUserPreference; requestScopedSettingChange; decideSettingChange; applyOwnerSettingChange; compareSettingVersions; rollbackSettingVersion; exportNonSecretSettings; diagnoseInheritance | owner API writes domain settings; SH-12 values; expected_version; no secret export |
| C14 | getDashboardOverview; getCenterSummary; getMyTasksOverview; getApprovalQueue; getQualityOverview; getSecurityOverview; getIntegrationHealth; getReleaseOverview; getOperationalHealth; saveDashboardLayout; pinWidget; exportDashboardReport; invalidateProjection | read projections only; source ACL/freshness; layout writes only; no domain mutation |

هذه القائمة لا تحول أسماء العمليات إلى endpoints نهائية؛ يجب على كل مالك API اعتماد الاسم والpayload والresponse والأخطاء والمهلة وسياسة التوافق.

## 3. سجل الأحداث المقترحة

| المجال | أمثلة أحداث واردة في العقود | المنتج | مستهلكون محتملون وفق العقود |
|---|---|---|---|
| C02/C03 | DesignWorkspaceCreated; DesignDocumentVersionSaved; DesignValidationCompleted; DesignApprovalSubmitted; DesignApproved; PageRegistered; RouteDefinitionChanged; NavigationDiagnosticsCompleted; NavigationApprovalSubmitted; NavigationApproved | C02/C03 أو SH-03 للقرار | C05/C07/C12/C01 بحسب الحدث |
| C04 | DataSourceRegistered; DataSourceHealthChanged; SchemaVersionCreated; SchemaValidationCompleted; MigrationPlanCreated; MigrationApplied; RowPolicyChanged; SyncConflictDetected; BackupCompleted; RestoreVerificationCompleted | C04/SH-15 | C05/C07/C10/C11/C12/C14 |
| C05 | CodeWorkspaceInitialized; CodeChangeSetCreated; CodeGenerationStarted; CodeGenerationCompleted; CodeDiagnosticsCompleted; DependencyScanCompleted; SandboxRunCompleted; BuildPreparationCompleted; GitHubPullRequestCreated; CodeApprovalSubmitted; CodeBaselineApproved; CodeHandoffPrepared | C05 أو SH-03 للقرار | C06/C07/C10/C11/C12/C14 |
| C06/C07 | AIAgentRegistered; AIAgentVersionActivated; AITaskCreated; AITaskAssigned; AITaskStateChanged; AIToolGrantRequested; AIAgentOutputSubmitted; AIAgentOutputReviewed; AIAgentConflictDetected; QAPlanCreated; QATestRunCompleted; QADefectOpened; QADefectVerified; QAQualityGateEvaluated; QATestResultsApproved | C06/C07 أو SH-03 للقرار | مالك المورد/C05/C10/C11/C12/C14 |
| C08 | AssetCreated/VersionCreated/UploadFinalized/AssetScanned/AssetArchived/DeletionRequested (اقتراحات أسماء) | C08/SH-06 | C01/C02/C05/C07/C12 |
| C09 | IntegrationRegistered/ConnectionValidated/SyncStarted/SyncCompleted/WebhookAccepted/WebhookRejected/ConnectionRevoked (اقتراحات أسماء) | C09/SH-08/09/13 | C10/C12/C14 |
| C10 | PolicyVersionPublished/SecurityFindingRegistered/ExceptionRequested/ExceptionDecided/AgentCapabilitySuspended/IncidentRecorded (اقتراحات أسماء) | C10/SH-02/03/04 | كل المراكز حسب الحاجة |
| C11 | BuildRequested/BuildCompleted/ArtifactAttested/ReleaseCandidateCreated/ReleaseApproved/DeploymentStarted/DeploymentCompleted/RollbackRequested/RollbackCompleted (اقتراحات أسماء) | C11/SH-08/14/15 | C07/C10/C12/C14 |
| C12 | KnowledgeDocumentCreated/VersionCreated/ReviewSubmitted/DocumentApproved/DocumentPublished/IndexUpdated (اقتراحات أسماء) | C12/SH-07/16 | C01/C05/C11/C14 |
| C13 | SettingChangeRequested/SettingChangeDecided/SettingApplied/SettingVersionRolledBack (اقتراحات أسماء) | C13/SH-03/12 | المركز المالك للمفتاح/C14 |
| C14 | DashboardProjectionRefreshed (اقتراح اسم) | C14/SH-07/14 | لا يعدل حقيقة المجال |

الأسماء الموسومة «اقتراحات أسماء» غير واردة حرفياً في المصدر، وتحتاج موافقة مالك الحدث.

## 4. غلاف الحدث — اقتراح schema لا يعتمد

| الحقل | الوصف | الحالة |
|---|---|---|
| eventId | معرف فريد للحدث | TBD |
| eventType | الاسم الدلالي | registry مطلوب |
| eventVersion | إصدار schema | TBD |
| occurredAt / recordedAt | وقت الوقوع/التسجيل | semantics مطلوب |
| producer | service/center identity | SH-01/service identity |
| organizationId / projectId | نطاق الحدث | عند الانطباق؛ يتحقق منه المستهلك |
| actorRef | الفاعل الأصلي/المفوض | لا يثق المستهلك ببيانات غير موثقة |
| resourceRef / resourceVersion | المورد ونسخته | مطلوب للحدث المرتبط بمورد |
| correlationId / causationId | ربط السبب وسلسلة العمل | مطلوب لسلاسل workflow |
| payload | بيانات الحدث الدنيا | schema لكل event TBD |
| provenanceRefs | مراجع الأدلة/المصدر | بحسب الحدث |
| classification | تصنيف الحدث | مالك المجال/C10 |
| traceContext | ربط التتبع | TBD |

## 5. قواعد نقل الأحداث

1. Producer يملك معنى الحدث ويصدره بعد تحقق انتقال حالة المجال.
2. Event لا يمنح صلاحية؛ consumer يعيد التحقق من actor/scope/ACL قبل أي side effect.
3. يجب اعتماد eventVersion وcompatibility strategy قبل نشره.
4. يجب أن تكون معالجة المستهلك idempotent باستخدام eventId أو مفتاح عمل موثق.
5. يجب تحديد retry policy وdead-letter/replay وordering guarantees وretention.
6. لا تضع أسراراً أو محتوى حساساً كاملاً داخل event؛ أرسل references مصرحاً بها.
7. حدث Approval decision يحمل approvalRef وdecision/version/hash ولا يستبدل التحقق من SH-03.
8. يجب تحديد مالك topic/schema registry وحقوق النشر/الاشتراك لكل حدث.
9. لا يعتبر event delivery دليلاً على اكتمال العمل؛ يعتمد على domain state/evidence.
10. لا يستهلك C14 الحدث ليصبح مالكاً للحقيقة؛ يبني projection للقراءة فقط.

## 6. دورة الموافقة المشتركة المقترحة

| الحالة | المعنى | انتقالات مقترحة |
|---|---|---|
| DRAFT | طلب قيد الإعداد | submit |
| SUBMITTED | أرسل للقرار | assign/review |
| UNDER_REVIEW | قيد المراجعة | request_changes / approve / reject |
| CHANGES_REQUESTED | يحتاج تعديل | resubmit |
| APPROVED | موافق عليه لنسخة محددة | consume only if still valid |
| REJECTED | مرفوض مع سبب | new request/version |
| EXPIRED | انتهت الصلاحية | new request |
| CANCELLED | ألغي قبل القرار | terminal |
| SUPERSEDED | استبدل بطلب/نسخة أحدث | terminal |

### حقول قرار الموافقة المطلوبة

approvalRef, requestType, resourceRef, resourceVersion, contentHash, requestedBy, requestedAt, assignedReviewer(s), decision, decisionReason, decidedBy, decidedAt, policyRef, evidenceRefs, expiresAt, supersedesRef. كلها مقترحة؛ schemas والتوقيع وquorum وSLA TBD.

### ضوابط فصل المهام

- لا يعتمد AI مخرجاته أو patch أنشأه.
- لا يعتمد منفذ migration أو deploy الطلب نفسه إذا اشترطت السياسة الفصل.
- لا يقبل approval لنسخة hash مختلفة.
- يجب أن يكون approver مخولاً وقت اتخاذ القرار ووقت استهلاك القرار.
- يجب إعادة فحص policy/approval قبل التنفيذ المؤجل.
- الاستثناء له نطاق وسبب ومالك ومدة وانتهاء ومراجعة لاحقة.
- قرار الرفض/التعديل المطلوب يسجل السبب ولا يمحى.

## 7. أخطاء العقود ومعالجة إعادة المحاولة

| الفئة | المعالجة المقترحة |
|---|---|
| AUTHENTICATION_FAILED | لا retry تلقائي دون تجديد سياق موثوق |
| AUTHORIZATION_DENIED | لا retry؛ يسجل قرار الرفض |
| SCOPE_MISMATCH | رفض دون كشف بيانات المورد |
| APPROVAL_REQUIRED / APPROVAL_INVALID | تعليق/رفض آمن؛ طلب موافقة جديدة |
| VERSION_CONFLICT / HASH_MISMATCH | جلب النسخة الحالية وإعادة إنشاء diff |
| VALIDATION_FAILED | إعادة أخطاء الحقول المنقحة |
| RESOURCE_NOT_FOUND | لا يكشف وجود مورد خارج النطاق |
| RATE_LIMITED / QUOTA_EXCEEDED | retry فقط وفق Retry-After/ميزانية معتمدة |
| DEPENDENCY_UNAVAILABLE | retry محدود للعمليات idempotent فقط |
| IDEMPOTENCY_CONFLICT | إرجاع النتيجة السابقة أو conflict حسب المفتاح |
| POLICY_SERVICE_UNAVAILABLE | fail closed للعمليات الحساسة |
| AUDIT_UNAVAILABLE | لا تنفذ العملية الحساسة إذا كان تسجيلها إلزامياً |

أسماء الأكواد وتفاصيل retry intervals غير معتمدة.

## 8. اختبارات قبول عقود API/Event/Approval — غير منفذة

| ID | الاختبار | معيار القبول | الحالة |
|---|---|---|---|
| API-79-01 | payload غير مطابق للإصدار | رفض واضح دون تغيير جزئي | SPECIFIED / NOT RUN |
| API-79-02 | contractVersion غير مدعوم | رفض/تفاوض إصدار موثق | SPECIFIED / NOT RUN |
| API-79-03 | expectedVersion قديم | conflict دون lost update | SPECIFIED / NOT RUN |
| API-79-04 | إعادة Command بنفس idempotency key | لا يكرر الأثر | SPECIFIED / NOT RUN |
| API-79-05 | retry لعملية غير idempotent | لا تكرار غير مضبوط | SPECIFIED / NOT RUN |
| EVT-79-01 | event مكرر | consumer idempotent | SPECIFIED / NOT RUN |
| EVT-79-02 | event خارج الترتيب | لا يفسد حالة المجال؛ version guard | SPECIFIED / NOT RUN |
| EVT-79-03 | event schema غير معروف | quarantine/DLQ موثق دون إسقاط صامت | SPECIFIED / NOT RUN |
| EVT-79-04 | replay بعد سحب الصلاحية | إعادة ACL check قبل side effect | SPECIFIED / NOT RUN |
| APR-79-01 | approval لhash قديم | invalidated | SPECIFIED / NOT RUN |
| APR-79-02 | approver غير مخول | رفض القرار | SPECIFIED / NOT RUN |
| APR-79-03 | self-approval ممنوع | رفض | SPECIFIED / NOT RUN |
| APR-79-04 | approval منتهي | رفض التنفيذ | SPECIFIED / NOT RUN |
| APR-79-05 | قرار رفض/طلب تغييرات | يبقى السبب والسجل متاحين للمخولين | SPECIFIED / NOT RUN |
| APR-79-06 | approval service غير متاح | fail closed للعملية الحساسة | SPECIFIED / NOT RUN |
| API-79-06 | audit service غير متاح | منع الأثر الحساس الذي يتطلب audit | SPECIFIED / NOT RUN |

لا توجد test run IDs أو بيئات أو نتائج أو artifacts مثبتة لهذه الاختبارات.

## 9. قرارات مطلوبة قبل تثبيت العقود

- تعيين API Owner وEvent Producer/Consumer Owner وApproval Owner لكل عملية.
- اعتماد schemas كاملة للمدخلات والمخرجات والأخطاء والحقول الإلزامية.
- اعتماد contract/event versioning وcompatibility/deprecation.
- اعتماد idempotency/concurrency/retry/timeout/rate-limit.
- تحديد topics/transport/schema registry/ordering/DLQ/replay/retention.
- اعتماد approval quorum/separation-of-duties/delegation/expiry/cancellation.
- اعتماد تصنيف البيانات وredaction ومراجع الملفات والأسرار.
- توحيد كل العمليات مع الوثيقة 78 ومراجعة OWN-01..10 وFLD-01..26.
- لا تنشأ APIs أو queues أو topics أو code قبل اعتماد المالكين وموافقة بدء التنفيذ.

**API/EVENT/APPROVAL CONTRACT REGISTRY / REVIEW CANDIDATE / NOT APPROVED / NO IMPLEMENTATION.**


## ملحق 82 — ربط العقود بقرارات ملكية غير ملتبسة

يجب أن تحمل كل عملية API/Event/Approval معرف قرار ملكية مؤهلاً: OWN36-nn أو OWN72-nn، مع تحديد Domain SoR وDomain Owner وAPI Owner. لا تستخدم OWN-nn منفرداً لأن الوثيقتين 36 و72 تستخدمان الأرقام نفسها لمعانٍ مختلفة. راجع الوثيقة 82. هذا شرط توثيق مقترح ولم تُنشأ APIs أو topics أو schemas تنفيذية.
