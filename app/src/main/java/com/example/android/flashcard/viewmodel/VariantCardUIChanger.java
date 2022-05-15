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

    public VariantCardUIChanger(ActivityCardBinding binding, Context context, boolean isReversed) {
        super(binding, context, isReversed);
        initSelections();
    }

    @Override
    public void showAnswer() {
        showCorrectAnswers();
        afterAnswer();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void beforeAnswer() {
        variantOption = new VariantOption(getWord());
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            TextView textView = (TextView) getBinding().radioGroup.getChildAt(i);
            textView.setText(isReversed()
                    ? variantOption.getOptions().get(i).getName()
                    : variantOption.getOptions().get(i).getTranslation());
            textView.setBackgroundColor(0x00000000);
        }
        setCorrect(false);
        isSelected = false;
        getBinding().btnShowAnswer.setText("Выбрать");
        getBinding().radioGroup.setVisibility(View.VISIBLE);
        getBinding().btnKnow.setVisibility(View.GONE);
        getBinding().btnDontKnow.setVisibility(View.GONE);
        getBinding().btnShowAnswer.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterAnswer() {
        getBinding().btnShowAnswer.setText("Далее");
    }

    private void initSelections() {
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            TextView textView = (TextView) getBinding().radioGroup.getChildAt(i);
            int finalI = i;
            textView.setOnClickListener(v -> {
                setCorrect(variantOption.isCorrect(textView.getText().toString(), isReversed()));
                for (int j = 0; j < getBinding().radioGroup.getChildCount(); j++) {
                    TextView tv = (TextView) getBinding().radioGroup.getChildAt(j);
                    tv.setBackgroundColor(0x00000000);
                }
                isSelected = true;
                chosenId = finalI;
                textView.setBackgroundColor(BACKGROUND_SELECT_COLOR);
            });
        }
    }

    private void showCorrectAnswers() {
        for (int i = 0; i < getBinding().radioGroup.getChildCount(); i++) {
            TextView textView = (TextView) getBinding().radioGroup.getChildAt(i);
            boolean correct = variantOption.isCorrect(textView.getText().toString(), isReversed());
            if (isSelected && chosenId == i) {
                textView.setBackgroundColor(BACKGROUND_INCORRECT_COLOR);
            }
            if (correct) {
                textView.setBackgroundColor(BACKGROUND_CORRECT_COLOR);
            }
        }
    }
}
