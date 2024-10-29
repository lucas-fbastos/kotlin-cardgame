package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import constants.SNEAKY

class Opponent(
    deck: MutableState<MutableList<Card>>,
    hand: MutableState<MutableList<Card>>,
    discardPile: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
    arena: MutableState<MutableList<Card>> = mutableStateOf(mutableListOf()),
) : Player(
    deck = deck,
    hand = hand,
    discardPile = discardPile,
    arena = arena,
) {

    private fun getBigCards(): List<Card> =
        hand.value.filter { card ->
            card.strength > 5 || card.keywords?.any() == true
        }


    private fun enemyHasDefence(player: Player): Boolean {

        var hasDefence = player.arena
            .value
            .any { card ->
                card.strength >= (this.arena.value
                    .maxByOrNull { opponentCard -> opponentCard.strength }
                    ?.strength ?: 0)
            }
        val sneaky = checkSneaky()
        val playerSneaky = player.checkSneaky()
        if (sneaky && !playerSneaky) hasDefence = false

        return hasDefence
    }

    fun act(player: Player) {
        println("ACT!!!!")
        if (enemyHasDefence(player) && hand.value.size > 0)
            playCard(
                card = selectCardToPlay(player = player)
            )
        else
            attack()
    }

    private fun attack() {
        println("ATTACK!!!!")
    }

    private fun selectCardToPlay(player: Player): Card {
        val bigCards = getBigCards()
        return if (player.checkSneaky()) {
            this.hand.value.first {
                it.keywords?.containsKey(SNEAKY) == true &&
                        it.strength >= player.getStrongestSneaky().strength
            }
        } else {
            if (bigCards.isNotEmpty() && !player.hasMindBugs())
                bigCards.maxBy { it.strength }
            else
                this.hand.value.firstOrNull { it.strength <= 5 } ?: this.hand.value.first()
        }
    }

    private fun playCard(card: Card) {
        hand.value = hand.value.toMutableList().apply { remove(card) }

        // Assign a new list instance to arena.value with the added card
        arena.value = arena.value.toMutableList().apply { add(card) }
    }
}

fun Player.checkSneaky() =
    this.arena.value.firstOrNull { card -> card.keywords?.containsKey("SNEAKY") == true }?.let { true } ?: false

fun Player.hasMindBugs() = this.amountOfMindBugs > 0
fun Player.getStrongestSneaky() = this.arena
    .value.filter { card ->
        card.keywords
            ?.containsKey("SNEAKY") == true
    }
    .maxBy { it.strength }