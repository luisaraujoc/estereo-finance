package com.coutinho.estereof.domain.repository

import com.coutinho.estereof.domain.model.Transaction
import com.coutinho.estereof.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.Date


interface TransactionRepository {
    /**
     * Obtém uma transação pelo seu ID.
     * @param transactionId O ID da transação.
     * @return Um Flow que emite a transação correspondente, ou null se não encontrada.
     */
    fun getTransactionById(transactionId: Long): Flow<Transaction?>

    /**
     * Obtém todas as transações de um utilizador específico.
     * @param userId O ID do utilizador (UUID).
     * @return Um Flow que emite uma lista de transações do utilizador.
     */
    fun getAllTransactionsByUserId(userId: String): Flow<List<Transaction>>

    /**
     * Obtém transações de um utilizador por tipo (INCOME ou EXPENSE).
     * @param userId O ID do utilizador (UUID).
     * @param type O tipo de transação a ser filtrado.
     * @return Um Flow que emite uma lista de transações do tipo especificado.
     */
    fun getTransactionsByUserIdAndType(userId: String, type: TransactionType): Flow<List<Transaction>>

    /**
     * Obtém transações de um utilizador por categoria.
     * @param userId O ID do utilizador (UUID).
     * @param categoryId O ID da categoria a ser filtrada.
     * @return Um Flow que emite uma lista de transações da categoria especificada.
     */
    fun getTransactionsByUserIdAndCategoryId(userId: String, categoryId: Long): Flow<List<Transaction>>

    /**
     * Obtém as N transações mais recentes de um utilizador.
     * @param userId O ID do utilizador (UUID).
     * @param limit O número máximo de transações a serem retornadas.
     * @return Um Flow que emite uma lista das transações mais recentes.
     */
    fun getRecentTransactionsByUserId(userId: String, limit: Int): Flow<List<Transaction>>

    /**
     * Calcula o total de entradas (INCOME) para um utilizador num período.
     * @param userId O ID do utilizador (UUID).
     * @param startDate A data de início do período.
     * @param endDate A data de fim do período.
     * @return Um Flow que emite o total de entradas.
     */
    fun getTotalIncomeByUserId(userId: String, startDate: Date, endDate: Date): Flow<BigDecimal?>

    /**
     * Calcula o total de saídas (EXPENSE) para um utilizador num período.
     * @param userId O ID do utilizador (UUID).
     * @param startDate A data de início do período.
     * @param endDate A data de fim do período.
     * @return Um Flow que emite o total de saídas.
     */
    fun getTotalExpenseByUserId(userId: String, startDate: Date, endDate: Date): Flow<BigDecimal?>

    /**
     * Insere uma nova transação.
     * @param transaction A transação a ser inserida.
     * @return O ID da transação inserida.
     */
    suspend fun insertTransaction(transaction: Transaction): Long

    /**
     * Atualiza uma transação existente.
     * @param transaction A transação a ser atualizada.
     */
    suspend fun updateTransaction(transaction: Transaction)

    /**
     * Apaga uma transação.
     * @param transaction A transação a ser apagada.
     */
    suspend fun deleteTransaction(transaction: Transaction)

    /**
     * Sincroniza as transações de um utilizador com o Supabase.
     * @param userId O ID do utilizador (UUID) para o qual as transações serão sincronizadas.
     */
    suspend fun syncUserTransactions(userId: String)
}
