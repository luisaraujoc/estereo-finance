package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

/**
 * Entidade que representa a tabela 'accounts' no banco de dados local.
 *
 * @property id ID único da conta. Chave primária e auto-gerada.
 * @property userId ID do usuário ao qual esta conta pertence (UUID do Supabase).
 * @property name Nome da conta (e.g., "Conta Corrente Santander").
 * @property balance Saldo atual da conta.
 * @property currency Moeda da conta (e.g., "BRL", "USD").
 * @property createdAt Data e hora de criação da conta.
 * @property updatedAt Data e hora da última atualização da conta.
 */
@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String,
    val balance: BigDecimal,
    val currency: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
