package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.DatabaseConnectionConfig
import com.example.data.model.DatabaseEngine
import com.example.data.model.DatabaseTableSchema
import com.example.data.model.Project
import com.example.data.model.SqlQueryResult
import com.example.data.repository.DatabaseIntegrationRepository
import com.example.ui.theme.MetraAmber
import com.example.ui.theme.MetraCard
import com.example.ui.theme.MetraCyan
import com.example.ui.theme.MetraEmerald
import com.example.ui.theme.MetraIndigo
import com.example.ui.theme.MetraRose
import com.example.ui.theme.metraGradientBackground
import kotlinx.coroutines.launch

@Composable
fun DatabaseIntegrationScreen(
    currentProject: Project?,
    databaseRepository: DatabaseIntegrationRepository
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("الاتصالات", "المستكشف", "المساعد SQL")

    val connections by databaseRepository.observeConnections(currentProject?.id ?: "").collectAsState(initial = emptyList())
    var selectedConn by remember { mutableStateOf<DatabaseConnectionConfig?>(null) }

    Box(modifier = Modifier.fillMaxSize().metraGradientBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            MetraCard(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Storage, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("مركز قواعد البيانات", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Unified Database Management System", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { idx, title ->
                    Tab(
                        selected = selectedTab == idx,
                        onClick = { selectedTab = idx },
                        text = { Text(title, fontSize = 13.sp, fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> DatabaseConnectionsTab(currentProject, databaseRepository, connections) { conn ->
                    selectedConn = conn
                    selectedTab = 1
                }
                1 -> DatabaseExplorerTab(selectedConn ?: connections.firstOrNull(), databaseRepository)
                2 -> DatabaseAssistantTab(selectedConn ?: connections.firstOrNull(), databaseRepository)
            }
        }
    }
}

@Composable
private fun DatabaseConnectionsTab(
    currentProject: Project?,
    databaseRepository: DatabaseIntegrationRepository,
    connections: List<DatabaseConnectionConfig>,
    onSelectConn: (DatabaseConnectionConfig) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showAddModal by remember { mutableStateOf(false) }

    var connName by remember { mutableStateOf("") }
    var engine by remember { mutableStateOf(DatabaseEngine.POSTGRESQL) }
    var host by remember { mutableStateOf("") }
    var dbName by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var secretRef by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("قواعد البيانات (${connections.size})", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Button(
                onClick = { showAddModal = !showAddModal },
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إضافة اتصال")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (showAddModal) {
            MetraCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("إعداد اتصال جديد", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    OutlinedTextField(
                        value = connName,
                        onValueChange = { connName = it },
                        label = { Text("اسم الاتصال") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                    OutlinedTextField(
                        value = host,
                        onValueChange = { host = it },
                        label = { Text("المضيف (Host)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                    Button(
                        onClick = {
                            val pId = currentProject?.id ?: "global"
                            scope.launch {
                                databaseRepository.addConnection(
                                    projectId = pId,
                                    connectionName = connName.ifBlank { "Prod Database" },
                                    engine = engine,
                                    host = host,
                                    port = engine.defaultPort,
                                    databaseName = dbName,
                                    username = user,
                                    secretReferenceKey = secretRef,
                                    isReadOnly = true
                                )
                                showAddModal = false
                                Toast.makeText(context, "تمت الإضافة بنجاح", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ الاتصال واختباره")
                    }
                }
            }
        }

        if (connections.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("لا توجد اتصالات حالياً.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(connections) { conn ->
                    MetraCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(conn.connectionName, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                                    Text("${conn.engineType} • ${conn.host}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
                                }
                                Surface(
                                    color = if (conn.isReadOnly) MetraEmerald.copy(alpha = 0.1f) else MetraRose.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        if (conn.isReadOnly) "Read-Only" else "Write",
                                        color = if (conn.isReadOnly) MetraEmerald else MetraRose,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                IconButton(onClick = {
                                    scope.launch { databaseRepository.deleteConnection(conn.id, conn.projectId) }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = null, tint = MetraRose, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { onSelectConn(conn) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                                    contentColor = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier.fillMaxWidth().height(36.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("استكشاف الهيكل", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DatabaseExplorerTab(
    config: DatabaseConnectionConfig?,
    databaseRepository: DatabaseIntegrationRepository
) {
    if (config == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("يرجى اختيار قاعدة بيانات أولاً.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        return
    }

    var tables by remember { mutableStateOf<List<DatabaseTableSchema>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    remember(config.id) {
        scope.launch {
            isLoading = true
            tables = databaseRepository.inspectSchema(config)
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("مستكشف: ${config.connectionName}", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tables) { table ->
                    MetraCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("الجدول: ${table.tableName}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            table.columns.forEach { col ->
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text("• ${col.columnName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                    Text(col.dataType, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DatabaseAssistantTab(
    config: DatabaseConnectionConfig?,
    databaseRepository: DatabaseIntegrationRepository
) {
    var sqlQuery by remember { mutableStateOf("SELECT * FROM users LIMIT 5;") }
    var queryResult by remember { mutableStateOf<SqlQueryResult?>(null) }
    var isExecuting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("مساعد SQL الذكي", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = sqlQuery,
            onValueChange = { sqlQuery = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (config == null) return@Button
                isExecuting = true
                scope.launch {
                    queryResult = databaseRepository.executeQuery(config, sqlQuery)
                    isExecuting = false
                }
            },
            enabled = config != null && !isExecuting,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isExecuting) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("تنفيذ الاستعلام", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        queryResult?.let { res ->
            MetraCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (res.isSuccess) {
                        Text("النتائج (${res.executionTimeMs}ms):", color = MetraEmerald, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        res.rows.forEach { row ->
                            Text(row.joinToString(" | "), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                    } else {
                        Text(res.errorMessage ?: "خطأ في التنفيذ", color = MetraRose, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
