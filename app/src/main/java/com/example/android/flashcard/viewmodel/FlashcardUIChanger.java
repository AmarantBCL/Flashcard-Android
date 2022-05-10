package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.view.View;

import com.example.android.flashcard.R;
import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.utils.ImageUtils;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;

public class FlashcardUIChanger implements UIChanger {
    private final ActivityCardBinding binding;
    private final Context context;
    private Word word;

    public FlashcardUIChanger(ActivityCardBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @Override
    public void showWord(Word word, int order) {
        this.word = word;
        binding.tvTranslation.setText("");
        showAnswerButton();
        binding.tvWord.setText(word.getName());
        binding.tvTranscription.setText(context.getString(R.string.transcription, word.getTranscription()));
        binding.tvPartOfSpeech.setText(word.getPartOfSpeech().toString().toLowerCase());
        binding.tvLevel.setText(word.getLevel().toString());
        binding.imgCategory.setImageResource(ImageUtils.getImageId(context,
                word.getCategory().name().toLowerCase()));
        binding.tvOrder.setText(order + "/" + Vocabulary.cardAmount);
    }

    @Override
    public void showAnswer() {
        binding.tvTranslation.setText(word.getTranslation());
        showKnowDontKnowButtons();
    }

    @Override
    public void showResult(int correct) {
        showAnswerButton();
        binding.tvWord.setText("ГОТОВО!");
        binding.tvTranslation.setText("Правильных ответов: " + correct);
        binding.tvTranscription.setText("");
        binding.tvOrder.setText("0/" + Vocabulary.cardAmount);
        binding.bShowAnswer.setText("Закончить");
    }

    private void showAnswerButton() {
        binding.bKnow.setVisibility(View.GONE);
        binding.bDontKnow.setVisibility(View.GONE);
        binding.bShowAnswer.setVisibility(View.VISIBLE);
    }

    private void showKnowDontKnowButtons() {
        binding.bKnow.setVisibility(View.VISIBLE);
        binding.bDontKnow.setVisibility(View.VISIBLE);
        binding.bShowAnswer.setVisibility(View.GONE);
    }
}
