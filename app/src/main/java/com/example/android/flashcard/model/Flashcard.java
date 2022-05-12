package com.example.android.flashcard.model;

import com.example.android.flashcard.enums.CardState;
import com.example.android.flashcard.viewmodel.CardUIChanger;

import java.util.List;

public class Flashcard implements Card {
    private final CardUIChanger cardUiChanger;
    private final List<Word> wordSet;
    private int correct;
    private Word word;
    private CardState state;

    public CardUIChanger getUiChanger() {
        return cardUiChanger;
    }

    public Word getWord() {
        return word;
    }

    public CardState getState() {
        return state;
    }

    public Flashcard(CardUIChanger cardUiChanger) {
        this.cardUiChanger = cardUiChanger;
        wordSet = Vocabulary.getRandomWords();
    }

    @Override
    public void show() {
        if (wordSet.size() <= 0 && state != CardState.RESULT) {
            end();
            return;
        }
        if (state == CardState.RESULT) return;
        word = wordSet.get(0);
        state = CardState.QUESTION;
        cardUiChanger.showWord(word, wordSet.size());
    }

    @Override
    public void answer(boolean isCorrect) {
        if (wordSet.size() > 0) {
            correct += isCorrect ? 1 : 0;
            wordSet.remove(0);
            show();
        }
    }

    public void confirm() {
        state = CardState.ANSWER;
        cardUiChanger.showAnswer();
    }

    public void end() {
        cardUiChanger.showResult(correct);
        state = CardState.RESULT;
    }
}
