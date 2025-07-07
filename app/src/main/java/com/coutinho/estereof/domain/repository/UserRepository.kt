package com.coutinho.estereof.domain.repository

import com.coutinho.estereof.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    /**
     * Obtém um utilizador pelo seu ID.
     * @param userId O ID do utilizador (UUID).
     * @return Um Flow que emite o utilizador correspondente, ou null se não encontrado.
     */
    fun getUserById(userId: String): Flow<User?>

    /**
     * Obtém um utilizador pelo seu nome de utilizador.
     * @param username O nome de utilizador.
     * @return Um Flow que emite o utilizador correspondente, ou null se não encontrado.
     */
    fun getUserByUsername(username: String): Flow<User?>

    /**
     * Obtém todos os utilizadores.
     * @return Um Flow que emite uma lista de todos os utilizadores.
     */
    fun getAllUsers(): Flow<List<User>>

    /**
     * Insere um novo utilizador.
     * @param user O utilizador a ser inserido.
     */
    suspend fun insertUser(user: User)

    /**
     * Atualiza um utilizador existente.
     * @param user O utilizador a ser atualizado.
     */
    suspend fun updateUser(user: User)

    /**
     * Apaga um utilizador.
     * @param user O utilizador a ser apagado.
     */
    suspend fun deleteUser(user: User)

    /**
     * Sincroniza o perfil do utilizador com o Supabase.
     * Isso pode envolver buscar o perfil do Supabase e salvá-lo localmente,
     * ou enviar o perfil local para o Supabase.
     * @param userId O ID do utilizador (UUID) a ser sincronizado.
     * @return O utilizador sincronizado ou null se a sincronização falhar.
     */
    suspend fun syncUserProfile(userId: String): User?
}
