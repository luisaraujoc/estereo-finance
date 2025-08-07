// src/main/java/com/coutinho/estereof/ui/transactions/TransactionDetailsScreen.kt
package com.coutinho.estereof.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.model.enums.TransactionType
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.TransactionRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.viewmodel.TransactionsViewModel
import com.coutinho.estereof.viewmodel.factory.TransactionsViewModelFactory
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowBack
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    transactionId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
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

    val transaction = transactionsState.selectedTransaction
    val category = transactionsState.categories.firstOrNull { it.id == transaction?.categoryId }
    val paymentMethod = transactionsState.paymentMethods.firstOrNull { it.id == transaction?.paymentMethodId }

    LaunchedEffect(key1 = transactionId) {
        viewModel.getTransactionDetails(transactionId)
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.transaction_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = EvaIcons.Outline.ArrowBack, contentDescription = stringResource(R.string.back_button_description))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            when {
                transactionsState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                }
                transactionsState.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(text = "Erro: ${transactionsState.error}", color = MaterialTheme.colorScheme.error) }
                }
                transaction != null -> {
                    // Exibir os detalhes da transação
                    DetailItem(label = stringResource(R.string.transaction_details_title_label), value = transaction.title)
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailItem(label = stringResource(R.string.transaction_details_description_label), value = transaction.description)
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailItem(label = stringResource(R.string.transaction_details_category_label), value = category?.name ?: "Sem Categoria")
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailItem(label = stringResource(R.string.transaction_details_date_label), value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(transaction.createdAt))
                    Spacer(modifier = Modifier.height(16.dp))

                    val amountText = if (transaction.type == TransactionType.EXPENSE) {
                        stringResource(R.string.currency_format, "-${transaction.amount}")
                    } else {
                        stringResource(R.string.currency_format, transaction.amount)
                    }
                    val amountColor = if (transaction.type == TransactionType.EXPENSE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    DetailItem(label = stringResource(R.string.transaction_details_amount_label), value = amountText, valueColor = amountColor)
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = valueColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailsScreenPreview() {
    EstereoAppTheme(darkTheme = false) {
        TransactionDetailsScreen(transactionId = 1L, onBackClick = {})
    }
}
