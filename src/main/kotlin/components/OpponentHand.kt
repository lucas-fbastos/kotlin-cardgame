package components

import OpponentCard
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entities.Opponent

@Composable
fun OpponentHand(
    opponent: Opponent,
) {

    Row {
        Text(
            text = "Health: ${opponent.lifePoints.value}",
            color = Color.Red
        )
    }
    Row {
        Text(
            text = "Mindbugs: ${opponent.amountOfMindBugs}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Cards: ${opponent.deck.value.size}",
            color = Color.Black
        )
    }
    Row {
        Text(
            text = "Arena: ${opponent.arena.value.size}",
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier
            .height(300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        opponent.hand.value.forEach { _ ->
            OpponentCard()
        }
    }
}