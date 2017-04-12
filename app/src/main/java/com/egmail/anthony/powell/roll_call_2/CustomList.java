package com.egmail.anthony.powell.roll_call_2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.egmail.anthony.powell.roll_call_2.model.DataItemImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by SGT_POWELL on 6/23/2016.
 */
public class CustomList extends ArrayAdapter<String> {


//    private final int listImageID;
    SearchView searchView;
    private ListView listView;
    private ArrayList<String> listImgKeys;
    private Map<String, String> URLMAP;
    private DataItemImage images = new DataItemImage();
    private Context context;
    WebView listItemImgView;


    public CustomList(Context context, ArrayList<String> itemText, SearchView searchView){
        super(context, R.layout.course_selection_dialog, itemText);
        DataItemImage images = new DataItemImage();
        listImgKeys = images.getCourseKeyList();
        URLMAP = images.getImageMap();
        this.context = context;
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
        listItemImgView = (WebView) listItemView.findViewById(R.id.listImg);
//        listItemImgView.setInitialScale(1);
        listItemImgView.loadUrl("https://image.flaticon.com/icons/svg/164/164953.svg");
        listItemImgView.setBackgroundColor(Color.TRANSPARENT);

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
        /**
         *  FIXME: Regex does remove trailing 'L' for lab courses which return a course that will never be in DB, I.E. "BIOL231L" will return "BIOLL".
         **/

        SetViewImage(getItem(position).toString().replaceAll("[^a-zA-Z ]", ""));



        return listItemView;
    }

    private void SetViewImage(String courseTitle){
        DataItemImage dataItemImage = new DataItemImage();

        dataItemImage.getImageUrl(courseTitle);
        listItemImgView.loadUrl(dataItemImage.getImageUrl(courseTitle));
    }


}

