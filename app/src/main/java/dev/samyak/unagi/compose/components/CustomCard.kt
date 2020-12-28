package dev.samyak.unagi.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.samyak.core.data.Library
import dev.samyak.core.data.Show
import java.util.*

@Composable
fun LibraryCard(library: Library, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(modifier.width(280.dp).padding(vertical = 8.dp)) {
        Card(elevation = 4.dp,
            modifier = modifier.padding(start = 16.dp, 8.dp).clickable { onClick() },
            shape = MaterialTheme.shapes.medium) {

            Column {
                LoadImage(url = library.thumbnail,
                    modifier = Modifier.fillMaxWidth().aspectRatio(6 / 3F),
                    enableShimmer = true)

                Text(text = library.name.capitalize(Locale.getDefault()),
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 3.dp).align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun ShowCard(show: Show, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(modifier.width(125.dp).padding(vertical = 2.dp)) {
        Card(elevation = 4.dp,
            modifier = modifier.padding(horizontal = 8.dp, 4.dp).clickable { onClick() },
            shape = MaterialTheme.shapes.medium) {

            Column {
                LoadImage(url = show.coverImage,
                    modifier = Modifier.fillMaxWidth().aspectRatio(2 / 3F),
                    enableShimmer = true)

                Text(text = show.title.capitalize(Locale.getDefault()),
                    style = MaterialTheme.typography.caption,
                    maxLines = 2,
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 3.dp).align(Alignment.CenterHorizontally))
            }
        }
    }
}