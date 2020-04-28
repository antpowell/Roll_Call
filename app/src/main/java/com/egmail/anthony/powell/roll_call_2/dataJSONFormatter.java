package com.egmail.anthony.powell.roll_call_2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SGT_POWELL on 7/4/2016.
 * @author Powell, Anthony
 *
 * Format data to JSON for POST/Put to database
 */
public class dataJSONFormatter {

 private String _courseNumber, _tNumber, _POD, _Date, _Time, _lName;
 Users _user;
 private Map<String, String> loginObject = new HashMap<String, String>();

 /**
  * Format User class object and the Password of the Day for database entry
  *
  * @param user {User}
  * @param POD {String}
  */
 dataJSONFormatter(Users user, String POD) {
  _user = user;
  _courseNumber = user.get_course();
  _tNumber = user.get_tNum();
  _lName = user.get_lastName();
  _Time = new SimpleDateFormat("HH:mm").format(new Date());
  _POD = POD;
  _Date = new SimpleDateFormat("dd/MM/yy").format(new Date());
 }


 /**
  * Set values for login object to be sent to the database
  *
  * @return loginObject {Map<String, String>}
  */
 Map<String, String> loginObject() {
  loginObject.put("Last Name", _lName);
  loginObject.put("Time", _Time);
  loginObject.put("POD", _POD);
  return loginObject;
 }


 /**
  * Returns course ID user selected from CourseSelectionScreen
  *
  * @return courseNumber {String}
  */
 String get_courseNumber() {
  return _courseNumber;
 }

 /**
  * Returns signed in user's T-Number
  *
  * @return tNumber {String}
  */
 String get_tNumber() {
  return _tNumber;
 }

 /**
  * Returns Password of the day user entered in SignIn activity
  *
  * @return POD {String}
  */
 public String get_POD() {
  return _POD;
 }

 /**
  * Returns signed in user's last name
  *
  * @return lName {String}
  */
 public String get_lName() {
  return _lName;
 }

}
