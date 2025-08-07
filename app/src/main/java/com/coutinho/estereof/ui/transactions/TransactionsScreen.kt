// src/main/java/com/coutinho/estereof/ui/transactions/TransactionsScreen.kt
package com.coutinho.estereof.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.model.Transaction
import com.coutinho.estereof.data.model.enums.TransactionType
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.TransactionRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.TransactionsViewModel
import com.coutinho.estereof.viewmodel.factory.TransactionsViewModelFactory
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Plus
import java.util.Calendar
import java.util.Date
import com.coutinho.estereof.data.model.Transaction as DataTransaction

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    addTransactionAction: () -> Unit = {},
    viewTransactionDetails: (Long) -> Unit = {}
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val transactionRepository = remember { TransactionRepository(database.transactionDao()) }
    val accountRepository = remember { AccountRepository(database.accountDao()) }
    val categoryRepository = remember { CategoryRepository(database.categoryDao()) }
    val paymentMethodRepository = remember { PaymentMethodRepository(database.paymentMethodDao()) }

    val viewModel: TransactionsViewModel = viewModel(
        factory = TransactionsViewModelFactory(userRepository, transactionRepository, accountRepository, categoryRepository, paymentMethodRepository)
    )

    val transactionsState by viewModel.transactionsState.collectAsState()

    val groupedTransactions = groupTransactionsByPeriod(transactionsState.transactions)

    Scaffold(
        modifier = Modifier,
        topBar = {
            // ... (top bar code remains the same) ...
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            if (transactionsState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (transactionsState.error != null) {
                Text(text = "Erro: ${transactionsState.error}", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (transactionsState.transactions.isEmpty()) {
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
                        groupedTransactions.forEach { (periodKey, transactionsForPeriod) ->
                            item {
                                Text(
                                    text = formatDateHeader(periodKey),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            items(transactionsForPeriod) { transaction ->
                                TransactionItem(
                                    transaction = transaction,
                                    categoryName = transactionsState.categories.firstOrNull { it.id == transaction.categoryId }?.name ?: "Sem Categoria",
                                    onClick = { viewTransactionDetails(transaction.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: DataTransaction, categoryName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(text = " ", modifier = Modifier.padding(12.dp))
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
                    text = categoryName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        val textColor = if (transaction.type == TransactionType.EXPENSE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        val amountPrefix = if (transaction.type == TransactionType.EXPENSE) "-" else ""
        Text(
            text = stringResource(R.string.currency_format, amountPrefix + transaction.amount.toString()),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontFamily = spaceGroteskFamily
        )
    }
}

// Funções utilitárias para agrupamento de datas
private fun groupTransactionsByPeriod(transactions: List<Transaction>): Map<String, List<Transaction>> {
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    return transactions.groupBy { transaction ->
        val cal = Calendar.getInstance().apply { time = transaction.createdAt }
        when {
            isSameDay(cal, today) -> "today"
            isSameDay(cal, yesterday) -> "yesterday"
            isSameWeek(cal, today) -> "this_week"
            isSameWeek(cal, Calendar.getInstance().apply { add(Calendar.WEEK_OF_YEAR, -1) }) -> "last_week"
            isSameMonth(cal, today) -> "this_month"
            isSameMonth(cal, Calendar.getInstance().apply { add(Calendar.MONTH, -1) }) -> "last_month"
            isSameYear(cal, today) -> "this_year"
            isSameYear(cal, Calendar.getInstance().apply { add(Calendar.YEAR, -1) }) -> "last_year"
            else -> "older"
        }
    }
}

@Composable
private fun formatDateHeader(periodKey: String): String {
    return when (periodKey) {
        "today" -> stringResource(R.string.period_today)
        "yesterday" -> stringResource(R.string.period_yesterday)
        "this_week" -> stringResource(R.string.period_this_week)
        "last_week" -> stringResource(R.string.period_last_week)
        "this_month" -> stringResource(R.string.period_this_month)
        "last_month" -> stringResource(R.string.period_last_month)
        "this_year" -> stringResource(R.string.period_this_year)
        "last_year" -> stringResource(R.string.period_last_year)
        else -> stringResource(R.string.period_older)
    }
}

private fun createDate(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

private fun isSameWeek(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
}

private fun isSameMonth(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
}

private fun isSameYear(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
}