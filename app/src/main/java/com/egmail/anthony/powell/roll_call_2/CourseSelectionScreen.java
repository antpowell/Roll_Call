package com.egmail.anthony.powell.roll_call_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
public class CourseSelectionScreen extends ActionBarActivity {

 public Users user;
 DBController dbController;
 private ArrayList<String> courses = new ArrayList<>();
 private ListView listView;
 public CustomList adapter;
 private DatabaseReference courseRef;

 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.course_selection);
  listView = (ListView) findViewById(R.id.list_dialog);

  adaptorSetup();

  courseRef = FirebaseDatabase.getInstance().getReference("Courses").child("Codes");

  ListenerHandler();

  //Get User
  user = new Users().getUser(this);

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

 public void adaptorSetup() {
  final CustomList customList;
  courseRef = FirebaseDatabase.getInstance().getReference("Courses").child("Codes");
  courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(DataSnapshot dataSnapshot) {
    courses = (ArrayList<String>) dataSnapshot.getValue();

    adapter = new CustomList(CourseSelectionScreen.this, courses);
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
  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    TextView courseTextView = (TextView) view.findViewById(R.id.listText);
//                Toast.makeText(CourseSelectionScreen.this, courseTextView.getText().toString().trim(),Toast.LENGTH_SHORT).show();
    courseSign(courseTextView.getText().toString().trim());
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

