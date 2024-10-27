package entities

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: Int = 3,
    var hand: MutableList<Card>,
    var discardPile: MutableList<Card> = mutableListOf(),
    var deck : List<Card> = mutableListOf(),
    var arena: MutableList<Card> = mutableListOf(),
)