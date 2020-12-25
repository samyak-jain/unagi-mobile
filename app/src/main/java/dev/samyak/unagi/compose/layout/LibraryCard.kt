package dev.samyak.unagi.compose.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import dev.samyak.core.data.Library

@Composable
fun LibraryCard(library: Library, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier.width(120.dp).padding(vertical = 8.dp)) {
        Card(elevation = 4.dp,
            modifier = Modifier.clickable { onClick() },
            shape = MaterialTheme.shapes.medium) {
            
            GlideImage(
                imageModel = library.thumbnail,
                modifier = Modifier.fillMaxWidth().aspectRatio(3 /4F),
                requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
            )
            
            Text(text = library.name,
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 2.dp))
        }
    }
}