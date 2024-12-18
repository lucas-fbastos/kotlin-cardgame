package seeder

import constants.POISONOUS
import constants.SNEAKY
import entities.Card
import entities.Poisonous
import entities.Sneaky

fun seed(): List<Card> {

    return listOf(
        createRegularAnt(), createRegularAnt(), createRegularAnt(), createRegularAnt(),
        createRegularAnt(), createDeadlyHamster(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createRegularAnt(), createRegularAnt(), createRegularAnt(),
        createRegularAnt(), createRegularAnt(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createDeadlyHamster(),  createPhoneStealerOtter(), createPhoneStealerOtter(),
    )
}

private fun createRegularAnt(): Card = Card(
    name = "Regular Ant",
    strength = 1,
    flavorText = "just an Ant",
    keywords = mapOf()
)

private fun createDeadlyHamster() : Card = Card(
    name = "Deadly Hamster",
    strength = 5,
    flavorText = "DANGEROUS CREATURE",
    keywords = mapOf()
)

private fun createAssassinHedgehog() : Card = Card(
    name = "Assassin Hedgehog",
    strength = 4,
    flavorText = "never lost a contract...",
    keywords = mapOf( POISONOUS to Poisonous())
)

private fun createPhoneStealerOtter() : Card = Card(
    name = "Phone Stealer Otter",
    strength = 3,
    flavorText = "Stay alert, check your purses - NOW!",
    keywords = mapOf( SNEAKY to Sneaky() )
)