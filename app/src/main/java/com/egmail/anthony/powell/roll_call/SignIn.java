package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class SignIn extends ActionBarActivity {

    public static final String TAG = "tag";
    public static final String PROXYTAG = "proxy_tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    public static final String COURSE = "course";

    private Toolbar tb;
    private EditText password;

    private SharedPreferences studentInfo;
    private SharedPreferences proxyInfo;
    private String studentLAST;
    private String studentID;
    private String c;
    private String studentPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);

        //Enable Custom Toolbar
        tb = (Toolbar) findViewById(R.id.sign_in_app_bar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);
        proxyInfo = getSharedPreferences(PROXYTAG, MODE_PRIVATE);

        password = (EditText) findViewById(R.id.editText);

        studentLAST = studentInfo.getString(LAST, "Not found");
        studentID = studentInfo.getString(T, "Not found");
        c = studentInfo.getString(COURSE, "Not found");


//        Toast.makeText(this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();

        setTitle(this, c);

        //Click sign in button saves password of the day entry then sends the sharedpreferences as a text message
        final Button signInButton = (Button) findViewById(R.id.signIn_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proxyLAST = proxyInfo.getString(LAST, "Not found");
                String proxyID = proxyInfo.getString(T, "Not found");
                String POD = password.getText().toString();
                studentPass = POD;

                final Date ts = new Date();
                final String date = new SimpleDateFormat("dd/MM/yy HH:mm").format(ts);
                final Calendar cal = Calendar.getInstance();
                //final String date = cal.getTime().toString();
                //final String date = DateFormat.getDateInstance(DateFormat.SHORT).format((Date)value);

                //Messages strings
                final String studentMessage = "(" + c + "," + studentLAST + "," + "T" + studentID + "," + date + "," + POD + ")";
                final String proxyMessage = "(" + c + "," + proxyLAST + "," + "T" + proxyID + "," + date + "," + POD + ")";

                final String contactNumber = /*"8327418926"*/"7138998111";

                //check if a PROXYTAG is stored
                if (proxyID.equals("Not found") || proxyLAST.equals("Not found")) {
                    //store password of the day

                    //Send users Course, Last Name, StudentID, and Time/Date stamp as a message
                    sentMessage(studentMessage, contactNumber);



                    //get all data from MAIN STUDENT prefs and send it with course and date/time stamp in message
                    Toast.makeText(SignIn.this, studentMessage, Toast.LENGTH_LONG).show();
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
        //---- This section now being handled by the options button on the app bar.----//

        //Proxy button click to take user to proxy screen
//        Button proxy = (Button) findViewById(R.id.ProxyButton);
//        proxy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignIn.this, Proxy.class));
//                //finish(); --->if finished when back button is press returns to the Course Selection Activity (ADD || DROP)?
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
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

    public void sentMessage(String messageToSend, String contactNumber) {
        //Google Forms HTTP section found @http://goo.gl/forms/HCtiSG0c0D
        //end Google Forms HTTP
        String sent = "MESSAGE_SENT";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(sent), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if (getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), "SMS sent",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "SMS could not sent",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter(sent));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(contactNumber, null, messageToSend, sentPI, null);

//        Intent sendMessage = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+contactNumber));
//        sendMessage.putExtra("sms_body",messageToSend);
//        startActivity(sendMessage);
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
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}

