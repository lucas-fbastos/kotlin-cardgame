package helper

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

class ImageHelper {
    companion object {
        fun loadImageFromResources(resourcePath: String): ImageBitmap {
            val inputStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("Resource not found: $resourcePath")

            return inputStream.use { stream ->
                Image.makeFromEncoded(stream.readBytes()).toComposeImageBitmap()
            }
        }
    }
}
