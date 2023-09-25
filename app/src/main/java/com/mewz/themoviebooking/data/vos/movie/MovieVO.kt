package com.mewz.themoviebooking.data.vos.movie

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.persistance.typeconverters.CastTypeConverter
import com.mewz.themoviebooking.persistance.typeconverters.GenreIdsTypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Entity("movie_table")
@TypeConverters(
    GenreIdsTypeConverter::class,
    CastTypeConverter::class
)
data class MovieVO(

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("original_title")
    @ColumnInfo("original_title")
    val originalTitle: String?,

    @SerializedName("release_date")
    @ColumnInfo("release_date")
    val releaseDate: String?,

    @SerializedName("genres")
    @ColumnInfo("genres")
    val genres: List<String>?,

    @SerializedName("overview")
    @ColumnInfo("overview")
    val overview: String?,

    @SerializedName("rating")
    @ColumnInfo("rating")
    val rating: Double?,

    @SerializedName("runtime")
    @ColumnInfo("runtime")
    val runtime: Int?,

    @SerializedName("poster_path")
    @ColumnInfo("poster_path")
    val posterPath: String?,

    @SerializedName("casts")
    @ColumnInfo("casts")
    val casts: List<CastVO>?,

    @ColumnInfo("type")
    var type: String?

){

    fun changeAverageFormat(): String {
        return "%.1f".format(rating)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeReleaseDateFormat(screen: String): String {
        val date = getReleasingDateInstance()
        val day = date.dayOfMonth.toString()
        val month = date.month.toString()

        val monthWithThreeCharacter = getMonthThreeCharacter(month)
        val dayWithSuffix = getDayWithSuffix(day.toInt())

        return if (screen == "home"){
            "$dayWithSuffix\n$monthWithThreeCharacter"
        }else{
            "$monthWithThreeCharacter $dayWithSuffix, ${date.year}"
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDayWithSuffix(day: Int): String {
        val suffixes = mapOf(1 to "st", 2 to "nd", 3 to "rd", 21 to "st", 22 to "nd", 23 to "rd", 31 to "st")
        val suffix = suffixes.getOrDefault(day % 100, "th")
        return "$day$suffix"
    }

    private fun getMonthThreeCharacter(month: String): String {
        var monthStr = String()
        for (letter in 0 ..2) {
            monthStr += month[letter]
        }
        return monthStr
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getReleasingDateInstance(): LocalDate {
        val releaseDateString = releaseDate?.replace('-','/')
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.ENGLISH)
        return LocalDate.parse(releaseDateString, dateFormatter)
    }

    fun changeRuntimeFormat(): String{
        val hour = runtime?.div(60)
        val remainingMinutes = (runtime ?: 0) % 60
        return "$hour\bhr $remainingMinutes\bmin"
    }

}

const val NOW_PLAYING_MOVIE = "NOW_PLAYING"
const val COMING_SOON_MOVIE = "COMING_SOON"
