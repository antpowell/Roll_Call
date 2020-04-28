package com.egmail.anthony.powell.roll_call_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by SGT_POWELL on 6/26/2016.
 * @author Powell, Anthony
 *
 * Local storage for user data.
 *
 */
public class Users {
 //Local
 private SharedPreferences studentInfo;
 private static final String TAG = "tag";
 private static final String LAST = "last";
 private static final String COURSE = "course";
 private static final String T = "tNum";

 private String _lastName;
 private String _tNum;
 private String _email;
 private String _password;
 private String _course;
 private HashMap<String, String> userMap = new HashMap<>();

 public Users() {
 }

 public Users(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);

 }

 public Users(Context context, String lastName, String tNum, String email, String password) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  this._lastName = lastName;
  this._tNum = tNum;
  this._email = email;
  this._password = password;


  addUser();
  setUserMap();

 }

 public Users(Context context, String lastName, String tNum) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _lastName = lastName;
  _tNum = tNum;
  addUser();
 }

 //LOCAL STORAGE

 /**
  * Stores user data into local storage also known as SharedPreferences
  */
 private void addUser() {
  SharedPreferences.Editor edit = studentInfo.edit();
  edit.putString(LAST, _lastName).apply();
  edit.putString(T, _tNum).apply();
  edit.apply();
 }

 /**
  * Stores course into local storage
  *
  * @param course {String}
  */
 void addCourse(String course) {
  _course = course;
  SharedPreferences.Editor edit = studentInfo.edit();
  edit.putString(COURSE, _course).apply();
  edit.apply();
 }

 /**
  * Remove user from local storage
  *
  * @param context {Context}
  */
 void dropUser(Context context) {
  //Clear local data
  SharedPreferences.Editor edit = studentInfo.edit().clear();
  edit.apply();
  if (hasUser()) Toast.makeText(context, "User Deleted...", Toast.LENGTH_SHORT).show();
 }

 /**
  * Returns true if user exist or false if no use is found in the SharedPreferences.
  * @return hasUser {boolean}
  */
 boolean hasUser() {
  return !studentInfo.getString(LAST, "").equals("") || !studentInfo.getString(T, "").equals("");
 }

 /**
  * Return current User object
  *
  * @param context {Context}
  * @return user {User}
  */
 Users getUser(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _course = studentInfo.getString(COURSE, "Not Found");
  if (!studentInfo.getString(COURSE, "Not Found").equals("Not Found")) {
   Users user = new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"));
   user.addCourse(studentInfo.getString(COURSE, "Not found"));
   return user;
  }
  return new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"));
 }

 /**
  * Return user's last name
  *
  * @return lastName {String}
  */
 String get_lastName() {
  return _lastName;
 }

 /**
  * Return T-Number {String}
  *
  * @return tNum
  */
 String get_tNum() {
  return "T" + _tNum;
 }

 /**
  * Return user's email
  *
  * @return email {String}
  */
 String get_email() {
  return _email;
 }

 /**
  * Return course
  *
  * @return course {String}
  */
 String get_course() {
  return _course;
 }

 /**
  * Return user password - CAUTION CAUTION CAUTION ->SHOULD NEVER BE USED<- CAUTION CAUTION CAUTION
  *
  * @return _password {String}
  */
 String get_password() { return _password;}


 /**
  * Returns dictionary of user data.
  *
  * @return userMap {HashMap<String, String>}
  */
 HashMap<String, String> getUserMap() {
  return userMap;
 }

 /**
  * Setup {User} dictionary for storage purposes.
  */
 private void setUserMap() {
  if (!_lastName.isEmpty() && !_tNum.isEmpty()) {

   userMap.put("Last Name", _lastName);
   userMap.put("TNum", "T"+_tNum);

  }else{

  }
  if(!_email.isEmpty()){
   userMap.put("Email", _email);
  }
 }
}
