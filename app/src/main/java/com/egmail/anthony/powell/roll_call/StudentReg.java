package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StudentReg extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        final EditText lastName = (EditText) findViewById(R.id.LastNameTextBox);
        final EditText Tnum = (EditText) findViewById(R.id.TNumberTextBox);
        final EditText eMail = (EditText) findViewById(R.id.EmailTextBox);
        final EditText studentPass = (EditText) findViewById(R.id.PassTextBox);

        Button regButton = (Button) findViewById(R.id.RegisterButton);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, email, pass, T;


                SharedPreferences studentAccount = getSharedPreferences("StudentInfo", 0);
                SharedPreferences.Editor editor = studentAccount.edit();
                //AlertDialog.Builder nameAlert;

                lastName.setText(studentAccount.getString("Name", ""));
                Tnum.setText(studentAccount.getString("tNum", ""));
                eMail.setText(studentAccount.getString("email", ""));
                studentPass.setText(studentAccount.getString("studentPass", ""));


                name = lastName.getText().toString();
                T = Tnum.getText().toString();
                email = eMail.getText().toString();
                pass = studentPass.getText().toString();

                //Set required fields (LastName and Student ID number for now) and display an alert if not completed

//                if (name.equals("")){
//                    AlertDialog.Builder nameAlert = new AlertDialog.Builder(StudentReg.this);
//                    nameAlert.setMessage("Student's last name is required!");
//                    nameAlert.setCancelable(true);
//                    AlertDialog alert = nameAlert.create();
//                    alert.show();
                //Students ID Numbers are 9 characters made of a T followed by 8 numbers. We only require the numbers so length is set to 8 to eliminate user error.
//                }else if (T.length() != 8){
//                    AlertDialog.Builder TAlert = new AlertDialog.Builder(StudentReg.this);
//                    TAlert.setMessage(getString(R.string.tnumberHint) + " we will handle the  \"T\"");
//                    TAlert.setCancelable(true);
//                    AlertDialog alert = TAlert.create();
//                    alert.show();
//
//                }
//                else {
                //if all required fields are complete store the students Registration data into the StoredPreferences
                editor.putString("name", name);
                editor.putString("tNum", T);
                editor.putString("email", email);
                editor.putString("studentPass", pass);
                editor.apply();


//                nameAlert = new AlertDialog.Builder(StudentReg.this);
//                nameAlert.setMessage(name + "Skipped");
//                nameAlert.setCancelable(true);
//                AlertDialog alert = nameAlert.create();
//                alert.show();
                startActivity(new Intent(StudentReg.this, CourseSelectionScreen.class));
                finish();
//                }
            }
        });
    }
}