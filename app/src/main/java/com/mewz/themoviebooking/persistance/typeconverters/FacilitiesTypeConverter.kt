package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.themoviebooking.data.vos.cinema.FacilitiesVO

class FacilitiesTypeConverter{

    @TypeConverter
    fun toString(facilitiesList: List<FacilitiesVO>?): String{
        return Gson().toJson(facilitiesList)
    }

    @TypeConverter
    fun toFacilitiesVO(jsonString: String): List<FacilitiesVO>? {
        val facilitiesType = object : TypeToken<List<FacilitiesVO>?>(){}.type
        return Gson().fromJson(jsonString, facilitiesType)
    }
}
