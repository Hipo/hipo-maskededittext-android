package com.hipo.maskededittext

class PhoneMask : Mask() {
    override val maskPattern: String
        get() = "(###) ###-####"
    override val returnPattern: String
        get() = "##########"

    override fun getParsedText(maskedText: String): String? {
        val filteredText = filterMaskedText(maskedText)
        return if (isValidToParse(maskedText)) filteredText else null
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length == maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.filter { it.isDigit() }
    }
}
