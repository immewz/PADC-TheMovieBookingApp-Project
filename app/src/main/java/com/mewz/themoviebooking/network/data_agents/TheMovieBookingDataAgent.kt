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
import com.mewz.themoviebooking.network.responses.LogoutResponse
import com.mewz.themoviebooking.network.responses.OtpResponse

interface TheMovieBookingDataAgent {

    fun getCities(
        onSuccess: (List<CitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    )

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

    fun getCinemaAndShowtimeByDate(
        authorization: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConfig(
        onSuccess: (List<ConfigVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinema(
        onSuccess: (List<CinemaInfoVO>) -> Unit,
        onFailure: (String) -> Unit
    )

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
}