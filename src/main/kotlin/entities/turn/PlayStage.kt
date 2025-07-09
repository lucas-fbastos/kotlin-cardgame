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
        }

    }

    override fun decideNext(stageContext: StageContext): TurnStage {
        stageContext.caster.abilitiesToResolve.value?.let { abilities ->
            if(abilities.isNotEmpty() && abilities.peek().skill.targetable){
                return ResolveStage()
            }
        }
        return ResolveStage().moveStage(stageContext)
    }
}