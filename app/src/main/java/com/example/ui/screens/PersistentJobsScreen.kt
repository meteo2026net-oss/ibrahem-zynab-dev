package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JobStatus
import com.example.data.model.PersistentJob
import com.example.data.model.Project
import com.example.data.repository.AutomationRepository
import com.example.data.repository.PersistentJobRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraPurple
import com.example.ui.theme.MetraRose
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun PersistentJobsScreen(
    currentProject: Project?,
    persistentJobRepository: PersistentJobRepository,
    automationRepository: AutomationRepository,
    onNavigateToApprovals: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val jobs by persistentJobRepository.getJobsFlow(currentProject?.id).collectAsState(initial = emptyList())

    var selectedFilter by remember { mutableStateOf<JobStatus?>(null) }
    var showCreateForm by remember { mutableStateOf(false) }
    var newJobTitle by remember { mutableStateOf("") }
    var newJobPrompt by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().metraGradientBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Guarantee Banner
            MetraCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "محرك التشغيل الخلفي الدائم",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "WorkManager • Worker Lease System",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "تستمر المهام في التنفيذ حتى بعد إغلاق التطبيق، وتُستأنف آلياً عند إعادة التشغيل من آخر نقطة آمنة.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Actions & New Job Button
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "طابور المهام (${jobs.size})",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showCreateForm = !showCreateForm },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (showCreateForm) "إلغاء" else "+ مهمة جديدة",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            // New Job Form
            AnimatedVisibility(visible = showCreateForm) {
                MetraCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("جدولة مهمة تنفيذ جديدة", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = newJobTitle,
                            onValueChange = { newJobTitle = it },
                            label = { Text("عنوان المهمة") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = newJobPrompt,
                            onValueChange = { newJobPrompt = it },
                            label = { Text("أمر التنفيذ البرمجي") },
                            minLines = 3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (newJobTitle.isBlank() || newJobPrompt.isBlank()) {
                                    Toast.makeText(context, "يرجى ملء كافة الحقول", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                val pId = currentProject?.id ?: "global_project"
                                val pName = currentProject?.name ?: "مشروع عام"
                                isSubmitting = true
                                scope.launch {
                                    persistentJobRepository.createAndScheduleJob(
                                        projectId = pId,
                                        projectName = pName,
                                        title = newJobTitle,
                                        userPrompt = newJobPrompt
                                    )
                                    isSubmitting = false
                                    showCreateForm = false
                                    newJobTitle = ""
                                    newJobPrompt = ""
                                    Toast.makeText(context, "تمت جدولة المهمة بنجاح", Toast.LENGTH_LONG).show()
                                }
                            },
                            enabled = !isSubmitting,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (isSubmitting) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                            } else {
                                Text("بدء التنفيذ الخلفي", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Filter chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = selectedFilter == null,
                        onClick = { selectedFilter = null },
                        label = { Text("الكل") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFilter == JobStatus.RUNNING,
                        onClick = { selectedFilter = JobStatus.RUNNING },
                        label = { Text("جارية") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFilter == JobStatus.COMPLETED,
                        onClick = { selectedFilter = JobStatus.COMPLETED },
                        label = { Text("مكتملة") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredJobs = jobs.filter {
                selectedFilter == null || it.status == selectedFilter
            }

            if (filteredJobs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لا توجد مهام حالياً.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredJobs) { job ->
                        JobCard(
                            job = job,
                            onPause = {
                                scope.launch {
                                    persistentJobRepository.pauseJob(job.jobId)
                                }
                            },
                            onResume = {
                                scope.launch {
                                    persistentJobRepository.resumeJob(job.jobId)
                                }
                            },
                            onCancel = {
                                scope.launch {
                                    persistentJobRepository.cancelJob(job.jobId)
                                }
                            },
                            onRetry = {
                                scope.launch {
                                    persistentJobRepository.retryJob(job.jobId)
                                }
                            },
                            onNavigateToApprovals = onNavigateToApprovals
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JobCard(
    job: PersistentJob,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
    onNavigateToApprovals: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val statusColor = when (job.status) {
        JobStatus.RUNNING -> MaterialTheme.colorScheme.primary
        JobStatus.WAITING_FOR_APPROVAL -> MetraAmber
        JobStatus.COMPLETED -> MetraEmerald
        JobStatus.FAILED -> MetraRose
        JobStatus.PAUSED -> MetraPurple
        JobStatus.QUEUED -> MetraIndigo
        JobStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant
        JobStatus.WAITING_FOR_RETRY -> MetraAmber
    }

    MetraCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "مشروع: ${job.projectName} • ${job.jobId.take(8)}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = job.status.titleAr,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = { job.progressPercent / 100f },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = statusColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${job.progressPercent}%",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Meta Info
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "الرموز: ${job.totalTokensUsed}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "التكلفة: $${String.format("%.4f", job.estimatedCostUsd)}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Approval Banner
            if (job.status == JobStatus.WAITING_FOR_APPROVAL) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = MetraAmber.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = MetraAmber, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "تتطلب موافقتك للمتابعة",
                            color = MetraAmber,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = onNavigateToApprovals,
                            colors = ButtonDefaults.buttonColors(containerColor = MetraAmber),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("موافق", color = Color.White, fontSize = 10.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (job.status == JobStatus.RUNNING) {
                        IconButton(onClick = onPause, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Pause, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    } else if (job.status == JobStatus.PAUSED) {
                        IconButton(onClick = onResume, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    if (job.status != JobStatus.COMPLETED && job.status != JobStatus.CANCELLED) {
                        IconButton(onClick = onCancel, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = MetraRose)
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { expanded = !expanded }
                ) {
                    Text(
                        text = if (expanded) "إخفاء" else "التفاصيل",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Icon(
                        if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Expanded Steps & Logs
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("خطوات التنفيذ:", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    job.steps.forEachIndexed { idx, step ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(
                                if (step.status == JobStatus.COMPLETED) Icons.Default.CheckCircle else Icons.Default.Check,
                                contentDescription = null,
                                tint = if (step.status == JobStatus.COMPLETED) MetraEmerald else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${idx + 1}. ${step.title}",
                                color = if (step.status == JobStatus.COMPLETED) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("السجلات الحية:", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            job.executionLogs.takeLast(5).forEach { log ->
                                Text(text = log, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp, lineHeight = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
