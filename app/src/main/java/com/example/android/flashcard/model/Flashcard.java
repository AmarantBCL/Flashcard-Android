package com.example.android.flashcard.model;

import java.util.List;

public class Flashcard implements Card {
    private final UIChanger uiChanger;
    private final List<Word> wordSet;
    private int correct;
    private boolean isFinished = false;

    public boolean isFinished() {
        return isFinished;
    }

    public Flashcard(UIChanger uiChanger) {
        this.uiChanger = uiChanger;
        wordSet = Vocabulary.getRandomWords();
        show();
    }

    @Override
    public void show() {
        if (wordSet.size() <= 0 && !isFinished) {
            end();
            return;
        }
        if (isFinished) return;
        Word word = wordSet.get(0);
        uiChanger.showWord(word, wordSet.size());
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
        uiChanger.showAnswer();
    }

    public void end() {
        uiChanger.showResult(correct);
        isFinished = true;
    }
}
