package com.coutinho.estereof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.coutinho.estereof.navigation.AppScaffold
import com.coutinho.estereof.ui.theme.EstereoAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstereoAppTheme {
                AppScaffold()
            }
        }
    }
}
