# 102 — تدقيق فجوة تغطية الأحداث وربط الاختبارات المرشحة

**الحالة:** TRACEABILITY GAP AUDIT / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** 80، 91–93، 96، 100، 101.  
**الغرض:** التحقق من أن سجل الأحداث 100 لا يخلط بين وجود عائلة اختبار عامة وبين وجود اختبار قبول دقيق لكل حدث، وتحديد ما يمكن ربطه الآن وما يجب أن يبقى TBD.

## 1. النتيجة التنفيذية

سجل 100 يحتوي 106 Event IDs، لكنه يضع في عمود الاختبار لكل حدث تقريباً إحالة عامة إلى EVT-96-T01..12 مع عبارة exact test TBD. هذه ليست تغطية اختبار مفردة. كتالوج 80 يعرّف EVT-96-T01..12 كاختبارات عامة لسجل الأحداث، كما يعرّف اختبارات مراكز ومجموعات INT/SEC وخدمات 91–93، لكنه لا يربط كل Event ID باختبار مفرد ذي oracle خاص به.

**نتيجة التدقيق:**
- أحداث في سجل 100: 106.
- أحداث لها exact event-specific Test ID مثبت في المصدر: 0 مثبتة في الوثائق التي تمت مراجعتها.
- أحداث ذات family/generic test reference فقط: 106.
- أحداث يجب أن تبقى EXACT-TEST-TBD / OWNER REVIEW: 106.
- اختبارات EVT-96-T01..12: 12 اختباراً عاماً، لا تُعامل على أنها 12 اختباراً لكل حدث.
- حالات الاختبار التشغيلية: SPECIFIED / NOT RUN أو BLOCKED حسب كتالوج 80 والحزم 91–93.

هذا العد يصف محتوى الوثائق المراجعة، ولا يثبت عدم وجود اختبار في مصدر آخر لم تتم استعادته.

## 2. تصنيف مجموعات الاختبار المتاحة

| مجموعة الاختبار | النطاق المثبت | ما يمكن استخدامها له | ما لا تثبته |
|---|---|---|---|
| DRAFT-AT-C01-01..12 | C01 إنشاء المشروع والمتطلبات | مرشحة لاختبارات أحداث C01 بعد فحص نص كل حالة | لا تحدد Event ID بعينه في 80 |
| DRAFT-AT-C02-01..15 وC02-DRAFT-AT-16..20 | C02 التصميم | مرشحة لأحداث التصميم والاعتماد | لا يوجد event-to-test mapping صريح |
| DRAFT-AT-C03-01..14 وC03-DRAFT-AT-15..20 | C03 الصفحات والتنقل | مرشحة لأحداث الصفحات والمسارات | لا يوجد event-to-test mapping صريح |
| DRAFT-AT-C04-01..17 وC04-DRAFT-AT-18..28 | C04 البيانات | مرشحة لأحداث المخطط والترحيل والتعارض والنسخ | restore/RTO-RPO بعضه BLOCKED؛ لا mapping مفرد |
| C05-AT-01..20 وC05-DRAFT-AT-21..32 | C05 البرمجة | مرشحة لأحداث code/sandbox/review | بعض نصوص المصدر غير مستعادة؛ لا mapping مفرد |
| C06-AT-01..20 وC06-DRAFT-AT-21..27 | C06 الوكلاء | مرشحة لأحداث agent/task/tool grant/output | لا mapping مفرد؛ لا تستبدل اختبارات SH-08 |
| C07-AT-01..22 وC07-DRAFT-AT-23..28 | C07 الجودة | مرشحة لأحداث QA/test/defect/quality gate | لا mapping مفرد؛ لا تستبدل telemetry SH-14 |
| حالات C08 في 64 | C08 الملفات | مرشحة لأحداث الأصل والرفع والفحص والحذف | OWN-01 مفتوح؛ لا IDs مفردة مستعادة في 80 |
| حالات C09 في 65 وINT-91-T01..12 | C09 والتكامل | مرشحة لأحداث التكامل والمزامنة والويب هوك والاتصال | لا mapping Event ID محدداً |
| AUTH-78 / APR-79 / SEC-80 | C10 الأمن والحوكمة | مرشحة لأحداث policy/grant/approval/audit/security | يلزم تحديد test oracle والمالك لكل حدث |
| حالات C11 في 67 وVER-85 وBAK-87 وMON-93 | C11 الإصدار | مرشحة لأحداث build/release/deploy/rollback | لا يجوز دمج نتيجة المجال مع job/telemetry/backup |
| حالات C12 في 68 وKNOW-90 | C12 المعرفة | مرشحة لأحداث المحتوى والمراجعة والنشر والفهرسة | لا mapping مفرد |
| حالات C13 في 69 وCFG-86 | C13 الإعدادات | مرشحة لأحداث التغيير والتوريث والموافقة | لا mapping مفرد |
| حالات C14 في 70 وINT-80-13 وMON-93-T08 | C14 العرض | مرشحة لأحداث projection/layout/freshness | لا تثبت ملكية مؤشرات المجال |
| EVT-96-T01..12 | inventory أحداث C01–C14 | تحقق عام من producer/scope/version/dedup/traceability | لا تثبت oracle لكل حدث |
| INT-80-01..18 وSEC-80-01..16 | تكامل وأمن مشترك | اختبارات ضوابط أفقية مشتركة عند انطباقها | لا تستبدل اختبار المجال الدقيق |
| INT-91-T01..12 | SH-13 | موصلات/OAuth/secrets/webhooks/revocation | لا تثبت صحة كل حدث C09 |
| TASK-92-T01..14 | SH-08 | execution/retry/cancel/replay/worker | لا تثبت نتيجة Domain Job |
| MON-93-T01..14 | SH-14 | telemetry/health/alert/freshness | لا تثبت نتيجة QA أو مجال |
| BAK-87-T01..12 | SH-15 | backup/restore integrity/authorization | لا تثبت semantic restore acceptance للمجال |

## 3. قواعد اختيار الاختبار للحدث

لا يربط أي Event ID باختبار لمجرد اشتراكهما في المركز. يجب أن يثبت الاختبار جميع ما يلي:
1. الحدث أو الانتقال الدلالي المستهدف صراحة.
2. producer/Domain SoR المتوقع.
3. preconditions بما فيها actor/scope/approval/policy version.
4. schema/version وrequired fields وprivacy classification.
5. idempotency/ordering/replay semantics عند انطباقها.
6. expected emitted event أو عدم صدوره في حالات الرفض.
7. أثر المجال الذي يجب أن يحدث مرة واحدة أو لا يحدث.
8. negative cases: unauthorized, cross-tenant, stale version, duplicate, invalid payload.
9. evidence: eventId, requestId, correlationId, commit/build, environment, actor, timestamps.
10. Test ID مفرد ثابت أو N/A مبرر ومعتمد من مالك الحدث.

## 4. مصفوفة مجموعات الأحداث والفجوة

| Event group | المركز | نوع الحدث | candidate test families | فجوة الإغلاق |
|---|---|---|---|---|
| EVT-C01-* | C01 | project/requirements/baseline | DRAFT-AT-C01-*; INT-80-01..08; EVT-96-* | استعادة نص الاختبارات المصدرية وتعيين Test ID لكل حدث |
| EVT-C02-* | C02 | design/version/validation/approval | DRAFT-AT-C02-*; INT-80-06/07/15; EVT-96-* | ربط event hash/version والاعتماد باختبار محدد |
| EVT-C03-* | C03 | page/route/navigation/approval | DRAFT-AT-C03-*; INT-80-07/08; EVT-96-* | test oracle للروابط اليتيمة والمسارات والاعتماد |
| EVT-C04-* | C04 | source/schema/migration/RLS/sync/backup | DRAFT-AT-C04-*; INT-80-17/18; BAK-87-*; EVT-96-* | فصل BackupCompleted عن restore verification وDomain validation |
| EVT-C05-* | C05 | code generation/diagnostics/sandbox/review | C05-AT-*; SEC-80-06/07/15; EVT-96-* | استعادة نصوص المصدر وربط كل مرحلة |
| EVT-C06-* | C06 | agent/task/grant/output/review | C06-AT-*; INT-80-14/15; SEC-80-09/10; TASK-92-* | فصل أحداث المهمة/الوكيل عن execution attempts |
| EVT-C07-* | C07 | QA plan/run/defect/gate/waiver | C07-AT-*; INT-80-07/10/15; MON-93-T07; TASK-92-* | فصل نتيجة الاختبار عن runner health وjob state |
| EVT-C08-* | C08 | asset/upload/scan/version/delete | حالات 64؛ SEC-80-03/04/14/16؛ INT-80-11/12 | استعادة IDs من المصدر وتثبيت ownership/storage boundary |
| EVT-C09-* | C09 | integration/authorization/sync/webhook/revoke | حالات 65؛ INT-91-*؛ TASK-92-*؛ INT-80-05/08/12 | فصل event domain عن connector runtime/transport |
| EVT-C10-* | C10 | policy/risk/privacy/exception/audit | AUTH-78؛ APR-79؛ SEC-80-*؛ INT-80-09/10/15/16 | تحديد test owner وapproval semantics لكل حدث |
| EVT-C11-* | C11 | build/artifact/release/deploy/rollback | حالات 67؛ VER-85-*؛ ART-95-*؛ BAK-87-*؛ MON-93-* | فصل build/deploy domain outcome عن execution/telemetry/backup |
| EVT-C12-* | C12 | knowledge/content/review/publish/index | KNOW-90-*؛ INT-80-11؛ SEC-80-12 | فصل content lifecycle عن indexing/storage |
| EVT-C13-* | C13 | setting/change/inheritance/approval | CFG-86-*؛ APR-98-*؛ INT-80-06/07/10 | اختبار effective value والنسخة والـscope |
| EVT-C14-* | C14 | projection/layout/freshness | INT-80-13؛ MON-93-T08؛ OPS-89-T03 | إثبات read-only projection وsource ACL/freshness |

النجمة تشير إلى الأحداث التابعة للمركز في الوثيقة 100، وليست wildcard تنفيذية أو مجموعة اختبار.

## 5. تصحيح استخدام EVT-96-T01..12

تظل EVT-96-T01..12 اختبارات inventory عامة، وتستخدم فقط كاختبارات cross-cutting حيث يتوافق السيناريو. لا يجوز:
- تكرار نفس الاختبار العام في 106 صفوف واحتسابه تغطية مفردة.
- ترقية حدث إلى COVERED لمجرد أن EVT-96 موجود في عمود الاختبار.
- اعتبار اختبار SH-08/13/14/15 اختباراً لنتيجة مجال.
- اعتبار وجود Test ID في dossier دليلاً على تشغيله.
- استخدام Test ID مرشح دون مراجعة السيناريو وoracle.

## 6. سجل قرارات الإغلاق

| ID | المطلوب | المالك المطلوب | الحالة |
|---|---|---|---|
| EVT-TEST-102-01 | استعادة test definitions المصدرية للفصول/الملاحق الأصلية | مالك المواصفات | OPEN |
| EVT-TEST-102-02 | اعتماد Event Producer وDomain SoR لكل Event ID | مالك كل مركز + SH-09 | OPEN |
| EVT-TEST-102-03 | تعيين exact Test ID أو N/A مبرر لكل Event ID | مالك الحدث + C07 | OPEN |
| EVT-TEST-102-04 | اعتماد event schema/version/ordering/idempotency | المنتج والمستهلك وC10 | OPEN |
| EVT-TEST-102-05 | ربط test evidence بالـbuild/environment/actor | C07 + مالك البيئة | OPEN |
| EVT-TEST-102-06 | تشغيل فحص آلي للتغطية المرجعية بعد اعتماد المصفوفة | C07/QA tooling | BLOCKED: implementation gate |
| EVT-TEST-102-07 | اعتماد سياسة حالات التغطية (covered/partial/uncovered/N/A) | C07 + الملاك | OPEN |

## 7. الحالة الموصى بها في الوثيقة 100

حتى إغلاق EVT-TEST-102-01..07، يجب أن تكون قيمة عمود الاختبار لكل Event ID:
**GENERIC-FAMILY-ONLY / EXACT-TEST-TBD / NOT EXECUTED**
بدلاً من وصف الحدث بأنه مغطى.

## 8. أثر الوثيقة

هذه مراجعة مشتقة لحدود التغطية، لا تغير المصدر الرئيسي أو كتالوج 80، ولا تعتمد أي اختبار، ولا تغلق RI-94-06/08 أو TRACE-99-T05، ولا تنشئ كوداً أو test runner أو event schema. تظل جميع الحالات SPECIFIED / NOT RUN أو BLOCKED حتى وجود أدلة تشغيل حقيقية.


## تحديث مصدرّي 2026-11-15 — نتائج استعادة الاختبارات
تضيف الوثيقة 104 سيناريوهات قبول مصدرية محددة الموضع، بما فيها مجموعات C01–C09 واختبارات C13 وAT/API/DOC المشتركة. لكنها لا تحتوي Event ID mapping صريحاً، ولذلك تظل نتيجة 102 كما هي: لا يوجد حتى الآن exact event-specific Test ID مثبت لكل حدث من أحداث 100. معرفات SRC-AT محلية وليست معرفات مصدر أو اختبارات منفذة.


## تحديث منشأ معرفات الاختبارات — الوثيقة 105
أكد تدقيق المصدر وجود 89 معرف اختبار فريداً عبر ثماني عائلات (AT/API/DATA/DOC/DASH-INT/KNOW-QA/KNOW-SEC/SET-INT). لم يثبت ربط مفرد لهذه المعرفات بأحداث سجل 100؛ لذلك لا تتغير نتيجة صفر exact event-specific Test IDs المثبتة في الوثائق المراجعة. TEST-NAV-0001 مثال افتراضي، وGATE-01..07 بوابات جاهزية لا اختبارات.
