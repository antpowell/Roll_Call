package com.egmail.anthony.powell.roll_call;


import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SGT_POWELL on 6/26/2016.
 */
public class DBController {
    //Web
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference("Users");
    private static String KEY;


//    FirebaseDatabase rootDBRef = FirebaseDatabase.getInstance().getReference();


    protected DBController() {
    }

    //Add user to DB
    public void addUser(Users user) {
        /*
        * Unique ID as parent
        * User data as child
        * User:{
        *       ID:{
        *           name: name
        *           tNum: 0000000
        *           email: email@email.com
        *           time: 00:00
        *           }
        *       }*/

        ref.child(ref.push().getKey()).setValue(user);

    }

    //Delete user form DB
    public void dropUser() {
        ref.child(KEY).removeValue();
    }

    //    Update user's current DB entery
    public void updateUser(Users oldUser, Users newUser) {

    }

    public String getKEY() {
        return KEY;
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            KEY = dataSnapshot.getKey();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            //Check local DB for reference to this Child
            //if found remove it
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}

