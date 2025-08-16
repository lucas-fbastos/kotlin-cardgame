package entities.turn

import entities.card.Card
import entities.abilities.GameContext
import entities.abilities.StackAbility
import java.util.Stack

class ResolveStage : TurnStage {

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

        val abilitiesToResolve = stageContext.caster
            .abilitiesToResolve
            .value

        val selectedAbility = stageContext.caster.selectedAbility.value

        return when {

            selectedAbility?.skill?.targetable == true && selectedAbility.target?.cardTarget == null -> ResolveStage()

            abilitiesToResolve?.isNotEmpty() == true && !abilitiesToResolve.isTopTargetable() -> ResolveStage()
                        .moveStage(stageContext = stageContext)

            abilitiesToResolve?.isEmpty() == true  -> EndStage().moveStage(stageContext=stageContext)

            else -> ResolveStage()
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

private fun Stack<StackAbility>?.isTopTargetable() : Boolean = this?.peek()?.skill?.targetable == true
