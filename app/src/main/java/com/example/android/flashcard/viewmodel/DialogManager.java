package com.example.android.flashcard.viewmodel;

import com.example.android.flashcard.model.Word;

public interface DialogManager {
    void showChange(Word word);

    void showAdd();

    void showInfo(Word word);
}
