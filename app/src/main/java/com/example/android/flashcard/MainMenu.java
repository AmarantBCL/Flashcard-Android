package com.example.android.flashcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityMainMenuBinding;
import com.example.android.flashcard.model.FileUtils;
import com.example.android.flashcard.model.Vocabulary;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;
    private Context context;
    private int cardAmount;
    private int difficulty;

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
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        });

        binding.btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DictionaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.difficulty_levels));
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        binding.spinnerDifficulty.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                difficulty = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        binding.spinnerDifficulty.setOnItemSelectedListener(itemSelectedListener);
    }

    private boolean checkCardAmount() {
        String result = binding.eCardAmount.getText().toString();
        return !result.isEmpty() && Integer.valueOf(result) <= Vocabulary.count &&
                Integer.valueOf(result) > 0;
    }
}