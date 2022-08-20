package me.zeyuan.app.calculator.ui.screen.history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.zeyuan.app.calculator.store.HistoryEntity
import me.zeyuan.app.calculator.store.HistoryRepositoryImpl
import java.net.URLEncoder

@Composable
fun HistoryScreen(
    navController: NavHostController,
    theViewModel: HistoryViewModel
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "历史记录")
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Outlined.ArrowBack, "")
                }
            },
            backgroundColor = Color.Transparent,
            contentColor = Color.Black,
            elevation = 0.dp
        )
    }, content = { padding ->
        HistoryList(padding, theViewModel.histories, navController)
        SideEffect {
            theViewModel.getAllHistories()
        }
    })
}

@Composable
private fun HistoryList(
    padding: PaddingValues,
    histories: List<HistoryEntity>,
    navController: NavHostController
) {
    if (histories.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Outlined.HistoryEdu,
                    contentDescription = "",
                    modifier = Modifier
                        .size(72.dp)
                        .padding(8.dp),
                    tint = Color.Gray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "无历史记录", color = Color.Gray)
            }
        }
    } else {
        LazyColumn(contentPadding = padding) {
            items(histories) { history ->
                HistoryRow(navController, history)
            }
        }
    }
}

@Composable
fun HistoryRow(navController: NavHostController, history: HistoryEntity) {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
        ) {
            OutlinedButton(
                onClick = {
                    val encodedExpression = URLEncoder.encode(history.expression, "utf-8")
                    navController.navigate("main?expression=$encodedExpression") {
                        popUpTo("history")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .wrapContentWidth(Alignment.Start),
                shape = RoundedCornerShape(20.dp),
            ) {
                Icon(Icons.Outlined.Replay, "")
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                fontSize = 32.sp,
                text = "${history.expression}="
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val context = LocalContext.current
            OutlinedButton(
                onClick = {
                    val clipboardManager =
                        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("text", history.result)
                    clipboardManager.setPrimaryClip(clipData)

                    val message = "计算结果:${history.result}\n已经复制到剪切板"
                    context.showToast(message)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .wrapContentWidth(Alignment.Start),
                shape = RoundedCornerShape(20.dp),
            ) {
                Icon(Icons.Outlined.ContentCopy, "")
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                text = history.result
            )
        }
        Divider()
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, duration)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

@Preview(showBackground = true)
@Composable
fun HistoryRowPreview() {
    val history = HistoryEntity("56+3", "59")
    val navController = rememberNavController()
    HistoryRow(navController, history)
}

@Preview(showBackground = true)
@Composable
fun HistoryListPreview() {
    val histories =
        listOf(
            HistoryEntity("56+3", "59"),
            HistoryEntity("56-3", "53"),
            HistoryEntity("56*3", "53"),
        )
    val navController = rememberNavController()
    HistoryList(PaddingValues(16.dp, 8.dp), histories, navController)
}

@Preview(showBackground = true)
@Composable
fun HistoryEmptyPreview() {
    val histories = emptyList<HistoryEntity>()
    val navController = rememberNavController()
    HistoryList(PaddingValues(16.dp, 8.dp), histories, navController)
}