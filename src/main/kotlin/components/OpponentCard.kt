package components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import entities.Card

@Composable
fun SimpleOpponentCard(
    card: Card
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(width = 181.dp, height = 281.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3E2A2A),
                        Color(0xFF2E1A1A),
                        Color(0xFF1F0F0F)
                    )
                )
            )
            .border(
                width = 2.dp,
                color = Color(0xFFE67E22),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFE67E22).copy(alpha = 0.3f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Large question mark
            Text(
                text = "?",
                color = Color(0xFFE67E22),
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hidden card text
            Text(
                text = card.name,
                color = Color(0xFFE67E22).copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        }
    }
}