package com.hipo.maskededittext

interface MaskCreator {
    fun create(maskPattern: String? = null, returnPattern: String? = null): Mask
}
