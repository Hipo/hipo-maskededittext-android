package com.hipo.maskededittext.maskers

import com.hipo.maskededittext.Mask

interface BaseMasker {
    fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, before: Int)
    fun getTextWithReturnPattern(): String?
    val onTextMaskedListener: (String) -> Unit
    val mask: Mask
    val inputType: Int
}
