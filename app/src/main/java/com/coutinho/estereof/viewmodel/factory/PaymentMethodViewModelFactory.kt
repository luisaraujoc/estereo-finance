package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.viewmodel.PaymentMethodViewModel

class PaymentMethodViewModelFactory(
    private val paymentMethodRepository: PaymentMethodRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentMethodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentMethodViewModel(paymentMethodRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}