package entities.abilities

import entities.Player

class HitDirectlyOnAttack(
    val amountToHit: Int,
    override val trigger: AbilityTrigger = AbilityTrigger.ON_ATTACK
) : AbilityCommand {

    private fun effect(target: Player){
        target.lifePoints.value -= amountToHit
    }

    override fun resolve(gameContext: GameContext) {
        effect(gameContext.opponent)
    }


}