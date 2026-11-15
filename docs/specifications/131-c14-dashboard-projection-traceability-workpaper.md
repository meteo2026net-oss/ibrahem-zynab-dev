# 131 — حزمة تتبع C14 للوحة التحكم والإسقاطات والصلاحيات والاختبارات

**الحالة:** DERIVED TRACEABILITY WORKPAPER / OWNER REVIEW REQUIRED / NOT APPROVED / NOT IMPLEMENTED  
**التاريخ:** 2026-11-15  
**النطاق:** C14 بوصفه مركز لوحة التحكم الرئيسية؛ لا ينقل ملكية بيانات المجالات أو التشغيل إلى لوحة العرض.

## 1. النتيجة التنفيذية

| المحور | العدد/الحالة | التفسير |
|---|---:|---|
| عمليات C14 في سجل 97 | 13 | 12 استعلاماً/تخصيصاً وinvalidateProjection؛ كلها مرشحة |
| أحداث C14 في سجل 100 | 1 | PROPOSED |
| أنواع الموافقات في سجل 98 | 1 | OWNER REVIEW لتصدير التقرير |
| سيناريوهات القبول المحلية | 22 | SPECIFIED / NOT RUN |
| Exact Event→Test ID مثبت | 0/1 | لا يوجد اختبار مفرد أصلي مثبت |
| اعتماد C14 النهائي | غير حاصل | لا اعتماد KPI/cache/freshness/export schemas |

**قاعدة حاكمة:** C14 قراءة وتجميع وتخصيص عرض. لا يملك الحقيقة الأصلية للمشاريع أو المهام أو الموافقات أو الاختبارات أو الأمن أو التكاملات أو الإصدارات أو التكلفة أو التشغيل. لا ينفذ إجراءات كتابة على سجلات المجالات؛ يوجه المستخدم إلى المركز المالك بعد التحقق من الصلاحية.

## 2. ما يملكه C14 وما لا يملكه

### 2.1 بيانات يملكها C14

- dashboard_layouts
- dashboard_widget_preferences
- dashboard_saved_views
- dashboard_filter_presets
- dashboard_display_config

هذه بيانات العرض والتخصيص فقط، وجميع الكيانات ما زالت مقترحة.

### 2.2 إسقاطات لا تصبح مصدر حقيقة

dashboard_summaries وdashboard_metrics وdashboard_alert_cards وdashboard_activity_views وdashboard_report_projections هي projections/cache مشتقة. يجب أن تحمل على الأقل source_ref وsource_version وas_of وfreshness_state وtenant_scope وaggregation_definition وpermission_context_ref. معنى المقياس وملكيته يبقيان عند المصدر المالك.

| المجال المعروض | المصدر/المالك | حد C14 |
|---|---|---|
| المشاريع والمتطلبات | C01 | عرض projection مع source ref؛ لا يغير المشروع |
| التصميم والصفحات | C02/C03 | عرض حالة النسخة؛ لا يحرر التصميم |
| البيانات | C04 | لا يصبح C14 SoR للبيانات أو schema |
| البرمجة والوكلاء | C05/C06 | عرض الحالة المسموح بها؛ لا يطلق أوامر المجال |
| الجودة والاختبارات | C07 | يعرض نتائج وأدلة المصدر؛ لا يعيد تعريف النجاح |
| الملفات والوسائط | C08 | يعرض refs/metadata وفق ACL؛ لا يملك bytes |
| التكاملات | C09/SH-13 | يعرض health مع freshness؛ لا ينفذ إدارة الموصلات |
| الأمن والحوكمة | C10 | عرض findings منقحة ومقيدة بالدور |
| البناء والنشر | C11/SH-08 | يعرض release/deployment refs؛ لا ينشر |
| المعرفة | C12/SH-07/16 | يعرض مراجع الوثائق والبحث وفق ACL |
| الإعدادات | C13/SH-12 | يعرض effective setting المسموح؛ لا يغيره |
| التشغيل والنسخ | SH-14/SH-15 | يعرض operational status؛ لا يصبح مالك المراقبة أو الاستعادة |
| الموافقات | SH-03 | يعرض queue/reference؛ لا يقرر الموافقة |

## 3. سجل العمليات الـ13

المعرفات والأسماء من سجل 97؛ جميعها مرشحة وغير منشورة.

| Operation ID | الاسم | الوظيفة المرشحة | الضابط |
|---|---|---|---|
| OP-C14-189 | getDashboardOverview | بطاقات عامة ونظرة شاملة | server-side scope وfreshness |
| OP-C14-190 | getCenterSummary | ملخص مركز مع source refs | permission-aware وعدم إعادة تفسير المصدر |
| OP-C14-191 | getMyTasksOverview | ملخص مهام المستخدم | SH-08/C06 owner؛ actor-scoped |
| OP-C14-192 | getApprovalQueue | عرض طلبات الموافقة | SH-03 هو مصدر الحقيقة |
| OP-C14-193 | getQualityOverview | عرض نتائج الجودة | C07 result refs وevidence |
| OP-C14-194 | getSecurityOverview | عرض findings أمنية منقحة | role-gated وC10 policy |
| OP-C14-195 | getIntegrationHealth | صحة التكاملات | C09/SH-13 مع freshness |
| OP-C14-196 | getReleaseOverview | حالة build/release/deployment | C11/SH-08 source-linked |
| OP-C14-197 | getOperationalHealth | صحة التشغيل والنسخ | SH-14/15؛ لا يملكها C14 |
| OP-C14-198 | saveDashboardLayout | حفظ layout version | صلاحية على إعداد العرض فقط |
| OP-C14-199 | pinWidget | حفظ تفضيل widget | لا يغير ACL أو اشتراكات المصدر |
| OP-C14-200 | exportDashboardReport | تصدير تقرير متعدد المصادر | ACL/classification/approval policy |
| OP-C14-201 | invalidateProjection | طلب تحديث projection | internal/idempotent؛ لا يكتب في Domain SoR |

**ملاحظة تصنيف:** سجل 97 يصنف exportDashboardReport كـQuery candidate، بينما مسودة 70 تصفه كتصدير Command. يجب اعتماد نوع العملية بعد تحديد ما إذا كانت تنشئ artifact/job أو تعيد استجابة مباشرة؛ لا يُعدل التصنيف آلياً.

## 4. الحدث المقترح

| Event ID | الاسم | الحالة | الحد |
|---|---|---|---|
| EVT-C14-001 | DashboardProjectionRefreshed | PROPOSED | C14 يعلن تحديث projection فقط بعد اكتمال refresh وتثبيت source versions وfreshness |

لا يعني هذا الحدث أن المجال المصدر تغير أو أن العملية الأصلية نجحت. يجب تعريف event schema وproducer وconsumer وtenant scope وsource refs وas_of وfreshness وcorrelation IDs وidempotency وreplay قبل التنفيذ.

## 5. نوع الموافقة

| Approval ID | الفعل | سياق القرار | الضابط |
|---|---|---|---|
| APR-C14-01 | exportDashboardReport | تصدير تقرير قد يجمع بيانات من مصادر متعددة | أصحاب مصادر البيانات/C10 حسب classification وSH-03 وفق policy |

الحالة OWNER REVIEW. يجب تحديد متى يكفي ACL المعتاد ومتى يلزم قرار موافقة مستقل، وربط القرار بنطاق التقرير ومصادره وfilters ونسخته وrecipient/export destination. لا توجد موافقات على dashboard layout أو pinWidget ما لم يقرر مالك المنتج خلاف ذلك.

## 6. قواعد العرض والحداثة والعزل

1. كل metric يعرض تعريفه ومالك المصدر ووقت as_of وحالة freshness والنطاق.
2. عند تعطل المصدر يعرض unavailable أو stale بوضوح، ولا تعرض القيمة القديمة كأنها live.
3. cache key يتضمن tenant وactor/permission context وfilters وsource version.
4. لا يؤدي aggregation إلى كشف معلومات أكثر مما تسمح به كل المصادر.
5. لا تحسب C14 تقدماً أو تكلفة إذا كان للمقياس تعريف رسمي لدى مالك آخر.
6. لا تعلن نجاح الاختبارات دون مراجع artifacts ونتائج C07.
7. سحب صلاحية المصدر يجب أن يؤثر على projection/cache وفق SLA معتمد.
8. تعطل مركز واحد لا ينبغي أن يوقف كل اللوحة؛ يجب إظهار حالة المصدر المتعطل دون إخفائه.
9. لا تستخدم قيم تقديرية على أنها actual؛ يعرض النوع والمصدر والفترة.
10. تخصيصات المستخدم لا تتجاوز سياسة المؤسسة أو ACL.

## 7. سيناريوهات القبول والتكامل المحلية

المعرفات محلية وليست Test IDs أصلية. جميعها SPECIFIED / NOT RUN.

| Test ID المحلي | السيناريو | النتيجة المتوقعة |
|---|---|---|
| C14-AT-001 | مستخدم في tenant A يطلب لوحة tenant B | لا تظهر أي بيانات أو metadata من B |
| C14-AT-002 | إعادة استخدام cache بعد تغير صلاحيات actor | لا تعاد بيانات السياق السابق |
| C14-AT-003 | عرض أي metric | يظهر source/as_of/freshness/owner |
| C14-AT-004 | تعطل مركز واحد | بقية اللوحة تعمل مع بيان المصدر unavailable |
| C14-AT-005 | بيانات مصدر قديمة | تظهر stale ولا تعرض كأنها live |
| C14-AT-006 | اختلاف قيمة لوحة عن مصدرها | يكتشف الفرق ويعرض تعريف aggregation/version |
| C14-AT-007 | محاولة كتابة على سجل مجال من C14 | رفض؛ توجيه المستخدم إلى المركز المالك |
| C14-AT-008 | export يتضمن مصادر متفاوتة الحساسية | يطبق ACL/classification/approval لكل مصدر |
| C14-AT-009 | مستخدمان يحفظان layout | لا يؤثر layout أحدهما في الآخر |
| C14-AT-010 | سحب صلاحية مصدر | projection لا يكشف بياناته بعد إعادة التحقق |
| C14-AT-011 | نتيجة اختبار بلا artifact evidence | لا تعرض على أنها نجاح موثق |
| C14-AT-012 | operational health مقابل QA quality | لا تخلط المؤشرين أو تسمي أحدهما نجاحاً للآخر |
| C14-AT-013 | cache key لا يتضمن permission context | يفشل اختبار العزل ويمنع النشر |
| C14-AT-014 | مصدر SH-14 متوقف | تعرض حالة telemetry unavailable دون تخمين |
| C14-AT-015 | approval queue | تعرض المراجع من SH-03 ولا تنشئ قراراً محلياً |
| C14-AT-016 | release overview | تربط الحالة بمرجع C11 ولا تستنتج النشر من build success |
| C14-AT-017 | getMyTasksOverview | لا يعرض مهام مستخدم آخر إلا بتفويض |
| C14-AT-018 | export أثناء تغير source version | يثبت snapshot أو يرفض التقرير غير المتسق |
| C14-AT-019 | invalidateProjection متكرر | idempotent ولا ينشئ refresh loops |
| C14-AT-020 | widget غير مصرح لمصدره | يخفي البيانات ويمنع الوصول server-side |
| C14-AT-021 | مؤشر تكاليف estimated مقابل actual | يعرض النوع والمصدر والفترة بوضوح |
| C14-AT-022 | dashboard summary من مصادر متعددة | يحتفظ بكل source refs وversion lineage ولا يخفي نقص المصدر |

## 8. القرارات المفتوحة

1. اعتماد تعريفات KPIs ومالك كل metric وصيغة aggregation.
2. اعتماد freshness thresholds لكل مصدر ونوع بيانات.
3. اعتماد projection/cache architecture وinvalidation triggers وSLA.
4. اعتماد permission-context-aware cache key والعزل بين tenants/users/roles.
5. اعتماد export snapshot semantics وartifact ownership والاحتفاظ.
6. اعتماد classification policy ومتى يلزم APR-C14-01.
7. اعتماد source outage/degraded mode وإظهار unavailable/stale.
8. اعتماد صلاحيات الإدارة على layouts العامة مقابل الشخصية.
9. اعتماد event producer/schema للحدث EVT-C14-001.
10. اعتماد سياسة إظهار security findings والبيانات الحساسة.
11. تحديد هل C14 يعرض بيانات مباشرة أو projections أو الاثنين وفق كل widget.
12. ربط العمليات والحدث والموافقة باختبارات أصلية أو اختبارات معتمدة وتشغيلها.

## 9. معيار إغلاق C14

لا ينتقل C14 إلى APPROVED أو READY-FOR-IMPLEMENTATION حتى:
- اعتماد قائمة widgets والـmetrics ومالكي مصادرها وتعريفاتها.
- اعتماد contracts لكل projection وfreshness/cache/invalidation.
- اعتماد ACL/tenant isolation على الخادم وعلى cache/export.
- اعتماد سياسة التصدير والموافقة والتصنيف والاحتفاظ.
- اعتماد event schema ودلالته وعدم مساواته بتغيير المجال.
- اعتماد صلاحيات layout/preferences ومنع أي domain write.
- ربط الاختبارات بمعرفات موثوقة وتشغيلها وإرفاق الأدلة.
- مراجعة مالكي C01–C13 وC10 وSH-03/08/13/14/15.

**الحكم النهائي:** C14 موثق في حزمة تتبع أولية؛ يبقى DERIVED / OWNER REVIEW / NOT APPROVED / NOT IMPLEMENTED. لا تثبت هذه الوثيقة وجود لوحة تشغيلية أو metrics pipeline أو projection/cache أو تصدير عامل.
