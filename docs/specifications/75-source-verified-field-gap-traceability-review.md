# 75 — التحقق المصدرّي لخريطة فجوات الحقول ومحاور المتطلبات

**الحالة:** SOURCE-VERIFIED TRACEABILITY REVIEW / OWNER REVIEW REQUIRED / NOT APPROVED  
**التاريخ:** 2026-11-15  
**المراجع:** 45 (AX-001..010)، 46 (AX-011..016)، 47 (AX-017..024)، 48 (AX-025..040)، 49 (AX-041..064)، والوثيقتان 73 و74.

## 1. منهج التحقق

تم فتح ملفات التدقيق التفصيلية الفعلية:
- 45-batch-a-axes-001-010-detailed-audit.md
- 46-batch-b-axes-011-016-detailed-audit.md
- 47-batch-c-axes-017-024-detailed-audit.md
- 48-batch-d-axes-025-040-detailed-audit.md
- 49-batch-e-axes-041-064-detailed-audit.md

تم التحقق من عناوين المحاور ومقاطع المتاح/الناقص/المراجع وحالة الحقول في الأقسام التفصيلية ذات الصلة. أسماء الملفات هذه هي الأسماء الصحيحة؛ أما المسار العام 45-requirements-axes-traceability-audit.md وما شابهه فليس موجوداً.

النتائج: SUPPORTED = اتصال مصدرّي واضح؛ PARTIAL = اتصال جزئي أو تفصيل مشتق؛ NARROWED = ربط سابق أوسع من اللازم وتم تضييقه. جميع النتائج لا تزال OWNER-REVIEW ولا تغلق فجوة تنفيذية أو قرار ملكية.

## 2. مصفوفة التحقق المصدرّي

| الفجوة | المحاور بعد المراجعة | الدليل التفصيلي | نتيجة التحقق |
|---|---|---|---|
| FLD-01 organization_id/tenant_id | AX-061، AX-012؛ AX-030 ثانوي | 49/AX-061 يطلب tenant context عبر endpoint/worker/search/cache/storage/export/backup؛ 46/AX-012 يذكر نموذج tenant/workspace/project؛ 48/AX-030 المشاريع المتعددة | SUPPORTED؛ AX-030 تابع فقط، والجوهر عزل المؤسسة والهوية |
| FLD-02 نقص project_requirements | AX-004، AX-001؛ AX-025 ثانوي | 45/AX-004 يحدد نقص مخطط الحوار والأسئلة والإجابات والمصادر والثقة وإصدار المتطلبات؛ 45/AX-001 يطلب capabilities-to-requirements traceability؛ 48/AX-025 رحلة المشروع | SUPPORTED؛ AX-004 هو الأقوى |
| FLD-03 design_pages/page_registry | AX-008، AX-002؛ AX-035 ثانوي | 45/AX-008 يطلب schema موحداً للصفحة والمسار والانتقال؛ 45/AX-002 التصميم قبل البرمجة؛ 48/AX-035 ذكاء التصميم | SUPPORTED؛ فصل C03 لهوية الصفحة وC02 للوحة التصميم اقتراح يحتاج اعتماداً |
| FLD-04 component definitions/generated components | AX-007، AX-009، AX-010؛ AX-028 ثانوي | 45/AX-007 schema ونسخ وslots/props/events وتوافق؛ AX-009 الدمج اليدوي/AI؛ AX-010 صيغة workflow؛ 48/AX-028 توليد الكود | SUPPORTED؛ عقد توافق التصميم والتنفيذ لا يزال مطلوباً |
| FLD-05 schema/migration مقابل سجلات المجال | AX-011؛ AX-042 ثانوي | 46/AX-011 يطلب schema نهائياً لكل مركز وملكية الحقول ومصدر الحقيقة واستراتيجية migrations؛ 49/AX-042 إدارة البيانات والتحليلات | SUPPORTED؛ AX-011 أساسي |
| FLD-06 ai_tasks مقابل SH-08 | AX-017، AX-036؛ AX-005 ثانوي | 47/AX-017 فريق AI؛ 48/AX-036 سير العمل والمهام؛ 45/AX-005 الاستقلالية والموافقات | PARTIAL؛ فصل task intent عن generic job توصية مشتقة من عقود الخدمات، لا قرار صريح في المحاور |
| FLD-07 AI usage مقابل SH-11 | AX-018، AX-033، AX-047 | 47/AX-018 تعدد المزودين والنماذج؛ 48/AX-033 التسعير والتكاليف؛ 49/AX-047 التكاليف والموارد | SUPPORTED؛ يلزم توحيد القياس عبر SH-11 واعتماد حدود سجل المزود |
| FLD-08 qa_test_runs مقابل jobs/health | AX-015، AX-044؛ AX-031 ثانوي | 46/AX-015 الاختبار والجودة؛ 49/AX-044 الاختبارات الآلية؛ 48/AX-031 المراقبة والتشخيص | SUPPORTED؛ فصل test result عن job attempt وruntime health توصية ملكية غير معتمدة |
| FLD-09 asset records/versions مقابل SH-06 | AX-014؛ AX-046 ثانوي | 46/AX-014 MED-F01..14 ودورة الملفات والإصدارات والتخزين؛ 49/AX-046 فهم الصور والمستندات | SUPPORTED؛ AX-014 أساسي |
| FLD-10 asset grants مقابل SH-02 | AX-014، AX-012، AX-023 | 46/AX-014 صلاحيات الملفات؛ 46/AX-012 التفويض؛ 47/AX-023 الأمن | SUPPORTED؛ اعتماد مصفوفة صلاحيات الملفات مطلوب |
| FLD-11 integration_runs مقابل SH-08 | AX-040؛ AX-022 ثانوي | 48/AX-040 APIs والتكاملات؛ 47/AX-022 تكامل GitHub | SUPPORTED؛ AX-040 عام وAX-022 مثال متخصص |
| FLD-12 connector/secret metadata مقابل SH-13 | AX-040، AX-023؛ AX-022 ثانوي | 48/AX-040 التكاملات؛ 47/AX-023 الأمن؛ 47/AX-022 GitHub | PARTIAL؛ تفاصيل connector/secret مصدرها الأهم عقود SH-13 في 23/32 |
| FLD-13 C10 policy decisions مقابل SH-02 | AX-012، AX-023، AX-063 | 46/AX-012 يفصل المصادقة عن التفويض؛ 47/AX-023 الأمن؛ 49/AX-063 حوكمة AI والتفويض | SUPPORTED؛ يلزم اعتماد فصل تأليف السياسة عن تقييم القرار |
| FLD-14 C10 audit events مقابل SH-04 | AX-023، AX-063؛ AX-051 ثانوي | 47/AX-023 الأمن؛ 49/AX-063 سجل تدقيق لا يعدله الوكيل؛ 49/AX-051 الحوادث والارتباط | SUPPORTED؛ SH-04 مرجع الخدمة المشتركة |
| FLD-15 C11 job state مقابل SH-08 | AX-016، AX-043؛ AX-036 ثانوي | 46/AX-016 البناء والنشر؛ 49/AX-043 دورة حياة التطبيق؛ 48/AX-036 المهام | SUPPORTED؛ فصل domain lifecycle عن job attempts يحتاج اعتماداً |
| FLD-16 artifact registry/storage | AX-016، AX-028، AX-039؛ AX-052 ثانوي | 46/AX-016 build/deploy؛ 48/AX-028 توليد الكود؛ 48/AX-039 جودة الكود؛ 49/AX-052 التراخيص | SUPPORTED؛ AX-052 يضيف SBOM/licensing ولا يحسم مالك registry |
| FLD-17 C12 documents مقابل SH-16 | AX-021، AX-046؛ AX-056 ثانوي | 47/AX-021 المعرفة والتوثيق؛ 49/AX-046 فهم المستندات؛ 49/AX-056 التوثيق القانوني | SUPPORTED؛ canonical content مقابل ingestion/index يحتاج قرار C12/SH-16 |
| FLD-18 knowledge search مقابل SH-07 | AX-048، AX-021 | 49/AX-048 search schema وACL filtering وreindexing وحذف الفهرس؛ 47/AX-021 المعرفة | SUPPORTED؛ AX-048 أساسي |
| FLD-19 C13 settings مقابل SH-12 | AX-024، AX-034، AX-057؛ AX-049 ثانوي | 47/AX-024 واجهة الإدارة؛ 48/AX-034 اللغات؛ 49/AX-057 التخصيص؛ 49/AX-049 المزامنة | PARTIAL؛ ملكية القيم والأولوية مصدرها SH-12 وSET لا عناوين المحاور وحدها |
| FLD-20 C14 projections/freshness/ACL | AX-024، AX-031، AX-058؛ AX-047 ثانوي | 47/AX-024 واجهة المصمم؛ 48/AX-031 المراقبة؛ 49/AX-058 التحليلات؛ 49/AX-047 الموارد | SUPPORTED؛ freshness وACL يجب ربطهما بمصدر المؤشر |
| FLD-21 actor/timestamps/provenance | AX-023، AX-063، AX-040، AX-054 | 47/AX-023 الأمن؛ 49/AX-063 الحوكمة؛ 48/AX-040 APIs؛ 49/AX-054 الخصوصية | NARROWED؛ أزيل التعميم AX-001..064؛ يطبق envelope مشترك مع تحقق حسب المجال |
| FLD-22 retention/delete/legal hold | AX-054، AX-014، AX-021، AX-037؛ AX-056 ثانوي | 49/AX-054 التصنيف والاحتفاظ والحذف؛ 46/AX-014 دورة الملف؛ 47/AX-021 المعرفة؛ 48/AX-037 النسخ والاستعادة؛ 49/AX-056 قانوني | SUPPORTED؛ يلزم مالك سياسة لكل فئة |
| FLD-23 enums/state machine/versioning | AX-026، AX-036، AX-040، AX-043 | 48/AX-026 الإصدارات؛ 48/AX-036 المهام؛ 48/AX-040 API؛ 49/AX-043 lifecycle | NARROWED؛ أزيل التعميم إلى جميع المحاور؛ state machine حسب نوع الكيان |
| FLD-24 cross-project/global references | AX-061، AX-030، AX-038؛ AX-014 ثانوي | 49/AX-061 عزل المؤسسة؛ 48/AX-030 المشاريع؛ 48/AX-038 الاستيراد/التصدير/النقل؛ 46/AX-014 الملفات | SUPPORTED؛ AX-061 هو محور الحماية الأساسي |
| FLD-25 domain owner مقابل created_by | AX-011، AX-012، AX-023، AX-063 | 46/AX-011 ملكية الحقول ومصدر الحقيقة؛ 46/AX-012 الهوية والأدوار؛ 47/AX-023 الأمن؛ 49/AX-063 الحوكمة | PARTIAL؛ قاموس domain owner/steward/creator مشتق وليس حقلاً مصدرياً مكتملاً |
| FLD-26 classification/legal basis | AX-054، AX-056، AX-014، AX-021، AX-061 | 49/AX-054 التصنيف والغرض والاحتفاظ؛ 49/AX-056 القانوني؛ 46/AX-014 الأصول؛ 47/AX-021 المعرفة؛ 49/AX-061 العزل | SUPPORTED؛ مالك المجال يعلن التصنيف والغرض وC10 يحكم الضوابط |

## 3. تصحيحات واجبة على الوثيقة 74

1. FLD-21: استبدال secondary AX-001..AX-064 بمجموعة محددة؛ التتبع المشترك لا يجعل كل محور متأثراً مباشرة.
2. FLD-23: استبدال secondary AX-001..AX-064 بالمحاور التي تملك lifecycle/state فعلياً.
3. FLD-25: إبقاء الربط جزئياً؛ قاموس domain owner/steward/creator مشتق.
4. FLD-06/08/11/15: وصف فصل domain intent/result عن generic execution كتوصية ownership مشتقة، لا نص صريح في متطلبات المحاور.
5. FLD-12/19: إبقاء الربط جزئياً؛ تفاصيل SH-13 وSH-12 تحتاج مراجعة عقود الخدمات.
6. لا تتغير حالات DATA/API/AUTH/TEST في 57؛ اتصال المصدر لا يعني schema معتمداً أو اختباراً منفذاً.

## 4. قرارات الملكية التي لا تزال تمنع الإغلاق

| الفجوات | القرار المطلوب | أطراف المراجعة المقترحة |
|---|---|---|
| FLD-01/24/25/26 | قاموس النطاق والفاعل والمالك والتصنيف | C10، SH-01/02/04، ملاك المجالات |
| FLD-03/04 | هوية الصفحة ومخطط المكونات وتوافق التصميم/الكود | C02، C03، C05 |
| FLD-05 | schema steward وmigration owner لكل كيان | C04 وكل domain owner |
| FLD-06/07/08/11/15 | فصل domain records عن shared job/usage/telemetry | C06/C07/C09/C11 وSH-08/11/14 |
| FLD-09/10/12 | سجل الأصل مقابل التخزين والاتصال والتفويض | C08/C09 وSH-02/06/13 |
| FLD-13/14 | policy authoring/evaluation وaudit ledger authority | C10 وSH-02/04 |
| FLD-16 | registry/storage authority وprovenance/SBOM | C05/C11/C10 |
| FLD-17/18 | canonical knowledge content وindex/search | C12 وSH-07/16 |
| FLD-19 | setting key owner وprecedence وversion semantics | C13 وSH-12 وملاك المفاتيح |
| FLD-20 | projection schema/freshness/ACL | C14 وكل ملاك المؤشرات |
| FLD-21..23 | audit envelope وlifecycle/state/enum policy | SH-04/10، C04، ملاك المجالات |

## 5. النتيجة وحدودها

- تمت مراجعة الأقسام التفصيلية ذات الصلة في تقارير 45–49، لا العناوين فقط.
- لكل فجوة FLD-01..26 نتيجة SUPPORTED أو PARTIAL أو NARROWED.
- ضُيّق التعميم غير الدقيق في FLD-21 وFLD-23 وFLD-25.
- جميع فجوات الملكية مفتوحة حتى اعتماد الأطراف المالكة.
- لم تتغير إحصاءات 57.
- لا توجد migrations أو APIs تنفيذية أو نتائج اختبارات جديدة.
- لا يبدأ تنفيذ كود التطبيق قبل إغلاق المواصفات وموافقة مستقلة صريحة.

**SOURCE-VERIFIED TRACEABILITY REVIEW / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION.**
