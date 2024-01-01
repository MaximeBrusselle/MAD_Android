package com.example.musicbrain.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Custom composable for displaying an image in the detail screen.
 *
 * @param id The resource ID of the image.
 * @param contentDescription The content description for accessibility.
 */
@Composable
fun DetailImage(
    id: Int,
    contentDescription: String,
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = contentDescription,
        modifier =
            Modifier
                .width(128.dp)
                .height(128.dp)
                .padding(8.dp)
                .testTag("DetailImage"),
    )
}
