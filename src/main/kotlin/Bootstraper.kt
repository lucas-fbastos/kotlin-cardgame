@file:Suppress("FunctionName")

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex
import components.arena.Battlefield
import components.hand.OpponentHand
import components.hand.PlayerHand
import components.shared.StatItem
import constants.COLOR_BACKGROUND
import constants.COLOR_PRIMARY
import entities.Opponent
import entities.Player
import helper.BoardHelper
import seeder.seed

@Composable
@Preview
fun App() {

    val (playerCards, opponentCards, playerHand, opponentHand) = seed()
        .chunked(size = 5)
        .map { it.toMutableList() }

    val turn by BoardHelper.turn.collectAsState()
    var canPlay: Boolean by BoardHelper.canPlay
    val gameFinished: Boolean by BoardHelper.gameFinished
    val playerWon: Boolean by BoardHelper.playerWon

    val opponent: Opponent by rememberSaveable {
        mutableStateOf(Opponent())
    }

    val player: Player by rememberSaveable {
        mutableStateOf(Player())
    }

    player.setCards(
        hand = playerHand,
        deck = playerCards,
    )

    opponent.setCards(
        hand = opponentHand,
        deck = opponentCards,
    )

    if (gameFinished) endGame(
        playerWon = playerWon,
        player = player,
        opponent = opponent
    ).also { canPlay = false }

    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(COLOR_BACKGROUND)
        ) {

            val screenWidth = maxWidth * 0.7f
            val cardWidth = (screenWidth * 0.12f)
            val cardHeight = (cardWidth * 1.66f)

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .fillMaxWidth()
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
                    cardHeight = cardHeight,
                    cardWidth = cardWidth,
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
fun endGame(
    player: Player,
    opponent: Opponent,
    playerWon: Boolean,
    ) {
    Window(
        onCloseRequest = {
            BoardHelper.restartGame()
            player.reset()
            opponent.reset()

            val (playerCards, opponentCards, playerHand, opponentHand) = seed()
                .chunked(size = 5)
                .map {
                    it.toMutableList()
                }

            player.setCards(
                hand = playerHand,
                deck = playerCards,
            )

            opponent.setCards(
                hand = opponentHand,
                deck = opponentCards,
            )

        },
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

