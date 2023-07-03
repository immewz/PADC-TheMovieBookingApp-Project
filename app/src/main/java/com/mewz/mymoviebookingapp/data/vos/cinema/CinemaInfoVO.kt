package com.mewz.mymoviebookingapp.data.vos.cinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.persistance.typeconverter.FacilitiesTypeConverter
import com.mewz.mymoviebookingapp.persistance.typeconverter.SafetyTypeConverter

@Entity("cinema_info")
@TypeConverters(
    FacilitiesTypeConverter::class,
    SafetyTypeConverter::class
)
data class CinemaInfoVO(
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("name")
    @ColumnInfo("name")
    val name: String?,

    @SerializedName("phone")
    @ColumnInfo("phone")
    val phone: String?,

    @SerializedName("email")
    @ColumnInfo("email")
    val email: String?,

    @SerializedName("address")
    @ColumnInfo("address")
    val address: String?,

    @SerializedName("promo_vdo_url")
    @ColumnInfo("promo_vdo_url")
    val promoVdoUrl: String?,

    @SerializedName("facilities")
    @ColumnInfo("facilities")
    val facilities: List<FacilitiesVO>?,

    @SerializedName("safety")
    @ColumnInfo("safety")
    val safety: List<String>?
)
