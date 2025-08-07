// src/main/java/com/coutinho/estereof/viewmodel/TransactionsViewModel.kt
package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.Category
import com.coutinho.estereof.data.model.PaymentMethod
import com.coutinho.estereof.data.model.Transaction
import com.coutinho.estereof.data.model.enums.TransactionType
import com.coutinho.estereof.data.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import java.util.Calendar

// Estado para as telas de transações
data class TransactionsState(
    val isLoading: Boolean = true,
    val transactions: List<Transaction> = emptyList(),
    val categories: List<Category> = emptyList(),
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val selectedTransaction: Transaction? = null,
    val error: String? = null
)

class TransactionsViewModel(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ViewModel() {

    private val _transactionsState = MutableStateFlow(TransactionsState())
    val transactionsState: StateFlow<TransactionsState> = _transactionsState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private var loggedInUserId: Long? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _transactionsState.value = _transactionsState.value.copy(isLoading = true)
            try {
                val user = userRepository.getAll().firstOrNull()?.firstOrNull()

                if (user != null) {
                    loggedInUserId = user.id

                    // Combine os flows para buscar todos os dados de uma vez
                    combine(
                        transactionRepository.getByUserId(user.id),
                        categoryRepository.getByUserId(user.id),
                        paymentMethodRepository.getByUserId(user.id)
                    ) { transactions, categories, paymentMethods ->
                        _transactionsState.value = _transactionsState.value.copy(
                            transactions = transactions,
                            categories = categories,
                            paymentMethods = paymentMethods,
                            isLoading = false
                        )
                    }.collect()

                } else {
                    _transactionsState.value = _transactionsState.value.copy(
                        isLoading = false,
                        error = "Nenhum usuário logado encontrado."
                    )
                }
            } catch (e: Exception) {
                _transactionsState.value = _transactionsState.value.copy(
                    isLoading = false,
                    error = "Ocorreu um erro ao carregar os dados: ${e.message}"
                )
            }
        }
    }

    fun getTransactionDetails(transactionId: Long) {
        viewModelScope.launch {
            _transactionsState.value = _transactionsState.value.copy(isLoading = true)
            try {
                val transaction = transactionRepository.getById(transactionId).firstOrNull()
                _transactionsState.value = _transactionsState.value.copy(
                    selectedTransaction = transaction,
                    isLoading = false
                )
            } catch (e: Exception) {
                _transactionsState.value = _transactionsState.value.copy(
                    isLoading = false,
                    error = "Erro ao buscar detalhes da transação: ${e.message}"
                )
            }
        }
    }

    fun addNewTransaction(
        title: String,
        description: String,
        categoryId: Long?,
        paymentMethodId: Long,
        amount: String,
        date: Date,
        isExpense: Boolean
    ) {
        if (loggedInUserId == null) {
            _userMessage.value = "Erro: Usuário não logado."
            return
        }

        viewModelScope.launch {
            try {
                val amountBigDecimal = BigDecimal(amount)
                val account = accountRepository.getByUserId(loggedInUserId!!).first().firstOrNull()

                if (account == null) {
                    _userMessage.value = "Erro: Nenhuma conta encontrada para o usuário."
                    return@launch
                }

                val newTransaction = Transaction(
                    userId = loggedInUserId!!,
                    accountId = account.id,
                    categoryId = categoryId,
                    paymentMethodId = paymentMethodId,
                    title = title,
                    description = description,
                    amount = amountBigDecimal,
                    type = if (isExpense) TransactionType.EXPENSE else TransactionType.INCOME,
                    createdAt = date,
                    updatedAt = date
                )

                val transactionId = transactionRepository.insert(newTransaction)
                val updatedBalance = if (isExpense) {
                    account.balance - amountBigDecimal
                } else {
                    account.balance + amountBigDecimal
                }
                val updatedAccount = account.copy(balance = updatedBalance)
                accountRepository.update(updatedAccount)

                _userMessage.value = "Transação adicionada com sucesso!"
                loadData()
            } catch (e: NumberFormatException) {
                _userMessage.value = "O valor inserido não é um número válido."
            } catch (e: Exception) {
                _userMessage.value = "Erro ao adicionar a transação: ${e.message}"
            }
        }
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}