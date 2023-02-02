import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
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
                modifier = Modifier.size(300.dp).blur(radius = 0.2.dp, edgeTreatment = BlurredEdgeTreatment(
                    RoundedCornerShape(8.dp)
                ))
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
                        "Биометрия",
                        "МРИЯ",
                        "ОККО",
                        "СберАвтоТех",
                        "SberDevices",
                        "СберЕАптека",
                        "СберЗдоровье",
                        "СберСтрахование",
                        "СберЛогистика",
                        "СберМаркет",
                        "SberShop",
                        "СберСпикер",
                        "СберПереводчик",
                        "СберТранспорт",
                        "Школа21",
                        "СберЗвук",
                        "СовТех",
                        "СТК"
                    )
                }
                val map = mapOf<String, Int>(
                    "Биометрия" to 1,
                    "МРИЯ" to 3,
                    "ОККО" to 3,
                    "СберАвтоТех" to 2,
                    "SberDevices" to 3,
                    "СберЕАптека" to 3,
                    "СберЗдоровье" to 2,
                    "СберСтрахование" to 4,
                    "СберЛогистика" to 3,
                    "СберМаркет" to 3,
                    "SberShop" to 3,
                    "СберСпикер" to 1,
                    "СберПереводчик" to 2,
                    "СберТранспорт" to 2,
                    "Школа21" to 3,
                    "СберЗвук" to 3,
                    "СовТех" to 1,
                    "СТК" to 1
                )

                val composableScope = rememberCoroutineScope()
                var dragEventsCount = mutableStateOf(0);
                val dragScope = rememberDraggableState(onDelta = { delta ->
                    composableScope.launch {
                        dragEventsCount.value += 1
                        println("count " + dragEventsCount.value)
                        if (dragEventsCount.value > 1) {
                            listState.animateScrollToItem(
                                listState.firstVisibleItemIndex - (delta * 0.6).toInt(),
                                listState.firstVisibleItemScrollOffset - (delta * 0.6).toInt()
                            )
                        }

                    }
                })

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = verticalPadding),
                    modifier = Modifier
                        .height(800.dp)
//                        .pointerInput(Unit) {
//                            detectDragGestures { change, dragAmount ->
//                                composableScope.launch {
//                                    listState.animateScrollToItem(
//                                        listState.firstVisibleItemIndex - dragAmount.y.toInt(),
//                                        listState.firstVisibleItemScrollOffset - dragAmount.y.toInt()
//                                    )
//                                }
//                            }
//                        }
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
                        Row(
                            modifier = Modifier.fillMaxWidth().draggable(
                                onDragStarted = { println("start") },
                                onDragStopped = {
                                    println("stopped")
                                    if (dragEventsCount.value <= 1) {
                                        println("pseudodrag" + dragEventsCount)
                                        content.kandinskyScreen(text, index, map[text]!!)
                                    }
                                    dragEventsCount.value = 0
                                },
                                state = dragScope,
                                orientation = Orientation.Vertical
                            ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = AnnotatedString(text = text),
                                fontSize = 45.sp,
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
                                            println("current tap" + index)
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
}
