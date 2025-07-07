package com.coutinho.estereof.domain.repository

import com.coutinho.estereof.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    /**
     * Obtém uma conta pelo seu ID.
     * @param accountId O ID da conta.
     * @return Um Flow que emite a conta correspondente, ou null se não encontrada.
     */
    fun getAccountById(accountId: Long): Flow<Account?>

    /**
     * Obtém todas as contas de um utilizador específico.
     * @param userId O ID do utilizador (UUID).
     * @return Um Flow que emite uma lista de contas do utilizador.
     */
    fun getAccountsByUserId(userId: String): Flow<List<Account>>

    /**
     * Insere uma nova conta.
     * @param account A conta a ser inserida.
     * @return O ID da conta inserida.
     */
    suspend fun insertAccount(account: Account): Long

    /**
     * Atualiza uma conta existente.
     * @param account A conta a ser atualizada.
     */
    suspend fun updateAccount(account: Account)

    /**
     * Apaga uma conta.
     * @param account A conta a ser apagada.
     */
    suspend fun deleteAccount(account: Account)

    /**
     * Sincroniza as contas de um utilizador com o Supabase.
     * @param userId O ID do utilizador (UUID) para o qual as contas serão sincronizadas.
     */
    suspend fun syncUserAccounts(userId: String)
}
