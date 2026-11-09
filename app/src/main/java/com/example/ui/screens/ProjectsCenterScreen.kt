package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import com.example.data.model.GoogleAccountEntry
import com.example.data.model.Project
import com.example.data.model.ProjectStatus
import com.example.data.model.ProjectType
import com.example.data.repository.IntegrationsRepository
import com.example.data.repository.ProjectRepository
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ProjectsCenterScreen(
    projectRepository: ProjectRepository,
    integrationsRepository: IntegrationsRepository,
    onSelectProject: (Project) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val projects by projectRepository.observeProjects().collectAsState(initial = emptyList())
    val googleAccounts by integrationsRepository.observeGoogleAccounts().collectAsState(initial = emptyList())

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }
    var showCreateDialog by remember { mutableStateOf(false) }
    var projectToEdit by remember { mutableStateOf<Project?>(null) }
    var projectToDelete by remember { mutableStateOf<Project?>(null) }
    var projectToClone by remember { mutableStateOf<Project?>(null) }
    var projectToExport by remember { mutableStateOf<Project?>(null) }

    val filteredProjects = projects.filter { project ->
        val matchesSearch = project.name.contains(searchQuery, ignoreCase = true) ||
                project.description.contains(searchQuery, ignoreCase = true) ||
                project.language.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "ACTIVE" -> project.status != ProjectStatus.ARCHIVED.name
            "ARCHIVED" -> project.status == ProjectStatus.ARCHIVED.name
            "IN_PROGRESS" -> project.status == ProjectStatus.IN_PROGRESS.name
            "COMPLETED" -> project.status == ProjectStatus.COMPLETED.name
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Search Bar - Technical & Modern
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("project_search_field")
                    .shadow(2.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                placeholder = { Text("بحث عن مشروع بالاسم أو التقنية...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Filter Chips Row - Premium Style
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val filters = listOf(
                    "ALL" to "الكل (${projects.size})",
                    "ACTIVE" to "النشطة (${projects.count { it.status != ProjectStatus.ARCHIVED.name }})",
                    "ARCHIVED" to "المؤرشفة (${projects.count { it.status == ProjectStatus.ARCHIVED.name }})",
                    "IN_PROGRESS" to "قيد التطوير",
                    "COMPLETED" to "المكتملة"
                )
                items(filters) { (key, label) ->
                    val isSelected = selectedFilter == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = key },
                        label = { 
                            Text(
                                text = label, 
                                fontSize = 12.sp, 
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
                            selectedBorderColor = MaterialTheme.colorScheme.primary,
                            borderWidth = 1.dp
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Projects List
            if (filteredProjects.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (searchQuery.isNotBlank()) "لم يتم العثور على مشاريع مطابقة" else "لا توجد مشاريع مضافة حتى الآن",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "اضغط على زر (+) لإنشاء مشروعك الأول وربطه بـ GitHub وDrive",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    items(filteredProjects, key = { it.id }) { project ->
                        ProjectCard(
                            project = project,
                            onOpenDashboard = { onSelectProject(project) },
                            onEdit = { projectToEdit = project },
                            onArchive = {
                                coroutineScope.launch {
                                    val isArchived = project.status == ProjectStatus.ARCHIVED.name
                                    projectRepository.toggleArchiveProject(project.id, !isArchived)
                                }
                            },
                            onDelete = { projectToDelete = project },
                            onClone = { projectToClone = project },
                            onExport = { projectToExport = project }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }

        // Floating Action Button - Modern Gradient Style
        MetraGradientButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .testTag("create_project_fab"),
            gradient = MetraCyanIndigo,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Project",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (showCreateDialog) {
        ProjectFormDialog(
            title = "إنشاء مشروع جديد",
            project = null,
            googleAccounts = googleAccounts,
            onDismiss = { showCreateDialog = false },
            onConfirm = { newProject ->
                coroutineScope.launch {
                    projectRepository.createProject(newProject)
                    showCreateDialog = false
                }
            }
        )
    }

    projectToEdit?.let { project ->
        ProjectFormDialog(
            title = "تعديل بيانات المشروع",
            project = project,
            googleAccounts = googleAccounts,
            onDismiss = { projectToEdit = null },
            onConfirm = { updated ->
                coroutineScope.launch {
                    projectRepository.updateProject(updated)
                    projectToEdit = null
                }
            }
        )
    }

    projectToDelete?.let { project ->
        AlertDialog(
            onDismissRequest = { projectToDelete = null },
            title = { Text("تأكيد حذف المشروع صراحة", fontWeight = FontWeight.Bold) },
            text = {
                Text("هل أنت متأكد تماماً من رغبتك في حذف المشروع '${project.name}'؟\nسيتم حذف سجلات المشروع والمهام التابعة له بالكامل ولا يمكن التراجع عن هذا الإجراء.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            projectRepository.deleteProject(project.id)
                            projectToDelete = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraRose)
                ) {
                    Text("نعم، احذف المشروع نهائياً")
                }
            },
            dismissButton = {
                TextButton(onClick = { projectToDelete = null }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    projectToClone?.let { project ->
        var newCloneName by remember { mutableStateOf("${project.name} - نسخة") }
        AlertDialog(
            onDismissRequest = { projectToClone = null },
            title = { Text("نسخ إعدادات المشروع") },
            text = {
                Column {
                    Text("سيتم نسخ إعدادات المشروع، الوجهات، والتقنيات إلى مشروع جديد، مع استبعاد مفاتيح الأسرار والـ Tokens لضمان الأمان.")
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newCloneName,
                        onValueChange = { newCloneName = it },
                        label = { Text("اسم المشروع الجديد") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            projectRepository.cloneProjectWithoutSecrets(project, newCloneName)
                            projectToClone = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraCyan, contentColor = Color.Black)
                ) {
                    Text("تأكيد النسخ")
                }
            },
            dismissButton = {
                TextButton(onClick = { projectToClone = null }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    projectToExport?.let { project ->
        val exportJson = remember(project) { projectRepository.exportProjectNonSecretConfig(project) }
        AlertDialog(
            onDismissRequest = { projectToExport = null },
            title = { Text("تصدير إعدادات المشروع (غير السرية)") },
            text = {
                Column {
                    Text("تم استخراج التكوين الآمن للمشروع دون أي بيانات اعتماد أو أسرار:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        LazyColumn(modifier = Modifier.padding(10.dp)) {
                            item {
                                Text(
                                    text = exportJson,
                                    fontSize = 11.sp,
                                    color = MetraCyan,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { projectToExport = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White)
                ) { Text("إغلاق") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun ProjectCard(
    project: Project,
    onOpenDashboard: () -> Unit,
    onEdit: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit,
    onClone: () -> Unit,
    onExport: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    MetraCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenDashboard() }
            .testTag("project_card_${project.name}"),
        backgroundColor = MaterialTheme.colorScheme.surface,
        borderColor = if (project.status == ProjectStatus.IN_PROGRESS.name) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
        elevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                (when (project.projectType) {
                                    ProjectType.AI_SYSTEM.name -> MetraIndigoPurple
                                    ProjectType.MOBILE.name -> MetraCyanIndigo
                                    else -> MetraEmeraldCyan
                                }).map { it.copy(alpha = 0.15f) }
                            )
                        )
                        .border(
                            1.dp, 
                            when (project.projectType) {
                                ProjectType.AI_SYSTEM.name -> MetraIndigo
                                ProjectType.MOBILE.name -> MetraCyan
                                else -> MetraEmerald
                            }.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (project.projectType) {
                            ProjectType.AI_SYSTEM.name -> Icons.Default.Psychology
                            ProjectType.MOBILE.name -> Icons.Default.Hub
                            else -> Icons.Default.Code
                        },
                        contentDescription = null,
                        tint = when (project.projectType) {
                            ProjectType.AI_SYSTEM.name -> MetraIndigo
                            ProjectType.MOBILE.name -> MetraCyan
                            else -> MetraEmerald
                        },
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = project.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        StatusBadge(status = project.status)
                    }
                    Text(
                        text = "${project.language} • ${project.framework}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    ) {
                        DropdownMenuItem(
                            text = { Text("فتح لوحة التحكم") },
                            onClick = { showMenu = false; onOpenDashboard() },
                            leadingIcon = { Icon(Icons.Default.Hub, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                        )
                        DropdownMenuItem(
                            text = { Text("تعديل المعلومات") },
                            onClick = { showMenu = false; onEdit() },
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = MetraAmber) }
                        )
                        DropdownMenuItem(
                            text = { Text(if (project.status == ProjectStatus.ARCHIVED.name) "استعادة من الأرشيف" else "أرشفة المشروع") },
                            onClick = { showMenu = false; onArchive() },
                            leadingIcon = { Icon(if (project.status == ProjectStatus.ARCHIVED.name) Icons.Default.Unarchive else Icons.Default.Archive, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) }
                        )
                        DropdownMenuItem(
                            text = { Text("نسخ الإعدادات") },
                            onClick = { showMenu = false; onClone() },
                            leadingIcon = { Icon(Icons.Default.ContentCopy, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary) }
                        )
                        DropdownMenuItem(
                            text = { Text("تصدير التكوين") },
                            onClick = { showMenu = false; onExport() },
                            leadingIcon = { Icon(Icons.Default.Share, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                        )
                        DropdownMenuItem(
                            text = { Text("حذف المشروع", color = MaterialTheme.colorScheme.error) },
                            onClick = { showMenu = false; onDelete() },
                            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                        )
                    }
                }
            }

            if (project.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (project.githubRepo.isNotBlank()) SmallTag(icon = Icons.Default.Code, text = project.githubRepo, color = MetraCyan)
                if (project.googleDriveAccountEmail.isNotBlank()) SmallTag(icon = Icons.Default.Cloud, text = project.googleDriveAccountEmail, color = MetraEmerald)
                SmallTag(icon = Icons.Default.Psychology, text = project.defaultAiModel, color = MetraIndigo)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "معدل الإنجاز: ${project.progress}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (project.progress > 80) MetraEmerald else MetraCyan,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${project.completedTasksCount} / ${project.openTasksCount + project.completedTasksCount} مهام",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { project.progress / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = if (project.progress > 80) MetraEmerald else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (bg, fg, label) = when (status) {
        ProjectStatus.ACTIVE.name -> Triple(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), MaterialTheme.colorScheme.primary, "نشط")
        ProjectStatus.IN_PROGRESS.name -> Triple(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), MaterialTheme.colorScheme.secondary, "قيد التطوير")
        ProjectStatus.COMPLETED.name -> Triple(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f), MaterialTheme.colorScheme.tertiary, "مكتمل")
        ProjectStatus.ARCHIVED.name -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, "مؤرشف")
        else -> Triple(MetraAmber.copy(alpha = 0.15f), MetraAmber, "تخطيط")
    }

    Surface(color = bg, shape = RoundedCornerShape(6.dp)) {
        Text(text = label, color = fg, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
    }
}

@Composable
fun SmallTag(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.08f),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, style = MaterialTheme.typography.labelSmall, color = color.copy(alpha = 0.9f), maxLines = 1, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProjectFormDialog(
    title: String,
    project: Project?,
    googleAccounts: List<GoogleAccountEntry>,
    onDismiss: () -> Unit,
    onConfirm: (Project) -> Unit
) {
    var name by remember { mutableStateOf(project?.name ?: "") }
    var description by remember { mutableStateOf(project?.description ?: "") }
    var projectType by remember { mutableStateOf(project?.projectType ?: ProjectType.AI_SYSTEM.name) }
    var language by remember { mutableStateOf(project?.language ?: "Kotlin / Python") }
    var framework by remember { mutableStateOf(project?.framework ?: "Jetpack Compose") }
    var githubRepo by remember { mutableStateOf(project?.githubRepo ?: "") }
    var githubBranch by remember { mutableStateOf(project?.githubBranch ?: "main") }
    var selectedGoogleEmail by remember { mutableStateOf(project?.googleDriveAccountEmail ?: (googleAccounts.firstOrNull()?.email ?: "")) }
    var aiProvider by remember { mutableStateOf(project?.defaultAiProvider ?: "Gemini") }
    var aiModel by remember { mutableStateOf(project?.defaultAiModel ?: "gemini-1.5-pro") }
    var notes by remember { mutableStateOf(project?.notes ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("اسم المشروع *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                }
                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("وصف المشروع") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                }
                item {
                    Text("نوع المشروع:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(ProjectType.values()) { type ->
                            FilterChip(
                                selected = projectType == type.name,
                                onClick = { projectType = type.name },
                                label = { Text(type.displayName, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = language,
                            onValueChange = { language = it },
                            label = { Text("لغة البرمجة") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                        OutlinedTextField(
                            value = framework,
                            onValueChange = { framework = it },
                            label = { Text("إطار العمل") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        value = githubRepo,
                        onValueChange = { githubRepo = it },
                        label = { Text("مستودع GitHub") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                }
                item {
                    OutlinedTextField(
                        value = selectedGoogleEmail,
                        onValueChange = { selectedGoogleEmail = it },
                        label = { Text("حساب Drive") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = aiProvider,
                            onValueChange = { aiProvider = it },
                            label = { Text("الذكاء") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                        OutlinedTextField(
                            value = aiModel,
                            onValueChange = { aiModel = it },
                            label = { Text("النموذج") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("ملاحظات") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm((project ?: Project()).copy(name = name, description = description, projectType = projectType, language = language, framework = framework, githubRepo = githubRepo, googleDriveAccountEmail = selectedGoogleEmail, defaultAiProvider = aiProvider, defaultAiModel = aiModel, notes = notes))
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White)
            ) { Text("حفظ") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("إلغاء") } },
        containerColor = MaterialTheme.colorScheme.surface
    )
}
