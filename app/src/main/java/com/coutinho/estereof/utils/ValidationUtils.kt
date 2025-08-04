package com.coutinho.estereof.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coutinho.estereof.R

// validação de senha
fun passwordIsValid(password: String, confirmPassword: String): Boolean {
    val regex = Regex(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!?]).{8,}\$"
    )
    return password == confirmPassword && regex.matches(password)
}

// Verificando se os campos estão preenchidos
fun fieldsAreFilled(email: String, name: String, password: String, confirmPassword: String): Boolean {
    return email.isNotBlank() && name.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
}