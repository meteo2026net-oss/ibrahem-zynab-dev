# 98 — سجل أنواع الموافقات وربطها بالمراكز والعمليات

**الحالة:** APPROVAL TYPE INVENTORY / DERIVED / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**المراجع:** 25، 59–70، 72، 78–80، 82، 94، 97.  
**الحد:** يستخرج أنواع الموافقات المرشحة من أسماء العمليات وضوابط العقود. لا يثبت أن كل عملية تحتاج موافقة؛ القرار النهائي يحدده مالك المجال وC10/SH-03 وفق مستوى الأثر. لا تنشئ هذه الوثيقة workflow فعلياً.

## 1. سجل أنواع الموافقات المرشحة

| Approval Type ID | المركز | العملية/المورد | سبب الموافقة المقترح | مالك قرار المجال | منفذ workflow | OWN reference | Test family | الحالة |
|---|---|---|---|---|---|---|---|---|
| APR-C01-01 | C01 | decideRequirementBaseline / requirement_baseline | اعتماد خط أساس متطلبات ثابت النسخة | C01 | SH-03 | OWNER TBD | DRAFT-AT-C01-01..12 | OWNER REVIEW |
| APR-C02-01 | C02 | decideDesignApproval / design_document_version | اعتماد تصميم محدد version/hash | C02 | SH-03 | C02/C03 boundary TBD | DRAFT-AT-C02-01..20 | OWNER REVIEW |
| APR-C03-01 | C03 | decideNavigationApproval / navigation_graph | اعتماد مسارات وتنقل محدد graph/hash | C03 | SH-03 | C02/C03 boundary TBD | DRAFT-AT-C03-01..20 | OWNER REVIEW |
| APR-C04-01 | C04 | decideSchemaApproval / schema_version | اعتماد schema أو row policy | C04 + C10 عند السياسة | SH-03 | OWN72-07 | DRAFT-AT-C04-01..28 | OWNER REVIEW |
| APR-C04-02 | C04 | applyMigration / migration_plan | اعتماد migration إنتاجية بعد dry-run/rollback | C04 + environment owner | SH-03 | OWN72-07 | INT-80-18 / DRAFT-AT-C04 | OWNER REVIEW |
| APR-C04-03 | C04 | requestRestore / restore_plan | اعتماد restore وتأثيره على بيانات المجال | C04 + owner impacted domains | SH-03 | OWN72-07 / BAK-87 | INT-80-17 / BAK-87 | OWNER REVIEW |
| APR-C05-01 | C05 | applyApprovedChangeSet / code_change_set | اعتماد patch محدد base hash/diff | C05 / reviewer مستقل | SH-03 | OWNER TBD | C05-AT-01..32 | OWNER REVIEW |
| APR-C05-02 | C05 | approveCodeBaseline / code_baseline | اعتماد baseline الكود | C05 / reviewer مستقل | SH-03 | OWNER TBD | C05-AT-01..32 | OWNER REVIEW |
| APR-C06-01 | C06 | requestToolGrant / tool capability | منح وكيل قدرة/نطاق أداة | C06 + C10 | SH-03 | OWN72-10 | C06-AT-01..27 / SEC-80-10 | OWNER REVIEW |
| APR-C06-02 | C06 | reviewAgentOutput / AI output | اعتماد مخرج AI قبل تطبيقه على مورد حساس | مالك المورد | SH-03 عند الحاجة | OWN72-10 + domain owner | C06-AT-01..27 | OWNER REVIEW |
| APR-C07-01 | C07 | approveTestResults / QA result | اعتماد نتيجة اختبار/بوابة جودة | C07 / reviewer مستقل | SH-03 | OWN36-03 / OWN72-08 | C07-AT-01..28 | OWNER REVIEW |
| APR-C07-02 | C07 | approveQualityWaiver / quality waiver | استثناء مؤقت من بوابة جودة | C07 + C10/C11 بحسب الأثر | SH-03 | OWN36-03 / OWN72-08 | C07-AT-23..28 | OWNER REVIEW |
| APR-C08-01 | C08 | requestDeletion / asset deletion | حذف أصل بعد retention/legal hold/ACL review | C08 + C10 | SH-03 | OWN72-01 | C08-DRAFT-AT / PRIV-88 | OWNER REVIEW |
| APR-C08-02 | C08 | requestExport / asset export | تصدير أصل/محتوى حساس أو واسع النطاق | C08 + data owner/C10 | SH-03 | OWN72-01 | C08-DRAFT-AT / SEC-80-11 | OWNER REVIEW |
| APR-C09-01 | C09 | startSync / external write | مزامنة ذات أثر كتابة خارجي أو destructive sync | C09 + external account owner | SH-03 | OWN72-02 | INTEGRATION / INT-91 | OWNER REVIEW |
| APR-C09-02 | C09 | beginAuthorization / connector scope | اعتماد نطاق OAuth عالي الأثر عند اشتراط السياسة | C09 + C10 | SH-03 | OWN72-02 | INT-91-T02/T06 | OWNER REVIEW |
| APR-C10-01 | C10 | decideSecurityException / security exception | استثناء أمني محدد النطاق والمدة | C10 + approver مستقل | SH-03 | OWN72-03 | AUTH-78 / SEC-80-16 | OWNER REVIEW |
| APR-C10-02 | C10 | requestPolicyChange / policy version | اعتماد تغيير سياسة مؤثر | C10 / governance owner | SH-03 | OWN72-03 | AUTH-78 / SEC-80-13 | OWNER REVIEW |
| APR-C11-01 | C11 | approveRelease / release_candidate | اعتماد إصدار محدد artifact/hash/environment | C11 + independent reviewer | SH-03 | OWN72-06 | RELEASE / ART-95 | OWNER REVIEW |
| APR-C11-02 | C11 | deployRelease / production deployment | موافقة نشر حسب البيئة والأثر | C11 + environment owner | SH-03 | OWN72-06 | RELEASE / SEC-80 | OWNER REVIEW |
| APR-C11-03 | C11 | requestRollback / rollback | اعتماد تراجع مع مراجعة أثر البيانات والمهاجرات | C11 + impacted domain owners | SH-03 | OWN72-06 | RELEASE / BAK-87 | OWNER REVIEW |
| APR-C12-01 | C12 | publishKnowledgeDocument / approved version | نشر نسخة معرفة معتمدة ومصدرها مثبت | C12/domain content owner | SH-03 | OWN72-04 | KNOW-90 / SEC-80-11 | OWNER REVIEW |
| APR-C12-02 | C12 | exportKnowledgeBundle | تصدير حزمة معرفة تخضع ACL/classification | C12 + data owner/C10 | SH-03 عند الحاجة | OWN72-04 | KNOW-90 / INT-80-11 | OWNER REVIEW |
| APR-C13-01 | C13 | applyOwnerSettingChange | تغيير إعداد على مستوى مشروع/مؤسسة/منصة | مالك مفتاح الإعداد | SH-03 | OWN72-05 | CFG-86 | OWNER REVIEW |
| APR-C13-02 | C13 | rollbackSettingVersion | تراجع عن إعداد موروث/مفروض | مالك المفتاح + C10 عند policy | SH-03 عند الحاجة | OWN72-05 | CFG-86 | OWNER REVIEW |
| APR-C14-01 | C14 | exportDashboardReport | تصدير تقرير قد يحتوي بيانات من مصادر متعددة | أصحاب مصادر البيانات/C10 | SH-03 وفق policy | OWN36-10 / OWN72-09 | INT-80-11/13 | OWNER REVIEW |

## 2. حقول قرار الموافقة

الحد الأدنى المرشح لكل Approval Record:
approvalRef, approvalTypeId, resourceRef, resourceVersion, contentHash, organizationScope, projectScope, requestedBy, requestedAt, assignedReviewerRefs, decision, decisionReason, decidedBy, decidedAt, policyRef, evidenceRefs, expiresAt, supersedesRef, idempotencyRef, auditEventRef.

جميع schemas/types/nullability/constraints TBD إلى حين اعتمادها. يجب ألا يخزن القرار موافقة عامة غير مرتبطة بنسخة/بصمة المورد عند انطباق ذلك.

## 3. قواعد الموافقة والرفض

- مالك المجال يقرر الحاجة الدلالية للموافقة؛ SH-03 ينفذ workflow؛ C10 يحدد policy governance؛ SH-02 ينفذ authorization.
- لا يوافق AI على مخرجاته أو يوسع grants لنفسه.
- يجب فصل مقدم الطلب والمراجع عند اشتراط separation of duties.
- لا تستهلك موافقة منتهية أو ملغاة أو مرتبطة بنسخة/بصمة مختلفة.
- يعاد فحص صلاحية الموافق والسياسة وقت القرار ووقت التنفيذ المؤجل.
- عند تعطل SH-03 أو SH-04، تتبع العمليات الحساسة fail-closed وفق السياسة المعتمدة.
- لا تعني الموافقة وحدها نجاح التنفيذ أو تجاوز quality/security gates.
- كل رفض أو طلب تعديل يحتفظ بالسبب والأثر في سجل تدقيق لا يمكن للوكيل تغييره.

## 4. اختبارات القبول

| Test ID | السيناريو | معيار النجاح | الحالة |
|---|---|---|---|
| APR-98-T01 | طلب موافقة بلا resource version/hash | رفض أو اشتراط الحقول بحسب نوع المورد | SPECIFIED / NOT RUN |
| APR-98-T02 | موافقة من actor غير مخول | رفض القرار | SPECIFIED / NOT RUN |
| APR-98-T03 | self-approval | رفض عند اشتراط reviewer مستقل | SPECIFIED / NOT RUN |
| APR-98-T04 | استهلاك approval بعد expiry/revoke | رفض التنفيذ | SPECIFIED / NOT RUN |
| APR-98-T05 | resource hash changed after approval | إبطال القرار القديم | SPECIFIED / NOT RUN |
| APR-98-T06 | سحب صلاحية الموافق بعد القرار وقبل التنفيذ | إعادة التحقق وفق policy | SPECIFIED / NOT RUN |
| APR-98-T07 | SH-03 unavailable لعملية حساسة | fail closed | SPECIFIED / NOT RUN |
| APR-98-T08 | SH-04 unavailable لعملية audit-required | لا أثر صامت غير مدقق | SPECIFIED / NOT RUN |
| APR-98-T09 | waiver بلا expiry أو owner | رفض | SPECIFIED / NOT RUN |
| APR-98-T10 | rollback متعدد المجالات | كل مالك متأثر يراجع أثر مجاله | SPECIFIED / NOT RUN |
| APR-98-T11 | export cross-domain | تحقق ACL لكل مصدر قبل التصدير | SPECIFIED / NOT RUN |
| APR-98-T12 | AI agent يحاول اعتماد مخرجه | منع وتسجيل الحدث | SPECIFIED / NOT RUN |

## 5. قرارات مفتوحة

- اعتماد هل كل نوع موافقة إلزامي أو مشروط بسياسة أو غير مطلوب.
- تعيين Domain Owner وApprover role وseparation-of-duties لكل نوع.
- اعتماد quorum/delegation/expiry/escalation/SLA.
- ربط كل نوع بـOWN مؤهل ومحور AX ومصدره وعقود API/Events واختبارات مفردة.
- اعتماد ما إذا كانت الموافقة على resource hash أو version أو policy snapshot أو أكثر.
- ربط retention/privacy/legal hold وسياسة الأدلة.

**لا تعتمد هذه الوثيقة أنواع الموافقات أو أدوار الموافقين ولا تنشئ workflow. جميع APR-Cxx وAPR-98 OPEN؛ الاختبارات SPECIFIED / NOT RUN.**



## ملحق 99 — ربط الموافقات بالعمليات

أضافت الوثيقة 99 جدول ربط أولياً لأنواع الموافقات الـ26 بعمليات 97. تطابق 25 نوعاً اسم عملية حرفياً، بينما APR-C07-02 مرتبط بطلب `requestQualityWaiver` في OP-C07-118؛ أما عملية قرار الموافقة النهائية فتظل TBD، ولا يجوز اعتبار عملية الطلب نفسها قرار اعتماد. يلزم تحديد عملية القرار ومالكها وtest ID من المالكين.
