package com.hipo.maskededittext.masks

import com.hipo.maskededittext.Mask

class StaticTextMask(private val customMaskPatter: String) : Mask() {
    override val maskPattern: String
        get() = customMaskPatter
    override val returnPattern: String
        get() = ""

    override fun getParsedText(maskedText: String): String? {
        return filterMaskedText(maskedText).takeIf { isValidToParse(maskedText) }
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length > maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.substring(maskPattern.length, maskedText.length)
    }
}
