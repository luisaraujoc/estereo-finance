// src/main/java/com/coutinho/estereof/viewmodel/AuthViewModel.kt
package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.Account
import com.coutinho.estereof.data.model.User
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.utils.ValidationUtils
import com.coutinho.estereof.utils.checkPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                if (!ValidationUtils.areLoginFieldsFilled(email, password)) {
                    _loginState.value = AuthState.Error("Preencha todos os campos.")
                    return@launch
                }
                val user = userRepository.login(email, password)
                if (user != null) {
                    _loginState.value = AuthState.Success(user)
                } else {
                    _loginState.value = AuthState.Error("Email ou senha incorretos.")
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "Ocorreu um erro no login.")
            }
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading

            // Adicionando as validações
            if (!ValidationUtils.areRegisterFieldsFilled(email, name, password, confirmPassword)) {
                _registerState.value = AuthState.Error("Preencha todos os campos.")
                return@launch
            }
            if (!ValidationUtils.isValidEmail(email)) {
                _registerState.value = AuthState.Error("Formato de email inválido.")
                return@launch
            }
            if (!ValidationUtils.isValidPassword(password)) {
                _registerState.value = AuthState.Error("A senha deve ter pelo menos 8 caracteres, 1 número, 1 letra maiúscula, 1 minúscula e 1 caractere especial.")
                return@launch
            }
            if (!ValidationUtils.passwordsMatch(password, confirmPassword)) {
                _registerState.value = AuthState.Error("As senhas não coincidem.")
                return@launch
            }

            try {
                // Insere o novo usuário e obtém o ID
                val userId = userRepository.register(name, email, password)

                // Cria uma conta padrão para o novo usuário
                val newAccount = Account(
                    userId = userId,
                    name = "Main Account", // Nome padrão para a conta
                    balance = BigDecimal.ZERO,
                    currency = "BRL", // Moeda padrão
                    createdAt = Date(),
                    updatedAt = Date()
                )
                accountRepository.insert(newAccount)

                // A navegação será acionada na tela após o sucesso
                _registerState.value = AuthState.Success(User(id = userId, name = name, email = email, username = email.substringBefore("@"), passwordHash = "", createdAt = Date(), updatedAt = Date()))

            } catch (e: IllegalArgumentException) {
                _registerState.value = AuthState.Error(e.message ?: "Email já registrado.")
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.message ?: "Ocorreu um erro no registro.")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = AuthState.Idle
    }

    fun resetRegisterState() {
        _registerState.value = AuthState.Idle
    }
}