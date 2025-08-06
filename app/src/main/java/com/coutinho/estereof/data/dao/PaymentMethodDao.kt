// src/main/java/com/coutinho/estereof/data/dao/PaymentMethodDao.kt
package com.coutinho.estereof.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentMethodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long

    @Update
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod)

    @Query("SELECT * FROM payment_methods WHERE id = :paymentMethodId AND userId = :userId")
    fun getPaymentMethodById(paymentMethodId: Long, userId: Long): Flow<PaymentMethod?>

    @Query("SELECT * FROM payment_methods WHERE userId = :userId")
    fun getAllPaymentMethodsForUser(userId: Long): Flow<List<PaymentMethod>>

    @Query("DELETE FROM payment_methods WHERE id = :paymentMethodId AND userId = :userId")
    suspend fun deletePaymentMethod(paymentMethodId: Long, userId: Long)
}
