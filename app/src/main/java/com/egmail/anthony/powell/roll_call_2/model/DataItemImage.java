package com.egmail.anthony.powell.roll_call_2.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by powel on 3/13/2017.
 */

public class DataItemImage {

 FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
 DatabaseReference mRef = firebaseDatabase.getReference();
 DatabaseReference imageRef = mRef.child("Courses").child("Images");
 public static Map<String, String> courseImageMap = new HashMap<>();
 private static ArrayList<String> courseKeyList = new ArrayList<>();



 public DataItemImage(){
  this.getImages();
 }


 public void getImages(){
  imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(DataSnapshot dataSnapshot) {

    for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
//     Log.i("COURSE IMAGE", snapshot.toString());
     if(courseKeyList.contains(snapshot.getKey().toString())){
      //DO NOTHING
     }else{
      courseKeyList.add(snapshot.getKey().toString());
     }
     courseImageMap.put(snapshot.getKey(), snapshot.getValue().toString());
    }
//    Log.i("COURSE KEY", courseKeyList.toString());
   }

   @Override
   public void onCancelled(DatabaseError databaseError) {

   }
  });
 }

 public ArrayList<String> getCourseKeyList(){
  Log.i("COURSE KEY LIST", courseKeyList.toString());
  return courseKeyList;
 }

 public Map<String, String> getImageMap(){
//  Log.i("COURSE MAP", courseImageMap.toString());
  return courseImageMap;
 }

 public String getImageUrl(String titleForCourse){
  String URL;
  if(!courseImageMap.containsKey(titleForCourse)){
   URL = courseImageMap.get("DEFAULT");
//   TODO: Add this link to the default value in firebase database course images for the default value
//   URL = "https://image.flaticon.com/icons/svg/164/164953.svg";
//   Log.i("COURSE KEY LIST", titleForCourse+": Course Not In Database: "+ courseImageMap.containsKey(titleForCourse));
  }else{
   URL = courseImageMap.get(titleForCourse);
//   Log.i("COURSE KEY LIST",URL);
  }


  return URL;
 }


}
