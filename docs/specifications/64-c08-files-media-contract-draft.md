# 64 — مسودة عقود C08: الملفات والوسائط

**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المرجع:** الوثائق 02 و07 و19 و23 و24 و25 و36 و46 و49؛ قائمة الشاشات المعروفة للمركز C08.  
**حدود المصدر:** تفاصيل المدخلات والمخرجات والملكية والواجهات والصلاحيات والاختبارات هنا استكمال هندسي مشتق، لا استعادة حرفية ولا قرار معتمد.

## 1. المسؤولية وحدود الملكية

C08 هو المالك المقترح لسجل الأصل الرقمي ودورة حياته داخل المشروع: تعريف الأصل، بياناته الوصفية، علاقاته، إصداراته، حالته، وارتباطاته. الخدمة المشتركة SH-06 تقدم واجهة التخزين/الملفات الموحدة وسجلات التشغيل والتخزين والتحقق والمعالجة وفق الوثيقة 36؛ يجب مراجعة هذا الفصل واعتماده من مالك C08 ومالك SH-06 قبل ERD.

- C08 يملك **سجل الأصل** وقرارات تصنيفه وربطه ونشره/أرشفته ضمن المجال.
- SH-06 يملك سجلات تشغيل التخزين، ومراجع مواقع التخزين، ومحاولات المعالجة، والفحوص والـchecksums وفق عقد الخدمة.
- C02 يملك تصميمات ومكونات التصميم؛ C05 يملك الكود والمخرجات البرمجية؛ C12 يملك الوثيقة المعرفية ومحتواها؛ يحتفظ كل منها بمرجع للأصل بدلاً من نقل ملكية المحتوى إلى C08.
- لا يعني امتلاك سجل الأصل امتلاك حساب خارجي أو حق نشر عام أو حق إعادة استخدام المحتوى.

## 2. نموذج البيانات المنطقي المقترح

| الكيان المرشح | المالك | الغرض/الحقول المرشحة | الحالة |
|---|---|---|---|
| asset_records | C08 | asset_id, tenant_id, project_id?, owner_domain, asset_type, display_name, lifecycle_status, created_by, timestamps | PROPOSED |
| asset_versions | C08 | asset_id, version_id, parent_version?, checksum, size, media_type, source_ref, created_at | PROPOSED |
| asset_links | C08 | asset_ref, target_domain, target_ref, relation_type, created_by | PROPOSED |
| asset_metadata | C08 | asset_ref, key, typed_value, visibility, provenance | PROPOSED |
| asset_classifications | C08 | asset_ref, classification, sensitivity, retention_class, legal_basis_ref? | PROPOSED |
| asset_processing_requests | SH-06 | asset_ref, operation, status, attempts, output_refs, error_ref | SHARED-SERVICE PROPOSED |
| asset_access_grants | SH-02/SH-06 | subject, resource_ref, permission, scope, expiry | CENTRAL AUTHORITY PROPOSED |
| asset_retention_events | C08 + SH-04 | policy_ref, action, actor, timestamp, evidence_ref | PROPOSED |

لا تثبت هذه القائمة أسماء جداول أو أنواع SQL أو مخزن ملفات أو حدود حجم أو صيغ مدعومة. يجب ألا تخزن النسخ الثنائية داخل قاعدة البيانات العلائقية لمجرد سهولة الربط.

## 3. العمليات المنطقية المقترحة

| العملية | النوع | المدخلات | النتيجة | التفويض |
|---|---|---|---|---|
| createAssetRecord | Command | project_ref?, asset_type, metadata, source | asset_ref, status | إنشاء أصل ضمن النطاق |
| requestUpload | Command | asset_ref, declared_type, size, checksum? | upload_session_ref, constraints | رفع مخول |
| finalizeUpload | Command | upload_session_ref, storage_receipt | version_ref, scan_status | المالك/الرافع المخول |
| inspectAsset | Query | asset_ref, version_ref? | metadata, scan state, provenance | ACL عند كل قراءة |
| createAssetVersion | Command | asset_ref, source_ref, expected_version | new_version_ref | محرر الأصل |
| linkAsset | Command | asset_ref, target_ref, relation | link_ref | صلاحية كتابة على الأصل والهدف |
| requestTransformation | Command | asset_ref, operation, parameters | processing_job_ref | عملية مسموحة ومفوضة |
| revokeAssetLink | Command | link_ref, reason | revoked status | مالك الأصل/مفوّض |
| archiveAsset | Command | asset_ref, retention_check | archived status | مالك الأصل |
| requestExport | Command | asset_refs, purpose, destination | export_job_ref | صلاحية تصدير ومراجعة السياسة |
| requestDeletion | Approval/Command | asset_ref, reason, retention evidence | deletion_request_ref | موافقة مستقلة عند الحاجة |

هذه أسماء دلالية وليست HTTP paths. كل كتابة تحمل requestId/correlationId وtenant scope وactor وexpected version وidempotency key حيث يلزم.

## 4. قواعد الواجهة والموثوقية

- الرفع يبدأ بجلسة محدودة الصلاحية؛ لا تثق في MIME أو الحجم المرسل من العميل، وتتحقق الخدمة من النوع الفعلي والحجم والبصمة.
- الملفات غير الموثوقة تدخل حالة quarantine ولا تصبح قابلة للتنزيل/المعالجة قبل فحصها.
- يجب فصل معاينة المحتوى عن تنزيل الأصل، وإعادة التحقق من الصلاحية عند كل طلب.
- المعالجة التحويلية تنتج إصداراً/أصلاً مشتقاً جديداً مع provenance؛ لا تستبدل المصدر بصمت.
- الروابط الخارجية تحفظ كمرجع ومصدر وثقة، ولا تُعامل كملف مملوك أو مضمون الاستمرار.
- التصدير والحذف والاحتفاظ عمليات محكومة بسياسة المؤسسة والقانون والعقود.
- يمنع تنفيذ التعليمات الموجودة داخل الملفات باعتبارها تعليمات موثوقة للنظام أو للوكلاء.
- لا تسجل محتوى الملفات أو أسرارها في logs؛ تستخدم معرفات وبصمات ومراجع آمنة.

## 5. مصفوفة الصلاحيات الأولية

| الفعل | مالك المشروع | محرر | قارئ | مسؤول الملفات | وكيل AI |
|---|---|---|---|---|---|
| عرض metadata | وفق عضوية | وفق ACL | وفق ACL | وفق النطاق | عند تفويض القراءة |
| رفع/إصدار | نعم | وفق تفويض | لا | دعم تشغيلي لا ملكية مجال | لا افتراضياً |
| ربط أصل بمجال | نعم | وفق الصلاحية على الطرفين | لا | لا | اقتراح فقط |
| تنزيل/تصدير | وفق سياسة | وفق ACL | لا افتراضياً | تشغيل آمن | لا إلا بمهمة مقيدة |
| حذف/إتلاف | طلب/اعتماد | لا افتراضياً | لا | تنفيذ تقني بعد قرار صحيح | ممنوع |
| تغيير سياسة الاحتفاظ | لا تلقائياً | لا | لا | لا | ممنوع |

المصفوفة مقترحة وتحتاج RBAC/ABAC owner review. SH-02 هو مصدر قرار السماح، ولا يستبدل C08 هذا القرار.

## 6. الأحداث المقترحة

AssetRecordCreated, AssetUploadRequested, AssetVersionReady, AssetQuarantined, AssetScanCompleted, AssetLinked, AssetLinkRevoked, AssetTransformationCompleted, AssetArchived, AssetDeletionRequested, AssetDeleted.

يجب تحديد schema/version، producer، consumer، tenant scope، event id، correlation، idempotency، retention، وسياسة إعادة المحاولة وdead-letter قبل اعتمادها.

## 7. اختبارات القبول المقترحة — غير منفذة

- C08-DRAFT-AT-01: رفض رفع يتجاوز الحد المعتمد دون إنشاء أصل جزئي.
- C08-DRAFT-AT-02: لا يُتاح ملف quarantined للمعاينة أو التنزيل أو AI.
- C08-DRAFT-AT-03: التحقق من MIME الفعلي وعدم الثقة بامتداد الملف.
- C08-DRAFT-AT-04: رفض ربط أصل بمشروع من مؤسسة أخرى.
- C08-DRAFT-AT-05: سحب ACL يمنع القراءة التالية حتى لو بقي cache.
- C08-DRAFT-AT-06: التحويل ينشئ مشتقاً مع مصدر وبصمة ولا يعدل الأصل.
- C08-DRAFT-AT-07: لا يظهر secret أو محتوى حساس في logs أو export metadata.
- C08-DRAFT-AT-08: منع الحذف عند وجود retention/legal hold.
- C08-DRAFT-AT-09: إعادة finalize بنفس idempotency key لا تنشئ إصداراً مكرراً.
- C08-DRAFT-AT-10: فشل SH-06 يظهر حالة قابلة للاستئناف دون ادعاء نجاح التخزين.

## 8. الاعتماديات والقرارات المفتوحة

يعتمد C08 على SH-01/02 للهوية والتفويض، SH-04 للتدقيق، SH-06 للتخزين والملفات، SH-08 للمهام، SH-09 للأحداث، SH-10 للإصدارات عند انطباقها، SH-12 للإعدادات، SH-15 للنسخ، SH-16/SH-07 للفهرسة، وعلى C02/C05/C12 كمستهلكين/مالكي محتوى متخصص.

مفتوح: اعتماد حدود C08/SH-06؛ تصنيف الحساسية والاحتفاظ والحذف؛ حدود الحجم والصيغ؛ مزود التخزين ومناطق البيانات؛ فحص البرمجيات الضارة؛ تشفير وإدارة المفاتيح؛ مشاركة الروابط؛ استعادة الإصدارات؛ سياسات المعالجة بالذكاء الاصطناعي. لا تُخترع قيمة أو مزود قبل قرار.

**الحكم:** عقد مبدئي متكامل للمراجعة، وليس مخططاً أو API معتمداً أو اختباراً منفذاً.
