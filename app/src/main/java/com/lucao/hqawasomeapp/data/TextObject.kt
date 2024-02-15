package com.lucao.hqawasomeapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Comic::class,
            parentColumns = ["id"],
            childColumns = ["comicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
data class TextObject(
    @PrimaryKey
    val textObjectId: Int?,
    val type: String?,
    val language: String?,
    val text: String?,
    @ColumnInfo(index = true)
    val comicId: Int?
)
