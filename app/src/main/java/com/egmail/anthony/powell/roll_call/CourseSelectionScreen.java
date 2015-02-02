package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CourseSelectionScreen extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection);

        final SharedPreferences studentAccount = getSharedPreferences("StudentInfo", 0);
        final SharedPreferences.Editor editor = studentAccount.edit();

        Button reReg = (Button) findViewById(R.id.ReRegisterButton);

        reReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(CourseSelectionScreen.this, StudentReg.class));
                finish();
            }
        });
        Button course = (Button) findViewById(R.id.course_selection);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateList();
            }

            private void populateList() {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CourseSelectionScreen.this);
                builder.setTitle("Select Course")
                        .setItems(R.array.course_list, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Open SignIn.java with the Title being the selections list
                                switch (which) {
                                    //First selection
                                    case 0:
                                        //===Add Toast to screen !!(Works Without)!!
                                        //Toast.makeText(CourseSelectionScreen.this,dialog. ,Toast.LENGTH_SHORT);

                                        //startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                        break;
                                    //Second selection
                                    case 1:
                                        startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                        break;
                                    //Third selection
                                    case 2:
                                        startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                        break;
                                    //Fourth selection
                                    case 3:
                                        startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                        break;
                                    default:
                                        AlertDialog.Builder d = new AlertDialog.Builder(CourseSelectionScreen.this);
                                        d.setTitle("Selection Error!");
                                        d.setMessage("Please make a valid selection.");
                                        final AlertDialog alertDialog = d.create();
                                        d.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                }

                                //Toast.makeText(this, );
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
//                builder.show();

            }

        });
    }


}
