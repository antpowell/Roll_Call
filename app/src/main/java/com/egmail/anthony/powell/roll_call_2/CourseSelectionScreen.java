package com.egmail.anthony.powell.roll_call_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.egmail.anthony.powell.roll_call_2.model.DataItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*This Activity is form the student to select the course they wish to sign in to(current course)
 * onCreate the 'Course List' and 'Re-Register buttons are displayed
   * if the Course List button is clicked a Alert with a ListView populated with the current courses appears,
   * student selects course the name of the course gets saved in sharedPrefs and assigned to the title for the SignIn Activity
   * else if the student press Re-Register the studentInfo sharedPrefs are cleared they are taken back to the registration Activity to re register*/
public class CourseSelectionScreen extends AppCompatActivity {

 public Users user;
 public CustomList adapter;
 private ArrayList<String> courses = new ArrayList<>();
 private ListView listView;
 private TableRow tableRow;
 private DatabaseReference courseRef;
 private  static SearchView searchView;
 DBController dbController;

 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.course_selection);
  RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.course_selection_layout);
  
  relativeLayout.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    Toast.makeText(CourseSelectionScreen.this, v.toString(), Toast.LENGTH_SHORT).show();
   }
  });
  TextView TV = (TextView)findViewById(R.id.course_selection_title);
  TV.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
    Toast.makeText(CourseSelectionScreen.this, String.format("Course Selection Title Clicked"), Toast.LENGTH_SHORT).show();
   }
  });
  

  
  listView = (ListView) findViewById(R.id.course_selection_list_dialog);
//  listView.setOnClickListener(new View.OnClickListener() {
//   @Override
//   public void onClick(View view) {
//    Toast.makeText(CourseSelectionScreen.this, String.format("Course Selection List View Clicked"), Toast.LENGTH_SHORT).show();
//   }
//  });


  adaptorSetup();



  ListenerHandler();

  //Pull in current user info from device local storage.
  /**
   * Pull in current User info from device Local Storage
   * i.e. Email, and  TNUmber
   */
  user = new Users().getUser(this);

 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.menu_course_selection_screen, menu);
  //setup search view from menu
  MenuItem searchItem = menu.findItem(R.id.editTextSearch);
  searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


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
   //DBController dbController = new DBController(this);


   //Clear local data
   user.dropUser(this);
   if (!user.hasUser()) Toast.makeText(this, "User Deleted...", Toast.LENGTH_SHORT).show();
   //Clear DB
//            DBController dbController = new DBController(this);
//         dbController.dropUser(user);

   if (!user.hasUser() /*&& dbController.wasUserDeleted()*/) {
    startActivity(new Intent(CourseSelectionScreen.this, StudentReg.class));
    finish();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    return true;
   } else {
    return false;
   }

  }
  if (id == android.R.id.home) {
   startActivity(new Intent(CourseSelectionScreen.this, StudentReg.class));
   finish();
   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

   return true;
  }

  return super.onOptionsItemSelected(item);
 }

 @Override
 public void onBackPressed() {
  finish();
  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  super.onBackPressed();
 }


 /**
  * Get course listings from Firebase and create the course listings
  * with the adapter.
  *
  */
 public void adaptorSetup() {
  courseRef = FirebaseDatabase.getInstance().getReference("Courses").child("Codes");
  courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(DataSnapshot dataSnapshot) {
    courses = (ArrayList<String>) dataSnapshot.getValue();

    adapter = new CustomList(CourseSelectionScreen.this, courses, searchView);
    listView.setAdapter(adapter);
   }

   @Override
   public void onCancelled(DatabaseError databaseError) {

   }
  });
//        DataItemAdapter adapter = new DataItemAdapter(this, android.R.layout.simple_expandable_list_item_1, courses);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, courses);

 }

 private void ListenerHandler() {

//  tableRow = (TableRow)findViewById(R.id.listItemRow);
  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    TableRow tableRow = (TableRow)findViewById(R.id.listItemRow);
//    TextView courseTextView = (TextView) view.findViewById(R.id.listText);
    Toast.makeText(CourseSelectionScreen.this, String.format("Touched %s inside of %s", tableRow.toString(), parent.toString()),Toast.LENGTH_SHORT).show();
    Log.d("Course Selection Touch:",String.format("String %s has been touched", view.toString()));
//    courseSign(courseTextView.getText().toString().trim());
   }
  });

 }

 public void courseSign(String courseID) {
  startActivity(new Intent(CourseSelectionScreen.this, SignIn.class));
  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  user.addCourse(courseID);
  finish();
 }

}

