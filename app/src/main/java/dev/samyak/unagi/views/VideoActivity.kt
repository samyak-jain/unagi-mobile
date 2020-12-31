package dev.samyak.unagi.views

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dev.samyak.unagi.databinding.PlayerViewBinding

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: PlayerViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.getStringExtra("uri")!!

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding = PlayerViewBinding.inflate(layoutInflater)
        val view = binding.root

        val trackSelector = DefaultTrackSelector(baseContext)
        trackSelector.setParameters(
            trackSelector.buildUponParameters()
        )

        val exoPlayer = SimpleExoPlayer.Builder(baseContext).setTrackSelector(trackSelector)
            .build()
            .apply {
                val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory(
                    Util.getUserAgent(baseContext, baseContext.packageName)
                )

                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(uri))

                setMediaSource(hlsMediaSource)

                prepare()
            }

        exoPlayer.playWhenReady = true
        binding.playerView.apply {
            player = exoPlayer
            hideController()
        }

        setContentView(view)

    }
}