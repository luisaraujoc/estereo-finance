// src/main/java/com/coutinho/estereof/data/model/Transaction.kt
package com.coutinho.estereof.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.coutinho.estereof.data.model.enums.TransactionType
import java.math.BigDecimal
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PaymentMethod::class,
            parentColumns = ["id"],
            childColumns = ["paymentMethodId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL // Categoria pode ser nula
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["accountId"]),
        Index(value = ["paymentMethodId"]),
        Index(value = ["categoryId"])
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val accountId: Long,
    val paymentMethodId: Long,
    val title: String,
    val description: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val categoryId: Long?, // Permite valor nulo
    val createdAt: Date,
    val updatedAt: Date
)
