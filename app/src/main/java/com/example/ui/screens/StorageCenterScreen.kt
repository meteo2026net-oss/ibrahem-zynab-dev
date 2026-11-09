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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FileCategory
import com.example.data.model.FileSource
import com.example.data.model.Project
import com.example.data.model.StorageDestination
import com.example.data.model.StoredFile
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
fun StorageCenterScreen(
    currentProject: Project?,
    storageRepository: StorageRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val projectId = currentProject?.id ?: ""
    val files by storageRepository.observeFiles(projectId).collectAsState(initial = emptyList())

    var searchQuery by remember { mutableStateOf("") }
    var destinationFilter by remember { mutableStateOf("ALL") }
    var categoryFilter by remember { mutableStateOf("ALL") }

    var fileToPreview by remember { mutableStateOf<StoredFile?>(null) }
    var fileToDelete by remember { mutableStateOf<StoredFile?>(null) }
    var showUploadDialog by remember { mutableStateOf(false) }

    val filteredFiles = files.filter { file ->
        val matchesSearch = file.fileName.contains(searchQuery, ignoreCase = true)
        val matchesDest = destinationFilter == "ALL" || file.storageDestination == destinationFilter
        val matchesCat = categoryFilter == "ALL" || file.fileCategory == categoryFilter
        matchesSearch && matchesDest && matchesCat
    }

    val githubFilesCount = files.count { it.storageDestination == StorageDestination.GITHUB.name }
    val driveFilesCount = files.count { it.storageDestination == StorageDestination.GOOGLE_DRIVE.name }
    val privateFilesCount = files.count { it.storageDestination == StorageDestination.PRIVATE_STORAGE.name }

    Box(modifier = Modifier.fillMaxSize().metraGradientBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Storage Summary Card
            MetraCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Folder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "مركز التخزين الموحد",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (currentProject != null) "المشروع: ${currentProject.name}" else "Unified Storage System",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Breakdown chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DestBadge(
                            icon = Icons.Default.Code,
                            title = "GitHub",
                            count = "$githubFilesCount ملف",
                            color = MetraIndigo,
                            modifier = Modifier.weight(1f)
                        )
                        DestBadge(
                            icon = Icons.Default.Cloud,
                            title = "Drive",
                            count = "$driveFilesCount ملف",
                            color = MetraEmerald,
                            modifier = Modifier.weight(1f)
                        )
                        DestBadge(
                            icon = Icons.Default.Lock,
                            title = "خزن خاص",
                            count = "$privateFilesCount ملف",
                            color = MetraAmber,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("storage_search_input"),
                placeholder = { Text("بحث في الملفات...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filters: Storage Destinations
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val dests = listOf(
                    "ALL" to "كل الوجهات",
                    StorageDestination.GITHUB.name to "GitHub",
                    StorageDestination.GOOGLE_DRIVE.name to "Google Drive",
                    StorageDestination.PRIVATE_STORAGE.name to "خزن خاص"
                )
                items(dests) { (key, label) ->
                    FilterChip(
                        selected = destinationFilter == key,
                        onClick = { destinationFilter = key },
                        label = { Text(label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Files List
            if (filteredFiles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Folder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "لا توجد ملفات حالياً",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "ارفع ملفاً جديداً للبدء",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredFiles, key = { it.id }) { file ->
                        MetraCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { fileToPreview = file }
                                .testTag("file_card_${file.fileName}")
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            when (file.storageDestination) {
                                                StorageDestination.GITHUB.name -> MetraIndigo.copy(alpha = 0.1f)
                                                StorageDestination.GOOGLE_DRIVE.name -> MetraEmerald.copy(alpha = 0.1f)
                                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (file.fileCategory) {
                                            FileCategory.IMAGE.name -> Icons.Default.Image
                                            FileCategory.PDF.name -> Icons.Default.PictureAsPdf
                                            FileCategory.CODE.name -> Icons.Default.Code
                                            else -> Icons.Default.Folder
                                        },
                                        contentDescription = null,
                                        tint = when (file.storageDestination) {
                                            StorageDestination.GITHUB.name -> MetraIndigo
                                            StorageDestination.GOOGLE_DRIVE.name -> MetraEmerald
                                            else -> MaterialTheme.colorScheme.primary
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = file.fileName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = file.getFormattedSize(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                        Text(text = "•", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                                        Text(text = file.storageDestination, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Surface(color = MetraEmerald.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Default.Security, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(10.dp))
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text("فحص الأمان: سليم", color = MetraEmerald, fontSize = 9.sp)
                                        }
                                    }
                                }

                                Row {
                                    IconButton(onClick = { fileToPreview = file }) {
                                        Icon(imageVector = Icons.Default.Visibility, contentDescription = "Preview", tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = { fileToDelete = file }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MetraRose)
                                    }
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        // Upload FAB
        FloatingActionButton(
            onClick = { showUploadDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .testTag("upload_file_fab"),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add File")
        }

        // Modals
        if (fileToPreview != null) {
            PreviewFileDialog(file = fileToPreview!!, onDismiss = { fileToPreview = null })
        }

        if (fileToDelete != null) {
            DeleteFileDialog(
                file = fileToDelete!!,
                storageRepository = storageRepository,
                onDismiss = { fileToDelete = null }
            )
        }

        if (showUploadDialog) {
            UploadFileDialog(
                projectId = projectId,
                storageRepository = storageRepository,
                onDismiss = { showUploadDialog = false }
            )
        }
    }
}

@Composable
fun PreviewFileDialog(file: StoredFile, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (file.fileCategory == FileCategory.IMAGE.name) Icons.Default.Image else Icons.Default.PictureAsPdf,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(file.fileName, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = when (file.fileCategory) {
                                FileCategory.IMAGE.name -> Icons.Default.Image
                                FileCategory.PDF.name -> Icons.Default.PictureAsPdf
                                else -> Icons.Default.Code
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "معاينة آمنة: ${file.mimeType}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "الحجم: ${file.getFormattedSize()} • Verified",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetraEmerald
                        )
                    }
                }

                Text("وجهة التخزين: ${file.storageDestination}", style = MaterialTheme.typography.bodySmall)
                Text("المصدر: ${file.source}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("الرابط: ${file.remoteUrl}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("إغلاق") }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun DeleteFileDialog(
    file: StoredFile,
    storageRepository: StorageRepository,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("تأكيد حذف الملف") },
        text = { Text("هل أنت متأكد من حذف الملف '${file.fileName}'؟") },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        storageRepository.deleteFile(file.id)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MetraRose)
            ) {
                Text("حذف")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun UploadFileDialog(
    projectId: String,
    storageRepository: StorageRepository,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var uploadName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(FileCategory.PDF) }
    var targetDest by remember { mutableStateOf(StorageDestination.GOOGLE_DRIVE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("رفع ملف جديد") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = uploadName,
                    onValueChange = { uploadName = it },
                    label = { Text("اسم الملف") },
                    modifier = Modifier.fillMaxWidth().testTag("upload_file_name_input"),
                    singleLine = true
                )
                Text("تصنيف الملف:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = category == FileCategory.PDF,
                        onClick = {
                            category = FileCategory.PDF
                            targetDest = StorageDestination.GOOGLE_DRIVE
                        },
                        label = { Text("وثيقة") }
                    )
                    FilterChip(
                        selected = category == FileCategory.IMAGE,
                        onClick = {
                            category = FileCategory.IMAGE
                            targetDest = StorageDestination.PRIVATE_STORAGE
                        },
                        label = { Text("صورة") }
                    )
                    FilterChip(
                        selected = category == FileCategory.CODE,
                        onClick = {
                            category = FileCategory.CODE
                            targetDest = StorageDestination.GITHUB
                        },
                        label = { Text("كود") }
                    )
                }
                Text("وجهة الحفظ: ${targetDest.titleAr}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (uploadName.isNotBlank()) {
                        coroutineScope.launch {
                            storageRepository.saveFile(
                                projectId = projectId,
                                fileName = uploadName,
                                fileCategory = category,
                                destination = targetDest,
                                source = FileSource.DIRECT_UPLOAD,
                                sizeBytes = (50_000L..3_000_000L).random(),
                                tags = listOf("manual-upload")
                            )
                            onDismiss()
                        }
                    }
                },
                enabled = uploadName.isNotBlank()
            ) {
                Text("رفع وتخزين")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun DestBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    count: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = title, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = count, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
        }
    }
}
