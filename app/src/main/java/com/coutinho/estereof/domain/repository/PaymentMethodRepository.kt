package com.coutinho.estereof.domain.repository

import com.coutinho.estereof.domain.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface PaymentMethodRepository {
    /**
     * Obtém um método de pagamento pelo seu ID.
     * @param paymentMethodId O ID do método de pagamento.
     * @return Um Flow que emite o método de pagamento correspondente, ou null se não encontrado.
     */
    fun getPaymentMethodById(paymentMethodId: Long): Flow<PaymentMethod?>

    /**
     * Obtém todos os métodos de pagamento de um utilizador específico.
     * @param userId O ID do utilizador (UUID).
     * @return Um Flow que emite uma lista de métodos de pagamento do utilizador.
     */
    fun getPaymentMethodsByUserId(userId: String): Flow<List<PaymentMethod>>

    /**
     * Insere um novo método de pagamento.
     * @param paymentMethod O método de pagamento a ser inserido.
     * @return O ID do método de pagamento inserido.
     */
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long

    /**
     * Atualiza um método de pagamento existente.
     * @param paymentMethod O método de pagamento a ser atualizado.
     */
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod)

    /**
     * Apaga um método de pagamento.
     * @param paymentMethod O método de pagamento a ser apagado.
     */
    suspend fun deletePaymentMethod(paymentMethod: PaymentMethod)

    /**
     * Sincroniza os métodos de pagamento de um utilizador com o Supabase.
     * @param userId O ID do utilizador (UUID) para o qual os métodos de pagamento serão sincronizados.
     */
    suspend fun syncUserPaymentMethods(userId: String)
}
