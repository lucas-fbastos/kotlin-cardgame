@file:Suppress("FunctionName")

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import components.Battlefield
import components.PlayerHand
import entities.Card
import entities.Player
import seeder.seed

@Composable
@Preview
fun App(deck: List<Card>) {
    val (playerDeck, opponentDeck) = deck.chunked(10)
    var turn: Int by rememberSaveable { mutableStateOf(1) }
    var canPlay: Boolean by rememberSaveable { mutableStateOf(true) }
    var opponenthand: MutableList<Card> by rememberSaveable {
        mutableStateOf(
            opponentDeck.take(5).toMutableStateList()
        )
    }
    val player: Player by rememberSaveable {
        mutableStateOf(
            Player(
                hand = playerDeck
                    .take(5)
                    .toMutableList()
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
            Text(
                text = "TURN: $turn",
                modifier = Modifier.size(100.dp)
            )
            Battlefield(
                onPlayChange = { canPlay = it },
                playerArena = player.arena
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
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
    val cards = seed()
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
        title = "MindBug",
    ) {
        App(
            deck = cards
        )
    }
}
