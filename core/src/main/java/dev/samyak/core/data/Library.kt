package dev.samyak.core.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "library")
data class Library(
    @PrimaryKey
    val id: Int,
    val name: String,
    val location: String,
    val thumbnail: String = "/media/$id.jpeg"
): Parcelable