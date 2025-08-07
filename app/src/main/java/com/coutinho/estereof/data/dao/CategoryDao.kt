package com.coutinho.estereof.data.dao

import androidx.room.*
import com.coutinho.estereof.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE userId = :userId")
    fun getByUserId(userId: Long): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getById(id: Long?): Flow<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)
}
