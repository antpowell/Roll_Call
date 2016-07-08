package com.egmail.anthony.powell.roll_call;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
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
 private static final String VERSION_CODE = "versionCode";
 private static final String VERSION_NUMBER = "versionNumber";

 private String _lastName;
 private String _tNum;
 private String _email;
 private String _password;
 private String _course;
 private String key;
 private String _versionCode;
 private int _versionNumber;

 public Users() {
 }

 public Users(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _versionCode = studentInfo.getString(VERSION_CODE, "Not Found");
  _versionNumber = studentInfo.getInt(VERSION_NUMBER, -1);
 }

 public Users(Context context, String lastName, String tNum, String email, String password) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _lastName = lastName;
  _tNum = tNum;
  _email = email;
  _password = password;
  _versionCode = studentInfo.getString(VERSION_CODE, "Not Found");
  _versionNumber = studentInfo.getInt(VERSION_NUMBER, -1);

  addUser();
 }

 public Users(Context context, String lastName, String tNum) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _lastName = lastName;
  _tNum = tNum;
  _versionCode = studentInfo.getString(VERSION_CODE, "Not Found");
  _versionNumber = studentInfo.getInt(VERSION_NUMBER, -1);
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
  return studentInfo.getString(LAST, null) != null || studentInfo.getString(T, null) != null;
 }

 protected Users getUser(Context context) {
  studentInfo = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
  _course = studentInfo.getString(COURSE, "Not Found");
  if (!studentInfo.getString(COURSE, "Not Found").equals("Not Found")) {
   Users user = new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"));
   user.addCourse(studentInfo.getString(COURSE, "Not found"));
   return user;
  }
  return new Users(context, studentInfo.getString(LAST, "Not found"), studentInfo.getString(T, "Not found"));
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


}
