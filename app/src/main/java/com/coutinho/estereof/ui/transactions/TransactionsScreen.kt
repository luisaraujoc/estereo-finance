package com.coutinho.estereof.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coutinho.estereof.R
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Plus
import java.math.BigDecimal

// Dados de exemplo para as transações
data class Transaction(
    val title: String,
    val category: String,
    val amount: BigDecimal,
    val isExpense: Boolean = true
)

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    addTransactionAction: () -> Unit = {}

) {
    // Lista de transações de exemplo
    val transactions = listOf<Transaction>(
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                    )
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = stringResource(R.string.transactions_title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addTransactionAction() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = EvaIcons.Fill.Plus,
                    contentDescription = stringResource(R.string.new_transaction_add_button),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    ){
        paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        ) {
            // Lista de transações (LazyColumn)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (transactions.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_transactions_found),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    items(transactions) { transaction ->
                        TransactionItem(transaction)
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
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = " ",
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = spaceGroteskFamily
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        val textColor = if (transaction.isExpense) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        val amountPrefix = if (transaction.isExpense) "-" else ""
        Text(
            text = stringResource(R.string.currency_format, amountPrefix + transaction.amount.toString()),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontFamily = spaceGroteskFamily
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionsScreenPreview() {
    EstereoAppTheme(darkTheme = false) {
        TransactionsScreen()
    }
}