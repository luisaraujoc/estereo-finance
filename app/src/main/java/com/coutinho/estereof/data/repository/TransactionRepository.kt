// src/main/java/com/coutinho/estereof/data/repository/TransactionRepository.kt
package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.TransactionDao
import com.coutinho.estereof.data.model.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    fun getTransactionById(transactionId: Long, userId: Long): Flow<Transaction?> {
        return transactionDao.getTransactionById(transactionId, userId)
    }

    fun getAllTransactionsForUser(userId: Long): Flow<List<Transaction>> {
        return transactionDao.getAllTransactionsForUser(userId)
    }

    fun getIncomeTransactionsForUser(userId: Long): Flow<List<Transaction>> {
        return transactionDao.getIncomeTransactionsForUser(userId)
    }

    fun getExpenseTransactionsForUser(userId: Long): Flow<List<Transaction>> {
        return transactionDao.getExpenseTransactionsForUser(userId)
    }

    suspend fun deleteTransaction(transactionId: Long, userId: Long) {
        transactionDao.deleteTransaction(transactionId, userId)
    }
}
