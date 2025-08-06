// src/main/java/com/coutinho/estereof/data/model/PaymentMethod.kt
package com.coutinho.estereof.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.coutinho.estereof.data.model.enums.PaymentMethodType
import java.util.Date

@Entity(
    tableName = "payment_methods",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class PaymentMethod(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val name: String,
    val type: PaymentMethodType,
    val createdAt: Date,
    val updatedAt: Date
)
