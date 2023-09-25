package com.mewz.themoviebooking.data.vos.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("cities_table")
data class CitiesVO(

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("name")
    @ColumnInfo("name")
    val name: String?,

    @SerializedName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String?
)
