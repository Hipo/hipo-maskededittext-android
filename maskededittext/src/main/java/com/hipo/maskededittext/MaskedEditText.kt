package com.hipo.maskededittext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.hipo.maskededittext.Masker.Companion.POUND
import com.hipo.maskededittext.masks.CustomMask
import com.hipo.maskededittext.masks.StaticTextMask
import com.hipo.maskededittext.masks.UnselectedMask
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
    private var masker: BaseMasker? = null
    private var maskType: Mask by Delegates.observable<Mask>(UnselectedMask()) { _, _, newValue ->
        setMasker(newValue)
    }

    var onTextChangedListener: ((String) -> Unit)? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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
            throw Exception("${LOG_TAG}: ${context.getString(R.string.exception_unselected_mask)}")
        }
        masker = when (mask) {
            is CustomMask -> handleCustomMask(mask)
            is StaticTextMask -> handleStaticTextMask(mask)
            else -> Masker(mask, ::setEditTextWithoutTriggerListener)
        }
    }

    private fun handleCustomMask(mask: Mask): Masker {
        return when {
            maskPattern.contains(POUND).not() -> {
                throw Exception("$LOG_TAG: ${context.getString(R.string.exception_mask_pound)}")
            }
            returnMaskPattern.contains(POUND).not() -> {
                throw Exception("$LOG_TAG: ${context.getString(R.string.exception_return_pound)}")
            }
            maskPattern.count { it == POUND } != returnMaskPattern.count { it == POUND } -> {
                throw Exception("$LOG_TAG: ${context.getString(R.string.exception_pound_count)}")
            }
            else -> {
                Masker(mask, ::setEditTextWithoutTriggerListener)
            }
        }
    }

    private fun handleStaticTextMask(mask: Mask): StaticTextMasker {
        return when {
            maskPattern.isBlank() -> {
                throw Exception("$LOG_TAG: ${context.getString(R.string.exception_mask_pound)}")
            }
            else -> StaticTextMasker(mask, ::setEditTextWithoutTriggerListener)
        }
    }

    private fun setEditTextWithoutTriggerListener(newText: String) {
        removeTextChangedListener(textWatcher)
        onTextChangedListener?.invoke(newText)
        setText(newText)
        setSelection(text?.length ?: 0)
        textWatcher?.let { textWatcher ->
            addTextChangedListener(textWatcher)
        }
    }

    private fun initMaskedEditText() {
        initTextWatcher()
        inputType = InputType.TYPE_CLASS_NUMBER
    }

    private fun initAttributes(attrs: AttributeSet, defStyle: Int = -1) {
        with(context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText, defStyle, 0)) {
            maskPattern = getString(R.styleable.MaskedEditText_maskPattern).orEmpty()
            returnMaskPattern = getString(R.styleable.MaskedEditText_returnPattern).orEmpty()
            maskType = Mask.Type.values()[
                    getInt(R.styleable.MaskedEditText_maskType, Mask.Type.UNSELECTED.ordinal)
            ].create(maskPattern, returnMaskPattern)
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

    /**
     *  Below 4 function which are
     *   - getHint()
     *   - onCreateInputConnection()
     *   - getTextInputLayout()
     *   - getHintFromLayout()
     *   copied from TextInputEditText to perform same label feature.
     */
    override fun getHint(): CharSequence? {
        val layoutHint = getTextInputLayout()?.hint
        return layoutHint ?: super.getHint()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        val inputConnection = super.onCreateInputConnection(outAttrs)
        if (inputConnection != null && outAttrs.hintText == null) {
            outAttrs.hintText = getHintFromLayout()
        }
        return inputConnection
    }

    private fun getTextInputLayout(): TextInputLayout? {
        var parent = this.parent
        while (parent is View) {
            if (parent is TextInputLayout) {
                return parent
            }
            parent = parent.getParent()
        }
        return null
    }

    private fun getHintFromLayout(): CharSequence? {
        val layout = getTextInputLayout()
        return layout?.hint
    }

    companion object {
        private val LOG_TAG = MaskedEditText::class.java.simpleName
    }
}
