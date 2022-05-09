package com.example.android.flashcard.model;

public interface UIChanger {
    void showWord(Word word, int order);

    void showAnswer();

    void showResult(int correct);
}
