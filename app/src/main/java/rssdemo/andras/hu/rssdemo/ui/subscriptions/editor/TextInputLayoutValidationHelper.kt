package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


internal class TextInputLayoutValidationHelper(private val context: Context, private val textInputLayout: TextInputLayout) {

    private val editText: EditText = textInputLayout.editText!!
    private val red: Int
    private var errorRaised: Boolean = false

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            dismissError()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    init {
        editText.addTextChangedListener(textWatcher)
        red = ContextCompat.getColor(context, android.R.color.holo_red_dark)
    }

    fun raiseError(@StringRes errorMessageResId: Int) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = context.getString(errorMessageResId)
        editText.background.setColorFilter(red, PorterDuff.Mode.SRC_ATOP)
        errorRaised = true
    }

    private fun dismissError() {
        if (errorRaised) {
            textInputLayout.isErrorEnabled = false
            editText.background.clearColorFilter()
            errorRaised = false
        }
    }

}
