package com.coutinho.estereof.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

/**
 * Entidade que representa a tabela 'transactions' no banco de dados local.
 *
 * @property id ID único da transação. É a chave primária e auto-gerada.
 * @property userId ID do usuário que possui esta transação.
 * @property accountId ID da conta financeira associada a esta transação.
 * @property paymentMethodId ID do método de pagamento usado para esta transação. Pode ser nulo.
 * @property title Título da transação.
 * @property description Descrição detalhada da transação.
 * @property amount Valor da transação. Usado BigDecimal para precisão monetária.
 * @property type Tipo da transação (INCOME ou EXPENSE).
 * @property categoryId ID da categoria à qual a transação pertence. Não pode ser nulo.
 * @property subcategoryId ID da subcategoria à qual a transação pertence. Pode ser nulo.
 * @property isDivided Indica se a transação é dividida em parcelas.
 * @property installments Número de parcelas, se a transação for dividida. Nulo por padrão.
 * @property isRecurring Indica se a transação é recorrente.
 * @property recurrenceInterval Intervalo de recorrência, se for recorrente. Nulo por padrão.
 * @property recurrenceEndDate Data de término da recorrência, se for recorrente. Nulo por padrão.
 * @property startDate Data de início da transação.
 * @property endDate Data de término da transação. Nulo se não for dividida.
 * @property status Status atual da transação (PENDING, PAID, CANCELED).
 * @property createdAt Data e hora de criação da transação.
 * @property updatedAt Data e hora da última atualização da transação.
 */
@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Se o usuário for deletado, suas transações também são.
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.RESTRICT // Não permite deletar conta se houver transações associadas
        ),
        ForeignKey(
            entity = PaymentMethodEntity::class,
            parentColumns = ["id"],
            childColumns = ["paymentMethodId"],
            onDelete = ForeignKey.SET_NULL // Define paymentMethodId como NULL se o método for deletado
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.RESTRICT // Não permite deletar categoria se houver transações associadas
        ),
        ForeignKey(
            entity = SubCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.SET_NULL // Define subcategoryId como NULL se a subcategoria for deletada
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["accountId"]),
        Index(value = ["paymentMethodId"]),
        Index(value = ["categoryId"]),
        Index(value = ["subcategoryId"])
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // Adicionado: FK para UserEntity
    val accountId: Int, // Adicionado: FK para AccountEntity
    val paymentMethodId: Int?, // Adicionado: FK para PaymentMethodEntity, pode ser nulo
    val title: String,
    val description: String,
    val amount: BigDecimal, // Usará TypeConverter
    val type: TransactionType, // Usará TypeConverter
    val categoryId: Int, // idCategory - não pode ser nulo
    val subcategoryId: Int?, // idSubCategory - pode ser nulo
    val isDivided: Boolean, // is_divided
    val installments: Int?, // number, default null
    val isRecurring: Boolean = false, // Adicionado: Indica se é uma transação recorrente
    val recurrenceInterval: RecurrenceInterval? = null, // Adicionado: Intervalo de recorrência
    val recurrenceEndDate: Date? = null, // Adicionado: Data de término da recorrência
    val startDate: Date, // date(start) - Usará TypeConverter
    val endDate: Date?, // date(end) - Usará TypeConverter
    val status: TransactionStatus = TransactionStatus.PENDING, // Adicionado: Status da transação
    val createdAt: Date = Date(), // Adicionado: Data de criação
    val updatedAt: Date = Date() // Adicionado: Data da última atualização
)
