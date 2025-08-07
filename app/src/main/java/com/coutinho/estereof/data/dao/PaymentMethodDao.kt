package com.coutinho.estereof.data.dao

import androidx.room.*
import com.coutinho.estereof.data.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentMethodDao {

    @Query("SELECT * FROM payment_methods WHERE userId = :userId")
    fun getByUserId(userId: Long): Flow<List<PaymentMethod>>

    @Query("SELECT * FROM payment_methods WHERE id = :id")
    fun getById(id: Long): Flow<PaymentMethod>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(method: PaymentMethod): Long

    @Update
    suspend fun update(method: PaymentMethod)

    @Delete
    suspend fun delete(method: PaymentMethod)
}
