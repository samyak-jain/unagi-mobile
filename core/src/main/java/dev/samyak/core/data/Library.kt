package dev.samyak.core.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "library")
data class Library(
    @PrimaryKey
    val id: Int,
    val name: String,
): Parcelable {
    @IgnoredOnParcel
    val thumbnail: String
        get() = "http://192.168.0.110:8000/library/thumbnail/$id"
}