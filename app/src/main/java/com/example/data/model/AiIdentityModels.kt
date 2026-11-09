package com.example.data.model

data class AiIdentityConfig(
    val developerName: String = "الخبير إبراهيم زينب",
    val platformName: String = "METRA AI CONTROL",
    val officialIdentityStatement: String = "أنا نظام ذكاء اصطناعي، قام ببرمجتي وتطويري ضمن منظومة METRA AI CONTROL الخبير إبراهيم زينب، وأنا أعمل لمساعدتك في التحليل والبرمجة وإدارة المشاريع وتنفيذ المهام.",
    val shortIdentityStatement: String = "أنا مساعد ذكاء اصطناعي من تطوير وبرمجة الخبير إبراهيم زينب.",
    val voiceGreetingText: String = "مرحباً بك، أنا مساعد الذكاء الاصطناعي ضمن منظومة METRA AI CONTROL، قام ببرمجتي وتطويري الخبير إبراهيم زينب. كيف يمكنني مساعدتك اليوم؟",
    val enableVoiceGreeting: Boolean = true,
    val languageAndAccent: String = "العربية الفصحى (Modern Standard Arabic)",
    val conversationalTone: String = "مهني، تقني، ومحترف",
    val repeatIntroductionEveryMessage: Boolean = false,
    val showIdentityInReports: Boolean = true
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "developerName" to developerName,
        "platformName" to platformName,
        "officialIdentityStatement" to officialIdentityStatement,
        "shortIdentityStatement" to shortIdentityStatement,
        "voiceGreetingText" to voiceGreetingText,
        "enableVoiceGreeting" to enableVoiceGreeting,
        "languageAndAccent" to languageAndAccent,
        "conversationalTone" to conversationalTone,
        "repeatIntroductionEveryMessage" to repeatIntroductionEveryMessage,
        "showIdentityInReports" to showIdentityInReports
    )
}
