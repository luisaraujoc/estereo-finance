package com.coutinho.estereof.data.local.converters

import androidx.room.TypeConverter
import com.coutinho.estereof.data.local.entities.PaymentMethodType
import com.coutinho.estereof.data.local.entities.RecurrenceInterval
import com.coutinho.estereof.data.local.entities.TransactionStatus
import com.coutinho.estereof.data.local.entities.TransactionType
import java.math.BigDecimal
import java.util.Date

/**
 * Classe que contém Type Converters para o Room Database.
 * Usada para converter tipos de dados complexos (como BigDecimal, Date, e Enums)
 * para tipos que o Room pode armazenar (como String, Long).
 */
class Converters {

    /**
     * Converte um objeto Date para um Long (timestamp).
     * @param date O objeto Date a ser convertido.
     * @return O timestamp Long correspondente, ou null se o Date for null.
     */
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    /**
     * Converte um Long (timestamp) para um objeto Date.
     * @param timestamp O timestamp Long a ser convertido.
     * @return O objeto Date correspondente, ou null se o timestamp for null.
     */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    /**
     * Converte um objeto BigDecimal para uma String.
     * Isso ajuda a manter a precisão de valores monetários.
     * @param amount O objeto BigDecimal a ser convertido.
     * @return A representação em String do BigDecimal, ou null se o BigDecimal for null.
     */
    @TypeConverter
    fun fromBigDecimal(amount: BigDecimal?): String? {
        return amount?.toPlainString()
    }

    /**
     * Converte uma String para um objeto BigDecimal.
     * @param amountString A String a ser convertida.
     * @return O objeto BigDecimal correspondente, ou null se a String for null ou vazia.
     */
    @TypeConverter
    fun toBigDecimal(amountString: String?): BigDecimal? {
        return if (amountString.isNullOrBlank()) null else BigDecimal(amountString)
    }

    /**
     * Converte um enum TransactionType para uma String.
     * @param type O enum TransactionType a ser convertido.
     * @return O nome do enum como String, ou null se o enum for null.
     */
    @TypeConverter
    fun fromTransactionType(type: TransactionType?): String? {
        return type?.name
    }

    /**
     * Converte uma String para um enum TransactionType.
     * @param typeString A String a ser convertida.
     * @return O enum TransactionType correspondente, ou null se a String for null ou não corresponder a um enum válido.
     */
    @TypeConverter
    fun toTransactionType(typeString: String?): TransactionType? {
        return typeString?.let { TransactionType.valueOf(it) }
    }

    /**
     * Converte um enum RecurrenceInterval para uma String.
     */
    @TypeConverter
    fun fromRecurrenceInterval(interval: RecurrenceInterval?): String? {
        return interval?.name
    }

    /**
     * Converte uma String para um enum RecurrenceInterval.
     */
    @TypeConverter
    fun toRecurrenceInterval(intervalString: String?): RecurrenceInterval? {
        return intervalString?.let { RecurrenceInterval.valueOf(it) }
    }

    /**
     * Converte um enum PaymentMethodType para uma String.
     */
    @TypeConverter
    fun fromPaymentMethodType(type: PaymentMethodType?): String? {
        return type?.name
    }

    /**
     * Converte uma String para um enum PaymentMethodType.
     */
    @TypeConverter
    fun toPaymentMethodType(typeString: String?): PaymentMethodType? {
        return typeString?.let { PaymentMethodType.valueOf(it) }
    }

    /**
     * Converte um enum TransactionStatus para uma String.
     */
    @TypeConverter
    fun fromTransactionStatus(status: TransactionStatus?): String? {
        return status?.name
    }

    /**
     * Converte uma String para um enum TransactionStatus.
     */
    @TypeConverter
    fun toTransactionStatus(statusString: String?): TransactionStatus? {
        return statusString?.let { TransactionStatus.valueOf(it) }
    }
}
