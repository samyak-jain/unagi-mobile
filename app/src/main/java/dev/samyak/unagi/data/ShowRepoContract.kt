package dev.samyak.unagi.data

import dev.samyak.core.data.Show

interface ShowRepoContract {
    suspend fun fetchAllShows(libraryId: Int): MutableList<Show>
}