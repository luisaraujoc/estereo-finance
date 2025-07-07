package com.coutinho.estereof.domain.model

import java.math.BigDecimal
import java.util.Date

/**
 * Modelo de domínio para uma transação financeira.
 */
data class Transaction(
    val id: Long,
    val userId: String, // ID do utilizador (UUID)
    val accountId: Long,
    val paymentMethodId: Long? = null,
    val title: String,
    val description: String,
    val amount: BigDecimal,
    val type: TransactionType, // Usará o enum local
    val categoryId: Long,
    val subcategoryId: Long? = null,
    val isDivided: Boolean,
    val installments: Int? = null,
    val isRecurring: Boolean = false,
    val recurrenceInterval: RecurrenceInterval? = null, // Usará o enum local
    val recurrenceEndDate: Date? = null,
    val startDate: Date,
    val endDate: Date? = null,
    val status: TransactionStatus, // Usará o enum local
    val createdAt: Date,
    val updatedAt: Date
)

// Enum de domínio para o intervalo de recorrência
enum class RecurrenceInterval {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

// Enum de domínio para o status da transação
enum class TransactionStatus {
    PENDING,
    PAID,
    CANCELED
}
