package entities.keywords

import entities.card.Card

class Poisonous : Keyword {

    override fun resolve(target: Card?, self: Card) {
        target?.die()
    }

    override fun getType(): KeywordType = KeywordType.POISONOUS

    override fun getDescription(): String = " Cards with poisonous will kill with any amount of damage "

}