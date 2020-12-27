package dev.samyak.unagi.data

import dev.samyak.core.data.Episode

interface EpisodeRepoContract {
    suspend fun fetchAllEpisodes(showId: Int): MutableList<Episode>
}