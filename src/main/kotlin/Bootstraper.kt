@file:Suppress("FunctionName")

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import entities.Card
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
                deck = mutableStateOf(opponentCards.toOpponentCard()),
                hand = mutableStateOf(opponentHand.toOpponentCard()),
            )
        )
    }

    val player: Player by rememberSaveable {
        mutableStateOf(
            Player(
                hand = mutableStateOf(playerHand.toMutableList()),
                deck = mutableStateOf(playerCards.toMutableList())
            )
        )
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .width(width = 1200.dp),
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
                player = player,
                opponent = opponent,
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlayerHand(
                player = player,
                opponent = opponent,
                canPlay = canPlay,
                onPlayChange = {
                    if (canPlay) turn += 1
                    canPlay = it
                },
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

private fun List<Card>.toOpponentCard() =
    this.map {
        it.copy(playerOwned = false)
    }.toMutableList()
