# 91 — حزمة قرارات ملكية SH-13 للتكاملات والموصلات

**الحالة:** DERIVED DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المراجع:** 32 (SH-13)، 36 (OWN36-13 ضمن بطاقة SH-13)، 65 (C09)، 72 (OWN72-02)، 79، 80، 82، 86، 88، 89.  
**حد المصدر:** الفصل 132.6 يسرد SH-13 ومسؤوليتها المختصرة؛ تفاصيل العقد أدناه مشتقة من العقود 32 و65 وليست نصاً تاريخياً مستقلاً.

## 1. القرار المعماري المطلوب

الفصل بين:
- **C09 — تكامل المجال:** يملك غرض التكامل، تعريفه ضمن المشروع، خرائط البيانات، اتجاه المزامنة، قواعد حل التعارض، وموافقة مالك المجال على الأثر.
- **SH-13 — قدرة الموصل المشتركة:** تملك تعريفات runtime المعتمدة، metadata الاتصال، دورة حياة الموصل، capability grants references، حالة الاتصال والصحة المرجعية، وإدارة دورة credential reference.
- **SH-01/02/03/04:** الهوية والتفويض والموافقات والتدقيق وفق عقودها.
- **Secret Manager المعتمد:** يملك قيمة السر الفعلية ومفاتيحها؛ SH-13 يحتفظ بمرجع محدود لا بالقيمة.
- **SH-08/09:** تنفيذ مهام التكامل ونقل الأحداث، ولا يملكان تعريف المجال أو بيانات المزود الأصلية.

هذه حدود مقترحة؛ OWN72-02 لا يغلق دون توقيع المالكين.

## 2. سجل قرارات المالكين

| ID | القرار المطلوب | التوصية القابلة للمراجعة | الحالة |
|---|---|---|---|
| INT-91-01 | مالك SH-13 وDomain SoR للموصل | SH-13 يملك runtime metadata؛ C09 يملك تعريف تكامل المشروع | OPEN |
| INT-91-02 | حد manifest مقابل integration definition | manifest تقني عند SH-13؛ الغرض والخرائط والسياسات عند C09 | OPEN |
| INT-91-03 | ملكية بيانات الحساب الخارجي | لا يملكها المصمم؛ provider/account owner يبقى خارجيّاً | OPEN |
| INT-91-04 | ملكية الأسرار | Secret Manager فقط؛ SH-13/C09 يحتفظان بـsecretRef | OPEN |
| INT-91-05 | اعتماد القدرات والنطاقات | allowlist + least privilege + موافقة مستقلة للأثر عالي المخاطر | OPEN |
| INT-91-06 | عزل connector runtime | عزل حسب tenant/project/environment مع egress policy | OPEN |
| INT-91-07 | دورة OAuth/callback | state/nonce/PKCE حيث ينطبق، redirect allowlist والتحقق الخادمي | OPEN |
| INT-91-08 | webhooks | تحقق توقيع/وقت ومنع replay وidempotency وحجم payload | OPEN |
| INT-91-09 | خرائط ومزامنة البيانات | C09 يملك mapping وdirection/source-of-truth/conflict policy | OPEN |
| INT-91-10 | retries والعمليات غير idempotent | retry للأخطاء العابرة فقط؛ منع تكرار الأثر الخارجي | OPEN |
| INT-91-11 | إبطال الاتصال | الإبطال يوقف الاستدعاءات والمهام المجدولة ويعالج الرموز لدى المزود حيث يدعم | OPEN |
| INT-91-12 | الاحتفاظ ومناطق المعالجة | تخضع لتصنيف 88 ومتطلبات المزود والاختصاص؛ القيم TBD | OPEN |
| INT-91-13 | اعتماد قائمة موصلات الإصدار الأول | لا مزود ولا نطاق OAuth معتمد بهذه الوثيقة | OPEN |
| INT-91-14 | ملكية incident/health | SH-13 يملك health refs؛ الحادث التشغيلي وفق نموذج الحوادث المعتمد | OPEN |

## 3. سجل الموارد والحقول

| المورد | المالك المقترح | حقول مرشحة | ما لم يثبت يبقى |
|---|---|---|---|
| integration_definition | C09 | integrationId, purpose, owner, scope, providerRef | schema/constraints TBD |
| integration_mapping | C09 | mappingId, version, sourceRef, targetRef, transformRef | transform language TBD |
| integration_sync_policy | C09 | direction, scheduleRef, conflictPolicy, sourceOfTruth | cadence/retention TBD |
| connector_manifest | SH-13 | connectorId, version, capabilities, signatureRef | signing standard TBD |
| connection_profile | SH-13 | connectionId, tenantScope, environment, lifecycleState | schema TBD |
| capability_grant_ref | SH-02/owner policy | grantRef, scope, expiry, policyVersion | grant schema TBD |
| secret_reference | Secret Manager | secretRef, versionRef, rotation metadata | provider TBD |
| connector_health_ref | SH-13 | status, checkedAt, evidenceRef | freshness SLO TBD |
| integration_run | C09 domain ref + SH-08 execution ref | runRef, mappingVersion, jobRef, outcomeRef | canonical owner split TBD |
| webhook_registration | C09/SH-13 boundary | endpointRef, signatureKeyRef, status | key custody TBD |

لا تُعتمد أنواع الحقول أو nullability أو القيود أو retention بمجرد ورودها في هذا الجدول.

## 4. عقد العمليات والأحداث المقترح

تخضع عمليات 65 مثل registerIntegration وconfigureIntegration وbeginAuthorization وcompleteAuthorization وvalidateConnection وtestMapping وstartSync وreceiveWebhook وpauseIntegration وrevokeConnection وgetIntegrationStatus لمعرف عقد وإصدار واضحين.

لكل عملية: requestId, correlationId, actor, scope, contractVersion؛ وidempotencyKey عند احتمال إعادة الإرسال. يجب على الخدمة المالكة إعادة التحقق من actor/scope/permission؛ ولا يكفي scope المرسل.

أحداث مرشحة: IntegrationRegistered, IntegrationConnected, IntegrationConnectionFailed, IntegrationSyncStarted, IntegrationSyncCompleted, IntegrationSyncFailed, IntegrationPaused, IntegrationRevoked, WebhookRejected. المنتج يملك حقيقة المجال؛ SH-09 يملك التسليم لا المعنى.

## 5. ضوابط القبول والأمن

- منع SSRF والوصول إلى loopback والشبكات الخاصة وعناوين metadata؛ allowlist للمضيفين والبروتوكولات.
- TLS والتحقق من الشهادات، حدود الاتصال، timeouts، rate limits وcircuit breakers.
- لا أسرار خام في manifests أو responses أو logs أو events أو سياق AI.
- عدم تمكين وكيل AI من إنشاء اتصال أو منح scope أو تفعيل مزود دون تفويض وموافقة لازمين.
- إعادة التحقق من ACL عند كل تشغيل مزامنة مؤجل.
- فصل البيئات والحسابات ومراجع الأسرار.
- عند إلغاء التكامل، إيقاف عمليات الجدولة وإبطال grants والرموز الممكن إبطالها.
- لا مزامنة ثنائية الاتجاه قبل تحديد المصدر المرجعي والتعارضات وسلوك الحذف.

## 6. اختبارات القبول

| Test ID | السيناريو | معيار النجاح | الحالة |
|---|---|---|---|
| INT-91-T01 | URL داخلي أو غير مسموح | رفض الاتصال قبل التنفيذ | SPECIFIED / NOT RUN |
| INT-91-T02 | OAuth state/redirect غير مطابق | رفض وإبطال الجلسة | SPECIFIED / NOT RUN |
| INT-91-T03 | استجابة أو log يحوي secret | لا تظهر قيمة السر | SPECIFIED / NOT RUN |
| INT-91-T04 | webhook بتوقيع/وقت غير صالح | رفض دون أثر مجال | SPECIFIED / NOT RUN |
| INT-91-T05 | webhook مكرر | لا يتكرر أثر المجال | SPECIFIED / NOT RUN |
| INT-91-T06 | sync write بلا approval مطلوب | رفض قبل الكتابة | SPECIFIED / NOT RUN |
| INT-91-T07 | mapping version تغير بعد الموافقة | إبطال الموافقة القديمة | SPECIFIED / NOT RUN |
| INT-91-T08 | سحب صلاحية المستخدم قبل job مؤجل | إعادة التفويض والرفض | SPECIFIED / NOT RUN |
| INT-91-T09 | إلغاء اتصال | منع الاستدعاءات والمهام التالية | SPECIFIED / NOT RUN |
| INT-91-T10 | cross-tenant connection ref | رفض دون كشف بيانات الطرف الآخر | SPECIFIED / NOT RUN |
| INT-91-T11 | retry لعملية خارجية غير idempotent | لا تكرار للأثر | SPECIFIED / NOT RUN |
| INT-91-T12 | فشل المزود أو stale health | عرض unavailable/stale لا healthy | SPECIFIED / NOT RUN |

## 7. شروط الإغلاق

تعيين مالك SH-13 وC09؛ اعتماد manifest/connection schemas؛ تعيين Secret Manager؛ اعتماد allowlist وegress/OAuth/webhook policies؛ تحديد مزودي الإصدار الأول ونطاقاتهم؛ حسم source-of-truth والمزامنة؛ ربط كل API/event/approval بقرار OWN72-02 واختبار؛ ثم تنفيذ الاختبارات وتسجيل الأدلة.

**لا تغلق هذه الوثيقة OWN72-02 أو FLD-12، ولا تنشئ موصلاً أو API أو صلاحية أو secret store.**
