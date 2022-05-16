package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.android.flashcard.R;
import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.model.Card;
import com.example.android.flashcard.model.VariantOption;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.utils.ImageUtils;

public class VariantCardUIChanger extends CardUIChanger {
    private static final int BACKGROUND_CORRECT_COLOR = 0xAA90EE90;
    private static final int BACKGROUND_INCORRECT_COLOR = 0xAACD5C5C;
    private static final int BACKGROUND_SELECT_COLOR = 0xAA4682B4;

    private VariantOption variantOption;
    private int chosenId;
    private boolean isSelected;
    private boolean isBlocked;

    public VariantCardUIChanger(ActivityCardBinding binding, Context context, boolean isReversed) {
        super(binding, context, isReversed);
        getBinding().btnKnow.setVisibility(View.GONE);
        getBinding().btnDontKnow.setVisibility(View.GONE);
        getBinding().editTyping.setVisibility(View.GONE);
        initSelections();
    }

    @Override
    public void showAnswer() {
        if (isReversed()) {
            getBinding().tvTranscription.setText(getContext().getString(R.string.transcription, getWord().getTranscription()));
        }
        showCorrectAnswers();
        afterAnswer();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void beforeAnswer() {
        variantOption = new VariantOption(getWord());
        int count = 0;
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            View view = getBinding().radioGroup.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) getBinding().radioGroup.getChildAt(i);
                textView.setText(isReversed()
                        ? variantOption.getOptions().get(count).getName()
                        : variantOption.getOptions().get(count).getTranslation());
                textView.setBackgroundColor(0x00000000);
                count++;
            }
        }
        setCorrect(false);
        isSelected = false;
        isBlocked = false;
        getBinding().btnShowAnswer.setText("Выбрать");
        getBinding().radioGroup.setVisibility(View.VISIBLE);
        getBinding().btnShowAnswer.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterAnswer() {
        getBinding().btnShowAnswer.setText("Далее");
        isBlocked = true;
    }

    private void initSelections() {
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            View view = getBinding().radioGroup.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setOnClickListener(v -> {
                    if (isBlocked) return;
                    setCorrect(variantOption.isCorrect(textView.getText().toString(), isReversed()));
                    for (int j = 0; j < getBinding().radioGroup.getChildCount(); j++) {
                        View innerView = getBinding().radioGroup.getChildAt(j);
                        if (innerView instanceof TextView) {
                            TextView tv = (TextView) getBinding().radioGroup.getChildAt(j);
                            tv.setBackgroundColor(0x00000000);
                        }
                    }
                    isSelected = true;
                    chosenId = variantOption.getAnswerId(textView.getText().toString());
                    textView.setBackgroundColor(BACKGROUND_SELECT_COLOR);
                });
            }
        }
    }

    private void showCorrectAnswers() {
        int count = 0;
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            View view = getBinding().radioGroup.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) getBinding().radioGroup.getChildAt(i);
                boolean correct = variantOption.isCorrect(textView.getText().toString(), isReversed());
                if (isSelected && chosenId == count) {
                    textView.setBackgroundColor(BACKGROUND_INCORRECT_COLOR);
                }
                if (correct) {
                    textView.setBackgroundColor(BACKGROUND_CORRECT_COLOR);
                }
                count++;
            }
        }
    }
}
