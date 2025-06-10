package constants

import androidx.compose.ui.graphics.Color

internal val COLOR_BACKGROUND = Color(0xFF1A1A1A)
internal val COLOR_SURFACE = Color(0xFF2D2D2D)
internal val COLOR_PRIMARY = Color(0xFF4A90E2)
internal val COLOR_SECONDARY = Color(0xFF6C757D)
internal val COLOR_RED = Color(0xFFE74C3C)
internal val COLOR_TEXT_PRIMARY = Color(0xFFE8E8E8)
internal val COLOR_TEXT_SECONDARY = Color(0xFFADB5BD)
internal val COLOR_BORDER = Color(0xFF404040)
internal val COLOR_GREEN = Color(0xFF059669)
internal val COLOR_DARK_PURPLE = Color(0xFF1F1F35)
internal val COLOR_PURPLE = Color(0xFF8B5CF6)
internal val COLOR_ORANGE = Color(0xFFE67E22)
internal val COLOR_DARK_ORANGE = Color(0xFF2E1907)

//GRADIENT
internal val CARD_BACKGROUND_GRADIENT = listOf(
    Color(0xFF2A2A3E),
    Color(0xFF1A1A2E)
)

internal val CARD_BORDER_GRADIENT = listOf(
    COLOR_PURPLE,
    Color(0xFFE879F9)
)

internal val CARD_STRENGTH_GRADIENT = listOf(
    COLOR_PURPLE,
    Color(0xFF6D28D9)
)

internal val OPPONENT_BACKGROUND_GRADIENT = listOf(
    Color(0xFF3E2A2A),
    Color(0xFF2E1A1A),
    Color(0xFF1F0F0F)
)

internal val OPPONENT_BORDER_GRADIENT = listOf(
    COLOR_ORANGE,
    Color(0xFFE67f44),
)

internal const val DEFAULT_ANIMATION_DURATION = 700

internal typealias CustomIcon = me.localx.icons.straight.Icons.Filled