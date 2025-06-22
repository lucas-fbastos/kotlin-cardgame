package components.hand

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.PlayerCard
import components.arena.ActionButton
import components.shared.Stats
import constants.COLOR_BACKGROUND
import constants.COLOR_BORDER
import constants.COLOR_RED
import constants.COLOR_SURFACE
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

        Stats(player = player)

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
private fun CardHandArea(
    player: Player,
    opponent: Opponent,
    canPlay: Boolean,
    onPositionChanged: (Offset, Offset) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {

        val screenWidth = maxWidth * 0.7f
        val cardWidth = (screenWidth * 0.12f)
        val cardHeight = (cardWidth * 1.66f)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight + 16.dp)
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
                            modifier = Modifier
                                .width(cardWidth)
                                .height(cardHeight)
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

}