package com.coutinho.estereof.data.local.entities

/**
 * Enum que representa o tipo de transação: Receita (income) ou Despesa (expense).
 */
enum class TransactionType {
    INCOME,
    EXPENSE
}

/**
 * Enum que representa o intervalo de recorrência para transações.
 */
enum class RecurrenceInterval {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

/**
 * Enum que representa os diferentes tipos de métodos de pagamento.
 */
enum class PaymentMethodType {
    CREDIT_CARD,
    DEBIT_CARD,
    CASH,
    PIX,
    BANK_TRANSFER,
    OTHER
}

/**
 * Enum que representa o status de uma transação.
 */
enum class TransactionStatus {
    PENDING,
    PAID,
    CANCELED
}
