// src/main/java/com/coutinho/estereof/data/model/User.kt
package com.coutinho.estereof.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val username: String,
    val email: String,
    val passwordHash: String, // Renomeado para evitar confusão com a senha em texto claro
    val createdAt: Date,
    val updatedAt: Date
)
