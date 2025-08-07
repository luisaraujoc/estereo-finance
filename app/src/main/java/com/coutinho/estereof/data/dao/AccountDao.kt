package com.coutinho.estereof.data.dao

import androidx.room.*
import com.coutinho.estereof.data.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE userId = :userId")
    fun getByUserId(userId: Long): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getById(id: Long): Flow<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account): Long

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)
}
