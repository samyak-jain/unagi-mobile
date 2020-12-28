package dev.samyak.unagi.compose.components

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.core.util.Pools
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.math.max
import kotlin.math.min
import kotlin.math.tan

@Composable
fun LoadImage(url: String,
              modifier: Modifier = Modifier,
              contentScale: ContentScale = ContentScale.Crop,
              enableShimmer: Boolean = false, onPalette: (Palette?) -> Unit = {}) {

    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val sizeModifier = modifier.fillMaxWidth().fillMaxHeight()
    val context = AmbientContext.current
    var imagePalette by remember { mutableStateOf<Palette?>(null) }

    var drawable by remember { mutableStateOf(true) }

    onCommit(url) {
        val glide = Glide.with(context)
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image = resource.asImageBitmap()
                Palette.from(resource).generate { palette ->
                    onPalette(palette)
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                image = null
            }
        }

        glide.asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target)

        onDispose {
            image = null
            drawable = true
            glide.clear(target)
        }
    }

    val theImage = image

    if (theImage != null) {
        Column(modifier = sizeModifier) {
            Image(bitmap = theImage, modifier = sizeModifier, contentScale = contentScale)
        }
    } else if (drawable) {
        if (enableShimmer) {
            Shimmer(baseColor = Color.DarkGray, highlightColor = Color.Black)
        }
    }
}

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    baseColor: Color,
    highlightColor: Color,
    intensity: Float = 0.5f,
    dropOff: Float = 0.5f,
    tilt: Float = 0f,
    durationMillis: Int = 650
) {
    val animatedProgress = animatedFloat(0f)
    onActive {
        animatedProgress.animateTo(
            targetValue = 1f,
            anim = repeatable(
                iterations = AnimationConstants.Infinite,
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            ),
        )
    }

    Canvas(modifier.fillMaxSize()) {
        val paint = paintPool.acquire() ?: Paint()
        val shaderMatrix = Matrix()
        val tiltTan = tan(Math.toRadians(tilt.toDouble()))
        val width = (size.width + tiltTan * size.height).toFloat()

        try {
            val dx = offset(-width, width, animatedProgress.value)
            val shader: Shader = LinearGradientShader(
                from = Offset(0f, 0f),
                to = Offset(size.width, 0f),
                colors = listOf(
                    baseColor,
                    highlightColor,
                    highlightColor,
                    baseColor
                ),
                colorStops = listOf(
                    max((1f - intensity - dropOff) / 2f, 0f),
                    max((1f - intensity - 0.001f) / 2f, 0f),
                    min((1f + intensity + 0.001f) / 2f, 1f),
                    min((1f + intensity + dropOff) / 2f, 1f)
                ),
                tileMode = TileMode.Clamp
            )
            val brush = ShaderBrush(shader)
            paint.asFrameworkPaint().apply {
                isAntiAlias = true
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                setShader(shader)
            }

            shaderMatrix.reset()
            shaderMatrix.setRotate(tilt, size.width / 2f, size.height / 2f)
            shaderMatrix.postTranslate(dx, 0f)
            shader.setLocalMatrix(shaderMatrix)

            drawIntoCanvas { canvasView ->
                canvasView.saveLayer(size.toRect(), paint)

                drawRect(brush, Offset(0f, 0f), size)

                canvasView.restore()
            }
        } finally {
            // resets the paint and release to the pool.
            paint.asFrameworkPaint().reset()
            paintPool.release(paint)
        }
    }
}

fun offset(start: Float, end: Float, percent: Float): Float {
    return start + (end - start) * percent
}

private val paintPool = Pools.SimplePool<Paint>(2)