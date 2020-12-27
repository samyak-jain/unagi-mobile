package dev.samyak.unagi.data

import dev.samyak.core.data.Show
import dev.samyak.core.database.dao.ShowDao
import dev.samyak.core.network.ShowsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowRepo @Inject constructor(
    private val showsAPI: ShowsAPI,
    private val showDao: ShowDao
): ShowRepoContract {
    override suspend fun fetchAllShows(libraryId: Int): MutableList<Show> {
        refresh(libraryId)

        return withContext(Dispatchers.IO) {
            showDao.getShows(libraryId)
        }
    }

    suspend fun getShow(showId: Int): Show? {
        val results = showDao.getShow(showId)
        return if (results.isEmpty()) {
            null
        } else {
            results.first()
        }
    }

    private suspend fun refresh(libraryId: Int) {
        val response = showsAPI.listShows(libraryId)
        val body = response.body()
        body?.let {
            showDao.addShows(it)
        }
    }
}