package entities

import constants.SNEAKY

class Opponent(
    deck: List<Card>,
    hand: MutableList<Card>,
    discardPile: MutableList<Card>,
    arena: MutableList<Card>,
) : Player(
    deck = deck,
    hand = hand,
    discardPile = discardPile,
    arena = arena,
) {

    private fun getBigCards(): List<Card> =
        hand.filter { card ->
            card.strength > 5 || card.keywords?.size!! > 1
        }


    private fun enemyHasDefence(player: Player): Boolean {

        var hasDefence = player.arena
            .any { card ->
                card.strength >= arena
                    .maxBy { opponentCard -> opponentCard.strength }
                    .strength
            }
        val sneaky = checkSneaky()
        val playerSneaky = player.checkSneaky()
        if (sneaky && !playerSneaky) hasDefence = false

        return hasDefence
    }

    fun act(player: Player) {
        if (enemyHasDefence(player) && hand.size > 0)
            playCard(
                card = selectCardToPlay(
                    player = player
                )
            )
        else
            attack()
    }

    private fun attack() {

    }

    private fun selectCardToPlay(player: Player): Card {
        val bigCards = getBigCards()
        return if (player.checkSneaky()) {
            hand.first {
                it.keywords?.containsKey(SNEAKY) == true &&
                        it.strength >= player.getStrongestSneaky().strength
            }
        } else {
            if (bigCards.isNotEmpty() && !player.hasMindBugs())
                bigCards.maxBy { it.strength }
            else
                hand.first { it.strength <= 5 }
        }
    }

    private fun playCard(card: Card) {
        hand.remove(card)
        arena.add(card)
    }
}

fun Player.checkSneaky() = this.arena.firstOrNull { card -> card.keywords?.containsKey("SNEAKY") == true }?.let { true } ?: false
fun Player.hasMindBugs() = this.amountOfMindBugs > 0
fun Player.getStrongestSneaky() = this.arena
    .filter { card ->
        card.keywords
            ?.containsKey("SNEAKY") == true
    }
    .maxBy { it.strength }