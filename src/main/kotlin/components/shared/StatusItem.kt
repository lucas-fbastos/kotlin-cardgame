package components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constants.COLOR_TEXT_SECONDARY

@Composable
internal fun StatItem(
    label: String? = null,
    value: String,
    icon: ImageVector? = null,
    color: Color,
    isEmphasis: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        icon?.let {
            Icon(
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = color,
            )
        } ?: Text(
            text = label!!,
            color = COLOR_TEXT_SECONDARY,
            fontSize = if (isEmphasis) 11.sp else 10.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = value,
            color = color,
            fontSize = if (isEmphasis) 18.sp else 14.sp,
            fontWeight = if (isEmphasis) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}