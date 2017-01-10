package com.egmail.anthony.powell.roll_call_2.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by powel on 1/10/2017.
 */

public class DataItemAdapter extends ArrayAdapter<String> {
 public DataItemAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
  super(context, resource, textViewResourceId, objects);
 }

 public DataItemAdapter(Context context, int resource) {
  super(context, resource);

 }

 public DataItemAdapter(Context context, int resource, int textViewResourceId) {
  super(context, resource, textViewResourceId);
 }

 public DataItemAdapter(Context context, int resource, List<String> objects) {
  super(context, resource, objects);
 }
}
