package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StudentReg extends Activity {
    public static final String TAG = "tag";
    public static final String LAST = "last";
    public static final String T = "tNum";

    EditText lastName, Tnum;
    private SharedPreferences studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);
        Toast.makeText(this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();

        Button regButton = (Button) findViewById(R.id.RegisterButton);
        lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
        Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tnum.getText().toString().length() != 8) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentReg.this).setTitle("Invalid T-Number")
                            .setMessage("T Number must contain 8 numbers ex.00102222").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog TError = builder.create();
                    TError.show();
                } else if (lastName.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentReg.this).setTitle("Entry error!")
                            .setMessage("Must enter last name!").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog LastError = builder.create();
                    LastError.show();
                } else {
                    storeUserInfo();
                    startActivity(new Intent(StudentReg.this, CourseSelectionScreen.class));
                    finish();
                }

            }
        });
    }

    private void storeUserInfo() {
        lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
        Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);
        EditText eMail = (EditText) this.findViewById(R.id.EmailTextBox);
        EditText studentPass = (EditText) this.findViewById(R.id.PassTextBox);

        SharedPreferences.Editor edit = studentInfo.edit();
        edit.putString(LAST, lastName.getText().toString()).apply();
        edit.putString(T, Tnum.getText().toString()).apply();
        Toast.makeText(StudentReg.this, lastName.getText().toString() + " \n" + "T" + Tnum.getText().toString(), Toast.LENGTH_LONG).show();
        edit.commit();
        Toast.makeText(StudentReg.this, studentInfo.getAll().toString(), Toast.LENGTH_LONG).show();
    }

}