package me.zeyuan.app.calculator.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
                .weight(1f)
        ) {
            ActionBar()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(5f)
        ) {
            DisplayArea(expression, console)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
            .padding(8.dp, 8.dp)
            .wrapContentWidth(Alignment.End)
    ) {
        Text(modifier = Modifier.padding(4.dp), text = "历史")
        Text(modifier = Modifier.padding(4.dp), text = "设置")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}