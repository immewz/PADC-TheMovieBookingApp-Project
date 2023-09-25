package com.mewz.themoviebooking.data.vos.snack

import com.google.gson.annotations.SerializedName

data class SnackCategoryVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("title_mm")
    val titleMm: String?,

    @SerializedName("is_active")
    val isActive: Int?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("deleted_at")
    val deletedAt: String?
)
