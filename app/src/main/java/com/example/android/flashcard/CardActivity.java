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
import com.example.android.flashcard.model.Typing;
import com.example.android.flashcard.model.Variant;
import com.example.android.flashcard.viewmodel.DictionaryDialog;
import com.example.android.flashcard.viewmodel.FlashcardCardUIChanger;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.viewmodel.TypingCardUIChanger;
import com.example.android.flashcard.viewmodel.VariantCardUIChanger;

public class CardActivity extends AppCompatActivity {
    public static final String MODE_KEY = "mode";
    public static final String CARD_AMOUNT_KEY = "card_amount";
    public static final String DIFFICULTY_KEY = "difficulty";
    public static final String CATEGORY_KEY = "category";
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
        String mode = arguments.getString(MODE_KEY);
        int cardAmount = arguments.getInt(CARD_AMOUNT_KEY);
        int difficulty = arguments.getInt(DIFFICULTY_KEY);
        String category = arguments.getString(CATEGORY_KEY);

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
            case "Написание":
                card = new Typing(new TypingCardUIChanger(binding, this, true));
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