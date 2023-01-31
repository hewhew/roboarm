import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.abs

@Composable
fun AsyncImage() {
    val homeScreenItems = remember {
        listOf(
            Icons.Default.Person,
            Icons.Default.Home,
            Icons.Default.Add,
        )
    }
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)
    val (rowHalfSize, setRowHalfSize) = remember { mutableStateOf<Int?>(null) }
    val horizontalContentPadding = 16.dp
    val density = LocalDensity.current
    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = horizontalContentPadding, vertical = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                setRowHalfSize(it.width / 2 - with(density) { horizontalContentPadding.roundToPx() })
            }
    ) {
        items(Int.MAX_VALUE) { globalIndex ->
            val index = globalIndex % homeScreenItems.size
            val opacity by remember(rowHalfSize) {
                derivedStateOf {
                    if (rowHalfSize == null) return@derivedStateOf 0.5f
                    val currentItemInfo = listState.layoutInfo.visibleItemsInfo
                        .firstOrNull() { it.index == globalIndex }
                        ?: return@derivedStateOf 0.5f
                    val itemHalfSize = currentItemInfo.size / 2
                    (1f - minOf(1f, abs(currentItemInfo.offset + itemHalfSize - rowHalfSize).toFloat() / itemHalfSize) * 0.5f)
                }
            }
            Icon(
                homeScreenItems[index], null,
                modifier = Modifier
                    .alpha(opacity)
                    .scale(opacity)
            )
        }
    }
}