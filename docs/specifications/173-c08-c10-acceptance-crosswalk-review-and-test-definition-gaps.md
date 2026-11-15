# 173 — مراجعة روابط القبول C08–C10 وفجوات تعريف الاختبار

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC CANDIDATE REVIEW / TEST DESIGN INCOMPLETE / NOT APPROVED / NOT RUN
**المصادر المباشرة:** 64–66، 126، 127، 149؛ مع الرجوع إلى 97 و98 و100 و164 و171 و172.

## 1. النطاق والنتيجة

تراجع هذه الوثيقة 34 عملية C08–C10 التي لها روابط سيناريو مرشحة في الوثيقة 149. وهي لا تعيد اعتماد الروابط؛ بل تختبر كفاية السيناريو المرتبط لتغطية العملية، وتحدد عناصر الاختبار الناقصة. سجل 149 نفسه يصف الروابط بأنها مرشحة وليست exact approved links.

| المركز | العمليات | سيناريوهات workpaper | معرفات مسودة العقد | نتيجة المراجعة |
|---|---:|---:|---:|---|
| C08 | 11 | 10 | 10 | مرشحات موجودة؛ aliases غير محسومة |
| C09 | 12 | 10 | 10 | مرشحات موجودة؛ aliases غير محسومة |
| C10 | 11 | 18 | 12 | مرشحات موجودة؛ اختلاف العدد والمعرفات يحتاج تسوية |
| **الإجمالي** | **34** | **38** | **32** | **لا روابط exact معتمدة** |

ملاحظة: 38 هو مجموع السيناريوهات المحلية في workpapers 126 و127 (10+10+18)، وليس مجموعاً جديداً يضاف إلى 124. معرفات مسودات العقود 64–66 وعددها 32 لا تثبت أنها نفس حالات workpapers.

## 2. Crosswalk تشغيلي مفصل — مرشح فقط

| Operation ID | العملية | السيناريوهات المرشحة في 149 | اختبار التغطية / فجوة التعريف |
|---|---|---|---|
| OP-C08-121 | createAssetRecord | C08-126-T01, T04 | رفض حجم/نطاق غير مخول؛ يلزم تحديد الحقول الإلزامية وحالة الإنشاء الذري |
| OP-C08-122 | requestUpload | C08-126-T01, T02, T03 | حد الحجم والحجر وفحص النوع؛ يلزم تعريف حدود الرفع الفعلية وبيئة التخزين |
| OP-C08-123 | finalizeUpload | C08-126-T02, T03, T09, T10 | حالة الفحص والإعادة idempotency وإيصال التخزين؛ يلزم نموذج receipt وحالات retry |
| OP-C08-124 | inspectAsset | C08-126-T02, T05, T07 | حجر وACL وتسريب metadata؛ يلزم تعريف response contract وحقول التنقيح |
| OP-C08-125 | createAssetVersion | C08-126-T06, T09 | نسب المصدر والإصدار ومنع تكرار الأثر؛ يلزم قواعد version/hash |
| OP-C08-126 | linkAsset | C08-126-T04, T05 | تفويض الطرفين وعزل tenant؛ يلزم fixture لموردين بنطاقين مختلفين |
| OP-C08-127 | requestTransformation | C08-126-T06, T07, T10 | قائمة العمليات ومصدر المشتق وحالة SH-06؛ يلزم تعريف transformation allowlist |
| OP-C08-128 | revokeAssetLink | C08-126-T05 | إبطال cache والوصول اللاحق؛ يلزم تحديد ضمانات invalidation |
| OP-C08-129 | archiveAsset | C08-126-T08 | سيناريو retention/legal hold محجوب؛ لا يمكن PASS قبل اعتماد السياسة |
| OP-C08-130 | requestExport | C08-126-T05, T07, T08 | scope/recipient/purpose/approval؛ يلزم hash للحزمة وتفاصيل التنقيح |
| OP-C08-131 | requestDeletion | C08-126-T08 | طلب الحذف لا يساوي التنفيذ؛ يلزم قرار مالك retention وإثبات تنفيذ SH-06 |
| OP-C09-132 | registerIntegration | C09-126-T01, T09 | allowlist وعزل النطاق؛ يلزم قائمة مزودين معتمدة وبيئة اختبار |
| OP-C09-133 | configureIntegration | C09-126-T03, T09, T10 | عدم كشف الأسرار وسلامة mapping؛ يلزم schema/versioned fixtures |
| OP-C09-134 | beginAuthorization | C09-126-T02, T03 | state/redirect/secret refs؛ يلزم تعريف OAuth flow المحدد وcallback fixture |
| OP-C09-135 | completeAuthorization | C09-126-T02, T03, T09 | التحقق من callback ومنع replay؛ يلزم مزود sandbox وسيناريو انتهاء/إعادة |
| OP-C09-136 | validateConnection | C09-126-T03, T09 | عدم كشف credentials والعزل؛ يلزم تعريف health freshness oracle |
| OP-C09-137 | testMapping | C09-126-T07, T09 | حفظ SoR والتحويل الحتمي؛ يلزم sample dataset وexpected transformed output |
| OP-C09-138 | startSync | C09-126-T06, T07, T08, T10 | موافقة الكتابة الخارجية وSoR/idempotency؛ يلزم target sandbox وapproval binding |
| OP-C09-139 | receiveWebhook | C09-126-T04, T05, T10 | التوقيع والوقت وreplay؛ يلزم fixtures موقعة وساعة اختبار منضبطة |
| OP-C09-140 | pauseIntegration | C09-126-T08, T09 | إيقاف المجدول والجاري؛ يلزم تعريف in-flight semantics |
| OP-C09-141 | revokeConnection | C09-126-T03, T08, T09 | إبطال credential وإيقاف الأعمال؛ يلزم دعم المزود وسجل الإبطال |
| OP-C09-142 | rotateSecretReference | C09-126-T03, T09 | عدم كشف السر والانتقال الذري؛ يلزم تعريف overlap/rollback |
| OP-C09-143 | getIntegrationStatus | C09-126-T03, T09 | ACL وحداثة الحالة؛ يلزم freshness threshold وstatus vocabulary |
| OP-C10-144 | evaluatePolicy | C10-127-T01, T02, T03 | fail-closed والنطاق؛ يلزم policy fixtures وقرار SH-02 موثق |
| OP-C10-145 | registerSecurityFinding | C10-127-T08, T09 | مصدر finding واستقلال الدليل؛ يلزم fixtures findings ودليل provenance |
| OP-C10-146 | createThreatModel | C10-127-T10 | وسم النموذج stale بعد تغير الأصل؛ يلزم change trigger/version fixture |
| OP-C10-147 | assessPrivacyImpact | C10-127-T11, T12 | الغرض والفئات والاحتفاظ؛ يلزم مالك بيانات ومراجع خصوصية ومرجع أساس قانوني |
| OP-C10-148 | requestPolicyChange | C10-127-T04, T05 | منع تطبيق التغيير دون قرار وربط hash/version؛ يلزم SH-03 workflow محدد |
| OP-C10-149 | decideSecurityException | C10-127-T04, T06, T07 | موافقة مستقلة وانتهاء/سحب؛ يلزم فصل مهام ومصفوفة أدوار معتمدة |
| OP-C10-150 | suspendAgentCapability | C10-127-T13, T14 | منع المهام الجديدة ومعالجة الجاري؛ يلزم lifecycle contract للمهام |
| OP-C10-151 | reviewAgentRun | C10-127-T14, T15 | provenance ولقطة grant/policy؛ يلزم run evidence schema ومراجع مستقل |
| OP-C10-152 | recordIncident | C10-127-T16 | وصول محدود وسجل أدلة؛ يلزم incident classification وretention owner |
| OP-C10-153 | closeFinding | C10-127-T08, T09 | تحقق مستقل وإعادة فتح؛ يلزم reviewer role ومعيار remediation |
| OP-C10-154 | exportAuditEvidence | C10-127-T17, T18 | scope/recipient/redaction وعدم تعديل audit؛ يلزم export schema وموافقة SH-03 حسب السياسة |

## 3. فحص اكتمال تعريف حالات الاختبار

تحتوي workpapers على شروط تحقق ونتائج قبول مختصرة (Oracle) لبعض السيناريوهات، لكنها لا تقدم لكل حالة مواصفة تنفيذ اختبار كاملة. لا تتوفر في المصادر المراجعة مصفوفة موحدة مثبتة تشمل جميع العناصر التالية لكل حالة:

| عنصر تعريف الاختبار | الوضع الحالي | ما يلزم قبل اعتماد الاختبار |
|---|---|---|
| Test owner / independent reviewer | غير محدد على مستوى كل حالة | تسمية مالك اختبار ومراجع مستقل عند الحاجة واعتماد المالك |
| Preconditions / setup | جزئي وموزع بين حدود العمليات | بيانات تهيئة ثابتة وحالات actor/tenant/role موثقة |
| Steps | غالباً وصف شرط أو تهديد لا خطوات تنفيذ | خطوات قابلة للتكرار مع مدخلات وحدود واضحة |
| Test data / fixtures | غير مكتملة | بيانات اصطناعية منقحة ومراجع fixture وإصداراتها |
| Expected result / oracle | موجود مختصراً في بعض السيناريوهات؛ ليس مواصفة كاملة لكل اختبار | assertions قابلة للقياس، response/state/event/audit assertions |
| Cleanup / rollback | غير موحد | إزالة fixtures وإبطال الرموز وإرجاع الحالة بأمان |
| Evidence | إشارات إلى audit refs أو أثر متوقع فقط | evidence schema، correlation IDs، سجلات منقحة، artifact refs |
| Environment | غير محدد بما يكفي | بيئة اختبار معزولة، مزود sandbox، نسخ الخدمات وإعداداتها |
| Execution result | NOT RUN؛ بعض الحالات BLOCKED | run ID وcommit/build ووقت التنفيذ وPASS/FAIL/BLOCKED |

## 4. ملاحظات تغطية جوهرية حسب المركز

### C08 — الملفات والوسائط
- السيناريو C08-126-T08 يجمع الأرشفة والحذف وretention/legal hold؛ ويظل BLOCKED لأن سياسة الاحتفاظ والحجز القانوني غير محسومة.
- T02 وT03 يحتاجان تعريفاً معتمداً لحالة quarantine ونتيجة فحص النوع الفعلي؛ لا يجوز اختراع limits أو scan provider.
- T06 يحتاج عقد provenance/hash وإصدار مشتق؛ لا يكفي وصف «لا يعدل الأصل» دون assertions على lineage.
- لا يوجد اختبار منفصل مكتمل يثبت تنفيذ الحذف التقني بعد موافقة مستقلة، ولا يثبت مسح النسخ المشتقة والنسخ الاحتياطية.

### C09 — التكاملات
- T01 يحتاج قائمة allowlist ونطاقات egress معتمدة وبيئة اختبار؛ لا تُستنتج عناوين أو سياسات شبكة.
- T02 يحتاج اختيار OAuth flow فعلياً وتعريف state/nonce/PKCE/redirect بحسب المزود.
- T04/T05 يحتاجان webhook fixtures موقعة وتوقيتاً مضبوطاً وبيانات replay قابلة للتكرار.
- T06 يحتاج موافقة مرتبطة ببصمة config/mapping والوجهة، مع اختبار يمنع الكتابة الخارجية قبلها.
- T07 يحتاج dataset وexpected transformed output لتأكيد أن تعارض المزامنة لا يغير Domain SoR بصمت.
- T08 يحتاج تعريف سلوك الأعمال الجارية عند pause/revoke، لا مجرد إيقاف المجدول.

### C10 — الأمن والحوكمة
- T01–T03 يحتاجون fixtures قرار SH-02 موثقة تثبت fail-closed وtenant/resource scoping.
- T04/T05/T06/T07 تحتاج تعريف فصل الواجبات، مالك القرار، ربط القرار بنسخة/بصمة، والانتهاء والسحب.
- T11/T12 لا يمكن اعتماد privacy assessment دون مالك بيانات ومدخلات قانونية/خصوصية من الجهة المخولة؛ لا يستنتج النظام الأساس القانوني.
- T13 يحتاج lifecycle واضح للمهام الجارية بعد تعليق قدرة الوكيل.
- T15 يحتاج schema لأدلة تشغيل الوكيل ولقطة grants/policy ومراجع مستقل.

## 5. الربط الدقيق مع سجلات المصدر

- لا يثبت 149 سوى candidate mapping؛ لذلك يظل Operation→Test exact approved = **0/34** ضمن هذا النطاق.
- سجلات الأحداث المقابلة ما زالت مرشحة؛ exact Event→Test approved = **0/27** في C08–C10 وفق تعداد 11+10+6 أحداث في 126/127.
- أنواع الموافقات الأربعة في C08/C09 واثنان في C10 ما زالت OWNER REVIEW؛ لا يوجد اختبار موافقة نهائي exact approved لهذه الحالات.
- لا تُنشأ aliases بين C08-DRAFT-AT-nn وC08-126-Tnn أو C09-DRAFT-AT-nn وC09-126-Tnn أو C10-DRAFT-AT-nn وC10-127-Tnn في هذه الوثيقة.

## 6. أعمال الإغلاق المطلوبة

| الإجراء | المطلوب | معيار الإغلاق |
|---|---|---|
| X-173-01 | اعتماد فضاء معرفات canonical/aliases لكل مركز | قرار مالك موثق مع حفظ معرفات المصدر |
| X-173-02 | استكمال تعريف 38 سيناريو workpaper | owner/setup/steps/data/oracle/cleanup/evidence/environment |
| X-173-03 | حسم C08 retention/legal hold والحذف | قرار سياسة ومالك retention واختبار تنفيذ بعد الموافقة |
| X-173-04 | حسم C09 OAuth/webhook/sync sandbox | flow/provider/fixtures/approval binding معتمدة |
| X-173-05 | حسم C10 Zero Trust/SoD/SH-02/SH-03 | قرارات الأمن والحوكمة والمالك المستقل |
| X-173-06 | اعتماد 34 operation-test links | موافقة مالك المركز ومراجع QA على كل صف؛ لا semantic inference |
| X-173-07 | اعتماد event-test links | exact event contract + assertions + owner approval |
| X-173-08 | تنفيذ الاختبارات | run evidence مرتبط بـcommit/build والبيئة والنتائج |

## 7. الحالة النهائية لهذه الجولة

| القياس | النتيجة |
|---|---:|
| عمليات تمت مراجعتها | 34/34 |
| روابط مرشحة واردة من 149 | 34/34 |
| روابط exact approved جديدة | 0 |
| سيناريوهات workpaper التي جرى تحليل نطاقها | 38 |
| سيناريوهات منفذة في هذه الجولة | 0 |
| قرارات مالك أو موافقات جديدة | 0 |
| تغييرات كود/API/schema/database | 0 |

**الحكم:** أصبحت فجوات تعريف الاختبار في C08–C10 محددة لكل عملية، لكن لا يمكن وصف الاختبارات بأنها مكتملة أو معتمدة أو منفذة. وتبقى هذه الأعمال توثيقية ضمن مرحلة استكمال المواصفات؛ لا تمنح إذناً لبدء البرمجة. بدء البرمجة يتطلب اكتمال بوابات المواصفات ثم موافقة المستخدم الصريحة المستقلة.