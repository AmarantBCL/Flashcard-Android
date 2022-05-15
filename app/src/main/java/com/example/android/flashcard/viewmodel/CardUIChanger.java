package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.view.View;

import com.example.android.flashcard.R;
import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.utils.ImageUtils;

public abstract class CardUIChanger {
    private final ActivityCardBinding binding;
    private final Context context;
    private Word word;
    private boolean isReversed;
    private boolean isCorrect;

    public ActivityCardBinding getBinding() {
        return binding;
    }

    public Context getContext() {
        return context;
    }

    public Word getWord() {
        return word;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public CardUIChanger(ActivityCardBinding binding, Context context, boolean isReversed) {
        this.binding = binding;
        this.context = context;
        this.isReversed = isReversed;
    }

    public void showWord(Word word, int order) {
        this.word = word;
        binding.tvTranslation.setText("");
        beforeAnswer();
        update();
        binding.tvOrder.setText(order + "/" + Vocabulary.cardAmount);
    }

    public void showAnswer() {
        binding.tvTranslation.setText(isReversed ? word.getName() : word.getTranslation());
        if (isReversed) {
            binding.tvTranscription.setText(context.getString(R.string.transcription, word.getTranscription()));
        }
        afterAnswer();
    }

    public void showResult(int correct) {
        beforeAnswer();
        binding.tvWord.setText("ГОТОВО!");
        binding.tvTranslation.setText("Правильных ответов: " + correct);
        binding.tvTranscription.setText("");
        binding.tvOrder.setText("0/" + Vocabulary.cardAmount);
        binding.btnShowAnswer.setText("Закончить");
        binding.radioGroup.setVisibility(View.GONE);
    }

    public void update() {
        binding.tvWord.setText(isReversed ? word.getTranslation() : word.getName());
        binding.tvTranscription.setText(isReversed ? "" : context.getString(R.string.transcription, word.getTranscription()));
        binding.tvPartOfSpeech.setText(word.getPartOfSpeech().toString().toLowerCase());
        binding.tvLevel.setText(word.getLevel().toString());
        binding.imgCategory.setImageResource(ImageUtils.getImageId(context,
                word.getCategory().name().toLowerCase()));
    }

    public abstract void beforeAnswer();

    public abstract void afterAnswer();
}
