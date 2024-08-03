package entities

open class Player(
    var amountOfMindBugs: Int = 2,
    var lifePoints: Int = 3,
    var hand: MutableList<Card>,
    var discardPile: MutableList<Card>,
    var deck : List<Card>,
    var arena: MutableList<Card>,
)