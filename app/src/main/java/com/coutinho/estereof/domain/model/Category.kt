package com.coutinho.estereof.domain.model

import java.util.Date

/**
 * Modelo de domínio para uma categoria de transação.
 */
data class Category(
    val id: Long,
    val userId: String, // ID do utilizador (UUID)
    val name: String,
    val createdAt: Date,
    val updatedAt: Date
)

// Enum de domínio para o tipo de transação
enum class TransactionType {
    INCOME,
    EXPENSE
}
