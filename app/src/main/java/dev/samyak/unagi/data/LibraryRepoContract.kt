package dev.samyak.unagi.data

import dev.samyak.core.data.Library
import dev.samyak.core.network.ApiResult

interface LibraryRepoContract {
    suspend fun fetchAllLibrary(): ApiResult<List<Library>>
}