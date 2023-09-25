package com.mewz.themoviebooking.data.vos.snack

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SnackVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("category_id")
    val categoryId: Int?,

    @SerializedName("image")
    val image: String?,

    var quantity: Int = 0
): Serializable
