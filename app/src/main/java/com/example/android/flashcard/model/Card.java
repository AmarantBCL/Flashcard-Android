package com.example.android.flashcard.model;

public interface Card {
    void show();

    boolean answer(boolean isCorrect);
}
