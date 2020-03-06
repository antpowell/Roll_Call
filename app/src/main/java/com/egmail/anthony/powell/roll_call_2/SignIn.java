package com.egmail.anthony.powell.roll_call_2;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
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
    private dataJSONFormatter dataJSONFormatter;
    //    private final int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
//    public int MY_PERMISSIONS_REQUEST;


    private EditText password;

    private SharedPreferences proxyInfo;
    private Users user;

    private String studentLAST, studentID, course, POD;
    private final static String contactNumber = /*"8327418926"*/"7138998111";
    private String studentMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);

        user = new Users().getUser(this);

        proxyInfo = getSharedPreferences(PROXYTAG, MODE_PRIVATE);

        password = (EditText) findViewById(R.id.editText);

        studentLAST = user.get_lastName();
        studentID = user.get_tNum();
        course = user.get_course();

        setTitle(this, course);


        //Click sign in button saves password of the day entry then sends the sharedpreferences as a text message
        final Button signInButton = (Button) findViewById(R.id.signIn_button);
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
                studentMessage = "(" + course + "," + studentLAST + "," + studentID + "," + date + "," + POD + ")";

                sendMessage(studentMessage, contactNumber);

            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *  Method to be called to set the title of this screen from another Activity
     * @param title title to be shown at the top of the Activity
     */
    public void setTitle(Activity a, String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Prepare message and send to SMS application for user to send.
     *
     * @param messageToSend User signature used to sign roll
     * @param contactNumber Static phone number to send message
     */
    public void sendMessage(String messageToSend, String contactNumber)  {

        //Google Forms HTTP section found @http://goo.gl/forms/HCtiSG0c0D
        //end Google Forms HTTP

        Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
        sendSMS.setData(Uri.parse("smsto:"));
//        sendSMS.setDataAndType(Uri.parse("smsto:"),"text/plain");
        sendSMS.putExtra("address", contactNumber);
        sendSMS.putExtra("sms_body", messageToSend);
        if(sendSMS.resolveActivity(getPackageManager()) != null){
            startActivity(sendSMS);
        }

//        Add roll signature to Firebase for record.
        dataJSONFormatter = new dataJSONFormatter(user, POD);
        DBController dbController = new DBController(this, "Attendance");
        dbController.getDB().child(dataJSONFormatter.get_courseNumber())
                .child(String.valueOf(new SimpleDateFormat("EEE, MMM d, yyyy")
                        .format(new Date()))).child(dataJSONFormatter.get_tNumber())
                .setValue(dataJSONFormatter.loginObject());

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