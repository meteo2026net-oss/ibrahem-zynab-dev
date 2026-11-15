# 62 — مسودة عقود البيانات والواجهات والصلاحيات للمركز C05

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المركز:** C05 — البرمجة.

## 1. مصادر المسودة وحدودها

مشتقة من الوثائق 10 و12 و20 و25 و27، ومن القرارات المعتمدة:
- Angular واجهة المصمم؛ محرك التصميم ومحرك البرمجة مستقلان عنها.
- Monaco محرر الكود.
- التوليد من التصميم والمتطلبات، مع وجود نموذج وسيط IR لا يرتبط بإطار واجهة واحد.
- عدم الكتابة فوق التعديلات اليدوية بصمت.
- الوكلاء يقترحون تغييرات ضمن نطاق مفوض، ولا يعتمدونها أو يدمجونها ذاتياً.
- اعتماد الكود منفصل عن اعتماد المواصفات وموافقة بدء البرمجة.

هذه مسودة عقود منطقية. لا تنشئ code generator أو sandbox أو API منشوراً أو schema نهائياً، ولا تعدل كود التطبيق الحالي.

## 2. الملكية وحدود C05

C05 يملك:
- مساحة العمل البرمجية وملفات المشروع وإصداراتها وتغييراتها.
- نموذج IR ومخرجات التحويل والمحولات البرمجية.
- ملفات manifests وlockfiles وسجل الاعتماديات.
- مهام التوليد والتحقق والتصحيح المقترح.
- مهام Sandbox ونتائجها ضمن بيئة معزولة.
- نتائج فحص الكود والتبعيات والتراخيص بوصفها سجلات تشغيلية، بالتنسيق مع C07 وC10.
- روابط GitHub والـbranches والـcommits والـPR التي ينشئها أو يتعامل معها وفق صلاحية المستودع.
- حزمة تسليم الكود وربطها بالمتطلبات والتصميم والبيانات والاختبارات.

لا يملك C05:
- اعتماد المتطلبات أو التصميم أو مخطط البيانات نيابة عن C01/C02/C04.
- اعتماد نتائج الجودة نيابة عن C07.
- تعريف سياسة الأمن المركزية نيابة عن C10.
- الموافقة على النشر نيابة عن C11.
- منح وكيل AI صلاحيات دائمة أو أوسع من المهمة المفوضة.

## 3. نموذج البيانات المقترح

| الكيان المقترح | الغرض | حقول مرشحة | الحالة |
|---|---|---|---|
| code_workspaces | مساحة البرمجة | id, organization_id, project_id, baseline_ref, status, created_by | PROPOSED |
| code_files | سجل الملفات | id, workspace_id, path, language, encoding, file_kind, current_version, status | PROPOSED |
| code_file_versions | إصدارات الملف | id, file_id, version, content_ref, content_hash, parent_version, created_by, created_at | PROPOSED |
| code_change_sets | مجموعة تغييرات | id, project_id, base_snapshot_ref, author_ref, scope_ref, summary, status | PROPOSED |
| code_change_items | عناصر التغيير | id, change_set_ref, file_ref, operation, patch_ref, before_hash, after_hash | PROPOSED |
| code_ir_documents | النموذج الوسيط | id, project_id, ir_type, version, content_ref, content_hash, source_baseline_ref | PROPOSED |
| code_ir_mappings | ربط IR بالمصادر | id, ir_ref, source_type, source_ref, target_ref, mapping_status | PROPOSED |
| code_generation_jobs | مهام التوليد | id, project_id, source_refs, target_profile, scope_ref, status, created_by, cost_ref | PROPOSED |
| code_generation_artifacts | مخرجات التوليد | id, generation_job_ref, artifact_ref, file_refs, validation_ref, provenance_ref | PROPOSED |
| platform_adapters | محولات المنصات | id, platform, language, framework, adapter_version, compatibility_range, status | PROPOSED |
| dependency_manifests | بيانات الاعتماديات | id, workspace_ref, manifest_ref, ecosystem, version | PROPOSED |
| dependency_lockfiles | نسخ قفل الاعتماديات | id, workspace_ref, lockfile_ref, content_hash, generated_at | PROPOSED |
| dependency_findings | نتائج الفحص | id, dependency_ref, finding_type, severity, license_ref, remediation_ref | PROPOSED |
| sandbox_runs | تشغيلات البيئة المعزولة | id, source_snapshot_ref, runtime_profile, resource_policy_ref, network_policy_ref, status, result_ref | PROPOSED |
| code_diagnostics | أخطاء وتحذيرات | id, workspace_ref, file_ref, location, rule_id, severity, message_ref, status | PROPOSED |
| code_reviews | مراجعات التغيير | id, change_set_ref, reviewer_ref, decision, comments_ref, reviewed_hash | PROPOSED |
| code_approval_records | موافقات الكود | id, change_set_ref, approved_hash, approver_ref, decision, decided_at | PROPOSED |
| github_repository_links | روابط المستودعات | id, project_ref, provider, repository_ref, permission_scope, status | PROPOSED |
| github_change_requests | commits/PR references | id, repository_link_ref, branch_ref, commit_ref, pull_request_ref, status | PROPOSED |
| code_handoff_packages | حزمة تسليم | id, project_ref, baseline_refs, artifact_refs, test_refs, license_report_ref, security_report_ref, status | PROPOSED |

هذه الكيانات لا تعني إنشاء جدول مستقل لكل عنصر؛ قد تدمج بعض المفاهيم وفق قرار schema review. جميعها مقترحات وليست أسماء جداول مستعادة من مصدر أصلي.

## 4. قواعد سلامة التوليد والتغيير

1. لا يبدأ التوليد النهائي من متطلبات أو تصميم غير معتمد؛ يمكن إنشاء معاينة تجريبية فقط مع وسمها بوضوح.
2. كل مهمة توليد تسجل إصدارات المتطلبات والتصميم والتنقل ومخطط البيانات والمعمارية المرجعية المستخدمة.
3. كل ملف مولد يحمل provenance يربطه بمصدره وإصدار المحول وإعدادات التوليد.
4. لا يكتب AI فوق تعديل يدوي دون diff وموافقة وفق الأثر.
5. كل patch يشير إلى base version/hash؛ يرفض أو يطلب إعادة الدمج عند تغير الأساس.
6. لا ينفذ Sandbox على جهاز المضيف أو بيانات الإنتاج؛ يعمل ضمن حدود موارد وعزل محددة.
7. الشبكة الخارجية معطلة افتراضياً في Sandbox؛ أي استثناء يتطلب سياسة وموافقة مناسبة.
8. لا تمرر الأسرار إلى prompt أو logs أو artifacts؛ تستخدم secret references محددة النطاق.
9. كل اعتماد dependency يثبت الإصدار والـlockfile ومعلومات الترخيص والفحص.
10. لا يدمج Commit/PR أو يغير فرعاً محمياً دون صلاحية وموافقة GitHub المطلوبة.
11. لا يعتبر نجاح التوليد نجاحاً وظيفياً؛ يلزم build/test/security/license validation.
12. اعتماد الكود يرتبط ببصمة patch ونتائج الاختبار والبيئة، ولا ينتقل تلقائياً إلى نسخة لاحقة.

## 5. عقود C05 المقترحة

الأسماء أدناه عمليات منطقية، لا endpoint paths نهائية.

| العملية | النوع | المدخلات الأساسية | المخرجات | الضوابط |
|---|---|---|---|---|
| initializeCodeWorkspace | Command | project_ref, approved_baseline_refs, target_profile | workspace_ref | لا baseline غير معتمد للتوليد النهائي |
| listProjectFiles | Query | workspace_ref, path_filter | file_tree_ref | project ACL |
| readProjectFile | Query | file_ref, version? | content_ref, hash, metadata | ACL + redaction |
| proposeFileChange | Command | file_ref, base_hash, patch_ref, reason | change_set_ref, diff_ref | scope + concurrency |
| applyApprovedChangeSet | Command | change_set_ref, approval_ref, expected_hash | new_snapshot_ref | موافقة وصلاحية |
| generateIR | Command/Job | approved_requirement_refs, approved_design_ref, schema_ref | ir_ref, mapping_report_ref | traceability required |
| validateIR | Command/Job | ir_ref, adapter_profile | validation_report_ref | schema + consistency checks |
| generateCodeFromDesign | Job | approved_design_ref, ir_ref, adapter_ref, scope | generation_job_ref | proposal/artifact; no silent merge |
| generateCodeFromRequirements | Job | approved_baseline_ref, architecture_ref, scope | code_task_refs, generation_job_ref | requirement traceability |
| getGenerationStatus | Query | generation_job_ref | status, logs_ref, artifacts | scoped access |
| analyzeDependencies | Job | manifest_ref, lockfile_ref, policies | dependency_report_ref, SBOM_ref | source + license + security |
| proposeDependencyUpdate | Command | dependency_ref, target_version, compatibility_report | change_set_ref | high impact approval |
| runCodeDiagnostics | Job | source_snapshot_ref, ruleset_ref | diagnostics_ref | no production secrets |
| proposeNaturalLanguagePatch | Job | request, allowed_paths, base_snapshot, constraints | patch_ref, diff_ref, rationale | scope enforcement |
| compareDesignAndCode | Job | design_version, code_snapshot, IR_mapping | drift_report_ref | read-only by default |
| createSandboxRun | Command | source_snapshot, runtime_profile, resource_limits, network_policy | sandbox_run_ref | isolated runtime |
| cancelSandboxRun | Command | sandbox_run_ref, reason | cancellation_status | authorized actor |
| createBuildPreparation | Job | source_snapshot, target_platform, toolchain_profile | build_plan_ref | compatibility checks |
| validateBuildConfiguration | Job | build_plan_ref, dependency_lock | validation_report_ref | no unapproved toolchain |
| createGitHubChangeRequest | Command | repository_ref, branch_ref, change_set_ref | commit_ref, PR_ref | repo permission + branch policy |
| requestCodeReview | Approval | change_set_ref, test_refs, scan_refs, license_refs | review_request_ref | independent reviewer |
| approveCodeBaseline | Approval | change_set_ref, reviewed_hash, evidence_refs | approved_code_version | human approval |
| prepareCodeHandoff | Command | approved_code_ref, trace_refs, test_reports, SBOM | handoff_package_ref | completeness gate |

## 6. أحداث C05 المقترحة

| الحدث | المنتج | المستهلكون |
|---|---|---|
| CodeWorkspaceInitialized | C05 | C12، C14 |
| CodeChangeSetCreated | C05 | C07، C10، C12 |
| CodeGenerationStarted | C05 | C06، C14 |
| CodeGenerationCompleted | C05 | C07، C12 |
| CodeDiagnosticsCompleted | C05 | C07، C10 |
| DependencyScanCompleted | C05/C07 | C10، C12 |
| SandboxRunCompleted | C05 | C07، C12 |
| BuildPreparationCompleted | C05 | C07، C11 |
| GitHubPullRequestCreated | C05 | C12، C14 |
| CodeApprovalSubmitted | C05 | Approval Service، C07، C10 |
| CodeBaselineApproved | Approval Service/C05 | C07، C11، C12 |
| CodeHandoffPrepared | C05 | C07، C11، C12 |

## 7. مصفوفة الصلاحيات الأولية

| الفعل | مالك المشروع | مطور/محرر | مراجع | مسؤول أمن | وكيل AI |
|---|---|---|---|---|---|
| قراءة الملفات | وفق ACL | وفق ACL | وفق ACL | وفق النطاق | ملفات المهمة فقط |
| تعديل ملف | نعم | نعم | لا | لا افتراضياً | patch ضمن allowlist |
| توليد كود | موافقة/تفويض | تشغيل وفق السياسة | لا | يراجع الضوابط | مهمة محددة |
| تغيير toolchain | موافقة للقرار عالي الأثر | اقتراح | مراجعة | مراجعة الترخيص/الأمن | ممنوع اعتماده |
| تشغيل Sandbox | نعم/مخول | نعم | قراءة النتيجة | يحدد السياسة | ضمن حدود المهمة |
| وصول Sandbox للشبكة | موافقة صريحة حسب السياسة | لا افتراضياً | لا | اعتماد الاستثناء | ممنوع افتراضياً |
| إنشاء PR | وفق صلاحية المستودع | وفق صلاحية المستودع | مراجعة | فحص | لا يدمج |
| دمج PR/اعتماد كود | مالك/مفوّض | حسب سياسة الفرع | مراجع مستقل | موافقة أمنية عند الحاجة | ممنوع ذاتياً |
| تعديل سجل التدقيق | ممنوع | ممنوع | ممنوع | append-only service | ممنوع |

هذه مصفوفة مقترحة تحتاج مراجعة C10 ومالك المستودع، ولا تحل محل صلاحيات GitHub الفعلية أو branch protection.

## 8. متطلبات Sandbox والعزل

- عزل الملفات ونظام التشغيل والعمليات والموارد.
- CPU/memory/disk/time quotas.
- منع الوصول إلى أسرار المضيف والملفات خارج workspace.
- منع الشبكة افتراضياً، مع allowlist وموافقة عند الحاجة.
- عدم استخدام بيانات إنتاج أو حسابات شخصية في بيئة الاختبار.
- تسجيل صورة runtime وإصدار الأدوات وhash المصدر.
- إيقاف تلقائي عند تجاوز الموارد أو كشف محاولة خروج من النطاق.
- عدم مشاركة filesystem بين مشاريع أو مؤسسات.
- الاحتفاظ بسجلات تشغيل منقحة من الأسرار.
- اختبار العزل كحاجز مستقل قبل السماح بتشغيل كود غير موثوق.

هذه متطلبات تصميم أمنية مقترحة؛ لم يتم بناء Sandbox أو اختباره بعد.

## 9. اختبارات القبول الإضافية المقترحة

تظل C05-AT-01..20 معرفات مستعادة كعائلة اختبارات، لكن نصوصها الأصلية الكاملة تحتاج استعادة ومطابقة قبل اعتمادها. وتقترح المسودة:

| المعرف | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C05-DRAFT-AT-21 | توليد من تصميم غير معتمد | منع التوليد النهائي أو وسمه كتجريبي |
| C05-DRAFT-AT-22 | تغير base hash قبل تطبيق patch | رفض patch القديم أو طلب rebase |
| C05-DRAFT-AT-23 | AI يحاول تعديل ملف خارج allowlist | رفض العملية وتسجيلها |
| C05-DRAFT-AT-24 | توليد ملف مع provenance | ربطه بمتطلبات/تصميم/IR/adapter version |
| C05-DRAFT-AT-25 | dependency بترخيص غير مقبول | إيقاف الاعتماد وطلب مراجعة |
| C05-DRAFT-AT-26 | Sandbox يطلب ملفاً خارج workspace | رفض الوصول |
| C05-DRAFT-AT-27 | Sandbox يحاول الاتصال بالشبكة افتراضياً | منع الاتصال |
| C05-DRAFT-AT-28 | تجاوز CPU/memory/time quota | إيقاف المهمة وتسجيل السبب |
| C05-DRAFT-AT-29 | كشف secret في diff | منع الدمج وإصدار finding |
| C05-DRAFT-AT-30 | PR على فرع محمي دون صلاحية | رفض GitHub للعملية |
| C05-DRAFT-AT-31 | اعتماد كود دون نتائج اختبار مطلوبة | منع الاعتماد أو طلب استثناء موثق |
| C05-DRAFT-AT-32 | محاولة وكيل اعتماد الكود الذي أنشأه | رفض self-approval |

الاختبارات غير منفذة؛ لا توجد نتائج أو أدلة تشغيل.

## 10. الفجوات المفتوحة

- تحديد صيغة IR وإصداراتها وschema validation.
- اختيار محولات المنصات واللغات للإصدار الأول.
- تحديد العلاقة بين ملفات المصدر والـIR والتصميم والبيانات.
- اعتماد سياسة merge وconflict resolution وحماية التعديلات اليدوية.
- تحديد sandbox runtime والحدود والعزل واختبارات الخروج.
- اعتماد إدارة secrets وnetwork egress.
- تحديد مصادر dependency metadata وSBOM وlicense policies.
- اعتماد عقود GitHub والصلاحيات والفروع والحماية.
- تحديد جودة التوليد ومقاييس القبول ومراجعة الكود.
- فصل موافقات الكود والجودة والأمن والنشر.
- استعادة النص الكامل لحالات C05-AT-01..20 من المصدر الأصلي.

## 11. أثر المسودة على التتبع

توفر الوثيقة مسودة أولية لمحاور توليد الكود وإدارة الملفات والتبعيات وIR وSandbox والمراجعة والتسليم، خصوصاً AX-028 وAX-039 وAX-041. يجب أن تبقى الحالات DRAFT-AVAILABLE / REVIEW-PENDING إلى أن تراجع من مالكي C05 وC07 وC10، وتستكمل schemas والعقود والأدلة.

**النتيجة:** أُعدت مسودة عقود C05 دون تنفيذ أي كود أو تغيير ملفات التطبيق أو اعتماد تقنية إضافية.
