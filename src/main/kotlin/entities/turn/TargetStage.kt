package entities.turn

class TargetStage : TurnStage {

    override fun getType(): StageType = StageType.TARGET

    override fun stageAction(stageContext: StageContext) {
        val stackSize = stageContext.caster.abilitiesToResolve.value?.let { stack ->
            if (stack.size > 0){
                stageContext.caster.selectedAbility.value = stack.pop()
            }
        }
    }

    override fun decideNext(stageContext: StageContext): TurnStage {
       return ResolveStage()
    }
}