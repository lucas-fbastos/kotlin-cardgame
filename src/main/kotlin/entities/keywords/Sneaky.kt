package entities.keywords

import entities.card.Card

class Sneaky : Keyword {
    override fun resolve(target: Card?, self: Card) {}

    override fun getType(): KeywordType = KeywordType.SNEAKY

    override fun getDescription(): String = " Cards with Sneaky can only be blocked by Sneaky cards "

    override fun isActive(target: Card?, self: Card) = true
}
