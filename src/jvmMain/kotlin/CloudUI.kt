import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
@Preview
fun cloudUI(content: ContentState) {
    Scaffold(bottomBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource("drawable/logo/watermark.png"),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(121, 138, 203, 255),
                        Color(15, 168, 224, 255),
                        Color(163, 205, 57, 255),
                        Color(163, 205, 57, 255),
                        Color(33, 160, 56, 255)
                    )
                )
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val listState = rememberLazyListState(Int.MAX_VALUE / 2)
                val (columnHalfSize, setColumnHalfSize) = remember { mutableStateOf<Int?>(null) }
                val verticalPadding = 0.dp
                val homeScreenItems = remember {
                    listOf(
                        "??????????????????",
                        "????????",
                        "????????",
                        "??????????????????????",
                        "SberDevices",
                        "??????????????????????",
                        "????????????????????????",
                        "??????????????????????????????",
                        "??????????????????????????",
                        "????????????????????",
                        "SberShop",
                        "????????????????????",
                        "????????????????????????????",
                        "??????????????????????????",
                        "??????????21",
                        "????????????????",
                        "????????????",
                        "??????"
                    )
                }
                val map = mapOf<String, Int>(
                    "??????????????????" to 1,
                    "????????" to 3,
                    "????????" to 3,
                    "??????????????????????" to 2,
                    "SberDevices" to 3,
                    "??????????????????????" to 3,
                    "????????????????????????" to 2,
                    "??????????????????????????????" to 4,
                    "??????????????????????????" to 3,
                    "????????????????????" to 3,
                    "SberShop" to 3,
                    "????????????????????" to 1,
                    "????????????????????????????" to 2,
                    "??????????????????????????" to 2,
                    "??????????21" to 3,
                    "????????????????" to 3,
                    "????????????" to 1,
                    "??????" to 1
                )

                val composableScope = rememberCoroutineScope()

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = verticalPadding),
                    modifier = Modifier
                        .height(500.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                composableScope.launch {
                                    listState.animateScrollToItem(
                                        listState.firstVisibleItemIndex - dragAmount.y.toInt(),
                                        listState.firstVisibleItemScrollOffset - dragAmount.y.toInt()
                                    )
                                }
                            }
                        }
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
                                .clickable(onClick = {
                                    println("current" + index)
                                    content.kandinskyScreen(text, index, map[text]!!)
                                })
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        println("current" + index)
                                        content.kandinskyScreen(text, index, map[text]!!)
                                    }
                                }
                                .animateContentSize()
                        )
                    }
                }
            }
        }
    }
}
