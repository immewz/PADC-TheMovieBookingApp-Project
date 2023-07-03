package com.mewz.mymoviebookingapp.network.utiles

const val BASE_URL = "https://tmba.padc.com.mm"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w400"

// Post
const val API_POST_OTP = "/api/v2/get-otp"
const val API_POST_SIGN_IN_WITH_PHONE = "/api/v2/check-otp"
const val API_POST_CHECK_OUT = "/api/v2/checkout"
const val API_POST_LOG_OUT = "/api/v1/logout"

// Get
const val API_GET_CITIES = "/api/v2/cities"
const val API_GET_BANNER = "/api/v2/banners"
const val API_GET_NOW_PLAYING = "/api/v2/movies"
const val API_GET_COMING_SOON = "/api/v2/movies"
const val API_GET_MOVIE_DETAIL = "/api/v1/movies"
const val API_GET_CINEMA_TIMESLOTS = "/api/v2/cinema-day-timeslots"
const val API_GET_CINEMA_CONFIG = "/api/v2/configurations"
const val API_GET_CINEMA_INFO = "/api/v2/cinemas"
const val API_GET_SEATING_PLAN = "/api/v2/seat-plan"
const val API_GET_SNACK_CATEGORY = "/api/v2/snack-categories"
const val API_GET_SNACK = "/api/v2/snacks"
const val API_GET_PAYMENT = "/api/v2/payment-types"

// Field
const val FIELD_PHONE = "phone"
const val FIELD_OTP = "otp"

// Params
const val PARAM_STATUS = "status"
const val PARAM_LATEST_TIME = "latest_time"
const val PARAM_DATE = "date"
const val PARAM_CINEMA_DAY_TIMESLOT_ID = "cinema_day_timeslot_id"
const val PARAM_BOOKING_DATE = "booking_date"
const val PARAM_SNACK_CATEGORY = "category_id"

// value
const val MOVIE_API_NOW_PLAYING_KEY = "current"
const val MOVIE_API_COMING_SOON_KEY = "comingsoon"

// Header
const val AUTHORIZATION = "Authorization"


