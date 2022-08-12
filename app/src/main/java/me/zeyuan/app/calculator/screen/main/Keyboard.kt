package me.zeyuan.app.calculator.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class KeyType {
    NUMBER,
    OPERATOR,
    ACTION,
}

data class Key(
    val content: String,
    val type: KeyType = KeyType.NUMBER,
)

@Composable
fun RowScope.KeyItem(
    key: Key,
    onClick: (Key) -> Unit = {},
    backgroundColor: Color = Color.Gray
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f)
            .background(Color.LightGray, shape = CircleShape)
            .clickable { onClick(key) },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = key.content
        )
    }
}

@Composable
fun Keyboard(onClick: (Key) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        val keys: List<List<Key>> = listOf(
            listOf(
                Key("C", KeyType.ACTION),
                Key("(", KeyType.OPERATOR),
                Key(")", KeyType.OPERATOR),
                Key("⁒", KeyType.OPERATOR)
            ),
            listOf(
                Key("7"),
                Key("8"),
                Key("9"),
                Key("x", KeyType.OPERATOR)
            ),
            listOf(
                Key("4"),
                Key("5"),
                Key("6"),
                Key("-", KeyType.OPERATOR),
            ),
            listOf(
                Key("1"),
                Key("2"),
                Key("3"),
                Key("+", KeyType.OPERATOR),
            ),
            listOf(
                Key("0"),
                Key("."),
                Key("<-", KeyType.ACTION),
                Key("=", KeyType.ACTION),
            ),
        )

        for (lineKeys in keys) {
            Row(Modifier.fillMaxWidth()) {
                lineKeys.forEach { KeyItem(it, onClick) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard(onClick = {})
}
