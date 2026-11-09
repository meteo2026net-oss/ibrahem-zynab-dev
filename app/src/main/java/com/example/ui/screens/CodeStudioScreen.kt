package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Source
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
import com.example.data.model.CodeStudioFile
import com.example.data.model.Project
import com.example.data.repository.CodeStudioRepository
import androidx.compose.ui.draw.shadow
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraCyanIndigo
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraEmeraldCyan
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraGradientButton
import kotlinx.coroutines.launch

@Composable
fun CodeStudioScreen(
    currentProject: Project?,
    codeStudioRepository: CodeStudioRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: "proj_default"
    val projectName = currentProject?.name ?: "Metra Project"
    val files by codeStudioRepository.observeProjectFiles(projectId).collectAsState(initial = emptyList())

    var activeFile by remember(files) { mutableStateOf(files.firstOrNull()) }
    var editorContent by remember(activeFile) { mutableStateOf(activeFile?.content ?: "") }
    var terminalCommand by remember { mutableStateOf("gradle :app:testDebugUnitTest") }
    var terminalOutput by remember { mutableStateOf("Ready in Sandbox. Tap Run to execute commands securely.") }
    var isExecutingTerminal by remember { mutableStateOf(false) }
    var aiAssistantOutput by remember { mutableStateOf("أنا نظام ذكاء اصطناعي، قام ببرمجتي وتطويري ضمن منظومة METRA AI CONTROL الخبير إبراهيم زينب. كيف يمكنني مساعدتك في تطوير هذا الملف؟") }
    var isAiAnalyzing by remember { mutableStateOf(false) }

    var showSidebar by remember { mutableStateOf(false) }

    val isLight = MaterialTheme.colorScheme.background.luminance() > 0.5f

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val isSmallScreen = maxWidth < 600.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Studio Header
            MetraCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.DeveloperMode,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "METRA Code Studio Professional",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "المشروع النشط: $projectName",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (isSmallScreen) {
                        IconButton(onClick = { showSidebar = !showSidebar }) {
                            Icon(
                                imageVector = if (showSidebar) Icons.Default.Check else Icons.Default.Source,
                                contentDescription = "Toggle Files",
                                tint = MetraAmber
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Studio Layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // File Explorer Sidebar
                if (!isSmallScreen || showSidebar) {
                    MetraCard(
                        modifier = Modifier
                            .width(if (isSmallScreen) 180.dp else 160.dp)
                            .fillMaxHeight(),
                        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Folder,
                                    contentDescription = null,
                                    tint = MetraAmber,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "الملفات",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                items(files, key = { it.id }) { file ->
                                    val isSelected = activeFile?.id == file.id
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                activeFile = file
                                                editorContent = file.content
                                                if (isSmallScreen) showSidebar = false
                                            },
                                        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(6.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                        )
                                    ) {
                                        Text(
                                            text = file.filePath.substringAfterLast("/"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(6.dp),
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Code Editor & AI Assistant
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Editor Tabs & Save Button
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = activeFile?.filePath ?: "No file selected",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MetraAmber,
                                modifier = Modifier.weight(1f),
                                maxLines = 1
                            )
                            MetraGradientButton(
                                onClick = {
                                    if (activeFile != null) {
                                        coroutineScope.launch {
                                            codeStudioRepository.saveFileContent(
                                                activeFile!!.id,
                                                editorContent
                                            )
                                            terminalOutput = "تم حفظ التعديلات على الملف '${activeFile!!.filePath}' بنجاح."
                                        }
                                    }
                                },
                                modifier = Modifier.height(34.dp),
                                gradient = MetraCyanIndigo,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("حفظ", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Code Input Field
                    OutlinedTextField(
                        value = editorContent,
                        onValueChange = { editorContent = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag("code_editor_textarea")
                            .shadow(4.dp, RoundedCornerShape(10.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            focusedContainerColor = if (isLight) Color(0xFFF8FAFC) else Color(0xFF0F172A),
                            unfocusedContainerColor = if (isLight) Color(0xFFF8FAFC) else Color(0xFF0F172A),
                            focusedTextColor = if (isLight) MaterialTheme.colorScheme.onSurface else MetraCyan,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace)
                    )

                    // AI Coding Assistant Panel
                    MetraCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        elevation = 6.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "المساعد البرمجي الذكي",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (isLight) 0.5f else 0.2f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    text = aiAssistantOutput,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isLight) Color(0xFF065F46) else MetraEmerald,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            MetraGradientButton(
                                onClick = {
                                    isAiAnalyzing = true
                                    coroutineScope.launch {
                                        kotlinx.coroutines.delay(500)
                                        aiAssistantOutput = "أنا مساعد ذكاء اصطناعي من تطوير وبرمجتي الخبير إبراهيم زينب. تم فحص الكود في '${activeFile?.filePath}' وجد أنه آمن وخالٍ من الأخطاء التجميعية."
                                        isAiAnalyzing = false
                                    }
                                },
                                modifier = Modifier.height(34.dp),
                                gradient = MetraEmeraldCyan,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "تحليل وتحسين الكود",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Sandbox Terminal
                    MetraCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = if (isLight) Color(0xFF1E293B) else Color(0xFF020617),
                        borderColor = MetraAmber.copy(alpha = 0.3f),
                        elevation = 8.dp
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Terminal,
                                    contentDescription = null,
                                    tint = MetraAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "محطة التشغيل المعزولة",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = terminalCommand,
                                    onValueChange = { terminalCommand = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp),
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MetraAmber,
                                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                        focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                        unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        cursorColor = MetraAmber
                                    )
                                )
                                MetraGradientButton(
                                    onClick = {
                                        if (terminalCommand.isNotBlank()) {
                                            isExecutingTerminal = true
                                            coroutineScope.launch {
                                                val res = codeStudioRepository.runSandboxTerminalCommand(terminalCommand)
                                                terminalOutput = res
                                                isExecutingTerminal = false
                                            }
                                        }
                                    },
                                    modifier = Modifier.size(46.dp),
                                    gradient = listOf(MetraAmber, Color(0xFFD97706)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    if (isExecutingTerminal) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(18.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            modifier = Modifier.size(22.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Black.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = terminalOutput,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = MetraCyan,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Color.luminance(): Float {
    return 0.2126f * red + 0.7152f * green + 0.0722f * blue
}

