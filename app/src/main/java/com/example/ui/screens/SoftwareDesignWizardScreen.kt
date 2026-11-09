package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DesignTemplate
import com.example.data.model.SoftwareDesignWizardState
import com.example.data.repository.SoftwareDesignWizardRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraCyanIndigo
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraEmeraldCyan
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraGradientButton
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoftwareDesignWizardScreen(
    repository: SoftwareDesignWizardRepository,
    onNavigateToCodeStudio: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by repository.wizardState.collectAsState()
    val templates by repository.templates.collectAsState()

    var currentStep by remember { mutableStateOf(1) } // 1 to 10
    var showTemplatesDialog by remember { mutableStateOf(false) }
    var showPrdDialog by remember { mutableStateOf(false) }

    val totalSteps = 10

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "مصمم البرامج والتطبيقات الذكي",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "الخطوة $currentStep من $totalSteps: ${getStepTitle(currentStep)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showTemplatesDialog = true }) {
                        Icon(Icons.Default.FolderSpecial, contentDescription = "القوالب الجاهزة", tint = MetraAmber)
                    }
                    IconButton(onClick = { showPrdDialog = true }) {
                        Icon(Icons.Default.Description, contentDescription = "وثيقة PRD", tint = MetraCyan)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    val isVerySmall = maxWidth < 450.dp

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (isVerySmall) {
                            LinearProgressIndicator(
                                progress = { currentStep.toFloat() / totalSteps.toFloat() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = MetraCyan,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (currentStep > 1) {
                                OutlinedButton(
                                    onClick = { currentStep-- },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                    modifier = Modifier.height(40.dp)
                                ) {
                                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("السابق", fontSize = 12.sp)
                                }
                            } else {
                                Spacer(modifier = Modifier.width(1.dp))
                            }

                            if (!isVerySmall) {
                                // Progress bar indicator for larger screens
                                LinearProgressIndicator(
                                    progress = { currentStep.toFloat() / totalSteps.toFloat() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 16.dp)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = MetraCyan,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }

                            if (currentStep < totalSteps) {
                                MetraGradientButton(
                                    onClick = {
                                        scope.launch {
                                            repository.saveToCloudOrLocal()
                                        }
                                        currentStep++
                                    },
                                    modifier = Modifier.height(42.dp),
                                    gradient = MetraCyanIndigo
                                ) {
                                    Text("التالي", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                                }
                            } else {
                                MetraGradientButton(
                                    onClick = {
                                        scope.launch {
                                            repository.updateState { it.copy(isApproved = true, prdGenerated = true) }
                                            repository.saveToCloudOrLocal()
                                            Toast.makeText(context, "تم اعتماد التصميم والانتقال إلى محرك التطوير!", Toast.LENGTH_LONG).show()
                                            onNavigateToCodeStudio()
                                        }
                                    },
                                    modifier = Modifier.height(42.dp),
                                    gradient = MetraEmeraldCyan
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("اعتماد وتطوير", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .metraGradientBackground
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            item {
                when (currentStep) {
                    1 -> Step1ProjectTypes(state, repository)
                    2 -> Step2TechStack(state, repository)
                    3 -> Step3NatureAndFunctions(state, repository)
                    4 -> Step4UiAndColors(state, repository)
                    5 -> Step5ScreensAndPages(state, repository)
                    6 -> Step6AdvancedFunctions(state, repository)
                    7 -> Step7ImplementationStyle(state, repository)
                    8 -> Step8PrdPreview(state, repository)
                    9 -> Step9TemplatesLibrary(templates, repository) { currentStep = 8 }
                    10 -> Step10ApprovalAndExecution(state, repository, onNavigateToCodeStudio)
                }
            }
        }
    }
}

    // Templates Dialog
    if (showTemplatesDialog) {
        AlertDialog(
            onDismissRequest = { showTemplatesDialog = false },
            title = { Text("مكتبة القوالب الجاهزة", color = MaterialTheme.colorScheme.onSurface) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(templates) { template ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    repository.applyTemplate(template)
                                    showTemplatesDialog = false
                                    Toast.makeText(context, "تم تطبيق قالب: ${template.nameAr}", Toast.LENGTH_SHORT).show()
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(template.nameAr, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text(template.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTemplatesDialog = false }) {
                    Text("إغلاق", color = MaterialTheme.colorScheme.primary)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // PRD Dialog
    if (showPrdDialog) {
        AlertDialog(
            onDismissRequest = { showPrdDialog = false },
            title = { Text("وثيقة متطلبات المشروع (PRD)", color = MaterialTheme.colorScheme.onSurface) },
            text = {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    item {
                        Text(
                            text = repository.generatePrd(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                    val clip = android.content.ClipData.newPlainText("PRD", repository.generatePrd())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "تم نسخ وثيقة PRD إلى الحافظة", Toast.LENGTH_SHORT).show()
                    showPrdDialog = false
                }) {
                    Text("نسخ الوثيقة", color = MetraEmerald)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPrdDialog = false }) {
                    Text("إغلاق", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

fun getStepTitle(step: Int): String = when (step) {
    1 -> "نوع المشروع"
    2 -> "لغات البرمجة والتقنيات"
    3 -> "طبيعة البرنامج ووظائفه"
    4 -> "مصمم الواجهة والألوان"
    5 -> "الشاشات والصفحات"
    6 -> "الوظائف المتقدمة والأمان"
    7 -> "أسلوب التنفيذ والسرعة"
    8 -> "المعاينة ووثيقة PRD"
    9 -> "القوالب الجاهزة"
    10 -> "الموافقة وبدء البرمجة"
    else -> ""
}

@Composable
fun Step1ProjectTypes(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val types = listOf(
        "تطبيق هاتف Android",
        "تطبيق هاتف iOS",
        "تطبيق Android وiOS معاً",
        "برنامج Windows",
        "برنامج macOS وLinux",
        "موقع إلكتروني",
        "تطبيق ويب تفاعلي",
        "نظام إدارة مؤسسات",
        "لوحة تحكم إدارية",
        "منصة ذكاء اصطناعي",
        "نظام قواعد بيانات",
        "لعبة إلكترونية",
        "مشروع مخصص"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("اختر المنصات وأنواع المشروع المطلوبة (يمكنك اختيار أكثر من منصة):", color = MaterialTheme.colorScheme.onSurfaceVariant)

        OutlinedTextField(
            value = state.projectName,
            onValueChange = { repo.updateState { s -> s.copy(projectName = it) } },
            label = { Text("اسم المشروع") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        OutlinedTextField(
            value = state.projectDescription,
            onValueChange = { repo.updateState { s -> s.copy(projectDescription = it) } },
            label = { Text("وصف موجز للمشروع أو الفكرة") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(4.dp))
        types.forEach { type ->
            val selected = state.selectedProjectTypes.contains(type)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updated = if (selected) state.selectedProjectTypes - type else state.selectedProjectTypes + type
                        repo.updateState { s -> s.copy(selectedProjectTypes = updated) }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(type, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    Checkbox(
                        checked = selected,
                        onCheckedChange = { chk ->
                            val updated = if (chk) state.selectedProjectTypes + type else state.selectedProjectTypes - type
                            repo.updateState { s -> s.copy(selectedProjectTypes = updated) }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Composable
fun Step2TechStack(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val techGroups = mapOf(
        "تقنيات الويب" to listOf("HTML وCSS وJavaScript", "TypeScript", "React", "Next.js", "Vue", "Angular", "Node.js"),
        "تطبيقات الهاتف" to listOf("Flutter وDart", "Kotlin", "Java", "Swift", "React Native"),
        "تطبيقات سطح المكتب والأنظمة" to listOf("C# و.NET", "Python", "Java", "C++", "Rust", "Go", "PHP", "Ruby"),
        "قواعد البيانات" to listOf("PostgreSQL", "Supabase", "MySQL", "SQL Server", "SQLite", "MongoDB", "Redis")
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("اختر التقنيات واللغات المفضلة أو اترك الذكاء الاصطناعي يختار الأنسب:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { repo.updateState { s -> s.copy(chooseBestForMe = !s.chooseBestForMe) } },
            colors = CardDefaults.cardColors(containerColor = if (state.chooseBestForMe) MetraEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            border = BorderStroke(1.dp, if (state.chooseBestForMe) MetraEmerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.chooseBestForMe,
                    onCheckedChange = { chk -> repo.updateState { s -> s.copy(chooseBestForMe = chk) } },
                    colors = CheckboxDefaults.colors(checkedColor = MetraEmerald)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("اختر الأنسب لي تلقائياً", fontWeight = FontWeight.Bold, color = if (state.chooseBestForMe) MetraEmerald else MaterialTheme.colorScheme.primary)
                    Text("يقوم الذكاء الاصطناعي باختيار التقنيات الأفضل بناءً على نوع المشروع", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        if (!state.chooseBestForMe) {
            techGroups.forEach { (groupName, list) ->
                Text(groupName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                list.forEach { tech ->
                    val selected = state.selectedTechStack.contains(tech)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val updated = if (selected) state.selectedTechStack - tech else state.selectedTechStack + tech
                                repo.updateState { s -> s.copy(selectedTechStack = updated) }
                            },
                        colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                        border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(tech, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                            Checkbox(
                                checked = selected,
                                onCheckedChange = { chk ->
                                    val updated = if (chk) state.selectedTechStack + tech else state.selectedTechStack - tech
                                    repo.updateState { s -> s.copy(selectedTechStack = updated) }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Step3NatureAndFunctions(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("حدد طبيعة البرنامج، أهدافه، والوظائف الأساسية المطلوبة:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        OutlinedTextField(
            value = state.goal,
            onValueChange = { repo.updateState { s -> s.copy(goal = it) } },
            label = { Text("ما الهدف الرئيسي من البرنامج؟") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), focusedLabelColor = MaterialTheme.colorScheme.primary)
        )

        OutlinedTextField(
            value = state.targetUsers,
            onValueChange = { repo.updateState { s -> s.copy(targetUsers = it) } },
            label = { Text("من سيستخدمه؟ (الجمهور المستهدف)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), focusedLabelColor = MaterialTheme.colorScheme.primary)
        )

        Text("المميزات والوظائف الأساسية:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

        val featuresMap = listOf(
            Triple("تسجيل الدخول وإدارة المستخدمين", state.needsAuth) { v: Boolean -> repo.updateState { s -> s.copy(needsAuth = v) } },
            Triple("صلاحيات متعددة وأدوار (RBAC)", state.needsRoles) { v: Boolean -> repo.updateState { s -> s.copy(needsRoles = v) } },
            Triple("قاعدة بيانات لحفظ البيانات", state.needsDatabase) { v: Boolean -> repo.updateState { s -> s.copy(needsDatabase = v) } },
            Triple("لوحة تحكم إدارية (Dashboard)", state.needsDashboard) { v: Boolean -> repo.updateState { s -> s.copy(needsDashboard = v) } },
            Triple("تقارير وإحصائيات متقدمة", state.needsReports) { v: Boolean -> repo.updateState { s -> s.copy(needsReports = v) } },
            Triple("إشعارات فورية وتنبيهات", state.needsNotifications) { v: Boolean -> repo.updateState { s -> s.copy(needsNotifications = v) } },
            Triple("العمل دون إنترنت (Offline)", state.needsOffline) { v: Boolean -> repo.updateState { s -> s.copy(needsOffline = v) } },
            Triple("مزامنة بين عدة أجهزة", state.needsSync) { v: Boolean -> repo.updateState { s -> s.copy(needsSync = v) } },
            Triple("ميزات ذكاء اصطناعي مدمجة", state.needsAi) { v: Boolean -> repo.updateState { s -> s.copy(needsAi = v) } },
            Triple("ربط بخدمات خارجية وواجهات API", state.needsApi) { v: Boolean -> repo.updateState { s -> s.copy(needsApi = v) } },
            Triple("رفع وتخزين الصور والملفات", state.needsFileUpload) { v: Boolean -> repo.updateState { s -> s.copy(needsFileUpload = v) } },
            Triple("تصدير وطباعة PDF وExcel", state.needsPdfExcelExport) { v: Boolean -> repo.updateState { s -> s.copy(needsPdfExcelExport = v) } },
            Triple("دعم لغات متعددة (RTL & LTR)", state.needsMultiLanguage) { v: Boolean -> repo.updateState { s -> s.copy(needsMultiLanguage = v) } }
        )

        featuresMap.forEach { (label, checked, onChange) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onChange(!checked) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, color = MaterialTheme.colorScheme.onSurface)
                Checkbox(
                    checked = checked,
                    onCheckedChange = onChange,
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun Step4UiAndColors(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val styles = listOf("احترافي رسمي", "عصري Minimal", "فاخر Premium", "تقني مستقبلي", "طبي", "حكومي", "تجاري", "صناعي", "تعليمي", "إبداعي")
    val palettes = listOf("أزرق وأبيض", "أسود وذهبي", "أخضر وأبيض", "كحلي ورمادي", "بنفسجي", "ألوان مخصصة")
    val modes = listOf("Light Mode", "Dark Mode", "Auto Mode")

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("تصميم واجهة المستخدم والألوان:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Text("نمط التصميم:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(styles) { style ->
                val selected = state.designStyle == style
                FilterChip(
                    selected = selected,
                    onClick = { repo.updateState { s -> s.copy(designStyle = style) } },
                    label = { Text(style) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Text("لوحة الألوان:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(palettes) { pal ->
                val selected = state.colorPalette == pal
                FilterChip(
                    selected = selected,
                    onClick = { repo.updateState { s -> s.copy(colorPalette = pal) } },
                    label = { Text(pal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Text("نمط العرض:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            modes.forEach { mode ->
                val selected = state.colorMode == mode
                FilterChip(
                    selected = selected,
                    onClick = { repo.updateState { s -> s.copy(colorMode = mode) } },
                    label = { Text(mode) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        // Live preview box
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = if (state.colorMode == "Light Mode") Color.White else MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("معاينة مباشرة للواجهة", fontWeight = FontWeight.Bold, color = if (state.colorMode == "Light Mode") Color.Black else MaterialTheme.colorScheme.primary)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Text("زر رئيسي", color = MaterialTheme.colorScheme.onPrimary)
                    }
                    OutlinedButton(onClick = {}) {
                        Text("زر ثانوي", color = if (state.colorMode == "Light Mode") Color.Black else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun Step5ScreensAndPages(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val defaultScreens = listOf(
        "تسجيل الدخول",
        "الصفحة الرئيسية",
        "لوحة التحكم",
        "إدارة المستخدمين",
        "إدارة البيانات والمحتوى",
        "التقارير والإحصائيات",
        "الإعدادات",
        "الإشعارات",
        "الملف الشخصي",
        "إدارة الصلاحيات"
    )

    var newScreenInput by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("حدد الشاشات والصفحات المطلوبة في المشروع:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = newScreenInput,
                onValueChange = { newScreenInput = it },
                label = { Text("إضافة شاشة جديدة") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
            Button(
                onClick = {
                    if (newScreenInput.isNotBlank() && !state.selectedScreens.contains(newScreenInput)) {
                        repo.updateState { s -> s.copy(selectedScreens = s.selectedScreens + newScreenInput) }
                        newScreenInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("إضافة", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        defaultScreens.forEach { screen ->
            val selected = state.selectedScreens.contains(screen)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updated = if (selected) state.selectedScreens - screen else state.selectedScreens + screen
                        repo.updateState { s -> s.copy(selectedScreens = updated) }
                    },
                colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(screen, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    Checkbox(
                        checked = selected,
                        onCheckedChange = { chk ->
                            val updated = if (chk) state.selectedScreens + screen else state.selectedScreens - screen
                            repo.updateState { s -> s.copy(selectedScreens = updated) }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Composable
fun Step6AdvancedFunctions(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val advFunctions = listOf(
        "تسجيل الدخول بالبريد الإلكتروني",
        "تسجيل الدخول بواسطة Google",
        "المصادقة الثنائية (2FA)",
        "بصمة الإصبع أو الوجه",
        "إدارة المستخدمين والأدوار",
        "سجل العمليات والتدقيق (Audit Logs)",
        "النسخ الاحتياطي التلقائي",
        "التشفير الشامل للبيانات",
        "المزامنة الذكية",
        "العمل دون إنترنت",
        "الذكاء الاصطناعي المدمج",
        "البحث المتقدم والفلاتر",
        "التنبيهات الفورية",
        "تصدير واستيراد البيانات",
        "التكامل مع Telegram",
        "التكامل مع Google Drive",
        "التكامل مع GitHub"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("اختر الوظائف المتقدمة والأمان المطلوب:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        advFunctions.forEach { func ->
            val selected = state.advancedFunctions.contains(func)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updated = if (selected) state.advancedFunctions - func else state.advancedFunctions + func
                        repo.updateState { s -> s.copy(advancedFunctions = updated) }
                    },
                colors = CardDefaults.cardColors(containerColor = if (selected) MetraEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, if (selected) MetraEmerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(func, color = if (selected) MetraEmerald else MaterialTheme.colorScheme.onSurface)
                    Checkbox(
                        checked = selected,
                        onCheckedChange = { chk ->
                            val updated = if (chk) state.advancedFunctions + func else state.advancedFunctions - func
                            repo.updateState { s -> s.copy(advancedFunctions = updated) }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MetraEmerald)
                    )
                }
            }
        }
    }
}

@Composable
fun Step7ImplementationStyle(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    val styles = listOf(
        "بناء المشروع بالكامل من الصفر",
        "بناء نسخة أولية MVP",
        "تطوير مشروع موجود",
        "إعادة تصميم مشروع موجود دون حذف وظائفه",
        "تحويل تصميم إلى تطبيق حقيقي",
        "إنشاء نسخة تجريبية قابلة للتشغيل"
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("اختر أسلوب التنفيذ والأولويات:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        styles.forEach { style ->
            val selected = state.implementationStyle == style
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { repo.updateState { s -> s.copy(implementationStyle = style) } },
                colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected,
                        onClick = { repo.updateState { s -> s.copy(implementationStyle = style) } },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(style, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Composable
fun Step8PrdPreview(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("ملخص مواصفات المشروع ووثيقة PRD:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("اسم المشروع: ${state.projectName.ifEmpty { "مشروع غير محدد" }}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("الوصف: ${state.projectDescription.ifEmpty { "لا يوجد وصف" }}", color = MaterialTheme.colorScheme.onSurface)
                Text("المنصات: ${state.selectedProjectTypes.joinToString(", ")}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("التقنيات: ${if (state.chooseBestForMe) "اختيار ذكي تلقائي" else state.selectedTechStack.joinToString(", ")}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("الشاشات المختارة: ${state.selectedScreens.size} شاشات", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("الوظائف المتقدمة: ${state.advancedFunctions.size} وظيفة", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("أسلوب التنفيذ: ${state.implementationStyle}", color = MetraEmerald)
            }
        }
    }
}

@Composable
fun Step9TemplatesLibrary(templates: List<DesignTemplate>, repo: SoftwareDesignWizardRepository, onApplied: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("مكتبة القوالب الاحترافية الجاهزة:", color = MaterialTheme.colorScheme.onSurfaceVariant)

        templates.forEach { template ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        repo.applyTemplate(template)
                        onApplied()
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(template.nameAr, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(template.category, style = MaterialTheme.typography.labelSmall, color = MetraAmber)
                    }
                    Text(template.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("انقر لتطبيق هذا القالب فوراً", style = MaterialTheme.typography.labelSmall, color = MetraEmerald)
                }
            }
        }
    }
}

@Composable
fun Step10ApprovalAndExecution(state: SoftwareDesignWizardState, repo: SoftwareDesignWizardRepository, onNavigateToCodeStudio: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Default.Verified, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(64.dp))
        Text("جاهز لاعتماد المشروع وبدء التنفيذ!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Text("لقد تم اكتمال جمع المتطلبات بنجاح. يمكنك الآن اعتماد التصميم والانتقال إلى محرك التطوير ومحرر الكود METRA Code Studio لإنشاء الملفات وتشغيل المشروع فوراً.", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Button(
            onClick = {
                scope.launch {
                    repo.updateState { s -> s.copy(isApproved = true, prdGenerated = true) }
                    repo.saveToCloudOrLocal()
                    Toast.makeText(context, "تم الاعتماد بنجاح! جاري الانتقال لمحرر الكود...", Toast.LENGTH_LONG).show()
                    onNavigateToCodeStudio()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MetraEmerald),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("اعتماد التصميم والبدء الفوري بالبرمجة", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
