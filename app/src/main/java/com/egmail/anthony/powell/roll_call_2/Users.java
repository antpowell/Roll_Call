package com.egmail.anthony.powell.roll_call_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SGT_POWELL on 6/26/2016.
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
 private Context context;

 public Users() {
 }

 public Users(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  this.context = context;

 }

 public Users(Context context, String lastName, String tNum, String email, String password) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  this.context = context;
  this._lastName = lastName;
  this._tNum = tNum;
  this._email = email;
  this._password = password;


  addUser();
 }

 public Users(Context context, String lastName, String tNum) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  this.context = context;
  _lastName = lastName;
  _tNum = tNum;
  addUser();
 }

 //LOCAL STORAGE
 protected void addUser() {
  SharedPreferences.Editor edit = studentInfo.edit();
  edit.putString(LAST, _lastName).apply();
  edit.putString(T, _tNum).apply();
  edit.apply();
 }

 protected void addCourse(String course) {
  _course = course;
  SharedPreferences.Editor edit = studentInfo.edit();
  edit.putString(COURSE, _course).apply();
  edit.apply();
 }

 protected void dropUser(Context context) {
  //Clear local data
  SharedPreferences.Editor edit = studentInfo.edit().clear();
  edit.apply();
  if (hasUser()) Toast.makeText(context, "User Deleted...", Toast.LENGTH_SHORT).show();
 }

 protected boolean hasUser() {
  return !studentInfo.getString(LAST, "").equals("") || !studentInfo.getString(T, "").equals("");
 }

 protected Users getUser(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _course = studentInfo.getString(COURSE, "Not Found");
  if (!studentInfo.getString(COURSE, "Not Found").equals("Not Found")) {
   Users user = new Users(context, get_lastName(), get_tNum(), get_email(), null);
   user.addCourse(studentInfo.getString(COURSE, "Not found"));
   return user;
  }
  return new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"));
 }

 protected Users getUser(){
  Users user = new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"), get_email(), null);
  return user;
 }

 public String get_lastName() {
  return _lastName;
 }

 public String get_tNum() {
  return "T" + _tNum;
 }

 public String get_email() {
  return _email;
 }

 public String get_course() {
  return _course;
 }

 public String get_password() {
  return _password;
 }


}
