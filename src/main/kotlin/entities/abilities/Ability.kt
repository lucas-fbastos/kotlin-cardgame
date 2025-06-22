package entities.abilities
interface AbilityCommand {

    val trigger: AbilityTrigger

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