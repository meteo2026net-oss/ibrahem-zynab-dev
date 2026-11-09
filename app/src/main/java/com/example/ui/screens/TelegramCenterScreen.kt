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
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.Project
import com.example.data.model.TelegramBotConfig
import com.example.data.model.TelegramWebhookEvent
import com.example.data.repository.IntegrationsRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun TelegramCenterScreen(
    currentProject: Project?,
    integrationsRepository: IntegrationsRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val bots by integrationsRepository.observeTelegramBots().collectAsState(initial = emptyList())
    val events by integrationsRepository.observeTelegramEvents().collectAsState(initial = emptyList())

    var showAddBotDialog by remember { mutableStateOf(false) }
    var showSimulateWebhookDialog by remember { mutableStateOf(false) }
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = MetraCyan,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "مركز بوتات Telegram",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "استقبال الصور والملفات عبر Webhook وتخزينها تلقائياً",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraCyan
                        )
                    }
                    Button(
                        onClick = { showAddBotDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("add_telegram_bot_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إضافة بوت", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick simulation banner
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "محاكاة رسالة / ملف من Telegram",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "أرسل صورة أو مستند PDF لاختبار التنزيل والتوجيه للمشروع الحالي",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = { showSimulateWebhookDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraIndigo),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("simulate_telegram_event_btn")
                ) {
                    Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("إرسال ملف اختبار", fontSize = 11.sp)
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

        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Text(
                    text = "البوتات النشطة (${bots.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (bots.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        Text(
                            text = "لم يتم تسجيل أي بوت Telegram بعد. اضغط على 'إضافة بوت' لإدخال رمز التوكن الخاص بـ BotFather.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(bots) { bot ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(MetraCyan.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.SmartToy, contentDescription = null, tint = MetraCyan, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = "${bot.botName} (${bot.botUsername})", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text(text = "الرمز: ${bot.maskedToken} • مشفر", style = MaterialTheme.typography.labelSmall, color = MetraEmerald)
                                }
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        integrationsRepository.deleteTelegramBot(bot.id)
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MetraRose)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Webhook: ${bot.webhookUrl}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "سجل الرسائل والمرفقات المستلمة (${events.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MetraIndigo,
                    fontWeight = FontWeight.Bold
                )
            }

            if (events.isEmpty()) {
                item {
                    Text(
                        text = "لا توجد رسائل أو ملفات مستلمة عبر Webhook حتى الآن.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            } else {
                items(events) { ev ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (ev.attachmentType) {
                                    "PHOTO" -> Icons.Default.Image
                                    "PDF" -> Icons.Default.PictureAsPdf
                                    else -> Icons.Default.AttachFile
                                },
                                contentDescription = null,
                                tint = if (ev.hasAttachment) MetraCyan else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = ev.senderUsername, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)) {
                                        Text(
                                            text = ev.targetStorageDestination,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MetraEmerald,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(text = ev.messageText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                if (ev.hasAttachment) {
                                    Text(
                                        text = "المرفق: ${ev.attachmentFileName} (${ev.fileSizeBytes / 1024} KB) - تم الفحص والحفظ",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MetraCyan
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog: Add Bot
    if (showAddBotDialog) {
        var botUsername by remember { mutableStateOf("") }
        var botName by remember { mutableStateOf("") }
        var botToken by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddBotDialog = false },
            title = { Text("إضافة بوت Telegram جديد") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("أدخل رمز Bot Token المستخرج من BotFather. سيتم تشفيره وإعداد Webhook له فورياً.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    OutlinedTextField(
                        value = botName,
                        onValueChange = { botName = it },
                        label = { Text("اسم البوت (مثال: Metra Assistant)") },
                        modifier = Modifier.fillMaxWidth().testTag("telegram_bot_name_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = botUsername,
                        onValueChange = { botUsername = it },
                        label = { Text("معرف البوت (مثال: @MetraAiBot)") },
                        modifier = Modifier.fillMaxWidth().testTag("telegram_bot_username_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = botToken,
                        onValueChange = { botToken = it },
                        label = { Text("رمز التوكن (Bot Token)") },
                        modifier = Modifier.fillMaxWidth().testTag("telegram_bot_token_input"),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (botUsername.isNotBlank() && botToken.isNotBlank()) {
                            coroutineScope.launch {
                                integrationsRepository.registerTelegramBot(
                                    projectId = currentProject?.id ?: "",
                                    botUsername = botUsername,
                                    botName = botName.ifBlank { "Telegram Bot" },
                                    botToken = botToken
                                )
                                showAddBotDialog = false
                                statusMessage = "تم تسجيل البوت $botUsername وإعداد الـ Webhook بنجاح!"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                    enabled = botUsername.isNotBlank() && botToken.isNotBlank(),
                    modifier = Modifier.testTag("submit_telegram_bot_btn")
                ) {
                    Text("حفظ وتفعيل")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddBotDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Dialog: Simulate Webhook File Arrival
    if (showSimulateWebhookDialog) {
        var senderUsername by remember { mutableStateOf("@engineer_ahmed") }
        var messageText by remember { mutableStateOf("يرجى مراجعة وتخزين مخطط المحطة والتقرير المرفق") }
        var attachmentType by remember { mutableStateOf("PDF") }
        var fileName by remember { mutableStateOf("weather_station_schematic.pdf") }

        AlertDialog(
            onDismissRequest = { showSimulateWebhookDialog = false },
            title = { Text("محاكاة وصول ملف عبر Telegram Webhook") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("يقوم هذا الاختبار بمحاكاة استقبال تحديث Webhook، وفحص أمان الملف، وتوجيهه إلى التخزين المخصص للمشروع.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    OutlinedTextField(
                        value = senderUsername,
                        onValueChange = { senderUsername = it },
                        label = { Text("اسم مرسل Telegram") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        label = { Text("نص الرسالة") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = attachmentType == "PDF",
                            onClick = {
                                attachmentType = "PDF"
                                fileName = "weather_station_spec.pdf"
                            },
                            label = { Text("مستند PDF") }
                        )
                        FilterChip(
                            selected = attachmentType == "PHOTO",
                            onClick = {
                                attachmentType = "PHOTO"
                                fileName = "sensor_diagram.jpg"
                            },
                            label = { Text("صورة Diagram") }
                        )
                    }
                    OutlinedTextField(
                        value = fileName,
                        onValueChange = { fileName = it },
                        label = { Text("اسم الملف المرفق") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val botId = bots.firstOrNull()?.id ?: "sim_bot"
                            integrationsRepository.simulateIncomingTelegramEvent(
                                botId = botId,
                                projectId = currentProject?.id ?: "",
                                senderUsername = senderUsername,
                                messageText = messageText,
                                hasAttachment = true,
                                attachmentType = attachmentType,
                                attachmentFileName = fileName,
                                fileSizeBytes = if (attachmentType == "PDF") 1_450_000L else 780_000L
                            )
                            showSimulateWebhookDialog = false
                            statusMessage = "تم استقبال الملف '$fileName' وتنزيله وفحصه وحفظه في مركز التخزين الموحد بنجاح!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                    modifier = Modifier.testTag("confirm_simulation_btn")
                ) {
                    Text("تنفيذ الاختبار الفعلي")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSimulateWebhookDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
        }
    }
}
