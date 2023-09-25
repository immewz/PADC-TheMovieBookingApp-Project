package com.mewz.themoviebooking.data.vos.confirmation

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckoutTicketSnackVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("unit_price")
    val unitPrice: Int?,

    @SerializedName("quantity")
    val quantity: Int?,

    @SerializedName("total_price")
    val totalPrice: Int?

): Serializable
