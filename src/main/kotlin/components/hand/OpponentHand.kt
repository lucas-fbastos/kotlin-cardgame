package components.hand

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.OpponentCard
import components.shared.StatItem
import constants.COLOR_BACKGROUND
import constants.COLOR_BORDER
import constants.COLOR_PRIMARY
import constants.COLOR_RED
import constants.COLOR_SURFACE
import constants.COLOR_TEXT_PRIMARY
import constants.COLOR_TEXT_SECONDARY
import entities.Opponent

@Composable
fun OpponentHand(
    opponent: Opponent,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(COLOR_BACKGROUND)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Opponent stats in a compact grid
        OpponentStats(opponent = opponent)

        // Card hand area - showing card backs since it's opponent
        OpponentCardHandArea(opponent = opponent)
    }
}

@Composable
private fun OpponentStats(opponent: Opponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = COLOR_SURFACE,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = COLOR_BORDER,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OpponentStatItem(
            label = "HP",
            value = opponent.lifePoints.value.toString(),
            color = COLOR_RED,
            isEmphasis = true
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem("Mind Bugs", opponent.amountOfMindBugs.toString(), COLOR_PRIMARY)
            StatItem("Hand", opponent.hand.value.size.toString(), COLOR_TEXT_PRIMARY)
            StatItem("Deck", opponent.deck.value.size.toString(), COLOR_TEXT_SECONDARY)
            StatItem("Arena", opponent.arena.value.size.toString(), COLOR_TEXT_SECONDARY)
            StatItem("Grave", opponent.discardPile.value.size.toString(), COLOR_TEXT_SECONDARY)
        }
    }
}

@Composable
private fun OpponentStatItem(
    label: String,
    value: String,
    color: Color,
    isEmphasis: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            color = COLOR_TEXT_SECONDARY,
            fontSize = if (isEmphasis) 11.sp else 10.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = color,
            fontSize = if (isEmphasis) 18.sp else 14.sp,
            fontWeight = if (isEmphasis) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

@Composable
private fun OpponentCardHandArea(opponent: Opponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                color = COLOR_SURFACE,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = COLOR_BORDER,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (opponent.hand.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Opponent has no cards",
                    color = COLOR_TEXT_SECONDARY,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            opponent.hand.value.forEach { card ->
                key(card.id) {
                    OpponentCard(card = card)
                }
            }
        }
    }
}


