package com.example.prans.sanskrit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pranshu on 16-07-2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceid) {
        super(context, 0, words);
        colorResourceId = colorResourceid;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView; //The adapters are built to reuse Views, when a View is scrolled so that is no longer visible,
        // it can be used for one of the new Views appearing. This reused View is the (convertView).
        // If this is null it means that there is no recycled View and we have to create a new one,
        // otherwise we should use it to avoid creating a new.

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        Word word = (Word) getItem(position);

        TextView defaultTextView = (TextView) listView.findViewById(R.id.itemtv1);
        TextView sanskritTextView = (TextView) listView.findViewById(R.id.itemtv2);
        ImageView imageView = (ImageView) listView.findViewById(R.id.imgv1);

        defaultTextView.setText(word.getDefaultTranslation());
        sanskritTextView.setText(word.getSanskritTranslation());

        if (word.hasImage()) {
            imageView.setImageResource(word.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listView.findViewById(R.id.ll1); // Set the theme of color for the item.xml file.
        int color = ContextCompat.getColor(getContext(), colorResourceId);// Find the color that the resource id maps to.
        textContainer.setBackgroundColor(color); // Set the background color of the text Container View.

        return listView;
    }
}














