package com.example.android.flashcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityMainMenuBinding;
import com.example.android.flashcard.model.FileUtils;
import com.example.android.flashcard.model.Vocabulary;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;
    private Context context;
    private int cardAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        FileUtils.readRaw(this);
        binding.tvTotalWords.setText("Всего слов: " + Vocabulary.count);
        binding.bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkCardAmount()) {
                    binding.eCardAmount.setError("Неправильное количество карточек!");
                    return;
                }
                cardAmount = Integer.valueOf(binding.eCardAmount.getText().toString());
                Intent intent = new Intent(context, CardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("card_amount", cardAmount);
                startActivity(intent);
            }
        });

        binding.bDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DictionaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    private boolean checkCardAmount() {
        String result = binding.eCardAmount.getText().toString();
        return !result.isEmpty() && Integer.valueOf(result) <= Vocabulary.count &&
                Integer.valueOf(result) > 0;
    }
}