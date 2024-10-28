package entities

import constants.POISONOUS
import constants.TOUGH

class Card(
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (name != other.name) return false
        if (strength != other.strength) return false
        if (flavorText != other.flavorText) return false
        if (keywords != other.keywords) return false
        if (ability != other.ability) return false
        if (alive != other.alive) return false
        if (resistance != other.resistance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + strength
        result = 31 * result + (flavorText?.hashCode() ?: 0)
        result = 31 * result + (keywords?.hashCode() ?: 0)
        result = 31 * result + (ability?.hashCode() ?: 0)
        result = 31 * result + alive.hashCode()
        result = 31 * result + resistance.hashCode()
        return result
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
