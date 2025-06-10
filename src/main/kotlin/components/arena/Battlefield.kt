package components.arena

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import entities.Card
import entities.Opponent
import entities.Player

@Composable
fun Battlefield(
    player: Player,
    opponent: Opponent,
) {
//OPPONENT AREA
    Box(
        modifier = Modifier
            .requiredWidthIn(
                min(a = 250.dp, b = 1080.dp)
            )
            .zIndex(10f)

    ) {
        Row {
            opponent.arena.value.forEach { card: Card ->
                Column {
                    OpponentArenaCard(
                        card = card,
                    )
                }
            }
        }
    }
//PLAYER AREA
    Box(
        modifier = Modifier
            .requiredWidthIn(
                min(a = 250.dp, b = 1080.dp)
            )
            .zIndex(-1f)

    ) {

        Row {
            player.arena.value.forEach { card ->
                Column {
                    ArenaCard(
                        card = card,
                        player = player,
                        opponent = opponent
                    )
                }
            }
        }
    }
}

