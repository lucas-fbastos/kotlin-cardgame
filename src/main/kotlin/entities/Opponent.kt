package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

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

    private fun getDeadlyCard(): Card? =
        hand.value.firstOrNull { card ->
            card.strength > 5 || card.keywords.any()
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
        if (enemyHasDefence(player) && hand.value.size > 0){

            playCard(
                card = selectCardToPlay(player = player)
            )
        } else
            attack(player = player)
    }

    private fun attack(player: Player) {
        println("ATTACK!!!!")
        val attacker = getDeadlyCard() ?: getSmallCard()
        player.setAttackedBy(attacker = attacker)
           /* .also {
                val target: Card = chooseTarget(player.arena.value)

                attacker.battle(target)
                removeCardsFromBoard(
                    opponent = this,
                    player = player
                )  <-- this will be done after the defender is selected
            }  */
    }

    private fun chooseTarget(playerArena: List<Card>): Card =
        playerArena.minBy { it.strength }

    private fun selectCardToPlay(player: Player): Card {
        return if (player.checkSneaky()) {
            hand.value.first {
                it.keywords.any { it.getType() == KeywordType.SNEAKY } &&
                        it.strength >= player.getStrongestSneaky().strength
            }
        } else {
            getDeadlyCard()
                ?.takeIf { player.hasMindBugs() }
                ?: getSmallCard()
        }
    }

    private fun getSmallCard() : Card =
        hand.value
            .firstOrNull { it.strength <= 5 && it.keywords.isEmpty() }
            ?: hand.value.maxBy { it.strength }


}

fun Player.checkSneaky() =
    this.arena.value.any { card -> card.keywords.any { it.getType() == KeywordType.SNEAKY  } }

fun Player.hasMindBugs() = this.amountOfMindBugs > 0

fun Player.getStrongestSneaky() = this.arena
    .value.filter { card ->
        card.keywords
            .any { it.getType() == KeywordType.SNEAKY  }
    }
    .maxBy { it.strength }
