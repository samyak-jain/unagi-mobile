package dev.samyak.unagi.compose.screens

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import dev.samyak.core.data.Episode
import dev.samyak.unagi.compose.components.CustomTabLayout
import dev.samyak.unagi.compose.components.LoadImage
import dev.samyak.unagi.viewmodels.EpisodeScreenModel
import dev.samyak.unagi.views.VideoActivity
import java.util.*


@Composable
fun EpisodePage(navController: NavController, episodeScreenModel: EpisodeScreenModel, showId: Int) {
    val showData = episodeScreenModel.showLiveData.observeAsState(initial = listOf())
    episodeScreenModel.getShow(showId)

    val episodeData = episodeScreenModel.episodeLiveData.observeAsState(initial = listOf())
    episodeScreenModel.fetchEpisodes(showId)

    val pageToLoad = remember { mutableStateOf(0) }

    var palette by remember { mutableStateOf<Palette?>(null) }

    if (showData.value.isNotEmpty()) {
        val showDataValue = showData.value.first()

        Column(Modifier.fillMaxHeight().fillMaxWidth().padding(bottom = 8.dp)) {
            LoadImage(url = showDataValue.bannerImage,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.FillHeight, onPalette = { palette = it })

            Text(text = showDataValue.title.capitalize(Locale.getDefault()),
                    modifier = Modifier.align(Alignment.Start).padding(10.dp),
                    style = MaterialTheme.typography.h6)


                palette?.getDarkVibrantColor(android.graphics.Color.RED)?.let {
                    CustomTabLayout(it) { index -> pageToLoad.value = index }
                } ?: CustomTabLayout(android.graphics.Color.BLACK) { index -> pageToLoad.value = index }

            if (pageToLoad.value == 0) {

                palette?.getDarkMutedColor(android.graphics.Color.BLACK)?.let {
                    AboutPage(description = showDataValue.description, color = it)
                } ?: AboutPage(description = showDataValue.description)

            } else if (pageToLoad.value == 1) {
                EpisodeScrollView(episodeData.value) { context, episode ->
                    episodeScreenModel.startTranscoding(episode.id)

                    val videoURL = "http://192.168.0.110:8000/file/${episode.UID}/out.m3u8"
                    val subtitleURL = "http://192.168.0.110:8000/file/${episode.UID}/out_vtt.m3u8"

                    val intent = Intent(context, VideoActivity::class.java).apply {
                        putExtra("uri", videoURL)
                    }

                    context.startActivity(intent)

                }
            }
        }
    }
}

@Composable
fun AboutPage(description: String, color: Int = android.graphics.Color.BLACK) {
    Box(modifier = Modifier.background(Color(color)).fillMaxSize()) {
        Column(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp)
        ) {
            Text(text = "Description", style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(vertical = 10.dp))

            Text(
                    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
                    } else {
                        @Suppress("DEPRECATION")
                        Html.fromHtml(description).toString()
                    }, style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 30.dp),
                    overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EpisodeScrollView(episodeList: List<Episode>, onClickEpisode: (Context, Episode) -> Unit) {
    val context = AmbientContext.current

    LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        items(episodeList) { item ->
            Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                item.thumbnail?.let {
                    LoadImage(url = it, enableShimmer = true, onClickImage = {
                        onClickEpisode(context, item)
                    })
                }

                Providers(AmbientContentAlpha provides ContentAlpha.medium) {

                    val textHeightModifier = if (item.thumbnail == null) {
                        Modifier.height(40.dp).align(Alignment.CenterStart)
                    } else {
                        Modifier.align(Alignment.BottomStart)
                    }

                    Text(
                            text = item.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = textHeightModifier.fillMaxWidth()
                                    .background(alpha = 0.5f, brush = SolidColor(Color.Black))
                    )
                }
            }
        }
    }
}