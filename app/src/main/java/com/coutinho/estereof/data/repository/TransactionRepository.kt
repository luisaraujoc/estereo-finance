package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.TransactionDao
import com.coutinho.estereof.data.model.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val dao: TransactionDao) {

    fun getByUserId(userId: Long): Flow<List<Transaction>> = dao.getByUserId(userId)

    fun getById(id: Long): Flow<Transaction> = dao.getById(id)

    suspend fun insert(transaction: Transaction): Long = dao.insert(transaction)

    suspend fun update(transaction: Transaction) = dao.update(transaction)

    suspend fun delete(transaction: Transaction) = dao.delete(transaction)
}
