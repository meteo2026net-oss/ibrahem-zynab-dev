# 150 — مرشحات ربط عمليات C11–C14 بسيناريوهات القبول المحلية

**التاريخ:** 2026-11-16  
**الحالة:** SEMANTIC CANDIDATES / OWNER REVIEW REQUIRED / NOT APPROVED / NOT EXECUTED.

## 1. الغرض والنتيجة

تستكمل هذه الوثيقة الفجوة التي رصدتها الوثيقة 149. تحتوي حزم 128–131 على سيناريوهات قبول محلية كاملة الأوصاف، لكن جداول العمليات لا تربط كل عملية بمعرف اختبار. أعددت أدناه **اقتراحات ربط دلالية** لـ47 عملية في C11–C14، اعتماداً على غرض العملية ووصف السيناريو والنتيجة المتوقعة.

هذه الروابط **ليست موجودة في المصدر كروابط معتمدة**؛ إنها مرشحات تحريرية تحتاج مراجعة مالك كل مركز ومالك الاختبار. لم أغير سجل 97 ولم أستبدل حالة EXACT TEST ID TBD.

## 2. ملخص التغطية المرشحة بعد الوثيقتين 149 و150

| المركز | العمليات | روابط صريحة في workpaper | مرشحات دلالية مقترحة هنا | الحالة |
|---|---:|---:|---:|---|
| C08 | 11 | 11 | 0 | Candidate references from 126 |
| C09 | 12 | 12 | 0 | Candidate references from 126 |
| C10 | 11 | 11 | 0 | Candidate references from 127 |
| C11 | 12 | 0 | 12 | Semantic candidates only |
| C12 | 12 | 0 | 12 | Semantic candidates only |
| C13 | 10 | 0 | 10 | Semantic candidates only |
| C14 | 13 | 0 | 13 | Semantic candidates only |
| **الإجمالي** | **81** | **34** | **47** | **0 approved exact links** |

## 3. مرشحات الربط الدلالية

| المركز | العملية | سيناريوهات مرشحة | سبب الترشيح المختصر |
|---|---|---|
| C11 | OP-C11-155 validateBuildProfile | C11-AT-002 | ملف البناء والتحقق من توافقه |
| C11 | OP-C11-156 requestBuild | C11-AT-001, C11-AT-003, C11-AT-004, C11-AT-005, C11-AT-021 | طلب build، تثبيت المصدر، منع التكرار وفشل runner |
| C11 | OP-C11-157 getBuildResult | C11-AT-005, C11-AT-018, C11-AT-022 | قراءة نتيجة build وتوفر telemetry |
| C11 | OP-C11-158 attestArtifact | C11-AT-007, C11-AT-008 | مطابقة artifact وprovenance والتوقيع |
| C11 | OP-C11-159 createReleaseCandidate | C11-AT-008, C11-AT-009 | قبول artifact وعزل tenant |
| C11 | OP-C11-160 submitReleaseForApproval | C11-AT-010, C11-AT-012 | أدلة gate وربط الموافقة ببصمة المرشح |
| C11 | OP-C11-161 approveRelease | C11-AT-012, C11-AT-013 | ثبات المرشح وفصل مقدم الطلب عن المعتمد |
| C11 | OP-C11-162 deployRelease | C11-AT-011, C11-AT-015 | منع النشر دون موافقة والتحقق بعد النشر |
| C11 | OP-C11-163 promoteEnvironment | C11-AT-014 | بوابات البيئة |
| C11 | OP-C11-164 runPostDeployChecks | C11-AT-015, C11-AT-018 | فحوص ما بعد النشر وتوفر القياس |
| C11 | OP-C11-165 requestRollback | C11-AT-016, C11-AT-019 | خطة التراجع ومتطلبات النسخ الاحتياطي |
| C11 | OP-C11-166 getReleaseStatus | C11-AT-017, C11-AT-018, C11-AT-020, C11-AT-022 | قراءة الحالة والأحداث والأدلة |
| C12 | OP-C12-167 createKnowledgeDocument | C12-AT-001, C12-AT-003 | إنشاء وثيقة مع owner ومصدر وصلاحيات |
| C12 | OP-C12-168 createDocumentVersion | C12-AT-005, C12-AT-006, C12-AT-008 | نسخة immutable وربط المراجعة ببصمة النسخة |
| C12 | OP-C12-169 linkSourceEvidence | C12-AT-002, C12-AT-003, C12-AT-015, C12-AT-021 | provenance للمصادر والصفحات واكتشاف الانحراف |
| C12 | OP-C12-170 generateDocumentationDraft | C12-AT-002, C12-AT-003, C12-AT-020 | توليد مسودة من مصادر مفوضة ومقاومة prompt injection |
| C12 | OP-C12-171 submitDocumentReview | C12-AT-006, C12-AT-007 | تثبيت نسخة المراجعة ومنع النشر قبل الاعتماد |
| C12 | OP-C12-172 decideDocumentReview | C12-AT-007, C12-AT-008 | قرار مراجعة وربطه ببصمة المحتوى |
| C12 | OP-C12-173 publishKnowledgeDocument | C12-AT-007, C12-AT-008 | النشر بعد الموافقة وتثبيت النسخة |
| C12 | OP-C12-174 searchKnowledge | C12-AT-003, C12-AT-009, C12-AT-010, C12-AT-011, C12-AT-014, C12-AT-020 | ACL وtenant isolation والتنقيح وحداثة الفهرس |
| C12 | OP-C12-175 compareDocumentVersions | C12-AT-017 | مقارنة نسختين دون تعديلهما |
| C12 | OP-C12-176 exportKnowledgeBundle | C12-AT-018 | حقوق وترخيص وتصدير |
| C12 | OP-C12-177 archiveKnowledgeDocument | C12-AT-012, C12-AT-019 | legal hold والاحتفاظ |
| C12 | OP-C12-178 reconcileDocumentation | C12-AT-014, C12-AT-021, C12-AT-022 | كشف drift وfreshness وsource lineage |
| C13 | OP-C13-179 getEffectiveSettings | C13-AT-001, C13-AT-015, C13-AT-020 | قيمة فعالة ومصدرها وprecedence |
| C13 | OP-C13-180 validateSettingValue | C13-AT-011, C13-AT-012 | allowlist وschema validation |
| C13 | OP-C13-181 updateUserPreference | C13-AT-010 | تفضيل شخصي لا يؤثر في الآخرين |
| C13 | OP-C13-182 requestScopedSettingChange | C13-AT-004, C13-AT-016 | طلب تغيير مع موافقة ومالك وأثر |
| C13 | OP-C13-183 decideSettingChange | C13-AT-004, C13-AT-005, C13-AT-016 | قرار ومطابقة نسخة الإعداد |
| C13 | OP-C13-184 applyOwnerSettingChange | C13-AT-009, C13-AT-017, C13-AT-018 | الكتابة عبر API المالك وعدم إصدار applied عند الفشل |
| C13 | OP-C13-185 compareSettingVersions | C13-AT-005, C13-AT-020 | مقارنة نسخ الإعداد ومصدر القيمة الفعالة |
| C13 | OP-C13-186 rollbackSettingVersion | C13-AT-007, C13-AT-020 | التراجع والتاريخ الفعال |
| C13 | OP-C13-187 exportNonSecretSettings | C13-AT-006, C13-AT-014 | تصدير غير سري مع حماية الأسرار |
| C13 | OP-C13-188 diagnoseInheritance | C13-AT-001, C13-AT-015, C13-AT-020 | تشخيص precedence ومصدر التوريث |
| C14 | OP-C14-189 getDashboardOverview | C14-AT-003, C14-AT-004, C14-AT-005, C14-AT-006, C14-AT-022 | مصادر وfreshness وتعطل مركز وتوافق snapshot |
| C14 | OP-C14-190 getCenterSummary | C14-AT-003, C14-AT-006, C14-AT-022 | مصدر المؤشر واختلاف الإسقاط |
| C14 | OP-C14-191 getMyTasksOverview | C14-AT-017 | نطاق actor |
| C14 | OP-C14-192 getApprovalQueue | C14-AT-015 | SH-03 مصدر حقيقة قائمة الموافقات |
| C14 | OP-C14-193 getQualityOverview | C14-AT-011, C14-AT-012 | نتائج الاختبار وتمييز QA عن operational health |
| C14 | OP-C14-194 getSecurityOverview | C14-AT-001, C14-AT-010, C14-AT-013 | عزل المستأجر والصلاحيات وسياق cache |
| C14 | OP-C14-195 getIntegrationHealth | C14-AT-004, C14-AT-014 | مصدر متعطل أو health stale |
| C14 | OP-C14-196 getReleaseOverview | C14-AT-016 | حالة الإصدار من C11 لا استنتاجها من build |
| C14 | OP-C14-197 getOperationalHealth | C14-AT-004, C14-AT-014 | توافر المراكز والقياس التشغيلي |
| C14 | OP-C14-198 saveDashboardLayout | C14-AT-009 | عزل إعدادات التخطيط بين المستخدمين |
| C14 | OP-C14-199 pinWidget | C14-AT-009, C14-AT-020 | تفضيل widget وحدود إعداد العرض |
| C14 | OP-C14-200 exportDashboardReport | C14-AT-008, C14-AT-018, C14-AT-021, C14-AT-022 | تصدير متعدد المصادر واتساق snapshot وlineage |
| C14 | OP-C14-201 invalidateProjection | C14-AT-002, C14-AT-010, C14-AT-013, C14-AT-019 | إبطال cache بعد تغير الصلاحيات ومنع refresh loop |

## 4. قواعد قبول هذه المرشحات

1. يراجع مالك المركز كل رابط ويقبل أو يرفض أو يستبدل معرف السيناريو.
2. يجب أن يغطي الاختبار السلوك الأساسي والحدود السلبية والصلاحيات والعزل والأخطاء وidempotency حيث ينطبق.
3. قد يرتبط الاختبار الواحد بأكثر من عملية، وقد تحتاج العملية عدة اختبارات؛ لا تفرض 1:1.
4. يجب إضافة expected result وtest data وenvironment وevidence artifact لكل رابط معتمد.
5. إذا لم يختبر السيناريو العملية فعلاً، يُرفض الرابط حتى لو تشابه الاسم.
6. بعد موافقة المالك فقط تُنقل الروابط المقبولة إلى سجل 97 وتُحدّث مصفوفة التتبع.

## 5. حالة التنفيذ

- سيناريوهات C11: 22؛ C12: 22؛ C13: 20؛ C14: 22. جميعها SPECIFIED / NOT RUN.
- المرشحات الـ47 لا تغير نتيجة 0/81 exact Operation→Test في سجل 97.
- الأحداث الـ55 ما زالت بلا exact Event→Test؛ واستخدام هذه الاختبارات لحدث ما يحتاج ربطاً مستقلاً.
- الموافقات الـ14 ما زالت OWNER REVIEW.
- لا توجد أدلة تشغيل أو نتائج PASS في هذه الجولة.

## 6. الحكم

**47 ADDITIONAL SEMANTIC TEST CANDIDATES IDENTIFIED; OWNER REVIEW REQUIRED.**  
هذه الخطوة تهيئ crosswalk قابل للمراجعة ولا تعتمد الربط نيابة عن مالكي المراكز. لا تغيّر المصدر الأصلي B08–B14، ولا تمنح موافقة برمجة أو تنفيذاً.
