package com.hipo.maskededittext

import kotlin.properties.Delegates

class Masker(
    override val mask: Mask,
    override val onTextMaskedListener: (String) -> Unit
) : BaseMasker {

    private var text: String by Delegates.observable("") { _, _, newValue ->
        onTextMaskedListener(newValue)
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, before: Int) {
        if (charSequence == null) {
            return
        }
        if (start >= mask.maskPattern.length) {
            onTextMaskedListener(removeLastItem(charSequence))
            return
        }
        when (count) {
            IS_REMOVED -> {
                handleDeletion(charSequence, start)
            }
            IS_ADDED -> {
                handleAddition(charSequence, start)
            }
            else -> {
                handleRestoration(charSequence)
            }
        }
    }

    override fun getTextWithReturnPattern(): String? {
        return mask.getParsedText(text)
    }

    private fun removeLastItem(chars: CharSequence) = chars.substring(0, chars.length - 1)

    private fun handleAddition(charSequence: CharSequence, start: Int) {
        if (charSequence.isEmpty()) return
        addFirstMaskIfNeed(start)
        text += charSequence.last()
        addUpcomingMasks(start)
    }

    private fun handleRestoration(charSequence: CharSequence) {
        text = charSequence.toString()
    }

    private fun handleDeletion(charSequence: CharSequence, start: Int) {
        if (start == 0 || charSequence.isEmpty()) {
            text = ""
            return
        }
        text = charSequence.toString()
        val tempText = removeCurrentMaskIfNeed(charSequence, start)
        removeUpcomingMasks(tempText)
    }

    private fun removeCurrentMaskIfNeed(charSequence: CharSequence, start: Int): CharSequence {
        var index = start
        var tempText = charSequence
        while (index > 0 && mask.maskPattern[index] != POUND) {
            tempText = removeLastItem(tempText)
            text = tempText
            index--
        }
        return tempText
    }

    private fun removeUpcomingMasks(charSequence: CharSequence) {
        var index = charSequence.length - 1
        var tempText = charSequence
        while (index >= 0 && mask.maskPattern[index] != POUND && charSequence.length > 1) {
            tempText = removeLastItem(tempText)
            text = tempText
            index--
        }
    }

    private fun addFirstMaskIfNeed(start: Int) {
        var index = start
        with(mask) {
            while (maskPattern[index] != POUND) {
                text += maskPattern[index]
                index++
            }
        }
    }

    private fun addUpcomingMasks(start: Int) {
        if (start == 0 && text.isEmpty().not()) {
            return
        }
        var index = text.length
        with(mask) {
            while (index in maskPattern.indices && maskPattern[index] != POUND) {
                text += maskPattern[index]
                index++
            }
        }
    }

    companion object {
        const val POUND = '#'
        const val IS_REMOVED = 0
        const val IS_ADDED = 1
    }
}
