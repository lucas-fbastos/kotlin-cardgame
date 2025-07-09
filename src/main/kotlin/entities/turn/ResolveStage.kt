package entities.turn

import entities.abilities.GameContext

class ResolveStage : TurnStage {

    override fun getType(): StageType = StageType.RESOLVE

    override fun stageAction(stageContext: StageContext) {

        checkSelectedAbility(stageContext = stageContext)

        stageContext.caster.selectedAbility.value?.let { ability ->
                ability
                    .skill
                    .resolve(gameContext = stageContext.toGameContext())

                stageContext.caster.selectedAbility.value = null
        }
    }

    override fun decideNext(stageContext: StageContext): TurnStage {
        return stageContext
            .caster
            .abilitiesToResolve
            .value
            .let {
                when{
                    it?.isNotEmpty() == true && it.peek().skill.targetable -> ResolveStage()
                    it?.isNotEmpty() == true && it.peek()?.skill?.targetable == false -> ResolveStage().moveStage(stageContext = stageContext)
                    else -> EndStage().moveStage(stageContext = stageContext)
                }
            }
    }
}

private fun checkSelectedAbility(stageContext: StageContext){
    stageContext.caster.abilitiesToResolve.value?.let { abilities ->
        if(stageContext.caster.selectedAbility.value == null && abilities.isNotEmpty())
            stageContext.caster.selectedAbility.value = abilities.pop()
    }
}

private fun StageContext.toGameContext() = GameContext(
    caster = caster,
    opponent = opponent,
    card = selectedCard,
    targetCard = null
)
