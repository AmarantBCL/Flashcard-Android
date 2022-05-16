package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.view.View;

import com.example.android.flashcard.R;
import com.example.android.flashcard.databinding.ActivityCardBinding;

public class TypingCardUIChanger extends CardUIChanger {
    public TypingCardUIChanger(ActivityCardBinding binding, Context context, boolean isReversed) {
        super(binding, context, isReversed);
        getBinding().btnKnow.setVisibility(View.GONE);
        getBinding().btnDontKnow.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
    }

    @Override
    public void showAnswer() {
        if (isReversed()) {
            getBinding().tvTranscription.setText(getContext().getString(R.string.transcription, getWord().getTranscription()));
        }
        String enteredWord = getBinding().editTyping.getText().toString().toLowerCase().trim();
        boolean correct = enteredWord.equals(getWord().getName().toLowerCase());
        setCorrect(correct);
        getBinding().editTyping.setText(getWord().getName());
        getBinding().editTyping.setEnabled(false);
        getBinding().editTyping.setTextColor(correct ? 0xFF228B22 : 0xFFB22222);
        afterAnswer();
    }

    @Override
    public void beforeAnswer() {
        getBinding().editTyping.setText("");
        getBinding().btnShowAnswer.setText("Ввести");
        getBinding().btnShowAnswer.setVisibility(View.VISIBLE);
        getBinding().editTyping.setEnabled(true);
        getBinding().editTyping.setTextColor(0xFF000000);
    }

    @Override
    public void afterAnswer() {
        getBinding().btnShowAnswer.setText("Далее");
    }
}
