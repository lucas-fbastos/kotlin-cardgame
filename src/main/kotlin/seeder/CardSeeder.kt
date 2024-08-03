package seeder

import entities.Card
import entities.Poisonous

fun seed(): List<Card> {
    val axolotlHealer = Card(
        name = "Axolotl Healer",
        strength = 4,
        keywords = mapOf("POISONOUS" to Poisonous())
    )
    val testCard = Card(
        name = "test",
        strength = 1,
        keywords = null,
    )

    return listOf(
        axolotlHealer,axolotlHealer,testCard,testCard,testCard,
        axolotlHealer,testCard,axolotlHealer,testCard,testCard,
        axolotlHealer,axolotlHealer,testCard,testCard,testCard,
        axolotlHealer,testCard,axolotlHealer,testCard,testCard,
    )
}