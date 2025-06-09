package components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.shared.StatItem
import constants.COLOR_BACKGROUND
import constants.COLOR_BORDER
import constants.COLOR_PRIMARY
import constants.COLOR_RED
import constants.COLOR_SURFACE
import constants.COLOR_TEXT_PRIMARY
import constants.COLOR_TEXT_SECONDARY
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


    Column{
        player.attackedBy.value?.let {
            ActionButton(
                text = "Take Damage",
                onClick = { player.takeDirectHit(opponent = opponent)},
                backgroundColor = COLOR_RED,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(COLOR_BACKGROUND)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        PlayerStats(player = player, opponent  = opponent)

        CardHandArea(
            player = player,
            opponent = opponent,
            canPlay = canPlay,
            onPositionChanged = { position, size ->
                positionHand = position
                sizeHand = size
            }
        )
    }
}

@Composable
private fun PlayerStats(player: Player) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = COLOR_SURFACE,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Health - emphasized as most important stat
        StatItem(
            label = "HP",
            value = player.lifePoints.value.toString(),
            color = COLOR_RED,
            isEmphasis = true
        )

        // Other stats in compact format
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem("Mind Bugs", player.amountOfMindBugs.toString(), COLOR_PRIMARY)
            StatItem("Hand", player.hand.value.size.toString(), COLOR_TEXT_PRIMARY)
            StatItem("Deck", player.deck.value.size.toString(), COLOR_TEXT_SECONDARY)
            StatItem("Arena", player.arena.value.size.toString(), COLOR_TEXT_SECONDARY)
            StatItem("Grave", player.discardPile.value.size.toString(), COLOR_TEXT_SECONDARY)
        }
    }
}

@Composable
private fun CardHandArea(
    player: Player,
    opponent: Opponent,
    canPlay: Boolean,
    onPositionChanged: (Offset, Offset) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                color = COLOR_SURFACE,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = COLOR_BORDER,
                shape = RoundedCornerShape(12.dp)
            )
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.positionInRoot()
                val size = Offset(
                    x = layoutCoordinates.size.width.toFloat(),
                    y = layoutCoordinates.size.height.toFloat()
                )
                onPositionChanged(position, size)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(player.hand.value.isNotEmpty()){
            player.hand.value.forEach { card ->
                key(card.id) {
                    PlayerCard(
                        canPlay = canPlay,
                        handPosition = Offset.Zero,
                        card = card,
                        player = player,
                        opponent = opponent,
                    )
                }
            }
        }else{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No cards in hand",
                    color = COLOR_TEXT_SECONDARY,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}