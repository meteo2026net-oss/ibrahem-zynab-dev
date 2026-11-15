# 116 — تدقيق ترابط أنواع الموافقات الـ26 مع عمليات المجال والاختبارات

**الحالة:** APPROVAL-OPERATION-TRACEABILITY AUDIT / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED  
**التاريخ:** 2026-11-15  
**المراجع:** 97 (201 API operations)، 98 (26 approval types)، 99، 113، DOCX الرئيسي.

## 1. النتيجة التنفيذية

تمت مطابقة العملية/الفعل المذكور في كل نوع موافقة في 98 مع أسماء العمليات الفعلية في سجل 97.

| المقياس | النتيجة |
|---|---:|
| أنواع الموافقات المرشحة | 26 |
| لها اسم عملية مطابق في سجل 97 | 25 |
| لا توجد لها عملية مطابقة في سجل 97 | 1 |
| كل الأنواع ما زالت OWNER REVIEW | 26 |
| أنواع لها اختبار قبول مفرد معتمد ومثبت | 0 |

النوع الوحيد الذي لا يملك عملية مطابقة هو `APR-C07-02`: يشير إلى `approveQualityWaiver`، بينما الموجود في 97 هو `requestQualityWaiver` (`OP-C07-118`). الطلب ليس قرار الموافقة، ولا يجوز اعتباره بديلاً عنه.

## 2. سجل الأنواع الـ26

| Approval ID | Center | Gated operation/action | Matching Operation ID | Decision owner in 98 | Executor | OWN reference | Test reference in 98 | Approval status |
|---|---|---|---|---|---|---|---|---|
| APR-C01-01 | C01 | `decideRequirementBaseline` | OP-C01-016 (C01) | C01 | SH-03 | OWNER TBD | DRAFT-AT-C01-01..12 | OWNER REVIEW |
| APR-C02-01 | C02 | `decideDesignApproval` | OP-C02-027 (C02) | C02 | SH-03 | C02/C03 boundary TBD | DRAFT-AT-C02-01..20 | OWNER REVIEW |
| APR-C03-01 | C03 | `decideNavigationApproval` | OP-C03-038 (C03) | C03 | SH-03 | C02/C03 boundary TBD | DRAFT-AT-C03-01..20 | OWNER REVIEW |
| APR-C04-01 | C04 | `decideSchemaApproval` | OP-C04-062 (C04) | C04 + C10 عند السياسة | SH-03 | OWN72-07 | DRAFT-AT-C04-01..28 | OWNER REVIEW |
| APR-C04-02 | C04 | `applyMigration` | OP-C04-056 (C04) | C04 + environment owner | SH-03 | OWN72-07 | INT-80-18 / DRAFT-AT-C04 | OWNER REVIEW |
| APR-C04-03 | C04 | `requestRestore` | OP-C04-059 (C04) | C04 + owner impacted domains | SH-03 | OWN72-07 / BAK-87 | INT-80-17 / BAK-87 | OWNER REVIEW |
| APR-C05-01 | C05 | `applyApprovedChangeSet` | OP-C05-067 (C05) | C05 / reviewer مستقل | SH-03 | OWNER TBD | C05-AT-01..32 | OWNER REVIEW |
| APR-C05-02 | C05 | `approveCodeBaseline` | OP-C05-084 (C05) | C05 / reviewer مستقل | SH-03 | OWNER TBD | C05-AT-01..32 | OWNER REVIEW |
| APR-C06-01 | C06 | `requestToolGrant` | OP-C06-090 (C06) | C06 + C10 | SH-03 | OWN72-10 | C06-AT-01..27 / SEC-80-10 | OWNER REVIEW |
| APR-C06-02 | C06 | `reviewAgentOutput` | OP-C06-099 (C06) | مالك المورد | SH-03 عند الحاجة | OWN72-10 + domain owner | C06-AT-01..27 | OWNER REVIEW |
| APR-C07-01 | C07 | `approveTestResults` | OP-C07-119 (C07) | C07 / reviewer مستقل | SH-03 | OWN36-03 / OWN72-08 | C07-AT-01..28 | OWNER REVIEW |
| APR-C07-02 | C07 | `approveQualityWaiver` | MISSING | C07 + C10/C11 بحسب الأثر | SH-03 | OWN36-03 / OWN72-08 | C07-AT-23..28 | OWNER REVIEW |
| APR-C08-01 | C08 | `requestDeletion` | OP-C08-131 (C08) | C08 + C10 | SH-03 | OWN72-01 | C08-DRAFT-AT / PRIV-88 | OWNER REVIEW |
| APR-C08-02 | C08 | `requestExport` | OP-C08-130 (C08) | C08 + data owner/C10 | SH-03 | OWN72-01 | C08-DRAFT-AT / SEC-80-11 | OWNER REVIEW |
| APR-C09-01 | C09 | `startSync` | OP-C09-138 (C09) | C09 + external account owner | SH-03 | OWN72-02 | INTEGRATION / INT-91 | OWNER REVIEW |
| APR-C09-02 | C09 | `beginAuthorization` | OP-C09-134 (C09) | C09 + C10 | SH-03 | OWN72-02 | INT-91-T02/T06 | OWNER REVIEW |
| APR-C10-01 | C10 | `decideSecurityException` | OP-C10-149 (C10) | C10 + approver مستقل | SH-03 | OWN72-03 | AUTH-78 / SEC-80-16 | OWNER REVIEW |
| APR-C10-02 | C10 | `requestPolicyChange` | OP-C10-148 (C10) | C10 / governance owner | SH-03 | OWN72-03 | AUTH-78 / SEC-80-13 | OWNER REVIEW |
| APR-C11-01 | C11 | `approveRelease` | OP-C11-161 (C11) | C11 + independent reviewer | SH-03 | OWN72-06 | RELEASE / ART-95 | OWNER REVIEW |
| APR-C11-02 | C11 | `deployRelease` | OP-C11-162 (C11) | C11 + environment owner | SH-03 | OWN72-06 | RELEASE / SEC-80 | OWNER REVIEW |
| APR-C11-03 | C11 | `requestRollback` | OP-C11-165 (C11) | C11 + impacted domain owners | SH-03 | OWN72-06 | RELEASE / BAK-87 | OWNER REVIEW |
| APR-C12-01 | C12 | `publishKnowledgeDocument` | OP-C12-173 (C12) | C12/domain content owner | SH-03 | OWN72-04 | KNOW-90 / SEC-80-11 | OWNER REVIEW |
| APR-C12-02 | C12 | `exportKnowledgeBundle` | OP-C12-176 (C12) | C12 + data owner/C10 | SH-03 عند الحاجة | OWN72-04 | KNOW-90 / INT-80-11 | OWNER REVIEW |
| APR-C13-01 | C13 | `applyOwnerSettingChange` | OP-C13-184 (C13) | مالك مفتاح الإعداد | SH-03 | OWN72-05 | CFG-86 | OWNER REVIEW |
| APR-C13-02 | C13 | `rollbackSettingVersion` | OP-C13-186 (C13) | مالك المفتاح + C10 عند policy | SH-03 عند الحاجة | OWN72-05 | CFG-86 | OWNER REVIEW |
| APR-C14-01 | C14 | `exportDashboardReport` | OP-C14-200 (C14) | أصحاب مصادر البيانات/C10 | SH-03 وفق policy | OWN36-10 / OWN72-09 | INT-80-11/13 | OWNER REVIEW |

## 3. فجوات دورة الموافقة

1. **APR-C07-02:** يلزم تعريف عملية قرار مستقلة مثل `decideQualityWaiver` أو اعتماد اسم بديل من مالك C07؛ لا تنشئ العملية تلقائياً.
2. **Request vs Decision:** وجود عملية `request*` لا يثبت وجود عملية `decide*`، ويجب أن تكون لكل approval request حالة lifecycle وقرار وapprover role وreason/evidence وexpiry/revocation حيث ينطبق.
3. **Decision ownership:** بعض الأنواع تسند القرار إلى OWNER TBD أو إلى حدود متعددة المراكز غير محسومة؛ يجب تحديد Domain Decision Owner وApprover Role والبديل/التفويض وفصل الواجبات.
4. **Executor vs authority:** SH-03 ينفذ workflow/policy orchestration، لكنه لا يصبح Domain Decision Owner. قرار المجال يبقى لدى المالك المعتمد.
5. **Test reference:** مراجع DRAFT-AT/Cxx-AT وINT/SEC/CFG/BAK ليست بحد ذاتها إثباتاً أن لكل approval type اختباراً مفرداً يغطي positive/negative paths. يلزم test intent/oracle وربط واضح.
6. **Mandatory/conditional/N/A:** سجل 98 لا يحسم بشكل نهائي متى تكون كل موافقة إلزامية أو مشروطة أو غير منطبقة؛ يجب تحديد policy trigger والحدود والآثار.

## 4. اختبارات قبول دورة الموافقة المطلوبة

عند تصميم الاختبارات، راجع على الأقل ما ينطبق من الحالات التالية لكل نوع:
- المرسل ليس مخولاً باتخاذ القرار، ومنع self-approval.
- رفض مستخدم خارج الدور/النطاق أو خارج tenant.
- الموافقة على نسخة/هاش مختلف عن النسخة المطلوب اعتمادها.
- انتهاء الصلاحية أو سحب الطلب أو إلغاء التفويض.
- تكرار الطلب أو إعادة تشغيل الحدث/idempotency.
- عدم اكتمال quorum أو فصل الواجبات عند اشتراطهما.
- قرار الرفض أو طلب التعديل مع حفظ السبب والأدلة.
- fail-closed عند تعطل محرك السياسة أو تعذر التحقق من الصلاحية.
- عدم تنفيذ side effect قبل اكتمال القرار والموافقة.

لا تُعتبر هذه قائمة اختبارات منفذة أو قرارات سياسة معتمدة؛ إنها معايير تصميم قبول تحتاج اعتماد مالكي المراكز والأمن والحوكمة.

## 5. قرارات الإغلاق

1. حسم عملية قرار `APR-C07-02` وربطها بمالك C07 وC10/C11 بحسب أثر الاستثناء.
2. مراجعة مالكي المراكز الـ25 الأخرى لتأكيد أن العملية المدرجة هي العملية التي تتطلب الموافقة، لا مجرد عملية طلب أو تنفيذ غير صحيحة.
3. اعتماد Decision Owner وApprover Role وSH-03 executor وحدود التفويض لكل نوع.
4. تحديد Mandatory/Conditional/N/A وشروط التشغيل والأثر.
5. اعتماد test case مفرد لكل نوع أو تجميع مبرر، مع source locator وoracle وnegative paths.
6. عدم تحويل أي OWNER REVIEW إلى APPROVED دون sign-off وتحديث سجل القرار.

## 6. الحكم

- Operation reference integrity: **25/26 exact name references present**.
- Missing decision/action operation: **1 (APR-C07-02)**.
- Approval owner approval: **0/26 verified**.
- Per-approval exact test mapping: **0/26 verified**.
- Test execution: **NOT RUN**.
- Application implementation: **NOT STARTED BY THIS AUDIT**.

**الحكم النهائي:** اكتمل فحص وجود اسم العملية المشار إليها في سجل 97، لكنه لا يثبت صحة كل علاقة موافقة أو اعتماد مالكها. فجوة APR-C07-02 واضحة ومفتوحة، ولا يجوز إغلاقها بمجرد إعادة تسمية عملية الطلب.