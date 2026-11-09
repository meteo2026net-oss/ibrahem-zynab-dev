package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Project
import com.example.data.repository.AiDevelopmentRepository
import com.example.data.repository.AiProvidersRepository
import com.example.data.repository.AutomationRepository
import com.example.data.repository.DatabaseIntegrationRepository
import com.example.data.repository.IntegrationsRepository
import com.example.data.repository.PersistentJobRepository
import com.example.data.repository.ProjectRepository
import com.example.data.repository.SecretsVaultRepository
import com.example.data.repository.StorageRepository
import com.example.data.repository.CodeStudioRepository
import com.example.data.repository.AiIdentityRepository
import com.example.data.repository.CyberShieldRepository
import com.example.data.repository.AiAccountsRepository
import com.example.data.repository.ExportSyncRepository
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraLightGradient
import com.example.ui.theme.metraGlowShadow

enum class NavSection(val titleAr: String, val icon: ImageVector) {
    PROJECTS("المشاريع", Icons.Default.AccountTree),
    PERSISTENT_JOBS("المهام الدائمة", Icons.Default.RocketLaunch),
    CODE_STUDIO("محطة الكود", Icons.Default.DeveloperMode),
    AI_ACCOUNTS("مركز حسابات الذكاء", Icons.Default.VerifiedUser),
    EXPORT_SYNC("التصدير والمزامنة", Icons.Default.SaveAlt),
    AI_IDENTITY("هوية الذكاء الاصطناعي", Icons.Default.Badge),
    CYBER_SHIELD("الحماية السيبرانية", Icons.Default.Fingerprint),
    AI_PROVIDERS("مزودو الذكاء", Icons.Default.Psychology),
    VOICE_ASSISTANT("المساعد الصوتي", Icons.Default.VolumeUp),
    SECRETS_VAULT("خزنة الأسرار", Icons.Default.Lock),
    DB_CENTER("قواعد البيانات", Icons.Default.Storage),
    TELEGRAM_CONSOLE("أوامر Telegram", Icons.Default.Terminal),
    STORAGE("التخزين", Icons.Default.Folder),
    SYNC_BACKUP("المزامنة والنسخ", Icons.Default.Sync),
    FILE_INTEL("ذكاء الملفات", Icons.Default.FindInPage),
    NOTIFICATIONS("الإشعارات", Icons.Default.Notifications),
    AI_DEV("الوكلاء والمحادثة", Icons.Default.SmartToy),
    GITHUB("GitHub", Icons.Default.Code),
    TELEGRAM("البوتات", Icons.Default.SmartToy),
    DRIVE("Google Drive", Icons.Default.Cloud),
    API_KEYS("مفاتيح API", Icons.Default.Key),
    SOFTWARE_DESIGN_WIZARD("مصمم البرامج الذكي", Icons.Default.AutoAwesome),
    PROJECT_MEMORY("ذاكرة المشروع", Icons.Default.Psychology),
    SETTINGS("الإعدادات", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    projectRepository: ProjectRepository,
    integrationsRepository: IntegrationsRepository,
    storageRepository: StorageRepository,
    aiDevRepository: AiDevelopmentRepository,
    automationRepository: AutomationRepository,
    aiProvidersRepository: AiProvidersRepository,
    persistentJobRepository: PersistentJobRepository,
    secretsVaultRepository: SecretsVaultRepository,
    databaseRepository: DatabaseIntegrationRepository,
    aiAccountsRepository: AiAccountsRepository,
    exportSyncRepository: ExportSyncRepository,
    aiIdentityRepository: AiIdentityRepository,
    cyberShieldRepository: CyberShieldRepository,
    codeStudioRepository: CodeStudioRepository,
    softwareDesignWizardRepository: com.example.data.repository.SoftwareDesignWizardRepository,
    projectMemoryRepository: com.example.data.repository.ProjectMemoryRepository,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val projects by projectRepository.observeProjects().collectAsState(initial = emptyList())
    var currentSection by remember { mutableStateOf(NavSection.PROJECTS) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }
    var showProjectSwitcher by remember { mutableStateOf(false) }

    val activeProject = selectedProject ?: projects.firstOrNull()

    BackHandler(enabled = selectedProject != null) {
        selectedProject = null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(MetraLightGradient))
    ) {
        // Subtle decorative overlay instead of dark image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(MetraCyan.copy(alpha = 0.05f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(0f, 0f),
                        radius = 1000f
                    )
                )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showProjectSwitcher = true }
                                .shadow(2.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "المطور التقني ابراهيم زينب",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (activeProject != null) "المشروع: ${activeProject.name}" else "اختر مشروعاً للبدء",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Switch project",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = showProjectSwitcher,
                                    onDismissRequest = { showProjectSwitcher = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                ) {
                                    Text(
                                        text = "تبديل المشروع النشط",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                    projects.forEach { proj ->
                                        DropdownMenuItem(
                                            text = {
                                                Column {
                                                    Text(proj.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                                    Text("${proj.projectType} • إنجاز ${proj.progress}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                                }
                                            },
                                            onClick = {
                                                selectedProject = proj
                                                showProjectSwitcher = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    actions = {
                        Surface(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    try {
                                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0934884157"))
                                        context.startActivity(dialIntent)
                                    } catch (_: Exception) {}
                                }
                                .shadow(4.dp, RoundedCornerShape(10.dp), spotColor = MetraEmerald.copy(alpha = 0.3f)),
                            color = MetraEmerald.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MetraEmerald.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "اتصال",
                                    color = MetraEmerald,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                Surface(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.05f))
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp
                    ) {
                        val bottomItems = listOf(
                            NavSection.PROJECTS,
                            NavSection.PERSISTENT_JOBS,
                            NavSection.AI_PROVIDERS,
                            NavSection.TELEGRAM_CONSOLE
                        )

                        bottomItems.forEach { section ->
                            val isSelected = currentSection == section && selectedProject == null
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    selectedProject = null
                                    currentSection = section
                                },
                                icon = {
                                    Icon(
                                        imageVector = section.icon,
                                        contentDescription = section.titleAr,
                                        modifier = if (isSelected) Modifier.metraGlowShadow else Modifier
                                    )
                                },
                                label = {
                                    Text(
                                        section.titleAr,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        var showMoreMenu by remember { mutableStateOf(false) }
                        val isMoreSelected = currentSection !in bottomItems
                        NavigationBarItem(
                            selected = isMoreSelected,
                            onClick = { showMoreMenu = true },
                            icon = {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.MoreHoriz,
                                        contentDescription = "المزيد",
                                        modifier = if (isMoreSelected) Modifier.metraGlowShadow else Modifier
                                    )
                                    DropdownMenu(
                                        expanded = showMoreMenu,
                                        onDismissRequest = { showMoreMenu = false },
                                        modifier = Modifier.background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                    ) {
                                        // Menu items remain unchanged but look better due to parent styling
                                        DropdownMenuItem(
                                            text = { Text("محطة الكود (Code Studio)") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.CODE_STUDIO },
                                            leadingIcon = { Icon(Icons.Default.DeveloperMode, contentDescription = null, tint = MetraAmber) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("هوية الذكاء الاصطناعي (AI Identity)") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.AI_IDENTITY },
                                            leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("منظومة الحماية السيبرانية") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.CYBER_SHIELD },
                                            leadingIcon = { Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MetraEmerald) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("مركز حسابات الذكاء الاصطناعي") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.AI_ACCOUNTS },
                                            leadingIcon = { Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("التصدير والمزامنة الشاملة") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.EXPORT_SYNC },
                                            leadingIcon = { Icon(Icons.Default.SaveAlt, contentDescription = null, tint = MetraEmerald) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("المساعد الصوتي المباشر") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.VOICE_ASSISTANT },
                                            leadingIcon = { Icon(Icons.Default.VolumeUp, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("خزنة الأسرار (Secrets Vault)") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.SECRETS_VAULT },
                                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MetraEmerald) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("مركز قواعد البيانات") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.DB_CENTER },
                                            leadingIcon = { Icon(Icons.Default.Storage, contentDescription = null, tint = MetraAmber) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("التخزين والملفات الموحدة") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.STORAGE },
                                            leadingIcon = { Icon(Icons.Default.Folder, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("المطور الذكي والمحادثة") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.AI_DEV },
                                            leadingIcon = { Icon(Icons.Default.SmartToy, contentDescription = null, tint = MetraIndigo) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("المزامنة والنسخ الاحتياطي") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.SYNC_BACKUP },
                                            leadingIcon = { Icon(Icons.Default.Sync, contentDescription = null, tint = MetraEmerald) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("ذكاء وتحليل الملفات") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.FILE_INTEL },
                                            leadingIcon = { Icon(Icons.Default.FindInPage, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("مركز الإشعارات") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.NOTIFICATIONS },
                                            leadingIcon = { Icon(Icons.Default.Notifications, contentDescription = null, tint = MetraAmber) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("تكامل GitHub") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.GITHUB },
                                            leadingIcon = { Icon(Icons.Default.Code, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("إعدادات بوتات Telegram") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.TELEGRAM },
                                            leadingIcon = { Icon(Icons.Default.SmartToy, contentDescription = null, tint = MetraIndigo) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("حسابات Google Drive") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.DRIVE },
                                            leadingIcon = { Icon(Icons.Default.Cloud, contentDescription = null, tint = MetraEmerald) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("مفاتيح API المستقلة") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.API_KEYS },
                                            leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = MetraAmber) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("مصمم البرامج الذكي") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.SOFTWARE_DESIGN_WIZARD },
                                            leadingIcon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MetraCyan) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("ذاكرة المشروع") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.PROJECT_MEMORY },
                                            leadingIcon = { Icon(Icons.Default.Psychology, contentDescription = null, tint = MetraAmber) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("الإعدادات وسجل التدقيق") },
                                            onClick = { showMoreMenu = false; selectedProject = null; currentSection = NavSection.SETTINGS },
                                            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null, tint = MetraRose) }
                                        )
                                    }
                                }
                            },
                            label = { Text("المزيد", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (selectedProject != null) {
                    ProjectDetailScreen(
                        project = selectedProject!!,
                        projectRepository = projectRepository,
                        integrationsRepository = integrationsRepository,
                        storageRepository = storageRepository,
                        aiDevRepository = aiDevRepository,
                        onBack = { selectedProject = null }
                    )
                } else {
                    AnimatedContent(
                        targetState = currentSection,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screen_transition"
                    ) { section ->
                        when (section) {
                            NavSection.PROJECTS -> ProjectsCenterScreen(projectRepository, integrationsRepository, { selectedProject = it })
                            NavSection.PERSISTENT_JOBS -> PersistentJobsScreen(activeProject, persistentJobRepository, automationRepository, { currentSection = NavSection.TELEGRAM_CONSOLE })
                            NavSection.CODE_STUDIO -> CodeStudioScreen(activeProject, codeStudioRepository)
                            NavSection.AI_ACCOUNTS -> AiAccountsCenterScreen(activeProject, aiAccountsRepository)
                            NavSection.EXPORT_SYNC -> ExportSyncCenterScreen(activeProject, exportSyncRepository, storageRepository)
                            NavSection.AI_IDENTITY -> AiIdentitySettingsScreen(aiIdentityRepository)
                            NavSection.CYBER_SHIELD -> CyberShieldScreen(cyberShieldRepository)
                            NavSection.AI_PROVIDERS -> AiProvidersScreen(activeProject, aiProvidersRepository, integrationsRepository)
                            NavSection.VOICE_ASSISTANT -> VoiceAssistantScreen(activeProject, persistentJobRepository)
                            NavSection.SECRETS_VAULT -> SecretsVaultScreen(activeProject, secretsVaultRepository)
                            NavSection.DB_CENTER -> DatabaseIntegrationScreen(activeProject, databaseRepository)
                            NavSection.TELEGRAM_CONSOLE -> TelegramCommandConsoleScreen(activeProject, automationRepository, projectRepository)
                            NavSection.AI_DEV -> AiControlScreen(activeProject, aiDevRepository, integrationsRepository)
                            NavSection.STORAGE -> StorageCenterScreen(activeProject, storageRepository)
                            NavSection.SYNC_BACKUP -> SyncAndBackupScreen(activeProject, automationRepository, projectRepository, storageRepository)
                            NavSection.FILE_INTEL -> FileIntelligenceScreen(activeProject, automationRepository, projectRepository, storageRepository)
                            NavSection.NOTIFICATIONS -> NotificationsScreen(activeProject, automationRepository)
                            NavSection.GITHUB -> GithubCenterScreen(activeProject, integrationsRepository, projectRepository)
                            NavSection.TELEGRAM -> TelegramCenterScreen(activeProject, integrationsRepository)
                            NavSection.DRIVE -> GoogleAccountsScreen(activeProject, integrationsRepository, projectRepository)
                            NavSection.API_KEYS -> ApiKeysScreen(activeProject, integrationsRepository)
                            NavSection.SOFTWARE_DESIGN_WIZARD -> SoftwareDesignWizardScreen(softwareDesignWizardRepository, { currentSection = NavSection.CODE_STUDIO })
                            NavSection.PROJECT_MEMORY -> ProjectMemoryScreen(activeProject, projectMemoryRepository)
                            NavSection.SETTINGS -> SettingsScreen(integrationsRepository, onSignOut)
                        }
                    }
                }
            }
        }
    }
}
