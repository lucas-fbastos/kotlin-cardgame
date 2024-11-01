package entities

import constants.POISONOUS
import constants.TOUGH

data class Card(
    val name: String,
    val strength: Int,
    val flavorText: String? = null,
    val keywords: Map<String, Keyword>?,
    val ability: Ability? = null,
    var alive: Boolean = true,
    var resistance: Boolean = false
) {

    fun die() {
        keywords?.get(TOUGH)
            ?.resolve(target = null, self = this)
            ?: run {
                alive = false
            }
    }

    fun battle(opponent: Card) {
        var hit = true
        var opponentHit = true
        this.keywords?.get(POISONOUS)
            ?.resolve(
                target = opponent,
                self = this,
            )
            ?: run { hit = false }

        opponent.keywords?.get(POISONOUS)
            ?.resolve(
                target = this,
                self = opponent,
            )
            ?: run { opponentHit = false }

        if (hit && opponentHit)
            return
        else {
            if (opponent.strength > this.strength && !opponentHit) {
                this.die()
            } else if (opponent.strength == this.strength) {
                if (!opponentHit)
                    this.die()
                if (!hit)
                    opponent.die()
            } else if (opponent.strength < this.strength && !hit) {
                opponent.die()
            }
        }
    }

}

interface Keyword {
    fun resolve(target: Card?, self: Card)
}

interface Ability {
    fun resolve(target: Card?)
}

class Poisonous : Keyword {
    override fun resolve(target: Card?, self: Card) {
        target?.die()
    }
}

class Tough : Keyword {
    override fun resolve(target: Card?, self: Card) {
        if (self.resistance) {
            self.resistance = false
            self.alive = true
        }
    }
}
