package com.example.android.flashcard.utils;

import android.content.Context;

import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JsonHelper {
    private static final String FILE_NAME = "data.json";

    public static boolean exportToJSON(Context context, List<Word> dataList) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        DataItems dataItems = new DataItems();
        dataItems.setWords(dataList);
        //String jsonString = gson.toJson(Vocabulary.getAllWords());
        String jsonString = gson.toJson(dataItems);
        try (FileOutputStream fileOutputStream =
                     context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Word> importFromJSON(Context context) {
        try (FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
             InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getWords();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static class DataItems {
        @Expose
        private List<Word> words;

        public List<Word> getWords() {
            return words;
        }

        public void setWords(List<Word> words) {
            this.words = words;
        }
    }
}
