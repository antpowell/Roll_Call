package com.egmail.anthony.powell.roll_call_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.IOException;

import static com.egmail.anthony.powell.roll_call_2.R.layout.activity_splash;


public class MainActivity extends Activity {

 public static final String TAG = "tag";
 public static final String LAST = "last";
 public static final String T = "tNum";
 private static final String VERSION_CODE = "versionCode";
 private static final String VERSION_NUMBER = "versionNumber";
 private String versionName, playVersionCode, user, t;

 //Use SharedPreferences(like database) to get store student Last Name and Student Number
 private SharedPreferences studentInfo;

 //First activity. Show splash screen if preferences are stored goto Course Selection Activity if not goto Student Registration Activity
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(activity_splash);

  getVersionNumberTask task = new getVersionNumberTask();
  task.execute();

  studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);
  SharedPreferences.Editor editor = studentInfo.edit();

  PackageInfo pInfo = null;
  try {
   pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
  } catch (PackageManager.NameNotFoundException e) {
   e.printStackTrace();
  }

  editor.putString(VERSION_CODE, pInfo.versionName);
  editor.putInt(VERSION_NUMBER, pInfo.versionCode);
  editor.apply();

  versionName = pInfo.versionName;

  user = studentInfo.getString(LAST, "Not found");
  t = studentInfo.getString(T, "Not found");

  //Toast.makeText(this, studentInfo.getAll().toString(),Toast.LENGTH_LONG).show();


 }

 class getVersionNumberTask extends AsyncTask<Void, Void, String> {
  @Override
  protected void onPreExecute() {
   super.onPreExecute();
  }

  @Override
  protected String doInBackground(Void... params) {
   try {
    playVersionCode = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName() + "&hl=en")
      .timeout(30000)
      .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
      .referrer("http://www.google.com")
      .get()
      .select("div[itemprop=softwareVersion]")
      .first()
      .ownText();
   } catch (IOException e) {
    e.printStackTrace();
   }
   return playVersionCode;
  }

  @Override
  protected void onPostExecute(String s) {
//   if (Integer.parseInt(playVersionCode.trim())>Integer.parseInt(versionName.trim()))
   if (!playVersionCode.equals(versionName)) {
    //FORCE USER TO UPDATE VIA PLAY STORE
    Toast.makeText(MainActivity.this, "THIS APPLICATION IS NOT IN SYNC", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
   } else {
    Toast.makeText(MainActivity.this, "YOU ARE RUNNING THE CURRENT VERSION OF THIS APP! THANK YOU FOR YOUR SUPPORT", Toast.LENGTH_SHORT).show();
    Thread splashTimer = new Thread() {
     public void run() {
      try {
       sleep(2000);
       //check if student info is stored on the device. If so goto Course Selection screen if not goto StudentReg screen.
       if (t.equals("Not found") || user.equals("Not found")) {
        startActivity(new Intent(MainActivity.this, StudentReg.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

       } else {
        startActivity(new Intent(MainActivity.this, CourseSelectionScreen.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
       }
      } catch (Exception e) {
       e.printStackTrace();
      } finally {
       finish();
      }
     }
    };
    splashTimer.start();
   }
   super.onPostExecute(s);
  }
 }

}
