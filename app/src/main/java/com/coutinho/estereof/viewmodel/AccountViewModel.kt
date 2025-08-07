package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.Account
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date

// Estado para a tela de contas
data class AccountState(
    val isLoading: Boolean = true,
    val account: Account? = null,
    val error: String? = null
)

class AccountViewModel(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _accountState = MutableStateFlow(AccountState())
    val accountState: StateFlow<AccountState> = _accountState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private var loggedInUserId: Long? = null

    init {
        loadAccount()
    }

    private fun loadAccount() {
        viewModelScope.launch {
            _accountState.value = _accountState.value.copy(isLoading = true)
            try {
                val user = userRepository.getAll().firstOrNull()?.firstOrNull()
                if (user != null) {
                    loggedInUserId = user.id
                    accountRepository.getByUserId(user.id).collect { accounts ->
                        _accountState.value = _accountState.value.copy(
                            account = accounts.firstOrNull(),
                            isLoading = false
                        )
                    }
                } else {
                    _accountState.value = _accountState.value.copy(
                        isLoading = false,
                        error = "Nenhum usuário logado encontrado."
                    )
                }
            } catch (e: Exception) {
                _accountState.value = _accountState.value.copy(
                    isLoading = false,
                    error = "Ocorreu um erro ao carregar a conta: ${e.message}"
                )
            }
        }
    }

    fun createAccount(name: String, initialBalance: BigDecimal, currency: String) {
        if (loggedInUserId == null) {
            _userMessage.value = "Erro: Usuário não logado."
            return
        }
        viewModelScope.launch {
            try {
                val newAccount = Account(
                    userId = loggedInUserId!!,
                    name = name,
                    balance = initialBalance,
                    currency = currency,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                accountRepository.insert(newAccount)
                _userMessage.value = "Conta criada com sucesso!"
            } catch (e: Exception) {
                _userMessage.value = "Erro ao criar a conta: ${e.message}"
            }
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            try {
                accountRepository.update(account)
                _userMessage.value = "Conta atualizada com sucesso!"
            } catch (e: Exception) {
                _userMessage.value = "Erro ao atualizar a conta: ${e.message}"
            }
        }
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}