package com.example.android.flashcard.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.flashcard.R;
import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.enums.WordCategory;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.utils.FileUtils;
import com.example.android.flashcard.utils.StringUtils;

public class DictionaryDialog implements DialogManager {
    private Context context;
    private DictionarySearcher searcher;
    private View view;
    private EditText editName;
    private EditText editTranslation;
    private Spinner spinnerCategory;
    private Spinner spinnerPartOfSpeech;
    private Spinner spinnerLevel;

    public DictionaryDialog(Context context, DictionarySearcher searcher) {
        this.context = context;
        this.searcher = searcher;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_word, null);
        loadViews();
    }

    @Override
    public void showAdd() {
        //showSpinner(); // TODO
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);
        builder.setTitle("Добавление слова");
        builder.setPositiveButton("Добавить", (dialog, i) -> {
            if (!editName.getText().toString().isEmpty() && !editTranslation.getText().toString().isEmpty()) {
                String wordName = editName.getText().toString().toLowerCase().trim();
                String wordTranslation = editTranslation.getText().toString().toLowerCase().trim();
                Toast.makeText(view.getContext(),
                        "Слово '" + wordName + " - " + wordTranslation + "' успешно добавлено",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "Укажите слово и перевод.", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Отмена", null);
        builder.create();
        builder.show();
    }

    @Override
    public void showChange(Word word) {
        //showSpinner(); // TODO
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);
        builder.setTitle("Изменение слова " + word.getName());
        builder.setPositiveButton("Подтвердить", (dialog, answer) -> {
            if (!editName.getText().toString().isEmpty() && !editTranslation.getText().toString().isEmpty()) {
                change(word, StringUtils.getWordText(editName), StringUtils.getWordText(editTranslation),
                        WordCategory.valueOf(spinnerCategory.getSelectedItem().toString()),
                        PartOfSpeech.valueOf(spinnerPartOfSpeech.getSelectedItem().toString()),
                        Level.valueOf(spinnerLevel.getSelectedItem().toString()));
            } else {
                Toast.makeText(view.getContext(), "Заполните слово и перевод.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Отмена", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        builder.show();
        autofill(word);
    }

    @Override
    public void showInfo(Word word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(word.getName());
        builder.setIcon(context.getResources().getIdentifier(word.getCategory().toString().toLowerCase(),
                "drawable", context.getPackageName()));
        builder.setMessage(word.getTranslation() + "\n" +
                "Категория: " + word.getCategory() + "\n" +
                "Часть речи: " + word.getPartOfSpeech().toString().toLowerCase() + "\n" +
                "Уровень: " + word.getLevel());
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Изменить", (dialog, i) -> showChange(word));
        builder.setNeutralButton("Удалить", (dialog, i) -> {
            Toast.makeText(context, "Недоступно.", Toast.LENGTH_LONG).show();
        });
        builder.create();
        builder.show();
    }

    private void change(Word word, String name, String translation, WordCategory category,
                        PartOfSpeech partOfSpeech, Level level) {
        word.edit(name, translation, category, partOfSpeech, level);
        FileUtils.saveChanges(view.getContext());
        searcher.update();
        Toast.makeText(view.getContext(),
                "Слово '" + word.getName() + "' было отредактировано.",
                Toast.LENGTH_LONG).show();
    }

    private void add() {
        Toast.makeText(context, "add", Toast.LENGTH_LONG).show();
    }

    private void loadViews() {
        editName = view.findViewById(R.id.edit_word_name);
        editTranslation = view.findViewById(R.id.edit_word_translation);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        spinnerPartOfSpeech = view.findViewById(R.id.spinner_part_of_speech);
        spinnerLevel = view.findViewById(R.id.spinner_difficulty);
    }

    private void autofill(Word word) {
        editName.setText(word.getName());
        editTranslation.setText(word.getTranslation());
        spinnerCategory.setSelection(word.getCategory().ordinal());
        spinnerPartOfSpeech.setSelection(word.getPartOfSpeech().ordinal());
        spinnerLevel.setSelection(word.getLevel().ordinal());
    }
}
