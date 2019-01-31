package com.egmail.anthony.powell.roll_call;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SGT_POWELL on 7/4/2016.
 */
public class dataJSONFormatter {


 private String _courseNumber, _tNumber, _POD, _Date, _Time, _lName;
 Users _user;
 private Map<String, String> loginObject = new HashMap<String, String>();

 protected dataJSONFormatter(Users user, String POD) {
  _user = user;
  _courseNumber = user.get_course();
  _tNumber = user.get_tNum();
  _lName = user.get_lastName();
  _Time = new SimpleDateFormat("HH:mm").format(new Date());
  _POD = POD;
  _Date = new SimpleDateFormat("dd/MM/yy").format(new Date());
 }

 protected Map<String, String> loginObject() {
  loginObject.put("Last Name", _lName);
  loginObject.put("Time", _Time);
  loginObject.put("POD", _POD);
  return loginObject;
 }

 public String get_courseNumber() {
  return _courseNumber;
 }

 public String get_tNumber() {
  return _tNumber;
 }

 public String get_POD() {
  return _POD;
 }

 public String get_lName() {
  return _lName;
 }

}
