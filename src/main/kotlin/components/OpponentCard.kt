
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entities.Card

@Composable
fun OpponentCard(
    card : Card,
) {

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
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = card.id.toString())

        }
        Row {
            Text(text = card.name)
        }
    }
}