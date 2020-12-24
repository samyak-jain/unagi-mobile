package dev.samyak.unagi.data

import dev.samyak.core.data.Library
import dev.samyak.core.database.dao.LibraryDao
import dev.samyak.core.network.ApiResult
import dev.samyak.core.network.LibraryAPI
import dev.samyak.core.network.handleApi
import javax.inject.Inject

class LibraryRepo @Inject constructor(
    private val libraryAPI: LibraryAPI,
    private val libraryDao: LibraryDao
): LibraryRepoContract {
    override suspend fun fetchAllLibrary(): ApiResult<List<Library>> = handleApi({
            libraryAPI.listAllLibrary()
    })

}