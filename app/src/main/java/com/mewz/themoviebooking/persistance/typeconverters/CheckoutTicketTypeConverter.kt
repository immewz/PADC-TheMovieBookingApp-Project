package com.mewz.themoviebooking.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutTicketVO

class CheckoutTicketTypeConverter {

    @TypeConverter
    fun toString(ticket: CheckoutTicketVO?): String {
        return Gson().toJson(ticket)
    }

    @TypeConverter
    fun toTicketInformation(jsonString: String): CheckoutTicketVO? {
        val ticketType = object : TypeToken<CheckoutTicketVO>(){}.type
        return Gson().fromJson(jsonString, ticketType)
    }
}