package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class TextInputLayoutValidationHelper {

    private EditText editText;
    private Context context;
    private TextInputLayout textInputLayout;
    private int red;
    private boolean errorRaised;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            dismissError();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public TextInputLayoutValidationHelper(Context context, TextInputLayout textInputLayout) {
        this.context = context;
        this.textInputLayout = textInputLayout;
        editText = textInputLayout.getEditText();
        editText.addTextChangedListener(textWatcher);
        red = ContextCompat.getColor(context, android.R.color.holo_red_dark);
    }

    public void raiseError(@StringRes int errorMessageResId) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(context.getString(errorMessageResId));
        editText.getBackground().setColorFilter(red, PorterDuff.Mode.SRC_ATOP);
        errorRaised = true;
    }

    public void dismissError() {
        if (errorRaised) {
            textInputLayout.setErrorEnabled(false);
            editText.getBackground().clearColorFilter();
            errorRaised = false;
        }
    }

    public boolean isEmpty() {
        return editText.getText().length() == 0;
    }
}
