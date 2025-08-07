// src/main/java/com/coutinho/estereof/viewmodel/factory/AuthViewModelFactory.kt
package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.viewmodel.AuthViewModel

class AuthViewModelFactory(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userRepository, accountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}