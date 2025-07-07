package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'categories' do Supabase.
 * Representa as categorias de transação.
 */
@Serializable
data class CategoryRemoteModel(
    val id: Long,
    @SerialName("user_id")
    val userId: String, // UUID do Supabase (auth.users.id)
    val name: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
