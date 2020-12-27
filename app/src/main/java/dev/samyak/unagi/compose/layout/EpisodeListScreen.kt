package dev.samyak.unagi.compose.layout

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dev.samyak.core.data.Episode
import dev.samyak.unagi.viewmodels.EpisodeScreenModel
import java.util.*
import kotlin.collections.HashMap

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
                    contentScale = ContentScale.Crop)
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
    Text(text = "Description", style = MaterialTheme.typography.subtitle1)
    Text(text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(description).toString()
    }, style = MaterialTheme.typography.body1)
}

@Composable
fun EpisodeScrollView(episodeList: List<Episode>) {

    val thumbnailList: MutableList<Bitmap> = remember { mutableListOf() }


    LazyColumnForIndexed(items = episodeList) { index, item ->

        Glide.with(AmbientContext.current).asBitmap()
            .load(item.thumbnail).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    thumbnailList[index] = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

            })

        thumbnailList.getOrNull(index)?.let {
            Box(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                Image(
                    bitmap = it.asImageBitmap(),
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )

                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                    Text(text = item.name, modifier = Modifier.align(Alignment.CenterStart), color = MaterialTheme.colors.background)
                }
            }
        }
    }
}