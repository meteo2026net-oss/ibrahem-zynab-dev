# 60 — مسودة عقود البيانات والواجهات والصلاحيات للمركزين C02 وC03

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**النطاق:** C02 مساحة التصميم (15 شاشة) وC03 الصفحات والتنقل (14 شاشة).

## 1. حدود المسودة ومصادرها

هذه مسودة هندسية مشتقة من الوثائق 01 و02 و19 و24 و25، ومن القرارات المحفوظة بشأن:
- التصميم المرئي ومساعدة AI معاً قبل البرمجة.
- التعديل المباشر للعناصر من المعاينة.
- نظام مكونات مختلط: مشتركة عند الملاءمة ومخصصة للصفحات عند الحاجة.
- معاينة مستقلة حسب المنصة.
- مدير صفحات وخريطة تنقل مرئية.
- كشف الروابط المكسورة والصفحات غير القابلة للوصول.
- AI يقترح تغييرات التنقل ولا يغير المسارات المعتمدة دون موافقة.

كل أسماء الكيانات والعمليات والأحداث الجديدة أدناه مقترحة. لا تمثل SQL أو endpoints منشورة أو معرفات مصدرية أو اعتماداً نهائياً. ولا يُجرى أي تعديل على كود التطبيق.

## 2. حدود الملكية

### C02 — مساحة التصميم
مالك مستندات التصميم وإصداراتها وعناصرها وطبقاتها ومكوناتها وتفاعلاتها وحالاتها وDesign System ومعاينات التصميم ومراجعاته.

لا يملك C02:
- سجل الصفحات والمسارات المعتمدة الذي يملكه C03.
- مخطط البيانات الذي يملكه C04.
- الكود التنفيذي الذي يملكه C05.
- قرار اعتماد AI لنفسه.

### C03 — الصفحات والتنقل
مالك سجل الصفحات والمسارات والانتقالات والقوائم والحراس وروابط التطبيق العميقة وحالات التنقل وخريطة التنقل والتشخيص واعتماد إصدار التنقل.

لا يملك C03:
- مستندات التصميم وعناصرها التي يملكها C02.
- تعريفات الصلاحيات الأمنية المركزية التي يملكها C10؛ يحتفظ C03 بمرجع السياسة وحارس المسار فقط.
- الكود التنفيذي الذي يملكه C05.

## 3. نموذج بيانات C02 المقترح

| الكيان المقترح | الغرض | حقول مرشحة | الحالة |
|---|---|---|---|
| design_workspaces | مساحة التصميم التابعة للمشروع | id, organization_id, project_id, name, status, created_by | PROPOSED |
| design_documents | المستند/اللوحة التصميمية | id, organization_id, project_id, workspace_id, name, document_type, current_version, status | PROPOSED |
| design_document_versions | نسخ المستند | id, document_id, version, content_ref, content_hash, created_by, created_at, change_summary | PROPOSED |
| design_pages | صفحات التصميم داخل المستند | id, document_version_id, page_key, name, order_index, viewport_profile | PROPOSED؛ لا تستبدل page registry الخاص بـC03 |
| design_elements | عناصر اللوحة | id, page_id, element_key, component_ref, parent_ref, geometry, props_ref, z_index | PROPOSED |
| design_layers | ترتيب وتجميع العناصر | id, page_id, layer_key, parent_layer_ref, order_index, visibility, lock_state | PROPOSED |
| component_definitions | تعريفات المكونات | id, owner_scope, name, version, props_schema_ref, slots_schema_ref, events_schema_ref | PROPOSED |
| component_instances | استخدام المكونات داخل الصفحات | id, element_ref, definition_ref, definition_version, overrides_ref | PROPOSED |
| design_tokens | ألوان وخطوط ومسافات وأنماط | id, design_system_ref, token_name, token_type, value, theme, version | PROPOSED |
| design_interactions | التفاعلات | id, element_ref, trigger, action_ref, target_ref, condition_ref, status | PROPOSED |
| design_states | حالات العناصر/الصفحات | id, owner_ref, state_key, condition_ref, transition_ref | PROPOSED |
| design_assets | روابط الأصول المستخدمة | id, element_ref, asset_ref, usage_role, transform_ref | PROPOSED؛ الأصل يبقى مملوكاً لخدمة الملفات |
| design_previews | سجلات المعاينة | id, document_version_ref, platform, viewport, result_ref, warnings_ref, created_at | PROPOSED |
| design_reviews | مراجعات واعتمادات التصميم | id, document_version_ref, reviewer_ref, decision, comments_ref, approved_hash, decided_at | PROPOSED |

قواعد C02:
1. كل مستند تصميم مرتبط بمؤسسة ومشروع موثوقين.
2. كل إصدار تصميم له رقم/معرف ثابت وبصمة محتوى؛ لا تعديل صامتاً لإصدار معتمد.
3. لا يسمح بعنصر يتيم أو دورة parent في شجرة العناصر والطبقات.
4. التعديل المباشر من المعاينة ينشئ change set يمكن مراجعته والتراجع عنه.
5. AI ينتج proposal/diff؛ تطبيقه يتبع الصلاحيات والأثر والموافقة.
6. تحديث مكون مشترك لا يغير المشاريع المرتبطة بصمت؛ يلزم إصدار وسياسة توافق.
7. لا تنسخ الأصول؛ استخدم asset_ref مع ACL وmetadata.
8. لا يتحول اعتماد التصميم إلى إذن برمجة إلا عبر بوابة مستقلة.

## 4. نموذج بيانات C03 المقترح

| الكيان المقترح | الغرض | حقول مرشحة | الحالة |
|---|---|---|---|
| page_registry | سجل صفحات المشروع | id, organization_id, project_id, page_key, name, page_type, lifecycle_status, owner_ref | PROPOSED |
| page_versions | إصدارات تعريف الصفحة | id, page_id, version, design_ref, data_contract_ref, content_hash | PROPOSED |
| route_definitions | المسارات | id, project_id, route_key, path_pattern, target_page_ref, version, status | PROPOSED |
| route_parameters | معلمات المسار | id, route_ref, parameter_name, data_type, required, validation_ref | PROPOSED |
| navigation_edges | الانتقالات | id, source_page_ref, target_page_ref, trigger_ref, condition_ref, route_ref | PROPOSED |
| menu_definitions | القوائم | id, project_id, menu_key, menu_type, version, status | PROPOSED |
| menu_items | عناصر القائمة | id, menu_ref, page_ref, label_ref, order_index, visibility_rule_ref | PROPOSED |
| navigation_components | أشرطة/تبويبات التنقل | id, project_id, component_type, platform_scope, config_ref | PROPOSED |
| route_guard_bindings | ربط حارس/سياسة بالمسار | id, route_ref, policy_ref, resource_scope, enforcement_mode | PROPOSED؛ السياسة يملكها C10 |
| deep_link_rules | الروابط العميقة | id, project_id, uri_pattern, target_route_ref, platform_association_ref, status | PROPOSED |
| navigation_states | حالات التنقل | id, route_ref, state_type, condition_ref, ui_response_ref | PROPOSED |
| navigation_diagnostics | نتائج التشخيص | id, project_id, graph_version, issue_type, source_ref, target_ref, severity, status | PROPOSED |
| user_journeys | سيناريوهات الرحلة | id, project_id, persona_ref, steps_ref, expected_path_ref, version | PROPOSED |
| navigation_reviews | مراجعة واعتماد التنقل | id, project_id, graph_version, reviewer_ref, decision, approved_hash | PROPOSED |

قواعد C03:
1. معرف الصفحة ثابت عند إعادة التسمية؛ لا يعاد استخدامه بعد الحذف.
2. لا يعتمد route إلا إذا كان نمطه صالحاً وهدفه موجوداً.
3. يجب كشف الصفحات orphan والروابط المكسورة والدورات غير المقصودة.
4. إخفاء عنصر القائمة لا يحمي الصفحة أو الخدمة الخلفية.
5. route guard واجهة مساعدة؛ التحقق النهائي من التفويض على الخادم وفق سياسة C10.
6. deep link لا يتجاوز المصادقة أو التفويض أو موافقات الوصول.
7. تغيير مسار معتمد ينشئ version/diff ويحدد المستهلكين المتأثرين.
8. اقتراح AI لا يغير المسارات المعتمدة دون موافقة مناسبة.

## 5. عقود C02 المقترحة

| العملية | النوع | المدخلات | المخرجات | الضوابط |
|---|---|---|---|---|
| createDesignWorkspace | Command | project_ref, name, template_ref? | design_workspace_ref | عضوية المشروع |
| createDesignDocument | Command | workspace_ref, name, document_type | document_ref, version=1 | إنشاء ضمن النطاق |
| getDesignDocument | Query | document_ref, version? | document_version, content_ref, hash | ACL |
| saveDesignChangeSet | Command | document_ref, base_version, operations, client_change_id | new_version/diff | optimistic concurrency, idempotency |
| validateDesignDocument | Command | document_ref, version, validation_scope | validation_report_ref | تحقق بنيوي |
| createComponentDefinition | Command | component_schema, version, scope | component_ref | صلاحية Design System |
| createDesignPreview | Command | document_ref, version, platform, viewport | preview_job_ref | موارد المعاينة وحدودها |
| requestDesignAIProposal | Command | prompt, document_ref, selected_elements, constraints | proposal_ref, diff_ref | AI لا يطبق مباشرة |
| applyDesignProposal | Command | proposal_ref, accepted_changes, expected_version | new_document_version | موافقة/صلاحية بحسب الأثر |
| submitDesignForApproval | Approval | document_ref, version, hash, validation_refs | approval_request_ref | المراجع/المالك |
| decideDesignApproval | Approval | approval_ref, decision, reason | decision_ref, approved_version | بشري مخول |
| getApprovedDesignVersion | Query | project_ref | approved_design_ref, version, hash | قراءة حسب النطاق |

## 6. عقود C03 المقترحة

| العملية | النوع | المدخلات | المخرجات | الضوابط |
|---|---|---|---|---|
| createPage | Command | project_ref, page_key, name, page_type | page_ref | صلاحية تحرير |
| renamePage | Command | page_ref, name, expected_version | page_version | الحفاظ على page_key |
| createRoute | Command | page_ref, path_pattern, parameters, guard_policy_ref | route_ref | تحقق الصلاحية والتعارض |
| updateNavigationGraph | Command | project_ref, base_graph_version, nodes, edges | graph_version, diff | optimistic concurrency |
| validateNavigationGraph | Command | graph_version, validation_scope | diagnostics_ref | فحص targets/orphans/cycles |
| analyzeReachability | Query/Job | graph_version, entry_points, role_context | reachability_report_ref | حسب الدور والسياق |
| createDeepLinkRule | Command | uri_pattern, target_route_ref, platform_scope | deep_link_ref | التحقق من association/auth |
| simulateUserJourney | Command | journey_ref, graph_version, role_context | journey_run_ref | تسجيل الانحراف |
| submitNavigationForApproval | Approval | graph_version, diagnostics_ref, diff_ref | approval_request_ref | لا اعتماد مع أخطاء حرجة دون استثناء |
| decideNavigationApproval | Approval | approval_ref, decision, reason | decision_ref, approved_graph_version | مالك/مراجع مخول |
| getApprovedNavigation | Query | project_ref | graph_version, routes, pages | قراءة وفق ACL |

هذه أسماء منطقية مقترحة وليست endpoint paths نهائية.

## 7. عقود الأحداث المقترحة

| الحدث | المنتج | المستهلكون |
|---|---|---|
| DesignWorkspaceCreated | C02 | C12، C05 |
| DesignDocumentVersionSaved | C02 | C07، C12، C05 |
| DesignValidationCompleted | C02 | C07، C12 |
| DesignApprovalSubmitted | C02 | Approval Service، C10، C12 |
| DesignApproved | Approval Service/C02 | C03، C05، C07، C12 |
| PageRegistered | C03 | C02، C05، C12 |
| RouteDefinitionChanged | C03 | C05، C07، C12 |
| NavigationDiagnosticsCompleted | C03 | C01، C07، C12 |
| NavigationApprovalSubmitted | C03 | Approval Service، C10 |
| NavigationApproved | Approval Service/C03 | C05، C07، C12 |

يجب لاحقاً تحديد event schemas وversioning وidempotency وretry/dead-letter policies.

## 8. مصفوفة الصلاحيات الأولية

| الفعل | مالك المشروع | محرر التصميم/التنقل | مراجع | مشاهد | وكيل AI |
|---|---|---|---|---|---|
| إنشاء/تعديل مستند تصميم | نعم | نعم ضمن النطاق | تعليق | قراءة | اقتراح |
| تعديل عنصر من المعاينة | نعم | نعم | لا | لا | diff فقط |
| إنشاء مكون مشترك | وفق التفويض | وفق صلاحية Design System | مراجعة | قراءة | اقتراح |
| تطبيق اقتراح AI | نعم/مفوّض | وفق الأثر والسياسة | يوصي | لا | ممنوع ذاتياً |
| إنشاء صفحة/مسار | نعم | نعم | تعليق | قراءة | اقتراح |
| تعديل مسار معتمد | موافقة/تفويض حسب الأثر | اقتراح change set | مراجعة | لا | اقتراح فقط |
| اعتماد التصميم/التنقل | نعم/مفوّض | لا افتراضياً | يوصي/يعتمد إذا فوض | لا | ممنوع |
| تغيير سياسة الحارس | وفق C10 | لا | C10/المخول | لا | ممنوع |

هذه المصفوفة مبدئية، ويجب توحيدها لاحقاً مع RBAC/ABAC المركزي في C10؛ لا تمنح واجهة C03 سلطة أمنية مستقلة.

## 9. اختبارات القبول

تُحفظ الاختبارات المصدرية الأولية DRAFT-AT-C02-01..15 وDRAFT-AT-C03-01..14 في الوثيقة 19. وتقترح هذه المسودة استكمال حالات إضافية:

| المعرف المقترح | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C02-DRAFT-AT-16 | تعديل تصميم من مشروع آخر | رفض الوصول وعدم كشف محتوى المستند |
| C02-DRAFT-AT-17 | تطبيق AI proposal على إصدار تغير منذ إنشائه | رفض التعارض أو طلب إعادة توليد diff |
| C02-DRAFT-AT-18 | تحديث مكون مشترك | عدم تغيير المشاريع المرتبطة بصمت |
| C02-DRAFT-AT-19 | اعتماد مستند تصميم | تثبيت version وcontent hash والفاعل |
| C02-DRAFT-AT-20 | تعديل يدوي متزامن مع AI | كشف التعارض وحماية التعديل اليدوي |
| C03-DRAFT-AT-15 | وصول مباشر لمسار مخفي من القائمة دون صلاحية | رفض خادمي |
| C03-DRAFT-AT-16 | route target غير موجود | فشل validation مع تحديد المصدر |
| C03-DRAFT-AT-17 | دورة تنقل غير مقصودة | تشخيص موضع الدورة والأثر |
| C03-DRAFT-AT-18 | deep link لمورد غير مصرح | رفض الوصول وعدم تسريب البيانات |
| C03-DRAFT-AT-19 | تغيير مسار معتمد | إنشاء diff وطلب اعتماد قبل التفعيل |
| C03-DRAFT-AT-20 | اختبار رحلة مستخدم | تسجيل المسار المتوقع والفعلـي والانحراف |

كل الاختبارات المقترحة غير منفذة، ولا تمثل دليلاً على نجاح التطبيق.

## 10. الفجوات المتبقية

- صيغة تصميم مرجعية/IR وربطها بمحرك التصميم ومحرك البرمجة.
- نظام قياس وتحويل وحدات الإحداثيات وresponsive layout.
- undo/redo وbranching والتحرير التعاوني وحل التعارض.
- schemas تفصيلية للخصائص والتفاعلات والحالات والمكونات.
- قواعد route precedence وwildcard/fallback وback-stack وuniversal links.
- سياسة حراس المسارات وتكاملها مع C10 وفرض التفويض على الخادم.
- حدود تخزين المحتوى والوسائط والنسخ والاحتفاظ.
- تحديد المنصات وأحجام المعاينة المدعومة في الإصدار الأول.
- موافقة مالكي C02 وC03 على حدود الملكية والعقود.

## 11. أثر المسودة على حالة التتبع

توفر هذه الوثيقة مسودة بيانات/API/AUTH لمحاور C02/C03 المرتبطة، خصوصاً AX-006..AX-010 وAX-008. لا يجوز تحديث حالات التتبع إلى DRAFT-AVAILABLE إلا بعد إدراج هذه المسودة في تقارير الدفعات ومصفوفة 57، مع إبقاء OWNER-REVIEW وSCHEMA-REVIEW-PENDING وCONTRACT-REVIEW-PENDING.

**النتيجة:** تم إعداد مسودة موحدة تغطي التصميم والتنقل دون تحويلها إلى مواصفات معتمدة أو بدء التنفيذ.
