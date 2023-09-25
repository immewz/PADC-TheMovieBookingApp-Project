package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ValueAnyTypeConverter {

    @TypeConverter
    fun toString(configAnyTimeslot: Any?): String{
        return Gson().toJson(configAnyTimeslot)
    }

    @TypeConverter
    fun toTimeslotAnyColorVO(jsonString: String): Any?{
        val configTimeslotAnyType = object : TypeToken<Any?>(){}.type
        return Gson().fromJson(jsonString, configTimeslotAnyType)
    }
}