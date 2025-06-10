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
import constants.CustomIcons
import entities.Keyword
import entities.KeywordType
import me.localx.icons.straight.bold.FlaskPoison
import me.localx.icons.straight.bold.MaskCarnival

@Composable
fun KeywordBadge(keyword: Keyword) {
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
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = keyword.getType().name,
            modifier = Modifier.size(16.dp),
            tint = Color.White
        )
    }
}


private fun getKeywordIconAndColor(keyword: Keyword): Pair<ImageVector, Color> {
    return when (keyword.getType()) {
        KeywordType.POISONOUS -> Pair(CustomIcons.FlaskPoison, Color(0xFF10B981))
        KeywordType.TOUGH -> Pair(Icons.Default.FavoriteBorder, Color(0xFF6B7280))
        KeywordType.SNEAKY -> Pair(CustomIcons.MaskCarnival, Color(0xFF8B5CF6))
    }
}