package com.example.android.flashcard.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VariantOption {
    private List<Word> options;
    private Word word;

    public List<Word> getOptions() {
        return options;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public VariantOption(Word word) {
        this.word = word;
        options = Vocabulary.getAllWords().stream()
                .filter(x -> x.getName() != word.getName() &&
                        x.getTranslation() != word.getTranslation() &&
                        x.getPartOfSpeech() == word.getPartOfSpeech() &&
                        x.getLevel() == word.getLevel())
                .collect(Collectors.toList());
        Collections.shuffle(options);
        options = options.stream()
                .limit(3)
                .collect(Collectors.toList());
        options.add(word);
        Collections.shuffle(options);
    }

    public boolean isCorrect(String wordName, boolean isReversed) {
        return isReversed ? word.getName().equals(wordName) : word.getTranslation().equals(wordName);
    }

    public int getAnswerId(String wordName) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getName().equals(wordName) || options.get(i).getTranslation().equals(wordName)) {
                return i;
            }
        }
        return 0;
    }
}
