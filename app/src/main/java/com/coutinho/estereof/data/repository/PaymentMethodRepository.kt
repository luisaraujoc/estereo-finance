package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.PaymentMethodDao
import com.coutinho.estereof.data.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

class PaymentMethodRepository(private val dao: PaymentMethodDao) {

    fun getByUserId(userId: Long): Flow<List<PaymentMethod>> = dao.getByUserId(userId)

    fun getById(id: Long): Flow<PaymentMethod> = dao.getById(id)

    suspend fun insert(method: PaymentMethod): Long = dao.insert(method)

    suspend fun update(method: PaymentMethod) = dao.update(method)

    suspend fun delete(method: PaymentMethod) = dao.delete(method)
}
