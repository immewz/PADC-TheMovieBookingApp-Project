package com.mewz.mymoviebookingapp.data.vos.ticket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutVO
import com.mewz.mymoviebookingapp.persistance.typeconverter.TicketInformationTypeConverter

@Entity("ticket")
@TypeConverters(
    TicketInformationTypeConverter::class
)
data class TicketInformation(
    @ColumnInfo("ticket_checkout")
    val ticketCheckout: CheckoutVO?,

    @ColumnInfo("address")
    val address: String?,

    @ColumnInfo("movie_name")
    val movieName: String?,

    @ColumnInfo("movie_poster")
    val moviePoster: String?
): java.io.Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
