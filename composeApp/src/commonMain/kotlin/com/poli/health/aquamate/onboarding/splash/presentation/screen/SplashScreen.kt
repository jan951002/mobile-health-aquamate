package com.poli.health.aquamate.onboarding.splash.presentation.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedWaterDrop(
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
private fun AnimatedWaterDrop(
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val surfaceColor = MaterialTheme.colorScheme.surface

    val infiniteTransition = rememberInfiniteTransition()
    val waterLevel by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .drawWithCache {
                val width = size.width
                val height = size.height

                val scaleX = width / 60f
                val scaleY = height / 72f
                val scale = minOf(scaleX, scaleY)

                val offsetX = (width - 60f * scale) / 2f
                val offsetY = (height - 72f * scale) / 2f

                val dropPath = Path().apply {
                    val sx = scale
                    val sy = scale
                    val ox = offsetX
                    val oy = offsetY

                    moveTo(30f * sx + ox, 61f * sy + oy)

                    cubicTo(
                        24.2917f * sx + ox, 61f * sy + oy,
                        19.5312f * sx + ox, 59.0417f * sy + oy,
                        15.7188f * sx + ox, 55.125f * sy + oy
                    )

                    cubicTo(
                        11.9062f * sx + ox, 51.2083f * sy + oy,
                        10f * sx + ox, 46.3333f * sy + oy,
                        10f * sx + ox, 40.5f * sy + oy
                    )

                    cubicTo(
                        10f * sx + ox, 36.3333f * sy + oy,
                        11.6562f * sx + ox, 31.8021f * sy + oy,
                        14.9688f * sx + ox, 26.9062f * sy + oy
                    )

                    cubicTo(
                        18.2812f * sx + ox, 22.0104f * sy + oy,
                        23.2917f * sx + ox, 16.7083f * sy + oy,
                        30f * sx + ox, 11f * sy + oy
                    )

                    cubicTo(
                        36.7083f * sx + ox, 16.7083f * sy + oy,
                        41.7188f * sx + ox, 22.0104f * sy + oy,
                        45.0312f * sx + ox, 26.9062f * sy + oy
                    )

                    cubicTo(
                        48.3438f * sx + ox, 31.8021f * sy + oy,
                        50f * sx + ox, 36.3333f * sy + oy,
                        50f * sx + ox, 40.5f * sy + oy
                    )

                    cubicTo(
                        50f * sx + ox, 46.3333f * sy + oy,
                        48.0938f * sx + ox, 51.2083f * sy + oy,
                        44.2812f * sx + ox, 55.125f * sy + oy
                    )

                    cubicTo(
                        40.4688f * sx + ox, 59.0417f * sy + oy,
                        35.7083f * sx + ox, 61f * sy + oy,
                        30f * sx + ox, 61f * sy + oy
                    )

                    close()
                }

                onDrawBehind {
                    drawPath(
                        path = dropPath,
                        color = primaryColor,
                        style = Fill
                    )

                    clipPath(dropPath) {
                        val waterY = height * waterLevel

                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    primaryColor,
                                    primaryContainerColor,
                                    primaryColor.copy(alpha = 0.8f)
                                ),
                                startY = waterY - height * 0.2f,
                                endY = height
                            ),
                            topLeft = Offset(0f, waterY),
                            size = Size(width, height - waterY)
                        )

                        drawRect(
                            color = surfaceColor.copy(alpha = 0.3f),
                            topLeft = Offset(0f, waterY - 2f),
                            size = Size(width, 4f)
                        )
                    }
                }
            }
    )
}
