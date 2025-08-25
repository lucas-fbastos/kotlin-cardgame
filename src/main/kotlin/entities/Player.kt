package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entities.card.Card
import constants.TOTAL_HP
import entities.abilities.AbilityTrigger
import entities.abilities.StackAbility
import entities.keywords.KeywordType
import entities.turn.actions.AttackAction
import entities.turn.actions.PlayAction
import entities.turn.actions.TurnAction
import entities.turn.stages.PlayStage
import entities.turn.stages.StageContext
import entities.turn.stages.TurnStage
import helper.BoardHelper
import java.util.Stack

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: MutableState<Int> = mutableStateOf(value = TOTAL_HP),
    val hand: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val discardPile: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val deck: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val arena: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val attackedBy: MutableState<Card?> = mutableStateOf(value = null),
    val turnStage : MutableState<TurnStage?> = mutableStateOf(null),
    val turnAction: MutableState<TurnAction> = mutableStateOf(PlayAction()),
    val abilitiesToResolve: MutableState<Stack<StackAbility>?> = mutableStateOf(value = Stack()),
    var selectedAbility: MutableState<StackAbility?> = mutableStateOf(value = null),
    val selectedCard: MutableState<Card?> = mutableStateOf(value = null ),
) {


    internal fun isDefeated() : Boolean = lifePoints.value <= 0 || ( hand.value.size == 0 && arena.value.size == 0 )

    internal open fun setCards(
        hand: MutableList<Card>,
        deck: MutableList<Card>,
    ){
        this.deck.value = deck
        this.hand.value = hand
    }

    internal fun reset(){
        lifePoints.value = TOTAL_HP
        hand.value = mutableListOf()
        discardPile.value = mutableListOf()
        discardPile.value = mutableListOf()
        arena.value = mutableListOf()
        attackedBy.value = null
        amountOfMindBugs = 2
    }

    internal fun buyCard() {
        hand.value.add(deck.value.first())
        deck.value.removeFirst()
    }

    fun playCard(
        card: Card,
        opponent: Player,
        target: Card? = null
    ) {
        hand.value = hand.value.toMutableList().apply { remove(card) }
        arena.value = arena.value.toMutableList().apply { add(card) }
        selectedCard.value = card

        PlayStage(trigger = AbilityTrigger.ON_PLAY)
            .moveStage(
                stageContext = StageContext(
                    caster = this,
                    opponent = opponent,
                    selectedCard = card
                )
            )
    }

    internal fun resolveTargetedAbility(
        target: Card,
        opponent: Opponent
    ){
        selectedAbility.value?.target = selectedAbility.value?.target?.copy(cardTarget = target)
        turnStage.value = turnStage.value?.moveStage(
            stageContext = StageContext(
                caster = this,
                opponent = opponent,
                selectedCard = selectedCard.value!!
            )
        )

        BoardHelper.removeCardsFromBoard(
            player = this,
            opponent = opponent
        )

        turnAction.value.resume(
            caster = this,
            opponent = opponent
        )
    }

    internal fun startCombat(opponent: Opponent, attacker: Card){
        turnAction.value = AttackAction()

        selectedCard.value = attacker

        turnStage.value = PlayStage(trigger = AbilityTrigger.ON_ATTACK)
            .moveStage(
                stageContext = StageContext(
                    caster = this,
                    opponent = opponent,
                    selectedCard = attacker
                )
            )

        if(selectedAbility.value != null)
            return

        // in case any card dies during the resolve
        BoardHelper.removeCardsFromBoard(
            opponent = opponent, player = this
        )
    }

    internal fun attack(opponent: Opponent, attacker: Card) {

        opponent.setAttackedBy(attacker)

        opponent.defend().also {
            BoardHelper.removeCardsFromBoard(
                opponent = opponent, player = this
            )

            attacker.keywords
                .firstOrNull {
                    it.getType() == KeywordType.FRENZY
                }?.let {
                    if(it.isActive(self = attacker)){
                        it.resolve(self = attacker)
                        opponent.defend()
                    }
                }

            endTurn(opponent = opponent, player = this, wasPlayerTurn = true)

            turnAction.value = PlayAction()
        }
    }

    internal fun defend(
        card: Card, opponent: Opponent
    ) {
        assert(attackedBy.value != null)
        val attacker = attackedBy.value!!

        card.battle(opponent = attacker)

        BoardHelper.removeCardsFromBoard(
            opponent = opponent,
            player = this
        )

        val frenzy = attacker.keywords.firstOrNull { it.getType() == KeywordType.FRENZY }

        if (!attacker.alive || frenzy == null || !frenzy.isActive(self = attacker)) {
            attackedBy.value = null
            endTurn(
                opponent = opponent,
                player = this,
                wasPlayerTurn = false
            )
            return
        }

        frenzy.resolve(self = attacker)
    }

    fun setAttackedBy(attacker: Card) {
        attackedBy.value = attacker
    }

    fun takeHit() {
        lifePoints.value = lifePoints.value.dec()
    }

    fun takeDirectHit(opponent: Opponent) =
        attackedBy.value?.let { attacker ->
            takeHit()
                .also {
                    if(attacker.hasActiveFrenzy()){
                        attacker.consumeFrenzy()
                        takeHit()
                    }
                    attackedBy.value = null
                    endTurn(opponent = opponent, player = this, wasPlayerTurn = false)
                }
        }


    fun refreshCards() {
        arena
            .value
            .forEach {
                it.refreshKeywords()
            }
    }

    fun endTurn(
        opponent: Opponent,
        player: Player,
        wasPlayerTurn: Boolean,
    ) {

        println("---------")
        println("TURN ended - was player turn? $wasPlayerTurn")

        if(opponent.isDefeated()){
            BoardHelper.finishGame(playerWon = true)
            return
        }

        if (player.isDefeated()){
            BoardHelper.finishGame(playerWon = false)
            return
        }

        player.refreshCards()
        opponent.refreshCards()

        if (wasPlayerTurn) BoardHelper.blockPlayer().also {
            if (player.deck.value.size > 0 && player.hand.value.size < 5) player.buyCard()
            opponent.act(player = player)
            return
        }

        BoardHelper.releasePlayer()
        BoardHelper.increaseTurn().also {
            if (opponent.deck.value.size > 0 && opponent.hand.value.size < 5) opponent.buyCard()
        }
    }

    internal fun Card.hasActiveFrenzy(): Boolean =
        this.keywords
            .firstOrNull{ it.getType() == KeywordType.FRENZY }
            ?.isActive(self = this)
            ?: false

    private fun Card.consumeFrenzy() =
        this.keywords
            .firstOrNull{ it.getType() == KeywordType.FRENZY }
            ?.resolve(self = this)

}


