package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.CategoryDao
import com.coutinho.estereof.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val dao: CategoryDao) {

    fun getByUserId(userId: Long): Flow<List<Category>> = dao.getByUserId(userId)

    fun getById(id: Long?): Flow<Category> = dao.getById(id)

    suspend fun insert(category: Category): Long = dao.insert(category)

    suspend fun update(category: Category) = dao.update(category)

    suspend fun delete(category: Category) = dao.delete(category)
}
