package com.mewz.mymoviebookingapp.data.vos.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.persistance.typeconverter.GenreIdsTypeConverter
import com.mewz.mymoviebookingapp.persistance.typeconverter.MovieCastTypeConverter

@Entity("movie")
@TypeConverters(
    MovieCastTypeConverter::class,
    GenreIdsTypeConverter::class
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
    var type:String?
){

}
const val NOW_PLAYING_MOVIE = "NOW_PLAYING"
const val COMING_SOON_MOVIE = "COMING_SOON"