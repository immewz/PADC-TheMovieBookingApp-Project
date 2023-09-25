package com.mewz.themoviebooking.data.vos.confirmation

import com.google.gson.annotations.SerializedName

data class PaymentVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("icon")
    val icon: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("deleted_at")
    val deletedAt: String?
)
