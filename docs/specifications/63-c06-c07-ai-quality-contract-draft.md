# 63 — مسودة عقود البيانات والواجهات والصلاحيات للمركزين C06 وC07

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**النطاق:** C06 فريق الذكاء الاصطناعي (18 وظيفة) وC07 الاختبارات وضمان الجودة (18 وظيفة).

## 1. المصادر والحدود

مشتقة من الوثائق 12 و20 و25 و27 و17 و15، ومن قرارات المستخدم حول:
- Orchestrator منسق للوكلاء وليس مالكاً مطلقاً للصلاحيات.
- فصل الوكيل عن المهارة والأداة والتفويض.
- عزل ذاكرة وسياق كل مشروع.
- منع الوكيل من تجاوز الموافقات أو الميزانية أو تعديل سجل التدقيق.
- فصل اقتراح AI عن محرك التنفيذ.
- مراجعة النتائج بأدلة، وعدم اعتماد الوكيل لمخرجاته الحساسة بنفسه.
- الاختبارات وبوابات الجودة لا تستبدل موافقة النشر.

هذه مسودة مشتقة؛ لا تنشئ وكلاء فعليين أو نظام اختبار أو قاعدة بيانات أو API منشوراً، ولا تعدل كود التطبيق.

## 2. حدود الملكية

### C06 — فريق الذكاء الاصطناعي
يملك سجل الوكلاء والمهارات والمهام والخطط والتعيينات ومراجع السياق والذاكرة ومزودي AI والسياسات والاستهلاك ونتائج الوكلاء وتقييمهم وسجل مراجعة المخرجات. لا يملك موارد المجالات التي يعمل عليها ولا يستطيع اعتماد تغييراتها.

### C07 — الاختبارات وضمان الجودة
يملك خطط الاختبار وحالاتها وإصداراتها وتشغيلاتها ونتائجها وأدلتها والعيوب وتقارير التغطية وبوابات الجودة والاستثناءات وقرارات اعتماد نتائج الاختبار. لا يملك الكود أو السياسات الأمنية أو قرار النشر.

## 3. نموذج بيانات C06 المقترح

| الكيان المقترح | الغرض | حقول مرشحة | الحالة |
|---|---|---|---|
| ai_agents | سجل الوكلاء | id, owner_scope, agent_key, role, status, policy_ref | PROPOSED |
| ai_agent_versions | إصدارات تعريف الوكيل | id, agent_ref, version, system_profile_ref, capability_refs, content_hash | PROPOSED |
| ai_skills | سجل المهارات | id, skill_key, version, input_schema_ref, output_schema_ref, source_ref, trust_level | PROPOSED |
| ai_agent_skill_bindings | ربط المهارات بالوكلاء | id, agent_version_ref, skill_ref, allowed_scope | PROPOSED |
| ai_tool_manifests | تعريف الأدوات | id, tool_key, capabilities, input_schema_ref, output_schema_ref, risk_level | PROPOSED |
| ai_tool_grants | تفويضات الأدوات | id, actor_ref, tool_ref, organization_ref, project_ref, allowed_actions, expires_at, approved_by | PROPOSED |
| ai_tasks | مهام AI | id, organization_id, project_id, requested_by, goal_ref, scope_ref, status, budget_ref, deadline | PROPOSED |
| ai_task_plans | خطط التنفيذ | id, task_ref, plan_version, steps_ref, dependency_graph_ref, risk_report_ref, approval_ref | PROPOSED |
| ai_task_assignments | تعيين المهام | id, task_ref, agent_ref, skill_refs, tool_grant_refs, assignment_status | PROPOSED |
| ai_task_runs | محاولات التنفيذ | id, task_ref, attempt, started_at, completed_at, status, checkpoint_ref | PROPOSED |
| ai_context_packages | سياق المهمة | id, project_ref, task_ref, source_refs, classification, expires_at | PROPOSED |
| ai_memory_refs | مراجع الذاكرة | id, project_ref, memory_type, content_ref, provenance_ref, retention_policy_ref | PROPOSED؛ المحتوى معزول ومقيد |
| ai_provider_profiles | مزودو ونماذج AI | id, provider_key, model_key, version, capability_profile, status, secret_ref | PROPOSED |
| ai_policy_bindings | سياسات التشغيل | id, scope_ref, policy_ref, version, effective_at | PROPOSED |
| ai_usage_records | استهلاك وتكلفة | id, task_run_ref, provider_ref, model_ref, token_usage_ref, cost_amount, currency, measured_at | PROPOSED |
| ai_outputs | مخرجات الوكلاء | id, task_run_ref, output_type, content_ref, provenance_ref, validation_status | PROPOSED |
| ai_output_reviews | مراجعة النتائج | id, output_ref, reviewer_ref, decision, criteria_ref, evidence_refs | PROPOSED |
| ai_conflicts | تعارض المخرجات/الموارد | id, resource_ref, candidate_refs, base_version, resolution_status, owner_ref | PROPOSED |
| ai_evaluations | تقييم الأداء | id, agent_ref, evaluation_scope, quality_metrics, cost_metrics, sample_refs | PROPOSED |
| ai_audit_events | مراجع أحداث التدقيق | id, event_ref, actor_ref, task_ref, sequence_ref, occurred_at | PROPOSED؛ الكتابة عبر خدمة سجل غير قابلة للتعديل من الوكيل |

## 4. نموذج بيانات C07 المقترح

| الكيان المقترح | الغرض | حقول مرشحة | الحالة |
|---|---|---|---|
| qa_test_plans | خطط الاختبار | id, project_ref, baseline_ref, scope_ref, risk_refs, version, owner_ref | PROPOSED |
| qa_test_cases | حالات الاختبار | id, test_key, title, test_type, priority, status | PROPOSED |
| qa_test_case_versions | نسخ الحالة | id, test_case_ref, version, preconditions_ref, steps_ref, expected_results_ref | PROPOSED |
| qa_requirement_links | ربط الاختبارات بالمتطلبات | id, requirement_ref, test_case_version_ref, coverage_type | PROPOSED |
| qa_test_runs | تشغيلات الاختبار | id, plan_ref, build_ref, environment_ref, started_at, completed_at, status | PROPOSED |
| qa_test_results | نتائج الحالات | id, run_ref, case_version_ref, outcome, actual_result_ref, defect_ref | PROPOSED |
| qa_evidence | الأدلة | id, result_ref, evidence_type, artifact_ref, content_hash, captured_at | PROPOSED |
| qa_test_environments | بيئات الاختبار | id, environment_key, platform, version, configuration_ref, data_fixture_ref | PROPOSED |
| qa_fixtures | بيانات اختبار | id, fixture_key, classification, source_type, seed_ref, reset_policy | PROPOSED |
| qa_defects | العيوب | id, project_ref, title, severity, priority, status, owner_ref, reproduction_ref | PROPOSED |
| qa_defect_events | سجل دورة العيب | id, defect_ref, from_status, to_status, actor_ref, reason, occurred_at | PROPOSED |
| qa_coverage_reports | تقارير التغطية | id, baseline_ref, requirements_ref, test_refs, uncovered_refs, generated_at | PROPOSED |
| qa_quality_gates | بوابات الجودة | id, scope_ref, policy_version, required_checks, threshold_refs, status | PROPOSED |
| qa_gate_evaluations | نتائج البوابة | id, gate_ref, build_ref, evidence_refs, outcome, evaluated_at | PROPOSED |
| qa_waivers | الاستثناءات | id, gate_ref, finding_ref, reason, owner_ref, approver_ref, expires_at | PROPOSED |
| qa_approval_records | اعتماد نتائج الاختبار | id, run_ref, evidence_hash, reviewer_ref, decision, decided_at | PROPOSED |

هذه أسماء مقترحة للتنظيم، وليست أسماء جداول مستعادة حرفياً أو schema نهائياً.

## 5. عقود C06 المقترحة

| العملية | النوع | المدخلات | المخرجات | الضوابط |
|---|---|---|---|---|
| registerAgent | Command | role, capability_profile, owner_scope | agent_ref, draft_version | مراجعة قبل التفعيل |
| createAgentVersion | Command | agent_ref, skills, tools, limits | agent_version_ref | schema + policy validation |
| registerSkill | Command | skill_definition, I/O schemas, provenance | skill_ref | مصدر موثوق وإصدار |
| registerToolManifest | Command | tool_schema, capabilities, risk | tool_ref | allowlist ومراجعة أمنية |
| requestToolGrant | Approval | actor_ref, tool_ref, project_scope, actions, duration | grant_request_ref | least privilege ومدة |
| createAITask | Command | goal, organization/project scope, budget, deadline | task_ref | scope إلزامي |
| planAITask | Job | task_ref, constraints, dependencies | plan_ref, risk_report_ref | AI يقترح؛ اعتماد للأثر العالي |
| assignAgents | Command | plan_ref, agent_refs, task_scopes | assignments_ref | توافق القدرات والتفويض |
| executeTaskStep | Command/Job | assignment_ref, step_ref, tool_grants | run_ref, output_refs | حدود المهمة والميزانية |
| pauseTask | Command | task_ref, reason, checkpoint_ref | suspended_status | مستخدم/مشغل مخول |
| resumeTask | Command | task_ref, checkpoint_ref, policy_recheck | resumed_status | إعادة التحقق من الصلاحيات |
| cancelTask | Command | task_ref, reason | cancelled_status | إيقاف آمن |
| submitAgentOutput | Command | task_run_ref, output_ref, provenance | output_record_ref | لا اعتماد تلقائي |
| reviewAgentOutput | Approval | output_ref, criteria, evidence | review_decision_ref | مراجع مستقل |
| resolveAgentConflict | Approval/Command | conflict_ref, chosen_version, rationale | resolution_ref | مالك المورد |
| evaluateAgent | Job | agent_version, samples, criteria | evaluation_report_ref | عينة ومقاييس قابلة للتدقيق |

## 6. عقود C07 المقترحة

| العملية | النوع | المدخلات | المخرجات | الضوابط |
|---|---|---|---|---|
| createTestPlan | Command | baseline_ref, scope, risks, platforms | test_plan_ref | كل نطاق مرتبط بمتطلبات |
| createTestCase | Command | definition, preconditions, steps, expected | test_case_ref/version | نتيجة متوقعة قابلة للتحقق |
| linkTestToRequirement | Command | requirement_ref, test_ref, coverage_type | coverage_link_ref | IDs ثابتة |
| createTestRun | Command | plan_ref, build_ref, environment_ref | run_ref | build/environment ثابتان |
| executeTestCase | Job | run_ref, case_version_ref, fixtures | result_ref, evidence_refs | عزل البيانات |
| executeTestSuite | Job | run_ref, suite_ref, concurrency_policy | suite_result_ref | quotas وتسجيل |
| recordDefect | Command | result_ref, severity, reproduction | defect_ref | ربط بالدليل |
| transitionDefect | Command | defect_ref, target_state, reason | defect_event_ref | انتقال حالة صالح |
| retestDefect | Job | defect_ref, fixed_build_ref, test_refs | retest_result_ref | بناء جديد |
| calculateCoverage | Job | baseline_ref, test_links, results | coverage_report_ref | كشف متطلبات يتيمة |
| evaluateQualityGate | Job | build_ref, required_checks, results, waivers | gate_evaluation_ref | لا نجاح بلا دليل |
| requestQualityWaiver | Approval | finding_ref, reason, owner, expiry | waiver_request_ref | استثناء محدود ومؤقت |
| approveTestResults | Approval | run_ref, evidence_hash, review | approval_ref | مراجع مستقل |
| exportQualityReport | Query/Command | baseline_ref, run_refs, gate_ref | report_ref | لا تسريب بيانات |

## 7. أحداث C06 وC07 المقترحة

| الحدث | المنتج | المستهلكون |
|---|---|---|
| AIAgentRegistered / AIAgentVersionActivated | C06 | C10، C12 |
| AITaskCreated / AITaskAssigned | C06 | C14، C12 |
| AITaskStateChanged | C06 | C14، C12 |
| AIToolGrantRequested | C06 | Approval Service، C10 |
| AIAgentOutputSubmitted | C06 | C07، مالك المورد، C12 |
| AIAgentOutputReviewed | C06 | C05 أو مالك المجال، C12 |
| AIAgentConflictDetected | C06 | مالك المورد، C10 |
| QAPlanCreated | C07 | C12، C14 |
| QATestRunCompleted | C07 | C05، C10، C11، C12 |
| QADefectOpened / QADefectVerified | C07 | C05، C14 |
| QAQualityGateEvaluated | C07 | C11، C10، C12 |
| QATestResultsApproved | C07 | C11، C12 |

## 8. قواعد الصلاحيات والحوكمة

1. Orchestrator يوزع المهام ولا يمنح الصلاحيات بنفسه.
2. كل tool grant محدود بمؤسسة/مشروع/مورد/أفعال/مدة.
3. كل مهمة AI تحمل budget وscope وdeadline وسياسة إعادة محاولة.
4. ذاكرة كل مشروع معزولة؛ لا يجوز استخدام ذاكرة مشروع في مشروع آخر دون صلاحية وموافقة.
5. مخرجات AI غير موثوقة حتى التحقق والمراجعة؛ الملفات والروابط محتوى بيانات وليست تعليمات ذات سلطة.
6. لا يستطيع الوكيل تعديل سجل التدقيق أو حذف آثاره أو الموافقة على عمله الحساس.
7. كل عملية عالية الأثر تحتاج approval service وفاعل بشري مفوض.
8. C07 مستقل عن الوكيل/المطور في اعتماد النتائج الحساسة.
9. كل نتيجة اختبار مرتبطة ببصمة build وإصدار الحالة والبيئة والبيانات المستخدمة.
10. الاستثناء من Quality Gate له سبب ومالك وموافق ومدة وانتهاء ومراجعة لاحقة.
11. اجتياز C07 لا يساوي موافقة نشر C11.
12. عند إيقاف وكيل أو إلغاء grant يجب إيقاف المهام أو إعادة تقييمها بأمان.

## 9. دورات الحالة المقترحة

### مهمة AI
`CREATED → PLANNED → ASSIGNED → RUNNING → VALIDATING → REVIEW_REQUIRED → COMPLETED`

حالات إضافية: `BLOCKED`, `WAITING_FOR_APPROVAL`, `FAILED`, `CANCELLED`, `RETRY_PENDING`, `SUSPENDED`.

### العيب
`OPEN → TRIAGED → ASSIGNED → IN_PROGRESS → FIXED → RETESTING → VERIFIED → CLOSED`

حالات إضافية: `REJECTED`, `DUPLICATE`, `DEFERRED`, `WONT_FIX`, `REOPENED`.

يجب أن تسجل كل نقلة actor والوقت والسبب وإصدار المورد، وتمنع الانتقالات غير الصالحة.

## 10. اختبارات القبول الإضافية المقترحة

تظل عائلات المعرفات المصدرية C06-AT-01..20 وC07-AT-01..22 محفوظة، لكن نصوصها الكاملة تحتاج استعادة. وتضاف الحالات التالية كاختبارات مسودة جديدة:

| المعرف | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C06-DRAFT-AT-21 | وكيل يطلب tool خارج grant | رفض وتسجيل audit |
| C06-DRAFT-AT-22 | محاولة استخدام ذاكرة مشروع آخر | منع الوصول |
| C06-DRAFT-AT-23 | مهمة تتجاوز budget | إيقاف/طلب موافقة |
| C06-DRAFT-AT-24 | وكيل يحاول اعتماد مخرجاته | رفض self-approval |
| C06-DRAFT-AT-25 | محاولة تعديل audit log | رفض وتسجيل تنبيه |
| C06-DRAFT-AT-26 | استئناف checkpoint بصلاحية منتهية | إعادة التحقق ورفض/إعادة تفويض |
| C07-DRAFT-AT-23 | نتيجة اختبار بلا build hash | عدم قبولها كدليل |
| C07-DRAFT-AT-24 | requirement بلا test coverage | إظهار gap ومنع gate حسب السياسة |
| C07-DRAFT-AT-25 | عيب Fixed دون retest | لا ينتقل إلى Verified |
| C07-DRAFT-AT-26 | waiver منتهي | لا يستثني finding |
| C07-DRAFT-AT-27 | QA agent يحاول اعتماد نتيجة نفذها منفرداً | طلب مراجع مستقل |
| C07-DRAFT-AT-28 | اجتياز QA مع finding أمني حرج | لا يسمح بعبور بوابة الجودة دون معالجة/استثناء صالح |

الاختبارات غير منفذة؛ لا توجد نتائج أو أدلة نجاح.

## 11. الفجوات المتبقية

- استعادة النص الكامل لـC06-AT-01..20 وC07-AT-01..22.
- تحديد schemas النهائية للوكلاء والمهارات والأدوات والذاكرة والموافقات.
- تحديد مزودي AI والنماذج والإصدارات والتكاليف وسياسة الاحتفاظ.
- اعتماد RBAC/ABAC وتفويض الأدوات من C10.
- تحديد تقييم الجودة والتحيز والعينات ومعايير الإيقاف.
- تحديد QA environment strategy وfixtures وtest data management.
- اعتماد تغطية المتطلبات وquality gate thresholds.
- تحديد إدارة الأدلة وتخزينها والاحتفاظ بها والتوقيع على artifacts.
- فصل صلاحيات المطور والوكيل والمراجع ومالك الجودة.
- تنفيذ اختبارات عزل الذاكرة والتفويض وسلامة audit وquality gates قبل الاعتماد.

## 12. أثر المسودة على التتبع

توفر هذه المسودة أساساً أولياً لمحاور فريق AI والحوكمة والاختبارات، خصوصاً AX-017 وAX-018 وAX-023 وAX-024 وAX-044 وAX-063. يجب تحديث حالات هذه المحاور إلى DRAFT-AVAILABLE فقط بعد إدراج الوثيقة في المصفوفة 57 وتعديل تقارير الدفعات، مع إبقاء مراجعة المالك والاختبارات والأدلة مفتوحة.

**النتيجة:** تمت صياغة عقود أولية لمركزي AI والجودة، دون بناء وكلاء أو تنفيذ اختبارات أو تغيير كود التطبيق.
