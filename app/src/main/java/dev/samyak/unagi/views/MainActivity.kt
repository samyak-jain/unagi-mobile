package dev.samyak.unagi.views

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.samyak.unagi.compose.screens.EpisodePage
import dev.samyak.unagi.compose.screens.HomePage
import dev.samyak.unagi.compose.screens.ShowPage
import dev.samyak.unagi.compose.screens.VideoPlayer
import dev.samyak.unagi.ui.UnagiTheme
import dev.samyak.unagi.viewmodels.EpisodeScreenModel
import dev.samyak.unagi.viewmodels.HomeModel
import dev.samyak.unagi.viewmodels.ShowScreenModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    private val homeModel: HomeModel by viewModels()
    private val showScreenModel: ShowScreenModel by viewModels()
    private val episodeScreenModel: EpisodeScreenModel by viewModels()

    @ExperimentalFoundationApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            UnagiTheme(true) {
                Surface(color = MaterialTheme.colors.surface) {
                    Box {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { HomePage(navController, homeModel) }

                            composable(
                                "shows/{libraryId}",
                                arguments = listOf(navArgument("libraryId") { type = NavType.IntType })
                            ) {
                                    backStackEntry -> backStackEntry.arguments?.getInt("libraryId")?.let {
                                        ShowPage(navController, showScreenModel, it)
                                    }
                            }

                            composable(
                                "episodes/{showId}",
                                arguments = listOf(navArgument("showId") { type = NavType.IntType })
                            ) {
                                backStackEntry -> backStackEntry.arguments?.getInt("showId")?.let {
                                    EpisodePage(
                                        navController = navController,
                                        episodeScreenModel = episodeScreenModel,
                                        showId = it
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}