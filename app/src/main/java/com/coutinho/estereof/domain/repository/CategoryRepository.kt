package com.coutinho.estereof.domain.repository

import com.coutinho.estereof.domain.model.Category
import com.coutinho.estereof.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    /**
     * Obtém uma categoria pelo seu ID.
     * @param categoryId O ID da categoria.
     * @return Um Flow que emite a categoria correspondente, ou null se não encontrada.
     */
    fun getCategoryById(categoryId: Long): Flow<Category?>

    /**
     * Obtém todas as categorias de um utilizador específico.
     * @param userId O ID do utilizador (UUID).
     * @return Um Flow que emite uma lista de categorias do utilizador.
     */
    fun getCategoriesByUserId(userId: String): Flow<List<Category>>

    /**
     * Obtém subcategorias de uma categoria pai específica.
     * @param parentCategoryId O ID da categoria pai.
     * @return Um Flow que emite uma lista de subcategorias.
     */
    fun getSubcategoriesForCategory(parentCategoryId: Long): Flow<List<SubCategory>>

    /**
     * Obtém uma subcategoria pelo seu ID.
     * @param subcategoryId O ID da subcategoria.
     * @return Um Flow que emite a subcategoria correspondente, ou null se não encontrada.
     */
    fun getSubCategoryById(subcategoryId: Long): Flow<SubCategory?>

    /**
     * Insere uma nova categoria.
     * @param category A categoria a ser inserida.
     * @return O ID da categoria inserida.
     */
    suspend fun insertCategory(category: Category): Long

    /**
     * Atualiza uma categoria existente.
     * @param category A categoria a ser atualizada.
     */
    suspend fun updateCategory(category: Category)

    /**
     * Apaga uma categoria.
     * @param category A categoria a ser apagada.
     */
    suspend fun deleteCategory(category: Category)

    /**
     * Insere uma nova subcategoria.
     * @param subCategory A subcategoria a ser inserida.
     * @return O ID da subcategoria inserida.
     */
    suspend fun insertSubCategory(subCategory: SubCategory): Long

    /**
     * Atualiza uma subcategoria existente.
     * @param subCategory A subcategoria a ser atualizada.
     */
    suspend fun updateSubCategory(subCategory: SubCategory)

    /**
     * Apaga uma subcategoria.
     * @param subCategory A subcategoria a ser apagada.
     */
    suspend fun deleteSubCategory(subCategory: SubCategory)

    /**
     * Sincroniza as categorias e subcategorias de um utilizador com o Supabase.
     * @param userId O ID do utilizador (UUID) para o qual as categorias serão sincronizadas.
     */
    suspend fun syncUserCategories(userId: String)
}
