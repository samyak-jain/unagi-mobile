package dev.samyak.unagi.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.samyak.unagi.compose.components.ShowCard
import dev.samyak.unagi.viewmodels.ShowScreenModel
import java.util.*


@ExperimentalFoundationApi
@Composable
fun ShowPage(navController: NavController, showScreenModel: ShowScreenModel, libraryId: Int) {
    val libraryName = showScreenModel.libraryName.observeAsState(initial = "Library")
    showScreenModel.getLibrary(libraryId)

    val showData = showScreenModel.showLiveData.observeAsState(initial = listOf())
    showScreenModel.fetchShows(libraryId)

    Column(Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)) {
        Text(text = libraryName.value.capitalize(Locale.getDefault()), style = MaterialTheme.typography.h6)

        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
        ) {
            items(showData.value) { item ->
                ShowCard(show = item, Modifier.padding(horizontal = 6.dp, vertical = 3.dp), onClick = {
                    navController.navigate("episodes/${item.id}")
                })
            }
        }
    }
}