package dev.afalabarce.kmm.jetpackcompose.utilities

import java.text.DecimalFormatSymbols
import java.util.*

actual class MPDecimalFormatSymbols {
    private val decimalSymbols = DecimalFormatSymbols(Locale.getDefault())

    actual val decimalSeparator: Char
        get() = decimalSymbols.decimalSeparator
    actual val groupingSeparator: Char
        get() = decimalSymbols.groupingSeparator
    actual val zeroDigit: Char
        get() = decimalSymbols.zeroDigit
    actual val perMill: String
        get() = decimalSymbols.perMill.toString()
    actual val percent: Char
        get() = decimalSymbols.percent
    actual val digit: Char
        get() = decimalSymbols.digit
    actual val patternSeparator: Char
        get() = decimalSymbols.patternSeparator
    actual val infinity: String
        get() = decimalSymbols.infinity
    actual val naN: String
        get() = decimalSymbols.naN
    actual val minusSign: Char
        get() = decimalSymbols.minusSign
    actual val currencySymbol: String
        get() = decimalSymbols.currencySymbol
    actual val internationalCurrencySymbol: String
        get() = decimalSymbols.internationalCurrencySymbol
    actual val monetaryDecimalSeparator: Char
        get() = decimalSymbols.monetaryDecimalSeparator
    actual val monetaryGroupingSeparator: Char
        get() = decimalSymbols.monetaryGroupingSeparator
    actual val exponentialSeparator: String
        get() = decimalSymbols.exponentSeparator

    actual companion object {
        actual fun getInstance(): MPDecimalFormatSymbols = MPDecimalFormatSymbols()
    }
}