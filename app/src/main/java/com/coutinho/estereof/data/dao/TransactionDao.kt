// src/main/java/com/coutinho/estereof/data/dao/TransactionDao.kt
package com.coutinho.estereof.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :transactionId AND userId = :userId")
    fun getTransactionById(transactionId: Long, userId: Long): Flow<Transaction?>

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllTransactionsForUser(userId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND type = 'INCOME' ORDER BY createdAt DESC")
    fun getIncomeTransactionsForUser(userId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND type = 'EXPENSE' ORDER BY createdAt DESC")
    fun getExpenseTransactionsForUser(userId: Long): Flow<List<Transaction>>

    @Query("DELETE FROM transactions WHERE id = :transactionId AND userId = :userId")
    suspend fun deleteTransaction(transactionId: Long, userId: Long)
}
