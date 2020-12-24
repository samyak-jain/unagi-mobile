package dev.samyak.core.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey
    val id: Int,
    @SerializedName("show_id")
    val showId: Int,
    val name: String,
    val thumbnail: String,
    @SerializedName("locator_id")
    val UID: String,
    @SerializedName("episode_number")
    val episodeNumber: Int
): Parcelable