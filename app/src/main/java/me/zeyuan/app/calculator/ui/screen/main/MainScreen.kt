package me.zeyuan.app.calculator.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.zeyuan.app.calculator.calculate.ExpressionEvaluator

@Composable
fun MainScreen() {
    val evaluator = ExpressionEvaluator()
    var expression by remember { mutableStateOf("") }
    var console by remember { mutableStateOf("") }

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
                    expression = "$console="
                    console = evaluator.evaluate(console)
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
            ActionBar()
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
fun ActionBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
    ) {
        Icon(
            Icons.Outlined.Settings,
            contentDescription = "设置",
            modifier = Modifier
                .size(42.dp)
                .padding(8.dp)
        )
        Icon(
            Icons.Outlined.History,
            contentDescription = "历史记录",
            modifier = Modifier
                .size(42.dp)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}