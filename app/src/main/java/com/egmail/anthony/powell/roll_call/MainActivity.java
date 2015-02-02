package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import static com.egmail.anthony.powell.roll_call.R.layout.activity_splash;


public class MainActivity extends Activity {

    private SharedPreferences studentAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);FLAG_FULLSCREEN
        setContentView(activity_splash);
        studentAccount = getSharedPreferences("StudentInfo", 0);

        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    //check if student info is stored on the device. If so goto Course Selection screen if not goto StudentReg screen.
                    //AlertDialog.Builder nameAlert;
                    if (studentAccount.getAll().isEmpty()) {
                        startActivity(new Intent(MainActivity.this, StudentReg.class));
                    } else {
                     /* nameAlert = new AlertDialog.Builder(MainActivity.this);
                      nameAlert.setMessage(studentAccount.getAll().toString());
                      nameAlert.setCancelable(true);
                      AlertDialog alert = nameAlert.create();
                      alert.show();*/
                        startActivity(new Intent(MainActivity.this, CourseSelectionScreen.class));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
