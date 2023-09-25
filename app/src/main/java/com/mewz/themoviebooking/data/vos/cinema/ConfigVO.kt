package com.mewz.themoviebooking.data.vos.cinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.persistance.typeconverters.ValueAnyTypeConverter

@Entity("config_table")
@TypeConverters(
    ValueAnyTypeConverter::class
)
data class ConfigVO(

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("key")
    @ColumnInfo("key")
    val key: String?,

    @SerializedName("value")
    @ColumnInfo("value")
    val value: Any?
)
