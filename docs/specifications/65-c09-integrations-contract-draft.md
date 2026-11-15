# 65 — مسودة عقود C09: التكاملات والخدمات الخارجية

**الحالة:** DERIVED-DRAFT / NOT-APPROVED / NO-IMPLEMENTATION  
**المراجع:** 02 و19 و23 و25 و27 و32 و36 و47 و48؛ التفاصيل الجديدة مقترحة.

## 1. حدود المسؤولية

C09 يملك تكاملات المجال: تعريف ما يتصل به المشروع أو المصمم، غرض الاتصال، إعدادات الموصل على مستوى المجال، خرائط البيانات، وسياسات استخدام الخدمة الخارجية. SH-13 يملك دورة حياة الموصل المشتركة وبياناته التشغيلية ومراجع الأسرار، بينما SH-01/02 يملكان الهوية والتفويض، وSH-04 سجل التدقيق، وSH-08 المهام، وSH-09 نقل الأحداث. لا يملك C09 حساب المستخدم لدى المزود ولا أسرار المصادقة الخام ولا حقيقة بيانات الطرف الخارجي.

## 2. الكيانات المقترحة

| الكيان | المالك | الغرض | الحالة |
|---|---|---|---|
| integration_definitions | C09 | وصف التكامل والغرض والمالك والمجال | PROPOSED |
| integration_instances | C09 | ربط التكامل بمؤسسة/مساحة/مشروع/بيئة | PROPOSED |
| integration_capabilities | C09 | عمليات مسموحة ونطاقات OAuth/permissions | PROPOSED |
| integration_mappings | C09 | mapping للحقول والتحويلات | PROPOSED |
| integration_sync_policies | C09 | اتجاه وتواتر ونطاق المزامنة | PROPOSED |
| integration_runs | SH-08/C09 | المهمة والمحاولات والنتيجة المرجعية | PROPOSED |
| connector_runtime_records | SH-13 | health/config lifecycle/connector version | SHARED PROPOSED |
| secret_references | Secret Manager/SH-13 | مرجع آمن فقط، لا القيمة السرية | SHARED PROPOSED |
| external_webhook_registrations | C09/SH-13 | تسجيل webhook ومفتاح التحقق المرجعي | PROPOSED |
| integration_audit_refs | SH-04 | مراجع التدقيق | SHARED |

كل سجل مؤسسي يثبت tenant scope. لا تُنسخ بيانات الطرف الخارجي إلا وفق غرض وسياسة وحقوق واضحة.

## 3. العمليات المقترحة

| العملية | النوع | المدخلات/النتيجة | الضابط |
|---|---|---|---|
| registerIntegration | Command | provider, purpose, scope, environment → integration_ref | موصل معتمد |
| configureIntegration | Command | mapping, capability set, secret_ref → config_version | لا secret raw |
| beginAuthorization | Command | integration_ref, redirect context → auth_session | state/nonce/PKCE وفق نوع التدفق |
| completeAuthorization | Command | auth_code/result → connection status | تحقق خادمي ومطابقة redirect |
| validateConnection | Command | integration_ref → health/result | لا كشف credentials |
| testMapping | Command | sample data, mapping version → validation report | بيانات اختبار منقحة |
| startSync | Command | direction, scope, cursor → job_ref | موافقة على الكتابة الخارجية |
| receiveWebhook | Inbound | signed payload metadata → accepted/rejected | تحقق التوقيع ومنع replay |
| pauseIntegration | Command | reason, expected_version → status | مخول |
| revokeConnection | Command | integration_ref, reason → revoked | إبطال الرمز لدى المزود عند الدعم |
| rotateSecretReference | Command | new_secret_ref → new config version | لا إظهار السر |
| getIntegrationStatus | Query | integration_ref → health, freshness, errors | ACL |

هذه عقود منطقية؛ ليست endpoints أو مزودين معتمدين. أي مزود أو نطاق OAuth أو endpoint خارجي يحتاج مراجعة أمنية وخصوصية وترخيصاً.

## 4. قواعد الاتصال

- allowlist للمضيفين والبروتوكولات؛ منع SSRF والوصول إلى loopback والشبكات الداخلية وعناوين metadata.
- TLS والتحقق من الشهادات؛ تدوير الأسرار وإبطالها دون كشفها.
- OAuth: state/nonce/PKCE حيث ينطبق، redirect URI مقيد، تخزين refresh tokens في مخزن أسرار خادمي.
- webhooks: تحقق من التوقيع والتوقيت ومنع replay، وحجم payload، وidempotency.
- rate limits، timeouts، circuit breakers، retry with backoff للأخطاء العابرة فقط، وعدم إعادة المحاولة للعمليات غير idempotent بلا حماية.
- تحديد اتجاه المزامنة وsource of truth وحل التعارضات قبل تفعيل الكتابة ثنائية الاتجاه.
- فصل بيئات التطوير والاختبار والإنتاج والحسابات الخارجية.
- تسجيل metadata والنتائج المرجعية لا credentials أو payloads الحساسة.

## 5. صلاحيات أولية

مسؤول التكامل: إعداد وتفعيل الموصل ضمن نطاقه؛ مالك المشروع: طلب الربط والموافقة على أثره؛ محرر: اختبار mapping ببيانات تجريبية؛ مدقق: قراءة سجل التدقيق؛ وكيل AI: اقتراح mapping أو تشخيص فقط؛ لا يطلب أو يخزن secret ولا يفعل تكاملاً خارج تفويض صريح. قرار الوصول النهائي عبر SH-02 والسياسات في C10.

## 6. أحداث مقترحة

IntegrationRegistered, IntegrationAuthorizationStarted, IntegrationConnected, IntegrationConnectionFailed, IntegrationSyncStarted, IntegrationSyncCompleted, IntegrationSyncFailed, IntegrationPaused, IntegrationRevoked, WebhookRejected.

كل حدث يحتاج schema version وtenant/project scope وcorrelation id وproducer/consumer وidempotency.

## 7. اختبارات مقترحة غير منفذة

- C09-DRAFT-AT-01: رفض URL داخلي/غير مسموح ومنع SSRF.
- C09-DRAFT-AT-02: رفض OAuth state غير مطابق أو redirect URI غير مسجل.
- C09-DRAFT-AT-03: لا يظهر access/refresh token في الاستجابة أو السجل.
- C09-DRAFT-AT-04: رفض webhook بتوقيع غير صالح أو timestamp خارج السياسة.
- C09-DRAFT-AT-05: منع تكرار webhook عند إعادة التسليم.
- C09-DRAFT-AT-06: لا تبدأ مزامنة كتابة قبل الموافقة المطلوبة.
- C09-DRAFT-AT-07: تعارض المزامنة لا يستبدل مصدر الحقيقة بصمت.
- C09-DRAFT-AT-08: إلغاء التكامل يبطل الاعتمادات ويوقف المهام المجدولة.
- C09-DRAFT-AT-09: عزل بيانات التكامل بين المؤسسات والبيئات.
- C09-DRAFT-AT-10: retry لا يكرر أثراً خارجياً غير idempotent.

## 8. التبعيات والقرارات المفتوحة

C09 يعتمد على C10 للسياسات والمخاطر، C13 لإعدادات النطاق، C08 للملفات المستوردة، C04 لمخططات/خرائط البيانات عند انطباقها، SH-01/02/03/04/08/09/10/13، وC14 للعرض فقط. يحتاج اعتماد مالك SH-13، قائمة الموصلات للإصدار الأول، سياسة OAuth/webhooks، تصنيف البيانات ومناطق المعالجة، الحدود والتكلفة، سياسات المزامنة والتعارض، واشتراطات مزودي الطرف الثالث.

**الحكم:** مسودة مراجعة؛ لا تثبت موصلات منشورة أو موافقات مزودين أو اختبارات ناجحة.
