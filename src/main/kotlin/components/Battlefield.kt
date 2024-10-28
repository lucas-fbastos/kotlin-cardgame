package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import entities.Card
import kotlin.math.roundToInt

@Composable
fun Battlefield(
    onPlayChange: (Boolean) -> Unit,
    playerArena: MutableList<Card>,
    opponentArena: MutableList<Card>,
) {
    Box(
        modifier = Modifier
            .background(Color.Green.copy(alpha = 0.5f))
            .height(300.dp)
            .requiredWidthIn(
                min(a = 250.dp, b = 1080.dp)
            )
            .zIndex(-1f)

    ) {
        Row {
            opponentArena.forEach { card: Card ->
                Column {
                    ArenaCard(
                        card = card
                    )
                }
            }
        }
        Row {
            playerArena.forEach { card ->
                Column {
                    ArenaCard(
                        card = card,
                    )
                }
            }
        }
    }
}

@Composable
fun ArenaCard(
    card: Card,
) {
    var offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier
        .padding(5.dp)
        .size(
            width = 150.dp,
            height = 250.dp
        )
        .offset {
            IntOffset(
                x = offset.x.roundToInt(),
                y = offset.y.roundToInt()
            )
        }
        .background(
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        )
        .onGloballyPositioned { layoutCoordinates ->
            cardPosition = layoutCoordinates.positionInRoot()
            cardSize = Offset(
                x = layoutCoordinates.size.width.toFloat(),
                y = layoutCoordinates.size.height.toFloat()
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = card.name,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                Column {
                    Badge(
                        backgroundColor = Color.Cyan
                    ) {
                        Text(
                            text = card.strength.toString(),
                            color = Color.White,
                        )
                    }
                }
            }
            card.flavorText?.let {
                Column {
                    Text(
                        text = it,
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            Row {
                Button(
                    onClick = { },
                    enabled = true
                ) {
                    Text("Attack")
                }
            }
        }
    }
}
