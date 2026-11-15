# 95 — حزمة قرارات ملكية سجل المخرجات والـArtifact Registry

**الحالة:** DERIVED DOSSIER / OWNER REVIEW REQUIRED / NOT APPROVED / NO IMPLEMENTATION  
**التاريخ:** 2026-11-15  
**المراجع:** 32 (SH-10/SH-15)، 36 (OWN36-02/06/10)، 67 (C11)، 72 (OWN72-01/06)، 77 (FLD-16)، 80، 82، 85 (VER-85-09)، 87، 88، 93، 94.  
**حد المصدر:** العقود الحالية تشير إلى build artifacts/attestations/provenance والتوقيع والاحتفاظ، لكنها لا تحسم مالك سجل المخرجات أو binary authority. هذه الحزمة قرار مطلوب وليست استعادة حرفية لمحتوى غير متاح.

## 1. المشكلة وحدود الحقيقة

يجب فصل أربعة أشياء لا يجوز دمجها:
1. **Artifact domain record:** سجل أن مخرجاً معيناً نُتج من build/release معين، ويملكه C11 كحقيقة دورة البناء/الإصدار.
2. **Artifact binary/object:** البايتات الفعلية ومكان تخزينها والتحكم بالوصول ودورة حياة الكائن؛ المالك التشغيلي/التقني لم يُحسم بين C08/SH-06 أو خدمة مخصصة معتمدة.
3. **Artifact provenance/attestation:** إثبات المصدر وسلسلة البناء والأدوات والتبعيات والتوقيع والتحقق؛ C11 يملك علاقة الإثبات بالمخرج، لكن سلطة التوقيع ومخزن المفاتيح والمعايير تحتاج اعتماداً من C10 ومالك المنصة.
4. **Release lifecycle:** C11 يملك candidate/approval/promotion/deployment/rollback؛ SH-10 يملك metadata عامة للإصدارات عند اعتمادها، ولا يحل محل دورة الإصدار.

## 2. قرارات مطلوبة

| ID | القرار | التوصية | الحالة |
|---|---|---|---|
| ART-95-01 | مالك Artifact Registry | C11 يملك artifact records وعلاقات build/release؛ binary storage owner قرار مستقل | OPEN |
| ART-95-02 | مالك binary/object bytes | تقييم SH-06/C08 مقابل مخزن artifacts منفصل؛ لا قرار مزود مسبق | OPEN |
| ART-95-03 | SoR للـartifact metadata | C11 domain SoR لـartifact identity/build association/release eligibility | OPEN |
| ART-95-04 | سجل digest/content hash | algorithm/encoding/canonicalization policy تعتمد قبل التنفيذ | OPEN |
| ART-95-05 | التوقيع وسلطة المفاتيح | C10 policy governance؛ signing service/key custody owner TBD؛ لا private keys في C11 DB | OPEN |
| ART-95-06 | provenance/attestation schema | ربط source commit/tree، build recipe/toolchain، dependency/SBOM refs، builder identity، timestamps، digest | OPEN |
| ART-95-07 | artifact immutability | لا overwrite لنفس immutable digest؛ أي تغيير ينتج artifact identity/version جديدة | OPEN |
| ART-95-08 | retention/deletion/legal hold | يتبع تصنيف 88 ومتطلبات الامتثال؛ مدد/أساس قانوني TBD | OPEN |
| ART-95-09 | tenant/project access | عزل على مستوى record/object/index/download URL؛ تحقق ACL عند الوصول | OPEN |
| ART-95-10 | artifact promotion | الترويج ينقل مرجعاً موثقاً بين البيئات ولا يعيد بناء binary ضمناً | OPEN |
| ART-95-11 | vulnerability/revocation | آلية quarantine/revoke/supersede مع أثر على release/deployment | OPEN |
| ART-95-12 | artifact download/export | signed URL/short-lived capability حسب policy؛ لا public URL افتراضي | OPEN |
| ART-95-13 | backup/restore | ربط metadata وbinary consistency مع SH-15 واختبار restore | OPEN |
| ART-95-14 | registry availability/integrity | SLO/freshness/replication/recovery targets TBD؛ لا أرقام مخترعة | OPEN |
| ART-95-15 | registry migration/vendor | لا مزود أو صيغة registry معتمدة في هذه الوثيقة | OPEN |
| ART-95-16 | SBOM/license/security evidence | C11 يربط الأدلة؛ C05/C10/C07 يملكون نتائج مجالاتهم | OPEN |

## 3. نموذج الملكية المقترح

| المورد | مالك الحقيقة المقترح | دور الخدمات الأخرى |
|---|---|---|
| build_artifact_record | C11 | SH-10 version metadata reference؛ SH-04 audit |
| artifact_binary | TBD: SH-06/C08 أو registry service | تخزين/نسخ/فحص/حذف فعلي |
| artifact_digest | C11 record + verifier | الخوارزمية/التنفيذ يعتمد من C10 |
| provenance_attestation | C11 association; producer evidence from C05/build runner | signing authority TBD |
| signature_record | signing authority TBD | C10 policy; C11 verification result |
| sbom_reference | C05/C11 بحسب مصدر SBOM | C10 security review؛ C07 gate |
| vulnerability_assessment | C10/C05 حسب finding source | C11 يستهلك gate result |
| release_candidate | C11 | refs immutable to artifact records |
| deployment_record | C11 | SH-08 execution; SH-14 telemetry |
| retention/legal_hold_record | owner TBD with C10 | SH-15 backup/recovery; SH-06 binary lifecycle |

## 4. الحد الأدنى المقترح لحقول سجل المخرج

artifactId, organizationId, projectId, buildRunRef, sourceCommitRef, sourceTreeDigest, buildRecipeRef, builderIdentityRef, toolchainRef, dependencyLockRef, sbomRef, artifactType, mediaType, byteSize, contentDigest, digestAlgorithm, objectRef, provenanceRef, attestationRef, signatureRef, verificationState, securityAssessmentRef, createdAt, immutableState, retentionClass, legalHoldRef, lifecycleState.

كل حقل أعلاه مقترح حتى يطابق مصدره؛ type/nullability/format/index/unique/retention/access policy تبقى TBD. لا تُنشأ schema من هذه القائمة قبل الاعتماد.

## 5. دورة الحياة المقترحة

Produced → DigestVerified → ProvenanceAttached → Attested → SignatureVerified → SecurityEvaluated → EligibleForRelease → Promoted → Superseded/Quarantined/Revoked/Expired.

- لا يعني Produced أنه صالح للنشر.
- لا يعني التوقيع أن المحتوى آمن.
- لا يعني نجاح build اجتياز QA أو security gates.
- لا يسمح الترويج باستبدال البايتات تحت digest نفسه.
- أي revoke/quarantine يجب أن يظهر في حالة release/deployment وفق قرار C11/C10.
- لا يحذف artifact تحت legal hold أو retention lock إلا بإجراء معتمد.

## 6. العمليات والأحداث المرشحة

عمليات: registerArtifactRecord, attachArtifactBinary, computeArtifactDigest, verifyArtifactDigest, attachProvenance, requestAttestation, verifySignature, evaluateArtifactEligibility, quarantineArtifact, revokeArtifact, promoteArtifactReference, getArtifactEvidence, requestArtifactDownload, reconcileArtifactStorage.

أحداث: ArtifactRecordCreated, ArtifactDigestVerified, ArtifactProvenanceAttached, ArtifactAttested, ArtifactSignatureVerified, ArtifactEligibilityChanged, ArtifactQuarantined, ArtifactRevoked, ArtifactPromoted, ArtifactStorageReconciled.

يجب أن يملك C11 معنى أحداث artifact/release؛ لا تملك SH-08 أو SH-14 أو SH-15 الحدث الدلالي لمجرد أنها تنفذ أو تراقب أو تحفظ نسخة.

## 7. اختبارات القبول

| Test ID | السيناريو | معيار النجاح | الحالة |
|---|---|---|---|
| ART-95-T01 | تغيير البايتات مع إبقاء digest | كشف mismatch ورفض التسجيل | SPECIFIED / NOT RUN |
| ART-95-T02 | artifact بلا source/build provenance | لا يصبح eligible for release | SPECIFIED / NOT RUN |
| ART-95-T03 | signature invalid أو key revoked | رفض التحقق/الترويج | SPECIFIED / NOT RUN |
| ART-95-T04 | artifact من tenant آخر | رفض الوصول دون كشف metadata | SPECIFIED / NOT RUN |
| ART-95-T05 | overwrite لنفس immutable digest | منع overwrite | SPECIFIED / NOT RUN |
| ART-95-T06 | promotion إلى بيئة أعلى | نفس artifact digest وattestation refs | SPECIFIED / NOT RUN |
| ART-95-T07 | security finding blocking | quarantine/منع release وفق policy | SPECIFIED / NOT RUN |
| ART-95-T08 | revoke بعد deployment | يطلق workflow مراجعة أثر release/deployment | SPECIFIED / NOT RUN |
| ART-95-T09 | حذف تحت legal hold | منع الحذف | BLOCKED: PRIV-88 policy TBD |
| ART-95-T10 | backup/restore | metadata وbinary refs/digests متطابقة | BLOCKED: BAK-87/RTO-RPO TBD |
| ART-95-T11 | download بعد ACL revoke | رفض capability/URL | SPECIFIED / NOT RUN |
| ART-95-T12 | فقد object مقابل بقاء metadata | كشف orphan/missing binary وعدم إعلان integrity | SPECIFIED / NOT RUN |
| ART-95-T13 | اختلاف SBOM عن lockfile/build inputs | إيقاف gate وطلب reconciliation | SPECIFIED / NOT RUN |
| ART-95-T14 | registry migration | preserve digest/provenance/signatures/access/retention | BLOCKED: migration policy TBD |

## 8. روابط القرار والاختبارات

- FLD-16: مفتوحة؛ ART-95 يحولها إلى مجموعة قرارات قابلة للاعتماد ولا يغلقها.
- OWN36-02 وOWN72-01: تخزين الأصول مقابل سجل المجال؛ يلزم اعتماد C08/SH-06.
- OWN36-06 وOWN72-06: SH-10 version metadata مقابل C11 lifecycle.
- VER-85-09: مالك registry والتوقيع والاحتفاظ؛ يبقى مفتوحاً حتى اعتماد هذه الحزمة.
- PRIV-88 وBAK-87 وMON-93: الاحتفاظ والتعافي والمراقبة؛ لا تنقل الملكية إلى SH-15 أو SH-14.
- RI-94-13: يتطلب ربط artifact operations/events باختبارات محددة بعد اعتماد المالكين.

## 9. شروط الإغلاق

تعيين C11 domain owner؛ تعيين binary storage owner؛ اعتماد digest/signature/provenance standards؛ تعيين key custody/signing authority؛ اعتماد immutable lifecycle/quarantine/revocation؛ تحديد retention/legal hold وtenant ACL؛ اعتماد backup/recovery consistency؛ ربط كل API/event/approval بقرار OWN مؤهل ومحور AX واختبار؛ تنفيذ الاختبارات بأدلة.

**لا تعين هذه الحزمة مزوداً أو مخزن مفاتيح أو معيار توقيع، ولا تنشئ registry أو schema أو APIs أو خدمات. لا تغلق FLD-16 أو VER-85-09.**
