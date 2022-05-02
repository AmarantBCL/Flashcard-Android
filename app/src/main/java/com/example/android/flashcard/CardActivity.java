package com.example.android.flashcard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.model.Flashcard;
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

        Vocabulary.cardAmount = cardAmount;
        Flashcard flashcard = new Flashcard(binding, this);

        binding.bKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard.answer(true);
            }
        });

        binding.bDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard.answer(false);
            }
        });

        binding.bShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard.show();
            }
        });
    }
}