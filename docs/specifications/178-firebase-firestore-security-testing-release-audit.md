# 178 — تدقيق Firebase وFirestore والأمن والاختبارات ومسار النشر

**الحالة:** STATIC AUDIT COMPLETE / RULES NOT EMULATOR-TESTED / BUILDS NOT RUN / DEPLOYMENT NOT VERIFIED  
**المستودع:** `meteo2026net-oss/ibrahem-zynab-dev`  
**الفرع:** `main`  
**Commit baseline المفحوص:** `72737f167c9c1a17250112551fef62d656edc977`  
**النطاق:** قراءة ساكنة فقط؛ لا اتصال بمشروع Firebase حي، ولا نشر، ولا تغيير لقواعد الأمان أو كود التطبيق أو API أو schema أو قاعدة البيانات.

## 1. الخلاصة التنفيذية

توجد قواعد Firestore فعلية في `firestore.rules`، لكنها تعتمد نمط ملكية مبسطاً على حقل `userId` وتسمح لصاحب السجل بتعديل وحذف عدد من السجلات الحساسة، بما فيها سجلات التدقيق وطلبات الموافقة ومراجع الأسرار وبيانات الاعتماد. توجد ثغرة تصميمية مؤكدة في قواعد التحديث: شرط التحديث يتحقق من مالك السجل الحالي `resource.data.userId` ولا يفرض بقاء `request.resource.data.userId` مساوياً له؛ يمكن لمالك السجل نقل حقل الملكية إلى UID آخر. لم يُختبر ذلك بمحاكي Firestore، لذا هو استنتاج مباشر من نص القاعدة وليس نتيجة استغلال منفذ.

لا يوجد في جذر المستودع `firestore.indexes.json`، ولا إعداد Emulator أو اختبارات قواعد Firestore ظاهرة في الشجرة. ملف `firebase.json` يربط القواعد ومعرّف قاعدة بيانات فقط. ملف Firebase blueprint يصف حقولاً كثيرة، لكنه ليس تحققاً من مخطط أو قيود أمنية.

اختبارات Android موجودة (35 اختباراً في `MetraCoreUnitTest.kt` إضافة إلى اختبار الوحدة النموذجي واختبار Instrumentation النموذجي)، لكن الاختبارات التي تمت مراجعتها اختبارات Kotlin محلية على نماذج/دوال، ولا تثبت قواعد Firestore أو المصادقة أو عزل المستأجرين أو تكامل الخدمات. لم تظهر ملفات Angular `.spec.ts` أو `.test.*` في شجرة المستودع المفحوصة (564 مدخلاً، شجرة غير مبتورة). يوجد workflow واحد لاستخراج المواصفات فقط؛ لا يوجد workflow بناء/اختبار/نشر للتطبيق ضمن ملفات workflows الحالية.

**الحكم:** غير جاهز للنشر الإنتاجي. لا ينبغي توصيل مستخدمين أو بيانات حقيقية بهذه القواعد أو اعتبار الواجهات الحالية نظام صلاحيات إنتاجياً قبل إغلاق نتائج P0 أدناه وإثباتها باختبارات Emulator/CI.

## 2. أدلة الملفات وبصماتها

| الملف | SHA للـblob | الملاحظة |
|---|---|---|
| `firestore.rules` | `c2f0da4870be67dacd9e7b86f60bf644f9d9e924` | قواعد 21 مجموعة تقريباً بنمط متكرر قائم على userId |
| `firebase.json` | `172c681d3d38615d25a88d2ac21bd5f13fb0d398` | يحدد ملف القواعد ومعرّف قاعدة البيانات فقط |
| `firebase-blueprint.json` | `1aa2a5a35b38ce65d40c3db810c5239aeb6750a2` | تعريف وصفي للحقول والمجموعات |
| `app/build.gradle.kts` | `f3ec3743e119e29b72bed9e0722396c86751a9c0` | Firebase Auth/Firestore/AI وApp Check dependencies |
| `app/src/main/AndroidManifest.xml` | `5d97f744eff23ad345a2af523a177caa8e9bb9d2` | `allowBackup=true` |
| `app/src/main/res/values/firebase_applet_config.xml` | `adcca4d61a61600576757ca09457c91262b54d71` | معرف Firestore ومفتاح reCAPTCHA لموقع الويب |
| `web-angular/src/app/core/auth.service.ts` | `f501a09a10914397ba68fd01e79e7029a17c14d0` | دخول محلي تجريبي لا يتحقق من كلمة المرور |
| `web-angular/src/app/core/project.service.ts` | `c4318a1f93b599b8760da3aefbf2cb4dac921bb5` | بيانات تجريبية وحفظ localStorage ومحاكاة build/tests |
| `web-angular/src/app/core/knowledge.service.ts` | `c701a45c70671fa3bc5870ec0f785a65b75d80df` | تخزين وثائق المعرفة محلياً |
| `web-angular/package.json` | `48fe9e7ad3ca67b7eacbcc54cd941f3adb057638` | scripts build/test موجودة |
| `.github/workflows/extract-master-specification.yml` | `49be89a7a19bca5eac3ffdf8de0ddfaa7d034fb1` | استخراج DOCX فقط |

لم تُدرج أي قيمة اعتماد سرية في هذا التقرير. معرّفات Firebase ومفاتيح reCAPTCHA العامة ليست بحد ذاتها أسرار خادم؛ يجب مع ذلك تقييدها بنطاقاتها وإعدادات المزود المناسبة.

## 3. نتائج قواعد Firestore

### SEC-FS-01 — تغيير حقل الملكية عند التحديث
**التصنيف:** CONFIRMED FROM RULE TEXT / NOT EMULATOR-TESTED / P0

قواعد `update, delete` في المجموعات المتكررة تتحقق من:
`request.auth.uid == resource.data.userId`
ولا تفرض:
`request.resource.data.userId == resource.data.userId`
في عملية التحديث. لذلك يستطيع مالك السجل الحالي تعديل `userId` إلى UID آخر؛ بعد النقل ستتغير نتيجة قراءات المالكين وفق حقل الملكية. يجب أن يكون حقل المالك ثابتاً أو أن تتم عملية النقل عبر إجراء خادمي صريح ومصرح ومدقق.

### SEC-FS-02 — العميل يستطيع تغيير سجلات التدقيق
**التصنيف:** CONFIRMED FROM RULE TEXT / P0

مجموعة `audit_logs` تسمح للمالك بالإنشاء والتحديث والحذف. هذا لا يحقق سجل تدقيق غير قابل للعبث. يجب أن تكون كتابة أحداث التدقيق من خدمة موثوقة فقط، مع منع تعديل/حذف السجلات من العميل وسياسة احتفاظ/تصدير واضحة.

### SEC-FS-03 — العميل يستطيع حسم طلبات الموافقة
**التصنيف:** CONFIRMED FROM RULE TEXT / P0

مجموعة `approval_requests` تسمح للمالك نفسه بالتحديث والحذف. القاعدة لا تميز بين مقدم الطلب وصاحب قرار الموافقة، ولا تقيد الانتقالات المسموح بها أو هوية `approvedBy` أو حالة القرار. هذا يتيح تزوير حالة الموافقة من منظور قواعد البيانات الحالية. يلزم مسار قرار خادمي، وفصل مقدم الطلب عن المعتمد، والتحقق من الدور والنطاق والانتقال وسجل التدقيق.

### SEC-FS-04 — بيانات الأسرار والاعتمادات قابلة للقراءة والكتابة من العميل
**التصنيف:** CONFIRMED FROM RULE TEXT / P0

المجموعات `project_secrets_vault` و`api_credentials` و`github_accounts` و`telegram_bots` و`database_connections` تستخدم نمط الملكية نفسه وتسمح للمالك بالقراءة والكتابة والتحديث والحذف. أسماء الحقول في blueprint مثل `encryptedSecretValue` و`encryptedToken` لا تثبت تشفيراً آمناً أو إدارة مفاتيح. يجب ألا يكون مخزن أسرار إنتاجي قابلاً للقراءة المباشرة من تطبيق العميل؛ تُخزن الأسرار في خدمة/خزنة موثوقة مع مفاتيح خارج Firestore وتُعرض مراجع محدودة الصلاحية فقط.

### SEC-FS-05 — لا يوجد تحقق كافٍ من بنية السجلات والحقول
**التصنيف:** CONFIRMED FROM RULE TEXT / P1

القواعد لا تتحقق من allowlist للحقول أو أنواعها أو حدود الأطوال/القيم أو الحقول الثابتة أو علاقة `projectId` بالمستخدم. إنشاء سجل يقتصر أساساً على مطابقة `userId` مع UID؛ لا توجد قيود تمنع حقولاً إضافية أو تغييرات حساسة في حقول الحالة والقرار.

### SEC-FS-06 — عزل مستخدم لا يساوي عزل مؤسسة/مساحة عمل
**التصنيف:** CONFIRMED GAP AGAINST MULTI-TENANT TARGET / P1

النمط الحالي مبني على `userId` ولا يفرض `organizationId` أو `workspaceId` أو عضوية المستخدم أو دوره أو صلاحياته على المشروع. هذا لا يحقق نموذج تعدد المؤسسات/المستأجرين والعضويات المعتمد في نطاق المنتج. يلزم تعريف مصدر حقيقة العضوية والأدوار وربط كل قراءة/كتابة بنطاق المؤسسة/المشروع.

### SEC-FS-07 — App Check غير مثبت كحاجز مفعل
**التصنيف:** NOT VERIFIED / P1

اعتماديات Android تتضمن مكتبات App Check لـreCAPTCHA وdebug، لكن مجرد وجود الاعتماديات ومفتاح موقع لا يثبت تهيئة App Check في التطبيق أو فرضه على Firestore/الخدمات. لا توجد أدلة إعداد أو اختبارات enforcement في الملفات التي تمت مراجعتها. يجب التحقق من تهيئة كل منصة وفرض App Check بعد اختبار بيئة staging.

### SEC-FS-08 — غياب ملفات إعداد الفهارس والمحاكي
**التصنيف:** CONFIRMED REPOSITORY GAP / P1

لم يوجد `firestore.indexes.json` في جذر المستودع، ولم يظهر إعداد Firestore Emulator أو قواعد اختبار ضمن الشجرة. قد توجد فهارس يدوية في مشروع Firebase خارج Git؛ لم يتم الاتصال بالمشروع للتحقق. يجب حفظ الفهارس وإعداد Emulator في المستودع وإضافة اختبارات سماح/رفض آلية.

### SEC-FS-09 — النسخ الاحتياطي المحلي على Android
**التصنيف:** CONFIRMED CONFIGURATION / NEEDS DATA CLASSIFICATION / P1

Android Manifest يضبط `android:allowBackup="true"`. لا يثبت هذا وحده أن أسراراً تُنسخ فعلاً، لكن قبل الإنتاج يجب مراجعة `data_extraction_rules` و`backup_rules` وتحديد استثناء بيانات الجلسات والرموز والأسرار وبيانات العمل الحساسة، ثم اختبار سلوك النقل/الاستعادة على إصدارات Android المستهدفة.

### SEC-FS-10 — إعداد Google Services متساهل مع غياب ملف الإعداد
**التصنيف:** CONFIRMED BUILD CONFIGURATION / RELEASE BLOCKER

`googleServices.missingGoogleServicesStrategy = WARN` يسمح باستمرار Gradle عند غياب إعداد Google Services. لا يظهر `google-services.json` في شجرة الملفات المفحوصة. يجب تحديد إعدادات Firebase الرسمية لكل variant/بيئة، وجعل غيابها خطأً في build الإنتاج، مع عدم وضع مفاتيح خادم في ملفات العميل.

## 4. المصادقة والتخزين في Angular

- `AuthService.login(email, _password)` لا يستخدم كلمة المرور؛ يقبل أي بريد غير فارغ، ويخزن هوية قابلة للتعديل في `localStorage` ويمنح الدور `admin`. هذا دخول تجريبي وليس Firebase Auth ولا مصادقة إنتاجية.
- `authGuard` يتحقق من وجود قيمة محلية فقط؛ لا يشكل حاجز تفويض خادمي.
- `ProjectService` يحفظ حالة المشاريع والاختبارات والبناء في `localStorage`، ويحتوي بيانات تجريبية؛ `runAllPendingTests` يختار نتيجة عشوائية، و`simulateBuild` يكتب نجاحاً تجريبياً بعد مؤقتات.
- `KnowledgeService` يحفظ وثائق المعرفة في `localStorage`.
- README الخاص بـAngular يصف الدخول التجريبي بأنه أي بريد وكلمة مرور، ويصرح بأن الواجهة ومنطقها محاكاة وأن توليد الكود الحقيقي وربط الخوادم مراحل لاحقة.

**النتيجة:** لا يجوز اعتبار أي role أو approval أو build/test state من واجهة Angular الحالية دليلاً أمنياً أو تشغيلياً.

## 5. الاختبارات وCI

| النطاق | ما وجد في الشجرة | ما لا يثبته |
|---|---|---|
| Angular | scripts `build` و`test`، Jasmine/Karma dependencies؛ لا ملفات `.spec.ts` أو `.test.*` ظاهرة في الشجرة | لا توجد اختبارات Angular فعلية مثبتة في المستودع المفحوص |
| Android unit | 35 دالة `@Test` في `MetraCoreUnitTest.kt` إضافة إلى اختبار نموذجي في `ExampleUnitTest.kt` | اختبارات منطق محلي ونماذج؛ لا تثبت اتصال Firebase أو قواعد Firestore |
| Android instrumentation | اختبار `useAppContext` نموذجي واحد | لا يختبر رحلة المنتج أو المصادقة أو قواعد البيانات |
| Firestore rules | لا ملفات Emulator/rules tests ظاهرة | لا يوجد دليل على اختبار allow/deny للقواعد |
| GitHub Actions | workflow واحد لاستخراج المواصفات | لا يوجد workflow بناء/اختبار/نشر Angular أو Android في المسارات المفحوصة |
| CI للـbaseline | لم تُنفذ أو تُسترجع نتائج build/test في هذا التدقيق | لا يمكن إعلان نجاح أو فشل أي build/test |

بعض اختبارات Kotlin تفحص كائنات مصطنعة أو منطقاً محلياً؛ على سبيل المثال اختبار العزل ينشئ قائمتين في الذاكرة ثم يرشحهما، واختبار read-only يفحص كلمات SQL عبر `contains`. هذه اختبارات مساعدة وليست إثباتاً لعزل فعلي أو منع SQL ضار في محرك قاعدة بيانات.

## 6. إعدادات الإصدار

- Android release signing يعتمد متغيرات بيئة `KEYSTORE_PATH` و`STORE_PASSWORD` و`KEY_PASSWORD`، مع اسم alias ثابت. لم يتم توفير/فحص أسرار التوقيع ولم ينفذ build.
- إعداد release الحالي يترك `isMinifyEnabled = false`؛ يلزم قرار سياسة التصغير/الحماية واختبارات الانعكاس قبل تفعيلها أو الإبقاء عليها.
- `firebase.json` لا يحدد Storage rules أو Functions أو Emulator أو indexes.
- `netlify.toml` وملف Angular موجودان، لكن لم ينفذ build أو deploy أو smoke test.
- لم يتم فحص مشروع Firebase الحي أو إعدادات App Check أو IAM أو billing أو domains؛ هذه العناصر **NOT ASSESSED** وليست مفترضة الإعداد.

## 7. خطة المعالجة ذات الأولوية

### P0 — قبل أي بيانات حقيقية أو نشر عام
1. تصميم قواعد Firestore جديدة على أساس أقل صلاحية، مع منع تغيير المالك من العميل والتحقق من العضوية والنطاق.
2. منع العميل من تعديل/حذف سجلات التدقيق وقرارات الموافقة؛ نقل الكتابة والحسم إلى خدمات موثوقة.
3. إخراج الأسرار والرموز من مسارات القراءة المباشرة للعميل، واعتماد secret manager/key management ومسارات إصدار مراجع محدودة.
4. تنفيذ مصادقة حقيقية وربط التفويض بالخادم؛ إزالة دور admin الافتراضي والدخول المحلي من مسار الإنتاج.
5. بناء اختبارات Firestore Emulator سلبية وإيجابية للحالات: مستخدم آخر، مؤسسة أخرى، تغيير userId، تعديل approval، تزوير audit، قراءة secret، حقول غير مسموحة.

### P1 — قبل اعتماد جاهزية staging
1. إضافة firestore indexes وEmulator config واختبارات rules إلى Git.
2. تحديد tenant/workspace membership schema وSoR والحقول القابلة للتعديل.
3. تهيئة App Check وفرضه تدريجياً بعد اختبارات staging.
4. تدقيق قواعد Storage إن وجدت/ستضاف، وسياسات رفع الملفات وفحصها.
5. مراجعة Android backup rules وملفات إعداد Firebase لكل variant.
6. إضافة اختبارات Angular حقيقية واختبارات تكامل/E2E، واختبارات Android ذات سلوك واقعي.
7. إضافة CI workflows للبناء والاختبار والتحليل الثابت وفحص الاعتماديات وحفظ artifacts.

### P2 — قبل production release
1. تنفيذ build نظيف لـAngular وAndroid، وتوقيع AAB/APK من أسرار خارج المستودع.
2. نشر staging، تنفيذ smoke/acceptance/security tests، وحفظ تقاريرها.
3. اعتماد مراقبة وتنبيهات ونسخ احتياطي واستعادة مجربة وRTO/RPO معتمدة.
4. ربط artifact بالـcommit ونسخة الاعتماديات وSBOM وموافقة الإصدار.
5. موافقة production مستقلة بعد نجاح جميع بوابات القبول.

## 8. ما تم وما لم يتم في هذه الجولة

| البند | الحالة |
|---|---|
| فحص HEAD وملفات Firebase/Firestore | مكتمل قراءة ساكنة |
| فحص قواعد المجموعات الحساسة | مكتمل قراءة ساكنة |
| فحص شجرة الاختبارات وworkflow | مكتمل |
| اختبار قواعد Firestore Emulator | لم ينفذ |
| تشغيل npm/Gradle build أو tests | لم ينفذ |
| فحص Firebase production project أو نشر | لم ينفذ |
| تعديل قواعد/كود/API/schema/database | لم يحدث |
| إصدار artifact قابل للنشر | غير متوفر/غير متحقق |

## 9. الحكم النهائي

النتائج SEC-FS-01 إلى SEC-FS-06 تمنع اعتبار قواعد Firestore الحالية نظام صلاحيات إنتاجياً؛ وأخطرها قابلية تغيير حقل الملكية، وإمكانية تعديل سجلات التدقيق والموافقات، وقراءة/كتابة بيانات الأسرار من العميل. لا ينبغي إصلاحها بتغيير عشوائي قبل تثبيت نموذج الملكية والعضوية والموافقات المعتمد، لكن يجب أن تكون جميعها شروط P0 قبل استخدام بيانات حقيقية.

هذا workpaper توثيقي فقط. لا يعد موافقة على تغيير قواعد الأمان أو بدء البرمجة. تبقى موافقة بدء البرمجة منفصلة وصريحة بعد إغلاق اعتماد المواصفات والقرارات المطلوبة.
