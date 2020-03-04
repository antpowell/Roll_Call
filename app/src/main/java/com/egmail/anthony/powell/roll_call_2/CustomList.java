package com.egmail.anthony.powell.roll_call_2;

import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.egmail.anthony.powell.roll_call_2.model.DataItemImage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SGT_POWELL on 6/23/2016.
 */
public class CustomList extends ArrayAdapter<String> {


//    private final int listImageID;
    private SearchView searchView;
    private Context context;
    WebView listItemImgView;


    public CustomList(Context context, ArrayList<String> itemText, SearchView searchView){
        super(context, R.layout.course_selection_dialog, itemText);
        DataItemImage images = new DataItemImage();
        images.getCourseKeyList();
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

    public void initView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View listItemView = inflater.inflate(R.layout.course_selection_dialog, parent, false);

        final TextView listItemTextView = (TextView) listItemView.findViewById(R.id.listText);

        listItemImgView = (WebView) listItemView.findViewById(R.id.listImg);
//        listItemImgView.setInitialScale(1);
        listItemImgView.setWebViewClient(new WebViewClient());
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

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.format("%s touched", listItemView.toString()), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View listItemView = inflater.inflate(R.layout.course_selection_dialog, parent, false);

        final TextView listItemTextView = (TextView) listItemView.findViewById(R.id.listText);
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

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, String.format("%s touched", listItemTextView.getText()), Toast.LENGTH_SHORT).show();
                CourseSelectionScreen courseSelectionScreen = (CourseSelectionScreen)context;
                courseSelectionScreen.courseSign(listItemTextView.getText().toString());
            }
        });

        return listItemView;
    }

    private void SetViewImage(String courseTitle){
        DataItemImage dataItemImage = new DataItemImage();

        dataItemImage.getImageUrl(courseTitle);
        listItemImgView.setWebViewClient(new WebViewClient());
        listItemImgView.loadUrl(dataItemImage.getImageUrl(courseTitle));
    }
}

