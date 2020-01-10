package com.hipo.maskededittext

import com.hipo.maskededittext.Masker.Companion.POUND

class CustomMask(private val customMaskPattern: String, private val customReturnPattern: String) :
    Mask() {
    override val maskPattern: String
        get() = customMaskPattern
    override val returnPattern: String
        get() = customReturnPattern

    override fun getParsedText(maskedText: String): String? {
        val filteredText = filterMaskedText(maskedText)
        return if (isValidToParse(maskedText)) filteredText else null
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length == maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        val filteredText = StringBuilder(returnPattern)
        maskedText.mapIndexed { index, char ->
            char.toString().takeIf {
                maskPattern[index] == POUND
            }.orEmpty()
        }.joinToString("").map { filteredMaskedTextChar ->
            filteredText[filteredText.indexOfFirst { it == POUND }] = filteredMaskedTextChar
        }
        return filteredText.toString()
    }
}
