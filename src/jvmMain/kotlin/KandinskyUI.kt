import androidx.compose.animation.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.IntSize
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.*


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
@Preview
fun kandinskyUI(content: ContentState) {


    val density = LocalDensity.current
    val sample = remember {
        val resourcePath = "drawable/jpg/" + content.getIndex() + "/" + content.getNum() + ".jpg"
        println(resourcePath)
        useResource(resourcePath, ::loadImageBitmap)
    }
    val sampleVector = remember {
        val resourcePath = "drawable/svg/" + content.getIndex() + "/" + content.getNum() + ".svg"
        println(resourcePath)
        useResource(resourcePath) { loadSvgPainter(it, density) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawIntoCanvas { canvas ->
                    canvas.withSave {
                        with(sampleVector) {
//                            println("vector " + size.width.toString() + " " + size.height)
                            draw(size)
                        }
                    }
                }
            }
            bitmap(sample)
        }
    }
}

@ExperimentalComposeUiApi
fun startRepeatingJob(timeInterval: Long, content: ContentState): Job {
    return CoroutineScope(Dispatchers.Default).launch {
        while (NonCancellable.isActive) {
            println("status check")
            val (request, response, result) = Fuel.get("http://localhost:8080/robot/info").responseString()
            if(result.get().contains("drawing done")) {
                content.cloudScreeen()
                this.cancel()
            }
            delay(timeInterval)
        }
    }
}

@Composable
fun bitmap(sample: ImageBitmap) {
    var editable = remember {
        MutableTransitionState(true).apply {
            // Start the animation immediately.
            targetState = false
        }
    }
    AnimatedVisibility(
        visibleState = editable,
        enter = fadeIn(tween(20000)),
        exit = fadeOut(tween(20000))
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { canvas ->
                canvas.withSave {
//                        println("bitmap " + size.width.toString() + " " + size.height)
                    canvas.drawImageRect(
                        sample,
                        dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                        paint = Paint()
                    )
                    canvas.translate(0f, 0f)
                }
            }
        }
    }
}