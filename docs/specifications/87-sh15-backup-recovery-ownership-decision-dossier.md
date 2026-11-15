# 87 — حزمة ملكية النسخ الاحتياطي والاستعادة SH-15

**الحالة:** DERIVED OPERATIONAL CONTRACT / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** 23 و32 و36 و56 و67 و77 و78 و80 وADR-028.

## 1. الحدود

SH-15 يملك كتالوج النسخ والاستعادة وبيانات المهام الوصفية ومراجع اللقطات وأدلة التحقق. كل مركز أو خدمة يظل مالك بياناته ومصدر الحقيقة. لا يثبت وجود سجل backup نجاح الاستعادة، ولا ينقل ملكية بيانات المصدر أو مفاتيح التشفير إلى SH-15.

يجب فصل:
- Backup catalog/job metadata.
- Domain data and canonical records.
- Snapshot consistency points.
- Encryption key ownership.
- Restore orchestration/job execution (مع SH-08/L9/L10 بحسب العقد).
- Service health and recovery evidence (SH-14).
- Release rollback في C11 عن استعادة بيانات المجال.

## 2. مصفوفة المسؤوليات المقترحة

| المورد/القدرة | المالك المقترح | الحد |
|---|---|---|
| Backup plans/catalog/manifests | SH-15 | تعريف النسخ وسجلاتها ومراجعها |
| Source data | كل Domain SoR owner | SH-15 لا يكتب بيانات المجال مباشرة |
| Snapshot participation | كل مالك مصدر | يؤكد consistency point والاكتمال |
| Backup execution jobs | SH-08 / orchestration layer | SH-15 ينسق ويحتفظ بالمرجع |
| Encryption keys | مالك KMS/Secret service الذي لم يُعيّن | SH-15 لا يملك المفتاح لمجرد استخدام keyRef |
| Restore authorization | مالك البيانات + C10 + SH-03 وفق الحساسية | مقدم الطلب لا يعتمد استعادته الحساسة منفرداً |
| Restore execution | SH-08 والمنفذ المعتمد | SH-15 يسجل job/evidence |
| Recovery objectives | مالك الأعمال والخدمات + C10/operations | القيم الرقمية غير محددة |
| Recovery telemetry | SH-14 | لا تساوي نجاح الاستعادة |

## 3. قرارات مطلوبة

| ID | القرار | الحالة |
|---|---|---|
| BAK-87-01 | تعيين مالك خدمة SH-15 ومالك كل مصدر بيانات | OPEN |
| BAK-87-02 | تحديد نطاق النسخ: DB/storage/config/docs/secrets/metadata | OPEN |
| BAK-87-03 | تحديد topology والنسخ العابرة للمناطق ومناطق الإقامة | OPEN |
| BAK-87-04 | تعيين KMS/key owner وآلية rotation/revocation | OPEN |
| BAK-87-05 | اعتماد consistency protocol واللقطات متعددة المجالات | OPEN |
| BAK-87-06 | اعتماد full/incremental/logical/physical policies بحسب المصدر | OPEN |
| BAK-87-07 | اعتماد encryption, integrity, immutability, access separation | OPEN |
| BAK-87-08 | تحديد RPO لكل فئة خدمة/بيانات | OPEN — numeric TBD |
| BAK-87-09 | تحديد RTO لكل خدمة/مسار أعمال | OPEN — numeric TBD |
| BAK-87-10 | اعتماد backup retention وlegal hold وعلاقة حذف المصدر | OPEN |
| BAK-87-11 | اعتماد restore approval وseparation of duties وbreak-glass | OPEN |
| BAK-87-12 | اعتماد restore validation وreconciliation وevidence format | OPEN |
| BAK-87-13 | اعتماد restore drills والتواتر ومعايير النجاح | OPEN |
| BAK-87-14 | اعتماد recovery ordering/dependency graph وservice fallback | OPEN |

## 4. بروتوكول الاستعادة المقترح

1. تحديد incident/scope/target والمالك ومصدر النسخة.
2. معاينة restore preview والتبعيات والمخاطر.
3. التحقق من integrity/checksums والتشفير وتوفر المفاتيح.
4. التحقق من actor/scope/approval وتعارض المصالح.
5. تثبيت consistency point وخطة rollback أو عزل الهدف.
6. تنفيذ restore عبر منفذ مخول.
7. التحقق من سلامة البيانات والعلاقات والعزل والنسخ التابعة.
8. إعادة الخدمات تدريجياً ومراقبة freshness/health.
9. تسجيل evidence وربط incident وbackup وrestore jobs.
10. عدم إعلان recovered حتى يوقع مالكو الخدمات على معايير القبول المعتمدة.

هذا تسلسل مرشح؛ تفاصيله تحتاج اعتماداً حسب نوع المصدر.

## 5. اختبارات القبول

| Test ID | الحالة | النتيجة | الوضع |
|---|---|---|---|
| BAK-87-T01 | checksum mismatch | فشل verification وعدم إتاحة النسخة كصالحة | SPECIFIED / NOT RUN |
| BAK-87-T02 | نسخة متعددة المصادر مع مشارك مفقود | partial/blocked وليس consistent | SPECIFIED / NOT RUN |
| BAK-87-T03 | restore دون موافقة مطلوبة | deny | SPECIFIED / NOT RUN |
| BAK-87-T04 | مقدم الطلب هو المعتمد الوحيد | منع self-approval عند اشتراط الفصل | SPECIFIED / NOT RUN |
| BAK-87-T05 | key unavailable/revoked | fail closed وتسجيل السبب | SPECIFIED / NOT RUN |
| BAK-87-T06 | استعادة tenant-scoped data | لا cross-tenant leakage | SPECIFIED / NOT RUN |
| BAK-87-T07 | restore مكتمل تقنياً لكن فشل تحقق المجال | لا إعلان recovery complete | SPECIFIED / NOT RUN |
| BAK-87-T08 | استعادة إعدادات قديمة | التحقق من precedence والسياسات الحالية قبل التفعيل | SPECIFIED / NOT RUN |
| BAK-87-T09 | restore تحت legal hold | لا حذف/استبدال يخالف الحجز | BLOCKED: policy TBD |
| BAK-87-T10 | recovery drill | evidence يثبت الزمن والنتيجة والنطاق | BLOCKED: RTO/RPO and drill cadence TBD |
| BAK-87-T11 | replay بعد restore | idempotency وعدم تكرار الأثر | SPECIFIED / NOT RUN |
| BAK-87-T12 | تعطل SH-14 telemetry | لا يفترض نجاح الخدمة من غياب القياس | SPECIFIED / NOT RUN |

## 6. ما لا يمكن تحديده الآن

لا توجد في المصادر قيم رقمية معتمدة لـRTO/RPO أو مدد الاحتفاظ أو تواتر الاختبارات أو سياسة النسخ الجغرافي. لا يتم اختراعها. يجب تحديدها بعد business impact analysis وتصنيف البيانات والتبعيات والتكلفة والقدرات الواقعية للمزود.

**SH-15 BACKUP & RECOVERY / OPEN / NOT APPROVED / NO IMPLEMENTATION.**
