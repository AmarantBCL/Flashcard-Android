package com.example.android.flashcard.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.flashcard.MainMenu;
import com.example.android.flashcard.databinding.ActivityCardBinding;

import java.util.List;

public class Flashcard implements Card {
    private ActivityCardBinding binding;
    private Context context;
    private List<Word> wordSet;
    private int correct;
    private boolean isFinished = false;

    public Flashcard(ActivityCardBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        wordSet = Vocabulary.getRandomWords();
        show();
    }

    @Override
    public void show() {
        if (wordSet.size() <= 0 && !isFinished) {
            finish();
            return;
        }
        if (isFinished) {
            Intent intent = new Intent(context, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
            return;
        }
        binding.tvTranslation.setText("");
        showAnswerButton();
        if (wordSet.size() > 0) {
            Word word = wordSet.get(0);
            binding.tvWord.setText(word.getName());
            binding.tvTranscription.setText("[" + word.getTranscription() +"]");
            binding.tvPartOfSpeech.setText(word.getPartOfSpeech().toString().toLowerCase());
            binding.tvLevel.setText(word.getLevel().toString());
            binding.imgCategory.setImageResource(ImageUtils.getImageId(context,
                    word.getCategory().name().toLowerCase()));
            binding.tvOrder.setText(wordSet.size() + "/" + Vocabulary.cardAmount);
        }
    }

    @Override
    public void answer(boolean isCorrect) {
        if (wordSet.size() > 0) {
            correct += isCorrect ? 1 : 0;
            wordSet.remove(0);
            show(); // confirm
        }
    }

    public void confirm() {
        binding.tvTranslation.setText(wordSet.get(0).getTranslation());
        showKnowDontKnowButtons();
    }

    @Override
    public void finish() {
        showAnswerButton();
        binding.tvWord.setText("ГОТОВО!");
        binding.tvTranslation.setText("Правильных ответов: " + correct);
        binding.tvTranscription.setText("");
        binding.tvOrder.setText(wordSet.size() + "/" + Vocabulary.cardAmount);
        binding.bShowAnswer.setText("Закончить");
        isFinished = true;
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
