package com.egmail.anthony.powell.roll_call_2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;


public class StudentReg extends ActionBarActivity {
 private boolean nameEntered, idEntered, emailEntered, passwordEntered;
 protected Users user;
 private DBController dbController;
 public static Context context;
 private boolean isUserLoggingIn, isUserRegistering;
 private final String D = "DEBUG";

 Button regButton;
 EditText lastName, Tnum, eMail, password;
 RadioButton loginSegment, registerSegment;
 SegmentedGroup login_registation_group;
 RelativeLayout nameLayout, tnumLayout, emailLayout, passwordLayout;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_student_reg);
  nameEntered = false;
  idEntered = false;
  emailEntered = false;
  passwordEntered = false;

  isUserRegistering = false;
  isUserLoggingIn = false;


  context = this;
  createAssociationsWithView();
  handlers();
  checkEnabled();


 }

 public void checkEnabled() {
  regButton.setEnabled(false);
  lastNameChecker();
  tNumberChecker();
  emailChecker();
  passwordChecker();

 }

 public void activateRegistrationButton() {

  if (nameEntered && idEntered && passwordEntered && emailEntered) {
   Log.d(D, "checking to activate button");
   regButton.setEnabled(true);
  } else {
   regButton.setEnabled(false);
  }
 }


 /*Function to save Student's last name and account number in studentInfo shared prefs the Email and Pass will be stored in a
 * another prefs setting later since it will not be sent in the message to the server phone.*/
 private void storeUserInfo() {
  EditText eMail = (EditText) this.findViewById(R.id.EmailTextBox);
  EditText studentPass = (EditText) this.findViewById(R.id.PassTextBox);
  //Create User
  user = new Users(this, lastName.getText().toString(), Tnum.getText().toString(), eMail.getText().toString(), studentPass.getText().toString());
  Toast.makeText(context, String.valueOf(user.hasUser() ? "Created user" : "Unable to create user"), Toast.LENGTH_SHORT).show();
  Toast.makeText(context, user.get_tNum() + ":" + user.get_lastName(), Toast.LENGTH_SHORT).show();

  //TODO:Fix user returning true when no user is in storage

//  if (user.hasUser()) {
//   //Save use to DB
//   dbController = new DBController(this);
//   dbController.CreateUser(user);
//   if (dbController.wasUserCreated()) {
//    Toast.makeText(this, user.get_lastName() + " \n" + user.get_tNum(), Toast.LENGTH_SHORT).show();
//    dbController.UserSignIn(user);
//    if (dbController.UserSignIn(user)) {
//
//    }
//   }
//
//   dbController.addUser(user);
//  } else
//   Toast.makeText(this, "Error storing user data, please inform admin.", Toast.LENGTH_LONG).show();
 }

 protected void createAssociationsWithView() {
  //        View hooks
  //Buttons
  regButton = (Button) findViewById(R.id.RegisterButton);
  //TextFields
  lastName = (EditText) this.findViewById(R.id.LastNameTextBox);
  Tnum = (EditText) this.findViewById(R.id.TNumberTextBox);
  eMail = (EditText) this.findViewById(R.id.EmailTextBox);
  password = (EditText) this.findViewById(R.id.PassTextBox);
//  TextInputLayout
  nameLayout = (RelativeLayout) findViewById(R.id.name_view);
  tnumLayout = (RelativeLayout) findViewById(R.id.tnumber_view);
  emailLayout = (RelativeLayout) findViewById(R.id.email_view);
  passwordLayout = (RelativeLayout) findViewById(R.id.passowrd_view);


  //Segments
  loginSegment = (RadioButton) findViewById(R.id.login_switch_segment);
  registerSegment = (RadioButton) findViewById(R.id.register_switch_segment);
  login_registation_group = (SegmentedGroup) findViewById(R.id.log_reg_seg);
  registerSegment.setChecked(true);
 }

 protected void handlers() {
  //TODO: Stop registration button from enabling on segment change
  login_registation_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
   @Override
   public void onCheckedChanged(RadioGroup radioGroup, int i) {
    switch (i) {
     case R.id.login_switch_segment:
      new AlertDialog.Builder(StudentReg.this).setMessage("This feature will be available in version 2.0\n\nYou are being sent back to the Register section.")
        .setCancelable(false)
        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialog, int which) {
        registerSegment.toggle();
       }
      }).create().show();
//      Snackbar snackbar = Snackbar
//        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
//      snackbar.show();

      Log.d(D, "User trying to login");
//    User is logging in
      isUserLoggingIn = true;
      isUserRegistering = false;
      //handle events while login segment is active
//      Toast.makeText(StudentReg.this, String.valueOf(emailChecker() && passwordChecker()), Toast.LENGTH_SHORT).show();
//      regButton.setEnabled(emailChecker()&&passwordChecker());

      activateRegistrationButton();
      regButton.setText("Login");

      //view animations
      nameLayout.animate().alpha(0.0f);
      tnumLayout.animate().alpha(0.0f);
      emailLayout.animate().translationY(-250);
      passwordLayout.animate().translationY(-250);
      regButton.animate().translationY(-250);
      //set a delay on removing visibility for animation to show then remove.
      new android.os.Handler().postDelayed(
        new Runnable() {
         @Override
         public void run() {
          nameLayout.setVisibility(View.INVISIBLE);
          tnumLayout.setVisibility(View.INVISIBLE);
         }
        },
        300);


      break;

     case R.id.register_switch_segment:
      Log.d(D, "User trying to register");
//      User is trying to register
      isUserLoggingIn = false;
      isUserRegistering = true;

      //handle events while login segment is active
      nameLayout.setVisibility(View.VISIBLE);
      tnumLayout.setVisibility(View.VISIBLE);

      activateRegistrationButton();
      regButton.setText("Register");

      //view animations
      nameLayout.animate().alpha(1.0f);
      tnumLayout.animate().alpha(1.0f);
      emailLayout.animate().translationY(0);
      passwordLayout.animate().translationY(0);
      regButton.animate().translationY(0);

      break;

     default:
      //Nothing to do

    }
   }
  });

//  lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//   @Override
//   public void onFocusChange(View v, boolean hasFocus) {
//    if (!hasFocus) {
//     if (lastName.getText().toString().trim().isEmpty()) {
//      lastName.setError("Must enter last name!");
//      nameEntered = false;
//     } else if (!lastName.getText().toString().matches("^[a-zA-Z\\s]+$")) {
//      lastName.setError("Invalid Character(s)... ONLY LETTERS");
//      nameEntered = false;
//     } else {
//      nameEntered = true;
//     }
//     activateRegistrationButton();
//    }
//   }
//  });
//
//  lastName.addTextChangedListener(new TextWatcher() {
//   @Override
//   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//   }
//
//   @Override
//   public void onTextChanged(CharSequence s, int start, int before, int count) {
//    if (!lastName.getText().toString().matches("^[a-zA-Z\\s]+$")) {
//     lastName.setError("Invalid Character(s)... ONLY LETTERS");
//     nameEntered = false;
//    } else {
//     nameEntered = true;
//    }
//   }
//
//   @Override
//   public void afterTextChanged(Editable s) {
//
//   }
//  });
//
//  Tnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//   @Override
//   public void onFocusChange(View v, boolean hasFocus) {
//    if (!hasFocus) {
//     if (Tnum.getText().toString().length() != 8) {
//      Tnum.setError("T Number must contain 8 numbers ex.00102222");
//      idEntered = false;
//     } else if (Tnum.getText().toString().length() == 0) {
//      Tnum.setError("Must enter a student number");
//      idEntered = false;
//     } else {
//      idEntered = true;
//     }
//     activateRegistrationButton();
//    }
//   }
//  });
//
//  Tnum.addTextChangedListener(new TextWatcher() {
//   @Override
//   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//   }
//
//   @Override
//   public void onTextChanged(CharSequence s, int start, int before, int count) {
//    if (Tnum.getText().toString().length() == 8) {
//     idEntered = true;
//    }else{
//     idEntered = false;
//    }
//    activateRegistrationButton();
//   }
//
//   @Override
//   public void afterTextChanged(Editable s) {
//
//   }
//  });
//
//  eMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//   @Override
//   public void onFocusChange(View v, boolean hasFocus) {
//    if (!hasFocus) {
//     if (!Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()) {
//      eMail.setError("Incorrect Email Format");
//      emailEntered = false;
//     } else if (eMail.getText().toString().length() == 0) {
//      eMail.setError("Must enter a email address");
//      emailEntered = false;
//     } else {
//      emailEntered = true;
//     }
//     activateRegistrationButton();
//    }
//   }
//  });
//
//  eMail.addTextChangedListener(new TextWatcher() {
//   @Override
//   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//   }
//
//   @Override
//   public void onTextChanged(CharSequence s, int start, int before, int count) {
//    if (Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()) {
//     emailEntered = true;
//    }else{
//     emailEntered = false;
//    }
//    activateRegistrationButton();
//   }
//
//   @Override
//   public void afterTextChanged(Editable s) {
//
//   }
//  });
//
//  password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//   @Override
//   public void onFocusChange(View v, boolean hasFocus) {
//    if (!hasFocus) {
//     if (password.getText().toString().length() < 7 || password.getText().toString().length() > 10) {
//      password.setError("Password must be 7 to 10 characters long");
//      passwordEntered = false;
//     } else if (password.getText().toString().length() == 0) {
//      password.setError("Must enter a password");
//      passwordEntered = false;
//     } else {
//      passwordEntered = true;
//     }
//     activateRegistrationButton();
//    }
//   }
//  });
//
//
//  password.addTextChangedListener(new TextWatcher() {
//   @Override
//   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//   }
//
//   @Override
//   public void onTextChanged(CharSequence s, int start, int before, int count) {
//    if(password.getText().toString().length() != 0){
//     if (password.getText().toString().length() > 7 || password.getText().toString().length() < 10) {
//      passwordEntered = false;
//     }else{
//      passwordEntered = true;
//     }
//     Toast.makeText(StudentReg.this, String.valueOf(passwordEntered), Toast.LENGTH_SHORT).show();
//     Toast.makeText(StudentReg.this, "Is enabled: " + String.valueOf(checkEnabled()), Toast.LENGTH_SHORT).show();
//     activateRegistrationButton();
//    }
//   }
//
//   @Override
//   public void afterTextChanged(Editable s) {
//
//   }
//  });
//
  regButton.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {

    Log.d(D, "User trying to register w/ reg button");
    register();

//    if (isUserLoggingIn) {
//     login();
//    } else if (isUserRegistering) {
//     Log.d(D, "User trying to register w/ reg button");
//     register();
//
//    }

   }
  });
 }

 private void login() {
//  TODO: retrieve user account from db the proceed
 }

 private void register() {

  storeUserInfo();
  startActivity(new Intent(StudentReg.this, CourseSelectionScreen.class));
  finish();
  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
 }

 private boolean lastNameChecker() {
  lastName.addTextChangedListener(new TextWatcher() {
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
//    Toast.makeText(StudentReg.this, "Changing Last Name", Toast.LENGTH_SHORT).show();

    if (lastName.getText().toString().length() > 0) {
//     Log.d("DEBUG", "Name > 0" + lastName.getText().toString().length());
     if (String.valueOf(lastName.getText().toString()).matches("^[a-zA-Z\\s]+$")) {
//      Log.d("DEBUG", "Correct string for name");
      nameEntered = true;
      activateRegistrationButton();
     } else {
//      Log.d("DEBUG", "Not correct string for name");
      nameEntered = false;
      activateRegistrationButton();
     }
    } else {
//     Log.d("DEBUG", "Name not long enough");
     nameEntered = false;
     activateRegistrationButton();
    }
   }

   @Override
   public void afterTextChanged(Editable s) {

   }
  });
  activateRegistrationButton();
  return nameEntered;
 }

 private boolean tNumberChecker() {
  idEntered = false;
  Tnum.addTextChangedListener(new TextWatcher() {
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (Tnum.getText().toString().length() != 0) {
     if (Tnum.getText().toString().length() == 8) {
      idEntered = true;
      activateRegistrationButton();
     } else {
      idEntered = false;
      activateRegistrationButton();
     }
    } else {
     idEntered = false;
     activateRegistrationButton();
    }
   }

   @Override
   public void afterTextChanged(Editable s) {

   }
  });
  activateRegistrationButton();
  return idEntered;
 }

 private boolean emailChecker() {
  emailEntered = false;
  eMail.addTextChangedListener(new TextWatcher() {
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (eMail.getText().toString().length() != 0) {
//     Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()
     if (Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()) {
      emailEntered = true;
      activateRegistrationButton();
     } else {
      emailEntered = false;
      activateRegistrationButton();
     }
    } else {
     emailEntered = false;
     activateRegistrationButton();
    }
   }

   @Override
   public void afterTextChanged(Editable s) {

   }
  });
  activateRegistrationButton();
  return emailEntered;
 }

 private boolean passwordChecker() {
  passwordEntered = false;
  password.addTextChangedListener(new TextWatcher() {
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (password.getText().toString().length() != 0) {
     if (password.getText().toString().length() > 6 && password.getText().toString().length() < 11) {

//      Log.d("DEBUG", String.valueOf(nameEntered && idEntered && passwordEntered && emailEntered));

      passwordEntered = true;
      activateRegistrationButton();
     } else {
      passwordEntered = false;
      activateRegistrationButton();
     }
    } else {
     passwordEntered = false;
     activateRegistrationButton();
    }
   }

   @Override
   public void afterTextChanged(Editable s) {

   }
  });
  return passwordEntered;
 }
}
