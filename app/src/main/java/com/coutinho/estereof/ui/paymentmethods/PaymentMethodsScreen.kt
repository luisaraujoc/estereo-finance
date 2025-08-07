package com.coutinho.estereof.ui.paymentmethods

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coutinho.estereof.R
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.model.PaymentMethod
import com.coutinho.estereof.data.model.enums.PaymentMethodType
import com.coutinho.estereof.data.repository.PaymentMethodRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import com.coutinho.estereof.viewmodel.PaymentMethodViewModel
import com.coutinho.estereof.viewmodel.factory.PaymentMethodViewModelFactory
import compose.icons.EvaIcons
import compose.icons.FontAwesomeIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Plus
import compose.icons.evaicons.outline.ChevronDown
import compose.icons.evaicons.outline.CreditCard
import compose.icons.evaicons.outline.Link
import compose.icons.evaicons.outline.ShoppingBag
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Paypal
import kotlinx.coroutines.launch

// Removendo a classe de dados fictícia, pois usaremos o modelo do banco de dados
// import com.coutinho.estereof.data.model.PaymentMethod
// Usamos um alias para evitar conflito de nomes
import com.coutinho.estereof.data.model.PaymentMethod as DataPaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodsScreen(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val paymentMethodRepository = remember { PaymentMethodRepository(database.paymentMethodDao()) }

    val viewModel: PaymentMethodViewModel = viewModel(
        factory = PaymentMethodViewModelFactory(paymentMethodRepository, userRepository)
    )
    val paymentMethodState by viewModel.paymentMethodState.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUserMessage()
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
            ){
                Text(
                    text = stringResource(R.string.payment_methods_title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = EvaIcons.Fill.Plus,
                    contentDescription = stringResource(R.string.add_payment_method_button_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                paymentMethodState.isLoading -> {
                    CircularProgressIndicator()
                }
                paymentMethodState.error != null -> {
                    Text(text = "Erro: ${paymentMethodState.error}", color = MaterialTheme.colorScheme.error)
                }
                paymentMethodState.paymentMethods.isEmpty() -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.payment_methods_empty),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(paymentMethodState.paymentMethods) { paymentMethod ->
                            PaymentMethodItem(paymentMethod)
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            AddPaymentMethodBottomSheetContent(
                onAddMethod = { name, type ->
                    viewModel.addPaymentMethod(name, type)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                onCancel = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }
            )
        }
    }
}

// Mapeia o tipo de pagamento para o ícone
@Composable
private fun getIconForPaymentMethodType(type: PaymentMethodType): ImageVector {
    return when (type) {
        PaymentMethodType.CREDIT_CARD -> EvaIcons.Outline.CreditCard
        PaymentMethodType.DEBIT_CARD -> EvaIcons.Outline.CreditCard
        PaymentMethodType.PIX -> EvaIcons.Outline.Link
        PaymentMethodType.BANK_SLIP -> EvaIcons.Outline.ShoppingBag
        PaymentMethodType.PAYPAL -> FontAwesomeIcons.Brands.Paypal
        PaymentMethodType.OTHER -> EvaIcons.Outline.ChevronDown
        PaymentMethodType.BANK_ACCOUNT -> EvaIcons.Outline.Link
        PaymentMethodType.CASH -> EvaIcons.Outline.CreditCard
    }
}

@Composable
fun AddPaymentMethodBottomSheetContent(
    onAddMethod: (String, PaymentMethodType) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<PaymentMethodType?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.new_payment_method_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        InputField(
            label = stringResource(R.string.payment_method_name_label),
            placeholder = stringResource(R.string.payment_method_name_placeholder),
            value = name,
            onValueChange = { name = it }
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.payment_method_type_label),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                onClick = { expanded = true },
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedType?.name?.replace("_", " ") ?: stringResource(R.string.payment_method_type_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedType == null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = EvaIcons.Outline.ChevronDown,
                        contentDescription = "Select type"
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                PaymentMethodType.values().forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name.replace("_", " ")) },
                        onClick = {
                            selectedType = type
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (name.isNotBlank() && selectedType != null) {
                        onAddMethod(name, selectedType!!)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.add_button),
                    style = MaterialTheme.typography.bodyLarge
                        .copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.bodyLarge
                        .copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}


@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    minLines: Int = 1
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            singleLine = minLines == 1,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun PaymentMethodItem(paymentMethod: DataPaymentMethod) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp) // Adicionado padding aqui
        ) {
            Icon(
                imageVector = getIconForPaymentMethodType(paymentMethod.type),
                contentDescription = paymentMethod.name,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(
            text = paymentMethod.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = spaceGroteskFamily
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}