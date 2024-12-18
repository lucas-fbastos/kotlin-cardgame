package helper

import entities.Opponent
import entities.Player

internal fun removeCardsFromBoard(
    opponent: Opponent,
    player: Player
) {
    val playerDeadCards = player.arena.value
        .filterNot { it.alive }
    val opponentDeadCards = opponent.arena.value
        .filterNot { it.alive }

    player.discardPile.value = player.discardPile.value
        .apply { addAll(playerDeadCards) }

    player.arena.value = player.arena.value
        .toMutableList()
        .apply {
            removeAll(playerDeadCards)
        }

    opponent.arena.value = opponent.arena.value
        .toMutableList()
        .apply {
            removeAll(this.filterNot { it.alive })
        }

    opponent.discardPile.value = player.discardPile.value
        .apply { addAll(opponentDeadCards) }
}