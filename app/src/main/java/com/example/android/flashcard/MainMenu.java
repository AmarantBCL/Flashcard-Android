package com.example.android.flashcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityMainMenuBinding;
import com.example.android.flashcard.model.CategoryAdapter;
import com.example.android.flashcard.model.CategoryItem;
import com.example.android.flashcard.model.Mode;
import com.example.android.flashcard.model.ModeAdapter;
import com.example.android.flashcard.utils.FileUtils;
import com.example.android.flashcard.model.Vocabulary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    private static int BACKGROUND_SELECTED = 0xAA5F9EA0;
    private static int BACKGROUND_NOT_SELECTED = 0xFFA2DFE7;
    private ActivityMainMenuBinding binding;
    private Context context;
    private int cardAmount;
    private int difficulty;
    private String category;
    private Mode mode;

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
            if (mode == null) {
                Toast.makeText(this, "Выберите режим.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checkCardAmount()) {
                binding.editCardAmount.setError("Неправильное количество карточек!");
                return;
            }
            cardAmount = Integer.valueOf(binding.editCardAmount.getText().toString());
            Intent intent = new Intent(context, CardActivity.class);
            intent.putExtra("mode", mode.getName());
            intent.putExtra("card_amount", cardAmount);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("category", category);
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
                category = item.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // GridView
        List<Mode> modes = initModes();
        ModeAdapter modeAdapter = new ModeAdapter(this, R.layout.grid_item, modes);
        binding.gridMode.setAdapter(modeAdapter);
        binding.gridMode.setOnItemClickListener((parent, view, position, id) -> {
            Mode selectedMode = (Mode) parent.getItemAtPosition(position);
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (i != position) {
                    View child = parent.getChildAt(i);
                    child.setBackgroundColor(BACKGROUND_NOT_SELECTED);
                }
            }
            view.setBackgroundColor(BACKGROUND_SELECTED);
            mode = selectedMode;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.tvTotalWords.setText("Всего слов: " + Vocabulary.count);
    }

    private boolean checkCardAmount() {
        String result = binding.editCardAmount.getText().toString();
        return !result.isEmpty() && Integer.valueOf(result) <= Vocabulary.count &&
                Integer.valueOf(result) > 0;
    }

    private List<Mode> initModes() {
        List<Mode> modes = new ArrayList<>();
        modes.add(new Mode("Карточки", R.drawable.ic_flashcarda));
        modes.add(new Mode("Обратные карточки", R.drawable.ic_flashcardb));
        modes.add(new Mode("Варианты", R.drawable.ic_varianta));
        modes.add(new Mode("Обратные варианты", R.drawable.ic_variantb));
        modes.add(new Mode("Написание", R.drawable.ic_typing));
        return modes;
    }
}