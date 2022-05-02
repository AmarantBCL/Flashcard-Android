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
            binding.tvWord.setText("ГОТОВО!");
            binding.tvTranslation.setText("Правильных ответов: " + correct);
            binding.tvTranscription.setText("");
            binding.tvOrder.setText(wordSet.size() + "/" + Vocabulary.cardAmount);
            binding.bShowAnswer.setText("Закончить");
            isFinished = true;
            return;
        }
        if (isFinished) {
            Intent intent = new Intent(context, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
            return;
        }
        binding.tvTranslation.setText("");
        binding.bKnow.setVisibility(View.VISIBLE);
        binding.bDontKnow.setVisibility(View.VISIBLE);
        binding.bShowAnswer.setVisibility(View.GONE);
        if (wordSet.size() > 0) {
            Word word = wordSet.get(0);
            binding.tvWord.setText(word.getName());
            binding.tvTranscription.setText("[" + word.getTranscription() +"]");
            binding.imgCategory.setImageResource(ImageUtils.getImageId(context,
                    word.getCategory().name().toLowerCase()));
            binding.tvOrder.setText(wordSet.size() + "/" + Vocabulary.cardAmount);
        }
    }

    @Override
    public boolean answer(boolean isCorrect) {
        if (wordSet.size() > 0) {
            correct += isCorrect ? 1 : 0;
            confirm();
            wordSet.remove(0);
        }
        return true;
    }

    public void confirm() {
        binding.tvTranslation.setText(wordSet.get(0).getTranslation());
        binding.bKnow.setVisibility(View.GONE);
        binding.bDontKnow.setVisibility(View.GONE);
        binding.bShowAnswer.setVisibility(View.VISIBLE);
    }
}
