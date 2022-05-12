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
import com.example.android.flashcard.model.CategoryAdapter;
import com.example.android.flashcard.model.CategoryItem;
import com.example.android.flashcard.utils.FileUtils;
import com.example.android.flashcard.model.Vocabulary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        Thread thread = new Thread(() -> {
            FileUtils.readInnerFile(this);
            binding.tvTotalWords.setText("Всего слов: " + Vocabulary.count);
        });
        thread.start();

        binding.btnStart.setOnClickListener(v -> {
            if (!checkCardAmount()) {
                binding.eCardAmount.setError("Неправильное количество карточек!");
                return;
            }
            cardAmount = Integer.valueOf(binding.eCardAmount.getText().toString());
            Intent intent = new Intent(context, CardActivity.class);
            intent.putExtra("card_amount", cardAmount);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        });

        binding.btnDictionary.setOnClickListener(v -> {
            Intent intent = new Intent(context, DictionaryActivity.class);
            startActivity(intent);
        });

        // TODO REFACTOR
        ArrayAdapter<String> levelAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.difficulty_levels));
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDifficulty.setAdapter(levelAdapter);
        AdapterView.OnItemSelectedListener levelListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        binding.spinnerDifficulty.setOnItemSelectedListener(levelListener);

        ArrayList<CategoryItem> categories = CategoryItem.init(this);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        binding.spinnerCategories.setAdapter(categoryAdapter);
        binding.spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem item = (CategoryItem) parent.getItemAtPosition(position);
                String clicked = item.getName();
                // TODO Clicks processing
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.tvTotalWords.setText("Всего слов: " + Vocabulary.count);
    }

    private boolean checkCardAmount() {
        String result = binding.eCardAmount.getText().toString();
        return !result.isEmpty() && Integer.valueOf(result) <= Vocabulary.count &&
                Integer.valueOf(result) > 0;
    }
}