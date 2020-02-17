package com.hipo.maskededittext.maskers

import android.text.InputType
import com.hipo.maskededittext.Mask
import kotlin.properties.Delegates

class StaticTextMasker(
    override val mask: Mask,
    override val onTextMaskedListener: (String) -> Unit
) : BaseMasker {

    override val inputType: Int
        get() = InputType.TYPE_CLASS_NUMBER

    private var text: String by Delegates.observable("") { _, _, newValue ->
        onTextMaskedListener(newValue)
    }

    init {
        text = mask.maskPattern
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, before: Int) {
        if (charSequence == null) {
            return
        }
        text = charSequence.toString()
        if (count == IS_REMOVED) {
            handleRemove(charSequence)
        }
    }

    override fun getTextWithReturnPattern(): String? {
        return mask.getParsedText(text)
    }

    private fun handleRemove(charSequence: CharSequence) {
        if (charSequence.startsWith(mask.maskPattern).not()) {
            text = mask.maskPattern
        }
    }

    companion object {
        const val IS_REMOVED = 0
    }
}
