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
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.model.GoogleAccountEntry
import com.example.data.model.Project
import com.example.data.repository.IntegrationsRepository
import com.example.data.repository.ProjectRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import kotlinx.coroutines.launch

@Composable
fun GoogleAccountsScreen(
    currentProject: Project?,
    integrationsRepository: IntegrationsRepository,
    projectRepository: ProjectRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val accounts by integrationsRepository.observeGoogleAccounts().collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

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
                        imageVector = Icons.Default.Cloud,
                        contentDescription = null,
                        tint = MetraEmerald,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "إدارة حسابات Google المتعددة",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Google Drive Manager & Backup Folders",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraEmerald
                        )
                    }
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MetraEmerald, contentColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("add_google_account_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ربط حساب", fontSize = 12.sp)
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

        // Accounts List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Text(
                    text = "الحسابات المرتبطة (${accounts.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (accounts.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        Text(
                            text = "لم يتم ربط أي حساب Google بعد. اضغط على 'ربط حساب' لإضافة حساب Drive مستقل لكل مشروع أو حساب مشترك.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(accounts) { acc ->
                    val usedGB = acc.storageUsedBytes / (1024.0 * 1024.0 * 1024.0)
                    val totalGB = acc.storageTotalBytes / (1024.0 * 1024.0 * 1024.0)
                    val pct = (usedGB / totalGB).toFloat().coerceIn(0f, 1f)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(MetraEmerald.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Cloud, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = acc.displayName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text(text = acc.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        integrationsRepository.removeGoogleAccount(acc.id, acc.email)
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MetraRose)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Storage bar
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "مساحة Drive المستهلكة: %.1f GB من %.0f GB".format(usedGB, totalGB),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(text = "${(pct * 100).toInt()}%", style = MaterialTheme.typography.labelSmall, color = MetraEmerald)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { pct },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = MetraEmerald,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Structured project folders mapping
                            Text("المجلدات المنظمة تلقائياً لهذا الحساب:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                FolderChip("BACKUPS")
                                FolderChip("DOCS")
                                FolderChip("REPORTS")
                                FolderChip("RELEASES")
                            }

                            if (currentProject != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                val isAssigned = currentProject.googleDriveAccountEmail == acc.email
                                if (isAssigned) {
                                    Surface(color = MetraEmerald.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                                        Text(
                                            text = "مخصص للمشروع الحالي (${currentProject.name})",
                                            color = MetraEmerald,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                projectRepository.updateProject(
                                                    currentProject.copy(googleDriveAccountEmail = acc.email)
                                                )
                                                statusMessage = "تم تخصيص حساب Drive '${acc.email}' للمشروع '${currentProject.name}'"
                                            }
                                        },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("تخصيص للمشروع الحالي", fontSize = 11.sp)
                                }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog: Add Google Account
    if (showAddDialog) {
        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("ربط حساب Google جديد") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "أدخل البريد الإلكتروني للحساب الجديد لتخصيص مجلدات Drive والنسخ الاحتياطي المستقلة له.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("اسم الحساب (مثال: Metra Primary Drive)") },
                        modifier = Modifier.fillMaxWidth().testTag("google_name_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("البريد الإلكتروني (Google Email)") },
                        modifier = Modifier.fillMaxWidth().testTag("google_email_input"),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (email.isNotBlank()) {
                            coroutineScope.launch {
                                integrationsRepository.addGoogleAccount(
                                    email = email,
                                    displayName = name.ifBlank { email }
                                )
                                showAddDialog = false
                                statusMessage = "تم ربط الحساب $email بنجاح وتجهيز مجلدات Drive المخصصة!"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraEmerald, contentColor = Color.White),
                    enabled = email.isNotBlank(),
                    modifier = Modifier.testTag("submit_google_account_btn")
                ) {
                    Text("ربط وتأكيد")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun FolderChip(name: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(6.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FolderSpecial, contentDescription = null, tint = MetraAmber, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
        }
    }
}
