package entities.turn

import entities.abilities.AbilityTrigger
import entities.abilities.StackAbility
import entities.abilities.Target

class PlayStage(
    val trigger: AbilityTrigger
) : TurnStage {

    override fun getType(): StageType = StageType.PLAY

    override fun stageAction(
        stageContext: StageContext
    ) {
        stageContext.selectedCard?.let {
            it.abilities
                .filter { it.trigger == trigger }
                .map {
                    StackAbility(
                        target = Target(playerTarget = null, cardTarget = null),
                        skill = it
                    )
                }
                .let {
                    stageContext.caster
                        .abilitiesToResolve
                        .value
                        ?.addAll(it)
                }

            stageContext
                .caster
                .abilitiesToResolve
                .value
                ?.let {  abilities ->
                    if(abilities.isNotEmpty()){
                        val ability = abilities.pop()
                        stageContext.caster.selectedAbility.value = ability.takeIf {
                            it.checkTargets(stageContext)
                        }
                    }
                }
        }

    }

    override fun decideNext(stageContext: StageContext): TurnStage {
        stageContext.caster.selectedAbility.value?.let { _ ->
                return ResolveStage(previousStage = this)
        }

        return EndStage().moveStage(stageContext)
    }

    internal fun StackAbility.checkTargets(stageContext: StageContext) =
        stageContext.opponent.arena.value.isNotEmpty() && this.skill.targetable

}