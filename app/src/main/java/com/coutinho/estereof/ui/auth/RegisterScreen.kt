package com.coutinho.estereof.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.AuthState
import com.coutinho.estereof.viewmodel.AuthViewModel
import com.coutinho.estereof.viewmodel.factory.AuthViewModelFactory

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    loginAction: () -> Unit = {},
    registerSuccessAction: () -> Unit = {}
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val accountRepository = remember { AccountRepository(database.accountDao()) }

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(userRepository, accountRepository)
    )

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(key1 = registerState) {
        when (registerState) {
            is AuthState.Success -> {
                registerSuccessAction()
                viewModel.resetRegisterState()
            }
            is AuthState.Error -> {
                println("Erro: ${(registerState as AuthState.Error).message}")
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
        // Título "register"
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 32.dp)
        )

        // Campo de texto para email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = stringResource(R.string.register_email_label),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(0.12F),
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLabelColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPrefixColor = MaterialTheme.colorScheme.error,
                focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSuffixColor = MaterialTheme.colorScheme.error,
                textSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para nome
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = stringResource(R.string.register_name_label),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(0.12F),
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLabelColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPrefixColor = MaterialTheme.colorScheme.error,
                focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSuffixColor = MaterialTheme.colorScheme.error,
                textSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(R.string.login_password_label),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(0.12F),
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLabelColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPrefixColor = MaterialTheme.colorScheme.error,
                focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSuffixColor = MaterialTheme.colorScheme.error,
                textSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para senha
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = {
                Text(
                    text = stringResource(R.string.register_confirm_password_label),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(0.12F),
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorLabelColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorPrefixColor = MaterialTheme.colorScheme.error,
                focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.38F),
                errorSuffixColor = MaterialTheme.colorScheme.error,
                textSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de registro
        Button(
            onClick = { viewModel.register(name, email, password, confirmPassword) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = registerState !is AuthState.Loading
        ) {
            if (registerState is AuthState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(
                    text = stringResource(R.string.register_button_text),
                    fontFamily = spaceGroteskFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Link para "log in"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(R.string.register_login_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = loginAction,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.register_login_prompt_button),
                    fontFamily = spaceGroteskFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

fun actionSignUp() {
    println("Usuário registrado com sucesso!")
}