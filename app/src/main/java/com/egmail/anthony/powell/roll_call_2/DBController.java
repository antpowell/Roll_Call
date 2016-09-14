package com.egmail.anthony.powell.roll_call_2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SGT_POWELL on 6/26/2016.
 */
public class DBController {
 //Web
 private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
 private FirebaseAuth  authRef;
 private FirebaseAuth.AuthStateListener authListener;
 private DatabaseReference ref;
 private String _db;
 private static String postID, dbEnteryTime;
 boolean userWasCreated = false;
// private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//   .requestIdToken(getString())
//   .requestEmail()
//   .build();
 private Context context;


//    FirebaseDatabase rootDBRef = FirebaseDatabase.getInstance().getReference();
DBController(){
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
  ref.child(user.get_tNum()).setValue(user);

 }

 //Delete user form DB
 public void dropUser(Users user) {
  UserSignOut(user);
  ref.child(user.get_tNum()).removeValue();
 }

 public String getKEY(Users user) {
  return user.get_tNum();
 }

 public DatabaseReference getDB() {
  return ref;
 }

 public boolean CreateUser(Users user) {
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
  return userWasCreated;
 }

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
 public void UserSignOut(Users user){
  authRef.signOut();
 }
 public void DeleteUser(Users user){
 }

 public void startListener(){
  authRef.addAuthStateListener(authListener);
 }

 public void stopLitener(){
  if(authListener != null) {
   authRef.removeAuthStateListener(authListener);
  }
 }

}


