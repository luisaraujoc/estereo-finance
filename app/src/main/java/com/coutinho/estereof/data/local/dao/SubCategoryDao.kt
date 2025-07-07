package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade SubCategoryEntity.
 * Define os métodos para operações CRUD na tabela de subcategorias.
 */
@Dao
interface SubCategoryDao {

    /**
     * Insere uma nova subcategoria na base de dados.
     * @param subCategory O objeto SubCategoryEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategory(subCategory: SubCategoryEntity): Long

    /**
     * Atualiza uma subcategoria existente na base de dados.
     * @param subCategory O objeto SubCategoryEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updateSubCategory(subCategory: SubCategoryEntity): Int

    /**
     * Apaga uma subcategoria da base de dados.
     * @param subCategory O objeto SubCategoryEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deleteSubCategory(subCategory: SubCategoryEntity): Int

    /**
     * Procura uma subcategoria pelo seu ID.
     * @param subCategoryId O ID da subcategoria a ser procurada.
     * @return Um Flow que emite o SubCategoryEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM subcategories WHERE id = :subCategoryId")
    fun getSubCategoryById(subCategoryId: Int): Flow<SubCategoryEntity?>

    /**
     * Procura todas as subcategorias pertencentes a uma categoria pai específica.
     * @param parentCategoryId O ID da categoria pai.
     * @return Um Flow que emite uma lista de SubCategoryEntity.
     */
    @Query("SELECT * FROM subcategories WHERE parentCategory = :parentCategoryId ORDER BY name ASC")
    fun getSubCategoriesByParentCategory(parentCategoryId: Int): Flow<List<SubCategoryEntity>>

    /**
     * Procura todas as subcategorias criadas por um utilizador específico.
     * @param userId O ID do utilizador criador.
     * @return Um Flow que emite uma lista de SubCategoryEntity criadas pelo utilizador.
     */
    @Query("SELECT * FROM subcategories WHERE userId = :userId ORDER BY name ASC")
    fun getSubCategoriesByUserId(userId: Int): Flow<List<SubCategoryEntity>>

    /**
     * Procura todas as subcategorias na base de dados.
     * @return Um Flow que emite uma lista de todos os SubCategoryEntity.
     */
    @Query("SELECT * FROM subcategories ORDER BY name ASC")
    fun getAllSubCategories(): Flow<List<SubCategoryEntity>>
}
