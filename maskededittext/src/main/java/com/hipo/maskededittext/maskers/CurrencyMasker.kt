package com.hipo.maskededittext.maskers

import android.text.InputType.*
import com.hipo.maskededittext.Mask
import java.text.DecimalFormat

class CurrencyMasker(override val mask: Mask, override val onTextMaskedListener: (String) -> Unit) :
    BaseMasker {

    override val inputType: Int
        get() = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL or TYPE_NUMBER_FLAG_SIGNED

    override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, before: Int) {
        if (charSequence == null) {
            return
        }

        if (charSequence.isNotEmpty()) {
            var numberStr = charSequence.toString().replace(",", "").replace(mask.maskPattern, "")
            if (numberStr.isBlank()) {
                numberStr = "0"
            }
            val numList = numberStr.split(".")
            var formattedText = DecimalFormat("${mask.maskPattern}#,##0").format(numList[0].toDouble())
            if (numList.size > 1) {
                formattedText += ".${numList[1].substring(0, if (numList[1].length < 3) numList[1].length else 2)}"
            }
            onTextMaskedListener(formattedText)
        }
    }

    override fun getTextWithReturnPattern(): String? {
        return null
    }
}