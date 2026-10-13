package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shapeRadius: Dp = 16.dp,
    borderBrush: Brush? = null,
    backgroundColor: Color? = null,
    elevation: Dp = 4.dp,
    onClick: (() -> Unit)? = null,
    testTag: String = "glass_card",
    content: @Composable BoxScope.() -> Unit
) {
    val defaultBorderBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.20f),
            Color.Transparent
        )
    )

    val shape = RoundedCornerShape(shapeRadius)
    val finalBg = backgroundColor ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)

    Surface(
        modifier = modifier
            .testTag(testTag)
            .shadow(elevation, shape, clip = false)
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            ),
        shape = shape,
        color = finalBg,
        border = BorderStroke(1.dp, borderBrush ?: defaultBorderBrush)
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun AuroraGlowBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                )
            ),
        content = content
    )
}
