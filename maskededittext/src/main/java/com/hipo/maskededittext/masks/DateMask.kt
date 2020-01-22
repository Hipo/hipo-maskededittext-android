package com.hipo.maskededittext.masks

import com.hipo.maskededittext.Mask

class DateMask : Mask() {
    override val maskPattern: String
        get() = "## / ## / ####"

    override val returnPattern: String
        get() = "####-##-##"

    override fun getParsedText(maskedText: String): String? {
        return if (isValidToParse(maskedText)) {
            with(filterMaskedText(maskedText)) {
                "${substring(YEAR_INDICES)}-${substring(MONTH_INDICES)}-${substring(DAY_INDICES)}"
            }
        } else {
            null
        }
    }

    override fun isValidToParse(maskedText: String): Boolean {
        return maskedText.length == maskPattern.length
    }

    override fun filterMaskedText(maskedText: String): String {
        return maskedText.filter { it.isDigit() }
    }

    companion object {
        private val MONTH_INDICES = 2..3
        private val DAY_INDICES = 0..1
        private val YEAR_INDICES = 4..7
    }
}
