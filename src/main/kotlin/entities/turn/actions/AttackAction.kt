package entities.turn.actions

import entities.Opponent
import entities.Player

class AttackAction : TurnAction {

    override fun resume(caster: Player, opponent: Player) {
        caster.selectedCard.value?.let {
            caster.attack(
                opponent = opponent as Opponent,
                attacker = it
            )
        } ?: error("NO SELECTED CARD DURING ATTACK  ACTION RESUME!!!!")
    }
}