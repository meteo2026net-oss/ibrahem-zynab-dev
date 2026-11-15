# 73 — سجل مطابقة الكيانات والحقول وملكية مصادر الحقيقة C01–C14

**الحالة:** FIELD-LEVEL RECONCILIATION DRAFT / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-15  
**المصادر:** 23، 24 (بالإحالة في 59)، 25، 27، 32، 36، 56، والعقود 59–70.

## 1. الغرض وحدود الدليل

تنقل هذه الوثيقة التدقيق إلى مستوى الكيان والحقول. وهي لا تنشئ ERD أو SQL أو API تنفيذية، ولا تثبت نجاح أي اختبار. أسماء الكيانات والحقول المقترحة لا تصبح schema نهائية إلا بعد اعتماد قاموس البيانات.

تصنيفات الدليل:
- SOURCE-REFERENCED: اسم/مفهوم أحالت إليه وثيقة مصدرية، وليس بالضرورة schema مكتملة.
- DRAFT-CONTRACT: كيان أو حقل وارد في مسودة 59–70.
- DERIVED-BOUNDARY: فصل ملكية مقترح استنتاجاً من 23/32/36 والعقود.
- OPEN: تعارض أو نقص يحتاج قرار مالك.
- NOT-IMPLEMENTED: لا يوجد تنفيذ أو اختبار مثبت.

## 2. خريطة مصادر الحقيقة

| نطاق الحقيقة | مالك المجال المقترح | تشغيل الخدمات المشتركة | ما يحتفظ به المستهلك |
|---|---|---|---|
| المؤسسة ومساحة العمل | المؤسسة/نطاق المؤسسة؛ التفاصيل تحتاج تثبيتاً | SH-01 الهوية، SH-02 التفويض، SH-04 التدقيق، SH-12 الإعدادات | مراجع النطاق والعضوية |
| المشروع والمتطلبات والتحليل | C01 | SH-03 الموافقات، SH-06 الملفات، SH-08 المهام، SH-10 إصدارات metadata، SH-16 الفهرسة | روابط ومراجع |
| التصميم والمكونات | C02 | SH-06 الأصول، SH-10 metadata، SH-08 المعاينات | asset/version refs |
| الصفحات والمسارات | C03 | SH-07 البحث، SH-10 metadata، SH-04 التدقيق | projections للقراءة |
| مخططات البيانات والترحيلات | C04 | SH-08 المهام، SH-10 metadata، SH-13 الاتصالات | schema/migration refs |
| الكود والاعتماديات | C05 | SH-06 الأصول، SH-08 المهام، SH-10 الإصدارات، SH-13 الموصلات | source/artifact refs |
| الوكلاء ومهام AI ومخرجاتهم | C06 | SH-02 التفويض، SH-03 الموافقات، SH-08 التنفيذ، SH-09 الأحداث، SH-11 الاستهلاك، SH-04 التدقيق | مراجع مصرح بها |
| الاختبارات والنتائج والعيوب | C07 | SH-08 التنفيذ، SH-06 الأدلة، SH-14 الصحة التشغيلية | build/evidence refs |
| سجل الأصل وتصنيفه وروابطه | C08 | SH-06 التخزين والرفع والفحص والمعالجة | asset refs |
| تعريف التكامل وmapping والمزامنة | C09 | SH-13 lifecycle، SH-08 jobs، SH-09 events، SH-04 audit | connection/status refs |
| سياسات الأمن والخصوصية والحوكمة | C10 | SH-01 الهوية، SH-02 القرار، SH-03 الموافقة، SH-04 التدقيق | policy/decision refs |
| طلب الإصدار والنشر | C11 | SH-08 jobs، SH-10 metadata، SH-14 monitoring، SH-15 backup | build/deploy refs |
| محتوى الوثيقة المعرفية | C12 أو مالك المجال المصدر | SH-06 binary، SH-16 ingestion/index، SH-07 search | citation/source refs |
| تجربة الإعدادات وطلبات تغييرها | C13 للواجهة؛ مالك المفتاح لدلالته؛ SH-12 للقيم | SH-03 approval، SH-04 audit، Secret Manager للأسرار | effective settings refs |
| تخطيط لوحة التحكم وتفضيلات العرض | C14 | SH-07 query، SH-14 telemetry، SH-03 approvals، SH-08 tasks | projections فقط |

الخريطة مشتقة من 36 ولا تستبدلها أو تعدل ملكياتها قبل اعتماد OWN-01..10.

## 3. مطابقة كيانات C01–C07

| المركز | الكيانات المسماة في العقد | الحقول/الخصائص المذكورة | حد الملكية وحالة الدليل |
|---|---|---|---|
| C01 | projects | id, organization_id, workspace_id, name, slug, description, project_type, lifecycle_status, default_environment_id, created_by, timestamps, archived_at | C01 يملك سجل المشروع؛ المؤسسة/مساحة العمل تحددان النطاق. SOURCE-REFERENCED + DRAFT |
| C01 | project_requirements, requirement_versions, requirement_relationships | UUID، status، versions، audit، organization/project scope | C01 يملك المتطلبات؛ تفاصيل الحقول الكاملة OPEN |
| C01 | requirement_artifact_links, requirement_acceptance_criteria | evidence refs ومعايير القبول | C01 يملك الرابط الدلالي والمعيار؛ C08/SH-06 الأصل؛ C07 الاختبار. DRAFT |
| C01 | project_idea_versions, project_reference_links, project_analysis_runs, project_personas, project_nfr_items, project_gaps, clarification_threads/items, technology_proposals, project_estimates, requirement_baselines | source/evidence/confidence/version/approval/status وحقول النطاق | إضافات مقترحة في 59؛ لا يتحول اقتراح AI أو تقدير إلى قرار معتمد. DRAFT |
| C02 | design_workspaces, design_documents, design_document_versions | organization_id, project_id, workspace_id, document_type, current_version, content_ref, content_hash, created_by | C02 يملك تعريف التصميم؛ SH-06 تخزين الأصل. DRAFT |
| C02 | design_pages, design_elements, design_layers | page_key, viewport_profile, element_key, component_ref, parent_ref, geometry, props_ref, z_index, order_index | لا تستبدل سجل صفحات C03. حد C02/C03 OPEN |
| C02 | component_definitions/instances, design_tokens, design_interactions/states, design_assets, design_previews, design_reviews | schemas/slots/events refs، tokens/themes/versions، asset_ref، platform/viewport، approved_hash | C02 يملك التصميم؛ C08 الأصل؛ C03 page identity. DRAFT |
| C03 | page_registry, page_versions | organization_id, project_id, page_key, lifecycle_status, design_ref, data_contract_ref, content_hash | C03 يملك معرف الصفحة ودورة حياتها؛ design_ref رابط إلى C02. DRAFT |
| C03 | route_definitions/parameters, navigation_edges, menu_definitions/items, navigation_components | route_key, path_pattern, target_page_ref, parameter_name/type/required, source/target refs, version/status | C03 يملك المسارات والتنقل؛ C10 يملك السياسة. DRAFT |
| C03 | route_guard_bindings, deep_link_rules, navigation_states/diagnostics, user_journeys, navigation_reviews | policy_ref, resource_scope, enforcement_mode, uri_pattern, graph_version, approved_hash | route guard ليس تفويضاً خادمياً. DRAFT |
| C04 | schema definitions, data contracts, migrations | schemas, constraints, migrations, connection/environment refs | C04 يملك تعريف المخطط والترحيل؛ مراكز المجال تملك سجلاتها. DRAFT؛ يلزم field catalog |
| C05 | code workspaces/snapshots/change sets/patches | source snapshot, base_hash, patch/diff refs, provenance, toolchain profile | C05 يملك الكود والتغيير؛ SH-10 version metadata؛ SH-08 execution. DRAFT |
| C05 | dependency manifests/lockfiles/findings, sandbox_runs, diagnostics, reviews/approvals | ecosystem/version, content_hash, runtime/network policy refs, severity, reviewed_hash | C05 manifests والpatches؛ C10 policy؛ C07 test results. DRAFT |
| C05 | github_repository_links/change_requests, code_handoff_packages | provider/repository/branch/commit/PR refs, baseline/artifact/test/SBOM refs | المستودع الخارجي لا يصبح مملوكاً لـC05. DRAFT |
| C06 | ai_agents/versions, skills, bindings, tool_manifests/grants | owner_scope, role, status, capability_refs, schemas, allowed_scope, expires_at, approved_by | C06 تعريف الوكلاء؛ SH-02 التفويض؛ C10 السياسة؛ SH-04 audit. DRAFT |
| C06 | ai_tasks/plans/assignments/runs, context_packages, memory_refs | organization/project, requested_by, scope, budget, deadline, dependencies, source_refs, classification, expiry | C06 تنسيق مهمة AI؛ SH-08 تنفيذ عام. ازدواج task records OPEN |
| C06 | provider_profiles, usage_records, outputs/reviews, conflicts, evaluations, audit refs | provider/model/version, secret_ref, usage/cost, provenance, validation, reviewer, evidence | SH-11 ledger والتجميع؛ SH-04 audit؛ C06 لا يملك الأسرار أو التفويض. DRAFT |
| C07 | qa_test_plans/cases/versions, requirement_links | baseline/scope/risk, test_key, preconditions/steps/expected, requirement_ref | C07 يملك تعريف الاختبار؛ C01 يملك المتطلب. DRAFT |
| C07 | qa_test_runs/results/evidence/environments/fixtures | build_ref, environment_ref, outcome, artifact_ref, content_hash, classification, reset_policy | C07 نتيجة الاختبار؛ SH-08 تشغيل job؛ C08/SH-06 أدلة. DRAFT |
| C07 | qa_defects/events, coverage_reports, quality_gates/evaluations/waivers/approval_records | severity/priority/status, uncovered refs, policy_version, evidence_refs, approver/expiry | C07 الجودة؛ SH-14 الصحة التشغيلية؛ C10 ضوابط الأمن. DRAFT |

## 4. مطابقة كيانات C08–C14

| المركز | الكيانات المسماة | الحقول/الخصائص المذكورة | حد الملكية وحالة الدليل |
|---|---|---|---|
| C08 | asset_records | asset_id, tenant_id, project_id?, owner_domain, asset_type, display_name, lifecycle_status, created_by, timestamps | C08 سجل الأصل؛ SH-06 التخزين/الرفع/الفحص. اختلاف tenant_id وorganization_id OPEN |
| C08 | asset_versions | asset_id, version_id, parent_version?, checksum, size, media_type, source_ref, created_at | C08 علاقة النسخة؛ SH-06 binary/storage receipt. من يولد version ID والchecksum OPEN |
| C08 | asset_links/metadata/classifications | asset_ref, target_domain/ref, relation_type, key/value, visibility, provenance, sensitivity, retention_class, legal_basis_ref | C08 metadata الدلالية؛ SH-02 grants؛ C10 policy؛ SH-04 audit. DRAFT |
| C08 | asset_processing_requests/access_grants/retention_events | processing attempts/output refs؛ subject/resource/permission/scope/expiry؛ policy/action/actor/evidence | SH-06/08 processing؛ SH-02 grants؛ SH-04 audit. OWN-01 |
| C09 | integration_definitions/instances/capabilities/mappings/sync_policies | provider, purpose, scope, environment, operations, OAuth scopes, field mapping, direction/frequency | C09 تعريف التكامل؛ SH-13 connector lifecycle/runtime. DRAFT |
| C09 | integration_runs, connector_runtime_records, secret_references | job refs/attempts/results; connector version/health; secret_ref | SH-08 attempts؛ SH-13 lifecycle؛ Secret Manager قيمة السر. OWN-02 |
| C09 | external_webhook_registrations, integration_audit_refs | signed payload metadata, key reference, audit ref | C09 تعريف webhook؛ SH-13 runtime؛ SH-04 audit. DRAFT |
| C10 | security_policies, threat_models, risk_register, security_findings | policy/version/scope, assets/threats/controls, risk/impact/likelihood/treatment, finding/source/evidence | C10 policy/risk؛ المنتج يملك finding المصدرية؛ SH-04 audit. DRAFT |
| C10 | privacy_assessments, data_classifications, security_exceptions, governance_reviews, agent_policy_bindings | purpose/data/legal basis/retention/transfer; classification; exception/expiry; review; agent policy | مالك المجال يحدد تصنيف بياناته؛ C10 يحكم policy؛ SH-02 runtime. Zero Trust OPEN |
| C11 | build/release/deployment lifecycle | source_ref, target, profile, commit, artifact refs, provenance, approval, environment, health report, rollback | C11 يملك دلالة release/deploy؛ SH-08 jobs؛ SH-10 version metadata؛ SH-14 monitoring؛ SH-15 backup. DRAFT |
| C11 | build profiles, artifacts/attestations, candidates, approvals, deployments, promotions, checks, rollbacks | profile/version, artifact/provenance/checks, candidate/version/notes, approval refs, environment/status | registry/storage authority غير محدد. DRAFT |
| C12 | knowledge_documents/document_versions/source_evidence | owner/scope/source, content/version/provenance, citations/evidence refs | C12 أو مالك المجال يملك المحتوى؛ C08 binary؛ SH-16 indexing؛ SH-07 query. DRAFT |
| C12 | ingestion/index/search/compare/export/archive/reconcile | source refs, ACL, version diff, export bundle, retention | الفهرس ليس مصدر حقيقة؛ ACL يعاد التحقق منه عند القراءة. OWN-04 |
| C13 | setting_definitions/schemas/values/versions/overrides/change_history (SH-12) | scope, key, schema, value, precedence, version, override, history | SH-12 storage/runtime؛ مالك المفتاح الدلالة؛ C13 UX/requests. Precedence OPEN |
| C13 | setting change requests/decisions/apply-owner changes/diagnostics | expected version, owner, approval, result, inheritance source | التغيير عبر API مالك المفتاح؛ SH-03 approval؛ لا كتابة مباشرة. DRAFT |
| C14 | dashboard layouts/preferences/widgets/projections | scope, layout version, widget config, source_ref, freshness/as_of, ACL | C14 يملك تفضيلات العرض؛ المصدر يملك metric؛ projection schema يحتاج استكمالاً. DRAFT |
| C14 | overview/center/task/approval/quality/security/integration/release/health queries | source refs, freshness, filtered summaries | كل ملخص يظل تابعاً لمصدره ولا يملك C14 قرار المصدر. DRAFT |

## 5. سجل مشكلات الحقول والكيانات المفتوحة

| ID | المشكلة | الأثر | المطلوب قبل الاعتماد |
|---|---|---|---|
| FLD-01 | organization_id وtenant_id وtenant scope بصيغ مختلفة | خطر عزل/توافق | قاموس موحد للمؤسسة والمستأجر وعلاقات workspace/project |
| FLD-02 | project_requirements أقل تفصيلاً من projects | ERD ناقص | حقول المتطلبات والإصدارات والعلاقات والقيود |
| FLD-03 | design_pages وpage_registry يحملان مفهوماً متشابهاً | ازدواج مصدر حقيقة | C03 page identity؛ C02 layout/canvas مع رابط ثابت |
| FLD-04 | component definitions مقابل generated code components | drift بين التصميم والتنفيذ | component contract/version mapping |
| FLD-05 | C04 schema ownership مقابل domain row ownership | التباس migration/data owner | Domain Owner وSchema Steward وMigration Approver وAPI Owner لكل كيان |
| FLD-06 | ai_tasks/runs مقابل SH-08 tasks/attempts | ازدواج task state | فصل AI task intent/plan عن generic execution job |
| FLD-07 | ai_usage_records مقابل SH-11 usage/cost | ازدواج ledger | C06 provider/model metering refs؛ SH-11 normalized ledger مع reconciliation ID |
| FLD-08 | qa_test_runs مقابل SH-08 وSH-14 | خلط اختبار/تنفيذ/صحة | C07 test run/result؛ SH-08 job؛ SH-14 telemetry |
| FLD-09 | asset_records/versions مقابل SH-06 records/versions | ازدواج ظاهر في 36 و64 | C08 domain asset registry؛ SH-06 storage/upload/scan/blob version handling |
| FLD-10 | asset_access_grants مقابل SH-02 grants | تكرار صلاحيات | إلغاء ملكية grants محلياً؛ القرار المركزي SH-02 |
| FLD-11 | integration_runs مقابل SH-08 task records | ازدواج حالات | C09 business run؛ SH-08 attempts؛ run_ref/job_ref |
| FLD-12 | connector/secret metadata مقابل SH-13 | حفظ مكرر أو كشف سر | SH-13 lifecycle؛ Secret Manager للقيمة؛ C09 mapping/purpose |
| FLD-13 | C10 policy decisions مقابل SH-02 access_decisions | محرك قرار مكرر | C10 authoring/governance؛ SH-02 evaluation/runtime |
| FLD-14 | C10 audit_events مقابل SH-04 ledger | سجل تدقيق مكرر | SH-04 يملك ledger؛ C10 يحتفظ audit_event_ref |
| FLD-15 | C11 job state مقابل SH-08 | ازدواج التنفيذ | C11 domain release state؛ SH-08 attempts/progress/cancel |
| FLD-16 | artifact registry/storage غير محدد | لا مصدر binary واضح | اعتماد registry/storage/retention/signature/replication |
| FLD-17 | C12 document versions مقابل SH-16 documents/index | تكرار المحتوى والفهرس | C12/domain canonical content؛ SH-16 ingestion/index metadata فقط |
| FLD-18 | C12 search docs مقابل SH-07 index | تضارب الفهرسة | SH-07 query index؛ SH-16 knowledge ingestion؛ المصدر ACL/content |
| FLD-19 | C13 setting values مقابل SH-12 | تكرار التخزين | C13 UX؛ SH-12 values/versions؛ مالك المفتاح schema semantics |
| FLD-20 | C14 projection cache/schema غير مفصل | freshness/ACL غير مثبتة | source_ref/version/as_of/freshness/scope/permission filter |
| FLD-21 | actor/timestamps/provenance غير موحدة | تدقيق وتزامن غير موثوق | canonical audit envelope/time semantics/actor identity |
| FLD-22 | archive/delete/retention غير موحدة | حذف غير منضبط | lifecycle/retention/legal hold لكل entity |
| FLD-23 | status/enums غير موحدة | أخطاء توافق | enum registry/versioning/unknown-value behavior |
| FLD-24 | cross-project/global asset references غير محسومة | خطر cross-tenant | قواعد scope inheritance/share grants/tenant-consistent references |
| FLD-25 | owner_ref/owner_domain/created_by مبهمة | خلط الملكية بالإنشاء | فصل domain_owner_ref وrecord_owner_scope وcreated_by_actor وsteward_ref |
| FLD-26 | classification/legal basis موزعة | privacy enforcement ناقص | مالك المجال يعلن التصنيف والغرض؛ C10 يراجع؛ enforcement موحد |

## 6. قاموس الحقول المشتركة المقترح

هذه قواعد دلالية مرشحة وليست schema معتمدة:
- organization_id: معرف المؤسسة المالكة للنطاق، بعد حسم اختلاف tenant_id.
- workspace_id وproject_id: مراجع نطاق لا تمنح صلاحية بذاتها.
- domain_owner_ref: مالك حقيقة المجال؛ لا يساوي created_by_actor.
- created_by_actor وupdated_by_actor: هوية مستخدم أو خدمة موثقة.
- record_version/expected_version: concurrency token؛ لا يخلط مع SH-10 version metadata.
- source_ref/source_version/provenance_ref: مصدر المعلومة وإصداره وسلسلة منشئها.
- policy_ref/decision_ref/approval_ref/audit_event_ref: مراجع إلى SH-02/03/04 لا نسخ محلية.
- task_ref/job_ref: يفصلان نية/سجل المجال عن تنفيذ SH-08.
- asset_ref/storage_object_ref: يفصلان سجل الأصل عن كائن التخزين.
- secret_ref: مرجع Secret Manager فقط؛ يمنع تخزين secret value في DB/logs/prompts.
- occurred_at/recorded_at: وقت الحدث مقابل وقت التسجيل.
- status: مرتبط بآلة حالات وإصدار انتقالات، لا نص حر.

## 7. بوابات إغلاق المطابقة قبل ERD/API

1. تعيين Domain Owner وSchema Steward وAPI Owner لكل كيان.
2. تحديد canonical entity واحد لكل حقيقة؛ وتوثيق أي read model/projection مشتق.
3. إغلاق FLD-01..26 بقرار موثق أو استثناء معتمد ومحدد الأثر.
4. توحيد organization/tenant/workspace/project scope في سياق الطلب 25.
5. تثبيت classification/retention/legal basis لكل فئة بيانات.
6. تثبيت lifecycle/state machine وenum registry.
7. توثيق idempotency/concurrency للكتابة والعمليات الحساسة.
8. اختبارات cross-tenant سلبية عبر API/DB/cache/search/storage/export/backup.
9. اعتماد contract schemas وcompatibility tests.
10. توثيق migration owner/rollback/backfill قبل التنفيذ.
11. توثيق event producer/consumer/schema/replay/idempotency.
12. توثيق API read/write surface وقرارات SH-02/03 وتدقيق SH-04.
13. توقيع المالكين ثم مراجعة الأمن والخصوصية.
14. لا يبدأ التنفيذ قبل اعتماد المواصفات وموافقة مستقلة صريحة.

## 8. الاختبارات والأدلة

| المجال | معرفات اختبار مقترحة | الوضع |
|---|---|---|
| ownership/duplicate SoR | T-OWN-01..02 وFLD-03 و06..19 | SPECIFIED / NOT RUN |
| tenant scope | T-SEC-02 وFLD-01/24 | SPECIFIED / NOT RUN |
| API contracts/version/errors | T-API-01 | SPECIFIED / NOT RUN |
| authorization/approval/audit | T-SEC-01 وT-APR-01 | SPECIFIED / NOT RUN |
| event/task idempotency | T-EVT-01 وFLD-06/11/15 | SPECIFIED / NOT RUN |
| retention/delete/legal hold | FLD-22/26 | NOT FULLY SPECIFIED / NOT RUN |
| migration/rollback | FLD-05 | NOT FULLY SPECIFIED / NOT RUN |
| projection freshness/ACL | FLD-20 | SPECIFIED / NOT RUN |
| recovery | T-REC-01 | BLOCKED: RTO/RPO غير معتمدين |

لا يوجد test run ID أو build ID أو بيئة تشغيل أو artifact ناجح في هذه المراجعة؛ لا يجوز إعلان نجاح اختبارات.

## 9. القرارات المفتوحة

- اعتماد OWN-01..10 في الوثيقة 72 ثم تحديث 36 بناءً على قرارات فعلية.
- اعتماد FLD-01..26 أو تعيين مالك وخطة إغلاق لكل منها.
- حسم Zero Trust / SEC-REV-01 مع الحفاظ على تعارض المصدر.
- تحديد RTO/RPO وSLO وأهداف freshness.
- اعتماد retention/deletion/legal hold/privacy basis.
- اعتماد precedence لإعدادات SH-12.
- اعتماد registry/storage authority للـbuild artifacts.
- تثبيت semantics للنسخ بين domain version وSH-10.
- استكمال الفصلين 156/157 وSH-17..SH-20 من مصدر موثوق فقط.
- اعتماد thresholds/عملة/مصادر تكلفة SH-11؛ 50/75/90/100 تبقى مقترحات.
- اعتماد التقنيات النهائية في ADRs منفصلة.

## 10. نتيجة المطابقة

تم تحديد 26 مشكلة كيان/حقل FLD-01..26. أبرز الازدواجات: assets/versions، grants، AI tasks، usage/cost، test runs، integration runs، policy decisions، audit events، build jobs، knowledge documents/indexes، settings values، dashboard projections.

لا تُحدّث إحصاءات المحاور الـ64 في 57 من هذه الوثيقة وحدها؛ يلزم field-to-axis crosswalk مستقل ومراجع. لا توجد schemas تنفيذية أو migrations أو APIs أو اختبارات منفذة مثبتة.

**FIELD-LEVEL RECONCILIATION CANDIDATE / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION.**
