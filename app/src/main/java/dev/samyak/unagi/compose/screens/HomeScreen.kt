package dev.samyak.unagi.compose.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.samyak.unagi.compose.components.LibraryCard
import dev.samyak.unagi.viewmodels.HomeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun HomePage(navController: NavController, homeModel: HomeModel) {
    val libraryData = homeModel.libraryLiveData.observeAsState(initial = listOf())
    ScrollableColumn(Modifier.fillMaxHeight().fillMaxWidth()) {
        HomeSection("My Library", libraryData.value) { item ->
            LibraryCard(library = item, onClick = {
                navController.navigate("shows/${item.id}")
            })
        }
    }
}

@Composable
fun<T> HomeSection(title: String, data: List<T>, content: @Composable (T) -> Unit) {
    Box(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 24.dp)) {
        Text(text = title, modifier = Modifier.align(Alignment.CenterStart), style = MaterialTheme.typography.h6)
    }
    LazyRowFor(data) { item ->
        content(item)
    }
}