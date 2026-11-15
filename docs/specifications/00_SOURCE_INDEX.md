# فهرس المصدر الرئيسي للمواصفات المعتمدة

## الملف الرئيسي

| الملف | الوصف |
|--------|--------|
| [00_المواصفات_المعتمدة_الكاملة_مصدر_رئيسي.docx](./00_المواصفات_المعتمدة_الكاملة_مصدر_رئيسي.docx) | المصدر الكامل المعتمد (Word) |
| [00_MASTER_APPROVED_SPECIFICATIONS_SOURCE.docx](./00_MASTER_APPROVED_SPECIFICATIONS_SOURCE.docx) | نفس الملف باسم إنجليزي |

هذا المستند يُعتبر **مرجعاً رئيسياً** لمواصفات «مصمم التطبيقات الذكي» المعتمدة من سلسلة قرارات العمل، ويشمل على سبيل المثال لا الحصر:

- مركز معرفة مستقل لكل مشروع وتحديث الوثائق ومطابقتها للتطبيق الفعلي
- تكامل GitHub الاحترافي (مستودعات، فروع، PR، Actions، حماية)
- نظام الأمان متعدد الطبقات والأسرار وسجل التدقيق
- واجهة إدارة المصمم بـ 14 مركزاً والوضع البسيط/الاحترافي
- تجربة إنشاء مشروع ذكية على مراحل
- قرارات لاحقة: استيراد التطبيقات، تعدد المؤسسات، حوكمة الوكلاء، الاستمرارية (ADR-024 … ADR-028)
- فصول مستعادة وملاحظات تعارضات (مثل تعريف C14 و Zero Trust)

## علاقة الملفات الأخرى

- `00_MASTER_SPECIFICATION.md` / `.docx`: ملخص تنفيذي مهيكل للخطة والمراكز
- بقية ملفات `01`–`63` في هذا المجلد: تفصيل مرحلي وتدقيق
- `web-angular/`: واجهة MVP وتجربة متجري الحقيقية

## ملاحظة حوكمة

المواصفات المعتمدة هنا **لا تعني بدء التنفيذ البرمجي الكامل للمنصة** إلا بعد بوابة مراجعة نهائية وموافقة صريحة من مالك المشروع، وفق ما ورد في سجل القرارات وبوابات الاعتماد.


## تقارير المطابقة الإضافية

- [51 — مطابقة المصدر الرئيسي والنسخة النصية](51-master-source-and-text-derivative-reconciliation.md): يثبت وجود DOCX الرئيسي ويحلل Markdown الملخصة ويحدد الاختلافات التي تتطلب مطابقة المصدر الكامل.
- [52 — مسار استخراج DOCX الرئيسي](./52-master-docx-extraction-pipeline.md): أداة استخراج النص والجداول وفهرس الوسائط؛ نجحت على المصدر الحقيقي مع التحقق من بصمته.
- [53 — التدقيق البنيوي لمصدر DOCX](./53-master-docx-structure-and-content-audit.md): جرد الفصول والمحاور والوسائط والتكرارات والفجوات بعد استخراج المصدر الكامل.
- [54 — مصفوفة مطابقة المصدر بالوثائق](./54-master-source-to-specification-crosswalk.md): ربط فصول DOCX والتصديرات 04–07 بالوثائق الحالية وتحديد التغطية والفجوات.
- [55 — المطابقة التفصيلية للفصول 141–146 و158–163](./55-crosswalk-chapters-141-146-and-158-163.md): تفصيل المطابقة مع التصديرين 06 و07، مع الفصل بين إحالة 156 وفجوة 157.
- [56 — سجل التعارضات وحالة القرارات](./56-decision-conflict-and-source-status-register.md): تصنيف التعارضات والقرارات المقترحة والفجوات المصدرية مع الحفاظ على القرارات الأحدث المعتمدة.
- [57 — الملحق الموحد لحالات حقول المحاور الـ64](./57-64-axes-field-status-consolidated-index.md): يجمع حالات التتبع المسجلة في تقارير الدفعات A–E لجميع المحاور الـ64 دون ترقية المسودات أو الاختبارات غير المنفذة إلى اعتماد.
- [58 — تدقيق فجوات تسلسل الفصول 136–139 و156–157](./58-chapter-sequence-gaps-136-139-and-156-157.md): تصنيف ما ظهر كعنوان تمهيدي أو إحالة أو استكمال، وتحديد الفصول التي لا يتوفر لها متن مستقل.
- [59 — مسودة عقود C01 للبيانات والواجهات والصلاحيات](./59-c01-data-api-authorization-contract-draft.md): مسودة مشتقة من المصادر 01 و02 و19 و24 و25 لاستكمال أولي لفجوات C01؛ ليست اعتماداً أو تنفيذاً.
- [60 — مسودة عقود C02 وC03 للتصميم والتنقل](./60-c02-c03-design-navigation-contract-draft.md): مسودة مشتقة من الوثائق 01 و02 و19 و24 و25 لاستكمال أولي لعقود التصميم والتنقل؛ ليست اعتماداً أو تنفيذاً.
- [61 — مسودة عقود C04 للبيانات وقواعد البيانات](./61-c04-data-database-contract-draft.md): مسودة مشتقة من الوثائق 13 و19 و24 و25 لاستكمال أولي لعقود البيانات والمصادر والسياسات والترحيل والمزامنة والنسخ؛ ليست اعتماداً أو تنفيذاً.
- [62 — مسودة عقود C05 لمحرك البرمجة](./62-c05-code-engine-contract-draft.md): مسودة مشتقة من الوثائق 10 و12 و20 و25 و27 لعقود مساحة الكود وIR والتوليد وSandbox والمراجعة؛ ليست اعتماداً أو تنفيذاً.
- [63 — مسودة عقود C06 وC07 للذكاء الاصطناعي والجودة](./63-c06-c07-ai-quality-contract-draft.md): مسودة مشتقة من الوثائق 12 و20 و25 و27 لعقود الوكلاء والمهام والتفويض والمراجعة والاختبارات والجودة؛ ليست اعتماداً أو تنفيذاً.


## إضافات 2026-11-15 — عقود C08–C14 والمراجعة الموحدة

- `64-c08-files-media-contract-draft.md` إلى `70-c14-dashboard-contract-draft.md`: مسودات عقود C08–C14، مشتقة وغير معتمدة.
- `71-cross-center-contracts-final-consolidated-review.md`: مراجعة موحدة لعقود C01–C14 وملكية البيانات والواجهات والصلاحيات والاختبارات والتبعيات؛ مرشح مراجعة وليس اعتماداً نهائياً.

- `72-ownership-boundaries-and-dependency-decision-register.md`: سجل قرارات OWN-01..10 لفصل ملكية المجالات عن SH-01..SH-16، ومصفوفة RACI وبوابات العقود والاختبارات؛ مشتق وغير معتمد.
- `73-entity-field-ownership-reconciliation-c01-c14.md`: مطابقة مستوى الكيان والحقل للعقود 59–70 مع 23/32/36، وتحديد FLD-01..26؛ مسودة مشتقة غير معتمدة، ولا تمثل schema تنفيذية.
- `74-field-gap-to-requirements-axes-crosswalk.md`: خريطة ربط مشتقة للفجوات FLD-01..26 بمحاور AX-001..064؛ لا تعدل حالات الملحق 57 قبل التحقق من تقارير 45–49 وموافقة المالك.
- `75-source-verified-field-gap-traceability-review.md`: تحقق مصدرّي لروابط FLD-01..26 مقابل تقارير المحاور التفصيلية 45–49؛ يميز SUPPORTED/PARTIAL/NARROWED ولا يغلق موافقات المالكين.
- `76-ownership-and-field-gap-decision-packet.md`: حزمة قرارات OWN-01..10 وFLD-01..26 مع مالكي اعتماد بحسب الدور وشروط قبول؛ لا تسجل موافقات غير صادرة.
- `77-entity-field-catalog-c01-c14.md`: كتالوج موحد للكيانات والحقول ومصادر الحقيقة C01–C14، يعيد تنظيم الحقول المصرح بها في 73 والعقود 59–70، ويصنف الأنواع والقيود غير المذكورة TBD؛ مسودة غير معتمدة ولا تنشئ schema.

- `78-unified-authorization-matrix-c01-c14.md`: مصفوفة موحدة actor/action/resource/scope لعمليات C01–C14، مع شروط الموافقة والتدقيق واختبارات قبول مقترحة غير منفذة؛ مسودة غير معتمدة.

- `79-api-event-approval-contract-registry-c01-c14.md`: سجل موحد لعمليات API والأحداث ودورة الموافقات وغلاف الرسائل والأخطاء واختبارات القبول؛ يميز أسماء المصدر عن الاقتراحات، وغير معتمد وغير منفذ.

- `80-acceptance-test-catalog-c01-c14.md`: كتالوج اختبارات القبول والتكامل والأمن للمراكز، يميز معرفات المصدر عن الاختبارات الموصوفة والمنفذة، ويشترط run/build/environment/evidence قبل PASS؛ غير منفذ.

- `81-ownership-id-namespace-and-contract-consistency-audit.md`: تدقيق مصدرّي لتصادم معرفات OWN بين الوثيقتين 36 و72، ومطابقة 76–80، وتحديد فجوات SH-11 والمؤسسة/مساحة العمل والأدوار؛ غير معتمد ولا يمثل تنفيذًا.

- `82-qualified-ownership-decision-id-registry.md`: سجل namespace مؤهل لقرارات الملكية OWN36-nn وOWN72-nn، ومطابقة إحالات 77–80، وفجوات الملكية المستقلة؛ غير معتمد ولا ينشئ تغييرات تنفيذية.

- `83-sh11-cost-budget-ownership-decision-dossier.md`: حزمة قرارات SH-11 للتكلفة والميزانيات، وتفصل القياس عن ledger والتقديرات عن الفعلي وتحدد سياسات العملة والتسوية والاختبارات المفتوحة؛ غير معتمدة وغير منفذة.

- `84-tenant-workspace-role-ownership-decision-dossier.md`: حزمة قرارات ملكية tenant/organization/workspace والعضويات والأدوار والأذونات، وتفصل SH-01/SH-02/C10 عن مالك المجال؛ جميع القرارات مفتوحة وغير منفذة.

- `85-sh10-version-ownership-and-lifecycle-decision-dossier.md`: حزمة قرارات SH-10 لملكية metadata الإصدارات ومجموعات التغيير واللقطات، وفصلها عن محتوى المجال وC11 release lifecycle وartifact registry؛ غير معتمدة وغير منفذة.

- `86-sh12-configuration-ownership-and-precedence-decision-dossier.md`: ملكية SH-12 وأولوية الإعدادات والأسرار؛ مفتوحة وغير معتمدة.
- `87-sh15-backup-recovery-ownership-decision-dossier.md`: ملكية النسخ والاستعادة ومفاتيح التشفير وأهداف RTO/RPO؛ مفتوحة وغير منفذة.
- `88-privacy-retention-deletion-and-legal-hold-decision-dossier.md`: تصنيف البيانات والغرض والاحتفاظ والحذف والحجز القانوني؛ مفتوحة وغير معتمدة.
- `89-zero-trust-and-operational-recovery-objectives-decision-register.md`: تعارض Zero Trust وأهداف SLO/RTO/RPO/freshness؛ مفتوحة ولا تحسم الموافقات.

- `90-sh16-knowledge-ownership-and-c12-boundary-dossier.md`: حدود C12/SH-16/SH-07 وملكية المحتوى والفهرسة والاستشهاد وACL/provenance؛ مفتوحة وغير منفذة.



## إضافات 2026-11-15 — SH-13 وSH-08 وSH-14

- `91-sh13-integration-connector-ownership-decision-dossier.md`: حزمة ملكية SH-13 مقابل C09، مع مراجع الأسرار وOAuth/webhooks والمزامنة؛ جميع القرارات مفتوحة والاختبارات غير منفذة.
- `92-sh08-shared-task-execution-ownership-decision-dossier.md`: فصل Domain Job عن Shared Execution Job ونتائج المجال عن حالة التنفيذ؛ جميع القرارات مفتوحة.
- `93-sh14-operational-monitoring-ownership-decision-dossier.md`: فصل telemetry التشغيلية عن جودة C07 ونشر C11 وعرض C14؛ SLO/freshness والاحتفاظ ما زالت TBD.



## إضافة 2026-11-15 — سجل أحداث المراكز

- `96-center-event-inventory-and-producer-boundary-crosswalk.md`: سجل أحداث C01–C14، مع فصل أسماء SOURCE-RECOVERED عن PROPOSED-IN-79 وتحديد حدود المنتج/المستهلك. لا يعتمد schemas أو topics.

## إضافات 2026-11-15 — تدقيق traceability وArtifact Registry

- `94-api-event-approval-test-referential-integrity-audit.md`: تدقيق ربط API/Event/Approval/Tests؛ النتيجة PARTIAL، مع فجوات مفردة في producer/owner/AX/OWN/test.
- `95-c11-artifact-registry-ownership-decision-dossier.md`: حزمة قرار FLD-16 وVER-85-09 لفصل artifact record عن binary storage وprovenance/signing وrelease lifecycle؛ OPEN / NOT APPROVED.



## إضافة 2026-11-15 — سجل العمليات المفردة

- `97-operation-level-api-test-ownership-inventory.md`: تفكيك 201 عملية API من 79 إلى Operation IDs مفردة، مع type/API Owner candidates وعائلة اختبار وOWN/TBD؛ لا schemas أو endpoints منفذة.



## إضافة 2026-11-15 — سجل أنواع الموافقات

- `98-approval-type-registry-c01-c14.md`: جرد 27 نوع موافقة مرشحاً، وحقول القرار وضوابطه واختبارات القبول؛ لا workflows منفذة ولا قرارات معتمدة.



## إضافة 2026-11-15 — مصفوفة تتبع API

- `99-operation-axis-test-approval-traceability-matrix.md`: ربط 201 عملية بمحاور AX المرشحة وعائلات الاختبارات ومراجع الملكية، وربط 26 نوع موافقة بالعمليات؛ لا exact test IDs أو اعتماد مالكين.



تحديث الوثيقة 99: أضيف مرجع العقد الوسيط 59–70 لكل عملية API، مع إبقاء موضع الفصل/القسم/الفقرة الأصلي وExact Test ID مفتوحين.



## إضافة 2026-11-15 — سجل الأحداث المفرد

- `100-event-level-axis-ownership-test-traceability-register.md`: تفكيك 106 أحداث من سجل 96 إلى Event IDs منفردة، مع حالة المصدر ومحاور AX المرشحة؛ الملكية والمخططات والاختبارات الدقيقة غير معتمدة.



تحديث 100.1: صحح تصنيف حالة المصدر في سجل الأحداث؛ 95 SOURCE-RECOVERED و11 PROPOSED بعد المطابقة مع 96.


## إضافات 2026-11-15 — الإغلاق التتبعي الشامل ومطابقة المصدر

- `132-c01-c07-unified-traceability-closure-workpaper.md`: مراجعة 120 عملية و51 حدثاً و12 موافقة عبر C01–C07؛ تفصيل الملكية والتبعيات وفجوة APR-C07-02؛ NOT APPROVED / NOT RUN.
- `133-global-fourteen-center-specification-readiness-consolidation.md`: تجميع 64 محوراً و201 عملية و106 أحداث و26 موافقة و26 فجوة حقول، مع بوابة الجاهزية الكلية؛ NOT READY FOR CODING.
- `134-source-chapters-144-155-detailed-reconciliation.md`: مطابقة مباشرة للفصول 144–155 من التصدير 06 مع المستودع، بما فيها MRTM والمعمارية وخطة التنفيذ والجاهزية وهيكل MPRTS والخصوصية والعقود.

تظل هذه الملفات وثائق تتبع ومراجعة؛ لا تمثل اعتماداً للعقود أو اختباراً منفذاً أو بدءاً للبرمجة.


## إضافة 2026-11-15 — مطابقة الفصول 158–163

- `135-source-chapters-158-163-security-dependencies-decisions-and-release-reconciliation.md`: مطابقة المصدر 07 (blob `ce7141928dde38df1b3a6b41d6ceb89816aa565a`) للفصول 158–163، تشمل الأمن والاعتماديات وسجل القرارات والتدقيق النهائي وهيكل MPRTS ومبادئ الفصل بين اعتماد المواصفات وبدء البرمجة. تسجل تعارض تعريف C14 ونطاق Zero Trust، وتؤكد أن الجزء الموسع للفصل 163 يعرض C01–C07 فقط وأن C08–C14 متبقية في الجزء التالي.


- `136-c01-c07-chapter163-screen-function-id-crosswalk.md`: مطابقة معرفات الشاشات والوظائف والاختبارات في الفصل 163 للمراكز C01–C07 مع الكتالوجات 02/12/19/20، مع حفظ source IDs وعدم إعادة الترقيم أو الادعاء بتنفيذ الاختبارات.


- `137-cost-dashboard-architecture-audit-closure-workpaper.md`: مطابقة تفصيلية للفصول المصدرية 141–143 (export06 blob `1af316420b444ab1375d899e66322dd76fd5f816`)، تشمل نموذج التكلفة، C14 Dashboard، تدقيق المعمارية، تصادم COST/DASH IDs، والفجوات والاختبارات. معرّف 137 هو رقم وثيقة عمل وليس ادعاءً باستعادة الفصل المصدر 137.


## إضافة 2026-11-15 — مطابقة نموذج الاعتماديات في الفصل 159

- `138-chapter159-fourteen-center-dependency-model-and-validation-workpaper.md`: مطابقة مباشرة لأقسام 159.3–159.16 من التصدير 07: ستة أنواع اعتماديات، مصفوفة الاحتياجات للمراكز الأربعة عشر، نموذج الحالات والعقود والدورات والاستثناءات، 14 اختبار DEP، و10 قرارات مقترحة. لا تعتبر DEP tests منفذة أو مرتبطة exact بكل عملية/حدث/موافقة. الوثيقة 138 workpaper وليست فصلاً مصدرياً.


## إضافة 2026-11-15 — مطابقة الفصول 160–162

- `139-chapters160-162-decision-register-final-audit-and-mprts-release-workpaper.md`: مطابقة مباشرة للفصول 160 (سجل القرارات)، 161 (التدقيق النهائي)، 162 (هيكل MPRTS)، مع فصل APPROVED عن PROPOSED/OPEN، وربط اختبارات DEC-TEST وSPEC-AUD وDOC-TEST، وتوثيق فجوات الملكية والتتبع والتعارضات. MPRTS 1.0 غير صادرة والبرمجة غير مصرح بها.


## Workpaper 140 — Chapter 163 readiness gate

- **Source:** `docs/source-conversations/2026-11-10-export-part-07.txt`, chapter 163, lines 3832–4280 in the current source snapshot.
- **Derived audit:** `docs/specifications/140-chapter163-executive-summary-readiness-gate-crosswalk.md`.
- **Verified scope:** executive summary, 14-center list, principles, capabilities, logical architecture, gates G-01..G-15, deliverables and FINAL-01..07.
- **Important limitation:** chapter 163 is not a screen/function catalog and contains no execution evidence. Export 07 continues with detailed B-05/B-06/B-07 sections and explicitly points to C08 as the next part; C08–C14 detailed source text remains unrecovered in the inspected exports.
- **Readiness:** chapter text verified; all gate statuses remain partial/open as documented; MPRTS 1.0 not issued; specification approval not recorded; coding not authorized.


## Workpaper 141 — B-01..B-07 source ID registry

- **Source:** `docs/source-conversations/2026-11-10-export-part-07.txt`, sections B-01..B-07, lines 5359–13073.
- **Attribution correction:** the screen/function/acceptance IDs are in B-01..B-07, not in chapter 163. Chapter 163 (around lines 3832–4280) is the executive summary and approval gate.
- **Inventory:** 111 unique screen IDs, 109 unique function IDs, and 130 unique acceptance-test IDs across C01–C07.
- **Status:** source IDs verified; catalog mapping partial; acceptance definitions/evidence and owner approvals remain open; B-08..B-14 detail not recovered from Export 07.


## Workpaper 142 — acceptance ID / scenario reconciliation

- **Export 07:** 130 unique `Cxx-AT-nn` IDs across C01–C07 in B-01..B-07.
- **DOCX inventory 104:** 86 local `SRC-AT` locators across C01–C09; these are derived locators, not source Test IDs.
- **Equivalence:** no automatic one-to-one mapping is established between these groups or the derived DRAFT-AT catalog in document 80.
- **Execution:** all remain NOT RUN; no run evidence is established by ID presence.
- **Reference:** `docs/specifications/142-acceptance-id-vs-recovered-scenario-reconciliation.md`.


## Workpaper 143 — full source acceptance ID register

- **Source:** Export 07, sections B-01..B-07.
- **Recovered:** all 130 unique C01–C07 acceptance IDs, their scenario labels, and source line locators.
- **Source detail level:** 15 IDs have a separate expected-result entry in the source table; 115 have a scenario label only. No missing expected result was invented.
- **Execution:** NOT RUN; no run evidence.
- **Reference:** `docs/specifications/143-full-source-acceptance-id-register-c01-c07.md`.


## Workpaper 144 — source function ID registry

- **Source:** Export 07 B-01..B-07.
- **Recovered:** 109 unique source function IDs across C01–C07 with source titles and line locators.
- **Catalog gap:** C01–C04 source IDs are not preserved in current operational catalog 19; C05–C07 IDs are present in catalog 12 but still require content reconciliation.
- **No inference:** screen/function ordering does not establish equivalence; no operation or test mapping is approved by this registry.
- **Reference:** `docs/specifications/144-source-function-id-registry-and-catalog-gap-audit-c01-c07.md`.
