package com.example.android.flashcard.model;

import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.viewmodel.CardUIChanger;

import java.util.List;

public abstract class Card {
    private final CardUIChanger cardUiChanger;
    private final List<Word> wordSet;
    private CardState state;
    private Word word;
    private int correct;

    public CardUIChanger getCardUiChanger() {
        return cardUiChanger;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public Card(CardUIChanger cardUiChanger) {
        this.cardUiChanger = cardUiChanger;
        wordSet = Vocabulary.getRandomWords();
    }

    public void show() {
        if (wordSet.size() <= 0 && getState() != CardState.RESULT) {
            end();
            return;
        }
        if (getState() == CardState.RESULT) return;
        setWord(wordSet.get(0));
        setState(CardState.QUESTION);
        getCardUiChanger().showWord(getWord(), wordSet.size());
    }

    public void answer(boolean isCorrect) {
        if (wordSet.size() > 0) {
            correct += isCorrect ? 1 : 0;
            wordSet.remove(0);
            show();
        }
    }

    public void confirm() {
        setState(CardState.ANSWER);
        getCardUiChanger().showAnswer();
    }

    public void end() {
        getCardUiChanger().showResult(correct);
        setState(CardState.RESULT);
    }
}
