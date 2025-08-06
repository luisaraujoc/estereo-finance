// src/main/java/com/coutinho/estereof/utils/ValidationUtils.kt
package com.coutinho.estereof.utils

import android.util.Patterns

object ValidationUtils { // Mudado para object para acesso direto

    // Validação de formato de e-mail
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validação de senha
    fun isValidPassword(password: String): Boolean {
        // Pelo menos 8 caracteres, 1 número, 1 letra maiúscula, 1 letra minúscula, 1 caracter especial
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).{8,}$")
        return password.matches(passwordRegex)
    }

    // Verifica se as senhas coincidem
    fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    // Verificando se os campos de registro estão preenchidos
    fun areRegisterFieldsFilled(email: String, name: String, password: String, confirmPassword: String): Boolean {
        return email.isNotBlank() && name.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
    }

    // Verificando se os campos de login estão preenchidos
    fun areLoginFieldsFilled(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}
