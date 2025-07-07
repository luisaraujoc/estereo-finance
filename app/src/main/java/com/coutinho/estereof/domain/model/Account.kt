package com.coutinho.estereof.domain.model

import java.math.BigDecimal
import java.util.Date

/**
 * Modelo de domínio para uma conta financeira.
 */
data class Account(
    val id: Long,
    val userId: String, // ID do utilizador (UUID)
    val name: String,
    val balance: BigDecimal,
    val currency: String,
    val createdAt: Date,
    val updatedAt: Date
)
