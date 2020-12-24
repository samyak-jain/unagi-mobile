package dev.samyak.core.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shows")
data class Show(
    @PrimaryKey
    val id: Int,
    @SerializedName("library_id")
    val libraryId: Int,
    val title: String,
    val description: String,
    @SerializedName("cover_image")
    val coverImage: String,
    @SerializedName("banner_image")
    val bannerImage: String,
    val season: Int,
    @SerializedName("parent_season")
    val parentSeason: Int,
): Parcelable