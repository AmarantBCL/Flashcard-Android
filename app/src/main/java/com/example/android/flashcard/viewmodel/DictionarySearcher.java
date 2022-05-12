package com.example.android.flashcard.viewmodel;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.flashcard.R;
import com.example.android.flashcard.databinding.ActivityDictionaryBinding;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordAdapter;

import java.util.ArrayList;
import java.util.List;

public class DictionarySearcher {
    private final ActivityDictionaryBinding binding;
    private final Context context;
    private WordAdapter adapter;
    private String searchInput = "";

    public DictionarySearcher(ActivityDictionaryBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        initClicks();
        createList(Vocabulary.getAllWords());
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }

    private void initClicks() {
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                search(searchInput);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchInput = s.toString().toLowerCase();
            }
        });
    }

    private void search(String searchInput) {
        List<Word> words = new ArrayList<>();
        for (Word word : Vocabulary.getAllWords()) {
            if (word.getName().length() >= searchInput.length()) {
                if (searchInput.equals(word.getName().substring(0, searchInput.length()))) {
                    words.add(word);
                }
            }
            if (word.getTranslation().length() >= searchInput.length()) {
                if (searchInput.equals(word.getTranslation().substring(0, searchInput.length()))) {
                    words.add(word);
                }
            }
        }
        if (searchInput.isEmpty()) {
            createList(Vocabulary.getAllWords());
        } else {
            createList(words);
        }
    }

    private void createList(List<Word> words) {
        ListView wordList = binding.wordList;
        adapter = new WordAdapter(context, R.layout.word_item, words);
        wordList.setAdapter(adapter);
        AdapterView.OnItemLongClickListener longListener = (parent, v, position, id) -> {
            Word word = (Word) parent.getItemAtPosition(position);
            new DictionaryDialog(context, this).showInfo(word);
            return false;
        };
        wordList.setOnItemLongClickListener(longListener);
    }
}
