package com.moin.crackd.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Task(
    val name: String,
    var isCompleted: Boolean = false
)

data class Stage(
    val name: String,
    val timeline: String,
    val tasks: List<Task>,
    var isCompleted: Boolean = false,
    var isUnlocked: Boolean = false
)

@Composable
fun JourneyScreen(navController: NavController) {
    val stages = remember {
        mutableStateListOf(
            Stage(
                name = "Stage 1",
                timeline = "1 Week",
                tasks = listOf(
                    Task("Task 1.1"),
                    Task("Task 1.2"),
                    Task("Task 1.3"),
                    Task("Task 1.4"),
                    Task("Task 1.5")
                ),
                isUnlocked = true
            ),
            Stage(
                name = "Stage 2",
                timeline = "1 Month",
                tasks = listOf(
                    Task("Task 2.1"),
                    Task("Task 2.2"),
                    Task("Task 2.3"),
                    Task("Task 2.4"),
                    Task("Task 2.5"),
                    Task("Task 2.6"),
                    Task("Task 2.7")
                )
            ),
            Stage(
                name = "Stage 3",
                timeline = "3 Months",
                tasks = listOf(
                    Task("Task 3.1"),
                    Task("Task 3.2"),
                    Task("Task 3.3"),
                    Task("Task 3.4"),
                    Task("Task 3.5"),
                    Task("Task 3.6"),
                    Task("Task 3.7"),
                    Task("Task 3.8")
                )
            ),
            Stage(
                name = "Stage 4",
                timeline = "6 Months",
                tasks = listOf(
                    Task("Task 4.1"),
                    Task("Task 4.2"),
                    Task("Task 4.3"),
                    Task("Task 4.4"),
                    Task("Task 4.5"),
                    Task("Task 4.6"),
                    Task("Task 4.7"),
                    Task("Task 4.8"),
                    Task("Task 4.9")
                )
            ),
            Stage(
                name = "Stage 5",
                timeline = "12 Months",
                tasks = listOf(
                    Task("Task 5.1"),
                    Task("Task 5.2"),
                    Task("Task 5.3"),
                    Task("Task 5.4"),
                    Task("Task 5.5"),
                    Task("Task 5.6"),
                    Task("Task 5.7"),
                    Task("Task 5.8"),
                    Task("Task 5.9"),
                    Task("Task 5.10")
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Journey",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(stages) { stage ->
                StageCard(stage = stage, onStageCompleted = { completedStage ->
                    val index = stages.indexOf(completedStage)
                    if (index < stages.size - 1) {
                        stages[index + 1].isUnlocked = true
                    }
                })
            }
        }
    }
}

@Composable
fun StageCard(stage: Stage, onStageCompleted: (Stage) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var allTasksCompleted by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing), label = ""
    )
    LaunchedEffect(key1 = stage.tasks) {
        allTasksCompleted = stage.tasks.all { it.isCompleted }
        if (allTasksCompleted) {
            stage.isCompleted = true
            onStageCompleted(stage)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (stage.isUnlocked) MaterialTheme.colorScheme.surfaceVariant else Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = stage.isUnlocked) {
                        isExpanded = !isExpanded
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!stage.isUnlocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = stage.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = if (stage.isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stage.timeline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (stage.isUnlocked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.outline
                    )
                    IconButton(
                        onClick = {
                            if (stage.isUnlocked) {
                                isExpanded = !isExpanded
                            }
                        },modifier = Modifier.rotate(rotationState)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expand",
                            tint = if (stage.isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded && stage.isUnlocked) {
                Column {
                    stage.tasks.forEach { task ->
                        TaskItem(task = task, onTaskCompleted = {
                            allTasksCompleted = stage.tasks.all { it.isCompleted }
                            if (allTasksCompleted) {
                                stage.isCompleted = true
                                onStageCompleted(stage)
                            }
                        })
                    }
                    val completedTasks = stage.tasks.count { it.isCompleted }
                    val progress = if (stage.tasks.isNotEmpty()) completedTasks.toFloat() / stage.tasks.size else 0f
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$completedTasks/${stage.tasks.size} Tasks Completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskCompleted: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                task.isCompleted = !task.isCompleted
                onTaskCompleted()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                task.isCompleted = it
                onTaskCompleted()
            },
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = task.name,
            style = MaterialTheme.typography.bodyLarge,
            color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
        )
    }
}