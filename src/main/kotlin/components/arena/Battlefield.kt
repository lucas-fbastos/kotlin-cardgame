package components.arena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import constants.COLOR_SECONDARY
import entities.card.Card
import entities.Opponent
import entities.Player

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Battlefield(
    player: Player,
    opponent: Opponent,
    cardWidth: Dp,
    cardHeight: Dp
) {

    val selectedCard : MutableState<Card?> = remember{ mutableStateOf(null) }
    val opponentSelectedCard : MutableState<Card?> = remember{ mutableStateOf(null) }
    val configuration = LocalWindowInfo.current.containerSize
    val screenWidth = configuration.width
    val screenHeight = configuration.height

    //OPPONENT AREA
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f)

    ) {
        Column {
            opponent.arena.value
                .chunked(size = 5)
                .forEach { chunk ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        chunk.forEach { card ->
                            OpponentArenaCard(
                                card = card,
                                player = player,
                                opponent = opponent,
                                cardWidth = cardWidth,
                                cardHeight = cardHeight,
                                modifier = Modifier
                                    .onPointerEvent(eventType = PointerEventType.Enter){
                                        selectedCard.value = card
                                    }
                                    .onPointerEvent(eventType = PointerEventType.Exit){
                                        selectedCard.value = null
                                    }
                            )
                        }
                    }
                }
        }
    }

    //PLAYER AREA
    Box(
        modifier = Modifier.requiredWidthIn( min(a = 250.dp, b = 1080.dp) )
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 20.dp),
            thickness = 2.dp,
            color = COLOR_SECONDARY
        )

        selectedCard.value
            ?.let { card ->
                DetailedArenaCard(
                    card = card,
                    player = player,
                    opponent = opponent,
                    cardWidth = cardWidth*2,
                    cardHeight = cardHeight*2,
                    modifier = Modifier
                        .zIndex(Float.MAX_VALUE)
                        .align(Alignment.CenterStart)
                        .offset(
                            x = Dp((screenWidth / 2) - (cardWidth.value * 3) / 2),
                            y = Dp((-screenHeight / 4).toFloat())),
                )
            }

        Column {
            player.arena.value
                .chunked(size = 5)
                .forEach { chunk ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        chunk.forEach { card ->
                            ArenaCard(
                                card = card,
                                player = player,
                                opponent = opponent,
                                cardWidth = cardWidth,
                                cardHeight = cardHeight,
                                modifier = Modifier
                                    .onPointerEvent(eventType = PointerEventType.Enter){
                                        selectedCard.value = card
                                    }
                                    .onPointerEvent(eventType = PointerEventType.Exit){
                                        selectedCard.value = null
                                    }

                            )
                        }
                    }
            }
        }
    }
}

