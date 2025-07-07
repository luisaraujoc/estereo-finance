package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entidade que representa a tabela 'subcategories' no banco de dados local.
 *
 * @property id ID único da subcategoria. É a chave primária e auto-gerada.
 * @property userId ID do usuário que criou esta subcategoria.
 * @property name Nome da subcategoria.
 * @property parentCategoryId ID da categoria pai.
 * @property iconName Nome do recurso de ícone (e.g., "ic_fast_food"). Pode ser nulo.
 * @property colorHex Código hexadecimal da cor para a subcategoria. Pode ser nulo.
 * @property createdAt Data e hora de criação da subcategoria.
 * @property updatedAt Data e hora da última atualização da subcategoria.
 */
@Entity(
    tableName = "subcategories",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Se o usuário for deletado, suas subcategorias também são.
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentCategoryId"],
            onDelete = ForeignKey.CASCADE // Se a categoria pai for deletada, suas subcategorias também são.
        )
    ],
    indices = [
        Index(value = ["userId"]), // Índice para otimizar consultas por userId
        Index(value = ["parentCategoryId"]) // Índice para otimizar consultas por parentCategory
    ]
)
data class SubCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // createdBy agora é userId, FK para UserEntity
    val name: String,
    val parentCategoryId: Int, // id_category
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
