package com.hipo.maskededittext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import kotlin.properties.Delegates

class MaskedEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initAttributes(attrs, defStyle)
    }

    private var textWatcher: TextWatcher? = null
    private var maskPattern: String = ""
    private var returnMaskPattern: String = ""
    private var masker: Masker? = null
    private var maskType: Mask by Delegates.observable<Mask>(UnselectedMask()) { _, _, newValue ->
        setMasker(newValue)
    }

    var onTextChangedListener: ((String) -> Unit)? = null

    init {
        initMaskedEditText()
    }

    override fun onDetachedFromWindow() {
        textWatcher = null
        super.onDetachedFromWindow()
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        setSelection(text?.length ?: 0)
    }

    fun getParsedText() = maskType.getParsedText(text.toString())

    fun isValid() = maskType.isValidToParse(text.toString())

    private fun setMasker(mask: Mask) {
        if (mask is UnselectedMask) {
            throw Exception("${MaskedEditText::class.java.simpleName}: maskType must be selected")
        }
        masker = when (mask) {
            is CustomMask -> {
                when {
                    maskPattern.contains(Masker.POUND).not() -> {
                        throw Exception("${MaskedEditText::class.java.simpleName}: Custom mask must contain #")
                    }
                    returnMaskPattern.contains(Masker.POUND).not() -> {
                        throw Exception("${MaskedEditText::class.java.simpleName}: Custom mask return type must contain #")
                    }
                    else -> {
                        Masker(CustomMask(maskPattern, returnMaskPattern), ::setEditTextWithoutTriggerListener)
                    }
                }
            }
            else -> Masker(mask, ::setEditTextWithoutTriggerListener)
        }
    }

    private fun setEditTextWithoutTriggerListener(newText: String) {
        removeTextChangedListener(textWatcher)
        onTextChangedListener?.invoke(newText)
        setText(newText)
        setSelection(text?.length ?: 0)
        addTextChangedListener(textWatcher)
    }

    private fun initMaskedEditText() {
        initTextWatcher()
        inputType = InputType.TYPE_CLASS_NUMBER
    }

    private fun initAttributes(attrs: AttributeSet, defStyle: Int = -1) {
        with(context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText, defStyle, 0)) {
            maskPattern = getString(R.styleable.MaskedEditText_maskPattern).orEmpty()
            maskType =
                Mask.Type.values()[getInt(R.styleable.MaskedEditText_maskType, Mask.Type.UNSELECTED.ordinal)].create()
        }
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                masker?.onTextChanged(s, start, count, before)
            }
        }
        addTextChangedListener(textWatcher)
    }
}
