package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entidade que representa a tabela 'payment_methods' no banco de dados local.
 *
 * @property id ID único do método de pagamento. Chave primária e auto-gerada.
 * @property userId ID do usuário ao qual este método de pagamento pertence.
 * @property name Nome do método de pagamento (e.g., "Cartão de Crédito Nubank", "Dinheiro").
 * @property type Tipo do método de pagamento (e.g., CREDIT_CARD, CASH).
 * @property createdAt Data e hora de criação do método de pagamento.
 * @property updatedAt Data e hora da última atualização do método de pagamento.
 */
@Entity(
    tableName = "payment_methods",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Se o usuário for deletado, seus métodos de pagamento também são.
        )
    ],
    indices = [Index(value = ["userId"])] // Índice para otimizar consultas por usuário
)
data class PaymentMethodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // Chave estrangeira para UserEntity
    val name: String,
    val type: PaymentMethodType, // Usará TypeConverter
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
