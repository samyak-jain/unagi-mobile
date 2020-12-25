package dev.samyak.unagi.compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.samyak.unagi.ui.UnagiTheme

@Composable
fun Navigator() {
    val navController = rememberNavController()
    UnagiTheme {
        Surface(color = MaterialTheme.colors.surface) {
            Box {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage(navController) }
                }
            }
        }
    }
}