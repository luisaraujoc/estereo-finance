package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'subcategories' do Supabase.
 * Representa as subcategorias de transação.
 */
@Serializable
data class SubCategoryRemoteModel(
    val id: Long,
    @SerialName("user_id")
    val userId: String, // UUID do Supabase (auth.users.id)
    val name: String,
    @SerialName("parent_category_id")
    val parentCategoryId: Long,
    @SerialName("icon_name")
    val iconName: String? = null,
    @SerialName("color_hex")
    val colorHex: String? = null,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
