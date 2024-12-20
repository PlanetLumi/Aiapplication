package com.example.aiapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CustomOptionAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] options;

    public CustomOptionAdapter(Context context, String[] options, int layout) {
        super(context, layout, options);
        this.context = context;
        this.options = options;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            int layout = setPalette.setLayout(context, "option_picker");
            convertView = inflater.inflate(layout, parent, false);
        }

        return convertView;
    }

}