package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.repository.AiDevelopmentRepository
import com.example.data.repository.AiProvidersRepository
import com.example.data.repository.AutomationRepository
import com.example.data.repository.DatabaseIntegrationRepository
import com.example.data.repository.IntegrationsRepository
import com.example.data.repository.PersistentJobRepository
import com.example.data.repository.ProjectRepository
import com.example.data.repository.SecretsVaultRepository
import com.example.data.repository.StorageRepository
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.MainScaffold
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.MetraTheme
import com.example.util.configureAppCheck
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        setContent {
            MetraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkNavyBg
                ) {
                    MetraAppRoot()
                }
            }
        }
    }
}

@Composable
fun MetraAppRoot() {
    var currentUser by remember { mutableStateOf(Firebase.auth.currentUser) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            currentUser = auth.currentUser
        }
        Firebase.auth.addAuthStateListener(listener)
        onDispose {
            Firebase.auth.removeAuthStateListener(listener)
        }
    }

    if (currentUser == null) {
        AuthScreen(
            onAuthSuccess = {
                currentUser = Firebase.auth.currentUser
            }
        )
    } else {
        val uid = currentUser!!.uid
        val context = androidx.compose.ui.platform.LocalContext.current

        val projectRepository = remember(uid) { ProjectRepository(context) }
        val integrationsRepository = remember(uid) { IntegrationsRepository(context) }
        val storageRepository = remember(uid) { StorageRepository(context) }
        val aiDevRepository = remember(uid) { AiDevelopmentRepository(context) }
        val automationRepository = remember(uid) { AutomationRepository(context, projectRepository, storageRepository, integrationsRepository) }
        val aiProvidersRepository = remember(uid) { AiProvidersRepository(context, integrationsRepository) }
        val persistentJobRepository = remember(uid) { PersistentJobRepository(context) }
        val secretsVaultRepository = remember(uid) { SecretsVaultRepository(context, integrationsRepository) }
        val databaseRepository = remember(uid) { DatabaseIntegrationRepository(context, secretsVaultRepository, integrationsRepository) }
        val aiAccountsRepository = remember(uid) { com.example.data.repository.AiAccountsRepository(context) }
        val exportSyncRepository = remember(uid) { com.example.data.repository.ExportSyncRepository(context) }
        val aiIdentityRepository = remember(uid) { com.example.data.repository.AiIdentityRepository(context) }
        val cyberShieldRepository = remember(uid) { com.example.data.repository.CyberShieldRepository(context) }
        val codeStudioRepository = remember(uid) { com.example.data.repository.CodeStudioRepository(context) }
        val softwareDesignWizardRepository = remember(uid) { com.example.data.repository.SoftwareDesignWizardRepository(context) }
        val projectMemoryRepository = remember(uid) { com.example.data.repository.ProjectMemoryRepository(context) }

        key(uid) {
            MainScaffold(
                projectRepository = projectRepository,
                integrationsRepository = integrationsRepository,
                storageRepository = storageRepository,
                aiDevRepository = aiDevRepository,
                automationRepository = automationRepository,
                aiProvidersRepository = aiProvidersRepository,
                persistentJobRepository = persistentJobRepository,
                secretsVaultRepository = secretsVaultRepository,
                databaseRepository = databaseRepository,
                aiAccountsRepository = aiAccountsRepository,
                exportSyncRepository = exportSyncRepository,
                aiIdentityRepository = aiIdentityRepository,
                cyberShieldRepository = cyberShieldRepository,
                codeStudioRepository = codeStudioRepository,
                softwareDesignWizardRepository = softwareDesignWizardRepository,
                projectMemoryRepository = projectMemoryRepository,
                onSignOut = {
                    currentUser = null
                }
            )
        }
    }
}
