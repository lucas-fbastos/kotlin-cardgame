package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun testBattle() {
        val poisonous = Poisonous()
        val tough = Tough()

        // Card with Poisonous keyword
        val card1 = Card(
            name = "Poisonous Card",
            strength = 5,
            keywords = listOf(poisonous)
        )

        // Card with Tough keyword
        val card2 = Card(
            name = "Tough Card",
            strength = 5,
            keywords = listOf(tough),
            resistance = true
        )

        // Battle between card1 and card2
        card1.battle(card2)

        // Assertions to verify the expected behavior
        assertFalse(card1.alive, "Card1 should be dead after the battle")
        assertTrue(card2.alive, "Card2 should be alive after the battle")
        assertFalse(card2.resistance, "Card2's resistance should be used up")

        // Creating another set of cards with different strengths
        val card3 = Card(
            name = "Strong Card",
            strength = 10,
            keywords = listOf(poisonous)
        )

        val card4 = Card(
            name = "Weak Card",
            strength = 1,
            keywords = listOf(tough),
            resistance = true
        )

        // Battle between card3 and card4
        card3.battle(card4)

        // Assertions to verify the expected behavior
        assertTrue(card3.alive, "Card3 should be alive after the battle")
        assertFalse(card4.resistance, "Card4 should be alive after the battle")
    }
}