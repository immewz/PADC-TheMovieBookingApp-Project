package com.mewz.mymoviebookingapp.persistance.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutVO

class TicketInformationTypeConverter {
    @TypeConverter
    fun toString(ticket: CheckoutVO?) :String {
        return Gson().toJson(ticket)
    }

    @TypeConverter
    fun toTicketInformation(jsonString:String) : CheckoutVO? {
        val ticketType = object : TypeToken<CheckoutVO?>(){}.type
        return Gson().fromJson(jsonString,ticketType)
    }
}