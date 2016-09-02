package com.egmail.anthony.powell.roll_call_2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StudentReg extends ActionBarActivity {
 private boolean nameEntered, idEntered, emailEntered, passwordEntered;
 protected Users user;
 private DBController dbController;
 public static Context context;

 Button regButton;
 EditText lastName, Tnum, eMail, password;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_student_reg);

  context = this;
//        View hooks
  regButton = (Button) findViewById(R.id.RegisterButton);
  lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
  Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);
  eMail = (EditText) this.findViewById(R.id.EmailTextBox);
  password = (EditText) this.findViewById(R.id.PassTextBox);

  lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
    if (!hasFocus) {
     if (lastName.getText().toString().trim().isEmpty()) {
      lastName.setError("Must enter last name!");
      nameEntered = false;
     } else if (!lastName.getText().toString().matches("^[a-zA-Z\\s]+$")) {
      lastName.setError("Invalid Character(s)... ONLY LETTERS");
      nameEntered = false;
     } else {
      nameEntered = true;
     }
     activateRegistrationButton();
    }
   }
  });

  Tnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
    if (!hasFocus) {
     if (Tnum.getText().toString().length() != 8) {
      Tnum.setError("T Number must contain 8 numbers ex.00102222");
      idEntered = false;
     } else if (Tnum.getText().toString().length() == 0) {
      Tnum.setError("Must enter a student number");
      idEntered = false;
     } else {
      idEntered = true;
     }
     activateRegistrationButton();
    }
   }
  });

  eMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
    if (!hasFocus) {
     if (!Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()) {
      eMail.setError("Incorrect Email Format");
      emailEntered = false;
     } else if (eMail.getText().toString().length() == 0) {
      eMail.setError("Must enter a email address");
      emailEntered = false;
     } else {
      emailEntered = true;
     }
     activateRegistrationButton();
    }
   }
  });
  password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
    if (!hasFocus) {
     if (password.getText().toString().length() < 7 || password.getText().toString().length() > 10) {
      password.setError("Password must be 7 to 10 characters long");
      passwordEntered = false;
     } else if (password.getText().toString().length() == 0) {
      password.setError("Must enter a password");
      passwordEntered = false;
     } else {
      passwordEntered = true;
     }
     activateRegistrationButton();
    }
   }
  });

  regButton.setOnClickListener(new View.OnClickListener() {
   @TargetApi(Build.VERSION_CODES.ECLAIR)
   @Override
   public void onClick(View v) {
    storeUserInfo();
    startActivity(new Intent(StudentReg.this, CourseSelectionScreen.class));
    finish();
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


   }
  });

 }

 public boolean checkEnabled() {
  return nameEntered && idEntered && emailEntered && passwordEntered;
 }

 public void activateRegistrationButton() {
  if (checkEnabled())
   regButton.setEnabled(!lastName.getText().toString().trim().isEmpty() && Tnum.getText().toString().trim().length() < 9);
  else
   regButton.setEnabled(lastName.getText().toString().trim().isEmpty() && Tnum.getText().toString().trim().length() < 9);
 }


 /*Function to save Student's last name and account number in studentInfo shared prefs the Email and Pass will be stored in a
 * another prefs setting later since it will not be sent in the message to the server phone.*/
 private void storeUserInfo() {
  EditText eMail = (EditText) this.findViewById(R.id.EmailTextBox);
  EditText studentPass = (EditText) this.findViewById(R.id.PassTextBox);
  //Create User
  user = new Users(this, lastName.getText().toString(), Tnum.getText().toString(), eMail.getText().toString(), studentPass.getText().toString());
  if (user.hasUser()) {
   Toast.makeText(this, user.get_lastName() + " \n" + user.get_tNum(), Toast.LENGTH_SHORT).show();
   //Save use to DB
   dbController = new DBController(this);

   dbController.addUser(user);
   dbController.CreateUser(user);


  } else
   Toast.makeText(this, "Error storing user data, please inform admin.", Toast.LENGTH_LONG).show();
 }

}
