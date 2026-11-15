# 123 — تدقيق إغلاق حدود SH-08 وSH-13 وSH-14 وSH-15 عبر دورة التشغيل

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED CROSS-SERVICE CLOSURE AUDIT / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**النطاق:** دورة تشغيل المهام والتكامل والمراقبة والنسخ والاستعادة.  
**المراجع:** 23 و32 و36 و67 و72 و87 و91 و92 و93 و97 و100 و106 و107 و109 و111–122.

## 1. الغرض

توجد حزم مستقلة للخدمات SH-08 (تنفيذ المهام)، SH-13 (التكامل والموصلات)، SH-14 (المراقبة التشغيلية)، وSH-15 (النسخ والاستعادة). يراجع هذا التدقيق نقاط التماس بينها حتى لا تتكرر ملكية حالة التنفيذ أو نتيجة المجال أو المراقبة أو بيانات المصدر.

هذه مراجعة اتساق مشتقة من الحزم القائمة، وليست عقداً معتمداً أو استعادة حرفية للفصل 157 المفقود. لا تعني وجود العقود أن الخدمات أو واجهاتها نُفذت.

## 2. نموذج الملكية عبر دورة التشغيل

| المرحلة | مالك الحقيقة/القرار المقترح | دور الخدمة المشتركة | ما يجب ألا يحدث |
|---|---|---|---|
| تعريف مهمة المجال | مركز المجال المنتج | لا يملك SH-08 معنى المهمة | تحويل Domain Job إلى سجل تنفيذ عام |
| إرسال التنفيذ | مركز المجال يملك الطلب الدلالي | SH-08 ينشئ execution reference | قبول scope أو actor دون إعادة تحقق |
| تشغيل محاولة | SH-08 | يدير queue/attempt/lease/retry | إعلان نجاح المجال بمجرد انتهاء worker |
| تكامل خارجي | C09 يملك definition/mapping/sync policy | SH-13 يملك connector runtime metadata؛ SH-08 ينفذ المهمة | SH-13 يملك mapping أو أسراراً خاماً |
| نتيجة مزامنة/مجال | C09 أو مالك المجال | SH-08 يحتفظ بمراجع التنفيذ؛ SH-13 بحالة الموصل | جعل execution status بديلاً عن domain outcome |
| قياس التشغيل | producer يملك المعنى الخام؛ مالك الخدمة يحدد SLI/SLO | SH-14 يجمع operational telemetry | نقل QA result أو business truth إلى SH-14 |
| لوحة المعلومات | مالك المجال يملك المصدر | C14 يعرض projection مع source/asOf | C14 يكتب أو يعيد تعريف المؤشر |
| خطة النسخ والكتالوج | SH-15 المقترح | ينسق jobs ومراجع اللقطات | SH-15 يصبح Domain SoR |
| استعادة المصدر | مالك بيانات المصدر يوافق على سلامة المجال | SH-15 ينسق؛ SH-08 ينفذ؛ SH-14 يراقب | إعلان التعافي من نجاح job أو غياب تنبيه فقط |

## 3. نقاط التماس الحرجة والقرارات المطلوبة

| ID | الحد الفاصل | القرار المطلوب | المراجع | الحالة |
|---|---|---|---|---|
| XSH-123-01 | Domain Job / Execution Job | اعتماد owner لكل domain job ونموذج ربطه بـexecutionJobRef | 92 / 97 | OPEN |
| XSH-123-02 | execution status / domain outcome | اعتماد حالات مستقلة ومعيار domain commit | 92 | OPEN |
| XSH-123-03 | C09 mapping / SH-13 manifest | اعتماد schema وحدود التعديل والإصدار | 91 / 65 | OPEN |
| XSH-123-04 | connector runtime / task execution | اعتماد التفاعل بين SH-13 وSH-08 ومالك المجال | 91 / 92 | OPEN |
| XSH-123-05 | connector secretRef / secret value | تعيين Secret Manager/KMS ومالك المفاتيح ودورة التدوير | 91 / 87 | OPEN |
| XSH-123-06 | retry / external side effect | اعتماد idempotency contract لكل عملية خارجية غير آمنة للتكرار | 91 / 92 | OPEN |
| XSH-123-07 | operational telemetry / QA result | اعتماد حدود SH-14 مقابل C07 | 93 / 63 | OPEN |
| XSH-123-08 | alert / incident lifecycle | تعيين مالك فتح/إقرار/معالجة/إغلاق الحادث وعلاقته بـSH-05/09 | 93 | OPEN |
| XSH-123-09 | health / readiness / liveness | اعتماد taxonomy ومصادر القياس وحدود freshness | 93 | OPEN |
| XSH-123-10 | backup catalog / source data | اعتماد مشاركة كل Domain SoR في consistency point | 87 | OPEN |
| XSH-123-11 | backup job / restore job | اعتماد تنسيق SH-15 مع SH-08 ومالك البيانات | 87 / 92 | OPEN |
| XSH-123-12 | restore complete / service recovered | اعتماد توقيع مالك المجال على تحقق البيانات بعد الاستعادة | 87 / 93 | OPEN |
| XSH-123-13 | approval / delayed execution | اعتماد إعادة التحقق من approval fingerprint وpermission عند التنفيذ | 87 / 91 / 92 / 118–119 | OPEN |
| XSH-123-14 | cross-tenant job/connector/restore | اعتماد scope propagation والتحقق عند كل حد خدمة | 84 / 91 / 92 / 87 | OPEN |
| XSH-123-15 | retention across logs/events/backups | توحيد تصنيف البيانات والاحتفاظ والحذف القانوني ومراجع الأسرار | 88 / 91 / 92 / 93 / 87 | OPEN |
| XSH-123-16 | recovery targets / monitoring objectives | اعتماد RTO/RPO/SLO وقياسها وعدم اختراع قيم | 87 / 93 | OPEN |

## 4. قواعد تكامل إلزامية قبل اعتماد العقود

1. **Identity and scope:** كل خدمة تتحقق من actor والـtenant/organization/workspace/project/environment scope عند الاستلام والتنفيذ المؤجل؛ لا يكفي تمرير معرف النطاق.
2. **Approval binding:** يرتبط القرار بالفعل والموارد وإصدار العقد والبصمة؛ أي تغيير جوهري يبطل الموافقة السابقة. يعاد التحقق عند بدء job وبعد الانتظار الطويل.
3. **Idempotency:** كل عملية قابلة لإعادة الإرسال تحدد مفتاح deduplication ونطاقه ومدة الاحتفاظ به؛ retries لا تعيد الأثر الخارجي غير الآمن.
4. **Version lineage:** تسجل taskType/connector manifest/mapping/config/approval policy versions المستخدمة في التنفيذ.
5. **Domain truth:** لا تنقل SH-08 أو SH-13 أو SH-14 أو SH-15 ملكية حقيقة المجال أو قرار قبوله.
6. **Telemetry provenance:** كل قياس يحمل producer/source/time/quality/scope؛ غياب القياس لا يعني Healthy.
7. **Restore verification:** نجاح النسخ أو الاستعادة تقنياً لا يثبت اتساق بيانات المجال أو عودة الخدمة؛ يلزم تحقق المصدر وتوقيع مالكه.
8. **Audit:** كل طلب حساس وقرار وموافقة وتنفيذ وإلغاء واستعادة وإبطال اتصال يملك correlation/audit reference؛ لا تسجل الأسرار الخام.
9. **Failure semantics:** يجب أن تكون حالات Unknown/Stale/Partial/Blocked ظاهرة، ولا تتحول إلى Success أو Healthy افتراضياً.
10. **Tenant isolation:** يمنع عبور jobs/connectors/telemetry/backups وملفات الأدلة بين المستأجرين دون تفويض صريح.

## 5. حزمة اختبارات تكامل مشتقة

هذه سيناريوهات مقترحة للتخطيط والربط؛ ليست Test IDs مصدرية، وليست منفذة. لا تستبدل معرفات الاختبار الأصلية في سجل 105 أو Exact mappings المفقودة في 112/115.

| Local Scenario ID | المسار | معيار القبول | الحالة |
|---|---|---|---|
| XSH-123-T01 | enqueue ثم سحب grant قبل التنفيذ | SH-08 يعيد التفويض ويرفض التنفيذ | SPECIFIED / NOT RUN |
| XSH-123-T02 | موافقة على mapping ثم تغيير mapping version | يرفض SH-13/SH-08 الموافقة القديمة | SPECIFIED / NOT RUN |
| XSH-123-T03 | webhook مكرر ثم retry | لا يتكرر domain side effect | SPECIFIED / NOT RUN |
| XSH-123-T04 | worker success مع domain commit failure | execution success لا يعلن domain success | SPECIFIED / NOT RUN |
| XSH-123-T05 | connector health قديم | SH-14 يعرض stale/unknown ولا healthy | SPECIFIED / NOT RUN |
| XSH-123-T06 | فشل C07 test | تبقى النتيجة ملك C07 ولا تتحول تلقائياً إلى incident | SPECIFIED / NOT RUN |
| XSH-123-T07 | غياب telemetry ingestion | حالة المراقبة degraded/unknown | SPECIFIED / NOT RUN |
| XSH-123-T08 | backup متعدد المصادر مع source participant مفقود | لا تعلن snapshot متسقة | SPECIFIED / NOT RUN |
| XSH-123-T09 | restore تقني ناجح وفشل تحقق المجال | لا تعلن recovery complete | SPECIFIED / NOT RUN |
| XSH-123-T10 | job/connector/restore ref من tenant آخر | رفض مع عدم كشف بيانات | SPECIFIED / NOT RUN |
| XSH-123-T11 | secret value داخل log/event | منع التخزين أو التنقيح | SPECIFIED / NOT RUN |
| XSH-123-T12 | retry/replay بعد restore | لا تكرار للأثر وتبقى lineage قابلة للتدقيق | SPECIFIED / NOT RUN |
| XSH-123-T13 | alert triggered دون source evidence | لا يعتمد كتنبيه موثوق | SPECIFIED / NOT RUN |
| XSH-123-T14 | موافقة انتهت أثناء انتظار job | يرفض التنفيذ أو يطلب قراراً جديداً | SPECIFIED / NOT RUN |

## 6. أثر التدقيق على سجلات التتبع

- لا تعدل الوثائق 97 أو 99 أو 100 أو 106 أو 107 أو 109 لإضافة ربط Exact غير مثبت.
- يجب أن تبقى XSH-123-T01..14 معرفات محلية مشتقة حتى يراجع مالك الاختبار ويعتمد ربطها بمعرفات 105 الأصلية أو ينشئ معرفات جديدة وفق سياسة معتمدة.
- لا يغلق هذا التدقيق OWN72-02 أو OWN72-06 أو OWN72-08 أو SH-15 ownership.
- لا يغلق 62 عملية TBD في 121 ولا يثبت صحة الإحالات الـ139 في 122.
- لا يحدد أرقام RTO/RPO/SLO أو retention أو quotas أو retry counts.
- لا يحسم موضع validateBuildConfiguration بين C05/C11.

## 7. ترتيب إغلاق الاعتماد

1. تعيين ملاك SH-08/13/14/15 وملاك المجال المقابلين، وتوقيع حدود المسؤولية.
2. اعتماد Domain SoR وtenant scope لكل مورد/كيان يتأثر.
3. اعتماد عقود التكامل والهوية والموافقات وإصدارات الموارد.
4. اعتماد حالات التنفيذ والنتيجة والحوادث والصحة والاستعادة.
5. تعيين Secret Manager/KMS وقرار الاحتفاظ والتصنيف.
6. اعتماد RTO/RPO/SLO والميزانيات والحدود التشغيلية بعد تحليل الأثر والتكلفة.
7. ربط كل عملية وحدث وموافقة باختبار قبول مفرد أو استثناء مبرر.
8. تنفيذ الاختبارات وإرفاق run IDs وتقارير الأدلة.
9. مراجعة الحزمة الموحدة واعتماد المواصفات النهائية.
10. طلب موافقة مستقلة وصريحة لبدء البرمجة.

## 8. الخلاصة

الحدود المقترحة متسقة على المستوى المفاهيمي: SH-08 تنسق التنفيذ، SH-13 تدير runtime الموصل، SH-14 تجمع المراقبة التشغيلية، SH-15 تنسق النسخ والاستعادة؛ وتبقى حقيقة المجال عند مالك المجال. لكن جميع القرارات الـ16 أعلاه ما زالت OPEN، والاختبارات الـ14 SPECIFIED/NOT RUN. هذه الحزمة تقلل تضارب الملكية لكنها لا تعتبر الخدمات جاهزة للتنفيذ أو الاعتماد.

**NO IMPLEMENTATION / NO API OR SCHEMA APPROVAL / NO TEST EXECUTION / SPECIFICATION APPROVAL GATE REMAINS CLOSED.**
