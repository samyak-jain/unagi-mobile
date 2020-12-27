package dev.samyak.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.samyak.core.data.Library

@Dao
interface LibraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToLibrary(library: List<Library>)

    @Query("SELECT * FROM library")
    suspend fun fetchAllLibrary(): MutableList<Library>

    @Query("SELECT * FROM library")
    suspend fun hasData(): List<Library>

    @Query("SELECT * FROM library WHERE id = :id")
    suspend fun fetchLibrary(id: Int): List<Library>
}