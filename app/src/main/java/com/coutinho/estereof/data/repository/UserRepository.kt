// src/main/java/com/coutinho/estereof/data/repository/UserRepository.kt
package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.UserDao
import com.coutinho.estereof.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId)
    }

    fun getUserByEmail(email: String): Flow<User?> {
        return userDao.getUserByEmail(email)
    }

    fun getUserByUsername(username: String): Flow<User?> {
        return userDao.getUserByUsername(username)
    }

    suspend fun deleteUser(userId: Long) {
        userDao.deleteUser(userId)
    }
}
