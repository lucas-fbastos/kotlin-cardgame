package entities.abilities

import entities.Card
import entities.Player

interface AbilityCommand {

    val trigger: AbilityTrigger

    val targetable: Boolean

    fun resolve(
        gameContext: GameContext
    )

}

enum class AbilityTrigger{
    ON_PLAY,
    ON_ATTACK,
    ON_DEFEAT,
    PERMANENT,
}

data class StackAbility(
    val target: Target?,
    val skill: AbilityCommand,
)

data class Target(
    val playerTarget: Player?,
    val cardTarget: Card?
)
