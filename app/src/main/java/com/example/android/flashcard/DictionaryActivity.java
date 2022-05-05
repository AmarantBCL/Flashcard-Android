package com.example.android.flashcard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityDictionaryBinding;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordAdapter;

import java.util.ArrayList;
import java.util.List;

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
            Intent intent = new Intent(context, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
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
        AdapterView.OnItemLongClickListener longListender = (parent, v, position, id) -> {
            Word word = (Word) parent.getItemAtPosition(position);
            showWordInfo(word);
            return false;
        };
        wordList.setOnItemLongClickListener(longListender);
    }

    private void showNewWord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.dialog_add_word);
        builder.setTitle("Добавление слова");
        builder.setPositiveButton("Добавить", null);
        builder.setNegativeButton("Отмена", null);
        builder.create();
        builder.show();
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
        builder.setNeutralButton("Изменить", null);
        builder.create();
        builder.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}