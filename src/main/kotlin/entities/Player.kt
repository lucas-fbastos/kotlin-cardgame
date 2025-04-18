package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import helper.BoardHelper

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: MutableState<Int> = mutableStateOf(value = 3),
    val hand: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val discardPile: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val deck: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val arena: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val attackedBy: MutableState<Card?> = mutableStateOf(value = null)
) {

    internal fun buyCard() {
        hand.value
            .add(deck.value.first())
        deck.value.removeFirst()
    }

    fun playCard(card: Card) {
        hand.value = hand.value.toMutableList()
            .apply { remove(card) }
        arena.value = arena.value.toMutableList()
            .apply { add(card) }
    }

    fun setAttackedBy(attacker: Card) {
        attackedBy.value = attacker
    }

    fun takeHit() {
        lifePoints.value = lifePoints.value.dec()
    }

    fun endTurn(
        opponent: Opponent,
        player: Player,
        wasPlayerTurn: Boolean,
    ) {
        if (wasPlayerTurn)
            BoardHelper.blockPlayer().also {
                if (player.deck.value.size > 0 && player.hand.value.size < 5) player.buyCard()
                opponent.act(player = player)
                return
            }
        BoardHelper.releasePlayer()
        BoardHelper.increaseTurn().also {
            if (opponent.deck.value.size > 0 && opponent.hand.value.size < 5) opponent.buyCard()
        }
    }


}


