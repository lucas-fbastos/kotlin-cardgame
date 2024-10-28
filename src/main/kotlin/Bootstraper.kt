@file:Suppress("FunctionName")

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import components.Battlefield
import components.OpponentHand
import components.PlayerHand
import entities.Opponent
import entities.Player
import seeder.seed

@Composable
@Preview
fun App() {
    val (playerCards, opponentCards, playerHand, opponentHand) = seed()
        .chunked(size = 5)
        .toMutableList()
    var turn: Int by rememberSaveable { mutableStateOf(1) }
    var canPlay: Boolean by rememberSaveable { mutableStateOf(true) }
    val opponent: Opponent by rememberSaveable {
        mutableStateOf(
            Opponent(
                deck = opponentCards,
                hand = opponentHand.toMutableList(),
            )
        )
    }

    val player: Player by rememberSaveable {
        mutableStateOf(
            Player(
                hand = playerHand.toMutableList(),
                deck = playerCards
            )
        )
    }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text("DEBUG MENU ")
                Button(
                    onClick = {
                        canPlay = !canPlay
                        turn = turn.inc()
                    },
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Text("change turn")
                }
            }

            Text(text = "TURN: $turn")

            OpponentHand(opponent = opponent)

            Spacer(modifier = Modifier.height(16.dp))

            Battlefield(
                onPlayChange = { canPlay = it },
                playerArena = player.arena,
                opponentArena = opponent.arena
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlayerHand(
                player = player,
                canPlay = canPlay,
                onPlayChange = {
                    canPlay = it
                }
            )
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = true,
        state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
        title = "MindBug",
    ) {
        App()
    }
}
