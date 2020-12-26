package dev.samyak.core.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.samyak.core.database.UnagiDB
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object StorageModule {
    @Provides
    @Singleton
    fun providesLibraryDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UnagiDB::class.java, "unagi_db")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesLibraryDao(unagiDB: UnagiDB) = unagiDB.libraryDao

    @Provides
    @Singleton
    fun providesShowDao(unagiDB: UnagiDB) = unagiDB.showDao

    @Provides
    @Singleton
    fun providesEpisodeDao(unagiDB: UnagiDB) = unagiDB.episodeDao
}