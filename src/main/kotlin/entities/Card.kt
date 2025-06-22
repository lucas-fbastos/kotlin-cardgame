package entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import entities.abilities.AbilityCommand
import entities.keywords.Keyword
import entities.keywords.KeywordType
import java.util.UUID

data class Card(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val strength: Int,
    val flavorText: String? = null,
    val abilities: MutableList<AbilityCommand> = mutableListOf(),
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

        val hasPoison = keywords
            .any{ it.getType() == KeywordType.POISONOUS }

        val opponentHasPoison = opponent.keywords
            .any{ it.getType() == KeywordType.POISONOUS }

        if(hasPoison){
            keywords
                .firstOrNull{ it.getType() == KeywordType.POISONOUS }
                ?.resolve(
                    target = opponent,
                    self = this
                )
        }

        if(opponentHasPoison)
            opponent.keywords
                .firstOrNull{ it.getType() == KeywordType.POISONOUS }
                ?.resolve(
                    target = this,
                    self = opponent
                )
        when {
            strength > opponent.strength -> opponent.die()
            strength < opponent.strength -> die()
           else -> {
                opponent.die()
                die()
            }
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
