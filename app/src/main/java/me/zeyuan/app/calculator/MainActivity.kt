package me.zeyuan.app.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.zeyuan.app.calculator.ui.screen.HistoryScreen
import me.zeyuan.app.calculator.ui.screen.main.MainScreen
import me.zeyuan.app.calculator.ui.screen.SettingsScreen
import me.zeyuan.app.calculator.ui.theme.CalculatorTheme
import me.zeyuan.app.calculator.ui.theme.NavigationBarColor
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White,
                                    NavigationBarColor
                                )
                            )
                        ),
                    color = Color.Transparent
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main?expression={expression}"
                    ) {
                        composable(
                            "main?expression={expression}",
                            arguments = listOf(navArgument("expression") { })
                        ) { backStackEntry ->
                            val expression = backStackEntry.arguments?.getString("expression") ?: ""
                            MainScreen(navController, URLDecoder.decode(expression, "utf-8"))
                        }
                        composable("settings") { SettingsScreen(navController) }
                        composable("history") { HistoryScreen(navController) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White,
                            NavigationBarColor
                        )
                    )
                ),
            color = Color.Transparent
        ) {
            val navController = rememberNavController()
            MainScreen(navController = navController, replayExpression = "")
        }
    }
}
