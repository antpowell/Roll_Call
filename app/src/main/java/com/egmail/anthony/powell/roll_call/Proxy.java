package com.egmail.anthony.powell.roll_call;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class Proxy extends ActionBarActivity {

    public static final String PROXYTAG = "proxy_tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    public TextView proxy_last, proxy_id;
    private Toolbar tb;

    private SharedPreferences proxyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proxy_sign_in);
        tb = (Toolbar) findViewById(R.id.proxy_app_bar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Proxy User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        proxy_last = (TextView) this.findViewById(R.id.ProxyLastNameTextField);
        proxy_id = (TextView) this.findViewById(R.id.ProxyTNumberTextField);
        proxyInfo = getSharedPreferences(PROXYTAG, MODE_PRIVATE);

        //On button press get proxy users last name and student number from text field and save to PROXYTAG
        //then return to sign in activity.
        final Button signIn = (Button) findViewById(R.id.StoreProxyButton);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proxy_last.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Proxy.this).setTitle("Entry error!")
                            .setMessage("Must enter last name!").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog LastError = builder.create();
                    LastError.show();
                } else if (proxy_id.getText().toString().length() != 8) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Proxy.this).setTitle("Invalid T-Number")
                            .setMessage("T Number must contain 8 numbers ex.00102222").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog TError = builder.create();
                    TError.show();
                } else {
                    SharedPreferences.Editor editor = proxyInfo.edit();
                    editor.putString(LAST, proxy_last.getText().toString().trim()).apply();
                    editor.putString(T, proxy_id.getText().toString().trim()).apply();

                    startActivity(new Intent(Proxy.this, SignIn.class));
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_course_selection_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
