# مصمم التطبيقات الذكي — واجهة Angular

واجهة الويب الأساسية للمشروع. إصدار Angular الحالي في فرع التطوير هو Angular 22.

## المتطلبات

- Node.js 24.15.0
- npm المتوافق مع إصدار Node
- مشروع Firebase فعلي عند اختبار تسجيل الدخول

## التشغيل المحلي

```bash
git clone https://github.com/meteo2026net-oss/ibrahem-zynab-dev.git
cd ibrahem-zynab-dev/web-angular
npm ci
npm start
```

يفتح التطبيق على `http://localhost:4200`.

## إعداد Firebase Authentication

قبل اختبار التسجيل أو الدخول:

1. أنشئ/اختر مشروع Firebase.
2. فعّل Email/Password في Authentication → Sign-in method.
3. انسخ Web API Key من إعدادات تطبيق الويب.
4. ضع المفتاح في `public/runtime-config.js` في قيمة `firebaseApiKey`.
5. قيّد مفتاح API بنطاقات HTTP المسموح بها من Google Cloud Console.

مثال إعداد عام (المفتاح ليس كلمة مرور أو مفتاحاً سرياً):

```js
window.__APP_CONFIG__ = {
  firebaseApiKey: 'YOUR_FIREBASE_WEB_API_KEY'
};
```

لا تضع service-account keys أو مفاتيح KMS أو رموز وصول خادمية في ملفات الواجهة أو المستودع.

## الاختبارات والبناء

```bash
npm test
npm run build -- --configuration production
```

## حدود التكامل الحالية — اقرأ قبل النشر

- تسجيل الدخول والتسجيل يستخدمان Firebase Authentication REST، ويحتفظ التطبيق برموز الجلسة في الذاكرة فقط. لا توجد جلسة دائمة بعد إعادة تحميل الصفحة.
- بيانات مساحة العمل الحالية تحفظ محلياً في المتصفح، مع فصل مساحة التخزين حسب Firebase UID. هذا ليس بديلاً عن قاعدة بيانات سحابية ولا يزامن المشاريع بين الأجهزة.
- خدمات Firebase Functions للمهام الدائمة موجودة في مجلد `functions`، لكن واجهة Angular لم تُربط بعد بتدفق إنشاء/متابعة/إلغاء/استرجاع نتائج المهام.
- دوال المهام الحساسة تفرض Firebase App Check، وإعداد App Check وربط رموزه في الواجهة غير مكتمل.
- التحليل، تشغيل الاختبارات، تنسيق الوكلاء، إنشاء APK/AAB، ونشر الإصدارات لا تُنفذ حالياً كعمليات إنتاجية من هذه الواجهة. لا تعرض أي نتيجة تجريبية على أنها بناء أو نشر حقيقي.
- لا توجد حزمة Android موقعة بمفتاح إصدار إنتاجي ضمن هذا الدليل.

## النشر

يمكن بناء ملفات الويب الإنتاجية بواسطة أمر البناء أعلاه. لا يعني نجاح البناء وحده أن التطبيق متصل بخدمات Firebase أو أنه منشور. راجع إعدادات Netlify في جذر المستودع، واضبط Firebase وApp Check وخدمات الخلفية واختبارات التكامل قبل فتح التطبيق للمستخدمين.

## Firebase project persistence API (backend available; web client pending)

The Functions deployment now exports three authenticated, App-Check-enforced callable functions:

- `createProject({ name, description, platforms })` → `{ projectId }`
- `listProjects({})` → `{ projects }` (maximum 100 projects for the signed-in owner)
- `updateProject({ projectId, name?, description?, platforms? })` → `{ projectId, updated: true }`

Supported platform values are `Web`, `Android`, `iOS`, `Desktop`, and `Backend`. Project ownership is assigned from the verified Firebase Authentication UID, never from a client-supplied owner field. Creation and updates append audit records. The callable API is not yet consumed by Angular: local workspace storage remains the active UI persistence path until the client has a correctly configured Firebase App Check provider and the project/job repository adapter is implemented and tested.
