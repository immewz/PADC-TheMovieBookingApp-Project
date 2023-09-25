package com.mewz.themoviebooking.data.models

import android.content.Context
import com.mewz.themoviebooking.data.vos.cinema.CinemaInfoVO
import com.mewz.themoviebooking.data.vos.cinema.CinemaVO
import com.mewz.themoviebooking.data.vos.cinema.ConfigVO
import com.mewz.themoviebooking.data.vos.cinema.SeatVO
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutBody
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutTicketVO
import com.mewz.themoviebooking.data.vos.confirmation.PaymentVO
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.data.vos.movie.COMING_SOON_MOVIE
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.data.vos.movie.NOW_PLAYING_MOVIE
import com.mewz.themoviebooking.data.vos.snack.SnackCategoryVO
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.persistance.TheMovieBookingRoomDatabase
import com.mewz.themoviebooking.network.data_agents.RetrofitDataAgentImpl
import com.mewz.themoviebooking.network.data_agents.TheMovieBookingDataAgent
import com.mewz.themoviebooking.network.remote_config.FirebaseRemoteConfigManager
import com.mewz.themoviebooking.network.responses.LogoutResponse
import com.mewz.themoviebooking.network.responses.OtpResponse

object TheMovieBookingModelImpl: TheMovieBookingModel {

    private var mTheMovieBookingDataAgent: TheMovieBookingDataAgent = RetrofitDataAgentImpl
    private var mTheMovieBookingRoomDatabase: TheMovieBookingRoomDatabase? = null
    private var mMovie: MovieVO? = null

    override var mFirebaseRemoteConfigManager: FirebaseRemoteConfigManager = FirebaseRemoteConfigManager
    fun initTheMovieBookingDatabase(context: Context) {
        mTheMovieBookingRoomDatabase = TheMovieBookingRoomDatabase.getDBInstance(context)
    }

    override fun insertCities(onSuccess: (List<CitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingDataAgent.getCities(
            onSuccess = {
                mTheMovieBookingRoomDatabase?.getDao()?.insertCities(it)
                onSuccess(it)
            }, onFailure )
    }

    override fun getCities() = mTheMovieBookingRoomDatabase?.getDao()?.getAllCities()

    override fun getOtp(
        phone: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getOtp(phone, onSuccess, onFailure)
    }

    override fun signInWithPhone(
        phone: String,
        otp: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.signInWithPhone(phone, otp,
            onSuccess = {
                mTheMovieBookingRoomDatabase?.getDao()?.insertSignInInformation(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getToken() = mTheMovieBookingRoomDatabase?.getDao()?.getToken()

    override fun getBanners(onSuccess: (List<BannerVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(mTheMovieBookingRoomDatabase?.getDao()?.getBanners() ?: listOf())
        mTheMovieBookingDataAgent.getBanners(
            onSuccess = {
                mTheMovieBookingRoomDatabase?.getDao()?.insertBanner(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getNowPlayingMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mTheMovieBookingRoomDatabase?.getDao()?.getMoviesByType(NOW_PLAYING_MOVIE) ?: listOf())
        mTheMovieBookingDataAgent.getNowPlayingMovie(
            onSuccess = {
                it.forEach{ movie ->
                    movie.type = NOW_PLAYING_MOVIE
                }
                mTheMovieBookingRoomDatabase?.getDao()?.insertMovies(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getComingSoonMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mTheMovieBookingRoomDatabase?.getDao()?.getMoviesByType(COMING_SOON_MOVIE) ?: listOf())
        mTheMovieBookingDataAgent.getNowPlayingMovie(
            onSuccess = {
                it.forEach{ movie ->
                    movie.type = COMING_SOON_MOVIE
                }
                mTheMovieBookingRoomDatabase?.getDao()?.insertMovies(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getMovieDetailById(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingRoomDatabase?.getDao()?.getMovieById(movieId.toInt())?.let { onSuccess(it) }

        mTheMovieBookingDataAgent.getMovieDetailById(movieId,
            onSuccess = {movie ->
                mMovie = movie
                val db = mTheMovieBookingRoomDatabase?.getDao()?.getMovieById(movieId.toInt())
                movie.type = db?.type
                mTheMovieBookingRoomDatabase?.getDao()?.insertSingleMovie(movie)
                onSuccess(movie)
            }, onFailure)
    }

    override fun getMovieForTicketById(movieId: String): MovieVO? = mMovie

    override fun getCinemaAndShowtimeByDate(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getCinemaAndShowtimeByDate(authorization, date, onSuccess, onFailure)
    }

    override fun insertConfig(onSuccess: (List<ConfigVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingDataAgent.getConfig(
            onSuccess = {
                mTheMovieBookingRoomDatabase?.getDao()?.insertCinemaConfig(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getConfig(key: String) = mTheMovieBookingRoomDatabase?.getDao()?.getCinemaConfig(key)

    override fun insertCinema(
        onSuccess: (List<CinemaInfoVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getCinema(
            onSuccess = {
                mTheMovieBookingRoomDatabase?.getDao()?.insertCinemaInfo(it)
                onSuccess(it)
            }, onFailure)
    }

    override fun getCinema(cinemaId: Int) = mTheMovieBookingRoomDatabase?.getDao()?.getCinemaInfo(cinemaId)

    override fun getSeatingPlanByShowTime(
        authorization: String,
        timeslotId: String,
        bookingDate: String,
        onSuccess: (MutableList<MutableList<SeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getSeatingPlanByShowTime(authorization, timeslotId, bookingDate, onSuccess, onFailure)
    }

    override fun getSnackCategory(
        authorization: String,
        onSuccess: (List<SnackCategoryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getSnackCategory(authorization, onSuccess, onFailure)
    }

    override fun getSnack(
        authorization: String,
        categoryId: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getSnack(authorization, categoryId, onSuccess, onFailure)
    }

    override fun getPaymentType(
        authorization: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getPaymentType(authorization, onSuccess, onFailure)
    }

    override fun getCheckoutTicket(
        authorization: String,
        checkoutTicket: CheckoutBody,
        onSuccess: (CheckoutTicketVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.getCheckoutTicket(authorization, checkoutTicket, onSuccess, onFailure)
    }

    override fun logout(
        authorization: String,
        onSuccess: (LogoutResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.logout(authorization, onSuccess, onFailure)
    }

    override fun deleteAllEntities() {
        mTheMovieBookingRoomDatabase?.getDao()?.deleteAllEntities()
    }

    override fun insertTicket(ticketInformation: TicketInformation) {
        mTheMovieBookingRoomDatabase?.getDao()?.insertTicket(ticketInformation)
    }

    override fun getAllTickets(): List<TicketInformation>? {
        return mTheMovieBookingRoomDatabase?.getDao()?.getAllTicket()
    }

    override fun deleteTicket(ticketId: Int) {
        mTheMovieBookingRoomDatabase?.getDao()?.deleteTicket(ticketId)
    }

    override fun setUpRemoteConfigWithDefaultValues() {
        mFirebaseRemoteConfigManager.setUpRemoteConfigWithDefaultValue()
    }

    override fun fetchRemoteConfigs() {
        mFirebaseRemoteConfigManager.fetchRemoteConfigs()
    }

    override fun getLoginSentence(): String {
        return mFirebaseRemoteConfigManager.getLoginSentence()
    }


}