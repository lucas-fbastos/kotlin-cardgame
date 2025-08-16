package entities.keywords

import entities.card.Card

class Poisonous : Keyword {

    override fun resolve(target: Card?, self: Card) {
        target?.die()
    }

    override fun getType(): KeywordType = KeywordType.POISONOUS

}