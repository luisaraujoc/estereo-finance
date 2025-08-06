// src/main/java/com/coutinho/estereof/ui/auth/AuthViewModel.kt
package com.coutinho.estereof.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.User
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.util.Date

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Eventos para SnackBar (sucesso/erro)
    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    // Estado de autenticação (para navegação)
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun registerUser(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Validação de campos vazios
                if (!ValidationUtils.areRegisterFieldsFilled(email, name, password, confirmPassword)) {
                    _snackbarEvent.emit("Please fill in all fields.") // Usar stringResource aqui no Compose
                    return@launch
                }

                // 2. Validação de formato de e-mail
                if (!ValidationUtils.isValidEmail(email)) {
                    _snackbarEvent.emit("Please enter a valid email address.") // Usar stringResource
                    return@launch
                }

                // 3. Validação de senhas que não coincidem
                if (!ValidationUtils.passwordsMatch(password, confirmPassword)) {
                    _snackbarEvent.emit("Passwords do not match.") // Usar stringResource
                    return@launch
                }

                // 4. Validação de complexidade da senha
                if (!ValidationUtils.isValidPassword(password)) {
                    _snackbarEvent.emit("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.") // Usar stringResource
                    return@launch
                }

                // 5. Verificar se o email já está em uso
                val existingUser = userRepository.getUserByEmail(email).firstOrNull()
                if (existingUser != null) {
                    _snackbarEvent.emit("Email is already registered.") // Adicionar ao strings.xml
                    return@launch
                }

                // 6. Criptografar a senha
                val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

                // 7. Criar e inserir o usuário
                val newUser = User(
                    name = name,
                    username = email, // Usando email como username por simplicidade, ajuste se necessário
                    email = email,
                    passwordHash = hashedPassword,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                userRepository.insertUser(newUser)
                _snackbarEvent.emit("Registration successful!") // Usar stringResource
                // Opcional: navegar para a tela de login ou fazer login automático
                // _isAuthenticated.value = true
            } catch (e: Exception) {
                _snackbarEvent.emit("Registration failed: ${e.message}") // Usar stringResource
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loginUser(emailOrUsername: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Validação de campos vazios
                if (!ValidationUtils.areLoginFieldsFilled(emailOrUsername, password)) {
                    _snackbarEvent.emit("Please fill in all fields.") // Usar stringResource
                    return@launch
                }

                // 2. Buscar usuário por email ou username
                val userByEmail = userRepository.getUserByEmail(emailOrUsername).firstOrNull()
                val userByUsername = userRepository.getUserByUsername(emailOrUsername).firstOrNull()

                val user = userByEmail ?: userByUsername

                if (user == null) {
                    _snackbarEvent.emit("Invalid credentials.") // Usar stringResource
                    return@launch
                }

                // 3. Verificar a senha
                if (BCrypt.checkpw(password, user.passwordHash)) {
                    _snackbarEvent.emit("Login successful!") // Usar stringResource
                    _isAuthenticated.value = true // Atualiza o estado de autenticação
                } else {
                    _snackbarEvent.emit("Invalid credentials.") // Usar stringResource
                }
            } catch (e: Exception) {
                _snackbarEvent.emit("Login failed: ${e.message}") // Usar stringResource
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Factory para instanciar o ViewModel com UserRepository
    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
