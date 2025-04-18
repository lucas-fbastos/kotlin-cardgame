package seeder

import entities.Card
import entities.Poisonous
import entities.Sneaky

fun seed(): List<Card> {

    return listOf(
        createRegularAnt(), createPhoneStealerOtter(), createRegularAnt(), createRegularAnt(),
        createPhoneStealerOtter(), createDeadlyHamster(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createRegularAnt(), createRegularAnt(), createRegularAnt(),
        createRegularAnt(), createRegularAnt(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createDeadlyHamster(),  createPhoneStealerOtter(), createPhoneStealerOtter(),
    ).shuffled()
}

private fun createRegularAnt(): Card = Card(
    name = "Regular Ant",
    strength = 1,
    flavorText = "just an Ant",
)

private fun createDeadlyHamster() : Card = Card(
    name = "Deadly Hamster",
    strength = 5,
    flavorText = "DANGEROUS CREATURE",
)

private fun createAssassinHedgehog() : Card = Card(
    name = "Assassin Hedgehog",
    strength = 4,
    flavorText = "never lost a contract...",
    keywords = listOf(Poisonous(),)
)

private fun createPhoneStealerOtter() : Card = Card(
    name = "Phone Stealer Otter",
    strength = 3,
    flavorText = "Stay alert, check your purses - NOW!",
    keywords = listOf(Sneaky())
)