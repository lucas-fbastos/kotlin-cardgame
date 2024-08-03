package entities

data class Match(
    val player: Player,
    val opponent: Opponent,
    var turns: Int = 0,
    var pile: List<Card>
) {

    fun shouldPlayerStart() : Boolean = Math.random().toInt().mod(2) == 1
}