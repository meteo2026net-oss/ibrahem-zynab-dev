# 85 — حزمة حسم ملكية الإصدارات ومجموعات التغيير واللقطات SH-10

**التاريخ:** 2026-11-15  
**الحالة:** SOURCE-BASED CONTRACT DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**المصادر:** الوثائق 23 و32 و36 و67 و77 و79 و80 و82؛ مع عدم افتراض اكتمال الفصل 157 المفقود.

## 1. سبب إعداد الحزمة

تسجل الوثيقة 36 المسألة OWN36-06: SH-10 يملك metadata الإصدار، بينما يملك المجال محتوى المورد. وتحدد الوثيقة 32 SH-10 كخدمة Version Service لإدارة تاريخ الإصدارات ومجموعات التغيير واللقطات والمقارنات، مع منعها من امتلاك منطق محتوى المورد. وتذكر الوثيقة 77 الفرق بين record_version/expected_version (تزامن سجل المجال) وversion_id/parent_version (إصدار المورد مع metadata SH-10 عند انطباقه). لكن حدود دورة إصدار C11، وملكية artifact registry، وسياسات branching/merge/restore، لا تزال مفتوحة.

## 2. فصل الحقائق والمسؤوليات المقترح

| العنصر | مالك الحقيقة المقترح | دور SH-10 | ما لا يجوز |
|---|---|---|---|
| canonical domain content | مركز المجال الذي يملك المورد | حفظ contentRef/hash والربط بالإصدار | تعديل محتوى المجال أو تفسيره |
| domain lifecycle/status | مركز المجال | ربط نسخة المورد بحالة المجال عند الحاجة | فرض lifecycle موحد على كل أنواع الموارد |
| version metadata | SH-10 | versionId/parentVersion/author/time/tags/relationships | امتلاك المحتوى الأصلي |
| change set metadata | SH-10 | changeSet, items, baseVersion, validation refs | قبول patch دون تحقق المالك |
| content diff/patch | مالك المجال أو محركه | حفظ مرجع diff أو patch evidence | دمج محتوى غير مفهوم للمجال |
| approval binding | SH-03 workflow + مالك المجال يحدد السياسة | ربط approvalRef ببصمة المورد والإصدار | تنفيذ موافقة على hash مختلف |
| project snapshot | SH-10 metadata + كل مالك مجال يقدم snapshot component | تجميع refs وإصدار لقطة قابلة للتتبع | ادعاء consistency إذا لم يقدم كل مالك نقطة اتساق |
| restore/rollback | مالك المجال يقرر معنى الاستعادة؛ SH-10 يسجل إصداراً جديداً | restoreReference ويثبت التاريخ | محو الإصدارات أو تجاوز سياسة المجال |
| release lifecycle | C11 | يستهلك artifact/version refs ويملك release state | نقل release approval أو deploy state إلى SH-10 |
| artifact binary/registry | مالك artifact registry الذي لم يُحسم | version metadata references فقط | اعتبار metadata سجل artifact أو توقيعه |
| operational execution | SH-08 job execution؛ SH-14 telemetry | لا يملك المحاولات أو صحة التشغيل | دمج job state مع version state |

## 3. حدود التزامن والإصدارات

يجب عدم خلط هذه المفاهيم:
- `record_version` / `expected_version`: optimistic concurrency لسجل مجال بعينه.
- `version_id` / `parent_version`: هوية إصدار المورد وعلاقته التاريخية.
- `contractVersion`: توافق عقد API/event.
- `artifactVersion`: إصدار حزمة/ملف منشور، ومالك registry ما زال بحاجة إلى قرار.
- `releaseVersion`: هوية دورة الإصدار/الطرح في C11.
- `changeSetId`: مجموعة تغييرات مقترحة أو محققة مرتبطة بقاعدة إصدار.

لا يجوز استعمال رقم واحد مكان هذه الهويات أو افتراض تطابق دورة حياتها.

## 4. عمليات SH-10 المستعادة كاقتراحات تعاقدية

العمليات المرشحة في الوثيقة 32:
- createVersion / getVersion / compareVersions
- createChangeSet / validateChangeSet
- createSnapshot / tagVersion / restoreReference

المدخلات المرشحة: resourceType/id، baseVersion، contentRef/contentHash، changeSet، actor، scope، reason، approvalRef عند الحاجة. المخرجات المرشحة: versionId، parentVersion، contentRef/hash، author، createdAt، changeSetRef، approvalRef، integrity status.

هذه أسماء/حقول عقد مقترحة وليست APIs منفذة أو schemas معتمدة.

## 5. قرارات مطلوبة

| Decision ID | القرار المطلوب | المالك/المراجع | الحالة |
|---|---|---|---|
| VER-85-01 | اعتماد SH-10 كمالك metadata فقط ونطاق metadata القياسي | مالك SH-10 + C01–C13 | OPEN |
| VER-85-02 | تحديد أنواع الموارد التي تتطلب versioning وأيها لا | كل مالك مجال | OPEN |
| VER-85-03 | اعتماد قواعد versionId/parentVersion/recordVersion/expectedVersion | SH-10 + C04 + ملاك المجالات | OPEN |
| VER-85-04 | اعتماد branching/merge/tag/immutability policy حسب نوع المورد | مالكو المجالات + SH-10 | OPEN |
| VER-85-05 | اعتماد change set validation وconflict resolution | مالك المجال + SH-10 | OPEN |
| VER-85-06 | اعتماد approval hash binding وrevalidation قبل apply | SH-03 + مالك المجال + C10 | OPEN |
| VER-85-07 | اعتماد project snapshot consistency protocol | SH-10 + جميع Domain SoR owners | OPEN |
| VER-85-08 | اعتماد restore/rollback semantics وإثبات integrity | مالك المجال + SH-10 + C07 | OPEN |
| VER-85-09 | تعيين مالك artifact registry والبصمات والتوقيع والاحتفاظ | C11 + C08 + C10 | OPEN |
| VER-85-10 | فصل release lifecycle في C11 عن resource version lifecycle في SH-10 | C11 + SH-10 | OPEN |
| VER-85-11 | اعتماد API/event schemas والتوافق والترحيل | API/Event owners | OPEN |
| VER-85-12 | اعتماد retention/legal hold لبيانات النسخ والفروقات واللقطات | مالكو المجالات + C10 | OPEN |

## 6. اختبارات القبول المطلوبة

| Test ID | السيناريو | معيار القبول | الحالة |
|---|---|---|---|
| VER-85-T01 | تحديث محتوى مورد عبر SH-10 مباشرة | رفض؛ يمر التغيير عبر مالك المجال | SPECIFIED / NOT RUN |
| VER-85-T02 | تعديل سجل على expected_version قديم | conflict دون lost update | SPECIFIED / NOT RUN |
| VER-85-T03 | موافقة مرتبطة ببصمة ثم تغير المحتوى | رفض apply أو طلب موافقة جديدة | SPECIFIED / NOT RUN |
| VER-85-T04 | استعادة إصدار سابق | إنشاء إصدار جديد مع حفظ التاريخ | SPECIFIED / NOT RUN |
| VER-85-T05 | مورد لا يدعم branching/merge | رفض العملية أو إظهار unsupported صراحة | SPECIFIED / NOT RUN |
| VER-85-T06 | snapshot متعدد المجالات مع component مفقود | لا يدعي الاتساق؛ يعلن partial/blocked | SPECIFIED / NOT RUN |
| VER-85-T07 | خلط record_version وversion_id | validation يرفض الاستخدام الخاطئ | SPECIFIED / NOT RUN |
| VER-85-T08 | artifact registry غير معتمد | لا إعلان artifact/release integrity كحقيقة | BLOCKED: registry ownership TBD |
| VER-85-T09 | release deploy state يحاول تحديث SH-10 | C11 يبقى مالك release lifecycle | SPECIFIED / NOT RUN |
| VER-85-T10 | حذف version/change-set تحت legal hold | منع الحذف وفق سياسة معتمدة | BLOCKED: retention/legal hold TBD |
| VER-85-T11 | replay لحدث version قديم | idempotency + contract version + owner recheck | SPECIFIED / NOT RUN |
| VER-85-T12 | restore بعد فقد dependency | فشل آمن مع evidence وعدم إعلان restore complete | SPECIFIED / NOT RUN |

لا توجد نتائج تنفيذ أو run IDs لهذه الاختبارات.

## 7. الربط بالفجوات الحالية

- OWN36-06 يبقى المرجع التاريخي المفتوح.
- FLD-16 (artifact registry/storage) يبقى OPEN.
- FLD-21/23/25 تبقى قواميس منفصلة؛ لا تدمج version author/owner/creator.
- INT-80-06 وINT-80-07 وINT-80-17 وINT-80-18 ترتبط بهذه الحزمة.
- SH-10 metadata لا تحسم RTO/RPO أو retention أو registry.
- لا تعالج هذه الحزمة نقص الفصل 156/157 ولا تستعيد محتوى غير موجود.

## 8. بوابة الإغلاق

- [ ] اعتماد حدود SH-10 وملاك metadata.
- [ ] اعتماد version identity model لكل نوع مورد.
- [ ] اعتماد change set, branching, merge, restore semantics.
- [ ] اعتماد snapshot consistency protocol ومشاركة ملاك المجالات.
- [ ] تعيين مالك artifact registry وسياسات التوقيع والتخزين.
- [ ] توثيق فصل C11 release lifecycle.
- [ ] اعتماد API/event schemas والـcompatibility.
- [ ] اعتماد retention/legal hold.
- [ ] تنفيذ اختبارات VER-85 وتقديم الأدلة.
- [ ] تحديث 36 و77 و79 و80 بعد قرارات المالكين فقط.

**لا تنشئ هذه الوثيقة خدمة SH-10 أو artifact registry أو جداول أو APIs أو صلاحيات أو migrations. لا تعتمد سياسات versioning ولا تعني بدء البرمجة.**

**SH-10 VERSION OWNERSHIP DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION.**
