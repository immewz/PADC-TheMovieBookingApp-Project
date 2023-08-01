package com.mewz.mymoviebookingapp.network.dataagents

import android.util.Log
import com.mewz.mymoviebookingapp.data.vos.cinema.CinemaInfoVO
import com.mewz.mymoviebookingapp.data.vos.login.CitiesVO
import com.mewz.mymoviebookingapp.data.vos.movie.BannerVO
import com.mewz.mymoviebookingapp.data.vos.movie.MovieVO
import com.mewz.mymoviebookingapp.data.vos.movie.PaymentVO
import com.mewz.mymoviebookingapp.data.vos.movie.SeatVO
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutBody
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackCategoryVO
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.network.TheMovieBookingApi
import com.mewz.mymoviebookingapp.network.responses.cinema.CinemaInfoResponse
import com.mewz.mymoviebookingapp.network.responses.login.CitiesResponse
import com.mewz.mymoviebookingapp.network.responses.login.OtpResponse
import com.mewz.mymoviebookingapp.network.responses.movie.*
import com.mewz.mymoviebookingapp.network.responses.movie.cinema.CinemaConfigResponse
import com.mewz.mymoviebookingapp.network.responses.movie.cinema.CinemaTimeslotResponse
import com.mewz.mymoviebookingapp.network.responses.movie.snack.SnackCategoryResponse
import com.mewz.mymoviebookingapp.network.responses.movie.snack.SnackListResponse
import com.mewz.mymoviebookingapp.network.responses.profile.LogoutResponse
import com.mewz.mymoviebookingapp.network.utiles.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

object RetrofitDataAgentImpl: MovieBookingDataAgent {

    private var mTheMovieBookingApi: TheMovieBookingApi? = null
    private var interceptor = HttpLoggingInterceptor()

    init {

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15,TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieBookingApi = retrofit.create(TheMovieBookingApi::class.java)
    }


    override fun getOTP(
        phone: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getOTP(phone)
            ?.enqueue(object: Callback<OtpResponse>{
                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun checkOTP(
        phone: String,
        otp: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.checkOTP(phone,otp)
            ?.enqueue(object: Callback<OtpResponse>{
                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                    if(response.isSuccessful){
                        response.body()?.let{
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getCities(onSuccess: (List<CitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getCities()
            ?.enqueue(object: Callback<CitiesResponse>{
                override fun onResponse(
                    call: Call<CitiesResponse>,
                    response: Response<CitiesResponse>
                ) {
                    if (response.isSuccessful){
                        val cities = response.body()?.data ?: listOf()
                        onSuccess(cities)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getBanner(onSuccess: (List<BannerVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getBanner()
            ?.enqueue(object : Callback<BannerResponse>{
                override fun onResponse(
                    call: Call<BannerResponse>,
                    response: Response<BannerResponse>
                ) {
                    if(response.isSuccessful){
                        val banner = response.body()?.data ?: listOf()
                        onSuccess(banner)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getNowPlayingMovies()
            ?.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if(response.isSuccessful){
                        val movieList = response.body()?.data ?: listOf()
                        onSuccess(movieList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getComingSoonMovies()
            ?.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if(response.isSuccessful){
                        val movieList = response.body()?.data ?: listOf()
                        onSuccess(movieList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getMovieDetailById(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getMovieDetailById(movieId)
            ?.enqueue(object: Callback<MovieDetailResponse>{
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if(response.isSuccessful){
                        response.body()?.data?.let {
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getCinemaAndTimeslot(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCinemaTimeslot(authorization,date)
            ?.enqueue(object : Callback<CinemaTimeslotResponse>{
                override fun onResponse(
                    call: Call<CinemaTimeslotResponse>,
                    response: Response<CinemaTimeslotResponse>
                ) {
                    if(response.isSuccessful){
                        val cinemaList = response.body()?.data ?: listOf()
                        onSuccess(cinemaList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CinemaTimeslotResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getCinemaConfig(
        onSuccess: (List<CinemaConfigVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCinemaConfig()
            ?.enqueue(object : Callback<CinemaConfigResponse>{
                override fun onResponse(
                    call: Call<CinemaConfigResponse>,
                    response: Response<CinemaConfigResponse>
                ) {
                    if (response.isSuccessful){
                        val configList = response.body()?.data ?: listOf()
                        Log.d("Retrofit","data.${configList}")
                        onSuccess(configList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CinemaConfigResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getCinemaInfo(
        onSuccess: (List<CinemaInfoVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCinemaInfo()
            ?.enqueue(object : Callback<CinemaInfoResponse>{
                override fun onResponse(
                    call: Call<CinemaInfoResponse>,
                    response: Response<CinemaInfoResponse>
                ) {
                    if (response.isSuccessful){
                        val cinemaList = response.body()?.data ?: listOf()
                        Log.d("Retrofit","dataCinemaInfo.$cinemaList")
                        onSuccess(cinemaList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CinemaInfoResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getSeatingPlan(
        authorization: String,
        timeslotId: Int,
        bookingDate: String,
        onSuccess: (MutableList<MutableList<SeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getSeatingPlan(authorization,timeslotId,bookingDate)
            ?.enqueue(object: Callback<SeatListResponse>{
                override fun onResponse(
                    call: Call<SeatListResponse>,
                    response: Response<SeatListResponse>
                ) {
                    if(response.isSuccessful){
                        val seatList = response.body()?.data ?: mutableListOf()
                        onSuccess(seatList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<SeatListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getSnackCategory(
        authorization: String,
        onSuccess: (List<SnackCategoryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getSnackCategory(authorization)
            ?.enqueue(object: Callback<SnackCategoryResponse>{
                override fun onResponse(
                    call: Call<SnackCategoryResponse>,
                    response: Response<SnackCategoryResponse>
                ) {
                    if(response.isSuccessful){
                        val snackCategoryList = response.body()?.data ?: listOf()
                        onSuccess(snackCategoryList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<SnackCategoryResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getSnackByCategoryId(
        authorization: String,
        categoryId: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getSnackByCategoryId(authorization,categoryId)
            ?.enqueue(object : Callback<SnackListResponse>{
                override fun onResponse(
                    call: Call<SnackListResponse>,
                    response: Response<SnackListResponse>
                ) {
                    if (response.isSuccessful){
                        val snackList = response.body()?.data ?: listOf()
                        onSuccess(snackList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<SnackListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getPaymentType(
        authorization: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getPaymentType(authorization)
            ?.enqueue(object: Callback<PaymentListResponse>{
                override fun onResponse(
                    call: Call<PaymentListResponse>,
                    response: Response<PaymentListResponse>
                ) {
                    if (response.isSuccessful){
                        val paymentList = response.body()?.data ?: listOf()
                        onSuccess(paymentList)
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<PaymentListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })

    }

    override fun getCheckoutTicket(
        authorization: String,
        checkoutTicket: CheckoutBody,
        onSuccess: (CheckoutVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCheckoutTicket(authorization, checkoutTicket)
            ?.enqueue(object: Callback<CheckoutResponse>{
                override fun onResponse(
                    call: Call<CheckoutResponse>,
                    response: Response<CheckoutResponse>
                ) {
                    if (response.isSuccessful){
                        val ticket = response.body()?.data
                        if(ticket != null){
                            onSuccess(ticket)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun logout(
        authorization: String,
        onSuccess: (LogoutResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.logout(authorization)
            ?.enqueue(object : Callback<LogoutResponse>{
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }


}