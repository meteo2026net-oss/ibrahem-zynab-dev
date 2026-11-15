# 126 — حزمة تتبع العمليات والأحداث والاختبارات للمركزين C08 وC09

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / TESTS NOT RUN  
**النطاق:** 23 عملية API مرشحة، 21 حدث مجال مرشحاً، 4 أنواع موافقة، و20 سيناريو قبول محلياً للمركزين C08/C09.  
**المراجع:** 64، 65، 77، 78، 79، 80، 91، 94، 97، 99، 100، 106، 107، 125.

## 1. نتيجة هذه الجولة

جرى تفكيك صفّي C08/C09 في سجل العمليات 97 إلى سجلات مفردة، وربطها بالمرشحات الموجودة في 99، والأحداث في 100، وحدود الخدمات المشتركة في 106/107، والاختبارات المقترحة في 64/65 و80/91.

| البند | C08 | C09 | الإجمالي | دلالة الحالة |
|---|---:|---:|---:|---|
| عمليات API مرشحة | 11 | 12 | 23 | أسماء مشتقة وليست جميعها حرفية المصدر |
| أحداث مجال مرشحة | 11 | 10 | 21 | SOURCE-RECOVERED في سجل 100؛ ليس VERBATIM |
| أنواع موافقة مرشحة | 2 | 2 | 4 | لا يثبت إلزام الموافقة قبل اعتماد السياسة |
| سيناريوهات مسودة محلية | 10 | 10 | 20 | SPECIFIED / NOT RUN |

الروابط AX الواردة في 99 مرشحة؛ مواضع المصدر الأصلي التفصيلية ما زالت TBD. لا يوجد Test ID أصلي مثبت لكل عملية أو حدث. السيناريوهات أدناه تسميات تتبع مشتقة لهذه الحزمة فقط، ولا تستبدل IDs المصدرية.

## 2. سجل عمليات C08 — الملفات والوسائط

| Operation ID | العملية | AX المرشحة من 99 | حد الملكية المقترح | سيناريوهات التحقق المرشحة | فجوة العقد |
|---|---|---|---|---|---|
| OP-C08-121 | createAssetRecord | AX-014, 046, 038 | C08 domain registry | C08-126-T01, T04 | schema/field constraints وtenant scope |
| OP-C08-122 | requestUpload | AX-014, 046, 038 | C08 طلب الأصل؛ SH-06 upload session | T01, T02, T03 | upload session contract وlimits TBD |
| OP-C08-123 | finalizeUpload | AX-014, 046, 038 | C08 يقرر جاهزية الأصل؛ SH-06 storage receipt/scan | T02, T03, T09, T10 | receipt trust, scan state, idempotency |
| OP-C08-124 | inspectAsset | AX-014, 046, 038 | C08 metadata؛ SH-06 storage/scan refs | T02, T05, T07 | ACL on each read, quarantine visibility |
| OP-C08-125 | createAssetVersion | AX-014, 046, 038 | C08 version lineage؛ SH-06 stores bytes | T06, T09 | optimistic concurrency/version/hash |
| OP-C08-126 | linkAsset | AX-014, 046, 038 | C08 relation؛ target domain owns target | T04, T05 | dual-resource authorization and cross-tenant checks |
| OP-C08-127 | requestTransformation | AX-014, 046, 038 | C08 transformation intent؛ SH-08 execution; SH-06 processing | T06, T07, T10 | operation allowlist, provenance, output version |
| OP-C08-128 | revokeAssetLink | AX-014, 046, 038 | C08 link lifecycle | T05 | cache/index invalidation and audit evidence |
| OP-C08-129 | archiveAsset | AX-014, 046, 038 | C08 lifecycle؛ retention policy owner | T08 | archive vs retention vs legal hold semantics |
| OP-C08-130 | requestExport | AX-038, 060 | C08 export request؛ SH-06 transfer/storage | T05, T07, T08 | recipient/purpose/format/expiry/approval binding |
| OP-C08-131 | requestDeletion | AX-014, 046 | C08 request؛ SH-06 executes approved storage action; SH-04 audit | T08 | deletion scope, retention/legal hold, derived copies |

### 2.1 C08 سيناريوهات القبول المحلية

| Scenario ID | شرط الاختبار | العملية/العمليات | Oracle المطلوب | الحالة |
|---|---|---|---|---|
| C08-126-T01 | رفض حجم يتجاوز الحد المعتمد دون أصل جزئي | 121–122 | لا asset record جزئي ولا upload session غير صالح | SPECIFIED / NOT RUN |
| C08-126-T02 | أصل quarantined لا يعرض أو ينزل أو يعالج | 122–124 | deny لكل مسار وصول حتى scan pass | SPECIFIED / NOT RUN |
| C08-126-T03 | عدم الثقة بالامتداد أو MIME المعلن | 122–123 | فحص النوع الفعلي ورفض mismatch وفق policy | SPECIFIED / NOT RUN |
| C08-126-T04 | رفض الربط عبر tenant أو project غير مخول | 121,126 | رفض atomic؛ لا link record ولا تسريب metadata | SPECIFIED / NOT RUN |
| C08-126-T05 | سحب ACL يمنع القراءة التالية حتى مع cache | 124,126,128,130 | reauthorization/invalidation عند الوصول | SPECIFIED / NOT RUN |
| C08-126-T06 | التحويل ينتج مشتقاً مع provenance ولا يعدل الأصل | 125,127 | sourceRef/hash محفوظان وإصدار مشتق جديد | SPECIFIED / NOT RUN |
| C08-126-T07 | منع تسرب محتوى/سر في logs وmetadata | 124,127,130 | logs تحتوي references آمنة فقط | SPECIFIED / NOT RUN |
| C08-126-T08 | منع حذف أصل تحت retention/legal hold | 129,131 | القرار BLOCKED/REJECTED مع audit ref | BLOCKED: policy/legal hold TBD |
| C08-126-T09 | إعادة finalize بنفس idempotency key | 123,125 | أثر إصدار واحد فقط | SPECIFIED / NOT RUN |
| C08-126-T10 | فشل SH-06 لا يظهر تخزيناً ناجحاً | 122,123,127 | حالة قابلة للاستئناف مع storage receipt واضح | SPECIFIED / NOT RUN |

## 3. سجل عمليات C09 — التكاملات والموصلات

| Operation ID | العملية | AX المرشحة من 99 | حد الملكية المقترح | سيناريوهات التحقق المرشحة | فجوة العقد |
|---|---|---|---|---|---|
| OP-C09-132 | registerIntegration | AX-022,040,049 | C09 integration definition | C09-126-T01, T09 | provider allowlist, purpose/scope owner |
| OP-C09-133 | configureIntegration | AX-022,040,049 | C09 mapping/policy؛ SH-13 runtime | T03, T09, T10 | mapping schema/version and secretRef constraints |
| OP-C09-134 | beginAuthorization | AX-012,023,061 | C09 authorization intent؛ SH-13 runtime | T02, T03 | flow-specific state/nonce/PKCE/redirect contract |
| OP-C09-135 | completeAuthorization | AX-012,023,061 | C09 connection domain state؛ SH-13 connector runtime | T02, T03, T09 | callback validation, token exchange, replay handling |
| OP-C09-136 | validateConnection | AX-022,040 | C09 business validation result؛ SH-13 health evidence | T03, T09 | health freshness and non-disclosure |
| OP-C09-137 | testMapping | AX-015,044,039 | C09 mapping validation | T07, T09 | sanitized fixtures, deterministic transform oracle |
| OP-C09-138 | startSync | AX-022,040,049 | C09 sync intent/run؛ SH-08 attempts؛ SH-13 connector | T06, T07, T08, T10 | external-write approval, source of truth, idempotency |
| OP-C09-139 | receiveWebhook | AX-022,040,049 | C09 domain event intake؛ SH-13 ingress runtime؛ SH-09 transport | T04, T05, T10 | signature/timestamp/replay/size/idempotency |
| OP-C09-140 | pauseIntegration | AX-022,040,049 | C09 integration lifecycle؛ SH-08 scheduled attempts | T08, T09 | race with running jobs and schedule cancellation |
| OP-C09-141 | revokeConnection | AX-022,040 | C09 domain revoke intent؛ SH-13 credential/runtime revoke | T03, T08, T09 | provider revocation support and task drain |
| OP-C09-142 | rotateSecretReference | AX-021,048,056 | Secret Manager owns secret value؛ SH-13 ref lifecycle | T03, T09 | atomic rotation, overlap window, rollback semantics |
| OP-C09-143 | getIntegrationStatus | AX-022,040,049 | C09 status projection؛ SH-13 health source | T03, T09 | freshness, status semantics, ACL |

### 3.1 C09 سيناريوهات القبول المحلية

| Scenario ID | شرط الاختبار | العملية/العمليات | Oracle المطلوب | الحالة |
|---|---|---|---|---|
| C09-126-T01 | رفض URL داخلي أو غير مسموح | 132–136,138 | egress policy denies؛ لا اتصال | SPECIFIED / NOT RUN |
| C09-126-T02 | رفض OAuth state/redirect mismatch | 134–135 | لا إكمال جلسة ولا إنشاء اتصال | SPECIFIED / NOT RUN |
| C09-126-T03 | لا تظهر access/refresh tokens أو secret values | 133–136,141–143 | response/log/event لا يحمل إلا secretRef | SPECIFIED / NOT RUN |
| C09-126-T04 | رفض webhook بتوقيع أو توقيت غير صالح | 139 | لا event accepted ولا domain side effect | SPECIFIED / NOT RUN |
| C09-126-T05 | منع replay لتسليم webhook مكرر | 139 | event/side effect واحد وفق idempotency contract | SPECIFIED / NOT RUN |
| C09-126-T06 | منع sync خارجي قبل الموافقة اللازمة | 138 | لا outbound write دون approval bound to config/mapping hash | SPECIFIED / NOT RUN |
| C09-126-T07 | تعارض sync لا يستبدل SoR بصمت | 137–139 | conflict surfaced; source-of-truth preserved | SPECIFIED / NOT RUN |
| C09-126-T08 | revoke/pause يوقف الأعمال اللاحقة | 140–141 | scheduled jobs stopped; in-flight semantics explicit | SPECIFIED / NOT RUN |
| C09-126-T09 | عزل tenant/project/environment | 132–143 | no cross-scope connection, mapping, health or secretRef leakage | SPECIFIED / NOT RUN |
| C09-126-T10 | retry لا يكرر أثر خارجي غير idempotent | 138–139 | one external effect or explicit indeterminate state | SPECIFIED / NOT RUN |

## 4. أحداث المجال

### C08 — 11 أحداث مرشحة

| Event ID | Event name | المنتج الدلالي المرشح | الحد المشترك | الاختبار المحلي المرشح |
|---|---|---|---|---|
| EVT-C08-001 | AssetRecordCreated | C08 | SH-06 لا يملك معنى الأصل | T01 |
| EVT-C08-002 | AssetUploadRequested | C08 | SH-06 upload execution evidence | T01/T02 |
| EVT-C08-003 | AssetVersionReady | C08 بعد receipt/scan | SH-06 storage evidence | T02/T09 |
| EVT-C08-004 | AssetQuarantined | C08 lifecycle من scan evidence | SH-06 scan execution | T02/T03 |
| EVT-C08-005 | AssetScanCompleted | C08 status transition | SH-06 scan evidence | T02/T03 |
| EVT-C08-006 | AssetLinked | C08 | target domain لا يفقد ملكية target | T04 |
| EVT-C08-007 | AssetLinkRevoked | C08 | cache/index consumers invalidate | T05 |
| EVT-C08-008 | AssetTransformationCompleted | C08 derived asset lineage | SH-08 job؛ SH-06 processing | T06/T10 |
| EVT-C08-009 | AssetArchived | C08 | retention policy owner remains explicit | T08 |
| EVT-C08-010 | AssetDeletionRequested | C08 | SH-03 approval/SH-04 audit as policy requires | T08 |
| EVT-C08-011 | AssetDeleted | C08 domain state after verified deletion | SH-06 deletion execution evidence | T08 |

### C09 — 10 أحداث مرشحة

| Event ID | Event name | المنتج الدلالي المرشح | الحد المشترك | الاختبار المحلي المرشح |
|---|---|---|---|---|
| EVT-C09-001 | IntegrationRegistered | C09 | SH-13 manifest/runtime distinct | T01/T09 |
| EVT-C09-002 | IntegrationAuthorizationStarted | C09 | SH-13 OAuth runtime evidence | T02 |
| EVT-C09-003 | IntegrationConnected | C09 | SH-13 connection state evidence | T02/T03 |
| EVT-C09-004 | IntegrationConnectionFailed | C09 | SH-13 health/runtime evidence | T02/T03 |
| EVT-C09-005 | IntegrationSyncStarted | C09 | SH-08 job attempt; SH-13 connector | T06/T10 |
| EVT-C09-006 | IntegrationSyncCompleted | C09 domain sync outcome | SH-08 execution outcome reference | T07/T10 |
| EVT-C09-007 | IntegrationSyncFailed | C09 domain failure outcome | SH-08 attempt/error reference | T07/T10 |
| EVT-C09-008 | IntegrationPaused | C09 | SH-08 schedule/execution cancellation | T08 |
| EVT-C09-009 | IntegrationRevoked | C09 | SH-13 connector/credential revoke | T03/T08 |
| EVT-C09-010 | WebhookRejected | C09 | SH-13 ingress validation; SH-09 transport distinct | T04/T05 |

**تحذير:** هذه الأحداث مصنفة SOURCE-RECOVERED في سجل 100 بمعنى استعادتها دلالياً من مسودات المراكز، لا أنها نص حرفي مثبت في المصدر الرئيسي. أسماء producer/consumer وschemas/topics وretry/dead-letter لم تعتمد.

## 5. الموافقات والعمليات المحمية

| Approval ID | الإجراء المحمي المرشح | مرجع العملية | الضابط اللازم | حالة القرار |
|---|---|---|---|---|
| APR-C08-01 | حذف أصل | OP-C08-131 | تحقق retention/legal hold، استقلال مقدم الطلب والمراجع، exact asset/version binding | OWNER REVIEW |
| APR-C08-02 | تصدير أصل | OP-C08-130 | purpose/recipient/scope/classification، وربط الموافقة ببصمة الحزمة | OWNER REVIEW |
| APR-C09-01 | مزامنة ذات كتابة خارجية | OP-C09-138 | mapping/config hash، direction، target account، idempotency | OWNER REVIEW |
| APR-C09-02 | منح نطاق اتصال خارجي حساس | OP-C09-134 | provider allowlist، OAuth scope minimization، consent/owner approval | OWNER REVIEW |

لا تعني تسمية APR أن الموافقة واجبة في جميع الحالات؛ يقرر ذلك مالك السياسة والمجال. يجب أن تفصل دورة الطلب والقرار والتنفيذ والتحقق والإلغاء/الانتهاء.

## 6. فجوات العقد التي تمنع الانتقال إلى التنفيذ

1. **المصدر:** لكل عملية/حدث locator أصلي دقيق (chapter/section/paragraph) أو قرار صريح بأنها إضافة مشتقة؛ locator المركز وحده لا يكفي.
2. **الملكية:** OWN72-01 وOWN72-02 مفتوحان؛ يجب اعتماد C08/SH-06 وC09/SH-13 وSH-08/SH-09/SH-04 boundaries.
3. **البيانات:** FLD-09 وFLD-10 وFLD-12 مفتوحة؛ يلزم حسم tenant/org/workspace scope، grants، secretRef، metadata/version/retention types.
4. **API:** request/response schema، errors، versioning، concurrency، idempotency، limits، pagination، authz scope لكل عملية.
5. **الأحداث:** producer/consumer، payload minimization، event schema/version، delivery guarantee، replay/dead-letter/retention.
6. **الموافقات:** متى تكون مشروطة أو إلزامية، من المراجع، separation of duties، hash binding، expiry/revocation.
7. **الاختبارات:** اعتماد السيناريوهات وربطها بمصدر/مالك، fixtures آمنة، test environment، oracle، run ID، evidence.
8. **السياسات:** retention/legal hold/deletion propagation، privacy/data residency، connector allowlist/OAuth/webhook/egress، malware scan and quarantine.
9. **التشغيل:** SLO/freshness، limits، retry/backoff، incident ownership، recovery objectives؛ لا توجد قيم رقمية معتمدة هنا.
10. **التكامل:** C08/C09 مع C02/C04/C05/C07/C12/C14 يجب أن يمر عبر contracts ومراجع، لا DB direct access.

## 7. تحديثات مطلوبة للسجلات الأم

بعد اعتماد المالكين، يجب تحديث 77 (الحقول)، 78 (الصلاحيات)، 79 (العقود المفردة)، 80 (اختبارات القبول)، 94 (referential integrity)، 97 (العمليات)، 99 (AX/test/approval traceability)، 100 (الأحداث)، 106/107 (حدود الخدمات المشتركة). لا تُعدل الحالات إلى APPROVED أو PASSED قبل الأدلة.

## 8. بوابة الإغلاق

- [ ] اعتماد OWN72-01 وOWN72-02 بقرار مالك مؤهل.
- [ ] اعتماد C08/SH-06 binary/storage boundary وC09/SH-13 connector/secret boundary.
- [ ] اعتماد SoR/tenant scope/classification/retention لكل كيان.
- [ ] اعتماد الـ23 عملية بعقود مفردة وsource locator أو مشتق مصرح به.
- [ ] اعتماد الـ21 حدثاً بمنتج معنى وschema/version/consumer policy.
- [ ] اعتماد متطلبات الموافقات الأربعة أو تبرير N/A لكل حالة.
- [ ] اعتماد السيناريوهات العشرين وربطها بالمتطلبات والعمليات والأحداث.
- [ ] تنفيذ الاختبارات وإرفاق run IDs والأدلة بعد الموافقة على التنفيذ.
- [ ] إعادة تدقيق 94 و125 وتحديث تقرير الجاهزية.

## 9. الحكم

أصبح لدى C08 وC09 الآن workpaper تفصيلي يربط 23 عملية و21 حدثاً و4 موافقات مرشحة و20 سيناريو قبول. هذا يحسن قابلية التتبع، لكنه لا يحسم OWN72-01/02 أو FLD-09/10/12، ولا يثبت أن جميع أسماء العمليات حرفية المصدر، ولا ينشئ عقود API أو schemas معتمدة، ولا يعني تنفيذ الاختبارات.

**GAP-044 / GAP-020 / GAP-027 تبقى OPEN. NOT APPROVED. NOT IMPLEMENTED. TESTS NOT RUN.**
