package com.mewz.themoviebooking.network.data_agents

import com.mewz.themoviebooking.data.vos.cinema.CinemaInfoVO
import com.mewz.themoviebooking.data.vos.cinema.CinemaVO
import com.mewz.themoviebooking.data.vos.cinema.ConfigVO
import com.mewz.themoviebooking.data.vos.cinema.SeatVO
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutBody
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutTicketVO
import com.mewz.themoviebooking.data.vos.confirmation.PaymentVO
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.data.vos.snack.SnackCategoryVO
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.network.BASE_URL
import com.mewz.themoviebooking.network.TheMovieBookingApi
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDataAgentImpl: TheMovieBookingDataAgent {

    private var mTheMovieBookingApi: TheMovieBookingApi? = null

    init {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieBookingApi = retrofit.create(TheMovieBookingApi::class.java)
    }

    override fun getCities(onSuccess: (List<CitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getCities()
            ?.enqueue(object : Callback<CitiesListResponse>{
                override fun onResponse(
                    call: Call<CitiesListResponse>,
                    response: Response<CitiesListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<CitiesListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }


    override fun getOtp(
        phone: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getOtp(phone)
            ?.enqueue(object : Callback<OtpResponse>{
                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                    if (response.isSuccessful){
                        response.body()?.let { onSuccess(it) }
                    }else{
                        onFailure(response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun signInWithPhone(
        phone: String,
        otp: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.signInWithPhone(phone, otp)
            ?.enqueue(object : Callback<OtpResponse>{
                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                    if (response.isSuccessful){
                        response.body()?.let{onSuccess(it)}
                    }else{
                        onFailure(response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getBanners(onSuccess: (List<BannerVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getBanners()
            ?.enqueue(object : Callback<BannerListResponse>{
                override fun onResponse(
                    call: Call<BannerListResponse>,
                    response: Response<BannerListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BannerListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getNowPlayingMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getNowPlayingMovies()
            ?.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getComingSoonMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getComingSoonMovies()
            ?.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.body()?.message.toString())
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
        mTheMovieBookingApi?.getMovieDetail(movieId)
            ?.enqueue(object : Callback<MovieDetailResponse>{
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                   if (response.isSuccessful){
                       val movie = response.body()?.data
                       movie?.let { onSuccess(it) }
                   }else{
                       onFailure(response.body()?.message.toString())
                   }
                }
                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getCinemaAndShowtimeByDate(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCinemaAndShowtimeByDate(authorization, date)
            ?.enqueue(object : Callback<CinemaAndTimeslotListResponse>{
                override fun onResponse(
                    call: Call<CinemaAndTimeslotListResponse>,
                    response: Response<CinemaAndTimeslotListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<CinemaAndTimeslotListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getConfig(onSuccess: (List<ConfigVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getConfig()
            ?.enqueue(object : Callback<ConfigListResponse>{
                override fun onResponse(
                    call: Call<ConfigListResponse>,
                    response: Response<ConfigListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<ConfigListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getCinema(onSuccess: (List<CinemaInfoVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getCinema()
            ?.enqueue(object : Callback<CinemaListResponse>{
                override fun onResponse(
                    call: Call<CinemaListResponse>,
                    response: Response<CinemaListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<CinemaListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getSeatingPlanByShowTime(
        authorization: String,
        timeslotId: String,
        bookingDate: String,
        onSuccess: (MutableList<MutableList<SeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getSeatingPlanByShowTime(authorization, timeslotId, bookingDate)
            ?.enqueue(object: Callback<SeatListResponse>{
                override fun onResponse(
                    call: Call<SeatListResponse>,
                    response: Response<SeatListResponse>
                ) {
                    if (response.isSuccessful){
                        val mutableList = response.body()?.data ?: mutableListOf()
                        onSuccess(mutableList)
                    }else{
                        onFailure(response.message().toString())
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
            ?.enqueue(object : Callback<SnackCategoryListResponse>{
                override fun onResponse(
                    call: Call<SnackCategoryListResponse>,
                    response: Response<SnackCategoryListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<SnackCategoryListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getSnack(
        authorization: String,
        categoryId: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getSnack(authorization, categoryId)
            ?.enqueue(object : Callback<SnackListResponse>{
                override fun onResponse(
                    call: Call<SnackListResponse>,
                    response: Response<SnackListResponse>
                ) {
                    if(response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
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
            ?.enqueue(object : Callback<PaymentTypeListResponse>{
                override fun onResponse(
                    call: Call<PaymentTypeListResponse>,
                    response: Response<PaymentTypeListResponse>
                ) {
                    if (response.isSuccessful){
                        val list = response.body()?.data ?: listOf()
                        onSuccess(list)
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<PaymentTypeListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getCheckoutTicket(
        authorization: String,
        checkoutTicket: CheckoutBody,
        onSuccess: (CheckoutTicketVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getCheckoutTicket(authorization, checkoutTicket)
            ?.enqueue(object : Callback<CheckoutTicketResponse>{
                override fun onResponse(
                    call: Call<CheckoutTicketResponse>,
                    response: Response<CheckoutTicketResponse>
                ) {
                    if (response.isSuccessful){
                        val ticket = response.body()?.data
                        if (ticket != null){
                            onSuccess(ticket)
                        }
                    }else{
                        onFailure(response.message().toString())
                    }
                }

                override fun onFailure(call: Call<CheckoutTicketResponse>, t: Throwable) {
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
                        response.body()?.let{
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message().toString())
                    }
                }
                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

}