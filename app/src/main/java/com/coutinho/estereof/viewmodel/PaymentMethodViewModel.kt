package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.PaymentMethod
import com.coutinho.estereof.data.model.enums.PaymentMethodType
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Date

data class PaymentMethodState(
    val isLoading: Boolean = true,
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val error: String? = null
)

class PaymentMethodViewModel(
    private val paymentMethodRepository: PaymentMethodRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _paymentMethodState = MutableStateFlow(PaymentMethodState())
    val paymentMethodState: StateFlow<PaymentMethodState> = _paymentMethodState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private var loggedInUserId: Long? = null

    init {
        loadPaymentMethods()
    }

    private fun loadPaymentMethods() {
        viewModelScope.launch {
            _paymentMethodState.value = _paymentMethodState.value.copy(isLoading = true)
            try {
                val user = userRepository.getAll().firstOrNull()?.firstOrNull()
                if (user != null) {
                    loggedInUserId = user.id
                    paymentMethodRepository.getByUserId(user.id).collect { methods ->
                        _paymentMethodState.value = _paymentMethodState.value.copy(
                            paymentMethods = methods,
                            isLoading = false
                        )
                    }
                } else {
                    _paymentMethodState.value = _paymentMethodState.value.copy(
                        isLoading = false,
                        error = "Nenhum usuário logado encontrado."
                    )
                }
            } catch (e: Exception) {
                _paymentMethodState.value = _paymentMethodState.value.copy(
                    isLoading = false,
                    error = "Erro ao carregar métodos de pagamento: ${e.message}"
                )
            }
        }
    }

    fun addPaymentMethod(name: String, type: PaymentMethodType) {
        if (loggedInUserId == null) {
            _userMessage.value = "Erro: Usuário não logado."
            return
        }

        viewModelScope.launch {
            try {
                val newMethod = PaymentMethod(
                    userId = loggedInUserId!!,
                    name = name,
                    type = type,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                paymentMethodRepository.insert(newMethod)
                _userMessage.value = "Método de pagamento adicionado com sucesso!"
            } catch (e: Exception) {
                _userMessage.value = "Erro ao adicionar método de pagamento: ${e.message}"
            }
        }
    }

    fun deletePaymentMethod(method: PaymentMethod) {
        viewModelScope.launch {
            try {
                paymentMethodRepository.delete(method)
                _userMessage.value = "Método de pagamento excluído com sucesso."
            } catch (e: Exception) {
                _userMessage.value = "Erro ao excluir método de pagamento: ${e.message}"
            }
        }
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}