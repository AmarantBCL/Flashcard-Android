package com.example.android.flashcard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.flashcard.databinding.ActivityDictionaryBinding;
import com.example.android.flashcard.enums.Level;
import com.example.android.flashcard.enums.PartOfSpeech;
import com.example.android.flashcard.model.Vocabulary;
import com.example.android.flashcard.model.Word;
import com.example.android.flashcard.model.WordAdapter;
import com.example.android.flashcard.enums.WordCategory;
import com.example.android.flashcard.utils.FileUtils;
import com.example.android.flashcard.viewmodel.DialogManager;
import com.example.android.flashcard.viewmodel.DictionaryDialog;
import com.example.android.flashcard.viewmodel.DictionarySearcher;

public class DictionaryActivity extends AppCompatActivity {
    private ActivityDictionaryBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        DictionarySearcher searcher = new DictionarySearcher(binding, this);
        binding.btnAdd.setOnClickListener(v -> new DictionaryDialog(context, searcher).showAdd());
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void showSpinner() {
        // Spinner on AlertDialog
//        View view = getLayoutInflater().inflate(R.layout.dialog_add_word, null);
//        Spinner spinner = view.findViewById(R.id.spinner_category);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.categories));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        // Spinner on the main screen of the dicionary
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.categories));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spinnerTest.setAdapter(adapter);
    }
}