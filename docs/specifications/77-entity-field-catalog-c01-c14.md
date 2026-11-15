# 77 — كتالوج الكيانات والحقول ومصادر الحقيقة C01–C14

**الحالة:** DERIVED FIELD CATALOG / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-15  
**المصادر المباشرة:** 73، 59–70، 23، 32، 71، 72، 75، 76.  
**حدود المصدر:** هذا الكتالوج يعيد تنظيم أسماء الكيانات والحقول التي ظهرت في مسودات العقود ومطابقة الحقول. لم يتسنّ في هذه الجولة استرجاع مصفوفة 36 عبر المسار المفترض؛ لذلك لا يدّعي هذا الملف أنه بديل عن 36 أو تحقق مستقل من كامل نصها. المرجع التفصيلي الحالي للحقول هو 73. كل عنصر أدناه مقترح أو مصدره إحالة، وليس schema معتمداً.

## 1. قواعد قراءة الكتالوج

| الرمز | المعنى |
|---|---|
| SRC | مفهوم أو حقل مشار إليه في وثيقة مصدرية؛ لا يثبت اكتمال schema |
| DRAFT | اسم كيان/حقل وارد في مسودة 59–70 |
| DERIVED | فصل ملكية أو توحيد مشتق من المقارنة |
| OPEN | يتطلب قرار مالك أو حسم تعارض |
| TBD | النوع أو nullability أو constraint أو retention غير محدد في المصدر المتاح |
| NOT-IMPLEMENTED | لا يوجد تنفيذ/اختبار مثبت في هذه المراجعة |

- لا يُستنتج نوع SQL أو طول أو default أو nullability من اسم الحقل.
- لا تُنشأ جداول أو APIs أو migrations من هذا الملف.
- لا يجوز للمستهلك امتلاك نسخة قابلة للكتابة من حقيقة مجال مركز آخر.
- المراجع إلى SH-01..SH-16 لا تنقل ملكية المجال إلى الخدمة المشتركة.
- القيم التي تحمل علامة ? في المصادر تظل اختيارية/غير محسومة، ولا تتحول إلى NOT NULL.

## 2. قاموس النطاق والفاعل والإصدار والمصدر

| الحقل/المفهوم | الدلالة المقترحة | مالك الدلالة | النوع | Nullability / القيود | الحالة |
|---|---|---|---|---|---|
| organization_id | معرف المؤسسة | نطاق المؤسسة؛ التفاصيل تحتاج تثبيتاً | TBD | علاقة tenant_id مفتوحة | OPEN |
| tenant_id | معرف المستأجر/حد العزل | C10 مع مالك النطاق | TBD | لا يفترض أنه مرادف organization_id | OPEN |
| workspace_id | مرجع مساحة العمل | مالك مساحة العمل | TBD | scope reference لا يمنح صلاحية | DRAFT |
| project_id | مرجع المشروع | C01 | TBD | scope reference؛ تحقق server-side | DRAFT |
| environment_id / default_environment_id | مرجع البيئة | مالك المجال/البيئة | TBD | علاقة البيئة وسياسة الوراثة TBD | DRAFT |
| domain_owner_ref | مالك حقيقة المجال | مالك المجال | TBD | لا يساوي المنشئ | DERIVED |
| record_owner_scope | نطاق ملكية السجل | مالك المجال | TBD | قيم النطاق TBD | DERIVED |
| steward_ref | مشرف البيانات | مالك المجال/حوكمة البيانات | TBD | الدور والتعيين TBD | DERIVED |
| created_by / created_by_actor | الفاعل المنشئ | SH-01 للهوية؛ المجال يسجل المرجع | TBD | هوية مستخدم/خدمة موثقة | DRAFT |
| updated_by_actor | آخر فاعل تعديل | SH-01 + المجال | TBD | actor reference | DERIVED |
| actor | فاعل الطلب | سياق الطلب الموحد/SH-01 | TBD | مستخدم أو وكيل أو خدمة | SRC |
| occurred_at | وقت وقوع الحدث | المنتج/مصدر الحدث | timestamp semantics TBD | timezone/clock source TBD | DRAFT |
| recorded_at | وقت تسجيل الحدث | الخدمة المستقبلة | timestamp semantics TBD | يميز عن occurred_at | DERIVED |
| timestamps | أوقات الإنشاء/التحديث | مالك المجال | TBD | أسماء الحقول الدقيقة TBD | DRAFT |
| record_version / expected_version | إصدار السجل/رمز التزامن | مالك المجال | TBD | لا يخلط مع SH-10 version metadata | DERIVED |
| version_id / parent_version | مرجع إصدار مورد | مالك المجال؛ SH-10 metadata عند انطباقها | TBD | قواعد branching/restore TBD | DRAFT |
| source_ref / source_version | مرجع المصدر وإصداره | مالك المجال | TBD | provenance required بحسب التصنيف | DRAFT |
| provenance_ref | سلسلة منشأ/تحويل | المنتج؛ SH-04 يسجل الأحداث الحساسة | TBD | بنية provenance TBD | DRAFT |
| content_ref / content_hash | مرجع المحتوى وبصمته | مالك المحتوى؛ SH-06 للتخزين | TBD | algorithm/encoding TBD | DRAFT |
| asset_ref / storage_object_ref | سجل الأصل مقابل كائن التخزين | C08 / SH-06 (حد الفصل OPEN) | TBD | لا تخلط سجل المجال بالـbinary | OPEN |
| task_ref / job_ref | مهمة مجال مقابل تنفيذ عام | C06/C09/C11 حسب المجال؛ SH-08 للتنفيذ | TBD | يجب عدم ازدواج حالة التنفيذ | OPEN |
| policy_ref / decision_ref | مرجع السياسة/قرار التفويض | C10 / SH-02 | TBD | SH-02 هو runtime evaluator المقترح | DERIVED |
| approval_ref | مرجع قرار الموافقة | مالك المجال يحدد الحاجة؛ SH-03 ينفذ دورة الموافقة | TBD | يربط بالنسخة/البصمة المعتمدة | DRAFT |
| audit_event_ref | مرجع سجل التدقيق | SH-04 | TBD | لا نسخ محلية لسجل التدقيق | DERIVED |
| secret_ref | مرجع آمن إلى قيمة سرية | Secret Manager / SH-13 lifecycle | TBD | لا تخزن قيمة السر في DB/log/prompt | DERIVED |
| classification / dataClassification | تصنيف البيانات | مالك المجال يعلن؛ C10 يحكم | TBD | taxonomy TBD | OPEN |
| retention_class | فئة الاحتفاظ | مالك المجال + C10 | TBD | المدة/الأساس TBD | OPEN |
| legal_basis_ref | أساس المعالجة القانوني | مالك المجال/C10 | TBD | لا يفترض قيمة قانونية | OPEN |
| lifecycle_status / status | حالة المورد | مالك المجال | TBD | enum/state machine غير معتمد | OPEN |
| scope | نطاق الطلب | سياق موحد؛ تتحقق منه الخدمة المستقبلة | TBD | organization/workspace/project/environment عند الانطباق | SRC |
| requestId / correlationId | تتبع الطلب وسلسلة العمل | طبقة الاتصال المشتركة | TBD | uniqueness/format TBD | SRC |
| contractVersion | إصدار عقد الرسالة | مالك العقد | TBD | compatibility policy TBD | SRC |
| idempotencyKey | منع تكرار أثر الأمر | الخدمة المستقبلة | TBD | نطاق/مدة المفتاح TBD | SRC |

## 3. سجل الكيانات بحسب مركز المجال

> الحقول أدناه هي فقط الحقول أو الخصائص التي ظهرت في 73 أو مسودات العقود. كل حقل آخر غير مذكور يظل TBD. الأنواع والقيود التفصيلية غير مثبتة.

| المركز / مالك الحقيقة المقترح | الكيانات المذكورة | الحقول/الخصائص الصريحة المتاحة | الخدمات المشتركة المتصلة | الحالة |
|---|---|---|---|---|
| C01 — المشروع والمتطلبات | projects; project_requirements; requirement_versions; requirement_relationships; requirement_artifact_links; requirement_acceptance_criteria; project_idea_versions; project_reference_links; project_analysis_runs; project_personas; project_nfr_items; project_gaps; clarification_threads/items; technology_proposals; project_estimates; requirement_baselines | projects: id, organization_id, workspace_id, name, slug, description, project_type, lifecycle_status, default_environment_id, created_by, timestamps, archived_at. بقية الكيانات: UUID/status/version/audit/scope وsource/evidence/confidence/approval حيث وردت؛ التفاصيل الكاملة TBD | SH-03/06/08/10/16 | DRAFT؛ حقول المتطلبات OPEN |
| C02 — التصميم | design_workspaces; design_documents; design_document_versions; design_pages; design_elements; design_layers; component_definitions/instances; design_tokens; design_interactions/states; design_assets; design_previews; design_reviews | organization_id, project_id, workspace_id, document_type, current_version, content_ref, content_hash, created_by, page_key, viewport_profile, element_key, component_ref, parent_ref, geometry, props_ref, z_index, order_index, schema/slots/events refs, tokens/themes/versions, asset_ref, platform/viewport, approved_hash | SH-06/08/10 | DRAFT؛ حد C02/C03 OPEN |
| C03 — الصفحات والتنقل | page_registry; page_versions; route_definitions/parameters; navigation_edges; menu_definitions/items; navigation_components; route_guard_bindings; deep_link_rules; navigation_states/diagnostics; user_journeys; navigation_reviews | organization_id, project_id, page_key, lifecycle_status, design_ref, data_contract_ref, content_hash, route_key, path_pattern, target_page_ref, parameter_name/type/required, source/target refs, version/status, policy_ref, resource_scope, enforcement_mode, uri_pattern, graph_version, approved_hash | SH-04/07/10؛ SH-02 للقرار | DRAFT؛ page identity يجب أن تبقى لدى C03 |
| C04 — مخططات البيانات والترحيلات | schema definitions; data contracts; migrations; connections; saved queries/data bindings بحسب العقد | schemas, constraints, migrations, connection/environment refs | SH-08/10/13 | DRAFT؛ نوع كل حقل/مالك migration TBD |
| C05 — الكود والاعتماديات | code workspaces/snapshots/change sets/patches; dependency manifests/lockfiles/findings; sandbox_runs; diagnostics; reviews/approvals; github_repository_links/change_requests; code_handoff_packages | source snapshot, base_hash, patch/diff refs, provenance, toolchain profile, ecosystem/version, content_hash, runtime/network policy refs, severity, reviewed_hash, provider/repository/branch/commit/PR refs, baseline/artifact/test/SBOM refs | SH-06/08/10/13؛ SH-02/03/04 | DRAFT |
| C06 — الوكلاء ومهام AI | ai_agents/versions; skills; bindings; tool_manifests/grants; ai_tasks/plans/assignments/runs; context_packages; memory_refs; provider_profiles; usage_records; outputs/reviews; conflicts; evaluations | owner_scope, role, status, capability_refs, schemas, allowed_scope, expires_at, approved_by, organization/project, requested_by, scope, budget, deadline, dependencies, source_refs, classification, expiry, provider/model/version, secret_ref, usage/cost, provenance, validation, reviewer, evidence | SH-02/03/04/08/09/11 | DRAFT؛ فصل AI task عن job مفتوح |
| C07 — الاختبارات والجودة | qa_test_plans/cases/versions; requirement_links; qa_test_runs/results/evidence/environments/fixtures; qa_defects/events; coverage_reports; quality_gates/evaluations/waivers/approval_records | baseline/scope/risk, test_key, preconditions/steps/expected, requirement_ref, build_ref, environment_ref, outcome, artifact_ref, content_hash, classification, reset_policy, severity/priority/status, uncovered refs, policy_version, evidence_refs, approver/expiry | SH-06/08/14؛ SH-03/04 | DRAFT؛ test result لا يساوي job أو health |
| C08 — سجل الأصول | asset_records; asset_versions; asset_links/metadata/classifications; asset_processing_requests; access_grants; retention_events | asset_id, tenant_id, project_id?, owner_domain, asset_type, display_name, lifecycle_status, created_by, timestamps, version_id, parent_version?, checksum, size, media_type, source_ref, created_at, asset_ref, target_domain/ref, relation_type, key/value, visibility, provenance, sensitivity, retention_class, legal_basis_ref | SH-02/04/06/08 | DRAFT؛ OWN-01 مفتوح |
| C09 — التكاملات | integration_definitions/instances/capabilities/mappings/sync_policies; integration_runs; connector_runtime_records; secret_references; external_webhook_registrations; integration_audit_refs | provider, purpose, scope, environment, operations, OAuth scopes, field mapping, direction/frequency, job refs/attempts/results, connector version/health, secret_ref, signed payload metadata, audit ref | SH-04/08/09/13 | DRAFT؛ OWN-02 مفتوح |
| C10 — الأمن والحوكمة | security_policies; threat_models; risk_register; security_findings; privacy_assessments; data_classifications; security_exceptions; governance_reviews; agent_policy_bindings | policy/version/scope, assets/threats/controls, risk/impact/likelihood/treatment, finding/source/evidence, purpose/data/legal basis/retention/transfer, classification, exception/expiry, review, agent policy | SH-01/02/03/04 | DRAFT؛ Zero Trust متعارض |
| C11 — البناء والنشر | build_pipelines/profiles/runs; build_artifacts/attestations; release_candidates/manifests; deployment_targets/requests/runs; approvals; promotions; checks; rollbacks | source_ref, target, profile, commit, artifact refs, provenance, approval, environment, health report, rollback; profile/version, checks, candidate/version/notes, approval refs, environment/status | SH-08/10/14/15 | DRAFT؛ artifact registry/storage authority TBD |
| C12 — المعرفة والتوثيق | knowledge_documents; document_versions; document_sections; document_source_links; document_reviews; document_approvals; knowledge_collections/tags/relations; documentation_templates; generated_document_jobs; knowledge_retention_records; search_index_refs | owner/scope/source, content/version/provenance, citations/evidence refs, source refs, ACL, version diff, export bundle, retention | SH-06/07/16 | DRAFT؛ OWN-04 مفتوح |
| C13 — الإعدادات | settings_scopes; setting_definitions; setting_values; inheritance_rules; effective_setting_snapshots; setting_profiles; setting_versions; setting_change_requests; setting_approvals; setting_diagnostics; setting_import_jobs; setting_export_jobs | scope, key, value_type, schema_ref, allowed_scopes, default_ref, sensitivity, secret_ref_only, inheritable, enforced, owner_center, policy_ref, version, expected_version, inheritance source | SH-03/04/12 | DRAFT؛ precedence ومالك القيم مفتوحان |
| C14 — لوحة التحكم | dashboard_layouts; dashboard_widget_preferences; dashboard_saved_views; dashboard_filter_presets; dashboard_display_config; dashboard_summaries/metrics/alert_cards/activity_views/report_projections | source_ref, source_version, as_of, freshness_state, tenant_scope, aggregation_definition, permission_context_ref؛ summaries/metrics projections للقراءة فقط | SH-03/07/08/14 | DRAFT؛ لا يملك حقيقة المؤشرات |

## 4. سجل التكرار والتعارضات التي تمنع اعتماد schema

| ID | الحقول/الكيانات المتداخلة | قاعدة الفصل المقترحة | قرار الاعتماد |
|---|---|---|---|
| FLD-01 | organization_id / tenant_id | توحيد قاموس tenant/org وعلاقاتهما دون افتراض الترادف | OPEN |
| FLD-03 | design_pages / page_registry | C03 يملك هوية الصفحة والمسار؛ C02 يملك لوحة التصميم والعناصر مع page_ref | OPEN |
| FLD-05 | schema/migration مقابل domain rows | C04 يملك schema registry؛ كل مركز مجال يملك سجلات مجاله | OPEN |
| FLD-06 | ai_tasks / SH-08 tasks | C06 يملك AI intent/plan؛ SH-08 يملك generic execution attempts | OPEN |
| FLD-07 | ai usage / SH-11 ledger | C06 metering refs للمزود؛ SH-11 normalized cost ledger | OPEN |
| FLD-08 | qa_test_runs / SH-08 jobs / SH-14 health | C07 test result؛ SH-08 job؛ SH-14 runtime telemetry | OPEN |
| FLD-09 | asset_records / SH-06 asset/storage records | C08 domain asset registry؛ SH-06 binary/upload/scan/storage operations | OWN-01 |
| FLD-10 | asset grants / SH-02 grants | قرار التفويض مركزي في SH-02؛ لا سجل grants قابل للكتابة في C08 | OPEN |
| FLD-11 | integration_runs / SH-08 jobs | C09 business run؛ SH-08 attempts مع run_ref/job_ref | OPEN |
| FLD-12 | connector metadata / secrets | C09 purpose/mapping؛ SH-13 connector runtime؛ Secret Manager للقيمة السرية | OWN-02 |
| FLD-13 | C10 policy decisions / SH-02 access decisions | C10 policy authoring/governance؛ SH-02 runtime evaluation | OPEN |
| FLD-14 | C10 audit_events / SH-04 ledger | SH-04 مصدر سجل التدقيق؛ C10 يحتفظ audit_event_ref | OPEN |
| FLD-15 | C11 release state / SH-08 jobs | C11 release lifecycle؛ SH-08 execution attempts | OPEN |
| FLD-16 | artifact registry/storage | اختيار مصدر binary/registry والتوقيع والاحتفاظ | OPEN |
| FLD-17/18 | C12 document content / SH-16 ingestion / SH-07 index | C12/domain canonical content؛ الخدمات تملك ingestion/index/query metadata فقط | OWN-04 |
| FLD-19 | C13 settings / SH-12 values | C13 UX/change requests؛ SH-12 value store؛ مالك المفتاح يملك الدلالة | OPEN |
| FLD-20 | C14 projections | projection read-only مع source/version/freshness/scope/ACL | OPEN |
| FLD-21/23/25 | actor/time/provenance; enums; owner/steward/creator | قواميس منفصلة مع قواعد مجال، لا تعميم تلقائي | OPEN |
| FLD-22/26 | retention/delete/legal hold/classification/legal basis | يحدد مالك المجال التصنيف والغرض؛ C10 يراجع الضوابط | OPEN |
| FLD-24 | cross-project/global refs | تحقق tenant-consistency ومشاركة صريحة عبر grants | OPEN |

## 5. حقول لا تزال TBD قبل ERD

لا يثبت المصدر المتاح الأنواع/nullability/unique/index/default/foreign-key/delete behavior لمعظم الكيانات. يجب ألا تُملأ هذه الفراغات بالتخمين. لكل حقل في النسخة التالية يلزم:
1. data type + format/precision.
2. nullable/required + default.
3. unique/index/foreign-key/constraint.
4. tenant/project scope and cross-scope behavior.
5. classification + purpose + retention + deletion/legal hold.
6. read/write actor/action/resource/scope.
7. owner and source-of-record.
8. version/concurrency/idempotency behavior.
9. event/API references and compatibility policy.
10. acceptance test and evidence pointer.

## 6. بوابات قبول الكتالوج

| الاختبار المقترح | معيار القبول | الحالة |
|---|---|---|
| CAT-01 completeness | لكل كيان مالك ومصدر وحقول أو قائمة TBD معلنة | SPECIFIED / NOT RUN |
| CAT-02 duplicate SoR | لا يوجد كيانان قابلان للكتابة للحقيقة الدلالية نفسها دون فصل موثق | SPECIFIED / NOT RUN |
| CAT-03 scope integrity | كل مرجع cross-tenant مرفوض افتراضياً ولا يسمح به إلا بمنحة صريحة | SPECIFIED / NOT RUN |
| CAT-04 actor/provenance | كل كتابة حساسة لها actor وsource/provenance حيث يلزم | SPECIFIED / NOT RUN |
| CAT-05 version concurrency | الكتابة بإصدار أساس قديم ترفض أو تدخل مسار تعارض صريح | SPECIFIED / NOT RUN |
| CAT-06 retention | لكل فئة سياسة احتفاظ/حذف/حجز قانوني معتمدة | NOT FULLY SPECIFIED / NOT RUN |
| CAT-07 schema/API compatibility | عقد البيانات والواجهة يملكان إصداراً واختبار توافق | SPECIFIED / NOT RUN |

لا توجد نتائج تنفيذ أو run IDs لهذه الاختبارات. لا تعني SPECIFIED أنها منفذة.

## 7. قرارات مطلوبة قبل اعتماد القاموس

- إغلاق OWN-01..10 وفق الوثيقتين 72 و76.
- إغلاق FLD-01..26 أو اعتماد استثناءات محددة الأثر.
- التحقق المباشر من مصفوفة 36 وتحديثها فقط بعد موافقة المالكين.
- تعيين Domain Owner وSchema Steward وAPI Owner لكل كيان.
- اعتماد أنواع الحقول وnullability والقيود وسياسات الاحتفاظ.
- حسم Zero Trust/SEC-REV-01، RTO/RPO، settings precedence، artifact registry، وSLO/freshness.
- لا يبدأ تنفيذ SQL/API أو تغيير كود التطبيق قبل اعتماد المواصفات وموافقة مستقلة صريحة.

**DERIVED FIELD CATALOG / OPEN FOR OWNER REVIEW / NOT APPROVED / NO IMPLEMENTATION.**
 

## ملحق 82 — تأهيل معرفات قرارات الملكية

الإحالات المختصرة في جدول FLD بالوثيقة إلى قرارات OWN في حزمة 72/76، وليست إلى المعرفات التاريخية المتشابهة في 36. لذلك تُقرأ: FLD-09 = OWN72-01، وFLD-12 = OWN72-02، وFLD-17/18 = OWN72-04. أما OWN36-07 الخاص بـSH-11 وOWN36-08 الخاص بالمؤسسة/مساحة العمل وOWN36-09 الخاص بالأدوار والأذونات فتبقى فجوات مستقلة مفتوحة. المرجع الملزم لصيغة الإحالة المؤهلة هو الوثيقة 82. لم تتغير حالة أي قرار ولم يُعتمد schema.


## ملحق 84 — ملكية tenant / organization / workspace / roles

تبقى FLD-01 وحقول organization_id وtenant_id وworkspace_id مفتوحة: لا تفترض أن tenant_id مرادف organization_id، وأن workspace_id مرجع نطاق لا يمنح صلاحية. حزمة القرار 84 تفصل بين مالك سجل المؤسسة ومساحة العمل والعضوية وبين SH-01 للهوية وSH-02 لمحرك التفويض وC10 لسياسات الأمن. كما تفصل معنى الدور المجالّي عن role/grant runtime. لا توجد أنواع أو nullability أو علاقات نهائية معتمدة، ولا تُغلق FLD-01 أو FLD-10 أو FLD-13 بهذا الملحق.


## ملحق 85 — فصل معرفات الإصدارات

لا تخلط record_version/expected_version (تزامن سجل المجال)، وversion_id/parent_version (هوية إصدار المورد وmetadata SH-10 عند انطباقها)، وcontractVersion (توافق API/event)، وartifactVersion، وreleaseVersion في C11، وchangeSetId. تظل أنواع الموارد وقواعد branching/merge/restore ومالك artifact registry مفتوحة وفق حزمة القرار 85؛ لا تعتمد هذه الإضافة schema أو types.
