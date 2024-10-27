package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Battlefield(onPlayChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.Green.copy(alpha = 0.5f))
            .height(300.dp)
            .width(600.dp)
            .zIndex(-1f)

    )
}