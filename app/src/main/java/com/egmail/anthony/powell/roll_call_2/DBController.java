package com.egmail.anthony.powell.roll_call_2;


import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SGT_POWELL on 6/26/2016.
 * @author Anthony Powell
 */
public class DBController {
 //Web
 private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
 private FirebaseAuth  authRef;
 private FirebaseUser _dbUser;
 private FirebaseAuth.AuthStateListener authListener;
 private DatabaseReference ref, courseRef;
 private String _db;
 private Map<String,String> _dbCourse;
 private ArrayList<String> courses = new ArrayList<>();


 private static String postID, dbEnteryTime;
 private boolean userWasCreated = false, userDeleted = false;
 private Users _user;
// private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//   .requestIdToken(getString())
//   .requestEmail()
//   .build();
 private Context context;


DBController(){
 courseRef = firebaseDatabase.getReference("Courses").child("Codes");
 authRef = FirebaseAuth.getInstance();
 authListener = new FirebaseAuth.AuthStateListener(){
  @Override
  public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
   FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

  }
 };

}

 protected DBController(Context c) {
  this();
  this.context = c;
  ref = firebaseDatabase.getReference("Users");
 }

 protected DBController(Context c, String db) {
  this();
  this.context = c;
  _db = db;
  ref = firebaseDatabase.getReference(db);
 }


 /**
  * Add user to Database
  *
  * @param user of type User
  */
 public void addUser(HashMap<String, String> user) {
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


  ref.child(user.get("TNum")).setValue(user);

 }

 //Delete user form DB

 /**
  * Remove user form Database
  * @param user of type User
  */
 public void dropUser(Users user) {
  DeleteUser();
//  UserSignOut(user);
  ref.child(user.get_tNum()).removeValue();
 }


 /**
  * Get user T-Number and return it as a key
  *
  * @param user of type User
  * @return T-Number to use as key
  */
 public String getKEY(Users user) {
  return user.get_tNum();
 }


 /**
  * Get database reference
  *
  * @return DatabaseReference ref
  */
 public DatabaseReference getDB() {
  return ref;
 }


 /**
  * Create Firebase Auth user in firebase using User data and return success status
  *
  * @param user of type User
  * @return Boolean userWasCreated
  */
 public boolean CreateUser(Users user) {
  if(user.hasUser()){
   authRef.createUserWithEmailAndPassword(user.get_email(), user.get_password())
     .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
       Log.d(String.valueOf(context), "createUserWithEmail:onComplete:" + task.isSuccessful());
       userWasCreated = true;
       if(!task.isSuccessful()){
        Toast.makeText(context, "Could not register you as a user, sorry!", Toast.LENGTH_SHORT).show();
        userWasCreated = false;
       }
      }
     });
  }
  return userWasCreated;
 }


 /**
  * Create user using Google Login and return success status
  *
  * @param user of type User
  * @return Boolean userWasCreated
  */
 public Boolean CreateUserGoogle(Users user){ return userWasCreated; }


 /**
  * Firebase Auth user sign in with User return success status
  *
  * @param user of type User
  * @return userWasCreated
  */
 public boolean UserSignIn(Users user){
//  Intent signInEvent = Auth.GoogleSignInApi.getSignInIntent();
  authRef.signInWithEmailAndPassword(user.get_email(), user.get_password())
    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
     @Override
     public void onComplete(@NonNull Task<AuthResult> task) {
      Log.d(String.valueOf(context), "signInWithEmail:onComplete:" + task.isSuccessful());
      userWasCreated = true;
      if(!task.isSuccessful()){
       Toast.makeText(context, "Could not sign you in at this time, sorry!", Toast.LENGTH_SHORT).show();
       userWasCreated = false;
      }
     }
    });
  return userWasCreated;
 }

 /**
  * Firebase Auth user sign out with User
  */
 public void UserSignOut(){
  authRef.signOut();
 }

/// Delete user from FirebaseDatebase

 /**
  * Firebase Auth delete currently signed in user
  */
 public void DeleteUser(){
  _dbUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
   @Override
   public void onComplete(@NonNull Task<Void> task) {
    if(task.isSuccessful()){
     Toast.makeText(context, "DB User deleted...", Toast.LENGTH_SHORT).show();
     userDeleted = true;
    }else{
     Toast.makeText(context, "User could not be deleted at this time...\nTry again.\nIf problem continues please contact us at roll.call.tsu@gmail.com", Toast.LENGTH_SHORT).show();
    }
    userDeleted = false;
   }
  });
 }

 /**
  * Start Firebase Auth listener
  */
 public void startListener(){
  authRef.addAuthStateListener(authListener);
 }


 /**
  * Stop Firebase Auth listener
  */
 public void stopListener(){
  if(authListener != null) {
   authRef.removeAuthStateListener(authListener);
  }
 }

 /**
  * Returns if user was created in Firebase Auth
  *
  * @return Boolean userWasCreated
  */
 public Boolean wasUserCreated(){
  return userWasCreated;
 }

 /**
  * Returns if the user was deleted
  *
  * @return Boolean userDeleted
  */
 public Boolean wasUserDeleted(){
  return userDeleted;
 }


 /**
  * Returns list of courses stored in the Database
  *
  * @return ArrayList<String> courses
  */
 public ArrayList<String> get_dbCourse() {
  courseRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(DataSnapshot dataSnapshot) {

    courses.addAll((ArrayList<String>)dataSnapshot.getValue());

    if(context!= null){
     Toast.makeText(context, "Made it", Toast.LENGTH_SHORT).show();
    }

   }
   @Override
   public void onCancelled(DatabaseError databaseError) {

   }
  });
  return courses;
 }

}


