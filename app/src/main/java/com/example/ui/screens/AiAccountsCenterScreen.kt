package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiAuthMethod
import com.example.data.model.AiProviderPolicies
import com.example.data.model.AiProviderType
import com.example.data.model.ConnectedAiAccount
import com.example.data.model.Project
import com.example.data.repository.AiAccountsRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun AiAccountsCenterScreen(
    currentProject: Project?,
    aiAccountsRepository: AiAccountsRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: "GLOBAL"
    val accounts by aiAccountsRepository.observeAccounts(projectId).collectAsState(initial = emptyList())

    var selectedProviderTab by remember { mutableStateOf(AiProviderType.GOOGLE_GEMINI) }
    var showConnectDialog by remember { mutableStateOf(false) }
    var accountToDisconnect by remember { mutableStateOf<ConnectedAiAccount?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().metraGradientBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Banner
            MetraCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "مركز حسابات الذكاء الاصطناعي",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "AI Accounts Center • OAuth 2.0 PKCE",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Provider Selector Chips
            Text(
                "اختر المزود للاطلاع على شروط الاشتراك والمصادقة:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(AiProviderType.values()) { provider ->
                    FilterChip(
                        selected = selectedProviderTab == provider,
                        onClick = { selectedProviderTab = provider },
                        label = { Text(provider.displayName, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Policy & Capabilities Info Card
            val policy = AiProviderPolicies.getPolicy(selectedProviderTab)
            MetraCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "سياسة الربط: ${policy.provider.displayName}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    PolicyRow(
                        "هل يمكن استخدام اشتراكك الحالي؟",
                        if (policy.canUseExistingSubscription) "نعم، مدعوم" else "لا، تتطلب رصيد API منفصل",
                        if (policy.canUseExistingSubscription) MetraEmerald else MetraAmber
                    )
                    PolicyRow("طريقة المصادقة الرسمية:", policy.officialAuthMethod.titleAr, MaterialTheme.colorScheme.primary)
                    PolicyRow("خصم التكاليف:", policy.billingDeductionText, MaterialTheme.colorScheme.onSurfaceVariant)
                    PolicyRow("حدود الاستخدام:", policy.usageLimitsText, MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showConnectDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("connect_ai_account_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ربط حساب ${policy.provider.displayName} جديد", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Connected Accounts List
            Text(
                text = "الحسابات المفوضة والمتصلة (${accounts.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (accounts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "لا توجد حسابات متصلة حالياً",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "أضف حسابك بـ OAuth أو الـ Vault للبدء",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(accounts, key = { it.id }) { acc ->
                        MetraCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("ai_account_card_${acc.accountEmail}")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Psychology,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = acc.displayName,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "طريقة المصادقة: ${acc.authMethod.titleAr}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Surface(
                                        color = MetraEmerald.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            acc.approvalStatus,
                                            color = MetraEmerald,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "نسبة استهلاك الكوتا:",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LinearProgressIndicator(
                                            progress = { acc.usedQuotaPercent / 100f },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp)),
                                            color = if (acc.usedQuotaPercent > 80) MetraRose else MetraEmerald,
                                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    }
                                    Text(
                                        "${acc.usedQuotaPercent}%",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Text(
                                        "حد الإنفاق: $${acc.monthlySpendCapUsd}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        "التكلفة الحالية: $${acc.currentMonthCostUsd}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MetraAmber
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                aiAccountsRepository.refreshAccountTokens(acc.id)
                                                statusMessage = "تم تجديد رموز الحساب بنجاح"
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                                            contentColor = MaterialTheme.colorScheme.secondary
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("تجديد الرموز", fontSize = 11.sp)
                                    }

                                    IconButton(
                                        onClick = { accountToDisconnect = acc },
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Disconnect", tint = MetraRose)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Modals
        if (showConnectDialog) {
            ConnectAccountDialog(
                selectedProviderTab = selectedProviderTab,
                projectId = projectId,
                aiAccountsRepository = aiAccountsRepository,
                onDismiss = { showConnectDialog = false },
                onSuccess = { statusMessage = it }
            )
        }

        accountToDisconnect?.let { acc ->
            DisconnectAccountDialog(
                account = acc,
                aiAccountsRepository = aiAccountsRepository,
                onDismiss = { accountToDisconnect = null }
            )
        }
    }
}

@Composable
fun ConnectAccountDialog(
    selectedProviderTab: AiProviderType,
    projectId: String,
    aiAccountsRepository: AiAccountsRepository,
    onDismiss: () -> Unit,
    onSuccess: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var emailInput by remember { mutableStateOf("user@metra.ai") }
    var apiKeyInput by remember { mutableStateOf("") }
    var connectViaOAuth by remember { mutableStateOf(selectedProviderTab == AiProviderType.GOOGLE_GEMINI || selectedProviderTab == AiProviderType.OPENAI) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ربط حساب ${selectedProviderTab.displayName}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("اختر طريقة الربط المصرح بها:", style = MaterialTheme.typography.bodySmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = connectViaOAuth,
                        onClick = { connectViaOAuth = true },
                        label = { Text("OAuth 2.0 PKCE") }
                    )
                    FilterChip(
                        selected = !connectViaOAuth,
                        onClick = { connectViaOAuth = false },
                        label = { Text("Vault API Key") }
                    )
                }

                if (connectViaOAuth) {
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("البريد الإلكتروني") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Surface(
                        color = MetraEmerald.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "سيتم استخدام المصادقة الرسمية لـ ${selectedProviderTab.displayName}.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraEmerald,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                } else {
                    OutlinedTextField(
                        value = apiKeyInput,
                        onValueChange = { apiKeyInput = it },
                        label = { Text("مفتاح API") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("vault_api_key_input"),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (connectViaOAuth) {
                            aiAccountsRepository.connectOAuthAccount(
                                provider = selectedProviderTab,
                                accountEmail = emailInput,
                                projectId = projectId,
                                authMethod = if (selectedProviderTab == AiProviderType.OPENAI) AiAuthMethod.SIGN_IN_WITH_CHATGPT else AiAuthMethod.GOOGLE_OAUTH,
                                subscriptionTier = "Pro Tier",
                                grantedScopes = listOf("api.read", "api.write")
                            )
                        } else if (apiKeyInput.isNotBlank()) {
                            aiAccountsRepository.connectApiKeyVaultAccount(
                                provider = selectedProviderTab,
                                apiKey = apiKeyInput,
                                accountName = emailInput,
                                projectId = projectId
                            )
                        }
                        onDismiss()
                        onSuccess("تم ربط الحساب بنجاح.")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("تأكيد الربط")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun DisconnectAccountDialog(
    account: ConnectedAiAccount,
    aiAccountsRepository: AiAccountsRepository,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("فصل الحساب") },
        text = { Text("هل أنت متأكد من فصل الحساب '${account.displayName}'؟") },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        aiAccountsRepository.disconnectAccount(account.id)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MetraRose)
            ) {
                Text("فصل الحساب")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun PolicyRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = valueColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.8f)
        )
    }
}
