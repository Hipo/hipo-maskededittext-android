package com.hipo.maskededittext

class StaticTextMask(private val customMaskPatter: String) : Mask() {
    override val maskPattern: String
        get() = customMaskPatter
    override val returnPattern: String
        get() = ""

    override fun getParsedText(maskedText: String): String? {
        val filteredText = filterMaskedText(maskedText)
        return if (isValidToParse(maskedText)) filteredText else null
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length > maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.substring(maskPattern.length, maskedText.length)
    }
}
