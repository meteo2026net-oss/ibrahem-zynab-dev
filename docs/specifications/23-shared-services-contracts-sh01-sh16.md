# 23 — عقود الخدمات المشتركة SH-01 إلى SH-16

**الحالة:** DRAFT / SOURCE-ALIGNED SERVICE CONTRACTS  
**المصدر الأساسي:** الفصل 132.6 من ملف التصدير 2026-11-10-export-part-04.txt، ومطابقته مع الملف 18.  
**حقيقة المصدر:** الفصل 132.6 يسمي 16 خدمة فقط تحت عبارة «الخدمات المقترحة» ولا يذكر SH-17..SH-20. لكن الفصل 155.13 في الأرشيف يشير إلى أن الفصل 157 «توحيد المكونات والخدمات المشتركة» كان مطلوباً لاحقاً؛ ولم يُستعد نص الفصل 157 التفصيلي بعد. لذلك لا نعد قائمة الـ16 نهائية، ولا نخترع أسماء إضافية قبل استعادة الفصل 157.  
**حدود الاستعادة:** الفقرات 132.6.1–132.6.8 تفصل بعض الخدمات الأولى؛ الخدمات SH-09..SH-16 مستعادة بالاسم/المسؤولية المختصرة فقط، وتحتاج استكمال المصدر أو مراجعة المالك.

## 1. قواعد ملكية الخدمات المشتركة

1. الخدمة المشتركة تقدم قدرة أفقية ولا تملك محتوى المجال الوظيفي للمراكز.
2. المركز يملك قواعد مجاله وبياناته؛ الخدمة المشتركة تملك فقط سجلات التشغيل اللازمة لقدرتها.
3. لا وصول مباشر غير منضبط إلى جداول مركز آخر؛ يكون التكامل عبر Query/Command/Event/File/Workflow contracts.
4. كل طلب يحمل requestId وcorrelationId وactor وscope وaction وcontractVersion.
5. الخدمة المستقبلة تعيد التحقق من الهوية والسياق والصلاحية؛ لا تثق في organizationId/projectId المرسل وحده.
6. كل عملية كتابة أو موافقة أو مشاركة أو تصدير حساسة تسجل في Audit Service.
7. يجب دعم idempotency حيث يمكن إعادة إرسال الطلب، وتحديد retry/timeout/cancellation.
8. لا تتضمن الأحداث أو السجلات أسراراً أو بيانات حساسة غير لازمة.
9. لا تعتبر أي عملية ناجحة قبل تأكيد الخدمة المالكة ووجود مرجع نتيجة.
10. كل خدمة لها مالك تقني، مالك بيانات، عقد إصدار، health indicators، واختبارات مستقلة.

## 2. سجل الخدمات المثبت في الفصل 132.6

| ID | الخدمة | الاسم المستعاد | المسؤولية المصدرية |
|---|---|---|---|
| SH-01 | Identity Service | الهوية والمصادقة | تسجيل الدخول والخروج والحسابات والجلسات والتحقق ومزودو الهوية |
| SH-02 | Authorization Service | الصلاحيات والسياسات | قرارات القراءة والتعديل والحذف والاعتماد والتشغيل والتفويض ونطاقات البيئة |
| SH-03 | Approval Service | الموافقات | دورة طلب الموافقة والقرار؛ سياسة من يوافق يحددها المجال بالتنسيق مع الأمن |
| SH-04 | Audit Service | سجل التدقيق | تسجيل الأحداث المهمة وحماية السجل من تعديل الوكلاء والمستخدمين العاديين |
| SH-05 | Notification Service | الإشعارات | قنوات الإشعار داخل التطبيق والبريد والمهام والموافقات والأعطال والميزانية |
| SH-06 | File Service | إدارة الملفات | واجهة موحدة للملفات والأصول وبياناتها الوصفية وصلاحياتها ومراجعها |
| SH-07 | Search Service | البحث | بحث موحد في مجالات المشروع مع احترام صلاحيات المصدر |
| SH-08 | Task Service | المهام | إدارة العمليات طويلة الأمد وحالاتها وإعادة المحاولة والإلغاء والتقدم والنتائج |
| SH-09 | Event Service | الأحداث | الاسم/المسؤولية مختصران في قائمة 132.6؛ التفاصيل تحتاج عقداً مستقلاً |
| SH-10 | Version Service | إدارة الإصدارات | الاسم/المسؤولية مختصران؛ الفصل 134.7 يقترح نموذج موارد وإصدارات ولقطات مشروع |
| SH-11 | Cost Service | التكاليف والميزانيات | الاسم/المسؤولية مختصران؛ لا تُعتمد عتبات أو أسعار من هذا الملف |
| SH-12 | Configuration Service | الإعدادات | الاسم/المسؤولية مختصران؛ الفصل 134.12 يقدم مستويات الإعدادات ووراثتها |
| SH-13 | Integration Service | إدارة الاتصالات | الاسم/المسؤولية مختصران؛ يتطلب موصلات وعقوداً ومراجع أسرار |
| SH-14 | Monitoring Service | المراقبة | الاسم/المسؤولية مختصران؛ يتطلب مؤشرات صحة وتشغيل وربطاً بالحوادث |
| SH-15 | Backup Service | النسخ الاحتياطي | الاسم/المسؤولية مختصران؛ يتطلب نسخاً واختبارات استعادة وأدلة |
| SH-16 | Knowledge Service | المعرفة والوثائق | الاسم/المسؤولية مختصران؛ C12 يملك المحتوى المعرفي للمجالات وفق حدود الملكية |

## 3. عقود الخدمات SH-01..SH-08

### SH-01 Identity Service

**المسؤولية:** إدارة هوية الحسابات والجلسات والمصادقة ومزودي الهوية، دون امتلاك عضويات المشروع أو سياسات التفويض الخاصة بالمجالات.

- مدخلات: login challenge، credentials/token assertion من مزود معتمد، session action، account identity reference.
- مخرجات: identity subject، authentication result، session reference، expiry/revocation state.
- عمليات مقترحة: authenticate، logout، getSession، revokeSession، linkIdentityProvider، verifyIdentity.
- بيانات تشغيلية مقترحة: identities، identity_providers، sessions، authentication_events.
- مستهلكون: جميع المراكز والخدمات التي تحتاج إثبات هوية.
- ضوابط: لا تخزن كلمات المرور في ملف المستخدم العام؛ لا ترسل الأسرار إلى الواجهة أو السجلات؛ لا تخلط authentication مع authorization.
- أخطاء: invalid credential، provider unavailable، expired assertion، revoked session، account suspended.
- اختبارات: جلسة منتهية تُرفض؛ إبطال الجلسة يمنع الطلبات التالية؛ لا يتسرب رمز المصادقة في السجلات.

### SH-02 Authorization Service

**المسؤولية:** قرار التفويض على مستوى المؤسسة ومساحة العمل والمشروع والمركز والمورد والعملية والبيئة.

- مدخلات: actor، action، resource، organization/workspace/project/environment scope، attributes، policy version.
- مخرجات: allow/deny، reason code، policy reference، decision timestamp، obligations عند الحاجة.
- عمليات مقترحة: authorize، evaluatePolicy، listEffectiveGrants، requestTemporaryGrant، revokeGrant.
- بيانات تشغيلية مقترحة: roles، permissions، role_permissions، access_policies، policy_rules، permission_grants، temporary_grants، access_decisions.
- مستهلكون: جميع الخدمات؛ لا تعتمد أي خدمة على إخفاء زر أو عنصر في الواجهة.
- ضوابط: RBAC وABAC وresource-level وenvironment-level؛ أقل صلاحية؛ التفويض المؤقت محدود الزمن والنطاق.
- أخطاء: missing scope، invalid actor، policy conflict، grant expired، resource not found or not visible.
- اختبارات: مستخدم عضو في المؤسسة دون عضوية مشروع لا يحصل تلقائياً على صلاحية المشروع؛ الوكيل لا يرفع صلاحياته؛ انتهاء grant يؤدي إلى الرفض.

### SH-03 Approval Service

**المسؤولية:** دورة طلب الموافقة والقرار، بينما تبقى قواعد الموافقة ومصفوفة الموافقين تحت ملكية المجال/السياسة.

- مدخلات: requester، domain، resource type/id/version أو fingerprint، action، impact/risk، cost estimate إن وجد، approver policy.
- مخرجات: approval request id، state، decisions، evidence refs، expiry.
- عمليات مقترحة: createRequest، submitRequest، assignApprover، approve، reject، requestChanges، cancel، expire، getDecision.
- بيانات تشغيلية مقترحة: approval_requests، approval_steps، approval_decisions، approval_assignments، approval_evidence، approval_policies، approval_delegations، approval_events.
- ضوابط: موافقة مستقلة؛ ربط القرار بنسخة محددة؛ تغير المورد يبطل صلاحية الموافقة القديمة؛ لا self-approval حيث يشترط الاستقلال.
- حالات مصدرية: Draft، Submitted، Pending، Approved، Rejected، Changes Requested، Expired، Cancelled، Superseded.
- أخطاء: approver unauthorized، stale resource version، expired request، duplicate decision.
- اختبارات: لا تُقبل موافقة على diff قديم؛ إعادة إرسال القرار لا تكرر الأثر؛ لا يعتبر طلب الموافقة تنفيذاً للعملية.

### SH-04 Audit Service

**المسؤولية:** سجل تدقيق محمي منفصل منطقياً عن النشاط العادي.

- مدخلات: actor identity/type، organization/project، action، resource، previous/new version، service source، timestamp، outcome، approval/task/correlation refs.
- مخرجات: append-only audit event، integrity checkpoint/reference، query result للمخولين.
- عمليات مقترحة: appendEvent، queryEvents، verifyIntegrity، exportAuditReport، applyRetentionPolicy.
- بيانات تشغيلية مقترحة: audit_events، audit_event_sources، audit_integrity_checkpoints، audit_export_jobs، audit_retention_policies.
- ضوابط: لا تعديل/حذف اعتيادي؛ صلاحيات قراءة مقيدة؛ لا كلمات مرور أو أسرار؛ تسجيل عمليات التصدير.
- أخطاء: missing actor، invalid event schema، integrity mismatch، retention conflict.
- اختبارات: رفض محاولة تعديل سجل من وكيل؛ اكتشاف فجوة تسلسل؛ عدم كشف محتوى حساس غير لازم.

### SH-05 Notification Service

**المسؤولية:** إرسال الإشعارات عبر قنوات قابلة للتوسعة؛ إعداد قواعد الأعمال ومحتوى التنبيه يظل لدى المركز المالك.

- مدخلات: recipient reference، channel، event/template، locale، consent/preferences، delivery constraints.
- مخرجات: notification id، queued/sent/delivered/failed state، provider receipt، retry schedule.
- عمليات مقترحة: enqueueNotification، dispatch، getDeliveryStatus، retryFailedDelivery، suppressDuplicate.
- بيانات تشغيلية مقترحة: notification_jobs، notification_deliveries، notification_templates، channel_configs، delivery_attempts.
- مستهلكون: المهام والموافقات والأعطال والميزانية والتكاملات ولوحة التحكم.
- ضوابط: احترام الموافقة والتفضيلات؛ منع التكرار؛ إخفاء الأسرار؛ لا إرسال لقناة غير مفعلة.
- أخطاء: invalid recipient، channel unavailable، provider rejected، rate limited، consent missing.
- اختبارات: فشل المزود لا يعلن التسليم؛ منع التكرار وفق مفتاح idempotency؛ احترام تفضيل إيقاف القناة.

### SH-06 File Service

**المسؤولية:** واجهة موحدة للأصول والملفات والبيانات الوصفية والصلاحيات والمراجع، مع إمكان التخزين المحلي أو السحابي وفق الإعدادات.

- مدخلات: upload intent، file metadata، owner/scope، content checksum، media type، license/source refs.
- مخرجات: asset id، version id، storage ref، scan/processing state، signed access reference.
- عمليات مقترحة: createUpload، finalizeUpload، getAsset، createVersion، linkAsset، requestDownload، archiveAsset، restoreAsset.
- بيانات تشغيلية مقترحة: assets، asset_versions، asset_storage_objects، asset_links، asset_permissions، asset_metadata، asset_processing_jobs، asset_checksums، asset_licenses، asset_retention_records.
- ضوابط: فحص قبل الإتاحة؛ صلاحيات على الأصل والارتباط؛ فصل metadata عن object storage؛ عدم كشف مسارات التخزين؛ عدم حذف أصل مستخدم دون معالجة علاقاته.
- أخطاء: upload incomplete، checksum mismatch، forbidden link، scan failed، unsupported media type، storage unavailable.
- اختبارات: لا يُعلن نجاح الرفع قبل finalize؛ لا يكشف البحث وجود أصل ممنوع؛ الاستعادة تحفظ الروابط والإصدارات.

### SH-07 Search Service

**المسؤولية:** بحث موحد في المشاريع والمتطلبات والصفحات والمكونات والملفات والكود والوثائق والتكاملات والمهام والقرارات، مع احترام صلاحيات المصدر.

- مدخلات: query، filters، actor/scope، resource categories، index version.
- مخرجات: ranked result refs، safe snippets، source refs، freshness metadata.
- عمليات مقترحة: search، suggest، indexResource، removeOrTombstoneResource، refreshIndex، explainResultSource.
- بيانات تشغيلية مقترحة: search_documents، search_sources، index_jobs، index_versions، query_audit_refs.
- ضوابط: التفويض قبل عرض النتائج أو المقتطفات؛ لا تعتبر نتيجة الفهرس مصدراً للحقيقة؛ cache key يشمل سياق التفويض.
- أخطاء: index stale، source unavailable، forbidden source، malformed query.
- اختبارات: البحث عن مورد محمي لا يكشف حتى وجوده؛ سحب الصلاحية ينعكس على الاسترجاع؛ إظهار مصدر وتاريخ الفهرسة.

### SH-08 Task Service

**المسؤولية:** إدارة العمليات طويلة الأمد، ولا يملك قواعد المجال التي تنفذها المهمة.

- مدخلات: task type، requester، scope، plan/dependencies، resource limits، deadline، idempotency key.
- مخرجات: task id، state، progress، attempts، output refs، error refs.
- عمليات مقترحة: createTask، schedule، assign، start، updateProgress، pause، resume، cancel، retry، getTask.
- بيانات تشغيلية مقترحة: tasks، task_attempts، task_dependencies، task_assignments، task_progress_events، task_outputs، task_errors، task_cancellation_requests.
- ضوابط: فصل المهمة عن المحاولات والنتائج؛ حفظ تاريخ المحاولات؛ retry لا يكرر الآثار المكتملة؛ صلاحيات الإيقاف والاستئناف.
- حالات المصدر في 132.12: Created، Queued، Scheduled، Running، Waiting Approval، Paused، Retrying، Completed، Failed، Cancelled، Expired.
- أخطاء: invalid transition، dependency failed، budget exceeded، timeout، duplicate side effect.
- اختبارات: عامل متوقف لا يفقد حالة المهمة؛ إعادة المحاولة تحفظ المحاولة السابقة؛ الإلغاء لا يدعي إلغاء أثر اكتمل بالفعل.

## 4. الخدمات SH-09..SH-16 — نطاق أولي يحتاج استكمالاً

| الخدمة | عقد مسؤولية أولي | بيانات/عقود مرتبطة في المصدر | ما لم يُحسم |
|---|---|---|---|
| SH-09 Event Service | نشر وتسليم الأحداث بين الخدمات والمراكز مع التحقق وإعادة المعالجة | event_outbox، event_inbox، event_subscriptions، event_delivery_attempts؛ نموذج eventId/eventType/eventVersion/correlationId | ضمان التسليم، retention، ترتيب الأحداث، broker |
| SH-10 Version Service | حفظ إصدارات الموارد ومجموعات التغييرات ولقطات المشروع | versioned_resources، resource_versions، version_relationships، change_sets، change_set_items، version_approvals، version_tags، version_comparisons، project_snapshots | قواعد الدمج والتفرع وسياسة الاحتفاظ والترقيم |
| SH-11 Cost Service | تجميع الاستهلاك والتكاليف والميزانيات على مستوى النطاق | usage/cost records يستهلكها C06 وC13 وC14 | العملة، مصادر الأسعار، العتبات، الفوترة، التقريب |
| SH-12 Configuration Service | تعريف القيم والمخططات والوراثة والتاريخ بحسب النطاق | settings_definitions، settings_values، settings_schemas، settings_versions، settings_overrides، settings_change_history | ترتيب التوريث النهائي والاستثناءات ومفاتيح الإعدادات |
| SH-13 Integration Service | إدارة تعريفات الموصلات والاتصالات وعقود المزودين | connector manifests وconnection profiles وsecret references ضمن C09 | lifecycle، sandbox، network egress، retry، provider-specific contracts |
| SH-14 Monitoring Service | تجميع صحة الخدمات والقياسات والتنبيهات التشغيلية | traces/metrics/health/incident refs في L9/L10 | SLI/SLO، sampling، retention، ownership boundaries |
| SH-15 Backup Service | نسخ البيانات والأصول والإعدادات واستعادة متسقة مع أدلة | backup/restore records وproject snapshots | topology، encryption/key ownership، RPO/RTO، restore verification |
| SH-16 Knowledge Service | خدمات الوصول والفهرسة المعرفية والروابط؛ المحتوى يملكه C12 أو المجال المصدر | documents، document_versions، sources، citations، knowledge links | حدود الخدمة مع Search، ACL indexing، retention، vector engine |

**تنبيه ملكية:** أسماء الكيانات في العمود الثالث مستمدة من الفصل 134 أو من نطاقات مراكز أخرى؛ لا يعني ذلك أن كل جدول منها مملوك للخدمة المشتركة. يجب أن يسجل سجل الملكية المالك الأصلي لكل كيان.

## 5. مصفوفة المستهلكين الرئيسيين

| الخدمة | مراكز مستهلكة رئيسية |
|---|---|
| SH-01 الهوية | جميع المراكز |
| SH-02 الصلاحيات | جميع المراكز والخدمات |
| SH-03 الموافقات | C01 وC02 وC04 وC05 وC06 وC09 وC10 وC11 وC13 |
| SH-04 التدقيق | جميع المراكز، خصوصاً C06 وC10 وC11 وC13 |
| SH-05 الإشعارات | جميع المراكز حسب الأحداث والتفضيلات |
| SH-06 الملفات | C01 وC02 وC05 وC07 وC08 وC09 وC11 وC12 |
| SH-07 البحث | جميع المراكز ضمن ACL |
| SH-08 المهام | C01 وC05 وC06 وC07 وC08 وC09 وC11 |
| SH-09 الأحداث | جميع خدمات المجالات |
| SH-10 الإصدارات | C01–C13، وC14 للقراءة التجميعية |
| SH-11 التكاليف | C06 وC09 وC11 وC13 وC14 |
| SH-12 الإعدادات | جميع المراكز |
| SH-13 التكاملات | C04 وC05 وC06 وC07 وC09 وC11 |
| SH-14 المراقبة | جميع الخدمات، وC10/C14 للعرض |
| SH-15 النسخ الاحتياطي | جميع مخازن المنصة ومراكز البيانات |
| SH-16 المعرفة | جميع المراكز |

هذه المصفوفة أولية مستنتجة من وظائف المراكز وليست مصفوفة اعتماديات نهائية.

## 6. عقود مشتركة إلزامية

### 6.1 سياق الطلب
requestId، correlationId، organizationId، projectId عند انطباقه، actor، action، contractVersion، payload.

### 6.2 استجابة موحدة
requestId، status، data أو jobRef، warnings، errors، sourceVersion، occurredAt.

### 6.3 بنية الخطأ
code، category، messageKey، retryable، correlationId، safeDetails. لا تعاد stack traces أو الأسرار إلى العميل.

### 6.4 قواعد التشغيل
- idempotency key للعمليات القابلة لإعادة الطلب.
- timeout وretry policy موثقان لكل عملية.
- pagination وحدود payload وحجم الملفات.
- contract version وcompatibility policy.
- health/readiness منفصلان عن نجاح الوظيفة.
- audit event لكل عملية حساسة.
- correlation بين task/event/approval/resource version.
- عدم اعتبار HTTP 2xx دليلاً على اكتمال مهمة غير متزامنة.

## 7. اختبارات قبول مشتركة

تُحفظ اختبارات الفصل 132.19 بالمعرفات AT-01..AT-15، ويُحفظ الفصل 135.11 بالمعرفات API-01..API-10. الاختبارات المطلوب ربطها بعقود الخدمات:
1. تعطل خدمة لا يعطل الخدمات غير المعتمدة عليها.
2. محاولة الوصول إلى مشروع آخر تُرفض.
3. إعادة الحدث نفسه لا تكرر الأثر.
4. عامل متوقف يسمح باستعادة المهمة وفق السياسة.
5. مزود AI غير متاح يؤدي إلى بديل معتمد أو توقف واضح.
6. انتهاء السر يمنع الاتصال ويعرض حالة آمنة.
7. وكيل يتجاوز الصلاحية يُرفض.
8. فشل البحث لا يعطل الخدمات الأساسية.
9. فشل التخزين لا يعلن نجاح الرفع.
10. تعارض إصدار عقد API يُكتشف.
11. استعادة نسخة احتياطية قابلة للتحقق.
12. طلب API بلا صلاحية يُرفض.
13. حقل إلزامي مفقود يُرفض برسالة آمنة.
14. إعادة طلب عملية حساسة لا تكرر الأثر.
15. تعارض organizationId مع هوية الفاعل يُرفض.

لم تُنفذ هذه الاختبارات هنا؛ إنها معايير مصدرية/مقترحة تحتاج خطة تشغيل وأدلة.

## 8. قرارات لا تُحسم في هذا الملف

- هل تتحول كل خدمة مشتركة إلى deployment مستقل؟ لا؛ الفصل المنطقي لا يفرض عدد خوادم.
- هل تُستخدم قاعدة بيانات مستقلة لكل خدمة؟ غير محسوم.
- ما تقنية الرسائل أو البحث أو التخزين؟ خيارات تقنية قيد المراجعة.
- ما قيم SLA/SLO وRTO/RPO؟ غير معتمدة رقمياً.
- ما حدود الإنفاق والعملة؟ غير محسومة.
- لا يذكر الفصل 132.6 الخدمات SH-17..SH-20؛ لكن الفصل 157 مسجل كمادة مطلوبة في الفصل 155.13 ولم يُستعد تفصيله بعد. تظل مسألة اكتمال القائمة مفتوحة.

**الخلاصة:** القائمة المصدرية المستعادة في الفصل 132.6 تضم SH-01..SH-16، لكن الفصل 157 اللاحق مطلوب لتوحيد المكونات والخدمات المشتركة ولم يُستعد بعد. لذلك هذه قائمة مثبتة جزئياً وليست حكماً نهائياً على العدد أو الأسماء. لا تُعد هذه الوثيقة اعتماداً نهائياً أو تنفيذاً للخدمات.
