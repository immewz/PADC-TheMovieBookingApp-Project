package com.mewz.themoviebooking.data.models

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
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.network.remote_config.FirebaseRemoteConfigManager
import com.mewz.themoviebooking.network.responses.LogoutResponse
import com.mewz.themoviebooking.network.responses.OtpResponse

interface TheMovieBookingModel {

    var mFirebaseRemoteConfigManager : FirebaseRemoteConfigManager

    fun insertCities(
        onSuccess: (List<CitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCities(): List<CitiesVO>?

    fun getOtp(
        phone: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    )

    fun signInWithPhone(
        phone: String,
        otp: String,
        onSuccess: (OtpResponse) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getToken(): OtpResponse?

    fun getBanners(
        onSuccess: (List<BannerVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getNowPlayingMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getComingSoonMovie(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetailById(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieForTicketById(
        movieId: String
    ): MovieVO?

    fun getCinemaAndShowtimeByDate(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun insertConfig(
        onSuccess: (List<ConfigVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConfig(key: String): ConfigVO?

    fun insertCinema(
        onSuccess: (List<CinemaInfoVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinema(
        cinemaId: Int
    ): CinemaInfoVO?

    fun getSeatingPlanByShowTime(
        authorization: String,
        timeslotId: String,
        bookingDate: String,
        onSuccess: (MutableList<MutableList<SeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackCategory(
        authorization: String,
        onSuccess: (List<SnackCategoryVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnack(
        authorization: String,
        categoryId: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentType(
        authorization: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCheckoutTicket(
        authorization: String,
        checkoutTicket: CheckoutBody,
        onSuccess: (CheckoutTicketVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun logout(
        authorization: String,
        onSuccess: (LogoutResponse) -> Unit,
        onFailure: (String) -> Unit
    )

    fun deleteAllEntities()

    fun insertTicket(ticketInformation: TicketInformation)

    fun getAllTickets(): List<TicketInformation>?

    fun deleteTicket(ticketId: Int)

    fun setUpRemoteConfigWithDefaultValues()
    fun fetchRemoteConfigs()
    fun getLoginSentence(): String
}