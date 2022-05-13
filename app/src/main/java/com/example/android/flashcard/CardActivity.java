package com.example.android.flashcard;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.model.Flashcard;
import com.example.android.flashcard.viewmodel.DictionaryDialog;
import com.example.android.flashcard.viewmodel.FlashcardCardUIChanger;
import com.example.android.flashcard.model.Vocabulary;

public class CardActivity extends AppCompatActivity {
    private ActivityCardBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        Bundle arguments = getIntent().getExtras();
        int cardAmount = arguments.getInt("card_amount");
        int difficulty = arguments.getInt("difficulty");

        Vocabulary.cardAmount = cardAmount;
        Vocabulary.difficulty = difficulty;
        Flashcard flashcard = new Flashcard(new FlashcardCardUIChanger(binding, this));
        flashcard.show();

        binding.bKnow.setOnClickListener(v -> flashcard.answer(true));
        binding.bDontKnow.setOnClickListener(v -> flashcard.answer(false));
        binding.bShowAnswer.setOnClickListener(v -> {
            if (flashcard.getState() == CardState.RESULT) {
                finish();
            } else {
                flashcard.confirm();
            }
        });

        binding.tvWord.setOnLongClickListener(v -> {
            if (flashcard.getState() != CardState.ANSWER) return false;
            DictionaryDialog dialog = new DictionaryDialog(context, flashcard.getUiChanger());
            dialog.showChange(flashcard.getWord());
            return false;
        });
    }
}