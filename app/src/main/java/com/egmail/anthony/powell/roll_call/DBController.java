package com.egmail.anthony.powell.roll_call;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

/**
 * Created by SGT_POWELL on 6/26/2016.
 */
public class DBController {
 //Web
 private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
 private DatabaseReference ref;
 private String _db;
 private static String postID, dbEnteryTime;


//    FirebaseDatabase rootDBRef = FirebaseDatabase.getInstance().getReference();


 protected DBController() {
  ref = firebaseDatabase.getReference("Users");
 }

 protected DBController(String db) {
  _db = db;
  ref = firebaseDatabase.getReference(db);
 }

 //Add user to DB
 public void addUser(Users user) {
        /*
        * Unique ID as parent
        * User data as child
        * User:{
        *       T Number:{
        *           name: name
        *           tNum: 0000000
        *           email: email@email.com
        *           time: 00:00
        *           }
        *       }*/
  ref.child(user.get_tNum()).setValue(user);

 }

 //Delete user form DB
 public void dropUser(Users user) {
  ref.child(user.get_tNum()).removeValue();

 }

 public String getKEY(Users user) {
  return user.get_tNum();
 }

 public DatabaseReference getDB() {
  return ref;
 }

}

