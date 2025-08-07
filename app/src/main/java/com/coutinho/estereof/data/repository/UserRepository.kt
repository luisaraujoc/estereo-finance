// src/main/java/com/coutinho/estereof/data/repository/UserRepository.kt
package com.coutinho.estereof.data.repository

import com.coutinho.estereof.data.dao.UserDao
import com.coutinho.estereof.data.model.User
import com.coutinho.estereof.utils.encryptPassword
import com.coutinho.estereof.utils.checkPassword // Adicione o import
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserRepository constructor(private val dao: UserDao) {

    fun getAll(): Flow<List<User>> = dao.getAll()

    fun getById(id: Long): Flow<User> = dao.getById(id)

    suspend fun findByEmail(email: String): User? = dao.findByEmail(email)

    suspend fun findByUsername(username: String): User? = dao.findByUsername(username)

    suspend fun insert(user: User): Long = dao.insert(user)

    suspend fun update(user: User) = dao.update(user)

    suspend fun delete(user: User) = dao.delete(user)

    suspend fun login(email: String, password: String): User? {
        val user = dao.findByEmail(email)
        return if (user != null && checkPassword(password, user.passwordHash)) { // Corrigido
            user
        } else {
            null
        }
    }

    suspend fun register(name: String, email: String, password: String): Long {
        val existing = dao.findByEmail(email)
        if (existing != null) {
            throw IllegalArgumentException("Email já registrado")
        }

        val username = email.substringBefore("@")
        val currentDate = Date()

        val newUser = User(
            name = name,
            email = email,
            username = username,
            passwordHash = encryptPassword(password),
            createdAt = currentDate,
            updatedAt = currentDate
        )
        return dao.insert(newUser)
    }
}