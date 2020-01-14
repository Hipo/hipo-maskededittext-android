package com.hipo.maskededittext

class CurrencyMask : Mask() {
    override val maskPattern: String
        get() = "$########"
    override val returnPattern: String
        get() = ""

    override fun getParsedText(maskedText: String): String? {
        return if (isValidToParse(maskedText)) filterMaskedText(maskedText) else null
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length > 1
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.substring(1, maskedText.length)
    }
}
