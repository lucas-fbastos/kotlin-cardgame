package helper

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entities.Opponent
import entities.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BoardHelper{

    companion object{


        private val _turn = MutableStateFlow(1)
        val turn: StateFlow<Int> = _turn.asStateFlow()
        @JvmStatic
         var canPlay : MutableState<Boolean> = mutableStateOf(value = true)

        fun getTurn() : Int = _turn.value

        fun increaseTurn(): Int {
            _turn.value += 1
            return _turn.value
        }

        fun blockPlayer() {  canPlay.value = false }

        fun releasePlayer() { canPlay.value = true }

        fun removeCardsFromBoard(
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

            opponent.discardPile.value = opponent.discardPile.value
                .apply { addAll(opponentDeadCards) }
        }
    }
}