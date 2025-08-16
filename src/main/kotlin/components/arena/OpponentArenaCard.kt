package components.arena

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.shared.KeywordBadge
import constants.CARD_STRENGTH_GRADIENT
import constants.COLOR_BRIGHT_RED
import constants.COLOR_CORAL
import constants.COLOR_CRIMSON
import constants.COLOR_DARK_ORANGE
import constants.COLOR_GOLD
import constants.COLOR_ORANGE
import constants.COLOR_ORANGE_RED
import constants.COLOR_RED
import constants.DEFAULT_ANIMATION_DURATION
import constants.OPPONENT_BACKGROUND_GRADIENT
import constants.OPPONENT_BORDER_GRADIENT
import entities.card.Card
import entities.Opponent
import entities.Player
import entities.turn.StageType
import kotlin.math.roundToInt

@Composable
fun OpponentArenaCard(
    card: Card,
    cardWidth: Dp,
    cardHeight: Dp,
    player: Player,
    opponent: Opponent,
) {
    val offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }

    val isAttacking by card.isAttacking

    val attackOffset by animateFloatAsState(
        targetValue = if (isAttacking) 30f else 0f,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            card.resetAttackAnimation()
        }
    )

    val attackScale by animateFloatAsState(
        targetValue = if (isAttacking) 1.15f else 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val attackRotation by animateFloatAsState(
        targetValue = if (isAttacking) -8f else 0f,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(width = cardWidth, height = cardHeight)
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = (offset.y + attackOffset).roundToInt()
                )
            }
            .scale(attackScale)
            .rotate(attackRotation)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isAttacking) {
                        listOf(
                            COLOR_ORANGE_RED,
                            COLOR_BRIGHT_RED,
                            COLOR_CORAL
                        )
                    } else {
                        OPPONENT_BACKGROUND_GRADIENT
                    }
                )
            )
            .border(
                width = if (isAttacking) 4.dp else 2.dp,
                brush = Brush.linearGradient(
                    colors = if (isAttacking) {
                        listOf(
                            COLOR_ORANGE_RED,
                            COLOR_GOLD
                        )
                    } else {
                        OPPONENT_BORDER_GRADIENT
                    }
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .onGloballyPositioned { layoutCoordinates ->
                cardPosition = layoutCoordinates.positionInRoot()
                cardSize = Offset(
                    x = layoutCoordinates.size.width.toFloat(),
                    y = layoutCoordinates.size.height.toFloat()
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
                            colors = if (isAttacking) {
                                listOf(
                                    COLOR_BRIGHT_RED,
                                    COLOR_ORANGE_RED,
                                    COLOR_CRIMSON
                                )
                            } else {
                                OPPONENT_BACKGROUND_GRADIENT
                            }
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

                    Box(
                        modifier = Modifier
                            .size(if (isAttacking) 36.dp else 32.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = if (isAttacking) {
                                        listOf(
                                            COLOR_GOLD,
                                            COLOR_ORANGE,
                                            COLOR_ORANGE_RED
                                        )
                                    } else {
                                        CARD_STRENGTH_GRADIENT
                                    }
                                ),
                                shape = CircleShape
                            )
                            .border(
                                width = if (isAttacking) 3.dp else 2.dp,
                                color = if (isAttacking) {
                                    Color.White
                                } else {
                                    Color.White.copy(alpha = 0.8f)
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = card.strength.toString(),
                            color = Color.White,
                            fontSize = if (isAttacking) 16.sp else 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Keywords overlay on image
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(actionHeight) // 30% of 250dp
                    .background(
                        if (isAttacking) {
                            COLOR_ORANGE_RED
                        } else {
                            COLOR_DARK_ORANGE
                        }
                    )
                    .padding(8.dp)
            ) {
                if (isAttacking) {
                    Text(
                        text = "ATTACKING!",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                player.turnStage.value?.let { stage ->
                    if (stage.getType() == StageType.RESOLVE) {
                        ActionButton(
                            text = "Target",
                            onClick = {
                                player.setTarget(
                                    target = card,
                                    opponent = opponent
                                )
                            },
                            backgroundColor = COLOR_RED,
                        )
                    }
                }
            }
        }

        if (isAttacking) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Transparent,
                                COLOR_ORANGE_RED.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}