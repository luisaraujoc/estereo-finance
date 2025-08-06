// src/main/java/com/coutinho/estereof/data/repository/PaymentMethodRepository.kt
package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.PaymentMethodDao
import com.coutinho.estereof.data.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

class PaymentMethodRepository(private val paymentMethodDao: PaymentMethodDao) {

    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long {
        return paymentMethodDao.insertPaymentMethod(paymentMethod)
    }

    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        paymentMethodDao.updatePaymentMethod(paymentMethod)
    }

    fun getPaymentMethodById(paymentMethodId: Long, userId: Long): Flow<PaymentMethod?> {
        return paymentMethodDao.getPaymentMethodById(paymentMethodId, userId)
    }

    fun getAllPaymentMethodsForUser(userId: Long): Flow<List<PaymentMethod>> {
        return paymentMethodDao.getAllPaymentMethodsForUser(userId)
    }

    suspend fun deletePaymentMethod(paymentMethodId: Long, userId: Long) {
        paymentMethodDao.deletePaymentMethod(paymentMethodId, userId)
    }
}
