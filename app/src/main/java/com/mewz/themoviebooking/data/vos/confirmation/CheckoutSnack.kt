package com.mewz.themoviebooking.data.vos.confirmation

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckoutSnack(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("quantity")
    val quantity: Int?
): Serializable
