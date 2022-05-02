package com.example.android.flashcard.model;

public class Word {
    private final int id;
    private final String name;
    private final String translation;
    private final String transcription;
    private final WordCategory category;

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

    public Word(String name, String translation, String transcription, WordCategory category) {
        id = Vocabulary.count++;
        this.name = name;
        this.translation = translation;
        this.transcription = transcription;
        this.category = category;
        Vocabulary.add(this);
    }

    @Override
    public String toString() {
        return name + " = " + translation + " [" + transcription + "] (" + category + ")";
    }
}
