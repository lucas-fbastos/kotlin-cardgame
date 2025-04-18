package components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import entities.Opponent
import entities.Player

@Composable
fun PlayerHand(
    player: Player,
    opponent: Opponent,
    canPlay: Boolean,
) {
    var positionHand by remember { mutableStateOf(Offset.Zero) }
    var sizeHand by remember { mutableStateOf(Offset.Zero) }

    Row(
        modifier = Modifier
            .height(20.dp)
    ) {
        Text(
            text = "Health: ${player.lifePoints.value}",
            color = Color.Red
        )
    }
    Row {
        Text(
            text = "Mindbugs: ${player.amountOfMindBugs}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Cards: ${player.hand.value.size}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Deck: ${player.deck.value.size}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Arena: ${player.arena.value.size}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Graveyard: ${player.discardPile.value.size}",
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier
            .height(400.dp)
            .border(
                width = 5.dp,
                shape = RoundedCornerShape(
                    size = 5.dp
                ),
                color = Color.Red
            ).onGloballyPositioned { layoutCoordinates ->
                positionHand = layoutCoordinates.positionInRoot()
                sizeHand = Offset(
                    x = layoutCoordinates.size.width.toFloat(),
                    y = layoutCoordinates.size.height.toFloat()
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Instead of wrapping the forEach with key, use key for each individual item
        player.hand.value.forEach { card ->
            // Use card's id as the key to help Compose track individual cards
            key(card.id) {
                PlayerCard(
                    canPlay = canPlay,
                    handPosition = positionHand,
                    card = card,
                    player = player,
                    opponent = opponent,
                )
            }
        }
    }
}