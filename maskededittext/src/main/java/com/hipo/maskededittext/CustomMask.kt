package com.hipo.maskededittext

class CustomMask(private val customMaskPattern: String, private val customReturnPattern: String) : Mask() {
    override val maskPattern: String
        get() = customMaskPattern
    override val returnPattern: String
        get() = customReturnPattern

    override fun getParsedText(maskedText: String): String? = null
    override fun isValidToParse(filteredText: String) = false
    override fun filterMaskedText(maskedText: String) = ""
}
