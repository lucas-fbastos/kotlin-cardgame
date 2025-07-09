package entities.turn

import java.util.Stack

class EndStage : TurnStage {

    override fun getType() = StageType.END

    override fun stageAction(stageContext: StageContext) {
        stageContext.caster.selectedAbility.value = null
        stageContext.caster.abilitiesToResolve.value = Stack()
    }

    override fun decideNext(stageContext: StageContext): TurnStage = PlayStage()

}