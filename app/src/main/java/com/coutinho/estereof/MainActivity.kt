package com.coutinho.estereof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.coutinho.estereof.navigation.AppNavigation
import com.coutinho.estereof.navigation.AppScaffold
import com.coutinho.estereof.ui.home.HomeScreen
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
