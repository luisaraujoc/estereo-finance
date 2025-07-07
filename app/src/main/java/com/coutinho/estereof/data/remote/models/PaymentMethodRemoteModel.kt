package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'payment_methods' do Supabase.
 * Representa os métodos de pagamento do usuário.
 */
@Serializable
data class PaymentMethodRemoteModel(
    val id: Long,
    @SerialName("user_id")
    val userId: String, // UUID do Supabase (auth.users.id)
    val name: String,
    val type: String, // ENUM 'payment_method_type_enum' no PostgreSQL, representado como String
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
