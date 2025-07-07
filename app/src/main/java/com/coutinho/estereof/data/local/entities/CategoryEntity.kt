package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entidade que representa a tabela 'categories' no banco de dados local.
 *
 * @property id ID único da categoria. É a chave primária e auto-gerada.
 * @property userId ID do usuário que criou esta categoria.
 * @property name Nome da categoria.
 * @property iconName Nome do recurso de ícone (e.g., "ic_food"). Pode ser nulo.
 * @property colorHex Código hexadecimal da cor para a categoria (e.g., "#FF0000"). Pode ser nulo.
 * @property type Tipo de transação associado à categoria (INCOME, EXPENSE, ou nulo se for genérica).
 * @property createdAt Data e hora de criação da categoria.
 * @property updatedAt Data e hora da última atualização da categoria.
 */
@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Se o usuário for deletado, suas categorias também são.
        )
    ],
    indices = [Index(value = ["userId"])] // Índice para otimizar consultas por userId
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // createdBy agora é userId, FK para UserEntity
    val name: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
