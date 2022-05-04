package com.example.android.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.flashcard.databinding.ActivityCardBinding;
import com.example.android.flashcard.databinding.ActivityDictionaryBinding;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordAdapter;

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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });

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

        // список слов
        showWordList(Vocabulary.getAllWords());
    }

    private void showWordList(List<Word> words) {
        // получаем элемент ListView
        ListView wordList = binding.wordList;
        // создаем адаптер
        WordAdapter adapter = new WordAdapter(this, R.layout.word_item, words);
        // устанавливаем адаптер
        wordList.setAdapter(adapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // получаем выбранный пункт
                Word selectedState = (Word)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Выбрано слово " + selectedState.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        wordList.setOnItemClickListener(itemListener);
    }
}