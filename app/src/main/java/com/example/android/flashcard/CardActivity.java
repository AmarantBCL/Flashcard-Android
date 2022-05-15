package com.example.android.flashcard;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.model.Card;
import com.example.android.flashcard.model.Flashcard;
import com.example.android.flashcard.model.Variant;
import com.example.android.flashcard.viewmodel.DictionaryDialog;
import com.example.android.flashcard.viewmodel.FlashcardCardUIChanger;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.viewmodel.VariantCardUIChanger;

public class CardActivity extends AppCompatActivity {
    private ActivityCardBinding binding;
    private Context context;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        Bundle arguments = getIntent().getExtras();
        String mode = arguments.getString("mode");
        int cardAmount = arguments.getInt("card_amount");
        int difficulty = arguments.getInt("difficulty");
        String category = arguments.getString("category");

        Vocabulary.cardAmount = cardAmount;
        Vocabulary.difficulty = difficulty;
        Vocabulary.category = category;
        switch (mode) {
            case "Карточки":
                card = new Flashcard(new FlashcardCardUIChanger(binding, this, false));
                break;
            case "Обратные карточки":
                card = new Flashcard(new FlashcardCardUIChanger(binding, this, true));
                break;
            case "Варианты":
                card = new Variant(new VariantCardUIChanger(binding, this, false));
                break;
            case "Обратные варианты":
                card = new Variant(new VariantCardUIChanger(binding, this, true));
                break;
            default:
                break;
        }
        card.show();

        binding.btnKnow.setOnClickListener(v -> card.answer(true));
        binding.btnDontKnow.setOnClickListener(v -> card.answer(false));
        binding.btnShowAnswer.setOnClickListener(v -> {
            if (card.getState() == CardState.RESULT) {
                finish();
            } else {
                card.confirm();
            }
        });

        binding.tvWord.setOnLongClickListener(v -> {
            if (card.getState() != CardState.ANSWER) return false;
            DictionaryDialog dialog = new DictionaryDialog(context, card.getCardUiChanger());
            dialog.showChange(card.getWord());
            return false;
        });
    }
}