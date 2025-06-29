import androidx.compose.ui.window.ComposeUIViewController
import com.coutinho.estereofinance.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
