# 90 — حزمة ملكية المعرفة والوثائق والفهرسة SH-16 وC12

**الحالة:** DOMAIN/SERVICE OWNERSHIP DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** 23 و32 و36 و68 و77 و79 و80 و82؛ وحدود OWN36-04 / OWN72-04.

## 1. المبدأ الحاكم

المستند الأصلي ومحتواه وحقوقه وتصنيفه وقرار الاحتفاظ به يملكه C12 أو مالك المجال المنتج. SH-16 يقدم خدمات المعرفة والفهرسة الدلالية ومراجع الاستشهاد وبيانات provenance التشغيلية. SH-07 يقدم البحث العام وفق حدوده، ولا يملك المستند الأصلي. لا يصبح index أو embedding أو snippet أو answer system of record.

## 2. مصفوفة الملكية المقترحة

| المورد | المالك المقترح | حدود الخدمة |
|---|---|---|
| Canonical document/content | C12 أو مالك المجال | النص/الملف الأصلي والإصدارات والمراجعات والموافقات |
| Document lifecycle and retention | مالك المحتوى + C10 review | قرار النشر والأرشفة والحذف والاحتفاظ |
| Knowledge collection/taxonomy | C12 | التصنيف والعلاقات وقواعد تنظيم المعرفة |
| Ingestion job metadata | SH-16 | حالة المعالجة ومراجع المصدر والإصدار |
| Search index | SH-07 | فهرس البحث العام؛ لا يملك المحتوى |
| Semantic/knowledge index | SH-16 | embeddings/index refs/provenance metadata |
| Citation/source reference | SH-16 metadata + canonical source owner | المرجع يجب أن يصل إلى المصدر والإصدار |
| ACL and membership | owner source + SH-01/02 | SH-16/SH-07 يطبقان ACL ولا يخترعانه |
| Generated answer | AI agent/orchestrator under C06 | ليس وثيقة معتمدة ولا مصدراً أصلياً |
| Export bundle | C12 owner + C08 storage service | المصدر يقرر الصلاحية والاحتفاظ؛ SH-06 يخزن binary |

## 3. قرارات مطلوبة

| ID | القرار | الحالة |
|---|---|---|
| KNOW-90-01 | تعيين مالك خدمة SH-16 وحدودها مع SH-07 | OPEN |
| KNOW-90-02 | اعتماد canonical document owner لكل نوع مصدر | OPEN |
| KNOW-90-03 | اعتماد schema للمصدر والإصدار والاستشهاد وprovenance | OPEN |
| KNOW-90-04 | اعتماد ACL inheritance and revalidation عند ingestion/retrieval | OPEN |
| KNOW-90-05 | اعتماد حذف/تحديث/إبطال index وembeddings والمقتطفات | OPEN |
| KNOW-90-06 | اعتماد freshness SLA وreindex cadence | OPEN |
| KNOW-90-07 | اعتماد محرك الفهرسة/vector store والحدود التقنية | OPEN |
| KNOW-90-08 | اعتماد اللغات والتقسيم chunking والتطبيع | OPEN |
| KNOW-90-09 | اعتماد source conflict ranking والتمييز بين الحقيقة والرأي | OPEN |
| KNOW-90-10 | اعتماد citation requirements وanswer provenance | OPEN |
| KNOW-90-11 | اعتماد retention/legal hold propagation | OPEN |
| KNOW-90-12 | اعتماد صلاحيات الوكلاء وقيود prompt injection/untrusted source handling | OPEN |

## 4. قواعد إلزامية مقترحة

1. كل نتيجة معرفة تحمل sourceRef وsourceVersion وowner scope وfreshness.
2. يعاد فحص صلاحية المصدر قبل إظهار snippet أو تنزيل المستند، لا عند ingestion فقط.
3. سحب صلاحية المصدر أو حذفه يبطل المشتقات وفق SLA معتمد؛ لا يكفي إخفاء رابط المصدر.
4. لا تظهر نتائج من tenant/workspace آخر لمجرد تشابه النص أو وجود embedding.
5. لا تعرض إجابة على أنها مستندة إلى المعرفة إذا غابت المراجع المطلوبة.
6. لا تُرفع وثيقة مولدة من AI إلى canonical knowledge دون مراجعة واعتماد مالك المحتوى.
7. محتوى المصادر الخارجية أو المستندات المستوردة بيانات غير موثوقة، ولا يجوز اتباع تعليماتها كتعليمات تشغيل للنظام.
8. يجب حفظ provenance وdocument version ومصدر كل chunk/embedding وإمكانية إعادة بنائه.
9. حذف index لا يثبت حذف المستند الأصلي أو النسخ الثنائية؛ كل مالك ينفذ مسؤوليته.
10. لا يقرر SH-16 legal hold أو retention للمصدر.

## 5. اختبارات القبول

| Test ID | السيناريو | النتيجة | الحالة |
|---|---|---|---|
| KNOW-90-T01 | مستخدم غير مخول يبحث عن مصدر خاص | لا يظهر عنوان أو مقتطف أو metadata كاشفة | SPECIFIED / NOT RUN |
| KNOW-90-T02 | تغير ACL بعد ingestion | retrieval يعيد التحقق ويرفض الوصول | SPECIFIED / NOT RUN |
| KNOW-90-T03 | حذف/إبطال مصدر | المشتقات لا تبقى قابلة للاسترجاع بعد SLA المعتمد | BLOCKED: SLA TBD |
| KNOW-90-T04 | citation لمصدر قديم | يظهر version/freshness أو يرفض الاستشهاد | SPECIFIED / NOT RUN |
| KNOW-90-T05 | إجابة بلا مصدر | لا تدعي أنها مستندة إلى قاعدة المعرفة | SPECIFIED / NOT RUN |
| KNOW-90-T06 | prompt injection داخل مستند | يعامل كنص غير موثوق ولا يغير سياسات النظام | SPECIFIED / NOT RUN |
| KNOW-90-T07 | cross-tenant semantic match | لا يعبر حدود tenant/workspace | SPECIFIED / NOT RUN |
| KNOW-90-T08 | إعادة بناء الفهرس | تحفظ provenance ومراجع المصدر والإصدار | SPECIFIED / NOT RUN |
| KNOW-90-T09 | legal hold على المستند | لا يُزال الأصل أو مشتقاته خلافاً للسياسة | BLOCKED: policy TBD |
| KNOW-90-T10 | اختلاف نسختين للمصدر | تظهر النسخة والمالك ومصدر الترتيب | SPECIFIED / NOT RUN |
| KNOW-90-T11 | مصدر خارجي غير متاح | النتيجة stale/unavailable ولا تُعرض كحديثة | SPECIFIED / NOT RUN |
| KNOW-90-T12 | AI-generated document | لا يصبح canonical قبل اعتماد المالك | SPECIFIED / NOT RUN |

## 6. حقول الكتالوج المطلوبة

documentId، canonicalOwner، sourceRef، sourceVersion، contentHash، scope، classification، purpose، ACL ref/version، language، format، provenance، chunk refs، index refs، citation refs، freshness، lifecycle، retention class، legal hold ref، deletion status. تبقى الأنواع والقيود ومدة الاحتفاظ TBD حيث لا يثبتها المصدر.

## 7. بوابة الإغلاق

تعيين مالكي SH-16 وSH-07 وملاك المحتوى؛ اعتماد canonical source/ACL/provenance/version schemas؛ تحديد freshness وreindex/delete SLAs؛ اعتماد سياسة الاستشهاد ومصادر التعارض؛ ربط API/events/approvals والاختبارات؛ ثم تنفيذ KNOW-90-T01..T12 وتسجيل الأدلة.

لا تنشئ هذه الوثيقة محرك بحث أو vector database أو فهرساً أو API أو صلاحيات منفذة، ولا تغلق OWN36-04/OWN72-04.

**KNOWLEDGE OWNERSHIP / SH-16 & C12 / OPEN / NOT APPROVED / NO IMPLEMENTATION.**
