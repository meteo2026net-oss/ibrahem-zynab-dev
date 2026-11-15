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
