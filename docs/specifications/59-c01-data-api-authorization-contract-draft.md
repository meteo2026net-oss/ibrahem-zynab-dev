# 59 — مسودة عقود البيانات والواجهات والصلاحيات للمركز C01

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المركز:** C01 — إنشاء المشروع وتحليل المتطلبات  
**النطاق:** استكمال مبدئي للفجوات DATA/API/AUTH التي ظهرت في AX-001..AX-005 وAX-025.

## 1. حدود هذه المسودة

هذه الوثيقة **مسودة هندسية مشتقة** من:
- الوثيقة 01: الرؤية والمتطلبات العامة ورحلة المستخدم.
- الوثيقة 02: C01 وعدد شاشاته (12).
- الوثيقة 19: كتالوج C01 التشغيلي الأولي، بما فيه C01-SCR-01..12 وDRAFT-AT-C01-01..12.
- الوثيقة 24: نموذج المؤسسة ومساحة العمل والمشروع وتتبع المتطلبات وملكية بيانات C01.
- الوثيقة 25: سياق الطلب الموحد وعقود Query/Command/Event/Approval.
- الوثيقة 15: فصل اعتماد المواصفات عن موافقة بدء البرمجة.

لا تستعيد هذه المسودة أسماء جداول أو endpoints أصلية غير موجودة في المصادر. كل اسم كيان إضافي أو عملية أو حدث أدناه **اقتراح للتوحيد والمراجعة**، وليس قراراً معتمداً أو SQL أو API منشوراً. ولا تُستخدم لتغيير كود التطبيق.

## 2. الهدف وحدود المسؤولية

C01 هو مالك دورة فهم المشروع وتحليل المتطلبات وتكوين خط أساسها. يملك بيانات المشروع والمتطلبات والنسخ والمصادر والافتراضات والفجوات والتقديرات الأولية، ويستهلك خدمات الهوية والملفات والذكاء الاصطناعي والموافقات والإعدادات.

لا يملك C01:
- اعتماد تصميم الواجهات نيابة عن C02.
- ملكية خريطة الصفحات والتنقل نيابة عن C03.
- ملكية مخطط البيانات التفصيلي للتطبيق المنشأ نيابة عن C04.
- تنفيذ الكود نيابة عن C05.
- منح الوكلاء صلاحيات أو اعتماد اقتراحاتهم تلقائياً.
- الموافقة على نشر أو إطلاق التطبيق.

## 3. نموذج البيانات المنطقي المقترح

### 3.1 الكيانات المعتمدة المصدرية ذات الصلة

| الكيان | المالك المنطقي | الحقول/الخصائص المثبتة في الوثيقة 24 | ملاحظات |
|---|---|---|---|
| organizations | نطاق المؤسسة | id, name, slug, status, plan_id?, settings_id?, created_at, updated_at | حالات status مقترحة في المصدر |
| workspaces | المؤسسة/مساحة العمل | id, organization_id, name, description, workspace_type, status, created_by, created_at | المؤسسة تمتلك مساحات العمل |
| projects | C01 | id, organization_id, workspace_id, name, slug, description, project_type, lifecycle_status, default_environment_id, created_by, timestamps, archived_at | المشروع يتبع مؤسسة واحدة ومساحة عمل واحدة |
| project_requirements | C01 | UUID، الحالة، الإصدارات، التدقيق، نطاق المؤسسة والمشروع | أسماء الجدول وملكيته مذكورة في الفصل 134؛ تفاصيل الحقول الكاملة لم تُحسم |
| requirement_versions | C01 | إصدار المتطلب وتاريخ تغييره | اسم كيان مقترح في الفصل 134 |
| requirement_relationships | C01/حسب المجال | علاقات المتطلبات | اسم كيان مقترح |
| requirement_artifact_links | C01/مالك الأصل | روابط الأدلة والمراجع | يجب احترام ملكية الملفات وصلاحياتها |
| requirement_acceptance_criteria | C01 مع C07 | معايير قبول المتطلبات | تحتاج ربطاً باختبارات C07 |

### 3.2 كيانات إضافية مقترحة لهذه المسودة

| الكيان المقترح | الغرض | أهم الحقول المرشحة | الحالة |
|---|---|---|---|
| project_idea_versions | حفظ نسخ وصف الفكرة | id, organization_id, project_id, version, description, goals, constraints, created_by, created_at | PROPOSED |
| project_reference_links | ربط الملفات والروابط المرجعية | id, organization_id, project_id, asset_id?, uri?, source_label, source_type, trust_level, scan_status, added_by | PROPOSED؛ لا يخزن محتوى ملف مكرر |
| project_analysis_runs | سجل تحليل الفكرة والمجال | id, organization_id, project_id, input_version, status, model_ref?, started_at, completed_at, output_ref, evidence_refs | PROPOSED؛ بيانات AI تخضع لسياسة الخصوصية |
| project_personas | شرائح المستخدمين واحتياجاتهم | id, organization_id, project_id, name, goals, tasks, context, evidence_refs, version | PROPOSED |
| project_nfr_items | متطلبات غير وظيفية | id, organization_id, project_id, category, metric, target_value, unit, measurement_method, priority, source_ref, status | PROPOSED؛ لا يقبل هدفاً رقمياً بلا وحدة وطريقة قياس |
| project_gaps | النواقص والافتراضات والتعارضات | id, organization_id, project_id, gap_type, description, impact, owner_ref, status, resolution_decision_ref | PROPOSED |
| clarification_threads | سياق الأسئلة والإجابات | id, organization_id, project_id, topic, status, created_at | PROPOSED |
| clarification_items | سؤال واحد وإجابته | id, thread_id, question, reason, options_ref, answer, answered_by, answered_at, resolved_requirement_refs | PROPOSED؛ يمنع إعادة سؤال محسوم بلا سبب جديد |
| technology_proposals | خيارات التقنيات | id, organization_id, project_id, category, option_ref, rationale, alternatives, risks, cost_assumptions, approval_status | PROPOSED؛ لا يتحول الاقتراح إلى اعتماد تلقائي |
| project_estimates | تقدير الموارد والتكلفة | id, organization_id, project_id, estimate_version, scope_ref, assumptions, range_min, range_max, currency, confidence, created_at | PROPOSED؛ نطاق تقديري لا سعر مضمون |
| requirement_baselines | خط أساس المتطلبات | id, organization_id, project_id, version, status, created_by, submitted_at, approved_at, snapshot_ref | PROPOSED؛ يثبت نسخة محددة غير قابلة للتعديل الصامت |

**قاعدة ملكية:** أي كيان يخص متطلبات المشروع أو تحليلها يظل تحت ملكية C01؛ أما الملف الفعلي فيبقى مملوكاً لخدمة الملفات/مالك الأصل، ويحتفظ C01 بعلاقة مرجعية لا بنسخة غير منضبطة.

## 4. قواعد البيانات والعزل وسلامة التغيير

1. كل سجل تابع لمؤسسة يجب أن يحمل `organization_id` أو يرتبط بسجل يثبت المؤسسة بطريقة موثوقة.
2. كل سجل خاص بمشروع يجب أن يحمل `project_id` أو يرتبط به بعلاقة موثوقة.
3. لا يُقبل `organization_id` أو `project_id` المرسل من العميل كدليل صلاحية؛ الخدمة تتحقق من العضوية والتفويض من جهة الخادم وفق سياق الوثيقة 25.
4. لا يسمح بربط متطلب أو ملف أو موافقة بمشروع تابع لمؤسسة أخرى.
5. تغيير متطلب معتمد ينشئ إصداراً جديداً؛ لا يستبدل النسخة المعتمدة بصمت.
6. لا يحذف متطلب مرتبط بتصميم أو كود أو اختبار أو إصدار معتمد؛ يستخدم إيقاف/أرشفة أو إجراء حذف محكوم بسياسة الاحتفاظ.
7. تحفظ مراجع المصدر ووقت الاستيراد ونوعه ومستوى الثقة ونتيجة الفحص؛ لا تُعامل تعليمات الملفات المستوردة كأوامر تنفيذ.
8. لا تُخزن كلمات مرور أو مفاتيح أو أسرار في كيانات C01.
9. تُسجل الموافقات والقرارات والتغييرات بمعرفات ارتباط ومراجع إصدارات.
10. لا يثبت النموذج المقترح تقنية قاعدة البيانات أو نوع UUID أو الفهارس النهائية؛ هذه قرارات خط أساس تقني تحتاج اعتماداً.

## 5. كتالوج عقود C01 المقترح

هذه أسماء عمليات منطقية وليست مسارات HTTP نهائية.

| العملية | النوع | المدخلات الأساسية | المخرجات | شرط التفويض |
|---|---|---|---|---|
| createProject | Command | workspace_ref, name, description?, project_type | project_ref, lifecycle_status | مالك/محرر مخول داخل مساحة العمل |
| updateProjectProfile | Command | project_ref, fields, expected_version | project_version | محرر مخول؛ تحقق من الإصدار |
| saveIdeaVersion | Command | project_ref, idea_text, goals, constraints, source_refs | idea_version_ref | مالك/محرر |
| attachProjectReference | Command | project_ref, asset_ref أو uri, source_metadata | reference_ref, scan_status | صلاحية رفع/ربط ملف؛ فحص المصدر |
| startDomainAnalysis | Command | project_ref, idea_version_ref, reference_refs, analysis_scope | analysis_job_ref, status | صلاحية التحليل؛ AI ينتج مسودة فقط |
| getAnalysisResult | Query | analysis_job_ref | findings, assumptions, risks, evidence_refs, confidence | عضو مخول في المشروع |
| upsertPersona | Command | project_ref, persona_data, expected_version? | persona_ref, version | محرر؛ اعتماد المالك عند تثبيت النسخة |
| createRequirement | Command | project_ref, title, description, source_ref, priority, acceptance_criteria | requirement_ref, status=draft | محرر؛ لا يعتمد تلقائياً |
| reviseRequirement | Command | requirement_ref, change_set, expected_version | new_requirement_version | محرر؛ يفتح تحليل أثر إذا كانت النسخة معتمدة |
| recordNFR | Command | project_ref, category, metric, target, unit, measurement_method, source_ref | nfr_ref | محرر؛ يتطلب وحدة وطريقة قياس |
| analyzeGaps | Command | project_ref, requirement_versions, decisions, source_refs | gap_report_ref | AI يقترح؛ لا يغلق التعارض |
| answerClarification | Command | clarification_ref, answer, source_refs | updated_requirement_refs | المستخدم/المراجع المخول |
| proposeTechnologyOptions | Command | project_ref, constraints, platform_scope, budget_context | proposals, alternatives, risks | AI يقترح؛ موافقة المالك للقرار عالي الأثر |
| createEstimate | Command | project_ref, scope_ref, assumptions, pricing_sources | estimate_ref, range, confidence | عرض للمخولين؛ اعتماد الميزانية للمالك |
| submitRequirementBaseline | Command | project_ref, requirement_version_refs, decision_refs, gap_refs | baseline_ref, approval_request_ref | مالك/مفوّض يطلب الاعتماد |
| decideRequirementBaseline | Approval | baseline_ref, decision, reason | approval_decision_ref, baseline_status | مالك المشروع/المفوّض؛ لا AI |
| getProjectTraceability | Query | project_ref, filters | requirements, sources, decisions, gaps, versions | عضو مخول وفق النطاق |

### 5.1 سياق الطلب الموحد

تستخدم العقود عند انطباقها سياق الوثيقة 25: `requestId`, `correlationId`, `organizationId`, `projectId`, وهوية الفاعل. يجب على الخدمة المستقبلة إعادة التحقق من الصلاحيات وعدم الثقة بمعرفات العميل وحدها.

### 5.2 بنية الأخطاء المقترحة

تتبع عقود C01 نموذج الأخطاء الموحد في الوثيقة 27، مع رموز مرشحة مثل:
- PROJECT_NOT_FOUND
- WORKSPACE_ACCESS_DENIED
- PROJECT_VERSION_CONFLICT
- REFERENCE_SCAN_REJECTED
- REQUIREMENT_SOURCE_REQUIRED
- REQUIREMENT_BASELINE_LOCKED
- BASELINE_APPROVAL_REQUIRED
- CROSS_TENANT_REFERENCE_DENIED
- ANALYSIS_RESULT_PENDING

هذه الرموز أمثلة مقترحة، لا معرفات أصلية معتمدة.

## 6. مصفوفة الصلاحيات الأولية

| الفعل | مالك المشروع | مدير مؤسسة/مساحة | محرر | مراجع | مشاهد | وكيل AI |
|---|---|---|---|---|---|---|
| إنشاء مشروع | نعم | وفق النطاق | وفق التفويض | لا افتراضياً | لا | لا |
| تعديل وصف الفكرة | نعم | وفق التفويض | نعم | تعليق فقط | لا | اقتراح مسودة |
| رفع/ربط مرجع | نعم | وفق التفويض | وفق ACL | لا افتراضياً | لا | لا؛ يمكنه قراءة المراجع المفوضة فقط |
| تشغيل التحليل | نعم | وفق التفويض | نعم | لا افتراضياً | لا | ضمن مهمة مفوضة |
| إنشاء/تعديل متطلب مسودة | نعم | وفق التفويض | نعم | تعليق/مراجعة | قراءة | اقتراح/مسودة |
| اعتماد متطلب أو baseline | نعم/مفوّض | وفق سياسة المؤسسة | لا افتراضياً | يوصي فقط | لا | ممنوع |
| اعتماد الميزانية | مالك الميزانية | حسب التفويض | لا | مراجعة | لا | ممنوع |
| إغلاق تعارض | مالك/مفوّض | حسب السياسة | لا افتراضياً | توصية | لا | ممنوع دون قرار بشري |

هذه المصفوفة **مقترحة**؛ يجب استبدالها بمصفوفة RBAC/ABAC معتمدة تربط actor/action/resource/tenant/project scope، ولا تعتبر دور «مدير المؤسسة» صلاحية تلقائية على كل مشروع.

## 7. دورة حياة المشروع وخط الأساس المقترحة

حالات مرشحة:
- Draft
- Analyzing
- Requirements In Progress
- Needs Clarification
- Conflict Review
- Ready for Baseline Review
- Baseline Pending Approval
- Baseline Approved
- Change Proposed
- Archived

قواعد الانتقال:
1. لا ينتقل إلى Baseline Approved إلا بعد قرار مالك/مفوّض على إصدار محدد.
2. وجود فجوات حرجة يمنع اعتماد baseline أو يتطلب استثناءً صريحاً موثقاً.
3. تغيير متطلب معتمد ينشئ نسخة جديدة ويعيد تقييم التأثير على التصميم والبيانات والواجهات والاختبارات.
4. اعتماد baseline لا يعني الإذن ببدء البرمجة؛ بوابة بدء البرمجة مستقلة.
5. لا يغير AI حالة الاعتماد بنفسه.

## 8. الأحداث المقترحة

| الحدث | المنتج | المستهلكون المحتملون | البيانات الدنيا |
|---|---|---|---|
| ProjectCreated | C01 | C02، C03، C04، C12 | project_ref, organization_ref, workspace_ref, version |
| IdeaVersionSaved | C01 | C06، C12 | project_ref, idea_version_ref, source_refs |
| ProjectReferenceAttached | C01/خدمة الملفات | C06، C12 | reference_ref, scan_status, trust_level |
| DomainAnalysisCompleted | C01/C06 | C01، C12 | analysis_ref, status, evidence_refs |
| RequirementCreated | C01 | C02–C07، C12 | requirement_ref, version, status |
| RequirementVersionChanged | C01 | C02–C07، C12 | old_version, new_version, impact_pending |
| GapDetected | C01 | C06، C10، C12 | gap_ref, severity, owner_ref |
| BaselineSubmitted | C01 | خدمة الموافقات، C10، C12 | baseline_ref, version, requested_by |
| BaselineApproved | خدمة الموافقات/C01 | C02، C03، C04، C05، C07، C12 | baseline_ref, version, approver_ref |

الأحداث مقترحة وتحتاج event schema وversioning وidempotency وسياسة إعادة المحاولة.

## 9. اختبارات القبول المقترحة الإضافية

المعرفات التالية امتداد محلي مقترح بعد DRAFT-AT-C01-01..12؛ ليست معرفات مصدرية:

| المعرف | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C01-DRAFT-AT-13 | محاولة إنشاء مشروع خارج workspace مخول | رفض الطلب وعدم إنشاء سجل جزئي |
| C01-DRAFT-AT-14 | مرجع ملف تابع لمؤسسة أخرى | رفض الربط وتسجيل حدث أمني |
| C01-DRAFT-AT-15 | تعديل متطلب معتمد | إنشاء نسخة جديدة مع بقاء النسخة السابقة |
| C01-DRAFT-AT-16 | متطلب بلا مصدر | إظهاره كغير مكتمل ومنع اعتماده وفق السياسة |
| C01-DRAFT-AT-17 | NFR بلا وحدة قياس | رفضه أو إبقاؤه مسودة غير قابلة للاعتماد |
| C01-DRAFT-AT-18 | تحليل AI يكتشف تعارضاً | إنشاء توصية/فجوة دون إغلاقها تلقائياً |
| C01-DRAFT-AT-19 | سؤال حُسم سابقاً دون تغير السياق | عدم تكراره |
| C01-DRAFT-AT-20 | اقتراح تقنية عالية الأثر | إبقاؤه مقترحاً حتى موافقة المالك |
| C01-DRAFT-AT-21 | اعتماد baseline | تثبيت الإصدار والفاعل والوقت والقرار |
| C01-DRAFT-AT-22 | محاولة وكيل اعتماد baseline | رفض العملية |
| C01-DRAFT-AT-23 | إعادة إرسال createProject بعد انقطاع | عدم إنشاء مشروع مكرر عند استخدام idempotency key |
| C01-DRAFT-AT-24 | تغيير متطلب بعد اعتماد baseline | إنشاء change proposal وتحليل أثر دون تعديل baseline السابق |

كل هذه الاختبارات مواصفات مقترحة؛ لا توجد نتائج تنفيذ أو أدلة نجاح مرفقة.

## 10. الفجوات التي لا تحسمها هذه المسودة

- تحديد أنواع البيانات النهائية والقيود والفهارس وسياسة الترحيل.
- اعتماد نمط API والإصدارات والـschemas الفعلية.
- مصفوفة صلاحيات RBAC/ABAC نهائية ومراجعة مالكيها.
- سياسات retention/deletion والتصدير وحقوق أصحاب البيانات.
- تحديد مستويات الثقة ومصادر المعرفة وسياسة معالجة تعليمات الملفات.
- تحديد أهداف NFR الرقمية ومصادر قياسها.
- تثبيت العملات ومصادر الأسعار وحدود الميزانية.
- تحديد مزودي AI للإصدار الأول وتكاليفهم وحدود البيانات.
- اعتماد حالات دورة حياة المشروع والاستثناءات.
- اعتماد عقود الأحداث واختبارات التكامل.

## 11. أثر المسودة على حالة التتبع

هذه المسودة توفر **DRAFT-AVAILABLE** أولياً للبيانات والعقود والصلاحيات في C01، لكنها لا تبرر تحويل أي حقل إلى APPROVED أو TEST-PASSED. بعد مراجعة المالك والمطابقة مع المصدر الأصلي، يمكن تحديث حالات المحاور AX-001..AX-005 وAX-025 في مصفوفة 57.

**النتيجة:** تمت صياغة أول حزمة مقترحة لسد فجوات C01 دون المساس بالقرارات المعتمدة أو بدء البرمجة. الحالة التالية هي مراجعة مالك C01/المستخدم للمسودة قبل ترقيتها إلى مواصفة معتمدة.
