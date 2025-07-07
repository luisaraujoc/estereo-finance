package com.coutinho.estereof.data.remote.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de dados remoto para a tabela 'profiles' do Supabase.
 * Representa as informações adicionais do perfil do usuário.
 */
@Serializable
data class ProfileRemoteModel(
    val id: String, // UUID do Supabase (auth.users.id)
    val name: String,
    val username: String,
    val email: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant
)
