package dev.samyak.unagi.data

import dev.samyak.core.data.Library
import dev.samyak.core.database.dao.LibraryDao
import dev.samyak.core.network.LibraryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LibraryRepo @Inject constructor(
    private val libraryAPI: LibraryAPI,
    private val libraryDao: LibraryDao
): LibraryRepoContract {
    override suspend fun fetchAllLibrary(): MutableList<Library> {
        refresh()

        return withContext(Dispatchers.IO) {
            libraryDao.fetchAllLibrary()
        }
    }

    suspend fun getLibrary(libraryId: Int): Library? {
        val results = libraryDao.fetchLibrary(libraryId)
        return if (results.isEmpty()) {
            null
        } else {
            results.first()
        }
    }

    private suspend fun refresh() {
        val response = libraryAPI.listAllLibrary()
        val body = response.body()
        body?.let {
            libraryDao.addToLibrary(it)
        }
    }
}