package com.coutinho.estereofinance.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coutinho.estereofinance.utils.Strings
import com.coutinho.estereofinance.utils.StringRes

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = Strings.get(StringRes.LOGIN_TITLE),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(Strings.get(StringRes.LOGIN_EMAIL_LABEL)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(Strings.get(StringRes.LOGIN_PASSWORD_LABEL)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                isLoading.value = true
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            enabled = !isLoading.value
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(Strings.get(StringRes.LOGIN_BUTTON_TEXT))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(Strings.get(StringRes.LOGIN_REGISTER_PROMPT))
            TextButton(onClick = onNavigateToRegister, shape = RoundedCornerShape(4.dp),) {
                Text(
                    Strings.get(StringRes.LOGIN_REGISTER_PROMPT_BUTTON),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ))
            }
        }
    }
}