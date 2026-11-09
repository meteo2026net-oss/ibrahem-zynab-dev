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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.data.model.FileIntelligenceResult
import com.example.data.model.FileSource
import com.example.data.model.Project
import com.example.data.repository.AutomationRepository
import com.example.data.repository.ProjectRepository
import com.example.data.repository.StorageRepository
import com.example.ui.theme.metraGradientBackground
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import kotlinx.coroutines.launch

@Composable
fun FileIntelligenceScreen(
    currentProject: Project?,
    automationRepository: AutomationRepository,
    projectRepository: ProjectRepository,
    storageRepository: StorageRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val allProjects by projectRepository.observeProjects().collectAsState(initial = emptyList())
    val storedFiles by storageRepository.observeFiles("").collectAsState(initial = emptyList())

    var testFileName by remember { mutableStateOf("weather_telemetry_schematic.pdf") }
    var intelligenceResult by remember { mutableStateOf<FileIntelligenceResult?>(null) }
    var saveStatus by remember { mutableStateOf<String?>(null) }

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
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MetraCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FindInPage, contentDescription = null, tint = MetraCyan, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "نظام ذكاء وتحليل الملفات (File Intelligence)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "المطور التقني ابراهيم زينب • 0934884157",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraCyan
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Input & Test Action
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "اختبار تحليل وتصنيف ملف ذكي",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "يقوم النظام باستخراج الميتاداتا، واكتشاف التكرار، وتصنيف الوجهة المناسبة، وإنشاء وصف تلقائي.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = testFileName,
                    onValueChange = { testFileName = it },
                    label = { Text("اسم الملف المراد فحصه") },
                    modifier = Modifier.fillMaxWidth().testTag("intel_filename_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(
                        selected = testFileName == "weather_telemetry_schematic.pdf",
                        onClick = { testFileName = "weather_telemetry_schematic.pdf" },
                        label = { Text("مستند PDF", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = testFileName == "circuit_board_architecture.jpg",
                        onClick = { testFileName = "circuit_board_architecture.jpg" },
                        label = { Text("صورة لوحة", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = testFileName == "CorePipelineModule.kt",
                        onClick = { testFileName = "CorePipelineModule.kt" },
                        label = { Text("كود مصدري", fontSize = 11.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val mockBytes = testFileName.toByteArray()
                        val result = automationRepository.analyzeFileIntelligence(
                            fileName = testFileName,
                            fileBytes = mockBytes,
                            activeProject = currentProject,
                            allProjects = allProjects,
                            existingFiles = storedFiles
                        )
                        intelligenceResult = result
                        saveStatus = null
                    },
                    modifier = Modifier.fillMaxWidth().testTag("analyze_file_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = MetraCyan, contentColor = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("بدء التحليل واستخراج البيانات الوصفية", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (saveStatus != null) {
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
                    Text(text = saveStatus ?: "", color = MetraEmerald, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Analysis Result Card
        intelligenceResult?.let { res ->
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MetraCyan)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (res.isDuplicate) Icons.Default.Warning else Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (res.isDuplicate) MetraAmber else MetraEmerald
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (res.isDuplicate) "تنبيه: ملف مكرر تم رصده مسبقاً!" else "تقرير تحليل الملف جاهز",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            InfoRow(label = "نوع ومطابقة MIME", value = "${res.detectedCategory} (${res.mimeType})")
                            InfoRow(label = "المشروع المقترح ذكياً", value = res.aiSuggestedProjectName)
                            InfoRow(label = "الوجهة التخزينية المقترحة", value = res.aiSuggestedDestination.name)
                            InfoRow(label = "بصمة النزاهة الرقمية (SHA-256)", value = res.duplicateChecksum.take(16) + "...")

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("الوصف التلقائي من الذكاء الاصطناعي:", style = MaterialTheme.typography.labelSmall, color = MetraCyan)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = res.aiGeneratedSummary,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val targetProj = allProjects.firstOrNull { it.name == res.aiSuggestedProjectName } ?: currentProject
                                        val targetProjId = targetProj?.id ?: ""
                                        storageRepository.saveFile(
                                            projectId = targetProjId,
                                            fileName = res.fileName,
                                            fileCategory = res.detectedCategory,
                                            destination = res.aiSuggestedDestination,
                                            source = FileSource.AI_GENERATOR,
                                            sizeBytes = res.fileSizeBytes,
                                            tags = res.suggestedTags
                                        )
                                        saveStatus = "تم اعتماد وحفظ الملف في المشروع '${res.aiSuggestedProjectName}' بالوجهة '${res.aiSuggestedDestination.name}'"
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().testTag("confirm_file_intel_save_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = MetraCyan, contentColor = Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("اعتماد الحفظ والتوجيه الذكي للمشروع", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        }
    }
}
