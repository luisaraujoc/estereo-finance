package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.*
import com.coutinho.estereof.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                userRepository,
                accountRepository,
                transactionRepository,
                categoryRepository,
                paymentMethodRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}