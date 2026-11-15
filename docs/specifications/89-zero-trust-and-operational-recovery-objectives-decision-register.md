# 89 — سجل حسم Zero Trust وأهداف التعافي والتشغيل

**الحالة:** CONFLICT REGISTER + OPERATIONAL TARGETS DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** 56 و23 و32 و36 و67 و78 و80 و84 و87، والمقاطع 158.7 و158.9 و160 كما سجلت في الوثيقة 56.

## 1. Zero Trust — لا حسم بلا دليل موافقة

التعارض المثبت:
- 158.7 يصوغ Zero Trust كمتطلب معماري إلزامي.
- 158.9 يدرج SEC-REV-01 ضمن القرارات المقترحة.
- 160 يعرض DEC-SEC-010 بحالة مقترح.
- الوثيقة 56 تقرر أن سجل الموافقات المتاح لا يقدم دليلاً مستقلاً كافياً لترقية القرار.

لذلك يبقى SEC-REV-01 مفتوحاً. هذه الوثيقة لا تعلن اعتماد Zero Trust ولا تلغيه.

| ID | القرار | المالك/المراجع | الحالة |
|---|---|---|---|
| ZT-89-01 | استعادة سجل الموافقة/القرار الأصلي إن وجد | صاحب القرار + C10 | OPEN |
| ZT-89-02 | تحديد ما إذا كان Zero Trust قراراً معمارياً ملزماً أم توصية | صاحب القرار + معماري النظام + C10 | OPEN |
| ZT-89-03 | تحديد نطاق التطبيق والحدود والاستثناءات | C10 + جميع ملاك الخدمات | OPEN |
| ZT-89-04 | اعتماد threat model وcontrol objectives | C10 + C07 | OPEN |
| ZT-89-05 | اعتماد migration/rollout plan واختبارات الرجوع | C10 + C11 | OPEN |
| ZT-89-06 | اعتماد قياس الامتثال والأدلة والمراجعة الدورية | C10 + SH-14/04 | OPEN |

## 2. مبادئ تشغيل لا تساوي أهدافاً رقمية

المصادر تدعم الحاجة إلى المراقبة والاستمرارية والنسخ والاستعادة والبدائل، لكنها لا تقدم قيماً رقمية معتمدة لـRTO/RPO/SLO أو freshness. يجب فصل:
- SLI: قياس فعلي بمصدر ووحدة ونافذة.
- SLO: هدف خدمة معتمد.
- SLA: التزام خارجي إن وجد.
- RTO: أقصى زمن استعادة معتمد لمسار/خدمة.
- RPO: أقصى فقد بيانات مقبول معتمد.
- Freshness: حد تأخر مقبول للبيانات أو projection.
- Recovery test cadence: تواتر اختبار الاستعادة.

## 3. سجل الأهداف المطلوبة

| ID | النطاق | القياس/القرار | الحالة |
|---|---|---|---|
| OPS-89-01 | كل خدمة حرجة | service availability SLI/SLO | OPEN / numeric TBD |
| OPS-89-02 | كل API critical path | latency/error-rate SLI/SLO | OPEN / numeric TBD |
| OPS-89-03 | C14 projections | freshness target per data source | OPEN / numeric TBD |
| OPS-89-04 | SH-15/database | RPO per data class/service | OPEN / numeric TBD |
| OPS-89-05 | SH-15/service recovery | RTO per business capability | OPEN / numeric TBD |
| OPS-89-06 | Backup/restore | verification and drill cadence | OPEN |
| OPS-89-07 | SH-14 telemetry | sampling, freshness, retention | OPEN |
| OPS-89-08 | Incident response | severity, ownership, escalation and closure | OPEN |
| OPS-89-09 | Degraded mode | permitted fallback and data-loss semantics | OPEN |
| OPS-89-10 | Failover | dependency order, fencing, split-brain protection | OPEN |
| OPS-89-11 | Recovery evidence | required test/run/build/actor/time artifacts | OPEN |
| OPS-89-12 | Budget/capacity | cost and resource constraints for objectives | OPEN |

## 4. منهج تحديد القيم — لا أرقام مفترضة

قبل اعتماد أي رقم:
1. تحديد business capability والعمليات التي تتأثر.
2. تصنيف البيانات والحد الأقصى المقبول لفقدها.
3. رسم dependency graph ومكونات الاستعادة.
4. تحديد إمكانات المزود والتخزين والنسخ والمناطق.
5. قياس الأداء والتعافي الفعلي في بيئة اختبار.
6. مقارنة الكلفة والسعة والقيود.
7. مراجعة المالكين واعتماد الهدف لكل خدمة/فئة.
8. تحويل الهدف إلى alert/test oracle قابل للقياس.
9. تنفيذ drills وتسجيل evidence ومراجعة الانحراف.
10. تحديث الأهداف بعد تغيير المعمارية أو التبعيات.

لا يصح اشتقاق RTO/RPO من عبارة «عالية التوافر» أو من مدة افتراضية عامة.

## 5. اختبارات القبول

| Test ID | الحالة | النتيجة | الوضع |
|---|---|---|---|
| OPS-89-T01 | لا توجد telemetry حديثة | Unknown/Stale لا Healthy | SPECIFIED / NOT RUN |
| OPS-89-T02 | SLO breach | incident/alert حسب policy مع source | BLOCKED: SLO TBD |
| OPS-89-T03 | C14 يعرض projection متأخرة | يظهر timestamp/freshness بوضوح | SPECIFIED / NOT RUN |
| OPS-89-T04 | recovery drill | يقاس RTO/RPO الفعليان ويقارنان بالهدف المعتمد | BLOCKED: targets TBD |
| OPS-89-T05 | failover مع dependency مفقودة | لا إعلان recovery complete | SPECIFIED / NOT RUN |
| OPS-89-T06 | split-brain risk | fencing/consistency guard يمنع كتابة متعارضة | SPECIFIED / NOT RUN |
| OPS-89-T07 | Zero Trust status | لا يُعرض كقرار معتمد دون approval evidence | SPECIFIED / NOT RUN |
| OPS-89-T08 | تغيير SLO/RTO/RPO | versioned approval and audit record | SPECIFIED / NOT RUN |

## 6. بوابة الإغلاق

- استعادة/توثيق موافقة Zero Trust أو إبقاؤه مقترحاً بقرار صريح.
- اعتماد نطاق الضوابط والاستثناءات.
- إجراء business impact analysis لكل قدرة حرجة.
- اعتماد RTO/RPO/SLO/freshness لكل نطاق.
- تنفيذ اختبارات الاستعادة والمراقبة وتقديم evidence.
- ربط النتائج بـSH-14 وSH-15 وC11 وC14 والوثيقة 80.

**لا تحسم هذه الوثيقة Zero Trust، ولا تحدد أرقام RTO/RPO/SLO، ولا تدعي اختبارات تشغيل منفذة.**
