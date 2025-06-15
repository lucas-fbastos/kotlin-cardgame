package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import constants.TOTAL_HP
import entities.keywords.KeywordType
import helper.BoardHelper

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: MutableState<Int> = mutableStateOf(value = TOTAL_HP),
    val hand: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val discardPile: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val deck: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val arena: MutableState<MutableList<Card>> = mutableStateOf(value = mutableListOf()),
    val attackedBy: MutableState<Card?> = mutableStateOf(value = null)
) {

    internal fun buyCard() {
        hand.value.add(deck.value.first())
        deck.value.removeFirst()
    }

    fun playCard(card: Card) {
        hand.value = hand.value.toMutableList().apply { remove(card) }
        arena.value = arena.value.toMutableList().apply { add(card) }
    }

    fun attack(opponent: Opponent, attacker: Card) {
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

    internal fun Card.consumeFrenzy() =
        this.keywords
            .firstOrNull{ it.getType() == KeywordType.FRENZY }
            ?.resolve(self = this)

}


