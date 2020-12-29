package dev.samyak.unagi.compose.screens

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun VideoPlayer(uri: Uri) {
    val context = AmbientContext.current

    val trackSelector = DefaultTrackSelector(context)
    trackSelector.setParameters(
        trackSelector.buildUponParameters()
            .setPreferredAudioLanguage("ja")
            .setPreferredTextLanguage("en")
    )

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector)
            .build()
            .apply {
                val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory(
                    Util.getUserAgent(context, context.packageName)
                )

                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(uri))

                setMediaSource(hlsMediaSource)
                prepare()
            }
    }

    exoPlayer.playWhenReady = true

    AndroidView(viewBlock = { context ->
        PlayerView(context).apply {
            hideController()
            useController = true
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }, modifier = Modifier.fillMaxWidth().fillMaxHeight())

    onDispose(callback = {
        exoPlayer.release()
    })

}