package com.coutinho.estereof.data.dao

import androidx.room.*
import com.coutinho.estereof.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY createdAt DESC")
    fun getByUserId(userId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getById(id: Long): Flow<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}
