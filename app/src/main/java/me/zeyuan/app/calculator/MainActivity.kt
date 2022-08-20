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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.zeyuan.app.calculator.store.HistoryRepositoryImpl
import me.zeyuan.app.calculator.store.getDatabase
import me.zeyuan.app.calculator.ui.screen.history.HistoryScreen
import me.zeyuan.app.calculator.ui.screen.main.MainScreen
import me.zeyuan.app.calculator.ui.screen.SettingsScreen
import me.zeyuan.app.calculator.ui.screen.history.HistoryViewModel
import me.zeyuan.app.calculator.ui.screen.history.HistoryViewModelFactory
import me.zeyuan.app.calculator.ui.theme.CalculatorTheme
import me.zeyuan.app.calculator.ui.theme.NavigationBarColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = HistoryRepositoryImpl.create(this)
        val viewModel = HistoryViewModelFactory(repository).create(HistoryViewModel::class.java)

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
                            arguments = listOf(navArgument("expression") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val expression = backStackEntry.arguments?.getString("expression") ?: ""
                            MainScreen(navController, expression, viewModel)
                        }
                        composable("settings") { SettingsScreen(navController) }
                        composable("history") { HistoryScreen(navController, viewModel) }
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

            val repository = HistoryRepositoryImpl.create(LocalContext.current)
            val viewModel = HistoryViewModelFactory(repository).create(HistoryViewModel::class.java)

            MainScreen(navController = navController, replayExpression = "",viewModel)
        }
    }
}
