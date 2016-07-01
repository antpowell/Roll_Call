package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SGT_POWELL on 6/23/2016.
 */
public class CustomList extends ArrayAdapter<String> {


//    private final int listImageID;



    public CustomList(Context context, String[] itemText) {
        super(context, R.layout.course_selection_dialog, itemText);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItemView = inflater.inflate(R.layout.course_selection_dialog, parent, false);

        TextView listItemTextView = (TextView) listItemView.findViewById(R.id.listText);
        ImageView listItemImgView = (ImageView) listItemView.findViewById(R.id.listImg);


        listItemTextView.setText(getItem(position));
        if (getItem(position).contains("CS")){
            listItemImgView.setImageResource(R.drawable.browser);
        }else if (getItem(position).contains("MATH")){
            listItemImgView.setImageResource(R.drawable.calculator);
        }else if (getItem(position).contains("BIO")){
            listItemImgView.setImageResource(R.drawable.dna);
        }else if (getItem(position).contains("HIST")){
            listItemImgView.setImageResource(R.drawable.history);
        }else if (getItem(position).contains("ENG")){
            listItemImgView.setImageResource(R.drawable.bookshelf);
        }




        return listItemView;
    }


}

