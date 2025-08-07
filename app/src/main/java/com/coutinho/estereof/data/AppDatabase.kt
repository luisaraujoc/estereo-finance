package com.coutinho.estereof.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coutinho.estereof.data.dao.*
import com.coutinho.estereof.data.model.*

@Database(
    entities = [
        User::class,
        Account::class,
        Category::class,
        PaymentMethod::class,
        Transaction::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun paymentMethodDao(): PaymentMethodDao
    abstract fun transactionDao(): TransactionDao


}
