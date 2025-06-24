package com.example.taskflow.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.taskflow.R;

public class FormUtils {
    public static void markInvalid(Context context, TextView editText, String invalidMessage) {
        editText.setHintTextColor(ContextCompat.getColor(context, R.color.invalid_red));
        editText.setHint(invalidMessage);
    }

    public static void markInvalid(Context context, TextView editText) {
        editText.setHintTextColor(ContextCompat.getColor(context, R.color.invalid_red));
        editText.setHint(ContextCompat.getString(context, R.string.text_required_field));
    }

    public static void markInvalid(Context context, TextView editText, TextView label, String invalidMessage) {
        markInvalid(context, editText, invalidMessage);
        label.setTextColor(ContextCompat.getColor(context, R.color.invalid_red));
    }

    public static void markInvalid(Context context, TextView editText, TextView label) {
        markInvalid(context, editText);
        label.setTextColor(ContextCompat.getColor(context, R.color.invalid_red));
    }
}
