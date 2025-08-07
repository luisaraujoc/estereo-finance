package com.coutinho.estereof.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.model.enums.TransactionType
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.TransactionRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.navigation.AuthDestinations
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.HomeViewModel
import com.coutinho.estereof.viewmodel.factory.HomeViewModelFactory
import com.coutinho.estereof.viewmodel.TransactionUi
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.MoreVertical
import java.math.BigDecimal

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onLogout: () -> Unit // Nova função de callback
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val accountRepository = remember { AccountRepository(database.accountDao()) }
    val transactionRepository = remember { TransactionRepository(database.transactionDao()) }
    val categoryRepository = remember { CategoryRepository(database.categoryDao()) }
    val paymentMethodRepository = remember { PaymentMethodRepository(database.paymentMethodDao()) }

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            userRepository,
            accountRepository,
            transactionRepository,
            categoryRepository,
            paymentMethodRepository
        )
    )
    val homeState by viewModel.homeState.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.setUserMessage(null)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                )
                // Dropdown Menu para Sair
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = EvaIcons.Outline.MoreVertical,
                            contentDescription = stringResource(R.string.menu_button_description)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.logout_button)) },
                            onClick = {
                                showMenu = false
                                // Aciona a função de callback
                                onLogout()
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (homeState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (homeState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = homeState.error ?: "Erro desconhecido")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.general_balance_label),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.currency_format, homeState.generalBalance),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IncomeExpenseCard(
                        title = stringResource(R.string.income_label),
                        amount = homeState.totalIncome,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    IncomeExpenseCard(
                        title = stringResource(R.string.expense_label),
                        amount = homeState.totalExpense,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.history_label),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (homeState.transactions.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_transactions_label),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    } else {
                        items(homeState.transactions) { transaction ->
                            TransactionItem(transaction)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IncomeExpenseCard(
    title: String,
    amount: BigDecimal,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.currency_format, amount),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(
                    8.dp
                ),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = " ",
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
                    text = transaction.categoryName, // Usando o nome da categoria
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = stringResource(R.string.currency_format, if(transaction.type == TransactionType.EXPENSE) -transaction.amount else transaction.amount),
            style = MaterialTheme.typography.bodyLarge,
            color = if (transaction.type == TransactionType.EXPENSE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            fontFamily = spaceGroteskFamily
        )
    }
}