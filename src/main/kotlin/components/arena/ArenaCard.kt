package components.arena

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.shared.KeywordBadge
import constants.CARD_BACKGROUND_GRADIENT
import constants.CARD_BORDER_GRADIENT
import constants.CARD_STRENGTH_GRADIENT
import constants.COLOR_DARK_PURPLE
import constants.COLOR_PURPLE
import constants.COLOR_SECONDARY
import constants.CustomIcon
import entities.card.Card
import entities.Opponent
import entities.Player
import me.localx.icons.straight.filled.BoxingGlove
import me.localx.icons.straight.filled.Shield
import kotlin.math.roundToInt

@Composable
fun ArenaCard(
    card: Card,
    player: Player,
    opponent: Opponent,
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier,
) {
    val offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }

    val infiniteTransition = rememberInfiniteTransition()

    val floatY by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val floatX by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .graphicsLayer(
                translationX = floatX,
                translationY = floatY,
                rotationZ = rotation,
                rotationX = PLAYER_CARD_ROTATION_X
            )
            .padding(6.dp)
            .size(
                width = cardWidth,
                height = cardHeight,
            )
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = offset.y.roundToInt()
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = CARD_BACKGROUND_GRADIENT
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = CARD_BORDER_GRADIENT
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .onGloballyPositioned { layoutCoordinates ->
                cardPosition = layoutCoordinates.positionInRoot()
                cardSize = Offset(
                    x = layoutCoordinates.size.width.toFloat(),
                    y = layoutCoordinates.size.height.toFloat(),
                )
            }
    ) {
        val imageHeight = cardHeight * 0.7f
        val actionHeight = cardHeight * 0.3f

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4C1D95),
                                Color(0xFF312E81),
                                Color(0xFF1E1B4B)
                            )
                        )
                    )
            ) {
                card.image?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Card Artwork Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))

                    // Strength
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = CARD_STRENGTH_GRADIENT
                                ),
                                shape = CircleShape
                            )
                            .border(
                                width = 2.dp,
                                color = Color.White.copy(alpha = 0.8f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = card.strength.toString(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Keywords overlay at bottom
                if (card.keywords.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(card.keywords) { keyword ->
                            KeywordBadge(keyword = keyword)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(actionHeight)
                    .background(COLOR_DARK_PURPLE)
                    .padding(8.dp)
            ) {
                when {
                    player.attackedBy.value != null && card.playerOwned && card.canDefend(attacker = player.attackedBy.value!!) -> {
                        IconButton(
                            onClick = {
                                player.defend(
                                    opponent = opponent,
                                    card = card
                                )
                            },

                            modifier = Modifier
                                .align(Alignment.Center)
                                .border(
                                    width = 1.dp,
                                    color = COLOR_PURPLE.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ){
                            Icon(
                                painter = rememberVectorPainter(CustomIcon.Shield),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = COLOR_PURPLE.copy(alpha = 0.6f),
                            )
                        }
                    }
                    card.playerOwned && player.attackedBy.value == null -> {
                        IconButton(
                            onClick = {
                                player.startCombat(
                                    opponent = opponent,
                                    attacker = card
                                )
                            },

                            modifier = Modifier
                                .align(Alignment.Center)
                                .border(
                                    width = 1.dp,
                                    color = Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ){
                            Icon(
                                painter = rememberVectorPainter(CustomIcon.BoxingGlove),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = COLOR_PURPLE.copy(alpha = 0.6f),
                            )
                        }
                    }
                    card.playerOwned -> {
                        IconButton(
                            enabled = false,
                            onClick = {  },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .border(
                                    width = 1.dp,
                                    color = COLOR_PURPLE.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ){
                            Icon(
                                painter = rememberVectorPainter(CustomIcon.Shield),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = COLOR_SECONDARY.copy(alpha = 0.9f),
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val PLAYER_CARD_ROTATION_X = 15f