package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.shared.KeywordBadge
import entities.card.Card
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
    modifier: Modifier = Modifier,
) {
    var offset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }
    var isPlayed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(5.dp)
            .offset {
                IntOffset(
                    x = offset.x.roundToInt(),
                    y = offset.y.roundToInt()
                )
            }
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A2A3E),
                        Color(0xFF1A1A2E),
                        Color(0xFF0F0F1F)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6),
                        Color(0xFFE879F9),
                        Color(0xFF8B5CF6)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                spotColor = Color(0xFF8B5CF6).copy(alpha = 0.3f)
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
                            val currentCardY = cardPosition.y + offset.y
                            val dragThreshold = 30f
                            val isOutsideParent = currentCardY < (handTopBoundary - dragThreshold)

                            if (isOutsideParent) {
                                BoardHelper.blockPlayer()
                                player.playCard(
                                    card = card,
                                    opponent = opponent)
                                isPlayed = true
                                player.endTurn(
                                    player = player,
                                    opponent = opponent,
                                    wasPlayerTurn = true,
                                )
                            } else {
                                println("INSIDE - Card Y: $currentCardY, Hand Y: $handTopBoundary")
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

        // Responsive scaling factor
        val scale = (cardSize.x / 160f).coerceIn(0.75f, 1.25f)
        val fontSize = (14 * scale).sp
        val smallFontSize = (11 * scale).sp
        val iconSize = (32 * scale).dp
        val padding = (12 * scale).dp
        val imageHeight = (cardSize.x * 0.85f).dp
        val keywordPadding = (8 * scale).dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = card.name + canPlay,
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    modifier = Modifier
                        .size(iconSize)
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
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height((8 * scale).dp))

            // Main card image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4C1D95),
                                Color(0xFF312E81),
                                Color(0xFF1E1B4B)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {

                card.image?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Card Artwork",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Card Artwork",
                    modifier = Modifier
                        .size(60.dp * scale)
                        .align(Alignment.Center),
                    tint = Color(0xFF8B5CF6).copy(alpha = 0.5f)
                )

                // Keywords overlay
                if (card.keywords.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(keywordPadding),
                        horizontalArrangement = Arrangement.spacedBy((4 * scale).dp)
                    ) {
                        items(card.keywords) { keyword ->
                            KeywordBadge(
                                keyword = keyword,
                                scale = scale,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height((8 * scale).dp))

            card.flavorText?.let { flavorText ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF1F1F35),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding((8 * scale).dp)
                ) {
                    Text(
                        text = flavorText,
                        color = Color(0xFFD1D5DB),
                        fontSize = smallFontSize,
                        fontStyle = FontStyle.Italic,
                        lineHeight = (14 * scale).sp,
                        textAlign = TextAlign.Center,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
