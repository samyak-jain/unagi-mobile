package dev.samyak.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.samyak.core.data.Episode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEpisodes(episodes: List<Episode>)

    @Query("SELECT * FROM episodes WHERE showId = :showId AND episodeNumber IS NOT NULL ORDER BY episodeNumber")
    suspend fun getEpisodes(showId: Int): MutableList<Episode>

    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    suspend fun getEpisode(episodeId: Int): Episode
}