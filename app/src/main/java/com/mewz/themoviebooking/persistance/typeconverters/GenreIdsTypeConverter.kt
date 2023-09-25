package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsTypeConverter {

    @TypeConverter
    fun toString(genreIds: List<String>?): String{
        return Gson().toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIds(jsonString: String): List<String>? {
        val genreIdsType = object : TypeToken<List<String>?>(){}.type
        return Gson().fromJson(jsonString, genreIdsType)
    }
}