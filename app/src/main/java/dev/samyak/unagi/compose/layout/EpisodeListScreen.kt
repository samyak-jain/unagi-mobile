package dev.samyak.unagi.compose.layout

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout
import dev.samyak.core.data.Episode
import dev.samyak.unagi.viewmodels.EpisodeScreenModel
import java.util.*

@Composable
fun EpisodePage(navController: NavController, episodeScreenModel: EpisodeScreenModel, showId: Int) {
    val showData = episodeScreenModel.showLiveData.observeAsState(initial = listOf())
    episodeScreenModel.getShow(showId)

    val episodeData = episodeScreenModel.episodeLiveData.observeAsState(initial = listOf())
    episodeScreenModel.fetchEpisodes(showId)

    val pageToLoad = remember { mutableStateOf(0) }

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    if (showData.value.isNotEmpty()) {
        val showDataValue = showData.value.first()

        Glide.with(AmbientContext.current).asBitmap()
            .load(showDataValue.bannerImage).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        Column(Modifier.fillMaxHeight().fillMaxWidth().padding(bottom = 8.dp)) {
            bitmap?.asImageBitmap()?.let {
                Image(bitmap = it,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.FillHeight)
            }

            Text(text = showDataValue.title.capitalize(Locale.getDefault()),
                modifier = Modifier.align(Alignment.Start).padding(horizontal = 10.dp),
                style = MaterialTheme.typography.h6)

            CustomTabLayout { index -> pageToLoad.value = index}
            if (pageToLoad.value == 0) {
                AboutPage(description = showDataValue.description)
            } else if (pageToLoad.value == 1) {
                EpisodeScrollView(episodeList = episodeData.value)
            }
        }
    }
}

@Composable
fun AboutPage(description: String) {
    Column(modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp)) {
        Text(text = "Description", style = MaterialTheme.typography.subtitle1)
        Text(text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(description).toString()
        }, style = MaterialTheme.typography.body1,
        overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun EpisodeScrollView(episodeList: List<Episode>) {
    LazyColumnFor(items = episodeList, modifier = Modifier.fillMaxHeight().fillMaxWidth()) { item ->
        Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
            item.thumbnail?.let { LoadImage(url = it) }

            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text(text = item.name, modifier = Modifier.align(Alignment.BottomStart))
            }
        }
    }
}

@Composable
fun LoadImage(url: String, modifier: Modifier = Modifier) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    val sizeModifier = modifier.fillMaxWidth().fillMaxHeight()
    val context = AmbientContext.current

    onCommit(url) {
        val glide = Glide.with(context)
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image = resource.asImageBitmap()
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                image = null
                drawable = placeholder
            }
        }

        glide.asBitmap().load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(target)

        onDispose {
            image = null
            drawable = null
            glide.clear(target)
        }
    }

    val theImage = image
    val theDrawable = drawable

    if (theImage != null) {
        Column(modifier = sizeModifier) {
            Image(bitmap = theImage, modifier = sizeModifier, contentScale = ContentScale.Crop)
        }
    } else if (theDrawable != null) {
        Canvas(modifier = sizeModifier) {
            drawIntoCanvas { canvas ->
                theDrawable.draw(canvas.nativeCanvas)
            }
        }
    }
}

@Composable
fun ShimmerView() {
    val context = AmbientContext.current

    val customView = remember {
        ShimmerFrameLayout(context)
    }

    AndroidView({ customView }, modifier = Modifier.fillMaxWidth().fillMaxHeight()) { view ->
        view.startShimmer()
    }
}