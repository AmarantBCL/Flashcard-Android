package com.example.android.flashcard.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.flashcard.R;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordTemplate;
import com.example.android.flashcard.utils.FileUtils;

public class DictionaryDialog implements DialogManager {
    private final Context context;
    private DictionarySearcher searcher;
    private final DictionaryDialogUIChanger dialogUIChanger;
    private CardUIChanger cardUiChanger;
    private final View view;

    public DictionaryDialog(Context context, DictionarySearcher searcher) {
        this.context = context;
        this.searcher = searcher;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_word, null);
        dialogUIChanger = new DictionaryDialogUIChanger(view);
    }

    public DictionaryDialog(Context context, CardUIChanger cardUiChanger) {
        this.context = context;
        this.cardUiChanger = cardUiChanger;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_word, null);
        dialogUIChanger = new DictionaryDialogUIChanger(view);
    }

    @Override
    public void showAdd() {
        //showSpinner(); // TODO
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);
        builder.setTitle("Добавление слова");
        builder.setPositiveButton("Добавить", (dialog, i) -> {
            if (dialogUIChanger.checkFields()) {
                add(dialogUIChanger.getWordTemplate());
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
            if (dialogUIChanger.checkFields()) {
                change(word, dialogUIChanger.getWordTemplate());
            } else {
                Toast.makeText(view.getContext(), "Заполните слово и перевод.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Отмена", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        builder.show();
        dialogUIChanger.autofill(word);
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

    private void change(Word word, WordTemplate wordTemplate) {
        word.edit(wordTemplate.getName(), wordTemplate.getTranslation(),
                wordTemplate.getCategory(), wordTemplate.getPartOfSpeech(), wordTemplate.getLevel());
        FileUtils.saveChanges(view.getContext());
        updateUI();
        Toast.makeText(view.getContext(),"Слово '" + word.getName() + "' было отредактировано.",
                Toast.LENGTH_LONG).show();
    }

    private void add(WordTemplate wordTemplate) {
        new Word(wordTemplate.getName(), wordTemplate.getTranslation(), "-",
                wordTemplate.getCategory(), wordTemplate.getPartOfSpeech(), wordTemplate.getLevel());
        FileUtils.saveChanges(view.getContext());
        Vocabulary.update();
        updateUI();
        Toast.makeText(view.getContext(), "Слово '" + wordTemplate.getName() + " - " +
                wordTemplate.getTranslation() + "' успешно добавлено",Toast.LENGTH_LONG).show();
    }

    private void updateUI() {
        if (searcher != null) {
            searcher.update();
        }
        if (cardUiChanger != null) {
            cardUiChanger.update();
        }
    }
}
