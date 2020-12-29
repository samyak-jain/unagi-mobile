package dev.samyak.core.network

import dev.samyak.core.data.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesAPI {
    @GET("episodes/{id}")
    suspend fun listEpisodes(@Path("id") id: Int): Response<List<Episode>>

    @GET("transcode/{uid}")
    suspend fun startTranscode(@Path("uid") uid: String)
}