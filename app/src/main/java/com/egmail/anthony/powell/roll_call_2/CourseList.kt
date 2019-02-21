package com.egmail.anthony.powell.roll_call_2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import kotlinx.android.synthetic.main.activity_course_list.*

class CourseList : AppCompatActivity() {
    private val adapter = CourseListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        setSupportActionBar(course_list_toolbar)

        println("Fetching Firebase")
        FirebaseService.fetchUserData(
                userFound = {println("User found data: $it")},
                userNotFound = { println(it) })

        course_list_recycler_view_listing.layoutManager = LinearLayoutManager(this)
        course_list_recycler_view_listing.adapter = adapter

        course_list_image_view_log_out.setOnClickListener {
            FirebaseService.signOut()
            finish()
//            startActivity(Intent(this, SignInRegister::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.course_list_search_menu_item)
        val searchView = searchItem.actionView as SearchView
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                filter(query)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                println("final ---> $query")
                filter(query)
                return false
            }
        })

        return true
    }

    fun filter(query: String?){
        val filteredCourseCodes = Course.codes.filter { it.contains(query!!.toUpperCase()) }
        println("filtered course codes ::::>> $filteredCourseCodes")
        adapter.filterList(filteredCourseCodes as ArrayList<String>)

//        adapter.filterList()
    }

}

