package com.egmail.anthony.powell.roll_call;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/*This Activity is form the student to select the course they wish to sign in to(current course)
 * onCreate the 'Course List' and 'Re-Register buttons are displayed
   * if the Course List button is clicked a Alert with a ListView populated with the current courses appears,
   * student selects course the name of the course gets saved in sharedPrefs and assigned to the title for the SignIn Activity
   * else if the student press Re-Register the studentInfo sharedPrefs are cleared they are taken back to the registration Activity to re register*/
public class CourseSelectionScreen extends ActionBarActivity {

    public static final String TAG = "tag";
    public static final String LAST = "last";
    public static final String T = "tNum";
    public static final String COURSE = "course";
    private SharedPreferences studentInfo;
    Toolbar tb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection);

        tb = (Toolbar) findViewById(R.id.core_sel_app_bar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Course Selection");

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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        Button course = (Button) findViewById(R.id.course_selection);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateList();
            }

            private void populateList() {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CourseSelectionScreen.this).setTitle("Select Course");
                builder.setItems(R.array.course_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TextView tv = (TextView) findViewById(R.id.SignInTitle);
                        switch (which) {
                            case 0:
                                courseSign("CS12401");
                                /*startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                edit.putString(COURSE, "CS12401");
                                edit.apply();
                                //Toast.makeText(CourseSelectionScreen.this, "CS12401", Toast.LENGTH_LONG).show();*/
                                break;
                            case 1:
                                courseSign("CS116WE1");
                                /*startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                edit.putString(COURSE, "CS116WE1");
                                edit.apply();
                                //Toast.makeText(CourseSelectionScreen.this, "CS116WE1", Toast.LENGTH_LONG).show();*/
                                break;
                            case 2:
                                courseSign("BIOL13204");
                                /*startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "BIOL13204");
                                edit.apply();
                                //Toast.makeText(CourseSelectionScreen.this, "BIOL13204", Toast.LENGTH_LONG).show();*/
                                break;
                            case 3:
                                courseSign("BIOL23201");
                                /*startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
                                edit.putString(COURSE, "BIOL23201");
                                edit.apply();
                                //Toast.makeText(CourseSelectionScreen.this, "BIOL23201", Toast.LENGTH_LONG).show();*/
                                break;

                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_selection_screen, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void courseSign(String courseID) {
        startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        final SharedPreferences.Editor edit = studentInfo.edit();

        edit.putString(COURSE, courseID);
        edit.apply();
        //Toast.makeText(CourseSelectionScreen.this, "CS12401", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }


}
