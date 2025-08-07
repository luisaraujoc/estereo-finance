package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.AccountDao
import com.coutinho.estereof.data.model.Account
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val dao: AccountDao) {

    fun getByUserId(userId: Long): Flow<List<Account>> = dao.getByUserId(userId)

    fun getById(id: Long): Flow<Account> = dao.getById(id)

    suspend fun insert(account: Account): Long = dao.insert(account)

    suspend fun update(account: Account) = dao.update(account)

    suspend fun delete(account: Account) = dao.delete(account)
}
