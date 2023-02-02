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
import androidx.compose.material.Button
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
                Column( modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    var cenerObj = mutableStateOf(Pair("",0))
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
                                        var pos = globalIndex - listState.firstVisibleItemIndex + 1
                                        if (pos.equals(8)) {
                                            cenerObj.value =Pair(homeScreenItems[index], index)
                                            return@derivedStateOf 1f
                                        } else return@derivedStateOf 0.5f
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
                                    .animateContentSize()
                            )
                        }
                    }
                    Spacer(Modifier.size(50.dp))
                    Button(onClick = {
                        println("current" + cenerObj.value.second)
                        content.kandinskyScreen(cenerObj.value.first, cenerObj.value.second, map[cenerObj.value.first]!!)
                    },
                        modifier = Modifier.width(150.dp).height(50.dp)) {
                        Text(text = "Рисовать", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}
