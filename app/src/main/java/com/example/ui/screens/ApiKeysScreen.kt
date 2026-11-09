package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.model.Project
import com.example.data.model.ProjectApiCredential
import com.example.data.repository.IntegrationsRepository
import com.example.ui.theme.metraGradientBackground
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import kotlinx.coroutines.launch

@Composable
fun ApiKeysScreen(
    currentProject: Project?,
    integrationsRepository: IntegrationsRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: ""
    val credentials by integrationsRepository.observeApiCredentials(projectId).collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var credToDelete by remember { mutableStateOf<ProjectApiCredential?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

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
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null,
                        tint = MetraAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "إدارة مفاتيح API المستقلة",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (currentProject != null) "مشروع: ${currentProject.name} (عزل تام للأسرار)" else "جميع مفاتيح المنصة المشفرة",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraAmber
                        )
                    }
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MetraAmber, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("add_api_key_btn")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إضافة مفتاح", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (statusMessage != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = MetraEmerald.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MetraEmerald)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = statusMessage ?: "", color = MetraEmerald, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Credentials List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    text = "المفاتيح المسجلة (${credentials.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MetraCyan,
                    fontWeight = FontWeight.Bold
                )
            }

            if (credentials.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = "لا توجد مفاتيح API مسجلة لهذا المشروع بعد. يمكنك إضافة مفاتيح مستقلة لـ OpenAI, Gemini, Anthropic مع حدود استهلاك شهرية.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(credentials) { cred ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(MetraAmber.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = MetraAmber, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${cred.keyName} (${cred.provider})",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "الرمز المحفوظ: ${cred.maskedKey} • النموذج: ${cred.defaultModel}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MetraCyan
                                    )
                                }
                                Switch(
                                    checked = cred.isEnabled,
                                    onCheckedChange = { enabled ->
                                        coroutineScope.launch {
                                            integrationsRepository.toggleApiCredential(cred.id, enabled)
                                        }
                                    },
                                    colors = SwitchDefaults.colors(checkedThumbColor = MetraCyan, checkedTrackColor = MetraIndigo)
                                )
                                IconButton(onClick = { credToDelete = cred }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MetraRose)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Consumption and Rate limits
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "الاستهلاك الشهري: $%.2f / $%.2f".format(cred.currentSpentMonthly, cred.spendingLimitMonthly),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "الحد الأقصى: ${cred.rateLimitRpm} RPM",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MetraAmber
                                )
                            }

                            val spendPct = (cred.currentSpentMonthly / cred.spendingLimitMonthly).toFloat().coerceIn(0f, 1f)
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { spendPct },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (spendPct > 0.85f) MetraRose else MetraCyan,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog: Add API Key
    if (showAddDialog) {
        var provider by remember { mutableStateOf("OpenAI") }
        var keyName by remember { mutableStateOf("Primary Key") }
        var plainKey by remember { mutableStateOf("") }
        var defaultModel by remember { mutableStateOf("gpt-4o") }
        var spendingLimit by remember { mutableStateOf("100") }
        var rateLimit by remember { mutableStateOf("120") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("إضافة مفتاح API مشفر") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("المفتاح سيعزل للمشروع المحدد ويشفر ولن يرسل لأي سجلات أو لواجهة المستخدم:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("OpenAI", "Gemini", "Anthropic", "Custom").forEach { prov ->
                            FilterChip(
                                selected = provider == prov,
                                onClick = {
                                    provider = prov
                                    defaultModel = when (prov) {
                                        "Gemini" -> "gemini-1.5-pro"
                                        "Anthropic" -> "claude-3-5-sonnet"
                                        else -> "gpt-4o"
                                    }
                                },
                                label = { Text(prov, fontSize = 11.sp) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = keyName,
                        onValueChange = { keyName = it },
                        label = { Text("اسم المفتاح الوصفي") },
                        modifier = Modifier.fillMaxWidth().testTag("api_key_name_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = plainKey,
                        onValueChange = { plainKey = it },
                        label = { Text("مفتاح الـ API الحقيقي (sk-...)") },
                        modifier = Modifier.fillMaxWidth().testTag("api_key_value_input"),
                        singleLine = true
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = defaultModel,
                            onValueChange = { defaultModel = it },
                            label = { Text("النموذج") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = spendingLimit,
                            onValueChange = { spendingLimit = it },
                            label = { Text("الحد ($)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (plainKey.isNotBlank()) {
                            coroutineScope.launch {
                                integrationsRepository.addApiCredential(
                                    projectId = projectId,
                                    provider = provider,
                                    keyName = keyName,
                                    plainKey = plainKey,
                                    defaultModel = defaultModel,
                                    spendingLimit = spendingLimit.toDoubleOrNull() ?: 100.0,
                                    rateLimitRpm = rateLimit.toIntOrNull() ?: 60
                                )
                                showAddDialog = false
                                statusMessage = "تم تشفير وحفظ مفتاح $provider بنجاح!"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraAmber, contentColor = Color.Black),
                    enabled = plainKey.isNotBlank(),
                    modifier = Modifier.testTag("submit_api_key_btn")
                ) {
                    Text("حفظ وتشفير")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Dialog: Delete Key
    credToDelete?.let { cred ->
        AlertDialog(
            onDismissRequest = { credToDelete = null },
            title = { Text("تأكيد حذف المفتاح") },
            text = { Text("هل أنت متأكد من حذف المفتاح '${cred.keyName}'؟") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            integrationsRepository.deleteApiCredential(cred.id, cred.projectId)
                            credToDelete = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraRose)
                ) {
                    Text("حذف نهائي")
                }
            },
            dismissButton = {
                TextButton(onClick = { credToDelete = null }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
        }
    }
}
