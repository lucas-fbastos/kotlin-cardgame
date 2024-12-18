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
import endTurn
import entities.Card
import entities.Opponent
import entities.Player
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerCard(
    card: Card,
    canPlay: Boolean,
    onPlayChange: (Boolean) -> Unit,
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
                width = 381.dp,
                height = 481.dp
            )
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = offset.y.roundToInt()
                )
            }
            .background(
                color = Color.Transparent,
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
                if (canPlay) {
                    detectDragGestures(
                        onDragEnd = {
                            val isOutsideParent = cardPosition.y < handPosition.y
                            if (isOutsideParent) {
                                onPlayChange(false)
                                player.hand.value.remove(card)
                                player.arena.value.add(card)
                                isPlayed = true
                                endTurn(
                                    player = player,
                                    opponent = opponent,
                                    wasPlayerTurn = true,
                                    onPlayChange = onPlayChange,
                                )
                            } else {
                                println("INSIDE")
                                offset = Offset(0f, 0f)
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
                .offset(x = 100.dp, y = 100.dp)
                .size(181.dp, 281.dp)
                .background(
                    color = Color(0xFF961A7B),
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
                .offset(x = 107.dp, y = 106.dp)
                .size(167.dp, 269.dp)
                .background(color = Color(0xFF040000))
        )

        Box(
            modifier = Modifier
                .offset(x = 114.dp, y = 266.dp)
                .size(152.dp, 92.dp)
                .background(color = Color(0xFFD9D9D9))
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
        )

        Box(
            modifier = Modifier
                .offset(x = 114.dp, y = 113.dp)
                .size(157.dp, 16.dp)
                .background(color = Color(0xFFD9D9D9))
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
        )

        Box(
            modifier = Modifier
                .offset(x = 114.dp, y = 144.dp)
                .size(152.dp, 107.dp)
                .background(color = Color(0xFFD9D9D9))
        )

        Box(
            modifier = Modifier
                .offset(x = 251.dp, y = 113.dp)
                .size(20.dp, 16.dp)
                .background(color = Color(0xFFFF5FDB).copy(alpha = 0.41f)) // Ellipse 1
        )

        Text(
            text = "${card.strength}",
            modifier = Modifier
                .offset(x = 257.dp, y = 113.dp)
                .background(Color.Transparent), // No background
            fontSize = 12.sp,
            lineHeight = 15.sp,
        )

        Text(
            text = card.name,
            modifier = Modifier
                .offset(x = 133.dp, y = 113.dp),
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
                    .offset(x = 126.dp, y = 297.dp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                color = Color.Black
            )
        }
    }

}