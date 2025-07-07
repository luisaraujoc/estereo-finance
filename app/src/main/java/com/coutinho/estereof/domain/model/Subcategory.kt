package com.coutinho.estereof.domain.model

import java.util.Date

/**
 * Modelo de domínio para uma subcategoria de transação.
 */
data class SubCategory(
    val id: Long,
    val userId: String, // ID do utilizador (UUID)
    val name: String,
    val parentCategoryId: Long,
    val createdAt: Date,
    val updatedAt: Date
)
