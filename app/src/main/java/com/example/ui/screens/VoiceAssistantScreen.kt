package com.example.ui.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Project
import com.example.data.repository.PersistentJobRepository
import com.example.data.voice.VoiceAssistantManager
import com.example.data.voice.VoiceState
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun VoiceAssistantScreen(
    currentProject: Project?,
    persistentJobRepository: PersistentJobRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var spokenTranscript by remember { mutableStateOf("") }
    var responseMessage by remember { mutableStateOf("") }

    val voiceManager = remember {
        VoiceAssistantManager(context) { command ->
            spokenTranscript = command
            responseMessage = "فهمت أمرك: \"$command\". تم توجيهه إلى مشروع [${currentProject?.name ?: "العام"}] وسيبدأ التنفيذ خلفياً فوراً."

            scope.launch {
                persistentJobRepository.createAndScheduleJob(
                    projectId = currentProject?.id ?: "global_voice",
                    projectName = currentProject?.name ?: "المشروع العام",
                    title = "أمر صوتي: ${command.take(20)}...",
                    userPrompt = command
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            voiceManager.shutdown()
        }
    }

    val voiceState by voiceManager.voiceState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            voiceManager.startListening()
        } else {
            Toast.makeText(context, "مطلوب إذن الميكروفون للتحدث مع المساعد الصوتي", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize().metraGradientBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Banner
            MetraCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.VolumeUp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "المساعد الصوتي المباشر",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Voice Assistant • Speech-to-Task",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "تحدث بلغة عربية وسيوجه المساعد كلامك فوراً كـ مهمة خلفية للمشروع (${currentProject?.name ?: "العام"}).",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Big Tactile Microphone Button
            val isListening = voiceState is VoiceState.Listening
            val micColor = if (isListening) MetraRose else MaterialTheme.colorScheme.primary
            val glowSize by animateDpAsState(
                targetValue = if (isListening) 140.dp else 120.dp,
                animationSpec = tween(500),
                label = "glow"
            )

            Box(contentAlignment = Alignment.Center) {
                // Pulsing glow effect
                Surface(
                    modifier = Modifier.size(glowSize),
                    shape = CircleShape,
                    color = micColor.copy(alpha = 0.05f)
                ) {}

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(micColor.copy(alpha = 0.1f))
                        .border(2.dp, micColor.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            if (isListening) {
                                voiceManager.stopListening()
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        },
                        modifier = Modifier.size(90.dp)
                    ) {
                        Icon(
                            if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = null,
                            tint = micColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = when (voiceState) {
                    is VoiceState.Listening -> "جاري الاستماع... تحدّث الآن"
                    is VoiceState.Speaking -> "جاري قراءة الرد صوتياً..."
                    is VoiceState.Processing -> "جاري تحليل الكلام..."
                    else -> "اضغط على الميكروفون للتحدث"
                },
                color = if (isListening) MetraRose else MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Spoken Transcript Box
            if (spokenTranscript.isNotBlank()) {
                MetraCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "ما تم التعرف عليه صوتياً:",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            spokenTranscript,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI Response & Speech Controls
            if (responseMessage.isNotBlank()) {
                MetraCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = MetraEmerald.copy(alpha = 0.3f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "رد المساعد والتنفيذ:",
                                color = MetraEmerald,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                IconButton(
                                    onClick = { voiceManager.speak(responseMessage) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MetraEmerald)
                                }
                                IconButton(
                                    onClick = { voiceManager.stopSpeaking() },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.Stop, contentDescription = null, tint = MetraRose)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            responseMessage,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
