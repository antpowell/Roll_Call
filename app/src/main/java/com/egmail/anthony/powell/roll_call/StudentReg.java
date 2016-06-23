package com.egmail.anthony.powell.roll_call;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StudentReg extends ActionBarActivity {
    public static final String TAG = "tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    private boolean nameEntered, idEntered;
//    private Toolbar tb;

    EditText lastName, Tnum;
    private SharedPreferences studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

//        tb = (Toolbar) findViewById(R.id.reg_app_bar);
//        setSupportActionBar(tb);
        getSupportActionBar().setTitle( "Student Registration");

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);
//        Toast.makeText(this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();

        final Button regButton = (Button) findViewById(R.id.RegisterButton);
        lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
        Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!lastName.getText().toString().trim().isEmpty()) nameEntered = true;
                else nameEntered = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Tnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Tnum.getText().toString().trim().length() == 8) idEntered = true;
                else idEntered = false;
                if (idEntered && nameEntered) {
                    regButton.setEnabled(!lastName.getText().toString().trim().isEmpty() && Tnum.getText().toString().trim().length() < 9);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v) {
                if (Tnum.getText().toString().length() != 8) {
                    Tnum.setError("T Number must contain 8 numbers ex.00102222");
                } else if (lastName.getText().toString().trim().isEmpty()) {
                    lastName.setError("Must enter last name!");
                }else if(!lastName.getText().toString().matches("^[a-zA-Z\\s]+$")){
                    lastName.setError("Invalid Character(s)... ONLY LETTERS");
                } else {
                    storeUserInfo();
                    startActivity(new Intent(StudentReg.this, CourseSelectionScreen.class));
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });
    }

    /*Function to save Student's last name and account number in studentInfo shared prefs the Email and Pass will be stored in a
    * another prefs setting later since it will not be sent in the message to the server phone.*/
    private void storeUserInfo() {
        lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
        Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);
        EditText eMail = (EditText) this.findViewById(R.id.EmailTextBox);
        EditText studentPass = (EditText) this.findViewById(R.id.PassTextBox);

        //
        SharedPreferences.Editor edit = studentInfo.edit();
        edit.putString(LAST, lastName.getText().toString()).apply();
        edit.putString(T, Tnum.getText().toString()).apply();
        Toast.makeText(StudentReg.this, lastName.getText().toString() + " \n" + "T" + Tnum.getText().toString(), Toast.LENGTH_LONG).show();
        edit.apply();
        //Display a Toast showing all current content of studentInfo
        //Toast.makeText(StudentReg.this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();
    }

}