package com.preach.mentaldisorder.Utils.GeneralUtils

import android.graphics.Color
import android.util.Log
import com.preach.mentaldisorder.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

open  class DateTimeUtils {
    fun changeDateTimeFormat(date: String): String {
//        val oldFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
        val oldFormat = "EEE MMM dd HH:mm:ss zzzz yyyy"
        val newFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        Log.e("timeformat", timeFormat.format(myDate))
        return timeFormat.format(myDate)

    }

    fun returnMonth(date: String): String {
        val oldFormat = "dd MMM yyyy"
        val newFormat = "MMMM"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }

    fun returnDateOfMonth(date: String): String {
        val oldFormat = "yyyyMMM-dd" //2020-11-25
        val newFormat = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }
    fun returnDateOfMonthForReview(date: String): String {
        val oldFormat = "yyyy-mm-dd hh:mm:ss" //2020-11-25
        val newFormat = "dd MMMM yyyy hh:mm:ss a"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }

    fun returnDateOfMonthForOrderHistory(date: String): String {
        val oldFormat = "yyyy-mm-dd hh:mm:ss" //2020-11-25
        val newFormat = "dd MMMM yyyy hh:mm:ss A"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }



    fun returnDateAndMonth(date: String): String {
        val oldFormat = "yyyy-MM-dd"
        val newFormat = "MMMM dd, yyyy"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }
    fun returnDateMonth(date: String): String {
        val oldFormat = "yyyy-MM-dd"
        val newFormat = "dd MMM yyyy"
        val dateFormat = SimpleDateFormat(oldFormat)
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val timeFormat = SimpleDateFormat(newFormat)
        return timeFormat.format(myDate)

    }
}