package com.hipo.maskededittext

abstract class Mask {
    abstract val maskPattern: String
    abstract val returnPattern: String

    abstract fun getParsedText(maskedText: String): String?
    abstract fun isValidToParse(filteredText: String): Boolean
    abstract fun filterMaskedText(maskedText: String): String

    enum class Type : MaskCreator {
        DATE {
            override fun create(maskPattern: String?, returnPattern: String?): Mask = DateMask()
        },
        PHONE {
            override fun create(maskPattern: String?, returnPattern: String?): Mask = PhoneMask()
        },
        SSN {
            override fun create(maskPattern: String?, returnPattern: String?): Mask = SSNMask()
        },
        CUSTOM {
            override fun create(maskPattern: String?, returnPattern: String?): Mask =
                CustomMask(maskPattern.orEmpty(), returnPattern.orEmpty())
        },
        UNSELECTED {
            override fun create(maskPattern: String?, returnPattern: String?): Mask = UnselectedMask()
        }
    }
}
