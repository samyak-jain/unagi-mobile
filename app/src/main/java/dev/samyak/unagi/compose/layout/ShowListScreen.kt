package dev.samyak.unagi.compose.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.samyak.unagi.viewmodels.ShowScreenModel

@Composable
fun ShowPage(navController: NavController, showScreenModel: ShowScreenModel, libraryId: Int) {
    val libraryName = showScreenModel.libraryName.observeAsState(initial = "Library")
    showScreenModel.getLibrary(libraryId)

    val showData = showScreenModel.showLiveData.observeAsState(initial = listOf())
    showScreenModel.fetchShows(libraryId)


    Column(Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)) {
        Text(text = libraryName.value.capitalize(), modifier = Modifier.align(Alignment.Start), style = MaterialTheme.typography.h6)
        LazyGridFor(showData.value) { item ->
            ShowCard(show = item)
        }
    }
}


@Composable
fun <T> LazyGridFor(
    items: List<T> = listOf(),
    rows: Int = 3,
    hPadding: Int = 6,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    val chunkedList = items.chunked(rows)
    LazyColumnFor(items = chunkedList, modifier = Modifier.padding(horizontal = hPadding.dp)) { it ->
        Row {
            it.forEach { item ->
                Box(modifier = Modifier.weight(1F).align(Alignment.Top).padding(3.dp)) {
                    itemContent(item)
                }
            }
            repeat(rows - it.size) {
                Box(modifier = Modifier.weight(1F).padding(8.dp)) {}
            }
        }
    }
}