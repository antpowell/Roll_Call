package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class SignIn extends Activity {

    public static final String TAG = "tag";
    public static final String PROXYTAG = "proxy_tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    public static final String COURSE = "course";
    public static final String PASSOFDAY = "password_of_the_day";


    private SharedPreferences studentInfo;
    private SharedPreferences proxyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);
        proxyInfo = getSharedPreferences(PROXYTAG, MODE_PRIVATE);

        final Long tsLong = System.currentTimeMillis() / 1000;
        final String ts = tsLong.toString();
        final String studentLAST = studentInfo.getString(LAST, "Not found");
        final String studentID = studentInfo.getString(T, "Not found");
        final String proxyLAST = proxyInfo.getString(LAST, "Not found");
        final String proxyID = proxyInfo.getString(T, "Not found");
        final Calendar cal = Calendar.getInstance();
//        final int seconds = cal.get(Calendar);
//        final long time = System.cu

        final SharedPreferences.Editor editor = studentInfo.edit();

//        Toast.makeText(this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();

        final String c = studentInfo.getString(COURSE, "Not found");
        setTitle(this, c);

        //Click sign in button saves password of the day entry then sends the sharedpreferences as a text message
        final Button signInButton = (Button) findViewById(R.id.signIn_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if a PROXYTAG is stored
                if (proxyID.toString().equals("Not found") || proxyLAST.toString().equals("Not found")) {
                    //store password of the day
                    editor.putString(PASSOFDAY, signInButton.getText().toString()).apply();
                    //get all data from MAIN STUDENT prefs and send it with course and date/time stamp in message
                    Toast.makeText(SignIn.this, c + " " + studentLAST + " " + studentID + " " /*+ seconds*/ + " " + ts, Toast.LENGTH_LONG).show();
                } else {
                    //Alert to ensure that user wants to use PROXY STUDENT prefs
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this)
                            .setTitle("Proxy User Found!")
                            .setMessage("To sign in using proxy data press \"OK\"")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //store password of the day
                                    editor.putString(PASSOFDAY, signInButton.getText().toString()).apply();
                                    //get data from PROXY STUDENT prefs and send it with course and date/time stamp in a message
                                    Toast.makeText(SignIn.this, c + " " + proxyLAST + " " + proxyID + " " + ts, Toast.LENGTH_LONG).show();
                                }
                            }).setNeutralButton("Clear Proxy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor proxyEdit = proxyInfo.edit();
                                    proxyEdit.clear().apply();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false);
                    AlertDialog proxy_found = builder.create();
                    proxy_found.show();


                }

            }
        });
        //Proxy button click to take user to proxy screen
        Button proxy = (Button) findViewById(R.id.ProxyButton);
        proxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Proxy.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    //Method to be called to set the title of this screen from another Activity
    public void setTitle(Activity a, String title) {
        TextView tv = (TextView) a.findViewById(R.id.SignInTitle);
        tv.setText(title);
    }
}
