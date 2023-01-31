import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.useResource
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import java.util.stream.Collectors.toList
import kotlin.random.Random
import org.apache.commons.io.FilenameUtils;
import java.io.*

@ExperimentalComposeUiApi
object ContentState {

    private var currentScreen = mutableStateOf(ScreenEnum.CLOUD)

    private var word = mutableStateOf("live")

    private var imgNum = mutableStateOf(1)

    private var index = mutableStateOf(0)

    fun wipScreen() {
        currentScreen.value = ScreenEnum.WIP
    }

    fun vectorScreen() {
        currentScreen.value = ScreenEnum.VECTOR_IMAGE
    }

    fun kandinskyScreen(
        word: String = "live",
        index: Int,
        max: Int
    ) {
        val nextInt = Random.nextInt(1,max)
        println("rand" + nextInt)
        val dir = "drawable/svg/" + index + "/" + nextInt + ".svg"
        val content = useResource(dir) {
            it.bufferedReader().lines().collect(Collectors.joining())
        }
        println("draw request")
        Fuel.post("http://localhost:8080/robot/api/draw_binary?tool=green&erase=true")
            .header(Headers.CONTENT_TYPE, "image/svg+xml").body(content).response()
        startRepeatingJob(3000, content = this)
        this.setIndex(index)
        this.setNum(nextInt)
        currentScreen.value = ScreenEnum.KANDINSKY_IMAGE
    }

    fun cloudScreeen() {
        currentScreen.value = ScreenEnum.CLOUD
    }

    fun getCurrentScreen(): ScreenEnum {
        return currentScreen.value
    }

    fun getIndex(): Int {
        return index.value
    }

    fun setIndex(index: Int) {
        this.index.value = index
    }

    fun getNum(): Int {
        return imgNum.value
    }

    fun setNum(num: Int) {
        this.imgNum.value = num
    }
}
