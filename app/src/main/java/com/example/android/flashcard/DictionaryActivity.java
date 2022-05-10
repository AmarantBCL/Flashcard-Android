package com.example.android.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityDictionaryBinding;
import com.example.android.flashcard.databinding.DialogAddWordBinding;
import com.example.android.flashcard.model.Level;
import com.example.android.flashcard.model.PartOfSpeech;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordAdapter;
import com.example.android.flashcard.model.WordCategory;
import com.example.android.flashcard.utils.FileUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DictionaryActivity extends AppCompatActivity {
    private ActivityDictionaryBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        binding.btnAdd.setOnClickListener(v -> {
            showNewWord();
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        searchWords();
    }

    private void searchWords() {
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            private String enteredStr = "";

            @Override
            public void afterTextChanged(Editable s) {
                List<Word> words = new ArrayList<>();
                for (Word word : Vocabulary.getAllWords()) {
                    if (word.getName().length() >= enteredStr.length()) {
                        if (enteredStr.equals(word.getName().substring(0, enteredStr.length()))) {
                            words.add(word);
                        }
                    }
                    if (word.getTranslation().length() >= enteredStr.length()) {
                        if (enteredStr.equals(word.getTranslation().substring(0, enteredStr.length()))) {
                            words.add(word);
                        }
                    }
                }
                showWordList(words);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enteredStr = s.toString().toLowerCase();
            }
        });
        showWordList(Vocabulary.getAllWords());
    }

    private void showWordList(List<Word> words) {
        ListView wordList = binding.wordList;
        WordAdapter adapter = new WordAdapter(this, R.layout.word_item, words);
        wordList.setAdapter(adapter);
        AdapterView.OnItemLongClickListener longListener = (parent, v, position, id) -> {
            Word word = (Word) parent.getItemAtPosition(position);
            showWordInfo(word);
            return false;
        };
        wordList.setOnItemLongClickListener(longListener);
    }

    private void showNewWord() {
        showSpinner();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.dialog_add_word);
        builder.setTitle("Добавление слова");
        builder.setPositiveButton("Добавить", null);
        builder.setNegativeButton("Отмена", null);
        builder.create();
        builder.show();
    }

    private void showChangeWord(Word word) {
        showSpinner();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_word, null);
        builder.setView(view);
        builder.setTitle("Изменение слова " + word.getName());
        builder.setPositiveButton("Подтвердить", (dialog, answer) -> {
            EditText name = view.findViewById(R.id.edit_word_name);
            EditText translation = view.findViewById(R.id.edit_word_translation);
            Spinner category = view.findViewById(R.id.spinner_category);
            Spinner partOfSpeech = view.findViewById(R.id.spinner_part_of_speech);
            Spinner level = view.findViewById(R.id.spinner_difficulty);
            if (answer == DialogInterface.BUTTON_POSITIVE) {
                word.edit(name.getText().toString().toLowerCase().trim(),
                        translation.getText().toString().toLowerCase().trim(),
                        WordCategory.valueOf(category.getSelectedItem().toString()),
                        PartOfSpeech.valueOf(partOfSpeech.getSelectedItem().toString()),
                        Level.valueOf(level.getSelectedItem().toString())
                );
                //FileUtils.saveChanges(view.getContext());
                showWordList(Vocabulary.getAllWords());
                showToast("Слово " + word.getName() + " было отредактировано.");
            }
        });
        builder.setNegativeButton("Отмена", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        builder.show();
        autoFillDialog(view, word);
    }

    private void autoFillDialog(View view, Word word) {
        EditText name = view.findViewById(R.id.edit_word_name);
        EditText translation = view.findViewById(R.id.edit_word_translation);
        Spinner category = view.findViewById(R.id.spinner_category);
        Spinner partOfSpeech = view.findViewById(R.id.spinner_part_of_speech);
        Spinner level = view.findViewById(R.id.spinner_difficulty);
        name.setText(word.getName());
        translation.setText(word.getTranslation());
        category.setSelection(word.getCategory().ordinal());
        partOfSpeech.setSelection(word.getPartOfSpeech().ordinal());
        level.setSelection(word.getLevel().ordinal());
    }

    private void showWordInfo(Word word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(word.getName());
        builder.setIcon(getResources().getIdentifier(word.getCategory().toString().toLowerCase(),
                "drawable", context.getPackageName()));
        builder.setMessage(word.getTranslation() + "\n" +
                "Категория: " + word.getCategory() + "\n" +
                "Часть речи: " + word.getPartOfSpeech().toString().toLowerCase() + "\n" +
                "Уровень: " + word.getLevel());
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Изменить", (dialog, i) -> {
            showChangeWord(word);
        });
        builder.setNeutralButton("Удалить", (dialog, i) -> {
            showToast("Недоступно");
        });
        builder.create();
        builder.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showSpinner() {
        // Spinner on AlertDialog
//        View view = getLayoutInflater().inflate(R.layout.dialog_add_word, null);
//        Spinner spinner = view.findViewById(R.id.spinner_category);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.categories));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        // Spinner on the main screen of the dicionary
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.categories));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spinnerTest.setAdapter(adapter);
    }
}