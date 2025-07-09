package entities.turn

import entities.abilities.AbilityTrigger
import java.util.Stack

class EndStage : TurnStage {

    override fun getType() = StageType.END

    override fun stageAction(stageContext: StageContext) {
        stageContext.caster.selectedAbility.value = null
        stageContext.caster.abilitiesToResolve.value = Stack()
    }

    override fun decideNext(stageContext: StageContext): TurnStage = PlayStage(trigger = AbilityTrigger.ON_PLAY)

}