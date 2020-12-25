package dev.samyak.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://192.168.0.110:8000"

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(client).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providesLibraryAPI(retrofit: Retrofit): LibraryAPI {
        return retrofit.create(LibraryAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesShowsAPI(retrofit: Retrofit): ShowsAPI {
        return retrofit.create(ShowsAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesEpisodesAPI(retrofit: Retrofit): EpisodesAPI {
        return retrofit.create(EpisodesAPI::class.java)
    }
}