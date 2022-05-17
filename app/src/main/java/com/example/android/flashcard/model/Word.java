package com.example.android.flashcard.model;

import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.enums.WordCategory;
import com.google.gson.annotations.Expose;

public class Word {
    @Expose
    private final int id;
    @Expose
    private String name;
    @Expose
    private String translation;
    @Expose
    private String transcription;
    @Expose
    private WordCategory category;
    @Expose
    private PartOfSpeech partOfSpeech;
    @Expose
    private Level level;

    public String getName() {
        return name;
    }

    public String getTranslation() {
        return translation;
    }

    public String getTranscription() {
        return transcription;
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

    public Word(String name, String translation, String transcription, WordCategory category,
                PartOfSpeech partOfSpeech, Level level) {
        id = Vocabulary.count++;
        this.name = name;
        this.translation = translation;
        this.transcription = transcription;
        this.category = category;
        this.partOfSpeech = partOfSpeech;
        this.level = level;
        Vocabulary.add(this);
    }

    public void edit(String name, String translation, WordCategory category, PartOfSpeech partOfSpeech, Level level) {
        this.name = name;
        this.translation = translation;
        this.category = category;
        this.partOfSpeech = partOfSpeech;
        this.level = level;
    }

    @Override
    public String toString() {
        return name + " = " + translation + " [" + transcription + "] (" + category + ") " +
                partOfSpeech + " " + level;
    }
}
