package com.moin.crackd.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.moin.crackd.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var alpha by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "infinite alpha")

    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha animation"
    )

    LaunchedEffect(true) {
        alpha = 1f
        delay(3000)
        if (FirebaseAuth.getInstance().currentUser != null) {
            navController.navigate(Routes.Main) {
                popUpTo(Routes.Splash) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.Login) {
                popUpTo(Routes.Splash) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Crackd",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(alpha)
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "“I will be truthful to myself throughout my journey and believe I am on the right path and success is already on the way.”",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 26.sp,
                modifier = Modifier.padding(horizontal = 32.dp).alpha(alpha)
            )
        }
    }
}