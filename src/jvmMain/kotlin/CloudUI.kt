import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
@Preview
fun cloudUI(content: ContentState) {
    Column(
        modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(
            colors = listOf(
                Color(33, 160, 56, 255),
                Color(163, 205, 57, 255),
//                Color(242, 233, 19, 255),
                Color(163, 205, 57, 255),
                Color(15, 168, 224, 255),
                Color(121, 138, 203, 255)
            )
        )),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val listState = rememberLazyListState(Int.MAX_VALUE / 2)
            val (columnHalfSize, setColumnHalfSize) = remember { mutableStateOf<Int?>(null) }
            val density = LocalDensity.current
            val verticalPadding = 0.dp
            val homeScreenItems = remember {
                listOf(
                    "СберЗдоровье",
                    "СберЛогистика",
                    "SberPortal",
                    "SberShop",
                    "МРИЯ",
                    "ОККО",
                    "СберОбразование",
                    "СберМаркет",
                    "СберЗвук",
                    "СберАвтоТех",
                    "ЕАптека",
                    "Восстание машин",
                    "Школа21"
                )
            }
            val map = mapOf<String, Int>(
                "СберЗдоровье" to 23,
                "СберЛогистика" to 21,
                "SberPortal" to 13,
                "SberShop" to 14,
                "МРИЯ" to 17,
                "ОККО" to 12,
                "СберОбразование" to 32,
                "СберМаркет" to 15,
                "СберЗвук" to 28,
                "СберАвтоТех" to 17,
                "ЕАптека" to 9,
                "Восстание машин" to 12,
                "Школа21" to 7
            )
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = verticalPadding),
                modifier = Modifier
                    .height(500.dp)
//                    .onSizeChanged {
//                        setColumnHalfSize(it.width / 2 - with(density) { verticalPadding.roundToPx() })
//                    }
            ) {
                items(Int.MAX_VALUE) { globalIndex ->
                    val index = globalIndex % homeScreenItems.size
                    val opacity by remember(columnHalfSize) {
                        derivedStateOf {
                            var length = listState.layoutInfo.visibleItemsInfo.size
                            if (length > 0) {
                                var mid = length.div(2).toFloat()
                                var pos = globalIndex - listState.firstVisibleItemIndex + 1
                                if (length.mod(2) == 0) {
                                    mid -= 0.5f
                                }
                                var res = mid / (mid + abs(mid - pos.toFloat()))
//                                println("length " + length.toString() + " mid " + mid + " pos " + pos + " res " + res)
                                res
                            } else 0f
                        }
                    }
                    val text = homeScreenItems[index]
                    Text(
                        text = AnnotatedString(text = text),
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier
                            .alpha(opacity)
                            .scale(opacity)
                            .clickable(onClick = { println("current" + index)
                                content.kandinskyScreen(text, index, map.get(text)!!) })
                            .animateContentSize())
                }
            }
        }
    }
}
