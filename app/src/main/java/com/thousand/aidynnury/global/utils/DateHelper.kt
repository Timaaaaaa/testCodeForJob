package com.thousand.aidynnury.global.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun convertOneFormatToAnother(
        dateStr: String?,
        parserFormat: String,
        formatterFormat: String
    ): String {

        dateStr?.apply {
            val parser = SimpleDateFormat(parserFormat, Locale.forLanguageTag("kk"))
            val formatter = SimpleDateFormat(formatterFormat, Locale.forLanguageTag("kk"))

            return formatter.format(parser.parse(dateStr))
        }
        return ""
    }

}