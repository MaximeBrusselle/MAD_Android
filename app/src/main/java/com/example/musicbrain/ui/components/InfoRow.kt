package com.example.musicbrain.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Custom composable for displaying an information row with a field and its corresponding value.
 *
 * @param field The title or label representing the information.
 * @param value The value associated with the information.
 */
@Composable
fun InfoRow(
    field: String,
    value: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        Text(
            text = field,
            style =
                TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp,
                ),
            textAlign = TextAlign.Start,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End,
        )
    }
}
