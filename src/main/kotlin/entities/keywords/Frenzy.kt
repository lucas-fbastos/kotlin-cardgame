package entities.keywords

import entities.Card

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
}