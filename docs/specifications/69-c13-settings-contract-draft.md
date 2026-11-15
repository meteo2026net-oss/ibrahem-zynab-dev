# 69 — مسودة عقود C13: الإعدادات

**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المراجع:** 08 و21 و23–27 و36.

## 1. قاعدة الملكية

C13 يملك تجربة إدارة الإعدادات المشتركة: تعريف المفاتيح والنطاقات والتوريث والإصدارات وطلبات التغيير والتشخيص. مالك كل مجال يملك دلالة إعداداته وقواعد تطبيقها؛ SH-12 يقدم آلية الإعدادات المشتركة بحسب مصفوفة 36. لا ينفذ C13 تغييرات أمن أو تكامل أو AI أو نشر نيابة عن المراكز المالكة، بل يمرر طلب تغيير عبر عقد المالك.

## 2. نموذج منطقي مقترح

settings_scopes, setting_definitions, setting_values, inheritance_rules, effective_setting_snapshots, setting_profiles, setting_versions, setting_change_requests, setting_approvals, setting_diagnostics, setting_import_jobs, setting_export_jobs.

كل تعريف مرشح يحتوي key, value_type, schema_ref, allowed_scopes, default_ref, sensitivity, secret_ref_only, inheritable, enforced, owner_center, policy_ref, version. كل كيان جديد PROPOSED وليس جدولاً معتمداً.

## 3. حل النطاق والتوريث

المستويات المستعادة في C13: Platform → Organization → Workspace → Project → Environment → User → Session. القاعدة المقترحة:
1. التفضيل الشخصي لا يغير سياسة المؤسسة.
2. القيمة المفروضة من نطاق أعلى لا يجوز تجاوزها بنطاق أدنى.
3. يجب إظهار القيمة الفعالة ومصدرها وسلسلة التوريث.
4. يحدد مالك المفتاح ما إذا كان قابلاً للتوريث أو التخصيص.
5. التغيير المتزامن يتطلب expected_version وتعارضاً صريحاً بدلاً من last-write-wins غير المنضبط.
6. لا تُخزن الأسرار؛ تحفظ secret references فقط.

## 4. عقود العمليات

| العملية | النوع | النتيجة | الحد |
|---|---|---|---|
| getEffectiveSettings | Query | القيم الفعالة ومصادرها | ACL + scope |
| validateSettingValue | Query | validation report | schema المالك |
| updateUserPreference | Command | إصدار شخصي | allowlist |
| requestScopedSettingChange | Command | change request | المالك يحدد التنفيذ |
| decideSettingChange | Approval | قرار مستقل | فصل المهام |
| applyOwnerSettingChange | Command | نتيجة تطبيق من API المالك | لا كتابة مباشرة في مجال آخر |
| compareSettingVersions | Query | diff | ACL |
| rollbackSettingVersion | Command/Approval | نتيجة أو رفض | تحليل الآثار |
| exportNonSecretSettings | Command | bundle منقح | لا أسرار |
| diagnoseInheritance | Query | تعارضات ومصدر القيمة | لا تغيير تلقائي |

## 5. الصلاحيات

المستخدم يعدل تفضيلاته الشخصية؛ مالك المشروع يعدل إعداداته ضمن policy؛ مدير مساحة العمل/المؤسسة وفق scope؛ مسؤول المنصة للنطاق العام؛ مالك المركز يعتمد تعريف مفاتيح مجاله؛ مسؤول الأمن يراجع السياسات الحساسة؛ المدقق يقرأ audit؛ الوكيل يقترح فقط ولا يوافق أو يغير سياسة أو secret. القرار النهائي للتفويض عبر SH-02 والموافقة عبر SH-03.

## 6. اختبارات مقترحة غير منفذة

- C13-DRAFT-AT-01: حل التوريث يعيد القيمة ومصدرها.
- C13-DRAFT-AT-02: قيمة enforced لا تتجاوزها قيمة project/user.
- C13-DRAFT-AT-03: منع قراءة/تعديل إعدادات مؤسسة أخرى.
- C13-DRAFT-AT-04: تغيير حساس لا يطبق قبل موافقة صحيحة.
- C13-DRAFT-AT-05: تغير diff بعد الموافقة يبطل الموافقة.
- C13-DRAFT-AT-06: لا تصدر secret values في bundle أو logs.
- C13-DRAFT-AT-07: rollback يرفض الآثار غير القابلة للعكس دون خطة.
- C13-DRAFT-AT-08: optimistic concurrency يمنع overwrite صامت.
- C13-DRAFT-AT-09: مفاتيح إعدادات المجال تمر عبر API مالك المجال.
- C13-DRAFT-AT-10: تفضيلات المستخدم لا تغير إعدادات بقية أعضاء المشروع.

## 7. التبعيات والقرارات المفتوحة

يعتمد C13 على SH-01/02/03/04/12، C10 للسياسات، وكل مالك مركز لتفسير مفاتيحه، وC14 للعرض. يلزم اعتماد schema definitions، precedence عند تعارض السياسات، قوائم المفاتيح، دورة الموافقة، سياسات الأسرار، تصدير/استيراد الإعدادات، rollback، وملكية SH-12.

**الحكم:** يحافظ على تعريف C13 المستعاد؛ تفاصيل العقود مقترحة وغير معتمدة.
