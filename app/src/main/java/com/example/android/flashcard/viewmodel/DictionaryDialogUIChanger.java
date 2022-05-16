package com.example.android.flashcard.viewmodel;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.flashcard.R;
import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.enums.WordCategory;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordTemplate;
import com.example.android.flashcard.utils.StringUtils;

public class DictionaryDialogUIChanger {
    private View view;
    private EditText editName;
    private EditText editTranslation;
    private Spinner spinnerCategory;
    private Spinner spinnerPartOfSpeech;
    private Spinner spinnerLevel;

    public DictionaryDialogUIChanger(View view) {
        this.view = view;
        this.editName = view.findViewById(R.id.edit_word_name);
        this.editTranslation = view.findViewById(R.id.edit_word_translation);
        this.spinnerCategory = view.findViewById(R.id.spinner_category);
        this.spinnerPartOfSpeech = view.findViewById(R.id.spinner_part_of_speech);
        this.spinnerLevel = view.findViewById(R.id.spinner_difficulty);
    }

    public WordTemplate getWordTemplate() {
        return new WordTemplate(StringUtils.getWordText(editName), StringUtils.getWordText(editTranslation),
                WordCategory.valueOf(spinnerCategory.getSelectedItem().toString()),
                PartOfSpeech.valueOf(spinnerPartOfSpeech.getSelectedItem().toString()),
                Level.valueOf(spinnerLevel.getSelectedItem().toString()));
    }

    public boolean checkFields() {
        return (!editName.getText().toString().isEmpty() && !editTranslation.getText().toString().isEmpty());
    }

    public void autofill(Word word) {
        editName.setText(word.getName());
        editTranslation.setText(word.getTranslation());
        spinnerCategory.setSelection(word.getCategory().ordinal() + 1);
        spinnerPartOfSpeech.setSelection(word.getPartOfSpeech().ordinal());
        spinnerLevel.setSelection(word.getLevel().ordinal());
    }
}
