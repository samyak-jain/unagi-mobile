package dev.samyak.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.samyak.core.data.Episode
import dev.samyak.core.data.Library
import dev.samyak.core.data.Show
import dev.samyak.core.database.dao.EpisodeDao
import dev.samyak.core.database.dao.LibraryDao
import dev.samyak.core.database.dao.ShowDao

@Database(entities = [Library::class, Show::class, Episode::class], version = 1, exportSchema = false)
abstract class UnagiDB: RoomDatabase() {
    abstract val libraryDao: LibraryDao
    abstract val showDao: ShowDao
    abstract val episodeDao: EpisodeDao
}