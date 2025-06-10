package components.arena

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.shared.KeywordBadge
import constants.COLOR_SECONDARY
import constants.CustomIcon
import entities.Card
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
) {
    val offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(width = 150.dp, height = 250.dp)
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = offset.y.roundToInt()
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A2A3E),
                        Color(0xFF1A1A2E)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6),
                        Color(0xFFE879F9)
                    )
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
        Column {
            // Image section (70% of card height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp) // 70% of 250dp
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
                // Header with name and strength
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Card name
                    Text(
                        text = card.name,
                        modifier = Modifier.weight(1f),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF8B5CF6),
                                        Color(0xFF6D28D9)
                                    )
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

                Spacer(modifier = Modifier.height(8.dp))

                // Placeholder for card image
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Card Image",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center),
                    tint = Color(0xFF8B5CF6).copy(alpha = 0.6f)
                )

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

            // Action section (30% of card height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp) // 30% of 250dp
                    .background(Color(0xFF1F1F35))
                    .padding(8.dp)
            ) {
                when {
                    player.attackedBy.value != null && card.playerOwned && card.canDefend(attacker = player.attackedBy.value!!) -> {
                        IconButton(
                            onClick = { player.defend(opponent = opponent, card = card) },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ){
                            Icon(
                                painter = rememberVectorPainter(CustomIcon.Shield),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Color(0xFF8B5CF6).copy(alpha = 0.6f),
                            )

                        }
                    }
                    card.playerOwned && player.attackedBy.value == null -> {
                        IconButton(
                            onClick = { player.attack(opponent = opponent, attacker = card) },
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
                                 tint = Color(0xFF8B5CF6).copy(alpha = 0.6f),
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
                                    color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
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


@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(32.dp)
            .fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
