package dev.afalabarce.kmm.jetpackcompose.utilities

import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter

actual class MPDecimalFormatSymbols {
    private val decimalSymbols = NSNumberFormatter().apply { locale = NSLocale.new()!! }

    actual val decimalSeparator: Char
        get() = decimalSymbols.decimalSeparator.first()
    actual val groupingSeparator: Char
        get() = decimalSymbols.groupingSeparator.first()
    actual val zeroDigit: Char
        get() = decimalSymbols.zeroSymbol?.first() ?: '0'
    actual val perMill: String
        get() = decimalSymbols.perMillSymbol
    actual val percent: Char
        get() = '%'
    actual val digit: Char
        get() = '0'
    actual val patternSeparator: Char
        get() = ';'
    actual val infinity: String
        get() = "∞"
    actual val naN: String
        get() = "NaN"
    actual val minusSign: Char
        get() = decimalSymbols.minusSign.first()
    actual val currencySymbol: String
        get() = decimalSymbols.currencySymbol
    actual val internationalCurrencySymbol: String
        get() = decimalSymbols.internationalCurrencySymbol
    actual val monetaryDecimalSeparator: Char
        get() = decimalSymbols.currencyDecimalSeparator.first()
    actual val monetaryGroupingSeparator: Char
        get() = decimalSymbols.currencyGroupingSeparator.first()
    actual val exponentialSeparator: String
        get() = decimalSymbols.exponentSymbol

    actual companion object {
        actual fun getInstance(): MPDecimalFormatSymbols = MPDecimalFormatSymbols()
    }
}