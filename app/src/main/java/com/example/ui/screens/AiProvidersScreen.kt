package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgentRole
import com.example.data.model.AiModelSpec
import com.example.data.model.AiProviderType
import com.example.data.model.ModelCapability
import com.example.data.model.Project
import com.example.data.repository.AiProvidersRepository
import com.example.data.repository.IntegrationsRepository
import com.example.ui.theme.metraGradientBackground
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraPurple
import com.example.ui.theme.MetraRose
import kotlinx.coroutines.launch

@Composable
fun AiProvidersScreen(
    currentProject: Project?,
    aiProvidersRepository: AiProvidersRepository,
    integrationsRepository: IntegrationsRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("المزودون الرسميون", "سجل النماذج (Model Registry)", "توجيه الوكلاء والتحويل الاحتياطي")

    val models by aiProvidersRepository.modelsState.collectAsState()
    val failoverLogs by aiProvidersRepository.getFailoverLogsFlow().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .metraGradientBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MetraCyan.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Psychology,
                    contentDescription = null,
                    tint = MetraCyan,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "مركز مزودي الذكاء الاصطناعي",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "DeepSeek • Google Gemini • OpenAI • Anthropic Claude",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
            contentColor = MetraCyan,
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) MetraCyan else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> ProvidersListTab(currentProject, aiProvidersRepository, integrationsRepository)
                1 -> ModelRegistryTab(models)
                2 -> AgentRoutingTab(currentProject, aiProvidersRepository, failoverLogs)
            }
        }
    }
}

@Composable
private fun ProvidersListTab(
    currentProject: Project?,
    aiProvidersRepository: AiProvidersRepository,
    integrationsRepository: IntegrationsRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val providers = AiProviderType.values()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(providers) { provider ->
            ProviderCard(
                provider = provider,
                currentProject = currentProject,
                onTestConnection = { apiKey, modelId, onResult ->
                    scope.launch {
                        val (success, msg) = aiProvidersRepository.testProviderConnection(provider, apiKey, modelId)
                        onResult(success, msg)
                    }
                },
                onSaveApiKey = { key ->
                    scope.launch {
                        if (currentProject != null) {
                            integrationsRepository.saveApiKey(provider.id, key, currentProject.id)
                            Toast.makeText(context, "تم حفظ وتشفير مفتاح ${provider.displayName} بنجاح", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "يرجى تحديد مشروع أولاً", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ProviderCard(
    provider: AiProviderType,
    currentProject: Project?,
    onTestConnection: (String, String, (Boolean, String) -> Unit) -> Unit,
    onSaveApiKey: (String) -> Unit
) {
    var apiKey by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isTesting by remember { mutableStateOf(false) }
    var testResult by remember { mutableStateOf<Pair<Boolean, String>?>(null) }

    val accentColor = when (provider) {
        AiProviderType.DEEPSEEK -> MetraCyan
        AiProviderType.GOOGLE_GEMINI -> MetraIndigo
        AiProviderType.OPENAI -> MetraEmerald
        AiProviderType.ANTHROPIC -> MetraAmber
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = provider.displayName,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = provider.defaultEndpoint,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "رسمي ومستقل",
                        color = accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("مفتاح API الخاص بالمشروع ($apiKey)") },
                placeholder = { Text(if (provider == AiProviderType.DEEPSEEK) "sk-..." else "مفتاح API") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedLabelColor = accentColor,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onSaveApiKey(apiKey) },
                    enabled = apiKey.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("حفظ وتشفير المفتاح", fontSize = 13.sp)
                }

                OutlinedButton(
                    onClick = {
                        isTesting = true
                        testResult = null
                        val defaultModel = when (provider) {
                            AiProviderType.DEEPSEEK -> "deepseek-chat"
                            AiProviderType.GOOGLE_GEMINI -> "gemini-2.5-flash"
                            AiProviderType.OPENAI -> "gpt-4o-mini"
                            AiProviderType.ANTHROPIC -> "claude-3-5-haiku-20241022"
                        }
                        onTestConnection(apiKey, defaultModel) { success, msg ->
                            isTesting = false
                            testResult = Pair(success, msg)
                        }
                    },
                    enabled = apiKey.isNotBlank() && !isTesting,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = accentColor),
                    modifier = Modifier.weight(1f)
                ) {
                    if (isTesting) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = accentColor, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("اختبار الاتصال", fontSize = 13.sp)
                    }
                }
            }

            testResult?.let { (success, message) ->
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (success) MetraEmerald.copy(alpha = 0.15f) else MetraRose.copy(alpha = 0.15f))
                        .padding(10.dp)
                ) {
                    Icon(
                        if (success) Icons.Default.CheckCircle else Icons.Default.Error,
                        contentDescription = null,
                        tint = if (success) MetraEmerald else MetraRose,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = message,
                        color = if (success) MetraEmerald else MetraRose,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun ModelRegistryTab(models: List<AiModelSpec>) {
    var selectedCapability by remember { mutableStateOf<ModelCapability?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "مصفوفة القدرات (Capability Matrix) وسجل النماذج المعتمدة",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "فحص فعلي لإمكانيات النموذج قبل توجيه المهام (استدلال، كود، وسائط، أدوات)",
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterChip(
                selected = selectedCapability == null,
                onClick = { selectedCapability = null },
                label = { Text("الكل", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MetraCyan,
                    selectedLabelColor = Color.Black
                )
            )
            FilterChip(
                selected = selectedCapability == ModelCapability.REASONING,
                onClick = { selectedCapability = ModelCapability.REASONING },
                label = { Text("استدلال R1/o1", fontSize = 11.sp) }
            )
            FilterChip(
                selected = selectedCapability == ModelCapability.CODE_GENERATION,
                onClick = { selectedCapability = ModelCapability.CODE_GENERATION },
                label = { Text("برمجة", fontSize = 11.sp) }
            )
            FilterChip(
                selected = selectedCapability == ModelCapability.TOOL_CALLING,
                onClick = { selectedCapability = ModelCapability.TOOL_CALLING },
                label = { Text("أدوات", fontSize = 11.sp) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val filteredModels = models.filter {
            selectedCapability == null || it.capabilities.contains(selectedCapability)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredModels) { model ->
                ModelSpecCard(model)
            }
        }
    }
}

@Composable
private fun ModelSpecCard(model: AiModelSpec) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = model.displayName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MetraCyan.copy(alpha = 0.12f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = model.provider.displayName,
                        color = MetraCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "معرف النموذج: ${model.modelId}",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = model.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (model.supportsReasoning) {
                    CapabilityBadge("استدلال", MetraPurple)
                }
                if (model.supportsTools) {
                    CapabilityBadge("أدوات/Functions", MetraAmber)
                }
                if (model.supportsVision) {
                    CapabilityBadge("صور/ملفات", MetraIndigo)
                }
                CapabilityBadge("${model.contextWindow / 1000}K سياق", MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "الإدخال: $${String.format("%.5f", model.costPer1kInputUsd)} / 1K",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
                Text(
                    text = "الإخراج: $${String.format("%.5f", model.costPer1kOutputUsd)} / 1K",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun CapabilityBadge(label: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = color, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun AgentRoutingTab(
    currentProject: Project?,
    aiProvidersRepository: AiProvidersRepository,
    failoverLogs: List<com.example.data.model.FailoverLog>
) {
    val roles = AgentRole.values()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "توجيه نماذج الوكلاء وسياسة التحويل الاحتياطي",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "يتم التحويل التلقائي للنموذج الاحتياطي عند حدوث خطأ أو تجاوز حدود الاستخدام، مع توثيق الحدث والتكلفة.",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }

        items(roles) { role ->
            val config = aiProvidersRepository.getAgentConfig(role)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = role.titleAr,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("النموذج الأساسي:", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text(config.primaryModelId, color = MetraCyan, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("المزود: ${config.primaryProvider.displayName}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                        }
                        Icon(Icons.Default.Route, contentDescription = null, tint = MetraAmber, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("النموذج الاحتياطي (Fallback):", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text(config.fallbackModelId, color = MetraAmber, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("المزود: ${config.fallbackProvider.displayName}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "سجل أحداث التحويل الاحتياطي (Failover Events Log)",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (failoverLogs.isEmpty()) {
            item {
                Text(
                    text = "لم يُسجل أي تحويل اضطراري حتى الآن. جميع النماذج تعمل باستقرار.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        } else {
            items(failoverLogs) { log ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "تحويل: ${log.primaryModel} ➔ ${log.fallbackModel}",
                                color = MetraAmber,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "+$${String.format("%.5f", log.costDifferenceUsd)}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = "السبب: ${log.failureReason}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
