package me.zeyuan.app.calculator.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DisplayArea(expression: String, result: String) {
    Column {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp)
                    .wrapContentWidth(Alignment.End)
            ) {
                Text(fontSize = 32.sp, text = expression)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp)
                    .wrapContentWidth(Alignment.End)
            ) {
                Text(fontSize = 48.sp, fontWeight = FontWeight.Bold, text = result)
            }
        }
    }
}
