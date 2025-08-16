package entities.turn.stages

import entities.card.Card
import entities.Player

interface TurnStage {

    fun getType(): StageType

    fun stageAction( stageContext: StageContext )

    fun decideNext(stageContext: StageContext) : TurnStage

    fun moveStage( stageContext: StageContext): TurnStage {
        stageAction(stageContext = stageContext)
        val next =  decideNext(stageContext = stageContext)
        println("--------->>>>>MOVING FROM ${this.javaClass.simpleName} to ${next.javaClass.simpleName}")
        return next
    }

}