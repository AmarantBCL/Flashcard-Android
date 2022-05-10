package com.example.android.flashcard.viewmodel;

import com.example.android.flashcard.model.Word;

public interface UIChanger {
    void showWord(Word word, int order);

    void showAnswer();

    void showResult(int correct);
}
