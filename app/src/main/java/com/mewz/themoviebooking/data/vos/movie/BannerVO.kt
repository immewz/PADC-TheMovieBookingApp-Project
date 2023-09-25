package com.mewz.themoviebooking.data.vos.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("banner_table")
data class BannerVO(

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("title")
    @ColumnInfo("title")
    val title: String?,

    @SerializedName("url")
    @ColumnInfo("url")
    val url: String?,

    @SerializedName("is_active")
    @ColumnInfo("is_active")
    val isActive: Int?,

    @SerializedName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String?
)
