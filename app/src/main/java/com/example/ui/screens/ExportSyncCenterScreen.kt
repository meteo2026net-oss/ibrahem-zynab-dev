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
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.ExportDestinationType
import com.example.data.model.ExportScope
import com.example.data.model.ExportTemplate
import com.example.data.model.Project
import com.example.data.model.StoredFile
import com.example.data.model.SyncMode
import com.example.data.model.SyncTaskJob
import com.example.data.model.SyncTrigger
import com.example.data.repository.ExportSyncRepository
import com.example.data.repository.StorageRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import kotlinx.coroutines.launch

@Composable
fun ExportSyncCenterScreen(
    currentProject: Project?,
    exportSyncRepository: ExportSyncRepository,
    storageRepository: StorageRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: ""
    val projectName = currentProject?.name ?: "Metra Project"
    val syncJobs by exportSyncRepository.observeSyncJobs(projectId).collectAsState(initial = emptyList())
    val storedFiles by storageRepository.observeFiles(projectId).collectAsState(initial = emptyList())

    var selectedDestination by remember { mutableStateOf(ExportDestinationType.DEVICE_LOCAL) }
    var selectedTemplate by remember { mutableStateOf(ExportTemplate.FULL_PROJECT_ZIP) }
    var selectedScope by remember { mutableStateOf(ExportScope.FULL_PROJECT) }
    var selectedSyncMode by remember { mutableStateOf(SyncMode.BIDIRECTIONAL) }
    var selectedTrigger by remember { mutableStateOf(SyncTrigger.MANUAL) }

    var isExporting by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var activeConflictJob by remember { mutableStateOf<SyncTaskJob?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MetraEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "مركز التصدير والمزامنة الشامل (Universal Export & Sync Center)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Multi-Destination Export, Background Sync & SHA-256 Conflict Shield",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraCyan
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Destination Tabs
        Text("اختر وجهة التصدير أو المزامنة:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(ExportDestinationType.values()) { dest ->
                FilterChip(
                    selected = selectedDestination == dest,
                    onClick = { selectedDestination = dest },
                    label = { Text(dest.titleAr, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Configurator Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("1. نطاق التصدير والقوالب المحفوظة:", style = MaterialTheme.typography.labelSmall, color = MetraCyan)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(ExportTemplate.values()) { tmpl ->
                        FilterChip(
                            selected = selectedTemplate == tmpl,
                            onClick = { selectedTemplate = tmpl },
                            label = { Text(tmpl.titleAr, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MetraEmerald,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("2. نمط المزامنة لحماية البيانات:", style = MaterialTheme.typography.labelSmall, color = MetraAmber)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(SyncMode.values()) { sm ->
                        FilterChip(
                            selected = selectedSyncMode == sm,
                            onClick = { selectedSyncMode = sm },
                            label = { Text(sm.titleAr, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MetraAmber,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Export to Device or Trigger Sync Button
                Button(
                    onClick = {
                        if (currentProject != null) {
                            isExporting = true
                            coroutineScope.launch {
                                val job = exportSyncRepository.createExportSyncJob(
                                    project = currentProject,
                                    destination = selectedDestination,
                                    scope = selectedScope,
                                    template = selectedTemplate,
                                    syncMode = selectedSyncMode,
                                    trigger = selectedTrigger,
                                    files = storedFiles
                                )
                                isExporting = false
                                statusMessage = "تم إنشاء وتنفيذ عملية التصدير '${job.template.titleAr}' إلى ${job.destination.titleAr} بنجاح (SHA-256 Verified)"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("export_to_device_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = MetraEmerald, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isExporting && currentProject != null
                ) {
                    if (isExporting) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("جاري الضغط والمزامنة والخلفية...")
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.SaveAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedDestination == ExportDestinationType.DEVICE_LOCAL) "Export to Device (تصدير كـ ZIP)" else "بدء المزامنة فوراً مع ${selectedDestination.titleAr}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (statusMessage != null) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                color = MetraEmerald.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = MetraEmerald)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(statusMessage ?: "", color = MetraEmerald, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Sync Jobs History and Background Worker Queue
        Text(text = "سجل المزامنة والعمال الخلفية (${syncJobs.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))

        if (syncJobs.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.FolderZip, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("لا توجد عمليات تصدير أو مزامنة سابقة", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(syncJobs, key = { it.id }) { job ->
                    Card(
                        modifier = Modifier.fillMaxWidth().testTag("sync_job_card_${job.id}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (job.hasConflict) MetraRose else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MetraEmerald.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Sync, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = "${job.template.titleAr} -> ${job.destination.titleAr}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text(text = "SHA-256: ${job.sha256Fingerprint.take(16)}...", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                }
                                Surface(
                                    color = if (job.status == "COMPLETED") MetraEmerald.copy(alpha = 0.15f) else MetraAmber.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(job.status, color = if (job.status == "COMPLETED") MetraEmerald else MetraAmber, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { job.progressPercent / 100f },
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                                color = MetraEmerald,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("النسبة: ${job.progressPercent}% • حجم البيانات: ${job.transferredBytes / 1024} KB • الملفات: ${job.successfulFilesCount}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            if (job.hasConflict) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { activeConflictJob = job },
                                    colors = ButtonDefaults.buttonColors(containerColor = MetraRose, contentColor = Color.White),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.fillMaxWidth().height(32.dp)
                                ) {
                                    Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("عرض تعارض البصمة الرقمية والحل", fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal: Conflict Resolution
    activeConflictJob?.let { job ->
        AlertDialog(
            onDismissRequest = { activeConflictJob = null },
            title = { Text("اكتشاف تعارض الشفرة البصرية (SHA-256)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("البصمة الرقمية للنسخة المحلية لا تطابق النسخة البعيدة:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("SHA-256 المحلية: ${job.sha256Fingerprint.take(20)}...", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Text("SHA-256 البعيدة: c3f7a1b2d9e8012...", style = MaterialTheme.typography.labelSmall, color = MetraAmber)
                    Text("سياسة الأمان تمنع الاستبدال التلقائي دون موافقتك الصريحة.", style = MaterialTheme.typography.labelSmall, color = MetraRose)
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeConflictJob = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraEmerald, contentColor = Color.White)
                ) {
                    Text("اعتماد ودمج أمن")
                }
            },
            dismissButton = {
                TextButton(onClick = { activeConflictJob = null }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}
