# 96 — سجل أحداث المراكز C01–C14 وحدود المنتج والمستهلك

**الحالة:** CONSOLIDATED EVENT INVENTORY DRAFT / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المصادر:** 59–70 و79 و91–95.  
**قاعدة الاستعادة:** الأسماء المدرجة تحت SOURCE-RECOVERED مأخوذة من مسودات العقود 59–70؛ أما PROPOSED-IN-79 فهي أسماء مقترحة في السجل المركزي 79 وليست نصاً حرفياً من المصدر الأصلي. لا تعني أي حالة موافقة على schema أو topic أو نشر event.

## 1. السجل الموحد

| المركز | الأحداث / event family | مصدر الاسم | منتج معنى المجال المقترح | ملاحظات الحدود | حالة |
|---|---|---|---|---|---|
| C01 | ProjectCreated; DomainAnalysisCompleted; RequirementCreated; RequirementVersionChanged; BaselineSubmitted; BaselineApproved | SOURCE-RECOVERED: 59 | C01؛ DomainAnalysisCompleted قد يتطلب producer evidence من C06 | approval decision من SH-03؛ C01 يثبت أثر خط الأساس | OWNER REVIEW |
| C02 | DesignWorkspaceCreated; DesignDocumentVersionSaved; DesignValidationCompleted; DesignApprovalSubmitted; DesignApproved | SOURCE-RECOVERED: 60/79 | C02؛ قرار الاعتماد SH-03 مع حدث مجال من C02 | لا تجعل SH-03 مالك مستند التصميم | OWNER REVIEW |
| C03 | PageRegistered; RouteDefinitionChanged; NavigationDiagnosticsCompleted; NavigationApprovalSubmitted; NavigationApproved | SOURCE-RECOVERED: 60/79 | C03؛ قرار الاعتماد SH-03 | C03 يملك page/route identity؛ لا تعديل معتمد دون موافقة | OWNER REVIEW |
| C04 | DataSourceRegistered; DataSourceHealthChanged; SchemaVersionCreated; SchemaValidationCompleted; MigrationPlanCreated; MigrationApplied; RowPolicyChanged; SyncConflictDetected; BackupCompleted; RestoreVerificationCompleted | SOURCE-RECOVERED: 61/79 | C04؛ backup execution قد يعتمد SH-15 | SH-15 ينفذ/يحفظ backup metadata حسب القرار؛ C04 يملك domain schema/migration result | OWNER REVIEW |
| C05 | CodeChangeSetCreated; CodeGenerationStarted; CodeGenerationCompleted; CodeDiagnosticsCompleted; DependencyScanCompleted; SandboxRunCompleted; BuildPreparationCompleted; GitHubPullRequestCreated; CodeApprovalSubmitted; CodeBaselineApproved | SOURCE-RECOVERED: 62/79 | C05؛ SH-03 لقرار الموافقة | build/release lifecycle ينتقل إلى C11؛ runner execution لا يملك معنى code change | OWNER REVIEW |
| C06 | AIAgentRegistered; AIAgentVersionActivated; AITaskCreated; AITaskAssigned; AITaskStateChanged; AIToolGrantRequested; AIAgentOutputSubmitted; AIAgentOutputReviewed; AIAgentConflictDetected | SOURCE-RECOVERED: 63/79 | C06؛ grant decision من SH-03/C10 بحسب السياسة | SH-08 ينفذ attempts فقط؛ SH-11 يملك cost ledger | OWNER REVIEW |
| C07 | QAPlanCreated; QATestRunCompleted; QADefectOpened; QADefectVerified; QAQualityGateEvaluated; QATestResultsApproved | SOURCE-RECOVERED: 63/79 | C07؛ approval service عند اعتماد النتائج | لا تدمج test result مع SH-08 job أو SH-14 health | OWNER REVIEW |
| C08 | AssetRecordCreated; AssetUploadRequested; AssetVersionReady; AssetQuarantined; AssetScanCompleted; AssetLinked; AssetLinkRevoked; AssetTransformationCompleted; AssetArchived; AssetDeletionRequested; AssetDeleted | SOURCE-RECOVERED: 64 | C08؛ SH-06 يرسل storage/scan operational evidence | SH-06 لا يصبح Domain SoR لسجل الأصل | OWNER REVIEW |
| C09 | IntegrationRegistered; IntegrationAuthorizationStarted; IntegrationConnected; IntegrationConnectionFailed; IntegrationSyncStarted; IntegrationSyncCompleted; IntegrationSyncFailed; IntegrationPaused; IntegrationRevoked; WebhookRejected | SOURCE-RECOVERED: 65 | C09 لأحداث التكامل الدلالية؛ SH-13 لأحداث connector runtime المحددة بعد الاعتماد | SH-08 execution refs؛ SH-09 transport؛ لا تخلطها مع Domain event | OWNER REVIEW |
| C10 | PolicyVersionPublished; SecurityFindingRegistered; ExceptionRequested; ExceptionDecided; AgentCapabilitySuspended; IncidentRecorded | PROPOSED-IN-79 | C10؛ SH-02 قرار runtime وSH-04 audit source | لا حدث يحل محل سجل SH-04 | PROPOSED |
| C11 | BuildRequested; BuildStarted; BuildCompleted; BuildFailed; ArtifactAttested; ReleaseCandidateCreated; ReleaseApprovalRequested; ReleaseApproved; DeploymentStarted; DeploymentCompleted; DeploymentFailed; RollbackRequested; RollbackCompleted; PostDeployCheckFailed | SOURCE-RECOVERED: 67 | C11؛ SH-08 execution؛ SH-14 telemetry؛ SH-15 backup | C11 يملك معنى build/release/deployment؛ راجع ART-95 | OWNER REVIEW |
| C12 | KnowledgeDocumentCreated; DocumentVersionCreated; DocumentReviewRequested; DocumentApproved; DocumentPublished; KnowledgeIndexUpdated; KnowledgeAccessRevoked; DocumentationDriftDetected; DocumentArchived | SOURCE-RECOVERED: 68 | C12؛ SH-16 indexing refs وSH-07 search | C12/domain owner يملك المحتوى الأصلي | OWNER REVIEW |
| C13 | SettingChangeRequested; SettingChangeDecided; SettingApplied; SettingVersionRolledBack | PROPOSED-IN-79 | C13؛ SH-03 approval؛ SH-12 value store | مالك المفتاح يملك دلالته؛ precedence OPEN | PROPOSED |
| C14 | DashboardProjectionRefreshed | PROPOSED-IN-79 | C14 للـprojection فقط | لا يملك معنى المؤشرات أو بيانات المصدر | PROPOSED |

## 2. ضوابط المنتج والناقل والمستهلك

- منتج الحدث الدلالي هو مالك Domain SoR؛ لا تنتقل الملكية إلى worker أو queue أو transport.
- SH-08 ينشر execution lifecycle الخاص به فقط، مع domainJobRef؛ لا يعيد إصدار أحداث المجال بدلاً من المركز المنتج.
- SH-09 يملك نقل/تسليم الرسائل وفق عقده، لا معنى payload.
- SH-13 يملك connector runtime events إن اعتمدت؛ أحداث C09 الدلالية تبقى لدى C09.
- SH-14 يملك telemetry/alert events؛ لا يصدر QA result نيابة عن C07 أو release result نيابة عن C11.
- SH-15 يملك backup execution/catalog refs حسب اعتماد المالكين؛ C04/C11 يحتفظان بقرارات المجال ونتائج التحقق.
- SH-03 يملك workflow decision record؛ مالك المجال يصدر event يعلن انتقال المورد بعد التحقق من قرار الموافقة.
- SH-04 مصدر التدقيق؛ لا يُعاد تعريف audit event كحدث مجال قابل للتعديل.

## 3. متطلبات كل event record

يجب أن يتضمن السجل التفصيلي لكل حدث:
eventId, eventType, eventVersion, producerService, domainOwner, domainSoR, consumers, organizationScope, projectScopeWhenApplicable, resourceRef, resourceVersion, occurredAt, recordedAt, actorRef, correlationId, causationId, payloadSchemaRef, classification, provenanceRefs, idempotency/deduplication policy, ordering guarantee, retry/DLQ/replay policy, retention, ACL revalidation rule, linked AX requirement, qualified OWN decision, acceptance test IDs.

أي عنصر غير موجود في المصدر يبقى TBD ولا يستنتج.

## 4. اختبارات تغطية السجل

| Test ID | الفحص | معيار القبول | الحالة |
|---|---|---|---|
| EVT-96-T01 | وجود event inventory لكل C01–C14 | كل مركز لديه أحداث أو N/A مبرر ومعتمد | SPECIFIED / NOT RUN |
| EVT-96-T02 | producer/SoR | كل حدث له منتج معنى واحد وDomain SoR | SPECIFIED / NOT RUN |
| EVT-96-T03 | shared service boundary | SH-08/09/13/14/15 لا تنتحل ملكية المجال | SPECIFIED / NOT RUN |
| EVT-96-T04 | event schema/version | كل event له schema/version معتمد قبل النشر | SPECIFIED / NOT RUN |
| EVT-96-T05 | consumer ACL | إعادة التحقق من النطاق والصلاحية قبل side effect | SPECIFIED / NOT RUN |
| EVT-96-T06 | idempotency/replay | تكرار/replay لا يكرر أثر المجال | SPECIFIED / NOT RUN |
| EVT-96-T07 | event-to-AX/OWN/test trace | كل event مربوط بمحور وقرار واختبار | SPECIFIED / NOT RUN |
| EVT-96-T08 | producer evidence | لا يصدر event انتقال حالة قبل تحقق domain transaction | SPECIFIED / NOT RUN |
| EVT-96-T09 | approval binding | event approval يطابق resource version/hash | SPECIFIED / NOT RUN |
| EVT-96-T10 | sensitive payload | لا أسرار أو محتوى غير مصرح به في payload | SPECIFIED / NOT RUN |
| EVT-96-T11 | ordering and late delivery | لا تفسد الأحداث المتأخرة حالة أحدث | SPECIFIED / NOT RUN |
| EVT-96-T12 | C14 projection | الإسقاط read-only مع source/asOf/freshness | SPECIFIED / NOT RUN |

## 5. شروط الإغلاق

تفكيك الأحداث إلى records منفردة؛ استكمال أحداث المراكز التي لا تملك inventory مصدرياً؛ اعتماد producer/consumer/schema owners؛ ربط كل event بمحور AX وقرار OWN مؤهل واختبار؛ اعتماد security/retention/replay policies؛ تنفيذ اختبارات التوافق والعزل والتكرار مع الأدلة.

**هذه الوثيقة لا تنشئ topics أو event bus أو schemas أو consumers، ولا تعتمد أسماء PROPOSED-IN-79، ولا تغلق RI-94.**



## ملحق 100 — تفكيك الأحداث إلى سجلات مفردة

أضيفت الوثيقة 100 لتفكيك 106 أحداث من event families أعلاه إلى Event IDs مستقلة، مع حفظ حالة المصدر والمرجع الوسيط ومحاور AX المرشحة وعائلة الاختبارات العامة. لا تعتبر هذه الخطوة اعتماداً للـproducer أو Domain SoR أو OWN أو schema أو exact test ID؛ تبقى كلها مفتوحة حسب سجل 100.



## تصحيح 100.1 — حالة المصدر لكل حدث

صححت الوثيقة 100 نقل حالة المصدر؛ فتمت مطابقة كل Event ID مع عمود مصدر الاسم في 96 بدلاً من نسخ OWNER REVIEW. العدد الحالي 95 SOURCE-RECOVERED و11 PROPOSED. حالة OWNER REVIEW تبقى مستقلة ولا تعني حالة المصدر.
