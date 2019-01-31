package com.egmail.anthony.powell.roll_call;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class SignIn extends AppCompatActivity {

 public static final String TAG = "tag";
 public static final String PROXYTAG = "proxy_tag";
 public static final String LAST = "last";
 public static final String T = "tNum";
 public static final String COURSE = "course";
 private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
 private dataJSONFormatter dataJSONFormatter;
 //    private final int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
//    public int MY_PERMISSIONS_REQUEST_SEND_SMS;


 private EditText password;

 private SharedPreferences proxyInfo;
 private Users user;

 private String studentLAST, studentID, c, POD;
 private final static String contactNumber = /*"8327418926"*/"7138998111";
 private String studentMessage, proxyMessage;
 private BroadcastReceiver broadcastReceiver;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.sing_in_activity);
  user = new Users().getUser(this);

  proxyInfo = getSharedPreferences(PROXYTAG, MODE_PRIVATE);

  password = findViewById(R.id.editText);

  studentLAST = user.get_lastName();
  studentID = user.get_tNum();
  c = user.get_course();

  setTitle(this, c);


  //Click sign in button saves password of the day entry then sends the sharedpreferences as a text message
  final Button signInButton = findViewById(R.id.signIn_button);
  signInButton.setOnClickListener(new View.OnClickListener() {


   @Override
   public void onClick(View v) {
    String proxyLAST = proxyInfo.getString(LAST, "Not found");
    String proxyID = proxyInfo.getString(T, "Not found");
    POD = password.getText().toString();

    final Date ts = new Date();
    final String date = new SimpleDateFormat("dd/MM/yy HH:mm").format(ts);
    final Calendar cal = Calendar.getInstance();

    //Messages strings
    studentMessage = "(" + c + "," + studentLAST + "," + studentID + "," + date + "," + POD + ")";
    proxyMessage = "(" + c + "," + proxyLAST + "," + "T" + proxyID + "," + date + "," + POD + ")";


    //check if a PROXYTAG is stored
    if (proxyID.equals("Not found") || proxyLAST.equals("Not found")) {
//                    Check for SMS permissions
     checkPermissions();

    } else {
     //Alert to ensure that user wants to use PROXY STUDENT prefs
     AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this)
       .setTitle("Proxy User Found!")
       .setMessage("To sign in using proxy data press \"OK\"")
       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

         //Send users Course, Last Name, StudentID, and Time/Date stamp as a message
         sentMessage(proxyMessage, contactNumber);


//

         //get data from PROXY STUDENT prefs and send it with course and date/time stamp in a message
         Toast.makeText(SignIn.this, proxyMessage, Toast.LENGTH_LONG).show();
        }
       }).setNeutralButton("Clear Proxy", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
         SharedPreferences.Editor proxyEdit = proxyInfo.edit();
         proxyEdit.remove(LAST).remove(T).clear().commit();

        }
       }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
         sentMessage(studentMessage, contactNumber);
         dialog.dismiss();
        }
       })
       .setCancelable(false);
     AlertDialog proxy_found = builder.create();
     proxy_found.show();
    }

   }

  });

 }

 @Override
 protected void onStop() {
  super.onStop();
 }

 //Method to be called to set the title of this screen from another Activity
 public void setTitle(Activity a, String title) {
  //TextView tv = (TextView) a.findViewById(R.id.SignInTitle);

  getSupportActionBar().setTitle(title);
 }

 @TargetApi(Build.VERSION_CODES.M)
 private void checkPermissions() {
  int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
  if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
   if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
    Toast.makeText(this, "SMS permission is required in this application to update the database.", Toast.LENGTH_LONG).show();
   }
   ActivityCompat.requestPermissions(SignIn.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
  } else sentMessage(studentMessage, contactNumber);

 }

 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  switch (requestCode) {
   case MY_PERMISSIONS_REQUEST_SEND_SMS: {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
     //Send users Course, Last Name, StudentID, and Time/Date stamp as a message
     sentMessage(studentMessage, contactNumber);
    } else
     Toast.makeText(SignIn.this, "We could not sign you in because permission was not granted.", Toast.LENGTH_SHORT).show();
    break;
   }
   default:
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

 }

 public void sentMessage(String messageToSend, String contactNumber) {
  //Google Forms HTTP section found @http://goo.gl/forms/HCtiSG0c0D
  //end Google Forms HTTP
  String sent = "MESSAGE_SENT";
  PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(sent), 0);

  broadcastReceiver = new BroadcastReceiver() {
   @Override
   public void onReceive(Context context, Intent intent) {
    if (getResultCode() == Activity.RESULT_OK) {
     Toast.makeText(SignIn.this, "Signed in with:\n" + studentMessage, Toast.LENGTH_LONG).show();
     Toast.makeText(getBaseContext(), "SMS sent",
       Toast.LENGTH_SHORT).show();
    } else {
     Toast.makeText(getBaseContext(), "SMS could not sent",
       Toast.LENGTH_SHORT).show();
    }
   }
  };
  registerReceiver(broadcastReceiver, new IntentFilter(sent));

  SmsManager sms = SmsManager.getDefault();
  sms.sendTextMessage(contactNumber, null, messageToSend, sentPI, null);

  dataJSONFormatter = new dataJSONFormatter(user, POD);


  DBController dbController = new DBController("Attendance");
  dbController.getDB().child(dataJSONFormatter.get_courseNumber()).child(String.valueOf(new SimpleDateFormat("EEE, MMM d, yyyy").format(new Date()))).child(dataJSONFormatter.get_tNumber()).setValue(dataJSONFormatter.loginObject());

 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.menu_sign_in, menu);
  return true;
 }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
  // Handle action bar item clicks here. The action bar will
  // automatically handle clicks on the Home/Up button, so long
  // as you specify a parent activity in AndroidManifest.xml.
  int id = item.getItemId();
//        Toast t = Toast.makeText(this, item.toString(),Toast.LENGTH_SHORT);
//        t.show();
  //noinspection SimplifiableIfStatement
  if (id == R.id.action_settings) {
   Toast k = Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT);
   k.show();
   startActivity(new Intent(SignIn.this, Proxy.class));
   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
   //finish();  --->if finished when back button is press returns to the Course Selection Activity (ADD || DROP)?

   return true;
  }
  if (id == android.R.id.home) {
   startActivity(new Intent(SignIn.this, CourseSelectionScreen.class));
   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
   finish();
  }

  return super.onOptionsItemSelected(item);
 }

 @Override
 public void onBackPressed() {
  startActivity(new Intent(SignIn.this, CourseSelectionScreen.class));
  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  finish();
  super.onBackPressed();
 }

 @Override
 protected void onPause() {
  super.onPause();
 }

}

