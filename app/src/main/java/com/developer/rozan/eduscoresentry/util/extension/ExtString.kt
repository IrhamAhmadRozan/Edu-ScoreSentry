package com.developer.rozan.eduscoresentry.util.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidPhoneNumber(): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", this)) {
        !(this.length < 6 || this.length > 15)
    } else {
        false
    }
}

fun String.trimCommaOfString(): String {
    return if (this.contains(".")) {
        this.replace(".", "")
    } else {
        this
    }
}

fun String.formatThousand(): String? {
    val value: String?
    val formatter = DecimalFormat("#,###,###")
    val symbols = DecimalFormatSymbols(Locale("id", "ID"))
    formatter.decimalFormatSymbols = symbols
    value = formatter.format(java.lang.Long.valueOf(this))
    return value
}

fun String.formatCurrency(): String? {
    val value: String?
    val formatter = DecimalFormat("#,###,###")
    val symbols = DecimalFormatSymbols(Locale("id", "ID"))
    formatter.decimalFormatSymbols = symbols
    value = formatter.format(java.lang.Double.valueOf(this))
    return "Rp $value"
}

fun String.toPhoneFormat(): String? {
    return if (this.startsWith("62", true)) {
        "+$this"
    } else {
        this
    }
}

fun String.removePhoneFormat(): String? {
    return this.replace("+", "")
}

fun String.convertToReadableDate(): String? {
    var date: Date? = null
    try {
        date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID")).parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return SimpleDateFormat("EEEE dd MMMM yyyy, HH:mm", Locale("id", "ID")).format(date!!)
}

fun String.noSpaceDate(): String? {
    return SimpleDateFormat("yyyyMMddHHmmss", Locale("id", "ID")).toString()
}

fun String.convertToReadableDateWithoutDayName(): String? {
    var date: Date? = null
    try {
        date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID")).parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID")).format(date!!)
}
