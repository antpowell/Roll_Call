package com.egmail.anthony.powell.roll_call;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/*This Activity is form the student to select the course they wish to sign in to(current course)
 * onCreate the 'Course List' and 'Re-Register buttons are displayed
   * if the Course List button is clicked a Alert with a ListView populated with the current courses appears,
   * student selects course the name of the course gets saved in sharedPrefs and assigned to the title for the SignIn Activity
   * else if the student press Re-Register the studentInfo sharedPrefs are cleared they are taken back to the registration Activity to re register*/
public class CourseSelectionScreen extends AppCompatActivity {

    public Users user;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection);

        SearchView search = (SearchView) findViewById(R.id.editTextSearch);

//        search.setText(new DBController().getKEY());
        //Get User
        user = new Users().getUser(this);

        Resources res = getResources();
        String[] listText = res.getStringArray(R.array.course_list);

        final ListAdapter popUpList = new CustomList(this, listText);
        final ListView listView = (ListView) findViewById(R.id.list_dialog);
        listView.setAdapter(popUpList);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView courseTextView = (TextView) view.findViewById(R.id.listText);
//                Toast.makeText(CourseSelectionScreen.this, courseTextView.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                courseSign(courseTextView.getText().toString().trim());
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
            new DBController().dropUser(user);
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


