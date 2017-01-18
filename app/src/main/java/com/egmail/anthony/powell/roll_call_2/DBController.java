package com.egmail.anthony.powell.roll_call_2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.egmail.anthony.powell.roll_call_2.model.DataItem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import java.util.List;
import java.util.Map;

/**
 * Created by SGT_POWELL on 6/26/2016.
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


//    FirebaseDatabase rootDBRef = FirebaseDatabase.getInstance().getReference();
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
  _user = user;
  ref.child(_user.get_tNum()).setValue(_user);

 }

 //Delete user form DB
 public void dropUser(Users user) {
  DeleteUser();
//  UserSignOut(user);
  ref.child(user.get_tNum()).removeValue();
 }

 public String getKEY(Users user) {
  return user.get_tNum();
 }

 public DatabaseReference getDB() {
  return ref;
 }


//Create User in Firebase Authentication
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
//Create user using Google Login
 public void CreateUserGoogle(Users user){

 }

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
 public void UserSignOut(){
  authRef.signOut();
 }

/// Delete user from FirebaseDatebase
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

 public void startListener(){
  authRef.addAuthStateListener(authListener);
 }

 public void stopLitener(){
  if(authListener != null) {
   authRef.removeAuthStateListener(authListener);
  }
 }

 public Boolean wasUserCreated(){
  return userWasCreated;
 }

 // Returns if the user was deleted
 public Boolean wasUserDeleted(){
  return userDeleted;
 }

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

// {8=MATH134, 1=CS124, 10=MATH136, 6=BIOL300, 7=BIOL443, 0=CS116-WE1, 4=BIOL231, 5=BIOL232, 11=CHEM131, 9=MATH135, 3=BIOL132, 2=BIOL131}
}


