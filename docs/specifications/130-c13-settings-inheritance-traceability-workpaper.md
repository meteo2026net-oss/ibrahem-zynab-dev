# 130 — حزمة تتبع C13 للإعدادات والتوريث والموافقات والاختبارات

**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**النطاق:** C13، مع حدود SH-12 وSH-02/03/04 وC10 ومالكي إعدادات المجالات.

## 1. النتيجة التنفيذية

| المحور | العدد/الحالة | التفسير |
|---|---:|---|
| عمليات C13 في سجل 97 | 10 | أسماء مشتقة ومرشحة وليست APIs منشورة |
| أحداث C13 في سجل 100 | 4 | PROPOSED؛ لا تثبت أسماء مصدرية أو producer معتمداً |
| أنواع الموافقات في سجل 98 | 2 | OWNER REVIEW |
| سيناريوهات القبول المحلية | 20 | SPECIFIED / NOT RUN |
| Exact Event→Test ID مثبت | 0/4 | لا يوجد ربط مفرد أصلي مثبت |
| اعتماد C13 النهائي | غير حاصل | لا اعتماد schema أو precedence أو secret policy |

لا تنشئ هذه الحزمة إعدادات فعلية أو محرك توريث أو مخطط بيانات أو APIs أو سياسات أسرار.

## 2. حدود ملكية C13 وSH-12 ومالكي المجالات

C13 يملك تجربة إدارة الإعدادات المشتركة: تعريف المفاتيح، النطاقات، التوريث، الإصدارات، طلبات التغيير، المقارنة والتشخيص. SH-12 يقدم الآلية المشتركة لتخزين القيم وتطبيق version/precedence وفق العقد المعتمد. لكن مالك كل مجال يملك معنى المفتاح وقواعد التحقق وتطبيق القيمة على مجاله.

| العنصر | المالك | حد C13/SH-12 |
|---|---|---|
| setting definition ودلالته | مالك المجال؛ C13 يملك registry/UX المشترك وفق التفويض | لا يغير C13 معنى المفتاح أو schema المالك |
| effective settings والوراثة | C13/SH-12 كآلية مشتركة | إظهار المصدر وسلسلة التوريث؛ لا تجاوز enforced policy |
| تطبيق إعداد مجال | API مالك المجال | applyOwnerSettingChange يمرر الطلب ولا يكتب مباشرة في مجال آخر |
| هوية وصلاحيات | SH-01/SH-02 | C13 لا ينشئ هوية ولا يقرر التفويض |
| الموافقات | SH-03 | C13 يملك سياق التغيير؛ SH-03 يدير lifecycle القرار |
| التدقيق | SH-04 | لا قيم أسرار في audit/events؛ حفظ refs آمنة |
| السياسات الأمنية | C10 ومالك المفتاح | إعداد حساس لا يتغير دون الموافقات والسياسة |
| عرض الحالة | C14 | لوحة التحكم تعرض snapshot ولا تصبح SoR للإعدادات |

## 3. نموذج النطاقات والتوريث

المستويات المذكورة في عقد 69 هي: Platform → Organization → Workspace → Project → Environment → User → Session. لا تعتبر هذه القائمة ترتيب أولوية نهائياً لكل مفاتيح الإعداد؛ يجب اعتماد precedence لكل مفتاح أو فئة.

قواعد التحقق المقترحة:
1. القيمة الشخصية لا تتجاوز سياسة المؤسسة أو المنصة.
2. القيمة enforced لا يمكن تجاوزها من نطاق أدنى.
3. كل نتيجة effective تعرض مصدر القيمة وسلسلة التوريث وversion.
4. يحدد مالك المفتاح allowed scopes وinheritable وenforced وsensitivity.
5. التعارض المتزامن يستخدم expected_version أو آلية conflict صريحة؛ لا يعتمد last-write-wins تلقائياً.
6. الأسرار لا تحفظ كقيم نصية في C13 أو SH-12؛ تستخدم secret references مع صلاحيات وصول منفصلة.
7. التغيير لا يطبق على المجال حتى يقبله API المالك ويعيد نتيجة تطبيق موثوقة.
8. إعدادات AI agents لا تمنح الوكيل صلاحيات تتجاوز المستخدم أو policy scope.

**OPEN:** ترتيب الأولوية النهائي، واستثناءات كل مجال، وسلوك القيم المحذوفة/null/default، وآلية version conflict.

## 4. سجل العمليات الـ10

| Operation ID | الاسم | الوظيفة المرشحة | ضابط الإغلاق |
|---|---|---|---|
| OP-C13-179 | getEffectiveSettings | قراءة القيم الفعالة ومصادرها | ACL + scope + version/provenance |
| OP-C13-180 | validateSettingValue | التحقق من قيمة حسب schema المالك | لا تطبيق جانبي؛ تشخيص واضح |
| OP-C13-181 | updateUserPreference | تعديل تفضيل شخصي | allowlist وعدم تجاوز enforced settings |
| OP-C13-182 | requestScopedSettingChange | طلب تغيير ضمن scope | تحديد owner وimpact وexpected_version |
| OP-C13-183 | decideSettingChange | قرار موافقة | SH-03 وفصل مقدم الطلب عن المعتمد |
| OP-C13-184 | applyOwnerSettingChange | تطبيق عبر API مالك المجال | لا كتابة مباشرة في Domain SoR |
| OP-C13-185 | compareSettingVersions | مقارنة نسختين | ACL وإظهار diff دون تغيير |
| OP-C13-186 | rollbackSettingVersion | طلب تراجع عن إصدار | فحص الآثار والموافقات قبل التطبيق |
| OP-C13-187 | exportNonSecretSettings | تصدير إعدادات غير سرية | حذف secret values وتنقيح metadata الحساسة |
| OP-C13-188 | diagnoseInheritance | تشخيص التوريث والتعارض | لا إصلاح تلقائي دون طلب وموافقة |

## 5. الأحداث الأربعة المقترحة

جميع أحداث C13 في سجل 100 مصنفة PROPOSED. لا يوجد اعتماد producer أو consumer أو event schema.

| Event ID | الاسم | المنتج/المستهلك المرشح | الدلالة |
|---|---|---|---|
| EVT-C13-001 | SettingChangeRequested | C13 → SH-03 | change request ref وscope وkey ref وexpected version |
| EVT-C13-002 | SettingChangeDecided | SH-03 → C13 | القرار وصاحبه وapproval ref وrequest hash |
| EVT-C13-003 | SettingApplied | C13 بعد تأكيد API المالك/SH-12 | نتيجة التطبيق وeffective version وdomain ref |
| EVT-C13-004 | SettingVersionRolledBack | C13 بعد تأكيد مالك المجال | الإصدار السابق والجديد ونتيجة التحقق |

يجب تعريف event envelope وschema version وtenant scope وcorrelation/causation IDs وidempotency وdelivery/replay وdead-letter وretention قبل التنفيذ. لا يصدر C13 SettingApplied لمجرد أن SH-03 وافق؛ يجب أن يصل تأكيد التطبيق من مالك المجال.

## 6. أنواع الموافقات

| Approval ID | الفعل المحمي | سياق القرار | الضابط |
|---|---|---|---|
| APR-C13-01 | applyOwnerSettingChange | تغيير إعداد على مستوى مشروع/مؤسسة/منصة | مالك المفتاح يقرر؛ SH-03 يدير القرار |
| APR-C13-02 | rollbackSettingVersion | تراجع عن إعداد موروث أو مفروض | مالك المفتاح وC10 عند وجود أثر policy/security |

كلاهما OWNER REVIEW. يلزم تحديد مستويات الأثر التي تتطلب موافقة، وكيفية ربط القرار بالـkey/scope/value hash/version، ومتى يلزم إشراك C10. لا تُستنتج موافقة شاملة لكل تغيير من وجود نوعين مرشحين.

## 7. سيناريوهات القبول والتكامل المحلية

المعرفات محلية وليست Test IDs مصدرية. جميعها SPECIFIED / NOT RUN.

| Test ID المحلي | السيناريو | النتيجة المتوقعة |
|---|---|---|
| C13-AT-001 | قراءة effective setting عبر نطاقات متعددة | القيمة ومصدرها وسلسلة التوريث واضحة |
| C13-AT-002 | محاولة تجاوز قيمة enforced من user/project | رفض التجاوز |
| C13-AT-003 | مستخدم من tenant آخر يقرأ/يعدل إعداداً | رفض الوصول |
| C13-AT-004 | تغيير إعداد حساس دون موافقة | يبقى pending أو يرفض حسب السياسة؛ لا تطبيق |
| C13-AT-005 | تغيير value/hash بعد الموافقة | إبطال الموافقة وطلب قرار جديد |
| C13-AT-006 | تصدير non-secret settings | لا secret values ولا secret-bearing metadata |
| C13-AT-007 | rollback لإعداد له آثار غير قابلة للعكس | إيقاف التنفيذ وطلب خطة معتمدة |
| C13-AT-008 | تحديث متزامن بنفس expected_version | تعارض صريح دون overwrite صامت |
| C13-AT-009 | إعداد يملكه C10/C05/C04 | يمر عبر API المالك ولا يكتب C13 مباشرة |
| C13-AT-010 | تفضيل مستخدم شخصي | لا يغير effective setting لبقية أعضاء المشروع |
| C13-AT-011 | مفتاح غير مسموح في scope معين | رفض التعديل |
| C13-AT-012 | قيمة تخالف schema المالك | إرجاع validation report دون تطبيق |
| C13-AT-013 | secret reference غير صالح أو غير مخول | رفض القراءة/التطبيق وعدم إظهار القيمة |
| C13-AT-014 | تسرب قيمة سرية في logs/events/audit | لا تظهر القيمة؛ يسمح بمرجع منقح فقط |
| C13-AT-015 | تعارض precedence بين org/project/environment | نتيجة حتمية وفق policy المعتمدة وإظهار المصدر |
| C13-AT-016 | طلب تغيير دون owner أو impact classification | رفض/إبقاء غير مكتمل |
| C13-AT-017 | SH-03 يوافق لكن API المالك يفشل | لا يصدر SettingApplied ولا تعرض القيمة كفعالة |
| C13-AT-018 | إعادة تسليم event SettingApplied | idempotent وعدم تكرار الأثر |
| C13-AT-019 | وكيل AI يحاول تعديل policy أو secret | رفض؛ يسمح بالاقتراح ضمن التفويض |
| C13-AT-020 | استرجاع إعداد قديم بعد rollback | حفظ التاريخ وإظهار effective version الجديدة ومصدرها |

## 8. القرارات المفتوحة

1. اعتماد precedence النهائي لكل نوع setting ومتى تكون القيمة enforced.
2. اعتماد تعريفات المفاتيح وschema_ref وallowed_scopes وdefault_ref وsensitivity وsecret_ref_only وowner_center.
3. تعيين مالك SH-12 وحدود التخزين والإصدارات والقراءة/الكتابة.
4. تحديد كيف تتعامل C13 مع الإعدادات التي يملكها كل من C10 وC04 وC05 وC06 وC09 وC11.
5. اعتماد آلية change request والـimpact classification وexpected_version وconflict handling.
6. اعتماد متى تكون الموافقة إلزامية ومن يقررها وفصل الواجبات.
7. اعتماد rollback semantics: استعادة قيمة أم إعادة تطبيق domain behavior؛ لا يُفترض أن إعادة القيمة تعكس آثارها.
8. اعتماد secret manager/KMS ownership وسياسة secret refs وتدويرها والوصول إليها.
9. اعتماد export/import policy وformat/version compatibility.
10. اعتماد audit/event schemas وpayload minimization والاحتفاظ.
11. اعتماد ACL وtenant isolation لكل scope.
12. ربط العمليات والأحداث والموافقات بمعرفات اختبارات أصلية أو اختبارات معتمدة وتشغيلها.

## 9. معيار إغلاق C13

لا ينتقل C13 إلى APPROVED أو READY-FOR-IMPLEMENTATION حتى:
- اعتماد ملكية C13 مقابل SH-12 ومالك معنى كل setting.
- اعتماد قاموس المفاتيح والحقول والنطاقات وprecedence.
- اعتماد سياسة الأسرار وعدم ظهور القيم في logs/events/exports.
- اعتماد دورة الطلب والقرار والتطبيق والتراجع وربط approval بالبصمة.
- اعتماد عقود API/event/error/auth/version وDomain Owner APIs.
- اعتماد سيناريوهات الاختبار وتشغيلها مع أدلة.
- مراجعة C10 وSH-02/03/04 ومالكي المجالات المتأثرة.

**الحكم النهائي:** C13 موثق في حزمة تتبع أولية؛ ما زال DERIVED / OWNER REVIEW / NOT APPROVED / NOT IMPLEMENTED. لا تثبت هذه الوثيقة وجود محرك إعدادات أو تخزين أسرار أو توريث أو rollback عامل.
