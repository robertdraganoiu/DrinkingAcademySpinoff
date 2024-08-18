package com.hack.drinkingacademy.common.mappers

import com.hack.drinkingacademy.database.Game_card
import com.hack.drinkingacademy.game.model.ChallengeType
import com.hack.drinkingacademy.game.model.GameCard

fun Game_card.toGameCard() = GameCard(
    type = this.challenge_type.toChallengeType(),
    description = this.content
)

fun String.toChallengeType(): ChallengeType = when(this.lowercase()) {
    "truth" -> ChallengeType.TRUTH
    "dare" -> ChallengeType.DARE
    "poll" -> ChallengeType.POLL
    "master" -> ChallengeType.MASTER
    "trivia" -> ChallengeType.TRIVIA
    "betrayal" -> ChallengeType.BETRAYAL
    else -> ChallengeType.OTHER
}