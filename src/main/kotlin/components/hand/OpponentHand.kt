package components.hand

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.OpponentCard
import components.shared.Stats
import constants.COLOR_BACKGROUND
import constants.COLOR_BORDER
import constants.COLOR_SURFACE
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
        Stats(player = opponent)

        // Card hand area - showing card backs since it's opponent
        OpponentCardHandArea(opponent = opponent)
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


