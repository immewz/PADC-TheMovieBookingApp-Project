package com.mewz.mymoviebookingapp.persistance.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ValueTypeConverter {
    @TypeConverter
    fun toString(configTimeslot: Any?): String{
        return Gson().toJson(configTimeslot)
    }

    @TypeConverter
    fun toCinemaTimeslotVO(jsonString: String): Any? {
        val cinemaTimeslotType = object : TypeToken<Any?>(){}.type
        return Gson().fromJson(jsonString,cinemaTimeslotType)
    }
}