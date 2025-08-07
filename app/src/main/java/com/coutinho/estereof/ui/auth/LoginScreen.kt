// src/main/java/com/coutinho/estereof/ui/auth/LoginScreen.kt
package com.coutinho.estereof.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.AuthViewModel
import com.coutinho.estereof.viewmodel.factory.AuthViewModelFactory // Verifique se o import está correto
import com.coutinho.estereof.viewmodel.AuthState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    signupAction: () -> Unit = {},
    loginSuccessAction: () -> Unit = {}
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val accountRepository = remember { AccountRepository(database.accountDao()) }

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(userRepository, accountRepository)
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = loginState) {
        when (loginState) {
            is AuthState.Success -> {
                loginSuccessAction()
                viewModel.resetLoginState()
            }
            is AuthState.Error -> {
                // Para simplificar, vou apenas imprimir no log
                println("Erro: ${(loginState as AuthState.Error).message}")
                viewModel.resetLoginState()
            }
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.login_email_label), color = MaterialTheme.colorScheme.onPrimaryContainer) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.login_password_label), color = MaterialTheme.colorScheme.onPrimaryContainer) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.login_button_text),
                fontFamily = spaceGroteskFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.login_register_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = signupAction,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_register_prompt_button),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }
    }
}