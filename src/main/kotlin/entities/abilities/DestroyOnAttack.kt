package entities.abilities

class DestroyOnAttack(
    override val trigger: AbilityTrigger = AbilityTrigger.ON_ATTACK,
    override val targetable: Boolean = true,
) : AbilityCommand {

    override fun resolve(gameContext: GameContext) {
        gameContext.targetCard?.die()
    }

}