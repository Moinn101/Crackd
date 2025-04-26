package com.moin.crackd.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moin.crackd.navigation.Routes

@Composable
fun UserFormScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var examType by remember { mutableStateOf("NEET") }
    var attemptYear by remember { mutableStateOf("2026") }
    var studentClass by remember { mutableStateOf("11") }
    var coachingName by remember { mutableStateOf("") }
    var coachingMode by remember { mutableStateOf("Online") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 32.dp, end = 24.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Tell us about yourself",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Help us personalize your experience",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        DropdownField("Exam Type", listOf("NEET", "JEE"), examType) { examType = it }
        DropdownField("Next Attempt", listOf("2026", "2027"), attemptYear) { attemptYear = it }
        DropdownField("Class", listOf("10", "11", "12", "Repeater"), studentClass) { studentClass = it }

        OutlinedTextField(
            value = coachingName,
            onValueChange = { coachingName = it },
            label = { Text("Coaching Institute") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        DropdownField("Coaching Mode", listOf("Online", "Offline"), coachingMode) { coachingMode = it }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                val uid = auth.currentUser?.uid ?: return@Button
                val userData = mapOf(
                    "fullName" to fullName,
                    "mobile" to mobileNumber,
                    "examType" to examType,
                    "attemptYear" to attemptYear,
                    "studentClass" to studentClass,
                    "coaching" to coachingName,
                    "mode" to coachingMode
                )

                db.collection("users").document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        navController.navigate(Routes.Main) {
                            popUpTo(Routes.Form) { inclusive = true }
                        }
                    }
                    .addOnFailureListener {
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = fullName.isNotBlank() && mobileNumber.isNotBlank()
        ) {
            Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun DropdownField(label: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelect(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}