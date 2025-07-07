package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'accounts' do Supabase.
 * Representa as contas financeiras do usuário.
 */
@Serializable
data class AccountRemoteModel(
    val id: Long,
    @SerialName("user_id")
    val userId: String, // UUID do Supabase (auth.users.id)
    val name: String,
    val balance: String, // NUMERIC(18,2) no PostgreSQL, representado como String para precisão
    val currency: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
