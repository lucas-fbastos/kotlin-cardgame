package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entities.keywords.KeywordType
import helper.BoardHelper

class Opponent(
    deck: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
    hand: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
    discardPile: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
    arena: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
) : Player(
    deck = deck,
    hand = hand,
    discardPile = discardPile,
    arena = arena,
) {

    override fun setCards(
        hand: MutableList<Card>,
        deck: MutableList<Card>,
    ){
        val opponentHand = hand.toOpponentCard()
        val opponentDeck = deck.toOpponentCard()

        super.setCards(
            hand = opponentHand,
            deck = opponentDeck,
        )
    }

    private val canDefendAgainstPoison: (defender: Card, attacker: Card) -> Boolean =
        { defender: Card, attacker: Card ->
            defender.isTough() && defender.strength >= attacker.strength
        }

    private fun MutableList<Card>.getDeadlyCard(): Card? =
        firstOrNull { card ->
            card.strength > 5 || card.keywords.any()
        }

    private fun playerDefenseWillSurvive(player: Player): Boolean {
        return player.arena
            .value
            .any { card ->
                card.strength >= (this.arena.value
                    .maxByOrNull { opponentCard -> opponentCard.strength }
                    ?.strength ?: 0)
            }
    }

    private fun canPlayerDefend(player: Player): Boolean =
        (player.arena.value.isNotEmpty() || checkSneaky() && player.checkSneaky())


    fun defend() {
        println("DEFEND!!!!!")
        assert(this.attackedBy.value != null)
        val attacker: Card = this.attackedBy.value!!
        if(attacker.isSneaky() && !arena.value.any { it.isSneaky() } || arena.value.isEmpty()) {
            takeHit()
            return
        }

        val defender = pickDefender(attacker)
        println(" DEFENDER: ${defender.name}")
        attacker
            .battle(defender)
            .also {
                if(!attacker.hasActiveFrenzy())
                    this.attackedBy.value = null
            }

    }

    private fun pickDefender(attacker: Card) = when {
        attacker.isPoisonous() && this.arena.value.any { canDefendAgainstPoison(attacker, it) } ->
            arena.value.firstOrNull { it.resistance && it.strength >= attacker.strength }

        attacker.resistance && this.arena.value.any { it.strength > attacker.strength } ->
            arena.value.firstOrNull { it.strength > attacker.strength }

        attacker.isSneaky() ->
            arena.value.firstOrNull { it.isSneaky() }

        attacker.strength > arena.value.maxBy { it.strength }.strength && arena.value.any { it.isPoisonous() } ->
            arena.value.firstOrNull { it.isPoisonous() }

        else ->
            arena.value.getSmallCard()

    } ?: arena.value.getSmallCard()


    fun act(player: Player) {

        if(isDefeated()){
            endTurn(
                opponent = this,
                player = player,
                wasPlayerTurn = false
            )
            return
        }

        println("ACT!!!!")

        val canDefend =  canPlayerDefend(player)
        val defenderWillSurvive = canDefend && playerDefenseWillSurvive(player)
        if (arena.value.isEmpty() || (defenderWillSurvive && hand.value.size > 0)) {
            println(" PLAY CARD!!!!")

            val cardToPlay = selectCardToPlay(player = player)

            println(" PLAY THIS CARD: ${cardToPlay.name}")
            playCard(card = cardToPlay, opponent = player)

            endTurn(
                opponent = this,
                player = player,
                wasPlayerTurn = false
            )
            return
        }

        attack(
            player = player,
            canDefend = canDefend
        )
    }


    private fun attack(
        player: Player,
        canDefend: Boolean,
    ) {
        println("ATTACK!!!!")
        val attacker = arena.value.getDeadlyCard() ?: arena.value.getSmallCard()
        println("ATTACKER : ${attacker.name}")

        attacker.triggerAttackAnimation()

        handleAttackTrigger(
            card = attacker,
            opponent = player,
            target = null // create mechanism to select the target something like the pick card to attack
        )

        if (canDefend || (player.arena.value.isNotEmpty() && !checkSneaky())) {
            player.setAttackedBy(attacker = attacker)
            BoardHelper.blockPlayer()
            return
        }
        println("ATTACKER : ${attacker.name} HIT ONCE -1")
        player.takeHit()

        if(attacker.hasActiveFrenzy()){
            println("ATTACKER : ${attacker.name} HIT TWICE -1")
            player.takeHit()
        }

        endTurn(
            opponent = this,
            player = player,
            wasPlayerTurn = false
        )


    }

    private fun selectCardToPlay(player: Player): Card {
        val sneakyCard = if (player.checkSneaky()) {
            hand.value.firstOrNull { card ->
                card.keywords.any { it.getType() == KeywordType.SNEAKY } &&
                        card.strength >= player.getStrongestSneaky().strength
            }
        } else null

        return sneakyCard
            ?: hand.value.getDeadlyCard()?.takeIf { player.hasMindBugs() }
            ?: hand.value.getSmallCard()
    }

    private fun MutableList<Card>.getSmallCard(): Card =
        firstOrNull { it.strength <= 5 && it.keywords.isEmpty() }
            ?: maxBy { it.strength }


}

internal fun Player.checkSneaky() =
    arena.value
        .any { card ->
            card.keywords
                .any { it.getType() == KeywordType.SNEAKY }
        }

internal fun Card.isPoisonous(): Boolean =
    this.keywords.any { it.getType() == KeywordType.POISONOUS }

internal fun Card.isSneaky(): Boolean =
    this.keywords.any { it.getType() == KeywordType.SNEAKY }

internal fun Card.isTough(): Boolean =
    this.keywords.any { it.getType() == KeywordType.TOUGH }

internal fun Player.hasMindBugs() = this.amountOfMindBugs > 0

internal fun Player.getStrongestSneaky() = this.arena
    .value.filter { card ->
        card.keywords
            .any { it.getType() == KeywordType.SNEAKY }
    }
    .maxBy { it.strength }

private fun List<Card>.toOpponentCard() =
    this.map {
        it.copy(playerOwned = false)
    }.toMutableList()
