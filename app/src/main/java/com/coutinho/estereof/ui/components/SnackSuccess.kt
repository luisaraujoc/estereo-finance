package com.coutinho.estereof.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.coutinho.estereof.R

@Composable
fun SnackSuccess(
    message: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    Snackbar(
        modifier = modifier,
        action = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.snackbar_dismiss_button))
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Text(text = message)
    }
}