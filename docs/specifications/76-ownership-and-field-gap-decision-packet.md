# 76 — حزمة قرارات اعتماد الملكية والحدود بين المراكز والخدمات المشتركة

**الحالة:** DECISION PACKET / RECOMMENDATIONS ONLY / OWNER SIGN-OFF REQUIRED  
**التاريخ:** 2026-11-15  
**المدخلات:** 36 (مصفوفة الملكية الأصلية المشار إليها في 72)، 72 (سجل OWN-01..10)، 73 (FLD-01..26)، 75 (التحقق المصدرّي)، وعقود 59–70.  
**الغرض:** تحويل التعارضات إلى قرارات محددة قابلة للاعتماد أو التعديل أو الرفض، مع شروط قبول قابلة للتحقق. لا تسجل هذه الوثيقة موافقة لم تصدر.

## 1. قواعد الاعتماد

لكل قرار:
- **Decision owner:** الدور الذي يملك اعتماد حقيقة المجال أو الخدمة، وليس اسم شخص مفترض.
- **Consulted owners:** الأطراف التي تتأثر بالحد.
- **Recommended option:** التوصية الحالية المستمدة من 72–75.
- **Acceptance criteria:** شروط يمكن مراجعتها قبل تثبيت ERD/API.
- **Status:** OPEN — لم يرد توقيع اعتماد في المصدر المتاح.
- يجب تسجيل القرار النهائي، صاحبه، التاريخ، rationale، النسخة المتأثرة، وآثار التغيير.
- لا يجوز اعتبار تحديث هذه الوثيقة موافقة من مالك المركز أو الخدمة.
- لا يجوز تعديل الوثيقة 36 أو تحويل أي مقترح إلى schema تنفيذي قبل إغلاق القرار.

## 2. سجل قرارات OWN-01..OWN-10

| القرار | مالك الاعتماد (دور) | الأطراف المستشارة | التوصية القابلة للاعتماد | شروط القبول | الحالة |
|---|---|---|---|---|---|
| OWN-01 — Asset Registry / Storage | مالك C08 ومالك SH-06 | C02/C05/C12/C10 | C08 يملك سجل الأصل الدلالي والتصنيف والغرض والروابط ودورة حياته؛ SH-06 يملك storage objects/locations، upload sessions، checksum/scan/processing jobs وsigned access refs | لكل asset/version معرف مرجعي واحد؛ لا نسخ مزدوجة لmetadata؛ checksum/source/version semantics موثقة؛ كل وصول يمر عبر ACL؛ حذف/احتفاظ متسق | OPEN |
| OWN-02 — Integration / Connector | مالك C09 ومالك SH-13 | C10 ومالكو التكاملات وSH-08 | C09 يملك تعريف التكامل والغرض والmapping واتجاه المزامنة؛ SH-13 يملك connector manifest/runtime/connection lifecycle/health؛ Secret Manager يملك القيم السرية | لا تخزين secret value في C09/SH-13؛ connection ref ثابت؛ فصل integration run عن job attempt؛ توثيق OAuth scopes/webhooks/retry/idempotency | OPEN |
| OWN-03 — Security / Identity / Authorization / Audit | مالك C10 وملاك SH-01/02/04 | كل المراكز | C10 يملك policy intent/risk/exception governance؛ SH-01 principal/auth/session؛ SH-02 authorization evaluation/grants/decisions؛ SH-04 audit ledger/integrity | فصل authentication عن authorization؛ مصفوفة actor/action/resource/scope؛ audit غير قابل لتعديل الوكيل؛ إثبات enforcement خادمي؛ مراجعة Zero Trust المستقلة | OPEN |
| OWN-04 — Knowledge Content / Index / Search | مالك C12 وملاك SH-07/16 | C08 وC10 وملاك المحتوى المتخصص | C12 أو مالك المجال يملك المحتوى canonical وإصداراته واعتماده؛ C08 الأصل الثنائي؛ SH-16 ingestion/index jobs/provenance؛ SH-07 query/index runtime | كل نتيجة تحمل source/version/ACL؛ إعادة التحقق من صلاحية المصدر عند القراءة؛ حذف/تحديث المصدر ينتشر للفهرس؛ لا يصبح index مصدر حقيقة | OPEN |
| OWN-05 — Settings | مالك C13 ومالك SH-12 ومالكو مفاتيح الإعداد | C10 وSH-03 والمراكز | C13 يملك UX وسير طلب التغيير؛ SH-12 يملك مخزن القيم والتوريث والإصدارات؛ مالك المفتاح يملك schema والدلالة والتطبيق؛ الأسرار references فقط | precedence موثق؛ expected_version/optimistic concurrency؛ موافقة للتغييرات عالية الأثر؛ audit؛ تعريف default/override/secret behavior | OPEN |
| OWN-06 — Build / Release / Deployment | مالك C11 وملاك SH-08/10/14/15 | C05/C07/C10/C14 | C11 يملك release/deployment domain lifecycle؛ SH-08 execution attempts؛ SH-10 version/change-set metadata؛ SH-14 telemetry؛ SH-15 backup/restore evidence؛ C05 source | artifact registry محدد؛ provenance/signature/SBOM؛ فصل release state عن job state؛ rollback/restore semantics؛ نشر محكوم بموافقات واختبارات | OPEN |
| OWN-07 — Schema / Domain Data | مالك C04 وملاك المجالات | كل C01–C14 حسب الكيان | C04 يملك schema/constraints/migration contracts؛ كل مركز مجال يملك حقائق سجلاته وواجهات كتابتها؛ schema steward منفصل عن domain owner | لكل كيان Domain Owner وSchema Steward وAPI Owner؛ tenant consistency؛ migration/rollback/backfill؛ لا كتابة مباشرة لمجال آخر | OPEN |
| OWN-08 — QA / Runtime Health | مالك C07 ومالك SH-14 | C05/C11/C10 | C07 يملك test plans/runs/results/defects/quality gates؛ SH-14 يملك runtime health/telemetry/incidents | test run مرتبط build/environment/evidence؛ health لا يحول إلى test pass؛ quality gate policy versioned؛ لا تكرار سجل تشغيل | OPEN |
| OWN-09 — Dashboard / Source Metrics | مالك C14 وملاك مصادر المؤشرات | كل مراكز المصدر وSH-02/07/14 | C14 يملك layouts/preferences/widgets؛ كل مصدر يملك metric وتعريفه؛ اللوحة read projection فقط | source_ref/source_version/as_of/freshness/ACL لكل projection؛ permission filtering قبل العرض؛ لا كتابة أو اعتماد domain action من projection | OPEN |
| OWN-10 — AI Agent Governance | مالك C06 ومالك C10 وملاك SH-02/03/04 | جميع المراكز الطالبة | C06 يملك تعريف الوكيل ومهاراته وسياق المشروع؛ C10 policy; SH-02 authorization; SH-03 approval; SH-04 audit | الوكيل لا يمنح نفسه صلاحية؛ تفويض قصير النطاق؛ موافقة عالية الأثر؛ kill switch؛ عزل ذاكرة؛ سجل تدقيق محمي؛ budget enforcement | OPEN |

## 3. قرارات فرعية FLD-01..26

| مجموعة FLD | القرار المطلوب | المخرج المطلوب لإغلاقها |
|---|---|---|
| FLD-01/24 | توحيد organization/tenant/workspace/project scope وقواعد references | canonical scope dictionary + DB/API enforcement + cross-tenant negative tests |
| FLD-02 | تثبيت كتالوج project_requirements والإصدارات والعلاقات | entity field catalog + lifecycle + acceptance criteria |
| FLD-03/04 | تثبيت page identity/design canvas/component contract | C02/C03/C05 signed interface contract |
| FLD-05 | تعيين schema/migration owners | entity-to-owner registry + migration policy |
| FLD-06 | فصل AI task intent/plan عن generic job | task/job relationship contract + idempotency semantics |
| FLD-07 | توحيد AI metering مع usage/cost ledger | normalized usage event + reconciliation ID + currency/tax rules |
| FLD-08 | فصل test run/result عن job/health | QA execution contract + health boundary |
| FLD-09/10 | اعتماد asset/version authority وauthorization grants | C08/SH-06 asset contract + SH-02 grant interface |
| FLD-11/12 | اعتماد integration run/connector/secret ownership | C09/SH-13 integration contract + secret reference rules |
| FLD-13/14 | اعتماد policy authoring/evaluation/audit ownership | C10 + SH-02/04 policy/decision/audit contracts |
| FLD-15/16 | اعتماد release/job/artifact registry authority | C11/SH-08/10/14/15 release contract + registry decision |
| FLD-17/18 | اعتماد canonical knowledge document وindex/search boundaries | C12 + SH-07/16 content/index contract |
| FLD-19 | اعتماد setting key ownership/precedence/version | SH-12 contract + key-owner registry |
| FLD-20 | اعتماد projection metadata/freshness/ACL | C14 read-model contract |
| FLD-21 | اعتماد canonical audit envelope/time/provenance | shared envelope contract with occurred_at/recorded_at/actor/source |
| FLD-22/26 | اعتماد classification/retention/legal basis/delete/legal hold | data inventory + retention schedule + privacy controls |
| FLD-23 | اعتماد enum/state machine/versioning registry | per-entity state model + compatibility rules |
| FLD-25 | توحيد domain owner/steward/creator/actor | role and reference dictionary |

## 4. نموذج سجل قرار إلزامي

كل موافقة نهائية يجب أن تحفظ بهذه البيانات (القيم التالية placeholders وليست قرارات):
- decision_id: OWN-XX / FLD-XX
- status: APPROVED / APPROVED-WITH-CONDITIONS / REJECTED / DEFERRED
- decision_owner_role:
- consulted_roles:
- selected_option:
- rationale:
- affected_entities_and_fields:
- affected_contract_versions:
- security_privacy_impact:
- acceptance_evidence:
- approved_at:
- supersedes:
- change_request_ref:

لا تملأ approved_at أو status بقيمة اعتماد دون قرار صريح موثق.

## 5. شروط الانتقال من هذه الحزمة إلى تثبيت العقود

1. إغلاق OWN-01..10 بقرارات موثقة أو استثناءات مؤقتة ذات تاريخ انتهاء.
2. إغلاق FLD-01..26 أو ربط كل متبقٍ بمالك وخطة وموعد ومعيار قبول.
3. تحديث 36 بعد القرار، مع حفظ النسخة السابقة وسجل التغيير.
4. إصدار entity catalog وfield dictionary موحدين مع تصنيف البيانات والنطاق والاحتفاظ.
5. إكمال API/Event/Approval contracts وربطها بصلاحيات actor/action/resource/scope.
6. بناء acceptance test specifications لكل عقد؛ وتبقى NOT RUN حتى تشغيلها.
7. تنفيذ مراجعة أمن/خصوصية مستقلة قبل schema freeze.
8. اعتماد ERD وmigrations وAPI schemas بإصدارات محددة.
9. مراجعة source crosswalk مع المالكين وإصدار baseline جديد.
10. الحصول على موافقة مستقلة صريحة من المستخدم على بدء البرمجة؛ لا تستنتج من عبارة «تابع» في مرحلة المواصفات.

## 6. ما لا تزال الوثيقة لا تدعيه

- لا توجد موافقة من مالكي C01–C14 أو SH-01..SH-16 مسجلة في المستودع لهذه القرارات.
- لا توجد قاعدة بيانات أو جداول أو migrations أو APIs منشأة نتيجة هذه الوثيقة.
- لا توجد اختبارات تشغيل ناجحة أو أدلة CI جديدة.
- لم تُعدل الوثيقة 36؛ التوصيات تنتظر اعتماد المالكين.
- لم يبدأ تنفيذ كود التطبيق.

**DECISION PACKET / OPEN FOR OWNER SIGN-OFF / NOT APPROVED / NO IMPLEMENTATION.**
