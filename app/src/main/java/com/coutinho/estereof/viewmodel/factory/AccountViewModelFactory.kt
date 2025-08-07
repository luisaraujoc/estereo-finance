package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.viewmodel.AccountViewModel

class AccountViewModelFactory(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(accountRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}