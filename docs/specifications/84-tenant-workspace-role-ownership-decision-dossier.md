# 84 — حزمة حسم ملكية المؤسسة ومساحة العمل والأدوار والأذونات

**التاريخ:** 2026-11-15  
**الحالة:** SOURCE-BASED OWNERSHIP DECISION DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** الوثائق 23 و25 و36 و56 و77 و78 و80 و82؛ مع المحافظة على حدود ما تثبته هذه المصادر.

## 1. سبب إعداد الحزمة

تسجل الوثيقة 36 المسألتين OWN-08 (المؤسسة ومساحة العمل) وOWN-09 (الأدوار والأذونات) كمسألتين مفتوحتين. يثبت العقد 23 أن SH-02 يقدم قرار التفويض على نطاق المؤسسة ومساحة العمل والمشروع والمركز والمورد والعملية والبيئة، ويقترح سجلات roles وpermissions وrole_permissions وaccess_policies وpolicy_rules وpermission_grants وtemporary_grants وaccess_decisions. لكنه لا يحدد وحده مالك حقيقة المؤسسة أو مساحة العمل أو التعريفات الدلالية للأدوار في كل مجال. كما أن الوثيقة 77 تفصل organization_id عن tenant_id وتبقي علاقتهما مفتوحة، وتصف workspace_id بأنه مرجع نطاق لا يمنح صلاحية.

هذه الحزمة تفصل القرارات المطلوبة حتى لا يتحول وجود SH-01/SH-02 إلى افتراض أن ملكية المؤسسة أو عضوية مساحة العمل أو كل تعريفات الأدوار قد حُسمت.

## 2. الحقائق المستعادة والحدود

| المصدر | الحقيقة المثبتة | ما لا يثبته |
|---|---|---|
| 23 / SH-01 | هوية الفاعل والمصادقة والجلسة ومراجع الحسابات | ملكية المؤسسة ومساحة العمل أو عضوياتها |
| 23 / SH-02 | تقييم allow/deny، الأدوار والأذونات والمنح والسياسات التشغيلية المقترحة على نطاقات متعددة | المالك الدلالي النهائي لكل دور مجالّي، أو سلطة إنشاء مؤسسة |
| 23 / SH-03 | دورة الموافقة؛ قواعد الموافقين تحت ملكية المجال/السياسة | من يملك المؤسسة أو مساحة العمل |
| 25 | organizationId وprojectId جزء من سياق الطلب؛ الخدمة تتحقق ولا تثق بقيم العميل | أن organizationId مرادف tenant_id أو أن وجوده يمنح العضوية |
| 36 / OWN-08 | الفصل 134 يذكر ملكية منصة/مؤسسة/مساحة عمل؛ المالك التشغيلي الدقيق غير محسوم | قرار مالك نهائي |
| 36 / OWN-09 | يجب فصل تعريف الدور المجالّي عن محرك قرار التفويض | نموذج نهائي للأدوار والعضويات والوراثة |
| 77 | organization_id وtenant_id ليسا مترادفين تلقائياً؛ workspace_id مرجع scope؛ أنواع الحقول والقيود TBD | canonical IDs أو العلاقات النهائية |
| 78 | Human Owner/Editor/Reviewer/Viewer وService Principal وAI Agent تصنيفات فاعلين مرشحة | أنها أدوار فعلية أو أن لها منحاً منشأة |
| 56 | Zero Trust/SEC-REV-01 متعارض المصدر وغير محسوم | موافقة أمنية نهائية |

## 3. نموذج الملكية المقترح — يحتاج اعتماداً

### 3.1 المؤسسة ومساحة العمل والنطاقات

| المفهوم/المورد | المالك المقترح | المسؤولية | ما لا يجوز افتراضه |
|---|---|---|---|
| tenant boundary / isolation identity | مالك منصة/مستأجر يُعيّن رسمياً مع C10 | تعريف حد العزل المؤسسي والتحقق من عدم عبور البيانات | لا يفترض tenant_id = organization_id |
| organization record and lifecycle | مالك نطاق المؤسسة الذي يعيّنه مالك المنتج/المنصة | إنشاء المؤسسة وتغيير حالتها وملفها وسياسات دورة حياتها | لا يفترض أن SH-01 أو SH-02 مالك سجل المؤسسة |
| workspace record and lifecycle | مالك مساحة العمل الذي يعيّنه صاحب المؤسسة/المنصة | إنشاء مساحة العمل وملفها وحالتها وعلاقتها بالمؤسسة | لا يفترض أن workspace_id يمنح صلاحية أو أن كل مؤسسة لها مساحة واحدة |
| organization-workspace membership | مالك نطاق المؤسسة/مساحة العمل | عضوية المؤسسة ومساحة العمل والعلاقات المسموح بها | لا تستنتج العضوية من معرف مرسل في API |
| project ownership | C01 وفق نطاق المشروع | سجل المشروع ومتطلباته وعلاقته بالمؤسسة ومساحة العمل | لا تنقل ملكية project إلى C10 |
| environment identity | مالك المجال/البيئة حسب المورد | تعريف البيئة وعلاقتها بالمشروع وسياسات الاستهداف | لا تفترض ترتيب وراثة قبل اعتماده |
| isolation enforcement | C10 policy governance + SH-02 runtime + كل service owner | enforcement عند API/DB/storage/search/jobs/events | SH-02 لا يصبح مالك بيانات المجال |

### 3.2 تعريف الأدوار مقابل محرك التفويض

| العنصر | المالك المقترح | الحد |
|---|---|---|
| domain role meaning | مالك المجال الذي يستخدم الدور (C01–C14 أو مالك المؤسسة) | يحدد ماذا يعني الدور في المجال، ولا ينفذ قرار الوصول مركزياً |
| platform-wide role catalog (إن اعتمد) | مالك منصة/حوكمة معيّن | أسماء الأدوار العامة ووصفها ونطاق استخدامها |
| authorization policy authoring | C10 | يملك سياسات الأمن والحوكمة وإصداراتها واستثناءاتها |
| authorization runtime | SH-02 | يقيّم actor/action/resource/scope ويصدر allow/deny وreason |
| identity and principal lifecycle | SH-01 | يثبت هوية المستخدم/الخدمة/الوكيل وحالة الجلسة؛ لا يقرر صلاحية المورد |
| membership and role assignment source | مالك المؤسسة/مساحة العمل/المجال حسب scope | يملك حقيقة العضوية والتعيين؛ SH-02 يستهلكها في القرار |
| grant lifecycle and effective permissions | SH-02 | إدارة المنح الفعالة والمؤقتة وسحبها وتقييمها وفق سياسة معتمدة |
| approval workflow | SH-03 | ينفذ دورة الموافقة؛ المجال/السياسة يحدد من يوافق ولماذا |
| audit ledger | SH-04 | سجل غير قابل للتعديل العادي لقرارات وتغييرات الهوية والمنح والسياسات |

## 4. قرارات مطلوبة — سجل مستقل

هذه معرفات حزمة مقترحة ولا تستبدل OWN36-08 أو OWN36-09:

| Decision ID | القرار المطلوب | المالك/المراجع المطلوب | الحالة |
|---|---|---|---|
| TENANT-84-01 | تعريف الفرق الرسمي بين tenant وorganization وعلاقة المفاتيح | مالك المنصة + C10 + C04 | OPEN |
| TENANT-84-02 | تعيين Domain SoR لسجل المؤسسة ودورة حياته | مالك المنتج/المنصة المعين | OPEN |
| TENANT-84-03 | تعيين Domain SoR لسجل مساحة العمل ودورة حياتها | مالك المنتج/المنصة + ممثلو المراكز | OPEN |
| TENANT-84-04 | اعتماد علاقات organization/workspace/project/environment وحدود المشاركة | C01 + مالكو النطاقات + C10 | OPEN |
| TENANT-84-05 | اعتماد عضوية المؤسسة ومساحة العمل ومصدر حقيقة العضوية | مالك المؤسسة/مساحة العمل + SH-01/02 | OPEN |
| ROLE-84-01 | اعتماد كتالوج أدوار المنصة إن وجد وحدود الأدوار العامة | مالك المنصة + C10 | OPEN |
| ROLE-84-02 | اعتماد أن كل مجال يملك معنى أدواره الدلالي | ملاك C01–C14 | OPEN |
| ROLE-84-03 | اعتماد نموذج role assignment وscope وexpiry وdelegation | مالك المؤسسة/المجال + SH-02 | OPEN |
| ROLE-84-04 | اعتماد قواعد الوراثة/التقييد بين المؤسسة ومساحة العمل والمشروع والبيئة | C10 + SH-02 + ملاك النطاقات | OPEN |
| ROLE-84-05 | اعتماد separation-of-duties وتعارض المصالح ومنع self-approval | C10 + SH-03 + المالك المعني | OPEN |
| ROLE-84-06 | اعتماد lifecycle لسحب العضوية والمنح وإبطال الجلسات والمهام الجارية | SH-01/02/03 + C10 | OPEN |
| ROLE-84-07 | اعتماد سجل التدقيق ومدة الاحتفاظ لأحداث العضوية والمنح | SH-04 + C10 + مالك البيانات | OPEN |
| ROLE-84-08 | اعتماد صلاحيات Service Principal وAI Agent وعزلها عن البشر | C06 + C10 + SH-01/02 | OPEN |

## 5. قواعد أمان لا يجوز تخفيفها

1. organization_id وtenant_id وworkspace_id وproject_id مراجع نطاق منفصلة إلى أن تعتمد العلاقات؛ لا يُفترض ترادفها.
2. وجود معرف نطاق في payload أو URL أو route لا يثبت العضوية أو الصلاحية.
3. كل خدمة مستقبلة تتحقق server-side من الهوية والنطاق والمالك والمنح والسياسة.
4. لا يكفي إخفاء زر أو قفل route أو client-side guard.
5. cross-tenant access مرفوض افتراضياً؛ المشاركة العابرة للنطاق تحتاج grant صريحاً محدوداً وقابلاً للإبطال.
6. تغيير عضوية أو دور أو grant حساس يربط بفاعل ومبرر ونسخة متوقعة وسجل تدقيق، وبموافقة مستقلة حيث تعتمد السياسة ذلك.
7. المهام المؤجلة والأحداث وإعادة المحاولة تعيد فحص التفويض قبل الأثر.
8. سحب الدور/العضوية/المنحة يجب أن يحدد أثره على الجلسات والروابط الموقعة والبحث والمهام الجارية؛ لا تفترض الإبطال التلقائي قبل تحديد آلية موثقة واختبارها.
9. لا يملك AI Agent إنشاء أو توسيع أو اعتماد صلاحياته أو عضويته.
10. لا تمنح تصنيفات Human Owner/Editor/Reviewer/Viewer في 78 أي صلاحيات فعلية قبل اعتماد تعريفاتها ومنحها.

## 6. حقول الكتالوج التي تبقى TBD

| الحقل/المجموعة | قرار مطلوب |
|---|---|
| tenant_id / organization_id | canonical identifier, relation, uniqueness, lifecycle, nullability |
| workspace_id | owner, parent organization, cardinality, lifecycle, sharing |
| membership_id / member_principal_ref | source of truth, status, start/end, invitation/acceptance |
| role_id / role_key / role_version | global vs domain namespace, immutable key, version policy |
| role_assignment | principal, role, resource scope, granted_by, valid_from/to, revocation |
| permission_id / action_key | naming, resource/action semantics, compatibility |
| grant_id / grant_scope / grant_expiry | grant source, constraints, delegation, revocation |
| policy_id / policy_version | C10 ownership, rollout, rollback, conflict resolution |
| effective_permissions | computed view only; not an independent writeable source |
| audit_event_ref | SH-04 source, integrity, retention, redaction |
| created_by / updated_by | SH-01 principal refs, domain record semantics |
| classification / retention / legal_basis | domain owner and C10 review; values remain TBD |

لا تحدد هذه الحزمة نوع SQL أو UUID أو nullability أو indexes أو cascade behavior لأن المصادر لا تثبتها.

## 7. اختبارات قبول لازمة

| Test ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| TENANT-84-T01 | tenant_id مختلف عن organization_id | لا يتم دمجهما أو قبولهما كقيمة واحدة دون mapping معتمد | SPECIFIED / NOT RUN |
| TENANT-84-T02 | عضو مؤسسة يحاول الوصول إلى مساحة عمل غير عضو فيها | رفض خادمي ما لم توجد عضوية/grant صريح | SPECIFIED / NOT RUN |
| TENANT-84-T03 | تبديل workspace_id في payload | التحقق من عضوية ونطاق المورد من مصدر موثوق؛ لا ثقة بالعميل | SPECIFIED / NOT RUN |
| TENANT-84-T04 | cross-tenant resource reference | رفض افتراضي دون تسريب metadata | SPECIFIED / NOT RUN |
| TENANT-84-T05 | تعليق/إغلاق مؤسسة أو مساحة عمل | منع العمليات الجديدة وفق lifecycle policy المعتمدة | BLOCKED: lifecycle policy TBD |
| ROLE-84-T01 | دور مجالّي يستخدم في مركز آخر | لا تنتقل دلالته أو صلاحياته تلقائياً | SPECIFIED / NOT RUN |
| ROLE-84-T02 | مستخدم لديه role دون membership في scope | لا يحصل على الوصول لمجرد وجود role assignment | SPECIFIED / NOT RUN |
| ROLE-84-T03 | انتهاء role assignment أو grant | deny بعد انتهاء الصلاحية مع سجل تدقيق | SPECIFIED / NOT RUN |
| ROLE-84-T04 | سحب عضوية أثناء job مؤجل | recheck يمنع الأثر اللاحق غير المصرح | SPECIFIED / NOT RUN |
| ROLE-84-T05 | actor ينشئ ويعتمد تغيير صلاحياته | رفض عند اشتراط فصل المهام | SPECIFIED / NOT RUN |
| ROLE-84-T06 | AI Agent يطلب توسيع صلاحياته | رفض؛ لا self-grant أو self-approval | SPECIFIED / NOT RUN |
| ROLE-84-T07 | فشل SH-02 أو غياب policy | fail closed للعمليات المحمية | SPECIFIED / NOT RUN |
| ROLE-84-T08 | تغيير role/policy version أثناء replay | إعادة تقييم وفق النسخة والسياسة الحالية المعتمدة | SPECIFIED / NOT RUN |
| ROLE-84-T09 | إزالة عضوية مع وجود cache/search index | لا تستمر نتائج أو تنزيلات خارج ACL بعد مدة الإبطال المعتمدة | BLOCKED: revocation SLA TBD |
| ROLE-84-T10 | audit service unavailable في تغيير حساس | السلوك وفق سياسة طوارئ معتمدة؛ لا تجاوز صامت | SPECIFIED / NOT RUN |

هذه حالات مخططة، لم تنفذ ولم تنتج run IDs أو build evidence.

## 8. الربط بالوثائق السابقة

- OWN36-08 وOWN36-09 هما المرجعان التاريخيان المفتوحان في الوثيقة 36.
- الوثيقة 82 تمنع استخدام OWN36 وOWN72 كمعرفات مختصرة متبادلة.
- FLD-01 في الوثيقة 77 يبقى مفتوحاً بسبب علاقة tenant_id/organization_id.
- FLD-10 وFLD-13 في الوثيقة 77 يبقيان مفتوحين لحدود grants وسياسة C10 وقرار SH-02.
- FLD-21/23/25 تبقى منفصلة؛ لا تعمم حقول actor/enum/owner دون موافقة مالكي المجال.
- الوثيقة 78 مصفوفة مرشحة وليست تعريفاً نهائياً للأدوار.
- INT-80-03 وSEC-80-01/02 وSEC-80-13 وINT-80-15 ترتبط مباشرة بحدود هذه الحزمة.
- DEC-CONFLICT-02 / Zero Trust يبقى مفتوحاً؛ لا تعتبر هذه الحزمة حلاً للتعارض.

## 9. بوابة الإغلاق

- [ ] تعيين مالك المؤسسة ومالك مساحة العمل رسمياً.
- [ ] اعتماد معنى tenant وعلاقته بـorganization.
- [ ] اعتماد مصدر حقيقة العضوية وعلاقات النطاقات.
- [ ] اعتماد كتالوج الأدوار العامة أو تقرير عدم وجوده.
- [ ] تعيين مالك دلالة كل دور مجالّي.
- [ ] اعتماد role assignment/grant/expiry/delegation/revocation.
- [ ] اعتماد precedence وseparation-of-duties وself-approval policy.
- [ ] اعتماد سياسات إبطال الجلسات والمهام والبحث والروابط الموقعة.
- [ ] اعتماد تصنيف واحتفاظ وسجل تدقيق.
- [ ] تحديث الكتالوج 77 ومصفوفة 78 والعقود 79 والاختبارات 80 بعد موافقات موثقة.
- [ ] تنفيذ اختبارات القبول والأمن وتسجيل commit/build/environment/run IDs والأدلة.

**لا تنشئ هذه الوثيقة أدواراً أو عضويات أو سياسات أو صلاحيات أو مخططات بيانات أو APIs. لا تعتمد Zero Trust ولا تحدد RTO/RPO أو سياسات الاحتفاظ. جميع القرارات OPEN، ولا تمثل إذناً ببدء البرمجة.**

**TENANT / WORKSPACE / ROLE OWNERSHIP DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION.**
