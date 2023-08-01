package com.mewz.mymoviebookingapp.data.vos.movie.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutSnack(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("quantity")
    val quantity: Int?
): java.io.Serializable
