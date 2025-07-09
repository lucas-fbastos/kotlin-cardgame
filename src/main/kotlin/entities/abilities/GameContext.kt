package entities.abilities

import entities.Card
import entities.Player

data class GameContext(
    val caster: Player,
    val opponent: Player,
    val card: Card?,
    val targetCard: Card?
)