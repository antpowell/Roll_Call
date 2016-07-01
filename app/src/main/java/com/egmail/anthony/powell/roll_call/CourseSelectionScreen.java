package com.egmail.anthony.powell.roll_call;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*This Activity is form the student to select the course they wish to sign in to(current course)
 * onCreate the 'Course List' and 'Re-Register buttons are displayed
   * if the Course List button is clicked a Alert with a ListView populated with the current courses appears,
   * student selects course the name of the course gets saved in sharedPrefs and assigned to the title for the SignIn Activity
   * else if the student press Re-Register the studentInfo sharedPrefs are cleared they are taken back to the registration Activity to re register*/
public class CourseSelectionScreen extends ActionBarActivity {

    public Users user;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection);


        EditText search = (EditText) findViewById(R.id.editTextSearch);
        search.setText(new DBController().getKEY());
        //Get User
        user = new Users().getUser(this);

        Resources res = getResources();
        String[] listText = res.getStringArray(R.array.course_list);

        ListAdapter popUpList = new CustomList(this, listText);
        final ListView listView = (ListView) findViewById(R.id.list_dialog);
        listView.setAdapter(popUpList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView courseTextView = (TextView) view.findViewById(R.id.listText);
//                Toast.makeText(CourseSelectionScreen.this, courseTextView.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                courseSign(courseTextView.getText().toString().trim());


//                switch (position) {
//                    case 0:
//                        courseSign("CS124");
//                        break;
//                    case 1:
//                        courseSign("CS116WE1");
//                        break;
//                    case 2:
//                        courseSign("BIOL132");
//                        break;
//                    case 3:
//                        courseSign("BIOL232");
//                        break;
//                    case 4:
//                        courseSign("MATH133");
//                        break;
//                    case 12:
//                        courseSign("MATH134");
//                        break;
//                    case 5:
//                        courseSign("MATH135");
//                        break;
//                    case 6:
//                        courseSign("MATH241");
//                        break;
//                    case 7:
//                        courseSign("HIST231");
//                        break;
//                    case 8:
//                        courseSign("HIST232");
//                        break;
//                    case 9:
//                        courseSign("ENG131");
//                        break;
//                    case 10:
//                        courseSign("ENG132");
//                        break;
//                    case 11:
//                        courseSign("ENG230");
//                        break;
//                }
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
            startActivity(new Intent(CourseSelectionScreen.this, StudentReg.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            //Clear local data
            user.dropUser(this);
            if (!user.hasUser()) Toast.makeText(this, "User Deleted...", Toast.LENGTH_SHORT).show();
            //Clear DB
//            new DBController().dropUser();
            return true;
        }
        if (id == android.R.id.home) {
            startActivity(new Intent(CourseSelectionScreen.this, StudentReg.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void courseSign(String courseID) {
        startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        user.addCourse(courseID);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}


