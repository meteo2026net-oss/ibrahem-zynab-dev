# 127 — حزمة تتبع C10 للأمن والحوكمة والسياسات والاختبارات

**التاريخ:** 2026-11-15  
**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED / TESTS NOT RUN  
**النطاق:** 11 عملية API مرشحة، 6 أحداث مقترحة، نوعا موافقة، و18 سيناريو قبول/تكامل محلياً.  
**المراجع:** 56، 66، 78، 79، 80، 84، 97، 99، 100، 114، 115، 116، 117، 118، 119، 121–126.

## 1. خلاصة التدقيق

| العنصر | العدد | النتيجة |
|---|---:|---|
| عمليات C10 في سجل 97 | 11 | جميعها مشتقة؛ لا تطابق حرفي لاسم API في DOCX وفق 114 |
| أحداث C10 في سجل 100 | 6 | PROPOSED في 100؛ لا تطابق حرفي وفق 115 |
| أنواع الموافقة | 2 | OWNER REVIEW في 116 |
| سيناريوهات محلية جديدة | 18 | SPECIFIED / NOT RUN؛ ليست Test IDs مصدرية |
| حدود الخدمات المشتركة الأساسية | 4 | SH-01/02/03/04؛ لا تنقل Domain SoR إلى C10 |

هذه الحزمة تفصل C10 (مالك سياسة الأمن والخصوصية والحوكمة) عن الخدمات التي تنفذ الهوية أو القرار أو الموافقة أو سجل التدقيق. لا تمثل مسودة C10 في 66 مواصفة تشغيلية مكتملة أو اعتماداً مؤسسياً.

## 2. حد الملكية ومسؤوليات الخدمات

| المجال | Domain/Service Owner | ما يملكه | ما لا يملكه |
|---|---|---|---|
| سياسة الأمن والخصوصية والمخاطر والاستثناءات | C10 | policy content/version, threat/risk assessment, privacy review, exception governance | كلمات المرور، tokens، القرار التنفيذي العام، سجل التدقيق المركزي |
| الهوية والمصادقة والجلسات | SH-01 | principal/authentication/session lifecycle | policy semantics وDomain resource ownership |
| التفويض والمنح والقرار | SH-02 | enforcement/decision result وفق policy وscope | إنشاء معنى المجال أو تجاوز مالك المورد |
| دورة الموافقة | SH-03 | request/assign/decision workflow evidence بحسب القرار | Domain Decision Owner؛ لا يحسم سياسة C10 بذاته |
| سجل التدقيق | SH-04 | central audit record/source and retrieval controls | تعديل حقائق المجال أو اتخاذ القرار بدلاً عن مالك المجال |
| بيانات المورد | كل مركز مالك | SoR وACL والعمليات على موارده | نقل الحقيقة إلى C10 أو SH-02 لمجرد التفويض |

**مسار القرار المنطقي المرشح:** تحديد هوية actor من SH-01 → تجميع resource/scope من مالك المجال → تقييم policy/enforcement في SH-02 → التحقق من SH-03 عند اشتراط موافقة → تسجيل الحدث الحساس عبر SH-04. فشل اعتماد أساسي لا يتحول إلى سماح ضمني. يظل ترتيب الاستدعاء النهائي ونمط التزامن والتعامل مع تعطل SH-04 قراراً تصميمياً مطلوباً، لا عقد API معتمداً.

## 3. عمليات C10 المفردة

تستند أسماء العمليات إلى سجل 97/مسودة 66. تدقيق 114 لم يجد أي اسم من أسماء C10 الـ11 حرفياً في DOCX؛ لذلك كل صف أدناه DERIVED NAME / OWNER REVIEW وليس API مصدرية مؤكدة.

| Operation ID | الاسم | الغرض وحدود المجال | ضوابط العقد المطلوبة | اختبار محلي |
|---|---|---|---|---|
| OP-C10-144 | evaluatePolicy | تقييم actor/action/resource/context؛ التنفيذ المركزي في SH-02 | policy version, tenant/resource scope, decision reason code, expiry, correlation ID؛ لا قبول client-side decision | C10-127-T01,T02,T03 |
| OP-C10-145 | registerSecurityFinding | تسجيل نتيجة مع مصدرها ومالك معالجتها | finding provenance, severity rubric, affected resource, dedup key, evidence refs؛ لا اختلاق finding | T08,T09 |
| OP-C10-146 | createThreatModel | حفظ threat model بإصدار وأصول وافتراضات وضوابط | scope/version, assumptions, data flow refs, owner review, change history | T10 |
| OP-C10-147 | assessPrivacyImpact | تقييم الغرض وفئات البيانات والأساس/الضرورة والاحتفاظ والنقل | purpose limitation, data categories, legal basis owner input, retention, transfer and reviewer fields؛ لا استنتاج قانوني آلي | T11,T12 |
| OP-C10-148 | requestPolicyChange | إنشاء طلب تغيير لا يطبق السياسة بنفسه | before/after version, rationale, impact analysis, requester, approval ref, effective time | T04,T05 |
| OP-C10-149 | decideSecurityException | قرار استثناء محدد النطاق والمدة | independent approver, explicit resources/actions, reason, compensating controls, expiry, revoke path | T04,T06,T07 |
| OP-C10-150 | suspendAgentCapability | تعليق grant/capability لوكيل ضمن scope | immediate deny semantics, active-job handling, reason, actor, evidence, resume approval path | T13,T14 |
| OP-C10-151 | reviewAgentRun | مراجعة أدوات ومدخلات ومخرجات تشغيل وكيل | immutable run refs, grant snapshot, policy version, sanitized evidence, independent reviewer | T14,T15 |
| OP-C10-152 | recordIncident | تسجيل حادثة أمنية وربط evidence وtimeline | incident classification, affected scope, restricted access, chronology, evidence refs, retention | T16 |
| OP-C10-153 | closeFinding | إغلاق finding بعد تحقق مستقل من المعالجة | remediation evidence, verification result, independent reviewer, residual risk and reopen path | T08,T09 |
| OP-C10-154 | exportAuditEvidence | طلب تصدير أدلة من SH-04 | explicit purpose/recipient/time range/tenant scope, redaction, approval if required, export audit | T17,T18 |

### 3.1 ملاحظات على دلالات العمليات

- evaluatePolicy عملية قرار/استعلام؛ لا تجعلها واجهة بديلة لخدمة SH-02، ولا تكشف تفاصيل policy الحساسة للعميل.
- registerSecurityFinding لا يثبت صحة النتيجة؛ مصدر finding قد يكون مركزاً أو أداة فحص أو مراجعة مستقلة، ويجب حفظ provenance.
- assessPrivacyImpact أداة حوكمة داخلية؛ لا تستبدل المراجعة القانونية أو تحديد الأساس القانوني من الجهة المخولة.
- requestPolicyChange طلب، وdecideSecurityException قرار، وSH-03 ينفذ workflow؛ لا تدمج الطلب والموافقة والتطبيق في command واحد.
- suspendAgentCapability يوقف القدرة الجديدة وفق حدود grant؛ لا يفترض إلغاء كل عمل جارٍ دون سياسة صريحة وتعريف حالة المهمة لدى C06/SH-08.
- exportAuditEvidence يطلب تصديراً مضبوطاً؛ SH-04 يبقى مصدر السجل، ولا تنشئ C10 نسخة قابلة للتعديل بوصفها audit source.

## 4. أحداث C10 الستة — كلها مقترحة

تدقيق 100 يصنف هذه الأسماء PROPOSED، وتدقيق 115 لم يجد تطابقاً حرفياً لها في المصدر. لا تنشر قبل اعتماد اسم الحدث وproducer/schema/consumer.

| Event ID | الاسم المقترح | منتج معنى المجال | الحد الأدنى من البيانات | حد الخدمة |
|---|---|---|---|---|
| EVT-C10-001 | PolicyVersionPublished | C10 | policyRef, version, effectiveAt, approvalRef, contentHash | SH-02 يستهلك نسخة السياسة المنشورة؛ لا يصبح مالك policy |
| EVT-C10-002 | SecurityFindingRegistered | C10/المركز المنتج وفق نوع finding | findingRef, sourceRef, severity, affectedScope, createdAt | SH-04 audit؛ المصدر الأصلي يظل مالك evidence |
| EVT-C10-003 | ExceptionRequested | C10 requester/workflow | requestRef, requestedScope, rationaleRef, expiryRequested | SH-03 يدير الطلب |
| EVT-C10-004 | ExceptionDecided | صاحب القرار المخول عبر SH-03 | requestRef, decision, approverRef, scopeHash, expiry, decisionVersion | SH-03 decision record؛ SH-02 enforcement |
| EVT-C10-005 | AgentCapabilitySuspended | C10 policy owner/SH-02 enforcement evidence | agentRef, capabilityRef, scope, effectiveAt, reasonCode | C06/SH-08 يوقفان العمل وفق lifecycle؛ لا يملك C10 run queue |
| EVT-C10-006 | IncidentRecorded | C10 incident governance | incidentRef, severity, affectedScope, occurredAt, evidenceRefs | SH-04 audit source؛ لا يخلط مع QA defect |

لا تضمّن secrets أو access tokens أو payloads شخصية غير لازمة في أحداث المجال. يجب تعريف classification، tenant boundary، retention، schema evolution، replay policy، delivery guarantee، producer identity، consumer authorization، correlation/causation، والحد الأدنى للبيانات قبل اعتمادها.

## 5. الموافقات

| Approval ID | الإجراء | المرجع | صاحب القرار المقترح | ضوابط مطلوبة |
|---|---|---|---|---|
| APR-C10-01 | decideSecurityException | OP-C10-149 | مسؤول مخول مستقل عن الطالب، وفق governance owner | scope/hash binding، reason، compensating controls، expiry، revoke، منع self-approval |
| APR-C10-02 | requestPolicyChange | OP-C10-148 | مالك الحوكمة/السياسة المخول؛ SH-03 workflow | impact analysis، affected centers, version diff/hash، effective date، rollback/supersession |

**تصحيح دلالي:** APR-C10-02 في 116 مرتبط بعملية الطلب requestPolicyChange، وهذا لا يجعل الطلب نفسه قرار الموافقة. يلزم وجود قرار مستقل في SH-03 وربط القرار بالنسخة الدقيقة. لا تضاف عملية decision API أخرى بالاسم أو المالك تخميناً؛ تحدد بعد استكمال عقد SH-03 وقرار مالك C10.

## 6. سيناريوهات القبول والتكامل

المعرفات التالية محلية مشتقة لهذه الوثيقة، ولا تستبدل Test IDs المصدرية. جميع السيناريوهات SPECIFIED / NOT RUN.

| Scenario ID | التحقق المطلوب | الأطراف | نتيجة القبول |
|---|---|---|---|
| C10-127-T01 | actor أو policy أو scope مفقود | C10/SH-01/02 | رفض افتراضي دون إنشاء grant |
| C10-127-T02 | محاولة cross-tenant مع resource ID صحيح | SH-02 + Domain Owner | deny دون كشف وجود المورد أو بياناته |
| C10-127-T03 | تعذر SH-02 أثناء عملية حساسة | SH-02 + كل consumer | لا تتحول حالة الفشل إلى allow |
| C10-127-T04 | مقدم الطلب يحاول اعتماد طلبه | SH-03/C10 | رفض self-approval حيث يلزم فصل المهام |
| C10-127-T05 | تغيير policy بعد تقديم approval | C10/SH-03/02 | approval القديم لا يطبق على hash/version مختلف |
| C10-127-T06 | استثناء بعد expiry أو خارج scope | C10/SH-02 | deny تلقائي؛ لا توسيع للنطاق |
| C10-127-T07 | سحب استثناء قبل انتهاء مدته | C10/SH-02/03/04 | enforcement محدث مع audit trail |
| C10-127-T08 | إغلاق finding دون دليل معالجة | C10/C07/مالك المورد | يبقى مفتوحاً أو يرفض الإغلاق |
| C10-127-T09 | إغلاق finding بدليل غير مستقل | C10/المراجع | رفض التحقق أو تصعيده لمراجع مستقل |
| C10-127-T10 | threat model قديم بعد تغيير أصل/تدفق بيانات | C10/Domain Owner | يوسم stale/review required ولا يظهر كنسخة حديثة |
| C10-127-T11 | privacy assessment لا يحدد الغرض أو فئة البيانات | C10/data owner | لا يعتمد التقييم؛ الحقول الناقصة صريحة |
| C10-127-T12 | تغيير غرض أو retention أو نقل بيانات | C10 + owner + privacy reviewer | إعادة تقييم وموافقة حسب السياسة |
| C10-127-T13 | تعطيل قدرة وكيل | C10/SH-02/C06/SH-08 | رفض المهام/الأدوات الجديدة؛ توثيق حالة الجاري وفق سياسة معلنة |
| C10-127-T14 | الوكيل يحاول تعديل grant أو policy أو audit | C06/SH-02/SH-04 | deny وتسجيل محاولة؛ لا self-escalation |
| C10-127-T15 | مراجعة agent run | C10/C06/SH-04 | provenance للأدوات والـgrant والسياسة والمخرجات محفوظ |
| C10-127-T16 | incident evidence access | C10/SH-04 | وصول محدود حسب الدور والنطاق مع سجل وصول |
| C10-127-T17 | exportAuditEvidence بدون scope أو recipient واضح | C10/SH-04/SH-03 | رفض الطلب أو طلب استكمال البيانات |
| C10-127-T18 | محاولة تعديل/حذف audit event أو تسريب secret | SH-04/كل المراكز | منع التعديل؛ إخفاء secret؛ تصحيح append-only وفق تصميم معتمد |

## 7. تعارض Zero Trust وقرارات الأمن

لا يجوز إغلاق هذا التعارض بالتفسير أو التفضيل التقني:

- مصدر الفصل 158.7 يصف Zero Trust بأنه إلزامي.
- الفصل 158.9 وDEC-SEC-010 في الفصل 160 يضعانه ضمن المقترحات/القرار غير المحسوم.
- الوثيقة 56 تسجل DEC-CONFLICT-02 وتبقيه PENDING_USER.
- لذلك يبقى Zero Trust قراراً معلقاً. لا يُعلن معتمداً ولا يُحذف من سجل الخيارات.
- حتى صدور قرار: تستمر الضوابط الأمنية الأخرى التي اعتمدها المصدر بصورة مستقلة، ولا يستخدم هذا التعارض لتعطيل المصادقة والتفويض والعزل والتدقيق أو لجعلها اختيارية.
- يجب أن يحدد قرار الحسم النطاق والمعايير القابلة للاختبار والاستثناءات ومسؤول الاعتماد ومتطلبات الهوية/الجهاز/الخدمة وإعادة التقييم والأدلة وخطة الانتقال. لا نفترض منتجاً أو تقنية بعينها.

## 8. فجوات الملكية والبيانات والعقود

1. **OWN72-03:** مرجع الملكية المستخدم في العمليات ليس بديلاً عن قرار مالك C10 المؤهل؛ يلزم تحديد Domain Owner، Policy Decision Owner، API owner، وSH-01/02/03/04 owners.
2. **Domain SoR:** C10 يملك معنى policy/risk/privacy/security governance فقط؛ بيانات كل مجال تبقى عند مالكها، وبيانات الهوية عند SH-01، وقرار التفويض عند SH-02، والموافقة عند SH-03، والتدقيق عند SH-04.
3. **الحقول:** يلزم استكمال تعريف policy version/hash/effective state، risk/finding provenance، privacy purpose/classification/retention، exception scope/expiry، agent capability binding، incident evidence references. لا تفترض نوعاً أو nullable/retention دون قرار.
4. **الصلاحيات:** مصفوفة 78 إطار موحد لا RBAC/ABAC مكتمل؛ الأدوار والعضويات والوراثة وتعارض المصالح تحتاج اعتماداً.
5. **Audit:** append-only مبدأ مطلوب في المسودة، لكن التقنية والتوقيع والتشفير والاحتفاظ والتصدير والتصحيح والاستعادة لم تعتمد.
6. **Privacy:** لا توجد بعد سياسة موحدة معتمدة للغرض والاحتفاظ والنقل والحذف وحقوق البيانات؛ لا تُستنتج متطلبات قانونية من أسماء الحقول.
7. **Agent governance:** يلزم ربط C10 بمالك agent في C06 وبمنفذ المهام SH-08، مع سياسة لإيقاف الجاري وإلغاء الأدوات والمفاتيح.
8. **Testing:** 18 سيناريو محلياً؛ لا يوجد exact operation→test أو event→test mapping، ولا نتائج تشغيل أو تقرير اختبار أمني مستقل.
9. **API/events:** لا schemas أو error catalogs أو versioning أو consumer permissions معتمدة؛ لا يُنشأ endpoint/topic من أسماء هذه الوثيقة.
10. **SLO/recovery:** لا نضع أرقاماً لزمن القرار أو توافره أو RTO/RPO من دون قرار تشغيلي.

## 9. بوابة إغلاق C10

- [ ] حسم DEC-CONFLICT-02 / Zero Trust من صاحب القرار.
- [ ] اعتماد OWN72-03 والتمييز بين C10 وSH-01/02/03/04.
- [ ] اعتماد سياسة تصنيف البيانات والخصوصية والاحتفاظ والنقل.
- [ ] اعتماد نموذج الصلاحيات والعضويات وتعارض المصالح.
- [ ] اعتماد 11 عملية بعقود مفردة ومصدرها أو التصريح بأنها مشتقة.
- [ ] اعتماد الأحداث الستة أو حذفها بقرار موثق، مع producer/schema/consumer.
- [ ] حسم APR-C10-01/02 وفصل الطلب عن القرار والتطبيق والتحقق.
- [ ] اعتماد سيناريوهات 18 وربطها بمتطلبات المصدر واختبارات مستقلة.
- [ ] تنفيذ اختبارات التكامل والأمن بعد الموافقة، وحفظ run IDs والأدلة.
- [ ] تحديث 77/78/79/80/94/97/99/100/114/115/116/117/118/119 بعد قرارات المالكين.

## 10. الحكم النهائي لهذه الجولة

اكتملت حزمة تتبع C10 بمستوى تفصيلي: 11 عملية، 6 أحداث مقترحة، موافقتان، و18 سيناريو قبول/تكامل. لكن جميع أسماء API في C10 مشتقة وغير مطابقة حرفياً للمصدر بحسب 114، والأحداث الستة PROPOSED بحسب 100/115، والموافقات OWNER REVIEW. Zero Trust متعارض ومعلق، وOWN72-03 والحقول والسياسات وعقود SH-01/02/03/04 لم تعتمد.

**لا تغلق هذه الوثيقة GAP-044 أو GAP-020 أو GAP-027. لا تغيير في كود التطبيق. لا APIs أو schemas أو migrations معتمدة. الاختبارات لم تنفذ. بوابة بدء البرمجة لا تزال مغلقة.**
