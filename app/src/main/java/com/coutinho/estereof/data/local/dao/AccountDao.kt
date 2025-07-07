package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.AccountEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade AccountEntity.
 * Define os métodos para operações CRUD na tabela de contas.
 */
@Dao
interface AccountDao {

    /**
     * Insere uma nova conta na base de dados.
     * @param account O objeto AccountEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity): Long

    /**
     * Atualiza uma conta existente na base de dados.
     * @param account O objeto AccountEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updateAccount(account: AccountEntity): Int

    /**
     * Apaga uma conta da base de dados.
     * @param account O objeto AccountEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deleteAccount(account: AccountEntity): Int

    /**
     * Procura uma conta pelo seu ID.
     * @param accountId O ID da conta a ser procurada.
     * @return Um Flow que emite o AccountEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountById(accountId: Int): Flow<AccountEntity?>

    /**
     * Procura todas as contas de um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Um Flow que emite uma lista de AccountEntity pertencentes ao utilizador.
     */
    @Query("SELECT * FROM accounts WHERE userId = :userId ORDER BY name ASC")
    fun getAccountsByUserId(userId: Int): Flow<List<AccountEntity>>

    /**
     * Procura todas as contas na base de dados.
     * @return Um Flow que emite uma lista de todos os AccountEntity.
     */
    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<AccountEntity>>
}
