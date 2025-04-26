package com.moin.crackd.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.moin.crackd.R

data class LeaderboardEntry(val name: String, val points: Int)

@Composable
fun DiscoveryScreen(navController: NavController) {
    val context = LocalContext.current
    val leaderboardData = remember {
        listOf(
            LeaderboardEntry("User 1", 1500),
            LeaderboardEntry("User 2", 1200),
            LeaderboardEntry("User 3", 1000),
            LeaderboardEntry("User 4", 900),
            LeaderboardEntry("User 5", 800),
        )
    }
    val showPaymentDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Discovery",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Scrollable content below title
        Column(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    DiscoveryCard(
                        title = "Share App",
                        description = "Share the app with friends and earn reward points!",
                        icon = Icons.Filled.Share,
                        imageResId = null,
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
                                putExtra(Intent.EXTRA_TEXT, "https://www.example.com/app")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                        }
                    )
                }

                item {
                    DiscoveryCard(
                        title = "Redeem Points",
                        description = "Redeem your reward points for discounts on renewal.",
                        icon = null,
                        imageResId = R.drawable.rewards,
                        onClick = {}
                    )
                }

                item {
                    DiscoveryCard(
                        title = "Subscription Status",
                        description = "View your current subscription plan status.",
                        icon = Icons.Filled.Star,
                        imageResId = null,
                        onClick = {}
                    )
                }

                item {
                    DiscoveryCard(
                        title = "Upgrade Plan",
                        description = "Upgrade to a paid plan for more features.",
                        icon = null,
                        imageResId = R.drawable.currency_exchange,
                        onClick = {
                            showPaymentDialog.value = true
                        }
                    )
                }

                item {
                    Text(
                        text = "Leaderboard",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Leaderboard(leaderboardData)
                }
            }
        }

        if (showPaymentDialog.value) {
            PaymentDialog(onDismiss = { showPaymentDialog.value = false })
        }
    }
}

@Composable
fun DiscoveryCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    imageResId: Int?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            } else if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Go",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun Leaderboard(leaderboardData: List<LeaderboardEntry>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            leaderboardData.forEach { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = entry.name, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = "${entry.points} pts", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Divider()
            }
        }
    }
}

@Composable
fun PaymentDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Upgrade to Paid Plan") },
        text = {
            Column {
                Text("Choose a payment method:")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    onDismiss()
                }) {
                    Text("Razorpay")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    onDismiss()
                }) {
                    Text("UPI")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
