// src/main/java/com/coutinho/estereof/data/dao/AccountDao.kt
package com.coutinho.estereof.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.model.Account
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account): Long

    @Update
    suspend fun updateAccount(account: Account)

    @Query("SELECT * FROM accounts WHERE id = :accountId AND userId = :userId")
    fun getAccountById(accountId: Long, userId: Long): Flow<Account?>

    @Query("SELECT * FROM accounts WHERE userId = :userId")
    fun getAllAccountsForUser(userId: Long): Flow<List<Account>>

    @Query("UPDATE accounts SET balance = :newBalance WHERE id = :accountId AND userId = :userId")
    suspend fun updateAccountBalance(accountId: Long, userId: Long, newBalance: BigDecimal)

    @Query("DELETE FROM accounts WHERE id = :accountId AND userId = :userId")
    suspend fun deleteAccount(accountId: Long, userId: Long)
}
