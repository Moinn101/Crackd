package com.moin.crackd.ui.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.moin.crackd.navigation.Routes
import com.moin.crackd.R

@Composable
fun GoogleSignInScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(key1 = true) {
        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            db.collection("users").document(uid)
                .get().addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        navController.navigate(Routes.Main) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.Form) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { taskResult ->
                    if (taskResult.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        db.collection("users").document(uid)
                            .get().addOnSuccessListener { doc ->
                                if (doc.exists()) {
                                    navController.navigate(Routes.Main) {
                                        popUpTo(Routes.Login) { inclusive = true }
                                    }
                                } else {
                                    navController.navigate(Routes.Form) {
                                        popUpTo(Routes.Login) { inclusive = true }
                                    }
                                }
                            }.addOnFailureListener {
                                it.printStackTrace()
                            }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val signInRequest = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("930154829741-huf9o14920s0vi9ag4082j7o04t3akg8.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, signInRequest)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome to Crackd",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in to continue your journey",
            fontSize = 16.sp,color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Sign in with Google",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}