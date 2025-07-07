package com.coutinho.estereof.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coutinho.estereof.data.local.converters.Converters
import com.coutinho.estereof.data.local.dao.AccountDao
import com.coutinho.estereof.data.local.dao.CategoryDao
import com.coutinho.estereof.data.local.dao.PaymentMethodDao
import com.coutinho.estereof.data.local.dao.SubCategoryDao
import com.coutinho.estereof.data.local.dao.TransactionDao
import com.coutinho.estereof.data.local.dao.UserDao
import com.coutinho.estereof.data.local.entities.AccountEntity
import com.coutinho.estereof.data.local.entities.CategoryEntity
import com.coutinho.estereof.data.local.entities.PaymentMethodEntity
import com.coutinho.estereof.data.local.entities.SubCategoryEntity
import com.coutinho.estereof.data.local.entities.TransactionEntity
import com.coutinho.estereof.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        PaymentMethodEntity::class,
        CategoryEntity::class,
        SubCategoryEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun accountDao(): AccountDao // Novo DAO

    abstract fun paymentMethodDao(): PaymentMethodDao // Novo DAO

    abstract fun categoryDao(): CategoryDao

    abstract fun subCategoryDao(): SubCategoryDao

    abstract fun transactionDao(): TransactionDao
}
