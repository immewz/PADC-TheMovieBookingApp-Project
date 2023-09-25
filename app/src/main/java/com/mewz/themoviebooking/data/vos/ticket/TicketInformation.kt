package com.mewz.themoviebooking.data.vos.ticket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutTicketVO
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.persistance.typeconverters.CheckoutTicketTypeConverter
import com.mewz.themoviebooking.persistance.typeconverters.SnackListTypeConverter
import java.io.Serializable

@Entity("ticket_table")
@TypeConverters(
    CheckoutTicketTypeConverter::class,
    SnackListTypeConverter::class
)
data class TicketInformation(

    @ColumnInfo("ticket_checkout")
    val ticketCheckout: CheckoutTicketVO?,

    @ColumnInfo("snack_list")
    val snackList: List<SnackVO>?,

    @ColumnInfo("movie_name")
    val movieName: String?,

    @ColumnInfo("movie_poster")
    val moviePoster: String?,

    @ColumnInfo("cinema_name")
    val cinemaName: String?,

    @ColumnInfo("address")
    val address: String?

): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
