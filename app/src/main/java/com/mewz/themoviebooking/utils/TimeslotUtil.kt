package com.mewz.themoviebooking.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.mewz.themoviebooking.utils.data.TimeslotData
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class TimeslotUtil {

    var dateList = mutableListOf<TimeslotData>()
    var dateListTimeslot = mutableListOf<String>()

    private lateinit var calendar: Calendar

    @SuppressLint("SimpleDateFormat")
    fun addTimeslotDataToList() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        calendar = Calendar.getInstance()

        var dayOfWeekOne = 0
        var monthOne = 0

        for (i in 1 ..14) {
            dayOfWeekOne = calendar.get(Calendar.DAY_OF_WEEK)
            val dayOfWeek = setUpDayOfWeek(dayOfWeekOne)

            monthOne = calendar.get(Calendar.MONTH)
            val month = setUpMonth(monthOne)

            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val latestTime = getLatestTime()
            dateList.add(TimeslotData(dayOfWeek, month, dayOfMonth.toString(), latestTime))
            calendar.add(Calendar.DATE, 1)
        }

        dateList.forEach{
            val  date = calendar.apply {
                set(Calendar.DAY_OF_WEEK, dayOfWeekOne)
                set(Calendar.MONTH, monthOne)
                set(Calendar.DAY_OF_MONTH, it.dayOfMonth.toInt())
            }.time
            val dateString = dateFormat.format(date)
            dateListTimeslot.add(dateString)
        }
    }

    private fun getLatestTime(): String {
        val currentTime = LocalTime.now()
        return "${currentTime.hour}:${currentTime.minute}:${currentTime.second}"

    }

    private fun setUpMonth(month: Int): String {
        return when (month) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            else -> "Dec"
        }
    }

    private fun setUpDayOfWeek(dayOfWeek: Int): String {
        return when(dayOfWeek) {
            1 -> "SUN"
            2 -> "MON"
            3 -> "TUE"
            4 -> "WED"
            5 -> "THU"
            6 -> "FRI"
            else -> "SAT"
        }
    }

}