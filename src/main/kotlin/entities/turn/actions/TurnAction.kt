package entities.turn.actions

import entities.Player

interface TurnAction{

    fun resume( caster: Player, opponent: Player)

}