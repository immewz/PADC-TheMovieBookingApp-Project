package com.mewz.mymoviebookingapp.persistance.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.mymoviebookingapp.data.vos.cinema.FacilitiesVO

class FacilitiesTypeConverter {
    @TypeConverter
    fun toString(facilities: List<FacilitiesVO>?): String{
        return Gson().toJson(facilities)
    }

    @TypeConverter
    fun toFacilitiesVO(jsonString: String): List<FacilitiesVO>?{
        val facilitiesType = object : TypeToken<List<FacilitiesVO>?>(){}.type
        return Gson().fromJson(jsonString,facilitiesType)
    }
}