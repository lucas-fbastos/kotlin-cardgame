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
        return decideNext(stageContext = stageContext)
    }

}

data class StageContext(
    val caster: Player,
    val opponent: Player,
    val selectedCard: Card?,
)

enum class StageType{
    PLAY,
    RESOLVE,
    TARGET,
    END
}