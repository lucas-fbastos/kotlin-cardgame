package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import entities.keywords.Keyword
import entities.keywords.KeywordType
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
    var image : ImageBitmap? = null,
    val keywords: List<Keyword> = emptyList(),
    val isAttacking: MutableState<Boolean> = mutableStateOf(false),
) {

    fun die() {
        keywords
            .firstOrNull { it.getType() == KeywordType.TOUGH }
            ?.resolve(target = null, self = this)
            ?: run {
                alive = false
            }
    }

    internal fun battle(opponent: Card) {
        println("BATTLE!!!")
        println(" ATTACKER: ${this.name}")
        println(" DEFENDER: ${opponent.name}")

        val hit = this.keywords
            .firstOrNull { it.getType() == KeywordType.POISONOUS }
            ?.resolve(target = opponent, self = this)
            ?.let { true }
            ?: false

        val opponentHit = opponent.keywords
            .firstOrNull { it.getType() == KeywordType.POISONOUS }
            ?.resolve(target = this, self = opponent)
            ?.let { true }
            ?: false

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
        println("-----------------")
        println("BATTLE RESULT:")
        println(" ATTACKER: ${this.name} - ${if(this.alive) "ALIVE" else "DEAD" }")
        println(" DEFENDER: ${opponent.name} - ${if(opponent.alive) "ALIVE" else "DEAD" }")
    }

    fun canDefend(attacker: Card): Boolean = attacker.keywords
        .firstOrNull { it.getType() == KeywordType.SNEAKY }
        ?.let {
            this.keywords.any { it.getType() == KeywordType.SNEAKY }
        } ?: true


    fun refreshKeywords(){
        keywords
            .firstOrNull{ it.getType() == KeywordType.FRENZY}
            ?.refresh()
    }

    fun triggerAttackAnimation() {
        isAttacking.value = true
    }

    fun resetAttackAnimation() {
        isAttacking.value = false
    }
}


interface Ability {
    fun resolve(target: Card?)
}
