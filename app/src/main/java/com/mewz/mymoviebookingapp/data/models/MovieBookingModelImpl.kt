package com.mewz.mymoviebookingapp.data.models

import android.content.Context
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
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.network.dataagents.RetrofitDataAgentImpl
import com.mewz.mymoviebookingapp.network.dataagents.MovieBookingDataAgent
import com.mewz.mymoviebookingapp.network.responses.login.OtpResponse
import com.mewz.mymoviebookingapp.network.responses.profile.LogoutResponse
import com.mewz.mymoviebookingapp.persistance.MovieBookingRoomDatabase

object MovieBookingModelImpl: MovieBookingModel {

    private val mMovieBookingDataAgent: MovieBookingDataAgent = RetrofitDataAgentImpl
    private var mMovieBookingRoomDatabase: MovieBookingRoomDatabase? = null
    private var mMovie: MovieVO? = null

    fun initMovieBookingDatabase(context: Context){
        mMovieBookingRoomDatabase = MovieBookingRoomDatabase.getDBInstance(context)
    }

    override fun insertCities(onSuccess: (List<CitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingDataAgent.getCities(
            onSuccess = {
                mMovieBookingRoomDatabase?.getDao()?.insertCities(it)
                onSuccess(it)
            }, onFailure = onFailure
        )
    }

    override fun getCities(): List<CitiesVO>? = mMovieBookingRoomDatabase?.getDao()?.getAllCities()

    override fun getOTP(
        phone: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getOTP(
            phone = phone,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun checkOTP(
        phone: String,
        otp: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.checkOTP(
            phone = phone,
            otp = otp,
            onSuccess = {
                mMovieBookingRoomDatabase?.getDao()?.insertSignInInformation(it)
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun getToken(code: Int): OtpResponse? = mMovieBookingRoomDatabase?.getDao()?.getSignInInformation(code)

    override fun getBanner(onSuccess: (List<BannerVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingDataAgent.getBanner(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getNowPlayingMovies(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getComingSoonMovies(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getMovieDetailById(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getMovieDetailById(
            movieId = movieId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getMovieByIdForTicket(movieId: String): MovieVO? = mMovie

    override fun getCinemaAndTimeslot(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCinemaAndTimeslot(
            authorization = authorization,
            date = date,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun insertCinemaConfig(
        onSuccess: (List<CinemaConfigVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCinemaConfig(
            onSuccess = {
                mMovieBookingRoomDatabase?.getDao()?.insertCinemaConfig(it)
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun getCinemaConfig(key: String): CinemaConfigVO? = mMovieBookingRoomDatabase?.getDao()?.getCinemaConfig(key)

    override fun insertCinemaInfo(
        onSuccess: (List<CinemaInfoVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCinemaInfo(
            onSuccess = {
                mMovieBookingRoomDatabase?.getDao()?.insertCinemaInfo(it)
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun getCinemaInfo(cinemaId: Int): CinemaInfoVO? = mMovieBookingRoomDatabase?.getDao()?.getCinemaInfo(cinemaId)

    override fun getSeatingPlan(
        authorization: String,
        timeslotId: Int,
        bookingDate: String,
        onSuccess: (MutableList<MutableList<SeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getSeatingPlan(
            authorization = authorization,
            timeslotId = timeslotId,
            bookingDate = bookingDate,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getSnackCategory(
        authorization: String,
        onSuccess: (List<SnackCategoryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getSnackCategory(
            authorization = authorization,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getSnackByCategoryId(
        authorization: String,
        categoryId: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getSnackByCategoryId(
            authorization = authorization,
            categoryId = categoryId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getPaymentType(
        authorization: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getPaymentType(
            authorization = authorization,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getCheckoutTicket(
        authorization: String,
        checkoutTicket: CheckoutBody,
        onSuccess: (CheckoutVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCheckoutTicket(
            authorization = authorization,
            checkoutTicket = checkoutTicket,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun logout(
        authorization: String,
        onSuccess: (LogoutResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.logout(
            authorization = authorization,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun deleteAll() {
        mMovieBookingRoomDatabase?.getDao()?.deleteAll()
    }

    // Ticket
    override fun insertTicket(ticket: TicketInformation) {
        mMovieBookingRoomDatabase?.getDao()?.insertTicket(ticket)
    }

    override fun getAllTicket(): List<TicketInformation>? = mMovieBookingRoomDatabase?.getDao()?.getAllTickets()

    override fun deleteTicket(ticketId: Int) {
        mMovieBookingRoomDatabase?.getDao()?.deleteTicket(ticketId)
    }


}