# 93 — حزمة قرارات ملكية SH-14 للمراقبة التشغيلية والقياسات

**الحالة:** DERIVED DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المراجع:** 23 و32 (SH-14)، 36 (OWN36-03)، 63 (C07)، 67 (C11)، 72 (OWN72-06 وOWN72-08)، 78–80، 82، 87، 89.  
**حد المصدر:** SH-14 واردة في قائمة الخدمات ومسؤوليتها المختصرة؛ التفاصيل التالية مشتقة وليست نصاً تاريخياً مستقلاً.

## 1. الفصل بين ثلاثة أنواع من الحقيقة

1. **Telemetry/Operational Health:** SH-14 يملك سجلات القياسات التشغيلية والفحوص والتنبيهات ومراجع الحوادث ومؤشرات freshness.
2. **Quality/Test Results:** C07 يملك خطط الاختبار وتشغيلاته ونتائجه وعيوبه وبوابات الجودة؛ فشل اختبار ليس تلقائياً حادثة تشغيل.
3. **Business/Domain Data:** كل مركز مجال يملك بياناته ونتائج عملياته؛ SH-14 لا يصححها ولا يصبح مصدر حقيقة بديل.
4. **Dashboard:** C14 يعرض إسقاطات قراءة من مصادرها ولا يملك المؤشر أو قرار إغلاق الحادث.

يظل OWN36-03 وOWN72-08 مفتوحين إلى أن يراجع الملاك حدود C07/SH-14، كما أن SH-14 ضمن OWN72-06 مع C11.

## 2. قرارات مطلوبة

| ID | القرار | التوصية | الحالة |
|---|---|---|---|
| MON-93-01 | مالك SH-14 ونطاقه | SH-14 يملك operational telemetry فقط | OPEN |
| MON-93-02 | فصل health/readiness/liveness | تعريف منفصل لكل معنى وعدم دمجها | OPEN |
| MON-93-03 | مالك SLI/SLO لكل خدمة | مالك الخدمة يحدد مؤشرات المجال؛ SH-14 يستضيف/يجمع | OPEN |
| MON-93-04 | freshness/staleness | لا قياس حديثاً بلا timestamp/source/quality | OPEN |
| MON-93-05 | قواعد التنبيه والشدة | اعتماد owner وseverity وrouting وsuppression | OPEN |
| MON-93-06 | incident lifecycle | تحديد من يفتح/يقر/يعالج/يغلق الحادث | OPEN |
| MON-93-07 | ملكية القياس ومصدره | producer يملك القياس الخام؛ SH-14 يملك ingestion/storage/series metadata | OPEN |
| MON-93-08 | التمييز عن C07 | لا تحويل نتيجة اختبار إلى incident تلقائياً | OPEN |
| MON-93-09 | التمييز عن C14 | dashboard projection read-only مع source/asOf | OPEN |
| MON-93-10 | sampling/cardinality | سياسة تمنع cardinality explosion وتوثق sampling | OPEN |
| MON-93-11 | retention/privacy | مدد الاحتفاظ والتصنيف وفق سياسة 88، القيم TBD | OPEN |
| MON-93-12 | alert delivery | يعتمد على SH-05/SH-09 دون ازدواج ملكية التنبيه | OPEN |
| MON-93-13 | operational targets | لا تُخترع أرقام SLO/RTO/RPO أو freshness | OPEN |
| MON-93-14 | outage/monitoring failure | مسار health بديل ومراقبة فقد المراقبة | OPEN |

## 3. سجل الموارد

| المورد | المالك المقترح | ملاحظات |
|---|---|---|
| metric_definition | مالك الخدمة المنتجة؛ catalog metadata عند SH-14 | معنى القياس ووحدته |
| metric_sample | producer + SH-14 ingestion | timestamp/source/labels/quality |
| health_check_definition | مالك الخدمة | شروط الصحة |
| health_check_result | SH-14 | النتيجة والوقت والدليل |
| alert_rule | مالك الخدمة/مفوّض التشغيل؛ runtime عند SH-14 | يحتاج اعتماداً |
| alert_instance | SH-14 | state/transitions/evidence refs |
| incident_record | مالك incident process غير محسوم | SH-14 يحتفظ بالمرجع/الحالة التشغيلية وفق قرار |
| service_level_indicator | مالك الخدمة | definition/source |
| service_level_objective | مالك الخدمة والحوكمة | target/period/error budget TBD |
| dashboard_projection | C14 | قراءة فقط ومصدر وتاريخ |
| test_run/test_result/defect | C07 | لا ينقل إلى SH-14 |
| audit_event | SH-04 | SH-14 يرسل event/reference |

## 4. عقد القياس المقترح

حقول مرشحة: metricId, metricName, metricType, unit, value, observedAt, receivedAt, producerRef, serviceRef, componentRef, environment, region, scope, dimensions, sampleQuality, sourceVersion, correlationId, classification.

تحتاج الأنواع والقيود وcardinality limits وsampling/retention قرارات مستقلة؛ لا تضع بيانات شخصية أو أسراراً أو payloads حساسة ضمن labels.

عمليات مرشحة: registerMetricDefinition, ingestMetric, publishHealthCheck, queryServiceHealth, queryMetricSeries, evaluateAlertRule, acknowledgeAlert, silenceAlert, openIncidentReference, resolveAlert, getTelemetryFreshness.

أحداث مرشحة: MetricIngested, HealthStatusChanged, AlertTriggered, AlertAcknowledged, AlertResolved, TelemetryBecameStale, MonitoringPipelineDegraded.

## 5. قواعد الصحة والتنبيه

- يجب أن تكون الحالات Healthy / Degraded / Unhealthy / Unknown / Stale أو ما يعادلها بعد اعتماد taxonomy؛ لا تستنتج Healthy من غياب القياس.
- تفرق liveness عن readiness وعن availability؛ نجاح عملية health endpoint لا يثبت نجاح وظيفة المجال.
- كل alert يحمل rule version وsource metric refs وobserved window وscope وseverity وtimestamps.
- suppression/deduplication لا يخفي تدهوراً جديداً ولا يكرر الحوادث بلا داعٍ.
- لا يعتبر alert resolved إلا بعد تحقق شرط الإغلاق وفق مالك الحادث.
- فقد pipeline المراقبة يولد حالة Monitoring Unknown/Degraded لا حالة سليمة.
- لا تعتمد أرقام thresholds أو sampling intervals أو retention هنا.

## 6. اختبارات القبول

| Test ID | السيناريو | معيار النجاح | الحالة |
|---|---|---|---|
| MON-93-T01 | لا تصل عينات القياس | الحالة Unknown/Stale لا Healthy | SPECIFIED / NOT RUN |
| MON-93-T02 | عينة قديمة تصل متأخرة | freshness يوضح observedAt/receivedAt | SPECIFIED / NOT RUN |
| MON-93-T03 | عينة مكررة | dedupe حسب المفتاح المعتمد | SPECIFIED / NOT RUN |
| MON-93-T04 | producer غير مخول | رفض القياس وتسجيل آمن | SPECIFIED / NOT RUN |
| MON-93-T05 | تغيير rule بعد إنشاء alert | يحتفظ alert بإصدار القاعدة الأصلية | SPECIFIED / NOT RUN |
| MON-93-T06 | تكرار تنبيه لنفس الحالة | dedup/suppression وفق سياسة معلنة | BLOCKED: policy TBD |
| MON-93-T07 | اختبار C07 فشل | يبقى نتيجة جودة ولا يتحول تلقائياً إلى incident | SPECIFIED / NOT RUN |
| MON-93-T08 | C14 يعرض مؤشراً | يعرض source/asOf/staleness ولا يكتب المصدر | SPECIFIED / NOT RUN |
| MON-93-T09 | labels عالية cardinality أو حساسة | رفض/تنقيح وفق policy | SPECIFIED / NOT RUN |
| MON-93-T10 | تعطل ingestion pipeline | تظهر حالة مراقبة متدهورة/مجهولة | SPECIFIED / NOT RUN |
| MON-93-T11 | alert بلا دليل مصدر | لا يعتمد كتنبيه موثوق | SPECIFIED / NOT RUN |
| MON-93-T12 | إغلاق حادث بلا تحقق | يمنع الإغلاق أو يسجل الاستثناء المعتمد | BLOCKED: incident policy TBD |
| MON-93-T13 | استعلام عبر tenant | لا يعبر حدود النطاق | SPECIFIED / NOT RUN |
| MON-93-T14 | قياس SLO/RTO غير معتمد | لا يعرض كهدف معتمد | SPECIFIED / NOT RUN |

## 7. شروط الإغلاق

تعيين مالك SH-14 وملاك المؤشرات؛ اعتماد health taxonomy وSLI/SLO/alert/incident lifecycle؛ تحديد retention/sampling/cardinality/privacy؛ تحديد العلاقة مع SH-05/09 وC07 وC14؛ اعتماد مصدر كل قياس؛ ربط API/events/approvals باختبارات OWN36-03 وOWN72-06/08؛ ثم تنفيذ الاختبارات وإرفاق run IDs/evidence.

**لا تغلق هذه الوثيقة OWN36-03 أو OWN72-06/08 ولا تنشئ monitoring stack أو dashboards أو alert routes أو SLOs رقمية.**
