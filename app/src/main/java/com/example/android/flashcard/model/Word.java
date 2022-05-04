package com.example.android.flashcard.model;

public class Word {
    private final int id;
    private final String name;
    private final String translation;
    private final String transcription;
    private final WordCategory category;
    private final PartOfSpeech partOfSpeech;
    private final Level level;

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

    @Override
    public String toString() {
        return name + " = " + translation + " [" + transcription + "] (" + category + ") " +
                partOfSpeech + " " + level;
    }
}
