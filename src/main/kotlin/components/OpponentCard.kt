
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OpponentCard() {

    Box(modifier = Modifier
        .padding(5.dp)
        .size(
            width = 150.dp,
            height = 250.dp
        )
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            color = Color.Black
        )
    ) {

    }
}