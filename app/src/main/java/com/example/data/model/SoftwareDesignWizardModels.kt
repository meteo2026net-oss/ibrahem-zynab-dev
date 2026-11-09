package com.example.data.model

data class SoftwareDesignWizardState(
    val wizardId: String = java.util.UUID.randomUUID().toString(),
    val projectName: String = "",
    val projectDescription: String = "",
    // Stage 1: Project Types
    val selectedProjectTypes: List<String> = emptyList(), // Android, iOS, Flutter, Windows, macOS, Web, Enterprise, Dashboard, AI, DB, Game, Custom
    // Stage 2: Tech Stack
    val selectedTechStack: List<String> = emptyList(), // HTML/CSS/JS, TypeScript, React, Next.js, Vue, Angular, Node.js, Flutter/Dart, Kotlin, Java, Swift, React Native, C#/.NET, Python, C++, Rust, Go, PHP, Ruby, PostgreSQL, Supabase, MySQL, SQL Server, SQLite, MongoDB, Redis
    val chooseBestForMe: Boolean = false,
    // Stage 3: Nature & Functions
    val goal: String = "",
    val targetUsers: String = "",
    val coreFeatures: List<String> = emptyList(),
    val needsAuth: Boolean = true,
    val needsRoles: Boolean = false,
    val needsDatabase: Boolean = true,
    val needsDashboard: Boolean = true,
    val needsReports: Boolean = true,
    val needsNotifications: Boolean = true,
    val needsOffline: Boolean = false,
    val needsSync: Boolean = true,
    val needsAi: Boolean = true,
    val needsApi: Boolean = true,
    val needsFileUpload: Boolean = true,
    val needsPdfExcelExport: Boolean = true,
    val needsMultiLanguage: Boolean = true,
    val customNatureText: String = "",
    // Stage 4: UI & Color Designer
    val designStyle: String = "عصري Minimal", // Professional, Minimal, Premium, Futuristic, Medical, Gov, Commercial, Industrial, Educational, Creative
    val colorPalette: String = "أزرق وأبيض", // Blue/White, Gold/Black, Green/White, Navy/Gray, Purple, Custom
    val primaryColorHex: String = "#1E88E5",
    val secondaryColorHex: String = "#26A69A",
    val backgroundColorHex: String = "#0F172A",
    val colorMode: String = "Dark Mode", // Light Mode, Dark Mode, Auto Mode
    val uiComponents: List<String> = listOf("قائمة جانبية", "شريط علوي", "بطاقات إحصائية", "جداول متقدمة", "رسوم بيانية"),
    // Stage 5: Screens & Pages
    val selectedScreens: List<String> = listOf(
        "تسجيل الدخول",
        "الصفحة الرئيسية",
        "لوحة التحكم",
        "إدارة المستخدمين",
        "التقارير",
        "الإعدادات",
        "الملف الشخصي"
    ),
    // Stage 6: Advanced Functions
    val advancedFunctions: List<String> = listOf(
        "تسجيل الدخول بالبريد",
        "تسجيل الدخول بواسطة Google",
        "المصادقة الثنائية",
        "بصمة الإصبع أو الوجه",
        "سجل العمليات والتدقيق",
        "النسخ الاحتياطي التلقائي",
        "التشفير الشامل",
        "الذكاء الاصطناعي المدمج",
        "التكامل مع Telegram",
        "التكامل مع Google Drive"
    ),
    // Stage 7: Implementation Style
    val implementationStyle: String = "بناء المشروع بالكامل من الصفر", // From scratch, MVP, upgrade existing, redesign, convert design to app, runnable demo
    val priorityLevel: String = "عالية",
    val executionSpeed: String = "متوسطة",
    // Stage 8 & 9: PRD & Approval
    val prdGenerated: Boolean = false,
    val isApproved: Boolean = false,
    val selectedTemplateName: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

data class DesignTemplate(
    val id: String,
    val nameAr: String,
    val category: String,
    val description: String,
    val defaultTypes: List<String>,
    val defaultTech: List<String>,
    val defaultScreens: List<String>,
    val defaultFeatures: List<String>
)
