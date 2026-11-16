# 180 — تدقيق محرك التنفيذ الدائم وقواعد الموافقة وقواعد البيانات والنسخ الاحتياطي

**الحالة:** STATIC SOURCE AUDIT / NO RUNTIME EXECUTION / NO CODE CHANGES  
**Commit baseline:** `1e50a6957e75740ff42137ead1f051431f65932e`  
**النطاق:** مراجعة TaskOrchestrator وPersistentJobWorker وAutomationRepository وDatabaseIntegrationRepository؛ لا تشغيل مهام فعلية ولا اتصال بقواعد بيانات خارجية.

## 1. الخلاصة

توجد بنية برمجية لمحرك مهام دائم تتضمن مفاهيم leases وheartbeats وcheckpoints وidempotency وموافقات بشرية، لكن التنفيذ الحالي لا يثبت هذه الضمانات تشغيلياً. أبرز الفجوات: لا يوجد استحواذ ذري على lease ولا تحقق فعلي من leaseToken قبل متابعة التنفيذ، وفشل تجديد heartbeat يتم تجاهله، وحفظ حالة المهمة يبتلع أخطاء الكتابة. كذلك توجد عمليات تعرض نجاحاً شكلياً دون تنفيذ العملية المعلنة: استعلام SQL محاكاة، المزامنة تولد أعداداً عشوائية وتكتب حالة COMPLETED، والنسخ الاحتياطي يخزن ملخصاً مشفراً في Firestore دون رفع نسخة ملفات فعلية إلى Google Drive، والاستعادة لا تعيد إنشاء المشروع أو ملفاته.

**الحكم:** محرك التنفيذ والنسخ الاحتياطي والتكامل مع قواعد البيانات غير جاهز للاستخدام الإنتاجي أو لعرض نتائج نجاح حقيقية.

## 2. أدلة الملفات وبصماتها

| الملف | SHA |
|---|---|
| `app/src/main/java/com/example/data/orchestrator/TaskOrchestrator.kt` | `ea083a95d996c8c536117f255f9ae234435a8611` |
| `app/src/main/java/com/example/data/worker/PersistentJobWorker.kt` | `72ec73cc89eb9b5f0d2d7eb47083a02e01c30c90` |
| `app/src/main/java/com/example/data/repository/AutomationRepository.kt` | `139c3578c343e8c4e276de348979fae715e3d4a6` |
| `app/src/main/java/com/example/data/repository/DatabaseIntegrationRepository.kt` | `87148a705188f4afc9da592e69de67525f531008` |
| `app/src/main/java/com/example/util/AppCheckHelper.kt` | `602918b955bed1fb30fd876085e295cd9f98691f` |
| `app/src/main/res/xml/backup_rules.xml` | `90529f8308df85b28292e3fbe783198efe9f8dfc` |
| `app/src/main/res/xml/data_extraction_rules.xml` | `0c4f95cab912665e8bfd0f0856471908c4d10d5b` |

## 3. النتائج

### ORCH-01 — استحواذ lease غير ذري ولا يتحقق من ملكية lease
**التصنيف:** CONFIRMED FROM SOURCE / P0

`executeJob` يقرأ مستند المهمة ثم يكتب `workerId` و`leaseToken` و`leaseExpiresAt` دون Firestore transaction أو شرط compare-and-set يفحص أن lease السابق منتهي أو يخص العامل نفسه. يمكن لعاملين متزامنين قراءة الحالة نفسها ثم استبدال lease ومتابعة التنفيذ بالتوازي. كما أن `renewHeartbeat` يكتب العامل والرمز الجديدين دون شرط أن الرمز المخزن يساوي رمز العامل الحالي؛ ولا يوقف التنفيذ إذا فشل التجديد.

**المعالجة:** تنفيذ claim/renew/release عبر backend موثوق ومعاملة ذرية تتحقق من المالك الحالي وانتهاء lease والرمز؛ كل خطوة حساسة تتحقق من lease قبل وبعد العملية. فشل heartbeat يجب أن يوقف التنفيذ أو يحوله لحالة آمنة، لا أن يستمر العامل بصمت.

### ORCH-02 — هوية العامل الافتراضية عند غياب المصادقة
**التصنيف:** CONFIRMED FROM SOURCE / P0

عند غياب `Firebase.auth.currentUser` يستخدم المحرك معرف `local_worker` بدلاً من رفض التنفيذ. لا يكفي هذا المعرف لتحديد مستخدم أو مؤسسة مالكة أو صلاحية تشغيل.

**المعالجة:** رفض التنفيذ دون هوية موثقة ومطالبة backend بهوية خدمة/عامل موثوقة ونطاق job/project/tenant.

### ORCH-03 — أخطاء حفظ الحالة ونقاط التحقق لا تفشل المهمة
**التصنيف:** CONFIRMED FROM SOURCE / P1

`saveJobState` و`saveCheckpoint` يلتقطان الاستثناءات ويسجلانها فقط ثم يعودان دون إبلاغ المستدعي. بذلك قد يستمر المحرك أو يعيد نجاحاً رغم عدم ثبوت حفظ الحالة/نقطة التحقق. كما أن `saveJobState` يستخدم `set` لكائن المهمة كاملاً، ما قد يستبدل تحديثات متزامنة.

**المعالجة:** جعل persistence جزءاً من شرط نجاح الخطوة؛ فشل checkpoint يمنع التقدم. استخدم transaction/version field أو تحديثات مشروطة لمنع lost updates، وسجل حالة durable واضحة.

### ORCH-04 — التحقق من الموافقة غير مرتبط بالطلب الكامل
**التصنيف:** CONFIRMED FROM SOURCE / P0

`checkExistingApproval` يبحث فقط عن `projectId` و`targetEntityId` و`status=APPROVED`. لا يتحقق من `userId` أو نوع العملية أو job/step النسخة الحالية أو صلاحية الموافقة الزمنية أو هوية صاحب القرار. هذا أضعف من نموذج الموافقات المركزي المعتمد.

**المعالجة:** قرار موافقة backend موقّع/مدقق ومربوط بطلب محدد immutable يتضمن tenant/workspace/project/job/step/operation/parameters/expiry/approver policy، ولا يجوز إعادة استخدام موافقة عامة لمعرف خطوة متكرر.

### ORCH-05 — فحص idempotency لا يتحقق من مالك السجل أو نوع الإجراء
**التصنيف:** CONFIRMED FROM SOURCE / P1

`checkIdempotency` يجلب المستند بواسطة المفتاح فقط، ويقرأ `userId` و`actionType` لكنه لا يرفض السجل إذا لم يطابق المستخدم الحالي أو نوع الإجراء. كذلك كتابة السجل تتم بعد العملية الخارجية؛ إذا نجحت العملية وفشل تسجيل المفتاح، قد يعاد تنفيذ العملية عند retry.

**المعالجة:** backend idempotency ledger بمفتاح scoped إلى tenant/user/job/action/parameters hash، مع atomic reservation ونتيجة العملية وحالة reconciliation، واختبارات انقطاع قبل/بعد external side effect.

### DB-01 — تنفيذ SQL محاكاة وليس اتصالاً بقاعدة البيانات
**التصنيف:** CONFIRMED FROM SOURCE / P0

`DatabaseIntegrationRepository.executeQuery` يحل مرجعاً سرياً ثم لا يستخدم اتصالاً فعلياً بقاعدة البيانات؛ التعليق نفسه يصف المرحلة بأنها `Query execution simulation`. استعلام SELECT يعيد صفوفاً ثابتة بأسماء usr_101 وusr_102، وأي استعلام آخر غير SELECT يعيد نجاحاً وصفاً واحداً متأثراً. لا يوجد تنفيذ SQL حقيقي في هذه الدالة.

**المعالجة:** لا تعرض نجاحاً أو نتائج محاكاة على أنها بيانات حقيقية. بناء backend connector مع drivers معتمدة، حسابات DB محدودة، timeouts، parameter binding، query allowlist/AST policy، audit redaction، وعزل الشبكة.

### DB-02 — منع الاستعلامات المدمرة بفحص كلمات نصي
**التصنيف:** CONFIRMED WEAK CONTROL / P1

يقرر `isDestructive` من خلال `contains("DELETE"/"DROP"/"TRUNCATE"/"ALTER"/"UPDATE")` على نص SQL، ثم يستخدم `startsWith("SELECT")` لتحديد نوع النتيجة. هذا ليس محللاً نحوياً ولا يثبت read-only؛ قد ينتج عنه رفض كاذب لاستعلامات سليمة، ولا يوفر سياسة موثوقة متعددة العبارات أو CTE أو تعليقات أو استدعاءات دوال. كما أن `userConfirmedDestructive` قيمة Boolean من المستدعي وليست دليلاً على موافقة موثوقة مرتبطة بالاستعلام.

**المعالجة:** فرض read-only بصلاحية قاعدة البيانات نفسها، وتحليل AST وقائمة عمليات مسموحة، ومنع multi-statement، وربط الموافقة ببصمة الاستعلام ونطاقه ومدة صلاحيتها في backend.

### OPS-01 — مزامنة وهمية تعرض حالة COMPLETED
**التصنيف:** CONFIRMED FROM SOURCE / P1

`runSynchronization` يولد `syncedFilesCount` عشوائياً بين 4 و12، ويكتب حالة COMPLETED مع نص يفيد بمطابقة GitHub وGoogle Drive والتخزين الخاص، دون تنفيذ نقل أو مقارنة ملفات. لا يجوز تفسير السجل كدليل مزامنة.

### BAK-01 — إنشاء النسخة الاحتياطية لا ينسخ الملفات الفعلية
**التصنيف:** CONFIRMED FROM SOURCE / P0

`createProjectBackup` يبني JSON يضم projectId/name/description وأعداد المهام والملفات ومسار التخزين فقط، ثم يشفر هذا الملخص ويخزنه في Firestore. لا يضم محتويات المهام والملفات أو مرفقاتها، ولا يرفع ملفاً إلى Google Drive رغم تعيين `destination=GOOGLE_DRIVE` ورسالة النجاح. حجم النسخة `backupSizeBytes` عشوائي.

### BAK-02 — الاستعادة لا تستعيد المشروع
**التصنيف:** CONFIRMED FROM SOURCE / P0

`restoreBackup` يتحقق من checksum ويفك الملخص ثم يحدث حالة المستند إلى RESTORED ويرسل إشعاراً. لا يحلل المحتوى المستعاد لإعادة إنشاء المشروع أو المهام أو الملفات، ولا ينفذ restore إلى وجهة التخزين. إرجاع true لا يثبت استعادة بيانات.

### APPCHK-01 — App Check initialization يتجاهل الفشل
**التصنيف:** CONFIRMED FROM SOURCE / P1

`configureAppCheck` يلتقط أي Exception ويسجلها ثم لا يفشل تهيئة التطبيق. في نسخة release، إذا لم يتوفر site key فلن يتم تثبيت provider وفق المسار الظاهر. يجب ألا يساوي ذلك enforcement على مشروع Firebase؛ enforcement الفعلي في إعدادات المزود لم يتم فحصه.

### BAK-03 — قواعد نسخ Android احتياطياً غير مخصصة
**التصنيف:** CONFIRMED CONFIGURATION / P1

`backup_rules.xml` و`data_extraction_rules.xml` ملفات نموذجية مع TODO وتعليمات include/exclude معلقة. مع `allowBackup=true` في Manifest، لم يثبت وجود سياسة استثناء واضحة لبيانات الجلسات أو الأسرار أو بيانات المشاريع الحساسة.

## 4. خطة المعالجة

**P0:**
1. نقل orchestration وleases والموافقات وidempotency الحساسة إلى backend موثوق مع معاملات ذرية.
2. رفض أي تشغيل دون هوية موثقة ونطاق صلاحية.
3. جعل حفظ checkpoint شرطاً لازماً لاستمرار الخطوة.
4. إلغاء مظهر نجاح SQL والمزامنة والنسخ الاحتياطي والاستعادة الوهمي؛ لا تُعرض إلا نتائج عمليات منفذة فعلياً.
5. تنفيذ نسخ احتياطي كامل قابل للاستعادة مع manifest ومحتويات ومراجع ملفات وفحوص سلامة واختبار restore دوري.

**P1:**
1. بناء SQL connector حقيقي آمن أو إبقاء الميزة معطلة حتى اكتماله.
2. بناء مزامنة حقيقية مع كشف التعارضات وإعادة المحاولة وidempotency.
3. استكمال App Check enforcement والتحقق من إعدادات كل بيئة.
4. وضع قواعد Android backup صريحة حسب تصنيف البيانات.

**P2:**
1. اختبارات chaos/failover للـworker leases وheartbeat وcheckpoint.
2. اختبارات end-to-end لعمليات backup/restore على بيئة staging.
3. ربط تقارير التنفيذ بمعرفات artifacts وسجلات تدقيق غير قابلة للتعديل.

## 5. ما لم ينفذ

لم تُشغل مهام دائمة، أو استعلامات SQL، أو مزامنة، أو نسخ/استعادة، أو App Check ضد مشروع حي. لم تُنفذ اختبارات أو builds أو deploy. لم تتغير أي ملفات كود أو قواعد أو schema أو قاعدة بيانات. هذه مراجعة توثيقية ولا تمنح إذناً ببدء البرمجة.
