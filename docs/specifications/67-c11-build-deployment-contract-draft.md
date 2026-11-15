# 67 — مسودة عقود C11: البناء والنشر

**الحالة:** DERIVED-DRAFT / NOT-APPROVED / NO-IMPLEMENTATION  
**المراجع:** 02 و04 و10 و12 و19 و20 و23–27 و32 و38 و46 و48 و49.

## 1. المسؤولية والحدود

C11 يملك دورة البناء والإصدار والنشر الخاصة بالتطبيق المنتج: إعدادات pipeline للمشروع، manifests الإصدارات، بوابات الإصدار، طلبات النشر، نتائج النشر، وحالة التراجع. SH-08 يملك تنفيذ المهام ومحاولاتها؛ SH-10 يملك metadata وإدارة إصدارات الموارد/change sets؛ SH-11 سجلات الاستهلاك/الميزانية؛ SH-14 telemetry التشغيل؛ SH-15 كتالوج النسخ والاستعادة. مالك المجال يظل مالك الكود/التصميم/البيانات، ولا يحول build artifact ملكية المصدر إلى C11.

## 2. الكيانات المنطقية المقترحة

build_pipelines, build_profiles, build_runs, build_artifacts, artifact_provenance, release_candidates, release_manifests, deployment_targets, deployment_requests, deployment_runs, deployment_approvals, environment_promotions, rollback_plans, rollback_runs, migration_checks, release_health_checks.

جميعها PROPOSED. يجب فصل metadata التي يملكها C11 عن artifact binary ومحتوى المصدر الذي يملكه C05/C08 أو مزود البناء المعتمد. لا تفترض مزود CI أو registry أو منصة توزيع.

## 3. العمليات المقترحة

| العملية | النوع | المدخلات/المخرجات | الضابط |
|---|---|---|---|
| validateBuildProfile | Query/Command | profile/version → diagnostics | schema/policy check |
| requestBuild | Command | source_ref, target, profile, commit → build_job_ref | source permission |
| getBuildResult | Query | build_ref → status, artifacts, logs refs | تنقيح الأسرار |
| attestArtifact | Command | artifact_ref, provenance, checks → attestation_ref | سلسلة مصدر موثوقة |
| createReleaseCandidate | Command | artifact_refs, version, notes → candidate_ref | توافق المنصة |
| submitReleaseForApproval | Command | candidate_ref, evidence → approval_ref | gate completeness |
| approveRelease | Approval | candidate_ref, decision → decision_ref | موافق مستقل |
| deployRelease | Command | candidate_ref, environment, approval_ref → deployment_ref | موافقة صالحة |
| promoteEnvironment | Command | deployment_ref, target_env → promotion_ref | شروط البيئة |
| runPostDeployChecks | Command | deployment_ref, health suite → report_ref | لا إعلان نجاح قبل النتائج |
| requestRollback | Command/Approval | deployment_ref, target, impact → rollback_ref | فحص migrations/side effects |
| getReleaseStatus | Query | release_ref → lifecycle, freshness, evidence | ACL |

## 4. بوابات النشر المقترحة

1. متطلبات وخط أساس معتمد.
2. مصدر محدد وبصمة commit.
3. مراجعة التغييرات والاعتماديات والتراخيص وSBOM عند اعتمادها.
4. بناء قابل لإعادة الإنتاج قدر الإمكان مع provenance.
5. اختبارات C07 المطلوبة للمنصة والبيئة.
6. فحوص أمنية وخصوصية وسياسات أسرار.
7. موافقة إصدار/نشر منفصلة عن موافقة baseline.
8. نشر تدريجي/مراقب وفق سياسة البيئة.
9. فحوص بعد النشر وقياس health.
10. خطة تراجع واختبار migrations والآثار الجانبية.

هذه بوابات مقترحة تحتاج اعتماد مالك المنتج والأمن والتشغيل. لا توجد عتبات جودة أو زمنية معتمدة هنا.

## 5. الصلاحيات

المطور/محرر: طلب build؛ مسؤول الإصدار: إعداد pipeline؛ المراجع: مراجعة artifact/evidence؛ مالك البيئة/المفوّض: الموافقة على النشر؛ مسؤول المنصة: إدارة runners/agents؛ وكيل AI: اقتراح patch أو طلب build داخل sandbox المفوض، لا اعتماد أو نشر production؛ المدقق: قراءة الأدلة. قرار التفويض عبر SH-02، والموافقة عبر SH-03.

## 6. الأحداث المقترحة

BuildRequested, BuildStarted, BuildCompleted, BuildFailed, ArtifactAttested, ReleaseCandidateCreated, ReleaseApprovalRequested, ReleaseApproved, DeploymentStarted, DeploymentCompleted, DeploymentFailed, RollbackRequested, RollbackCompleted, PostDeployCheckFailed.

## 7. اختبارات مقترحة غير منفذة

- C11-DRAFT-AT-01: منع build من commit غير مصرح أو غير مثبت.
- C11-DRAFT-AT-02: عدم تسريب secrets في logs/artifacts.
- C11-DRAFT-AT-03: artifact مرتبط بمصدر وبصمة وبيئة وأدوات البناء.
- C11-DRAFT-AT-04: لا نشر دون approval مرتبط بنفس candidate hash.
- C11-DRAFT-AT-05: تغيير artifact بعد الموافقة يبطل الموافقة.
- C11-DRAFT-AT-06: منع ترقية artifact بين tenants.
- C11-DRAFT-AT-07: فشل post-deploy check يحول الحالة إلى فشل/تحذير وفق سياسة معلنة.
- C11-DRAFT-AT-08: التراجع لا ينفذ migration عكسية غير آمنة.
- C11-DRAFT-AT-09: إعادة تشغيل build بنفس المفتاح لا تنشئ إصداراً مكرراً.
- C11-DRAFT-AT-10: فشل runner لا يعلن build ناجحاً.
- C11-DRAFT-AT-11: فصل بيئات التطوير والاختبار والتجهيز والإنتاج.
- C11-DRAFT-AT-12: provenance غير مكتمل يمنع اعتماد artifact حسب السياسة.

## 8. الاعتماديات والقرارات المفتوحة

C11 يعتمد على C05 للمصدر والكود، C07 للاختبارات، C10 للأمن والحوكمة، C13 للإعدادات، C04 للمخططات والترحيلات، C08 للوسائط/المخرجات، C14 للعرض، SH-03/04/08/10/11/14/15. يلزم اعتماد منصات البناء والتوزيع، artifact registry، pipeline runners، توقيع artifacts، SBOM، release channels، موافقات المتاجر، سياسات rollback، SLO/RTO/RPO، وميزانية الموارد.

**الحكم:** لا يثبت وجود pipeline أو نشر أو build ناجح.
