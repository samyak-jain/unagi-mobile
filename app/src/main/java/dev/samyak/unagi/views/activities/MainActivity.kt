package dev.samyak.unagi.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.samyak.unagi.compose.layout.HomePage
import dev.samyak.unagi.compose.layout.ShowPage
import dev.samyak.unagi.ui.UnagiTheme
import dev.samyak.unagi.viewmodels.HomeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    private val homeModel: HomeModel by viewModels()

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
                            composable("shows") { ShowPage() }
                        }
                    }
                }
            }
        }
    }
}