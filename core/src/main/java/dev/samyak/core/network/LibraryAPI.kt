package dev.samyak.core.network

import dev.samyak.core.data.Library
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LibraryAPI {
    @GET("library")
    suspend fun listAllLibrary(): Response<List<Library>>

    @GET("library/{id}")
    suspend fun getLibrary(@Path("id") id: Int): Response<Library>
}