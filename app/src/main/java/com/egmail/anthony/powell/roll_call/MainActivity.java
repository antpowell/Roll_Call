package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import static com.egmail.anthony.powell.roll_call.R.layout.activity_splash;


public class MainActivity extends Activity {

    public static final String TAG = "tag";
    public static final String LAST = "last";
    public static final String T = "tNum";

    //Use SharedPreferences(like database) to get store student Last Name and Student Number
    private SharedPreferences studentInfo;

    //First activity. Show splash screen if preferences are stored goto Course Selection Activity if not goto Student Registration Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_splash);

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);

        final String user = studentInfo.getString(LAST, "Not found");
        final String t = studentInfo.getString(T, "Not found");

        //Toast.makeText(this, studentInfo.getAll().toString(),Toast.LENGTH_LONG).show();


        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    //check if student info is stored on the device. If so goto Course Selection screen if not goto StudentReg screen.
                    if (t.equals("Not found") || user.equals("Not found")) {
                        startActivity(new Intent(MainActivity.this, StudentReg.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, CourseSelectionScreen.class));
                        Toast.makeText(MainActivity.this, "Student data found", Toast.LENGTH_LONG).show();
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

}
