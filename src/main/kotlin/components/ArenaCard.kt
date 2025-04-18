package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import entities.Card
import entities.Opponent
import entities.Player
import kotlin.math.roundToInt


@Composable
fun ArenaCard(
    card: Card,
    player: Player,
    opponent: Opponent,
) {
    val offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
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
                        text = card.id.toString(),
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
            card.keywords.forEach {
                Column {
                    Text(
                        text = it.getType().name,
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )
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
            player.attackedBy.value?.let {
                if (card.playerOwned && card.canDefend(attacker = it)) {
                    Row {
                        Button(
                            onClick = {
                                card.defend(opponent = opponent, player = player)
                            },
                            enabled = true,
                        ) {
                            Text("Defend")
                        }
                    }
                }
            }
            if (card.playerOwned && player.attackedBy.value == null) {
                Row {
                    Button(
                        onClick = { player.attack(opponent = opponent, attacker = card) },
                        enabled = true
                    ) {
                        Text("Attack")
                    }
                }
            }else if (card.playerOwned){
                Row {
                    Text("ATTACKED BY: ${player.attackedBy.value?.name}"
                    )
                }
            }
        }
    }
}