package com.coutinho.estereof.domain.model

import java.util.Date

/**
 * Modelo de domínio para um método de pagamento.
 */
data class PaymentMethod(
    val id: Long,
    val userId: String, // ID do utilizador (UUID)
    val name: String,
    val type: PaymentMethodType, // Usará o enum local
    val createdAt: Date,
    val updatedAt: Date
)

// Enum de domínio para o tipo de método de pagamento
enum class PaymentMethodType {
    CREDIT_CARD,
    DEBIT_CARD,
    CASH,
    PIX,
    BANK_TRANSFER,
    OTHER
}
