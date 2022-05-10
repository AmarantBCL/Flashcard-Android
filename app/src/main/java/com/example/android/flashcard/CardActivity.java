package com.example.android.flashcard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.model.Flashcard;
import com.example.android.flashcard.viewmodel.FlashcardUIChanger;
import com.example.android.flashcard.model.Vocabulary;

public class CardActivity extends AppCompatActivity {
    private ActivityCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle arguments = getIntent().getExtras();
        int cardAmount = arguments.getInt("card_amount");
        int difficulty = arguments.getInt("difficulty");

        Vocabulary.cardAmount = cardAmount;
        Vocabulary.difficulty = difficulty;
        Flashcard flashcard = new Flashcard(new FlashcardUIChanger(binding, this));

        binding.bKnow.setOnClickListener(v -> flashcard.answer(true));
        binding.bDontKnow.setOnClickListener(v -> flashcard.answer(false));
        binding.bShowAnswer.setOnClickListener(v -> {
            if (flashcard.isFinished()) {
                finish();
            } else {
                flashcard.confirm();
            }
        });
    }
}