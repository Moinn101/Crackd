package com.moin.crackd.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.moin.crackd.R
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController) {
    val taskCompletion = remember {
        mutableStateMapOf(
            "Morning Check-in" to false,
            "Evening Check-in" to false,
            "Morning Audio" to false,
            "Evening Audio" to false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Page",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                iconRes = R.drawable.ic_stage,
                label = "Current",
                value = "Stage 3",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                iconRes = R.drawable.ic_points,
                label = "Total Points",
                value = "1,200",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                iconRes = R.drawable.ic_streak,
                label = "Streak",
                value = "5",
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                TaskCardCombined(
                    title = "Daily Check-ins",
                    subTasks = listOf(
                        "Morning Check-in" to taskCompletion["Morning Check-in"]!!,
                        "Evening Check-in" to taskCompletion["Evening Check-in"]!!
                    ),
                    onSubTaskComplete = { taskCompletion[it] = true },
                    leadingIcon = { Text("☀️", fontSize = 28.sp) }
                )
            }

            item {
                TaskCardCombined(
                    title = "Daily Coaching Audio",
                    subTasks = listOf(
                        "Morning Audio" to taskCompletion["Morning Audio"]!!,
                        "Evening Audio" to taskCompletion["Evening Audio"]!!
                    ),
                    onSubTaskComplete = { taskCompletion[it] = true },
                    leadingIcon = { Text("🎧", fontSize = 28.sp) }
                )
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Emotional Well-being",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Mood Meter",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "How are you feeling today?",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                MoodEmoji(emoji = "😊", size = 22.dp)
                                MoodEmoji(emoji = "😐", size = 22.dp)
                                MoodEmoji(emoji = "😟", size = 22.dp)
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_eq_lessons),
                                    contentDescription = "EQ Lessons Icon",
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "EQ Lessons",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Short videos to boost your EQ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = "Go to EQ Lessons",
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCardCombined(
    title: String,
    subTasks: List<Pair<String,Boolean>>,
    onSubTaskComplete: (String) -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                leadingIcon()

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            subTasks.forEach { (label, isDone) ->
                TaskItem(label = label, isDone = isDone, onComplete = onSubTaskComplete)
            }
        }
    }
}

@Composable
fun TaskItem(label: String, isDone: Boolean, onComplete: (String) -> Unit) {
    var showConfetti by remember { mutableStateOf(false) }
    var confettiScale by remember { mutableStateOf(0f) }
    val animatedConfettiScale by animateFloatAsState(
        targetValue = confettiScale,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
    )
    LaunchedEffect(key1 = showConfetti) {
        if (showConfetti) {
            confettiScale = 1.5f
            delay(500)
            confettiScale = 0f
            showConfetti = false
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            visible = showConfetti,
            enter = fadeIn(animationSpec = tween(durationMillis = 200)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Yellow)
                    .scale(animatedConfettiScale)
            ) {
                Text(text = "🎉", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (!isDone) {
                        onComplete(label)
                        showConfetti = true
                    }
                }
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            Icon(
                imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.MailOutline,
                contentDescription = null,
                tint = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun StatCard(
    iconRes: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Text(text = value, fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
            Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
fun MoodEmoji(emoji: String, size: Dp = 24.dp) {
    Text(
        text = emoji,
        fontSize = size.value.sp,
        modifier = Modifier
            .clickable { }
            .padding(4.dp)
    )
}