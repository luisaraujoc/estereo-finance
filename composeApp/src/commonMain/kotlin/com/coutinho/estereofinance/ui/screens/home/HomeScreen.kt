package com.coutinho.estereofinance.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coutinho.estereofinance.ui.theme.AppTheme
import com.coutinho.estereofinance.utils.StringRes
import com.coutinho.estereofinance.utils.Strings
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.CloseCircle
import compose.icons.evaicons.outline.Move
import compose.icons.evaicons.outline.ShoppingBag

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Define a data class for a bill item
data class BillItem(
    val title: String,
    val category: String,
    val amount: BigDecimal
)

@Composable
fun HomeScreen(
    onNavigateToTransactions: () -> Unit
) {

    val amount_income = "0.00".toBigDecimal()
    val amount_expense = "0.00".toBigDecimal()
    val generalBalance = "0.00".toBigDecimal()

    val billItems = listOf(
        BillItem("Electricity bill", "Home expenses", "0.00".toBigDecimal()),
        BillItem("Internet", "Home expenses", "0.00".toBigDecimal()),
        BillItem("Water bill", "Home expenses", "0.00".toBigDecimal())
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = Strings.get(StringRes.GENERAL_BALANCE_LABEL),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "R$ ${generalBalance.toPlainString().replace('.', ',')}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BalanceCard(
                        title = Strings.get(StringRes.INCOME_LABEL),
                        amount = amount_income,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    BalanceCard(
                        title = Strings.get(StringRes.EXPENSE_LABEL),
                        amount = amount_expense,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {
                            onNavigateToTransactions()
                        },
                        modifier = Modifier.padding(2.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = Strings.get(StringRes.VIEW_ALL_TRANSACTIONS_BUTTON),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(billItems) { item ->
                        BillEntry(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun BalanceCard(title: String, amount: BigDecimal, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 8.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "R$ ${amount.toPlainString().replace('.', ',')}",
            fontSize = 18.sp,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp),
        )
    }
}

@Composable
fun BillEntry(item: BillItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Column {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = item.category,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    )
                )
            }
        Text(
            text = "- R$ ${item.amount.toPlainString().replace('.', ',')}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}