package com.mewz.mymoviebookingapp.persistance.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.mymoviebookingapp.data.vos.movie.CastVO

class MovieCastTypeConverter {
    @TypeConverter
    fun toString(cast: List<CastVO>?): String{
        return Gson().toJson(cast)
    }

    @TypeConverter
    fun toCastVO(jsonString: String): List<CastVO>?{
        val castType = object : TypeToken<List<CastVO>?>(){}.type
        return Gson().fromJson(jsonString,castType)
    }
}