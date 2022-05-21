package com.example.android.flashcard.utils;

import android.content.Context;

import com.example.android.flashcard.R;
import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.enums.WordCategory;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static final String FILEPATH = "vocabulary.txt";
    private static final String JSON_FILE = "data.json";
    private static final int FILE_RESOURCE = R.raw.vocabulary;
    private static final int JSON_RESOURCE = R.raw.data;
    private static final Pattern PATTERN = Pattern.compile(
            "(.+)\\s=\\s(.+)\\s\\[(.+)\\]\\s\\((\\w+)\\)\\s(\\w+)\\s(.+)",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static boolean isRead = false;

    public static void readInnerFile(Context context) {
        if (isRead) return;
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + JSON_FILE);
        if (!file.exists()) {
            readRaw(context);
            return;
        }
        List<Word> words = JsonHelper.importFromJSON(context);
        for (Word word : words) {
            Vocabulary.getAllWords().add(word);
        }
        Vocabulary.count = Vocabulary.getAllWords().size();
        readJsonRaw(context);
        isRead = true;
    }

    public static void writeInnerFile(Context context, String path, String text) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    context.openFileOutput(path, Context.MODE_PRIVATE));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveChanges(Context context) {
        StringBuilder sb = new StringBuilder();
        List<Word> allWords = Vocabulary.getAllWords();
        Collections.sort(allWords, (word1, word2) -> word1.getName().compareToIgnoreCase(word2.getName()));
        JsonHelper.exportToJSON(context, Vocabulary.getAllWords());
        writeInnerFile(context, FILEPATH, sb.toString());
    }

    @Deprecated
    public static void readJsonRaw(Context context) {
        if (isRead) return;
        String string;
        StringBuilder sb = new StringBuilder();
        try (InputStream in = context.getResources().openRawResource(JSON_RESOURCE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((string = reader.readLine()) != null) {
                sb.append(string).append("\n");
            }
            List<Word> words = JsonHelper.importFromJSON(context); // TODO import another JSON file
            for (Word word : words) {
                String name = word.getName();
                String translation = word.getTranslation();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRead = true;
    }

    @Deprecated
    public static void readRaw(Context context) {
        if (isRead) return;
        String string;
        StringBuilder sb = new StringBuilder();
        try (InputStream in = context.getResources().openRawResource(FILE_RESOURCE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((string = reader.readLine()) != null) {
                sb.append(string).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = PATTERN.matcher(sb.toString());
        try {
            while (matcher.find()) {
                new Word(matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        WordCategory.valueOf(matcher.group(4)),
                        PartOfSpeech.valueOf(matcher.group(5).toUpperCase()),
                        Level.valueOf(matcher.group(6).toUpperCase())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveChanges(context);
        isRead = true;
    }
}
