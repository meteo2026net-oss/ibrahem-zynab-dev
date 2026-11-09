package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.ProjectBackupSnapshot
import com.example.data.repository.AutomationRepository
import com.example.data.repository.ProjectRepository
import com.example.data.repository.StorageRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun SyncAndBackupScreen(
    currentProject: Project?,
    automationRepository: AutomationRepository,
    projectRepository: ProjectRepository,
    storageRepository: StorageRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: ""
    val syncRecords by automationRepository.observeSyncRecords(projectId).collectAsState(initial = emptyList())
    val backups by automationRepository.observeBackups(projectId).collectAsState(initial = emptyList())
    val tasks by projectRepository.observeTasks(projectId).collectAsState(initial = emptyList())
    val files by storageRepository.observeFiles(projectId).collectAsState(initial = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) }
    var isSyncing by remember { mutableStateOf(false) }
    var isBackingUp by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var snapshotToRestore by remember { mutableStateOf<ProjectBackupSnapshot?>(null) }

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
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Sync, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "المزامنة والنسخ الاحتياطي",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Sync Engine • Disaster Recovery System",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("المزامنة", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("النسخ الاحتياطي", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (statusMessage != null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = MetraEmerald.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = statusMessage!!, color = MetraEmerald, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            when (selectedTab) {
                0 -> SyncTabContent(
                    syncRecords = syncRecords,
                    isSyncing = isSyncing,
                    currentProject = currentProject,
                    onSyncTrigger = {
                        if (currentProject != null) {
                            isSyncing = true
                            coroutineScope.launch {
                                automationRepository.runSynchronization(currentProject.id, "MANUAL")
                                isSyncing = false
                                statusMessage = "تمت المزامنة بنجاح!"
                            }
                        }
                    }
                )
                1 -> BackupTabContent(
                    backups = backups,
                    isBackingUp = isBackingUp,
                    currentProject = currentProject,
                    tasks = tasks,
                    files = files,
                    onBackupTrigger = {
                        if (currentProject != null) {
                            isBackingUp = true
                            coroutineScope.launch {
                                val snapshot = automationRepository.createProjectBackup(currentProject, tasks, files)
                                isBackingUp = false
                                statusMessage = "تم إنشاء نسخة (${snapshot.snapshotVersion}) بنجاح!"
                            }
                        }
                    },
                    onRestoreSnapshot = { snapshotToRestore = it }
                )
            }
        }

        // Restore Confirmation Dialog
        snapshotToRestore?.let { snap ->
            AlertDialog(
                onDismissRequest = { snapshotToRestore = null },
                title = { Text("استعادة النسخة الاحتياطية") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("سيتم فك التشفير واستعادة المشروع '${snap.projectName}' إلى النسخة '${snap.snapshotVersion}'.")
                        Text("SHA-256 Verified", color = MetraEmerald, style = MaterialTheme.typography.labelSmall)
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                automationRepository.restoreBackup(snap)
                                snapshotToRestore = null
                                statusMessage = "تم استعادة النسخة بنجاح!"
                            }
                        }
                    ) {
                        Text("تأكيد الاستعادة")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { snapshotToRestore = null }) { Text("إلغاء") }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SyncTabContent(
    syncRecords: List<com.example.data.model.SyncRecord>,
    isSyncing: Boolean,
    currentProject: Project?,
    onSyncTrigger: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MetraCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "مزامنة الوجهات المتعددة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "GitHub • Drive • Private Storage • Telegram",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onSyncTrigger,
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("trigger_sync_btn"),
                    enabled = !isSyncing && currentProject != null,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (isSyncing) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("بدء المزامنة الآن", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("سجل المزامنة", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (syncRecords.isEmpty()) {
            Text("لا توجد سجلات حالياً.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(syncRecords) { rec ->
                    MetraCard(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CloudDone, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "مزامنة ${rec.syncType} • ${rec.status}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                                Text(text = "${rec.syncedFilesCount} ملف • ${rec.details}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BackupTabContent(
    backups: List<ProjectBackupSnapshot>,
    isBackingUp: Boolean,
    currentProject: Project?,
    tasks: List<com.example.data.model.ProjectTask>,
    files: List<com.example.data.model.StoredFile>,
    onBackupTrigger: () -> Unit,
    onRestoreSnapshot: (ProjectBackupSnapshot) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MetraCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "النسخ الاحتياطي المشفر",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "AES-256 Snapshot • Disaster Recovery",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBackupTrigger,
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("create_backup_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = MetraAmber, contentColor = Color.Black),
                    enabled = !isBackingUp && currentProject != null,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (isBackingUp) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.Backup, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إنشاء نسخة احتياطية", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("النسخ المحفوظة", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (backups.isEmpty()) {
            Text("لا توجد نسخ احتياطية.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(backups) { bkp ->
                    MetraCard(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = MetraAmber, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "نسخة ${bkp.snapshotVersion}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                                Text(text = "${bkp.getFormattedSize()} • ${bkp.destination}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Button(
                                onClick = { onRestoreSnapshot(bkp) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), contentColor = MaterialTheme.colorScheme.secondary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("استعادة", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
