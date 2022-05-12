package com.example.android.flashcard.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.flashcard.R;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private LayoutInflater inflater;
    private int layout;
    private List<Word> words;

    public WordAdapter(Context context, int resource, List<Word> words) {
        super(context, resource, words);
        this.words = words;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Word word = words.get(position);
        viewHolder.wordName.setText(word.getName());
        viewHolder.translation.setText(word.getTranslation());
        String category = word.getCategory().toString().toLowerCase();
        int resId = getContext().getResources().getIdentifier(category,
                "drawable", getContext().getPackageName());
        viewHolder.image.setImageResource(resId);
        return convertView;
    }

    private class ViewHolder {
        final ImageView image;
        final TextView wordName, translation;

        ViewHolder(View view) {
            wordName = view.findViewById(R.id.word);
            translation = view.findViewById(R.id.translation);
            image = view.findViewById(R.id.category);
        }
    }
}
