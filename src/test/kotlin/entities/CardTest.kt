package entities

import entities.keywords.Poisonous
import entities.keywords.Tough
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun testBattleWithPoisonousCard(){

        val card1 = Card(
            name = "Poisonous Card",
            strength = 5,
            keywords = listOf(Poisonous())
        )

        val card2 = Card(
            name = "Tough Card",
            strength = 5,
            keywords = listOf(Poisonous(),),
            resistance = true
        )

        card1.battle(card2)

        assertFalse(card1.alive, "Card1 should be dead after the battle")
        assertTrue(card2.alive, "Card2 should be alive after the battle")
        assertFalse(card2.resistance, "Card2's resistance should be used up")
    }

    @Test
    fun testBattleWithStrongCard(){


        val card3 = Card(
            name = "Strong Card",
            strength = 10,
            keywords = listOf(Poisonous())
        )

        val card4 = Card(
            name = "Weak Card",
            strength = 1,
            keywords = listOf(Tough()),
            resistance = true
        )

        card3.battle(card4)

        assertTrue(card3.alive, "Card3 should be alive after the battle")
        assertFalse(card4.resistance, "Card4 should be alive after the battle")
    }
}