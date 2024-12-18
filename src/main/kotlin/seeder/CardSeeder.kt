package seeder

import constants.POISONOUS
import entities.Card
import entities.Poisonous

fun seed(): List<Card> {

    return listOf(
        createRegularAnt(), createRegularAnt(), createRegularAnt(), createRegularAnt(),
        createRegularAnt(), createDeadlyHamster(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createRegularAnt(), createRegularAnt(), createRegularAnt(),
        createRegularAnt(), createRegularAnt(), createDeadlyHamster(), createAssassinHedgehog(),
        createAssassinHedgehog(), createDeadlyHamster(),  createDeadlyHamster(), createDeadlyHamster(),
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