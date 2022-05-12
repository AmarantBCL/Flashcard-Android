package com.example.android.flashcard.model;

import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.enums.WordCategory;

public class WordTemplate {
    private String name;
    private String translation;
    private WordCategory category;
    private PartOfSpeech partOfSpeech;
    private Level level;

    public String getName() {
        return name;
    }

    public String getTranslation() {
        return translation;
    }

    public WordCategory getCategory() {
        return category;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public Level getLevel() {
        return level;
    }

    public WordTemplate(String name, String translation, WordCategory category, PartOfSpeech partOfSpeech, Level level) {
        this.name = name;
        this.translation = translation;
        this.category = category;
        this.partOfSpeech = partOfSpeech;
        this.level = level;
    }
}
