package com.hipo.maskededittext.masks

import com.hipo.maskededittext.Mask

@Deprecated("Use static_text and define maskPattern")
class CurrencyMask : Mask() {
    override val maskPattern: String
        get() = "$########"
    override val returnPattern: String
        get() = ""

    override fun getParsedText(maskedText: String): String? {
        return filterMaskedText(maskedText).takeIf { isValidToParse(maskedText) }
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length > 1
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.substring(1, maskedText.length)
    }
}
