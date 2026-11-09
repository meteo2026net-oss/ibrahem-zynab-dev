package com.example.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MetraCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.5f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            )
            .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius)),
        color = backgroundColor
    ) {
        content()
    }
}

@Composable
fun MetraGlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    val isDark = !MaterialTheme.colorScheme.surface.isLight()
    
    Surface(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.4f),
                spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
            )
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                    )
                ),
                RoundedCornerShape(cornerRadius)
            )
            .clip(RoundedCornerShape(cornerRadius)),
        color = if (isDark) Color.White.copy(alpha = 0.03f) else Color.White.copy(alpha = 0.7f)
    ) {
        content()
    }
}

// Extension to check if a color is light
private fun Color.isLight(): Boolean {
    val luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue
    return luminance > 0.5
}

@Composable
fun MetraGradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    gradient: List<Color> = MetraCyanIndigo,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null, 
                enabled = enabled,
                onClick = onClick
            )
            .then(
                if (enabled) Modifier.background(Brush.horizontalGradient(gradient))
                else Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            )
            .shadow(8.dp, shape, spotColor = gradient.first().copy(alpha = 0.6f)),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            content = content
        )
    }
}

val Modifier.metraGradientBackground: Modifier
    @Composable
    get() = this.background(
        Brush.verticalGradient(if (MaterialTheme.colorScheme.surface.isLight()) MetraLightGradient else MetraDarkGradient)
    )

val Modifier.metraCyanGradient: Modifier
    @Composable
    get() = this.background(
        Brush.horizontalGradient(MetraCyanIndigo)
    )

val Modifier.metraGlowShadow: Modifier
    @Composable
    get() = this.shadow(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    )
