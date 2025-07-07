package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entidade que representa a tabela 'profiles' no banco de dados local.
 * O ID é um String (UUID) para corresponder ao ID do usuário do Supabase.
 *
 * @property id ID único do usuário (UUID do Supabase). É a chave primária.
 * @property name Nome completo do usuário.
 * @property username Nome de usuário único.
 * @property email Endereço de e-mail do usuário.
 * @property createdAt Data e hora de criação do registro do usuário.
 * @property updatedAt Data e hora da última atualização do registro.
 */
@Entity(tableName = "profiles")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val username: String,
    val email: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
