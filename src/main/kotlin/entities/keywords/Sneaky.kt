package entities.keywords

import entities.Card

class Sneaky : Keyword {
    override fun resolve(target: Card?, self: Card) {}

    override fun getType(): KeywordType = KeywordType.SNEAKY

    override fun isActive(target: Card?, self: Card) = true
}
