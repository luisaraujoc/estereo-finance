package com.coutinho.estereof.ui.paymentmethods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.FontAwesomeIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.CreditCard
import compose.icons.evaicons.outline.Link
import compose.icons.evaicons.outline.ShoppingBag
import com.coutinho.estereof.R
import com.coutinho.estereof.ui.theme.EstereoAppTheme
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Paypal

// Dados de exemplo para os métodos de pagamento
data class PaymentMethod(
    val name: String,
    val icon: ImageVector
)

@Composable
fun PaymentMethodsScreen(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    // Ícones para cada método de pagamento.
    val paymentMethods = listOf(
        PaymentMethod("Credit Card", EvaIcons.Outline.CreditCard),
        PaymentMethod(
            "Bank Account",
            EvaIcons.Outline.ShoppingBag
        ), // Usando um ícone de placeholder
        PaymentMethod("PayPal", FontAwesomeIcons.Brands.Paypal),
        PaymentMethod("Venmo", EvaIcons.Outline.Link) // Usando um ícone de placeholder
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            Text(
                text = stringResource(R.string.payment_methods_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(16.dp).padding(paddingValues)
        ) {
            // Lista de métodos de pagamento
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(paymentMethods) { method ->
                    PaymentMethodItem(method)
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(paymentMethod: PaymentMethod) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = Modifier
                .size(38.dp) // Define um tamanho fixo para a superfície do ícone
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = paymentMethod.icon,
                contentDescription = paymentMethod.name,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            text = paymentMethod.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = spaceGroteskFamily
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentMethodsScreenPreview() {
    EstereoAppTheme(darkTheme = false) {
        PaymentMethodsScreen()
    }
}