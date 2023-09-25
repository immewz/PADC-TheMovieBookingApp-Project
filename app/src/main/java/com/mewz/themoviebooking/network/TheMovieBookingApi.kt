package com.mewz.themoviebooking.network

import com.mewz.themoviebooking.data.vos.confirmation.CheckoutBody
import com.mewz.themoviebooking.network.responses.BannerListResponse
import com.mewz.themoviebooking.network.responses.CheckoutTicketResponse
import com.mewz.themoviebooking.network.responses.CinemaAndTimeslotListResponse
import com.mewz.themoviebooking.network.responses.CinemaListResponse
import com.mewz.themoviebooking.network.responses.CitiesListResponse
import com.mewz.themoviebooking.network.responses.ConfigListResponse
import com.mewz.themoviebooking.network.responses.LogoutResponse
import com.mewz.themoviebooking.network.responses.MovieDetailResponse
import com.mewz.themoviebooking.network.responses.MovieListResponse
import com.mewz.themoviebooking.network.responses.OtpResponse
import com.mewz.themoviebooking.network.responses.PaymentTypeListResponse
import com.mewz.themoviebooking.network.responses.SeatListResponse
import com.mewz.themoviebooking.network.responses.SnackCategoryListResponse
import com.mewz.themoviebooking.network.responses.SnackListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieBookingApi {

    @GET(GET_CITIES)
    fun getCities(): Call<CitiesListResponse>

    @FormUrlEncoded
    @POST(POST_OTP)
    fun getOtp(@Field(FIELD_PHONE) phone: String): Call<OtpResponse>

    @FormUrlEncoded
    @POST(POST_SIGN_IN_WITH_PHONE)
    fun signInWithPhone(
        @Field(FIELD_PHONE) phone: String,
        @Field(FIELD_OTP) otp: String
    ): Call<OtpResponse>

    @GET(GET_BANNER)
    fun getBanners(): Call<BannerListResponse>

    @GET(GET_MOVIES)
    fun getNowPlayingMovies(
        @Query(PARAM_STATUS) status: String = "current"
    ): Call<MovieListResponse>

    @GET(GET_MOVIES)
    fun getComingSoonMovies(
        @Query(PARAM_STATUS) status: String = "comingsoon"
    ): Call<MovieListResponse>

    @GET("$GET_MOVIES_DETAIL/{movie_id}")
    fun getMovieDetail(
        @Path(PATH_MOVIE_ID) movieId: String
    ): Call<MovieDetailResponse>

    @GET(GET_CINEMA_DAY_TIMESLOTS)
    fun getCinemaAndShowtimeByDate(
        @Header(HEADER_AUTH) authorization: String,
        @Query(PARAM_DATE) date: String
    ): Call<CinemaAndTimeslotListResponse>

    @GET(GET_CINEMA_CONFIG)
    fun getConfig(): Call<ConfigListResponse>

    @GET(GET_CINEMA_INFO)
    fun getCinema(
        @Query(PARAM_LATEST_TIME) latest_time: String = "2022-09-17 00:23:04"
    ): Call<CinemaListResponse>

    @GET(GET_SEATING_PLAN_BY_SHOW_TIME)
    fun getSeatingPlanByShowTime(
        @Header(HEADER_AUTH) authorization: String,
        @Query(PARAM_CINEMA_DAY_TIMESLOT_ID) timeslotId: String,
        @Query(PARAM_BOOKING_DATE) bookingDate: String
    ): Call<SeatListResponse>

    @GET(GET_SNACK_CATEGORY)
    fun getSnackCategory(
        @Header(HEADER_AUTH) authorization: String
    ): Call<SnackCategoryListResponse>

    @GET(GET_SNACK)
    fun getSnack(
        @Header(HEADER_AUTH) authorization: String,
        @Query(PARAM_CATEGORY_ID) categoryId: String
    ): Call<SnackListResponse>

    @GET(GET_PAYMENT_TYPE)
    fun getPaymentType(
        @Header(HEADER_AUTH) authorization: String
    ): Call<PaymentTypeListResponse>

    @POST(POST_CHECKOUT)
    fun getCheckoutTicket(
        @Header(HEADER_AUTH) authorization: String,
        @Body checkoutTicket: CheckoutBody
    ): Call<CheckoutTicketResponse>

    @POST(POST_LOGOUT)
    fun logout(
        @Header(HEADER_AUTH) authorization: String,
    ): Call<LogoutResponse>

}