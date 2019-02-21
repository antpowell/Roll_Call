package com.egmail.anthony.powell.roll_call_2

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.egmail.anthony.powell.roll_call_2.Model.Course
import kotlinx.android.synthetic.main.course_item.view.*

/**
 * Created by Powell on 1/28/2018.
 */

class CourseListAdapter(context: Context) : RecyclerView.Adapter<CourseListCustomViewHolder>() {
    val fullCourseCodeList = Course.codes
    val fullCourseImageCodeList = Course.images
    var courseCodeList = fullCourseCodeList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListCustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cellForRow = inflater.inflate(R.layout.course_item, parent, false)

        return CourseListCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return courseCodeList.count()
    }

    override fun onBindViewHolder(holder: CourseListCustomViewHolder, position: Int) {
        courseCodeList.sort()

        val courseCode: String = courseCodeList[position]

        val courseImageCode: String = courseCode.replace("([^a-zA-Z]{3}L)$|[^a-zA-Z$]{3}".toRegex(), "")

        if (fullCourseImageCodeList.containsKey(courseImageCode)) {
            holder.v.course_item_image_view.webViewClient = WebViewClient()
            holder.v.course_item_image_view.loadUrl(fullCourseImageCodeList[courseImageCode])
        } else {
            holder.v.course_item_image_view.webViewClient = WebViewClient()
            holder.v.course_item_image_view.loadUrl(fullCourseImageCodeList["DEFAULT"])
        }
        holder.v.course_item_text_view.text = courseCode

    }

    fun filterList(filteredCourses: ArrayList<String>){
        courseCodeList = filteredCourses
        notifyDataSetChanged()
    }

}

class CourseListCustomViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

    init {
        v.setOnClickListener {
            println("Clicked: ${v.course_item_text_view.text}")

            val intent = Intent(v.context.applicationContext, StudentSignature::class.java)
            intent.putExtra("COURSE_TITLE", v.course_item_text_view.text)
            v.context.startActivity(intent)
            println("Moved to Student Signature activity")
        }
    }

}