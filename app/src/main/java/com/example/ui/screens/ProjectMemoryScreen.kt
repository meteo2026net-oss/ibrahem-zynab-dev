package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Project
import com.example.data.model.ProjectMemory
import com.example.data.repository.ProjectMemoryRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectMemoryScreen(
    currentProject: Project?,
    repository: ProjectMemoryRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val memories by (if (currentProject != null) repository.getMemoriesForProject(currentProject.id) 
                   else kotlinx.coroutines.flow.flowOf(emptyList())).collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ذاكرة المشروع الذكية", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { showAddDialog = true }, enabled = currentProject != null) {
                        Icon(Icons.Default.Add, contentDescription = "إضافة ذاكرة")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (currentProject == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("يرجى اختيار مشروع لتفعيل الذاكرة السياقية", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Search Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("البحث السيمانتيكي (Semantic Search)", fontWeight = FontWeight.Bold, color = MetraCyan)
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("ماذا تريد أن يتذكر المساعد؟ (مثلاً: أسلوب التشفير)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    searchResult = repository.findRelevantContext(currentProject.id, searchQuery)
                                }
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("استرجاع السياق", color = Color.White)
                        }
                        
                        if (searchResult.isNotBlank()) {
                            Surface(
                                color = MetraCyan.copy(alpha = 0.05f),
                                border = BorderStroke(1.dp, MetraCyan.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    searchResult,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Text("الذاكرة المسجلة لهذا المشروع:", fontWeight = FontWeight.Bold, color = MetraAmber)

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(memories) { memory ->
                        MemoryItem(memory) {
                            scope.launch { repository.deleteMemory(memory.id) }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog && currentProject != null) {
        var key by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Architecture") }
        var tags by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("إضافة ذاكرة جديدة للمشروع") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = key, onValueChange = { key = it }, label = { Text("المفتاح (مثلاً: نظام التوثيق)") })
                    OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("المحتوى / القرار") })
                    OutlinedTextField(value = tags, onValueChange = { tags = it }, label = { Text("الوسوم (مفصولة بفاصلة)") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        repository.addMemory(ProjectMemory(
                            projectId = currentProject.id,
                            key = key,
                            content = content,
                            category = category,
                            tags = tags,
                            importance = 3
                        ))
                        showAddDialog = false
                    }
                }) { Text("إضافة") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun MemoryItem(memory: ProjectMemory, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge(containerColor = MetraIndigo) { Text(memory.category) }
                    Spacer(Modifier.width(8.dp))
                    Text(memory.key, fontWeight = FontWeight.Bold, color = MetraCyan)
                }
                Spacer(Modifier.height(4.dp))
                Text(memory.content, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (memory.tags.isNotBlank()) {
                    Text("#${memory.tags}", style = MaterialTheme.typography.labelSmall, color = MetraAmber)
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = MetraRose)
            }
        }
    }
}
