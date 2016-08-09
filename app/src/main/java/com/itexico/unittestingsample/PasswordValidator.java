package com.itexico.unittestingsample;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
public class PasswordValidator implements TextWatcher {
    /**
     * Password validation pattern.
     */
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}");

    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    /**
     * Validates if the given input is a valid password.     *
     * @return {@code true} if the input is a valid password. {@code false} otherwise.
     */
    public static boolean isValidPassword(CharSequence email) {
        return email != null && PASSWORD_PATTERN.matcher(email).matches() && email.length() > 10;
    }

    @Override
    final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidPassword(editableText);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*No-op*/}

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {/*No-op*/}


}
