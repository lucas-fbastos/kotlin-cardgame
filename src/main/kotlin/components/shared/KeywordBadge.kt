package components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import constants.CustomIcon
import entities.keywords.Keyword
import entities.keywords.KeywordType
import me.localx.icons.straight.filled.FlaskPoison
import me.localx.icons.straight.filled.MaskCarnival
import me.localx.icons.straight.filled.Thunderstorm

@Composable
fun KeywordBadge(
    keyword: Keyword,
    scale: Float = 1f,
) {
    val (icon, color) = getKeywordIconAndColor(keyword)

    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.9f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = (6 * scale).dp, vertical = (2 * scale).dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = keyword.getType().name,
            modifier = Modifier.size((16 * scale).dp),
            tint = Color.White
        )
    }
}


private fun getKeywordIconAndColor(keyword: Keyword): Pair<ImageVector, Color> {
    return when (keyword.getType()) {
        KeywordType.POISONOUS -> Pair(CustomIcon.FlaskPoison, Color(0xFF10B981))
        KeywordType.TOUGH -> Pair(Icons.Default.FavoriteBorder, Color(0xFF6B7280))
        KeywordType.SNEAKY -> Pair(CustomIcon.MaskCarnival, Color(0xFF8B5CF6))
        KeywordType.FRENZY -> Pair(CustomIcon.Thunderstorm, Color(0xFF10B981))
    }
}