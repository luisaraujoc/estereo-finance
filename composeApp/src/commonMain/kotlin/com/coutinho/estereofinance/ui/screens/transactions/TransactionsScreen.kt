package com.coutinho.estereofinance.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coutinho.estereofinance.ui.theme.AppTheme // Assumindo que você tem um AppTheme
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal // Importe esta função de extensão!
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowCircleDown
import compose.icons.evaicons.outline.ArrowCircleUp
import compose.icons.evaicons.outline.CreditCard
import org.jetbrains.compose.ui.tooling.preview.Preview

// Enum para o tipo de transação (receita ou despesa)
enum class TransactionType {
    INCOME, EXPENSE
}

// Data class para representar uma única transação
data class Transaction(
    val id: String,
    val title: String,
    val category: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val date: String // Para simplificar, usaremos String para a data por enquanto
)

@Composable
fun TransactionsScreen() {
    // Dados de exemplo para as transações
    val transactions = listOf(
        Transaction(
            id = "1",
            title = "Work Payment",
            category = "Income",
            amount = "3500.00".toBigDecimal(),
            type = TransactionType.INCOME,
            date = "25/06/2024"
        ),
        Transaction(
            id = "2",
            title = "Electricity bill",
            category = "Home expenses",
            amount = "120.50".toBigDecimal(),
            type = TransactionType.EXPENSE,
            date = "20/06/2024"
        ),
        Transaction(
            id = "3",
            title = "Internet",
            category = "Home expenses",
            amount = "99.90".toBigDecimal(),
            type = TransactionType.EXPENSE,
            date = "15/06/2024"
        ),
        Transaction(
            id = "4",
            title = "House Rent",
            category = "Home expenses",
            amount = "1500.00".toBigDecimal(),
            type = TransactionType.EXPENSE,
            date = "01/06/2024"
        ),
        Transaction(
            id = "5",
            title = "Payment from Freelance Project",
            category = "Extra Income",
            amount = "500.00".toBigDecimal(),
            type = TransactionType.INCOME,
            date = "10/06/2024"
        ),
        Transaction(
            id = "6",
            title = "Market Shopping",
            category = "Groceries",
            amount = "300.75".toBigDecimal(),
            type = TransactionType.EXPENSE,
            date = "05/06/2024"
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize().padding(
            vertical = 8.dp
        ),
        color = MaterialTheme.colorScheme.background // Cor de fundo do tema
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (transactions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No transactions found",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(transactions) { transaction ->
                        TransactionItem(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Ícone da transação (Receita/Despesa)
        Icon(
            imageVector = when (transaction.type) {
                TransactionType.INCOME -> EvaIcons.Outline.ArrowCircleUp // Seta para cima para receita
                TransactionType.EXPENSE -> EvaIcons.Outline.ArrowCircleDown // Seta para baixo para despesa
            },
            contentDescription = null,
            tint = when (transaction.type) {
                TransactionType.INCOME -> MaterialTheme.colorScheme.primary // Verde para receita
                TransactionType.EXPENSE -> MaterialTheme.colorScheme.error // Vermelho para despesa
            },
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Detalhes da transação (Título, Categoria, Data)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = transaction.category,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = transaction.date,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        // Valor da transação
        Text(
            text = "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"} R$ ${transaction.amount.toPlainString().replace('.', ',')}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = when (transaction.type) {
                TransactionType.INCOME -> MaterialTheme.colorScheme.primary
                TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
            }
        )
    }
}

@Preview
@Composable
fun PreviewTransactionsScreen() {
    AppTheme {
        TransactionsScreen()
    }
}
