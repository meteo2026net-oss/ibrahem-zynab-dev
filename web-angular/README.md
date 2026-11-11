# مصمم التطبيقات الذكي — Angular MVP

تطبيق Angular 19 جاهز للتشغيل محلياً وللنشر على Netlify.

## التشغيل

```bash
npm install
npm start
```

افتح http://localhost:4200

## البناء

```bash
npm run build
```

المخرجات: `dist/intelligent-app-designer/browser`

## الميزات الجاهزة

- 14 مركزاً + الصفحة الرئيسية
- حالة مشتركة (ProjectService + Signals)
- حفظ تلقائي في localStorage
- سيناريو «متجري» تفاعلي:
  - مشاريع (إنشاء / اختيار)
  - تحليل واعتماد متطلبات
  - تصميم متعدد الصفحات
  - وظائف (مرئي + لغة طبيعية)
  - بيانات ووكلاء
  - بناء محاكى + اختبار + إطلاق
  - مراقبة، أمان، إعدادات، تعاون، تقارير

## Netlify

الإعداد في `../netlify.toml` و `netlify.toml`.

## الإصدار

0.1.0
