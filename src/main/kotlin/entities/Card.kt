package entities

import java.util.UUID

data class Card(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val strength: Int,
    val flavorText: String? = null,
    val ability: Ability? = null,
    var alive: Boolean = true,
    var playerOwned: Boolean = true,
    var resistance: Boolean = false,
    val keywords: List<Keyword> = emptyList()
) {

    fun die() {
        keywords
            .firstOrNull(){ it.getType() == KeywordType.TOUGH }
            ?.resolve(target = null, self = this)
            ?: run {
                alive = false
            }
    }

    fun battle(opponent: Card) {
        var hit = true
        var opponentHit = true

        this.keywords
            .firstOrNull{ it.getType() == KeywordType.POISONOUS}
            ?.resolve(target = opponent, self = this)
            ?: run { hit = false }

        opponent.keywords
            .firstOrNull{ it.getType() == KeywordType.POISONOUS}
            ?.resolve(target = this, self = opponent)
            ?: run { opponentHit = false }


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

    fun canDefend(attacker: Card): Boolean
        = attacker.keywords
            .firstOrNull { it.getType() == KeywordType.SNEAKY }
            ?.let {
                this.keywords.any{ it.getType() == KeywordType.SNEAKY}
            } ?: true

}

interface Keyword {
    fun resolve(target: Card?, self: Card)

    fun getType() : KeywordType
}

enum class KeywordType{
    SNEAKY,
    POISONOUS,
    TOUGH
}

interface Ability {
    fun resolve(target: Card?)
}

class Poisonous : Keyword {
    override fun resolve(target: Card?, self: Card) {
        target?.die()
    }

    override fun getType(): KeywordType = KeywordType.POISONOUS
}

class Tough : Keyword {
    override fun resolve(target: Card?, self: Card) {
        if (self.resistance) {
            self.resistance = false
            self.alive = true
        }
    }

    override fun getType(): KeywordType = KeywordType.TOUGH
}

class Sneaky : Keyword{
    override fun resolve(target: Card?, self: Card) { }

    override fun getType(): KeywordType = KeywordType.SNEAKY

}
