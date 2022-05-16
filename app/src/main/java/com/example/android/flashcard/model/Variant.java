package com.example.android.flashcard.model;

import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.viewmodel.CardUIChanger;

public class Variant extends Card {
    public Variant(CardUIChanger cardUIChanger) {
        super(cardUIChanger);
    }

    @Override
    public void confirm() {
        if (getState() != CardState.ANSWER) {
            setState(CardState.ANSWER);
            getCardUiChanger().showAnswer();
        } else {
            answer(getCardUiChanger().isCorrect());
        }
    }
}
