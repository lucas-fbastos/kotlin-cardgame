package components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constants.COLOR_BORDER
import constants.COLOR_PRIMARY
import constants.COLOR_RED
import constants.COLOR_SURFACE
import constants.COLOR_TEXT_PRIMARY
import constants.COLOR_TEXT_SECONDARY
import constants.CustomIcon
import entities.Player
import me.localx.icons.straight.filled.Heart
import me.localx.icons.straight.filled.Tombstone

@Composable
internal fun Stats(player: Player) {
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
        StatItem(
            value = player.lifePoints.value.toString(),
            color = COLOR_RED,
            icon = CustomIcon.Heart,
            isEmphasis = true
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                label = "Mind Bugs",
                value = player.amountOfMindBugs.toString(),
                color = COLOR_PRIMARY,
            )
            StatItem(
                label = "Hand",
                value = player.hand.value.size.toString(),
                color = COLOR_TEXT_PRIMARY
            )
            StatItem(
                label = "Deck",
                value = player.deck.value.size.toString(),
                color = COLOR_TEXT_SECONDARY
            )
            StatItem(
                label = "Arena",
                value = player.arena.value.size.toString(),
                color = COLOR_TEXT_SECONDARY
            )
            StatItem(
                icon = CustomIcon.Tombstone,
                value = player.discardPile.value.size.toString(),
                color = COLOR_TEXT_SECONDARY
            )
        }
    }
}