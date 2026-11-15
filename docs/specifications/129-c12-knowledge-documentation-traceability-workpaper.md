# 129 — حزمة تتبع C12 للمعرفة والتوثيق والمصادر والاختبارات

**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**النطاق:** C12 فقط، مع حدود SH-07/16 وSH-06/03/04/02 والمالكين الأصليين للمحتوى والملفات والبيانات.

## 1. النتيجة التنفيذية

| المحور | العدد/الحالة | التفسير |
|---|---:|---|
| عمليات C12 في سجل 97 | 12 | أسماء مشتقة/مرشحة وليست APIs منشورة |
| أحداث C12 في سجل 100 | 9 | SOURCE-RECOVERED دلالياً في السجل، لا تطابق حرفي مثبت |
| أنواع الموافقات في سجل 98 | 2 | OWNER REVIEW |
| سيناريوهات القبول المحلية | 22 | SPECIFIED / NOT RUN |
| Exact Event→Test ID مثبت | 0/9 | لا يوجد ربط مفرد أصلي مثبت |
| اعتماد مالك C12 النهائي | غير حاصل | لا اعتماد API/schema/ACL/retention |
| تنفيذ مركز المعرفة | غير مثبت | لا يوجد اختبار بحث/فهرسة/تصدير منفذ بهذه الحزمة |

الحزمة توسع التتبع ولا تعتمد مخطط بيانات أو API أو سياسة احتفاظ أو مزود OCR/AI/Search.

## 2. حدود الملكية ومصدر الحقيقة

C12 يملك الوثيقة المعرفية canonical التي أُنشئت أو اعتُمدت داخل مساحة المشروع: محتواها المنطقي، إصداراتها، حالة مراجعتها، علاقاتها، provenance links، قرار نشرها، وسياسة أرشفتها ضمن التفويض. أما الملف الثنائي الأصلي ومحتوى المجال فلا ينتقلان إلى C12.

| العنصر | مالك المجال/الخدمة | حد C12 |
|---|---|---|
| المستند الأصلي وbytes والصور والملفات | C08/SH-06 أو مالك المجال | يحفظ asset refs وhash ومواضع المصدر؛ لا يصبح مخزن الأصل |
| التصميم والكود وبيانات المجال | C02/C03/C04/C05 ومالك المجال | ينشئ توثيقاً ومراجع، ولا ينسخ المصدر ليصبح SoR بديل |
| نتائج الاختبارات | C07 | يربط تقرير الاختبار ونسخته ودليله؛ لا يعيد تعريف نتيجة الاختبار |
| قرارات المشروع والمتطلبات | C01/مراكز القرار | يربط القرار والنسخة والمالك |
| البحث العام | SH-07 | بحث واسترجاع عام حسب عقده وACL |
| الفهرسة الدلالية والـingestion | SH-16 | index/chunks/embeddings/provenance metadata مشتقات وليست محتوى أصلياً |
| الموافقات | SH-03 | يملك lifecycle العام؛ C12 يملك سياق مراجعة الوثيقة ومحتواها |
| الهوية والتفويض | SH-01/SH-02 | C12 يطبق التفويض ولا يقرر الهوية أو يتجاوز ACL |
| التدقيق | SH-04 | C12 يرسل actor/action/document/version refs؛ لا يملك سجل التدقيق |
| الخصوصية والاحتفاظ | C10 ومالك البيانات | يطبق سياسات معتمدة؛ لا يخترع مدد retention أو legal hold |

**قاعدة retrieval:** يجب إعادة التحقق من ACL عند الاسترجاع، لا الاكتفاء بصلاحية مخزنة وقت الفهرسة. يجب تحديد SLA لإبطال chunks وembeddings وsnippets وcaches بعد سحب الصلاحية أو حذف الأصل؛ هذه المدد غير معتمدة.

## 3. سجل العمليات الـ12

المعرفات والأسماء من سجل 97؛ الأوصاف التالية تفصيل مرشح لا يعتمد واجهات.

| Operation ID | الاسم | الوظيفة المرشحة | ضابط الإغلاق |
|---|---|---|---|
| OP-C12-167 | createKnowledgeDocument | إنشاء وثيقة مع owner/scope/source refs | إلزام tenant وACL وتصنيف الحساسية |
| OP-C12-168 | createDocumentVersion | إنشاء نسخة immutable جديدة | حفظ hash وparent version وactor وسبب التغيير |
| OP-C12-169 | linkSourceEvidence | ربط فقرة/قسم بمصدر أو قرار أو اختبار | مرجع دقيق ونسخة مصدر وموضع/صفحة عند توفرها |
| OP-C12-170 | generateDocumentationDraft | توليد مسودة من مصادر مفوضة | provenance لكل جزء وتمييز الحقائق والاستنتاجات والافتراضات |
| OP-C12-171 | submitDocumentReview | إرسال نسخة محددة للمراجعة | تثبيت version/hash والمراجعين |
| OP-C12-172 | decideDocumentReview | قرار قبول/رفض/طلب تعديل | قرار مستقل ضمن SH-03 مع سبب وأثر |
| OP-C12-173 | publishKnowledgeDocument | نشر نسخة معتمدة في نطاق محدد | منع النشر قبل الاعتماد والتحقق من ACL |
| OP-C12-174 | searchKnowledge | البحث في المحتوى المسموح | إعادة تحقق ACL وعزل tenant وتنقيح snippets |
| OP-C12-175 | compareDocumentVersions | مقارنة نسختين | عدم تعديل النسخ الأصلية وحفظ مرجع المقارنة |
| OP-C12-176 | exportKnowledgeBundle | تصدير حزمة وثائق | تحقق الصلاحيات والتصنيف والحقوق والاحتفاظ |
| OP-C12-177 | archiveKnowledgeDocument | أرشفة الوثيقة وفق policy | منع تجاوز legal hold أو متطلبات الاحتفاظ |
| OP-C12-178 | reconcileDocumentation | مقارنة الوثائق بمصادر المشروع والإصدار | إظهار drift مع مراجع المصدر دون تعديل تلقائي غير معتمد |

## 4. سجل الأحداث الـ9

أسماء الأحداث من سجل 100. تدقيق المصدر 115 لم يثبت تطابقاً حرفياً لأسماء الأحداث في سجل 100؛ لذلك لا تحول SOURCE-RECOVERED إلى مطابقة حرفية.

| Event ID | الاسم | المنتج/المستهلك المرشح | الحد الأدنى من الدلالة |
|---|---|---|---|
| EVT-C12-001 | KnowledgeDocumentCreated | C12 | document ref وtenant وowner وsource refs |
| EVT-C12-002 | DocumentVersionCreated | C12 | version/hash وparent version وactor |
| EVT-C12-003 | DocumentReviewRequested | C12 → SH-03 | version/hash والمراجعون والأدلة |
| EVT-C12-004 | DocumentApproved | SH-03 decision / C12 outcome | القرار وصاحبه والنسخة المحددة |
| EVT-C12-005 | DocumentPublished | C12 | نسخة منشورة ونطاق الإتاحة |
| EVT-C12-006 | KnowledgeIndexUpdated | SH-16 | source version وindex version وcompletion state |
| EVT-C12-007 | KnowledgeAccessRevoked | مصدر ACL/مالك المجال → SH-16/C12 | source ref ونطاق الإبطال ووقت السريان |
| EVT-C12-008 | DocumentationDriftDetected | C12 | مصدر المقارنة ونسخها ومواضع الاختلاف |
| EVT-C12-009 | DocumentArchived | C12 | document/version وpolicy ref وlegal-hold check |

Producer/consumer النهائي، event envelope، schema version، correlation/causation IDs، tenant scope، deduplication، delivery guarantees، replay، dead-letter، retention وacknowledgement semantics جميعها تحتاج اعتماداً. لا يصدر C12 حدثاً نيابة عن SH-03 أو SH-16 أو مالك ACL.

## 5. أنواع الموافقات

| Approval ID | الفعل | سياق القرار | الضابط |
|---|---|---|---|
| APR-C12-01 | publishKnowledgeDocument | نشر نسخة معرفة معتمدة ومصدرها مثبت | موافقة مستقلة مرتبطة بـversion/hash ونطاق الإتاحة |
| APR-C12-02 | exportKnowledgeBundle | تصدير حزمة معرفة حسب ACL/classification | موافقة مالك البيانات/C10 عند اشتراط السياسة؛ SH-03 lifecycle عند الحاجة |

كلاهما OWNER REVIEW. يجب تحديد متى يكون التصدير منخفض المخاطر أو حساساً، وهل يلزم قرار مستقل لكل تصدير أم تفويض مسبق محدود. لا تُخترع قاعدة عامة قبل اعتماد C10 ومالكي البيانات.

## 6. سيناريوهات القبول والتكامل المحلية

المعرفات محلية وليست Test IDs مصدرية. كل السيناريوهات SPECIFIED / NOT RUN ما لم يذكر أنها BLOCKED.

| Test ID المحلي | السيناريو | النتيجة المتوقعة |
|---|---|---|
| C12-AT-001 | إنشاء وثيقة دون owner أو tenant scope | رفض الإنشاء |
| C12-AT-002 | توليد مسودة AI من مصادر متعددة | إظهار provenance لكل قسم مع source version |
| C12-AT-003 | مصدر غير متاح أو لا يملك المستخدم حق قراءته | عدم تضمين محتواه أو تلخيصه |
| C12-AT-004 | محاولة AI اعتماد/نشر وثيقة | رفض؛ يسمح بالمسودة والاقتراح فقط |
| C12-AT-005 | تعديل وثيقة معتمدة | إنشاء version جديد immutable |
| C12-AT-006 | تعديل محتوى بعد طلب المراجعة | إبطال review binding وإعادة التقديم |
| C12-AT-007 | نشر دون موافقة صالحة | رفض النشر |
| C12-AT-008 | اختلاف hash بعد الموافقة | إبطال القرار وطلب مراجعة جديدة |
| C12-AT-009 | سحب ACL عن المصدر بعد الفهرسة | رفض retrieval وبدء invalidation |
| C12-AT-010 | استعلام مستخدم من tenant آخر | لا عنوان ولا snippet ولا نتيجة مشتقة |
| C12-AT-011 | وجود secret أو بيانات حساسة في النص المسترجع | تطبيق سياسة التنقيح ومنع التسريب |
| C12-AT-012 | حذف المصدر مع legal hold قائم | منع الحذف أو تطبيق الحجز وفق السياسة |
| C12-AT-013 | إعادة فهرسة المصدر نفسه | لا تكرار للوثيقة أو نسخ غير متسقة |
| C12-AT-014 | index version أقدم من canonical version | إظهار freshness state وعدم تقديمه كأحدث نسخة |
| C12-AT-015 | OCR لملف متعدد الصفحات | حفظ file ref والصفحة ومواضع النص والجداول |
| C12-AT-016 | ترجمة محتوى RTL | حفظ النص الأصلي ومرجع الترجمة وعدم تبديل provenance |
| C12-AT-017 | مقارنة نسختين | إظهار الفروق دون تغيير أي نسخة |
| C12-AT-018 | تصدير وثيقة محمية بحقوق/ترخيص | منع التصدير أو تطبيق القيود المعتمدة |
| C12-AT-019 | أرشفة وثيقة خاضعة لاحتفاظ إلزامي | منع الأرشفة/الحذف المخالف للسياسة |
| C12-AT-020 | محتوى مستند يتضمن prompt injection | يعامل كمحتوى غير موثوق ولا يغير سياسات النظام |
| C12-AT-021 | reconcileDocumentation يكتشف drift | إظهار مصدر المقارنة ونسخه والفرق |
| C12-AT-022 | سحب ACL مع وجود cache/chunks/embeddings | إبطال المشتقات ضمن SLA معتمد وإثبات lineage |

**BLOCKED:** لا يمكن اعتماد اختبارات deletion/legal hold وinvalidation timing حتى اعتماد retention policy وSLA ومالك المصدر.

## 7. محاور التتبع

السجل 100 يربط أحداث C12 مرشحاً بـAX-021 وAX-048. هذا ليس قرار ربط معتمداً. يجب مطابقة كل عملية/حدث/موافقة مع متطلب ومحور وشاشة وfield وSoR وصلاحية واختبار ودليل قبول، ومراجعة مالك C12 ومالكي المحاور.

## 8. قرارات الحدود المفتوحة مع SH-07/16

استناداً إلى التدقيق 124:
1. canonical knowledge مقابل index/embeddings/snippets.
2. مالك البحث العام SH-07 مقابل البحث الدلالي SH-16 وحدود الاسترجاع.
3. إعادة تحقق ACL عند retrieval وليس فقط ingestion.
4. إبطال chunks/embeddings/cache بعد سحب الصلاحية أو حذف المصدر.
5. propagation لسياسة retention/legal hold عبر النسخ والمشتقات.
6. event producer وacknowledgement بين domain completion وindex completion.
7. replay/deduplication ومنع تكرار الآثار الجانبية.
8. freshness SLA وإظهار index lag.
9. التعامل مع فشل OCR/ingestion وإعادة المحاولة والنتائج الجزئية.
10. التمييز بين حذف canonical source وحذف مشتقات الفهرسة.
11. سياسة استخدام AI providers ومواضع معالجة البيانات وموقعها.
12. حقوق المصدر والترخيص والتصدير والاقتباس.
13. اعتماد قوالب الوثائق والتصنيفات واللغات وRTL.
14. سياسة نشر الوثائق المولدة ومراجعتها البشرية.
15. حدود الاحتفاظ بسجلات provenance والتدقيق.
16. حدود صلاحيات الوكيل عند التوليد والربط والمراجعة.

هذه قرارات OPEN، ولا تحلها هذه الحزمة.

## 9. قرارات تمنع اعتماد C12

- اعتماد canonical document schema وversion model وdocument/source relation.
- اعتماد field-level ownership وprivacy classification وtenant isolation.
- اعتماد SH-16 ingestion/index contract وSH-07 retrieval contract.
- اعتماد ACL revalidation وinvalidation SLA وfreshness SLA.
- اعتماد OCR/AI/search providers أو آلية اختيارها وتكلفتها وترخيصها.
- اعتماد retention/deletion/legal hold ونسخ المصدر والمشتقات.
- اعتماد حقوق المحتوى الخارجي وسياسة التصدير.
- اعتماد موافقات النشر والتصدير وفصل الواجبات.
- اعتماد event schemas وproducer/consumer ownership.
- ربط العمليات والأحداث والموافقات باختبارات أصلية أو اختبارات معتمدة وتشغيلها.
- مراجعة المالك النهائي لـC12 وC10 وC08 وC07 وSH-07/16.

## 10. معيار إغلاق C12

لا ينتقل C12 إلى APPROVED أو READY-FOR-IMPLEMENTATION حتى:
1. اعتماد SoR والكيانات والحقول وإصدارات الوثائق.
2. اعتماد الفصل بين C12 وC08 وSH-07 وSH-16.
3. اعتماد ACL وtenant isolation وinvalidation/freshness SLAs.
4. اعتماد retention/deletion/legal hold والحقوق والتصدير.
5. اعتماد مراجعة بشرية للمخرجات المولدة وprovenance.
6. اعتماد APIs/events/approvals schemas ونسخها وأخطائها وصلاحياتها.
7. ربط الاختبارات بمعرفات موثوقة وتشغيلها وإرفاق الأدلة.
8. اعتماد مالكي المجالات والحوكمة والأمن والخصوصية.

**الحكم النهائي:** C12 موثق في حزمة تتبع أولية تفصيلية؛ ما زال DERIVED / OWNER REVIEW / NOT APPROVED / NOT IMPLEMENTED. لا تثبت هذه الوثيقة وجود محرك معرفة أو بحث أو OCR أو فهرسة أو تصدير عامل.
