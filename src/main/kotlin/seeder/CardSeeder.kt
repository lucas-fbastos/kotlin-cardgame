package seeder

import constants.deadlyHamsterRef
import constants.phoneStealerOtterRef
import entities.Card
import entities.keywords.Frenzy
import entities.keywords.Poisonous
import entities.keywords.Sneaky

fun seed(): List<Card> {

    return listOf(
        createFrenzyCard(), createPhoneStealerOtter(), createFrenzyCard(), createFrenzyCard(),
        createPhoneStealerOtter(), createDeadlyHamster(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createRegularAnt(), createRegularAnt(), createFrenzyCard(),
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
    image = deadlyHamsterRef
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
    keywords = listOf(Sneaky()),
    image = phoneStealerOtterRef
)

private fun createFrenzyCard() : Card = Card(
    name = "Frenzy Guinea Pig",
    strength = 2,
    flavorText = "Cuy Cuy Cuy!!",
    keywords = listOf(Frenzy()),
)