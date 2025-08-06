// src/main/java/com/coutinho/estereof/data/repository/CategoryRepository.kt
package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.CategoryDao
import com.coutinho.estereof.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    fun getCategoryById(categoryId: Long, userId: Long): Flow<Category?> {
        return categoryDao.getCategoryById(categoryId, userId)
    }

    fun getAllCategoriesForUser(userId: Long): Flow<List<Category>> {
        return categoryDao.getAllCategoriesForUser(userId)
    }

    suspend fun deleteCategory(categoryId: Long, userId: Long) {
        categoryDao.deleteCategory(categoryId, userId)
    }
}
