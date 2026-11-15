# 92 — حزمة قرارات ملكية SH-08 لتنفيذ المهام المشتركة

**الحالة:** DERIVED DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المراجع:** 23 و32 (SH-08)، 36، 63 (C06/C07)، 65 (C09)، 67 (C11)، 72 (OWN72-06)، 78–80، 82، 87، 89.  
**حد المصدر:** SH-08 واردة في قائمة الخدمات ومسؤوليتها المختصرة؛ تفاصيل التنفيذ هنا مشتقة وليست استعادة حرفية للفصل 157 المفقود.

## 1. المبدأ الفاصل: Domain Job مقابل Shared Execution Job

يجب عدم دمج نوعين مختلفين:

1. **Domain Job:** أمر عمل يعبّر عن غرض مجال محدد ونتيجته الدلالية، مثل مزامنة تكامل أو بناء تطبيق أو مهمة AI. يملكه المركز/المجال المنتج (C09/C11/C06 وغيرها)، ويحدد مدخلاته وقواعده ونجاحه التجاري.
2. **Shared Execution Job:** سجل تنفيذ عام ينسق الطابور والتعيين والمحاولات والجدولة والتقدم والإلغاء وإعادة المحاولة والنتيجة التشغيلية. هذه قدرة SH-08 المقترحة.
3. **نتيجة المجال:** تبقى عند مالك المجال؛ SH-08 يحتفظ بمراجع النتائج التشغيلية ولا يصبح مصدر حقيقة النتيجة.

يجب أن يكون لكل Domain Job معرف ثابت ومرجع إلى executionJobRef، مع فصل status المجال عن execution status.

## 2. قرارات مطلوبة

| ID | القرار | التوصية | الحالة |
|---|---|---|---|
| TASK-92-01 | مالك SH-08 وسجل التنفيذ | SH-08 يملك job/attempt/queue metadata المشتركة | OPEN |
| TASK-92-02 | تعريف Domain Job | يملكه المركز المنتج ولا تنقله SH-08 | OPEN |
| TASK-92-03 | نموذج job envelope | requestId/correlationId/actor/scope/contractVersion | OPEN |
| TASK-92-04 | idempotency/deduplication | مفتاح ثابت ضمن scope والعملية، مع سياسة احتفاظ TBD | OPEN |
| TASK-92-05 | retries/backoff | أخطاء عابرة فقط، وحدود لكل نوع مهمة تعتمد لاحقاً | OPEN |
| TASK-92-06 | cancellation semantics | يفرق بين طلب الإلغاء والتوقف المؤكد والآثار الجانبية | OPEN |
| TASK-92-07 | scheduling/priority/fairness | سياسات حسب tenant والموارد، دون أرقام غير معتمدة | OPEN |
| TASK-92-08 | dependencies and DAG | يتحقق من الدورات والاعتمادية والإصدار | OPEN |
| TASK-92-09 | leases/heartbeat/worker loss | كشف العامل المفقود واستعادة آمنة دون تشغيل مزدوج | OPEN |
| TASK-92-10 | approval/re-authorize | فحص الموافقة والتفويض عند بدء التنفيذ وكل حد حساس | OPEN |
| TASK-92-11 | output/error ownership | SH-08 يملك execution refs؛ المجال يملك النتائج الدلالية | OPEN |
| TASK-92-12 | logs/progress retention | تقليل البيانات الحساسة والاحتفاظ وفق سياسة معتمدة | OPEN |
| TASK-92-13 | tenant isolation and quotas | عزل صفوف التنفيذ والموارد والقياسات بين المؤسسات | OPEN |
| TASK-92-14 | job replay/recovery | إعادة تشغيل آمنة مع lineage وidempotency | OPEN |
| TASK-92-15 | shared vs domain monitoring | SH-08 يرسل telemetry؛ SH-14 يملك القياسات التشغيلية | OPEN |

## 3. حدود الملكية

| المورد | المالك المقترح | الحد |
|---|---|---|
| domain_job_definition | مركز المجال | المعنى والمدخلات وشروط النجاح |
| domain_job_result | مركز المجال | النتيجة المعتمدة وأثر المجال |
| execution_job | SH-08 | حالة التنفيذ العامة والتعيين |
| execution_attempt | SH-08 | محاولات التشغيل والمهل والخروج |
| queue/schedule metadata | SH-08 | التنسيق لا معنى المهمة |
| worker/runner registration | SH-08 أو منصة التشغيل | الهوية والصحة وفق قرار لاحق |
| dependency edge | SH-08 execution graph + domain refs | لا يملك تعريف المجال |
| progress snapshot | SH-08 | تقدم تشغيلي، لا حقيقة الأعمال |
| output reference | SH-08 metadata + owner storage | المحتوى عند مالكه |
| approval decision | SH-03 | SH-08 يتحقق ولا يصدر القرار |
| authorization policy/grant | SH-02/C10/domain owner | SH-08 يطبق إعادة التحقق |
| audit event | SH-04 | SH-08 يحتفظ بمرجع correlation فقط |
| operational telemetry | SH-14 | SH-08 مصدر producer لا مالك المؤشرات |

## 4. حالات التنفيذ المقترحة

Queued, Scheduled, Ready, Running, RetryScheduled, CancelRequested, Cancelled, Succeeded, Failed, TimedOut, Blocked, DeadLetter.

لا يجوز مساواة:
- CancelRequested مع Cancelled.
- worker finished مع domain operation committed.
- execution Succeeded مع domain result accepted.
- retry مع replay غير محدود.
- absence of heartbeat مع نجاح المهمة.

تحدد انتقالات الحالات والمهل وسياسة retries لكل فئة مهمة بقرار منفصل.

## 5. عقد التنفيذ المقترح

حقول envelope المنطقية: executionJobId, domainJobRef, producerCenter, taskType, taskVersion, actorRef, scope, requestId, correlationId, contractVersion, idempotencyKey, approvalRef, policyVersion, createdAt, deadlineRef, inputRef, outputRef.

لا ترسل payload حساساً كاملاً إلى queue أو logs إذا أمكن استخدام مرجع مصرح به. يعاد التحقق من صلاحية actor والـscope والموافقة عند التنفيذ، لا عند enqueue فقط.

عمليات مرشحة: submitExecution, getExecution, cancelExecution, retryExecution, replayExecution, getAttemptHistory, getProgress, registerWorker, heartbeatWorker, resolveDependency, inspectDeadLetter.

أحداث مرشحة: ExecutionQueued, ExecutionStarted, ExecutionProgressed, ExecutionRetryScheduled, ExecutionCancelRequested, ExecutionCancelled, ExecutionSucceeded, ExecutionFailed, ExecutionTimedOut, WorkerLost.

## 6. علاقات المراكز والخدمات

- C06 يملك AI task/plan/agent result؛ SH-08 ينفذ job refs ولا يقرر صلاحية الوكيل أو ميزانيته.
- C09 يملك integration definition/mapping/sync policy؛ SH-08 ينفذ sync attempts ولا يملك بيانات المزود أو سياسة التعارض.
- C11 يملك build/release/deployment lifecycle؛ SH-08 يشغل jobs ولا يعتمد نشر الإنتاج.
- C07 يملك test plan/results/defects؛ SH-08 يمكن أن يشغل الاختبار ولا يملك نتيجة الجودة الدلالية.
- SH-09 ينقل أحداث التنفيذ؛ SH-14 يرصد health؛ SH-04 يسجل audit؛ SH-03 يملك الموافقات.
- L9/L10 تنسقان الاستمرارية والتشغيل وفق قرارات لاحقة؛ لا تُنشأ خدمة مشتركة إضافية.

## 7. اختبارات القبول

| Test ID | السيناريو | معيار النجاح | الحالة |
|---|---|---|---|
| TASK-92-T01 | إعادة إرسال submit بنفس idempotency key | سجل تنفيذ واحد وأثر واحد | SPECIFIED / NOT RUN |
| TASK-92-T02 | سحب grant بعد enqueue | يرفض worker التنفيذ بعد recheck | SPECIFIED / NOT RUN |
| TASK-92-T03 | انتهاء lease/فقد worker | استعادة آمنة دون تشغيل متزامن غير مسموح | SPECIFIED / NOT RUN |
| TASK-92-T04 | طلب إلغاء أثناء التنفيذ | يميز requested عن confirmed ويعرض الآثار | SPECIFIED / NOT RUN |
| TASK-92-T05 | مهمة ذات dependency cycle | ترفض قبل التشغيل | SPECIFIED / NOT RUN |
| TASK-92-T06 | job تابع لمؤسسة أخرى | رفض وعزل كامل | SPECIFIED / NOT RUN |
| TASK-92-T07 | retry لخطأ غير عابر | لا يعاد تلقائياً خلاف السياسة | SPECIFIED / NOT RUN |
| TASK-92-T08 | job انتهى لكن domain commit فشل | لا يعلن نجاح المجال | SPECIFIED / NOT RUN |
| TASK-92-T09 | replay لمهمة كتابة | يطبق approval/version/idempotency | SPECIFIED / NOT RUN |
| TASK-92-T10 | تسرب secret إلى logs | يمنع أو ينقح قبل التخزين | SPECIFIED / NOT RUN |
| TASK-92-T11 | worker heartbeat مفقود | الحالة Unknown/Lost لا Healthy | SPECIFIED / NOT RUN |
| TASK-92-T12 | queue overload | fairness/backpressure وفق سياسة معلنة | BLOCKED: policy TBD |
| TASK-92-T13 | تقدم مهمة C11 أو C09 | progress لا يستبدل حالة المجال | SPECIFIED / NOT RUN |
| TASK-92-T14 | استعادة بعد انقطاع | lineage ومحاولات ونتيجة قابلة للتدقيق | SPECIFIED / NOT RUN |

## 8. شروط الإغلاق

اعتماد مالك SH-08؛ تعريف job envelope والحالات والانتقالات؛ تحديد semantics للإلغاء والـretry والـreplay؛ اعتماد leases/worker model/quotas/fairness؛ ربط كل مركز مستهلك بـDomain Job Contract؛ تحديد output/log retention؛ ربط API/events/approvals بـOWN72-06 وقرارات المجالات؛ ثم تنفيذ الاختبارات بأدلة.

**لا تغلق هذه الوثيقة OWN72-06 أو OWN36-08/09 ولا تنشئ queue أو worker أو scheduler أو API أو migration.**
