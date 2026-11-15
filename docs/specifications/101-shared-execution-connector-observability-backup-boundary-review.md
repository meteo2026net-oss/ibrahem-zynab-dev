# 101 — مراجعة حدود خدمات التنفيذ والتكامل والمراقبة والنسخ الاحتياطي

**الحالة:** DERIVED BOUNDARY REVIEW / OWNER DECISIONS REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المراجع المباشرة:** 72، 95، 96، 100.  
**الغرض:** تحويل حدود SH-08 وSH-13 وSH-14 وSH-15 إلى قواعد فصل قابلة للمراجعة، مع إبقاء حقيقة المجال لدى المركز المنتج.  
**حدود المصدر:** هذه المراجعة مشتقة من سجلات الملكية والأحداث الحالية؛ لا تستعيد نصاً مفقوداً من المصدر الرئيسي، ولا تعتمد أسماء عمليات أو أحداث جديدة، ولا تنشئ APIs أو مخططات أو خدمات.

## 1. قاعدة الفصل الحاكمة

يجب الفصل بين ثلاثة أنواع من السجلات:
1. **Domain record:** حقيقة العمل ومعنى انتقال المورد، ومالكها مركز المجال.
2. **Shared-service execution record:** محاولة تشغيل تقنية أو مهمة أفقية، ومالكها الخدمة المشتركة المختصة.
3. **Operational telemetry:** القياسات والتنبيهات وحالة صحة الخدمة، ومالكها SH-14.

لا يجوز أن يحل نجاح مهمة تقنية محل نجاح مجال، ولا أن يتحول حدث صحة تشغيلية إلى نتيجة قبول أو قرار مجال. يحتفظ كل سجل بمرجع correlation/causation إلى السجلات المرتبطة دون نسخ ملكيتها.

## 2. مصفوفة الحدود

| القدرة | Domain SoR المقترح | Shared Service SoR المقترح | ما لا يجوز للخدمة المشتركة امتلاكه | حالة القرار |
|---|---|---|---|---|
| تنفيذ مهام المجال والـworkers (SH-08) | المركز الطالب: C06 لمهمة AI، C07 لخطة/نتيجة الاختبار، C11 لدورة البناء/النشر، وسائر مراكز المجال لمواردها | SH-08 لمحاولة التنفيذ، العامل، الجدولة، retry/cancel، execution status، logs/artifacts التشغيلية المرجعية | لا يملك SH-08 معنى AITask أو QA result أو Build/Release/Deployment result | توصية؛ اعتماد المالكين مطلوب |
| تشغيل الموصلات (SH-13) | C09 لتعريف التكامل وغرضه وخرائط المجال واتجاه المزامنة وقواعدها | SH-13 لتعريف/تشغيل connector runtime والاتصال والصحة ومرجع secret | لا يملك SH-13 تعريف التكامل الدلالي أو mapping أو قرار قبول بيانات المجال | توصية؛ اعتماد C09/SH-13 والأمن مطلوب |
| المراقبة (SH-14) | كل مركز يملك نتائج مجاله: QA، build، release، integration، data quality | SH-14 للقياسات، traces، health checks، alerts، incident telemetry | لا يملك SH-14 نتيجة اختبار C07 أو نجاح إصدار C11 أو صحة بيانات C04 | توصية؛ اعتماد المالكين مطلوب |
| النسخ والاستعادة (SH-15) | المركز يملك المورد المصدر وقرار صلاحية الاستعادة ونتيجة التحقق الدلالي | SH-15 لكتالوج النسخ ومحاولات backup/restore والأدلة التشغيلية | لا يملك SH-15 schema أو بيانات المجال ولا يعلن سلامة مجال لمجرد نجاح restore job | توصية؛ اعتماد المالكين مطلوب |
| النقل والأحداث (SH-09) | منتج المجال يملك الحدث الدلالي ومحتواه | SH-09 يملك النقل والتسليم والتكرار التشغيلي وdead-letter/replay metadata | لا يملك SH-09 payload semantics أو قرار المجال | توصية من 72/96؛ اعتماد المالكين مطلوب |

## 3. حالات فصل إلزامية للمراجعة

### 3.1 C06 وSH-08 — مهمة AI مقابل محاولة تنفيذ
- C06 يملك تعريف الوكيل، سياق المشروع، المهمة الدلالية، ومراجعة المخرجات.
- SH-08 يملك execution attempt وworker assignment وretry/cancel ونتيجة تشغيل الأداة.
- لا يعني اكتمال execution attempt أن مخرجات الوكيل مقبولة؛ يبقى review/acceptance في C06.
- يجب أن تحمل محاولة التنفيذ مرجعاً إلى المهمة الدلالية دون أن تنشئ نسخة موازية قابلة للكتابة منها.

### 3.2 C07 وSH-08 وSH-14 — الاختبار مقابل التنفيذ والصحة التشغيلية
- C07 يملك QA plan وtest run/result/defect/quality gate.
- SH-08 ينفذ test job ويحتفظ بمحاولة التشغيل وسجلاتها.
- SH-14 يقيس صحة runner والمنصة والموارد التشغيلية.
- لا يجوز تحويل runner healthy إلى test passed، ولا تحويل job succeeded إلى QA approved.

### 3.3 C11 وSH-08 وSH-14 وSH-15 — دورة الإصدار
- C11 يملك build/release/deployment/rollback semantics وقرار الترويج.
- SH-08 ينفذ build/deploy/rollback attempts بناءً على أوامر مصرح بها.
- SH-14 يملك telemetry وhealth/alert evidence بعد النشر.
- SH-15 يملك backup/restore job catalog/evidence، بينما C11 يقرر صلاحية استعادة إصدار/بيئة ويثبت النتيجة.
- Artifact record يبقى ضمن قرار C11 المقترح في 95؛ binary storage/signing authority لا يزالان OPEN.

### 3.4 C09 وSH-13 — تعريف التكامل مقابل runtime الموصل
- C09 يملك integration intent وmapping وsync direction/domain rules.
- SH-13 يملك connector runtime/connection lifecycle/health/secret references حسب اعتماد OWN-02.
- نجاح الاتصال لا يعني نجاح مزامنة المجال؛ ونجاح نقل payload لا يعني قبولها من قواعد المجال.
- secret reference لا يمنح SH-13 ملكية مخزن الأسرار الفعلي.

### 3.5 C04/C11 وSH-15 — النسخ مقابل صلاحية الاستعادة
- SH-15 يثبت ما إذا كانت مهمة النسخ/الاستعادة قد نُفذت تقنياً ويحتفظ بأدلتها.
- C04 يملك schema/migration ونتيجة تحقق البيانات بعد الاستعادة.
- C11 يملك نتيجة تحقق artifact/release/deployment بعد الاستعادة.
- لا يجوز إصدار RestoreVerificationCompleted من SH-15 نيابة عن C04 أو C11؛ يجب أن يصدر الحدث الدلالي من مالك المجال بعد التحقق.

## 4. ثوابت العقد التي يجب اعتمادها

1. لكل execution record مرجع domainJobRef أو resourceRef عند وجود مهمة مجال؛ ولا يستخدم المرجع بديلاً عن سجل المجال.
2. لكل attempt هوية مستقلة عن domain job، مع attempt number/state/timestamps/worker ref وفق schema يعتمد لاحقاً.
3. عمليات retry/cancel/replay يجب أن تكون idempotent أو ذات semantics معلنة، مع حماية من تكرار side effects.
4. كل command إلى shared service يتحقق خادمياً من tenant/workspace/project scope ومن actor/service grant قبل التنفيذ.
5. لا يسمح للـworker بتوسيع صلاحياته أو استدعاء خدمة أخرى خارج grant محدد النطاق والمدة والغرض.
6. نتيجة المجال لا تُعلن إلا بعد تحقق مالك المجال من أثر المعاملة/النسخة المتوقعة.
7. telemetry وlogs لا تحتوي أسراراً أو payloads حساسة غير لازمة؛ تخضع للتصنيف والاحتفاظ والحذف.
8. backup evidence يثبت النسخة والمصدر والتوقيت والتكامل؛ أما semantic validation فيصدر عن مالك المورد.
9. لا يُسمح لـC14 بإظهار حالة نجاح موحدة دون sourceRef وasOf/freshness وACL revalidation.
10. أي أحداث مقترحة في 100 تبقى غير منشورة حتى اعتماد المنتج والمالك والمخطط والاختبارات.

## 5. خريطة أدلة الأحداث الحالية

| المجال | Event IDs من 100 | معنى الحدث الذي يبقى لدى المجال | سجل الخدمة المشتركة الذي يجب فصله |
|---|---|---|---|
| C06 AI | EVT-C06-003..009 | إنشاء/إسناد/تغير حالة المهمة ومراجعة المخرج والتعارض | SH-08 execution attempts؛ SH-09 delivery |
| C07 QA | EVT-C07-001..006 | خطة الاختبار ونتيجته والعيب وquality gate والاعتماد | SH-08 test job؛ SH-14 runner telemetry |
| C09 Integration | EVT-C09-001..010 | تعريف التكامل وقواعده ونتيجة المزامنة الدلالية | SH-13 connector runtime؛ SH-09 transport |
| C11 Release | EVT-C11-001..014 | build/release/deployment/rollback outcomes | SH-08 execution؛ SH-14 telemetry؛ SH-15 backup evidence |
| C04 Data | EVT-C04-001..010 | schema/migration/sync conflict ونتيجة تحقق الاستعادة | SH-08 migration job؛ SH-15 backup/restore execution |
| C14 Dashboard | EVT-C14-001 | تحديث projection للقراءة فقط | SH-14 freshness/health evidence؛ مصادر المؤشرات تبقى مالكة |

**تنبيه:** معرفات الأحداث أعلاه مستعادة/مقترحة حسب حالة كل صف في 100؛ الجدول لا يرفعها إلى حالة معتمدة ولا يحدد schema أو producer runtime نهائياً.

## 6. قرارات مفتوحة مطلوبة

| القرار | الأطراف التي يلزم إشراكها | المطلوب قبل الإغلاق |
|---|---|---|
| SH08-BOUNDARY-01 | ملاك C06/C07/C11 وكل مركز ينشئ jobs + مالك SH-08 | اعتماد domainJob مقابل executionAttempt وعمليات retry/cancel |
| SH13-BOUNDARY-01 | مالك C09 + مالك SH-13 + C10 | اعتماد connector manifest/connection/secret-reference boundary |
| SH14-BOUNDARY-01 | ملاك C07/C11/C04 + مالك SH-14 | اعتماد فصل domain outcomes عن telemetry/alerts |
| SH15-BOUNDARY-01 | ملاك C04/C11/C08 + مالك SH-15 + C10 | اعتماد backup catalog، binary ownership، semantic restore verification، retention |
| SH09-BOUNDARY-01 | منتجو الأحداث + مالك SH-09 + C10 | اعتماد delivery, ordering, retry, DLQ, replay, ACL |
| SHARED-BOUNDARY-01 | جميع المراكز + C10 | اعتماد tenant isolation وaudit/provenance لكل side effect |

هذه IDs محلية لحزمة المراجعة وليست بدائل عن OWN36/OWN72 المؤهلة، ولا تعتبر قرارات ملكية معتمدة.

## 7. اختبارات قبول مقترحة لهذه الحدود

| Test ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| SBR-101-T01 | SH-08 attempt succeeds but domain validation fails | تبقى نتيجة المجال غير ناجحة/غير معتمدة | SPECIFIED / NOT RUN |
| SBR-101-T02 | retry delivers duplicate completion | لا يتكرر side effect الدلالي | SPECIFIED / NOT RUN |
| SBR-101-T03 | cross-tenant execution reference | رفض التنفيذ دون كشف بيانات الجهة الأخرى | SPECIFIED / NOT RUN |
| SBR-101-T04 | connector connected but mapping rejects record | الاتصال ناجح والمزامنة الدلالية مرفوضة بوضوح | SPECIFIED / NOT RUN |
| SBR-101-T05 | runner healthy while test fails | SH-14 health لا يغير نتيجة C07 | SPECIFIED / NOT RUN |
| SBR-101-T06 | deployment job succeeded but post-deploy check fails | C11 يسجل deployment outcome/check failure وفق المجال | SPECIFIED / NOT RUN |
| SBR-101-T07 | backup job succeeded but restore semantic validation fails | SH-15 job success لا يعلن restore verified | SPECIFIED / NOT RUN |
| SBR-101-T08 | dashboard projection stale or ACL revoked | تعرض freshness/source أو تحجب البيانات عند سحب الصلاحية | SPECIFIED / NOT RUN |
| SBR-101-T09 | worker attempts ungranted tool call | رفض الطلب وتسجيل أثر تدقيق غير قابل للتلاعب | SPECIFIED / NOT RUN |
| SBR-101-T10 | replay after policy/ACL change | يعاد التحقق من الصلاحية والسياسة قبل أي side effect | SPECIFIED / NOT RUN |

هذه اختبارات مواصفات مقترحة، لم تُنفذ ولم تحصل على Exact Test IDs من كتالوج 80؛ لا تربطها الوثيقة بعائلات اختبارات على أنها تغطية معتمدة.

## 8. شروط الإغلاق

- اعتماد OWN-02 وOWN-06 وOWN-07 وOWN-08 وOWN-09 وOWN-10 في سجل الملكية المؤهل.
- مراجعة التوصيات مع ملاك SH-08/09/13/14/15 وتسجيل قرار وموافق ودليل لكل حد.
- إضافة source locator أصلي لكل متطلب ذي صلة؛ مراجع العقود الوسيطة لا تكفي.
- ربط كل عملية/حدث باختبار قبول مفرد معتمد أو N/A مبرر.
- اعتماد schemas وscope/ACL/retention/retry/replay semantics قبل أي ERD/API implementation.
- اختبار فصل domain outcome عن execution status وtelemetry وbackup evidence في بيئة تكامل لاحقة.

## 9. أثر الوثيقة

تضيف هذه الوثيقة مراجعة حدود مشتقة فقط. لا تعدل المصدر الرئيسي أو الوثيقة 36، ولا تغلق OWN/FLD/RI، ولا تنشئ API أو event topic أو قاعدة بيانات أو خدمة تشغيلية. تظل جميع القرارات أعلاه مفتوحة لحين اعتماد أصحابها.
