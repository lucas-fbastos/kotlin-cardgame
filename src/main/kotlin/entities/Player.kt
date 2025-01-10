package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: Int = 3,
    var hand: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    var discardPile: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    var deck: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    var arena: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    var isDefending: MutableState<Boolean> = mutableStateOf(value= false)
) {

    fun playCard(card: Card) {
        hand.value = hand.value.toMutableList()
            .apply { remove(card) }
        // Assign a new list instance to arena.value with the added card
        arena.value = arena.value.toMutableList()
            .apply { add(card) }
    }

    fun setDefendingState(){
        isDefending.value = true
    }

    fun endTurn(
        opponent: Opponent,
        player: Player,
        wasPlayerTurn: Boolean,
        onPlayChange: (Boolean) -> Unit,
    ) {
        if (wasPlayerTurn)
            onPlayChange(false).run {
                opponent.act(player = player)
            }
        onPlayChange(true)
    }


}


