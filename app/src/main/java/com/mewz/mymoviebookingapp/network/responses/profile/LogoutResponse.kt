package com.mewz.mymoviebookingapp.network.responses.profile

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?
)
