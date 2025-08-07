// src/main/java/com/coutinho/estereof/viewmodel/factory/TransactionsViewModelFactory.kt
package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.*
import com.coutinho.estereof.viewmodel.TransactionsViewModel

class TransactionsViewModelFactory(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionsViewModel(
                userRepository,
                transactionRepository,
                accountRepository,
                categoryRepository,
                paymentMethodRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}