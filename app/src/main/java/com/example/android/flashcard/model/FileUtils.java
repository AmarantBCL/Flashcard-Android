package com.example.android.flashcard.model;

import android.content.Context;
import android.widget.Toast;

import com.example.android.flashcard.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static final String FILEPATH = "vocabulary.txt";
    private static final int FILE_RESOURCE = R.raw.vocabulary;
    private static final Pattern PATTERN = Pattern.compile(
            "(.+)\\s=\\s(.+)\\s\\[(.+)\\]\\s\\((\\w+)\\)\\s(\\w+)\\s(.+)",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    public static void readRaw(Context context) {
        String string;
        StringBuilder sb = new StringBuilder();
        InputStream in = context.getResources().openRawResource(FILE_RESOURCE);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((string = reader.readLine()) != null) {
                sb.append(string).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        }
    }

    @Deprecated
    public static void readInnerFile(Context context) {
        FileInputStream fin;
        try {
            fin = context.openFileInput(FILEPATH);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
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
            }
        } catch (IOException e) {
            Toast.makeText(context, "Не смогли прочитать файл!", Toast.LENGTH_LONG).show();
        }
    }

    @Deprecated
    public static void writeInnerFile(Context context, String line) {
        StringBuilder sb = new StringBuilder().append(line);
        FileOutputStream fos;
        try {
            String text = sb.toString();
            fos = context.openFileOutput(FILEPATH, Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(context, "Файл записан!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
