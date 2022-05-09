package com.example.android.flashcard.model;

public interface Card {
    void show();

    void answer(boolean isCorrect);

    void end();
}
