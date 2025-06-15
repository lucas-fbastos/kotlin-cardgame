package entities.keywords

import entities.Card

interface Keyword {

    fun resolve(target: Card? = null, self: Card)

    fun isActive(target: Card? = null, self: Card) : Boolean = true

    fun getType(): KeywordType

    fun refresh() = run { }
}