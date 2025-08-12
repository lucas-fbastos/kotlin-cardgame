package entities.turn

import entities.Card
import entities.Opponent
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

data class StageContext(
    val caster: Player,
    val opponent: Player,
    var selectedCard: Card?,
)

enum class StageType{
    PLAY,
    RESOLVE,
    END
}