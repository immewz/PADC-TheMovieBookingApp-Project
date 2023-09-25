package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.themoviebooking.data.vos.snack.SnackVO

class SnackListTypeConverter{

    @TypeConverter
    fun toString(snackList: List<SnackVO>?) : String {
        return Gson().toJson(snackList)
    }

    @TypeConverter
    fun toSnackList(jsonString: String): List<SnackVO>? {
        val snackListType = object : TypeToken<List<SnackVO>?>(){}.type
        return Gson().fromJson(jsonString, snackListType)
    }
}
