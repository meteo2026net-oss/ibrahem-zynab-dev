# مصمم التطبيقات الذكي — واجهة Angular

تطبيق Angular 19 للواجهات الرئيسية للمنصة (14 مركزاً).

## التشغيل

```bash
cd web-angular   # أو المجلد الذي وُضع فيه المشروع داخل المستودع
npm install
npm start
```

ثم افتح: http://localhost:4200

## الهيكل

```
src/app/
  layout/          # الهيكل الجانبي + الشريط العلوي
  features/
    projects/      # مركز المشاريع
    analysis/      # مركز التحليل
    design/        # مركز التصميم
    functions/     # مركز الوظائف
    data/          # مركز البيانات
    agents/        # مركز الوكلاء
    build/         # مركز البناء
    test/          # مركز الاختبار
    release/       # مركز الإطلاق
    monitor/       # مركز المراقبة
    security/      # مركز الأمان
    settings/      # مركز الإعدادات
    collab/        # مركز التعاون
    reports/       # مركز التقارير
```

## ملاحظات

- اللغة العربية وRTL مفعّلان من `index.html` و`styles.scss`.
- السيناريو المرجعي: مشروع «متجري».
- المواصفات: `docs/specifications/00_MASTER_SPECIFICATION.md`.
- هذه واجهات أولية (MVP UI) قابلة للتوسع حسب خطة الـ 12 مرحلة.

## الإصدار

0.1.0 — نوفمبر 2026
