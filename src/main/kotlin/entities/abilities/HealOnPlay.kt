package entities.abilities

import entities.Player

class HealOnPlay(
    val amountToHeal: Int,
    override val trigger: AbilityTrigger = AbilityTrigger.ON_PLAY,
    override val targetable: Boolean = false,
    ) : AbilityCommand {

    private fun effect(
        caster: Player,
    )  {
         caster.lifePoints.value += amountToHeal
    }

    override fun resolve(
       gameContext: GameContext,
    ) = effect(gameContext.caster)

}