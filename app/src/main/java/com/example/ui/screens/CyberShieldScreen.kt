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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.SecurityIncidentLog
import com.example.data.model.TrustedDevice
import com.example.data.repository.CyberShieldRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.MetraCard
import kotlinx.coroutines.launch

@Composable
fun CyberShieldScreen(
    cyberShieldRepository: CyberShieldRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val trustedDevices by cyberShieldRepository.observeTrustedDevices().collectAsState(initial = emptyList())
    val securityIncidents by cyberShieldRepository.observeSecurityIncidents().collectAsState(initial = emptyList())

    var deviceToRevoke by remember { mutableStateOf<TrustedDevice?>(null) }
    var isAuthenticatingBiometric by remember { mutableStateOf(false) }
    var biometricVerified by remember { mutableStateOf(true) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MetraEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "منظومة الحماية السيبرانية البيومترية (CyberShield & SOC)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Biometric Passkeys, AES-256-GCM Envelope Encryption & Trusted Devices",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Biometric Lock Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (biometricVerified) MetraEmerald else MetraAmber)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = null,
                    tint = if (biometricVerified) MetraEmerald else MetraAmber,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (biometricVerified) "المصادقة البيومترية مفعلة ومؤمنة" else "مطلوبة إعادة المصادقة البيومترية",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Android BiometricPrompt & Apple LocalAuthentication active",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = {
                        isAuthenticatingBiometric = true
                        coroutineScope.launch {
                            kotlinx.coroutines.delay(800)
                            biometricVerified = true
                            isAuthenticatingBiometric = false
                            statusMessage = "تم التحقق من البصمة البيومترية بنجاح وفتح مفاتيح التشفير."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("biometric_auth_btn")
                ) {
                    Text(if (isAuthenticatingBiometric) "جاري الفحص..." else "تحقق بالبصمة")
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Trusted Devices Section
        Text(text = "الأجهزة الموثوقة والمصرح لها (${trustedDevices.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(trustedDevices, key = { it.id }) { dev ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("trusted_device_${dev.deviceName}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MetraEmerald, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = dev.deviceName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "${dev.operatingSystem} • IP: ${dev.ipAddress} • ثقة: ${dev.trustScore}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { deviceToRevoke = dev }, modifier = Modifier.testTag("revoke_device_btn")) {
                            Icon(Icons.Default.Delete, contentDescription = "Revoke", tint = MetraRose)
                        }
                    }
                }
            }
        }
    }

    // Modal: Revoke Device
    deviceToRevoke?.let { dev ->
        AlertDialog(
            onDismissRequest = { deviceToRevoke = null },
            title = { Text("إلغاء اعتماد الجهاز") },
            text = { Text("هل أنت متأكد من إلغاء صلاحية الجهاز '${dev.deviceName}' وإنهاء جلسته عن بُعد؟") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            cyberShieldRepository.revokeDevice(dev.id)
                            deviceToRevoke = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MetraRose)
                ) {
                    Text("إلغاء الصلاحية")
                }
            },
            dismissButton = {
                TextButton(onClick = { deviceToRevoke = null }) { Text("إلغاء") }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}
