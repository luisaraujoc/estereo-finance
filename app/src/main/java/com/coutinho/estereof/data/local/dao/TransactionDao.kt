package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.TransactionEntity
import com.coutinho.estereof.data.local.entities.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.Date

@Dao
interface TransactionDao {

    /**
     * Insere uma nova transação na base de dados.
     * @param transaction O objeto TransactionEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    /**
     * Atualiza uma transação existente na base de dados.
     * @param transaction O objeto TransactionEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity): Int

    /**
     * Apaga uma transação da base de dados.
     * @param transaction O objeto TransactionEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity): Int

    /**
     * Procura uma transação pelo seu ID.
     * @param transactionId O ID da transação a ser procurada.
     * @return Um Flow que emite o TransactionEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun getTransactionById(transactionId: Int): Flow<TransactionEntity?>

    /**
     * Procura todas as transações de um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Um Flow que emite uma lista de todos os TransactionEntity para o utilizador, ordenadas pela data de início.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY startDate DESC")
    fun getAllTransactionsByUserId(userId: Int): Flow<List<TransactionEntity>>

    /**
     * Procura transações de um utilizador por tipo (INCOME ou EXPENSE).
     * @param userId O ID do utilizador.
     * @param type O tipo de transação a ser filtrado.
     * @return Um Flow que emite uma lista de TransactionEntity do tipo especificado para o utilizador.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId AND type = :type ORDER BY startDate DESC")
    fun getTransactionsByUserIdAndType(userId: Int, type: TransactionType): Flow<List<TransactionEntity>>

    /**
     * Procura transações de um utilizador por categoria.
     * @param userId O ID do utilizador.
     * @param categoryId O ID da categoria a ser filtrada.
     * @return Um Flow que emite uma lista de TransactionEntity da categoria especificada para o utilizador.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId AND categoryId = :categoryId ORDER BY startDate DESC")
    fun getTransactionsByUserIdAndCategoryId(userId: Int, categoryId: Int): Flow<List<TransactionEntity>>

    /**
     * Procura transações de um utilizador por subcategoria.
     * @param userId O ID do utilizador.
     * @param subcategoryId O ID da subcategoria a ser filtrada.
     * @return Um Flow que emite uma lista de TransactionEntity da subcategoria especificada para o utilizador.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId AND subcategoryId = :subcategoryId ORDER BY startDate DESC")
    fun getTransactionsByUserIdAndSubcategoryId(userId: Int, subcategoryId: Int): Flow<List<TransactionEntity>>

    /**
     * Procura transações de um utilizador dentro de um período de datas.
     * @param userId O ID do utilizador.
     * @param startDate A data de início do período (inclusive).
     * @param endDate A data de fim do período (inclusive).
     * @return Um Flow que emite uma lista de TransactionEntity dentro do período para o utilizador.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId AND startDate BETWEEN :startDate AND :endDate ORDER BY startDate DESC")
    fun getTransactionsByUserIdBetweenDates(userId: Int, startDate: Date, endDate: Date): Flow<List<TransactionEntity>>

    /**
     * Procura as N transações mais recentes de um utilizador.
     * Útil para o histórico recente no ecrã inicial.
     * @param userId O ID do utilizador.
     * @param limit O número máximo de transações a serem retornadas.
     * @return Um Flow que emite uma lista das transações mais recentes.
     */
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY startDate DESC, createdAt DESC LIMIT :limit")
    fun getRecentTransactionsByUserId(userId: Int, limit: Int): Flow<List<TransactionEntity>>

    /**
     * Calcula o total de entradas (INCOME) para um utilizador num período.
     * @param userId O ID do utilizador.
     * @param startDate A data de início do período (inclusive).
     * @param endDate A data de fim do período (inclusive).
     * @return Um Flow que emite o total de entradas.
     */
    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND type = 'INCOME' AND startDate BETWEEN :startDate AND :endDate")
    fun getTotalIncomeByUserId(userId: Int, startDate: Date, endDate: Date): Flow<BigDecimal?>

    /**
     * Calcula o total de saídas (EXPENSE) para um utilizador num período.
     * @param userId O ID do utilizador.
     * @param startDate A data de início do período (inclusive).
     * @param endDate A data de fim do período (inclusive).
     * @return Um Flow que emite o total de saídas.
     */
    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND type = 'EXPENSE' AND startDate BETWEEN :startDate AND :endDate")
    fun getTotalExpenseByUserId(userId: Int, startDate: Date, endDate: Date): Flow<BigDecimal?>

    /**
     * Calcula o saldo geral (entradas - saídas) para um utilizador num período.
     * Esta consulta pode ser mais complexa ou ser calculada na camada de ViewModel/Repository
     * combinando os resultados de getTotalIncome e getTotalExpense.
     */
    // Não incluído diretamente aqui, será calculado na camada superior
}
