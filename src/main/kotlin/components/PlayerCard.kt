package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constants.BLACK
import constants.GRAY
import constants.LIGHT_PURPLE
import constants.PURPLE
import entities.Card
import entities.Opponent
import entities.Player
import helper.BoardHelper
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerCard(
    card: Card,
    canPlay: Boolean,
    handPosition: Offset,
    player: Player,
    opponent: Opponent,
) {
    var offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }
    var isPlayed by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(
                width = 181.dp,
                height = 281.dp
            )
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = offset.y.roundToInt()
                )
            }
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(2.dp)
            )
            .border(
                width = 1.dp,
                color = Color( 255, 255, 255, 25)
            )
            .onGloballyPositioned { layoutCoordinates ->
                cardPosition = layoutCoordinates.positionInRoot()
                cardSize = Offset(
                    x = layoutCoordinates.size.width.toFloat(),
                    y = layoutCoordinates.size.height.toFloat()
                )
            }
            .pointerInput(canPlay) {
                if (canPlay && player.attackedBy.value == null) {
                    detectDragGestures(
                        onDragEnd = {
                            val handTopBoundary = handPosition.y

                            // Calculate the current position of the card (top + current offset)
                            val currentCardY = cardPosition.y + offset.y

                            // Check if the card is significantly above the hand area (add some threshold)
                            val dragThreshold = 30f
                            val isOutsideParent = currentCardY < (handTopBoundary - dragThreshold)

                            if (isOutsideParent) {
                                BoardHelper.blockPlayer()
                                player.playCard(card = card)
                                isPlayed = true
                                player.endTurn(
                                    player = player,
                                    opponent = opponent,
                                    wasPlayerTurn = true,
                                )
                            } else {
                                println("INSIDE - Card Y: $currentCardY, Hand Y: $handTopBoundary")
                                offset = Offset(0f, 0f) // Reset position
                            }
                        },
                        onDrag = { dragAmount ->
                            offset = Offset(
                                x = offset.x + dragAmount.x,
                                y = offset.y + dragAmount.y
                            ).takeIf { !isPlayed } ?: offset
                        }
                    )
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(181.dp, 281.dp)
                .background(
                    color = Color(color = PURPLE),
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black
                )
                .shadow(4.dp, shape = RoundedCornerShape(4.dp))
        )

        Box(
            modifier = Modifier
                .offset(x = 7.dp, y = 6.dp)
                .size(167.dp, 269.dp)
                .background(color = Color(color = BLACK))
        )

        Box(
            modifier = Modifier
                .offset(x = 14.dp, y = 66.dp)
                .size(152.dp, 92.dp)
                .background(color = Color(GRAY))
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
        )

        Box(
            modifier = Modifier
                .offset(x = 14.dp, y = 13.dp)
                .size(157.dp, 16.dp)
                .background(color = Color(color = GRAY))
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
        )

        Box(
            modifier = Modifier
                .offset(x = 14.dp, y = 44.dp)
                .size(152.dp, 107.dp)
                .background(color = Color(color = GRAY))
        )

        Box(
            modifier = Modifier
                .offset(x = 151.dp, y = 13.dp)
                .size(20.dp, 16.dp)
                .background(color = Color(LIGHT_PURPLE).copy(alpha = 0.41f))
        )

        Text(
            text = "${card.strength}",
            modifier = Modifier
                .offset(x = 157.dp, y = 13.dp)
                .background(Color.Transparent), // No background
            fontSize = 12.sp,
            lineHeight = 15.sp,
        )

        Text(
            text = card.name,
            modifier = Modifier
                .offset(x = 33.dp, y = 13.dp),
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            color = Color.Black
        )

        card.flavorText?.let {
            Text(
                text = it,
                modifier = Modifier
                    .offset(x = 26.dp, y = 197.dp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                color = Color.White
            )
        }

    }

}