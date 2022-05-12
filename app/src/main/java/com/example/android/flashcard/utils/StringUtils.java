package com.example.android.flashcard.utils;

import android.text.Editable;
import android.widget.EditText;

public class StringUtils {
    public static String getWordText(EditText edit) {
        return edit.getText().toString().toLowerCase().trim();
    }

    public static String getText(EditText edit) {
        return edit.getText().toString();
    }
}
