package entities.keywords

import entities.card.Card

class Tough : Keyword {

    override fun resolve(target: Card?, self: Card) {
        if (self.resistance) {
            self.resistance = false
            self.alive = true
        }
    }

    override fun isActive(target: Card?, self: Card) = self.resistance

    override fun getType(): KeywordType = KeywordType.TOUGH

    override fun getDescription(): String = " Cards with Tough have an extra life, when they are suposed to be destroyed, remove its resistence counter instead "
}