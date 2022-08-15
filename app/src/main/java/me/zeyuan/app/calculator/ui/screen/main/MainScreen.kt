package me.zeyuan.app.calculator.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.zeyuan.app.calculator.calculate.ExpressionEvaluator
import me.zeyuan.app.calculator.store.HistoryEntity
import me.zeyuan.app.calculator.store.getDatabase
import java.util.*

@Composable
fun MainScreen(navController: NavHostController, replayExpression: String? = "") {
    val evaluator = ExpressionEvaluator()
    var expression by remember { mutableStateOf(replayExpression ?: "") }
    var console by remember { mutableStateOf("") }

    val context = LocalContext.current

    val onInput: (Key) -> Unit = { key ->
        if (key.type == KeyType.NUMBER || key.type == KeyType.OPERATOR) {
            console += key.content
        } else {
            when (key.content) {
                "C" -> {
                    console = ""
                    expression = ""
                }
                "=" -> {
                    val expressionDisplay = "$console="
                    val resultDisplay = evaluator.evaluate(console)

                    try {
                        val historyEntity = HistoryEntity(console, resultDisplay)
                        //TODO： 异步
                        getDatabase(context).historyDao().insert(historyEntity)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    expression = expressionDisplay
                    console = resultDisplay
                }
                "<-" -> {
                    if (console.isNotEmpty()) {
                        console = console.dropLast(1)
                    }
                }
            }
        }
    }

    Column {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            ActionBar(navController)
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DisplayArea(expression, console)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Keyboard(onInput)
        }
    }
}

@Composable
fun ActionBar(navController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
    ) {
        IconButton(onClick = { navController.navigate("settings") }) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = "设置",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }
        IconButton(onClick = { navController.navigate("history") }) {
            Icon(
                Icons.Outlined.History,
                contentDescription = "历史记录",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController, "")
}