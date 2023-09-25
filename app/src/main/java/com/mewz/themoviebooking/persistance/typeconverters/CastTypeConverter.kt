package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.themoviebooking.data.vos.movie.CastVO

class CastTypeConverter {

    @TypeConverter
    fun toString(casts: List<CastVO>?): String{
        return Gson().toJson(casts)
    }

    @TypeConverter
    fun toCastVO(jsonString: String): List<CastVO>? {
        val castType = object : TypeToken<List<CastVO>?>(){}.type
        return Gson().fromJson(jsonString, castType)
    }
}