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

public class ModeAdapter extends ArrayAdapter<Mode> {
    private LayoutInflater inflater;
    private int layout;
    private List<Mode> modes;

    public ModeAdapter(Context context, int resource, List<Mode> modes) {
        super(context, resource, modes);
        this.modes = modes;
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
        Mode mode = modes.get(position);
        viewHolder.image.setImageResource(mode.getImage());
        viewHolder.text.setText(mode.getName());
        return convertView;
    }

    private class ViewHolder {
        final ImageView image;
        final TextView text;

        ViewHolder(View view) {
            image = view.findViewById(R.id.img_mode);
            text = view.findViewById(R.id.tv_mode);
        }
    }
}
