package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.Account
import com.coutinho.estereof.data.model.Category
import com.coutinho.estereof.data.model.PaymentMethod
import com.coutinho.estereof.data.model.Transaction
import com.coutinho.estereof.data.model.enums.TransactionType
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.TransactionRepository
import com.coutinho.estereof.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date

// Classe de dados para a UI de transação
data class TransactionUi(
    val id: Long,
    val title: String,
    val categoryName: String,
    val amount: BigDecimal,
    val type: TransactionType
)

// Estado da tela inicial
data class HomeState(
    val isLoading: Boolean = true,
    val user: String = "Usuário",
    val generalBalance: BigDecimal = BigDecimal.ZERO,
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val totalExpense: BigDecimal = BigDecimal.ZERO,
    val transactions: List<TransactionUi> = emptyList(), // Usando TransactionUi para a lista
    val error: String? = null
)

class HomeViewModel(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(isLoading = true)
            try {
                val user = userRepository.getAll().firstOrNull()?.firstOrNull()

                if (user != null) {
                    val userId = user.id

                    combine(
                        accountRepository.getByUserId(userId),
                        transactionRepository.getByUserId(userId),
                        categoryRepository.getByUserId(userId)
                    ) { accounts, transactions, categories ->
                        val account = accounts.firstOrNull()
                        val filteredTransactions = filterTransactionsByCurrentMonth(transactions)
                        val (income, expense) = calculateIncomeAndExpense(filteredTransactions)

                        // CÁLCULO CORRIGIDO: Saldo geral é a diferença entre a renda e a despesa do mês.
                        val monthlyBalance = income - expense

                        val transactionsUi = filteredTransactions.map { transaction ->
                            val categoryName = categories.firstOrNull { it.id == transaction.categoryId }?.name ?: "Sem Categoria"
                            TransactionUi(
                                id = transaction.id,
                                title = transaction.title,
                                categoryName = categoryName,
                                amount = transaction.amount,
                                type = transaction.type
                            )
                        }

                        _homeState.value = _homeState.value.copy(
                            user = user.name,
                            generalBalance = monthlyBalance, // Usando o saldo mensal
                            totalIncome = income,
                            totalExpense = expense,
                            transactions = transactionsUi,
                            isLoading = false
                        )
                    }.collect {}
                } else {
                    _homeState.value = _homeState.value.copy(
                        isLoading = false,
                        error = "Nenhum usuário logado encontrado."
                    )
                }
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    isLoading = false,
                    error = "Ocorreu um erro ao carregar os dados: ${e.message}"
                )
            }
        }
    }

    private fun filterTransactionsByCurrentMonth(transactions: List<Transaction>): List<Transaction> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        return transactions.filter { transaction ->
            val transactionCalendar = Calendar.getInstance().apply {
                time = transaction.createdAt
            }
            transactionCalendar.get(Calendar.MONTH) == currentMonth &&
                    transactionCalendar.get(Calendar.YEAR) == currentYear
        }.sortedByDescending { it.createdAt }
    }

    private fun calculateIncomeAndExpense(transactions: List<Transaction>): Pair<BigDecimal, BigDecimal> {
        var income = BigDecimal.ZERO
        var expense = BigDecimal.ZERO
        for (transaction in transactions) {
            when (transaction.type) {
                TransactionType.INCOME -> income += transaction.amount
                TransactionType.EXPENSE -> expense += transaction.amount
            }
        }
        return Pair(income, expense)
    }

    suspend fun logout(): Boolean {
        return try {
            val user = userRepository.getAll().firstOrNull()?.firstOrNull()
            if (user != null) {
                userRepository.delete(user)
                true // Retorna true em caso de sucesso
            } else {
                false // Retorna false se não houver usuário logado
            }
        } catch (e: Exception) {
            _userMessage.value = "Erro ao tentar sair: ${e.message}"
            false // Retorna false em caso de erro
        }
    }

    fun setUserMessage(message: String?) {
        _userMessage.value = message
    }
}