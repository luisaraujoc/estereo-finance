package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.CategoryEntity
import com.coutinho.estereof.data.local.entities.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade CategoryEntity.
 * Define os métodos para operações CRUD na tabela de categorias.
 */
@Dao
interface CategoryDao {

    /**
     * Insere uma nova categoria na base de dados.
     * @param category O objeto CategoryEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    /**
     * Atualiza uma categoria existente na base de dados.
     * @param category O objeto CategoryEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updateCategory(category: CategoryEntity): Int

    /**
     * Apaga uma categoria da base de dados.
     * @param category O objeto CategoryEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deleteCategory(category: CategoryEntity): Int

    /**
     * Procura uma categoria pelo seu ID.
     * @param categoryId O ID da categoria a ser procurada.
     * @return Um Flow que emite o CategoryEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int): Flow<CategoryEntity?>

    /**
     * Procura todas as categorias criadas por um utilizador específico.
     * @param userId O ID do utilizador criador.
     * @return Um Flow que emite uma lista de CategoryEntity criadas pelo utilizador.
     */
    @Query("SELECT * FROM categories WHERE userId = :userId ORDER BY name ASC")
    fun getCategoriesByUserId(userId: Int): Flow<List<CategoryEntity>>

    /**
     * Procura todas as categorias na base de dados.
     * @return Um Flow que emite uma lista de todos os CategoryEntity.
     */
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    /**
     * Procura subcategorias de uma categoria pai específica.
     * @param parentCategoryId O ID da categoria pai.
     * @return Um Flow que emite uma lista de SubCategoryEntity.
     */
    @Query("SELECT * FROM subcategories WHERE parentCategory = :parentCategoryId ORDER BY name ASC")
    fun getSubcategoriesForCategory(parentCategoryId: Int): Flow<List<SubCategoryEntity>>
}
