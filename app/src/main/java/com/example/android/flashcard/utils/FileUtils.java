package com.example.android.flashcard.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.android.flashcard.R;
import com.example.android.flashcard.model.Level;
import com.example.android.flashcard.model.PartOfSpeech;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordCategory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static final String FILEPATH = "vocabulary.txt";
    private static final int FILE_RESOURCE = R.raw.vocabulary;
    private static final Pattern PATTERN = Pattern.compile(
            "(.+)\\s=\\s(.+)\\s\\[(.+)\\]\\s\\((\\w+)\\)\\s(\\w+)\\s(.+)",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static boolean isRead = false;

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
        while (matcher.find()) {
            new Word(matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    WordCategory.valueOf(matcher.group(4)),
                    PartOfSpeech.valueOf(matcher.group(5).toUpperCase()),
                    Level.valueOf(matcher.group(6).toUpperCase())
            );
            StringBuilder b = new StringBuilder();
            // abandon = покидать, оставлять [əˈbændən] (NONE) verb B2
            b.append(matcher.group());
            writeInnerFile(context, b.toString());
        }
        isRead = true;
    }

    @Deprecated
    public static void readInnerFile(Context context) {
        if (isRead) return;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream in = context.openFileInput(FILEPATH)) {
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            String text = new String(bytes);
            Matcher matcher = PATTERN.matcher(text);
            while (matcher.find()) {
                new Word(matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        WordCategory.valueOf(matcher.group(4)),
                        PartOfSpeech.valueOf(matcher.group(5).toUpperCase()),
                        Level.valueOf(matcher.group(6).toUpperCase())
                );
                sb.append(matcher.group() + "\n");
            }

        } catch (IOException e) {
            Toast.makeText(context, "Не смогли прочитать файл!", Toast.LENGTH_LONG).show();
        }
        writeInnerFile(context, sb.toString());
        isRead = true;
    }

    @Deprecated
    public static void writeInnerFile(Context context, String line) {
        //StringBuilder sb = new StringBuilder(line);
        //FileOutputStream fos;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(FILEPATH, Context.MODE_PRIVATE));
            outputStreamWriter.write(line);
            outputStreamWriter.close();
            //fos = context.openFileOutput(FILEPATH, Context.MODE_PRIVATE);
            //String text = sb.toString();
            //fos.write(text.getBytes());
            //fos.close();
            // TODO the entire problem was this toast!
            //Toast.makeText(context, "Файл записан!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveChanges(Context context) {
        StringBuilder sb = new StringBuilder();
        try (FileOutputStream fos = context.openFileOutput(FILEPATH, Context.MODE_APPEND)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            List<Word> allWords = Vocabulary.getAllWords();
            for (Word word : allWords) {
                String text = String.format("%s = %s [%s] (%s) %s %s\n",
                        word.getName(),
                        word.getTranslation(),
                        word.getTranscription(),
                        word.getCategory().name(),
                        word.getPartOfSpeech().name().toLowerCase(),
                        word.getLevel().name());
                sb.append(text);
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
