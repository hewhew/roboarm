import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.Dimension
import java.awt.Toolkit

@ExperimentalComposeUiApi
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
fun main() = application {
    val content = remember {
        ContentState
    }
    Window(
        onCloseRequest = ::exitApplication,
//        undecorated= true,
        title = "SberArm",
        state = WindowState(
//            placement = WindowPlacement.Fullscreen,
            position = WindowPosition.Aligned(Alignment.Center),
//            size = getPreferredWindowSize(720, 1280)
        ),
    ) {
        MaterialTheme {
            when(content.getCurrentScreen()) {
                ScreenEnum.CLOUD -> cloudUI(content)
                ScreenEnum.KANDINSKY_IMAGE -> kandinskyUI(content)
                ScreenEnum.VECTOR_IMAGE -> TODO()
                ScreenEnum.WIP -> TODO()
            }
        }
    }
}

fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
    val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
    val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
    val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
    return DpSize(width.dp, height.dp)
}
