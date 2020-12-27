package dev.samyak.unagi.data

import dev.samyak.core.data.Library

interface LibraryRepoContract {
    suspend fun fetchAllLibrary(): MutableList<Library>
}