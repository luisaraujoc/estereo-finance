// src/main/java/com/coutinho/estereof/ui/transactions/NewTransactionScreen.kt
package com.coutinho.estereof.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.AppDatabase
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.repository.AccountRepository
import com.coutinho.estereof.data.repository.TransactionRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.components.InputField
import com.coutinho.estereof.ui.components.SelectableInputField
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.TransactionsViewModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Calendar
import compose.icons.evaicons.outline.ChevronDown
import compose.icons.evaicons.outline.Close
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.coutinho.estereof.viewmodel.factory.TransactionsViewModelFactory
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.model.Category
import com.coutinho.estereof.data.model.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransactionScreen(
    onCloseClick: () -> Unit,
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

    // Estados para os campos do formulário
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var amount by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(Date()) }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.time)

    val userMessage by viewModel.userMessage.collectAsState()
    val transactionsState by viewModel.transactionsState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados para os Dropdowns
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showPaymentMethodDropdown by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUserMessage()
            if (it == "Transação adicionada com sucesso!") {
                onCloseClick()
            }
        }
    }
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.new_transaction_title)) },
                navigationIcon = { IconButton(onClick = onCloseClick) { Icon(imageVector = EvaIcons.Outline.Close, contentDescription = stringResource(R.string.close_button_description)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.addNewTransaction(
                        title = title,
                        description = description,
                        categoryId = selectedCategory?.id,
                        paymentMethodId = selectedPaymentMethod?.id ?: 0,
                        amount = amount,
                        date = selectedDate,
                        isExpense = isExpense
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(text = stringResource(R.string.new_transaction_add_button), fontFamily = spaceGroteskFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TransactionTypeButton(text = stringResource(R.string.income_label), isSelected = !isExpense, onClick = { isExpense = false }, modifier = Modifier.weight(1f))
                TransactionTypeButton(text = stringResource(R.string.expense_label), isSelected = isExpense, onClick = { isExpense = true }, modifier = Modifier.weight(1f))
            }
            InputField(label = stringResource(R.string.new_transaction_title_label), placeholder = stringResource(R.string.placeholder_enter_title), value = title, onValueChange = { title = it })
            InputField(label = stringResource(R.string.new_transaction_description_label), placeholder = stringResource(R.string.new_transaction_description_label), value = description, onValueChange = { description = it }, minLines = 3)

            // Campo de Categoria
            SelectableInputField(
                label = stringResource(R.string.new_transaction_category_label),
                placeholder = stringResource(R.string.placeholder_select_category),
                value = selectedCategory?.name ?: "",
                onClick = { showCategoryDropdown = true },
                trailingIcon = { Icon(imageVector = EvaIcons.Outline.ChevronDown, contentDescription = stringResource(R.string.select_category_icon_description)) }
            )
            DropdownMenu(
                expanded = showCategoryDropdown,
                onDismissRequest = { showCategoryDropdown = false }
            ) {
                transactionsState.categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            showCategoryDropdown = false
                        }
                    )
                }
            }

            // Campo de Método de Pagamento
            SelectableInputField(
                label = stringResource(R.string.new_transaction_payment_method_label),
                placeholder = stringResource(R.string.placeholder_select_payment_method),
                value = selectedPaymentMethod?.name ?: "",
                onClick = { showPaymentMethodDropdown = true },
                trailingIcon = { Icon(imageVector = EvaIcons.Outline.ChevronDown, contentDescription = stringResource(R.string.select_payment_method_icon_description)) }
            )
            DropdownMenu(
                expanded = showPaymentMethodDropdown,
                onDismissRequest = { showPaymentMethodDropdown = false }
            ) {
                transactionsState.paymentMethods.forEach { method ->
                    DropdownMenuItem(
                        text = { Text(method.name) },
                        onClick = {
                            selectedPaymentMethod = method
                            showPaymentMethodDropdown = false
                        }
                    )
                }
            }

            SelectableInputField(
                label = stringResource(R.string.new_transaction_date_label),
                placeholder = stringResource(R.string.placeholder_select_date),
                value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate),
                onClick = { showDatePickerDialog = true },
                trailingIcon = { Icon(imageVector = EvaIcons.Outline.Calendar, contentDescription = stringResource(R.string.select_date_icon_description)) }
            )
            InputField(label = stringResource(R.string.new_transaction_amount_label), placeholder = stringResource(R.string.placeholder_enter_amount), value = amount, onValueChange = { amount = it })
        }
    }
    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = { TextButton(onClick = { datePickerState.selectedDateMillis?.let { selectedDate = Date(it) }; showDatePickerDialog = false }) { Text("OK") } },
            dismissButton = { TextButton(onClick = { showDatePickerDialog = false }) { Text("Cancelar") } }
        ) { DatePicker(state = datePickerState) }
    }
}

// Botão para selecionar o tipo de transação (receita/despesa)
@Composable
fun TransactionTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold)
    }
}