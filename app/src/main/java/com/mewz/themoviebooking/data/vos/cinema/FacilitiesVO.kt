package com.mewz.themoviebooking.data.vos.cinema

import com.google.gson.annotations.SerializedName

data class FacilitiesVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("img")
    val img: String?
)
