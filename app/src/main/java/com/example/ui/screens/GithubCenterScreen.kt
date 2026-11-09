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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Commit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MergeType
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.GitHubAccountConnection
import com.example.data.model.GitHubRepositoryInfo
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
fun GithubCenterScreen(
    currentProject: Project?,
    integrationsRepository: IntegrationsRepository,
    projectRepository: ProjectRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val accounts by integrationsRepository.observeGitHubAccounts().collectAsState(initial = emptyList())

    var showConnectDialog by remember { mutableStateOf(false) }
    var showCommitDialog by remember { mutableStateOf(false) }
    var showPrDialog by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isOperating by remember { mutableStateOf(false) }

    val activeUsername = accounts.firstOrNull()?.username ?: "developer"
    val repos = remember(activeUsername) { integrationsRepository.getSampleRepositoriesForAccount(activeUsername) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Card: Active Project & GitHub Status
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = MetraCyan,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "مركز تكامل GitHub",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { showConnectDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("connect_github_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ربط حساب", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (currentProject != null) "المشروع النشط: ${currentProject.name} (الفرع الافتراضي: ${currentProject.githubBranch})" else "يرجى اختيار مشروع لتوجيه عمليات Commit والـ PR إليه",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (statusMessage != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                color = MetraEmerald.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MetraEmerald.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = MetraEmerald)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = statusMessage ?: "", color = MetraEmerald, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Accounts & Repositories
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Text(
                    text = "الحسابات المرتبطة (${accounts.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MetraCyan,
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
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("لم يتم ربط أي حساب GitHub حتى الآن", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "اربط حسابك بواسطة Personal Access Token بصلاحيات repo لقراءة ودفع التعديلات بأمان.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(accounts) { acc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(MetraCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = acc.username.take(1).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    color = MetraCyan
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "@${acc.username}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "الرمز: ${acc.maskedToken} • متصل ومحمي",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MetraEmerald
                                )
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    integrationsRepository.disconnectGitHubAccount(acc.id, acc.username)
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Disconnect", tint = MetraRose)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "المستودعات المتاحة",
                    style = MaterialTheme.typography.titleMedium,
                    color = MetraIndigo,
                    fontWeight = FontWeight.Bold
                )
            }

            items(repos) { repo ->
                val isLinkedToCurrent = currentProject?.githubRepo == repo.repoFullName
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isLinkedToCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isLinkedToCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = repo.repoFullName,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (isLinkedToCurrent) {
                                Surface(
                                    color = MetraCyan.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "المستودع النشط",
                                        color = MetraCyan,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            } else if (currentProject != null) {
                                OutlinedButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            projectRepository.updateProject(
                                                currentProject.copy(
                                                    githubRepo = repo.repoFullName,
                                                    githubBranch = repo.defaultBranch
                                                )
                                            )
                                            statusMessage = "تم ربط المستودع '${repo.repoFullName}' بالمشروع '${currentProject.name}'"
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("ربط بالمشروع", fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = repo.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = "الفرع: ${repo.defaultBranch}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Surface(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = "CI: ${repo.ciStatus}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (repo.ciStatus == "SUCCESS") MetraEmerald else MetraAmber,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            if (isLinkedToCurrent) {
                                Button(
                                    onClick = { showCommitDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Commit كود", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { showPrDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = MetraIndigo),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("إنشاء PR", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog: Connect GitHub
    if (showConnectDialog) {
        var username by remember { mutableStateOf("") }
        var token by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showConnectDialog = false },
            title = { Text("ربط حساب GitHub جديد") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "أدخل اسم المستخدم والرمز الشخصي (PAT). سيتم تشفير الرمز بواسطة AES-GCM ولن يظهر في السجلات.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("اسم المستخدم (GitHub Username)") },
                        modifier = Modifier.fillMaxWidth().testTag("github_username_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = token,
                        onValueChange = { token = it },
                        label = { Text("رمز الوصول (Personal Access Token)") },
                        modifier = Modifier.fillMaxWidth().testTag("github_token_input"),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (username.isNotBlank() && token.isNotBlank()) {
                            coroutineScope.launch {
                                integrationsRepository.connectGitHubAccount(username, token)
                                showConnectDialog = false
                                statusMessage = "تم ربط الحساب @$username بنجاح"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                    enabled = username.isNotBlank() && token.isNotBlank(),
                    modifier = Modifier.testTag("submit_github_btn")
                ) {
                    Text("ربط وتأكيد")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConnectDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Dialog: Commit to Branch
    if (showCommitDialog && currentProject != null) {
        var commitMsg by remember { mutableStateOf("feat: update project components via Metra AI") }
        var branchName by remember { mutableStateOf("feature/ai-update") }

        AlertDialog(
            onDismissRequest = { showCommitDialog = false },
            title = { Text("إنشاء Commit ورفع التعديلات") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("الرفع محمي: سيتم فحص الملفات لمنع رفع أي أسرار، وسيتم توجيه الرفع إلى فرع مخصص (تجنب الدفع المباشر لـ main).", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    OutlinedTextField(
                        value = branchName,
                        onValueChange = { branchName = it },
                        label = { Text("اسم فرع العمل (Branch)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = commitMsg,
                        onValueChange = { commitMsg = it },
                        label = { Text("رسالة الالتزام (Commit Message)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val sha = integrationsRepository.createCommit(
                                projectId = currentProject.id,
                                repoFullName = currentProject.githubRepo,
                                branch = branchName,
                                commitMessage = commitMsg,
                                changedFiles = listOf("src/Main.kt", "README.md")
                            )
                            showCommitDialog = false
                            statusMessage = "تم إنشاء Commit برقم $sha ورفعه إلى الفرع $branchName بنجاح!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White)
                ) {
                    Text("تنفيذ الالتزام")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCommitDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Dialog: Create Pull Request
    if (showPrDialog && currentProject != null) {
        var prTitle by remember { mutableStateOf("Merge feature: Metra AI autonomous updates") }
        var srcBranch by remember { mutableStateOf("feature/ai-update") }
        var tgtBranch by remember { mutableStateOf("main") }

        AlertDialog(
            onDismissRequest = { showPrDialog = false },
            title = { Text("إنشاء Pull Request للمراجعة") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("المستودع: ${currentProject.githubRepo}", color = MetraCyan, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = prTitle,
                        onValueChange = { prTitle = it },
                        label = { Text("عنوان طلب الدمج (PR Title)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = srcBranch,
                            onValueChange = { srcBranch = it },
                            label = { Text("من فرع") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = tgtBranch,
                            onValueChange = { tgtBranch = it },
                            label = { Text("إلى فرع") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val prUrl = integrationsRepository.createPullRequest(
                                projectId = currentProject.id,
                                repoFullName = currentProject.githubRepo,
                                sourceBranch = srcBranch,
                                targetBranch = tgtBranch,
                                title = prTitle
                            )
                            showPrDialog = false
                            statusMessage = "تم إنشاء طلب الدمج بنجاح: $prUrl"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraIndigo)
                ) {
                    Text("إنشاء Pull Request")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPrDialog = false }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}
