# 70 — مسودة عقود C14: لوحة التحكم الرئيسية

**الحالة:** DERIVED-DRAFT / SOURCE-ALIGNED / NOT-APPROVED / NO-IMPLEMENTATION  
**المراجع:** 02 و09 و21 و23–27 و36 و56.

## 1. حدود المركز

C14 هو مركز لوحة التحكم الرئيسي (20 شاشة). هو طبقة قراءة وتجميع وتخصيص عرض. لا يملك الحقيقة الأصلية للمشاريع أو المهام أو الموافقات أو الاختبارات أو الأمن أو التكاملات أو البناء أو التكلفة أو التشغيل. لا ينفذ إجراءات كتابة؛ يوجه المستخدم إلى المركز المالك بعد التحقق من صلاحياته. التشغيل والاستمرارية قدرة مشتركة ضمن L9/L10 وليسا C14 بديلاً عن خدمات المراقبة/النسخ/التعافي.

## 2. بيانات C14 التي يملكها

dashboard_layouts, dashboard_widget_preferences, dashboard_saved_views, dashboard_filter_presets, dashboard_display_config. هذه بيانات تخص العرض والتخصيص فقط.

أما dashboard_summaries, dashboard_metrics, dashboard_alert_cards, dashboard_activity_views, dashboard_report_projections فهي projections/cache مشتقة لا تصبح source of truth. يجب أن تحمل source_ref, source_version, as_of, freshness_state, tenant_scope, aggregation_definition, permission_context_ref. ملكية المؤشر ومعناه تبقى للمركز المنتج/المالك المحدد.

## 3. العمليات المقترحة

| العملية | النوع | المخرجات | قيد |
|---|---|---|---|
| getDashboardOverview | Query | summary cards + freshness | scoped server-side |
| getCenterSummary | Query | projection + source refs | permission-aware |
| getMyTasksOverview | Query | tasks summary | SH-08/C06 task owner |
| getApprovalQueue | Query | approval references | SH-03 only |
| getQualityOverview | Query | C07 result refs | لا يعيد تفسير النجاح |
| getSecurityOverview | Query | sanitized C10 findings | role-gated |
| getIntegrationHealth | Query | C09/SH-13 health | freshness required |
| getReleaseOverview | Query | C11 build/deploy state | source linked |
| getOperationalHealth | Query | SH-14/15 status | not owned by C14 |
| saveDashboardLayout | Command | personal/org layout version | permission on layout only |
| pinWidget | Command | widget preference | لا يغير ACL |
| exportDashboardReport | Command | report artifact | export policy/ACL |
| invalidateProjection | Internal command | refresh request | idempotent |

## 4. عقود العرض والبيانات

كل بطاقة/مؤشر يجب أن تعرض تعريف المقياس، مالك المصدر، آخر تحديث، حالة الحداثة، النطاق، وأي حدود/نقص. عند فشل المصدر يظهر unavailable/stale بدلاً من قيمة قديمة تعرض كأنها حية. cache key يتضمن tenant + actor/permission context + filters + source version. لا يجوز أن يؤدي تجميع البيانات إلى كشف معلومات أكثر مما يسمح به كل مصدر.

لا تستخدم C14 أرقام تقدم أو تكلفة محسوبة محلياً إذا كان لها تعريف رسمي عند مالك آخر. لا تعلن "نجاح الاختبارات" دون مراجع artifacts ونتائج C07.

## 5. الصلاحيات

كل استعلام يخضع لصلاحيات المصدر المالك؛ لا يكفي إخفاء widget في الواجهة. مسؤول لوحة التحكم يدير تخطيطات عامة فقط؛ المستخدم يدير تخطيطه الشخصي؛ مسؤول تقارير يصدر فقط نطاقاً مخولاً؛ الوكيل قد يلخص مؤشرات مفوضة ولا يوسع النطاق أو يغير البيانات؛ لا توجد موافقات أو عمليات نشر أو إغلاق finding داخل C14.

## 6. اختبارات مقترحة غير منفذة

- C14-DRAFT-AT-01: مستخدم في مؤسسة A لا يرى مؤشرات مؤسسة B.
- C14-DRAFT-AT-02: cache لا يعيد بيانات مستخدم سابق بعد تغير الصلاحية.
- C14-DRAFT-AT-03: كل مؤشر يحمل source/as_of/freshness.
- C14-DRAFT-AT-04: تعطل مركز واحد لا يوقف اللوحة كاملة.
- C14-DRAFT-AT-05: لا تعرض بيانات قديمة كأنها حية.
- C14-DRAFT-AT-06: أرقام اللوحة تطابق تعريف ومصدر المقياس.
- C14-DRAFT-AT-07: لا توجد عملية كتابة على سجل مجال من لوحة العرض.
- C14-DRAFT-AT-08: export يطبق ACL وتصنيف البيانات.
- C14-DRAFT-AT-09: تخصيص المستخدم لا يغير تخطيط مستخدم آخر.
- C14-DRAFT-AT-10: سحب صلاحية المصدر يخفي projection بعد إعادة التحقق.
- C14-DRAFT-AT-11: لا تعرض نتيجة اختبار بلا artifact evidence.
- C14-DRAFT-AT-12: لا تخلط operational health مع جودة المنتج أو نتائج QA.

## 7. التبعيات والقرارات المفتوحة

يستهلك C14 قراءات من C01–C13 وSH-03/04/08/11/13/14/15؛ يعتمد على SH-01/02 في الهوية والتفويض وSH-07 للبحث عند الحاجة. يحتاج اعتماد تعريفات KPIs، freshness thresholds، projection/cache policy، التقارير والتصدير، أدوار القيادة، ومصدر كل metric.

**الحكم:** C14 يبقى لوحة تحكم عرضية كما اعتمد المستخدم؛ كل عقودها المقترحة غير معتمدة.
