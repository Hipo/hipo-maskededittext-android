package com.hipo.maskededittext.masks

import com.hipo.maskededittext.Mask

class CurrencyMask(override val maskPattern: String) : Mask() {
    override val returnPattern: String
        get() = ""

    override fun getParsedText(maskedText: String): String? {
        val filteredText = filterMaskedText(maskedText).takeIf { isValidToParse(maskedText) } ?: "0"
        return filteredText.replace(",", "")
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length > maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        if (maskedText.isBlank()) return "0"
        return maskedText.substring(maskPattern.length, maskedText.length).run {
            if (last() == '.') substring(0, length - 1) else this
        }
    }
}
