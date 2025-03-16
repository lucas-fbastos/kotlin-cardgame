package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: MutableState<Int> = mutableStateOf(value = 3),
    val hand: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val discardPile: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val deck: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val arena: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val attackedBy: MutableState<Card?> = mutableStateOf(value= null)
) {

    fun playCard(card: Card) {
        hand.value = hand.value.toMutableList()
            .apply { remove(card) }
        // Assign a new list instance to arena.value with the added card
        arena.value = arena.value.toMutableList()
            .apply { add(card) }
    }

    fun setAttackedBy(attacker: Card){
        attackedBy.value = attacker
    }

    fun takeHit() {
        lifePoints.value = lifePoints.value.dec()
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


