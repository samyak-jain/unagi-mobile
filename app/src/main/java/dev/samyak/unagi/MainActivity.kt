package dev.samyak.unagi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import dev.samyak.unagi.models.Library
import dev.samyak.unagi.ui.DenkiUITheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DenkiUITheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
fun Library(libraryItem: Library) {
    Column {
        Text(text = libraryItem.name)
        com.skydoves.landscapist.glide.GlideImage(imageModel = libraryItem.thumbnail, contentScale = ContentScale.FillWidth)
    }
}

@Composable
fun MainPage() {
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DenkiUITheme {
        MainPage()
    }
}