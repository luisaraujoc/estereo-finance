package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade UserEntity.
 * Define os métodos para operações CRUD (Create, Read, Update, Delete) na tabela de utilizadores.
 */
@Dao
interface UserDao {

    /**
     * Insere um novo utilizador na base de dados.
     * Se houver conflito (e.g., ID duplicado se não for autoGenerate), substitui o existente.
     * @param user O objeto UserEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    /**
     * Atualiza um utilizador existente na base de dados.
     * @param user O objeto UserEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updateUser(user: UserEntity): Int

    /**
     * Apaga um utilizador da base de dados.
     * @param user O objeto UserEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deleteUser(user: UserEntity): Int

    /**
     * Procura um utilizador pelo seu ID.
     * @param userId O ID do utilizador a ser procurado.
     * @return Um Flow que emite o UserEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): Flow<UserEntity?>

    /**
     * Procura um utilizador pelo seu nome de utilizador.
     * @param username O nome de utilizador a ser procurado.
     * @return Um Flow que emite o UserEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): Flow<UserEntity?>

    /**
     * Procura todos os utilizadores na base de dados.
     * @return Um Flow que emite uma lista de todos os UserEntity.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>
}
