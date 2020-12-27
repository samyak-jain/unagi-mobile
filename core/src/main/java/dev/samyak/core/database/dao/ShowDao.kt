package dev.samyak.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.samyak.core.data.Show

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShows(show: List<Show>)

    @Query("SELECT * FROM shows WHERE libraryId = :libraryId")
    suspend fun getShows(libraryId: Int): MutableList<Show>
}