package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.view.View;

import com.example.android.flashcard.databinding.ActivityCardBinding;

public class FlashcardCardUIChanger extends CardUIChanger {
    public FlashcardCardUIChanger(ActivityCardBinding binding, Context context, boolean isReversed) {
        super(binding, context, isReversed);
        binding.radioGroup.setVisibility(View.GONE);
    }

    @Override
    public void beforeAnswer() {
        getBinding().btnKnow.setVisibility(View.GONE);
        getBinding().btnDontKnow.setVisibility(View.GONE);
        getBinding().btnShowAnswer.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterAnswer() {
        getBinding().btnKnow.setVisibility(View.VISIBLE);
        getBinding().btnDontKnow.setVisibility(View.VISIBLE);
        getBinding().btnShowAnswer.setVisibility(View.GONE);
    }
}
