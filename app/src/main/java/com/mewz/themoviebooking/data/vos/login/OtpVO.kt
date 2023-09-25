package com.mewz.themoviebooking.data.vos.login

import com.google.gson.annotations.SerializedName

data class OtpVO(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("total_expense")
    val totalExpense: Int?,

    @SerializedName("profile_image")
    val profileImage: String?,
)
