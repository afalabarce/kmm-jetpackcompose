package dev.afalabarce.kmm.jetpackcompose.utilities

expect class MPDecimalFormatSymbols {

    companion object {
        fun getInstance(): MPDecimalFormatSymbols
    }

    val decimalSeparator: Char
    val groupingSeparator: Char
    val zeroDigit: Char
    val perMill: String
    val percent: Char
    val digit: Char
    val patternSeparator: Char
    val infinity: String
    val naN: String
    val minusSign: Char
    val currencySymbol: String
    val internationalCurrencySymbol: String
    val monetaryDecimalSeparator: Char
    val monetaryGroupingSeparator: Char
    val exponentialSeparator: String
}