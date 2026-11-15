# 80 — كتالوج اختبارات القبول والتكامل والأمن C01–C14

**الحالة:** ACCEPTANCE TEST CATALOG DRAFT / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المصادر:** 19، 25، 59–70، 73، 75، 77–79.  
**الحدود:** هذا كتالوج تخطيط للاختبارات ومعاييرها، وليس تقرير نتائج. لا توجد في هذه الوثيقة بيئة اختبار أو run IDs أو artifacts أو نتائج ناجحة. المعرفات ذات DRAFT أو SPECIFIED لا تعني أنها منفذة.

## 1. قواعد حالة الاختبار

| الحالة | المعنى |
|---|---|
| SOURCE-ID-RECOVERED | معرف اختبار موجود في المصدر، وقد يحتاج نصه الكامل إلى استعادة |
| SPECIFIED | سيناريو ومعيار قبول موصوفان في مسودة |
| READY-FOR-REVIEW | الوصف مكتمل مبدئياً لكن يحتاج اعتماد المالك |
| BLOCKED | لا يمكن تنفيذ الاختبار قبل حسم قرار/بيئة/بيانات |
| EXECUTED | لا تستخدم إلا مع run ID وcommit/build وenvironment وevidence |
| PASSED / FAILED | لا تسجل إلا بعد تنفيذ موثق |

لا يجوز ترقية أي اختبار إلى EXECUTED/PASSED من مجرد كتابة السيناريو أو وجود workflow.

## 2. مصفوفة اختبار المراكز

| المركز | تغطية الاختبار | المعرفات/المجموعات | معايير القبول الأساسية | الحالة الحالية |
|---|---|---|---|---|
| C01 | إنشاء المشروع والنطاق؛ تحليل AI؛ المتطلبات والنسخ؛ NFR؛ الأسئلة؛ خط الأساس والموافقة | DRAFT-AT-C01-01..12؛ مجموعات create/update/analysis/requirements/baseline | tenant/project isolation؛ evidence provenance؛ AI draft only؛ expected_version؛ baseline approval مستقل | SOURCE IDs موجودة في 19؛ المسودة 59 تضيف حالات؛ غير منفذ |
| C02 | التصميم والإصدارات والمكونات والمعاينة واقتراح AI والاعتماد | DRAFT-AT-C02-01..15؛ C02-DRAFT-AT-16..20 | منع cross-project access؛ optimistic concurrency؛ منع تطبيق AI تلقائياً؛ approved hash/version | SOURCE IDs؛ الحالات الإضافية SPECIFIED؛ غير منفذ |
| C03 | الصفحات والمسارات والتنقل والروابط العميقة والوصول والاعتماد | DRAFT-AT-C03-01..14؛ C03-DRAFT-AT-15..20 | منع route target اليتيم؛ كشف orphan/cycle؛ server authorization؛ عدم تغيير مسار معتمد دون approval | SOURCE IDs؛ الإضافية SPECIFIED؛ غير منفذ |
| C04 | مصادر البيانات والمخطط والحقول والعلاقات وRLS والاستعلام والترحيل والنسخ والاستعادة | DRAFT-AT-C04-01..17؛ C04-DRAFT-AT-18..28 | لا أسرار؛ tenant-safe relations؛ dry-run؛ موافقة إنتاج؛ integrity بعد restore؛ sync reauthorization | SOURCE IDs؛ الإضافية SPECIFIED؛ restore/RTO-RPO BLOCKED |
| C05 | IR والتوليد والملفات والتبعيات وSandbox وGitHub والمراجعة والتسليم | C05-AT-01..20؛ C05-DRAFT-AT-21..32 | source traceability؛ patch hash؛ sandbox isolation؛ network deny by default؛ no self-approval؛ provenance | SOURCE ID family؛ النص الأصلي الكامل لبعض الحالات يحتاج استعادة؛ الإضافية SPECIFIED؛ غير منفذ |
| C06 | الوكلاء والأدوات والمنح والمهام والذاكرة والمزودين والتكلفة والمخرجات | C06-AT-01..20؛ C06-DRAFT-AT-21..27 | grant scope/expiry؛ memory isolation؛ budget enforcement؛ no audit tampering؛ independent review | SOURCE ID family؛ الإضافية SPECIFIED؛ غير منفذ |
| C07 | خطط الاختبار والحالات والتغطية والتشغيل والعيوب والبوابات والاستثناءات | C07-AT-01..22؛ C07-DRAFT-AT-23..28 | build/environment/test version evidence؛ retest before verified؛ waiver expiry؛ independent approval | SOURCE ID family؛ الإضافية SPECIFIED؛ غير منفذ |
| C08 | سجل الأصل والرفع والفحص والإصدارات والروابط والتصدير والحذف والاحتفاظ | حالات C08 في 64؛ ASSET + AUTH-78 + OWN-01 | فصل domain asset عن binary storage؛ ACL وقت الوصول؛ checksum/version provenance؛ retention/legal hold | حالات مسودة؛ OWN-01 OPEN؛ غير منفذ |
| C09 | الموصلات وOAuth والمزامنة وwebhooks والأسرار والإلغاء | حالات C09 في 65؛ INTEGRATION + EVT-79 | لا secret value؛ redirect/signature validation؛ replay/idempotency؛ external-write approval | حالات مسودة؛ OWN-02 OPEN؛ غير منفذ |
| C10 | السياسات والمخاطر والخصوصية والاستثناءات والوكلاء والحوادث والتدقيق | حالات C10 في 66؛ AUTH-78 + APR-79 | centralized decision؛ fail closed للحساس؛ independent exception approval؛ immutable audit source | حالات مسودة؛ Zero Trust conflict OPEN؛ غير منفذ |
| C11 | البناء والتوقيع والإصدار والنشر والترقية والفحوص والتراجع | حالات C11 في 67؛ RELEASE + APR-79 | artifact provenance؛ QA/security gate؛ approval version/hash؛ post-deploy evidence؛ rollback safety | حالات مسودة؛ artifact registry TBD؛ غير منفذ |
| C12 | المحتوى والنسخ والمصادر والمراجعة والنشر والبحث والتصدير والأرشفة | حالات C12 في 68؛ KNOWLEDGE + AUTH-78 | provenance؛ immutable approved version؛ ACL recheck؛ حذف/تراجع الفهرس بعد سحب المصدر | حالات مسودة؛ OWN-04 OPEN؛ غير منفذ |
| C13 | الإعدادات والتوريث والقيمة الفعالة والتغيير والموافقة والتراجع والتصدير | حالات C13 في 69؛ SETTINGS + APR-79 | scope precedence؛ expected_version؛ owner API only؛ no secret export | حالات مسودة؛ precedence OPEN؛ غير منفذ |
| C14 | الاستعلامات والتجميع والحداثة والتخصيص والتصدير | حالات C14 في 70؛ DASHBOARD + AUTH-78 | read-only projection؛ source ACL؛ freshness/as_of؛ layout write لا يغير ACL | حالات مسودة؛ غير منفذ |

## 3. اختبارات التكامل المشتركة

| ID | المجال | السيناريو | معيار القبول | الحالة |
|---|---|---|---|---|
| INT-80-01 | Domain SoR | كتابة المورد نفسه من مركزين | يرفض مصدر الكتابة المكرر أو يثبت الفصل بين الحقيقة والإسقاط | SPECIFIED / NOT RUN |
| INT-80-02 | Cross-center API | مركز يقرأ بيانات مركز آخر عبر DB مباشرة | لا وصول مباشر؛ يمر عبر عقد المالك | SPECIFIED / NOT RUN |
| INT-80-03 | Scope propagation | event/job بلا tenant/project scope المطلوب | quarantine/reject دون تنفيذ أثر | SPECIFIED / NOT RUN |
| INT-80-04 | Contract version | consumer يستقبل إصداراً غير مدعوم | توافق موثق أو رفض آمن | SPECIFIED / NOT RUN |
| INT-80-05 | Idempotency | command/event مكرر | أثر واحد فقط ضمن نافذة idempotency المعتمدة | SPECIFIED / NOT RUN |
| INT-80-06 | Concurrency | تعديلان متزامنان على base version واحدة | كشف conflict دون lost update | SPECIFIED / NOT RUN |
| INT-80-07 | Approval binding | موافقة على hash مختلف عن المورد الجاري تنفيذه | رفض التنفيذ | SPECIFIED / NOT RUN |
| INT-80-08 | Revocation | سحب grant أثناء job أو قبل event replay | recheck ثم منع الأثر غير المصرح | SPECIFIED / NOT RUN |
| INT-80-09 | Audit dependency | تعطل SH-04 في عملية audit-required | fail closed أو سلوك طوارئ معتمد صراحة؛ لا تجاوز صامت | SPECIFIED / NOT RUN |
| INT-80-10 | Approval dependency | تعطل SH-03 في عملية تتطلب موافقة | لا تنفيذ دون قرار صحيح | SPECIFIED / NOT RUN |
| INT-80-11 | Search ACL | سحب صلاحية ثم البحث/التصدير | لا نتائج/محتوى من cache أو index بعد سحب الصلاحية | SPECIFIED / NOT RUN |
| INT-80-12 | Secrets | البحث في DB/logs/events/prompts عن secret values | لا توجد قيم سرية؛ references فقط | SPECIFIED / NOT RUN |
| INT-80-13 | C14 projection | اختلاف projection عن مصدر الحقيقة | يظهر source/version/as_of/freshness ولا يكتب إلى المصدر | SPECIFIED / NOT RUN |
| INT-80-14 | AI governance | وكيل يتجاوز tool scope أو budget أو deadline | إيقاف/رفض وتسجيل الأثر | SPECIFIED / NOT RUN |
| INT-80-15 | Separation of duties | نفس actor ينشئ ويعتمد عملية حساسة | رفض عند اشتراط reviewer مستقل | SPECIFIED / NOT RUN |
| INT-80-16 | Retention | طلب حذف مورد تحت legal hold | منع الحذف حتى قرار صحيح | BLOCKED: policy/legal hold TBD |
| INT-80-17 | Recovery | استعادة متعددة المراكز/المؤسسة | تحقق integrity والعزل والاتساق وفق RTO/RPO معتمدين | BLOCKED: RTO/RPO TBD |
| INT-80-18 | Migration | تغيير schema يؤثر في عدة مراكز | dependency plan + dry run + rollback/backfill + approval | BLOCKED: ownership/migration policy OPEN |

## 4. اختبارات الأمن والخصوصية السلبية

| ID | الاختبار | معيار القبول | الحالة |
|---|---|---|---|
| SEC-80-01 | تبديل organizationId في payload | رفض خادمي |
| SEC-80-02 | استخدام projectRef من tenant آخر | رفض دون كشف metadata |
| SEC-80-03 | الوصول إلى object storage URL بعد انتهاء ACL | رفض/انتهاء الرابط وفق السياسة |
| SEC-80-04 | استخدام signed URL بعد revoke أو expiry | لا يمنح وصولاً خارج السياسة |
| SEC-80-05 | محاولة SQL/query injection في data/query designer | validation + parameterization + policy enforcement |
| SEC-80-06 | محاولة Sandbox الوصول إلى host filesystem/secrets | منع وتسجيل finding |
| SEC-80-07 | محاولة Sandbox network egress افتراضياً | منع |
| SEC-80-08 | replay webhook أو event | رفض التكرار/منع side effect |
| SEC-80-09 | وكيل يقرأ ذاكرة مشروع آخر | منع |
| SEC-80-10 | وكيل يغير policy/grant/audit أو يعتمد نفسه | منع |
| SEC-80-11 | تصدير بيانات شخصية دون purpose/approval | منع وفق السياسة |
| SEC-80-12 | البحث عن بيانات مصنفة دون صلاحية | لا تظهر snippets أو counts كاشفة |
| SEC-80-13 | فشل خدمة التفويض أثناء أمر حساس | fail closed |
| SEC-80-14 | فشل فحص malware/asset scanning | quarantine؛ لا نشر الأصل |
| SEC-80-15 | secret في logs/diff/AI prompt | منع/تنقيح وتنبيه |
| SEC-80-16 | استثناء أمني منتهي | لا يستمر أثره |

جميع الحالات SPECIFIED / NOT RUN حتى يتم تنفيذها في بيئة معتمدة.

## 5. قالب إثبات تنفيذ الاختبار

يجب أن يحتوي سجل الاختبار الفعلي على:
- testId وtestVersion وapprovedExpectedResult.
- repository commit SHA وbuild/artifact SHA.
- environmentId/platform/runtime/provider versions.
- tenant/project fixture IDs غير حقيقية الإنتاج.
- actor identity/role/grant/policy version (مع التنقيح).
- requestId/correlationId/jobId/eventId عند الانطباق.
- خطوات التنفيذ والنتيجة الفعلية ووقت التنفيذ.
- logs/trace/evidence refs مع content hashes.
- reviewer/approval عند الاختبار الأمني أو الحساس.
- status: PASSED/FAILED/BLOCKED مع سبب واضح.

غياب أي دليل أساسي يمنع إعلان الاختبار PASSED.

## 6. بوابات الدخول إلى الاختبار

1. اعتماد مالك المجال والعقد والنتيجة المتوقعة.
2. اكتمال schemas وبيانات الاختبار المصطنعة وتصنيفها.
3. توفر بيئة معزولة ومحددة الإصدار.
4. توفر actor/grant/policy fixtures قابلة للتكرار.
5. تعريف setup/teardown وreset policy.
6. تحديد test oracle وevidence retention.
7. ربط الاختبار بمتطلب ومحور وقرار/عقد.
8. مراجعة الأمن والخصوصية لاختبارات العزل والتصدير.
9. لا تختبر بيانات إنتاج أو أسرار فعلية في بيئة غير معتمدة.
10. لا تبدأ الاختبارات التنفيذية قبل اعتماد المالكين وموافقة بدء التنفيذ.

## 7. فجوات تمنع إعلان الجاهزية

- نصوص بعض عائلات C05/C06/C07 المصدرية لم تستعد كاملة في هذه المراجعة.
- لا توجد schemas نهائية أو environments أو providers approved.
- لا توجد RTO/RPO/SLO أو freshness targets معتمدة.
- لم تحسم retention/legal hold/classification.
- لم تحسم ملكية migration/artifact registry/connector lifecycle.
- لا توجد run IDs أو build IDs أو artifacts تنفيذية.
- لم يُعد حساب 57 من المحاور الـ64 بعد إدراج المسودات 59–79.
- OWN-01..10 وFLD-01..26 لا تزال مفتوحة.

**ACCEPTANCE TEST CATALOG / REVIEW CANDIDATE / NOT APPROVED / NOT EXECUTED.**


## ملحق 82 — مرجع قرار الملكية في الاختبارات

أي اختبار ملكية أو صلاحيات أو عقد بين المراكز يجب أن يشير إلى معرف OWN مؤهل (OWN36-nn أو OWN72-nn) وإلى المتطلب والعقد المقابلين. الإحالة المختصرة OWN-01..10 ملتبسة ولا تكفي لتتبع الاختبار. راجع الوثيقة 82. لا يغير ذلك حالات الاختبارات: جميعها SPECIFIED / NOT RUN أو BLOCKED بحسب السجل، ولا توجد نتائج تنفيذ مثبتة.


## ملحق 84 — اختبارات tenant/workspace/role ownership

اربط اختبارات scope وmembership وrole assignment بحزمة القرار 84: TENANT-84-T01..T05 وROLE-84-T01..T10. تظل هذه الحالات SPECIFIED / NOT RUN أو BLOCKED حيث نصت الحزمة؛ لا تحولها إلى نتائج تنفيذ. وتبقى INT-80-03 وSEC-80-01/02/13 وINT-80-15 نقاط ربط مشتركة.


## ملحق 85 — اختبارات ملكية الإصدارات

أضيفت حالات VER-85-T01..T12 في الوثيقة 85 لفصل SH-10 metadata عن محتوى المجال وC11 release lifecycle وartifact registry. تبقى الحالات SPECIFIED / NOT RUN أو BLOCKED بحسب الحزمة، ولا توجد أدلة تنفيذ.


## ملاحق 86–89 — اختبارات الإعدادات والنسخ والخصوصية والتعافي

أضيفت مجموعات CFG-86-T01..T10 وBAK-87-T01..T12 وPRIV-88-T01..T12 وOPS-89-T01..T08. جميعها SPECIFIED / NOT RUN أو BLOCKED حيث أشير إلى TBD. تعارض Zero Trust لا يعد نتيجة اختبار ولا قراراً معتمداً. يلزم تسجيل run/build/environment/actor/evidence عند التنفيذ الفعلي.


## ملحق 90 — اختبارات ملكية المعرفة

أضيفت KNOW-90-T01..T12 في الوثيقة 90 لاختبار ACL عند retrieval، عزل النطاقات، freshness، provenance، citations، prompt injection في المصادر، حذف المشتقات، واعتماد المحتوى المولد. جميعها SPECIFIED / NOT RUN أو BLOCKED؛ لا توجد نتائج تنفيذ.



## ملحق 91–93 — اختبارات ملكية الخدمات المشتركة

| الحزمة | مجموعة الاختبارات | التغطية | الحالة |
|---|---|---|---|
| 91 / SH-13 | INT-91-T01..T12 | SSRF/OAuth/secrets/webhooks/idempotency/approval/tenant isolation/revocation | SPECIFIED / NOT RUN أو BLOCKED |
| 92 / SH-08 | TASK-92-T01..T14 | idempotency/re-authorization/worker lease/cancellation/dependencies/tenant isolation/replay/domain-result separation | SPECIFIED / NOT RUN أو BLOCKED |
| 93 / SH-14 | MON-93-T01..T14 | stale telemetry/health taxonomy/alert provenance/C07 separation/C14 read projection/privacy/tenant isolation | SPECIFIED / NOT RUN أو BLOCKED |

تظل هذه اختبارات موصوفة لا منفذة. لا تسجل PASS دون test run ID وcommit/build وenvironment وactor والبيانات والأدلة وفق شروط هذه الوثيقة.



## ملحق 96 — اختبارات سجل الأحداث الموحد

أضيفت EVT-96-T01..T12 في الوثيقة 96 لفحص تغطية أحداث C01–C14 ومالك المعنى والمنتج والنسخة والنطاق والتكرار والربط بمحور AX وقرار OWN واختبار القبول. جميعها SPECIFIED / NOT RUN.

## ملحق 94–95 — ربط الاختبارات بالعقود المفردة

تؤكد مراجعة 94 أن مجموعات الاختبارات في هذا الملف لا ترتبط بعد بكل عملية API وحدث وموافقة على نحو مفرد. يجب أن يضاف لكل عقد test ID أو N/A مبرر من المالك، مع محور AX/source وقرار OWN مؤهل. تظل API-79 وEVT-79 وAPR-79 وINT-80 وSEC-80 واختبارات 91–95 SPECIFIED / NOT RUN أو BLOCKED؛ لا توجد run IDs جديدة.

أضيفت مجموعة ART-95-T01..T14 في الوثيقة 95 لفحص artifact digest/provenance/signature/immutability/tenant access/promotion/revocation/retention/recovery. تبقى الاختبارات SPECIFIED / NOT RUN أو BLOCKED.



## ملحق 97 — ربط الاختبارات على مستوى العملية

أضيفت الوثيقة 97 لتوفير سجل 201 عملية مفردة، مع عائلة الاختبار الخاصة بكل مركز. لا يزال exact test ID لكل عملية TBD/OWNER REVIEW؛ وجود عائلة اختبار لا يثبت تغطية العملية. تبقى OP-97-T01..T08 SPECIFIED / NOT RUN.



## ملحق 98 — اختبارات سجل أنواع الموافقات

أضيفت APR-98-T01..T12 لاختبار ربط الموافقة بنسخة/بصمة المورد، صلاحية الموافق، فصل المهام، الانتهاء والسحب، إعادة التحقق، fail-closed، والتصدير متعدد المجالات. الاختبارات SPECIFIED / NOT RUN.



## ملحق 99 — فجوة الاختبار المفرد

تربط الوثيقة 99 كل عملية بعائلة اختبار مركزها، لكنها لا تدعي وجود تغطية اختبار مفرد. يبقى TRACE-99-T05 BLOCKED حتى اعتماد Test ID لكل عملية أو N/A مبرر، وإضافة شروط التنفيذ والبيئة والأدلة.



## ملحق 99.1 — مصدر العقد الوسيط

تضيف الوثيقة 99 مرجع العقد 59–70 لكل عملية إلى جانب عائلة اختبار المركز. ما زال exact test ID لكل عملية وlocator المصدر الأصلي غير محسومين؛ لا تعتبر هذه الإضافة دليلاً على تغطية الاختبارات.



## ملحق 100 — تتبع قبول الأحداث

تحدد الوثيقة 100 عدد 106 أحداث منفردة وتربطها مؤقتاً بعائلات الاختبارات العامة EVT-96-T01..12. لا يوجد بعد exact test ID لكل حدث؛ EVT-100-T06 BLOCKED حتى اعتماد حالات الاختبار الفردية والأدلة.
