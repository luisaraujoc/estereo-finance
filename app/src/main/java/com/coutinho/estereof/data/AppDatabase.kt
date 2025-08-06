// src/main/java/com/coutinho/estereof/data/AppDatabase.kt
package com.coutinho.estereof.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coutinho.estereof.data.dao.AccountDao
import com.coutinho.estereof.data.dao.CategoryDao
import com.coutinho.estereof.data.dao.PaymentMethodDao
import com.coutinho.estereof.data.dao.TransactionDao
import com.coutinho.estereof.data.dao.UserDao
import com.coutinho.estereof.data.model.Account
import com.coutinho.estereof.data.model.Category
import com.coutinho.estereof.data.model.PaymentMethod
import com.coutinho.estereof.data.model.Transaction
import com.coutinho.estereof.data.model.User

@Database(
    entities = [User::class, Account::class, PaymentMethod::class, Category::class, Transaction::class],
    version = 1, // Comece com a versão 1
    exportSchema = false // Defina como true para exportar o esquema para uma pasta
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun paymentMethodDao(): PaymentMethodDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}
