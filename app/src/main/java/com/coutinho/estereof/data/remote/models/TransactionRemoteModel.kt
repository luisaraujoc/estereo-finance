package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'transactions' do Supabase.
 * Representa as transações financeiras.
 */
@Serializable
data class TransactionRemoteModel(
    val id: Long,
    @SerialName("user_id")
    val userId: String, // UUID do Supabase (auth.users.id)
    @SerialName("account_id")
    val accountId: Long,
    @SerialName("payment_method_id")
    val paymentMethodId: Long? = null,
    val title: String,
    val description: String,
    val amount: String, // NUMERIC(18,2) no PostgreSQL, representado como String para precisão
    val type: String, // ENUM 'transaction_type_enum' no PostgreSQL, representado como String
    @SerialName("category_id")
    val categoryId: Long,
    @SerialName("subcategory_id")
    val subcategoryId: Long? = null,
    @SerialName("is_divided")
    val isDivided: Boolean,
    val installments: Int? = null,
    @SerialName("is_recurring")
    val isRecurring: Boolean = false,
    @SerialName("recurrence_interval")
    val recurrenceInterval: String? = null, // ENUM 'recurrence_interval_enum' no PostgreSQL, representado como String
    @SerialName("recurrence_end_date")
    val recurrenceEndDate: Instant? = null,
    @SerialName("start_date")
    val startDate: Instant,
    @SerialName("end_date")
    val endDate: Instant? = null,
    val status: String, // ENUM 'transaction_status_enum' no PostgreSQL, representado como String
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
