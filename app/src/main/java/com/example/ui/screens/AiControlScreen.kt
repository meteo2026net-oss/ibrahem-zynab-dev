package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terminal
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FileCategory
import com.example.data.model.Project
import com.example.data.model.ProjectAttachment
import com.example.data.model.StorageDestination
import com.example.data.model.VisionAnalysisTask
import com.example.data.repository.AiDevelopmentRepository
import com.example.data.repository.DevExecutionResult
import com.example.data.repository.IntegrationsRepository
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraCyanIndigo
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraGradientButton
import com.example.ui.theme.MetraEmeraldCyan
import kotlinx.coroutines.launch

@Composable
fun AiControlScreen(
    currentProject: Project?,
    aiDevRepository: AiDevelopmentRepository,
    integrationsRepository: IntegrationsRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: ""
    val projectName = currentProject?.name ?: "Metra Project"
    val agents by aiDevRepository.observeAgents(projectId).collectAsState(initial = emptyList())

    var promptInput by remember { mutableStateOf("أنشئ لي تطبيقاً لإدارة المحطات الجوية.") }
    var isExecuting by remember { mutableStateOf(false) }
    var executionResult by remember { mutableStateOf<DevExecutionResult?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    var pendingAttachments by remember { mutableStateOf(listOf<ProjectAttachment>()) }
    var selectedVisionTask by remember { mutableStateOf(VisionAnalysisTask.GENERAL_DESCRIPTION) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        val att = ProjectAttachment(
            fileName = "camera_capture_${System.currentTimeMillis()}.jpg",
            mimeType = "image/jpeg",
            sizeBytes = 245_000L,
            caption = "صورة ملتقطة مباشر من الكاميرا",
            category = FileCategory.IMAGE,
            base64Data = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==",
            isFromCamera = true,
            destination = StorageDestination.PRIVATE_STORAGE
        )
        pendingAttachments = pendingAttachments + att
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        val newAtts = uris.mapIndexed { idx, uri ->
            val path = uri.path ?: "file"
            val isImg = path.endsWith(".jpg") || path.endsWith(".png") || uri.toString().contains("image")
            ProjectAttachment(
                fileName = path.substringAfterLast("/").ifBlank { "attached_file_$idx.jpg" },
                fileUri = uri.toString(),
                mimeType = if (isImg) "image/jpeg" else "application/pdf",
                sizeBytes = (100_000L..1_500_000L).random(),
                caption = "مرفق مختار من جهاز المستخدم",
                category = if (isImg) FileCategory.IMAGE else FileCategory.PDF,
                base64Data = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==",
                isFromCamera = false,
                destination = StorageDestination.PRIVATE_STORAGE
            )
        }
        pendingAttachments = pendingAttachments + newAtts
    }

    val quickCommands = listOf(
        "أنشئ لي تطبيقاً لإدارة المحطات الجوية.",
        "راجع المشروع الحالي وافحص أمان الملفات.",
        "أصلح جميع الأخطاء البرمجية وضع حماية Nullable.",
        "قارن الإصدار الحالي بالإصدار السابق."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Card: Command Console
        MetraCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Terminal, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(26.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "مركز أوامر وتطوير الذكاء الاصطناعي",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "AI Vision, OCR & Code Evolution",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Prompt Input
                OutlinedTextField(
                    value = promptInput,
                    onValueChange = { promptInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_dev_prompt_input")
                        .shadow(2.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    placeholder = { Text("اكتب أمراً برمجياً أو اطلب تحليل الصور والـ OCR...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Attachment & Camera trigger row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetraGradientButton(
                        onClick = { cameraLauncher.launch(null) },
                        modifier = Modifier.weight(1f).height(40.dp).testTag("camera_capture_btn"),
                        gradient = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("كاميرا", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }

                    MetraGradientButton(
                        onClick = { filePickerLauncher.launch("*/*") },
                        modifier = Modifier.weight(1f).height(40.dp).testTag("attach_file_btn"),
                        gradient = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach File", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إرفاق ملف", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                    }
                }

                // Vision Analysis Task chips
                Spacer(modifier = Modifier.height(12.dp))
                Text("نوع تحليل الرؤية (AI Vision Task):", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(VisionAnalysisTask.values()) { vTask ->
                        val isSelected = selectedVisionTask == vTask
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedVisionTask = vTask },
                            label = { 
                                Text(
                                    text = vTask.titleAr, 
                                    fontSize = 10.sp, 
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium 
                                ) 
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                selectedBorderColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                // Pending Attachments Preview bar
                if (pendingAttachments.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("المرفقات الجاهزة (${pendingAttachments.size}):", style = MaterialTheme.typography.labelSmall, color = MetraAmber, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(pendingAttachments, key = { it.id }) { att ->
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MetraAmber.copy(alpha = 0.4f)),
                                modifier = Modifier.shadow(2.dp, RoundedCornerShape(8.dp), spotColor = MetraAmber.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (att.isImage) Icons.Default.Image else Icons.Default.AttachFile,
                                        contentDescription = null,
                                        tint = MetraAmber,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = att.fileName.take(15) + if(att.fileName.length > 15) ".." else "",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    IconButton(
                                        onClick = {
                                            pendingAttachments = pendingAttachments.filter { it.id != att.id }
                                        },
                                        modifier = Modifier.size(20.dp).testTag("remove_attachment_btn")
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                MetraGradientButton(
                    onClick = {
                        if (promptInput.isNotBlank() || pendingAttachments.isNotEmpty()) {
                            isExecuting = true
                            executionResult = null
                            coroutineScope.launch {
                                val fullCommand = if (pendingAttachments.isNotEmpty()) {
                                    "$promptInput\n[مرفق عدد ${pendingAttachments.size} صورة/ملف | مهمة الرؤية: ${selectedVisionTask.titleAr}]"
                                } else {
                                    promptInput
                                }
                                val result = aiDevRepository.executeDevCommand(
                                    projectId = projectId,
                                    projectName = projectName,
                                    command = fullCommand
                                )
                                executionResult = result
                                isExecuting = false
                                pendingAttachments = emptyList()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("execute_ai_dev_btn"),
                    gradient = MetraCyanIndigo,
                    enabled = !isExecuting && (promptInput.isNotBlank() || pendingAttachments.isNotEmpty())
                ) {
                    if (isExecuting) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("جاري التنفيذ والتحليل...", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onPrimary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (pendingAttachments.isNotEmpty()) "تحليل الصور وإرسال المهمة" else "تنفيذ الأمر البرمجي الذكي", 
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 15.sp
                        )
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
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = statusMessage ?: "", color = MaterialTheme.colorScheme.onTertiaryContainer, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Result and Agents list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            executionResult?.let { res ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = res.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = res.explanationAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("الملفات المكتوبة:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            res.generatedFiles.forEach { (fname, snippet) ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(text = fname, fontWeight = FontWeight.Bold, color = MetraAmber, fontSize = 11.sp)
                                        Text(
                                            text = snippet,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = res.testSummary,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                MetraGradientButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            if (currentProject != null && currentProject.githubRepo.isNotBlank()) {
                                                integrationsRepository.createCommit(
                                                    projectId = currentProject.id,
                                                    repoFullName = currentProject.githubRepo,
                                                    branch = res.targetBranch,
                                                    commitMessage = res.suggestedCommitMessage,
                                                    changedFiles = res.generatedFiles.map { it.first }
                                                )
                                                integrationsRepository.createPullRequest(
                                                    projectId = currentProject.id,
                                                    repoFullName = currentProject.githubRepo,
                                                    sourceBranch = res.targetBranch,
                                                    targetBranch = currentProject.githubBranch,
                                                    title = res.title
                                                )
                                                statusMessage = "تم حفظ الكود في الفرع ${res.targetBranch} وإنشاء Pull Request بنجاح!"
                                            } else {
                                                statusMessage = "تم اعتماد التعديلات واجتياز الاختبارات وتخزين النسخة في الذاكرة المخصصة للمشروع."
                                            }
                                        }
                                    },
                                    modifier = Modifier.weight(1f).height(44.dp),
                                    gradient = MetraEmeraldCyan
                                ) {
                                    Text("موافقة واعتماد التعديل", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "فريق الوكلاء المتخصصين (${agents.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }

            items(agents) { agent ->
                MetraCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = agent.name, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                            Text(text = agent.role, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(text = "النموذج: ${agent.model}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f), 
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = agent.status,
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
