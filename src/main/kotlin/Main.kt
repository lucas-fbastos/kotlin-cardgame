
import entities.Match
import entities.Opponent
import entities.Player
import seeder.seed

fun main(args: Array<String>) {
    val deck = seed()
    val player = Player(
        arena = mutableListOf(),
        hand = mutableListOf(),
        discardPile = mutableListOf(),
        deck = deck.subList(fromIndex = 0, toIndex = 9)
    )
    val opponent = Opponent(
        arena = mutableListOf(),
        hand = mutableListOf(),
        discardPile = mutableListOf(),
        deck = deck.subList(fromIndex = 10, toIndex = 19)
    )

    val match = Match(player =  player, opponent = opponent, pile = deck)
    val startingPlayer = match.shouldPlayerStart()
}

class Game(
    val turns : Collection<Turn>,
    val match: Match,
    val playerWillStart: Boolean,
){
    fun startGame(){

        if(playerWillStart){

        }
    }
}

class Turn(
    val playerTurn: PlayerTurn,
    val opponentTurn: PlayerTurn,
    val turnNumber: Int = 0,
){
}

class PlayerTurn(
    val playerDecision: PlayerDecision,
    val turnStatus: TurnStatus,
){

}

class PlayerDecision(
    val actingPlayer: PlayerType,
    val playerAction: PlayerAction,
){

}

enum class TurnStatus(){
    FINISHED,
    STARTED,
    CANCELED,
}

enum class PlayerAction(){
    PLAY_CARD,
    USE_MINDBUG,
    ATTACK,
    LET_RESOLVE,
}

enum class PlayerType(){
    PLAYER,
    OPPONENT,
}