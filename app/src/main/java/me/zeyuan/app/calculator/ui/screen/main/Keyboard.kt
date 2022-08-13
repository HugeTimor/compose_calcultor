package me.zeyuan.app.calculator.ui.screen.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.zeyuan.app.calculator.R
import me.zeyuan.app.calculator.ui.theme.ClearButtonColor
import me.zeyuan.app.calculator.ui.theme.FormulaButtonColor
import me.zeyuan.app.calculator.ui.theme.NormalLightButtonColor
import me.zeyuan.app.calculator.ui.theme.OperatorButtonColor

enum class KeyType {
    NUMBER,
    OPERATOR,
    ACTION,
}

data class Key(
    val content: String,
    val type: KeyType = KeyType.NUMBER,
    val backgroundColor: Color = NormalLightButtonColor,
    @DrawableRes val iconRes: Int? = null,
    val contentDescription: String = ""
)

@Composable
fun RowScope.KeyItem(
    key: Key,
    onClick: (Key) -> Unit = {},
    backgroundColor: Color = NormalLightButtonColor
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1.25f),
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        onClick = { onClick(key) }
    ) {
        if (key.iconRes != null) {
            Icon(
                painter = painterResource(id = key.iconRes),
                contentDescription = key.contentDescription,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                tint = Color.White
            )
        } else {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = key.content
            )
        }
    }
}

@Composable
fun Keyboard(onClick: (Key) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        val keys: List<List<Key>> = listOf(
            listOf(
                Key("C", KeyType.ACTION, ClearButtonColor),
                Key("(", KeyType.OPERATOR, FormulaButtonColor),
                Key(")", KeyType.OPERATOR, FormulaButtonColor),
                Key("÷", KeyType.OPERATOR, OperatorButtonColor)
            ),
            listOf(
                Key("7"),
                Key("8"),
                Key("9"),
                Key("×", KeyType.OPERATOR, OperatorButtonColor)
            ),
            listOf(
                Key("4"),
                Key("5"),
                Key("6"),
                Key("-", KeyType.OPERATOR, OperatorButtonColor),
            ),
            listOf(
                Key("1"),
                Key("2"),
                Key("3"),
                Key("+", KeyType.OPERATOR, OperatorButtonColor),
            ),
            listOf(
                Key("0"),
                Key("."),
                Key("<-", KeyType.ACTION, OperatorButtonColor, R.drawable.ic_delete_left),
                Key("=", KeyType.ACTION, OperatorButtonColor),
            ),
        )

        for (lineKeys in keys) {
            Row(Modifier.fillMaxWidth()) {
                lineKeys.forEach { KeyItem(it, onClick, it.backgroundColor) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard(onClick = {})
}


@Preview(showBackground = true)
@Composable
fun KeyPreview() {
    Row {
        val key = Key("<-", KeyType.ACTION, OperatorButtonColor, R.drawable.ic_delete_left)
        KeyItem(key)
    }
}