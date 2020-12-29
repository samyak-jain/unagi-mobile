package dev.samyak.unagi.data

import dev.samyak.core.data.Episode
import dev.samyak.core.data.Show
import dev.samyak.core.database.dao.EpisodeDao
import dev.samyak.core.database.dao.ShowDao
import dev.samyak.core.network.EpisodesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodeRepo @Inject constructor(
    private val episodesAPI: EpisodesAPI,
    private val episodeDao: EpisodeDao,
    private val showDao: ShowDao
) : EpisodeRepoContract {
    override suspend fun fetchAllEpisodes(showId: Int): MutableList<Episode> {
        refresh(showId)

        return withContext(Dispatchers.IO) {
            episodeDao.getEpisodes(showId)
        }
    }

    override suspend fun startTranscoding(episodeID: Int) {
        val episode = withContext(Dispatchers.IO) {
            episodeDao.getEpisode(episodeID)
        }

        episodesAPI.startTranscode(episode.UID)
    }

    suspend fun getShow(showId: Int): List<Show> {
        return withContext(Dispatchers.IO) {
            showDao.getShow(showId)
        }
    }

    private suspend fun refresh(showId: Int) {
        val response = episodesAPI.listEpisodes(showId)
        val body = response.body()
        body?.let {
            episodeDao.addEpisodes(it)
        }
    }
}