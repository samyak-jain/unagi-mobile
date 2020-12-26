package dev.samyak.unagi.data.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.samyak.core.data.Library

class LibraryProvider: PreviewParameterProvider<Library> {
    override val values: Sequence<Library>
        get() = sequenceOf(
                Library(1, "Anime")
        )
}