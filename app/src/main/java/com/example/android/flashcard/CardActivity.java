package com.example.android.flashcard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.model.Flashcard;
import com.example.android.flashcard.model.Level;
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
        int id = Level.A1.ordinal();
        Vocabulary.difficulty = difficulty;
        Flashcard flashcard = new Flashcard(binding, this);

        binding.bKnow.setOnClickListener(v -> flashcard.answer(true));
        binding.bDontKnow.setOnClickListener(v -> flashcard.answer(false));
        binding.bShowAnswer.setOnClickListener(v -> flashcard.confirm());
    }
}