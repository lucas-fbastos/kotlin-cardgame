package seeder

import constants.alchemistHedgehogRef
import constants.deadlyHamsterRef
import constants.phoneStealerOtterRef
import entities.Card
import entities.abilities.DestroyOnAttack
import entities.abilities.HealOnPlay
import entities.abilities.HitDirectlyOnAttack
import entities.keywords.Frenzy
import entities.keywords.Poisonous
import entities.keywords.Sneaky

fun seed(): List<Card> {

    return listOf(
        createFrenzyCard(), createPhoneStealerOtter(), createFrenzyCard(), createFrenzyCard(),
        createPhoneStealerOtter(), createDeadlyHamster(), createDeadlyHamster(), createAlchemistHedgehog(),
        createAlchemistHedgehog(), createRegularAnt(), createRegularAnt(), createFrenzyCard(),
        createRegularAnt(), createRegularAnt(), createDeadlyHamster(), createAlchemistHedgehog(),
        createAlchemistHedgehog(), createDeadlyHamster(),  createPhoneStealerOtter(), createPhoneStealerOtter(),
    ).shuffled()
}

private fun createRegularAnt(): Card = Card(
    name = "Regular Ant",
    strength = 1,
    flavorText = "just an Ant",
    abilities = mutableListOf(
        HealOnPlay(amountToHeal = 1),
        DestroyOnAttack(),
    )
)

private fun createDeadlyHamster() : Card = Card(
    name = "Deadly Hamster",
    strength = 5,
    flavorText = "DANGEROUS CREATURE",
    image = deadlyHamsterRef,
    abilities = mutableListOf(
        HitDirectlyOnAttack(amountToHit = 1),
        DestroyOnAttack(),
    )
)

private fun createAlchemistHedgehog() : Card = Card(
    name = "Alchemist Hedgehog",
    strength = 4,
    flavorText = "He forgot more about magic than you will ever know.",
    keywords = listOf(Poisonous(),),
    image = alchemistHedgehogRef,
    abilities = mutableListOf(
        DestroyOnAttack(),
    )
)

private fun createPhoneStealerOtter() : Card = Card(
    name = "Phone Stealer Otter",
    strength = 3,
    flavorText = "Stay alert, check your purses - NOW!",
    keywords = listOf(Sneaky()),
    image = phoneStealerOtterRef,
    abilities = mutableListOf(
        DestroyOnAttack(),
    )
)

private fun createFrenzyCard() : Card = Card(
    name = "Frenzy Guinea Pig",
    strength = 2,
    flavorText = "Cuy Cuy Cuy!!",
    keywords = listOf(Frenzy()),
    abilities = mutableListOf(
        DestroyOnAttack(),
    )
)