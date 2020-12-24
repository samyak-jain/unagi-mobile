package dev.samyak.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.samyak.core.data.Episode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEpisode(episode: Episode): Long

    @Query("SELECT * FROM episodes WHERE showId = :showId")
    suspend fun getEpisodes(showId: Int): List<Episode>
}