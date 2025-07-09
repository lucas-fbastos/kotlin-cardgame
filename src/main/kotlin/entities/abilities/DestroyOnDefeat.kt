package entities.abilities

class DestroyOnDefeat(
    override val trigger: AbilityTrigger = AbilityTrigger.ON_DEFEAT,
    override val targetable: Boolean = true,
) : AbilityCommand {

    override fun resolve(gameContext: GameContext) {
        gameContext.targetCard?.die()
    }

}