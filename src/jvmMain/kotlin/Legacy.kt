import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
@Preview
fun legacy(content: ContentState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("СБЕР")
            val listState = rememberLazyListState(Int.MAX_VALUE / 2)
            val (columnHalfSize, setColumnHalfSize) = remember { mutableStateOf<Int?>(null) }
            val density = LocalDensity.current
            val verticalPadding = 16.dp
            val homeScreenItems = remember {
                listOf("Здоровье",
                    "Логистика",
                    "Portal",
                    "Shop",
                    "МРИЯ",
                    "ОККО",
                    "Стразование",
                    "Маркет",
                    "Звук",
                    "АвтоТех",
                    "Аптека",
                    "?????????",
                    "Школа")
            }
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = verticalPadding),
                modifier = Modifier
                    .height(200.dp)
                    .onSizeChanged {
                        setColumnHalfSize(it.width / 2 - with(density) { verticalPadding.roundToPx() })
                    }
            ) {
                items(Int.MAX_VALUE) { globalIndex ->
                    val index = globalIndex % homeScreenItems.size
                    val opacity by remember(columnHalfSize) {
                        derivedStateOf {
                            var length = listState.layoutInfo.visibleItemsInfo.size
                            if(length>0) {
                                var mid = length.div(2).toFloat()
                                var pos = globalIndex - listState.firstVisibleItemIndex + 1
                                if (length.mod(2) == 0) {
                                    mid -= 0.5f
                                }
                                var res = mid / (mid + abs(mid - pos.toFloat()))
                                println("length " + length.toString() + " mid " + mid + " pos " + pos + " res " + res)
                                res
                            } else 0f
                        }
                    }
//                    ClickableText(
//                        text = AnnotatedString(homeScreenItems[index]),
//                        modifier = Modifier
//                            .alpha(opacity)
//                            .scale(opacity)
//                            .animateContentSize(),
//                        onClick = { content.kandinskyScreen(homeScreenItems[index],index)})
                }
            }
        }
    }
}