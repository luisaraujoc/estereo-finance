// src/main/java/com/coutinho/estereof/data/dao/CategoryDao.kt
package com.coutinho.estereof.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM categories WHERE id = :categoryId AND userId = :userId")
    fun getCategoryById(categoryId: Long, userId: Long): Flow<Category?>

    @Query("SELECT * FROM categories WHERE userId = :userId")
    fun getAllCategoriesForUser(userId: Long): Flow<List<Category>>

    @Query("DELETE FROM categories WHERE id = :categoryId AND userId = :userId")
    suspend fun deleteCategory(categoryId: Long, userId: Long)
}
