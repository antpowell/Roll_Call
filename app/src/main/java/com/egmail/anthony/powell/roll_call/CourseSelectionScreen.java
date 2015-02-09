package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CourseSelectionScreen extends Activity {

    private SharedPreferences studentInfo;
    public static final String TAG = "tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    public static final String COURSE = "course";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection);

        studentInfo = getSharedPreferences(TAG, MODE_PRIVATE);

//        Toast.makeText(this, studentInfo.getAll().toString(),Toast.LENGTH_LONG).show();
        final SharedPreferences.Editor edit = studentInfo.edit();

        Button reReg = (Button) findViewById(R.id.ReRegisterButton);

        reReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.clear();
                edit.apply();
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

                final ListView lv = new ListView(CourseSelectionScreen.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(CourseSelectionScreen.this).setTitle("Select Course");
                builder.setItems(R.array.course_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tv = (TextView) findViewById(R.id.SignInTitle);
                        switch (which) {
                            case 0:
                                startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "CS12401");
                                edit.apply();
                                Toast.makeText(CourseSelectionScreen.this, "CS12401", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "CS116WE1");
                                edit.apply();
                                Toast.makeText(CourseSelectionScreen.this, "CS116WE1", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "BIOL13204");
                                edit.apply();
                                Toast.makeText(CourseSelectionScreen.this, "BIOL13204", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "BIOL23201");
                                edit.apply();
                                Toast.makeText(CourseSelectionScreen.this, "BIOL23201", Toast.LENGTH_LONG).show();
                                break;

                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


}
