package entities.turn

import entities.Card
import entities.abilities.GameContext

class ResolveStage(
    val previousStage: TurnStage
) : TurnStage {

    override fun getType(): StageType = StageType.RESOLVE

    override fun stageAction(stageContext: StageContext) {

        checkSelectedAbility(stageContext = stageContext)

        stageContext.caster.selectedAbility.value?.let { ability ->
            if(ability.skill.targetable && stageContext.opponent.arena.value.isEmpty()){
                stageContext.caster.selectedAbility.value = null
                stageContext.selectedCard = null
                return
            }

            if (ability.skill.targetable && ability.target?.cardTarget == null) {
                return
            }

            ability
                .skill
                .resolve(
                    gameContext = stageContext.toGameContext(
                        cardTarget = ability.target?.cardTarget
                    )
                )

            stageContext.caster.selectedAbility.value = null
            stageContext.selectedCard = null
        }
    }

    override fun decideNext(stageContext: StageContext): TurnStage {
        return stageContext
            .caster
            .abilitiesToResolve
            .value
            .let {
                when {
                    it?.isNotEmpty() == true && it.peek().skill.targetable -> ResolveStage(previousStage = previousStage)
                    it?.isNotEmpty() == true && it.peek()?.skill?.targetable == false -> ResolveStage(previousStage).moveStage(
                        stageContext = stageContext
                    )

                    else -> previousStage
                }
            }
    }
}

private fun checkSelectedAbility(stageContext: StageContext) {
    stageContext.caster.abilitiesToResolve.value?.let { abilities ->
        if (stageContext.caster.selectedAbility.value == null && abilities.isNotEmpty())
            stageContext.caster.selectedAbility.value = abilities.pop()
    }
}

private fun StageContext.toGameContext(cardTarget: Card?) = GameContext(
    caster = caster,
    opponent = opponent,
    card = selectedCard,
    targetCard = cardTarget
)
