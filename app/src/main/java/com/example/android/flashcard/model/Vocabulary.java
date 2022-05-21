package com.example.android.flashcard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vocabulary {
    private static final List<Word> allWords = new ArrayList<>();

    public static int count;
    public static int cardAmount;
    public static int difficulty;
    public static String category;

    public static List<Word> getAllWords() {
        return allWords;
    }

    public static void add(Word word) {
        allWords.add(word);
    }

    public static List<Word> getRandomWords() {
        List<Word> words = new ArrayList<>();
        for (Word word : allWords) {
            if (!category.equals("ALL")) {
                if (category.equals(word.getCategory().toString())) {
                    if (word.getLevel().getOrder() == difficulty) {
                        words.add(word);
                    }
                }
            } else {
                if (word.getLevel().getOrder() == difficulty) {
                    words.add(word);
                }
            }
        }
        Collections.shuffle(words);
        if (cardAmount > words.size()) {
            cardAmount = words.size();
            return words.subList(0, words.size());
        }
        return words.subList(0, cardAmount);
    }

    public static void update() {
        count = allWords.size();
    }
}
