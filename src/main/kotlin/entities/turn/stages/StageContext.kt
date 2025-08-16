package entities.turn.stages

import entities.Player
import entities.card.Card

data class StageContext(
    val caster: Player,
    val opponent: Player,
    var selectedCard: Card?,
)