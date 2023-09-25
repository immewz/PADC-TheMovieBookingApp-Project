package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.themoviebooking.data.vos.login.OtpVO

class OtpTypeConverter {

    @TypeConverter
    fun toString(otp: OtpVO?) : String {
        return Gson().toJson(otp)
    }

    @TypeConverter
    fun toOtpVO(jsonString: String): OtpVO? {
        val otpType = object : TypeToken<OtpVO?>(){}.type
        return Gson().fromJson(jsonString, otpType)
    }
}