import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.zIndex
import entities.Card
import seeder.seed
import kotlin.math.roundToInt

@Composable
@Preview
fun App(deck: List<Card>) {
    val (playerDeck, opponentDeck) = deck.chunked(10)

    var canPlay by rememberSaveable { mutableStateOf(true) }
    val playerHand by rememberSaveable { mutableStateOf(playerDeck.take(5)) }
    var opponenthand by rememberSaveable { mutableStateOf(opponentDeck.take(5)) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Battlefield()
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            PlayerHand(
                cards = playerHand,
                canPlay = canPlay,
                onPlayChange = {
                    canPlay = it
                }
            )
        }
    }
}

@Composable
fun PlayerHand(
    canPlay: Boolean,
    onPlayChange: (Boolean) -> Unit,
    cards: List<Card>,
) {
    var positionHand by remember { mutableStateOf(Offset.Zero) }
    var sizeHand by remember { mutableStateOf(Offset.Zero) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .border(
                width = 10.dp,
                shape = RoundedCornerShape(
                    size = 5.dp
                ),
                color = Color.Red
            )

            .width(800.dp)
            .height(700.dp)
            .onGloballyPositioned { layoutCoordinates ->
                positionHand = layoutCoordinates.positionInRoot()
                sizeHand = Offset(
                    layoutCoordinates.size.width.toFloat(),
                    layoutCoordinates.size.height.toFloat()
                )
            },
    ) {
        key(positionHand, sizeHand) {
            cards.forEach { card ->
                PlayerCard(
                    canPlay = canPlay,
                    onPlayChange = onPlayChange,
                    handPosition = positionHand,
                    card = card,
                )
            }
        }
    }
}

@Composable
fun PlayerCard(
    card: Card,
    canPlay: Boolean,
    onPlayChange: (Boolean) -> Unit,
    handPosition: Offset,
) {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var cardPosition by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(100.dp, 150.dp)
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(
                color = Color.Blue,
                width = 5.dp,
                shape = RoundedCornerShape(
                    size = 5.dp
                )
            )
            .onGloballyPositioned { layoutCoordinates ->
                cardPosition = layoutCoordinates.positionInRoot()
                cardSize = Offset(
                    layoutCoordinates.size.width.toFloat(),
                    layoutCoordinates.size.height.toFloat()
                )
            }
            .pointerInput(canPlay) {
                if (canPlay) {
                    detectDragGestures(
                        onDragEnd = {
                            val isOutsideParent = cardPosition.y < handPosition.y
                            if (isOutsideParent) {
                                onPlayChange(false)
                                addToBattlefield(card = card)
                            } else {
                                offset = Offset(x = 0f, y = 0f)
                            }
                        },
                    ) { change, dragAmount ->
                        offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                        change.consume()

                        val isOutsideParent = cardPosition.y < handPosition.y
                        if (isOutsideParent) {
                            println("Card is outside the parent")
                        } else {
                            println("Card is inside the parent")
                        }

                    }
                }
            }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = card.name,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 2.5.em,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                )
            )
            Text(
                text = if(canPlay) "playable" else "not playable"
            )
        }


    }
}

@Composable
fun PlayerBattlefield(){
    Row {

    }
}

@Composable
fun Battlefield() {
    Column(
        modifier = Modifier
            .background(Color.Green.copy(alpha = 0.5f))
            .height(300.dp)
            .width(600.dp)
            .zIndex(-1f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row {

        }
        Row {
            PlayerBattlefield()
        }
    }
}

private fun addToBattlefield(card: Card){

}

fun main() = application {
    val cards = seed()
    Window(onCloseRequest = ::exitApplication) {
        App(deck = cards)
    }
}
