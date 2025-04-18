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
        if(checkSneaky() && !player.checkSneaky())
            return false

        return player.arena
            .value
            .any { card ->
                card.strength >= (this.arena.value
                    .maxByOrNull { opponentCard -> opponentCard.strength }
                    ?.strength ?: 0)
            }
    }

    fun act(player: Player) {
        println("ACT!!!!")
        val canDefend = enemyHasDefence(player)
        if ( canDefend && hand.value.size > 0) {
            playCard(
                card = selectCardToPlay(player = player)
            )
            endTurn(opponent = this,player = player, wasPlayerTurn = false)
        } else
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
        val attacker = getDeadlyCard() ?: getSmallCard()
        if (canDefend || (player.arena.value.isNotEmpty() && !checkSneaky() ) )
            player.setAttackedBy(attacker = attacker)
        else
            player.takeHit()
    }

    private fun selectCardToPlay(player: Player): Card {
        return if (player.checkSneaky()) {
            hand.value.first { card ->
                card.keywords.any { it.getType() == KeywordType.SNEAKY } &&
                        card.strength >= player.getStrongestSneaky().strength
            }
        } else {
            getDeadlyCard()
                ?.takeIf { player.hasMindBugs() }
                ?: getSmallCard()
        }
    }

    private fun getSmallCard(): Card =
        hand.value
            .firstOrNull { it.strength <= 5 && it.keywords.isEmpty() }
            ?: hand.value.maxBy { it.strength }


}

internal fun Player.checkSneaky() =
    this.arena.value
        .any { card -> card.keywords
            .any { it.getType() == KeywordType.SNEAKY }
        }

internal fun Player.hasMindBugs() = this.amountOfMindBugs > 0

internal fun Player.getStrongestSneaky() = this.arena
    .value.filter { card ->
        card.keywords
            .any { it.getType() == KeywordType.SNEAKY }
    }
    .maxBy { it.strength }
