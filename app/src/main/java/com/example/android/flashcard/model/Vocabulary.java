package com.example.android.flashcard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vocabulary {
    private static final List<Word> allWords = new ArrayList<>();

    public static int count;
    public static int cardAmount;

    public static List<Word> getAllWords() {
        return allWords;
    }

    public static void add(Word word) {
        allWords.add(word);
    }

    public static List<Word> getRandomWords() {
        List<Word> words = new ArrayList<>(allWords);
        Collections.shuffle(words);
        return words.subList(0, cardAmount);
    }
}
