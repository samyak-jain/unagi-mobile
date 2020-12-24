package dev.samyak.core.network

import dev.samyak.core.data.Show
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShowsAPI {
    @GET("shows/{id}")
    suspend fun listShows(@Path("id") id: Int): Response<List<Show>>
}