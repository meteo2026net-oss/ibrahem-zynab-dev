package com.example.data.model

import java.util.UUID

enum class AiAuthMethod(val titleAr: String, val titleEn: String) {
    OAUTH2_PKCE("OAuth 2.0 + PKCE", "OAuth 2.0 with PKCE"),
    SIGN_IN_WITH_CHATGPT("Sign in with ChatGPT", "Sign in with ChatGPT"),
    GOOGLE_OAUTH("Google OAuth 2.0", "Google OAuth 2.0"),
    API_KEY_VAULT("مفتاح آمن في الخزنة", "API Key in Secret Vault")
}

data class ConnectedAiAccount(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val projectId: String = "GLOBAL", // "GLOBAL" or specific projectId
    val provider: AiProviderType = AiProviderType.GOOGLE_GEMINI,
    val accountEmail: String = "",
    val displayName: String = "",
    val authMethod: AiAuthMethod = AiAuthMethod.API_KEY_VAULT,
    val subscriptionTier: String = "Pay-As-You-Go",
    val canUseSubscriptionForApi: Boolean = false,
    val requiresSeparateApiCredit: Boolean = true,
    val approvalStatus: String = "ACTIVE", // ACTIVE, EXPIRED, REQUIRES_REAPPROVAL, REVOKED
    val encryptedAccessToken: String = "",
    val encryptedRefreshToken: String = "",
    val tokenExpiresAt: Long = System.currentTimeMillis() + (30L * 24 * 3600 * 1000), // 30 days default
    val autoRenewEnabled: Boolean = true,
    val rateLimitRpm: Int = 60,
    val usedQuotaPercent: Int = 12,
    val monthlySpendCapUsd: Double = 50.0,
    val currentMonthCostUsd: Double = 3.42,
    val grantedScopes: List<String> = listOf("api.read", "api.write", "model.chat"),
    val lastVerifiedAt: Long = System.currentTimeMillis(),
    val auditLogs: List<String> = listOf("تم ربط الحساب والتحقق من التشفير الأولي")
) {
    val isExpired: Boolean get() = System.currentTimeMillis() > tokenExpiresAt
    
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "projectId" to projectId,
        "provider" to provider.id,
        "accountEmail" to accountEmail,
        "displayName" to displayName,
        "authMethod" to authMethod.name,
        "subscriptionTier" to subscriptionTier,
        "canUseSubscriptionForApi" to canUseSubscriptionForApi,
        "requiresSeparateApiCredit" to requiresSeparateApiCredit,
        "approvalStatus" to approvalStatus,
        "encryptedAccessToken" to encryptedAccessToken,
        "encryptedRefreshToken" to encryptedRefreshToken,
        "tokenExpiresAt" to tokenExpiresAt,
        "autoRenewEnabled" to autoRenewEnabled,
        "rateLimitRpm" to rateLimitRpm,
        "usedQuotaPercent" to usedQuotaPercent,
        "monthlySpendCapUsd" to monthlySpendCapUsd,
        "currentMonthCostUsd" to currentMonthCostUsd,
        "grantedScopes" to grantedScopes,
        "lastVerifiedAt" to lastVerifiedAt,
        "auditLogs" to auditLogs
    )
}

data class ProviderCapabilityPolicy(
    val provider: AiProviderType,
    val canUseExistingSubscription: Boolean,
    val officialAuthMethod: AiAuthMethod,
    val requiresVerificationApproval: Boolean,
    val usageLimitsText: String,
    val extraFeesText: String,
    val billingDeductionText: String,
    val officialNoticeAr: String
)

object AiProviderPolicies {
    fun getPolicy(provider: AiProviderType): ProviderCapabilityPolicy = when (provider) {
        AiProviderType.OPENAI -> ProviderCapabilityPolicy(
            provider = provider,
            canUseExistingSubscription = false,
            officialAuthMethod = AiAuthMethod.SIGN_IN_WITH_CHATGPT,
            requiresVerificationApproval = true,
            usageLimitsText = "تخضع القيود لخطتك (ChatGPT Plus / Pro) ولحدود معدل الطلبات RPM/TPM",
            extraFeesText = "استخدام الـ API ينفصل عن اشتراك ChatGPT السكني المباشر وفق سياسة OpenAI",
            billingDeductionText = "تخصم التكاليف من رصيد API المخصص في حساب OpenAI Platform",
            officialNoticeAr = "تطبيقا لشروط OpenAI الرسمية: يربط الحساب عبر Sign in with ChatGPT بالتفويض المصرح."
        )
        AiProviderType.GOOGLE_GEMINI -> ProviderCapabilityPolicy(
            provider = provider,
            canUseExistingSubscription = true,
            officialAuthMethod = AiAuthMethod.GOOGLE_OAUTH,
            requiresVerificationApproval = false,
            usageLimitsText = "15 طلب/دقيقة مجاناً للنماذج الأساسية، غير محدودة للحسابات المدفوعة",
            extraFeesText = "مجانية للحدود القياسية عبر Google AI Studio مع إمكانية ربط مشروع GCP",
            billingDeductionText = "تخصم التكاليف من مشروع Google Cloud Platform المرتبط",
            officialNoticeAr = "تسجيل الدخول بـ Google يتطلب منح صلاحية 'generativelanguage.googleapis.com'."
        )
        AiProviderType.DEEPSEEK -> ProviderCapabilityPolicy(
            provider = provider,
            canUseExistingSubscription = false,
            officialAuthMethod = AiAuthMethod.API_KEY_VAULT,
            requiresVerificationApproval = false,
            usageLimitsText = "سعة معالجة عالية جداً تصل لـ 64k نافذة سياق",
            extraFeesText = "أرخص أسعار بالرموز ($0.00027/1k input)",
            billingDeductionText = "تخصم من رصيد DeepSeek API مسبق الدفع",
            officialNoticeAr = "يتم حفظ مفتاح DeepSeek المعتمد مشفراً بنظام AES-GCM 256."
        )
        AiProviderType.ANTHROPIC -> ProviderCapabilityPolicy(
            provider = provider,
            canUseExistingSubscription = false,
            officialAuthMethod = AiAuthMethod.OAUTH2_PKCE,
            requiresVerificationApproval = true,
            usageLimitsText = "حسب مستوى الحساب (Tier 1-4)",
            extraFeesText = "يتطلب رصيد API مستقل في Anthropic Console",
            billingDeductionText = "تخصم من رصيد Anthropic Console",
            officialNoticeAr = "يستخدم بروتوكول OAuth 2.0 PKCE للربط المباشر."
        )
    }
}
