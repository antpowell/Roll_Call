package com.egmail.anthony.powell.roll_call_2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SGT_POWELL on 6/23/2016.
 */
public class CustomList extends ArrayAdapter<String> {


//    private final int listImageID;
    SearchView searchView;
    private ListView listView;
    private Map<String, String> listImgs;

    public CustomList(Context context, ArrayList<String> itemText, SearchView searchView){
        super(context, R.layout.course_selection_dialog, itemText);
//        get searchView from activity to filter through list
        this.searchView = searchView;
    }

    public CustomList(Context context, ArrayList<String> itemText, Map<String, String> imgs, SearchView searchView){
        super(context, R.layout.course_selection_dialog, itemText);
//        get searchView from activity to filter through list
        this.searchView = searchView;
    }


    public CustomList(Context context, String[] itemText, SearchView searchView) {
        super(context, R.layout.course_selection_dialog, itemText);
//        get searchView from activity to filter through list
        this.searchView = searchView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItemView = inflater.inflate(R.layout.course_selection_dialog, parent, false);

        TextView listItemTextView = (TextView) listItemView.findViewById(R.id.listText);
        ImageView listItemImgView = (ImageView) listItemView.findViewById(R.id.listImg);

        //Set up search view to query ListView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CustomList.this.getFilter().filter(newText);
                return false;
            }
        });

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

