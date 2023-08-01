package com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.persistance.typeconverter.ValueTypeConverter

@Entity("config")
@TypeConverters(
    ValueTypeConverter::class
)
data class CinemaConfigVO(
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("key")
    @ColumnInfo("key")
    val key: String?,

    @SerializedName("value")
    @ColumnInfo("value")
    val value: Any?,
)