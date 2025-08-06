// src/main/java/com/coutinho/estereof/data/Converters.kt
package com.coutinho.estereof.data

import androidx.room.TypeConverter
import com.coutinho.estereof.data.model.enums.PaymentMethodType
import com.coutinho.estereof.data.model.enums.TransactionType
import java.math.BigDecimal
import java.util.Date

class Converters {
    // Converte Date para Long (timestamp) e vice-versa
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Converte BigDecimal para String e vice-versa
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toPlainString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    // Converte PaymentMethodType para String e vice-versa
    @TypeConverter
    fun fromPaymentMethodType(value: PaymentMethodType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toPaymentMethodType(value: String?): PaymentMethodType? {
        return value?.let { PaymentMethodType.valueOf(it) }
    }

    // Converte TransactionType para String e vice-versa
    @TypeConverter
    fun fromTransactionType(value: TransactionType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toTransactionType(value: String?): TransactionType? {
        return value?.let { TransactionType.valueOf(it) }
    }
}
