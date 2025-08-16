package entities.card

import entities.keywords.KeywordType

internal fun Card.isPoisonous(): Boolean =
    this.keywords.any { it.getType() == KeywordType.POISONOUS }

internal fun Card.isSneaky(): Boolean =
    this.keywords.any { it.getType() == KeywordType.SNEAKY }

internal fun Card.isTough(): Boolean =
    this.keywords.any { it.getType() == KeywordType.TOUGH }