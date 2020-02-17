package com.hipo.maskededittext.masks

import com.hipo.maskededittext.Mask
import com.hipo.maskededittext.maskers.Masker.Companion.POUND

class CustomMask(private val customMaskPattern: String, private val customReturnPattern: String) :
    Mask() {
    override val maskPattern: String
        get() = customMaskPattern
    override val returnPattern: String
        get() = customReturnPattern

    override fun getParsedText(maskedText: String): String? {
        return filterMaskedText(maskedText).takeIf { isValidToParse(maskedText) }
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
