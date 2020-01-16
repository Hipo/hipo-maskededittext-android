package com.hipo.maskededittext

interface BaseMasker {
    fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, before: Int)
    fun getTextWithReturnPattern(): String?
    val onTextMaskedListener: (String) -> Unit
    val mask: Mask
}
