package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.model.DesignTemplate
import com.example.data.model.SoftwareDesignWizardState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class SoftwareDesignWizardRepository(private val context: Context) {
    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.w("SoftwareDesignWizardRepo", "Firestore not available, using local cache", e)
            null
        }
    }

    private val _wizardState = MutableStateFlow(SoftwareDesignWizardState())
    val wizardState: StateFlow<SoftwareDesignWizardState> = _wizardState.asStateFlow()

    private val _templates = MutableStateFlow(
        listOf(
            DesignTemplate(
                id = "hospital",
                nameAr = "نظام إدارة مستشفيات",
                category = "أنظمة مؤسسية",
                description = "إدارة المرضى، الأطباء، المواعيد، السجلات الطبية، والصيدلية.",
                defaultTypes = listOf("تطبيق ويب تفاعلي", "نظام إدارة مؤسسات"),
                defaultTech = listOf("React", "Node.js", "PostgreSQL"),
                defaultScreens = listOf("تسجيل الدخول", "لوحة التحكم", "إدارة المرضى", "الأطباء والمواعيد", "الصيدلية", "التقارير", "الإعدادات"),
                defaultFeatures = listOf("تسجيل الدخول بالبريد", "إدارة المستخدمين والأدوار", "سجل العمليات والتدقيق", "التشفير الشامل")
            ),
            DesignTemplate(
                id = "school",
                nameAr = "نظام إدارة مدارس وجامعات",
                category = "تعليمي",
                description = "إدارة الطلاب، المعلمين، الحضور والغياب، الامتحانات، والرسوم الدراسية.",
                defaultTypes = listOf("تطبيق ويب تفاعلي", "تطبيق Android وiOS معاً"),
                defaultTech = listOf("Flutter وDart", "Supabase"),
                defaultScreens = listOf("تسجيل الدخول", "لوحة التحكم", "شؤون الطلاب", "المعلمون", "الامتحانات والنتائج", "الرسوم", "الإعدادات"),
                defaultFeatures = listOf("تسجيل الدخول بواسطة Google", "إدارة المستخدمين والأدوار", "التنبيهات الفورية", "تصدير واستيراد البيانات")
            ),
            DesignTemplate(
                id = "ecommerce",
                nameAr = "متجر إلكتروني متكامل",
                category = "تجاري",
                description = "عرض المنتجات، سلة المشتريات، الدفع الإلكتروني، وتتبع الطلبات.",
                defaultTypes = listOf("موقع إلكتروني", "تطبيق Android وiOS معاً"),
                defaultTech = listOf("Next.js", "Node.js", "MongoDB"),
                defaultScreens = listOf("تسجيل الدخول", "الصفحة الرئيسية", "المنتجات", "سلة المشتريات", "إتمام الطلب", "حسابي", "لوحة التحكم"),
                defaultFeatures = listOf("تسجيل الدخول بالبريد", "تسجيل الدخول بواسطة Google", "البحث المتقدم", "التكامل مع Telegram")
            ),
            DesignTemplate(
                id = "ai_platform",
                nameAr = "منصة ذكاء اصطناعي متقدمة",
                category = "ذكاء اصطناعي",
                description = "محادثة مع النماذج، تحليل الصور، توليد النصوص، وإدارة مفاتيح API.",
                defaultTypes = listOf("منصة ذكاء اصطناعي", "تطبيق ويب تفاعلي"),
                defaultTech = listOf("Python", "React", "FastAPI", "Redis"),
                defaultScreens = listOf("تسجيل الدخول", "مساعد الذكاء الاصطناعي", "تحليل الملفات", "إدارة النماذج", "سجل المحادثات", "الإعدادات"),
                defaultFeatures = listOf("الذكاء الاصطناعي المدمج", "سجل العمليات والتدقيق", "التشفير الشامل", "المصادقة الثنائية")
            )
        )
    )
    val templates: StateFlow<List<DesignTemplate>> = _templates.asStateFlow()

    fun updateState(updater: (SoftwareDesignWizardState) -> SoftwareDesignWizardState) {
        val newState = updater(_wizardState.value).copy(updatedAt = System.currentTimeMillis())
        _wizardState.value = newState
    }

    fun applyTemplate(template: DesignTemplate) {
        _wizardState.value = _wizardState.value.copy(
            projectName = template.nameAr,
            projectDescription = template.description,
            selectedProjectTypes = template.defaultTypes,
            selectedTechStack = template.defaultTech,
            selectedScreens = template.defaultScreens,
            coreFeatures = template.defaultFeatures,
            selectedTemplateName = template.nameAr,
            updatedAt = System.currentTimeMillis()
        )
    }

    fun generatePrd(): String {
        val state = _wizardState.value
        return buildString {
            appendLine("# وثيقة متطلبات المشروع PRD - ${state.projectName.ifEmpty { "مشروع جديد" }}")
            appendLine("تاريخ الإنشاء: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())}")
            appendLine("تم التطوير عبر: منظومة METRA AI CONTROL (الخبير إبراهيم زينب)")
            appendLine()
            appendLine("## 1. نظرة عامة")
            appendLine("- **اسم المشروع:** ${state.projectName}")
            appendLine("- **الوصف:** ${state.projectDescription}")
            appendLine("- **أنواع المنصات:** ${state.selectedProjectTypes.joinToString(", ")}")
            appendLine("- **التقنيات المختارة:** ${if (state.chooseBestForMe) "اختارها الذكاء الاصطناعي" else state.selectedTechStack.joinToString(", ")}")
            appendLine()
            appendLine("## 2. الهدف والجمهور المستهدف")
            appendLine("- **الهدف الرئيسي:** ${state.goal.ifEmpty { "تقديم حل تقني متكامل وعالي الأداء." }}")
            appendLine("- **المستهدفون:** ${state.targetUsers.ifEmpty { "المستخدمون والمسؤولون في النظام." }}")
            appendLine()
            appendLine("## 3. الشاشات والصفحات")
            state.selectedScreens.forEach { screen ->
                appendLine("- $screen")
            }
            appendLine()
            appendLine("## 4. الوظائف والمميزات المتقدمة")
            appendLine("- مصادقة وأمان: ${if (state.needsAuth) "مطلوبة" else "غير مطلوبة"}")
            appendLine("- قاعدة بيانات: ${if (state.needsDatabase) "مطلوبة" else "غير مطلوبة"}")
            appendLine("- ذكاء اصطناعي: ${if (state.needsAi) "مدمج" else "عادي"}")
            appendLine("- نمط التصميم: ${state.designStyle} (الألوان: ${state.colorPalette})")
            appendLine()
            appendLine("## 5. خطة التنفيذ")
            appendLine("- **أسلوب التنفيذ:** ${state.implementationStyle}")
            appendLine("- **مستوى الأولوية:** ${state.priorityLevel}")
        }
    }

    suspend fun saveToCloudOrLocal(): Boolean {
        return try {
            val db = firestore
            if (db != null) {
                val state = _wizardState.value
                db.collection("software_design_wizards")
                    .document(state.wizardId)
                    .set(state)
                    .await()
            }
            true
        } catch (e: Exception) {
            Log.e("SoftwareDesignWizardRepo", "Failed to save wizard state", e)
            false
        }
    }
}
