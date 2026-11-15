# 61 — مسودة عقود البيانات والواجهات والصلاحيات للمركز C04

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المركز:** C04 — البيانات وقواعد البيانات (17 شاشة).

## 1. مصادر المسودة وحدودها

مشتقة من الوثائق 13 و19 و24 و25، ومن متطلبات إدارة البيانات المرئية، وربط Firebase/Supabase/APIs وقواعد أخرى، والعزل متعدد المؤسسات، والعمل دون اتصال والمزامنة والنسخ والاستعادة.

- الوثيقة 19 تعرض 17 شاشة واختبارات DRAFT-AT-C04-01..17.
- الوثيقة 24 تستعيد ملكية C04 للمصادر والجداول والعلاقات والقيود والاستعلامات والترحيلات، وتوضح أن كثيراً من أسماء الجداول والحقول مقترحة لا مخططاً نهائياً.
- الوثيقة 25 تحدد سياق الاتصال الموحد وقاعدة أن الخدمة المستقبلة تتحقق من الصلاحيات بنفسها.
- الوثيقة 13 تحدد متطلبات الهوية والتفويض وسلامة البيانات وOffline & Sync.

هذه مسودة مشتقة؛ لا تنشئ SQL أو API منشوراً أو اعتماداً نهائياً لمزود قاعدة بيانات، ولا تعدل كود التطبيق.

## 2. ملكية C04 وحدود المسؤولية

C04 يملك:
- تعريفات مصادر البيانات والاتصالات المنطقية ومراجع الأسرار.
- مخططات الكيانات والحقول والعلاقات والقيود والفهارس.
- سياسات الوصول إلى البيانات على مستوى المخطط/المورد، بالتنسيق مع C10.
- الاستعلامات المحفوظة وData Bindings.
- تعريفات migrations وإصدارات المخطط.
- قواعد التحقق وسلامة البيانات وبيانات الاختبار.
- سياسات Offline & Sync ونسخ البيانات واستعادتها ضمن حدود المجال.

لا يملك C04:
- الهوية العامة للمستخدمين ومخزن المصادقة (C10/SH-01).
- ملفات الوسائط والأصول نفسها (C08)؛ يحتفظ C04 بمراجع التخزين والربط.
- الكود المصدري وملفات المحولات (C05).
- نتائج الاختبارات النهائية وقرار الجودة (C07).
- الأسرار كنص مكشوف أو سياسات الحوكمة المركزية (C10).

## 3. نموذج البيانات المنطقي المقترح

### 3.1 مصادر البيانات

| الكيان المقترح | الغرض | الحقول المرشحة | الحالة |
|---|---|---|---|
| data_sources | تعريف مصدر بيانات | id, organization_id, project_id, name, provider_type, source_kind, owner_ref, status, secret_ref, created_at | PROPOSED |
| data_source_versions | إصدارات إعدادات المصدر | id, source_id, version, config_ref, credential_ref, status, created_at | PROPOSED |
| data_source_capabilities | قدرات الموصل | id, source_version_id, capability_key, support_level, limitation_ref | PROPOSED |
| data_source_health_checks | نتائج صحة الاتصال | id, source_id, checked_at, status, latency_ms?, error_code? | PROPOSED |

### 3.2 المخططات والكيانات والحقول

| الكيان المقترح | الغرض | الحقول المرشحة | الحالة |
|---|---|---|---|
| data_schemas | مخطط منطقي | id, organization_id, project_id, name, schema_version, status, owner_ref | PROPOSED |
| schema_versions | نسخ المخطط | id, schema_id, version, content_hash, parent_version_ref, created_by, created_at | PROPOSED |
| data_entities | جداول/مجموعات منطقية | id, schema_version_id, entity_key, display_name, storage_mapping_ref, classification, retention_policy_ref | PROPOSED |
| data_fields | خصائص/أعمدة | id, entity_ref, field_key, data_type, nullable, default_ref, sensitivity, validation_ref | PROPOSED |
| data_relations | علاقات الكيانات | id, source_entity_ref, target_entity_ref, relation_type, cardinality, delete_behavior | PROPOSED |
| data_constraints | قواعد سلامة | id, entity_ref, constraint_type, expression_ref, severity, status | PROPOSED |
| data_indexes | فهارس منطقية/مادية | id, entity_ref, field_refs, index_type, uniqueness, provider_support | PROPOSED |

### 3.3 الوصول والاستعلامات والربط

| الكيان المقترح | الغرض | الحقول المرشحة | الحالة |
|---|---|---|---|
| data_access_policies | سياسات وصول البيانات | id, resource_ref, actor_scope, action, predicate_ref, policy_version, status | PROPOSED؛ C10 يملك السياسة المركزية |
| row_level_policies | قواعد RLS للمزود | id, entity_ref, provider_ref, operation, predicate_ref, policy_hash | PROPOSED |
| saved_queries | استعلامات محفوظة | id, project_id, query_model_ref, parameter_schema_ref, owner_ref, status | PROPOSED |
| query_execution_records | سجل تنفيذ الاستعلام | id, query_ref, actor_ref, correlation_id, result_summary, duration_ms, status | PROPOSED؛ لا يخزن محتوى حساساً بلا داعٍ |
| data_bindings | ربط البيانات بعناصر UI | id, project_id, source_ref, entity_ref, field_refs, transform_ref, target_component_ref | PROPOSED |

### 3.4 الترحيل والمزامنة والنسخ

| الكيان المقترح | الغرض | الحقول المرشحة | الحالة |
|---|---|---|---|
| schema_migrations | تغييرات المخطط | id, project_id, source_version, target_version, migration_plan_ref, checksum, status | PROPOSED |
| migration_steps | خطوات الترحيل | id, migration_ref, step_order, operation_ref, reversible, precondition_ref, postcondition_ref | PROPOSED |
| migration_runs | سجل التنفيذ | id, migration_ref, environment_ref, started_at, completed_at, result, evidence_ref | PROPOSED |
| sync_policies | سياسة المزامنة | id, project_id, entity_ref, direction, offline_scope, conflict_policy, retention | PROPOSED |
| sync_runs | جلسات المزامنة | id, project_id, device_ref, started_at, status, conflict_count, evidence_ref | PROPOSED |
| sync_conflicts | تعارضات المزامنة | id, sync_run_ref, resource_ref, local_version, remote_version, resolution_status | PROPOSED |
| backup_policies | سياسات النسخ | id, scope_ref, schedule_ref, retention_ref, encryption_policy_ref | PROPOSED |
| backup_jobs | مهام النسخ | id, policy_ref, started_at, completed_at, status, artifact_ref, checksum | PROPOSED |
| restore_jobs | مهام الاستعادة | id, backup_ref, target_environment, requested_by, approval_ref, status, verification_ref | PROPOSED |

هذه أسماء كيانات مقترحة لتنظيم النطاق، وليست schema نهائياً أو إلزاماً بجدول مستقل لكل مفهوم.

## 4. قواعد سلامة البيانات والعزل

1. كل مصدر/مخطط/كيان تابع لمؤسسة ومشروع يجب أن يكون له نطاق موثوق.
2. لا يعتمد الخادم على organization_id أو project_id المرسل من العميل وحده؛ يتحقق من العلاقة والعضوية والتفويض.
3. يمنع ربط مفاتيح خارجية أو علاقات بين مؤسسات مختلفة إلا عبر عقد نقل مشروع معتمد.
4. فصل بيانات منصة المصمم عن بيانات التطبيقات التي ينشئها المستخدمون.
5. RLS لا يغني عن تحقق التفويض في الخدمة؛ يجب اختبار الوصول المباشر والوظائف الخلفية والمهام المؤجلة.
6. لا تحفظ كلمات مرور أو مفاتيح API أو connection strings السرية داخل schema أو logs؛ تحفظ في secret manager وتستخدم مراجع آمنة.
7. لا تعرض عينة بيانات حساسة في preview أو logs أو fixtures.
8. كل migration يحمل preconditions وpostconditions وchecksum وخطة تراجع/تعويض؛ لا يفترض أن rollback يعيد بيانات حذفت.
9. الترحيل في الإنتاج يحتاج موافقة مستقلة وخطة backup/restore ونافذة تشغيل عند الحاجة.
10. النسخ الاحتياطي لا يعد دليلاً على نجاح الاستعادة؛ يلزم restore test منفصل والتحقق من الاتساق.
11. مزامنة Offline يجب أن تعيد التحقق من الصلاحيات عند المزامنة؛ سحب التفويض يمنع رفع بيانات لم يعد المستخدم مخولاً بها.
12. تعريف الاحتفاظ والحذف والتصدير يجب أن يراعي النسخ والأرشيفات ومراجع الملفات وسجلات التدقيق.

## 5. عقود C04 المقترحة

أسماء العمليات منطقية مقترحة وليست endpoint paths.

| العملية | النوع | المدخلات الأساسية | المخرجات | ضوابط |
|---|---|---|---|---|
| registerDataSource | Command | provider_type, config_ref, secret_ref, owner_ref | source_ref, validation_status | لا أسرار مكشوفة |
| validateDataSource | Command/Job | source_ref, test_scope | validation_report_ref | أقل صلاحية اتصال |
| createSchema | Command | project_ref, schema_definition | schema_ref, version | التحقق من النطاق |
| addEntity | Command | schema_version_ref, entity_definition | entity_ref | عدم تكرار الاسم |
| addField | Command | entity_ref, field_definition | field_ref | تحقق type/default/nullable |
| defineRelation | Command | source_entity, target_entity, cardinality | relation_ref | منع علاقة يتيمة/عابرة للـtenant |
| defineConstraint | Command | entity_ref, constraint_definition | constraint_ref | validate expression |
| defineIndex | Command | entity_ref, field_refs, index_type | index_plan_ref | عرض التكلفة والأثر |
| defineRowPolicy | Command | entity_ref, actor_scope, actions, predicate | policy_ref | مراجعة C10 |
| validateSchema | Command/Job | schema_version_ref, validation_scope | validation_report_ref | schema lint + invariants |
| previewQuery | Query | query_model_ref, params, safe_limit | preview_result_ref | enforce auth, limit, redaction |
| createDataBinding | Command | source_ref, entity_ref, field_refs, target_ref | binding_ref | type-safe mapping |
| generateTestFixtures | Command | schema_ref, seed_rules, data_classification | fixture_ref | synthetic/non-production |
| configureSync | Command | entity_ref, conflict_policy, offline_scope | sync_policy_ref | privacy + authorization |
| createMigrationPlan | Command | from_version, to_version, changes, dependencies | migration_ref, risk_report | لا تنفيذ تلقائي |
| dryRunMigration | Job | migration_ref, environment_ref | dry_run_report | لا يغير المخطط |
| applyMigration | Command | migration_ref, approval_ref, environment_ref | migration_run_ref | موافقة للإنتاج |
| createRollbackPlan | Command | migration_run_ref, current_state | rollback_plan_ref | لا ضمان استعادة بيانات محذوفة |
| startBackup | Command | scope_ref, policy_ref | backup_job_ref | تشفير + سجل تدقيق |
| requestRestore | Approval/Command | backup_ref, target_env, reason | restore_request_ref | موافقة وفصل صلاحيات |
| verifyRestore | Job | restore_job_ref, integrity_checks | verification_report_ref | إثبات مستقل |
| submitSchemaForApproval | Approval | schema_version_ref, migration_ref, tests_ref | approval_request_ref | مالك البيانات/مراجع |
| decideSchemaApproval | Approval | approval_ref, decision, reason | approved_schema_version | لا AI approval |

## 6. أحداث C04 المقترحة

| الحدث | المنتج | المستهلكون |
|---|---|---|
| DataSourceRegistered | C04 | C09، C12 |
| DataSourceHealthChanged | C04 | C14، C09، C12 |
| SchemaVersionCreated | C04 | C05، C07، C12 |
| SchemaValidationCompleted | C04 | C07، C10، C12 |
| MigrationPlanCreated | C04 | C07، C10، C11 |
| MigrationApplied | C04 | C05، C07، C12 |
| RowPolicyChanged | C04/C10 | C07، C10، C12 |
| SyncConflictDetected | C04 | C14، C12 |
| BackupCompleted | C04 | C14، C12 |
| RestoreVerificationCompleted | C04 | C07، C10، C14، C12 |

يجب تحديد event schemas وversioning وidempotency وretry/dead-letter policies قبل التنفيذ.

## 7. مصفوفة الصلاحيات الأولية

| الفعل | مالك البيانات | محرر بيانات | مسؤول أمن | مختبر | مشاهد | وكيل AI |
|---|---|---|---|---|---|---|
| تعريف مصدر بيانات | نعم | وفق التفويض | مراجعة الأسرار | لا | قراءة | اقتراح |
| تعديل schema | نعم | نعم ضمن النطاق | مراجعة أثر السياسة | لا | قراءة | اقتراح فقط |
| تغيير RLS/row policy | اعتماد مشترك مع C10 | لا افتراضياً | مراجعة/اعتماد | اختبار | لا | ممنوع |
| تنفيذ migration في الإنتاج | موافقة | لا افتراضياً | موافقة حسب الأثر | يراقب | لا | ممنوع |
| إنشاء test fixtures | نعم | وفق التفويض | مراجعة التصنيف | نعم | لا | بيانات اصطناعية فقط |
| تشغيل backup | نعم/مفوض | لا افتراضياً | مراجعة السياسة | لا | حالة فقط | ممنوع |
| طلب restore | نعم/مفوض | لا | مراجعة | تحقق | لا | ممنوع |
| اعتماد schema | مالك/مفوض | لا | مراجعة | نتيجة الاختبار | لا | ممنوع |

هذه مصفوفة أولية، وتحتاج توحيداً مع التفويض المركزي C10، ولا تمثل قرار ملكية نهائياً.

## 8. اختبارات القبول الإضافية المقترحة

تستمر اختبارات المصدر DRAFT-AT-C04-01..17 في الوثيقة 19. وتضاف الحالات التالية كمقترحات جديدة:

| المعرف | السيناريو | النتيجة المطلوبة |
|---|---|---|
| C04-DRAFT-AT-18 | اتصال مصدر بيانات دون secret_ref صالح | رفض الاتصال وعدم تسجيل سر مكشوف |
| C04-DRAFT-AT-19 | استعلام من مؤسسة أخرى | رفض خادمي وتسجيل correlation_id |
| C04-DRAFT-AT-20 | علاقة بين كيانين من tenant مختلف | رفض العلاقة |
| C04-DRAFT-AT-21 | migration يفشل في خطوة وسيطة | إيقاف آمن وإظهار حالة غير مؤكدة وخطة معالجة |
| C04-DRAFT-AT-22 | migration بلا dry-run أو موافقة إنتاج | منع التنفيذ |
| C04-DRAFT-AT-23 | RLS يسمح بالقراءة لكنه يمنع الكتابة | اختبار كل عملية على حدة |
| C04-DRAFT-AT-24 | سحب صلاحية مستخدم أثناء Offline | رفض مزامنة الكتابة غير المصرح بها |
| C04-DRAFT-AT-25 | استعادة نسخة احتياطية | تحقق integrity وtenant isolation قبل إعلان النجاح |
| C04-DRAFT-AT-26 | محاولة AI قراءة بيانات خارج التفويض | رفض الطلب وتسجيل الحدث |
| C04-DRAFT-AT-27 | تغيير نوع حقل مستخدم | تحليل أثر وإظهار migration غير متوافق |
| C04-DRAFT-AT-28 | حذف كيان مرتبط بمخرجات معتمدة | منع الحذف الصامت وطلب معالجة الاعتماديات |

كل الاختبارات مواصفات مقترحة وغير منفذة.

## 9. الفجوات المتبقية

- اختيار قواعد البيانات والمزودين المدعومين للإصدار الأول وإصداراتهم.
- نوع المخطط التنفيذي وschema registry ومصفوفة دعم قدرات كل مزود.
- أنواع الحقول والقيود والفهارس النهائية.
- SQL/migration generation وآلية التوافق بين المزودين.
- data lineage وclassification وretention/deletion.
- transaction boundaries وconcurrency/locking.
- سياسات RLS/ABAC النهائية واختبارات العزل الشاملة.
- سياسات Offline conflict resolution وتخزين الجهاز والتشفير.
- backup/restore architecture وRTO/RPO ونتائج استعادة فعلية.
- مراجعة مالكي C04 وC10 وC07 وC11 وC08.

## 10. أثر المسودة على التتبع

توفر هذه الوثيقة مسودة بيانات/API/AUTH لمحور AX-011 أساساً، ولأجزاء من AX-042 وAX-037 عند انطباق الملكية. لا تُرقى الحالات إلى APPROVED أو TEST-PASSED؛ وتبقى مراجعة المالك وschema review وcontract review والاختبارات الفعلية مطلوبة.

**النتيجة:** تم إنشاء أول مسودة تعاقدية متكاملة لمركز البيانات، مع حفظ جميع القرارات التقنية غير المحسومة كخيارات مفتوحة.
