package com.egmail.anthony.powell.roll_call_2;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
 private DatabaseReference ref;
 private String _db;
 private static String postID, dbEnteryTime;
// private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//   .requestIdToken(getString())
//   .requestEmail()
//   .build();
 private FirebaseAuth auth;
 private Context cx;


//    FirebaseDatabase rootDBRef = FirebaseDatabase.getInstance().getReference();


 protected DBController(Context c) {
  cx = c;
  ref = firebaseDatabase.getReference("Users");
 }

 protected DBController(Context c, String db) {
  cx = c;
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

 public boolean CreateUser(Users user){
//  FirebaseAuth mAuth;
//  mAuth = FirebaseAuth.getInstance();
//
//  mAuth.createUserWithEmailAndPassword(cx, new OnCompleteListener<AuthResult>(){
//   @Override
//   public void onComplete(@NonNull Task<AuthResult> task) {
//
////    if(!task.isSuccessful()){
////     Toast.makeText(cx, "Sorry could not register you as a user at the moment... \nTry again shortly.", Toast.LENGTH_SHORT).show();
////    }
//   }
//  });

  return false;
 }
 public void CreateUserGoogle(Users user){

 }
 public boolean UserSignIn(Users user){
//  Intent signInEvent = Auth.GoogleSignInApi.getSignInIntent();
  return false;
 }
 public boolean UserSignOut(Users user){
  return false;
 }
 public boolean DeleteUser(Users user){
  return false;
 }

}

