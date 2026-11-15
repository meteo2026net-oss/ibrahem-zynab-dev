# 175 — مرشحات ربط أحداث وموافقات C11–C14 بسيناريوهات القبول

**الحالة:** SEMANTIC CANDIDATES ONLY / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**النطاق:** 28 حدثاً و8 أنواع موافقات و86 سيناريو قبول محلي في C11–C14.  
**المصادر:** workpapers 128–131، الوثيقة 150، الوثائق 164 و171–174، وسجلات الأحداث/الموافقات 98 و100.

## 1. النتيجة التنفيذية

هذه الوثيقة تسجل مرشحات دلالية لا روابط مصدرية exact. الربط لا يصبح معتمداً إلا بعد مراجعة مالك الحدث/الموافقة، ومالك الاختبار، ومالك المجال، ثم تثبيت Test ID canonical وتعريف oracle والأدلة.

| المركز | الأحداث | مرشحات Event→Test | الموافقات | مرشحات Approval→Test |
|---|---:|---:|---:|---:|
| C11 | 14 | 14 | 3 | 3 |
| C12 | 9 | 9 | 2 | 2 |
| C13 | 4 | 4 | 2 | 2 |
| C14 | 1 | 1 | 1 | 1 |
| **الإجمالي** | **28** | **28** | **8** | **8** |

**الحالة المعتمدة exact بعد هذه الوثيقة: 0/28 أحداث و0/8 موافقات.** كل صف أدناه CANDIDATE / OWNER REVIEW. لا تغيير في سجلات 98 أو100، ولا اعتماد aliases.

## 2. C11 — أحداث البناء والنشر

| Event ID | الحدث | اختبار/اختبارات مرشحة | سبب المرشح وحدود الإثبات |
|---|---|---|---|
| EVT-C11-001 | BuildRequested | C11-AT-001, C11-AT-004 | التفويض ومنع تكرار الطلب؛ يلزم event schema وidempotency scope |
| EVT-C11-002 | BuildStarted | C11-AT-003, C11-AT-004 | ثبات commit وبدء job؛ لا يثبت الاختبار الحالي ترتيب الحدث |
| EVT-C11-003 | BuildCompleted | C11-AT-005, C11-AT-007 | اكتمال build وربط artifacts؛ يلزم terminal state وartifact evidence |
| EVT-C11-004 | BuildFailed | C11-AT-005, C11-AT-006 | فشل runner وتنقيح السبب؛ يلزم failure taxonomy |
| EVT-C11-005 | ArtifactAttested | C11-AT-007, C11-AT-008 | digest/provenance/signature validation |
| EVT-C11-006 | ReleaseCandidateCreated | C11-AT-009, C11-AT-012 | عزل tenant وثبات artifact بعد الموافقة |
| EVT-C11-007 | ReleaseApprovalRequested | C11-AT-010, C11-AT-011 | اكتمال gate evidence وربط الطلب بالمرشح |
| EVT-C11-008 | ReleaseApproved | C11-AT-012, C11-AT-013 | ثبات المرشح وفصل مقدم الطلب عن المعتمد |
| EVT-C11-009 | DeploymentStarted | C11-AT-011, C11-AT-014 | نشر معتمد وترقية بيئة؛ يلزم deployment lifecycle |
| EVT-C11-010 | DeploymentCompleted | C11-AT-015, C11-AT-017 | تحقق ما بعد النشر وعدم إعلان صحة قبل الأدلة |
| EVT-C11-011 | DeploymentFailed | C11-AT-015, C11-AT-018 | فشل النشر أو عدم توافر telemetry؛ يلزم تمييز failure عن unavailable |
| EVT-C11-012 | RollbackRequested | C11-AT-016, C11-AT-019 | خطة التراجع واعتماد backup/migration readiness |
| EVT-C11-013 | RollbackCompleted | C11-AT-017, C11-AT-019 | لا اكتمال دون evidence والتحقق من الاستعادة |
| EVT-C11-014 | PostDeployCheckFailed | C11-AT-015, C11-AT-018 | فشل الفحص أو telemetry؛ يحتاج producer/consumer contract واضح |

## 3. C12 — أحداث المعرفة والتوثيق

| Event ID | الحدث | اختبار/اختبارات مرشحة | سبب المرشح وحدود الإثبات |
|---|---|---|---|
| EVT-C12-001 | KnowledgeDocumentCreated | C12-AT-001, C12-AT-002 | owner/tenant/provenance عند الإنشاء |
| EVT-C12-002 | DocumentVersionCreated | C12-AT-005, C12-AT-006, C12-AT-008 | immutable version وربط المراجعة بالنسخة/hash |
| EVT-C12-003 | DocumentReviewRequested | C12-AT-006, C12-AT-007 | تثبيت النسخة قبل المراجعة ومنع النشر دون قرار |
| EVT-C12-004 | DocumentApproved | C12-AT-007, C12-AT-008 | قرار SH-03 مرتبط بنسخة/hash محددين |
| EVT-C12-005 | DocumentPublished | C12-AT-007, C12-AT-018 | النشر بعد الموافقة والالتزام بقيود الحقوق |
| EVT-C12-006 | KnowledgeIndexUpdated | C12-AT-013, C12-AT-014, C12-AT-022 | idempotency وfreshness وإبطال المشتقات |
| EVT-C12-007 | KnowledgeAccessRevoked | C12-AT-009, C12-AT-010, C12-AT-022 | ACL revocation وعزل tenant وإبطال cache/chunks |
| EVT-C12-008 | DocumentationDriftDetected | C12-AT-021 | كشف drift مع source/version lineage |
| EVT-C12-009 | DocumentArchived | C12-AT-012, C12-AT-019 | legal hold والاحتفاظ؛ BLOCKED حتى اعتماد السياسة |

## 4. C13 — أحداث الإعدادات والتوريث

| Event ID | الحدث | اختبار/اختبارات مرشحة | سبب المرشح وحدود الإثبات |
|---|---|---|---|
| EVT-C13-001 | SettingChangeRequested | C13-AT-004, C13-AT-016 | موافقة وowner وimpact classification |
| EVT-C13-002 | SettingChangeDecided | C13-AT-005, C13-AT-017 | ثبات hash وربط القرار بنتيجة API المالك |
| EVT-C13-003 | SettingApplied | C13-AT-009, C13-AT-017, C13-AT-018 | تطبيق عبر API المالك فقط، وعدم إصدار حدث نجاح عند الفشل، idempotency |
| EVT-C13-004 | SettingVersionRolledBack | C13-AT-007, C13-AT-020 | آثار rollback وتاريخ النسخ والمالك |

## 5. C14 — حدث الإسقاط

| Event ID | الحدث | اختبار/اختبارات مرشحة | سبب المرشح وحدود الإثبات |
|---|---|---|---|
| EVT-C14-001 | DashboardProjectionRefreshed | C14-AT-003, C14-AT-005, C14-AT-019, C14-AT-022 | تثبيت source versions/freshness وidempotent refresh وحفظ lineage؛ الحدث ما زال PROPOSED |

## 6. C11–C14 — مرشحات ربط الموافقات

| Approval ID | الفعل المحمي | اختبار/اختبارات مرشحة | ما يلزم قبل اعتماد الربط |
|---|---|---|---|
| APR-C11-01 | approveRelease | C11-AT-012, C11-AT-013 | owner مستقل، SoD matrix، candidate hash binding |
| APR-C11-02 | deployRelease | C11-AT-011, C11-AT-014 | تحديد البيئات عالية الأثر وشروط الموافقة ونافذة النشر |
| APR-C11-03 | requestRollback | C11-AT-016, C11-AT-019 | سياسة migration/backup ومالكو المجالات المتأثرة |
| APR-C12-01 | publishKnowledgeDocument | C12-AT-007, C12-AT-008 | مالك النشر ومراجع مستقل وربط version/hash |
| APR-C12-02 | exportKnowledgeBundle | C12-AT-018 | تصنيف وحقوق ومالك بيانات وسياسة تصدير معتمدة |
| APR-C13-01 | applyOwnerSettingChange | C13-AT-004, C13-AT-009, C13-AT-017 | مالك المفتاح، نطاق التغيير، expected version، SH-03 |
| APR-C13-02 | rollbackSettingVersion | C13-AT-007, C13-AT-020 | تعريف rollback semantics ومالك المجال ومراجعة C10 عند الأثر الأمني |
| APR-C14-01 | exportDashboardReport | C14-AT-008, C14-AT-018 | مالكو المصادر، classification، snapshot consistency وSH-03 policy |

## 7. ملاحظات سلامة المصدر والملكية

1. منتج الحدث لا يعني بالضرورة مالك التنفيذ: بعض أحداث C11 تصدرها SH-08 أو C07 أو SH-03 وفق workpaper 128؛ وبعض أحداث C12 مصدرها SH-03 أو SH-16 أو مالك ACL؛ لا تعاد نسبتها إلى C11/C12 لمجرد استهلاكها.
2. الحدث EVT-C14-001 ما زال PROPOSED؛ لا يُعامل كحدث منشور.
3. وجود مرشح اختبار لا يثبت أن الاختبار يغطي كامل payload أو ترتيب الحدث أو إعادة التسليم أو أثر التدقيق.
4. أنواع الموافقات الثمانية ما زالت بحاجة إلى اعتماد مالك؛ اسم الفعل المحمي وحده لا يثبت lifecycle أو صلاحية القرار.
5. SH-03 هو lifecycle المشترك للموافقات؛ المراكز تملك سياق القرار ولا تستبدل خدمة الموافقات.
6. لا تعتمد الوثيقة أي تغيير في Domain SoR أو Shared Service SoR.

## 8. بوابات الإغلاق المطلوبة

- اعتماد canonical event name/schema/version وproducer وconsumer وdelivery semantics وcorrelation/idempotency لكل حدث.
- تعيين event owner وdomain owner وtest owner ومراجع QA مستقل.
- اعتماد Test ID canonical ومطابقة كل سيناريو مع setup/steps/fixtures/assertions/cleanup/evidence/environment.
- اعتماد approval policy: requester/approver separation، quorum عند الحاجة، expiry، revocation، reason، evidence، re-approval عند تغير hash/version.
- تشغيل الاختبارات في بيئة معزولة وإرفاق run ID وcommit SHA وartifact/evidence refs.
- تحديث السجلات 98 و100 بعد قرارات المالك فقط.

## 9. الحالة النهائية

| المقياس | الحالة |
|---|---:|
| أحداث C11–C14 ذات مرشح دلالي | 28/28 |
| أحداث ذات ربط exact approved | 0/28 |
| موافقات C11–C14 ذات مرشح دلالي | 8/8 |
| موافقات ذات ربط اختبار exact approved | 0/8 |
| موافقات مالك نهائية مثبتة بهذه الوثيقة | 0/8 |
| اختبارات منفذة بهذه الوثيقة | 0 |
| تغييرات كود/API/schema/database | 0 |

**الحكم:** توفر هذه المراجعة خريطة مرشحة كاملة للأحداث والموافقات في C11–C14 دون تحويل الاستنتاج الدلالي إلى حقيقة مصدرية. تبقى الموافقات والروابط غير معتمدة وغير منفذة. لا تمنح الوثيقة إذناً بالبرمجة أو النشر.
