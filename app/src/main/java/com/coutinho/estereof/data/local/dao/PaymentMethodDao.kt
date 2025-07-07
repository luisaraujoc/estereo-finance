package com.coutinho.estereof.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coutinho.estereof.data.local.entities.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade PaymentMethodEntity.
 * Define os métodos para operações CRUD na tabela de métodos de pagamento.
 */
@Dao
interface PaymentMethodDao {

    /**
     * Insere um novo método de pagamento na base de dados.
     * @param paymentMethod O objeto PaymentMethodEntity a ser inserido.
     * @return O ID da linha inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethodEntity): Long

    /**
     * Atualiza um método de pagamento existente na base de dados.
     * @param paymentMethod O objeto PaymentMethodEntity a ser atualizado.
     * @return O número de linhas atualizadas.
     */
    @Update
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethodEntity): Int

    /**
     * Apaga um método de pagamento da base de dados.
     * @param paymentMethod O objeto PaymentMethodEntity a ser apagado.
     * @return O número de linhas apagadas.
     */
    @Delete
    suspend fun deletePaymentMethod(paymentMethod: PaymentMethodEntity): Int

    /**
     * Procura um método de pagamento pelo seu ID.
     * @param paymentMethodId O ID do método de pagamento a ser procurado.
     * @return Um Flow que emite o PaymentMethodEntity correspondente, ou null se não encontrado.
     */
    @Query("SELECT * FROM payment_methods WHERE id = :paymentMethodId")
    fun getPaymentMethodById(paymentMethodId: Int): Flow<PaymentMethodEntity?>

    /**
     * Procura todos os métodos de pagamento de um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Um Flow que emite uma lista de PaymentMethodEntity pertencentes ao utilizador.
     */
    @Query("SELECT * FROM payment_methods WHERE userId = :userId ORDER BY name ASC")
    fun getPaymentMethodsByUserId(userId: Int): Flow<List<PaymentMethodEntity>>

    /**
     * Procura todos os métodos de pagamento na base de dados.
     * @return Um Flow que emite uma lista de todos os PaymentMethodEntity.
     */
    @Query("SELECT * FROM payment_methods ORDER BY name ASC")
    fun getAllPaymentMethods(): Flow<List<PaymentMethodEntity>>
}
