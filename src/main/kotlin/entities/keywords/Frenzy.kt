package entities.keywords

import entities.card.Card

class Frenzy : Keyword {

    private var active = false

    override fun resolve(target: Card?, self: Card) {
        if (active && self.alive) active = false
    }

    override fun getType(): KeywordType = KeywordType.FRENZY

    override fun isActive(target: Card?, self: Card)  = active

    override fun refresh() {
        active = true
    }

    override fun getDescription(): String = " Cards with Frenzy can attack twice if they survive the first attack "
}