@file:Suppress("FunctionName")

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex
import components.Battlefield
import components.OpponentHand
import components.PlayerHand
import components.shared.StatItem
import constants.COLOR_BACKGROUND
import constants.COLOR_PRIMARY
import entities.Card
import entities.Opponent
import entities.Player
import helper.BoardHelper
import seeder.seed

@Composable
@Preview
fun App() {
    val (playerCards, opponentCards, playerHand, opponentHand) = seed()
        .chunked(size = 5)
        .toMutableList()

    val turn by BoardHelper.turn.collectAsState()
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

    if (player.lifePoints.value == 0) endGame(playerWon = false).also { canPlay = false }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(COLOR_BACKGROUND)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .width(width = 1200.dp)
                    .zIndex(1f),

                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                StatItem(
                    label = "Turn",
                    value = turn.toString(),
                    color = COLOR_PRIMARY,
                    isEmphasis = true,
                )

                OpponentHand(opponent = opponent)

                Battlefield(
                    player = player,
                    opponent = opponent,
                )

                PlayerHand(
                    player = player,
                    opponent = opponent,
                    canPlay = canPlay,
                )
            }
        }
    }
}

@Composable
fun endGame(playerWon: Boolean) {
    Window(
        onCloseRequest = { },
        resizable = false,
        title = "Game Ended",
        state = WindowState(width = 200.dp, height = 200.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = if (playerWon) "YOU WON!" else "YOU LOST!"
        )
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
