package com.c14220203.belajarroomdatabase.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DataHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss",
                Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }
}